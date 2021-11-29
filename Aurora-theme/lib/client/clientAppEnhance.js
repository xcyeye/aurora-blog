import { h } from 'vue';
import { defineClientAppEnhance } from '@vuepress/client';
import {useScrollPromise, useThemeData, useThemeLocaleData} from './composables';
import 'default-passive-events'

import CodeGroup from './components/global/CodeGroup';
import Badge from './components/global/Badge.vue';
import CodeGroupItem from './components/global/CodeGroupItem.vue';
import OutboundLink from './components/global/OutboundLink.vue';
import TopImage from './components/global/TopImage';
import BCenter from './components/global/BCenter';
import Common from './components/global/Common';
import Footer from './components/global/Footer';
import Message from './components/global/Message';
import Donate from "./components/global/Donate";
import Comment from "./components/global/Comment";
import Mood from "./components/Mood";
import About from './components/About';
import Tag from './components/Tag';
import store from './public/js/store'
import Link from './components/Link';
import Poster from "./components/global/Poster";
import HomeSidebar from "./components/child/side/HomeSidebar";
import HomeBottom from "./components/HomeBottom";
import AuroraGlobal from "./components/global/AuroraGlobal";
import Archive from './components/Archive'
//const AV = require('leancloud-storage');

//主题内置组件
import Pins from "./components/global/inner/Pins";

import './styles/index.scss';
import './styles/photo.scss'
//import './styles/coverStyle.css'
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

    delete app._context.components.OutboundLink;
    // override the built-in `<OutboundLink>`
    app.component('OutboundLink', OutboundLink);
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
