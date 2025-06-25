import {useEffect, useState} from "react";
import {getAllMovie} from "../../service/userService/MovieService";
import {Clock, Star, Calendar, MapPin} from "lucide-react";
import '../../css/user/movieUser.css'
import HeaderComponent from "../homePage/HeaderComponent";
const GetAllMovieUserComponent = () => {
    const [movie, setMovie] = useState([]);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const getMovie = await getAllMovie();
                setTimeout(() => {
                    setMovie(getMovie);
                    setLoading(false);
                }, 2000);
            } catch (e) {
                console.error("Lỗi", e);
                setLoading(false);
            }
        }
        fetchData();
    }, []);
    const formatPrice = (price) => {
        const num = Number(price);
        if (isNaN(num)) return '0 ₫'; // hoặc "Liên hệ"
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(num);
    };
    if (loading) {
        return (
            <div className="hero-movie">
                <div className="loading-container">
                    <div className="loading-spinner"></div>
                    <p className="loading-text">Đang tải phim...</p>
                </div>
            </div>
        );
    }
    return (
        <>
            <HeaderComponent/>
            <div className="hero-movie">
                <div className="main-content">
                    <h1 className="page-title">CinemaMax</h1>
                    <div className="movies-grid">
                        {movie && movie.map((m, i) => (
                            <div key={m.id} className="movie-card" style={{cursor:'pointer'}}>
                                <img
                                    src={m.posterUrl}
                                    alt={m.title}
                                    className="movie-poster"
                                />
                                <h3 className="movie-title">{m.title}</h3>
                                <p className="movie-genre">{m.genre}</p>
                                <div className="movie-info">
                                    <div className="info-item">
                                        <Clock size={16}/>
                                        <span>{m.duration}</span>
                                    </div>
                                </div>
                                <p className="movie-description">{m.description}</p>
                                <div className="movie-footer">
                                    <div className="movie-price">
                                        {formatPrice(m.price)}
                                    </div>
                                    <button
                                        className="book-button"
                                        onClick={() => alert(`Đặt vé cho phim: ${m.title}`)}
                                    >
                                        Đặt vé ngay
                                    </button>
                                </div>
                            </div>

                        ))}
                    </div>
                </div>
            </div>
        </>
    )
}
export default GetAllMovieUserComponent;