interface AdminSidebar {

  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 前台导航的展示等级 比如0就是一级导航
   */
  level?: number | null;

  /**
   * 当前导航的父导航uid，也就是该导航显示在哪个导航下面
   */
  preSidebarUid?: string | null;

  /**
   * 导航的标题
   */
  title?: string | null;

  /**
   * 导航的对应地址
   */
  path?: string | null;

  /**
   * 1：链接到外部地址 0：链接就是此站点的，不在新标签也打开
   */
  external?: boolean | null;

  /**
   * 此导航的类名，用户icon
   */
  iconClassName?: string | null;

  /**
   * 此导航的顺序编号
   */
  sort?: number | null;

  /**
   * 
   */
  userUid?: string | null;

  /**
   * 
   */
  createTime?: string | null;

  /**
   * 
   */
  updateTime?: string | null;

}

export { AdminSidebar }