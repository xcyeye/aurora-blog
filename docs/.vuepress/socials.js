module.exports = {
    //这里是将config.js中的社交信息部分单独提取出来，方便配置
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
    ]
}