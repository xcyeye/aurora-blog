# Aurora主题bug记录

::: tip 

这里记录的是每一个版本所遇到的bug

:::

```js
{
    title: "",//博客名称
    url: "",//博客url
    logo: "",//博客logo
    describe: "",//博客描述
    cover: "",//博客截屏
    联系我: ''//如果有任何问题，请给我发邮件
},
```

## v1.3.3

该版本为部分bug解决和功能增加

详细请查看<a href="https://github.com/qsyyke/vuepress-theme-aurora/releases/tag/v1.3.3">V1.3.3</a>

从该版本开始，主题commit message将完全按照`angular`规范进行提交

## v1.3.2

该版本目前已知的bug有

- 1.运行`npm run build`命令，当build成功之后，并不会自动退出，需要手动`ctrl c`才能退出build过程，如果你会配置`webpack`，应该可以解决这个问题

- 2.站点在`ipad`上，或者一些平板上，会出现logo和文字被挤在一起的情况，像下图这样

  ::: details 查看

  ![image-20211013103447635](https://ooszy.cco.vin/img/blog-note/image-20211013103447635.png?x-oss-process=style/pictureProcess1)

  :::

- 3.在`ipad`或者某些电脑上，文章等顶部图片会显得特别大(`顶部图片高度使用vh，所以会出现这个问题`)，这个问题主要存在于部分iPad和电脑

  ::: details view

  ​	![image-20211013104423679](https://ooszy.cco.vin/img/blog-note/image-20211013104423679.png?x-oss-process=style/pictureProcess1)

  ![image-20211013104450699](https://ooszy.cco.vin/img/blog-note/image-20211013104450699.png?x-oss-process=style/pictureProcess1)

  :::

- 4.在某些ipad或者电脑上，首页文章图片会显得很小，影响美观，像下图一样

  ::: details view

  ![image-20211013104220662](https://ooszy.cco.vin/img/blog-note/image-20211013104220662.png?x-oss-process=style/pictureProcess1)

  :::



- 5.友情链接，标签等页面，刷新会出现404，对于相册页面，如果进入该页面，在刷新，会出现404问题(`这些页面使用动态路由，所以就出现这种情况`)

  ::: details view

  ​	![image-20211013104857123](https://ooszy.cco.vin/img/blog-note/image-20211013104857123.png?x-oss-process=style/pictureProcess1)

  :::

​		



- 6.在火狐浏览器上访问站点，首页侧边栏，和文章列表，会出现滚动条



## 提交bug

> 如果你在使用此主题的过程中，还遇到其他的bug，那么欢迎在此页面留言或者到github提交issue



<a target="_blank" href="https://github.com/qsyyke/vuepress-theme-aurora/issues">Github Issue</a>