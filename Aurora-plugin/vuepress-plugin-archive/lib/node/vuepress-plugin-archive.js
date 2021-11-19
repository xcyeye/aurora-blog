"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.cozePlugin = void 0;
const { createPage } = require('@vuepress/core');
const utils_1 = require("@vuepress/utils");
const vuepressPluginArchive = ({ excludes,noTitle }) => {
    return {
        define: {
            __EXCLUDES__: excludes,
            __NO_TITLE__: noTitle
        },
        onInitialized: async (app) => {
            // 如果主页不存在
            const timeline = await createPage(app, {
                path: '/aurora-archive',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'AuroraArchive',
                    slug: 'aurora-plugin-archive',
                },
                permalink: '/aurora-archive',
                // 设置 markdown 内容
                content: "",
            })

            // 把它添加到 `app.pages`
            app.pages.push(timeline)
        },
        onPrepared: async (app) => {
            const pageData = app.pages.map((page) => {
                return page;
            });
            await app.writeTemp(
                "page-data.js",
                `export default ${JSON.stringify(pageData)}`
            );
        },
        name: 'vuepress-plugin-archive',
        multiple: false,
        clientAppEnhanceFiles: utils_1.path.resolve(__dirname, '../client/clientAppEnhance.js')
    };
};
exports.archivePlugin = vuepressPluginArchive;
