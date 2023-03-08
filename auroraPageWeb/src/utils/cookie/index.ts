import {StringUtil} from "@/utils";

export interface AuroraCookie {
  key: string,
  value: string
}
export const getCookie = (name: string, returnAll: boolean = false): Array<AuroraCookie> | AuroraCookie => {
  let cookies = document.cookie
  if (!StringUtil.haveLength(cookies)) return []
  return cookies.split(';')
    .map(cookie => cookie.replaceAll(' ', ''))
    .map(v => {
      let cookieInfo = v.split('=');
      const auroraCookie: AuroraCookie = {
        key: cookieInfo[0],
        value: cookieInfo[1]
      }
      return auroraCookie
    }).concat()
}

export const deleteCookie = (name: string | null | undefined) => {
  const auroraCookieArr = getCookie("") as Array<AuroraCookie>
  auroraCookieArr.forEach(v => {
    document.cookie = `${v.key}=${v.value};expires=-1`
  })
  // let exp = new Date();
  // exp.setTime(exp.getTime() - 1);
  // let cval = getCookie(name);
  // if (cval != null) document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}