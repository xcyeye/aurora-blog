---
stick: true
---





# Aurora主题详细使用教程

> 该文章会从零开始教你使用<a href="https://v2.vuepress.vuejs.org/zh/" target="_blank">Vuepress</a>搭建自己的博客网站,如果你会git和node的使用，那就更容易了
>
> 从node，Git安装，到创建vuepress站点
>
> 如果此篇文章对你有帮助，那么可以帮我分享给更多的人么

[[toc]]



## node安装

### 下载node

<a href="https://www.aliyundrive.com/s/2E9D8FW2mWq" target="_blank">点击下载</a>

点击上方链接，下载node，或者你也可以到官方网站进行下载

[Download | Node.js (nodejs.org)](https://nodejs.org/en/download/)

![image-20211013112000619](https://ooszy.cco.vin/img/blog-note/image-20211013112000619.png?x-oss-process=style/pictureProcess1)



当你下载完成之后，你会得到如下文件

![image-20211013112130043](https://ooszy.cco.vin/img/blog-note/image-20211013112130043.png?x-oss-process=style/pictureProcess1)



### 安装过程化

1. 双击点击安装

   ![image-20211013112222351](https://ooszy.cco.vin/img/blog-note/image-20211013112222351.png?x-oss-process=style/pictureProcess1)



2. 选择一个安装目录

   ![image-20211013132648644](https://ooszy.cco.vin/img/blog-note/image-20211013132648644.png?x-oss-process=style/pictureProcess1)

   这里的安装目录可以随便选择，`但是一定要记得此路径，在后面需要使用到`

3. 点击next

   ![image-20211013132823567](https://ooszy.cco.vin/img/blog-note/image-20211013132823567.png?x-oss-process=style/pictureProcess1)

   这里还是点击next

   ![image-20211013132911956](https://ooszy.cco.vin/img/blog-note/image-20211013132911956.png?x-oss-process=style/pictureProcess1)

   ![image-20211013132930873](https://ooszy.cco.vin/img/blog-note/image-20211013132930873.png?x-oss-process=style/pictureProcess1)

   ![image-20211013133002992](https://ooszy.cco.vin/img/blog-note/image-20211013133002992.png?x-oss-process=style/pictureProcess1)



​		然后等待安装完成之后，就可以了



### 设置环境和变量



#### 验证

上一步安装完成之后，按住`win r`键，输入`cmd`

![image-20211013133147151](https://ooszy.cco.vin/img/blog-note/image-20211013133147151.png?x-oss-process=style/pictureProcess1)



在面板中，输入`node -v`验证是否安装成功

![image-20211013133235630](https://ooszy.cco.vin/img/blog-note/image-20211013133235630.png?x-oss-process=style/pictureProcess1)

当出现`v14.17.3`时，表示node已经安装成功了，此值也有可能不同，取决于你下的node版本



#### 设置

1. 进入上一步安装node的目录中，你将看到如下文件

   ![image-20211013133600368](https://ooszy.cco.vin/img/blog-note/image-20211013133600368.png?x-oss-process=style/pictureProcess1)

2. 在此目录中，新建`node_cache`和`node_global`两个文件夹

   ![image-20211013133711581](https://ooszy.cco.vin/img/blog-note/image-20211013133711581.png?x-oss-process=style/pictureProcess1)



3. 在`cmd`面板中，运行下面两个命令，一个一个的运行

   ```sh
   npm config set prefix "D:\Develop\nodejs\node_global"
   npm config set cache "D:\Develop\nodejs\node_cache"
   ```

   ::: tip

   将后面的路径改成你自己对应文件夹就行

   :::



4. 然后再运行下面这个命令

   ```sh
   npm config set registry=http://registry.npm.taobao.org/ 
   ```

   使用淘宝镜像，不然下载速度非常慢

5. 设置环境变量

   ![image-20211013134927339](https://ooszy.cco.vin/img/blog-note/image-20211013134927339.png?x-oss-process=style/pictureProcess1)

   ![image-20211013134955551](https://ooszy.cco.vin/img/blog-note/image-20211013134955551.png?x-oss-process=style/pictureProcess1)

   ![image-20211013135021613](https://ooszy.cco.vin/img/blog-note/image-20211013135021613.png?x-oss-process=style/pictureProcess1)

   ![image-20211013135103286](https://ooszy.cco.vin/img/blog-note/image-20211013135103286.png?x-oss-process=style/pictureProcess1)![image-20211013135237437](https://ooszy.cco.vin/img/blog-note/image-20211013135237437.png?x-oss-process=style/pictureProcess1)

![image-20211013165742042](https://ooszy.cco.vin/img/blog-note/image-20211013165742042.png?x-oss-process=style/pictureProcess1)

![image-20211013135923646](https://ooszy.cco.vin/img/blog-note/image-20211013135923646.png?x-oss-process=style/pictureProcess1)



::: tip

这一步的时候，一定要点击用户变量

:::

![image-20211013165858204](https://ooszy.cco.vin/img/blog-note/image-20211013165858204.png?x-oss-process=style/pictureProcess1)

![image-20211013165921000](https://ooszy.cco.vin/img/blog-note/image-20211013165921000.png?x-oss-process=style/pictureProcess1)



![image-20211013140101867](https://ooszy.cco.vin/img/blog-note/image-20211013140101867.png?x-oss-process=style/pictureProcess1)





### 验证

现在验证一下，刚才安装的是否正确

打开cmd面板，运行下面命令

```sh
npm install express -g
```

![image-20211013170051105](https://ooszy.cco.vin/img/blog-note/image-20211013170051105.png?x-oss-process=style/pictureProcess1)

![image-20211013170201772](https://ooszy.cco.vin/img/blog-note/image-20211013170201772.png?x-oss-process=style/pictureProcess1)



::: tip

如果你觉得此篇文章的node安装不好理解，可以对照下面的文章进行安装



<a href="https://www.cnblogs.com/ldsweely/p/15041923.html" target="_blank">Node.js安装及环境配置之Windows篇 - ldsweely - 博客园 (cnblogs.com)</a>

:::





## 安装主题

### 初始化(以windows安装为例)

1. 随便创建一个文件夹`blog-demo`(这里已创建一个blog-demo文件夹为例)

2. 进入此`blog-demo`文件夹内，使用`npm init`命令初始化

    <img src="https://ooszy.cco.vin/img/blog-note/image-20211010210735931.png?x-oss-process=style/pictureProcess1" alt="image-20211010210735931" style="zoom:33%;" />![image-20211010210848054](https://ooszy.cco.vin/img/blog-note/image-20211010210848054.png?x-oss-process=style/pictureProcess1)

    在cmd窗口中，输入`npm init`命令

    

    ```sh
    npm init
    ```
    
    
    
    ![image-20211013172518789](https://ooszy.cco.vin/img/blog-note/image-20211013172518789.png?x-oss-process=style/pictureProcess1)
    
    这里会让你输入，直接按住回车键就可以跳过
    
    
    
3. 你将得到一个`package.json`文件，将下面代码加入到`package.json`对应位置

    ![image-20211013172624615](https://ooszy.cco.vin/img/blog-note/image-20211013172624615.png?x-oss-process=style/pictureProcess1)

    ![image-20211013172817416](https://ooszy.cco.vin/img/blog-note/image-20211013172817416.png?x-oss-process=style/pictureProcess1)

    ```json
    "scripts": {
        "test": "echo \"Error: no test specified\" && exit 1",
        "dev": "vuepress dev docs",
        "build": "vuepress build docs",
        "deploy": "bash deploy.sh"
      },
    ```

或者你也可以将下面代码全部复制替换`package.json`内的所有内容



```json
{
  "name": "blog-demo",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1",
    "dev": "vuepress dev docs",
    "build": "vuepress build docs",
  },
  "author": "",
  "license": "ISC"
}
```

最终此`package.json`文件的内容如下



![image-20211013173018983](https://ooszy.cco.vin/img/blog-note/image-20211013173018983.png?x-oss-process=style/pictureProcess1)





### 依赖及主题安装

在cmd面板中运行下面命令安装`vuepress`和`Aurora`主题

```shell
npm i vuepress@2.0.0-beta.25
npm i vuepress-theme-aurora
```



如果安装上面依赖和主题，遇到下面这个错误

![image-20211013173214619](https://ooszy.cco.vin/img/blog-note/image-20211013173214619.png?x-oss-process=style/pictureProcess1)

那么就是你`package.json`内容格式有误，请下载`notepad++`，然后再将下面的代码，替换`package.json`内的所有内容

```json
{
  "name": "blog-demo",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1",
    "dev": "vuepress dev docs",
    "build": "vuepress build docs",
  },
  "author": "",
  "license": "ISC"
}
```

然后再安装上面`vuepress`依赖和`aurora`主题

![image-20211013173817510](https://ooszy.cco.vin/img/blog-note/image-20211013173817510.png?x-oss-process=style/pictureProcess1)

### 使用主题

#### 1.新建readme.md文件

在`blog-demo`文件夹内，新建一个新文件夹`docs`，以及在`docs`内新建`.vuepress`文件夹

![image-20211013174000388](https://ooszy.cco.vin/img/blog-note/image-20211013174000388.png?x-oss-process=style/pictureProcess1)

![image-20211013174036575](https://ooszy.cco.vin/img/blog-note/image-20211013174036575.png?x-oss-process=style/pictureProcess1)

在`docs`文件夹下，新建`README.md`，在此`README.md`文件内添加下面内容



```yaml
---
home: true
---
```

![image-20211013174132851](https://ooszy.cco.vin/img/blog-note/image-20211013174132851.png?x-oss-process=style/pictureProcess1)

![image-20211013174205415](https://ooszy.cco.vin/img/blog-note/image-20211013174205415.png?x-oss-process=style/pictureProcess1)



#### 2.配置config.js

在`docs/.vuepress/`文件夹内，新建`config.js`

进入`docs/.vuepress/config.js`中，加入下面代码(`请全部复制完`)

```js
module.exports = {
    //设置head 一定要加入<script src="https://at.alicdn.com/t/font_2849934_v6y652peian.js"></script>项配置，否则一些图标不能正常显示
    head: [
        [
            "script",
            {
                src: "https://at.alicdn.com/t/font_2849934_v6y652peian.js",
            },
        ]
    ],
    onPrepared: async (app) => {
        const myData = app.pages.map((page) => {
            return page;
        });
        await app.writeTemp(
            "my-data.js",
            `export default ${JSON.stringify(myData)}`
        );
    },
    theme: 'vuepress-theme-aurora',
    themeConfig: {
        darkMode: false,
    }
};
```



![image-20211013174317058](https://ooszy.cco.vin/img/blog-note/image-20211013174317058.png?x-oss-process=style/pictureProcess1)

![image-20211013174402160](https://ooszy.cco.vin/img/blog-note/image-20211013174402160.png?x-oss-process=style/pictureProcess1)

#### 3.使用

在cmd中，进入创建的`blog-demo`文件夹内，运行`npm run dev`

待运行成功之后，你便能在面板中看到一个地址，在浏览器中输入此地址，便可看到下面界面

![image-20211013174555152](https://ooszy.cco.vin/img/blog-note/image-20211013174555152.png?x-oss-process=style/pictureProcess1)

<img src="https://ooszy.cco.vin/img/blog-note/image-20211010232842842.png?x-oss-process=style/pictureProcess1" alt="image-20211010232842842" style="zoom:50%;" />

![image-20211010232918219](https://ooszy.cco.vin/img/blog-note/image-20211010232918219.png?x-oss-process=style/pictureProcess1)



> 恭喜你，到这里，你已经安装成功了，接下来便可以尽情书写博客和修改配置(`上图展示出来的效果是默认配置，你可以更改`)
>
> 主题配置修改，请参考此<a href="https://github.com/qsyyke/vuepress-theme-aurora/blob/master/docs/.vuepress/config-fu.js">config.js</a>文件





如果你在使用的过程中，遇到其他的问题，那么你可以在此页面下留言，我都会看到，或者通过首页，或者侧边栏的qq联系我