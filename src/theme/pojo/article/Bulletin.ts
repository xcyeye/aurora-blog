interface Bulletin {

  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 公告的标题
   */
  title?: string | null;

  /**
   * 公告内容
   */
  content?: string | null;

  /**
   * 公告创建时间
   */
  createTime?: string | null;

  /**
   * 公告最后修改时间
   */
  updateTime?: string | null;

  /**
   * 发布此公告的用户uid
   */
  userUid?: string | null;

  /**
   * 1: 显示公告 0： 不显示该公告
   */
  show?: boolean | null;

  /**
   * 1：定时发布 0： 不定时发布公告
   */
  timing?: boolean | null;

  /**
   * 定时发布公告的时间
   */
  timingPublishTime?: string | null;

  /**
   * 1:删除 0：未删除
   */
  delete?: boolean | null;

}

export { Bulletin }