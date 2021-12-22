# 在aurora主题中，加入看板娘教程

最终效果

![image-20211212151305342](https://ooszy.cco.vin/img/blog-note/image-20211212151305342.png?x-oss-process=style/pictureProcess1)

::: tip

如果你还不会使用插件，请先看一下这篇[文章](../base/plugin.md)

:::

## 下载依赖

因为这个需要使用到插件，所以需要先下载依赖，请在命令行窗口中，执行下面命令

```sh
npm install -D vuepress-plugin-live2d-plus
```

## 使用

当下载好之后，请到`docs/.vuepress/config.js`文件中，加入此插件

> 没有这个`docs/.vuepress/config.js`文件？这是一个路径...



进入到`config.js`文件内，将下面内容，添加到对应位置

```js
plugins: [
    ['vuepress-plugin-live2d-plus', {
        enable: true,
        model: {
            url: 'https://cdn.jsdelivr.net/gh/qsyyke/vscode-live2d-models/model-library/haru02/haru02.model.json'
        },
        display: {
            position: 'left',
            width: '135px',
            height: '300px',
            xOffset: '5%',
            yOffset: '5%'
        },
        mobile: {
            show: true
        },
        react: {
            opacity: 0.8
        }
    }
    ],
]
```

::: details 什么是对应位置?

![image-20211212155421101](https://ooszy.cco.vin/img/blog-note/image-20211212155421101.png?x-oss-process=style/pictureProcess1)

:::



::: details 最终

```js
const { themeConfig } = require("./themeConfig");
module.exports = {
    plugins: [
        ['vuepress-plugin-live2d-plus', {
            enable: true,
            model: {
              url: 'https://cdn.jsdelivr.net/gh/qsyyke/vscode-live2d-models/model-library/haru02/haru02.model.json'
            },
            display: {
              position: 'left',
              width: '135px',
              height: '300px',
              xOffset: '5%',
              yOffset: '5%'
            },
            mobile: {
              show: true
            },
            react: {
              opacity: 0.8
            }
          }
        ],
    ],

    theme: 'aurora',
    title: '一款简洁.美观.功能强大的vuepress主题',
    themeConfig: {
        
    }
}
```

:::



## 选择模型

进入[这里](https://github.com/qsyyke/vscode-live2d-models)选择自己喜欢的模型

![image-20211212155716585](https://ooszy.cco.vin/img/blog-note/image-20211212155716585.png?x-oss-process=style/pictureProcess1)

设置好之后，重新运行`npm run dev`即可

::: tip

如果你需要修改显示的位置，请修改一下下面配置，这个根据你自己的需要，自己修改,都是css样式

```js
display: {
    position: 'left',
    width: '135px',
    height: '300px',
    xOffset: '5%',
    yOffset: '5%'
},
```



:::