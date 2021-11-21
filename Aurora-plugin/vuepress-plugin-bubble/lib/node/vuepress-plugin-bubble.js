"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.cozePlugin = void 0;
const { path } = require('@vuepress/utils')
const utils_1 = require("@vuepress/utils");
const vuepressPluginBubble = ({bubbleNumber,bubbleAlpha,alphaChangeSpeed,size,sizeChangeSpeed,riseSpeed,color,zIndex}) => {
    return {
        onInitialized: (app) => {

        },
        name: 'vuepress-plugin-bubble',
        define: {
            __BUBBLE_NUMBER__: bubbleNumber,
            __BUBBLE_ALPHA__: bubbleAlpha,
            __ALPHA_CHANGE_SPEED__: alphaChangeSpeed,
            __SIZE__: size,
            __SIZE_CHANGE_SPEED__: sizeChangeSpeed,
            __RISE_SPEED__: riseSpeed,
            __COLOR__: color,
            __Z_INDEX__: zIndex
        },
        multiple: false,
        clientAppEnhanceFiles: utils_1.path.resolve(__dirname, '../clientAppEnhance.js'),
        clientAppRootComponentFiles: path.resolve(__dirname, '../AuroraBubble.vue'),
    };
};
exports.bubblePlugin = vuepressPluginBubble;
