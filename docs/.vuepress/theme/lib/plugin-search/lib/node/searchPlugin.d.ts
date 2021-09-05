import type { Page, Plugin } from '@vuepress/core';
import type { LocaleConfig } from '@vuepress/shared';
export interface SearchPluginOptions {
    locales?: LocaleConfig<{
        placeholder: string;
    }>;
    hotKeys: string[];
    maxSuggestions: number;
    isSearchable: (page: Page) => boolean;
    getExtraFields: (page: Page) => string[];
}
export declare const searchPlugin: Plugin<SearchPluginOptions>;
