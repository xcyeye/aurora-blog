# 社交


组件位置`docs/.vuepress/theme/lib/client/components/child/HomeSocial.vue`

![image-20210831150058756](http://ooszy.cco.vin/img/blog-note/image-20210831150058756.png?x-oss-process=style/pictureProcess1)



## 配置

```yaml
socialMaxLength: 7 #设置最多展示多少个

socials: [
 {	#连接
    aHref: tencent://message/?uin=2291308094,
    #展示图片
    imgSrc: https://ooszy.cco.vin/img/ico/qq.svg,
    #是否在首页展示
    isHome: true,
    #是否展示，如果此值为false，那么不会再首页和关于页面展示
    show: true
  },
 {
    aHref: javascript:;,
    //imgSrc: /assets/img/ico/wechat.svg,
    imgSrc: https://ooszy.cco.vin/img/ico/wechat.svg,
    #如果需要点击不跳转，显示图片，如二维码，则加上下面图片地址
    showImgSrc: https://ooszy.cco.vin/img/blog-public/wechat.jpg,
    isHome: true,
    show: true
  }
]
```





## 全部代码

```yaml
socialMaxLength: 7 #设置最多展示多少个
socials: [
 {
    aHref: tencent://message/?uin=2291308094,
    imgSrc: https://ooszy.cco.vin/img/ico/qq.svg,
    isHome: true,
    show: true
  },
 {
    aHref: javascript:;,
    //imgSrc: /assets/img/ico/wechat.svg,
    imgSrc: https://ooszy.cco.vin/img/ico/wechat.svg,
    showImgSrc: https://ooszy.cco.vin/img/blog-public/wechat.jpg,
    isHome: true,
    show: true
  },
 {
    aHref: https://github.com/qsyyke/,
    imgSrc: https://ooszy.cco.vin/img/ico/github.svg,
    isHome: true,
    show: true
  },
 {
    aHref: https://stackoverflow.com/,
    imgSrc: https://ooszy.cco.vin/img/ico/stack.svg,
    isHome: false,
    show: true
  },
 {
    aHref: "https://space.bilibili.com/483962286",
    imgSrc: https://ooszy.cco.vin/img/ico/bilibili-.svg,
    isHome: true,
    show: true
  },
 {
    aHref: https://music.163.com/#/user/home?id=1411050784,
    imgSrc: https://ooszy.cco.vin/img/ico/wangyiyunyinle1.svg,
    isHome: true,
    show: true
  },
 {
    aHref: mailto:2291308094@qq.com,
    imgSrc: https://ooszy.cco.vin/img/ico/email.svg,
    isHome: false,
    show: false
  }
]
```