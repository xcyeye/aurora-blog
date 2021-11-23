# 友情链接页面



## 标题

![image-20210831200157058](http://ooszy.cco.vin/img/blog-note/image-20210831200157058.png?x-oss-process=style/pictureProcess1)

如需更改此标题，请更改源码，在`docs/.vuepress/theme/lib/client/components/Link.vue`，暂未提供配置修改





## 自己站点信息

```yaml
siteInformation: {
  title: 青衫烟雨客,
  url: https://vipblogs.cn,
  logo: https://ooszy.cco.vin/img/blog-public/1.png,
  describe: 这是我的站点描述
}
```

自己的站点信息，在1.3.2之后，作为友联申请要求

<img src="https://ooszy.cco.vin/img/blog-note/image-20211021000922907.png?x-oss-process=style/pictureProcess1" alt="image-20211021000922907" style="zoom:50%;" />

## 朋友站点

`friendLinks`是一个数组对象

```js
module.exports = {

    themeConfig: {
        //友情链接数组
        friendLinks: [
            {
                //网站标题
                title: "XI溪",

                //站点链接
                url: "http://www.xiaoxuya.top",

                //站点logo
                logo: "https://www.xiaoxuya.top/img/logo.png",

                //站点描述
                describe: "人生若只是初见,何事秋风悲画扇",
            },
            {
                title: "左眼会陪右眼哭の博客",
                url: "http://qkongtao.cn/",
                logo: "http://qiniu.qkongtao.cn/2020/12/d11-e1628358435552.png",
                describe: "干嘛这么想不开，要在脸上贴个输字！",
            },
            {
                title: "[ Blog We]",
                url: "https://blogwe.com/",
                logo: "https://blogwe.com/favicon.ico",
                describe: "博客大全-做最好的博客导航！",
            },
            {
                title: "I Am I",
                url: "https://5ime.cn",
                logo: "https://cdn.jsdelivr.net/gh/5ime/img/avatar.jpg",
                describe: "永远相信美好的事情即将发生",
            },
            {
                title: "Davinci的红茶馆",
                url: "https://davincievans.top/",
                logo: "https://cdn.jsdelivr.net/gh/DavinciEvans/Imgs-bed@master/gallery/avatar.jpg",
                describe: "You are all stardust.",
            },
            {
                title: "皮皮凛の小窝",
                url: "https://owomoe.net/",
                logo: "https://cdn.jsdelivr.net/gh/AyagawaSeirin/Assets/img/logo.jpg",
                describe: "永远相信美好的事情即将发生~",
            },
            {
                title: "月月月子喵",
                url: "https://haozi.moe",
                logo: "https://haozi.moe/css/images/logo_christmas.png",
                describe: "可爱的月子酱",
            },
            {
                title: "疫情在线捐款系统",
                url: "http://yq.vipblogs.cn/",
                logo: "https://ooszy.cco.vin/img/blog-public/avatar.jpg",
                describe: "基于echarts的疫情捐款系统",
            },
            {
                title: "疫情在线捐款系统后台登录",
                url: "http://admin.vipblogs.cn/",
                logo: "https://ooszy.cco.vin/img/blog-public/avatar.jpg",
                describe: "该捐款系统后台登录",
            },
        ],
    }
}
```



![image-20210831200558910](http://ooszy.cco.vin/img/blog-note/image-20210831200558910.png?x-oss-process=style/pictureProcess1)

颜色随机选择，随机然后配置

```js
randomColor: []
```



## 说说页面配置

[说说页面配置](mood.md)



