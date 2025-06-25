import axios from "axios";
const apiUrl = process.env.REACT_APP_API_URL;
export async function getAllMovie() {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/user/movie`,authHeader);
    return response.data;
}

export async function detailMovie(id) {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/user/movie/${id}`,authHeader);
    return response.data;
}
export async function detailShowByMovieId(id) {
    const token = localStorage.getItem("token");
    const authHeader = token
        ? {headers: {Authorization: `Bearer ${token}`}}
        : {};
    const response = await axios.get(`${apiUrl}/api/user/movie/${id}/detail`,authHeader);
    return response.data;
}