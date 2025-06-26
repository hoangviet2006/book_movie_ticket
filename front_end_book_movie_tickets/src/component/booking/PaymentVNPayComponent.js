import React, {useEffect,useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import {getEmailByToken, getUserNameByToken} from "../../service/userService/UserService";
import axios from "axios";
import {toast} from "react-toastify";
const apiUrl = process.env.REACT_APP_API_URL;

const PaymentVNPayComponent = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const {ticketData} = location.state || {};
    const [txnRef, setTxnRef] = useState(null);
    useEffect(() => {
        if (!ticketData) {
            navigate("/");
            return;
        }
        const sendPaymentRequest = async () => {
            try {
                const amount = ticketData.price * ticketData.listSeatCode.length;
                const params = new URLSearchParams({
                    amount: amount,
                    vnp_OrderInfo: `Thanh toán vé xem phim ${ticketData.titleMovie}`,
                    ordertype: "other",
                    language: "vn",
                    txt_billing_fullname: await getUserNameByToken(),
                    txt_billing_email: await getEmailByToken(),
                    ticketIds: ticketData.ticketIds.join(",")
                });
                const response = await axios.post(`${apiUrl}/api/payment/vnpay`, params.toString(), {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        Authorization: `Bearer ${localStorage.getItem("token")}`
                    }
                });
                const result = response.data
                if (result.code === "00") {
                    const paymentUrl = result.data;
                    const urlObj = new URL(paymentUrl);
                    const txnRefParam = urlObj.searchParams.get("vnp_TxnRef");
                    setTxnRef(txnRefParam);
                    window.location.href = result.data; //  redirect sang VNPay
                } else {
                    toast.warning("không tạo được link thanh toán")
                }
            } catch (error) {
                console.error("Lỗi thanh toán:", error);
                toast.error("Lỗi khi kết nối đến hệ thống thanh toán");
            }
        }
        sendPaymentRequest();
    }, [ticketData,navigate]);
    return (
        <div style={{textAlign: "center", marginTop: "50px"}}>
            <h2>Đang chuyển hướng đến VNPay...</h2>

        </div>
    );
}

export default PaymentVNPayComponent;