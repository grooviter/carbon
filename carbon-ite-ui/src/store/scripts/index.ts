import { Module } from 'vuex';
import { RootState } from '@/store/common/types';
import { ScriptState } from '@/store/scripts/types';
import actions from '@/store/scripts/actions';
import getters from '@/store/scripts/getters';

/**
 * Is this module namespaced ?
 *
 * @since 0.1.0
 */
const namespaced: boolean = true;

/**
 * Scripts state
 *
 * @since 0.2.0
 */
export const state: ScriptState = {
    list: [],
};

/**
 * Scripts module
 *
 * @since 0.2.0
 */
const scripts: Module<ScriptState, RootState> = {
    namespaced,
    state,
    getters,
    actions,
};

export default scripts;
