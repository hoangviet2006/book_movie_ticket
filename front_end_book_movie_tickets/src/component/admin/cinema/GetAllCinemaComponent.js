import React, {useEffect, useState} from "react";
import {Formik, Form, Field} from "formik";
import {deleteCinema, getAllCinema} from "../../../service/adminService/CicemaService";
import {Link, useNavigate} from "react-router-dom";
import {toast} from "react-toastify";

import '../../../css/admin/cinema/cinemaAdmin.css'
const GetAllCinemaComponent = () => {
    const [cinema, setCinema] = useState([]);
    const [isShowModal, setIsShowModal] = useState(false);
    const [idDelete, setIdDelete] = useState(null);
    const [nameDelete, setNameDelete] = useState(null);
    const [totalPage, setTotalPage] = useState(0);
    const [page, setPage] = useState(0);
    const [nameSearch, setNameSearch] = useState('');
    const [addressSearch, setAddressSearch] = useState('');
    const  navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const {data, totalPage} = await getAllCinema(page, nameSearch, addressSearch);
                console.log(data)
                setCinema(data);
                setTotalPage(totalPage)
            } catch (e) {
                setCinema([])
            }
        }
        fetchData();

    }, [isShowModal, page, nameSearch, addressSearch,isShowModal]);

    const handleDelete = (id, name) => {
        setIsShowModal((pre => !pre));
        setIdDelete(id);
        setNameDelete(name);
    }
    const closeModal = () => {
        setIsShowModal((pre => !pre));
        setIdDelete(null);
        setNameDelete(null);
    }
    const handleDeleteQuestions = async () => {
        await deleteCinema(idDelete);
        setIsShowModal((pre => !pre));
        setIdDelete(null);
        setNameDelete(null);
        navigate('/admin/cinema')
        toast("Xoá thành công")
    }
    return (
        <div className="cinema-management">
            <h2 style={{textAlign: 'center'}}>Danh sách rạp chiếu</h2>
            <Formik
                initialValues={{nameSearch: nameSearch, addressSearch: addressSearch}}
                onSubmit={(values) => {
                    setNameSearch(values.nameSearch);
                    setAddressSearch(values.addressSearch);
                    setPage(0);
                }}
            >
                <Form className={'search-form'}>
                    <div className="search-field">
                        <label>
                            Tên rạp:
                            <Field type="text" name="nameSearch"/>
                        </label>
                        <label>
                            Địa chỉ:
                            <Field type="text" name="addressSearch"/>
                        </label>
                        <button type={'submit'} className="search-btn">Tìm kiếm</button>
                    </div>
                </Form>
            </Formik>
            <div className="cinema-table-container">
                <table className="cinema-table" style={{overflowX:"hidden"}}>
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Tên rạp</th>
                        <th>Địa chỉ</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    {cinema && cinema.length > 0 ? (
                        cinema.map((cinema, index) => (
                            <tr key={cinema.id}>
                                <td>{index+1}</td>
                                <td>{cinema.name}</td>
                                <td>{cinema.address}</td>
                                <td>
                                    <div style={{display: 'flex'}}>
                                        <button onClick={() => handleDelete(cinema.id, cinema.name)}
                                                className="delete-btn-table">Xoá
                                        </button>
                                        <Link to={'/admin/cinema/update/' + cinema.id}
                                              className={'edit-btn'}>Chỉnh sửa</Link>
                                    </div>

                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>

                            <div className="no-data-message">
                                <td colSpan="3">Không có dữ liệu</td>
                            </div>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>

            {isShowModal && (
                <div className="modal-overlay">
                    <div className="custom-modal">
                        <h4>Xác nhận xoá rạp chiếu này?</h4>
                        <p>
                            Thao tác này sẽ xoá rạp chiếu "{nameDelete}" của bạn. Bạn sẽ không thể
                            huỷ được thao tác này sau khi thực hiện.
                        </p>
                        <div className="modal-buttons">
                            <button className="cancel-btn" onClick={closeModal}>
                                Huỷ
                            </button>
                            <button className="delete-btn" onClick={handleDeleteQuestions}>
                                Xác nhận xoá bộ phim này
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
export default GetAllCinemaComponent;