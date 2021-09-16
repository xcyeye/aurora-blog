---
categories: [主题配置,配置项,配置总览]
---



# 所有配置

## 首页

<a href="https://theme-ccds.cco.vin/config/">点击查看配置</a>

### home

- Boolean

> true为首页，false反之



### logoColor

- String 

> 首页左上角Logo图旁文字颜色



logoTitle

- String

> 左上角，logo旁标题文字



### showFont

- String

> 圆角等控制面板，字体切换的占位符文字，推荐一个字



### faviconIco

- String

> 网站顶部favicon图片链接，如https://ooszy.cco.vin/img/blog-public/ccds_64.ico



### customRandomSay

- Boolean

> 是否自定义随机一言，true表示不会使用随机一言接口



### customRandomValue

- String

> 自定义一言文字，只有customRandomSay为true时有效



### showHomeMood

- Boolean

> 是否在首页显示心情，true开启，需要设置文字

### mood

- String

> 首页中间，心情文字





heroLogo

- String

> 首页中间图片链接



### logo

- String

> 左上角logo图片



### cancelIcoHref

- String

> 圆角字体控制面板，取消按钮图片链接



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

> 从随机一言接口中，如何获取值，如上面`randomSaw`接口，输入hitokoto

### method

- String

> 请求接口



animeOption: {
  baseURL: https://api.vvhan.com/api/acgimg?type=json,
  method: get,
  timeout: 5000,
  query: imgurl
}

heroImage: "https://ooszy.cco.vin/img/blog-public/head.jpg"


### isFitter

- Boolean

> 默认是否启用毛玻璃效果，true表示开启





## 文章

<a href="https://theme-ccds.cco.vin/config/page/page.html#%E9%A1%B6%E9%83%A8%E5%9B%BE%E7%89%87">点击查看文章配置</a>

### lazyLoadingImg

- String

> 文章懒加载占位符图片链接，如https://ooszy.cco.vin/img/blog-public/ljz.gif


### defaultTitleColor

- String

> 文章顶部标题颜色，如#8093f1



## 社交

<a herf="https://theme-ccds.cco.vin/config/page/about.html#%E7%A4%BE%E4%BA%A4">点击查看社交配置</a>

### socials

- Array

> 数组对象

::: details 点击查看实例
```yaml
socials: [
  {
    aHref: mailto:2291308094@qq.com,
    imgSrc: https://ooszy.cco.vin/img/ico/email.svg,
    isHome: false,
    show: false
  },
    ....
]
```
:::

### aHref

- String

> 社交链接地址

### imgSrc

- String

> 社交图片地址

### isHome

- Boolean

> 是否在首页显示，true表示会在首页显示，其他页面也会显示，为false，首页不会显示，其他页面会显示

### show

- Boolean

> 是否显示此社交，控制全局



### socialMaxLength

- Number

> 社交展示的最大个数



## 关于页面

<a href="https://theme-ccds.cco.vin/config/page/about.html#%E7%A4%BE%E4%BA%A4">点击查看关于页面</a>

### about

- Array

> 关于页面数组对象



