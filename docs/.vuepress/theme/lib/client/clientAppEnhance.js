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
import Profile from './components/global/Profile';
import Donate from "./components/global/Donate";
import Comment from "./components/global/Comment";
import Mood from "./components/Mood";
import { useScrollPromise } from './composables';

import About from './components/About';
import Tag from './components/Tag';
import store from './public/js/store'
import Link from './components/Link';

//搜索组件
import { SearchBox } from '../plugin-search/lib/client/components/SearchBox';
import '../plugin-search/lib/client/styles/vars.css';
import '../plugin-search/lib/client/styles/search.css';

const searchOptions = {
    locales: {
        '/': {
            placeholder: 'Search',
        },
        '/zh/': {
            placeholder: '搜索',
        },
    },
}

import './styles/index.scss';
export default defineClientAppEnhance(({ app, router }) => {
    app.component('Badge', Badge);
    app.component('CodeGroup', CodeGroup);
    app.component('CodeGroupItem', CodeGroupItem);
    app.component('TopImage', TopImage);
    app.component('BCenter', BCenter);
    app.component('Common', Common);
    app.component('Footer', Footer);
    app.component('Message', Message);
    app.component('Profile', Profile);
    app.component('Donate', Donate);
    app.component('Comment', Comment);

    //app.component('SearchBox', (props) => h(SearchBox, {
    //    locales,
    //    hotKeys,
    //    maxSuggestions,
    //    ...props,
    //}));

    //app.component('SearchBox',SearchBox);

    app.component('SearchBox', (props) => h(SearchBox, searchOptions));

    //app.component('Docsearch', () => h(Docsearch, { options }));
    //路由
    // @ts-ignore
    app.use(store)

    router.addRoute({
        path: '/about',
        name: 'c-about',
        component: About
    })

    router.addRoute({
        path: '/link',
        name: 'c-link',
        component: Link
    })

    router.addRoute({
        path: '/tag',
        name: 'c-tag',
        component: Tag
    })
    router.addRoute({
        path: '/mood',
        name: 'c-time',
        component: Mood
    })

    // unregister the built-in `<OutboundLink>` to avoid warning
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
