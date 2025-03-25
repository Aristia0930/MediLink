import { createRouter, createWebHistory } from 'vue-router'
import Test from '@/components/test.vue'
import Signup from '@/page/Signup.vue'
import Login from '@/page/Login.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Test,
    },

    {
      path :'/signup',
      name:'signup',
      component:Signup
    },
    {
      path : '/login',
      name:'login',
      component:Login
    }
    

  ],
})

export default router
