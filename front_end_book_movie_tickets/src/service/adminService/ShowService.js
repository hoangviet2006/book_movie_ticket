
import axios from "axios";
const apiUrl = process.env.REACT_APP_API_URL;

export async  function getAllShow(page,date,time){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/admin/show?page=${page}&date=${date}&time=${time}`,authHeader);
    const  data = response.data.content;
    const totalPage = response.data.totalPages;
    return {data,totalPage}
}

export async  function detailShow(id){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/admin/show/${id}`,authHeader);
    return response.data;
}

export async  function createShow(newShow){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.post(`${apiUrl}/api/admin/show`,newShow,authHeader);
    return response.data;
}

export async  function updateShow(id,newShow){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.put(`${apiUrl}/api/admin/show/${id}`,newShow,authHeader);
    return response.data;
}

export async  function deleteShow(id){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.delete(`${apiUrl}/api/admin/show/${id}`,authHeader);
    return response.data;
}