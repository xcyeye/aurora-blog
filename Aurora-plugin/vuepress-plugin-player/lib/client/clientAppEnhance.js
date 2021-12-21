import { defineClientAppEnhance } from '@vuepress/client';
import AuroraMusic from "./components/AuroraMusic.vue";
import './style/aurora.music.css'
export default defineClientAppEnhance(({ app, router }) => {
    app.component("AuroraMusic",AuroraMusic)
});
