import axios from "axios";

const apiUrl = process.env.REACT_APP_API_URL;

export async function findCart() {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/cart`,authHeader);
    return response.data;
}