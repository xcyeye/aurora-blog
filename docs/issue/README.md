---
stick: true
date: 2021/10/10 12:45
---



# Aurora主题问题解决



::: tip

这里是你

:::

s时代峰峻看还<Badge type="tip" text="水电费会计和" vertical="top" />

## 更换主题之后，浏览器报错`Uncaught (in promise) ReferenceError: Cannot access 'clientAppEnhances' before initialization`



如果你更换主题之后，打开浏览器，报下面错误

![image-20211012082239703](https://ooszy.cco.vin/img/blog-note/image-20211012082239703.png?x-oss-process=style/pictureProcess1)

![image-20211012082253643](https://ooszy.cco.vin/img/blog-note/image-20211012082253643.png?x-oss-process=style/pictureProcess1)

这个错误我暂时不知道是因为什么造成的，而且，当遇到这个错误，你在`config.js`将下面内容全部注释之后，使用默认主题，也会出现这个错误

::: details 注释内容

```js
//const { themeConfig } = require("./themeConfig");
module.exports = {
    plugins: [
        [
            '@vuepress/plugin-search',
            {
                locales: {
                    '/': {
                        placeholder: 'Search',
                    },
                    '/zh/': {
                        placeholder: '搜索',
                    },
                },
            }
        ],
    ],
    head: [
        [
            "script",
            {
                src: "https://at.alicdn.com/t/font_2849934_v6y652peian.js",
            },
        ]
    ],
    //theme: 'aurora',
    /*onPrepared: async (app) => {
        const myData = app.pages.map((page) => {
            return page
        })
        await app.writeTemp('my-data.js', `export default ${JSON.stringify(myData)}`)
    },*/
    title: '一款简洁.美观.功能强大的vuepress主题',
    //themeConfig
}
```



:::



### 解决

我目前的解决方案为：

- 1.删除`docs/.vuepress/.cache`和`docs/.vuepress/.temp`两个文件夹

<img src="https://ooszy.cco.vin/img/blog-note/image-20211012082533295.png?x-oss-process=style/pictureProcess1" alt="image-20211012082533295" style="zoom: 50%;" />

- 2.删除`node_modules`文件夹
- 3.注释`docs/.vuepress/config.js`内的全部内容，或者你可以删除此文件夹内的全部内容
- 4.如果你打开编辑器，那么关闭编辑器
- 5.重新启动编辑器，或者是命令面板
- 6.重新运行`npm install`，待此命令执行成功之后
- 7.将`docs/.vuepress/config.js`取消注释，或者添加你刚才删除的数据
- 8.直接执行命令`npm run dev`

便可以解决





## 运行`npm run dev`之后，报下面错误

![image-20211012092541299](https://ooszy.cco.vin/img/blog-note/image-20211012092541299.png?x-oss-process=style/pictureProcess1)

这是因为你在`docs/.vuepress/config.js`内有添加上下面代码

```js
module.exports = {
    ...
    //一定要添加下面代码
     onPrepared: async (app) => {
        const myData = app.pages.map((page) => {
            return page;
        });
        await app.writeTemp(
            "my-data.js",
            `export default ${JSON.stringify(myData)}`
        );
    },
    ...
}
```





## 首页文章列表没有显示文章更新时间

修改测试

![image-20211013102443617](https://ooszy.cco.vin/img/blog-note/image-20211013102443617.png?x-oss-process=style/pictureProcess1)

如果在这里没有显示该文章的更新时间，这是因为你`docs/`下没有`.git`文件夹，因为此功能是通过git的`commit`获取的

![image-20211013102746980](https://ooszy.cco.vin/img/blog-note/image-20211013102746980.png?x-oss-process=style/pictureProcess1)









