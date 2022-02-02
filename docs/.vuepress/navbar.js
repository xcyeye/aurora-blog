module.exports = {
    //这里是将config.js中的顶部导航栏配置单独提取出来，方便配置
    navbar: [
        {
            //设置标签
            text: '快速开始',
            //设置此标签的链接
            link: '/readme/',
            //设置此导航栏的图标，请注意，导航图标只对一级标题有用，
            iconClass: 'aurora-navbar-si-glyph-dial-number-1'
        },
        {
            text: '所有配置及必读',
            iconClass: 'aurora-navbar-blaze-line',
            children: [
                //在这里面的是二级标题，不能为二级标题设置图标
                {
                    text: '问题及必读',
                    children: [
                        '/issue/',
                    ]
                },
                {
                    text: "所有配置",
                    children: [
                        '/home/config.md'
                    ]
                },
                {
                    text: "主题样式配置",
                    children: [
                        '/style/code-style.md',
                        '/style/icon.md',
                        '/style/live2d.md',
                        '/style/style.md',
                        '/feature/registercom.md'
                    ]
                }
            ]
        },
        {
            text: '基本知识',
            iconClass: 'aurora-navbar-si-glyph-billiard-ball',
            children: [
                {
                    text: '静态文件',
                    children: [
                        '/base/public.md',
                    ]
                },
                {
                    text: "插件使用",
                    children: [
                        '/base/plugin.md'
                    ]
                },
                {
                    text: "docs目录结构",
                    children: [
                        '/base/docs.md'
                    ]
                },
                {
                    text: 'cmd管理员权限',
                    children: [
                        '/base/admin.md'
                    ]
                },
                {
                    text: 'npm常见命令',
                    children: [
                        '/base/command.md'
                    ]
                }
            ]
        },
        {
            text: '问题和bug',
            iconClass: 'aurora-navbar-shoulijindu-xuanzhong',
            children: [
                {
                    text: 'node安装教程',
                    children: [
                        //'/readme/introduce.md',
                        '/node.md'
                    ]
                },
                {
                    text: 'bug',
                    children: [
                        '/issue/bug.md',
                    ]
                },
                {
                    text: '更新日志',
                    children: [
                        '/issue/CHANGELOG.md'
                    ]
                },
                {
                    text: '运行常见错误',
                    children: [
                        '/issue/common.md'
                    ]
                }

            ]
        },
        {
            text: '其他配置',
            iconClass: 'aurora-navbar-weather',
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
                    text: 'webpack和vite切换',
                    children: [
                        '/base/vite-webpack.md'
                    ]
                }
            ],
        },
        {
            text: '插件',
            iconClass: 'aurora-navbar-si-glyph-egg',
            children: [
                {
                    children: [
                        "/plugin/archive/readme.md",
                    ],
                    text: "时间轴",
                },
                {
                    children: [
                        "/plugin/bubble/readme.md",
                    ],
                    text: "浪漫气泡",
                },
                {
                    children: [
                        "/plugin/coze/readme.md",
                    ],
                    text: "说说",
                },
                {
                    children: [
                        "/plugin/player/readme.md",
                    ],
                    text: "音乐播放器",
                },
                {
                    text: '看板娘',
                    children: [
                        '/style/live2d.md'
                    ]
                }
            ],
        },
        {
            text: 'life',
            iconClass: 'aurora-navbar-hua2',
            children: [
                {
                    children: [
                        {
                            text: 'Me',
                            link: "/about",
                        }

                    ],
                    text: "我?",
                },
                {
                    text: '说说',
                    children:[
                        {
                            text: 'chat',
                            link: '/mood'
                        }
                    ],
                },
                {
                    text: "相册",
                    children: [
                        {
                            text: 'photo',
                            link: '/photo'
                        }
                    ],
                },
            ],
        },
        {
            text: 'page',
            iconClass: 'aurora-navbar-a-ziyuan107',
            children: [
                {
                    text: '标签',
                    children: [{
                        text: 'tag',
                        link: '/tag'
                    }],
                },
                {
                    text: "时间轴",
                    children: [
                        {
                            text: 'archive',
                            link: '/archive'
                        }
                    ],
                },
            ],
        },

        {
            text: '友情链接',
            link: '/link',
            iconClass: 'aurora-navbar-guide'
        },
        {
            text: '案例',
            link: '/use.html',
            iconClass: 'aurora-navbar-si-glyph-load'
        },
        {
            text: 'Aurora',
            link: 'https://github.com/vuepress-aurora/vuepress-theme-aurora',
            iconClass: 'aurora-navbar-github1'
        }
    ]
}