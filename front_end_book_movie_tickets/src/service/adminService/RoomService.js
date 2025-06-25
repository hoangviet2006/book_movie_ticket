
import axios from "axios";
const apiUrl = process.env.REACT_APP_API_URL;

export async  function getAllRoom(page,name,startSeatCount,endSeatCount){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/admin/room?page=${page}&name=${name}&startSeatCount=${startSeatCount}&endSeatCount=${endSeatCount}`,authHeader);
    const  data = response.data.content;
    const totalPage = response.data.totalPages;
    return {data,totalPage}
}

export async  function detailRoom(id){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/admin/room/${id}`,authHeader);
    return response.data;
}

export async  function createRoom(newRoom){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.post(`${apiUrl}/api/admin/room`,newRoom,authHeader);
    return response.data;
}

export async  function updateRoom(id,newRoom){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.put(`${apiUrl}/api/admin/room/${id}`,newRoom,authHeader);
    return response.data;
}

export async  function deleteRoom(id){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.delete(`${apiUrl}/api/admin/room/${id}`,authHeader);
    return response.data;
}