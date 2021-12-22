# 自定义图标

## 自定义顶部导航栏图标

1. 进入[iconfont-阿里巴巴矢量图标库](https://www.iconfont.cn/)

2. 挑选自己喜欢的图标

   ![image-20211111002835625](https://ooszy.cco.vin/img/blog-note/image-20211111002835625.png?x-oss-process=style/pictureProcess1)

![image-20211111002919678](https://ooszy.cco.vin/img/blog-note/image-20211111002919678.png?x-oss-process=style/pictureProcess1)

![image-20211111003008017](https://ooszy.cco.vin/img/blog-note/image-20211111003008017.png?x-oss-process=style/pictureProcess1)

![image-20211111003037996](https://ooszy.cco.vin/img/blog-note/image-20211111003037996.png?x-oss-process=style/pictureProcess1)

![image-20211111003109493](https://ooszy.cco.vin/img/blog-note/image-20211111003109493.png?x-oss-process=style/pictureProcess1)



![image-20211127211933492](https://ooszy.cco.vin/img/blog-note/image-20211127211933492.png?x-oss-process=style/pictureProcess1)

当你设置了`fontFamily`之后，先点击下载到本地看看

![image-20211111081548949](https://ooszy.cco.vin/img/blog-note/image-20211111081548949.png?x-oss-process=style/pictureProcess1)

![image-20211127212253018](https://ooszy.cco.vin/img/blog-note/image-20211127212253018.png?x-oss-process=style/pictureProcess1)

![image-20211111081748431](https://ooszy.cco.vin/img/blog-note/image-20211111081748431.png?x-oss-process=style/pictureProcess1)

然后将此链接复制下来，进入到`config.js`文件中

```js
module.exports = {
    head: [
        [
            "link",
            {
                rel: 'stylesheet',
                type: 'text/css',
                href: '这里替换成，你上一步在阿里矢量图标库中，复制的css链接'
            }
        ]
    ]
}
```



设置好之后，便可以更改`navbar`的图标了

![image-20211111082047409](https://ooszy.cco.vin/img/blog-note/image-20211111082047409.png?x-oss-process=style/pictureProcess1)

![image-20211111082237278](https://ooszy.cco.vin/img/blog-note/image-20211111082237278.png?x-oss-process=style/pictureProcess1)



然后启动，一定要注意，设置之后，一定需要重新启动，也就是重新运行`npm run dev`命令

![image-20211111082400666](https://ooszy.cco.vin/img/blog-note/image-20211111082400666.png?x-oss-process=style/pictureProcess1)



::: tip

我只对一级导航设置了图标，对于二级，三级，四级...导航，你设置了也没有用

```js
navbar: [
    {
        //这里是一级导航
        text: '问题和bug',
        iconClass: 'aurora-si-glyph-global',
        
        //children中配置的是二级导航
        children: [
            {
                text: 'CHANGELOG',
                children: [
                    '/issue/CHANGELOG.md'
                ]
            },
            {
                text: '主题详细搭建教程',
                children: [
                    '/readme/introduce.md',
                    '/use/useTheme.md'
                ]
            },
            {
                text: '问题',
                children: [
                    '/issue/',
                ]
            },
            {
                text: 'bug',
                children: [
                    '/issue/bug.md',
                ]
            }
        ]
    },
]
```



该主题不仅支持阿里矢量图标库，还支持其他的，只是我演示使用阿里矢量图标库，对于其他的，包括使用阿里矢量图标库，一定要保证图标的`font-family`是`aurora-font`，否则不会生效，因为阿里矢量图标库，他默认就会为每一个图标设置`class`，并且设置

```css
.custom-aurora-xxx:before {
  content: "\exxx";
}
```

所以你可以直接复制这个图标得类名就可以了，对于其他的图标，你可以参照这个[css](http://at.alicdn.com/t/font_2919721_uriegglr5q.css)样式进行设置



:::



到这里就完成了导航栏自定义图标

