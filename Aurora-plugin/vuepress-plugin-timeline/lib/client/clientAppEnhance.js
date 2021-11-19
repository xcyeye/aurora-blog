import { h } from 'vue';
import { defineClientAppEnhance } from '@vuepress/client';
import Timeline from "./components/Timeline";
import './style/aurora-timeline.css'

export default defineClientAppEnhance(({ app, router }) => {
    app.component("Timeline",Timeline)
});
