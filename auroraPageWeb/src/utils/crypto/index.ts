import CryptoJS from 'crypto-js';

const CryptoSecret = '__CryptoJS_Secret__';

/**
 * 加密数据
 * @param data - 数据
 */
export function encrypto(data: any) {
	if (data instanceof Map) {
		if (data.size === 0) {
			return JSON.stringify({})
		}
		const obj: object = {}
		let num: number = 0
		for (let[k,v] of data) {
			// @ts-ignore
			obj[k] = v
			num = num + 1
			if (num === data.size) {
				const newDataJson = JSON.stringify(obj);
				// return CryptoJS.AES.encrypt(newData, CryptoSecret).toString();
				return newDataJson
			}
		}
	}else {
		const newData = JSON.stringify(data);
		// return CryptoJS.AES.encrypt(newData, CryptoSecret).toString();
		return newData
	}
}

/**
 * 解密数据
 * @param cipherText - 密文
 */
export function decrypto(cipherText: string) {
  const bytes = CryptoJS.AES.decrypt(cipherText, CryptoSecret);
  const originalText = bytes.toString(CryptoJS.enc.Utf8);
  if (originalText) {
    return JSON.parse(originalText);
  }
  return null;
}
