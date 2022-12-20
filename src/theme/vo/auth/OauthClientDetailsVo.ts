interface OauthClientDetailsVo {
  /**
   * 客户端id
   */
  clientId?: string | null;

  /**
   * 资源的id，多个用逗号分隔
   */
  resourceIds?: string | null;

  /**
   * 客户端的秘钥
   */
  clientSecret?: string | null;

  /**
   * 客户端的权限，多个用逗号分隔
   */
  scope?: string | null;

  /**
   * 授权类型，五种，多个用逗号分隔
   */
  authorizedGrantTypes?: string | null;

  /**
   * 授权码模式的跳转uri
   */
  webServerRedirectUri?: string | null;

  /**
   * 权限，多个用逗号分隔
   */
  authorities?: string | null;

  /**
   * access_token的过期时间，单位毫秒，覆盖掉硬编码
   */
  accessTokenValidity?: number | null;

  /**
   * refresh_token的过期时间，单位毫秒，覆盖掉硬编码
   */
  refreshTokenValidity?: number | null;

  /**
   * 扩展字段，JSON
   */
  additionalInformation?: string | null;

  /**
   * 默认false，是否自动授权
   */
  autoapprove?: string | null;

  /**
   *
   */
  createTime?: string | null;

  /**
   *
   */
  updateTime?: string | null;
}

export { OauthClientDetailsVo };
