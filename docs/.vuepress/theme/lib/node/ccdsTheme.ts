import type { Theme, ThemeConfig } from '@vuepress/core';
import type { DefaultThemeLocaleOptions, DefaultThemePluginsOptions } from '../shared';
export interface DefaultThemeOptions extends ThemeConfig, DefaultThemeLocaleOptions {
    /**
     * To avoid confusion with the root `plugins` option,
     * we use `themePlugins`
     */
    themePlugins?: DefaultThemePluginsOptions;
}
export declare const defaultTheme: Theme<DefaultThemeOptions>;
