"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.resolveContainerPluginOptionsForCodeGroupItem = exports.resolveContainerPluginOptionsForCodeGroup = exports.resolveContainerPluginOptionsForDetails = exports.resolveContainerPluginOptions = void 0;
/**
 * Resolve options for @vuepress/plugin-container
 *
 * For custom containers default title
 */
const resolveContainerPluginOptions = (themePlugins, localeOptions, type) => {
    var _a;
    if (((_a = themePlugins === null || themePlugins === void 0 ? void 0 : themePlugins.container) === null || _a === void 0 ? void 0 : _a[type]) === false) {
        return false;
    }
    const locales = Object.entries(localeOptions.locales || {}).reduce((result, [key, value]) => {
        var _a;
        result[key] = {
            defaultInfo: (_a = value === null || value === void 0 ? void 0 : value[type]) !== null && _a !== void 0 ? _a : localeOptions[type],
        };
        return result;
    }, {});
    return {
        type,
        locales,
    };
};
exports.resolveContainerPluginOptions = resolveContainerPluginOptions;
/**
 * Resolve options for @vuepress/plugin-container
 *
 * For details container
 */
const resolveContainerPluginOptionsForDetails = (themePlugins) => {
    var _a;
    if (((_a = themePlugins === null || themePlugins === void 0 ? void 0 : themePlugins.container) === null || _a === void 0 ? void 0 : _a.details) === false) {
        return false;
    }
    return {
        type: 'details',
        before: (info) => `<details class="custom-container details">${info ? `<summary>${info}</summary>` : ''}\n`,
        after: () => '</details>\n',
    };
};
exports.resolveContainerPluginOptionsForDetails = resolveContainerPluginOptionsForDetails;
/**
 * Resolve options for @vuepress/plugin-container
 *
 * For code-group container
 */
const resolveContainerPluginOptionsForCodeGroup = (themePlugins) => {
    var _a;
    if (((_a = themePlugins === null || themePlugins === void 0 ? void 0 : themePlugins.container) === null || _a === void 0 ? void 0 : _a.codeGroup) === false) {
        return false;
    }
    return {
        type: 'code-group',
        before: () => `<CodeGroup>\n`,
        after: () => '</CodeGroup>\n',
    };
};
exports.resolveContainerPluginOptionsForCodeGroup = resolveContainerPluginOptionsForCodeGroup;
/**
 * Resolve options for @vuepress/plugin-container
 *
 * For code-group-item block
 */
const resolveContainerPluginOptionsForCodeGroupItem = (themePlugins) => {
    var _a;
    if (((_a = themePlugins === null || themePlugins === void 0 ? void 0 : themePlugins.container) === null || _a === void 0 ? void 0 : _a.codeGroupItem) === false) {
        return false;
    }
    return {
        type: 'code-group-item',
        before: (info) => `<CodeGroupItem title="${info}">\n`,
        after: () => '</CodeGroupItem>\n',
    };
};
exports.resolveContainerPluginOptionsForCodeGroupItem = resolveContainerPluginOptionsForCodeGroupItem;
