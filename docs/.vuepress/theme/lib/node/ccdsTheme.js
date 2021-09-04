"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.defaultTheme = void 0;
const { path } = require('@vuepress/utils')
const utils_1 = require("@vuepress/utils");
const utils_2 = require("./utils");
const ccdsTheme = ({ themePlugins = {}, ...localeOptions }) => {
    utils_2.assignDefaultLocaleOptions(localeOptions);
    return {
        //name: '@vuepress/theme-default',
        name: 'vuepress-theme-ccds',
        layouts: utils_1.path.resolve(__dirname, '../client/layouts'),
        clientAppEnhanceFiles: utils_1.path.resolve(__dirname, '../client/clientAppEnhance.js'),
        clientAppSetupFiles: utils_1.path.resolve(__dirname, '../client/clientAppSetup.js'),
        // use the relative file path to generate edit link
        extendsPageData: ({ filePathRelative }) => ({ filePathRelative }),
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
exports.ccdsTheme = ccdsTheme;
