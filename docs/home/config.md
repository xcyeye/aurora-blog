---
categories: [主题配置,配置项,配置总览]
stick: true
---



# 所有配置

::: tip

主题从V1.3.2版本之后，就对页面进行重大改变，将主题配置移到`docs/.vuepress/config.js`内，并且加入了多种功能，如自动生成文章目录，侧边栏等效果...

`以下配置，没有特殊说明都是在docs/.vuepress/config.js ---> themeConfig内进行配置`



推荐阅读

> [部署](./deploy.md)
>
> [主题目录结构](./directory-structure.md)
>
> [bug](../issue/bug.md)
>
> [更新日志](../issue/CHANGELOG.md)
>
> [主题问题解决](../issue/)
>
> [主题写作技巧](../learn/)
>
> [主题自定义](../feature/registercom.md)

:::

## 首页

<a href="https://aurora.cco.vin/config/">点击查看配置</a>

### home

- Boolean

> true为首页，false反之
>
> `此项在docs/readme.md`内配置，用于设置首页位置



### logoColor

- String 

> 首页左上角Logo图旁文字颜色



### logoTitle

- String

> 左上角，logo旁标题文字
> 


### defaultBorderRadius

- Number

> 默认的圆角，传入数字
> 

### defaultOpacity

- Number

> 默认的透明度，传入0到1之间的小数，0表示全透明
> 

### isHomePageFollow

- boolean

> 首页文章列表透明度是否跟随样式面板改变，true表示跟随，全白色,false表示不跟随
> 

### defaultBlur

- Number

> 默认模糊度(`0 到 1之间`)

### showFont

- String

> 圆角等控制面板，字体切换的占位符文字，推荐一个字



### faviconIco

- String

> 网站顶部favicon图片链接，如https://ooszy.cco.vin/img/blog-public/ccds_64.ico
>
> `v1.3.2移除，改在config.js的head内配置`

::: details 实例

```js
module.exports = {
    ...
    head: [
        [
            "link",
            {
                href: "https://ooszy.cco.vin/img/ico/yuan.png",
                rel: "icon",
            },
        ],
    ],
}
```

:::



### customRandomSay

- Boolean

> 是否自定义随机一言，true表示不会使用随机一言接口
>
> 默认使用的接口为:https://international.v1.hitokoto.cn/?c=b&max_length=45，感谢大佬提供的一言接口



### customRandomValue

- String

> 自定义一言文字，只有customRandomSay为true时有效



### showHomeMood

- Boolean

> 是否在首页显示心情，true开启，需要设置文字
>
> `v1.3.2已移除`

### mood

- String

> 首页中间，心情文字
>
> `v1.3.2已移除`





### heroLogo

- String

> 首页中间图片链接
>
> `v1.3.2已移除`



### heroImg

- String

> 首页中间图片链接，默认https://ooszy.cco.vin/img/blog-public/avatar.jpg
>



### logo

- String

> 左上角logo图片



### cancelIcoHref

- String

> 圆角字体控制面板，取消按钮图片链接
>
> `v1.3.2已移除，改用阿里字体图标，如果修改，请f12，修改对应值`，组件位置为docs/.vuepress/theme/lib/client/components/child/home/StyleMenu.vue



### description

- String 

> 首页和非文章页面的description描述文字



### keyword

- String

> 首页和非文章页面的keyword，请使用`,`分割开





### slideTime

- Number

> 圆角控制面板，隐藏时间，单位毫秒



### randomSaw

- String

> 随机一言接口 https://international.v1.hitokoto.cn/?c=b&max_length=45


### randomSawQuery

- String

> 从随机一言接口中，如何获取值，如上面`randomSaw`接口，输入hitokoto(`后续版本会优化此项`)

### method

- String

> 随机一言请求接口方法



animeOption: {
  baseURL: https://api.vvhan.com/api/acgimg?type=json,
  method: get,
  timeout: 5000,
  query: imgurl
}


### isFitter

- Boolean

> 默认是否启用毛玻璃效果，true表示开启





## 文章

<a href="https://aurora.cco.vin/config/page/page.html#%E9%A1%B6%E9%83%A8%E5%9B%BE%E7%89%87">点击查看文章配置</a>

### lazyLoadingImg

- String

