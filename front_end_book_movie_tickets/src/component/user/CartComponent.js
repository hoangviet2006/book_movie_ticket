import {useEffect, useState} from "react";
import {findCart} from "../../service/userService/CartService";
import '../../css/user/cart.css'
import HeaderComponent from "../homePage/HeaderComponent";
import FooterComponent from "../homePage/FooterComponent";
import {bookTicket} from "../../service/userService/BookingService";
import {useNavigate} from "react-router-dom";

const CartComponent = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    useEffect(() => {
        try {
            const fetchData = async () => {
                const data = await findCart();
                setData(data);
                setLoading(false);
            }
            fetchData();
        } catch (e) {
            console.log(e?.response?.data);
            setLoading(false);
        }
    }, []);

    if (loading) {
        return (
            <>
                <HeaderComponent/>
                <div className="cart-container">
                    <div className="loading-container">
                        <div className="loading-spinner"></div>
                    </div>
                </div>
                <FooterComponent/>
            </>
        );
    }

    const handlePayment = async (item) => {
        try {
            navigate('/booking-view', {
                state: {
                    ticketData: {
                        urlMovie: item?.posterUrl,
                        titleMovie: item?.titleMovie,
                        dateShow: item?.dateShow,
                        startTime: item?.startTime,
                        endTime: item?.endTime,
                        nameCinema: item?.nameCinema,
                        addressCinema: item?.addressCinema,
                        nameRoom: item?.nameRoom,
                        listSeatCode: item?.seatCode?.split(',') || [],
                        listSeatId: item?.seatId?.split(',').map(id => parseInt(id)),
                        ticketIds: item?.idTicket.split(',').map(id => parseInt(id)),
                        price: parseInt(item?.ticketPrice.split(',')[0])
                    }
                }
            });
        } catch (e) {
            console.log("Lỗi thanh toán: " + e?.response?.data)
        }

    }
    return (
        <>
            <HeaderComponent/>
            <div className="cart-container">
                <div className="cart-content">
                    <h1 className="cart-title">Vé của bạn</h1>
                    {data && data.length === 0 ? (
                        <div className="empty-cart-message">
                            <p>Bạn chưa có vé nào trong giỏ hàng.</p>
                        </div>
                    ) : (
                        <div className="cart-items">
                            {data && data.map((item) => (
                                <div key={item?.idBooking} className="cart-item">
                                    <div className="cart-item-poster">
                                        <img src={item?.posterUrl} alt="Poster"/>
                                    </div>
                                    <div className="cart-item-details">
                                        <h2 className="cart-item-title">{item?.titleMovie}</h2>
                                        <p className="cart-item-info">
                                            <strong>Thể loại:</strong> {item?.genreMovie}
                                        </p>
                                        <p className="cart-item-info">
                                            <strong>Thời lượng:</strong> {item?.durationMovie} phút
                                        </p>
                                        <p className="cart-item-info">
                                            <strong>Ngày chiếu:</strong> {item?.dateShow}
                                        </p>
                                        <p className="cart-item-info">
                                            <strong>Giờ chiếu:</strong> {item?.startTime} - {item?.endTime}
                                        </p>
                                        <p className="cart-item-info">
                                            <strong>Rạp:</strong> {item?.nameCinema} - {item?.nameRoom}
                                        </p>

                                        <p className="cart-item-info">
                                            <strong>Địa chỉ:</strong> {item?.addressCinema}
                                        </p>

                                        <p className="cart-item-info">
                                            <strong>Ghế:</strong> <span className="seat-code">{item?.seatCode}</span>
                                        </p>

                                        <p className="cart-item-info">
                                            <strong>Giá vé:</strong>
                                            <span
                                                className="price-highlight"> {item?.totalPrice.toLocaleString()} VNĐ</span>
                                        </p>
                                            <p className="cart-item-info">
                                                <strong>Trạng thái:</strong>
                                                {item?.statusTicket === 'PAID' && (
                                                    <span className="status-badge status-paid">Đã thanh toán</span>
                                                )}
                                                {item?.statusTicket === 'UNPAID' && (
                                                    <button className="pay-now-button"
                                                            onClick={() => handlePayment(item)}>
                                                        Thanh toán ngay
                                                    </button>
                                                )}
                                                {item?.statusTicket === 'CANCELLED' && (
                                                    <span className="status-badge status-cancelled">Ghế đã bị huỷ vì không thanh toán</span>
                                                )}
                                            </p>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </div>
            <FooterComponent/>
        </>
    );
};
export default CartComponent;