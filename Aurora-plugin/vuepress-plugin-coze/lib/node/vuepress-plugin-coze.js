"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.cozePlugin = void 0;
const { createPage } = require('@vuepress/core');
const utils_1 = require("@vuepress/utils");
const AV = require('leancloud-storage');
const vuepressPluginCoze = ({appId,appKey,masterKey,avatarPath,registerPath,onlyAdministrator }) => {
    return {
        onInitialized: async (app) => {
            if (registerPath === undefined || registerPath === null) {
                registerPath = "/aurora-register"
            }

            const AuroraCozePage = await createPage(app, {
                //说说页面，默认
                path: '/aurora-coze',
                frontmatter: {
                    layout: 'CozeMood',
                    slug: 'aurora-coze-mood',
                },
                // 设置 markdown 内容
                content: "",
            })

            const photoPage = await createPage(app, {
                path: '/photo',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'CozePhoto',
                    slug: 'aurora-photo',
                },
                // 设置 markdown 内容
                content: "",
            })

            const RegisterUserPage = await createPage(app, {
                //说说页面，默认
                path: registerPath,
                frontmatter: {
                    layout: 'RegisterUser',
                    slug: 'aurora-coze-register',
                },
                // 设置 markdown 内容
                content: "",
            })

            // 把它添加到 `app.pages`
            app.pages.push(AuroraCozePage)
            app.pages.push(RegisterUserPage)
            app.pages.push(photoPage)
        },
        define: {
            __APP_ID__: appId,
            __APP_KEY__: appKey,
            __Master_Key__: masterKey,
            __AVATAR_PATH__: avatarPath,
            __ONLY_ADMINISTRATOR: onlyAdministrator
        },
        name: 'vuepress-plugin-coze',

        multiple: false,
        clientAppEnhanceFiles: utils_1.path.resolve(__dirname, '../client/clientAppEnhance.js')
    };
};
exports.cozePlugin = vuepressPluginCoze;
