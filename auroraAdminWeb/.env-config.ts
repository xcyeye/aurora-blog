/** 请求服务的环境配置 */
type ServiceEnv = Record<ServiceEnvType, ServiceEnvConfig>;

/** 不同请求服务的环境配置 */
const serviceEnv: ServiceEnv = {
  dev: {
    url: 'https://api.xcye.xyz',
    urlPattern: '/aurora-api',
    secondUrl: 'http://localhost:7777',
    secondUrlPattern: '/second-aurora-api'
  },
  test: {
    url: 'http://localhost:8080',
    urlPattern: '/aurora-api',
    secondUrl: 'http://localhost:8081',
    secondUrlPattern: '/second-aurora-api'
  },
  prod: {
    url: 'https://api.xcye.xyz',
    urlPattern: '/aurora-api',
    secondUrl: 'https://api.xcye.xyz',
    secondUrlPattern: '/second-aurora-api'
  }
};

/**
 * 获取当前环境模式下的请求服务的配置
 * @param env 环境
 */
export function getServiceEnvConfig(env: ImportMetaEnv) {
  const { VITE_SERVICE_ENV = 'dev' } = env;

  const config = serviceEnv[VITE_SERVICE_ENV];

  return config;
}
