const { path } = require("@vuepress/utils");
const { themeConfig } = require("./themeConfig");

module.exports = {
    plugins: [
        "@vuepress/plugin-search",
        {
            locales: {
                "/": {
                    placeholder: "Search",
                },
                "/zh/": {
                    placeholder: "搜索",
                },
            },
        },
        '@vuepress/plugin-google-analytics',
        {
            id: 'G-2MH1NBN9ER',
        },
        [
            '@vuepress/plugin-palette',
            { preset: 'css' },
        ],
    ],
    //设置head 一定要加入<script src="https://at.alicdn.com/t/font_2849934_v6y652peian.js"></script>项配置，否则一些图标不能正常显示
    head: [
        [
            "script",
            {
                src: "https://at.alicdn.com/t/font_2849934_v6y652peian.js",
            },
        ],
        [
            "script",
            {
                src: 'https://www.googletagmanager.com/gtag/js?id=G-2MH1NBN9ER',
                async: 'async'
            }
        ],
        [
            "link",
            {
                href: "https://ooszy.cco.vin/img/ico/yuan.png",
                rel: "icon",
            },
        ],
    ],
    theme: path.resolve(__dirname, "theme/lib/node/index.js"),
    title: "I do not follow,i lives is always all you want",
    lang: 'zh-CN',
    port: 8080,
    themeConfig,
};
