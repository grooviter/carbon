import Vue from 'vue';
import App from './components/layout/main/App.vue';
import List from './components/pages/list/List.vue';
import Dashboard from './components/pages/dashboard/Dashboard.vue';
import Router from 'vue-router';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

Vue.use(Router)
Vue.component('font-awesome-icon', FontAwesomeIcon);

const routes = [
  { path: '/', component: Dashboard },
  { path: '/list', component: List }
]

const router = new Router({
  routes
})

new Vue({
  el: '#app',
  render: h => h(App),
  router
});
