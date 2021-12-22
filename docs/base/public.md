---
data: "2021/12/8 16:02"
sticky: false
title: 什么是静态文件以及如何使用本地图片
---

::: tip

在vuepress中，你可以在博客中，使用你本地的图片，或者是其他的静态文件，如字体，css，视频等等，但是必须要将这些静态文件放入到`docs/.vuepress/public`文件夹中

:::



加入现在在该`public`目录下，存在如下文件及文件夹，目前博客启动的链接为`http://localhost:8080/`(你的不一定跟我一样)

![image-20211208161259246](https://ooszy.cco.vin/img/blog-note/image-20211208161259246.png?x-oss-process=style/pictureProcess1)

![image-20211208161655614](https://ooszy.cco.vin/img/blog-note/image-20211208161655614.png?x-oss-process=style/pictureProcess1)



那么这些静态文件，可以通过下面这些方式访问

- `public/avatar.jpg` http://localhost:8080/avatar.jpg
- `public/bg/j.jpg` http://localhost:8080/bg/1.jpg
- `public/bg/animate/2.jpg` http://localhost:8080/bg/animate/2.jpg



::: tip

上面的`public/avatar.jpg`是什么？

>  这是`public`目录下的`avatar.jpg`文件

:::



::: warning 

请注意，`public`目录是静态文件目录，加入该目录下有`avatar.jpg`文件，访问的时候，直接`/avatar.jpg`，不需要像这样`/public/avatar.jpg`(没有任何用),如果该`avatar.jpg`文件，在`public/avatar/`目录下，那么访问路径就是`/avatar/avatar.jpg`

:::



## 背景图片使用本地

如果对如何访问本地静态文件，还是不清楚，请自己在`docs/.vuepress/public`目录下，放几张图片，按照上面的方式自己访问，自己测试一下，容易理解

![image-20211208163212971](https://ooszy.cco.vin/img/blog-note/image-20211208163212971.png?x-oss-process=style/pictureProcess1)



::: tip

因为`public/bg/3.jpg`文件是在本地，所以我们在配置背景图片的时候，可以直接`/bg/3.jpg`就可以访问到了，该图片因为可以直接在浏览器中输入`http://localhost:8080/bg/3.jpg`(端口不一定是8080)进行访问，所以你也可以直接在背景图片那里输入`http://localhost:8080/bg/3.jpg`，但是不推荐，当你部署之后，此`http://localhost:8080/bg/3.jpg`并不能打开`3.jpg`图片，因为`localhost`只能在本地打开

:::





