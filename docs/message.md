# 公告栏

公告栏显示在侧边栏，显示效果如下

![image-20211011160422278](https://ooszy.cco.vin/img/blog-note/image-20211011160422278.png?x-oss-process=style/pictureProcess1)



## 配置

```js
module.exports = {

    themeConfig: {
        //公告，是一个数组，支持图片，HTML
        message: [
            '该博客主题为Aurora,<a href="https://github.com/qsyyke/vuepress-theme-aurora">vuepress-theme-Aurora</a>',
            "主题交流群: 681602026，欢迎各位大佬进群交流",
        ],
    }
}
```



::: tip

`message`项是一个数组，可以支持HTML，图片等

:::

