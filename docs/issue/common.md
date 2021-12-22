---
date: 2021/12/10 22:27
---

# 运行报错整理

## 因为权限问题操作被拒绝

如果你运行，出现下面这种错误

![image-20211210214629801](https://ooszy.cco.vin/img/blog-note/image-20211210214629801.png?x-oss-process=style/pictureProcess1)



::: warning 

出现这个问题的原因是因为，你当前并没有使用管理员方式进入`CMD`中，所以就出现操作被拒，解决的方式为

请看[这里](../base/admin.md)

:::



## 未在package.json同级目录下，运行npm命令

![image-20211210205447370](https://ooszy.cco.vin/img/blog-note/image-20211210205447370.png?x-oss-process=style/pictureProcess1)



::: warning 

解决方法，就是在`package.json`同级目录下，运行npm命令

![image-20211210221942585](https://ooszy.cco.vin/img/blog-note/image-20211210221942585.png?x-oss-process=style/pictureProcess1)

:::



## 'vuepress' 不是内部或外部命令，也不是可运行的程序

当你运行`npm run dev`或者`npm run build`出现下面错误的时候

![image-20211210225318731](https://ooszy.cco.vin/img/blog-note/image-20211210225318731.png?x-oss-process=style/pictureProcess1)

> 这是因为你没有安装相关的依赖导致的，解决方法请看[这里](../base/command.md)

![image-20211210225429250](https://ooszy.cco.vin/img/blog-note/image-20211210225429250.png?x-oss-process=style/pictureProcess1)