
import { AxiosInstance } from 'axios'

/**
 * Retrieves data regarding scripts
 * 
 * @since 0.2.0
 */  
export default class Service {

    /**
     * Axios instance
     * 
     * @since 0.2.0
     */
    http:AxiosInstance;

    /**
     * Constructor receiving an instance of axios
     * 
     * @since 0.2.0
     */
    constructor(http:AxiosInstance) {
        this.http = http;
    }
}