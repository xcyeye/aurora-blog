import type { ContainerPluginOptions } from '@vuepress/plugin-container';
import type { DefaultThemePluginsOptions, DefaultThemeData } from '../../shared';
/**
 * Resolve options for @vuepress/plugin-container
 *
 * For custom containers default title
 */
export declare const resolveContainerPluginOptions: (themePlugins: DefaultThemePluginsOptions, localeOptions: DefaultThemeData, type: 'tip' | 'warning' | 'danger') => ContainerPluginOptions | boolean;
/**
 * Resolve options for @vuepress/plugin-container
 *
 * For details container
 */
export declare const resolveContainerPluginOptionsForDetails: (themePlugins: DefaultThemePluginsOptions) => ContainerPluginOptions | boolean;
/**
 * Resolve options for @vuepress/plugin-container
 *
 * For code-group container
 */
export declare const resolveContainerPluginOptionsForCodeGroup: (themePlugins: DefaultThemePluginsOptions) => ContainerPluginOptions | boolean;
/**
 * Resolve options for @vuepress/plugin-container
 *
 * For code-group-item block
 */
export declare const resolveContainerPluginOptionsForCodeGroupItem: (themePlugins: DefaultThemePluginsOptions) => ContainerPluginOptions | boolean;
