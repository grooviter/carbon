import Vue from 'vue';
import Vuex, { StoreOptions } from 'vuex';
import { RootState } from '@/store/common/types';
import navigation from '@/store/navigation';
import scripts from '@/store/scripts';

Vue.use(Vuex);

/**
 * Here we should aggregate the rest of the store related
 * modules
 *
 * @since 0.2.0
 */
const store: StoreOptions<RootState> = {
    state: {
        version: '0.2.0',
    },
    modules: {
        navigation,
        scripts,
    },
};

export default new Vuex.Store<RootState>(store);
