interface MessageLogVo {
  /**
   * 唯一uid
   */
  uid?: string | null;

  /**
   * 投递的消息
   */
  message?: string | null;

  /**
   * 交换机名称
   */
  exchange?: string | null;

  /**
   * 队列名称
   */
  queue?: string | null;

  /**
   * 绑定路由key
   */
  routingKey?: string | null;

  /**
   * 重试次数
   */
  tryCount?: number | null;

  /**
   * 1: 表示消费成功 0：表示消费失败
   */
  consumeStatus?: boolean | null;

  /**
   *
   */
  createTime?: string | null;

  /**
   *
   */
  updateTime?: string | null;

  /**
   * 交换机类型
   */
  exchangeType?: string | null;

  /**
   * 确认状态 1：应答了
   */
  ackStatus?: boolean | null;

  /**
   * 如果发生错误，则错误消息是什么
   */
  errorMessage?: string | null;
}

export { MessageLogVo };
