import { useResolveRouteWithRedirect } from './useResolveRouteWithRedirect';
import myData from'@temp/my-data'
/**
 * Resolve NavLink props from string
 *
 * @example
 * - Input: '/README.md'
 * - Output: { text: 'Home', link: '/' }
 */
export const useNavLink = (item) => {
    const resolved = useResolveRouteWithRedirect(item);
    if (resolved.name !== "404") {
        for (let i = 0; i < myData.length; i++) {
            if (resolved.fullPath === myData[i].path) {
                resolved.meta.title = myData[i].title
                return {
                    text: resolved.meta.title || item,
                    link: resolved.fullPath,
                }
            }
        }
    }else {
        return {
            text: resolved.meta.title || item,
            link: item,
        };
    }
};
