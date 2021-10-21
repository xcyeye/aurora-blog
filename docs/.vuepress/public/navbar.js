module.exports = {
    navbar: [

        {
            text: '快速开始',
            link: '/readme/'
        },
        {
            text: "所有配置",
            link: '/home/config.html'
        },
        {
            text: '问题和bug',
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
            children: [
                {
                    children: [
                        "/home/README.md",
                    ],
                    text: "home",
                },
                {
                    children: [
                        "/config/comment/README.md",
                        "/config/feature/README.md",
                        "/config/other/README.md",
                        "/config/page/README.md",
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
            link: "/about"
        },
        {
            text: 'link',
            link: '/link'
        },
        {
            text: 'tag',
            link: '/tag'
        },
        {
            text: 'chat',
            link: '/mood'
        },
        {
            text: "photo",
            link: '/photo'
        }
    ],
}