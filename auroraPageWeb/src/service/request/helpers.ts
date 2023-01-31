import type { AxiosRequestConfig } from 'axios';

/**
 * 刷新token
 * @param axiosConfig - token失效时的请求配置
 */
export async function handleRefreshToken(axiosConfig: AxiosRequestConfig) {
  // TODO 处理token过期，目前先清楚缓存
  localStorage.clear()
  window.$message?.error('登录信息已过期，请重新登录')
  if (window) {
    window.location.reload()
  }
  return null;
}
