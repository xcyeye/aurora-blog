interface AdminRouterVo {

  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 标题
   */
  title?: string | null;

  /**
   * 作为单级路由的父级路由布局组件
   */
  singleLayout?: string | null;

  /**
   * 需要登录权限
   */
  requiresAuth?: boolean | null;

  /**
   * 是否缓存
   */
  keepalive?: boolean | null;

  /**
   * 图标
   */
  icon?: string | null;

  /**
   * 使用本地svg作为的菜单和面包屑对应的图标(assets/svg-icon文件夹的的svg文件名)
   */
  localIcon?: string | null;

  /**
   * 是否在菜单中隐藏
   */
  hide?: boolean | null;

  /**
   * 外链链接
   */
  href?: string | null;

  /**
   * 路由顺序，可用于菜单的排序
   */
  order?: number | null;

  /**
   * 是否固定在tab卡不可关闭
   */
  affix?: boolean | null;

  /**
   *
   */
  createTime?: string | null;

  /**
   *
   */
  updateTime?: string | null;

  /**
   * 路由名称
   */
  name?: string | null;

  /**
   * 路由路径
   */
  path?: string | null;

  /**
   * 组件
   */
  component?: string | null;

  /**
   * 该路由的子路由集合，使用,分隔开
   */
  sonRouterUids?: string | null;

  /**
   * 父路由uid
   */
  parentRouterUid?: string | null;

}

export { AdminRouterVo }
