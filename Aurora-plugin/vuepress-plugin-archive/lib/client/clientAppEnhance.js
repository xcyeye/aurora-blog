import { defineClientAppEnhance } from '@vuepress/client';
import AuroraArchive from "./components/AuroraArchive.vue";
import './style/aurora-archive.css'

export default defineClientAppEnhance(({ app, router }) => {
    app.component("AuroraArchive",AuroraArchive)
});
