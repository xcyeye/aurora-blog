interface Comment {
  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 此评论的用户名
   */
  username?: string | null;

  /**
   * 此评论这的头像uid
   */
  avatar?: string | null;

  /**
   * 此评论者的博客地址
   */
  site?: string | null;

  /**
   * 此评论这的邮箱地址
   */
  email?: string | null;

  /**
   * 此评论的创时间
   */
  createTime?: string | null;

  /**
   * 此评论最后修改时间
   */
  updateTime?: string | null;

  /**
   * 评论者的ip地址
   */
  commentIp?: string | null;

  /**
   * 评论者的浏览器版本
   */
  operationSystemInfo?: string | null;

  /**
   * 是否显示此评论 1： 显示 0： 不显示
   */
  showComment?: boolean | null;

  /**
   * 此评论是回复哪个评论的
   */
  replyCommentUid?: string | null;

  /**
   * 如果此评论是回复某条评论，则1：已通知回复的那条评论的邮箱，0：未发送邮箱通知
   */
  emailNotice?: boolean | null;

  /**
   * 在哪个地址发布评论
   */
  path?: string | null;

  /**
   * 此评论的所有下一条集合
   */
  nextCommentUidArray?: string | null;

  /**
   * 评论内容
   */
  content?: string | null;

  /**
   * 此评论是属于哪个用户的
   */
  userUid?: string | null;

  /**
   * 1：删除 0：未删除
   */
  delete?: boolean | null;

  /**
   * 此评论是在哪种页面发布的
   */
  pageType?: string | null;
}

export { Comment };
