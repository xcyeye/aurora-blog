# vuepress-theme-Aurora

<a target="_blank" href="http://aurora.cco.vin/" >Demo</a>

> A vuepress-based animation blog theme, simple, beautiful, multi-color, multiple custom functions, providing article poster sharing, talk, photo album, comment and other features
>
> 一款基于vuepress的动漫类博客主题，简洁，漂亮，多色彩，多种自定义功能，提供文章海报分享，说说，相册，评论，侧边栏，自动生成文章侧边栏等特色功能

<a href="https://www.npmjs.com/package/vuepress-theme-aurora"><img alt="npm" src="https://img.shields.io/npm/v/vuepress-theme-aurora"></a>![npm](https://img.shields.io/npm/dw/vuepress-theme-aurora)<a href="https://github.com/qsyyke/vuepress-theme-aurora"><img alt="GitHub Repo stars" src="https://img.shields.io/github/stars/qsyyke/vuepress-theme-aurora?style=social"></a>


![](https://ooszy.cco.vin/img/theme/%E4%B8%BB%E9%A2%98.jpg)

![image-20211009083404086](https://ooszy.cco.vin/img/blog-note/image-20211009083404086.png?x-oss-process=style/pictureProcess1)



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



## 安装

运行环境需要依赖`node`，所以在安装之前，请确保操作系统已经安装了node，我运行时的版本为`v14.17.3`

> 如何安装node，请自行百度解决

### 初始化(以windows安装为例)

1. 创建一个文件夹`blog-demo`

2. 进入此`blog-demo`文件夹内，使用`npm init`命令初始化

    <img src="https://ooszy.cco.vin/img/blog-note/image-20211010210735931.png?x-oss-process=style/pictureProcess1" alt="image-20211010210735931" style="zoom:33%;" />![image-20211010210848054](https://ooszy.cco.vin/img/blog-note/image-20211010210848054.png?x-oss-process=style/pictureProcess1)

    在cmd窗口中，输入`npm init`命令

    ```sh
npm init
    ```
    
3. 你将得到一个`package.json`文件，将下面代码加入到`package.json`对应位置

    ```json
    "scripts": {
        "test": "echo \"Error: no test specified\" && exit 1",
        "dev": "vuepress dev docs",
        "build": "vuepress build docs",
        "deploy": "bash deploy.sh"
      },
    ```

或者你也可以将下面代码全部复制替换`package.json`内的所有内容

```json
{
  "name": "blog-demo",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1",
    "dev": "vuepress dev docs",
    "build": "vuepress build docs",
    "deploy": "bash deploy.sh"
  },
  "author": "",
  "license": "ISC"
}
```

自此，便可以开始安装主题及其依赖



### 依赖及主题安装

在cmd面板中运行下面命令安装`vuepress`和`Aurora`主题

```shell
npm i vuepress@2.0.0-beta.25
npm i vuepress-theme-aurora
```

### 使用主题

#### 1.新建readme.md文件

在`blog-demo`文件夹内，新建一个新文件夹`docs`，以及在`docs`内新建`.vuepress`文件夹

在`docs`文件夹下，新建`README.md`，在此`README.md`文件内添加下面内容

```yaml
---
home: true
---
```



#### 2.配置config.js

在`docs/.vuepress/`文件夹内，新建`config.js`

进入`docs/.vuepress/config.js`中，加入下面代码(`请全部复制完`)

```js
module.exports = {
    //设置head 一定要加入<script src="https://at.alicdn.com/t/font_2849934_v6y652peian.js"></script>项配置，否则一些图标不能正常显示
    head: [
        [
            "script",
            {
                src: "https://at.alicdn.com/t/font_2849934_v6y652peian.js",
            },
        ]
    ],
    onPrepared: async (app) => {
        const myData = app.pages.map((page) => {
            return page;
        });
        await app.writeTemp(
            "my-data.js",
            `export default ${JSON.stringify(myData)}`
        );
    },
    theme: 'vuepress-theme-aurora',
    themeConfig: {
        darkMode: false,
    }
};
```



#### 3.使用

在cmd中，进入创建的`blog-demo`文件夹内，运行`npm run dev`

待运行成功之后，你便能在面板中看到一个地址，在浏览器中输入此地址，便可看到下面界面

<img src="https://ooszy.cco.vin/img/blog-note/image-20211010232842842.png?x-oss-process=style/pictureProcess1" alt="image-20211010232842842" style="zoom:50%;" />

![image-20211010232918219](https://ooszy.cco.vin/img/blog-note/image-20211010232918219.png?x-oss-process=style/pictureProcess1)



> 恭喜你，到这里，你已经安装成功了，接下来便可以尽情书写博客和修改配置(`上图展示出来的效果是默认配置，你可以更改`)
>
> 主题配置修改，请参考此<a href="https://github.com/qsyyke/vuepress-theme-aurora/blob/master/docs/.vuepress/config-fu.js">config.js</a>文件




## 渲染

更多的体验效果，可以查看<a href="https://aurora.cco.vin/">演示站点</a>

![image-20211010162940583](https://ooszy.cco.vin/img/blog-note/image-20211010162940583.png?x-oss-process=style/pictureProcess1)

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

或者我的<a target="_blank" href="https://blog.cco.vin">博客站点</a>



## 文档

Aurora主题已提供完整文档供使用，点击进入<a target="_blank" href="https://aurora.cco.vin/">文档页面</a>

