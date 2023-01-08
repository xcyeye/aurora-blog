export const defaultSiteSettingInfo: SiteSettingInfo = {
    showRandomSay: true,
    showPrintText: true,
    socialsArr: [
        {
            //社交链接
            aHref: "tencent://message/?uin=2291308094",
            // imgSrc: "https://ooszy.cco.vin/img/ico/qq.svg", 从v1.3.2开始久移除次配置，以前版本用于社交ico图标配置

            //true为在首页显示，反之
            isHome: true,

            //是否显示此社交信息,如果为false，尽管isHome=true，sidebar=true，也不会显示
            show: true,

            //是否在侧边栏显示
            sidebar: true,

            //使用阿里图标 使用的是阿里图标库，我也挑选部分图标，请进入http://ico.cco.vin/theme查看
            symbol: '#icon-qq',

            //鼠标移入此图标时，显示的图片，适用于微信等通过二维码添加好友
            // showImgSrc: "/aurora/wechat.jpg",
        },
        {
            aHref: "javascript:;",
            //imgSrc: /assets/img/ico/wechat.svg,
            showImgSrc: "/wechat.jpg",
            isHome: true,
            show: true,
            symbol: '#icon-weixin',
            sidebar: true
        },
        {
            aHref: "https://github.com/qsyyke/",
            isHome: true,
            show: true,
            sidebar: true,
            symbol: '#icon-github-fill'
        },
        {
            aHref: "https://stackoverflow.com/",
            isHome: true,
            show: true,
            symbol: '#icon-stackoverflow',
            sidebar: true
        },
        {
            aHref: "https://space.bilibili.com/483962286",
            isHome: true,
            show: true,
            sidebar: true,
            symbol: '#icon-bilibili-1'
        },
        {
            aHref: "https://music.163.com/#/user/home?id=1411050784",
            isHome: true,
            show: true,
            symbol: '#icon-wangyiyunyinle',
            sidebar: true
        },
        {
            aHref: "mailto:2291308094@qq.com",
            isHome: true,
            show: true,
            sidebar: true,
            symbol: '#icon-email'
        },
    ],
    showWave: true,
    randomSayApi: {
        requestMethod: 'GET',
        urlApi: 'https://v1.hitokoto.cn/?encode=text&c=a'
    },
    randomColor: [
        "#ffcad4", "#d8e2dc", "#8d99ae", "#b8f2e6", "#84c7d0", "#aed9e0", "#00b4d8",
        "#caf0f8", "#fbc4ab", "#fdc5f5", "#84dcc6", "#a9def9", "#fcf6bd", "#f0a6ca",
        "#b9faf8", "#42a5f5", "#ff9800", "#b39ddb", "#6d45bb", "#b388ff", "#1565c0",
        "#26c6da", "#5e548e", "#90f1ef", "#5b5f97", "#bbe6e4", "#42bfdd", "#72ddf7",
        "#8093f1", "#9ed8d8", "#7ea8be", "#ef90b3", "#b892ef", "#c0b9dd", "#c0d9dd",
        "#75c9c8", "#ded9e2", "#b5e2fa", "#62b6cb", "#5fa8d3", "#0fa3b1", "#b5e2fa",
        "#5fa8d3", "#62b6cb", "#b892ff",
    ],
    homePageLazyLoadingImg: 'https://gss0.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/7af40ad162d9f2d39ed74a22abec8a136227cc3d.jpg',
    friendLinkSiteInformation: {
        //站点标题
        title: "Aurora-bean",

        //自己站点链接
        url: "https://aurora.cco.vin",

        //自己站点logo
        logo: "/avatar.jpg",

        //自己站点描述
        describe: "vuepress-bean-Aurora是一款简洁，美观，功能强大的静态主题",
        cover: '/my-cover.png',

        //自己的头像
        contact: "email: cqycco@gmail.com, qq:2291308094",
        otherDescribe: [
            '申请友链请按照下面格式，在此页面留言，我看到就会进行添加',
            '博客名称    博客地址      博客描述      博客logo',
            '友链申请要求',
            '拒绝涉政/涉黄/太多广告/盈利性站点',
            '站点能正常访问',
            '这是一个html的测试<a href="https://aurora.xcye.xyz" target="_blank">这是一个a标签</a>'
        ]
    },
    mobilePageSidebar: true,
    latestPageSize: 6,
    sidebarPersonDesc: '喜欢动漫',
    githubUrl: 'https://github.com',
    footerInfo: {
        enable: true,
        footInfo: [
            "Copyright © by qsyyke All Rights Reserved.",
            "<a target='_blank' href='http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142' style='display:inline-block;text-decoration:none;height:20px;line-height:20px;'><img src='' style='float:left;'/><p style='float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px;'>滇公网安备 53060202000142号</p></a>",
        ],
        isShowThemeCopyright: true,
        isShowRunTime: true,
        startRunTime: '2022-12-23 09:08:09',
        prefixRuntime: '测试主题'
    },
    showAboutPageBubble: true

}