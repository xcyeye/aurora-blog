interface SysSettingVo {

  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 参数编码
   */
  paramCode?: string | null;

  /**
   * 参数名称
   */
  paramName?: string | null;

  /**
   * 参数值
   */
  paramValue?: string | null;

  /**
   * 创建时间
   */
  createTime?: string | null;

  /**
   * 最后更新时间
   */
  updateTime?: string | null;

}

export { SysSettingVo }