interface Condition {
  /**
   * 开始时间
   */
  startTime?: string | null;

  /**
   * 结束时间
   */
  endTime?: string | null;

  /**
   * 根据唯一id
   */
  uid?: string | null;

  /**
   * 其他的uid
   */
  otherUid?: string | null;

  /**
   * 是否显示
   */
  show?: boolean | null;

  /**
   * 状态，比如发送状态，发布状态
   */
  status?: boolean | null;

  /**
   * 是否删除，并不是物理删除，而是逻辑删除
   */
  delete?: boolean | null;

  /**
   * 查询的关键词
   */
  keyword?: string | null;

  /**
   * 查询时页数
   */
  pageNum?: number | 1;

  /**
   * 查询数据时，每页返回多少条记录
   */
  pageSize?: number | 20;

  /**
   * 排序字段
   */
  orderBy: string | null;

  /**
   * 其他的字段
   */
  otherField?: unknown;
}

interface PageData<T> {
	/**
	 * 总数
	 */
	total?: number | null;
	/**
	 * 总页数
	 */
	pages?: number | null;

	result: Array<T> | null;
}

export { Condition, PageData };
