<template>
    <div class="main">
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
        <div class="body">
            <nav class="admin">
                <div class="left">
                    <a class="collapse" href="#collapse">
                        <font-awesome-icon icon="compress-arrows-alt" size="lg"/>
                    </a>
                </div>
                <div class="right">
                    <a href="#notifications"><font-awesome-icon icon="bell" size="lg"/></a>
                    <a href="#notifications"><font-awesome-icon icon="comments" size="lg"/></a>
                    <a class="user" href="#notifications">
                        <img src="../../../assets/images/pro4.jpg" />
                        <span>Mario Garcia</span>
                        <font-awesome-icon icon="caret-down" size="lg" />
                    </a>
                </div>
            </nav>
            <div class="content">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { library } from '@fortawesome/fontawesome-svg-core'
import { Component, Vue } from 'vue-property-decorator'
import {
    faUser,
    faUserPlus,
    faBell,
    faComments,
    faCompressArrowsAlt,
    faCaretDown,
    faListUl,
    faBurn
} from '@fortawesome/free-solid-svg-icons'

library.add(faUser, faUserPlus, faBurn, faListUl, faBell, faComments, faCompressArrowsAlt, faCaretDown)

@Component
export default class App extends Vue {

}
</script>

<style lang="scss">
    @import 'App.scss';
</style>
