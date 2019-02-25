import { AxiosInstance, AxiosPromise } from 'axios';
import { Script } from '@/store/scripts/types';

/**
 * All related GraphQL calls regarding {@see Script}
 *
 * @since 0.2.0
 */
export const scripts = (client: AxiosInstance) => ({
    list(): AxiosPromise<Script> {
        const query = `{
            runningScripts {
                uuid
                name
                status
            }
        }`;

        return client.post('', { query });
    },
});
