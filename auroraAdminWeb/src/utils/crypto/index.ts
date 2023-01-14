import CryptoJS from 'crypto-js';

const CryptoSecret = '__CryptoJS_Secret__';

/**
 * 加密数据
 * @param data - 数据
 */
export function encrypto(data: any) {
	const dataTemp = data['value'];
	if (dataTemp instanceof Map) {
		const obj: object = {}
		let num: number = 0
		for (let[k,v] of dataTemp) {
			// @ts-ignore
			obj[k] = v
			num = num + 1
			if (num === dataTemp.size) {
				data.value = obj
				const newData = JSON.stringify(data);
				return CryptoJS.AES.encrypt(newData, CryptoSecret).toString();
			}
		}
	}else {
		const newData = JSON.stringify(data);
		return CryptoJS.AES.encrypt(newData, CryptoSecret).toString();
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
