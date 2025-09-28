import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home.vue'
import CsrfViaImage from '@/views/CsrfViaImage.vue'
import CsrfViaForm1 from '@/views/CsrfViaForm1.vue'
import CsrfViaForm2 from '@/views/CsrfViaForm2.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/csrf-via-image',
    name: 'CSRF via Image',
    component: CsrfViaImage
  },
  {
    path: '/csrf-via-form1',
    name: 'CSRF via Form 1',
    component: CsrfViaForm1
  },
  {
    path: '/csrf-via-form2',
    name: 'CSRF via Form 2',
    component: CsrfViaForm2
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router