interface Site {

  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 站点的icon地址
   */
  siteIcon?: string | null;

  /**
   * 站点的标题 浏览器顶部部分
   */
  title?: string | null;

  /**
   * 站点的前台logo文字
   */
  logoTitle?: string | null;

  /**
   * 站点的logo地址
   */
  siteLogo?: string | null;

  /**
   * 站点前台中间部分logo
   */
  siteCenterLogo?: string | null;

  /**
   * 站点额外的head信息，直接传入<script/>这种
   */
  additionalHead?: string | null;

  /**
   * 此站点信息属于哪个用户
   */
  userUid?: string | null;

  /**
   * 创建时间
   */
  createTime?: string | null;

  /**
   * 最后更新时间
   */
  updateTime?: string | null;

  /**
   * 0:不删除 1： 删除
   */
  delete?: boolean | null;

}

export { Site }