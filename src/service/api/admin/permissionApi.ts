import type {AxiosRequestConfig} from 'axios';
import {EnumContentType} from '@/enum';
import {request} from '@/service/request';
import type {Condition, PageData} from '@/theme/core/bean';
import {RolePermission} from "@/theme/pojo/admin/RolePermission";

/** 接口前缀 */
const apiPrefix = '/admin/permissionRelation/';
type beanType = RolePermission;
type viewBeanType = RolePermissionDto;

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

	function loadPermissionByUserUid(data: beanType) {
		return request.post<Array<viewBeanType>>(`${apiPrefix}loadPermissionByUserUid`, data, baseAxiosRequestConfig);
	}

	function loadAllRolePermission(data: beanType) {
		return request.post<Array<viewBeanType>>(`${apiPrefix}loadAllRolePermission`, data, baseAxiosRequestConfig);
	}

	function loadAllRoleByUsername(data: beanType) {
		return request.post<Array<viewBeanType>>(`${apiPrefix}loadAllRoleByUsername`, data, baseAxiosRequestConfig);
	}

	function loadPermissionByUsername(data: beanType) {
		return request.post<Array<viewBeanType>>(`${apiPrefix}loadPermissionByUsername`, data, baseAxiosRequestConfig);
	}

	function loadPermissionByRoleName(data: beanType) {
		return request.post<Array<viewBeanType>>(`${apiPrefix}loadPermissionByRoleName`, data, baseAxiosRequestConfig);
	}

	function queryRoleByPermissionPath(data: beanType) {
		return request.post<Array<viewBeanType>>(`${apiPrefix}queryRoleByPermissionPath`, data, baseAxiosRequestConfig);
	}

	function batchInsertUserRole(data: beanType) {
		return request.post<Array<viewBeanType>>(`${apiPrefix}batchInsertUserRole`, data, baseAxiosRequestConfig);
	}

	function batchDeleteUserRole(data: beanType) {
		return request.post<Array<viewBeanType>>(`${apiPrefix}batchDeleteUserRole`, data, baseAxiosRequestConfig);
	}

	function updateUserRole(data: beanType) {
		return request.post<Array<viewBeanType>>(`${apiPrefix}updateUserRole`, data, baseAxiosRequestConfig);
	}

	function batchDeleteRolePermission(data: beanType) {
		return request.post<Array<viewBeanType>>(`${apiPrefix}batchDeleteRolePermission`, data, baseAxiosRequestConfig);
	}

	function updateRolePermission(data: beanType) {
		return request.post<Array<viewBeanType>>(`${apiPrefix}updateRolePermission`, data, baseAxiosRequestConfig);
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
		loadPermissionByUserUid,
		updateRolePermission,
		batchDeleteRolePermission,
		updateUserRole,
		batchDeleteUserRole,
		batchInsertUserRole,
		queryRoleByPermissionPath,
		loadPermissionByRoleName,
		loadPermissionByUsername,
		loadAllRoleByUsername,
		loadAllRolePermission
  };
}

export const permissionApi = _baseApi();
