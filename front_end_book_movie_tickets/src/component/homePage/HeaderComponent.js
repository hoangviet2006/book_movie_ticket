import {Link} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {profile} from "../../service/userService/UserService";
import '../../css/home/home.css'
const HeaderComponent=()=>{
    const [user, setUser] = useState('');
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    useEffect( () => {
        const fetchData = async () => {
            try {
                const findUser = await profile();
                setUser(findUser);
                console.log(findUser)
            } catch (error) {
                console.log("lỗi:"+error)
            }
        };
        fetchData()
    }, []);
    const toggleMenu = () => {
        setIsMenuOpen(!isMenuOpen);
    };
    return(
        <>
            <header className="glass-header">
                <Link to={'/'}>
                    <div className="logo">MovieGo.vn</div>
                </Link>
                <div className={`hamburger ${isMenuOpen ? 'active' : ''}`} onClick={toggleMenu}>
                    <span></span>
                    <span></span>
                    <span></span>
                </div>

                <nav className={`nav-links ${isMenuOpen ? 'active' : ''}`}>
                    <Link to={'/user/booking/history'} className="movie">Vé của bạn</Link>
                    <div className={`user-links ${isMenuOpen ? 'active' : ''}`}>
                        {!user ? (
                            <Link to="/login" className="profile-link">Đăng nhập</Link>
                        ) : (
                            <>
                                <Link to={'/profile'} className="profile-link">
                                    <span>Xin chào {user?.username}</span>
                                </Link>
                                <Link to="/logout" className="logout-link">Đăng xuất</Link>
                            </>
                        )}
                    </div>
                </nav>
            </header>
        </>
    );
}
export default HeaderComponent;