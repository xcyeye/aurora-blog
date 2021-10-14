"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.resolveMediumZoomPluginOptions = void 0;
/**
 * Resolve options for @vuepress/plugin-medium-zoom
 */
const resolveMediumZoomPluginOptions = (themePlugins) => {
    if ((themePlugins === null || themePlugins === void 0 ? void 0 : themePlugins.mediumZoom) === false) {
        return false;
    }
    return {
        //selector: '.theme-default-content > img, .theme-default-content :not(a) > img',
        selector: '.medium-zoom-content > img, .medium-zoom-content :not(a) > img, .medium-zoom-content :not(button) > img',
        zoomOptions: {},
        // should greater than page transition duration
        delay: 400,
    };
};
exports.resolveMediumZoomPluginOptions = resolveMediumZoomPluginOptions;