::: details 点击查看实例
```yaml
about: [
  {
    bar: false,
    title: '我?',
    describe: [],
    tag: [],
    showTag: true
  },
  {
    bar: false,
    title: '大三规划',
    describe: [],
    tag: [],
    showTag: true
  },
  {
    bar: false,
    title: '未来规划',
    describe: [],
    tag: [

    ],
    showTag: false
  },
  {
    bar: true,
    title: '技  能',
    describe: [
      {
        name: 'springBoot',
        score: 82
      },
    ],
    showTag: false
  },

  {
    showTag: false,
    bar: true,
    title: '掌握框架',
    describe: [
      {
        name: 'dubbo',
        score: 61
      },

    ]
  },
  {
    bar: true,
    showTag: false,
    title: '掌握技能',
    describe: [
      {
        name: 'Ctrl C V',
        score: 100
      }

    ]
  },
  {
    title: '关于主题',
    describe: [
        该主题是我自己独立开发，是一款基于vuepress，对默
    ]
  },
  {
    bar: false,
    title: '更新日志',
    describe: [
        '更新日志',
        '更新日志',
        '更新日志',
        '更新日志',
        '项目'
    ],
    tag: [

    ],
    showTag: false
  },
]
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



### randomWallpaperMb

- String

> 手机端随机背景地址



## 字体和颜色



### randomColor

- Array

> 随机颜色字符串数组，文章推荐，tag页面，关于页面条状背景色...，并不会控制字体颜色



::: details 点击查看代码
```yaml
#随机图片地址，设置背景用
randomWallpaper: https://api.iro.tw/webp_pc.php
randomWallpaperMb: https://api.iro.tw/webp_mb.php
#这是配置随机背景；颜色
randomColor: ['#ffcad4','#d8e2dc','#8d99ae','#b8f2e6','#84c7d0',
'#aed9e0','#00b4d8','#caf0f8','#fbc4ab','#fdc5f5',
'#84dcc6','#a9def9','#fcf6bd','#f0a6ca','#b9faf8',
'#42a5f5','#ff9800','#b39ddb','#6d45bb','#b388ff',
'#1565c0','#26c6da','#5e548e','#90f1ef','#5b5f97',
'#bbe6e4','#42bfdd','#72ddf7','#8093f1','#9ed8d8',
'#7ea8be','#ef90b3','#b892ef','#c0b9dd','#c0d9dd',
'#75c9c8','#ded9e2','#b5e2fa','#62b6cb','#5fa8d3',
'#0fa3b1','#b5e2fa','#5fa8d3','#62b6cb','#b892ff']
maxFontColorArr: 8
fontColor: [
'#2c3e50','#42a5f5','#8093f1','#FF6EC7','#FF7F00',
'#8FBC8F','#EAADEA','#3299CC','#CDCDCD','#CC3299',
'#FF7F00','#2F4F4F']
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
如果想要使用自己新引入的字体，请在源码中引入字体，css文件位置`docs/.vuepress/theme/lib/client/styles/theme.style.css`,引入之后，在此处填写字体名
:::



## 友情链接

<a href="https://theme-ccds.cco.vin/config/page/friendlink.html#%E9%A1%B6%E9%83%A8%E5%9B%BE%E7%89%87">点击查看友情链接页面</a>

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
friendLinks: [
  {
  title: 疫情在线捐款系统,
  url: http://yq.vipblogs.cn/,
  logo: http://yq.vipblogs.cn/favicon.ico,
  describe: 基于echarts的疫情捐款系统
  },
  {
  title: 疫情在线捐款系统后台登录,
  url: http://admin.vipblogs.cn/,
  logo: /img/head.jpg,
  describe: 该捐款系统后台登录
  },
  {
  title: 另一博客,
  url: https://cco.vin/,
  logo: https://vipblogs.cn/wp-content/uploads/2021/03/bitbug_favicon.ico,
  describe: 以前博客
  },
]
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



## ico图标



### ico

- Object

> 友情链接，关于页面，还有圆角控制面板ico图标地址



### linkIco

- String

> 友情链接图标地址

也就是这一坨

