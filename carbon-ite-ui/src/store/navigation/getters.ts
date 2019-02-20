import { GetterTree } from 'vuex';
import { RootState } from '@/store/common/types';
import { NavigationState } from '@/store/navigation/types';

/**
 * Functions to get data from the state
 *
 * @since 0.2.0
 */
const getters: GetterTree<NavigationState, RootState> = {
    getVisible(state): boolean {
        return state.visible;
    },
};

export default getters;
