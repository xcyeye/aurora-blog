import type { LocaleConfig } from '@vuepress/shared';
import type { PropType } from 'vue';
export declare type SearchBoxLocales = LocaleConfig<{
    placeholder: string;
}>;
export declare const SearchBox: import("vue").DefineComponent<{
    locales: {
        type: PropType<SearchBoxLocales>;
        required: false;
        default: () => {};
    };
    hotKeys: {
        type: PropType<string[]>;
        required: false;
        default: () => never[];
    };
    maxSuggestions: {
        type: NumberConstructor;
        required: false;
        default: number;
    };
}, () => import("vue").VNode<import("vue").RendererNode, import("vue").RendererElement, {
    [key: string]: any;
}>, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, Record<string, any>, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<{
    locales?: unknown;
    hotKeys?: unknown;
    maxSuggestions?: unknown;
} & {
    hotKeys: string[];
    maxSuggestions: number;
    locales: SearchBoxLocales;
} & {}> & {}, {
    hotKeys: string[];
    maxSuggestions: number;
    locales: SearchBoxLocales;
}>;
