interface Article {
  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 文章是否展示评论，0：否，1：是
   */
  showComment?: boolean | null;

  /**
   * 文章有附件的话，附件的uid集合
   */
  accessoryUids?: string | null;

  /**
   * 文章类别uid集合
   */
  categoryNames?: string | null;

  /**
   * 文章标签uid集合
   */
  tagNames?: string | null;

  /**
   * 文章是否发布，1：发布，0：不发布
   */
  publish?: boolean | null;

  /**
   * 发布此篇文章的用户uid
   */
  userUid?: string | null;

  /**
   * 是否原创，1：原创 0：不是原创
   */
  originalArticle?: boolean | null;

  /**
   * 如果是原创，则原创链接
   */
  originalArticleUrl?: string | null;

  /**
   * 文章封面对应的图片uid
   */
  coverPictureUrl?: string | null;

  /**
   * 文章内容
   */
  content?: string | null;

  /**
   * 文章标题
   */
  title?: string | null;

  /**
   * 文章简介
   */
  summary?: string | null;

  /**
   * 是否定时发布 0： 不定时，1：定时
   */
  timing?: boolean | null;

  /**
   * 定时发布时间
   */
  timingPublishTime?: string | null;

  /**
   * 文章点赞数
   */
  likeNumber?: number | null;

  /**
   * 阅读量
   */
  readNumber?: number | null;

  /**
   * 此篇文章对应的评论uid集合，只需要设置所有父评论的uid
   */
  commentUids?: string | null;

  /**
   * 此篇文章最后修改的时间
   */
  updateTime?: string | null;

  /**
   * 文章发布时间
   */
  createTime?: string | null;

  /**
   * 是否删除 逻辑删除 1： 已删除
   */
  delete?: boolean | null;
}

export { Article };
