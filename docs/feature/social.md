# 社交


![image-20211011151211549](https://ooszy.cco.vin/img/blog-note/image-20211011151211549.png?x-oss-process=style/pictureProcess1)

<img src="https://ooszy.cco.vin/img/blog-note/image-20211011151225054.png?x-oss-process=style/pictureProcess1" alt="image-20211011151225054" style="zoom:50%;" />

> 组件位置
>
> docs/.vuepress/theme/lib/client/components/child/side/HomeSidebarSocialItem.vue



其中，手机端至多显示7个(`首页`)，侧边栏等不受影响，PC端首页至多只能显示19个，如果超过，则不会显示，侧边栏不受影响



## 配置

```js
module.exports = {

    themeConfig: {
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
            }
        ],

    }
}
```



::: tip

配置项解释，请查看

:::

## 修改图标

社交图标全部使用的是阿里图标库，我选了一些最常用的图标，你可以自己挑选

地址为[iconfont Demo (cco.vin)](http://aurora-font.cco.vin/)



```js

module.exports = {

    themeConfig: {
        socials: [
            {
                aHref: "javascript:;",
                showImgSrc: "https://ooszy.cco.vin/img/blog-public/wechat.jpg",
                isHome: true,
                show: true,
                symbol: '#icon-weixin',
                sidebar: true
            },
        ]

    }
}
```

<img src="https://ooszy.cco.vin/img/blog-note/image-20211011155752690.png?x-oss-process=style/pictureProcess1" alt="image-20211011155752690" style="zoom:50%;" />



![image-20211011155915438](https://ooszy.cco.vin/img/blog-note/image-20211011155915438.png?x-oss-process=style/pictureProcess1)

## 评论配置

[评论配置](../comment/)

