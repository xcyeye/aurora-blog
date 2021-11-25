---
tag: [首页配置,一言,头像,社交,页脚]
date: "2021/10/19 21:18"
---



# 首页配置

::: tip

vuepress框架，会自动将`docs`目录下的所有`.md`文件自动解析成`.html`文件，如果你建立站点之后，发现首页没有文章，那么你需要在`docs`目录下，创建几个`.md`文件

在阅读本章节之前，请确保你已经对 Markdown 有所了解。

[Markdown使用](/learn/markdown.md)

如果你还不了解 Markdown ，请先学习一下[Markdown 教程](https://www.runoob.com/markdown/md-tutorial.html)



- 必读

> [主题问题解决](./issue/)
>
> [部署](./home/deploy.md)

- 推荐阅读

> [主题增加新组件，新页面](./feature/registercom.md)
>
> [修改主题默认样式](./style.md)
>
> [主题目录结构](./home/directory-structure.md)
>
> [主题写作技巧](./learn/markdown.md)
>
> [主题bug](./issue/bug.md)
>
> [更新日志](./issue/CHANGELOG.md)

- 环境安装

> [node安装教程](./use/useTheme.md)

:::

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
        },
        {
            text: 'photo',
            link: '/photo'
        }
    ],
  },
}
```

其中`/about,/link,/tag,/mood`是主题新增的路由，对应关于页面，友情链接，标签，心情页面，所以请在navbar上，添加



## logo

![image-20210831141849241](http://ooszy.cco.vin/img/blog-note/image-20210831141849241.png?x-oss-process=style/pictureProcess1)

导航栏logo可以在`config.js`文件中，进行设置，你可以将图片放在`docs/.vuepress/public`目录下，然后使用`/图片文件名`，也可以使用一个url

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

如果没有在此`config.js`文件中进行设置，默认使用[default logo](https://ooszy.cco.vin/img/ico/yuan.png)



### logo文字

对于logo旁边文字，需要在`docs/readme.md`的`logoTitle`进行设置，默认值为`aurora`

```js
logoTitle: 'qsyyke'
```



![image-20210831142236064](http://ooszy.cco.vin/img/blog-note/image-20210831142236064.png?x-oss-process=style/pictureProcess1)

并且支持设置字体颜色，配置项为`logoColor`

```js
logoColor: '#2c3e50'
```

配置说明https://aurora.cco.vin/home/config.html#logotitle





## 中间头像

![image-20210831142850834](http://ooszy.cco.vin/img/blog-note/image-20210831142850834.png?x-oss-process=style/pictureProcess1)

此头像通过`heroImg`进行修改，并不是使用logo图片地址，默认图片使用<a href="https://ooszy.cco.vin/img/blog-public/avatar.jpg">default</a>

```js
heroImg: 'https://ooszy.cco.vin/img/blog-public/avatar.jpg'
```



## 随机一言

![image-20210831143353819](http://ooszy.cco.vin/img/blog-note/image-20210831143353819.png?x-oss-process=style/pictureProcess1)

该随机一言打印效果使用<a href="https://github.com/pengqiangsheng/easy-typer-js">easy-typer-js</a>



在后续版本中，会对随机一言进行优化，当前版本更改随机一言地址，不太容易











<img src="https://ooszy.cco.vin/img/blog-note/image-20211019211924609.png?x-oss-process=style/pictureProcess1" alt="image-20211019211924609" style="zoom:50%;" />

> 首页会根据你通过`git commit`提交的时间戳或者是否在文章的`frontmatter`中设置是否置顶



## 替换文章列表图片地址

图片地址的修改，可以有两个选择

1. 通过在每篇文章的顶部，设置`coverUrl`属性，此属性值需要传入一个图片url地址，语法为`yaml`

   ::: details view
   
   ```yaml
   stick: true
   coverUrl: 'https://img2.baidu.com/it/u=2192265457,2884791613&fm=26&fmt=auto'
   time: 2021/10/14
   ```
   
   :::



2. 如果你没有在每篇文章中，设置`coverUrl`值，那么将使用你设置的随机图片接口，如果没设置，那么将使用主题默认提供的接口，

   ```js
   //首页文章列表封面图api接口
   homePageImgApi: "https://api.ixiaowai.cn/api/api.php",
   ```

   



## 最后更新时间

### 1.git-commit

![image-20211020235623101](https://ooszy.cco.vin/img/blog-note/image-20211020235623101.png?x-oss-process=style/pictureProcess1)



当你使用`git init`初始化之后，你需要使用下面命令进行提交

```sh
git add .
git commit -m "最后更新时间"
```

然后重启服务`npm run dev`



###  2. frontmatter

同时，还有另外一种选择，你可以在每一个md文件中，在每篇文章的顶部，添加`date`字段，自己设置日期

```md
---
date: "2021/10/20 23:59"
---
```

请注意，`---`内的是`yaml`语法，详细请查看官方说明https://v1.vuepress.vuejs.org/zh/guide/frontmatter.html

> `date字段值，请使用双引号引起来，你可以只写2021/10/20，不需要写后面的时候，如果你在frontmatter中，添加此字段，那么将以此字段的时间，作为首页文章列表的最后更新时间`



## 修改首页文章列表懒加载图片

```js
//config.js
homePageLazyLoadingImg: 'https://ooszy.cco.vin/img/blog-note/aurora-loading.gif?name=chuchen',
```





![image-20211021000532423](https://ooszy.cco.vin/img/blog-note/image-20211021000532423.png?x-oss-process=style/pictureProcess1)



## 关于页面配置

[关于页面配置](/page/about.md)