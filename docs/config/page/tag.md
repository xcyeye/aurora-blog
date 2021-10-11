# 归档



## 顶部图片





## 标签

![image-20210831201338288](http://ooszy.cco.vin/img/blog-note/image-20210831201338288.png?x-oss-process=style/pictureProcess1)

通过在每篇md文件上，添加`frontMatter`进行设置文章标签分类，格式为

```markdown
---
categories: [vue,前端,分类]

#or

categories: 
  - vue
  - 前端
  - 分类
---
```

`categories`是一个数组，设置之后，请重新启动，否则不会生效





## 类别

![image-20210831201350855](http://ooszy.cco.vin/img/blog-note/image-20210831201350855.png?x-oss-process=style/pictureProcess1)

类别是通过对文章path路径，进行分割选取的，如果一个path路径为

```
/spring/springmvc/springmvc重定向和请求转发操作.html#请求转发
```

那么此类别就有`spring,springmvc`



### 排除某个类别

你也可以配置`excludeTag`值，就不会对含有该值的path进行类别选取，数组类型

```js
excludeTag: ['note']
```



如果需要自定义类别选取，可以查看`docs/.vuepress/theme/lib/client/public/js/tag.js`文件，自行设置



## 分割符

![image-20210831202338204](http://ooszy.cco.vin/img/blog-note/image-20210831202338204.png?x-oss-process=style/pictureProcess1)



此分割符可以通过`split`项进行设置

```js
split: "~"
```



