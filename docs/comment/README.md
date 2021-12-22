---
tag: [评论配置]
---



# 评论

::: tip 

Aurora主题加入了评论，使用[Waline | Waline](https://waline.js.org/)集成评论，Valine是一款基于 Valine 衍生的简洁、安全的评论系统。

:::



## 获取serverURL



请移步到Waline官网，根据他们的教程，获取serverURL

[快速上手 | Waline](https://waline.js.org/guide/get-started.html#leancloud-设置-数据库)

## 配置

在配置文件`config.js`文件中，

```js
module.exports = {

    themeConfig: {
        comment: {
            //是否显示评论
            showComment: true,
            serverURL: '上一步得到的serverURL地址',
        },
    }
}
```

关于评论的详细配置项，请看[快速上手 | Waline](https://waline.js.org/guide/get-started.html#leancloud-设置-数据库)，目前主题集成上述项配置，如需Waline其他的配置项，请自行克隆<a href="https://github.com/vuepress-aurora/vuepress-theme-aurora">主题源码</a>进行更改，评论组件位置`docs/.vuepress/theme/lib/client/components/global/Comment.vue`,所有的配置都是在`docs/readme.md`中进行配置

> 所有评论配置项，必须放在`comment`对象中



## 组件使用

`Comment`是一个全局组件，目前默认只在关于页面，文章页面以及友情链接页面进行使用，如果在其他页面使用，可以直接`<Comment/>`

