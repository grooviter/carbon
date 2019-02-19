<template>
    <aside v-bind:class="{hidden: !visible}">
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
                    <router-link to="/scripts">List scripts</router-link>
                </li>
            </ul>
        </section>
        <section class="main">
            <h2>Users</h2>
            <ul>
                <li>
                    <font-awesome-icon icon="user"></font-awesome-icon>
                    <router-link to="/users">List Users {{ visible }}</router-link>
                </li>
                <li>
                    <font-awesome-icon icon="user-plus"></font-awesome-icon>
                    <router-link to="/users/add">Add User</router-link>
                </li>
            </ul>
        </section>
    </aside>
</template>
<script lang="ts">
import { mapState } from 'vuex';
import { Component, Vue } from 'vue-property-decorator';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
    faUser,
    faUserPlus,
    faListUl,
    faBurn,
} from '@fortawesome/free-solid-svg-icons';

library.add(faUser, faUserPlus, faBurn, faListUl);

@Component({
    components: {
        FontAwesomeIcon,
    },
})
export default class AsideMenu extends Vue {
    get visible() {
        return this.$store.getters.visible;
    }
}
</script>
<style lang="postcss" scoped>

aside {
  border-right: 1px solid #ccc;
  height: 96vh;
  width: 12vw;

  & h1 {
    align-items: center;
    border-bottom: 1px solid #999;
    display: flex;
    font-size: 1.1rem;
    font-weight: 200;
    height: 2rem;
    margin: auto;
    padding: .8rem .8rem;

    & svg {
      margin-right: 1rem;
    }
  }

  & section {
    padding: 1.2rem 0 0 1.5rem;

    & h2 {
      font-size: 1.2rem;
      font-variant: all-small-caps;
      margin-bottom: .6rem;
    }

    & ul {
      list-style: none;

      & li {
        font-size: .9rem;
        line-height: 1.2rem;
        list-style: none;
        margin-bottom: .4rem;

        & a {
          color: var(--gray-400);
          text-align: left;
          text-decoration: none;

          &:hover {
            text-decoration: underline;
          }
        }

        & svg {
          margin-right: .8rem;
        }
      }
    }
  }

  &.hidden {
    display: none;
  }
}
</style>


