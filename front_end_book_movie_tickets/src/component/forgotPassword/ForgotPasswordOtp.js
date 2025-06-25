import HeaderComponent from "../homePage/HeaderComponent";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {toast} from "react-toastify";
import '../../css/home/otp.css'
import {verifyForgotPassword} from "../../service/userService/UserService";
import {useNavigate} from "react-router-dom";
const apiUrl = process.env.REACT_APP_API_URL;
const ForgotPasswordOtp = ()=>{
    const [otp, setOtp] = useState("");
    const [loading, setLoading] = useState(false);
    const [showResend, setShowResend] = useState(true);
    const [countdown, setCountdown] = useState(60);
    const email = localStorage.getItem("email");
    const navigate = useNavigate();
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

    const handleSubmit= async (e)=>{
        e.preventDefault();
        try {
            setLoading(true)
            const otpData = {
                email: localStorage.getItem("email"),
                code: otp
            };
            await verifyForgotPassword(otpData);
            localStorage.setItem('OTP', otp);
            navigate('/update-password')
        }catch (e) {
            toast.warning(e?.response?.data)
        }finally {
            setLoading(false);
        }

    }
    return(
        <>
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

                    <div style={{display: 'flex', gap: '10px', marginTop: '15px'}}>
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
                            disabled={!showResend}
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
        </>
    );
}
export default ForgotPasswordOtp;