import { baseAxiosRequestConfig } from '@/constants';
import { request } from '@/service/request';
import type { Condition, PageData } from '@/bean/core/bean';
import type { User } from '@/bean/pojo/admin/User';
import type { UserVo } from '@/bean/vo/admin/UserVo';

/** 接口前缀 */
const apiPrefix = '/admin/user/';
type beanType = User;
type viewBeanType = UserVo;

function _baseApi() {
  /**
   * 插入一条数据
   * @param data
   */
  function insertData(data: beanType) {
    return request.post<void>(`${apiPrefix}insertUser`, data, baseAxiosRequestConfig);
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
    return request.post<number>(`${apiPrefix}physicalDeleteUser`, data, baseAxiosRequestConfig);
  }

  /**
   * 逻辑删除数据
   * @param data
   */
  function logicDeleteData(data: beanType) {
    return request.post<number>(`${apiPrefix}logicDeleteUser`, data, baseAxiosRequestConfig);
  }

  /**
   * 更新数据
   * @param data
   */
  function updateData(data: beanType) {
    return request.post<number>(`${apiPrefix}updateUser`, data, baseAxiosRequestConfig);
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
    return request.post<PageData<viewBeanType>>(`${apiPrefix}queryListUserByCondition`, data, baseAxiosRequestConfig);
  }

  /**
   * 根据uid查询一条数据
   * @param data
   */
  function queryOneDataByUid(data: beanType) {
    return request.post<viewBeanType>(`${apiPrefix}queryUserByUid`, data, baseAxiosRequestConfig);
  }

  /**
   * 根据条件查询一条数据
   * @deprecated
   * @param data
   */
  function queryOneData(data: beanType) {
    return request.post<viewBeanType>(`${apiPrefix}queryOneData`, data, baseAxiosRequestConfig);
  }

  function queryUserByUsername(data: beanType) {
    return request.post<viewBeanType>(`${apiPrefix}queryUserByUsername`, data, baseAxiosRequestConfig);
  }

  /**
   * 绑定邮箱
   * @param data 邮箱号
   */
  function bindingEmail(data: beanType) {
    return request.post<number>(`${apiPrefix}bindingEmail`, data, baseAxiosRequestConfig);
  }

  /**
   * 根据用户名查询密码
   * @param data
   */
  function queryUserByUsernameContainPassword(data: beanType) {
    return request.post<viewBeanType>(`${apiPrefix}queryUserByUsernameContainPassword`, data, baseAxiosRequestConfig);
  }

  /**
   * 更新密码
   * @param data
   */
  function updatePassword(data: beanType) {
    return request.post<number>(`${apiPrefix}updatePassword`, data, baseAxiosRequestConfig);
  }

	function forgotPassword(data: beanType) {
		return request.post<number>(`${apiPrefix}forgotPassword`, data, baseAxiosRequestConfig);
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
    bindingEmail,
    updatePassword,
    queryUserByUsernameContainPassword,
		forgotPassword,
    queryUserByUsername
  };
}

export const userApi = _baseApi();
