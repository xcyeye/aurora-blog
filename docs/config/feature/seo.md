# SEO

主题已对搜索引擎进行初步优化，主要就是自动设置关键词和描述，如果想要更深入的SEO优化，请自行解决



## 首页

首页的`description`和`keyword`通过以下修改，在`readme.md`文件中，不推荐在`config.js`中进行修改

```yaml
description: qsyyke的个人博客，记录生活琐事，学习笔记
keyword: vuepress主题,vuepress theme,生活琐事,二次元博客,简约博客,博客主题,静态主题
```







## 文章页面

文章页默认使用





### 默认获取

```html
<meta name="description" content="1、服务器：	2、web服务器	3、常见的web服务器	4、Tomcat服务器的目录结构	5、WebApps中打开自己的项目	6、tomcat项目的部署方式	7、配置tomcat	8、新建module	9、项目结构	10、解决方法	11、宝塔添加反向代理	12、推荐阅读	">
<meta name="keyword" content="服务器：,web服务器,常见的web服务器,Tomcat服务器的目录结构,Web">
```



#### description

默认情况下，description是获取文章的h2标签，如果h2标签个数小于2时，就会获取全文文本，截取80个字符作为description值



#### keyword

默认情况下，keyword会截取description的值作为keyword，可以在`readme.md`中设置截取多少个字符作为keyword值

```yaml
#如果没有设置key值，那么就截取描述作为key，keyRule就是截取多少个字
keyRule: 40
```



### 自定义

#### description

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


#### keyword

如需自定义，请在该`.md`文件中，推荐在h1标签下，加上下面容器，会自动获取第一个类名为`key`的文本质值，作为keyword

```html
<div class="key">
keyword,test,java
</div>
```





最终为效果为

![image-20210831214727190](http://ooszy.cco.vin/img/blog-note/image-20210831214727190.png?x-oss-process=style/pictureProcess1)