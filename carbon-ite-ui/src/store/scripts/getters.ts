import { GetterTree } from 'vuex';
import { RootState } from '@/store/common/types';
import { Script, ScriptState } from './types';

/**
 * All functions getting data from state
 *
 * @since 0.2.0
 */
const getters: GetterTree<ScriptState, RootState> = {
    /**
     * Reads the current list of script executions
     *
     * @param state the current state
     * @return an array of scripts information
     * @since 0.2.0
     */
    getScriptList(state): Script[] {
        return [];
    },
};

export default getters;
