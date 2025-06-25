import axios from "axios";

const apiUrl = process.env.REACT_APP_API_URL;

export async function register(user) {
    const response = await axios.post(`${apiUrl}/api/auth/register`, user);
    if (response.status===200){
        localStorage.setItem("email",response.data.email)
        return response.data.message;
    }
    throw new Error("Đăng ký thất bại!")
}

export async function login(user) {
    const response = await axios.post(`${apiUrl}/api/auth/login`, user);
    if (response.data) {
        const token = response.data.jwtDto.token;
        const email = response.data.email;
        localStorage.setItem("token", token);
        localStorage.setItem("email", email);
    }
    return response.data.token
}
export async function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem('email')
    localStorage.removeItem('username')
}

export async function profile() {
    const token = localStorage.getItem("token");
        const authHeader = token
            ? {headers: {Authorization: `Bearer ${token}`}}
            : {};
        const response = await axios.get(`${apiUrl}/api/user/profile`,authHeader);
        return response.data;

}
export async function forgotPassword(email) {
    try {
        const response = await axios.get(`${apiUrl}/api/user/forgot-password?email=${email}`);
        return response.data;
    }catch (e) {
        throw e;
    }

}
export async function verifyForgotPassword(otpCode) {
    const response = await axios.post(`${apiUrl}/api/user/forgot-password-otp`,otpCode);
}
export async function confirmPasswordForgot(otpCode) {
    const response = await axios.post(`${apiUrl}/api/user/update-password`,otpCode);
}
export async function resetPassword(newPasswordUser) {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.post(`${apiUrl}/api/user/reset-password`,newPasswordUser,authHeader);
    return response.data;
}

export async function getUserNameByToken() {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/auth/get-username`,authHeader);
    return response.data;
}
export async function getEmailByToken() {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/auth/get-email`,authHeader);
    return response.data;
}