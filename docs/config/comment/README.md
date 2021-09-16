---
categories: [评论配置]
---



# 评论

::: tip 

ccds主题加入了评论，使用<a href="https://valine.js.org/">Valine</a>集成评论，Valine是一款基于[LeanCloud](https://leancloud.cn/)的快速、简洁且高效的无后端评论系统。可以很快为博客加入评论功能

:::



## 获取APP ID 和 APP Key

在使用之前，请先获取APP ID 和 APP Key，[登录](https://leancloud.cn/dashboard/login.html#/signin)或[注册](https://leancloud.cn/dashboard/login.html#/signup) `LeanCloud`, 进入[控制台](https://leancloud.cn/dashboard/applist.html#/apps)后点击左下角[创建应用](https://leancloud.cn/dashboard/applist.html#/newapp)：

![image-20210904163716097](https://ooszy.cco.vin/img/blog-note/image-20210904163716097.png?x-oss-process=style/pictureProcess1)

应用创建好以后，进入刚刚创建的应用，选择左下角的`设置`>`应用Key`，然后就能看到你的`APP ID`和`APP Key`了：

![image-20210904163741371](https://ooszy.cco.vin/img/blog-note/image-20210904163741371.png?x-oss-process=style/pictureProcess1)



## 配置

在配置文件`docs/readme.md`文件中，进行配置

```yaml
comment: {
  #占位符
  placeholder: 在这里留下你的脚印吧...,
  appId: 上一步申请的appid,
  appKey: 上一步申请的appKey,
  #头像显示
  avatar: robohash,
  # 每页评论数量
  pageSize: 10,
  #是否开启访问量
  visitor: true,
  # 是否记录评论者ip
  recordIP: true,
  # 是否自动获取用户qq头像，需根据qq号码
  adminUsername: qsyyke,
  #是否显示评论，默认显示，此
  showComment: true 
}
```

关于评论的详细配置项，请看Valine<a href="https://valine.js.org/configuration.html#appId">文档</a>，目前主题集成上述项配置，如需Valine其他的配置项，请自行克隆<a href="https://github.com/qsyyke/vuepress-theme-ccds">主题源码</a>进行更改，评论组件位置`docs/.vuepress/theme/lib/client/components/global/Comment.vue`,所有的配置都是在`docs/readme.md`中进行配置

> 所有评论配置项，必须放在`comment`对象中



## 组件使用

`Comment`是一个全局组件，目前默认只在关于页面，文章页面以及友情链接页面进行使用，如果在其他页面使用，可以直接`<Comment/>`

