"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.defaultTheme = void 0;
const { createPage } = require('@vuepress/core');
const utils_1 = require("@vuepress/utils");
const utils_2 = require("./utils");

const auroraTheme = ({ themePlugins = {},...localeOptions }) => {
    utils_2.assignDefaultLocaleOptions(localeOptions);
    return {
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
            const aboutPage = await createPage(app, {
                path: '/about',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'About',
                    slug: 'aurora-about',
                },
                permalink: '/about',
                // 设置 markdown 内容
                content: "",
            })

            const linkPage = await createPage(app, {
                path: '/link',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'Link',
                    slug: 'aurora-link',
                },

                // 设置 markdown 内容
                content: "",
            })

            const moodPage = await createPage(app, {
                path: '/mood',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'Mood',
                    slug: 'aurora-mood',
                },
                // 设置 markdown 内容
                content: "",
            })

            const tagPhoto = await createPage(app, {
                path: '/tag',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'Tag',
                    slug: 'aurora-tag',
                },
                // 设置 markdown 内容
                content: "",
            })

            const ArchivePage = await createPage(app, {
                path: '/archive',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'Archive',
                    slug: 'aurora-archive',
                },
                // 设置 markdown 内容
                content: "",
            })

            if (app.pages.every((page) => page.path !== '/')) {
                const homepage = await createPage(app, {
                    path: '/',
                    // 设置 frontmatter
                    frontmatter: {
                        layout: 'Layout',
                        home: true
                    },
                    // 设置 markdown 内容
                    content: ""
                })
                app.pages.push(homepage)
            }

            // 把它添加到 `app.pages`
            app.pages.push(aboutPage)
            app.pages.push(moodPage)
            app.pages.push(linkPage)
            app.pages.push(tagPhoto)
            app.pages.push(ArchivePage)
        },
        onGenerated: (app) => {
            console.log("\x1B[32m\x1B[1m","\n博客部署请看: https://aurora.xcye.xyz/home/deploy.html\n")
            setTimeout(() => {
                process.exit(0)
            },3000)
        },
        extendsMarkdown: (md) => {
            md.use(require('markdown-it-modify-token')).set({
                modifyToken: function (token, env) {
                    switch (token.type) {
                        case 'image':
                            let originSrc = token.attrObj.src
                            token.attrObj.src = "https://ooszy.cco.vin/img/blog-public/ljz.gif"
                            token.attrObj.originSrc = originSrc
                            token.attrObj.data = "aurora"
                            break;
                    }
                }
            })
        },
        plugins: [
            ['@vuepress/external-link-icon', themePlugins.externalLinkIcon !== false],
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
            ['@vuepress/palette', { preset: 'css' }],
            ['@vuepress/prismjs', themePlugins.prismjs !== false],
            ['@vuepress/theme-data', { themeData: localeOptions }]
        ],
    };
};
exports.auroraTheme = auroraTheme;
