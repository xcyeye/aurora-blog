---
date: "2021/10/19 21:18"
---



# 首页文章列表配置

::: tip

vuepress框架，会自动将`docs`目录下的所有`.md`文件自动解析成`.html`文件，如果你建立站点之后，发现首页没有文章，那么你需要在`docs`目录下，创建几个`.md`文件

在阅读本章节之前，请确保你已经对 Markdown 有所了解。

[Markdown使用](/learn/markdown.md)

如果你还不了解 Markdown ，请先学习一些 [Markdown](https://commonmark.org/help/)。

:::



<img src="https://ooszy.cco.vin/img/blog-note/image-20211019211924609.png?x-oss-process=style/pictureProcess1" alt="image-20211019211924609" style="zoom:50%;" />

> 在首页，会根据你的通过`git commit`提交的时间戳或者是否在文章的`frontmatter`中设置是否置顶



## 替换图片地址

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

目前版本暂未提供修改，此图片为

https://ooszy.cco.vin/img/blog-note/aurora-loading.gif



![image-20211021000532423](https://ooszy.cco.vin/img/blog-note/image-20211021000532423.png?x-oss-process=style/pictureProcess1)



## 关于页面配置

[关于页面配置](/page/about.md)