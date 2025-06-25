import axios from "axios";
const apiUrl = process.env.REACT_APP_API_URL;

export async  function getAllShowOnDisplay(){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/user/show`,authHeader);
    return response.data;
}

export async  function detailShow(id){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/user/show/${id}`,authHeader);
    return response.data;
}


export async  function detailBookingShow(id){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/user/show/${id}/detail-booking`,authHeader);
    return response.data;
}