const stringTool = () => {
  const haveLength = (obj: string): boolean => {
    if (obj === null || obj === undefined) {
			return false
		}
		return obj.length > 0
  }
	return {haveLength}
}

export const StringUtil = stringTool()
