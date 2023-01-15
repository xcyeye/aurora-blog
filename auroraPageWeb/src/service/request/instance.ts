import type {AxiosError, AxiosInstance, AxiosRequestConfig} from 'axios';
import axios from 'axios';
import {REFRESH_TOKEN_CODE, REGEXP_SWAGGER_CONFIG_INTERFACE, REGEXP_SWAGGER_INTERFACE_INFO} from '@/config';
import {
    handleAxiosError,
    handleBackendError,
    handleResponseError,
    handleServiceResult,
    transformRequestData
} from '@/utils';
import {handleRefreshToken} from './helpers';
import {useAuthStore} from "@/stores";
import {isNotEmptyObject} from "@/utils/business";

/**
 * 封装axios请求类
 * @author Soybean<honghuangdc@gmail.com>
 */
export default class CustomAxiosInstance {
    instance: AxiosInstance;

    backendConfig: Service.BackendResultConfig;

    /**
     *
     * @param axiosConfig - axios配置
     * @param backendConfig - 后端返回的数据配置
     */
    constructor(
        axiosConfig: AxiosRequestConfig,
        backendConfig: Service.BackendResultConfig = {
            codeKey: 'code',
            dataKey: 'data',
            msgKey: 'message',
            successCode: 200,
            successFlag: 'success'
        }
    ) {
        this.backendConfig = backendConfig;
        this.instance = axios.create(axiosConfig);
        this.setInterceptor();
    }

    /** 设置请求拦截器 */
    setInterceptor() {
        this.instance.interceptors.request.use(
            async config => {
              const handleConfig = { ...config };
              if (handleConfig.headers) {
                // 数据转换
                // @ts-ignore
                const contentType = handleConfig.headers['Content-Type'] as string;
                handleConfig.data = await transformRequestData(handleConfig.data, contentType);
              }
              // 如果存在登录信息，则设置token
              if (useAuthStore().authInfo && isNotEmptyObject(useAuthStore().authInfo)) {
                // @ts-ignore
                handleConfig.headers.Authorization = `bearer ${useAuthStore().authInfo.access_token}`;
              }
              return handleConfig;
            },
            (axiosError: AxiosError) => {
                const error = handleAxiosError(axiosError);
                return handleServiceResult(error, null);
            }
        );
        this.instance.interceptors.response.use(
            // @ts-ignore
            async response => {
                const { status } = response;
                if (status === 200 || status < 300 || status === 304) {
                    let backend = response.data;
                    // 对swagger的请求进行封装
                    const requestUrl = response.config.url
                    if (REGEXP_SWAGGER_INTERFACE_INFO.test(requestUrl!) || REGEXP_SWAGGER_CONFIG_INTERFACE.test(requestUrl!)) {
                        backend = {
                            code: 200,
                            data: response.data,
                            message: 'swagger配置封装',
                            success: true
                        }
                    }
                    const { codeKey, dataKey, successFlag } = this.backendConfig;
                    // 请求成功
                    if (backend[successFlag] === true) {
                        return handleServiceResult(null, backend[dataKey]);
                    }

                    // token失效, 刷新token
                    if (REFRESH_TOKEN_CODE.includes(backend[codeKey])) {
                        const config = await handleRefreshToken(response.config);
                        if (config) {
                            return this.instance.request(config);
                        }
                    }

                    const error = handleBackendError(backend, this.backendConfig);
                    return handleServiceResult(error, null);
                }
                const error = handleResponseError(response);
                return handleServiceResult(error, null);
            },
            (axiosError: AxiosError) => {
                const error = handleAxiosError(axiosError);
                return handleServiceResult(error, null);
            }
        );
    }
}
