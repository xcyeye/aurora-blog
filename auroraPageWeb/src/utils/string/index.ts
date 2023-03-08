const stringTool = () => {
  const haveLength = (obj: string | null | undefined): boolean => {
    if (obj === null || obj === undefined) {
			return false
		}
		return obj.length > 0
  }
	return {haveLength}
}

export const StringUtil = stringTool()
