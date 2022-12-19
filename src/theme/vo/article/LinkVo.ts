interface LinkVo {

  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 此条友情链接是哪个用户的
   */
  userUid?: string | null;

  /**
   * 此条友情链接属于哪个分类
   */
  categoryName?: string | null;

  /**
   * logo地址
   */
  linkLogo?: string | null;

  /**
   * 链接地址
   */
  linkUrl?: string | null;

  /**
   * 对方的名称
   */
  linkTitle?: string | null;

  /**
   * 描述信息
   */
  linkDescription?: string | null;

  /**
   * 对方站点的封面图
   */
  linkCover?: string | null;

  /**
   * 是否展示此条友情链接 1：展示 0：不展示
   */
  publish?: boolean | null;

  /**
   * 此友情链接对应的站长邮箱
   */
  email?: string | null;

  /**
   * 此友情链接对应的站长的qq号
   */
  qqNumber?: string | null;

  /**
   * 创建时间
   */
  createTime?: string | null;

  /**
   * 最后更新时间
   */
  updateTime?: string | null;

}

export { LinkVo }