> 文章懒加载占位符图片链接，如https://ooszy.cco.vin/img/blog-public/ljz.gif


### defaultTitleColor

- String

> 文章顶部标题颜色，如#8093f1
>
> `v1.3.2已移除`



### coverUrl

- String

> 首页文章列表图片地址,详细配置说明，请点击此处<a href="">click me</a>



## 社交

<a herf="https://aurora.cco.vin/config/page/about.html#%E7%A4%BE%E4%BA%A4">点击查看社交配置</a>

### socials

- Array

> 数组对象

::: details 点击查看实例
```js
module.exports = {
   	...
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
:::

### aHref

- String

> 社交链接地址

### imgSrc

- String

> 社交图片地址
>
> `v1.3.2已移除`

### isHome

- Boolean

> 是否在首页显示，true表示会在首页显示，其他页面也会显示，为false，首页不会显示，其他页面会显示

### show

- Boolean

> 是否显示此社交，控制全局



### sidebar

- Boolean

> 是否在侧边栏展示，手机侧边栏无效



### symbol

- String

> 社交图标使用的是阿里图标库，目前只添加一些常用彩色图标，后期会添加，图标地址http://aurora-font.cco.vin/
> 


::: details 点击查看配置

<img src="https://ooszy.cco.vin/img/blog-note/image-20211011103250195.png?x-oss-process=style/pictureProcess1" alt="image-20211011103250195" style="zoom:50%;" />



<img src="https://ooszy.cco.vin/img/blog-note/image-20211011103342575.png?x-oss-process=style/pictureProcess1" alt="image-20211011103342575" style="zoom:50%;" />

> `symbol`值就填上面的#aurora-Email，那么社交图标便会使用此图标进行显示



:::

### socialMaxLength

- Number

> 社交展示的最大个数



### showImgSrc

- String

> 鼠标移动到图标上面，需要显示的图片链接，适用于微信二维码这种

## 关于页面

<a href="https://aurora.cco.vin/config/page/about.html#%E7%A4%BE%E4%BA%A4">点击查看关于页面</a>

### about

- Array

> 关于页面数组对象



::: details 点击查看实例
```js
module.exports = {

    themeConfig: {
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
    }
}
```
:::



### bar

- Boolean

> 此项是否展示为条状 如果为true，请正确设置



### title

- String

> 标题



### tag

- Array

> 字符串数组，此项的标签





### showTag

- Boolean

> 是否显示标签，true表示显示



### describe

- Array

> 字符串数组或者对象数组，如果length为1，不会显示编号，一行展示
>
> 如果为对象数组，会以条状展示，属性有`name`,`score`



### name

- String

> 条状描述文字



### score

- Number

> 条状展示值，最终会将其转化为百分数，表示条状长短



## 背景



### randomWallpaper

- String

> PC端，随机背景地址
>
> `v1.3.2已移除`



### randomWallpaperMb

- String

> 手机端随机背景地址
>
> `v1.3.2已移除`



### homePageImgApi

- String

> 首页文章列表封面图及文章页面，标签页面，说说页面，友情链接页面顶部图片(`顶部图片仅在customTopImg没有配置以及customTopImg ---> custom: false情况下生效，否则顶部图片将使用自己设置的图片`)api接口
>
> 默认https://api.ixiaowai.cn/api/api.php



### homeWps

- Array

> PC端网站背景图片数组，从v1.3.2开始，移除使用随机图片接口作为背景图片，改用自己设置



::: details 配置

```js
module.exports = {
    themeConfig: {
        ....
        homeWps: [
                    'https://ooszy.cco.vin/img/blog-note/illust_74502138_20211008_183343.png',
                    "https://picoss.cco.vin/animate/wall/404901.png",
                    "https://picoss.cco.vin/animate/wall/734386.png",
                    "https://picoss.cco.vin/animate/wall/5332.png",
                    "https://picoss.cco.vin/animate/wall/6202.png",
                ],
    }
}
```



:::



### homeWpsMobile

- Array

> 手机端网站背景图片



::: details 配置

```js
module.exports = {

    themeConfig: {
        homeWpsMobile: [
            "https://ooszy.cco.vin/img/blog-note/881770.jpg",
            "https://ooszy.cco.vin/img/blog-note/109136.jpg",
            "https://ooszy.cco.vin/img/blog-note/929842.jpg",
        ],
    }
}
```



:::





### customTopImg

- Object

> 定制顶部图片,为null，将使用随机图片接口，默认为https://api.ixiaowai.cn/api/api.php

::: details 配置

```js
module.exports = {

    themeConfig: {
        customTopImg: {
            //是否启用定制顶部图片，控制全局，如果关闭，那么将使用随机图片，随机图片接口可以自己设置
            custom: true,

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
    }
}
```



:::



### custom

- Boolean

> 是否定制顶部图片，为FALSE，将使用默认随机图片接口



### page

- Array

> 文章顶部图片链接数组



### friend

- Array

> 友情链接页面顶部图片数组



### tag

- Array

> 标签页面顶部图片数组



### mood

- Array

> 说说页面顶部图片数组

## 字体和颜色



### randomColor

- Array

> 随机颜色字符串数组，文章推荐，tag页面，关于页面条状背景色...，并不会控制字体颜色



::: details 点击查看代码
```js
module.exports = {

    themeConfig: {
        randomColor: [
            "#ffcad4", "#d8e2dc", "#8d99ae", "#b8f2e6", "#84c7d0", "#aed9e0", "#00b4d8",
            "#caf0f8", "#fbc4ab", "#fdc5f5", "#84dcc6", "#a9def9", "#fcf6bd", "#f0a6ca",
            "#b9faf8", "#42a5f5", "#ff9800", "#b39ddb", "#6d45bb", "#b388ff", "#1565c0",
            "#26c6da", "#5e548e", "#90f1ef", "#5b5f97", "#bbe6e4", "#42bfdd", "#72ddf7",
            "#8093f1", "#9ed8d8", "#7ea8be", "#ef90b3", "#b892ef", "#c0b9dd", "#c0d9dd",
            "#75c9c8", "#ded9e2", "#b5e2fa", "#62b6cb", "#5fa8d3", "#0fa3b1", "#b5e2fa",
            "#5fa8d3", "#62b6cb", "#b892ff",
        ],
    }
}
```
:::



### maxFontColorArr

- Number

> 圆角控制面板，切换颜色最大个数，也控制这字体最大个数



### fontColor

- Array

> 字体随机颜色字符串数组，请传入颜色值



### fontFamily

- Array

> 字体字符串数组



::: warning

如果想要使用自己新引入的字体，请在`docs/.vuepress/config.js --->head内引入自己的css样式 `

然后在`fontFamily`数组中，加入引入的字体名称，如果字体文件太大的话，网站加载速度会受到影响

:::



## 友情链接

<a href="https://aurora.cco.vin/config/page/friendlink.html#%E9%A1%B6%E9%83%A8%E5%9B%BE%E7%89%87">点击查看友情链接页面</a>

### friendLinks

- Array

> 数组对象，所有友情链接在此处进行配置



### title

- String

> 标题



### url

- String

> 朋友站点地址



### logo

- String

> 朋友站点logo图



### describe

- String

> 朋友站点描述



::: details 点击查看代码
```js
module.exports = {

    themeConfig: {
        friendLinks: [
            {
                //网站标题
                title: "XI溪",

                //站点链接
                url: "http://www.xiaoxuya.top",

                //站点logo
                logo: "https://www.xiaoxuya.top/img/logo.png",

                //站点描述
                describe: "人生若只是初见,何事秋风悲画扇",
            },
            {
                title: "左眼会陪右眼哭の博客",
                url: "http://qkongtao.cn/",
                logo: "http://qiniu.qkongtao.cn/2020/12/d11-e1628358435552.png",
                describe: "干嘛这么想不开，要在脸上贴个输字！",
            },
            {
                title: "[ Blog We]",
                url: "https://blogwe.com/",
                logo: "https://blogwe.com/favicon.ico",
                describe: "博客大全-做最好的博客导航！",
            },
            {
                title: "I Am I",
                url: "https://5ime.cn",
                logo: "https://cdn.jsdelivr.net/gh/5ime/img/avatar.jpg",
                describe: "永远相信美好的事情即将发生",
            },
            {
                title: "Davinci的红茶馆",
                url: "https://davincievans.top/",
                logo: "https://cdn.jsdelivr.net/gh/DavinciEvans/Imgs-bed@master/gallery/avatar.jpg",
                describe: "You are all stardust.",
            },
            {
                title: "皮皮凛の小窝",
                url: "https://owomoe.net/",
                logo: "https://cdn.jsdelivr.net/gh/AyagawaSeirin/Assets/img/logo.jpg",
                describe: "永远相信美好的事情即将发生~",
            },
            {
                title: "月月月子喵",
                url: "https://haozi.moe",
                logo: "https://haozi.moe/css/images/logo_christmas.png",
                describe: "可爱的月子酱",
            },
            {
                title: "疫情在线捐款系统",
                url: "http://yq.vipblogs.cn/",
                logo: "https://ooszy.cco.vin/img/blog-public/avatar.jpg",
                describe: "基于echarts的疫情捐款系统",
            },
            {
                title: "疫情在线捐款系统后台登录",
                url: "http://admin.vipblogs.cn/",
                logo: "https://ooszy.cco.vin/img/blog-public/avatar.jpg",
                describe: "该捐款系统后台登录",
            },
        ],
    }
}
```
:::

### siteInformation

- Object

> 自己站点描述信息



#### url

- String

> 自己站点url



#### logo

- String

> 自己站点logo图片地址



#### describe

- String

> 自己站点描述信息



## ico图标(v1.3.2已移除)



### ico

- Object

> 友情链接，关于页面，还有圆角控制面板ico图标地址
>
> `v1.3.2已移除`



### linkIco

- String

> 友情链接图标地址
>
> `v1.3.2已移除`

也就是这一坨

![image-20210912210416750](https://ooszy.cco.vin/img/blog-note/image-20210912210416750.png?x-oss-process=style/pictureProcess1)



### aboutIco

- String

> 关于页面，图标地址
>
> `v1.3.2已移除`



### homeWelcome(v1.3.2已移除)

- Array

> 字符串数组，圆角，字体控制面板，背景切换和开启毛玻璃效果图标，源码中，是通过数组顺序进行使用的，传入时，请注意顺序

![image-20210912210648219](https://ooszy.cco.vin/img/blog-note/image-20210912210648219.png?x-oss-process=style/pictureProcess1)





## 标签

<a herf="https://aurora.cco.vin/config/page/tag.html#%E6%A0%87%E7%AD%BE">点击查看标签页面配置</a>

### excludeTag

- Array

> 字符串数组，标签中，需要排除的链接分割文字，如
>
> ```js
> module.exports = {
> 
>        themeConfig: {
>            excludeTag: ["note"],
>        }
> }
> ```
>
> 如果有有一个文章url为`https://blog.cco.vin/note/spring/springboot.html`，那么在标签列表中，不会显示`note`，但会显示`spring`

### split

- String 如`~`

> tag页分割符

![image-20210912212058062](https://ooszy.cco.vin/img/blog-note/image-20210912212058062.png?x-oss-process=style/pictureProcess1)



## 页脚

<a href="https://aurora.cco.vin/config/#%E9%A1%B5%E8%84%9A">点击查看页脚配置</a>

### footer

- Array

> 字符串数组，页脚显示的HTML，支持传入HTML，对于首页，会选择数组中的第一个和第二个显示



::: details 点击查看代码
```js
module.exports = {

    themeConfig: {
        footer: [
            "Copyright © by qsyyke All Rights Reserved.",
            "<a target='_blank' href='http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142' style='display:inline-block;text-decoration:none;height:20px;line-height:20px;'><img src='' style='float:left;'/><p style='float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px;'>滇公网安备 53060202000142号</p></a>",
            "<a href='https://www.upyun.com/?utm_source=lianmeng&utm_medium=referral'>本网站由<img style='height: 24.1px;width: 55.3px;' src='https://ooszy.cco.vin/img/blog-note/%E5%8F%88%E6%8B%8D%E4%BA%91_logo5.png?x-oss-process=style/pictureProcess1'>提供CDN加速服务</a>",
        ],
    }
}
```
:::

### isShowFooter

- Boolean

> 是否显示页脚，控制全局



### isShowRunTime

- Boolean

> 是否显示运行时间



### startRunTime

- String

> 网站开始时间，请注意格式，如`8/7/2021 12:22:00`



### prefixRuntime

- String

> 运行时间描述，如小破站已运行



### keyRule

- Number

> 如果没有设置key值，那么就截取描述作为key，keyRule就是截取多少个字
>
> `v1.3.2已移除`



### isShowThemeCopyright

- Boolean

> 是否显示主题版权信息，默认显示，你可以设为false关闭(`小小心愿，此项能否保留(｀・ω・´)`)



## 广告(v1.3.2已移除)

### message

- String

> 广告文字描述，如目前博客还没开发评论，支持html



### isShowMessage

- Boolean

> 是否显示广告，控制全局



## 广告(v1.3.2已移除)

```yaml
adsenseArr: [
  {
    position: center,
    adsenseMessage: 官网用户专享3,
    script: <a href="https://baidu.com">这是中间3</a>,
    adsenseBackgroundImg: /img/33.jpg,
  },
]

insertAdsenseRule: 17
#每一页文章中，最大广告数
adsenseLength: 3
```



## 文章推荐

<a href="https://aurora.cco.vin/config/feature/recommend.html#%E9%85%8D%E7%BD%AE">点击查看文章推荐配置</a>

### recommendPageLength

- Number

> 文章推荐的最大个数，默认为30

### recommendNoTitle

- String

> 推荐列表标题为空时，就会使用这个进行代替，默认是`╮(￣▽￣)╭`



### tagNoTitle

- String

> tag页，没有标题时，代替文字 默认: `暂时还没有标题哟`



## 打赏

<a href="https://aurora.cco.vin/config/feature/donate.html#%E9%85%8D%E7%BD%AE">赞赏配置</a>

### donate

- Object

> 



### donateImg

- Array

> 赞赏付款二维码地址，推荐放置两张



### articlePage

- Boolean

> 文章页面是否显示打赏



### aboutPage

- Boolean

> 关于页面是否显示打赏



### donateProduct

- Array

> 产品列表

![image-20210912212759510](https://ooszy.cco.vin/img/blog-note/image-20210912212759510.png?x-oss-process=style/pictureProcess1)



### name

- String

> 产品名



### img

- String

> 产品图片地址



### price

- Number

> 价格



### prefix

- String

> 价格前缀，如`$,￥`



### onlineList

- Boolean

> 是否显示在线支付



### donateList

- Array

> 对象数组，用户打赏列表





::: details 点击查看代码
```js
module.exports = {

    themeConfig: {
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
                    //用户名
                    name: "qsyyke",

                    //赞赏信息
                    msg: "文章写的非常好",

                    //用户头像
                    img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",

                    //打赏金额
                    price: 11,

                    //金额前缀
                    prefix: "￥",
                },
                {
                    name: "初尘",
                    msg: "主题太棒了",
                    img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",
                    price: 7,
                    prefix: "￥",
                },
            ],
        },
    }
}
```
:::



## 评论

<a herf="https://aurora.cco.vin/config/comment/">评论配置</a>

### comment

- Object

> 评论相关配置



### showComment

- Boolean

> 是否显示评论
>
> `控制全局`



### placeholder

- String

> 评论区占位符 如在这里留下你的脚印吧...
>
> `v1.3.2使用Waline作为评论，已移除该项，后续版本会添加`



### appId(v1.3.2已移除)

- String

### appKey(v1.3.2已移除)

- String



### avatar

- String

> 头像显示默认，请看相关配置[头像配置 | Waline](https://waline.js.org/guide/client/avatar.html)



### pageSize(v1.3.2已移除)

- Number

> 每页评论展示数目
>
> `后续版本会再次添加`



###  visitor(v1.3.2已移除)

- Boolean

> 是否开启文章统计
>
> `v1.3.2已移除，后续版本会再次添加`



### recordIP

- Boolean

> 是否记录用户IP地址
>
> `v1.3.2已移除，后续版本会再次添加`



### adminUsername

- String

> 管理员用户名
>
> `v1.3.2已移除，后续版本会再次添加`

设置之后，如果评论区的用户名中，存在此值，那么就会在用户名后面加上`UP`标识，但是这样也会存在一个问题，如果用户使用这个用户名评论或者恢复，那么也就相当于管理员回复，无后端不好控制



::: details 点击查看代码
```yaml
#评论配置
comment: {
  showComment: true,
  #占位符
  placeholder: 在这里留下你的脚印吧...,
  appId: LwdW7EDeTGFUjTfUToF9WjEh-gzGzoHsz,
  appKey: yGiWpb4TbwSz5ihpfloV1gDX,
  #头像显示
  avatar: robohash,
  # 每页评论数量
  pageSize: 3,
  #是否开启访问量
  visitor: true,
  # 是否记录评论者ip
  recordIP: true,
  # 是否自动获取用户qq头像，需根据qq号码
  adminUsername: qsyyke
}
```
:::



### serverURL

- String 

> serverURL地址，请点击进行评论相关配置
>
> [快速上手 | Waline](https://waline.js.org/guide/get-started.html#html-引入-客户端)



::: tip 

当在Waline官网配置完成之后，请将`serverURL`添加至此

:::



### emojis

- Array

> [自定义表情 | Waline](https://waline.js.org/guide/client/emoji.html#预设)
>
> 主题默认加入哔哩哔哩表情，不推荐设置太多，`v1.3.2在此处存在一个bug，设置表情太多，选择表情的时候，会将表情做放大处理`

## 海报

### poster

- Object

> 海报相关配置



### description

- String

> 海报底部博客描述



### author

- String

> 作者

### preBlog

- String

> 博客前缀

### suffixBlog

- String

> 博客后缀

### avatar

- String

> 头像，推荐奖此头像放入`docs/.vuepress/public`下，否则可能会出现跨域问题



::: details 点击查看代码
```js
module.exports = {

    themeConfig: {
        poster: {
            //博客描述
            description: "I do not follow,i lives is always all you want",

            //作者
            author: "qsyyke",

            //博客前缀
            preBlog: "qsyyke",

            //海报博客名称后缀
            suffixBlog: "のblog'",

            //头像，请放置在docs/public目录下，或者请保证此图片链接能够跨域访问，否则头像不能正常显示
            avatar: "/avatar.png",
        },
    }
}
```
:::



![image-20210912214810565](https://ooszy.cco.vin/img/blog-note/image-20210912214810565.png?x-oss-process=style/pictureProcess1)



## 侧边栏

### sidebarTag

- String

> 侧边栏标签部分是显示分类，还是标签，默认是显示分类

`sidebarTag: "tag"` 显示分类

`sidebarTag: "categories"` 显示标签



### mobileCutText

- String

> 手机端侧边栏文字

::: details 点击查看详情

<img src="https://ooszy.cco.vin/img/blog-note/image-20211011134928958.png?x-oss-process=style/pictureProcess1" alt="image-20211011134928958" style="zoom:50%;" />

:::



### sidebarCatalogLevel

- Number

> 自动生成文章侧边栏等级



::: tip 

假如一文件夹如`docs/home/config/note.md`

此`sidebarCatalogLevel: 1`，那么在浏览器中打开`localhost:8080/home/config/note.html`时，将只会为`config`文件夹内的md文件生成侧边栏，

如果`sidebarCatalogLevel: 2`，那么会为`home`和`config`文件夹内的md文件，自动生成侧边栏

不推荐此值太大，不然文章侧边栏数量太多，不美观

:::



### latestPageSize

- Number

> 首页侧边栏最新文章的数量，推荐值为6





### githubUrl

- String

> 侧边栏Github链接



### pageSize

- Number

> 首页文章列表数目，默认为4



### showMoodEdit

- Boolean

> 是否开启在线添加说说功能，如果需要，请自己写后台，修改源码，目前在主题内部暂未加入 组件位置docs/.vuepress/theme/lib/client/components/child/AddMood.vue



### showOnlineMood

- Boolean

> 是否展示从网络上请求回来的说说 如果启用，请自己写后台服务，修改源码，目前暂未在主题中加入，期望在将来能够实现，组件位置docs/.vuepress/theme/lib/client/components/Mood.vue



### mobilePageSidebar

- Boolean

> 手机端，是否在页面的底部显示侧边栏列表，默认开启，如果需要开启，请将此值设置为FALSE

## 额外功能

### showAddMood

- Boolean

> 是否显示在线增加说说功能，目前只能我自己使用，在后续版本中，会进行更改