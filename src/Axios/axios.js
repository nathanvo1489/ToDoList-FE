import axios from "axios"
const instance = axios.create({
    baseURL:"http://54.179.76.123:8000/api"
    // baseURL: process.env.REACT_APP_API_URL
})
export default instance