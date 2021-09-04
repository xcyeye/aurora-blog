# Feature

[[toc]]



## SEO

主题已对搜索引擎进行初步优化，主要就是自动设置关键词和描述，如果想要更深入的SEO优化，请自行解决



### 首页

首页的`description`和`keyword`通过以下修改，在`readme.md`文件中，不推荐在`config.js`中进行修改

```yaml
description: qsyyke的个人博客，记录生活琐事，学习笔记
keyword: vuepress主题,vuepress theme,生活琐事,二次元博客,简约博客,博客主题,静态主题
```







### 文章页面

文章页默认使用





#### 默认获取

```html
<meta name="description" content="1、服务器：	2、web服务器	3、常见的web服务器	4、Tomcat服务器的目录结构	5、WebApps中打开自己的项目	6、tomcat项目的部署方式	7、配置tomcat	8、新建module	9、项目结构	10、解决方法	11、宝塔添加反向代理	12、推荐阅读	">
<meta name="keyword" content="服务器：,web服务器,常见的web服务器,Tomcat服务器的目录结构,Web">
```



##### description

默认情况下，description是获取文章的h2标签，如果h2标签个数小于2时，就会获取全文文本，截取80个字符作为description值



##### keyword

默认情况下，keyword会截取description的值作为keyword，可以在`readme.md`中设置截取多少个字符作为keyword值

```yaml
#如果没有设置key值，那么就截取描述作为key，keyRule就是截取多少个字
keyRule: 40
```



#### 自定义

##### description

如需自定义，请在该`.md`文件中，推荐在h1标签下，加上下面容器，会自动获取第一个tip的值，作为description

```markdown
::: tip description
这是一个提示
:::
```

此被解析为

::: tip description
这是一个提示
:::


##### keyword

如需自定义，请在该`.md`文件中，推荐在h1标签下，加上下面容器，会自动获取第一个类名为`key`的文本质值，作为keyword

```html
<div class="key">
keyword,test,java
</div>
```





最终为效果为

