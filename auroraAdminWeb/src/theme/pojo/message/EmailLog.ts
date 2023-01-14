interface EmailLog {
  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 发送者的邮箱号
   */
  sender?: string | null;

  /**
   * 标题
   */
  subject?: string | null;

  /**
   * 内容
   */
  content?: string | null;

  /**
   * 接受者的邮箱号
   */
  receiver?: string | null;

  /**
   * 1:发送成功0：没有发送成功
   */
  send?: boolean | null;

  /**
   *
   */
  createTime?: string | null;

  /**
   *
   */
  updateTime?: string | null;
}

export { EmailLog };
