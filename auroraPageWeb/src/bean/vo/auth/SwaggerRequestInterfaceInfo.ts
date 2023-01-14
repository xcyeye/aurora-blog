interface InterfaceTag {
	name?: string | null
}

interface InterfaceRequestPathInfo {
	tags?: Array<string> | [];
	summary?: string | null;
	requestMethod?: string | null;
	requestPath?: string | null
}
interface SwaggerRequestInterfaceInfo {
	tags?: Array<InterfaceTag> | [];
	requestPaths?: Array<InterfaceRequestPathInfo> | []
}

export {SwaggerRequestInterfaceInfo, InterfaceRequestPathInfo, InterfaceTag}
