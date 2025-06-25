import axios from "axios";
const apiUrl = process.env.REACT_APP_API_URL;

export async  function getAllRoom(){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/user/room`,authHeader);
    return response.data;
}

export async  function detailRoom(id){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/user/room/${id}`,authHeader);
    return response.data;
}
