interface SwaggerConfigUrlInfo {
	name?: string | null;
	url?: string | null
}
interface SwaggerConfigVo {
	configUrl?: string | null;
	oauth2RedirectUrl?: string | null;
	validatorUrl?: string | null;
	urls?: Array<SwaggerConfigUrlInfo> | []
}

export {SwaggerConfigVo, SwaggerConfigUrlInfo}
