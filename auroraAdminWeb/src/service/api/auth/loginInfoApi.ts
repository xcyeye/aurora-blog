import { baseAxiosRequestConfig } from '@/constants';
import { request } from '@/service/request';
import type { Condition, PageData } from '@/theme/core/bean';
import type { LoginInfo } from '@/theme/pojo/auth/LoginInfo';
import type { LoginInfoVo } from '@/theme/vo/auth/LoginInfoVo';

/** 接口前缀 */
const apiPrefix = '/login/loginInfo/';
type beanType = LoginInfo;
type viewBeanType = LoginInfoVo;

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
   * @param data
   */
  function physicalDeleteData(data: beanType) {
    return request.post<number>(`${apiPrefix}physicalDeleteLoginInfo`, data, baseAxiosRequestConfig);
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
   * @param data
   */
  function queryListDataByCondition(data: Condition) {
    return request.post<PageData<viewBeanType>>(
      `${apiPrefix}queryListLoginInfoByCondition`,
      data,
      baseAxiosRequestConfig
    );
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

  function queryLoginInfoByUsername(data: beanType) {
    return request.post<viewBeanType>(`${apiPrefix}queryLoginInfoByUsername`, data, baseAxiosRequestConfig);
  }

  function batchDeleteLoginInfoByUid(data: beanType) {
    return request.post<number>(`${apiPrefix}batchDeleteLoginInfoByUid`, data, baseAxiosRequestConfig);
  }

  return {
    queryLoginInfoByUsername,
    batchDeleteLoginInfoByUid,
    queryOneData,
    queryOneDataByUid,
    queryListDataByCondition,
    batchUpdateData,
    updateData,
    logicDeleteData,
    physicalDeleteData,
    insertData,
    batchInsertData
  };
}

export const loginInfoApi = _baseApi();
