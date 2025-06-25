import axios from "axios";
const apiUrl = process.env.REACT_APP_API_URL;

export async function getAllCinema(page,name,address){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/admin/cinema?page=${page}&name=${name}&address=${address}`,authHeader)
    const  data = response.data.content;
    const totalPage = response.data.totalPages;
    return {data,totalPage}
}

export async function detailCinema(id){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/admin/cinema/${id}`,authHeader)
    return response.data;
}

export async function createCinema(newCinema){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.post(`${apiUrl}/api/admin/cinema`,newCinema,authHeader)
    return response.data;
}

export async function updateCinema(id,newCinema){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.put(`${apiUrl}/api/admin/cinema/${id}`,newCinema,authHeader)
    return response.data;
}

export async function deleteCinema(id){
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.delete(`${apiUrl}/api/admin/cinema/${id}`,authHeader)
    return response.data;
}