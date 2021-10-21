import { h } from 'vue';
import { defineClientAppEnhance } from '@vuepress/client';
import Badge from './components/global/Badge.vue';
import CodeGroup from './components/global/CodeGroup';
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
import { useScrollPromise } from './composables';

import About from './components/About';
import Tag from './components/Tag';
import store from './public/js/store'
import Link from './components/Link';
import Poster from "./components/global/Poster";
import Photo from "./components/Photo";
import PhotoFall from "./components/PhotoFall";
import AddMood from "./components/child/AddMood";
import HomeSidebar from "./components/child/side/HomeSidebar";
import HomeBottom from "./components/HomeBottom";
import AuroraGlobal from "./components/global/AuroraGlobal";

import './styles/index.scss';
import './styles/photo.scss'
export default defineClientAppEnhance(({ app, router }) => {
    app.component('Badge', Badge);
    app.component('CodeGroup', CodeGroup);
    app.component('CodeGroupItem', CodeGroupItem);
    app.component('TopImage', TopImage);
    app.component('BCenter', BCenter);
    app.component('Common', Common);
    app.component('Footer', Footer);
    app.component('Message', Message);
    app.component('Donate', Donate);
    app.component('Comment', Comment);
    app.component('Poster', Poster);
    app.component("Photo",Photo)
    app.component("AddMood",AddMood)
    app.component("HomeSidebar",HomeSidebar)
    app.component("HomeBottom",HomeBottom)
    app.component("About",About)
    app.component("Link",Link)
    app.component("Mood",Mood)
    app.component("PhotoFall",PhotoFall)
    app.component("Tag",Tag)
    app.component("AuroraGlobal",AuroraGlobal)

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
