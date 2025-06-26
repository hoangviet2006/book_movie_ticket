import React, {useEffect, useState} from "react";
import {detailBookingShow} from "../../service/userService/ShowService";
import "../../css/user/confirmBooking.css"
import HeaderComponent from "../homePage/HeaderComponent";
import FooterComponent from "../homePage/FooterComponent";
import {bookTicket} from "../../service/userService/BookingService";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

const ConfirmBookingTicket = () => {
    const [idShow, setIdShow] = useState('');
    const [listSeat, setListSeat] = useState([]);
    const [listSeatCode, setListSeatCode] = useState([]);
    const [data, setData] = useState('');
    const [isShowModal, setIsShowModal] = useState(false);
    const navigate = useNavigate();
    useEffect(() => {
        try {
            const fetchData = async () => {
                const idShow = localStorage.getItem("idShow");
                const listSeat = JSON.parse(localStorage.getItem("ListSeat") || "[]");
                const listSeatCode = JSON.parse(localStorage.getItem("ListCode") || "[]");
                setListSeatCode(listSeatCode)
                setIdShow(idShow);
                setListSeat(listSeat);
                const response = await detailBookingShow(idShow);
                setData(response)
            }
            fetchData();
        } catch (e) {
            console.log("Lỗi: " + e)
        }
    }, []);
    const handleBooking = async () => {
        try {
            const  ticketIds = await bookTicket(idShow, listSeat);
            navigate('/booking-view',{
                state:{
                    ticketData:{
                        urlMovie: data?.movieInfoDto?.urlMovie,
                        titleMovie: data?.movieInfoDto?.titleMovie,
                        dateShow: data?.dateShow,
                        startTime: data?.startTime,
                        endTime: data?.endTime,
                        nameCinema: data?.cinemaInfoDto?.nameCinema,
                        addressCinema:data?.cinemaInfoDto?.addressCinema,
                        nameRoom: data?.roomInfoDto?.nameRoom,
                        listSeatCode: listSeatCode,
                        listSeatId:listSeat,
                        ticketIds:ticketIds,
                        price: data?.movieInfoDto?.priceMovie
                    }
                }
            });
            localStorage.removeItem("idShow");
            localStorage.removeItem("ListSeat");
            localStorage.removeItem("ListCode");
            setIsShowModal(false)
        } catch (e) {
            navigate(-1);
            toast.warning(e?.response?.data)
        }
    }
    const closeModal=()=>{
        setIsShowModal(((pre)=>!pre))
    }
    return (
        <>
            <div className="hero-movie">
                <HeaderComponent/>
                <div className={'overlay-content confirm-booking'}>
                    <img src={data && data?.movieInfoDto?.urlMovie} alt={data && data?.movieInfoDto?.titleMovie}
                    />
                    <div>
                        <h2>{data && data?.movieInfoDto?.titleMovie}</h2>
                        <p><strong>Thời gian chiếu: </strong>
                            {data && data?.dateShow} | {data && data?.startTime}-{data && data?.endTime}
                        </p>
                        <p><strong>Thể loại:</strong> {data && data?.movieInfoDto?.genreMovie}</p>
                        <p><strong>Thời lượng:</strong> {data && data?.movieInfoDto?.duration} phút</p>
                        <p><strong>Giá 1 vé:</strong> {data && data?.movieInfoDto?.priceMovie?.toLocaleString()} VND</p>
                        <p><strong>Mô tả:</strong> {data && data?.movieInfoDto?.description}</p>
                    </div>
                    <div className="selected-seats">
                        <h3>Ghế đã chọn</h3>
                        <div className="seat-list">
                            {listSeatCode && listSeatCode.map((s, i) => (
                                <p key={i}>{s}</p>
                            ))}
                        </div>
                        <h3 style={{marginTop: "20px", color: "#ff9472"}}>
                            Tổng tiền: {data?.movieInfoDto?.priceMovie && listSeatCode.length > 0
                            ? (data.movieInfoDto.priceMovie * listSeatCode.length).toLocaleString()
                            : 0} VND
                        </h3>
                        <button className={'submit-button'} onClick={()=>setIsShowModal((pre)=>!pre)}>Xác nhân đặt vé</button>
                    </div>
                </div>
            </div>
            {isShowModal && (
                <div className="modal-overlay">
                    <div className="custom-modal">
                        <h4>Xác nhận đặt vé</h4>
                        <p>
                            bạn chắn chắn đặt vé với ghế là "{listSeatCode.toLocaleString()}" này chứ
                        </p>
                        <div className="modal-buttons">
                            <button className="cancel-btn" onClick={closeModal}>
                                Huỷ
                            </button>
                            <button className="delete-btn" onClick={handleBooking}>
                                Xác nhận
                            </button>
                        </div>
                    </div>
                </div>
            )}
            <FooterComponent/>
        </>

    );
}
export default ConfirmBookingTicket;