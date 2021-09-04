# ccds主题初步使用

## 介绍

[部署](/home/deploy.md)

[目录结构](/home/directory-structure.md)

该ccds主题是基于vuepress，在默认主题的基础上进行修改，但也剔除了一些默认主题特点，该主题是一个应用于博客，并不建议用于文档编写，所以在首页，就不能使用feture.

ccds主题是一个动漫类型的博客主题，界面简洁，多色彩，多功能。



### 博客预览

#### **home page**

![image-20210831130544863](http://ooszy.cco.vin/img/blog-note/image-20210831130544863.png?x-oss-process=style/pictureProcess1)



---

#### **article page**

![image-20210831102155968](http://ooszy.cco.vin/img/blog-note/image-20210831102155968.png?x-oss-process=style/pictureProcess1)

---

![image-20210831102455370](http://ooszy.cco.vin/img/blog-note/image-20210831102455370.png?x-oss-process=style/pictureProcess1)

----



除此以外，主题还提供了毛玻璃效果，全局字体，颜色，背景设置，对于文章图片，加入了懒加载，提升页面打开速度



##### 字体，字体颜色多样

![image-20210831104744133](http://ooszy.cco.vin/img/blog-note/image-20210831104744133.png?x-oss-process=style/pictureProcess1)

![image-20210831104804688](http://ooszy.cco.vin/img/blog-note/image-20210831104804688.png?x-oss-process=style/pictureProcess1)

![image-20210831104842307](http://ooszy.cco.vin/img/blog-note/image-20210831104842307.png?x-oss-process=style/pictureProcess1)





#### **about page**

![image-20210831102825225](http://ooszy.cco.vin/img/blog-note/image-20210831102825225.png?x-oss-process=style/pictureProcess1)

---

![image-20210831103010376](http://ooszy.cco.vin/img/blog-note/image-20210831103010376.png?x-oss-process=style/pictureProcess1)

----



#### **friend link page**

![](http://ooszy.cco.vin/img/blog-note/friend%20link.png?x-oss-process=style/pictureProcess1)

----



#### tag page

![](http://ooszy.cco.vin/img/blog-note/tag-page.png?x-oss-process=style/pictureProcess1)



### **theme feature**

主题特点

- 多彩
- 高度自定义设置，大多数页面都提供了大量选项进行自定义设置
- 开箱即用
- 圆角，字体，颜色，背景图片等自定义
- 毛玻璃效果
- 图片懒加载



## 安装

运行环境需要依赖`node`，所以在安装之前，请确保操作系统已经安装了node



### 初始化

1. 创建一个文件夹

2. 进入此文件夹内，使用`npm init`初始化

    ```sh
    npm init
    ```

3. 将下面代码加入到`package.json`对应位置

    ```json
    "scripts": {
        "test": "echo \"Error: no test specified\" && exit 1",
        "dev": "vuepress dev docs",
        "build": "vuepress build docs",
        "deploy": "bash deploy.sh"
      }
    ```



### 安装

安装vuepress(`一定要保证vurepss版本为2以上`)

```sh
npm i vuepress@2.0.0-beta.22
```

```sh
npm i vuepress-theme-ccds
```

因为在主题部分组件中，使用到了`jquery`,`vuex`,`axios`,`easy-typer-js`，所以还需要安装这三个依赖

```sh
npm i jquery
npm i vuex@4.0.0
npm i axios@0.21.1
npm i easy-typer-js@2.1.0
```



所有package.json文件的内容为

```json
{
  "name": "blog",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1",
    "dev": "vuepress dev docs",
    "build": "vuepress build docs",
    "deploy": "bash deploy.sh"
  },
  "author": "",
  "license": "ISC",
  "devDependencies": {
    "@types/jquery": "^3.5.6",
    "jquery": "^3.2.1",
    "prettier": "^2.3.2",
    "vuepress": "^2.0.0-beta.22"
  },
  "dependencies": {
    "@vuepress/plugin-search": "^2.0.0-beta.24",
    "axios": "^0.21.1",
    "easy-typer-js": "^2.1.0",
    "stylus-loader": "^6.1.0",
    "vue-lazyload": "^1.3.3",
    "vuex": "^4.0.0-0"
  }
}
```



## 使用主题

安装完上述依赖之后，就可以直接使用ccds主题

### 1.新建readme.md文件

在`docs/`下，新建`readme.md`主题配置文件，此文件用于配置主题的所有信息，包括首页logo，head标签中的`description`和`keyword`(虽然vuepress在config.js中，可以对description和keyword进行配置，但是不推荐在这里进行配置，因为ccds主题，对所有页面的description和keyword进行过配置，如果在config.js中，再次配置的话，可能会出现意想不到的结果)

**在readme.md**中，对主题配置进行修改之后，需要重新启动，需要重新启动，需要重新启动才能生效，因为在部分页面中，获取readme.md中的配置，是通过启动生成的文件获取的，所以主题配置修改之后，需要重新启动

因为此主题还没有将所有的配置信息设置默认值，所以不能开箱即用，需要你将所有配置复制到`docs/readme.md`中，否则会报错，此文件可以在仓库中，进行复制<a href="https://github.com/qsyyke/vuepress-theme-ccds/blob/main/docs/README.md">readme.md</a>

粘贴的时候，请确保格式没有变，否则会启动报错，配置使用的语法为`yaml`

### 2.设置主题

```js
module.exports = {
    theme: 'vuepress-theme-ccds',
    onPrepared: async (app) => {
        const myData = app.pages.map((page) => {
            return page
        })
        await app.writeTemp('my-data.js', `export default ${JSON.stringify(myData)}`)
    },
    themeConfig: {
        darkMode: false,
        navbar: [
            {
                text: '主题使用',
                children: [
                    {
                        text: '主题使用',
                        link: '/issue/'
                    },
                ]
            },
            {
                text: "关于",
                link: "/about"
            },
            {
                text: '友情链接',
                link: '/link'
            },
            {
                text: '标签',
                link: '/tag'
            },
            {
                text: '心情',
                link: '/mood'
            }
        ]
    }
}
```

> 一定要在`config.js`文件中，加上下面代码，否则会报错
>
> ```js
> onPrepared: async (app) => {
>     const myData = app.pages.map((page) => {
>         return page
>     })
>     await app.writeTemp('my-data.js', `export default ${JSON.stringify(myData)}`)
> },
> ```



现在运行`npm run dev`，那么你在浏览器中，看到的页面是下面这样

![image-20210829151710110](http://ooszy.cco.vin/img/blog-noteimage-20210829151710110.png?x-oss-process=style/pictureProcess1)

如果页面和上图差不多，表示已经切换到ccsd主题，如果出现白页，请`f12`查看错误，很大可能是因为某些依赖没有安装

## 主题配置

下面将一次多主题进行配置

### 配置首页

新建`docs/readme.md`文件

在此文件最顶部插入

```markdown
---
home: true
---
```

`---`是vuepress特有的语法，上面就是将其设置为首页，进行此设置之后，回到浏览器，刷新，那么你看到的页面将变成下图所示

![image-20210829152305791](http://ooszy.cco.vin/img/blog-noteimage-20210829152305791.png?x-oss-process=style/pictureProcess1)



### 导航栏配置

导航栏的配置可以查看<a href="https://v2.vuepress.vuejs.org/zh/reference/default-theme/config.html#navbar">navbar</a>进行配置

```js
module.exports = {
  themeConfig: {
    navbar: [
      	//......
        {
            text: "关于",
            link: "/about"
        },
        {
            text: '友情链接',
            link: '/link'
        },
        {
            text: '标签',
            link: '/tag'
        }
    ],
  },
}
```

其中，`/about,/link,/tag`是三个已经添加的路由，对应关于组件，友情链接已经文章标签

### 导航栏logo配置



