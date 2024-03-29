import {StringUtil} from "@/utils";
import {REGEXP_URL} from "@/config";

export const isImage = (fileName: string): boolean => {
	return /\.(png|jpg|jpeg|gif|webp)$/.test(fileName);
}

export const isVideo = (fileName: string): boolean => {
	return /\.(mp4|wmv|avi|mov|webp)$/.test(fileName);
}

export const removeDuplicateElement = <T>(arr :Array<T>): Array<T> => {
	const set: Set<T> = new Set<T>();
	if (arr.length === 0) {
		return new Array<T>()
	}else {
		arr.forEach(v => set.add(v))
		return Array.from(set)
	}
}
export const getRandomNum = (min: number,max: number): number => {
	const range = max - min;
	const rand = Math.random();
	return(min + Math.round(rand * range));
}

export const getRandomTagType = (): string => {
	const typeArr: string[] = ['primary' , 'info' , 'success' , 'warning' , 'error']
	return typeArr[getRandomNum(0, 4)]
}

export const getFileFormat = (fileName: string | null | undefined): string => {
	if (fileName?.indexOf(".") === -1) return '未知格式'
	return fileName!.substring(fileName!.lastIndexOf(".") + 1, fileName!.length);
}

export const getFileSize = (size: number | null | undefined): string => {
	if (!size) {
		return '0 KB'
	}
	if ((size / 1024 / 1024) < 1) {
		return (size / 1024).toFixed(2) + " KB"
	}
	if ((size / 1024 / 1024) > 1024) {
		return (size / 1024 / 1024 / 1024).toFixed(2) + " GB"
	}

	return (size / 1024 / 1024).toFixed(2) + " MB"
}

export const getLocalTime = (time: Date | number | string, isToDay: boolean = true) => {
	if (time === undefined) {
		//没有时间戳
		return ''
	}

	if (time === 0) {
		//没有时间戳
		return ''
	}

	let date = new Date(time);
	let day = date.getDate()
	let year = date.getFullYear()
	let month = date.getMonth() + 1
	let hours = date.getHours()
	let min = date.getMinutes()
	let sec = date.getSeconds()
	if (isToDay) {
		return year + "-" + month + "-" + day + " "
	}else {
		return year + "-" + month + "-" + day + " " + hours + ":" + min + ":" + sec
	}
}

export const parseTime = (time: string): Date => {
	if (!StringUtil.haveLength(time)) new Date()
	return new Date(time)
}

export const hexToRgb = (hex: string): {r: number, g:number , b: number} | null => {
	let result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
	return result ? {
		r: parseInt(result[1], 16),
		g: parseInt(result[2], 16),
		b: parseInt(result[3], 16)
	} : null;
}

export const getRealImageUrl = (host: string | null | undefined, filePath: string | null | undefined): string => {
	if (REGEXP_URL.test(filePath!)) return filePath!
	if (!StringUtil.haveLength(host)) return filePath!
	if (host!.endsWith("/")) {
		host = host!.substring(0, host!.length - 1)
	}
	return host + filePath!
}