![image-20210831214727190](http://ooszy.cco.vin/img/blog-note/image-20210831214727190.png?x-oss-process=style/pictureProcess1)





## 自定义面板

![image-20210901135152962](http://ooszy.cco.vin/img/blog-note/image-20210901135152962.png?x-oss-process=style/pictureProcess1)





### 功能介绍

此自定义面板的功能有：

1. 切换背景图片
2. 开启毛玻璃模糊效果
3. 全局颜色控制
4. 全局字体控制
5. 全局圆角控制
6. 全局模糊度控制
7. 全局透明度控制



#### 切换背景

![image-20210901135603882](http://ooszy.cco.vin/img/blog-note/image-20210901135603882.png?x-oss-process=style/pictureProcess1)



#### 毛玻璃

- 开启(默认是开启的)

    ![image-20210901135749484](http://ooszy.cco.vin/img/blog-note/image-20210901135749484.png?x-oss-process=style/pictureProcess1)

- 关闭

    ![image-20210901135846975](http://ooszy.cco.vin/img/blog-note/image-20210901135846975.png?x-oss-process=style/pictureProcess1)



为了获得更佳的体验效果，开启毛玻璃效果之后，请设置透明度，模糊度(`模糊度仅在毛玻璃效果开启之后，才生效`)，以达到最佳视觉效果



#### 全局颜色

![image-20210901140141856](http://ooszy.cco.vin/img/blog-note/image-20210901140141856.png?x-oss-process=style/pictureProcess1)

![image-20210901140158487](http://ooszy.cco.vin/img/blog-note/image-20210901140158487.png?x-oss-process=style/pictureProcess1)



#### 全局字体

![image-20210901140240329](http://ooszy.cco.vin/img/blog-note/image-20210901140240329.png?x-oss-process=style/pictureProcess1)

![image-20210901140257315](http://ooszy.cco.vin/img/blog-note/image-20210901140257315.png?x-oss-process=style/pictureProcess1)

#### 设置显示文字

![image-20210902181737898](http://ooszy.cco.vin/img/blog-note/image-20210902181737898.png?x-oss-process=style/pictureProcess1)

```yaml
showFont: 程
```

#### 全局圆角

![image-20210901140433200](http://ooszy.cco.vin/img/blog-note/image-20210901140433200.png?x-oss-process=style/pictureProcess1)



![image-20210901140509942](http://ooszy.cco.vin/img/blog-note/image-20210901140509942.png?x-oss-process=style/pictureProcess1)



圆角控制，对绝大多数的边框都进行控制，但像文章内图片，代码块等并没有进行控制，如需加上，请自行解决



#### 模糊度

`模糊度仅在毛玻璃效果开启之后有效`

- 模糊度为0时

![image-20210901140748473](http://ooszy.cco.vin/img/blog-note/image-20210901140748473.png?x-oss-process=style/pictureProcess1)



- 变化之后

![image-20210901140842940](http://ooszy.cco.vin/img/blog-note/image-20210901140842940.png?x-oss-process=style/pictureProcess1)





#### 透明度

- 0时

![image-20210901141006270](http://ooszy.cco.vin/img/blog-note/image-20210901141006270.png?x-oss-process=style/pictureProcess1)



- 变化后

![image-20210901141047340](http://ooszy.cco.vin/img/blog-note/image-20210901141047340.png?x-oss-process=style/pictureProcess1)







### 配置修改

#### 设置背景切换，开启毛玻璃背景图片

![image-20210901141208901](http://ooszy.cco.vin/img/blog-note/image-20210901141208901.png?x-oss-process=style/pictureProcess1)

```yaml
homeWelcome: [
  http://ooszy.cco.vin/img/ico/love2.svg,
  http://ooszy.cco.vin/img/ico/xy.svg,
]
```

`homeWelcome`是一个数组，请按顺序传入两张图片，因为通过设置背景图片进行显示，可以查看`docs/.vuepress/theme/lib/client/components/child/HomeWelcome.vue`



#### 背景图片切换接口

对于PC端和手机端，主题使用不同的背景图片接口

```yaml
#手机端
randomWallpaperMb: https://api.iro.tw/webp_mb.php

#PC端
randomWallpaper: https://api.iro.tw/webp_pc.php
```





#### 字体颜色，字体

```yaml
# 最大显示颜色和字体数
maxFontColorArr: 7

#字体颜色
fontColor: [
'#2c3e50','#42a5f5','#8093f1','#FF6EC7','#FF7F00',
'#8FBC8F','#EAADEA','#3299CC','#CDCDCD','#CC3299',
'#FF7F00','#2F4F4F']

#字体
fontFamily: [
  "hlt",
  "tzt",
  "sst",
  "lf",
  "xsf",
  "lsf",
  "cgt"
]
```

`maxFontColorArr`是控制颜色和字体显示多少个，字体和颜色排列个数都会受此值影响，不推荐添加多个，控制在8个或者7个以内

![image-20210901144515893](http://ooszy.cco.vin/img/blog-note/image-20210901144515893.png?x-oss-process=style/pictureProcess1)

#### 默认开启毛玻璃效果

```yaml
#毛玻璃效果默认值，true表示开启，false表示关闭，只是默认值
isFitter: false
```



#### 滚轮

![image-20210901144745455](http://ooszy.cco.vin/img/blog-note/image-20210901144745455.png?x-oss-process=style/pictureProcess1)

滚轮样式请自行修改，组件位置`docs/.vuepress/theme/lib/client/components/child/HomeWelcome.vue`，对于其他浏览器，滚轮样式可能不美观，请自行解决



#### 取消图片

![image-20210901144948962](http://ooszy.cco.vin/img/blog-note/image-20210901144948962.png?x-oss-process=style/pictureProcess1)



```yaml
cancelIcoHref: https://ooszy.cco.vin/img/ico/quxiao.svg
```

传入图片连接





### 自定义



