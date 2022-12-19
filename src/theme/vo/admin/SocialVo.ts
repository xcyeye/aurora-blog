interface SocialVo {

  /**
   * 唯一uid,自增
   */
  uid?: string | null;

  /**
   * 社交名称
   */
  socialName?: string | null;

  /**
   * 此社交图标的地址
   */
  socialIcon?: string | null;

  /**
   * 此社交的链接
   */
  socialUrl?: string | null;

  /**
   * 1： 显示此社交 0： 不显示
   */
  show?: boolean | null;

  /**
   * 此社交属于哪个用户
   */
  userUid?: string | null;

  /**
   * 创建时间
   */
  createTime?: string | null;

  /**
   * 最后更新时间
   */
  updateTime?: string | null;

  /**
   * 1: 删除 0：不删除
   */
  delete?: boolean | null;

}

export { SocialVo }