interface CommentDto {
  /**
   * 唯一uid 不能为null 主键
   */
  uid?: string | null;

  /**
   * 评论这的用户名 不能为null
   * <p>length < 15</p>
   */
  username?: string | null;

  /**
   * 逻辑删除
   */
  delete?: boolean | null;

  /**
   * 头像地址 可以为null
   * <p>length < 255</p>
   */
  avatar?: string | null;

  /**
   * 站点地址 不能为null
   * <p>length < 255</p>
   */
  site?: string | null;

  /**
   * 创建时间 不能为null
   * <p>mysql -> datetime</p>
   */
  createTime?: string | null;

  /**
   * 评论者的操作系统信息 可以为null
   * <p>length < 200</p>
   */
  operationSystemInfo?: string | null;

  /**
   * 此条评论是回复哪条评论的 不能为null
   */
  replyCommentUid?: string | null;

  /**
   * 评论的内容
   */
  content?: string | null;

  /**
   * 此评论对应的页面地址
   */
  path?: string | null;

  /**
   * 此评论对应此页面的用户
   */
  userUid?: string | null;

  sonCommentList?: Array<CommentDto> | null;

  nextCommentUidArray?: string | null;

  commentIp?: string | null;
}
