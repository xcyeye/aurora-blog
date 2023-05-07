/** 手机号码正则 */
export const REGEXP_PHONE =
  /^[1](([3][0-9])|([4][01456789])|([5][012356789])|([6][2567])|([7][0-8])|([8][0-9])|([9][012356789]))[0-9]{8}$/;

/** 邮箱正则 */
export const REGEXP_EMAIL = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;

/** 密码正则(密码为6-18位数字/字符/符号的组合) */
export const REGEXP_PWD =
	/^\S*(?=\S{6,})(?=\S*\d)(?=\S*[A-Z])(?=\S*[a-z])(?=\S*[!@#$%^&*? ])\S*$/;

/** 6位数字验证码正则 */
export const REGEXP_CODE_SIX = /^\d{6}$/;

/** 4位数字验证码正则 */
export const REGEXP_CODE_FOUR = /^\d{4}$/;

/** url链接正则 */
export const REGEXP_URL =
  /^(http|https):\/\/([a-zA-Z0-9]+\.)*[a-zA-Z0-9][\-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][\-a-zA-Z0-9]{0,62})+(:\d+)?(\/([a-zA-Z0-9_\-\.~%!$&'()*+,;=:@]|(\/[\da-fA-F]{2})*)*)*(\?[a-zA-Z0-9_\-\.~%!$&'()*+,;=:@\/?]*)?(#[a-zA-Z0-9_\-\.~%!$&'()*+,;=:@\/?]*)?$/;

/** 用户名校验 */
export const REGEXP_USERNAME = /^[\w_]{5,15}$/

export const REGEXP_SWAGGER_CONFIG_INTERFACE = /^\/v3\/api-docs\/swagger-config/
export const REGEXP_SWAGGER_INTERFACE_INFO = /^\/v3\/api-docs/
