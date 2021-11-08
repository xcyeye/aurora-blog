import { defineClientAppEnhance } from '@vuepress/client';
const appId = __APP_ID__;
const appKey = __APP_KEY__;
const masterKey = __Master_Key__;
const avatar = __AVATAR_PATH__;
const AV = require('leancloud-storage');
const { Query, User } = AV;
import CozeMood from './components/CozeMood'
import RegisterUser from './components/RegisterUser'
import CozeLogin from "./components/CozeLogin";
import './style/coze-plugin.css'
export default defineClientAppEnhance(({ app, router }) => {
    app.component("CozeMood",CozeMood)
    app.component("RegisterUser",RegisterUser)
    app.component("CozeLogin",CozeLogin)

    if (avatar === undefined) {}
    AV.init({
        appId: appId,
        appKey: appKey
    });
});
