import {encrypto} from "@/utils/crypto";

export const createLocalStorage = (key: string, value: any) => {
  // console.info(`存储值在本地 ${key} => ${valueStr}`);
  const json = encrypto(value);
  localStorage.setItem(key, json as string)
}

export const cleanLocalStorage = (key: string) => {
  // console.info(`清除本地缓存 ${key}`);
  localStorage.removeItem(key)
}

export const getLocalStorage = (key: string) => {
  let value = localStorage.getItem(key);
  if (value) {
    return JSON.parse(value);
  }else {
    // @ts-ignore
    return {}
  }
}