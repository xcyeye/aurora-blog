import {getRandomNum, StringUtil} from "@/utils";

export async function setDefaultProperties<T>(originObj: T, defaultObj: T) {
  return new Promise((resolve, reject) => {
    Object.keys(defaultObj).forEach((v, index) => {
      if (originObj[v as keyof T] === null || originObj[v as keyof T] === undefined) {
        originObj[v as keyof T] = defaultObj[v as keyof T]
      }
      if (index === Object.keys(defaultObj).length - 1) {
        resolve(null)
      }
    })
  })
}

export const isNotEmptyObject = (obj: any): boolean => {
  if (typeof obj === "object") {
    if (Object.keys(obj).length > 0) {
      return true;
    }
  }
  return false;
}

export const setRandomInterface = (randomInterface: string | null | undefined, isCache: boolean = true): string => {
  if (!StringUtil.haveLength(randomInterface)) return ''
  let time = 0;
  time = getRandomNum(0, 1000000)
  time = time + getRandomNum(0, new Date().getTime())
  if (/.*&.*=.*/.test(randomInterface!)) {
    return `${randomInterface}&aurora_time=${time});`
  }else if (/.*?.*=.*/.test(randomInterface!)) {
    return `${randomInterface}&aurora_time=${time}`
  }
  return randomInterface!
}

export const getPaginationStartAndEnd = (currentPageNum: number, pageSize: number, isStart: boolean): number => {
  let start = (currentPageNum -1) * pageSize
  let end = start + pageSize
  if (isStart) return start
  return end
}