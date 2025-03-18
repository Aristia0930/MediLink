import { createRouter, createWebHistory } from 'vue-router'
import Map from '@/map/pages/map.vue'
import Test from '@/map/components/test.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Test,
    },

    {
      path: '/map',
      name: 'map',
      component: Map,
    }
    

  ],
})

export default router
