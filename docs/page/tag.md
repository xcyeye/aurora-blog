# 归档

## 标签

![image-20210831201338288](http://ooszy.cco.vin/img/blog-note/image-20210831201338288.png?x-oss-process=style/pictureProcess1)

通过在每篇md文件上，添加`frontMatter`进行设置文章标签和类别，格式为

```markdown
---
categories: [vue,前端,分类] # 设置类别
tag: [tag1,tag2,tag3] #设置标签
---
```

`categories,tag`是一个数组，设置之后，请重新启动，否则不会生效



## 类别

![image-20210831201350855](http://ooszy.cco.vin/img/blog-note/image-20210831201350855.png?x-oss-process=style/pictureProcess1)

类别是通过对文章path路径，进行分割选取的，如果一个path路径为

```
/spring/springmvc/springmvc重定向和请求转发操作.html#请求转发
```

那么此篇文章的类别就有`spring,springmvc`，同时，你也可以禁用将此篇文章的文件夹包含在类别中，禁用下面这个配置，便可以了

```js
//config.js
//类别项是否包含文件夹名，默认包含
categoriesIncludeFolderName: true,
```

### 排除某个类别

你也可以配置`excludePath`值，那个在excludePath数组中的路径的文件夹，就不会被包含在类别中，同时该路径所对应的文章，你在首页也将看不到，但是你可以通过`/xxx.md`进行访问

```js
//在统计文章，标签，类别的时候，需要排除的路径，只针对于根目录下的路径，
excludePath: ['/footer.html','/v1.3.0/','/plugin/'],
```

只支持排除在`docs`根目录下的md文件以及在`docs`子目录中，可以直接通过`/xxx/xxx/xxx/`进行访问的路径，其他的不支持



## 分割符

![image-20210831202338204](http://ooszy.cco.vin/img/blog-note/image-20210831202338204.png?x-oss-process=style/pictureProcess1)



此分割符可以通过`split`项进行设置

```js
split: "~"
```





## 文章页面配置

[文章页面配置](./page.md)

