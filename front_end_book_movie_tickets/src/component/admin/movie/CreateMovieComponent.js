
import React, { useState } from "react";
import { toast } from "react-toastify";
import 'bootstrap/dist/css/bootstrap.min.css';
import { createMovie } from "../../../service/adminService/MovieService";
import * as Yup from "yup";
import { Formik, Form, Field, ErrorMessage } from "formik";
import {useNavigate} from "react-router-dom";
import '../../../css/admin/movie/createMovie.css'
const CreateMovieComponent = ()=>{
    const validationSchema = Yup.object({
        title: Yup.string().required("Tên phim không được để trống"),
        description: Yup.string().required("Mô tả không được để trống"),
        genre: Yup.string().required("Thể loại không được để trống"),
        duration: Yup.number().typeError("Phải là số").positive("Phải lớn hơn 0").required("Thời lượng không được để trống"),
        releaseDate: Yup.date().required("Ngày phát hành không được để trống"),
        posterUrl: Yup.string().url("Phải là một URL hợp lệ").required("Poster không được để trống"),
        price: Yup.number().typeError("Phải là số").positive("Phải lớn hơn 0").required("Giá thành khi công chiếu không được để trống"),
    });
    const initialValues = {
        title: "",
        description: "",
        genre: "",
        duration: "",
        releaseDate: "",
        posterUrl: "",
        price:''
    };
    const navigate =useNavigate();
    const handleSubmit = async (values) => {
        try {
            await createMovie(values);
            navigate('/admin/movie')
            toast.success("Thêm phim thành công!");
        } catch (err) {
            toast.error(err.response?.data || "Lỗi khi thêm phim!");
        }
    };
    return (
        <div className="create-movie-container">
                <div className={'create-movie'} style={{width:650}}>
                <h2 style={{textAlign:'center'}}>Thêm phim mới</h2>
                <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={handleSubmit}>
                    <Form>
                        <div className="form-group">
                            <label htmlFor="title">Tên phim</label>
                            <Field type="text" name="title"/>
                            <ErrorMessage name="title" component="div" className="text-danger"/>
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

                        <button type="submit">Thêm phim</button>
                    </Form>
                </Formik>
            </div>
        </div>

    );
}
export default CreateMovieComponent;