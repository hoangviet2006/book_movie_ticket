import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { detailMovie, updateMovie } from "../../../service/adminService/MovieService";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { toast } from "react-toastify";
import "../../../css/admin/movie/updateMovie.css"
const UpdateMovieComponent=()=>{
    const { id } = useParams();
    const navigate = useNavigate();
    const [initialValues, setInitialValues] = useState(null);


    useEffect(() => {
        const fetchMovie = async () => {
            try {
                const data = await detailMovie(id);
                setInitialValues(data);
            } catch (error) {
                toast.error("Không tìm thấy phim cần sửa.");
                navigate("/movie");
            }
        };
        fetchMovie();
    }, [id, navigate]);
    const validationSchema = Yup.object({
        title: Yup.string().required("Tên phim không được để trống"),
        description: Yup.string().required("Mô tả không được để trống"),
        genre: Yup.string().required("Thể loại không được để trống"),
        duration: Yup.number().typeError("Phải là số").positive("Phải lớn hơn 0").required("Thời lượng không được để trống"),
        releaseDate: Yup.date().required("Ngày phát hành không được để trống"),
        posterUrl: Yup.string().url("Phải là một URL hợp lệ").required("Poster không được để trống"),
    });
    const handleSubmit = async (values) => {
        try {
            await updateMovie(id, values);
            toast.success("Cập nhật phim thành công!");
            navigate("/admin/movie");
        } catch (err) {
            toast.error(err.response?.data || "Lỗi khi cập nhật phim!");
        }
    };
    return (
        <div className="update-movie-container">
            <div className={'update-movie'} style={{width:650}}>
                <h2 style={{textAlign:'center'}}>Chỉnh sửa phim</h2>
                <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={handleSubmit}
                        enableReinitialize={true}>
                    <Form>
                        <div className="form-group">
                            <div className="form-group">
                                <label htmlFor="title">Tên phim</label>
                                <Field type="text" name="title"/>
                                <ErrorMessage name="title" component="div" className="text-danger"/>
                            </div>
                        </div>

                        <div className="form-group">
                            <label htmlFor="description">Mô tả</label>
                            <Field as="textarea" name="description"/>
                            <ErrorMessage name="description" component="div" className="text-danger"/>
                        </div>

                        <div className="form-group">
                            <label htmlFor="genre">Thể loại</label>
                            <Field type="text" name="genre"/>
                            <ErrorMessage name="genre" component="div" className="text-danger"/>
                        </div>

                        <div className="form-group">
                            <label htmlFor="duration">Thời lượng (phút)</label>
                            <Field type="number" name="duration"/>
                            <ErrorMessage name="duration" component="div" className="text-danger"/>
                        </div>

                        <div className="form-group">
                            <label htmlFor="releaseDate">Ngày phát hành</label>
                            <Field type="date" name="releaseDate"/>
                            <ErrorMessage name="releaseDate" component="div" className="text-danger"/>
                        </div>

                        <div className="form-group">
                            <label htmlFor="posterUrl">Poster (URL)</label>
                            <Field type="text" name="posterUrl" className="form-control"/>
                            <ErrorMessage name="posterUrl" component="div" className="text-danger"/>
                        </div>

                        <div className="form-group">
                            <label htmlFor="price">Giá thành khi công chiếu</label>
                            <Field type="number" name="price" className="form-control"/>
                            <ErrorMessage name="price" component="div" className="text-danger"/>
                        </div>

                        <button type="submit">Cập nhật phim</button>
                    </Form>
                </Formik>
            </div>

        </div>
    );
}
export default UpdateMovieComponent;