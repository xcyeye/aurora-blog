import { defineClientAppEnhance } from '@vuepress/client';
import AuroraMusic from "./components/AuroraMusic";
import './style/aurora.music.css'
import {createApp} from "vue";
export default defineClientAppEnhance(({ app, router }) => {
    app.component("AuroraMusic",AuroraMusic)
    setTimeout(() => {
        let divElement = document.createElement("div");
        divElement.setAttribute("id",'aurora-music-append')
        document.querySelector("#app").appendChild(divElement)
        createApp(AuroraMusic,{
            app: app,
        }).mount("#aurora-music-append")
    },1500)
});
