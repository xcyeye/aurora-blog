interface Tag {
  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 此标签的标题
   */
  title?: string | null;

  /**
   * 此标签的简介
   */
  summary?: string | null;

  /**
   * 创建时间
   */
  createdTime?: string | null;

  /**
   * 最后修改时间
   */
  updatedTime?: string | null;

  /**
   * 此类别的封面图uid
   */
  coverUrl?: string | null;

  /**
   * 1: 删除，0：未删除
   */
  delete?: boolean | null;

  /**
   * 用户的userUid
   */
  userUid?: string | null;
}

export { Tag };
