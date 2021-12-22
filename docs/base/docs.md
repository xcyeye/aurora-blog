---
date: "2021/12/8 16:37"
title: docs目录详解
---



目录结构

```sh
docs-
    │  style.md #文章
    │  
    ├─.vuepress # 必须
    │  │  clientAppEnhance.js #根据自己的需要，扩展文件
    │  │  config.js #配置文件
    │  │  
    │  │      
    │  ├─dist # build命令后，静态文件目录
    │  ├─public # 静态文件目录
    │  │  │  avatar.jpg
    │  │  │  
    │  │  ├─bg
    │  │  │  │  1.jpg
    │  │  │  │  
    │  │  │  └─animate
    │  │  │          1.jpg
    │  │  │          
    │  │  └─song
    │  │          2.mp3
    │  │          
    │  └─styles # 覆盖主题默认样式
    │          index.css # 样式
    │          palette.css
    │          
    └─comment # 文章
            README.md # 文章
```



## 文章

![image-20211208165052341](https://ooszy.cco.vin/img/blog-note/image-20211208165052341.png?x-oss-process=style/pictureProcess1)



::: tip

在`docs`目录中，除了`docs/.vuepress`文件夹外的文件及文件夹，都是博客文章，`docs/.vuepress`目录不能删除，里面存放临时文件，build命令生成的文件，以及静态文件，主题配置文件

:::



例如上面的`style.md`和`comment/readme.md`文件都是文章，都可以直接通过`http://localhost:8080/style.html`和`http://localhost:8080/comment/index.html`进行访问(8080端口不一定和我的一样)

> `readme.md`文件，相当于`index.html`



## 主题配置文件

vuepress中，主题配置文件存放在`docs/.vuepress/config.js`内，一定不要搞错了

> `docs/.vuepress/config.js`是一个文件名么??????????



![image-20211208165730551](https://ooszy.cco.vin/img/blog-note/image-20211208165730551.png?x-oss-process=style/pictureProcess1)

