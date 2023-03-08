export interface LoadingEmptyWrapperProps {
  /** 是否加载 */
  loading: boolean;
  /** 是否为空 */
  empty?: boolean;
  /** 加载图标的大小 */
  loadingSize?: 'small' | 'medium' | 'large';
  /** 中间占位符的class */
  placeholderClass?: string;
  /** 空数据描述文本 */
  emptyDesc?: string;
  /** 图标的class */
  iconClass?: string;
  /** 描述文本的class */
  descClass?: string;
  /** 显示网络异常的重试点击按钮 */
  showNetworkReload?: boolean;
}
