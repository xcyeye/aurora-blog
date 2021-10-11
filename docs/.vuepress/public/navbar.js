module.exports = {
    navbar: [
        {
            text: '快速开始',
            link: '/home/'
        },
        /*{
            text: '主题配置',
            link: '/config/'
        },*/
        {
            text: '主题特征配置',
            children: [
                '/config/feature/index.html',
                '/config/feature/donate.html',
                '/config/feature/feature-config.html',
                '/config/feature/image.html',
                '/config/feature/recommend.html',
                '/config/feature/seo.html',
                '/config/feature/social.html',
            ]
        },
        {
            text: '页面配置',
            children: [
                '/config/page/index.html',
                '/config/page/about.html',
                '/config/page/ad.html',
                '/config/page/friendlink.html',
                '/config/page/mood.html',
                '/config/page/tag.html',
                '/config/page/page.html',
            ]
        },
        {
            text: '其他配置',
            children: [
                '/config/other/index.html',
                '/config/other/footer.html',
                '/config/other/message.html',
            ]
        },
        {
            text: "关于",
            link: "/about"
        },
        {
            text: '友情链接',
            link: '/link'
        },
        {
            text: '标签',
            link: '/tag'
        },
        {
            text: '心情',
            link: '/mood'
        },
        {
            text: "photo",
            link: '/photo'
        }
    ],
}