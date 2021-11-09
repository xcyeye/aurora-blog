const { path } = require("@vuepress/utils");

module.exports = {
    //在这里配置插件
    plugins: [
        "@vuepress/plugin-search",
        {
            locales: {
                "/": {
                    placeholder: "Search",
                },
                "/zh/": {
                    placeholder: "搜索",
                },
            },
        },
        /*[
            //'vuepress-plugin-coze',//你如果不是克隆我仓库项目，那么将此注释打开，并注释path.resolve(__dirname, "../../Aurora-plugin/vuepress-plugin-coze/lib/node/index.js"),
            path.resolve(__dirname, "../../Aurora-plugin/vuepress-plugin-coze/lib/node/index.js"),
            {
                appId: 'leanCloud中得到的appId',
                appKey: 'leanCloud中得到的appKey',
                masterKey: 'leanCloud中得到的masterKey',
                //下面这些是可选的
                avatarPath: 'https://ooszy.cco.vin/img/blog-note/avatar-aurora.png',//说说头像url
                registerPath: '/aurora-register', //自定义插件默认提供的注册页面路由，请在前面加上/
                onlyAdministrator: false //是否运行其他注册的用户发布说说，true表示只有管理员可以发布
            }
        ],*/
        [
            path.resolve(__dirname, "../../Aurora-plugin/vuepress-plugin-coze/lib/node/index.js"),
            {
                appId: '2A2Dyd2AffrnldhwftlEddVn-MdYXbMMI',
                appKey: 'qHYTbb91iOPLelyC9lpbXxLH',
                masterKey: 'eUwfvS3luIPnPiHS5SpEhDYr',
                //下面这些是可选的
                avatarPath: 'https://ooszy.cco.vin/img/blog-note/avatar-aurora.png',//说说头像url
                registerPath: '/aurora-register', //自定义插件默认提供的注册页面路由，请在前面加上/
                onlyAdministrator: false //是否运行其他注册的用户发布说说，true表示只有管理员可以发布
            }
        ]
    ],
    //设置head 一定要加入<script src="https://at.alicdn.com/t/font_2849934_v6y652peian.js"></script>项配置，否则一些图标不能正常显示
    head: [
        [
            "script",
            {
                src: "https://at.alicdn.com/t/font_2849934_v6y652peian.js",
            },
        ],
        //设置站点icon
        [
            "link",
            {
                href: "https://ooszy.cco.vin/img/ico/yuan.png",
                rel: "icon",
            },
        ],
        [
            "link",
            {
                rel: 'stylesheet',
                type: 'text/css',
                href: '//at.alicdn.com/t/font_2849934_7el0cvwfmn.css'
            }
        ]
    ],
    //这里使用本地主题
    theme: path.resolve(__dirname, "../../Aurora-theme/lib/node/index.js"),

    //站点title
    title: "I do not follow,i lives is always all you want",

    lang: 'zh-CN',

    //设置运行npm run dev的端口
    port: 8080,

    //themeConfig的所有配置，都是主题配置，包含默认主题配置项
    themeConfig: {
        //这里配置导航栏项，建议将navbar提取出，比如navbar.js
        navbar: [

            {
                text: '快速开始',
                link: '/readme/',
                iconClass: 'aurora-0023pacman'
            },
            {
                text: "所有配置",
                link: '/home/config.html',
                iconClass: 'aurora-link'
            },
            {
                text: '问题和bug',
                iconClass: 'aurora-si-glyph-global',
                children: [
                    {
                        text: 'CHANGELOG',
                        children: [
                            '/issue/CHANGELOG.md'
                        ]
                    },
                    {
                        text: '主题详细搭建教程',
                        children: [
                            '/readme/introduce.md',
                            '/use/useTheme.md'
                        ]
                    },
                    {
                        text: '问题',
                        children: [
                            '/issue/',
                        ]
                    },
                    {
                        text: 'bug',
                        children: [
                            '/issue/bug.md',
                        ]
                    }
                ]
            },
            {
                text: '其他配置',
                iconClass: 'aurora-sticker',
                children: [
                    {
                        children: [
                            "/home/deploy.md",
                        ],
                        text: "home",
                    },
                    {
                        children: [
                            "/comment/README.md",

                            "/page/README.md",
                        ],
                        text: "其他配置",
                    },
                    {
                        children: [
                            "/v1.3.0/README.md",

                        ],
                        text: "V1.3.2",
                    }
                ],
            },
            {
                text: "about",
                link: "/about",
                iconClass: 'aurora-chat'
            },
            {
                text: 'link',
                link: '/link',
                iconClass: 'aurora-menu'
            },
            {
                text: 'tag',
                link: '/tag',
                iconClass: 'aurora-page'
            },
            {
                text: 'chat',
                link: '/mood',
                iconClass: 'aurora-volume-icon'
            },
            {
                text: "photo",
                link: '/photo',
                iconClass: 'aurora-icon-time'
            }
        ],

        //禁用黑夜模式，当前版本未提供
        darkMode: false,

        //项目地址
        repo: "https://github.com/qsyyke/vuepress-theme-aurora",

        repoIconClass: 'aurora-github1',

        //md文件的仓库地址
        docsRepo: 'https://github.com/qsyyke/aurora-docs',

        /*
        * 站点是否是使用github Actions自动部署，如果你使用github Actions完成自动部署，那么这里一定要为TRUE，为TRUE，假如你把此项目push
        * 到https://github.com/qsyyke/aurora-docs这个仓库中，那么你需要将docsRepo设置为https://github.com/qsyyke/aurora-docs，
        * 那么在文章页面，会根据根据你docsRepo和docsBranch设置正确的edit链接，也就是可以一键打开GitHub仓库中，此篇md文件的编辑
        * */
        githubActions: true,

        //md文件存放的仓库分支
        docsBranch: 'main',

        //顶部导航栏，你repo地址的超链接文本
        repoLabel: "Aurora-theme",

        //是否启用文章在线编辑
        editLink: true,

        //在线编辑文字
        editLinkText: "edit",
        lastUpdated: true,
        lastUpdatedText: "lastTime",

        //下面的都是主题自己的配置文件
        //logo旁文字颜色
        logoColor: "#2c3e50",

        //样式控制面板字体占位符，如果是国内用户，请使用中文
        showFont: "程",

        //首页中间hero图片地址，默认为https://ooszy.cco.vin/img/blog-public/avatar.jpg
        heroImg: 'https://ooszy.cco.vin/img/ico/yuan.png',

        //logo图片地址，默认为https://ooszy.cco.vin/img/ico/yuan.png
        logo: "https://ooszy.cco.vin/img/ico/yuan.png",

        //文章懒加载图片 仅限文章，首页文章占位图片并不是这个
        lazyLoadingImg: "https://ooszy.cco.vin/img/blog-public/ljz.gif",

        //这是首页文章列表懒加载图片
        homePageLazyLoadingImg: 'https://ooszy.cco.vin/img/blog-note/aurora-loading.gif?name=chuchen',

        //是否启用定制首页随机一言，默认未开启，使用随机一言 接口为https://international.v1.hitokoto.cn/?c=b&max_length=45
        customRandomSay: false,

        //定制首页随机一言文字
        customRandomValue: 'Vuepress-theme-Aurora',

        //社交信息，首页PC端至多显示19个，手机端至多显示7个，侧边栏不影响
        socials: [
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
                // showImgSrc: "https://ooszy.cco.vin/img/blog-public/wechat.jpg",
            },
            {
                aHref: "javascript:;",
                //imgSrc: /assets/img/ico/wechat.svg,
                showImgSrc: "https://ooszy.cco.vin/img/blog-public/wechat.jpg",
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

        // cancelIcoHref: "https://ooszy.cco.vin/img/ico/quxiao.svg", 在v1.3.2中移除

        //logo旁文字 默认值为Aurora
        logoTitle: "Aurora",

        headTitle: "this is headTitle",

        //站点描述
        description: "vuepress-theme-Aurora是一款简洁，美观，功能强大的静态主题",

        //站点关键词，在后续版本中，还需优化 请使用英文状态下的逗号','隔开
        keyword: "vuepress主题,vuepress theme,生活琐事,二次元博客,简约博客,博客主题,静态主题",

        //样式控制面板打开之后，休眠多长时间自动关闭面板，单位毫秒
        slideTime: 300000,

        //随机一言接口
        randomSaw: "https://international.v1.hitokoto.cn/?c=b&max_length=45",
        //关于页面
        about: [
            {
                bar: false,
                title: "我?",
                describe: [
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
                tag: [
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
                describe: [
                    "做项目",
                    "软考二级",
                    "英语四级",
                    "想找实习",
                    "复习数据结构",
                    "驾照",
                    "做点什么有意义的事",
                ],
                tag: ["大家加油呀`Σ(￣□￣||)` ..."],
                showTag: true,
            },
            {
                bar: false,
                title: "未来规划",
                describe: ["后端工程师", "赚money"],
                tag: ["忘记过去，展望未来"],
                showTag: true,
            },
            {
                bar: true,
                title: "技  能",
                describe: [
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
                describe: [
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
                describe: [
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
                describe: [
                    "theme-ccds主题是我自己独立开发，是一款基于vuepress，对默认主题进行了大量修改，我以前使用的是wordpress的博客主题，但是我对PHP不了解，想改成自己想要的主题，太难了，最近在学vue，正好看到可以使用vuepress来搭建 博客，就将默认主题改成现在这个样，并且喜欢vuepress的最主要原因是，它可以 直接将本地的markdown文档进行编译部署，我原来博客，我记了几个月的笔记，不太想再慢慢从本地复制到WordPress进行发布，以至于博客几个月没有发布文章了，虽然有技术可以解决，但还是喜欢vuepress，简直是懒癌福音，如果你喜欢的话，可以在我的GitHub进行下载，使用文档可以查看https://theme-ccds.cco.vin，该主题已将所有的配置进行抽离，你现在看到的所有信息，都抽离在了一个配置文件中，但是目前还并不能做到开箱即用，如果使用的人多了，我可以进行修改，并且我自己也写了几个组件，包括文章页面看到的顶部图片，友情链接，海报分享功能等等 ，可以在你想使用的地方，直接使用这些组件就可以，该主题我加入了vuex，对前端不太了解，有很多css不对的地方，请大家多多包涵，Thanks♪(･ω･)ﾉ",
                ],
            },
            {
                bar: false,
                title: "更新日志",
                describe: [
                    "开发永不止步......",
                    "2021.9.12 增加相册功能，解决已知bug",
                    "2021.9.9 增加海报分享功能",
                    "2021.9.5 重新修改文章SEO自动配置，使用新规则，解决从tag页面进入文章页面，懒加载失效问题",
                    "2021.9.4 重新初始化仓库",
                    "2.21.8.10 -- 2021.9 主题开发",
                ],
                tag: ['想求个star`(⌒▽⌒)`'],
                showTag: true,
            },
            {
                title: "主题后续计划及闲话",
                bar: false,
                tag: [],
                showTag: false,
                describe: [
                    "为说说页面增加随时发布，随时修改功能",
                    "增加每日计划打卡功能",
                ],
            },
        ],

        //拿取对象 在后续版本中，会对此项进行优化
        randomSawQuery: "hitokoto",
        method: "get",

        //这是配置随机背景；颜色 可以不设置，有默认值
        randomColor: [
            "#ffcad4", "#d8e2dc", "#8d99ae", "#b8f2e6", "#84c7d0", "#aed9e0", "#00b4d8",
            "#caf0f8", "#fbc4ab", "#fdc5f5", "#84dcc6", "#a9def9", "#fcf6bd", "#f0a6ca",
            "#b9faf8", "#42a5f5", "#ff9800", "#b39ddb", "#6d45bb", "#b388ff", "#1565c0",
            "#26c6da", "#5e548e", "#90f1ef", "#5b5f97", "#bbe6e4", "#42bfdd", "#72ddf7",
            "#8093f1", "#9ed8d8", "#7ea8be", "#ef90b3", "#b892ef", "#c0b9dd", "#c0d9dd",
            "#75c9c8", "#ded9e2", "#b5e2fa", "#62b6cb", "#5fa8d3", "#0fa3b1", "#b5e2fa",
            "#5fa8d3", "#62b6cb", "#b892ff",
        ],

        //样式控制面板至多显示多少个字体和字体颜色，推荐不超过8个
        maxFontColorArr: 8,

        //在样式控制面板中，显示的字体颜色集合
        fontColor: [
            "#2c3e50", "#42a5f5", "#8093f1", "#FF6EC7", "#FF7F00", "#8FBC8F", "#EAADEA",
            "#3299CC", "#CDCDCD", "#CC3299", "#FF7F00", "#2F4F4F",
        ],

        //友情链接数组
        friendLinks: [
            {
                title: '我的朋友',
                links: [
                    {
                        //网站标题
                        title: "XI溪",

                        //站点链接
                        url: "http://www.xiaoxuya.top",

                        //站点logo
                        logo: "https://www.xiaoxuya.top/img/logo.png",

                        //站点描述
                        describe: "人生若只是初见,何事秋风悲画扇",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100103898.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "左眼会陪右眼哭の博客",
                        url: "http://qkongtao.cn/",
                        logo: "http://qiniu.qkongtao.cn/2020/12/d11-e1628358435552.png",
                        describe: "干嘛这么想不开，要在脸上贴个输字！",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106101057042.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "[ Blog We]",
                        url: "https://blogwe.com/",
                        logo: "https://blogwe.com/favicon.ico",
                        describe: "博客大全-做最好的博客导航！",
                        cover: "https://ooszy.cco.vin/img/blog-note/image-20211106100344348.png?x-oss-process=style/pictureProcess1"
                    },
                    {
                        title: "I Am I",
                        url: "https://5ime.cn",
                        logo: "https://cdn.jsdelivr.net/gh/5ime/img/avatar.jpg",
                        describe: "永远相信美好的事情即将发生",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100424035.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "Davinci的红茶馆",
                        url: "https://davincievans.top/",
                        logo: "https://cdn.jsdelivr.net/gh/DavinciEvans/Imgs-bed@master/gallery/avatar.jpg",
                        describe: "You are all stardust.",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100502873.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "皮皮凛の小窝",
                        url: "https://owomoe.net/",
                        logo: "https://cdn.jsdelivr.net/gh/AyagawaSeirin/Assets/img/logo.jpg",
                        describe: "永远相信美好的事情即将发生~",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100608395.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "月月月子喵",
                        url: "https://haozi.moe",
                        logo: "https://haozi.moe/css/images/logo_christmas.png",
                        describe: "可爱的月子酱",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100009647.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "疫情在线捐款系统",
                        url: "http://yq.vipblogs.cn/",
                        logo: "https://ooszy.cco.vin/img/blog-public/avatar.jpg",
                        describe: "基于echarts的疫情捐款系统",
                        cover: ''
                    },
                    {
                        title: "疫情在线捐款系统后台登录",
                        url: "http://admin.vipblogs.cn/",
                        logo: "https://ooszy.cco.vin/img/blog-public/avatar.jpg",
                        describe: "该捐款系统后台登录",
                        cover: ''
                    },
                ]
            },
            {
                title: '大佬',
                links: [
                    {
                        //网站标题
                        title: "XI溪",

                        //站点链接
                        url: "http://www.xiaoxuya.top",

                        //站点logo
                        logo: "https://www.xiaoxuya.top/img/logo.png",

                        //站点描述
                        describe: "人生若只是初见,何事秋风悲画扇",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100103898.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "左眼会陪右眼哭の博客",
                        url: "http://qkongtao.cn/",
                        logo: "http://qiniu.qkongtao.cn/2020/12/d11-e1628358435552.png",
                        describe: "干嘛这么想不开，要在脸上贴个输字！",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106101057042.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "皮皮凛の小窝",
                        url: "https://owomoe.net/",
                        logo: "https://cdn.jsdelivr.net/gh/AyagawaSeirin/Assets/img/logo.jpg",
                        describe: "永远相信美好的事情即将发生~",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100608395.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "月月月子喵",
                        url: "https://haozi.moe",
                        logo: "https://haozi.moe/css/images/logo_christmas.png",
                        describe: "可爱的月子酱",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100009647.png?x-oss-process=style/pictureProcess1'
                    },
                ]
            },
            {
                title: '小伙伴',
                links: [
                    {
                        //网站标题
                        title: "XI溪",

                        //站点链接
                        url: "http://www.xiaoxuya.top",

                        //站点logo
                        logo: "https://www.xiaoxuya.top/img/logo.png",

                        //站点描述
                        describe: "人生若只是初见,何事秋风悲画扇",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100103898.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "[ Blog We]",
                        url: "https://blogwe.com/",
                        logo: "https://blogwe.com/favicon.ico",
                        describe: "博客大全-做最好的博客导航！",
                        cover: "https://ooszy.cco.vin/img/blog-note/image-20211106100344348.png?x-oss-process=style/pictureProcess1"
                    },
                    {
                        title: "Davinci的红茶馆",
                        url: "https://davincievans.top/",
                        logo: "https://cdn.jsdelivr.net/gh/DavinciEvans/Imgs-bed@master/gallery/avatar.jpg",
                        describe: "You are all stardust.",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100502873.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "皮皮凛の小窝",
                        url: "https://owomoe.net/",
                        logo: "https://cdn.jsdelivr.net/gh/AyagawaSeirin/Assets/img/logo.jpg",
                        describe: "永远相信美好的事情即将发生~",
                        cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100608395.png?x-oss-process=style/pictureProcess1'
                    },
                    {
                        title: "疫情在线捐款系统",
                        url: "http://yq.vipblogs.cn/",
                        logo: "https://ooszy.cco.vin/img/blog-public/avatar.jpg",
                        describe: "基于echarts的疫情捐款系统",
                        cover: ''
                    },
                ]
            },
        ],

        //自己的站点信息 我自己的站点描述 会显示在友情链接的底部
        siteInformation: {
            //站点标题
            title: "Aurora-theme",

            //自己站点链接
            url: "https://aurora.cco.vin",

            //自己站点logo
            logo: "https://ooszy.cco.vin/img/ico/yuan.png",

            //自己站点描述
            describe: "vuepress-theme-Aurora是一款简洁，美观，功能强大的静态主题",
            cover: 'https://ooszy.cco.vin/img/blog-note/image-20211106100103898.png?x-oss-process=style/pictureProcess1',

            //自己的头像
            contact: "email: cqycco@gmail.com, qq:2291308094",
            otherDescribe: [
                '申请友链请按照下面格式，在此页面留言，我看到就会进行添加',
                '博客名称    博客地址      博客描述      博客logo',
                '友链申请要求',
                '拒绝涉政/涉黄/太多广告/盈利性站点',
                '站点能正常访问',
            ]
        },

        //需要排除的标签，自动生成的标签中，不会有这个标签，这是一个数组
        excludeTag: ["note"],

        //样式控制面板显示的字体，有默认值
        fontFamily: [
            "-apple-system", "hlt", "tzt", "sst", "lf", "xsf", "lsf", "cgt",
        ],

        //页脚信息，支持HTML，这是一个数组
        footer: [
            "Copyright © by qsyyke All Rights Reserved.",
            "<a target='_blank' href='http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142' style='display:inline-block;text-decoration:none;height:20px;line-height:20px;'><img src='' style='float:left;'/><p style='float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px;'>滇公网安备 53060202000142号</p></a>",
        ],

        //是否显示页脚，控制全局
        isShowFooter: true,

        //是否显示主题信息在页脚，为false关闭
        isShowThemeCopyright: true,

        //是否展示运行时间
        isShowRunTime: true,

        //网站开始时间，请按照以下格式进行
        startRunTime: "8/7/2021 12:22:00",

        //网站运行时间前缀
        prefixRuntime: "小破站已运行",

        //公告，是一个数组，支持图片，HTML
        message: [
            '该博客主题为Aurora,<a href="https://github.com/qsyyke/vuepress-theme-aurora">vuepress-theme-Aurora</a>',
            "主题交流群: 681602026，欢迎各位大佬进群交流",
        ],

        //文章底部最大推荐文章数 默认值为30
        recommendPageLength: 30,

        //推荐列表标题为空时，就会使用这个进行代替，默认是`╮(￣▽￣)╭`
        recommendNoTitle: "`╮(￣▽￣)╭`",

        //tag页，没有标题时，代替文字 默认是下面这个
        tagNoTitle: "暂时还没有标题哟",

        //首页中间框的话语
        mood: "青衫烟雨客",

        //默认打开网站时的毛玻璃状态，TRUE表示默认开启毛玻璃效果
        isFitter: true,

        //默认的圆角，传入数字
        defaultBorderRadius: 10,

        //默认的透明度，传入0到1之间的小数，0表示全透明
        defaultOpacity: 1,

        //首页文章列表透明度是否跟随样式面板改变，true表示跟随，全白色,false表示不跟随
        isHomePageFollow: false,

        //默认模糊度
        defaultBlur: 1,

        //手机端，是否在页面的底部显示侧边栏列表，默认开启，如果需要开启，请将此值设置为FALSE
        //mobilePageSidebar: false,

        //tag页面，标签分割符 请不要传入一个空字符串，默认值就是' ' 一个空格
        split: "~",

        //赞赏信息
        donate: {

            //赞赏页面，支付二维码，推荐放置两张图片链接
            donateImg: [
                "https://ooszy.cco.vin/img/blog-public/wxpay.png",
                "https://ooszy.cco.vin/img/blog-public/zfbpay.jpg",
            ],

            //是否在文章页面显示赞赏 默认显示
            articlePage: true,

            //是否在关于页面显示 默认显示
            aboutPage: true,

            //显示在赞赏页面的信息
            donateProduct: [
                {
                    //名字
                    name: "奶茶",

                    //图片地址
                    img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",

                    //价格
                    price: 18,

                    //前缀
                    prefix: "$",
                },
                {
                    name: "全味奶茶",
                    img: "https://ooszy.cco.vin/img/blog-note/image-20210911233612031.png?",
                    price: 11,
                    prefix: "￥",
                },
            ],

            //是否显示在线支付的订单信息，如果需要开启，请自己写支付接口，自己修改源码，默认关闭
            onlineList: true,

            //用户赞赏列表数组
            donateList: [
                {
                    name: "初尘",
                    msg: "主题太棒了",
                    img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",
                    price: 7,
                    prefix: "￥",
                },
            ],
        },

        //评论配置 请自己查看文档配置 https://aurora.cco.vin/config/comment/
        comment: {
            //是否显示评论
            showComment: true,
            serverURL: 'https://aurora-local-7hrjb6mp8-qsyyke.vercel.app/',
            emojis: [
                'https://cdn.jsdelivr.net/gh/walinejs/emojis@1.0.0/alus',
                'https://cdn.jsdelivr.net/gh/walinejs/emojis@1.0.0/bilibili',
                'https://cdn.jsdelivr.net/gh/walinejs/emojis@1.0.0/tieba',
                'https://cdn.jsdelivr.net/gh/walinejs/emojis@1.0.0/weibo'
            ],
            avatar: 'monsterid'
        },

        //海报分享配置
        poster: {
            //博客描述
            description: "vuepress-theme-Aurora是一款简洁，美观，功能强大的静态主题",

            //作者
            author: "qsyyke",

            //博客前缀
            preBlog: "Aurora",

            //海报博客名称后缀
            suffixBlog: " theme",

            //头像，请放置在docs/public目录下，或者请保证此图片链接能够跨域访问，否则头像不能正常显示
            avatar: "/avatar.png",
        },

        //是否展示从网络上请求回来的说说 如果启用，请自己写后台服务，修改源码，目前暂未在主题中加入，期望在将来能够实现，组件位置docs/.vuepress/theme/lib/client/components/Mood.vue
        showOnlineMood: true,

        //是否开启在线添加说说功能，如果需要，请自己写后台，修改源码，目前在主题内部暂未加入 组件位置docs/.vuepress/theme/lib/client/components/child/AddMood.vue
        showMoodEdit: false,

        //此项仅限我自己的增加说说请求配置，暂未提供注册服务，目前仅限自己使用，可能在以后完善之后，你们只需要简单配置下，就能使用了，期待
        addMood: {
            siteName: "localhost:8080",
            appId: "q4LlYVTTk2r39sNrJr7BL9A6p",
            appKey: "VxezWTppppyX2LTx4TUK8w6e2",
        },

        //自定义顶部图片
        customTopImg: {
            //是否启用定制顶部图片，控制全局，如果关闭，那么将使用随机图片，随机图片接口可以自己设置
            custom: false,

            //文章顶部图片，数组，每次从数组中随机选择一张
            page: [
                "https://picoss.cco.vin/animate/wall/555260.png",
                'https://picoss.cco.vin/animate/wall/404901.png',
                'https://picoss.cco.vin/animate/wall/734386.png'
            ],
            //友情链接页面
            friend: [
                "https://picoss.cco.vin/animate/wall/669.png",
                'https://picoss.cco.vin/animate/wall/5332.png'
            ],
            //标签页面
            tag: [
                "https://picoss.cco.vin/animate/wall/763311.png"
            ],
            //心情页面
            mood: [
                "https://picoss.cco.vin/animate/wall/5849.png"
            ],
        },

        //首页背景图片数组，考虑到使用随机图片，打开网站速度变慢，所以移除随机图片，使用自己设置的图片链接
        homeWps: [
            'https://ooszy.cco.vin/img/blog-note/illust_74502138_20211008_183343.png',
            "https://picoss.cco.vin/animate/wall/404901.png",
            "https://picoss.cco.vin/animate/wall/734386.png",
            "https://picoss.cco.vin/animate/wall/5332.png",
            "https://picoss.cco.vin/animate/wall/6202.png",
        ],

        //手机端首页背景图片
        homeWpsMobile: [
            "https://ooszy.cco.vin/img/blog-note/881770.jpg",
            "https://ooszy.cco.vin/img/blog-note/109136.jpg",
            "https://ooszy.cco.vin/img/blog-note/929842.jpg",
        ],

        //首页文章显示条数，默认为4，此值不推荐设置太大
        pageSize: 4,

        //侧边栏配置
        //github地址
        githubUrl: "https://github.com/qsyyke/vuepress-theme-aurora",

        //最新文章数量，默认为6
        latestPageSize: 6,

        //首页是否显示文章图片，默认关闭，如果显示的话，首页加载会非常慢 已弃用，从v1.3.2开始，直接移除首页文章内容图片
        showHomePageImg: false,

        //文章侧边栏自动获取的层次 默认为1，也就是http://localhost:8080/config/feature/donate.html,只会自动生成feature目录下的文件
        sidebarCatalogLevel: 3,

        //首页文章列表封面图api接口
        homePageImgApi: "https://api.ixiaowai.cn/api/api.php",

        //手机端侧边栏横线分割文字，默认为Aurora
        mobileCutText: "Aurora",

        //侧边栏标签处显示还是分类还是标签，只有两个值，默认为分类，如果为tag，那么就显示为分类，否则显示为标签
        sidebarTag: "tag",

        //额外的功能，也就是样式控制面板上面那个，默认是关闭的
        showAddMood: true,

        //生成海报的顶部图片api接口，请注意，该接口需要直接返回图片地址，不能有跨域问题，设置之前，可以先使用ajax看是否存在跨域
        postImgApi: 'https://picture.cco.vin/pic/rmimg?type=bing',

        /*
        * 以下为v1.5.4新增功能
        * */

        //文章h2标签的icon
        articleH2Icon: '🌸',

        //文章h3标签的icon
        articleH3Icon: '🐳',

        //文章h4标签及h4标签之后的标签的icon
        articleH4Icon: '⛄',


        /*
        * 下面这些是在v1.3.2中移除的配置，但是在V1.3.2之前的版本中，可能还可以使用，某些配置我不确定具体在哪个
        * 版本中被移除的，并且在v1.3.2版本中，才把主题配置移到docs/.vuepress/config.js中，之前所有版本都是在
        * docs/readme.md中进行配置，并不推荐使用V1.3.2之前的版本，没有侧边栏，可能还存在一些bug
        * 同时你可能会发现上面的某些配置项并不是很好，比如随机一言的配置，我最开始想的就是希望能在配置项中，通过简单的配置
        * 就能从api接口返回回来的复杂json数据中，获取到想要的值，但是目前此配置项，特别不方便，在后续版本中，会继续对此项
        * 进行优化
        *
        * 未来:
        * 此博客主题目前也拥有博客的最基本功能，但是某些功能，我目前并没有实现，在后续版本中，如果使用此主题的用户多的话
        * 我都会逐一实现，此项目我会一直维护，以下便是在后续版本中，需要实现或者优化的
        *
        * 1.时间轴
        * 2.优化SEO
        * 3.优化海报分享功能，目前海报分享功能，对于一些文章，海报的宽度和高度会发生变化，并将海报分享制作成一个插件
        * 4.广告，其实在最初的版本中，就已经实现了广告功能，只是不太理想，就移除了，后续会重新开发
        * 5.页面交互动画，我并不擅长写前端，所以此主题的页面动画只是简单的做了一下，会在后续版本中进行优化
        * 6.希望将所有的样式配置都进行抽离，比如菜单栏的ico图标等等，可以自己配置，虽然目前也可以更改，但是需要在
        * 源码中进行简单的Unicode替换，其实如果改源码的话，也是非常简单的，只是我想在配置文件中，就可以进行修改
        * 7.说说功能感觉如果在本地编写，然后再进行上传发布的话，并不是很方便，说说应该是一个随时能发布，修改的功能，
        * 所以在后续版本中，会加入(目前也实现了这个功能，只是后端api是我自己写的，目前只能我自己使用，在后续版本中，希望能
        * 够找到解决办法)
        * 8.暗夜模式，最初在修改默认主题的时候，就被我移除了，但是一些用户可能还会使用到，所以也会在后续版本中，添加此功能
        * 9.优化首页文章列表
        *
        * 最后:该主题最开始只是想魔改vuepress默认主题供自己使用，但是后面又想继续完善供所有需要的人使用，由于我对前端特别
        * 菜，源码和页面中有很多不规范的地方，请各位大佬多多担待`(｀・ω・´)`
        * */

        // heroLogo: "https://ooszy.cco.vin/img/blog-public/avatar.jpg", 在v1.3.2中被移除
        //faviconIco: https://ooszy.cco.vin/img/ico/yuan.png 已弃用
        //faviconIco: https://github.githubassets.com/favicons/favicon.svg 已弃用
        // showHomeMood: false,在v1.3.2中已移除
        // defaultTitleColor: "#8093f1", 默认文章页，标题颜色 在v1.3.2中移除
        //animeOption 配置已弃用
        /*animeOption: {
            baseURL: "https://api.vvhan.com/api/acgimg?type=json",
            method: 'get',
            timeout: 5000,
            query: "imgurl",
        },*/
        //随机图片地址，设置背景用 已弃用，在v1.3.2以后，改用自己设置的形式
        //randomWallpaper: https://api.mtyqx.cn/api/random.php
        //randomWallpaperMb: https://api.mtyqx.cn/api/random.php
        /*ico: { 在v1.3.2之后被移除
            linkIco: "https://ooszy.cco.vin/img/ico/tea.svg",
            aboutIco: "https://ooszy.cco.vin/img/ico/cat.svg",
            homeWelcome: [
                "https://ooszy.cco.vin/img/ico/love2.svg",
                "https://ooszy.cco.vin/img/ico/xy.svg",
            ],
        },*/
        // keyRule: 40, 如果没有设置key值，那么就截取描述作为key，keyRule就是截取多少个字 弃用
        // isShowMessage: true,//已弃用，在v1.3.2之后，改用在侧边栏显示公告
        /*adsenseArr: [ 弃用
            {
                position: "right",
                adsenseMessage: "官网用户专享",
                script: "",
                adsenseBackgroundImg: "/img/33.jpg",
            },
            {
                position: 'center',
                adsenseMessage: "官网用户专享1",
                script: '<a href="https://baidu.com">这是中间1</a>',
                adsenseBackgroundImg: "/img/33.jpg",
            },
            {
                position: "center",
                adsenseMessage: "官网用户专享2",
                script: '<a href="https://baidu.com">这是中间2</a>',
                adsenseBackgroundImg: "/img/33.jpg",
            },
        ],*/
        // insertAdsenseRule: 17, 弃用
        // adsenseLength: 3, 每一页文章中，最大广告数 弃用
        // socialMaxLength: 7, 在v1.3.2版本中移除
    }
};
