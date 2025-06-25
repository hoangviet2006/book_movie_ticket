import React, {useState,useEffect} from "react";
import axios from "axios";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import '../../css/home/otp.css'
import HeaderComponent from "../homePage/HeaderComponent";
const apiUrl = process.env.REACT_APP_API_URL;
const ConfirmOtp = () => {
    const [otp, setOtp] = useState("");
    const [loading, setLoading] = useState(false);
    const [showResend, setShowResend] = useState(true);
    const [countdown, setCountdown] = useState(60);
    const email = localStorage.getItem("email");
    const navigate=useNavigate();
    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!otp) {
            toast.error("Vui lòng nhập mã OTP");
            return;
        }

        setLoading(true);
        try {
            const otpCode = {
                email: email,
                code: otp
            }
            const response = await axios.post(`${apiUrl}/api/auth/verify-otp`, otpCode)
            toast("Xác thực OTP thành công!");
            navigate('/login')
            localStorage.removeItem("email");
        } catch (e) {
            toast.warning(
                e.response?.data || "Xác thực OTP thất bại, vui lòng thử lại"
            );
        } finally {
            setLoading(false);
        }
    }
    useEffect(() => {
        let timer;
        if (!showResend) {
            timer = setInterval(() => {
                setCountdown((prev) => {
                    if (prev <= 1) {
                        clearInterval(timer);
                        setShowResend(true);
                        return 60;
                    }
                    return prev - 1;
                });
            }, 1000);
        }
        return () => clearInterval(timer);
    }, [showResend]);

    const handleResendOtp= async ()=>{
        setShowResend(false)
        try {
            const response = await axios.post(`${apiUrl}/api/auth/verify-otp-confirm`,JSON.stringify(email), {
                headers: {
                    'Content-Type': 'application/json'
                }}
            )
            toast(response?.data?.message || "Đã gửi lại mã OTP");
            setCountdown(60);
        }catch (e) {
            toast.warning(e?.response?.data?.message || "Gửi mã OTP thất bại");
            setShowResend(true)
        }
    }
    return (
        <div className="otp-container">
        <HeaderComponent/>
            <form onSubmit={handleSubmit} className="otp-form">
                <h2>Xác thực OTP</h2>
                <div>
                    <label htmlFor="otp">Mã OTP:</label>
                    <input
                        type="text"
                        id="otp"
                        value={otp}
                        onChange={(e) => setOtp(e.target.value)}
                        maxLength={6}
                        placeholder="Nhập mã OTP"
                    />
                </div>

                <div style={{ display: 'flex', gap: '10px', marginTop: '15px' }}>
                    <button
                        type="submit"
                        disabled={loading}
                        style={{
                            flex: 7,
                            padding: "10px",
                            border: "none",
                            borderRadius: "4px",
                            backgroundColor: "#007bff",
                            color: "#fff",
                            cursor: loading ? "not-allowed" : "pointer"
                        }}
                    >
                        {loading ? "Đang xác thực..." : "Xác nhận"}
                    </button>

                    <button
                        type="button"
                        onClick={handleResendOtp}
                        disabled={!showResend} // Khi showResend = false thì disabled
                        style={{
                            flex: 3,
                            backgroundColor: "#444",
                            color: "#fff",
                            padding: "10px",
                            border: "none",
                            borderRadius: "4px",
                            cursor: showResend ? "pointer" : "not-allowed",
                            opacity: showResend ? 1 : 0.6
                        }}
                    >
                        {showResend ? "Gửi lại mã" : `Gửi lại ${countdown}s`}
                    </button>
                </div>
            </form>
        </div>
    );
}
export default ConfirmOtp;