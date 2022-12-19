interface LoginInfoVo {

  /**
   * 
   */
  uid?: string | null;

  /**
   * 登录的用户名
   */
  username?: string | null;

  /**
   * 登录地
   */
  loginLocation?: string | null;

  /**
   * 登录ip地址
   */
  loginIp?: string | null;

  /**
   * 登录的操作系统
   */
  operationSystemInfo?: string | null;

  /**
   * 创建时间
   */
  createTime?: string | null;

  /**
   * 最后更新时间
   */
  updateTime?: string | null;

  /**
   * 登录的状态 1：登录成功
   */
  status?: boolean | null;

  /**
   * 登录消息，记录登录异常等信息
   */
  message?: string | null;

}

export { LoginInfoVo }