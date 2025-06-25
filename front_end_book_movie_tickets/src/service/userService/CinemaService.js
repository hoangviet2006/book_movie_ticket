import axios from "axios";
const apiUrl = process.env.REACT_APP_API_URL;

export async function getAllCinema(){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/user/cinema`,authHeader)
    return response.data;
}

export async function detailCinema(id){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/user/cinema/${id}`,authHeader)
    return response.data;
}
