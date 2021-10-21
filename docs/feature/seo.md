# SEO

主题已对搜索引擎进行初步优化，主要就是自动设置关键词和描述，如果想要更深入的SEO优化，请自行解决



## 首页

首页的`description`和`keyword`通过以下修改，在`readme.md`文件中，不推荐在`config.js`中进行修改



```js
module.exports = {

    themeConfig: {
        description: 'qsyyke的个人博客，记录生活琐事，学习笔记',
        keyword: 'vuepress主题,vuepress theme,生活琐事,二次元博客,简约博客,博客主题,静态主题'
    }
}
```





## 文章页面

对于文章页面，你可以通过`frontmatter`语法在每个`.md`文件上进行修改，可以参照官方<a href="https://v2.vuepress.vuejs.org/zh/reference/config.html#description">description</a>



```yaml
---
description: 这是一个测试description项
keyword: key1,key2,key3,key4,key5
---
```

- description: String
- keyword: 数组



如果你没有在该`.md`文件中，没有对description或者keyword进行修改，那么默认通过获取当前文章的部分h2标签和部分p标签内容进行组合

description字符默认为`1220`，暂未在`config.js`中提供修改

keyword字符默认为90



## 社交信息配置

[社交信息配置](./social.md)