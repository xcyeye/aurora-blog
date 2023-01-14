interface RolePermissionDto {
	/**
	 * 角色名称
	 */
	roleName?: string | null;

	username?: string | null;

	/**
	 * 角色的状态
	 */
	roleStatus?: boolean | null;

	/**
	 * 权限名称
	 */
	permissionName?: string | null;

	/**
	 * 此角色可以允许访问的路径
	 */
	path?: string | null;

	/**
	 * 用户的userUid
	 */
	userUid?: string | null;
}
