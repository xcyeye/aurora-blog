---
date: 2021/12/17 16:14
title: 自定义代码块样式及解决C#高亮问题
---

在本主题中，支持自定义代码块高亮样式，使用的是[highlightjs](https://highlightjs.org/)

![image-20211217161621978](https://ooszy.cco.vin/img/blog-note/image-20211217161621978.png?x-oss-process=style/pictureProcess1)



## 下载css

在使用之前，你需要先下载`highlightjs`的css文件，我们是通过导入高亮css样式，来切换代码块高亮样式的，请在当前博客中，运行下面命令

```sh
npm install highlight.js
```



## 导入css



![image-20211217162242259](https://ooszy.cco.vin/img/blog-note/image-20211217162242259.png?x-oss-process=style/pictureProcess1)

当运行`npm install highlight.js`命令之后，会将highlight.js的所有文件放置在`node_modules`目录中，你需要在此目录中，找到一个文件夹为`highlight.js`的目录，点击进去

> 如果你实在没有找到这个`highlight.js`文件夹，那么就是你没有运行`npm install highlight.js`这个命令，或者是没有在正确的地方运行

![image-20211217162321915](https://ooszy.cco.vin/img/blog-note/image-20211217162321915.png?x-oss-process=style/pictureProcess1)

![image-20211217162422523](https://ooszy.cco.vin/img/blog-note/image-20211217162422523.png?x-oss-process=style/pictureProcess1)

![image-20211217162813364](https://ooszy.cco.vin/img/blog-note/image-20211217162813364.png?x-oss-process=style/pictureProcess1)


## 修改config.js

进入`docs/.vuepress/config.js`文件内，将下面的代码加入到此文件中

```js
extendsMarkdown: (md) => {
    md.set({
        highlight: function (str, lang) {
            if (lang && require('highlight.js').getLanguage(lang)) {
                try {
                    //if (lang === "c") {
                    //    lang = "csharp"
                    //}
                    return require('highlight.js').highlight(str, { language: lang }).value;
                } catch (__) {}
            }

            return '';
        }
    })
},
```



::: details 必看

![image-20211217163636994](https://ooszy.cco.vin/img/blog-note/image-20211217163636994.png?x-oss-process=style/pictureProcess1)

:::



## 使用样式

待你上面步骤完成之后，你现在只需要在`docs/.vuepress/styles/index.css`文件内，使用你刚才复制的那些样式就行了

```css
@import "css样式路径";
```

> 这里的css样式文件，就是你刚才在[第二步](#导入css)中，从`\node_modules\highlight.js\styles`内复制出来的文件，如果你的路径正确的话，那么你现在刷新网页，就会看到代码块样式发生改变了

![image-20211217164321437](https://ooszy.cco.vin/img/blog-note/image-20211217164321437.png?x-oss-process=style/pictureProcess1)



::: tip

因为highlightjs的样式非常多，有200多款，你具体喜欢哪种，请自己尝试，上图中的,styles目录下的css样式，每一个css文件，就是一个代码块样式，我这里也没有能够直接展示这200多款样式的工具，需要你自己修改`@import "css样式路径";`的样式文件路径，才能看到效果

:::





## 关于C#代码块不能高亮问题

如果C#代码块不能高亮，请修改一下下面的判断条件，如果你使用其他语言，也是代码块高亮不正确的话，也是一样的，修改一下判断条件

```js
extendsMarkdown: (md) => {
    md.set({
        highlight: function (str, lang) {
            if (lang && require('highlight.js').getLanguage(lang)) {
                try {
                    /*
                    修改一下下面，如果同时存在多个语言，代码块不能正常显示，也是增加多个判断就行
                    这是是写c，但是我修改成csharp的原因是因为，C#代码块，默认显示的时候，是c，但是其真正的名字需要为csharp，才能正常显示，否则C#代码
                    块，最终就是以C的高亮显示
                    */
                    if (lang === "c") {
                        lang = "csharp"
                    }
                    return require('highlight.js').highlight(str, { language: lang }).value;
                } catch (__) {}
            }

            return '';
        }
    })
},
```

