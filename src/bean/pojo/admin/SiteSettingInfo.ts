interface SocialInfo {
	aHref?: string,
	isHome?: boolean,
	show?: boolean,
	sidebar?: boolean,
	symbol?: string,
	showImgSrc?: string
}

interface FriendLinkSiteInformation {
	title?: string,
	url?: string,
	logo?: string,
	describe?: string,
	cover?: string,
	contact?: string,
	otherDescribe?: Array<string>
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
	homePageLazyLoadingImg?: string,
	friendLinkSiteInformation?: FriendLinkSiteInformation,
	// 手机端，是否在页面的底部显示侧边栏列表，默认开启，如果需要开启，请将此值设置为FALSE
	mobilePageSidebar?: boolean,

}
