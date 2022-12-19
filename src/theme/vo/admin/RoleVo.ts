interface RoleVo {

  /**
   * 唯一uid，自增
   */
  uid?: string | null;

  /**
   * 角色的名称，不用添加ROLE_
   */
  name?: string | null;

  /**
   * 
   */
  createTime?: string | null;

  /**
   * 
   */
  updateTime?: string | null;

  /**
   * 用户的状态 1：已禁用 0：未禁用
   */
  status?: boolean | null;

}

export { RoleVo }