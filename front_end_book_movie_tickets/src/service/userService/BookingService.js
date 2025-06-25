import axios from "axios";
const apiUrl = process.env.REACT_APP_API_URL;

export async function bookTicket(idShow,listSeat) {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};

    const body = {
        idShow: Number(idShow),
        idSeats: listSeat
    }
    const response = await axios.post(`${apiUrl}/api/user/booking`,body,authHeader);
    return response.data;
}