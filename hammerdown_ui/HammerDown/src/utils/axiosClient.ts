import axios from 'axios';

const axiosClient = axios.create({
  baseURL:'http://localhost:8080/api/',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});



axiosClient.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      console.error('Unauthorized access - logging out');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default axiosClient;