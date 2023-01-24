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
            icon: 'fa:qq',
            color: 'red'

            //鼠标移入此图标时，显示的图片，适用于微信等通过二维码添加好友
            // showImgSrc: "/aurora/wechat.jpg",
        },
        {
            aHref: "javascript:;",
            //imgSrc: /assets/img/ico/wechat.svg,
            showImgSrc: "/wechat.jpg",
            isHome: true,
            show: true,
            color: 'green',
            icon: 'fa:wechat',
            sidebar: true
        },
        {
            aHref: "https://github.com/qsyyke/",
            isHome: true,
            show: true,
            sidebar: true,
            icon: 'fa:github'
        },
        {
            aHref: "https://stackoverflow.com/",
            isHome: true,
            show: true,
            icon: 'fa:stack-overflow',
            sidebar: true
        },
        {
            aHref: "https://space.bilibili.com/483962286",
            isHome: true,
            show: true,
            sidebar: true,
            icon: 'fa:wpbeginner'
        },
        {
            aHref: "https://music.163.com/#/user/home?id=1411050784",
            isHome: true,
            show: true,
            icon: 'fa:music',
            sidebar: true
        },
        {
            aHref: "mailto:2291308094@qq.com",
            isHome: true,
            show: true,
            sidebar: true,
            icon: 'fa:eercast'
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
    homePageLazyLoadingImg: 'https://picture.xcye.xyz/image-20220328221012634.png',
    defaultCoverRequestInterface: 'https://pic-tool.xcye.xyz/pic/rmimg',
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
        backgroundImage: 'https://picture.xcye.xyz/animate/1194.png',
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
    showAboutPageBubble: true,
    aboutInfoArr: [
        {
            bar: false,
            title: "我?",
            describeArr: [
                "这是关于页面支持html<a href='https://blog.cco.vin' target='_blank'>这是一个标签</a>",
                "目前是一名大三学生,CS专业,坐标西南边陲",
                "喜欢安静,不喜社交",
                "喜欢听音乐,什么类型都可",
                "喜欢技术,coding",
                "目前正在学习java后端",
                '最喜欢的电影是"忠犬八公的故事",梦想以后独居也能有一只"Hachi"',
                "主题是我自己开发的，前端太菜了，如果有bug，希望大家多多包涵`Σ(￣□￣||)` ",
                "有点懒",
                "目前除了编程没有什么兴趣爱好",
                "正在追的番有《百妖谱》,《致不灭的你》,《少年歌行》,《鬼灭之刃》,《关于我转生变成史莱姆这档事》...",
                "喜欢小说，喜欢的作者是--十月流年，最喜欢的小说《至尊修罗》,《一念永恒》,《星辰变》",
            ],
            tagArr: [
                "coding",
                "社恐",
                "番剧",
                "电影",
                "安静",
                "音乐",
                "小说",
                "宅",
                "懒",
            ],
            showTag: true,
        },
        {
            bar: false,
            title: "大三规划",
            describeArr: [
                "做项目",
                "软考二级",
                "英语四级",
                "想找实习",
                "复习数据结构",
                "驾照",
                "做点什么有意义的事",
            ],
            tagArr: ["大家加油呀`Σ(￣□￣||)` ..."],
            showTag: true,
        },
        {
            bar: false,
            title: "未来规划",
            describeArr: ["后端工程师", "赚money"],
            tagArr: ["忘记过去，展望未来"],
            showTag: true,
        },
        {
            bar: true,
            title: "技  能",
            barDescArr: [
                {
                    name: "java",
                    score: 70,
                },
                {
                    name: "HTML5",
                    score: 87,
                },
                {
                    name: "javascript",
                    score: 82,
                },
                {
                    name: "css",
                    score: 73,
                },
                {
                    name: "python",
                    score: 50,
                },
                {
                    name: "redis",
                    score: 59,
                },
                {
                    name: "mysql",
                    score: 82,
                },
                {
                    name: "vue",
                    score: 60,
                },
                {
                    name: "spring",
                    score: 71,
                },
                {
                    name: "springMVC",
                    score: 77,
                },
                {
                    name: "springBoot",
                    score: 71,
                },
            ],
            showTag: false,
        },

        {
            showTag: false,
            bar: true,
            title: "掌握框架",
            barDescArr: [
                {
                    name: "spring",
                    score: 79,
                },
                {
                    name: "springMVC",
                    score: 81,
                },
                {
                    name: "springBoot",
                    score: 82,
                },
                {
                    name: "mybatis",
                    score: 82,
                },
                {
                    name: "vue",
                    score: 60,
                },
                {
                    name: "dubbo",
                    score: 61,
                },
            ],
        },
        {
            bar: true,
            showTag: false,
            title: "掌握技能",
            barDescArr: [
                {
                    name: "jetbrains",
                    score: 75,
                },
                {
                    name: "linux",
                    score: 68,
                },
                {
                    name: "git",
                    score: 78,
                },
                {
                    name: "Ctrl C V",
                    score: 100,
                },
            ],
        },
        {
            title: "关于主题",
            describeArr: [
                "bean-ccds主题是我自己独立开发，是一款基于vuepress，对默认主题进行了大量修改，我以前使用的是wordpress的博客主题，但是我对PHP不了解，想改成自己想要的主题，太难了，最近在学vue，正好看到可以使用vuepress来搭建 博客，就将默认主题改成现在这个样，并且喜欢vuepress的最主要原因是，它可以 直接将本地的markdown文档进行编译部署，我原来博客，我记了几个月的笔记，不太想再慢慢从本地复制到WordPress进行发布，以至于博客几个月没有发布文章了，虽然有技术可以解决，但还是喜欢vuepress，简直是懒癌福音，如果你喜欢的话，可以在我的GitHub进行下载，使用文档可以查看https://aurora.xcye.xyz，该主题已将所有的配置进行抽离，你现在看到的所有信息，都抽离在了一个配置文件中，但是目前还并不能做到开箱即用，如果使用的人多了，我可以进行修改，并且我自己也写了几个组件，包括文章页面看到的顶部图片，友情链接，海报分享功能等等 ，可以在你想使用的地方，直接使用这些组件就可以，该主题我加入了vuex，对前端不太了解，有很多css不对的地方，请大家多多包涵，Thanks♪(･ω･)ﾉ",
            ],
        },
        {
            bar: false,
            title: "更新日志",
            describeArr: [
                "开发永不止步......",
                "2021.9.12 增加相册功能，解决已知bug",
                "2021.9.9 增加海报分享功能",
                "2021.9.5 重新修改文章SEO自动配置，使用新规则，解决从tag页面进入文章页面，懒加载失效问题",
                "2021.9.4 重新初始化仓库",
                "2.21.8.10 -- 2021.9 主题开发",
            ],
            tagArr: ['想求个star`(⌒▽⌒)`'],
            showTag: true,
        },
        {
            title: "主题后续计划及闲话",
            bar: false,
            tagArr: [],
            showTag: false,
            describeArr: [
                "为说说页面增加随时发布，随时修改功能",
                "增加每日计划打卡功能",
            ],
        },
    ],
    randomPictureInterface: 'https://cdn.seovx.com/ha/?mom=302'

}