import {ErrorMessage, Field, Form, Formik} from "formik";
import * as Yup from "yup";
import {useNavigate} from "react-router-dom";
import {confirmPasswordForgot, resetPassword} from "../../service/userService/UserService";
import {toast} from "react-toastify";
import '../../css/home/confirmPassword.css'
import HeaderComponent from "../homePage/HeaderComponent";

const ResetPasswordComponent = () => {
    const navigate = useNavigate();

    const handleSubmit = async (values) => {
        console.log("submit", values);
        try {
            const newPasswordUser = {
                username: localStorage.getItem('username'),
                password:values?.password,
                newPassword:values?.newPassword
            }

            const response= await resetPassword(newPasswordUser)
            toast(response)
            navigate("/login");
        } catch (error) {
            toast.warning(error?.response?.data)
        }
    };
    const validationSchema = Yup.object({
        password: Yup.string()
            .required("Vui lòng nhập mật hiện tại")
            .min(6, "Mật khẩu phải có ít nhất 6 ký tự"),
        newPassword:Yup.string()
            .required("Vui lòng nhập mật khẩu mới")
            .min(6, "Mật khẩu phải có ít nhất 6 ký tự"),
        confirmNewPassword: Yup.string()
            .required("Vui lòng xác nhận mật khẩu")
            .oneOf([Yup.ref("newPassword")], "Mật khẩu không khớp"),
    });
    return (
        <div className="confirm-password-container">
            <HeaderComponent/>
            <Formik
                initialValues={{password: '',newPassword:'', confirmNewPassword: ''}}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
            >
                <Form className={'confirm-password-form'}>
                    <h3>Cập nhật mật khẩu</h3>
                    <div className="form-group">
                        <label>Nhập mật hiện tại</label>
                        <Field type="password" name="password" />
                        <ErrorMessage name="password" component="div" className="error"/>
                    </div>
                    <div className="form-group">
                        <label>Nhập mật khẩu mới</label>
                        <Field type="password" name="newPassword" />
                        <ErrorMessage name="newPassword" component="div" className="error"/>
                    </div>
                    <div className="form-group">
                        <label>Xác nhận mật khẩu mới</label>
                        <Field type="password" name="confirmNewPassword"/>
                        <ErrorMessage name="confirmNewPassword" component="div" className="error"/>
                    </div>
                    <button type={'submit'}>Xác nhận</button>
                </Form>
            </Formik>
        </div>
    );
}
export default ResetPasswordComponent;