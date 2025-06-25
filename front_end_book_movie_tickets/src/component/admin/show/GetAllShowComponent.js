import React, {useEffect, useState} from "react";
import {getAllShow,deleteShow} from "../../../service/adminService/ShowService";
import {useNavigate,Link} from "react-router-dom";
import {toast} from "react-toastify";
import "../../../css/admin/show/showAdmin.css"
import dayjs from "dayjs";
const GetAllShowComponent = () => {
    const [show, setShow] = useState([]);
    const [date, setDate] = useState('');
    const [time, setTime] = useState('');
    const [page, setPage] = useState(0);
    const [totalPage, setTotalPage] = useState(0);
    const [isShowModal, setIsShowModal] = useState(false);
    const [idDelete,setIdDelete] = useState(null);
    const [dateDelete,setDateDelete] = useState(null);
    const [startTimeDelete,setStartTimeDelete] = useState(null);
    const [endTimeDelete,setEndTimeDelete] = useState(null);
    const navigate = useNavigate();
    useEffect(() => {
        try {
            const fetchData = async () => {
                const {data, totalPage} = await getAllShow(date, time)
                console.log(data)
                setShow(data);
                setTotalPage(totalPage);
            }
            fetchData();
        } catch (e) {
            console.log("lỗi khi lấy dữ liệu" + e)
        }
    }, [date, time, page, isShowModal]);
    const handleShowModal =(id,date,startTime,endTime)=>{
        setIsShowModal((pre)=>!pre);
        setDateDelete(date)
        setStartTimeDelete(startTime)
        setEndTimeDelete(endTime);
        setIdDelete(id)
    }
    const handleCLoseModal=()=>{
        setIdDelete(null);
        setDateDelete(null);
        setStartTimeDelete(null);
        setEndTimeDelete(null);
        setIsShowModal(false)
    }
    const handleDelete = async ()=>{
        await deleteShow(idDelete);
        setIsShowModal(false);
        setIdDelete(null);
        setDateDelete(null);
        setStartTimeDelete(null);
        setEndTimeDelete(null);
        navigate('/admin/show');
        toast("Xoá thành công!")
    }
    return (
        <div className="show-management">
            <h2 style={{textAlign: 'center'}}>Danh sách xuất chiếu</h2>
            <div className="show-table-container">
                <table className="show-table" style={{overflowX: "hidden"}}>
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Ngày chiếu</th>
                        <th>Thời gian bắt đầu</th>
                        <th>Thời gian kết thúc</th>
                        <th>Tên phim</th>
                        <th>Tên phòng</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    {show && show.map((s, i) => (
                        <tr>
                            <td key={s?.id}>{i + 1}</td>
                            <td>{dayjs(s?.dateShow).format("DD/MM/YYYY")}</td>
                            <td>{s?.startTime}</td>
                            <td>{s?.endTime}</td>
                            <td>{s?.movie?.title}</td>
                            <td>{s?.room?.name}</td>
                            <td>
                                <button onClick={() => handleShowModal(s.id, s.date, s.startTime, s.endTime)}
                                 className={'delete-btn'}>
                                    Xoá
                                </button>
                                <Link to={'/admin/show/update/'+s.id} className={'edit-btn'}> chỉnh sửa</Link>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
            {isShowModal && (
                <div className="modal-overlay">
                    <div className="custom-modal">
                        <h4>Xác nhận xoá xuất chiếu này?</h4>
                        <p>
                            Thao tác này sẽ xoá xuất chiếu ngày "{dateDelete} vào lúc {startTimeDelete}--{endTimeDelete}"
                            của bạn. Bạn sẽ không thể
                            huỷ được thao tác này sau khi thực hiện.
                        </p>
                        <div className="modal-buttons">
                            <button className="cancel-btn" onClick={handleCLoseModal}>
                                Huỷ
                            </button>
                            <button className="delete-btn" onClick={handleDelete}>
                                Xoá xuất chiếu?
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
export default GetAllShowComponent;