---
categories: [issue,导航,问题]
---



# 	Aurora主题导航

## 导航

主题介绍及安装请移步到<a href="https://aurora.cco.vin/readme">this</a>



此页面是主题文档导航

[首页](https://aurora.cco.vin/)  
[安装](https://aurora.cco.vin/readme)  
[主题特征配置](https://aurora.cco.vin/config/feature/)  
[主题所有页面配置](https://aurora.cco.vin/config/page/)  
[其他配置](https://aurora.cco.vin/config/other/) 
[Github](https://github.com/qsyyke/vuepress-theme-aurora)

使用过程中，遇到问题，可以在此页面留言或者在Issue，QQ，微信联系我
[问题](https://aurora.cco.vin/about)
[Issue](https://github.com/qsyyke/vuepress-theme-aurora/issues)


## 问题及报错

### 报错



> 如果你安装完依赖，直接设置主题为ccds，如果启动报下面错误
>
> ```js
> TypeError: Cannot destructure property 'createComponentInstance' of 'vue.ssrUtils' as it is undefined
> ```
>
> 请尝试将`docs/.vuepress/config.js`中的内容注释，`package.json`里面的依赖项，只留下`vuepress`，然后删除`node_modules`文件夹，重新运行`npm install`，结束后，再运行命令`npm run dev`，先使用默认运行成功，再切换为Aurora主题，就可以解决，我也不知道为啥会出现这种情况
