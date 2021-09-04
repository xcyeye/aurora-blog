# 友情链接页面

## 顶部图片

![image-20210831200004738](http://ooszy.cco.vin/img/blog-note/image-20210831200004738.png?x-oss-process=style/pictureProcess1)



## 标题

![image-20210831200157058](http://ooszy.cco.vin/img/blog-note/image-20210831200157058.png?x-oss-process=style/pictureProcess1)

如需更改此标题，请更改源码，在`docs/.vuepress/theme/lib/client/components/Link.vue`





## 自己站点信息

```yaml
siteInformation: {
  title: 青衫烟雨客,
  url: https://vipblogs.cn,
  logo: https://ooszy.cco.vin/img/blog-public/1.png,
  describe: 这是我的站点描述
}
```



## 朋友站点

`friendLinks`是一个数组对象

```yaml
friendLinks: [
  {
  title: 疫情在线捐款系统,
  url: http://yq.vipblogs.cn/,
  logo: http://yq.vipblogs.cn/favicon.ico,
  describe: 基于echarts的疫情捐款系统
  },
  {
  title: 疫情在线捐款系统后台登录,
  url: http://admin.vipblogs.cn/,
  logo: /img/head.jpg,
  describe: 该捐款系统后台登录
  },
  {
  title: 另一博客,
  url: https://cco.vin/,
  logo: https://vipblogs.cn/wp-content/uploads/2021/03/bitbug_favicon.ico,
  describe: 以前博客
  },
]
```



![image-20210831200558910](http://ooszy.cco.vin/img/blog-note/image-20210831200558910.png?x-oss-process=style/pictureProcess1)

单个颜色是从`randomColor`中随机选取，如需自定义，请删除所有randomColor，添加自己喜欢的颜色

