interface User {

  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 用户简介
   */
  userSummary?: string | null;

  /**
   * 用户昵称
   */
  nickname?: string | null;

  /**
   * 用户性别
   */
  gender?: string | null;

  /**
   * 用户登录记录的uid
   */
  loginUid?: string | null;

  /**
   * 用户的站点配置uid
   */
  siteUid?: string | null;

  /**
   * 用户的头像地址
   */
  avatar?: string | null;

  /**
   * 用户的密码
   */
  password?: string | null;

  /**
   * 用户名
   */
  username?: string | null;

  /**
   * 用户的工作集合
   */
  profession?: string | null;

  /**
   * 此用户对应的邮箱uid
   */
  emailUid?: string | null;

  /**
   * 
   */
  createTime?: string | null;

  /**
   * 
   */
  updateTime?: string | null;

  /**
   * 是否删除 1：删除 
   */
  delete?: boolean | null;

  /**
   * 1: 账户被锁住，0：未被锁住
   */
  accountLock?: boolean | null;

  /**
   * 1: 邮箱已验证，0：邮箱未验证
   */
  verifyEmail?: boolean | null;

}

export { User }