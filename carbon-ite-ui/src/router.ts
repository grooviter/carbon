import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import { default as ListScripts } from '@/views/scripts/List.vue';
import { default as AddUser } from '@/views/users/Add.vue';
import { default as ListUsers } from '@/views/users/List.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/scripts',
      name: 'list-scripts',
      component: ListScripts,
    },
    {
      path: '/users',
      name: 'list-users',
      component: ListUsers,
    },
    {
      path: '/users/add',
      name: 'add-user',
      component: AddUser,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/Home.vue'),
    },
  ],
});
