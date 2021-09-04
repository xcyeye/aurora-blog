# 文章配置

## 顶部图片

顶部图片是一个全局组件，可以直接使用，位置`docs/.vuepress/theme/lib/client/components/global/TopImage.vue`



## 侧标栏

侧边栏的配置可以查看vuepress官网<a href="https://v2.vuepress.vuejs.org/zh/reference/default-theme/config.html#sidebar">入口</a>，如下是我的部分配置

```js
module.exports = {
  themeConfig: {
    // 侧边栏对象
    // 不同子路径下的页面会使用不同的侧边栏
    sidebar: {
      "/issue/": [{
      children: [
        "/issue/idea.md",
        "/issue/前端.md",
        "/issue/README.md",
        "/issue/编译器.md",
      ],
      text: "issue",
    }, ],
    "/project/": [{
      children: ["/project/README.md"],
      text: "project",
    }, 
    ],
    },
  },
}
```





## 图片懒加载

![image-20210831192820453](http://ooszy.cco.vin/img/blog-note/image-20210831192820453.png?x-oss-process=style/pictureProcess1)

```yaml
#设置加载时的图片
lazyLoadingImg: https://ooszy.cco.vin/img/blog-public/ljz.gif
```

默认是上图



## 文章推荐

文章推荐设置可以查看



## edit

<a href="https://v2.vuepress.vuejs.org/zh/reference/default-theme/config.html#editlink">点击</a>查看vuepress的editlink设置



## 最后更新时间

<a href="https://v2.vuepress.vuejs.org/zh/reference/default-theme/config.html#lastupdated">点击</a>查看vuepress的lastupdated设置



## 贡献者



<a href="https://v2.vuepress.vuejs.org/zh/reference/default-theme/config.html#contributors">点击</a>查看vuepress的contributors设置



## 赞赏





