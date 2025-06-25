import {useEffect, useState} from "react";
import {detailShowByMovieId} from "../../service/userService/MovieService";
import {Link, useParams} from "react-router-dom";
import {Clock, MapPin, Calendar, Star} from "lucide-react";
import "../../css/admin/bookticket.css"
import HeaderComponent from "../homePage/HeaderComponent";
import FooterComponent from "../homePage/FooterComponent";
const DetailShowByMovieId = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const {id} = useParams();
    useEffect(() => {
        try {
            const fetchData = async () => {
                const movie = await detailShowByMovieId(id)
                setTimeout(() => {
                    setData(movie)
                    setLoading(false);
                }, 2000);
            }
            fetchData()
        } catch (e) {
            console.error("Lỗi", e);
            setLoading(false);
        }

    }, []);
    if (loading) {
        return (
            <div className="movie-detail-loading">
                <div className="loading-movie">
                    <div className="loading-spin"></div>
                    <p className="loading">Đang tải phim...</p>
                </div>
            </div>
        );
    }
    return (
        <>
            <div className="movie-detail-loading">
                <HeaderComponent/>
                <div className="detail-card">
                    <h1 className="title" style={{marginBottom:50}}>Một Chạm Là Có Vé Ngại Gì Mà Không Xem?</h1>
                    <div className="book-grid">
                        {data && data.map((show) => (
                            <div key={show.id} className="movies-card" style={{cursor: 'pointer'}}>
                                <Link to={`/booking/${show.id}`} style={{textDecoration:'none'}}>
                                <img
                                    src={show.poster}
                                    alt={show.titleMovie}
                                    className="poster"
                                />
                                <h3 className="title">{show.titleMovie}</h3>
                                <p className="genre">{show.genre}</p>

                                <div className="info">
                                    <div className="inf-item">
                                        <Clock size={16}/>
                                        <span>{show.duration} phút</span>
                                    </div>
                                    <div className="inf-item">
                                        <span>Phòng: {show.nameRoom}</span>
                                    </div>
                                    <div className="inf-item">
                                        <span>Rạp: {show.nameCinema}</span>
                                    </div>
                                    <div className="inf-item">
                                        <span>Địa chỉ: {show.address}</span>
                                    </div>
                                    <div className="inf-item">
                                        <span>Ngày: {new Date(show.dateShow).toLocaleDateString('vi-VN')}</span>
                                    </div>
                                    <div className="inf-item">
                                        <span>Giờ chiếu: {show.startTime}-{show.endTime}</span>
                                    </div>
                                </div>

                                <p className="description">Mô tả: {show.description}</p>

                                <div className="footer-book-grid">
                                    <div className="price">
                                        {new Intl.NumberFormat('vi-VN', {
                                            style: 'currency',
                                            currency: 'VND'
                                        }).format(show.price)}
                                    </div>
                                    <Link to={`/booking/${show.id}`} className="book-button">
                                        Đặt vé
                                    </Link>
                                </div>
                                </Link>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
            <FooterComponent/>
        </>

    )
}
export default DetailShowByMovieId;