import React, {useEffect, useState} from "react";
import {useParams, useNavigate} from "react-router-dom";
import {detailCinema, updateCinema} from "../../../service/adminService/CicemaService";
import {Formik, Form, Field, ErrorMessage} from "formik";
import * as Yup from "yup";
import {toast} from "react-toastify";
import '../../../css/admin/cinema/updateCinema.css'
const UpdateCinemaComponent = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const [initialValue, setInitialValue] = useState(null);
    useEffect(() => {
        const fetchCinema = async () => {
            try {
                const res = await detailCinema(id);
                setInitialValue(res);
            } catch (err) {
                toast.error("Không tìm thấy rạp");
                navigate("/cinema");
            }
        };
        fetchCinema();
    }, [id, navigate]);

    const validationSchema = Yup.object({
        name: Yup.string().required("Tên rạp không được để trống"),
        address: Yup.string().required("Địa chỉ không được để trống"),
    });
    const handleSubmit = async (values) => {
        try {
            await updateCinema(id, values);
            toast("Cập nhật thành công!");
            navigate("/admin/cinema");
        } catch (err) {
            toast.error(err?.response?.data || "Lỗi khi cập nhật rạp!");
        }
    };

    return (
        <div className="update-cinema-container">
            <div className={'update-cinema'} style={{width:500,marginTop:200}}>
                <h2 style={{textAlign:'center'}}>Chỉnh sửa rạp chiếu</h2>
                <Formik initialValues={initialValue} validationSchema={validationSchema} onSubmit={handleSubmit}
                        enableReinitialize={true}>
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
                        <button type="submit">Cập nhật</button>
                    </Form>
                </Formik>
            </div>

        </div>
    )
}
export default UpdateCinemaComponent;