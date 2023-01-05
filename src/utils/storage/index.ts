export const createLocalStorage = (key: string, value: any) => {
  const valueStr = JSON.stringify(value, null, 2)
  // console.info(`存储值在本地 ${key} => ${valueStr}`);
  localStorage.setItem(key, valueStr)
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