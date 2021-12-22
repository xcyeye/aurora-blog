---
date: 2021/12/1
title: 快速开始
tag: [快速开始,博客搭建]
categories: [教程]
---

- [动漫Demo](https://aurora-animate.xcye.xyz/)
- [非动漫Demo](https://aurora-common.xcye.xyz/)
- [文档](https://aurora.xcye.xyz/)


## Introduce

![](https://img.shields.io/npm/v/vuepress-theme-aurora)![npm](https://img.shields.io/npm/dm/vuepress-theme-aurora)![](https://img.shields.io/github/stars/qsyyke/vuepress-theme-aurora?style=social)

![image-20211208135404881](https://ooszy.cco.vin/img/blog-note/image-20211208135404881.png?x-oss-process=style/pictureProcess1)

---

[Aurora](https://www.npmjs.com/package/vuepress-theme-aurora)是一款基于**Vuepress2**的博客主题，将本地**Markdown**文件解析成静态html页面，作为博客文章。搭配**说说**，**时间轴**，**文章分类**，**评论**，**友情链接**，**相册**，**音乐播放器**等特色功能，给您不一样的使用体验。

- 🏆**100%自定义**
- 🌈**简洁，漂亮**
- 🎨**高扩展**
- 💫**多功能**
- 📖**配置文档**


## Quick start

### 安装CLI

> `管理员状态下运行cmd`
>
> 如何进入管理员状态下的`CMD`,[点这](./cmd.md)

- npm

  ```sh
  npm i vuepress-theme-cli -g
  ```

- yarn

  ```sh
  yarn global vuepress-theme-cli
  ```

- [视频教程](https://ooszy.cco.vin/theme-template/%E6%9C%80%E7%BB%88%E7%89%88%E6%9C%AC.mp4)

### 创建博客

待脚手架cli安装成功之后，进入cmd中

> 这里推荐最好在管理员状态下，[如何进入?](./cmd.md)

![image-20211127104249135](https://ooszy.cco.vin/img/blog-note/image-20211127104249135.png?x-oss-process=style/pictureProcess1)



在命令行处输入下面命令(`之后一直按Enter键`)

```sh
aurora blog-demo
```

> `blog-demo`是你的博客文件件名称，会自动创建以此名字命名的文件夹
>
> 当你输入`aurora blog-demo`命令之后，会需要你填写一下`description`和`logoText(首页logo文字)`，你可以随便填一下



::: tip

如果提示,请看[这里](https://aurora.xcye.xyz/issue/cli-issue.md)

```sh
'aurora' 不是内部或外部命令，也不是可运行的程序
或批处理文件。
```

:::

![image-20211130123037624](https://ooszy.cco.vin/img/blog-note/image-20211130123037624.png?x-oss-process=style/pictureProcess1)

> 这个过程大概几秒，取决于你的网速，待安装成功之后，你会发现在当前目录下，多了一个目录

![image-20211210202945519](https://ooszy.cco.vin/img/blog-note/image-20211210202945519.png?x-oss-process=style/pictureProcess1)



::: warning

不知道你有没有注意到上图红色框中的信息，当博客模板下载完成之后，你还需要安装依赖，请根据你自己的环境，选择使用`npm`还是`yarn`

:::



::: details 运行npm install报错?

如果你运行`npm install`命令报错，就像下面这种情况

![image-20211211083929879](https://ooszy.cco.vin/img/blog-note/image-20211211083929879.png?x-oss-process=style/pictureProcess1)



使用下面这条命令，便可以解决，感谢[ranyong](https://github.com/ranyong1997/)

```sh
npm install --legacy-peer-deps
```

![image-20211211084030624](https://ooszy.cco.vin/img/blog-note/image-20211211084030624.png?x-oss-process=style/pictureProcess1)

:::




<video controls="" class="aurora-video" autoplay="" name="media"><source src="https://ooszy.cco.vin/theme-template/%E6%9C%80%E7%BB%88%E7%89%88%E6%9C%AC.mp4" type="video/mp4"></video>



### 启动

> 运行成功之后，会出现一个地址，你只需要在浏览器中，输入这个地址，就可以看到下面的页面

![image-20211010232918219](https://ooszy.cco.vin/img/blog-note/image-20211010232918219.png?x-oss-process=style/pictureProcess1)



::: tip

恭喜你，到这里，你已经安装成功了，接下来便可以尽情书写博客和修改配置(`上图展示出来的效果是默认配置，你可以更改`)

为了更好的对主题进行配置，你可以直接复制我GitHub中的<a href="https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/docs/.vuepress/config-copy.js" target="_blank">config.js</a>文件

配置项

- [主题config.js配置文件](https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/docs/.vuepress/config.js)
- [主题所有配置速查](https://aurora.xcye.xyz/home/config.md)
- [首页配置](https://aurora.xcye.xyz/homeconfig.html)

---

报错区

- [进入管理员状态下的CMD](https://aurora.xcye.xyz/readme/cmd.html)
- [切换到管理员状态下的CMD](https://aurora.xcye.xyz/base/admin.html)
- [主题常用命令](https://aurora.xcye.xyz/base/command.html#npm-install)
- [运行报错解决](https://aurora.xcye.xyz/issue/common.html)

---

必读区

- [一定一定要读](https://aurora.xcye.xyz/issue/)

---

Vuepress基本知识

- [关于使用本地静态文件(图片,mp3等)](https://aurora.xcye.xyz/base/public.html)
- [Vuepress插件使用](https://aurora.xcye.xyz/base/plugin.html)
- [博客目录结构](https://aurora.xcye.xyz/base/docs.html)
- [主题最近更新,在这里看看每个新版本如何配置](https://aurora.xcye.xyz/issue/bug.html)

---

部署区

- [博客如何部署?](https://aurora.xcye.xyz/home/deploy.html)

:::

## 联系我

如果你在使用该主题的过程中，有任何的问题，都可以通过以下方式联系我

| QQ                                                           | 主题交流群                                                   | 微信                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233620332.png?x-oss-process=style/pictureProcess1" style="zoom:33%;" /> | <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233827133.png?x-oss-process=style/pictureProcess1" alt="image-20211024233827133" style="zoom:33%;" /> | <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233735110.png?x-oss-process=style/pictureProcess1" alt="image-20211024233735110" style="zoom: 39%;" /> |

## 首页配置

[首页](../homeconfig.md)