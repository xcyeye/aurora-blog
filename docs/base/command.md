---
date: 2021/12/10 20:51
---

# 博客中常见命令及注意事项



## npm run dev

此命令是在本地启动博客，运行此命令之后，会自动为博客绑定一个端口，然后你就可以在浏览器中输入这个网址进行访问

```sh
http://localhost:[post]
```

![image-20211210223203670](https://ooszy.cco.vin/img/blog-note/image-20211210223203670.png?x-oss-process=style/pictureProcess1)

![image-20211210223343459](https://ooszy.cco.vin/img/blog-note/image-20211210223343459.png?x-oss-process=style/pictureProcess1)



::: warning

如果你运行`npm run dev`，但是打开`http://localhost:8080/`，却不能访问，你可以先按住`Ctrl`键和`C`键退出，然后重新运行`npm run dev`试试

网址的输入位置也别弄错了，这个是Google下的视图

![image-20211210223619339](https://ooszy.cco.vin/img/blog-note/image-20211210223619339.png?x-oss-process=style/pictureProcess1)

:::



## npm run build

此命令会将博客程序，自动生成html文件，此html和css和js等静态文件的存储位置为`docs/.vuepress/dist`

![image-20211210224142914](https://ooszy.cco.vin/img/blog-note/image-20211210224142914.png?x-oss-process=style/pictureProcess1)

![image-20211210224213071](https://ooszy.cco.vin/img/blog-note/image-20211210224213071.png?x-oss-process=style/pictureProcess1)





## npm install

node_modules

此命令，就是安装依赖的，比如当

![image-20211210224420012](https://ooszy.cco.vin/img/blog-note/image-20211210224420012.png?x-oss-process=style/pictureProcess1)、

你如果现在运行`npm run dev`或者`npm run build`命令，那么会出现下面错误

![image-20211210224527152](https://ooszy.cco.vin/img/blog-note/image-20211210224527152.png?x-oss-process=style/pictureProcess1)

这是因为你还没有安装博客所需要的依赖，所以你需要运行`npm install`命令，此命令会自动下载`package.json`文件中，所有的依赖

![image-20211210224713144](https://ooszy.cco.vin/img/blog-note/image-20211210224713144.png?x-oss-process=style/pictureProcess1)

![image-20211210224845840](https://ooszy.cco.vin/img/blog-note/image-20211210224845840.png?x-oss-process=style/pictureProcess1)

::: tip

只有当所需要的已依赖，都安装完成之后，你才可以使用`npm run dev`和`npm run build`等命令

:::



