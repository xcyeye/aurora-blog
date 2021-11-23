# vuepress-plugin-bubble

::: tip

这是vuepress的一个浪漫气泡特效插件

:::

- 使用效果

![image-20211122002556797](https://ooszy.cco.vin/img/blog-note/image-20211122002556797.png?x-oss-process=style/pictureProcess1)





## 安装

```sh
npm i vuepress-plugin-bubble
```

```js
//docs/.vuepress/config.js

const { path } = require("@vuepress/utils");

module.exports = {
    //在这里配置插件
    plugins: [
        [
            'bubble',
            {
                //气泡数量 推荐0(不包括)到1之前的小数，
                bubbleNumber: 0.14,

                //气泡透明度 0到1之间的小数
                bubbleAlpha: 0.6,

                //透明度变化速度，越接近于0越好
                alphaChangeSpeed: 0.00001,

                //气泡大小，推荐0到1之间的值
                size: 0.4,

                //气泡大小变化速度 越小越好
                sizeChangeSpeed: 0.0002,

                //气泡上升速度
                riseSpeed: 0.4,

                //气泡颜色，白色rgb(255,255,255) 请传入255,255,255
                color: '255,255,255',

                //该气泡盒子的z-index值，请根据你自己主题，传入适当的值，否则可能不会显示
                zIndex: -2
            }
        ],
}
```





当你在`config.js`文件中，使用该插件后，该插件会自动在便会自动显示

![image-20211122003330518](https://ooszy.cco.vin/img/blog-note/image-20211122003330518.png?x-oss-process=style/pictureProcess1)



图中红框内的dom节点，便是该插件自动添加的，如果你确实配置了该插件，但是在首页中，并没有显示的话，那么请f12看一下你网站的css样式，并且传入正确，合适的`zIndex`值



::: tip

该插件所在节点的`z-index`是通过插件配置项中的`zIndex`进行配置的

:::



