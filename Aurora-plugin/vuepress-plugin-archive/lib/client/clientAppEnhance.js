import { defineClientAppEnhance } from '@vuepress/client';
import AuroraArchive from "./components/AuroraArchive";
import './style/aurora-archive.css'

export default defineClientAppEnhance(({ app, router }) => {
    app.component("AuroraArchive",AuroraArchive)
});
