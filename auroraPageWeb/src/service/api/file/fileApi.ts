import { EnumContentType } from '@/enum';
import { baseAxiosRequestConfig } from '@/constants';
import { request } from '@/service/request';
import type { Condition, PageData } from '@/bean/core/bean';
import type { FileVo } from '@/bean/vo/file/fileVo';
import type { AuroraFile } from '@/bean/pojo/file/file';

/** 接口前缀 */
const apiPrefix = '/file/';
type beanType = AuroraFile;
type viewBeanType = FileVo;

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
    return request.post<number>(`${apiPrefix}physicalDeleteFileInfo`, data, baseAxiosRequestConfig);
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
    return request.post<number>(`${apiPrefix}updateFile`, data, baseAxiosRequestConfig);
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
    return request.post<PageData<viewBeanType>>(`${apiPrefix}queryListFileByCondition`, data, baseAxiosRequestConfig);
  }

  /**
   * 根据uid查询一条数据
   * @param data
   */
  function queryOneDataByUid(data: beanType) {
    return request.post<viewBeanType>(`${apiPrefix}queryFileByUid`, data, baseAxiosRequestConfig);
  }

  function queryOneData(data: beanType) {
    return request.post<viewBeanType>(`${apiPrefix}`, data, baseAxiosRequestConfig);
  }

	function queryListFileFormat(data: beanType) {
		return request.post<Array<string>>(`${apiPrefix}queryListFileFormat`, data, baseAxiosRequestConfig);
	}

	function querySpecifyFormatFiles(data: beanType) {
		return request.post<PageData<viewBeanType>>(`${apiPrefix}querySpecifyFormatFiles`, data, baseAxiosRequestConfig);
	}

  function deleteFile(data: beanType) {
    return request.post<number>(`${apiPrefix}deleteFile`, data, baseAxiosRequestConfig);
  }

  /**
   * 下载文件 此调用接口有错误
   * @param uid
   */
  function downloadFile(uid: string) {
    return request.post<void>(`${apiPrefix}/download/${uid}`, baseAxiosRequestConfig);
  }

  function singleUploadFile(file: File, storageMode: number, userUid: string, summary: string) {
    return request.post<viewBeanType>(
      `${apiPrefix}singleUploadFile`,
      { file, storageMode, userUid, summary },
      {
        headers: {
          'Content-Type': EnumContentType.formData
        }
      }
    );
  }

  function multiUploadFile(files: File[], storageMode: number, userUid: string, summary: string) {
    return request.post<Array<viewBeanType>>(
      `${apiPrefix}multiUploadFile`,
      { files, storageMode, userUid, summary },
      {
        headers: {
          'Content-Type': EnumContentType.formData
        }
      }
    );
  }

  /**
   * 这里需要修改，后端直接返回图片路径
   * @param file
   * @param storageMode
   */
  function typoraUploadFile(file: File, storageMode: number, userUid: string, summary: string) {
    return request.post<string>(
      `${apiPrefix}typoraUploadFile`,
      { file, storageMode, userUid, summary },
      {
        headers: {
          'Content-Type': EnumContentType.formData
        }
      }
    );
  }

  return {
    downloadFile,
    deleteFile,
    typoraUploadFile,
    multiUploadFile,
    singleUploadFile,
    queryOneData,
    queryOneDataByUid,
    queryListDataByCondition,
    batchUpdateData,
    updateData,
    logicDeleteData,
    physicalDeleteData,
    insertData,
    batchInsertData,
		queryListFileFormat,
		querySpecifyFormatFiles
  };
}

export const fileApi = _baseApi();
