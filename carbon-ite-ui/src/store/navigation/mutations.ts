import { MutationTree } from 'vuex';
import { NavigationState } from '@/store/navigation/types';

/**
 * Mutations are sync actions over properties
 *
 * @since 0.2.0
 */
const mutations: MutationTree<NavigationState> = {
    /**
     * Toggles the navigation menu (aside)
     *
     * @since 0.2.0
     */
    toggle(state) {
        return state.visible = !state.visible;
    },
};

export default mutations;
