/**
 * Base nav item, displayed as text
 */
export interface NavItem {
    text: string;
    ariaLabel?: string;
}
/**
 * Base nav group, has nav items children
 */
export interface NavGroup<T> extends NavItem {
    children: T[];
}
/**
 * Props for `<NavLink>`
 */
export interface NavLink extends NavItem {
    link: string;
    rel?: string;
    target?: string;
    activeMatch?: string;
}
/**
 * Navbar types
 */
export declare type NavbarItem = NavLink;
export declare type NavbarGroup = NavGroup<NavbarGroup | NavbarItem | string>;
export declare type NavbarConfig = (NavbarItem | NavbarGroup | string)[];
export declare type ResolvedNavbarItem = NavbarItem | NavGroup<ResolvedNavbarItem>;
/**
 * Sidebar types
 */
export declare type SidebarItem = NavItem & Partial<NavLink> & Partial<Pick<NavGroup<NavLink | SidebarItem | string>, 'children'>>;
export declare type SidebarConfigArray = (SidebarItem | string)[];
export declare type SidebarConfigObject = Record<string, SidebarConfigArray>;
export declare type SidebarConfig = SidebarConfigArray | SidebarConfigObject;
export declare type ResolvedSidebarItem = NavItem & Partial<NavLink> & Partial<Pick<NavGroup<ResolvedSidebarItem>, 'children'>>;
