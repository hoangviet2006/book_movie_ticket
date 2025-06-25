import {ErrorMessage, Field, Form, Formik} from "formik";
import '../../css/home/login.css';
import {login} from "../../service/userService/UserService";
import {toast} from "react-toastify";
import {Link, useNavigate} from "react-router-dom";
import HeaderComponent from "../homePage/HeaderComponent";
import {useState} from "react";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import FooterComponent from "../homePage/FooterComponent";
const LoginComponent = ()=>{
    const navigate=useNavigate();
    const [showPassword, setShowPassword] = useState(false);
    const handleSubmit = async (value) => {
            const user = {
                username:value?.username,
                password:value?.password
            };
            try {
                const user1=await login(user);
                console.log("✅ user1 từ login():", user1);
                navigate('/')
            }catch (e) {
                toast.warning(e?.response?.data|| "Đăng nhập thất bại")
            }
        }
    return(
        <>
            <div className="login-container">
                <HeaderComponent/>
                <Formik initialValues={{
                    username: '',
                    password: ''
                }} onSubmit={handleSubmit}>
                    <Form className="login-form">
                        <h3>Đăng nhập</h3>
                        <label htmlFor="username">Nhập tài khoản</label>
                        <Field id="username" name="username" placeholder="Username"/>
                        <ErrorMessage name="username" component="div"/>
                        <div className="input-password-wrapper">
                            <label htmlFor="password" style={{marginTop: 20}}>Nhập mật khẩu</label>
                            <Field id="password" name="password" className={'inputPassword'}
                                   type={showPassword ? "text" : "password"}
                                   placeholder="Password"/>
                            <span onClick={() => setShowPassword(!showPassword)} className="input-password-icon">
                               {showPassword ? <FaEyeSlash/> : <FaEye/>}
                              </span>
                            <ErrorMessage name="password" component="div"/>
                        </div>
                        <div className="forgot-password">
                            <Link to="/forgot-password">Quên mật khẩu?</Link>
                        </div>
                        <button type="submit">Đăng nhập</button>
                        <div className="register-link">
                            <span>Bạn chưa có tài khoản? </span>
                            <Link to="/register">Đăng ký</Link>
                        </div>
                    </Form>
                </Formik>
            </div>
            <FooterComponent/>
        </>

    );
}
export default LoginComponent;