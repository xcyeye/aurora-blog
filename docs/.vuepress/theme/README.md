# vuepress-theme-ccds

> A vuepress-based animation blog theme, simple, beautiful, multi-color, multiple custom functions, providing article poster sharing, talk, photo album, comment and other features
>
> 一款基于vuepress的动漫类博客主题，简洁，漂亮，多色彩，多种自定义功能，提供文章海报分享，说说，相册，评论等特色功能

<a href="https://www.npmjs.com/package/vuepress-theme-ccds"><img src="https://img.shields.io/npm/v/vuepress-theme-ccds" /></a>![GitHub package.json version](https://img.shields.io/github/package-json/v/qsyyke/vuepress-theme-ccds)![npm](https://img.shields.io/npm/dw/vuepress-theme-ccds)![npm bundle size](https://img.shields.io/bundlephobia/min/vuepress-theme-ccds)


![](https://ooszy.cco.vin/img/theme/%E4%B8%BB%E9%A2%98.jpg)




## Feature

- **简洁美观** 细节精致，界面漂亮，毛玻璃效果
- **高度自定义** 提供多个组件和主题配置文件进行自定义
- **功能繁多** 文章海报分享，打赏，文章分类，友情链接，关于页面，说说，相册，广告，评论，图片懒加载...
- **阅读体验** 切换背景，修改字体，颜色，毛玻璃效果，圆角，透明度控制
- **海报分享** 自动为文章生成海报分享功能，支持自定义海报样式
- **动漫背景** 激活所有页面背景都使用动漫图片，也支持自定义
- **图片懒加载** 为文章图片加入懒加载效果，支持自定义占位符，大大提升页面加载效果
- **说说** 每天一个感悟，随时发布，在指定页面进行展示，并且支持生成海报分享，下载
- **图片** 支持图片动画展示，每日拍一张照，记录生活的美好
- **搜索** 支持文章搜索功能
- **SEO** 默认对所有文章设置过SEO，提升搜索引擎优化
- **广告** 为文章页面加入广告配置，只需传入script便可直接显示，并支持自定义
- **打赏** 不一样的打赏功能，扫码，在线支付...
- **随机一言打字机** 首页或者其他位置，通过打字机效果展示随机一言或者自定义语句
- **评论** 使用Valine无后端评论框架，让你在使用vuepress的同时，也能为博客加入评论功能
- **自适应** 为手机端和PC端都做了适配
- **vuepress** 依赖于vuepress，一个静态博客框架，支持几乎所有的vuepress特性

**....**



## Demo

更多的体验效果，可以查看<a href="https://theme-ccds.cco.vin/">演示站点</a>

![](https://ooszy.cco.vin/img/theme/article.jpg)
![](https://ooszy.cco.vin/img/theme/about.jpg)
![](https://ooszy.cco.vin/img/theme/comment-theme.jpg)
![](https://ooszy.cco.vin/img/theme/link.jpg)
![](https://ooszy.cco.vin/img/theme/mood.jpg)
![](https://ooszy.cco.vin/img/theme/phoone.jpg)
![](https://ooszy.cco.vin/img/theme/photo-theme.jpg)
![](https://ooszy.cco.vin/img/theme/tag.jpg)


或者我的<a href="https://blog.cco.vin">博客站点</a>



## 安装

详细安装可查看<a href="https://theme-ccds.cco.vin/home/#%E5%AE%89%E8%A3%85-1">文档</a>



运行环境需要依赖`node`，所以在安装之前，请确保操作系统已经安装了node，我运行时的版本为`v14.17.3`

### 初始化

1. 创建一个文件夹`blog-demo`

2. 进入此`blog-demo`文件夹内，使用`npm init`初始化

    ```sh
    npm init
    ```

3. 将下面代码加入到`package.json`对应位置

    ```json
    "scripts": {
        "test": "echo \"Error: no test specified\" && exit 1",
        "dev": "vuepress dev docs",
        "build": "vuepress build docs",
        "deploy": "bash deploy.sh"
      }
    ```

### 依赖安装

运行下面命令安装`vuepress`和`ccds`主题

```shell
npm i vuepress@2.0.0-beta.25
npm i vuepress-theme-ccds
```



### 使用

#### 1.新建readme.md文件

在`docs/`下，新建`readme.md`主题配置文件，将<a href="https://github.com/qsyyke/vuepress-theme-ccds/blob/v1.2.0/docs/README.md">readme.md</a>中所有内容复制到`docs/readme.md`文件中，请全部复制

#### 2.配置config.js

进入`docs/.vuepress/config.js`中，加入下面代码

```js
module.exports = {
    theme: 'vuepress-theme-ccds',
    onPrepared: async (app) => {
        const myData = app.pages.map((page) => {
            return page
        })
        await app.writeTemp('my-data.js', `export default ${JSON.stringify(myData)}`)
    },
    themeConfig: {
        darkMode: false,
        navbar: [
            {
                text: '主题使用',
                children: [
                    {
                        text: '主题使用',
                        link: '/issue/'
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
                text: '心情',
                link: '/mood'
            },
            {
                text: "相册",
                link: '/photo'
            }
        ]
    }
}
```



#### 3使用

在cmd中，进入创建的`blog-demo`文件夹内，运行`npm run dev`便可使用该主题

如果在使用或者安装过程中，遇到任何问题，可以在<a href="https://theme-ccds.cco.vin/about">此站点</a>下留言



## 文档

theme-ccds主题已提供完整文档供使用，点击进入<a href="https://theme-ccds.cco.vin/home/#%E4%BB%8B%E7%BB%8D">文档页面</a>

