import { inject } from 'vue';
import { useRoute } from 'vue-router';
import { usePageData } from '@vuepress/client';
import { isArray, isPlainObject, isString, resolveLocalePath, } from '@vuepress/shared';
import { useNavLink } from './useNavLink';
export const sidebarItemsSymbol = Symbol('sidebarItems');
/**
 * Inject sidebar items global computed
 */
export const useSidebarItems = () => {
    const sidebarItems = inject(sidebarItemsSymbol);
    if (!sidebarItems) {
        throw new Error('useSidebarItems() is called without provider.');
    }
    return sidebarItems;
};
/**
 * Resolve sidebar items global computed
 *
 * It should only be resolved and provided once
 */
export const resolveSidebarItems = (frontmatter, themeLocale) => {
    var _a, _b, _c, _d;
    // get sidebar config from frontmatter > themeConfig
    const sidebarConfig = (_b = (_a = frontmatter.sidebar) !== null && _a !== void 0 ? _a : themeLocale.sidebar) !== null && _b !== void 0 ? _b : 'auto';
    const sidebarDepth = (_d = (_c = frontmatter.sidebarDepth) !== null && _c !== void 0 ? _c : themeLocale.sidebarDepth) !== null && _d !== void 0 ? _d : 2;
    // resolve sidebar items according to the config
    if (frontmatter.home || sidebarConfig === false) {
        return [];
    }
    if (sidebarConfig === 'auto') {
        return resolveAutoSidebarItems(sidebarDepth);
    }
    if (isArray(sidebarConfig)) {
        return resolveArraySidebarItems(sidebarConfig, sidebarDepth);
    }
    if (isPlainObject(sidebarConfig)) {
        return resolveMultiSidebarItems(sidebarConfig, sidebarDepth);
    }
    return [];
};
/**
 * Util to transform page header to sidebar item
 */
export const headerToSidebarItem = (header, sidebarDepth) => ({
    text: header.title,
    link: `#${header.slug}`,
    children: headersToSidebarItemChildren(header.children, sidebarDepth),
});
export const headersToSidebarItemChildren = (headers, sidebarDepth) => sidebarDepth > 0
    ? headers.map((header) => headerToSidebarItem(header, sidebarDepth - 1))
    : [];
/**
 * Resolve sidebar items if the config is `auto`
 */
export const resolveAutoSidebarItems = (sidebarDepth) => {
    const page = usePageData();
    return [
        {
            text: page.value.title,
            children: headersToSidebarItemChildren(page.value.headers, sidebarDepth),
        },
    ];
};
/**
 * Resolve sidebar items if the config is an array
 */
export const resolveArraySidebarItems = (sidebarConfig, sidebarDepth) => {
    const route = useRoute();
    const page = usePageData();
    const handleChildItem = (item) => {
        var _a;
        let childItem;
        if (isString(item)) {
            childItem = useNavLink(item);
        }
        else {
            childItem = item;
        }
        if (childItem.children) {
            return {
                ...childItem,
                children: childItem.children.map((item) => handleChildItem(item)),
            };
        }
        // if the sidebar item is current page and children is not set
        // use headers of current page as children
        if (childItem.link === route.path) {
            // skip h1 header
            const headers = ((_a = page.value.headers[0]) === null || _a === void 0 ? void 0 : _a.level) === 1
                ? page.value.headers[0].children
                : page.value.headers;
            return {
                ...childItem,
                children: headersToSidebarItemChildren(headers, sidebarDepth),
            };
        }
        return childItem;
    };
    return sidebarConfig.map((item) => handleChildItem(item));
};
/**
 * Resolve sidebar items if the config is a key -> value (path-prefix -> array) object
 */
export const resolveMultiSidebarItems = (sidebarConfig, sidebarDepth) => {
    var _a;
    const route = useRoute();
    const sidebarPath = resolveLocalePath(sidebarConfig, route.path);
    const matchedSidebarConfig = (_a = sidebarConfig[sidebarPath]) !== null && _a !== void 0 ? _a : [];
    return resolveArraySidebarItems(matchedSidebarConfig, sidebarDepth);
};
