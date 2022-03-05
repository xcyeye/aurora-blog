import { h } from 'vue';
import { defineClientAppEnhance } from '@vuepress/client';
import {useScrollPromise, useThemeData, useThemeLocaleData} from './composables';
import 'default-passive-events'
import store from './public/js/store'

//组件导入
import CodeGroup from "./components/common/CodeGroup";
import Badge from './components/common/Badge.vue';
import CodeGroupItem from './components/common/CodeGroupItem.vue';
import TopImage from './components/common/TopImage.vue';
import BCenter from './components/common/BCenter.vue';
import Common from './components/common/Common.vue';
import Footer from './components/common/Footer.vue';
import Message from './components/common/Message.vue';
import Donate from "./components/common/Donate.vue";
import Comment from "./components/common/Comment.vue";
import Mood from "./components/Mood.vue";
import About from './components/About.vue';
import Tag from './components/Tag.vue';
import Link from './components/Link.vue';
import Poster from "./components/common/Poster.vue";
import HomeSidebar from "./components/child/side/HomeSidebar.vue";
import HomeBottom from "./components/HomeBottom.vue";
import AuroraGlobal from "./components/common/AuroraGlobal.vue";
import Archive from './components/Archive.vue'

import Mood2 from "./components/Mood2.vue";
import AuroraApp from "./components/common/AuroraApp.vue";

//主题内置组件
import Pins from "./components/common/inner/Pins.vue";
import AuroraBubble from "./components/common/inner/AuroraBubble.vue";

//样式导入
import './styles/index.scss';
import './styles/theme.style.css'
import "@vuepress/plugin-palette/palette";
import "@vuepress/plugin-palette/style";
export default defineClientAppEnhance(({ app, router }) => {
    app.component('Badge', Badge);
    app.component('CodeGroup', CodeGroup);
    app.component('CodeGroupItem', CodeGroupItem);
    app.component('TopImage', TopImage);
    app.component('BCenter', BCenter);
    app.component('Common', Common);
    app.component('Footer', Footer);
    app.component('aurora-footer', Footer);
    app.component('Message', Message);
    app.component('Donate', Donate);
    app.component('Comment', Comment);
    app.component('Poster', Poster);
    app.component("HomeSidebar",HomeSidebar)
    app.component("HomeBottom",HomeBottom)
    app.component("About",About)
    app.component("Link",Link)
    app.component("Mood",Mood)
    app.component("Tag",Tag)
    app.component("AuroraGlobal",AuroraGlobal)
    app.component("Archive",Archive)
    app.component("Pins",Pins)
    app.component("AuroraBubble",AuroraBubble)
    app.component("Mood2",Mood2)
    app.component("AuroraApp",AuroraApp)


    // 导入测试组件

    /*let themeConfig = useThemeData().value
    let isSetLeanCloud = false

    if (themeConfig.leanCloud === undefined || themeConfig.leanCloud.appId === undefined) {
        console.warn("你未在主题配置中指定leanCloud秘钥，不能使用文章阅读量统计功能")
    }else {
        isSetLeanCloud = true
    }

    if (isSetLeanCloud) {
        AV.init({
            appId: themeConfig.leanCloud.appId,
            appKey: themeConfig.leanCloud.appKey,
            masterKey: themeConfig.leanCloud.masterKey
        });
    }*/

    //路由
    // @ts-ignore
    app.use(store)

    // compat with @vuepress/plugin-docsearch and @vuepress/plugin-search
    app.component('NavbarSearch', () => {
        const SearchComponent = app.component('Docsearch') || app.component('SearchBox');
        if (SearchComponent) {
            return h(SearchComponent);
        }
        return null;
    });
    // handle scrollBehavior with transition
    const scrollBehavior = router.options.scrollBehavior;
    router.options.scrollBehavior = async (...args) => {
        await useScrollPromise().wait();
        return scrollBehavior(...args);
    };
});
