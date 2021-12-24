import { defineClientAppEnhance } from '@vuepress/client';
import CozeMood from './components/CozeMood.vue'
import RegisterUser from './components/RegisterUser.vue'
import CozeLogin from "./components/CozeLogin.vue";
import CozePhoto from "./components/CozePhoto.vue";
import './style/coze-plugin.css'

import { init } from "leancloud-storage";

let appId = ''
let appKey = ''
let masterKey = ''
let onlyAdministrator = true;
let avatar = 'https://ooszy.cco.vin/img/blog-note/avatar-aurora.png'
try {
    appId = __APP_ID__;
    appKey = __APP_KEY__;
    masterKey = __Master_Key__;
    avatar = __AVATAR_PATH__;
    onlyAdministrator = __ONLY_ADMINISTRATOR
}catch (e) {
    console.warn("你必须在插件中传入appId,appKey,masterKey配置项")
}

export default defineClientAppEnhance(({ app, router }) => {
    app.component("CozeMood",CozeMood)
    app.component("RegisterUser",RegisterUser)
    app.component("CozeLogin",CozeLogin)
    app.component("CozePhoto",CozePhoto)

    router.addRoute({
        path: '/login',
        component: CozePhoto
    })

    try {
        init({
            appId: appId,
            appKey: appKey
        });
    }catch (e) {
        console.warn(e)
    }
});
