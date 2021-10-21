import { h } from 'vue';
import { defineClientAppEnhance } from '@vuepress/client';
import Aurora from './components/Aurora'
export default defineClientAppEnhance(({ app, router }) => {
    app.component("Aurora",Aurora)
});
