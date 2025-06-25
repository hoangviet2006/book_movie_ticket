import React, {useEffect, useState} from "react";
import {getAllRoom,deleteRoom} from "../../../service/adminService/RoomService";
import {Link, useNavigate} from "react-router-dom";
import {Field, Form, Formik} from "formik";
import '../../../css/admin/room/roomAdmin.css'
import {toast} from "react-toastify";
const GetAllRoomComponent = () => {
    const [room, setRoom] = useState([]);
    const [nameSearch, setNameSearch] = useState('');
    const [startSeatCount, setStartSeatCount] = useState('');
    const [endSeatCount, setEndSeatCount] = useState('');
    const [totalPage, setTotalPage] = useState(0)
    const [page, setPage] = useState(0)
    const [isShowModal,setIsShowModal] = useState(false);
    const [idDelete,setIdDelete]= useState(null);
    const [nameDelete,setNameDelete]= useState(null);
    const navigate=  useNavigate();
    useEffect(() => {
        const fetchData = async () => {
            try {
                const {
                    data,
                    totalPage
                } = await getAllRoom(page, nameSearch, startSeatCount, endSeatCount)
                setRoom(data)
                setTotalPage(totalPage)
            } catch (e) {
                setRoom([])
            }
        }
        fetchData();
    }, [page, nameSearch,  startSeatCount, endSeatCount,isShowModal]);
    const handleShowModal = (id,name)=>{
        setIdDelete(id);
        setNameDelete(name);
        setIsShowModal((pre)=>!pre);
    }
    const handleCloseModal =()=>{
        setIsShowModal(false);
        setIdDelete(null);
        setNameDelete(null);
    }
    const handleDelete = async ()=>{
        await deleteRoom(idDelete);
        setIdDelete(null);
        setNameDelete(null);
        setIsShowModal(false);
        navigate('/admin/room');
        toast("Xoá thành công!")
    }
    return (
        <div className="room-management">
            <h2 style={{textAlign: 'center'}}>Danh sách phòng chiếu</h2>
            <Formik
                initialValues={{
                    nameSearch: nameSearch,
                    startSeatCount: startSeatCount,
                    endSeatCount: endSeatCount
                }}
                onSubmit={(values) => {
                    setNameSearch(values.nameSearch);
                    setStartSeatCount(values.startSeatCount);
                    setEndSeatCount(values.endSeatCount);
                    setPage(0);
                }}
                enableReinitialize={true}>
                <Form className={'search-form'}>
                    <div className="search-field">
                        <label>
                            Tên phòng chiếu:
                            <Field type="text" name="nameSearch"/>
                        </label>
                        <label>
                            Số ghế từ
                            <Field type="text" name="startSeatCount"/>
                        </label>
                        <label>
                            Đến
                            <Field type="text" name="endSeatCount"/>
                        </label>
                        <button type={'submit'} className={'search-btn'}>Tìm kiếm</button>
                    </div>
                </Form>
            </Formik>
                <div className="room-table-container">
                    <table className="room-table" style={{overflowX:"hidden"}}>
                        <thead>
                        <tr>
                            <th>STT</th>
                            <th>Tên phòng chiếu</th>
                            <th>Số ghế</th>
                            <th>Tên rạp chiếu</th>
                            <th>Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        {room && room.length === 0 ? (
                            <tr>
                                <td colSpan="5" style={{ textAlign: 'center', padding: '1rem' }}>
                                    Không có dữ liệu phòng chiếu.
                                </td>
                            </tr>
                        ) : (
                        room && room.map((r, index) => (
                            <tr key={r.id}>
                                <td>{index + 1}</td>
                                <td>{r.name}</td>
                                <td>{r.seatCount}</td>
                                <td>{r.cinema?.name}</td>
                                <td>
                                    <div className={'room-btn'}>
                                        <Link to={'/admin/room/detail/' + r.id} className={'edit-btn'} style={{textDecoration:'none'}}>Chi tiết</Link>
                                        <Link to={'/admin/room/update/' + r.id} className={'edit-btn'}>Chỉnh sửa</Link>
                                        <button className={'delete-btn'}
                                           onClick={()=>handleShowModal(r.id,r.name)}>Xoá</button>
                                    </div>
                                </td>
                            </tr>
                        )))}
                        </tbody>
                    </table>
                </div>
            {isShowModal && (
                <div className="modal-overlay">
                    <div className="custom-modal">
                        <h4>Xác nhận xoá phòng chiếu này?</h4>
                        <p>
                            Thao tác này sẽ xoá phòng chiếu "{nameDelete}" của bạn. Bạn sẽ không thể
                            huỷ được thao tác này sau khi thực hiện.
                        </p>
                        <div className="modal-buttons">
                            <button className="cancel-btn" onClick={handleCloseModal}>
                                Huỷ
                            </button>
                            <button className="delete-btn" onClick={handleDelete}>
                                Xác nhận xoá phòng chiếu này
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>

    );
}
export default GetAllRoomComponent;