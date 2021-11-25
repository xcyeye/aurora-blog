---
tag: [海报,poster]
---



# 海报分享配置

![image-20210911105738192](https://ooszy.cco.vin/img/blog-note/image-20210911105738192.png?x-oss-process=style/pictureProcess1)

![image-20210911105852609](https://ooszy.cco.vin/img/blog-note/image-20210911105852609.png?x-oss-process=style/pictureProcess1)

> 主题默认在文章页面和心情页面加入了海报分享功能，你也可以在你想要的地方使用该组件，以注册为全局组件`<Poster>`





## 配置

- 作者头像

    考虑到该海报分享，大家可能想要自定义，所以默认并没有使用站点的logo，如果没有在`docs/readme.md`中进行配置，并且请确保此头像连接允许跨域请求，如果出现跨域，在进行生成图片的时候，就会出问题，推荐将此图片放置在`docs/.vuepress/public`文件夹中，没有默认值

    如果你运行出现下面错误，那很有可能就是你设置的头像连接不支持跨域

    ```js
    Access to image at '...' from origin 'http://localhost:8080' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
    ```

    

- 作者

    默认值`qsyyke`

- 博客描述

    ![image-20210911115155380](https://ooszy.cco.vin/img/blog-note/image-20210911115155380.png?x-oss-process=style/pictureProcess1)

    此项需要配置两个值，一个前缀，一个后缀

- 博客描述

    ![image-20210911115301740](https://ooszy.cco.vin/img/blog-note/image-20210911115301740.png?x-oss-process=style/pictureProcess1)
    
- 海报顶部背景

    默认使用的是bing首页的图片，暂时不支持自定义



```yaml
poster: {
	#博客描述
    description: 初尘博客呀,
    #作者
    author: 'qsyyke',
    #博客前缀
    preBlog: 'chuchen',
    #博客后缀
    suffixBlog: 'blog',
    #头像
    avatar: /avatar.jpg
}
```



## 组件

如果使用`Poster`组件，需要传入一些值

| 属性    | 类型   | 描述         |
| ------- | ------ | ------------ |
| title   | String | 海报标题     |
| content | String | 海报分享内容 |



## 文章推荐配置

[文章推荐配置](./recommend.md)