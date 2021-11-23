# vuepress-plugin-archive

::: tip

这是vuepress2的一个文章归档时间轴的一个插件

:::



![image-20211122003904803](https://ooszy.cco.vin/img/blog-note/image-20211122003904803.png?x-oss-process=style/pictureProcess1)





## 安装

```sh
npm i vuepress-plugin-archive
```



```js
//docs/.vuepress/config.js

const { path } = require("@vuepress/utils");

module.exports = {
    //在这里配置插件
    plugins: [
        [
            'archive',
            {
                //需要排除的页面url，在该数组里面的路径，都不会被统计
                excludes: ['/footer.html','/404.html','/about/','/mood/','/link/','/tag/','/photo/'],
                //当某篇文章没有标题时，将使用下面值进行替换
                noTitle: '暂时没有标题配置'
            }
        ],
}
```

当你配置好之后，该插件会注册一个全局组件`<AuroraArchive></AuroraArchive>`，你可以在任何地方使用该组件，从而在你网站中，开启文章归档功能，同时该插件会默认注册一个路由，你可以直接在浏览器中，打开该路由，查看归档页面`/aurora-archive`



如果你使用的不是`vuepress-theme-aurora`主题，那么你可以像下面这样为你网站添加归档功能

1. 创建`archive.md`文件,在`docs`目录下，创建一个`archive.md`文件

2. 在该md文件中，加入下面内容就可以了

   ```md
   ---
   layout: AuroraArchive
   ---
   ```

3. 打开浏览器，输入`localhost:[8080]/archive.html`，你就可以看到该归档页面了







## 配置

### noTitle

- String

> 暂时没有标题配置

### excludes

- Array

> 需要排除的页面url，在该数组里面的路径，都不会被统计



```js
excludes: ['/footer.html','message.html','/v1.3.0/']
```

![image-20211122004613835](https://ooszy.cco.vin/img/blog-note/image-20211122004613835.png?x-oss-process=style/pictureProcess1)





## css样式

你可以在`docs/.vuepress/styles/index.css`文件中，设置下面样式变量

::: tip

如果你使用的不是`vuepress-theme-aurora`主题，那么请先看[vuepress官方关于palette的介绍](https://v2.vuepress.vuejs.org/zh/reference/plugin/palette.html#palette)，官方默认使用的就是`SASS `，对应的你需要在`docs/.vuepress/styles/index.sass`中，覆盖下面的样式变量

:::

```css
:root {
    /* 时间线颜色 */
    --archive-timeline: rgba(144, 241, 239, 0.35);

    /* 鼠标移动到某个标题上，时间线上的圆点颜色 */
    --archive-timeline-active: pink;

    /* 鼠标移动到某个标题上，标题颜色 */
    --archive-timeline-active-title: pink;

    /* 归档页面的宽度 */
    --archive-box-width: 80%;
}
```



如果你想设置背景颜色，请自行f12查看对应类名，然后在`docs/.vuepress/styles/index.css`文件中，设置对应类名的css样式



