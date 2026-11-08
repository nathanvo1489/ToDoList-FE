import axios from "axios"
const instance = axios.create({
    baseURL: "https://js-express-be.nathan-vo.com/api"
})
export default instance