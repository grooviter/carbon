import axios, { AxiosRequestConfig, AxiosInstance } from 'axios';
import { scripts } from './scripts';

/**
 * Axios configuration
 *
 * @since 0.2.0
 */
const config: AxiosRequestConfig = {
    responseType: 'json',
    baseURL: 'http://localhost:8080/graphql',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
    },
};

/**
 * The configured axios instance used in all services
 *
 * @since 0.2.0
 */
export const http: AxiosInstance = axios.create(config);

/**
 * Aggregated services export.
 *
 * @since 0.2.0
 */
export default {
    scripts: scripts(http),
};
