interface RolePermission {
  /**
   *
   */
  uid?: string | null;

	roleUidArr?: Array<string> | [];

	userUidArr?: Array<string> | [];
	permissionUidArr?: Array<string> | [];

	originRoleUidArr?: Array<string> | [];
	newRoleUidArr?: Array<string> | [];
	originPermissionUidArr?: Array<string> | [];
	newPermissionUidArr?: Array<string> | [];

	usernameArr?: Array<string> | [];
	roleNameArr?: Array<string> | [];
	permissionPathArr?: Array<string> | [];
}

export { RolePermission };
