import axios from "axios";



// name must start with VITE_
const {VITE_SERVER_URL } = import.meta.env;


const api = axios.create({
    baseURL: VITE_SERVER_URL ,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  
  // 요청 인터셉터 설정 (JWT 토큰 자동 추가)
  api.interceptors.request.use((config) => {
    const token = localStorage.getItem("access_token"); // 🔹 요청 시마다 토큰 가져오기
    if (token) {
      config.headers.Authorization = `${token}`;
      console.log("완료")
    }
    return config;
  });
  
  export default api;
  