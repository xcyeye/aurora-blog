interface SocialInfo {
	aHref?: string,
	isHome?: boolean,
	show?: boolean,
	sidebar?: boolean,
	symbol?: string,
	showImgSrc?: string
}
interface SiteSettingInfo {
	showRandomSay?: boolean,
	showPrintText?: boolean,
	socialsArr?: Array<SocialInfo>,
	/** pc端侧边栏最大宽度 */
	showWave?: boolean,
	randomSayApi?: {
		requestMethod: 'GET' | 'POST' | 'DELETE' | 'UPDATE',
		urlApi: string
	},
	customRandomSay?: {
		show: boolean,
		value: string
	},
	randomColor?: Array<string>,
	homePageLazyLoadingImg?: string
}
