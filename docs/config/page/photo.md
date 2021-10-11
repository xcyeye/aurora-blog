---
categories: [相册,photo]
---



# 相册功能

::: tip

目前版本存在一个bug

在本地访问相册没有问题，但是部署到github之后，刷新会出现问题，后续版本解决

:::

- 图片欣赏

    此图片动画，使用的是大佬开发的<a href="https://github.com/ymback/Vue-Fucking-Gallery">Vue-Fucking-Gallery</a>，主题并没有提供配置修改，如果需要，我可以修改

    ![image-20210911121202901](https://ooszy.cco.vin/img/blog-note/image-20210911121202901.png?x-oss-process=style/pictureProcess1)

- 自己图片

    ![image-20210911121146092](https://ooszy.cco.vin/img/blog-note/image-20210911121146092.png?x-oss-process=style/pictureProcess1)



## 配置

> 配置主要是配置自己的图片
>
> 此页面手机端默认是两列，PC端为5列，主题会自动计算宽度

主题会寻找在`docs/photos/readme.md`中的所有图片，在此页面中展示，所以一定要在`docs/photos/readme.md`中，加入自己的图片，可以是本地，或者图片url连接，如果不存在此文件，那么会使用默认的图片进行展示

```md
//docs/photos/reasdme.md
![image-20210910160320304](https://ooszy.cco.vin/img/blog-note/image-20210910160320304.png)

![image-20210910160335602](https://ooszy.cco.vin/img/blog-note/image-20210910160335602.png)
```



