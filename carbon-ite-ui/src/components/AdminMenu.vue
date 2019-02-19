<template>
    <nav>
        <div class="left">
            <a class="collapse" @click="toggle">
                <font-awesome-icon icon="compress-arrows-alt" size="lg"/>
            </a>
        </div>
        <div class="right">
            <a href="#notifications"><font-awesome-icon icon="bell" size="lg"/></a>
            <a href="#notifications"><font-awesome-icon icon="comments" size="lg"/></a>
            <a class="user" href="#notifications">
                <img src="@/assets/pro4.jpg" />
                <span>Mario Garcia</span>
                <font-awesome-icon icon="caret-down" size="lg" />
            </a>
        </div>
    </nav>
</template>
<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
    faBell,
    faComments,
    faCompressArrowsAlt,
    faCaretDown,
} from '@fortawesome/free-solid-svg-icons';

library.add(faBell, faComments, faCompressArrowsAlt, faCaretDown);

@Component({
    components: {
        FontAwesomeIcon,
    },
})
export default class AdminMenu extends Vue {
    public toggle() {
        this.$store.commit('toggle');
    }
}
</script>
<style lang="postcss" scoped>
  nav {
    align-items: center;
    border-bottom: 1px solid #999;
    display: flex;
    font-size: 1rem;
    height: 2rem;
    justify-content: space-between;
    padding: .8rem 0;
    width: 100%;

    & div {
      display: flex;
    }

    & div.left {
      display: flex;
      padding-left: 1rem;
    }

    & div.right {
      align-items: center;
      display: flex;
      flex-direction: row;
      justify-content: space-around;
      padding-right: 1rem;
      width: 16rem;

      & a {
        color: #666;
        text-decoration: none;
        width: 2rem;
      }

      & a.user {
        align-items: center;
        border: 1px solid #9999;
        border-radius: 5%;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        margin-left: 1rem;
        padding: .2rem .8rem;
        width: 12rem;

        & svg {
          display: flex;
        }

        & img {
          border-radius: 50%;
          height: 2rem;
          width: 2rem;
        }

        &:hover {
          background: #666;
          color: #fff;
        }
      }
    }
  }
</style>

