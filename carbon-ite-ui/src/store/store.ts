import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export const store = new Vuex.Store({
    state: {
        navigation: {
            visible: true,
        },
    },
    mutations: {
        toggle(state) {
            state.navigation.visible = !state.navigation.visible;
        },
    },
    getters: {
        visible: (state) => state.navigation.visible,
    },
});
