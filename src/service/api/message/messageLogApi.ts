import { baseAxiosRequestConfig } from "@/constants";
import { request } from "@/service/request";
import { Condition, PageData } from "@/theme/core/bean";
import {MessageLog} from "@/theme/pojo/message/MessageLog";
import {MessageLogVo} from "@/theme/vo/message/MessageLogVo";

/** 接口前缀 */
const apiPrefix = "/message/messageLog/";
type beanType = MessageLog
type viewBeanType = MessageLogVo

function _baseApi() {

	/**
	 * 插入一条数据
	 * @param data
	 */
	function insertData(data: beanType){
		return request.post<void>(apiPrefix + "insertMessageLog", data, baseAxiosRequestConfig);
	}

	/**
	 * 插入多条数据
	 * @deprecated
	 * @param data
	 */
	function batchInsertData(data: Array<beanType>) {
		return request.post<void>(apiPrefix + "", data, baseAxiosRequestConfig);
	}

	/**
	 * 物理删除数据
	 * @param data
	 */
	function physicalDeleteData(data: beanType) {
		return request.post<number>(apiPrefix + "physicalDeleteMessageLog", data, baseAxiosRequestConfig);
	}

	/**
	 * 逻辑删除数据
	 * @param data
	 */
	function logicDeleteData(data: beanType) {
		return request.post<number>(apiPrefix + "", data, baseAxiosRequestConfig);
	}

	/**
	 * 更新数据
	 * @param data
	 */
	function updateData(data: beanType) {
		return request.post<number>(apiPrefix + "updateMessageLog", data, baseAxiosRequestConfig);
	}

	/**
	 * 批量更新数据
	 * @deprecated
	 * @param data
	 */
	function batchUpdateData(data: beanType) {
		return request.post<number>(apiPrefix + "", data, baseAxiosRequestConfig);
	}

	/**
	 * 根据条件查询满足要求的数据
	 * @param data
	 */
	function queryListDataByCondition(data: Condition) {
		return request.post<PageData<viewBeanType>>(apiPrefix + "queryListMessageLogByCondition", data, baseAxiosRequestConfig);
	}

	/**
	 * 根据uid查询一条数据
	 * @param data
	 */
	function queryOneDataByUid(data: beanType) {
		return request.post<viewBeanType>(apiPrefix + "queryMessageLogByUid", data, baseAxiosRequestConfig);
	}

	/**
	 * 根据条件查询一条数据
	 * @deprecated
	 * @param data
	 */
	function queryOneData(data: beanType) {
		return request.post<viewBeanType>(apiPrefix + "", data, baseAxiosRequestConfig);
	}

	function resendRabbitMqMessage(data: beanType) {
		return request.post<void>(apiPrefix + "resendRabbitMqMessage", data, baseAxiosRequestConfig);
	}

	return { resendRabbitMqMessage, queryOneData, queryOneDataByUid, queryListDataByCondition, batchUpdateData, updateData, logicDeleteData, physicalDeleteData, insertData, batchInsertData }
}

export const messageLogApi = _baseApi();
