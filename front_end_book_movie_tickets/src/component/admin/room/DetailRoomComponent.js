import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {detailRoom} from "../../../service/adminService/RoomService";
import '../../../css/admin/room/detailRoom.css'
const DetailRoomComponent =()=>{
    const {id} = useParams();
    const [roomDetail, setRoomDetail] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await detailRoom(id);
                console.log(response)
                setRoomDetail(response);
            } catch (error) {
                console.error("Lỗi khi lấy chi tiết phòng:", error);
            }
        };
        fetchData();
    }, [id]);

    return(
        <>
            <div className="detail-room-container" >
                <h2>Chi tiết phòng chiếu</h2>
                <p><strong>Tên phòng:</strong> {roomDetail && roomDetail?.room?.name}</p>
                <p><strong>Số ghế:</strong> {roomDetail && roomDetail?.room?.seatCount}</p>
                <p><strong>Rạp:</strong> {roomDetail && roomDetail?.room?.cinema?.name}</p>
                <p><strong>Địa chỉ rạp:</strong> {roomDetail && roomDetail?.room?.cinema?.address}</p>
            {/*</div>*/}
            {/*<div className="full-seat-list">*/}
                <h4>Danh sách ghế</h4>
                <div className="seat-list">
                    {roomDetail && roomDetail.seats?.map(seat => (
                        <button key={seat.id}>
                            {seat?.seatCode}
                        </button>
                    ))}
                </div>
            </div>
        </>
    );
}
export default DetailRoomComponent;