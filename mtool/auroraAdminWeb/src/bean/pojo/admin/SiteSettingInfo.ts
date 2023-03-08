interface SocialInfo {
	aHref?: string,
	isHome?: boolean,
	show?: boolean,
	sidebar?: boolean,
	icon?: string,
	color?: string,
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
	prefixRuntime?: string,
	backgroundImage?: string
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
	showWave?: boolean,
	// 不要
	showAboutPageBubble?: boolean,
	// 新增
	showTopImgBubble?: boolean,

	// 手机端，是否在页面的底部显示侧边栏列表，默认开启，如果需要开启，请将此值设置为FALSE
	mobilePageSidebar?: boolean,

	// 最新文章数量，默认为6
	latestPageSize?: number,

	githubUrl?: string,

	homePageLazyLoadingImg?: string,

	randomPictureInterface?: string,

	/** 默认请求接口方法 */
	defaultCoverRequestInterface?: string,

	footerInfo?: FooterInfo,


	socialsArr?: Array<SocialInfo>,
	/** pc端侧边栏最大宽度 */
	showRandomSay?: boolean,
	// 不要的
	showPrintText?: boolean,
	randomSayApi?: {
		requestMethod: 'GET' | 'POST' | 'DELETE' | 'UPDATE',
		urlApi: string
	},
	customRandomSay?: {
		show: boolean,
		value: string
	},

	// 不要的
	randomColor?: Array<string>,
	friendLinkSiteInformation?: FriendLinkSiteInformation,
	// 不要的
	aboutInfoArr?: Array<AboutInfo>,
	readme?: string,
	pcBackgroundImageList?: Array<string>,
	mobileBackgroundImageList?: Array<string>
}
