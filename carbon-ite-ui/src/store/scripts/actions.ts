import { ActionTree, Commit } from 'vuex';
import { RootState } from '@/store/common/types';
import { Script, ScriptState } from '@/store/scripts/types';
import api from '@/services/index';
import { AxiosResponse } from 'axios';
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
    list({ commit }): Promise<void> {
        return api.scripts.list()
            .then((response: AxiosResponse<Script>) => {
                const payload: Script = response && response.data;
                commit('scriptsList', payload);
            }, (error: any) => {
                commit('scriptsError');
            });
    },
};

export default actions;
