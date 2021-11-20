import { defineClientAppEnhance } from '@vuepress/client';
import AuroraMusic from "./components/AuroraMusic";
import './style/aurora.music.css'
import $ from "jquery";
import {createApp} from "vue";
export default defineClientAppEnhance(({ app, router }) => {
    app.component("AuroraMusic",AuroraMusic)

    let posterAppend = $("<div class=\"aurora-music-append\" id=\"aurora-music-append\">").get(0)
    $("#app").get(0).appendChild(posterAppend)
    createApp(AuroraMusic,{
        app: app,
    }).mount("#aurora-music-append")
});
