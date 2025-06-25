import {ErrorMessage, Field, Form, Formik} from "formik";
import '../../css/home/forgotPassword.css'
import HeaderComponent from "../homePage/HeaderComponent";
import {forgotPassword} from "../../service/userService/UserService";
import {useState} from "react";
import {toast} from "react-toastify";
import {Link, useNavigate} from "react-router-dom";
import FooterComponent from "../homePage/FooterComponent";

const ForgotPasswordComponent = () => {
    const [loading, setLoading] = useState(false);
    const navigate= useNavigate();
    const handleSubmit = async (value) => {
        setLoading(true)
        localStorage.setItem("email",value?.email)
        try {
            const fetchData= async ()=>{
               const forgot=await forgotPassword(value?.email)
                navigate('/forgot-password-otp')
                toast(forgot)
            }
           await fetchData();
        }catch (e){
            toast.warning(e?.response?.data|| "Lỗi hệ thống")
            setLoading(false)
        }
    }
    return (
        <>
            <div className="hero-movie">
                <HeaderComponent/>
                <div className="forgotpassword-content">
                    <Formik initialValues={{
                        email: ''
                    }} onSubmit={handleSubmit}>
                        <Form className="forgotpassword-form">
                            <label>Nhập email khi đăng kí tài khoản</label>
                            <Field name={'email'}></Field>
                            <ErrorMessage name={'email'}></ErrorMessage>
                            <button type="submit" disabled={loading} className="submit-button">
                                {loading ? <span className="spinner"></span> : "Xác nhận"}
                            </button>
                            <div className="forgot-password">
                                <Link to="/login">Quay lại trang đăng nhập</Link>
                            </div>
                        </Form>
                    </Formik>
                </div>
            </div>
            <FooterComponent/>
        </>

    );
}
export default ForgotPasswordComponent;