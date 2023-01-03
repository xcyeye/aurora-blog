import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/home/Home.vue')
    },
    {
      path: '/friendLink',
      name: 'friendLink',
      component: () => import('../views/friend-link/index.vue')
    },
    {
      path: '/archive',
      name: 'archive',
      component: () => import('../views/archive/index.vue')
    },
    {
      path: '/tag-category',
      name: 'tagCategory',
      component: () => import('../views/tagAndCategory/index.vue')
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/about/index.vue')
    },
    {
      path: '/shareSpace1',
      name: '说说1',
      component: () => import('../views/share-space/sharePage1.vue')
    },
    {
      path: '/shareSpace2',
      name: '说说2',
      component: () => import('../views/share-space/sharePage2.vue')
    }
  ]
})

export default router
