<template>
    <div class="main">
        <aside-menu />
        <div class="body">
            <admin-menu />
            <div class="content">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>
<script lang="ts">
import Vue from 'vue';
import AsideMenu from '@/components/AsideMenu.vue';
import AdminMenu from '@/components/AdminMenu.vue';

export default Vue.extend({
  components: {
    AsideMenu,
    AdminMenu,
  },
});
</script>

<style lang="postcss">
@import url('https://fonts.googleapis.com/css?family=Montserrat');

* {
  font-family: 'Montserrat', sans-serif;
  margin: 0;
  padding: 0;
}

div.main {
  display: flex;

  & aside {
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
      padding: .5rem 1rem;

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
  }

  & div.body {
    width: 100%;

    & nav {
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
  }

  & div.content {
    align-content: flex-start;
    align-items: flex-start;
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    padding: 1rem;
  }
}
</style>
