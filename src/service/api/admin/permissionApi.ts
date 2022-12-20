import type { AxiosRequestConfig } from 'axios';
import { EnumContentType } from '@/enum';
import { request } from '@/service/request';
import type { Condition, PageData } from '@/theme/core/bean';
import type { Permission } from '@/theme/pojo/admin/Permission';
import type { PermissionVo } from '@/theme/vo/admin/PermissionVo';

/** 接口前缀 */
const apiPrefix = '/admin/permission';
type beanType = Permission;
type viewBeanType = PermissionVo;

const baseAxiosRequestConfig: AxiosRequestConfig = {
  headers: {
    'Content-Type': EnumContentType.json
  }
};

function _baseApi() {
  /**
   * 插入一条数据
   * @param data
   */
  function insertData(data: beanType) {
    return request.post<void>(`${apiPrefix}insertPermission`, data, baseAxiosRequestConfig);
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
    return request.post<number>(`${apiPrefix}physicalDeletePermission`, data, baseAxiosRequestConfig);
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
   * @param data
   */
  function updateData(data: beanType) {
    return request.post<number>(`${apiPrefix}updatePermission`, data, baseAxiosRequestConfig);
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
      `${apiPrefix}queryListPermissionByCondition`,
      data,
      baseAxiosRequestConfig
    );
  }

  /**
   * 根据uid查询一条数据
   * @param data
   */
  function queryOneDataByUid(data: beanType) {
    return request.post<viewBeanType>(`${apiPrefix}queryPermissionByUid`, data, baseAxiosRequestConfig);
  }

  /**
   * 根据条件查询一条数据
   * @deprecated
   * @param data
   */
  function queryOneData(data: beanType) {
    return request.post<any>(`${apiPrefix}`, data, baseAxiosRequestConfig);
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
    batchInsertData
  };
}

export const permissionApi = _baseApi();
