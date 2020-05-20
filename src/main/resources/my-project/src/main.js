import Vue from 'vue'
import App from './App.vue'
import baseURL from './assets/js/url'
import './plugins/element.js';
import axios from 'axios';
import VueAxios from 'vue-axios';
import router from './router';
import store from './store';
import VueCookies from 'vue-cookies';
import Vuex from 'vuex'
import './assets/css/global.css'
import 'element-ui/lib/theme-chalk/index.css'
axios.defaults.withCredentials=true //让ajax携带cookie
axios.defaults.headers.common['Content-Type'] = 'application/json;charset=UTF-8'
Vue.config.productionTip = false
Vue.use(VueAxios,axios);
Vue.use(VueCookies);
Vue.use(Vuex)
Vue.prototype.global = baseURL
//挂在路由导航首位
router.beforeEach((to,from,next)=>{
  //to 要访问的路径
  //from 从哪个路径跳转来
  //next 放行
  if(to.path == '/login') return next(); 
  if(to.path == '/home') return next(); 
  if(to.path == '/') return next('/home'); 
  //获取token
  const tokenStr = VueCookies.get('User')
  if(tokenStr == null) return next('/login')
  next();
})
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
