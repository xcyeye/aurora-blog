import {EnumFileStorageModeConstant} from "@/enum";

interface File {
  /**
   * 唯一uid 不能为null 主键
   */
  uid?: string | null;

  userUid?: string | null;

  /**
   * 文件创建时间 不能为null
   * <p>mysql -> datetime</p>
   */
  createTime?: string | null;

  /**
   * 此文件的删除状态 true：已删除 false：未删除
   */
  delete?: boolean | null;

  /**
   * 此文件的名字，含后缀
   * <p>length < 120</p>
   */
  fileName?: string | null;

  /**
   * 文件的大小 字节单位
   */
  size?: string | null;

  /**
   * 此文件的删除时间 可以为null
   * <p>mysql -> datetime</p>
   */
  deleteTime?: string | null;

  /**
   * 此文件的简介 可以为null
   * <p>length < 500</p>
   */
  summary?: string | null;

  /**
   * 此文件的访问路径
   */
  path?: string | null;

  /**
   * 文件的存储模式
   */
  storageMode?: EnumFileStorageModeConstant;

  /**
   * 此文件的存储位置
   */
  storagePath?: string | null;
}

export type AuroraFile = File
