const {path} = require("@vuepress/utils");
const {sidebar} = require("./theme/lib/client/public/js/hrefs");
const {navbar} = require("./theme/lib/client/public/js/navbar");
module.exports = {

    plugins: [
            //'@vuepress/plugin-docsearch'
    ],    //base: '/chu',
    onPrepared: async (app) => {
        const myData = app.pages.map((page) => {
            return page
        })
        await app.writeTemp('my-data.js', `export default ${JSON.stringify(myData)}`)
    },
    //theme: 'ccds',
    theme: path.resolve(__dirname, "theme/lib/node/index.js"),
    //theme: 'vuepress-theme-ccds',
    title: "qsyyke",
    //description: "我和你，可以做朋友？",
    //description: "qsyyke的个人博客，记录生活琐事，学习笔记",

    port: 8082,

    themeConfig: {
        logo: "https://ooszy.cco.vin/img/blog-public/ccds_64.ico",
        navbar: navbar,
        sidebar: sidebar,
        darkMode: false,
        repo: "https://github.com/qsyyke/vuepress-theme-ccds",
        repoLabel: "ccds-theme",
        editLink: true,
        editLinkText: "edit",
        lastUpdated: true,
        lastUpdatedText: "lastTime",
    },
};