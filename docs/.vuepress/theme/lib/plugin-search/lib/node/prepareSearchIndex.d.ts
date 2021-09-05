import type { App } from '@vuepress/core';
import type { SearchPluginOptions } from './searchPlugin';
export declare const prepareSearchIndex: ({ app, isSearchable, getExtraFields, }: {
    app: App;
    isSearchable: SearchPluginOptions['isSearchable'];
    getExtraFields: SearchPluginOptions['getExtraFields'];
}) => Promise<string>;
