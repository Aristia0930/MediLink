import { createRouter, createWebHistory } from 'vue-router'
import Test from '@/components/test.vue'
import Signup from '@/page/Signup.vue'

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
    }
    

  ],
})

export default router
