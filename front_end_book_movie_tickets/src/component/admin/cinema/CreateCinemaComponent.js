
import React from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import {createCinema} from "../../../service/adminService/CicemaService";
import '../../../css/admin/cinema/createCinema.css'
const CreateCinemaComponent=()=>{
    const navigate = useNavigate();
    const initialValues = {
        name: "",
        address: ""
    };
    const validationSchema = Yup.object({
        name: Yup.string().required("Tên rạp không được để trống"),
        address: Yup.string().required("Địa chỉ không được để trống"),
    });
    const handleSubmit = async (values) => {
        try {
            await createCinema(values);
            toast.success("Thêm rạp thành công!");
            navigate("/admin/cinema");
        } catch (err) {
            toast.error(err.response?.data || "Lỗi khi thêm rạp!");
        }
    };
    return (
        <div className={'create-cinema-container'}>
            <div className={'create-cinema'} style={{width:500,marginTop:200}}>
                <h2 style={{textAlign:'center'}}>Thêm mới rạp chiếu</h2>
                <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={handleSubmit}>
                    <Form>
                        <div className="form-group">
                            <label htmlFor="name">Tên rạp:</label>
                            <Field type="text" name="name"/>
                            <ErrorMessage name="name" component="div" className="text-danger"/>
                        </div>

                        <div className="form-group">
                            <label htmlFor={'address'}>Địa chỉ:</label>
                            <Field type="text" name="address" as="textarea"/>
                            <ErrorMessage name="address" component="div" className="text-danger"/>
                        </div>

                        <button type="submit" >Thêm rạp</button>
                    </Form>
                </Formik>
            </div>

        </div>
    );
}
export default CreateCinemaComponent;