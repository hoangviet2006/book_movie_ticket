import {useEffect, useState} from "react";
import {getAllMovie, deleteMovie} from "../../../service/adminService/MovieService";
import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import '../../../css/admin/modalConfirm.css'
import '../../../css/admin/movie/movieAdmin.css'
import {Link} from "react-router-dom";
import {Formik, Form, Field} from "formik";

const GetAllMovieComponent = () => {
    const [movie, setMovie] = useState('');
    const [totalPage, setTotalPage] = useState(0);
    const [page, setPage] = useState(0);
    const [titleSearch, setTitleSearch] = useState('');
    const [genreSearch, setGenreSearch] = useState('');
    const [isShowModal, setIsShowModal] = useState(false);
    const [nameMovie, setNameMovie] = useState('');
    const [idDelete, setIdDelete] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const {data, totalPage} = await getAllMovie(page, titleSearch, genreSearch);
                console.log(data)
                setMovie(data)
                setTotalPage(totalPage)
            } catch (e) {
                setMovie([]);
            }
        }
        fetchData();

    }, [isShowModal, page, titleSearch, genreSearch]);
    const closeModal = () => {
        setIsShowModal((pre) => !pre)
        setIdDelete(null)
        setNameMovie(null)
    }
    const handleShowModal = (id, title) => {
        setIsShowModal((pre) => !pre);
        setNameMovie(title)
        setIdDelete(id)
    }
    const handleDeleteQuestions = () => {
        const fetchData = async () => {
            await deleteMovie(idDelete);
            setIdDelete(null);
            setIsShowModal((pre) => !pre)
        }
        fetchData()
    }
    return (
        <div className="movie-management">
            <h2 style={{textAlign: 'center'}}>Danh sách phim</h2>
            <Formik
                initialValues={{titleSearch: titleSearch, genreSearch: genreSearch}}
                onSubmit={(values) => {
                    setTitleSearch(values.titleSearch);
                    setGenreSearch(values.genreSearch);
                    setPage(0);
                }}
            >
                <Form className={'search-form'}>
                    <div className="search-field">
                        <label>
                            Tên phim:
                            <Field type="text" name="titleSearch"/>
                        </label>
                        <label>
                            Thể loại :
                            <Field type="text" name="genreSearch"/>
                        </label>
                        <button type="submit" className="search-btn">Tìm kiếm</button>
                    </div>
                </Form>
            </Formik>
            {movie && movie.length === 0 ? (
                <div className="no-data-message">
                    <p>Không có dữ liệu phim nào được tìm thấy.</p>
                </div>
            ) : (
                <div className="movies-table-container">
                    <table className="movies-table" style={{overflowX: "hidden"}}>
                        <thead>
                        <tr>
                            <th>STT</th>
                            <th>Tên phim</th>
                            <th>Mô tả</th>
                            <th>Thể loại</th>
                            <th>Thời lượng (phút)</th>
                            <th>Ngày phát hành</th>
                            <th>Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        {movie && movie.map((m, index) => (
                            <tr key={m.id}>
                                <td>{index + 1}</td>
                                <td title={m.title} style={{cursor: 'pointer'}}>{m.title}</td>
                                <td className="movie-description" title={m.description}>{m.description}</td>
                                <td>{m.genre}</td>
                                <td>{m.duration}</td>
                                <td>{m.releaseDate}</td>
                                <td>
                                    <div style={{display: 'flex'}}>
                                        <button onClick={() => handleShowModal(m.id, m.title)}
                                                className="delete-btn-table">Xóa
                                        </button>
                                        <Link to={'/admin/movie/update/' + m.id} className={'edit-btn'}>Chỉnh sửa</Link>
                                    </div>

                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}

            {isShowModal && (
                <div className="modal-overlay">
                    <div className="custom-modal">
                        <h4>Xác nhận xoá bộ phim này?</h4>
                        <p>
                            Thao tác này sẽ xoá bộ phim "{nameMovie}" của bạn. Bạn sẽ không thể
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
export default GetAllMovieComponent;