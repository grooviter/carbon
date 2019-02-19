import Service from './Service';
import Script from '../domains/Script'
import { AxiosResponse } from 'axios'
import { http } from '../services/Axios'

interface Payload {
    runningScripts:Array<Script>
}

interface Response {
    data:Payload
}

/**
 * Retrieves data regarding scripts
 * 
 * @since 0.2.0
 */  
export default class ScriptService extends Service {

    /** 
     * Lists all available scripts
     * 
     * @return all current running scripts
     * @since 0.2.0
     */
    list():Promise<Array<Script>> {
        const query = `{
            runningScripts {
                uuid
                name
                status
            }
        }`

        return new Promise((resolve, reject) =>{
            http.post<Response>('', { query })
                .then((response:AxiosResponse<Response>) => {           
                    resolve(response.data.data.runningScripts as Array<Script>);
                })    
        });
    }
}