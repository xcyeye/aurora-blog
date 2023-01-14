interface Email {
  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 此条记录和用户表中的某个用户对应
   */
  userUid?: string | null;

  /**
   * 发送者邮件的主机
   */
  emailHost?: string | null;

  /**
   * 发送者邮件的密码，或者授权码
   */
  emailPassword?: string | null;

  /**
   * 发送者的协议
   */
  protocol?: string | null;

  /**
   * 邮箱号
   */
  email?: string | null;

  /**
   * 此邮件发送的端口
   */
  port?: number | null;

  /**
   *
   */
  createTime?: string | null;

  /**
   *
   */
  updateTime?: string | null;
}

export { Email };
