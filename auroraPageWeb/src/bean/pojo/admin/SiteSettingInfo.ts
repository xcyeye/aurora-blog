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

interface FooterInfo {
	enable?: boolean,
	footInfo?: Array<string>,
	isShowThemeCopyright?: boolean,
	isShowRunTime?: boolean,
	startRunTime?: string,
	prefixRuntime?: string
}

interface AboutInfo {
	bar?: boolean,
	title?: string,
	describeArr?: Array<string>,
	tagArr?: Array<string>,
	showTag?: boolean,
	barDescArr?: Array<{
		name?: string,
		score?: number
	}>

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
	// 最新文章数量，默认为6
	latestPageSize?: number,
	// 侧边栏的描述
	sidebarPersonDesc?: string,
	githubUrl?: string,
	footerInfo?: FooterInfo,
	showAboutPageBubble?: boolean,
	aboutInfoArr?: Array<AboutInfo>
}
