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
        }
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
            "link",
            {
                href: "https://ooszy.cco.vin/img/ico/yuan.png",
                rel: "icon",
            },
        ],
    ],
    onPrepared: async (app) => {
        const myData = app.pages.map((page) => {
            return page;
        });
        await app.writeTemp(
            "my-data.js",
            `export default ${JSON.stringify(myData)}`
        );
    },
    theme: path.resolve(__dirname, "theme/lib/node/index.js"),
    title: "I do not follow,i lives is always all you want",

    port: 8080,
    themeConfig
};
