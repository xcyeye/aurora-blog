export const isNotEmptyObject = (obj: any): boolean => {
	if (typeof obj === "object") {
		if (Object.keys(obj).length > 0) {
			return true;
		}
	}
	return false;
}