![image-20210912210416750](https://ooszy.cco.vin/img/blog-note/image-20210912210416750.png?x-oss-process=style/pictureProcess1)



### aboutIco

- String

> 关于页面，图标地址



### homeWelcome

- Array

> 字符串数组，圆角，字体控制面板，背景切换和开启毛玻璃效果图标，源码中，是通过数组顺序进行使用的，传入时，请注意顺序

![image-20210912210648219](https://ooszy.cco.vin/img/blog-note/image-20210912210648219.png?x-oss-process=style/pictureProcess1)





## 标签

<a herf="https://theme-ccds.cco.vin/config/page/tag.html#%E6%A0%87%E7%AD%BE">点击查看标签页面配置</a>

### excludeTag

- Array

> 字符串数组，标签中，需要排除的链接分割文字，如
>
> ```yaml
> excludeTag:
>   - note
> ```
>
> 如果有有一个文章url为`https://blog.cco.vin/note/spring/springboot.html`，那么在标签列表中，不会显示`note`，但会显示`spring`

### split

- String 如`~`

> tag页分割符

![image-20210912212058062](https://ooszy.cco.vin/img/blog-note/image-20210912212058062.png?x-oss-process=style/pictureProcess1)



## 页脚

<a href="https://theme-ccds.cco.vin/config/#%E9%A1%B5%E8%84%9A">点击查看页脚配置</a>

### footer

- Array

> 字符串数组，页脚显示的HTML，支持传入HTML，对于首页，会选择数组中的第一个和第二个显示



::: details 点击查看代码
```yaml
footer:
  - Copyright © by qsyyke All Rights Reserved.
  - Theme <a href="https://www.cco.vin">ccds</a> by qsyyke
  - "<a target='_blank' href='http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142' style='display:inline-block;text-decoration:none;height:20px;line-height:20px;'><img src='' style='float:left;'/><p style='float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px;'>滇公网安备 53060202000142号</p></a>"
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

> 网站开始时间，请注意格式，如8/7/2021 12:22:00



### prefixRuntime

- String

> 运行时间描述，如小破站已运行



### keyRule

- Number

> 如果没有设置key值，那么就截取描述作为key，keyRule就是截取多少个字



## 广告

### message

- String

> 广告文字描述，如目前博客还没开发评论，支持html



### isShowMessage

- Boolean

> 是否显示广告，控制全局



## 广告

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

<a href="https://theme-ccds.cco.vin/config/feature/recommend.html#%E9%85%8D%E7%BD%AE">点击查看文章推荐配置</a>

### recommendPageLength

- Number

> 文章推荐的最大个数

### recommendNoTitle

- String

> 推荐列表标题为空时，就会使用这个进行代替，默认是`╮(￣▽￣)╭`



### tagNoTitle

- String

> tag页，没有标题时，代替文字 默认是下面这个



## 打赏

<a href="https://theme-ccds.cco.vin/config/feature/donate.html#%E9%85%8D%E7%BD%AE">赞赏配置</a>

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
donate: {
  donateImg: [
      https://ooszy.cco.vin/img/blog-public/wxpay.png,
      https://ooszy.cco.vin/img/blog-public/zfbpay.jpg
  ],
  articlePage: true,
  aboutPage: true,
  donateProduct: [
    {
      name: 奶茶,
      img: https://ooszy.cco.vin/img/blog-public/nc.jpeg,
      price: 18,
      prefix: $
    }
  ],
  onlineList: true,
  donateList: [
    {
      name: 初尘,
      msg: 主题太棒了,
      img: https://ooszy.cco.vin/img/blog-public/nc.jpeg,
      price: 7,
      prefix: ￥
    },
  ],
}
```
:::



## 评论

<a herf="https://theme-ccds.cco.vin/config/comment/">评论配置</a>

### comment

- Object

> 评论相关配置



### showComment

- Boolean

> 是否显示评论



### placeholder

- String

> 评论区占位符 如在这里留下你的脚印吧...



### appId

- String

### appKey

- String

<a href="https://theme-ccds.cco.vin/config/comment/">点击查看</a>



### avatar

- String

> 头像显示默认，请看相关配置https://theme-ccds.cco.vin/config/comment/



### pageSize

- Number

> 每页评论展示数目



###  visitor

- Boolean

> 是否开启文章统计



### recordIP

- Boolean

> 是否记录用户IP地址



### adminUsername

- String

> 管理员用户名

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
## 海报
poster: {
  description: 初尘博客呀,
  author: 'qsyyke',
  preBlog: 'chuchen',
  suffixBlog: 'blog',
  avatar: /avatar.png
}
```
:::



![image-20210912214810565](https://ooszy.cco.vin/img/blog-note/image-20210912214810565.png?x-oss-process=style/pictureProcess1)