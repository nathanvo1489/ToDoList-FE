import axios from "axios"
const instance = axios.create({
    baseURL:"https://js-express-be.nathan-vo.com/api"
    // baseURL:"http://localhost:8000/api"
    // baseURL: process.env.REACT_APP_API_URL
})
export default instance