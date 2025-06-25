import {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {profile} from "../../service/userService/UserService";
import HeaderComponent from "../homePage/HeaderComponent";
import '../../css/home/home.css'
import '../../css/user/profile.css'
import {ErrorMessage, Field, Form, Formik} from "formik";
import {FaEye, FaEyeSlash} from "react-icons/fa";
import {Link} from "react-router-dom";
import FooterComponent from "../homePage/FooterComponent";

const Profile = () => {
    const [user, setUser] = useState('');
    const email = localStorage.getItem('email')
    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await profile();
                localStorage.setItem('username',data?.username)
                setUser(data);
            } catch (e) {
                if (e.response && e.response.status === 403) {
                    toast.error("Không có quyền truy cập");
                } else {
                    toast.error(e.response?.data || "Lỗi xảy ra");
                }
            }
        }
        fetchData()
    }, []);
    const handleSubmit = () => {

    }
    return (
        <>

            <div className="hero-movie">
                <HeaderComponent/>
                <div className="profile-content">
                    <Formik initialValues={{
                        username: user?.username,
                        password: user?.password,
                        email: email
                    }} onSubmit={handleSubmit} enableReinitialize={true}>
                        <Form className="profile-form">
                            <label htmlFor="username">Tên tài khoản</label>
                            <Field name={'username'}></Field>
                            <ErrorMessage name={'username'} component={'div'}/>
                            <label htmlFor="email">Email</label>
                            <Field name={'email'} ></Field>
                            <ErrorMessage name={'email'} component={'div'}/>
                            <button className="submit-button"> Cập nhật</button>
                            <div className="register-link">
                                <Link to="/reset-password">Đổi mật khẩu?</Link>
                            </div>
                        </Form>
                    </Formik>
                </div>
            </div>
            <FooterComponent/>
        </>

    );
}
export default Profile;