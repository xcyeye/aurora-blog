"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.cozePlugin = void 0;
const { path } = require('@vuepress/utils')
const { createPage } = require('@vuepress/core');
const utils_1 = require("@vuepress/utils");
const vuepressPluginPlayer = ({songIds,playlist,showPlaylist ,disabledNetEaseMusic,localSongs,serverUrl,disableSpace}) => {
    return {
        define: {
            __SONG_IDS__: songIds,
            __PLAYLIST__: playlist,
            __SHOW_PLAYLIST__: showPlaylist,
            __DISABLED_NET_EASE_MUSIC__: disabledNetEaseMusic,
            __LOCAL_SONGS__: localSongs,
            __SERVER_URL__ :serverUrl,
            __DISABLED_SPACE__: disableSpace
        },
        onInitialized: async (app) => {
            // 如果主页不存在
            const timeline = await createPage(app, {
                path: '/aurora-music',
                // 设置 frontmatter
                frontmatter: {
                    layout: 'AuroraMusic',
                    slug: 'aurora-plugin-music',
                },
                permalink: '/aurora-music',
                // 设置 markdown 内容
                content: "",
            })

            // 把它添加到 `app.pages`
            app.pages.push(timeline)
        },
        name: 'vuepress-plugin-player',
        multiple: false,
        clientAppEnhanceFiles: utils_1.path.resolve(__dirname, '../client/clientAppEnhance.js'),
        clientAppRootComponentFiles: path.resolve(__dirname, '../client/components/AuroraMusic.vue'),
    };
};
exports.playerPlugin = vuepressPluginPlayer;
