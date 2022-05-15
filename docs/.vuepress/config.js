const { path } = require("@vuepress/utils");
const {navbar} = require("./navbar.js")
const {friendLinks} = require("./friendLinks")
const {socials} = require("./socials");
const {randomColor} = require("./randomColor");
const {siteInformation} = require("./siteInformation");
const {message} = require("./message");
const {donate} = require("./donate");
module.exports = {
    // base: '/REPO/',//如果你准备发布到 https://<USERNAME>.github.io/<REPO>/ ，也就是说你的仓库地址是 https://github.com/<USERNAME>/<REPO> ，则将 base 设置为 "/<REPO>/"。

    /*
    * 这里是配置Markdown语法增强的配置，如需配置，请查看官方介绍https://v2.vuepress.vuejs.org/zh/guide/markdown.html
    * 或者你也可以看过我的配置，使用该配置的文件路径Aurora-theme/lib/node/auroraTheme.js
    * */
    extendsMarkdown: (md) => {

    },

    // 打开下面注释，将使用webpack作为打包工具，如果注释将使用vite作为打包工具[目前主题不能使用vite打包，会报错]
    bundler: '@vuepress/bundler-webpack',
    bundlerConfig: {

    },

    /*
    * 在这里配置插件
    * 插件plugins是一个数组，官方介绍: https://v2.vuepress.vuejs.org/zh/guide/plugin.html
    * */
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
        /*
        * 在主题中，使用path.resolve(__dirname,的地方，都是使用本地的插件或者主题，看位置
        * 这里是配置说说插件，使用https://aurora.xcye.xyz/plugin/coze/
        * */
        [
            // path.resolve(__dirname, "../../Aurora-plugin/vuepress-plugin-coze/lib/node/index.js"),
            "vuepress-plugin-coze",
            {
                appId: '2A2Dyd2AffrnldhwftlEddVn-MdYXbMMI',
                appKey: 'qHYTbb91iOPLelyC9lpbXxLH',
                masterKey: 'eUwfvS3luIPnPiHS5SpEhDYr',
                //下面这些是可选的
                avatarPath: '/avatar.jpg',//说说头像url
                registerPath: '/register', //自定义插件默认提供的注册页面路由，请在前面加上/
                onlyAdministrator: false //是否运行其他注册的用户发布说说，true表示只有管理员可以发布
            }
        ],
        /*
        * 该插件是配置时间轴，使用https://aurora.xcye.xyz/plugin/archive/
        * 请配置一下excludes，否则在时间轴那里，会出现非文件页面链接
        * */
        [
            "vuepress-plugin-archive",
            // path.resolve(__dirname, "../../Aurora-plugin/vuepress-plugin-archive/lib/node/index.js"),
            {
                excludes: ['/footer.html','/404.html','/about/','/mood/','/link/','/tag/','/photo/'],
                noTitle: '暂时没有标题配置'
            }
        ],
        /*
        * 音乐插件，使用：https://aurora.xcye.xyz/plugin/player/
        * */
        [
            // path.resolve(__dirname, "../../Aurora-plugin/vuepress-plugin-player/lib/node/index.js"),
            "vuepress-plugin-player",
            {
                // disableSpace: false,
                //网易云单个歌单id
                songIds: ['29723011','1887893189','1421069053'],
                //网易云歌单
                playlist: '7082462754',
                showPlaylist: true,
                //是否禁用网易云音乐，如果你选择禁用，那么就将使用本地的歌曲，请传入链接
                disabledNetEaseMusic: true,

                //请求接口的baseURL
                serverUrl: 'https://netease-cloud-music-api-teal-psi.vercel.app/',

                //本地歌曲
                localSongs: {
                    coverUrl: '/avatar.jpg',
                    songs: [
                        {
                            path: '/song/1.mp3',
                            songName: '12',
                            cover: 'https://p1.music.126.net/Rg1x9LeUacIDqtvUzL35Cw==/109951163688517312.jpg'
                        },
                        // {
                        //     path: 'http://m7.music.126.net/20211120155029/f99e2fe5f557455fd37b7bfd0c0d6c3e/ymusic/545a/005e/025f/c03ab3077e74b9d50e07557d82ca472b.flac',
                        //     songName: '23',
                        //     cover: 'https://p2.music.126.net/8mnn6YiQldsRIHe_nER8wg==/109951162894925733.jpg'
                        // },

                        {
                            path: '/song/2.mp3',
                            songName: 'sdf'
                        },
                        {
                            path: '/song/3.mp3',
                            songName: '34'
                        },
                    ]
                }
            }
        ],

        /*
        * 气泡插件，使用：https://aurora.xcye.xyz/plugin/bubble/
        * */
        [
            "vuepress-plugin-bubble",
            // path.resolve(__dirname, "../../Aurora-plugin/vuepress-plugin-bubble/lib/node/index.js"),
            {
                //气泡数量 推荐0(不包括)到1之前的小数，
                bubbleNumber: 0.14,

                //气泡透明度 0到1之间的小数
                bubbleAlpha: 0.6,

                //透明度变化速度，越接近于0越好
                alphaChangeSpeed: 0.00001,

                //气泡大小，推荐0到1之间的值
                size: 0.4,

                //气泡大小变化速度 越小越好
                sizeChangeSpeed: 0.0002,

                //气泡上升速度
                riseSpeed: 0.4,

                //气泡颜色，白色rgb(255,255,255) 请传入255,255,255
                color: '255,255,255',

                zIndex: -2
            }
        ],
    ],
    /*
    * 设置head 一定要加入<script src="https://at.alicdn.com/t/font_2849934_v6y652peian.js"></script>项配置，否则一些图标不能正常显示
    * 如果你需要额外引入css样式或者是js文件，请在head中引入，如何引入，可以参照下面的引入方式
    * */
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
                href: "/avatar.png",
                rel: "icon",
            },
        ],
        [
            "link",
            {
                rel: 'stylesheet',
                type: 'text/css',
                href: '//at.alicdn.com/t/font_2932340_r7zitafg82.css'
            }
        ],
        /*[
            "link",
            {
                rel: 'stylesheet',
                type: 'text/css',
                href: '//at.alicdn.com/t/font_2951154_btu3y5blqnn.css'
            }
        ]*/
    ],
    //这里使用本地主题
    // theme: path.resolve(__dirname, "../../Aurora-theme/lib/node/index.js"),
    theme: 'aurora',

    //站点title
    title: "I do not follow,i lives is always all you want",

    //设置站点语言
    lang: 'zh-CN',

    //设置运行npm run dev的端口
    port: 8080,

    /*
    * themeConfig是和主题相关的配置项，主题的所有配置，都是在此处进行配置的
    * */
    themeConfig: {
        /*
        * 这里配置导航栏项，建议将navbar提取出，比如navbar.js
        * 使用：https://aurora.xcye.xyz/homeconfig.html#%E5%AF%BC%E8%88%AA%E6%A0%8F
        * 因为navbar会存在很多的配置，所以推荐在该目录下，创建一个navbar.js文件，此文件中，使用module.exports = {navbar: XXX}
        * 可以参照docs/.vuepress/navbar.js使用，然后在config.js的themeConfig处引入该文件
        * */
        navbar: navbar,

        //禁用黑夜模式，当前版本未提供
        darkMode: false,

        //项目地址，会在顶部导航栏和侧边栏显示GitHub的地址
        repo: "https://github.com/qsyyke/vuepress-theme-aurora",

        //自定义GitHub的图标
        repoIconClass: 'aurora-navbar-github',

        //md文件的仓库地址
        docsRepo: 'https://github.com/qsyyke/aurora-docs',

        /*
        * 站点是否是使用github Actions自动部署，如果你使用github Actions完成自动部署，那么这里一定要为TRUE，为TRUE，假如你把此项目push
        * 到https://github.com/qsyyke/aurora-docs这个仓库中，那么你需要将docsRepo设置为https://github.com/qsyyke/aurora-docs，
        * 那么在文章页面，会根据根据你docsRepo和docsBranch设置正确的edit链接，也就是可以一键打开GitHub仓库中，此篇md文件的编辑
        * */
        githubActions: false,

        //md文件存放的仓库分支
        docsBranch: 'main',

        //顶部导航栏，你repo地址的超链接文本
        repoLabel: "Aurora-theme",

        //是否启用文章在线编辑，默认开启，会在每篇文章的底部，创建一个链接，指向该md在GitHub中的edit地址
        editLink: true,

        //在线编辑文字，点击编辑该文章的label
        editLinkText: "edit",

        //该md最后更新时间，使用github commit的提交时间
        lastUpdated: true,
        lastUpdatedText: "lastTime",

        //下面的都是主题自己的配置文件
        //logo旁文字颜色
        logoColor: "#2c3e50",

        //样式控制面板字体占位符，如果是国内用户，请使用中文
        showFont: "程",

        //首页中间hero图片地址
        heroImg: '/avatar.jpg',

        //logo图片地址
        logo: "/avatar.png",

        //文章懒加载图片 仅限文章，首页文章占位图片并不是这个
        lazyLoadingImg: "/ljz.gif",

        //这是首页文章列表懒加载图片
        homePageLazyLoadingImg: '/aurora-loading.gif',

        //是否启用定制首页随机一言，默认未开启，使用随机一言 接口为https://international.v1.hitokoto.cn/?c=b&max_length=45
        customRandomSay: false,

        //定制首页随机一言文字
        customRandomValue: 'Vuepress-theme-Aurora',

        //社交信息，首页PC端至多显示19个，手机端至多显示7个，侧边栏不影响，推荐自己创建一个socials.js
        socials: socials,

        //logo旁文字 默认值为Aurora
        logoTitle: "Aurora",

        //貌似没用的配置项┭┮﹏┭┮
        headTitle: "this is headTitle",

        //站点描述
        description: "vuepress-theme-Aurora是一款简洁，美观，功能强大的静态主题",

        //站点关键词，在后续版本中，还需优化 请使用英文状态下的逗号','隔开
        keyword: "vuepress主题,vuepress theme,生活琐事,二次元博客,简约博客,博客主题,静态主题",

        //样式控制面板打开之后，休眠多长时间自动关闭面板，单位毫秒
        slideTime: 300000,

        //随机一言接口 没用配置
        randomSaw: "https://international.v1.hitokoto.cn/?c=b&max_length=45",
        /*
        * 关于页面 推荐单独创建一个about.js文件存放关于页面信息
        * 使用：https://aurora.xcye.xyz/homeconfig.html
        * */
        about: [
            {
                bgImg: "/bg/about.jpg",
                bar: false,
                title: "我?",
                describe: [
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
                bgImg: '/bg/1.jpg',
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
                bgImg: '/bg/3.jpg',
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
                bgImg: 'https://pica.zhimg.com/80/v2-879728aa9d88d75de8ffe375c123de74_720w.jpg',
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
                bgImg: 'http://localhost:8080/bg/763311.jpg',
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
                    "theme-ccds主题是我自己独立开发，是一款基于vuepress，对默认主题进行了大量修改，我以前使用的是wordpress的博客主题，但是我对PHP不了解，想改成自己想要的主题，太难了，最近在学vue，正好看到可以使用vuepress来搭建 博客，就将默认主题改成现在这个样，并且喜欢vuepress的最主要原因是，它可以 直接将本地的markdown文档进行编译部署，我原来博客，我记了几个月的笔记，不太想再慢慢从本地复制到WordPress进行发布，以至于博客几个月没有发布文章了，虽然有技术可以解决，但还是喜欢vuepress，简直是懒癌福音，如果你喜欢的话，可以在我的GitHub进行下载，使用文档可以查看https://aurora.xcye.xyz，该主题已将所有的配置进行抽离，你现在看到的所有信息，都抽离在了一个配置文件中，但是目前还并不能做到开箱即用，如果使用的人多了，我可以进行修改，并且我自己也写了几个组件，包括文章页面看到的顶部图片，友情链接，海报分享功能等等 ，可以在你想使用的地方，直接使用这些组件就可以，该主题我加入了vuex，对前端不太了解，有很多css不对的地方，请大家多多包涵，Thanks♪(･ω･)ﾉ",
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

        //这是配置随机背景；颜色 可以不设置，有默认值 推荐单独创建一个random.js文件
        randomColor: randomColor,

        //样式控制面板至多显示多少个字体和字体颜色，推荐不超过8个
        maxFontColorArr: 8,

        //在样式控制面板中，显示的字体颜色集合
        fontColor: [
            "#2c3e50", "#42a5f5", "#8093f1", "#FF6EC7", "#FF7F00", "#8FBC8F", "#EAADEA",
            "#3299CC", "#CDCDCD", "#CC3299", "#FF7F00", "#2F4F4F",
        ],

        /*
        * 友情链接数组 推荐单独创建一个friendLinks.js存放友情链接的配置，可以参照navbar项的配置
        * 使用：https://aurora.xcye.xyz/page/friendlink.html
        * */
        friendLinks: friendLinks,

        //自己的站点信息 我自己的站点描述 会显示在友情链接的底部，推荐单独创建一个文件存放
        siteInformation: siteInformation,

        //需要排除的标签，自动生成的标签中，不会有这个标签，这是一个数组
        //excludeTag: ["note"],

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
        message: message,

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
        donate: donate,

        //评论配置 请自己查看文档配置 https://aurora.xcye.xyz/comment/
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
            avatar: "/avatar.jpg",
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
            custom: true,

            //文章顶部图片，数组，每次从数组中随机选择一张
            page: [
                "/bg/555260.png",
                '/bg/404901.png',
                '/bg/734386.png'
            ],
            //友情链接页面
            friend: [
                "/bg/669.png",
                '/bg/5332.png'
            ],
            //标签页面
            tag: [
                "/bg/763311.png"
            ],
            //心情页面
            mood: [
                "/bg/5849.png"
            ],
        },

        //首页背景图片数组，考虑到使用随机图片，打开网站速度变慢，所以移除随机图片，使用自己设置的图片链接
        homeWps: [
            "/bg/404901.png",
            "/bg/734386.png",
            "/bg/5332.png",
            '/bg/2.jpg',
            '/bg/3.jpg',
            '/bg/4.jpg',
            '/bg/5.jpg',

            "https://w.wallhaven.cc/full/v9/wallhaven-v9v3r5.jpg",
            "https://w.wallhaven.cc/full/z8/wallhaven-z8dg9y.png",
            "https://w.wallhaven.cc/full/6o/wallhaven-6op786.jpg",
            "https://w.wallhaven.cc/full/8o/wallhaven-8oky1j.jpg",
            "https://w.wallhaven.cc/full/pk/wallhaven-pkvw9p.jpg",
            "https://w.wallhaven.cc/full/9m/wallhaven-9m7kwd.png"
        ],
        /* homeWps: [
             '/bg/1.jpg',
             '/bg/2.jpg',
             '/bg/3.jpg',
             '/bg/4.jpg',
             '/bg/5.jpg',
         ],*/

        //手机端首页背景图片
        homeWpsMobile: [
            '/bg/4.jpg',
            '/bg/5.jpg',
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
        homePageImgApi: "https://www.dmoe.cc/random.php?return=text?time=-Infinity",

        //手机端侧边栏横线分割文字，默认为Aurora
        mobileCutText: "Aurora",

        //侧边栏标签处显示还是分类还是标签，只有两个值，默认为分类，如果为categories，那么就显示为类别，否则显示为标签
        sidebarTag: "categories",

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

        //v1.6.4新增配置
        sidebarAvatar: '/avatar.jpg',

        //1.7.1增加配置
        sugCountPerMin: 300,

        //v1.8.0新增配置项
        //是否展示文章推荐 默认展示
        showRecommend: true,

        //文章页面是否显示上一页，下一页 默认展示
        articlePagination: true,

        //这个global暂时没用
        global: {
            sidebar: {
                showStatus: true,
                perData: false,
                social: false,
                friendLink: true,
                navbar: false,
                latestPage: true,
                message: false,
                tag: true,
                siteData: false
            },
            footer: false,
        },

        //excludeTag: ["note"], 此配置项，不再需要使用

        //在统计文章，标签，类别的时候，需要排除的路径，只针对于根目录下的路径，
        excludePath: ['/footer.html','/v1.3.0/','/plugin/',"/node.html","/style.html"],

        //类别项是否包含文件夹名，默认包含
        categoriesIncludeFolderName: false,
        //首页波浪效果设置
        wave: {
            showWave: true
        },

        //顶部图片的气泡控制
        bubble: {
            show: true,

            //气泡数量 推荐0(不包括)到1之前的小数，
            bubbleNumber: 0.14,

            //气泡透明度 0到1之间的小数
            bubbleAlpha: 0.6,

            //透明度变化速度，越接近于0越好
            alphaChangeSpeed: 0.00001,

            //气泡大小，推荐0到1之间的值
            size: 0.4,

            //气泡大小变化速度 越小越好
            sizeChangeSpeed: 0.0002,

            //气泡上升速度
            riseSpeed: 0.4,

            //气泡颜色，白色rgb(255,255,255) 请传入255,255,255
            color: '255,255,255'
        },

        /**
         * 这里是配置统计阅读量的leanCloud配置，从1.9.0开始，将不再需要依赖waline的阅读统计,这里配置的appId,appKey,masterKey可以
         * 和vuepress-plugin-coze插件使用同一个应用
         * */
        leanCloud: {
            appId: '2A2Dyd2AffrnldhwftlEddVn-MdYXbMMI',
            appKey: 'qHYTbb91iOPLelyC9lpbXxLH',
            masterKey: 'eUwfvS3luIPnPiHS5SpEhDYr',
        },


        //这是v1.9.0新增加的功能 是否在文章页底部显示最后更新时间，贡献者，点击编辑,默认显示
        showPageMeta: true,

        /*
        * 以下是1.11.0版本新增的配置项
        * */

        //随机一言接口，请注意，一定要保证该接口直接返回Text文本，一定要保证该接口直接返回Text文本，而不是返回json
        randomSayApi: {
            method: 'GET',
            urlApi: 'https://v1.hitokoto.cn/?encode=text&c=a'
        },

        /*
        * 一下是v1.11.1版本增加的配置
        * */
        afDianUrl: 'https://afdian.net/@qsyyke',//你的爱发电个人页面地址

        /*
        * 下面这些是v1.12.0版本新增的配置项
        * */

        // 首页置顶文字
        homeTopText: '置顶',

        //侧边栏一句话描述
        sidebarDesc: '喜欢动漫，Coding目前是一名大三学生,CS专业,坐标西南边陲',

        /*
        * 下面的是v1.13.0版本的配置
        * */

        /*
        * 如果你以前使用的是其他的主题或者工具，比如hexo的主题，reco主题等等，发现frontmatter中的标签并不是使用tag字段设置，而是使用比如tags进行设置的
        * 那么你可以像customTagName: 'tags'自定义标签的字段，此配置仅仅针对于md文件设置的标签并不是使用tag字段，请注意，谨慎使用
        * */
        //customTagName: 'tags',

        /*
       * 如果你以前使用的是其他的主题，比如hexo，reco等等，发现frontmatter中的类别并不是使用categories字段，而是使用比如category进行设置的
       * 那么你可以像customCategoriesName: "category"这样自定义类别的字段，此配置仅仅针对于md文件设置的类别并不是使用categories字段，请注意，谨慎使用
       * */
        //customCategoriesName: "category"

        /*
        * 下面这个是v1.13版本新增的配置项
        * */

        //是否显示关于页面气泡效果 默认开启
        showAboutPageBubble: false
    }
};
