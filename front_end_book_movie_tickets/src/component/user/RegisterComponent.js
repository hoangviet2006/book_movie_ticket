import {ErrorMessage, Field, Form, Formik} from "formik";
import {register} from "../../service/userService/UserService";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import '../../css/home/register.css';
import {Link} from "react-router-dom";
import {useState} from "react";
import HeaderComponent from "../homePage/HeaderComponent";
import * as Yup from "yup";
import FooterComponent from "../homePage/FooterComponent";

const RegisterComponent = () => {
    const navigate=useNavigate();
    const [loading, setLoading] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
    const handleSubmit = async (value)=>{
        const newUser = {
            username:value?.username,
            password:value?.password,
            email:value?.email
        };
        setLoading(true);
        try {
          const registerMessage= await register(newUser);
          toast(registerMessage)
            navigate('/confirmOtp')
        }catch (e) {
           const errorMessage = e?.response?.data
            if (errorMessage === "Tên đăng nhập đã tồn tại") {
                toast.warning("Tên đăng nhập đã tồn tại, vui lòng chọn tên khác");
            } else if (errorMessage === "Email đã được sử dụng") {
                toast.warning("Email đã được sử dụng, vui lòng dùng email khác");
            } else {
                toast.warning(errorMessage || "Đăng ký thất bại");
            }
            setLoading(false);
        }
    }

    const validationSchema = Yup.object({
        username: Yup.string().required("Vui lòng nhập tài khoản"),
        password: Yup.string()
            .required("Vui lòng nhập mật khẩu")
            .min(6, "Mật khẩu phải có ít nhất 6 ký tự"),
        confirmPassword: Yup.string()
            .required("Vui lòng xác nhận mật khẩu")
            .oneOf([Yup.ref('password'), null], "Mật khẩu không khớp"),
        email: Yup.string()
            .required("Vui lòng nhập email")
            .email("Email không hợp lệ"),
    });
    return (
        <>
            <div className="register-container">
                <HeaderComponent/>
                <Formik initialValues={{
                    username: '',
                    password: '',
                    confirmPassword: '',
                    email: ''
                }} onSubmit={handleSubmit} validationSchema={validationSchema}>
                    <Form className="register-form">
                        <h3>Đăng ký tài khoản</h3>
                        <label htmlFor="username">Nhập tài khoản</label>
                        <Field id="username" name="username" placeholder="Username"/>
                        <ErrorMessage name="username" component="div"/>

                        <label htmlFor="password">Nhập mật khẩu</label>
                        <Field id="password" name="password" type={showPassword ? "text" : "password"}
                               placeholder="Password"/>
                        <ErrorMessage name="password" component="div"/>

                        <label htmlFor="confirmPassword">Xác nhận mật khẩu</label>
                        <Field
                            id="confirmPassword"
                            name="confirmPassword"
                            type={showPassword ? "text" : "password"}
                            placeholder="Confirm Password"
                        />
                        <ErrorMessage name="confirmPassword" component="div"/>

                        <label htmlFor="email">Nhập email</label>
                        <Field id="email" name="email" type="email" placeholder="Email"/>
                        <ErrorMessage name="email" component="div"/>
                        <button type="submit" disabled={loading} className="submit-button">
                            {loading ? <span className="spinner"></span> : "Đăng ký"}
                        </button>
                        <div className="register-link">
                            <span>Đã có tài khoản? </span>
                            <Link to="/login">Đăng nhập</Link>
                        </div>
                    </Form>
                </Formik>

            </div>
            <FooterComponent/>
        </>

    );
}
export default RegisterComponent;