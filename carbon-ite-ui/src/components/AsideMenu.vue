<template>
    <aside>
        <h1>
            <font-awesome-icon icon="burn"></font-awesome-icon>Carbonite Lite
        </h1>
        <section class="main">
            <h2>Dashboard</h2>
            <ul>
                <li>
                    <font-awesome-icon icon="list-ul"></font-awesome-icon>
                    <router-link to="/">Dashboard</router-link>
                </li>
            </ul>
        </section>            
        <section class="main">
            <h2>Scripts</h2>
            <ul>
                <li>
                    <font-awesome-icon icon="list-ul"></font-awesome-icon>
                    <router-link to="/list">List scripts</router-link>
                </li>
            </ul>
        </section>
        <section class="main">
            <h2>Users</h2>
            <ul>
                <li><font-awesome-icon icon="user"></font-awesome-icon><a href="#">List Users</a></li>
                <li><font-awesome-icon icon="user-plus"></font-awesome-icon><a href="#">Add User</a></li>
            </ul>
        </section>
    </aside>
</template>
<script lang="ts">
import Vue from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
    faUser,
    faUserPlus,
    faListUl,
    faBurn,
} from '@fortawesome/free-solid-svg-icons';

library.add(faUser, faUserPlus, faBurn, faListUl);
export default Vue.extend({
    components: {
        FontAwesomeIcon,
    },
});
</script>

