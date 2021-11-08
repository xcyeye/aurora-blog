"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.resolveGitPluginOptions = void 0;
/**
 * Resolve options for @vuepress/plugin-git
 */
const resolveGitPluginOptions = (themePlugins, localeOptions) => {
    if ((themePlugins === null || themePlugins === void 0 ? void 0 : themePlugins.git) === false) {
        return false;
    }
    const enableUpdatedTime = localeOptions.lastUpdated !== false;
    const enableContributors = localeOptions.contributors !== false;
    if (!enableUpdatedTime && !enableContributors) {
        return false;
    }
    return {
        createdTime: false,
        updatedTime: enableUpdatedTime,
        contributors: enableContributors,
    };
};
exports.resolveGitPluginOptions = resolveGitPluginOptions;
