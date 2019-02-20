import { ActionTree } from 'vuex';
import axios from 'axios';
import { RootState } from '@/store/common/types';
import { Script, ScriptState } from '@/store/scripts/types';

/**
 * All actions related to scripts are defined here
 *
 * @since 0.2.0
 */
const actions: ActionTree<ScriptState, RootState> = {
    /**
     * Lists all scripts
     *
     * @since 0.1.0
     */
    list({ commit }): any {
        axios.post('http://localhost:8080/graphql', {}).then((response: any) => {
            const payload: Script = response && response.data;
            commit('scriptsList', payload);
        }, (error: any) => {
            commit('scriptsError');
        });
    },
};

export default actions;
