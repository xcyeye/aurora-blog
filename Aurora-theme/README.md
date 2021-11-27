## Introduce

<a target="_blank" href="http://aurora.cco.vin/" >Demo</a>

> A vuepress-based animation blog theme, simple, beautiful, multi-color, multiple custom functions, providing article poster sharing, talk, photo album, comment and other features
>
> 一款基于vuepress的动漫类博客主题，简洁，漂亮，多色彩，多种自定义功能，提供文章海报分享，说说，相册，评论，侧边栏，自动生成文章侧边栏等特色功能

<a href="https://www.npmjs.com/package/vuepress-theme-aurora"><img alt="npm" src="https://img.shields.io/npm/v/vuepress-theme-aurora"></a>![npm](https://img.shields.io/npm/dw/vuepress-theme-aurora)<a href="https://github.com/qsyyke/vuepress-theme-aurora"><img alt="GitHub Repo stars" src="https://img.shields.io/github/stars/qsyyke/vuepress-theme-aurora?style=social"></a>



如果你之前没有使用过`vuepress`，那么安装过程，可以查看<a target="_blank" href="https://v2.vuepress.vuejs.org/zh/guide/getting-started.html">官方教程</a>或者本主题<a target="_blank" href="/readme/introduce.md">详细安装教程</a>

## Feature

- **简洁美观** 细节精致，界面简约漂亮，毛玻璃效果
- **开箱即用** 下载便可直接使用
- **`自动生成文章侧边栏`** 自动为文章所在目录下的所有md文件生成侧边栏文章目录 ，不用在为文章侧边栏而烦恼
- **图片懒加载** 为文章所有图片，首页文章列表加入懒加载效果，支持自定义占位符，大大提升页面加载速度
- **海报分享** 自动为文章生成海报分享功能，支持自定义海报样式
- **SEO** 自定义keyword，description或者主题自动生成keyword，description，提升搜索引擎收录
- **多种功能** 文章海报分享，赞赏，文章分类，友情链接，心情说说，相册，评论，图片懒加载，自动生成文章目录...
- **高度自定义** 提供多个组件和主题配置文件进行自定义，除了部分页面布局文字之外，页面都是可定制的
- `自定义页脚` 支持自定义页脚
- **文章置顶** 首页文章置顶功能
- **移除默认主题的侧边栏** 移除默认主题侧边栏，新侧边栏更加美观，并且在侧边栏上加入多个模块，如文章信息，最新文章，友情链接等等 
- **社交图标** 全部采用阿里图标，所有图标都是经过精心挑选，并且图标修改非常简单
- **阅读体验** 切换背景，修改字体，颜色，毛玻璃效果，圆角，透明度控制
- **动漫背景** 所有页面背景都使用动漫图片，同样也可以使用自己的背景图
- **说说** 每天一个感悟，随时发布，美好的一天从发布说说开始...
- **图片** 支持图片动画展示，每日拍一张照，记录生活的美好
- **搜索** 文章搜索功能
- **打赏** 不一样的打赏功能，扫码，在线支付...
- **随机一言打字机** 首页或者其他位置，通过打字机效果展示随机一言或者自定义语句
- **评论** 使用Waline无后端评论框架，让你在使用vuepress的同时，也能为博客加入评论功能
- **自适应** 为手机端和PC端都做了适配
- **vuepress** 依赖于vuepress，一个静态博客框架，支持几乎所有的vuepress特性

**....**



## Quick start

### Node安装

运行环境需要依赖`node`，如果你未安装`node`，请先安装node，我安装的版本为`v14.17.3`

> 安装node可查看<a href="https://aurora.xcye.xyz/node.html" target="_blank">node 安装</a>



### 安装脚手架

- npm

  ```sh
  npm i vuepress-theme-cli -g
  ```

- yarn

  ```sh
  yarn global vuepress-theme-cli
  ```



### 创建博客

待脚手架安装成功之后，进入cmd中

![image-20211127104249135](https://ooszy.cco.vin/img/blog-note/image-20211127104249135.png?x-oss-process=style/pictureProcess1)



在命令行处输入下面命令

```sh
aurora blog-demo
```

> `blog-demo`是你的博客文件件名称，会自动创建以此名字命名的文件夹



::: tip

如果提示

```sh
'aurora' 不是内部或外部命令，也不是可运行的程序
或批处理文件。
```

请看[这里](https://aurora.xcye.xyz/issue/cli-issue.md)

:::



![image-20211127104417748](https://ooszy.cco.vin/img/blog-note/image-20211127104417748.png?x-oss-process=style/pictureProcess1)



> 这个过程大概几秒，取决于你的网速，待安装成功之后，你会发现在当前目录下，多了一个目录

![image-20211127104750379](https://ooszy.cco.vin/img/blog-note/image-20211127104750379.png?x-oss-process=style/pictureProcess1)



> 然后进入此`demo-blog`目录中，使用命令`npm install`或者`yarn install`安装所需的依赖，待依赖安装成功之后，使用`npm run dev`或者`yarn dev`启动博客

![image-20211127104833140](https://ooszy.cco.vin/img/blog-note/image-20211127104833140.png?x-oss-process=style/pictureProcess1)







> 运行成功之后，会出现一个地址，你只需要在浏览器中，输入这个地址，就可以看到下面的页面



![image-20211010232918219](https://ooszy.cco.vin/img/blog-note/image-20211010232918219.png?x-oss-process=style/pictureProcess1)



::: tip

恭喜你，到这里，你已经安装成功了，接下来便可以尽情书写博客和修改配置(`上图展示出来的效果是默认配置，你可以更改`)

为了更好的对主题进行配置，你可以直接复制我GitHub中的<a href="https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/docs/.vuepress/config-copy.js" target="_blank">config.js</a>文件

推荐阅读

- [主题所有配置](/home/config.md)
- [问题及报错解决](/issue/bug.md)
- [注意更新日志](/issue/CHANGELOG.md)

:::

## 联系我

如果你在使用该主题的过程中，有任何的问题，都可以通过以下方式联系我

| QQ                                                           | 主题交流群                                                   | 微信                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233620332.png?x-oss-process=style/pictureProcess1" style="zoom:33%;" /> | <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233827133.png?x-oss-process=style/pictureProcess1" alt="image-20211024233827133" style="zoom:33%;" /> | <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233735110.png?x-oss-process=style/pictureProcess1" alt="image-20211024233735110" style="zoom: 39%;" /> |




## 渲染

更多的体验效果，可以查看<a href="https://aurora.xcye.xyz/">演示站点</a>

![image-20211010162940583](https://ooszy.cco.vin/img/blog-note/image-20211010162940583.png?x-oss-process=style/pictureProcess1)

![image-20211111090833031](https://ooszy.cco.vin/img/blog-note/image-20211111090833031.png?x-oss-process=style/pictureProcess1)

![image-20211010163133623](https://ooszy.cco.vin/img/blog-note/image-20211010163133623.png?x-oss-process=style/pictureProcess1)

![image-20211010163256145](https://ooszy.cco.vin/img/blog-note/image-20211010163256145.png?x-oss-process=style/pictureProcess1)

![](https://ooszy.cco.vin/img/theme/article.jpg)
![](https://ooszy.cco.vin/img/theme/about.jpg)
![](https://ooszy.cco.vin/img/theme/comment-theme.jpg)
![](https://ooszy.cco.vin/img/theme/link.jpg)
![](https://ooszy.cco.vin/img/theme/mood.jpg)
![](https://ooszy.cco.vin/img/theme/phoone.jpg)
![](https://ooszy.cco.vin/img/theme/photo-theme.jpg)
![](https://ooszy.cco.vin/img/theme/tag.jpg)

或者我的<a target="_blank" href="https://blog.xcye.xyz">博客站点</a>

## 首页配置

[首页](https://aurora.xcye.xyz/homeconfig.html)

