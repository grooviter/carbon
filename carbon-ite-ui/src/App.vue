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

  & div.body {
    width: 100%;
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
