# 心情页面

## 顶部图片

自行修改





## 配置

![image-20210901195633933](http://ooszy.cco.vin/img/blog-note/image-20210901195633933.png?x-oss-process=style/pictureProcess1)



所有的心情页面文件都是放在`docs/moods`目录下，文件格式为`.md`，主题会自动获取此`moods`目录下的所有文件内容，更改此目录中的文件时，需要重新启动，否则不会生效



### **title**

![image-20210901195958478](http://ooszy.cco.vin/img/blog-note/image-20210901195958478.png?x-oss-process=style/pictureProcess1)

标题会自动将md文件中的h1标签作为标题，如果需要使用其他的内容，请自己修改代码，位置`docs/.vuepress/theme/lib/client/components/child/MoodItem.vue`



### **content**

![image-20210901200135053](http://ooszy.cco.vin/img/blog-note/image-20210901200135053.png?x-oss-process=style/pictureProcess1)

内容会自动获取所有p标签中的内容，不推荐在`moods`目录下的md文件中，写太多内容



### **图片**

![image-20210901200242152](http://ooszy.cco.vin/img/blog-note/image-20210901200242152.png?x-oss-process=style/pictureProcess1)

图片会自动获取md文件中，所有img标签的src值



### 头像

头像默认使用的是首页中的头像地址，修改logo需要在`docs/readme.md`文件中

```yaml
heroLogo: https://ooszy.cco.vin/img/blog-public/avatar.jpg
```





