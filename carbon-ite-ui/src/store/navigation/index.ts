import { Module } from 'vuex';
import { RootState } from '@/store/common/types';
import { NavigationState } from '@/store/navigation/types';
import getters from '@/store/navigation/getters';
import mutations from '@/store/navigation/mutations';

/**
 * Is this module namespaced ?
 *
 * @since 0.1.0
 */
const namespaced: boolean = true;

/**
 * Navigation state
 *
 * @since 0.2.0
 */
const state: NavigationState = {
    visible: true,
};

/**
 * Navigation module
 *
 * @since 0.2.0
 */
const navigation: Module<NavigationState, RootState> = {
    namespaced,
    state,
    getters,
    mutations,
};

export default navigation;
