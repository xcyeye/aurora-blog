# 文章配置

## 侧标栏

侧边栏是自动生成的，你可以更改自动生成的规则

```js
//文章侧边栏自动获取的层次 默认为1，也就是http://localhost:8080/config/feature/donate.html,只会自动生成feature目录下的文件
sidebarCatalogLevel: 1,
```



## 图片懒加载

图片懒加载有默认值，但是你也可以自己指定

```js
module.exports = {
    themeConfig: {
        //文章懒加载图片 仅限文章，首页文章占位图片并不是这个
        lazyLoadingImg: "https://ooszy.cco.vin/img/blog-public/ljz.gif",
    }
}
```



## 文章推荐

文章推荐设置可以查看

```js
//文章底部最大推荐文章数 默认值为30
recommendPageLength: 30,

//推荐列表标题为空时，就会使用这个进行代替，默认是`╮(￣▽￣)╭`
recommendNoTitle: "`╮(￣▽￣)╭`",
```



## edit

<a href="https://v2.vuepress.vuejs.org/zh/reference/default-theme/config.html#editlink">点击</a>查看vuepress的editlink设置



## 最后更新时间

<a href="https://v2.vuepress.vuejs.org/zh/reference/default-theme/config.html#lastupdated">点击</a>查看vuepress的lastupdated设置



## 贡献者



<a href="https://v2.vuepress.vuejs.org/zh/reference/default-theme/config.html#contributors">点击</a>查看vuepress的contributors设置



## 上一页，下一页

![image-20211021085208081](https://ooszy.cco.vin/img/blog-note/image-20211021085208081.png?x-oss-process=style/pictureProcess1)





上一页，下一页会自动获取，并不能在`frontmatter`中，指定下一页，上一页，往后版本会增加



## 赞赏功能

[赞赏配置](../feature/donate.md)



## SEO配置

[seo](../feature/seo.md)



## 文章推荐

[文章推荐](../feature/recommend.md)



## 评论

[评论](../comment/)

## 页脚

[页脚](../footer.md)