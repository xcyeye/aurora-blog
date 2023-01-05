import { baseAxiosRequestConfig } from '@/constants';
import { request } from '@/service/request';
import type { Condition, PageData } from '@/theme/core/bean';
import {SiteSettingVo} from "@/theme/vo/admin/SiteSettingVo";

/** 接口前缀 */
const apiPrefix = '/admin/siteSetting/';
type beanType = SiteSetting;
type viewBeanType = SiteSettingVo;

function _baseApi() {
	/**
	 * 插入一条数据
	 * @param data
	 */
	function insertData(data: beanType) {
		return request.post<void>(`${apiPrefix}insertSiteSetting`, data, baseAxiosRequestConfig);
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
		return request.post<number>(`${apiPrefix}physicalDeleteSiteSetting`, data, baseAxiosRequestConfig);
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
		return request.post<number>(`${apiPrefix}updateSiteSetting`, data, baseAxiosRequestConfig);
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
		return request.post<PageData<viewBeanType>>(`${apiPrefix}queryListSiteSettingByCondition`, data, baseAxiosRequestConfig);
	}

	/**
	 * 根据uid查询一条数据
	 * @param data
	 */
	function queryOneDataByUid(data: beanType) {
		return request.post<viewBeanType>(`${apiPrefix}querySiteSettingByUid`, data, baseAxiosRequestConfig);
	}

	/**
	 * 根据userUid查询一条数据
	 * @param data
	 */
	function queryOneDataByUserUid(data: beanType) {
		return request.post<viewBeanType>(`${apiPrefix}querySiteSettingByUserUid`, data, baseAxiosRequestConfig);
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
		queryOneDataByUserUid
	};
}

export const siteSettingApi = _baseApi();
