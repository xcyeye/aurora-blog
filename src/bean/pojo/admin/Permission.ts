interface Permission {
  /**
   * 唯一uid，自增
   */
  uid?: string | null;

  /**
   * 权限的名称
   */
  name?: string | null;

  /**
   * 权限的地址，可以是组件的名称
   */
  path?: string | null;

  /**
   *
   */
  createTime?: string | null;

  /**
   *
   */
  updateTime?: string | null;

	permissionList?: Array<Permission> | []
}

export { Permission };
