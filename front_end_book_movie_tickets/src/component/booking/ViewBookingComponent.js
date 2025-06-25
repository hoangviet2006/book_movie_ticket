import React from "react";
import "../../css/user/viewBooking.css"
import HeaderComponent from "../homePage/HeaderComponent";
import FooterComponent from "../homePage/FooterComponent";
import {useLocation, useNavigate} from "react-router-dom";

const ViewBookingComponent = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const {ticketData} = location.state || {};
    console.log(ticketData)

    if (!ticketData) {
        navigate('/');
        return null;
    }

    const handlePayment = () => {
        navigate("/payment-vnpay", { state: { ticketData } });
    };

    const handlePayLater = () => {
        navigate('/');
    };

    return (
        <>
            <div className="hero-movie">
                <HeaderComponent/>
                <div className="hero-content-wrapper">
                    <div className="ticket-container">
                        <h2>🎫 Vé xem phim của bạn</h2>
                        <div className="ticket">
                            <div className="ticket-left">
                                <img src={ticketData.urlMovie} alt="Poster phim"/>
                            </div>
                            <div className="ticket-right">
                                <p><strong>🎬 Phim:</strong> {ticketData.titleMovie}</p>
                                <p><strong>📅 Ngày chiếu:</strong> {ticketData.dateShow}</p>
                                <p><strong>🕐 Giờ chiếu:</strong> {ticketData.startTime} – {ticketData.endTime}</p>
                                <p><strong>📍 Rạp:</strong> {ticketData.nameCinema} – {ticketData.addressCinema}</p>
                                <p><strong>🏠 Phòng:</strong> {ticketData.nameRoom}</p>
                                <p><strong>💺 Ghế:</strong> {ticketData.listSeatCode.join(", ")}</p>
                                <p><strong>💰 Tổng
                                    tiền:</strong> {(ticketData.price * ticketData.listSeatCode.length).toLocaleString()} VND
                                </p>

                                <div className="ticket-actions">
                                    <button className="pay-btn" onClick={handlePayment}>Thanh toán</button>
                                    <button className="later-btn" onClick={handlePayLater}>Thanh toán sau</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
            <FooterComponent/>
        </>
    );
};

export default ViewBookingComponent;
