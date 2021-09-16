---
categories: [issue,导航,问题]
---



# 	ccds主题导航

## 导航

主题介绍及安装请移步到<a href="https://theme-ccds.cco.vin/readme">this</a>



此页面是主题文档导航

[首页](https://theme-ccds.cco.vin/)  
[安装](https://theme-ccds.cco.vin/readme)  
[主题特征配置](https://theme-ccds.cco.vin/config/feature/)  
[主题所有页面配置](https://theme-ccds.cco.vin/config/page/)  
[其他配置](https://theme-ccds.cco.vin/config/other/) 
[Github](https://github.com/qsyyke/vuepress-theme-ccds)

使用过程中，遇到问题，可以在此页面留言或者在Issue，QQ，微信联系我
[问题](https://theme-ccds.cco.vin/about)
[Issue](https://github.com/qsyyke/vuepress-theme-ccds/issues)


## 问题及报错



### 问题

如果你在`docs/.vuepress/config.js`中，已将主题切换为`ccds`，但是首页并没有像此站点一样，请将<a href="https://github.com/qsyyke/vuepress-theme-ccds/blob/master/docs/README.md">readme.md</a>中的内容，完整复制到`docs/readme.md`文件夹中，粘贴时，请注意格式不要错误，使用的是`yaml`语法



### 报错



> 如果你安装完依赖，直接设置主题为ccds，如果启动报下面错误
>
> ```js
> TypeError: Cannot destructure property 'createComponentInstance' of 'vue.ssrUtils' as it is undefined
> ```
>
> 请尝试将`docs/.vuepress/config.js`中的内容注释，`package.json`里面的依赖项，只留下`vuepress`，然后删除`node_modules`文件夹，重新运行`npm install`，结束后，再运行命令`npm run dev`，先使用默认运行成功，再切换为ccds主题，就可以解决，我也不知道为啥会出现这种情况





## 导航

[首页](../README.md)  
[配置参考](../reference/config.md)  
[快速上手](./getting-started.md)  
<!-- 绝对路径 -->
[指南](/zh/guide/README.md)  
[配置参考 > markdown.links](/zh/reference/config.md#links)  
<!-- URL -->
[GitHub](https://github.com) 