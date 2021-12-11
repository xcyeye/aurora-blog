---
stick: true
date: 2021/10/10 12:45
title: ceshi
tag: [标签1,标签1,标签1,标签1,标签1,标签1,标签1,标签1,标签1,标签1,标签1,标签1]
---



# Aurora主题问题解决

::: tip

这里是你在使用`aurora`主题的时候，必须，一定要读的选项，汇总了一些相关概念，以及使用技巧，我语文一直不好，如果文档有不好的地方，请多多包涵。

在使用的过程中，在你执行`npm run dev`成功之后，需要修改配置，请先读一遍该文档，有不会的地方，再在群里进行询问，我看到消息都会解答。对于一些非主题的问题，请自己先百度尝试解决吧，因为是一个开源项目，目前也只有我一个人，精力有限，我做不到一步一步的教你搭建，运行，部署，如果你是一个技术小白，那么在搭建的时候，遇到问题，直接在群里询问，因为在搜索引擎上搜索到答案，也不知道如何解决

:::

<Pins username="anuraghazra" repo="github-readme-stats"/>

## 使用和需学习知识

请根据自己的实际情况，选择安装

### 技术小白

::: tip

如果你是一个技术小白，请先安装下面的软件，搭建好运行环境，请一步一步的按照文档走，如果遇到问题，请直接在[QQ群](https://jq.qq.com/?_wv=1027&k=R7DF9XtV)里进行询问，或者联系我[QQ](tencent://message/?uin=2291308094)

:::



1. 安装[Node环境](../node.md)，node是必须要安装的
2. 安装[Git]()，非必须，还是推荐安装上，你如果不需要使用Github page进行托管的话，不需要安装
3. 安装[VsCoe](https://blog.csdn.net/weixin_43883917/article/details/113867914),一定要安装一个编辑器，这里推荐使用`VsCode`
4. 学习[MarkDown语法](https://www.runoob.com/markdown/md-title.html)，必须要学习的内容，否则不能正常写博客



> 只需要安装上面三个和学习MarkDown语法就行就行



### 非技术小白

::: tip

如果你在使用过程中，遇到非主题上得问题，请先百度，谷歌看能够解决，目前主题安装上，遇到的错误，都可以通过搜索引擎解决，如果解决不了，再[询问我](tencent://message/?uin=2291308094)或者到[主题交流群](https://jq.qq.com/?_wv=1027&k=R7DF9XtV)中进行询问，我看到都会回复

:::

如果你是一个程序员，或者会简单的使用`Git`，一些编辑工具，那么下面的选项，请根据自己的情况进行安装

1. 安装[Node环境](../node.md)，node是必须要安装的
2. 安装`Git`，推荐安装
3. 编辑器的话，看你喜欢哪个，只要能编辑的，都可以
4. 学习[MarkDown语法](https://www.runoob.com/markdown/md-title.html)，必须要学习的内容，否则不能正常写博客





## 博客目录结构

::: tip

在使用之前，一定要先了解该博客的目录结构，这对你写博客很有帮助

:::



![image-20211115224947764](https://ooszy.cco.vin/img/blog-note/image-20211115224947764.png?x-oss-process=style/pictureProcess1)

其中，除了自己自动创建的文件夹之外，全部都不要手动创建，所以你如果没有`docs,.vuepress,public,styles`等目录的话，是需要你自己创建(`因为运行npm run dev`就会生成.temp等文件夹，也就会默认创建`docs/.vuepress`目录，所以这种情况下，你就不要在手动创建`docs`和`.vuepress`目录了)



### package.json

此文件必须至少包含下面代码，否则主题不能运行成功

```json
{
  "name": "blog-demo",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "dev": "vuepress dev docs",
    "build": "vuepress build docs"
  },
  "author": "",
  "license": "ISC",
  "dependencies": {
    "vuepress": "^2.0.0-beta.27",
    "vuepress-theme-aurora": "^1.7.1"
  }
}
```

如果你需要在`package.json`文件中，加入其他的一些配置，请在搜索引擎中搜索

> 搜索内容: `常见package.json文件配置`

### config.js

此文件是主题目录文件，其文件位置为`docs/.vuepress/config.js`，如果你没有的话，需要自己创建`config.js`文件

> `docs/.vuepress/config.js`并不是一个文件的名称，而是一个`config.js`文件，在`.vuepress`这个文件夹目录中




## 安装主题之后，如何进行配置?

该主题提供大量的[全部配置信息概览](https://aurora.xcye.xyz/home/config.html)，[快速开始](https://aurora.xcye.xyz/readme/)，请跟着配置文档一步一步的配置

::: tip

如果你不是克隆我的[仓库](https://github.com/vuepress-aurora/vuepress-theme-aurora)，那么为了配置方便，请直接复制[Github config.js](https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/docs/.vuepress/config.js)内的所有内容，然后替换`docs/.vuepress/config.js`内的所有内容

:::



![image-20211115230757485](https://ooszy.cco.vin/img/blog-note/image-20211115230757485.png?x-oss-process=style/pictureProcess1)



## 如何添加说说功能<Badge type="tip" text="v1.6.0以后版本" vertical="top" />

说说功能需要依赖于[vuepress-plugin-coze](https://www.npmjs.com/package/vuepress-plugin-coze)插件，详细配置请[查看](https://aurora.xcye.xyz/plugin/coze)



## 如何自定义导航栏图标<Badge type="tip" text="v1.6.0以后版本" vertical="top" />

请[查看](https://aurora.xcye.xyz/style/icon.html)


## 遇到报错如何解决?

你如果遇到报错信息，请先尝试复制报错信息到搜索引擎中，进行查找，使用上面对应的方法，看能不能解决，推荐使用[Bing](https://cn.bing.com/)或者[Google](https://google.com)

如果还未解决，请联系我或者到主题交流群进行询问，截图的时候，记得截完整的报错信息，包括控制台，和浏览器控制台(`在浏览器中按住F12,点击Console`)

| QQ                                                           | 主题交流群                                                   | 微信                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233620332.png?x-oss-process=style/pictureProcess1" style="zoom:33%;" /> | <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233827133.png?x-oss-process=style/pictureProcess1" alt="image-20211024233827133" style="zoom:33%;" /> | <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233735110.png?x-oss-process=style/pictureProcess1" alt="image-20211024233735110" style="zoom: 39%;" /> |

## 新建md文件后，首页不显示<Badge type="tip" text="针对于v1.3.0以后版本" vertical="top" />

有两个原因

1. 首先看你新建的这个md文件的位置是否正确，`.md`文件，需要放在`docs`目录下，注意是放在`docs`目录下，不能放在`docs/.vuepress`目录下

2. 该`.md`文件确实放在`docs`目录下，但是你没有重新运行`npm run dev`命令，所以也不会在首页显示

::: tip

在`docs`目录下，新建md文件，或者是修改`docs/.vuepress/config.js`内的配置后，都需要重新运行`npm run dev`才可以生效

:::



## 如果编写文章？

因为此博客的所有文章都是存放在`docs`目录下的，vuepress会将所有在此`docs`目录下的md文件，自动解析成`.html`文件

> 注意，这里说的是`docs`目录，你不要放在`docs/.vuepress`目录下，而且你需要重新运行`npm run dev`命令才能够生效

你需要学习一下[markdown语法](https://www.runoob.com/markdown/md-title.html)，除此以外，推荐你下载[Typora](https://typora.io/)，此软件书写md文件，非常棒，你如果不想看文章，也可以去bilibili搜索如何使用typora软件教程



## 如何部署？？？

关于如何部署这个问题，你需要先了解vuepress是一个静态网站生成框架，当运行`npm run build`命令之后，vuepress会将`docs`目录下的所有`.md`文件(`不包括docs/.vuepress目录内的md文件`)自动解析成`.html`文件，并且`自动`放在`docs/.vuepress/dist`目录下，此目录下，所有的html和js等，便是你博客的所有页面和js等文件

![image-20211115232524302](https://ooszy.cco.vin/img/blog-note/image-20211115232524302.png?x-oss-process=style/pictureProcess1)



所以了解了vuepress的基本过程，那么部署我们自己的博客，其实也就是如何部署`docs/.vuepress/dist`目录下的所有静态html文件，所以你就不要再让我一步一步的教你如何部署这些静态文件！！，有以下几个途径，但是不局限于这些，仅仅是我目前知道的，你也可以直接看[vuepress官方](https://v2.vuepress.vuejs.org/zh/guide/deployment.html#netlify)的

1. 使用云服务器部署

   > 如你可以直接将`docs/.vuepress/dist`目录下的所有文件，直接上传到你云服器某个域名空间下，直接使用域名就可以访问

2. 使用`Github `进行托管

   > [部署](https://aurora.xcye.xyz/home/deploy.html)，其实也就是将`docs/.vuepress/dist`目录下的所有文件，push到github的某个仓库，某个分支中，然后你就可以简单设置一下，就可以通过github给的一个免费域名(`https://<user>.github.io/<repo>`)直接访问你博客了

   ![image-20211115233623578](https://ooszy.cco.vin/img/blog-note/image-20211115233623578.png?x-oss-process=style/pictureProcess1)



3. 使用[vercel](https://vercel.com/guides/deploying-vuepress-to-vercel)，这个并不是部署静态资源，如果你需要使用vercel进行部署的话，你需要将你博客的索赔由于文件(`除node_modules`)都push到github，然后在vercel中导入你push到github的这个仓库，然后设置一下部署流程就行

   > 记住，是所有除`node_modules`外的所有文件及文件夹，包括`docs`下的所有，`package.json`文件等等

4. 还有其他很多的静态资源部署方式





## Github Actions部署是什么？？

如果你需要使用Github Actions进行部署，那么你的先知道`Github Actions`是什么！！

> 我们可以在Github中持续集成很多操作方法，比如抓取代码、运行测试、登录远程服务器，发布到第三方服务等等，把这些操作就称为 actions。
>
> 所以`Github Actions`也就是会自动执行你设置的一些命令，按照你`.github\workflows`目录内编写的文件自动执行
>
> 如果你还想要链接更多的Github Actions信息，推荐你看一下[GitHub Actions 入门教程 - 阮一峰的网络日志 (ruanyifeng.com)](http://www.ruanyifeng.com/blog/2019/09/getting-started-with-github-actions.html)



::: tip

所以如果需要使用`Github Actions`完成自动部署，那么你就必须将你博客项目中，除了`node_modules`文件夹之外的所有文件及文件夹，全部都push到github的某个分支中(`看你喜好，我的是main分支`)，然后github只要检测到你该分支中的内容发生改变，就会自动触发`.github/workflows/`目录下某个yaml(`或者yml`)文件，自动执行一下命令

1. `npm run build` 目的: 在dist目录中，生成你博客的所有html及其js，css灯光
2. `进入dist` 
3. `初始化该dist目录，然后自动将该目录内的所有html及其他文件push到该仓库中的某个分支中`

一般最开始使用`Github Actions`都会出错，你可以看到他执行的过程，如果出错的话，请多试几次(`我最开始也是这样`)，然后在对照其执行过程，看看哪一步出错，自己先尝试解决吧

:::



![image-20211116084812735](https://ooszy.cco.vin/img/blog-note/image-20211116084812735.png?x-oss-process=style/pictureProcess1)

![image-20211116084840153](https://ooszy.cco.vin/img/blog-note/image-20211116084840153.png?x-oss-process=style/pictureProcess1)



![image-20211116085044115](https://ooszy.cco.vin/img/blog-note/image-20211116085044115.png?x-oss-process=style/pictureProcess1)



![image-20211116085230372](https://ooszy.cco.vin/img/blog-note/image-20211116085230372.png?x-oss-process=style/pictureProcess1)



![image-20211116085338792](https://ooszy.cco.vin/img/blog-note/image-20211116085338792.png?x-oss-process=style/pictureProcess1)



::: tip

如果是技术小白的话，使用github Actions出错，也就是状态是红色，请直接在[QQ群](https://jq.qq.com/?_wv=1027&k=R7DF9XtV)里进行询问，或者联系我[QQ](tencent://message/?uin=2291308094)，如果是非技术小白，请先看一下哪一步出错，看一下自己push到仓库的文件是否完整，如果你克隆我github，那么一般会报个`theme not found`，请看一下`docs/.vuepress/config.js`中的`theme`项使用的是本地主题还是npm安装的主题，插件也会存在这个问题，如果多次都解决不了请直接在[QQ群](https://jq.qq.com/?_wv=1027&k=R7DF9XtV)里进行询问，或者联系我[QQ](tencent://message/?uin=2291308094)

:::



## 关于静态图片

如果在博客中，访问本地的图片，在介绍之前，请一定查看[官方](https://v2.vuepress.vuejs.org/zh/guide/assets.html)的说明，自己先写个md文件，引用本地图片

## 安装依赖，主题之后，没有config.js等文件

除了`package.json,package-lock.json,node_modules`文件及目录之外，其余的所有文件夹以及文件都是需要你自己创建

运行必须的文件及文件夹有

| 路径                              | 类型   | 描述                                                         |
| --------------------------------- | ------ | ------------------------------------------------------------ |
| docs                              | 文件夹 |                                                              |
| docs/.vuepress                    | 文件夹 | 放置配置文件，临时文件，build后的文件                        |
| docs/.vuepress/config.js          | 文件   | 主题及站点配置文件                                           |
| docs/.vuepress/public             | 文件夹 | 静态文件，可以放置图片等，访问`localhost[端口]/静态资源文件名` |
| docs/.vuepress/styles             | 文件夹 | 覆盖及自定义css样式                                          |
| docs/.vuepress/styles/index.css   | 文件   | 可以在此css文件内，覆盖主题的所有样式                        |
| docs/.vuepress/styles/palette.css | 文件   | 定义css变量，可以在index.css文件内，使用这些定义的变量       |



## 需要vue知识？

如果你只是搭建一个博客，不想对博客进行一些功能上的升级，那么不需要vue知识，只需要安装`node`，运行几个命令就可以了，所有的配置都可以在`docs/.vuepress/config.js`文件内进行配置



::: tip

注意需要的命令(`前提你直接复制https://aurora.xcye.xyz/readme/下的scripts内容`)

- `npm run dev`

  > 在本地运行，比如你写md文件的时候，可以运行起来，看看效果

- `npm run build`

  > 在`docs/.vuepress/dist`目录下，生成包含`index.html`等页面内容

:::



## 主题如何升级

前提是你没有使用本地主题

::: details 本地主题?

如果`config.js`下的theme像下面这样，那么你使用的就是本地主题

```js
theme: path.resolve(__dirname, "theme/lib/node/index.js"),
```



如果使用下面这个样子，那么就是使用的是npm安装的主题

```
theme: 'aurora',
```

:::



![image-20211026223646160](https://ooszy.cco.vin/img/blog-note/image-20211026223646160.png?x-oss-process=style/pictureProcess1)



> 主题最新版，可以在GitHub中查看[releases](https://github.com/vuepress-aurora/vuepress-theme-aurora/releases)或者在<a href="https://www.npmjs.com/package/vuepress-theme-aurora" target="_blank">npm</a>查看



## 在首页没有显示最后更新时间

因为文章的最后更新时间，是根据你`git commit`获取的，所以你需要在`package.json`同级目录下，初始化git仓库，或者在`docs`目录下，最后`git commit`之后就可以了

![image-20211111092140467](https://ooszy.cco.vin/img/blog-note/image-20211111092140467.png?x-oss-process=style/pictureProcess1)

然后输入命令

```sh
git init && git add . && git commit -m "update"
```



## 如何部署到服务器上？

- 你可以运行`npm run build`命令之后，会在`docs/.vuepress/dist`目录下，生成所有文章的HTML文件，你可以直接将此文件内的内容，像静态页面那样，部署到服务器中
- 如果你使用GitHub进行托管，那么你可以看一下这篇文章https://v2.vuepress.vuejs.org/zh/guide/deployment.html，我推荐使用GitHub Actions进行自动部署[详细](https://aurora.xcye.xyz/home/deploy.html)
- 你也可以使用其他方式进行部署



## 如何自定义主题样式

你可以根据自己的喜好，对主题的css样式进行修改，在`docs/.vuepress/styles/index.css`文件内的样式，会覆盖注意的默认样式，所以你可以直接在`docs/.vuepress/styles/index.css`内修改需要改变的样式



## 什么是Front Matter

在md文件中，你可以设置是否置顶，设置更新时间，设置标签等等，都需要使用到`frontMatter`，这个需要使用`yaml`语法，详细可以查看官方<a href="https://v1.vuepress.vuejs.org/zh/guide/frontmatter.html">Front Matter</a>



::: details 实例

```md
---
stick: false #是否置顶
categories: [首页配置,一言,头像,社交,页脚] #标签
date: "2021/10/19 21:18" #手动设置最后更新时间，如果有git commit，将使用该值作为最后更新时间
layout: RegisterCom #为该md设置layout
---

# 这是一个FrontMatter演示
```



:::

## 报错解决



### 更换主题之后，浏览器报错`Uncaught (in promise) ReferenceError: Cannot access 'clientAppEnhances' before initialization`



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
    title: '一款简洁.美观.功能强大的vuepress主题',
    //themeConfig: {}
}
```



:::



#### 解决

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



### 运行`npm run dev`之后，报下面错误<Badge type="tip" text="v1.3.2以下版本" vertical="top" />

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





### 首页文章列表没有显示文章更新时间

修改测试

![image-20211013102443617](https://ooszy.cco.vin/img/blog-note/image-20211013102443617.png?x-oss-process=style/pictureProcess1)

如果在这里没有显示该文章的更新时间，这是因为你`docs/`下没有`.git`文件夹，因为此功能是通过git的`commit`获取的

![image-20211013102746980](https://ooszy.cco.vin/img/blog-note/image-20211013102746980.png?x-oss-process=style/pictureProcess1)









