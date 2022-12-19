interface FileVo {
	/**
	 * 唯一uid
	 */
	uid?: string | null;

	/**
	 * 用户uid
	 */
	userUid?: string | null;

	/**
	 * 此文件的名称
	 */
	fileName?: string | null;

	/**
	 * 此文件的大小 字节为单位
	 */
	size?: number | null;

	/**
	 * 此文件的简介
	 */
	summary?: string | null;

	/**
	 * 此文件的存放路径，如果是对象存储，则表示objectName
	 */
	path?: string | null;

	/**
	 * 文件存储的模式
	 */
	storageMode?: FileStorageModeConstant;

	/**
	 * 存储的路径
	 */
	storagePath?: string | null;

	/**
	 * 1.: 已经删除
	 */
	delete?: boolean | null;

	/**
	 * 上传此文件的时间
	 */
	createTime?: string | null;

	/**
	 * 最后删除时间
	 */
	deleteTime?: string | null;
}

export {FileVo}
