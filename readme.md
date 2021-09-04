# 首页配置

当正确设置主题，并且将<a href="https://github.com/qsyyke/vuepress-theme-ccds/blob/main/docs/README.md">readme.md</a>赋值到`docs/readme.md`文件后，首页图展示如下

![image-20210831140859682](http://ooszy.cco.vin/img/blog-note/image-20210831140859682.png?x-oss-process=style/pictureProcess1)

首页配置会从以下进行介绍

[toc]

## 导航栏

导航栏配置可以查看vuepress官方配置<a href="https://v2.vuepress.vuejs.org/zh/reference/default-theme/config.html#navbar">入口</a>，可以借鉴一下我的配置

```js
//docs/.vuepress.config.js
module.exports = {
  themeConfig: {
    navbar: [
      {
            text: '数据库',
            children: [
                {
                    text: 'redis',
                    link: '/redis/'
                },
                {
                    text: 'mysql',
                    link: '/'
                }
            ]
        },
        {
            text: '其他',
            children: [
                {
                    text: '问题',
                    link: '/issue/'
                },
                {
                    text: '捐款系统整理',
                    link: '/donationSystem/'
                },
                {
                    text: '自然语言',
                    link: '/sdk/'
                },
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
            text: '记录',
            link: '/mood'
        }
    ],
  },
}
```

其中`/about,/link,/tag,/mood`是主题新增的路由，对应关于页面，友情链接，标签，心情页面，所以请在navbar上，添加



## logo

![image-20210831141849241](http://ooszy.cco.vin/img/blog-note/image-20210831141849241.png?x-oss-process=style/pictureProcess1)

导航栏logo可以在`config.js`文件中，进行设置

```js
module.exports = {
  themeConfig: {
    // Public 文件路径
    logo: '/images/hero.png',
    // URL
    logo: 'https://vuejs.org/images/logo.png',
  },
}
```

如果没有在此`config.js`文件中进行设置，默认使用<a href="https://ooszy.cco.vin/img/blog-public/ccds_64.ico">default</a>



### logo文字

对于logo旁边文字，需要在`docs/readme.md`的`logoTitle`进行设置，默认值为`ccds`

```yml
logoTitle: qsyyke
```



![image-20210831142236064](http://ooszy.cco.vin/img/blog-note/image-20210831142236064.png?x-oss-process=style/pictureProcess1)

并且支持设置字体颜色，配置项为`logoColor`

```yml
logoColor: '#2c3e50'
```





## 中间头像

![image-20210831142850834](http://ooszy.cco.vin/img/blog-note/image-20210831142850834.png?x-oss-process=style/pictureProcess1)

此头像通过`heroLogo`进行修改，并不是使用logo图片地址，默认图片使用<a href="https://ooszy.cco.vin/img/blog-public/avatar.jpg">default</a>

```yml
heroLogo: https://ooszy.cco.vin/img/blog-public/avatar.jpg
```





## 自定义一言

![image-20210831143051671](http://ooszy.cco.vin/img/blog-note/image-20210831143051671.png?x-oss-process=style/pictureProcess1)

```yml
mood: 青衫烟雨客 #无默认值
showHomeMood: false #是否显示，默认不显示
```



- 默认只在首页进行显示，如果你在其他页面使用`Home.vue`组件，这个不会显示

## 随机一言

![image-20210831143353819](http://ooszy.cco.vin/img/blog-note/image-20210831143353819.png?x-oss-process=style/pictureProcess1)

该随机一言打印效果使用<a href="https://github.com/pengqiangsheng/easy-typer-js">easy-typer-js</a>

随机一言默认使用接口请求随机一言

```yaml
#随机一言接口
randomSaw: https://international.v1.hitokoto.cn/?c=b&max_length=45

#设置如何获取值
randomSawQuery: hitokoto

#请求方法
method: get

#使用自定义随机一样
customRandomSay: true #true表示使用自定义随机一样
customRandomValue: 青衫烟雨客 #只有true时，才生效
```

---

如果接口响应数据格式为

![image-20210831144952230](http://ooszy.cco.vin/img/blog-note/image-20210831144952230.png?x-oss-process=style/pictureProcess1)

那么`randomSawQuery`就是hitokoto

---

- 默认只在首页显示

- 文字两边并没有提供设置，如果自定义，请在`Home.vue`中进行修改

    ```vue
    <div v-if="isHome"><span>「 {{obj.output}}」</span></div>
    ```

- 打印间隔时间，并未在`readme.md`中提供设置，默认设置为1500，如需修改，请在`Home.vue`中修改`intervalTime: 1500`对象值

    ```js
    intervalTime: 1500
    ```



## 社交

组件位置`docs/.vuepress/theme/lib/client/components/child/HomeSocial.vue`

![image-20210831150058756](http://ooszy.cco.vin/img/blog-note/image-20210831150058756.png?x-oss-process=style/pictureProcess1)

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





### 全部代码

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





## 页脚

页脚组件位置`docs/.vuepress/theme/lib/client/components/global/Footer.vue`

页脚有两种展示效果

- 首页

    ![image-20210831151756160](http://ooszy.cco.vin/img/blog-note/image-20210831151756160.png?x-oss-process=style/pictureProcess1)

- 其他页面

    ![image-20210831151827669](http://ooszy.cco.vin/img/blog-note/image-20210831151827669.png?x-oss-process=style/pictureProcess1)





```yaml
isShowFooter: true #是否展示页脚，如果你自己使用Footer.vue组件，此项没用

#页脚显示，以HTML的形式显示 并且首页只显示此数组中的两项，只显示两项
footer: 
  - Copyright © by qsyyke All Rights Reserved.
  - Theme <a href="https://www.cco.vin">ccds</a> by qsyyke
  - "<a target='_blank' href='http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142' style='display:inline-block;text-decoration:none;height:20px;line-height:20px;'><img src='' style='float:left;'/><p style='float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px;'>滇公网安备 53060202000142号</p></a>"

#是否展示运行时间 默认为true
isShowRunTime: true 

#开始时间
startRunTime: 8/7/2021 12:22:00
#运行时间前缀
prefixRuntime: 小破站已运行
```



### 组件设置

因为Footer.vue是一个全局组件，可以直接使用

```vue
<Footer :theme-property="themeProperty"
        :is-home="frontmatter.home"
        :is-show-footer="isShowFooter">
</Footer>
```

- theme-property主题配置
- is-home是否为首页，决定页脚展示效果
- is-show-footer 是否展示页脚

如果需要修改运行多少天等等，需要修改代码，在`Footer.vue`中

```js
this.runTime = this.prefixRuntime + A + "天" + B + "小时" + C + "分" + D + "秒";
```

