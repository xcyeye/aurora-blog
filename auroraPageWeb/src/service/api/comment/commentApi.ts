import { baseAxiosRequestConfig } from '@/constants';
import { request } from '@/service/request';
import type { Condition, PageData } from '@/bean/core/bean';
import type { CommentVo } from '@/bean/vo/comment/CommentVo';
import type { Comment } from '@/bean/pojo/comment/Comment';

/** 接口前缀 */
const apiPrefix = '/comment/';
type beanType = Comment;
type viewBeanType = CommentVo;

function _baseApi() {
  /**
   * 插入一条数据
   * @param data
   */
  function insertData(data: beanType) {
    return request.post<void>(`${apiPrefix}insertComment`, data, baseAxiosRequestConfig);
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
    return request.post<number>(`${apiPrefix}physicalDeleteComment`, data, baseAxiosRequestConfig);
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
    return request.post<number>(`${apiPrefix}updateComment`, data, baseAxiosRequestConfig);
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
      `${apiPrefix}queryListCommentByCondition`,
      data,
      baseAxiosRequestConfig
    );
  }

  /**
   * 根据uid查询一条数据
   * @param data
   */
  function queryOneDataByUid(data: beanType) {
    return request.post<viewBeanType>(`${apiPrefix}queryCommentByUid`, data, baseAxiosRequestConfig);
  }

  /**
   * 根据条件查询一条数据
   * @deprecated
   * @param data
   */
  function queryOneData(data: beanType) {
    return request.post<viewBeanType>(`${apiPrefix}`, data, baseAxiosRequestConfig);
  }

  function queryListCommentByUidArr(data: beanType) {
    return request.post<ShowCommentVo>(`${apiPrefix}queryListCommentByUidArr`, data, baseAxiosRequestConfig);
  }

  function resendEmailNotice(data: beanType) {
    return request.post<number>(`${apiPrefix}resendEmail`, data, baseAxiosRequestConfig);
  }

  function queryTotalCount(data: beanType) {
    return request.post<number>(`${apiPrefix}queryTotalCommentCount`, data, baseAxiosRequestConfig)
  }

  return {
    queryTotalCount,
    queryOneData,
    queryOneDataByUid,
    queryListDataByCondition,
    batchUpdateData,
    updateData,
    logicDeleteData,
    physicalDeleteData,
    insertData,
    batchInsertData,
    queryListCommentByUidArr,
    resendEmailNotice,
  };
}

export const commentApi = _baseApi();
