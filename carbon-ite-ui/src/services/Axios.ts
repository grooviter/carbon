import axios, {
    AxiosRequestConfig
} from 'axios'


const config: AxiosRequestConfig = {
    responseType: 'json',
    baseURL: 'http://localhost:8080/graphql',
    headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }
}

export const http = axios.create(config);
