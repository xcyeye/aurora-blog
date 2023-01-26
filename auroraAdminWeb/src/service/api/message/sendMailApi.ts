import { baseAxiosRequestConfig } from '@/constants';
import { request } from '@/service/request';
import type { Condition, PageData } from '@/bean/core/bean';

/** 接口前缀 */
const apiPrefix = '/message/sendMail/';
type beanType = SendMailPojo;
type viewBeanType = void;

function _baseApi() {
  /**
   * 插入一条数据
   * @param data
   */
  function insertData(data: beanType) {
    return request.post<void>(`${apiPrefix}`, data, baseAxiosRequestConfig);
  }

  function simpleText(data: beanType) {
    return request.post<void>(`${apiPrefix}simpleText`, data, baseAxiosRequestConfig);
  }

	function customMail(data: beanType) {
		return request.post<void>(`${apiPrefix}customMail`, data, baseAxiosRequestConfig);
	}

	function resendCustomMail(data: beanType) {
		return request.post<void>(`${apiPrefix}resendCustomMail`, data, baseAxiosRequestConfig);
	}

	function batchInsertData(data: Array<beanType>) {
		return request.post<void>(`${apiPrefix}`, data, baseAxiosRequestConfig);
	}

  /**
   * 物理删除数据
   * @param data
   */
  function physicalDeleteData(data: beanType) {
    return request.post<number>(`${apiPrefix}`, data, baseAxiosRequestConfig);
  }

  /**
   * 逻辑删除数据
   * @param data
   */
  function logicDeleteData(data: beanType) {
    return request.post<number>(`${apiPrefix}`, data, baseAxiosRequestConfig);
  }

  /**
   * 更新数据
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
   * @param data
   */
  function queryListDataByCondition(data: Condition) {
    return request.post<PageData<viewBeanType>>(`${apiPrefix}`, data, baseAxiosRequestConfig);
  }

  /**
   * 根据uid查询一条数据
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
		simpleText,
		customMail,
		resendCustomMail
  };
}

export const sendMailApi = _baseApi();
