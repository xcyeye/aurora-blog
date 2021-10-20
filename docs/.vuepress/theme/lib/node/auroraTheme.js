"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.defaultTheme = void 0;
const { path } = require('@vuepress/utils')
const { createPage } = require('@vuepress/core');
const utils_1 = require("@vuepress/utils");
const utils_2 = require("./utils");
const auroraTheme = ({ themePlugins = {}, ...localeOptions }) => {
    utils_2.assignDefaultLocaleOptions(localeOptions);
    return {
        //name: '@vuepress/theme-default',
        name: '@vuepress-theme-aurora',
        layouts: utils_1.path.resolve(__dirname, '../client/layouts'),
        clientAppEnhanceFiles: utils_1.path.resolve(__dirname, '../client/clientAppEnhance.js'),
        clientAppSetupFiles: utils_1.path.resolve(__dirname, '../client/clientAppSetup.js'),
        // use the relative file path to generate edit link
        extendsPageData: ({ filePathRelative }) => ({ filePathRelative }),
        onPrepared: async (app) => {
            const myData = app.pages.map((page) => {
                return page;
            });
            await app.writeTemp(
                "my-data.js",
                `export default ${JSON.stringify(myData)}`
            );
        },
        onInitialized: async (app) => {
            // 如果主页不存在
            const homepage1 = await createPage(app, {
                path: '/about',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'About'
                },
                // 设置 markdown 内容
                content: "",
            })

            const homepage2 = await createPage(app, {
                path: '/link',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'Link'
                },
                // 设置 markdown 内容
                content: "",
            })

            const homepage3 = await createPage(app, {
                path: '/mood',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'Mood'
                },
                // 设置 markdown 内容
                content: "",
            })

            const homepage4 = await createPage(app, {
                path: '/photo',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'PhotoFall'
                },
                // 设置 markdown 内容
                content: "",
            })

            const homepage5 = await createPage(app, {
                path: '/tag',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'Tag'
                },
                // 设置 markdown 内容
                content: "",
            })

            // 把它添加到 `app.pages`
            app.pages.push(homepage1)
            app.pages.push(homepage2)
            app.pages.push(homepage3)
            app.pages.push(homepage4)
            app.pages.push(homepage5)
        },
        onGenerated: (app) => {
            setTimeout(() => {
                process.exit(0)
            },3000)
        },
        plugins: [
            [
                '@vuepress/active-header-links',
                utils_2.resolveActiveHeaderLinksPluginOptions(themePlugins),
            ],
            ['@vuepress/back-to-top', themePlugins.backToTop !== false],
            [
                '@vuepress/container',
                utils_2.resolveContainerPluginOptions(themePlugins, localeOptions, 'tip'),
            ],
            [
                '@vuepress/container',
                utils_2.resolveContainerPluginOptions(themePlugins, localeOptions, 'warning'),
            ],
            [
                '@vuepress/container',
                utils_2.resolveContainerPluginOptions(themePlugins, localeOptions, 'danger'),
            ],
            [
                '@vuepress/container',
                utils_2.resolveContainerPluginOptionsForDetails(themePlugins),
            ],
            [
                '@vuepress/container',
                utils_2.resolveContainerPluginOptionsForCodeGroup(themePlugins),
            ],
            [
                '@vuepress/container',
                utils_2.resolveContainerPluginOptionsForCodeGroupItem(themePlugins),
            ],
            ['@vuepress/git', utils_2.resolveGitPluginOptions(themePlugins, localeOptions)],
            ['@vuepress/medium-zoom', utils_2.resolveMediumZoomPluginOptions(themePlugins)],
            ['@vuepress/nprogress', themePlugins.nprogress !== false],
            ['@vuepress/palette', { preset: 'sass' }],
            ['@vuepress/prismjs', themePlugins.prismjs !== false],
            ['@vuepress/theme-data', { themeData: localeOptions }],
        ],
    };
};
exports.auroraTheme = auroraTheme;
