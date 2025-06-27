import React, {useEffect, useState} from "react";
import '../../css/home/home.css'
import ClickSpark from "../animation/ClickSpark";
import FooterComponent from "./FooterComponent";
import HeaderComponent from "./HeaderComponent";
import {Clock} from "lucide-react";
import {Link, useLocation} from "react-router-dom";
import SplashCursor from "../animation/SplashCursor";
import {getAllShowOnDisplay} from "../../service/userService/ShowService";
import {toast} from "react-toastify";

const HomeComponent = () => {
    const [movie, setMovie] = useState([]);
    const {search} = useLocation()
    const queryParams = new URLSearchParams(search);
    const message = queryParams.get('message');
    const status = queryParams.get('status');
    useEffect(() => {
        const fetchData = async () => {
            if (message) {
                toast[status === 'success' ? 'success' : 'error'](message);
            }
            try {
                const data = await getAllShowOnDisplay();
                console.log(data)
                setTimeout(() => {
                    setMovie(data);
                });
            } catch (e) {
                console.error("Lỗi", e);
            }
        }
        fetchData();
    }, [message, status]);
    const formatPrice = (price) => {
        const num = Number(price);
        if (isNaN(num)) return '0 ₫'; // hoặc "Liên hệ"
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(num);
    };
    const getTodayDate = () => {
        const today = new Date();
        return today.toISOString().split('T')[0]; // '2025-06-19' chẳng hạn
    };

    return (
        <ClickSpark
            sparkColor='#fff'
            sparkSize={10}
            sparkRadius={15}
            sparkCount={8}
            duration={400}
        >
            {message && <div className={status === 'success' ? 'text-green-600' : 'text-red-600'}>
            </div>}
            <div className="page-container">
                <div className="hero-movie">
                    <HeaderComponent/>
                    <div className="hero-content">
                        <h1>Chào mừng bạn đến với MovieGo.vn</h1>
                        <p>Trải nghiệm điện ảnh tuyệt vời không cần xếp hàng không lo hết chỗ đặt vé ngay để chọn vị trí
                            ưng ý nhất! Đặt vé xem phim ngay !!!</p>
                        <button className="btn">Khám phá ngay</button>
                    </div>
                    <div className="movie-slider">
                        <div className="slides">
                            <img src="https://cdn-media.sforum.vn/storage/app/media/truyen-cua-nhi-can-1.jpg"
                                 alt="Inception"/>
                            <img src="https://image.tmdb.org/t/p/w500/oRvMaJOmapypFUcQqpgHMZA6qL9.jpg"
                                 alt="The Dark Knight"/>
                            <img
                                src="https://th.bing.com/th/id/R.54126a728f4110eed30453acbf529ac9?rik=he1vICf47uQ2OQ&pid=ImgRaw&r=0"
                                alt="Monkey King: Hero Is Back"/>
                            <img src="https://images.unsplash.com/photo-1517602302552-471fe67acf66"
                                 alt="The Dark Knight"/>
                            <img
                                src="https://th.bing.com/th/id/R.0885f18015f3780830ae1c138103c9b3?rik=aLCJuBfpDz%2bIOA&pid=ImgRaw&r=0"
                                alt="Avengers: Endgame"/>
                            <img
                                src="https://www.themoviedb.org/t/p/original/4BeWNciB0UHTBeSKF42nbteaTXM.jpg"
                                alt="Dahufa "/>
                            <img
                                src="https://live.staticflickr.com/7927/46604301952_2ac9f1c889.jpg"
                                alt="Huyền Thoại La Tiểu Hắc"/>
                            <img
                                src="https://m.media-amazon.com/images/M/MV5BYmRlODVlZDAtNzcxOC00N2U2LWE4MmQtZTYzZjg3Yjg1MzUxXkEyXkFqcGc%40._V1_FMjpg_UX1000_.jpg"
                                alt="Natra Ma Đồng Náo Hải"/>
                            <img
                                src="https://obj.shine.cn/files/2025/03/06/ea1368bc-10b1-46d4-b9b2-09bc9678aa15_0.jpg"
                                alt="Natra Ma Đồng Giáng Thế"/>

                            <img src="https://images.unsplash.com/photo-1534447677768-be436bb09401"
                                 alt="The Dark Knight"/>
                        </div>
                    </div>
                    {movie&&movie.length>0 &&(
                        <div className="showtime-announcement">
                            Các suất chiếu hiện đang có trong hệ thống. Hãy chọn phim yêu thích để đặt vé ngay và
                            giữ chỗ ngồi đẹp nhất!
                        </div>
                    )}
                    <div className="movie-grid">
                        {movie && movie.map((m, i) => (
                            <Link to={`/movie/${m.idMovie}/detail`} style={{cursor: 'pointer', textDecoration: 'none'}}>
                                <div key={`${m.idMovie}-${m.nameRoom}`} className="movie-card">
                                    <img
                                        src={m.url}
                                        alt={m.title}
                                        className="movie-poster"
                                    />
                                    <h3 className="movie-title">{m.titleMovie}</h3>
                                    <p className="movie-genre">{m.genre}</p>
                                    <div className="movie-info">
                                        <div className="info-item">
                                            <Clock size={16}/>
                                            <span>{m.duration} phút</span>
                                        </div>
                                        <div className="info-item">
                                            <span> Rạp: {m.nameCinema} – {m?.showtime[0].nameRoom}</span>
                                        </div>
                                    </div>
                                    <p className="movie-description">{m.description}</p>
                                    <div className="movie-footer">
                                        <div className="movie-price">
                                            {formatPrice(m.price)}
                                        </div>
                                    </div>
                                    <div className="showtimes-container">
                                        <h4 className="showtimes-title">Suất chiếu:</h4>
                                        <div className="showtimes-list">
                                            {m.showtime &&
                                            m.showtime.filter(show => show.dateShow === getTodayDate()).length > 0 ? (
                                                <>
                                                    {m.showtime.filter(show => show.dateShow === getTodayDate())
                                                        .splice(0, 3)
                                                        .map((show) => (
                                                            <button
                                                                className="showtime-button"
                                                            >
                                                                <Clock size={20} style={{marginRight: 2}}></Clock>
                                                                <span>{show.startTime}</span>
                                                            </button>
                                                        ))}
                                                    {m.showtime.filter(show => show.dateShow === getTodayDate()).length > 3
                                                        && (
                                                            <span
                                                                style={{
                                                                    color: '#ccc',
                                                                    fontStyle: 'italic'
                                                                }}>......</span>)}
                                                </>
                                            ) : (
                                                <span style={{color: '#ccc', fontStyle: 'italic', fontSize: '12.7px'}}>
                                                   Không có suất chiếu hôm nay – nhấn vào để xem các ngày khác</span>
                                            )}
                                        </div>
                                    </div>
                                </div>
                            </Link>
                        ))}
                    </div>
                    <div className="how-it-works-section">
                        <h2 className="how-title">Nó hoạt động như thế nào</h2>
                        <div className="steps-container">
                            <div className="step-card">
                                <div className="step-icon">1</div>
                                <h3>Chọn phim của bạn</h3>
                                <p>Duyệt bộ sưu tập phim phong phú của chúng tôi và chọn phim yêu thích của bạn. Kiểm
                                    tra xếp hạng, đoạn giới thiệu và thời gian chiếu.</p>
                            </div>
                            <div className="step-card">
                                <div className="step-icon">2</div>
                                <h3>Chọn chỗ ngồi và mua vé</h3>
                                <p>Chọn chỗ ngồi ưu thích bằng bản đồ chỗ ngồi tương tác của chúng tôi.</p>
                            </div>
                            <div className="step-card">
                                <div className="step-icon">3</div>
                                <h3>Thưởng thức bộ phim của bạn</h3>
                                <p>Nhận vé qua email . Xuất trình tại lối vào rạp và tận hưởng trải nghiệm xem
                                    phim tuyệt vời!</p>
                            </div>
                        </div>
                    </div>
                </div>
                {/*<SplashCursor/>*/}
                <FooterComponent/>
            </div>
        </ClickSpark>
    );
}
export default HomeComponent;