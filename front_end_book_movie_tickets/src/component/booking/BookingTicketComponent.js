import {useEffect, useState} from "react";
import {detailBookingShow} from "../../service/userService/ShowService";
import {useNavigate, useParams} from "react-router-dom";
import "../../css/user/bookingPage.css"
import HeaderComponent from "../homePage/HeaderComponent";
import FooterComponent from "../homePage/FooterComponent";

const BookingTicketComponent = () => {
    const {id} = useParams();
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectSeatId, setSelectSeatId] = useState([]);
    const [selectSeatCode, setSelectSeatCode] = useState([]);
    const [confirmIdSeat, setConfirmIdSeat] = useState([]);
    const navigate = useNavigate();
    useEffect(() => {
        try {
            const fetchData = async () => {
                const response = await detailBookingShow(id);
                setData(response)
            }
            fetchData();
        } catch (e) {
            console.log("Lỗi: " + e)
        } finally {
            setLoading(false)
        }

    }, [confirmIdSeat]);
    const toggleSelectSeat = (seatId, seatCode) => {
        setSelectSeatId((pre) =>
            pre.includes(seatId) ? pre.filter((id) => id !== seatId) : [...pre, seatId])
        setSelectSeatCode((pre) =>
            pre.includes(seatCode) ? pre.filter((code) => code !== seatCode) : [...pre, seatCode])
    }
    const handleBooking = async (arrayId, arrayCode) => {
        setConfirmIdSeat(arrayId)
        localStorage.setItem("ListSeat", JSON.stringify(arrayId));
        localStorage.setItem("ListCode", JSON.stringify(arrayCode));
        localStorage.setItem('idShow', data?.idShow)
        setSelectSeatId([])
        navigate('/confirm-booking')
    }
    if (loading) return <div style={{color: "white", padding: "2rem"}}>Đang tải dữ liệu...</div>;
    if (!data) return <div style={{color: "white", padding: "2rem"}}>Không tìm thấy suất chiếu</div>;
    return (
        <>
            <HeaderComponent/>
            <div className="hero-movie booking-page">
                <div className="overlay-content">
                    <h1 className={'title-booking'}>
                        Mỗi ghế ngồi là một hành trình – Bạn muốn bắt đầu từ đâu?
                    </h1>
                    <div className="movie-info">
                        <img src={data && data?.movieInfoDto?.urlMovie} alt={data && data?.movieInfoDto?.titleMovie}
                             className="poster"/>
                        <div className="details">
                            <h2>{data && data?.movieInfoDto?.titleMovie}</h2>
                            <p><strong>Thời gian chiếu:</strong>
                                {data && data?.dateShow} | {data && data?.startTime}-{data && data?.endTime}
                            </p>
                            <p><strong>Thể loại:</strong> {data && data?.movieInfoDto?.genreMovie}</p>
                            <p><strong>Thời lượng:</strong> {data && data?.movieInfoDto?.duration} phút</p>
                            <p><strong>Giá vé:</strong> {data && data?.movieInfoDto?.priceMovie?.toLocaleString()} VND
                            </p>
                            <p><strong>Mô tả:</strong> {data && data?.movieInfoDto?.description}</p>
                        </div>
                    </div>
                    <div className="room-info">
                        <h3>{data && data?.roomInfoDto?.nameRoom}: {data && data?.roomInfoDto?.seatCount} ghế</h3>
                        <div className="seat-map">
                            {data && data?.seatStatusDto?.map((seat) => {
                                const isSelected = selectSeatId.includes(seat.seatId);
                                let seatClass = "seat";
                                if (seat.seatStatus === "PAID") {
                                    seatClass += " paid";
                                } else if (seat.seatStatus === "UNPAID") {
                                    seatClass += " unpaid";
                                } else if (isSelected) {
                                    seatClass += " selected";
                                } else {
                                    seatClass += " empty";
                                }
                                return (
                                    <div key={seat.seatId}
                                         className={seatClass}
                                         onClick={() => {
                                             if (!seat.seatStatus) {
                                                 toggleSelectSeat(seat?.seatId, seat?.seatCode);
                                             }
                                         }}
                                    >
                                        {seat?.seatCode}
                                    </div>
                                );
                            })}
                        </div>
                        <div className="seat-legend">
                            <div className="legend-item">
                                <div className="seat selected"></div>
                                <span>Đã chọn ghế</span>
                            </div>
                            <div className="legend-item">
                                <div className="seat unpaid"></div>
                                <span>Đã đặt nhưng chưa thanh toán</span>
                            </div>
                            <div className="legend-item">
                                <div className="seat paid"></div>
                                <span>Đã thanh toán</span>
                            </div>
                            <div className="legend-item">
                                <div className="seat empty"></div>
                                <span>Chưa có người đặt</span>
                            </div>
                        </div>
                    </div>
                    <div style={{textAlign: "center"}}>
                        <button onClick={() => handleBooking(selectSeatId, selectSeatCode)}
                                disabled={selectSeatId.length === 0}
                                style={{
                                    opacity: selectSeatId.length === 0 ? 0.5 : 1,
                                    cursor: selectSeatId.length === 0 ? "not-allowed" : "pointer"
                                }} type={'button'}>Đặt vé
                        </button>
                    </div>
                </div>
            </div>
            <FooterComponent/>
        </>

    );
}
export default BookingTicketComponent;