import {ErrorMessage, Field, Form, Formik} from "formik";
import * as Yup from "yup";
import {useNavigate} from "react-router-dom";
import {confirmPasswordForgot} from "../../service/userService/UserService";
import {toast} from "react-toastify";
import '../../css/home/confirmPassword.css'

const ConfirmForgotPasswordComponent = () => {
    const navigate = useNavigate();
    const handleSubmit = async (values) => {
        try {
            const data = {
                email: localStorage.getItem('email'),
                code: localStorage.getItem('OTP'),
                password: values?.password
            };
            await confirmPasswordForgot(data);
            toast("Cập nhật mật khẩu thành công");
            localStorage.removeItem("OTP")
            localStorage.removeItem("email")
            navigate("/login");
        } catch (error) {
            toast.warning(error?.response?.data)
        }
    };
    const validationSchema = Yup.object({
        password: Yup.string()
            .required("Vui lòng nhập mật khẩu mới")
            .min(6, "Mật khẩu phải có ít nhất 6 ký tự"),
        confirmPassword: Yup.string()
            .required("Vui lòng xác nhận mật khẩu")
            .oneOf([Yup.ref("password")], "Mật khẩu không khớp"),
    });
    return (
        <div className="confirm-password-container">

            <Formik
                initialValues={{password: "", confirmPassword: ""}}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
            >
                <Form className={'confirm-password-form'}>
                    <h3>Lấy lại mật khẩu</h3>
                    <div className="form-group">
                        <label>Nhập mật khẩu mới</label>
                        <Field type="password" name="password" placeholder="password"/>
                        <ErrorMessage name="password" component="div" className="error"/>
                    </div>
                    <div className="form-group">
                        <label>Xác nhận mật khẩu mới</label>
                        <Field type="password" name="confirmPassword" placeholder="confirmPassword"/>
                        <ErrorMessage name="confirmPassword" component="div" className="error"/>
                        <button type="submit">Xác nhận</button>
                    </div>
                </Form>
            </Formik>
        </div>
    );
}
export default ConfirmForgotPasswordComponent;