# V1.3.0 首页配置

::: tip 

其中所有yaml代码，除非特别说明，否则都是在`docs/readme.md`文件中进行配置

:::

首页在新版本中的打开效果如下

- PC

![image-20211001225813353](https://ooszy.cco.vin/img/blog-note/image-20211001225813353.png?x-oss-process=style/pictureProcess1)



- Mobile

![image-20211001225920441](https://ooszy.cco.vin/img/blog-note/image-20211001225920441.png?x-oss-process=style/pictureProcess1)



首页logo，首页中间头像，以及随机一言并没有发生改变，只是在社交上添加了一个选项



## 社交

```yaml
socials: [
 {
    aHref: tencent://message/?uin=2291308094,
    imgSrc: https://ooszy.cco.vin/img/ico/qq.svg,
    isHome: true,
    show: true,
    sidebar: true
  },
]
```

- sideba

> 为true，该项会显示在首页侧边栏如下位置，推荐不超过四个

![image-20211001230303389](https://ooszy.cco.vin/img/blog-note/image-20211001230303389.png?x-oss-process=style/pictureProcess1)



## 文章置顶

在v1.3.0中，在首页中，你能够对文章进行置顶操作，被置顶的文章，会在首页显示，但是首页默认显示数量为4个，如果置顶数量大于4，会在后一页显示，你也可以设置首页文章显示长度



> 文章置顶：在每个`.md`文件中，加入下面值，便可让此文章置顶

```yaml
stick: true
```



## 文章展位图

```yaml
---
categories: [海报,poster]
stick: true
coverUrl: https://img2.baidu.com/it/u=1357720467,2636512316&fm=26&fmt=auto
---
```

<img src="https://ooszy.cco.vin/img/blog-note/image-20211001230823018.png?x-oss-process=style/pictureProcess1" alt="image-20211001230823018" style="zoom:50%;" />

> 你可以在文章中，设置`coverUrl`项，使用自定义的图片，否则会使用系统默认的地址



## 首页单页面文章最大长度

```yaml
#首页文章分页条数
pageSize: 4
```

> 不推荐配置太长



## 文章内容图片占位符

```yaml
#首页是否显示文章图片，默认关闭，如果显示的话，首页加载会非常慢
showHomePageImg: false
```



<img src="https://ooszy.cco.vin/img/blog-note/image-20211001232159105.png?x-oss-process=style/pictureProcess1" alt="image-20211001232159105" style="zoom: 33%;" />

在首页文章中，文章内容如果存在图片，我默认是不加载，使用此`https://ooszy.cco.vin/img/ico/loading.png`图片进行代替，主要是因为，如果加载图片，会拖慢首页打开速度，但是此还存在一个bug，首页内容图片被全部替换掉了，但是如果跳转其他页，比如第二页，第三页，图片并没有被替换掉，我F12发现，其虽然没有替换掉，但是没有加载此图片，就未解决此bug，后面版本会解决



## 侧边栏GitHub地址

```yaml
# 侧边栏配置
githubUrl: https://github.com/qsyyke
```



## 最新文章数量

```yaml
latestPageSize: 6
```



## 侧边栏菜单和导航

<img src="https://ooszy.cco.vin/img/blog-note/image-20211001232727029.png?x-oss-process=style/pictureProcess1" alt="image-20211001232727029" style="zoom:33%;" />

如果你们仔细一点，可能发现，这个菜单和导航其实就是顶部导航的内容



### Menu

会选择所有的在`docs/config.js`中对导航栏的配置进行生成

```js
{
    text: '友情链接',
    link: '/link'
},
```

Menu会选择所有没有children的项

并且对于关于页面，首页，标签，心情，图片，github页面的ico，会使用固定的，对于这几个以外的链接，会随机选择



## 导航

```yaml
{
    text: '其他配置',
    children: [
        '/config/other/index.html',
        '/config/other/footer.html',
        '/config/other/message.html',
    ]
},
```

会选择上述这种，有children的导航配置生成，详细查看官方配置

<a href="https://v2.vuepress.vuejs.org/zh/reference/default-theme/config.html#navbar">官方</a>

如果你需要自定义的话，请修改源码，我使用的是<a href="https://icomoon.io/">icommon</a>图标库

> 组件位置:docs/.vuepress/theme/lib/client/components/child/MobileSidebarNav.vue  ---> `setHomeIco()`



## 公告

在此版本中，对公告进行了更改，原来公告配置是一个字符串，现在是一个数组

```yaml
message:
    - 目前博客还没开发评论，如需添加友情链接，请到我旧博客添加评论评论我都会看，看到会添加到此处<a target="_blank" href="https://www.cco.vin/%e5%8f%8b%e6%83%85%e9%93%be%e6%8e%a5/">点击进入</a>
    - 这是一个测试
    - 再次测试
    - 这是一张图片的测试<img src="https://ooszy.cco.vin/img/blog-public/wxpay.png" />
```

你可以传入图片，html，最终会以HTML进行显示



其实我已对侧边栏组件，提供了多个配置，你可以选择哪个组件是否需要在首页显示，或者手机端显示等等，但是并没有在`docs/readme.md`中提供配置，如需进行单独自定义，请修改源码

> 组件位置: docs/.vuepress/theme/lib/client/components/child/HomeSidebar.vue
>
> 手机端组件位置: docs/.vuepress/theme/lib/client/components/MobileSidebar.vue



## 自定义顶部图片

在v1.3.0中，对文章，标签等等页面的顶部图片提供了自定义

```yaml
#自定义顶部图片
customTopImg: {
    custom: true,
    page: 'https://ooszy.cco.vin/img/mood/blog.cco.vin_illust_92786671_20210919_001829.jpg',
    friend: 'https://ooszy.cco.vin/img/mood/blog.cco.vin_illust_87746715_20210920_231648.jpg',
    tag: 'http://ooszy.cco.vin/img/blog-note/image-20210901183045860.png',
    mood: 'http://ooszy.cco.vin/img/blog-note/image-20210901183204994.png'
}
```

设置之后，对应页面的顶部图片，便不会使用随机的





## 文章侧边栏自动生成规则

```yaml
# 文章侧边栏自动获取的层次 默认为1，也就是http://localhost:8080/config/feature/donate.html,只会自动生成feature目录下的文件
sidebarCatalogLevel: 1
```

> 如果此值为1，有一个地址为`http://localhost:8080/home/config.html`，那么侧边栏就只会获取`home`下的所有文章生成侧边栏
>
> 如果此值为2，有一个地址为`http://localhost:8080/spring/springmvc/sdf.html`
>
> 那么会获取spring,springmvc两个努力下的所有文件生成侧边栏，推荐此值为1