import {baseAxiosRequestConfig} from '@/constants';
import {request} from '@/service/request';
import type {Condition, PageData} from '@/bean/core/bean';
import type {SwaggerConfigVo} from "@/bean/vo/auth/SwaggerConfigVo";
import type {
	InterfaceRequestPathInfo,
	InterfaceTag,
	SwaggerRequestInterfaceInfo
} from "@/bean/vo/auth/SwaggerRequestInterfaceInfo";

/** 接口前缀 */
const apiPrefix = '/admin/user/';
type beanType = any;
type viewBeanType = any;

function _baseApi() {
	/**
	 * 插入一条数据
	 * @deprecated
	 * @param data
	 */
	function insertData(data: beanType) {
		return request.post<void>(`${apiPrefix}`, data, baseAxiosRequestConfig);
	}

	/**
	 * 插入多条数据
	 * @deprecated
	 * @param data
	 */
	function batchInsertData(data: Array<beanType>) {
		return request.post<void>(`${apiPrefix}`, data, baseAxiosRequestConfig);
	}

	/**
	 * 物理删除数据
	 * @deprecated
	 * @param data
	 */
	function physicalDeleteData(data: beanType) {
		return request.post<number>(`${apiPrefix}`, data, baseAxiosRequestConfig);
	}

	/**
	 * 逻辑删除数据
	 * @deprecated
	 * @param data
	 */
	function logicDeleteData(data: beanType) {
		return request.post<number>(`${apiPrefix}`, data, baseAxiosRequestConfig);
	}

	/**
	 * 更新数据
	 * @deprecated
	 * @param data
	 */
	function updateData(data: beanType) {
		return request.post<number>(`${apiPrefix}`, data, baseAxiosRequestConfig);
	}

	/**
	 * 批量更新数据
	 * @deprecated
	 * @param data
	 */
	function batchUpdateData(data: beanType) {
		return request.post<number>(`${apiPrefix}`, data, baseAxiosRequestConfig);
	}

	/**
	 * 根据条件查询满足要求的数据
	 * @deprecated
	 * @param data
	 */
	function queryListDataByCondition(data: Condition) {
		return request.post<PageData<viewBeanType>>(`${apiPrefix}`, data, baseAxiosRequestConfig);
	}

	/**
	 * 根据uid查询一条数据
	 * @deprecated
	 * @param data
	 */
	function queryOneDataByUid(data: beanType) {
		return request.post<viewBeanType>(`${apiPrefix}`, data, baseAxiosRequestConfig);
	}

	/**
	 * 根据条件查询一条数据
	 * @deprecated
	 * @param data
	 */
	function queryOneData(data: beanType) {
		return request.post<viewBeanType>(`${apiPrefix}`, data, baseAxiosRequestConfig);
	}

	function queryListSwaggerConfig() {
		return request.get<SwaggerConfigVo>(`/v3/api-docs/swagger-config`);
	}

	function querySingleSwaggerInterfaceInfo(serviceName: string): Promise<SwaggerRequestInterfaceInfo> {
		return new Promise((resolve, reject) => {
			request.get<any>(`/v3/api-docs/${serviceName}`).then(result => {
				if (!result.error) {
					// 获取tags
					const tags: Array<InterfaceTag> = result.data.tags
					const pathArr: Array<InterfaceRequestPathInfo> = []
					// 获取paths
					const paths = result.data.paths
					Object.keys(paths).forEach(requestPath => {

						// 遍历方法
						const methodObj = paths[requestPath]
						Object.keys(methodObj).forEach(method => {

							// 遍历方法中的对象
							const interfaceInfo = methodObj[method]
							const interfaceRequestPathInfo :InterfaceRequestPathInfo = {
								requestMethod: method,
								summary: interfaceInfo['summary'],
								tags: interfaceInfo['tags'],
								requestPath: requestPath
							}
							pathArr.push(interfaceRequestPathInfo)
						})
					})
					const swaggerRequestInterfaceInfo :SwaggerRequestInterfaceInfo = {
						tags: tags,
						requestPaths: pathArr
					}
					resolve(swaggerRequestInterfaceInfo)
				}else {
					resolve({})
				}
			})
		})
	}

	return {
		queryOneData,
		queryOneDataByUid,
		queryListDataByCondition,
		batchUpdateData,
		updateData,
		logicDeleteData,
		physicalDeleteData,
		insertData,
		batchInsertData,
		queryListSwaggerConfig,
		querySingleSwaggerInterfaceInfo
	};
}

export const interfaceInfoApi = _baseApi();
