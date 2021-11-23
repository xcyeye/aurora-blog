---
categories: [首页配置,一言,头像,社交,页脚]
date: "2021/11/18"
coverUrl: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp05%2F19100220060CY5-0-lp.jpg'
---


[aa](httpsas)


# 首页配置

![image-20211011164307090](https://ooszy.cco.vin/img/blog-note/image-20211011164307090.png?x-oss-process=style/pictureProcess1)

## 导航栏

导航栏配置可以查看vuepress官方配置<a href="https://v2.vuepress.vuejs.org/zh/reference/default-theme/config.html#navbar">入口</a>，可以借鉴一下我的配置

```js
//docs/.vuepress.config.js
module.exports = {
  themeConfig: {
    navbar: [
      {
            text: '数据库',
            children: [
                {
                    text: 'redis',
                    link: '/redis/'
                },
                {
                    text: 'mysql',
                    link: '/'
                }
            ]
        },
        {
            text: '其他',
            children: [
                {
                    text: '问题',
                    link: '/issue/'
                },
                {
                    text: '捐款系统整理',
                    link: '/donationSystem/'
                },
                {
                    text: '自然语言',
                    link: '/sdk/'
                },
            ]
        },
        {
            text: "关于",
            link: "/about"
        },
        {
            text: '友情链接',
            link: '/link'
        },
        {
            text: '标签',
            link: '/tag'
        },
        {
            text: '记录',
            link: '/mood'
        },
        {
            text: 'photo',
            link: '/photo'
        }
    ],
  },
}
```

其中`/about,/link,/tag,/mood`是主题新增的路由，对应关于页面，友情链接，标签，心情页面，所以请在navbar上，添加



## logo

![image-20210831141849241](http://ooszy.cco.vin/img/blog-note/image-20210831141849241.png?x-oss-process=style/pictureProcess1)

导航栏logo可以在`config.js`文件中，进行设置

```js
module.exports = {
  themeConfig: {
    // Public 文件路径
    logo: '/images/hero.png',
    // URL
    logo: 'https://vuejs.org/images/logo.png',
  },
}
```

如果没有在此`config.js`文件中进行设置，默认使用<a href="https://ooszy.cco.vin/img/blog-public/ccds_64.ico">default</a>



### logo文字

对于logo旁边文字，需要在`docs/readme.md`的`logoTitle`进行设置，默认值为`aurora`

```js
logoTitle: 'qsyyke'
```



![image-20210831142236064](http://ooszy.cco.vin/img/blog-note/image-20210831142236064.png?x-oss-process=style/pictureProcess1)

并且支持设置字体颜色，配置项为`logoColor`

```js
logoColor: '#2c3e50'
```

配置说明https://aurora.cco.vin/home/config.html#logotitle





## 中间头像

![image-20210831142850834](http://ooszy.cco.vin/img/blog-note/image-20210831142850834.png?x-oss-process=style/pictureProcess1)

此头像通过`heroImg`进行修改，并不是使用logo图片地址，默认图片使用<a href="https://ooszy.cco.vin/img/blog-public/avatar.jpg">default</a>

```js
heroImg: 'https://ooszy.cco.vin/img/blog-public/avatar.jpg'
```



## 随机一言

![image-20210831143353819](http://ooszy.cco.vin/img/blog-note/image-20210831143353819.png?x-oss-process=style/pictureProcess1)

该随机一言打印效果使用<a href="https://github.com/pengqiangsheng/easy-typer-js">easy-typer-js</a>



在后续版本中，会对随机一言进行优化，当前版本更改随机一言地址，不太容易



### 自定义一言

如果需要自定义一言，那么你需要开启，默认关闭

```js
//是否启用定制首页随机一言，默认未开启，使用随机一言 接口为https://international.v1.hitokoto.cn/?c=b&max_length=45
customRandomSay: false,

//定制首页随机一言文字
customRandomValue: '青衫烟雨客',
```



## 首页文章列表配置

[首页文章列表配置](./homePage.md)