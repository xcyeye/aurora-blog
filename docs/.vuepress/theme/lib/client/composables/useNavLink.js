import { useResolveRouteWithRedirect } from './useResolveRouteWithRedirect';
/**
 * Resolve NavLink props from string
 *
 * @example
 * - Input: '/接口.md'
 * - Output: { text: 'Home', link: '/' }
 */
export const useNavLink = (item) => {
    const resolved = useResolveRouteWithRedirect(item);
    return {
        text: resolved.meta.title || item,
        link: resolved.name === '404' ? item : resolved.fullPath,
    };
};
