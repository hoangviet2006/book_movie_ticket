import axios from "axios";
const apiUrl = process.env.REACT_APP_API_URL;


export async function getAllMovie(page,title,genre) {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/admin/movie?page=${page}&title=${title}&genre=${genre}`,authHeader);
    const  data = response.data.content;
    const totalPage = response.data.totalPages;
    return {data,totalPage}
}

export async function detailMovie(id) {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/admin/movie/${id}`,authHeader);
    return response.data;
}

export async function createMovie(newMovie) {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.post(`${apiUrl}/api/admin/movie`,newMovie,authHeader);
    return response.data;
}

export async function updateMovie(id,newMovie) {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.put(`${apiUrl}/api/admin/movie/${id}`,newMovie,authHeader);
    return response.data;
}

export async function deleteMovie(id) {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.delete(`${apiUrl}/api/admin/movie/${id}`,authHeader);
    return response.data;
}