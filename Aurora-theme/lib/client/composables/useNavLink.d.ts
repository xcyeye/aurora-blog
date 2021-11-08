import type { NavLink } from '../../shared';
/**
 * Resolve NavLink props from string
 *
 * @example
 * - Input: '/接口.md'
 * - Output: { text: 'Home', link: '/' }
 */
export declare const useNavLink: (item: string) => NavLink;
