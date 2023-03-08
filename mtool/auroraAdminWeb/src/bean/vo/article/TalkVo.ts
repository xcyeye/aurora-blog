interface TalkVo {
  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 发布此说说的用户uid
   */
  userUid?: string | null;

  /**
   * 此说说是否显示评论 1： 显示 0： 不显示
   */
  showComment?: boolean | null;

  /**
   * 此说说的评论uid集合
   */
  commentUids?: string | null;

  /**
   * 最后更新时间
   */
  updatedTime?: string | null;

  /**
   * 此说说发布时间
   */
  createdTime?: string | null;

  /**
   * 此说说的内容
   */
  content?: string | null;

  /**
   * 此说说标题
   */
  title?: string | null;

  /**
   * 1： 显示说说，0： 不显示说说
   */
  show?: boolean | null;

  /**
   * 此说说的点赞数
   */
  likeNumber?: number | null;

  /**
   * 此说说对应的图片uid集合
   */
	pictureSrcList?: string | null;

  /**
   * 1: 已删除
   */
  delete?: boolean | null;
}

export { TalkVo };
