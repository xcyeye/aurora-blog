# 搜索

搜索功能请移步至<a href="https://v2.vuepress.vuejs.org/zh/reference/plugin/search.html#issearchable">vuepress官网</a>，安装此`@vuepress/plugin-search`插件时，请确保插件版本为`2.0.0-beta.25`或以上

也可以直接运行下面命令

```shell
npm i @vuepress/plugin-search@2.0.0-beta.25
```

当你安装完成之后，在`docs/config.js`内进行下面配置便可以

```js
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
    ],
};
```



## seo配置

[seo配置](./seo.md)