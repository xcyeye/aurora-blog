module.exports = {
    navbar: [
        {
            text: 'spring',
            children: [
                {
                    children: [
                        "/spring/springmvc/springmvc重定向和请求转发操作.md",
                        "/spring/springmvc/SSM整合开发.md",
                        "/spring/springmvc/url-pattern设置.md",
                        "/spring/springmvc/执行流程分析.md",
                        "/spring/springmvc/核心技术.md",
                        "/spring/springmvc/README.md",
                    ],
                    text: "springmvc",
                },
                {
                    children: [
                        "/spring/spring5/aop面向切面编程.md",
                        "/spring/spring5/mybatis和spring整合.md",
                        "/spring/spring5/README.md",
                        "/spring/spring5/注解方式注入.md",
                    ],
                    text: "spring5",
                },
                {
                    children: [
                        "/spring/springboot/web开发.md",
                        "/spring/springboot/yaml.md",
                        "/spring/springboot/单元测试.md",
                        "/spring/springboot/启动原理.md",
                        "/spring/springboot/嵌入式servlet服务器.md",
                        "/spring/springboot/指标监控.md",
                        "/spring/springboot/数据访问.md",
                        "/spring/springboot/README.md",
                        "/spring/springboot/注意.md",
                        "/spring/springboot/注解.md",
                        "/spring/springboot/高级特性.md",
                    ],
                    text: "springboot",
                }
            ],
        },
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