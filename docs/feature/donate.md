# 赞赏

该组件 是一个全局组件，位置`docs/.vuepress/theme/lib/client/components/global/Donate.vue`

![image-20210831164021528](http://ooszy.cco.vin/img/blog-note/image-20210831164021528.png?x-oss-process=style/pictureProcess1)

<img src="http://ooszy.cco.vin/img/blog-note/image-20210831164036678.png?x-oss-process=style/pictureProcess1" alt="image-20210831164036678" style="zoom:50%;" />



## 配置

### 扫码支付

<img src="http://ooszy.cco.vin/img/blog-note/image-20210831164324595.png?x-oss-process=style/pictureProcess1" style="zoom:50%;" />

```js
module.exports = {

    themeConfig: {
        donate: {
            donateImg: [
                "https://ooszy.cco.vin/img/blog-public/wxpay.png",
                "https://ooszy.cco.vin/img/blog-public/zfbpay.jpg",
            ],
        }
    }
}
```

推荐传入两张二维码图片，支付宝或者位置



### 商品

<img src="http://ooszy.cco.vin/img/blog-note/image-20210831164526261.png?x-oss-process=style/pictureProcess1" alt="image-20210831164526261" style="zoom:50%;" />

```yaml
module.exports = {

    themeConfig: {
        donate: {
            donateProduct: [
                {
                    //名字
                    name: "奶茶",

                    //图片地址
                    img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",

                    //价格
                    price: 18,

                    //前缀
                    prefix: "$",
                },
                {
                    name: "全味奶茶",
                    img: "https://ooszy.cco.vin/img/blog-note/image-20210911233612031.png?",
                    price: 11,
                    prefix: "￥",
                },
            ],
        }
    }
}
```



### 赞赏列表

<img src="http://ooszy.cco.vin/img/blog-note/image-20210831164718349.png?x-oss-process=style/pictureProcess1" alt="image-20210831164718349" style="zoom:50%;" />

```yaml
module.exports = {

    themeConfig: {
        donate: {
            //用户赞赏列表数组
            donateList: [
                {
                    //用户名
                    name: "qsyyke",

                    //赞赏信息
                    msg: "文章写的非常好",

                    //用户头像
                    img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",

                    //打赏金额
                    price: 11,

                    //金额前缀
                    prefix: "￥",
                },
                {
                    name: "初尘",
                    msg: "主题太棒了",
                    img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",
                    price: 7,
                    prefix: "￥",
                },
            ],
        }
    }
}
```



### 在线支付

![image-20210831164942581](http://ooszy.cco.vin/img/blog-note/image-20210831164942581.png?x-oss-process=style/pictureProcess1)

```js
module.exports = {

    themeConfig: {
        donate: {
            onlineList: true
        }
    }
}
```



对于在线支付，请自行写支付接口，我并没有提供，只是提供一个布局，组件位置docs/.vuepress/theme/lib/client/components/global/Donate.vue

```html
<form action="https://pay.cco.vin/pay/" method="post">
```





### 全部配置

```yaml
module.exports = {

    themeConfig: {
        donate: {

            //赞赏页面，支付二维码，推荐放置两张图片链接
            donateImg: [
                "https://ooszy.cco.vin/img/blog-public/wxpay.png",
                "https://ooszy.cco.vin/img/blog-public/zfbpay.jpg",
            ],

            //是否在文章页面显示赞赏 默认显示
            articlePage: true,

            //是否在关于页面显示 默认显示
            aboutPage: true,

            //显示在赞赏页面的信息
            donateProduct: [
                {
                    //名字
                    name: "奶茶",

                    //图片地址
                    img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",

                    //价格
                    price: 18,

                    //前缀
                    prefix: "$",
                },
                {
                    name: "全味奶茶",
                    img: "https://ooszy.cco.vin/img/blog-note/image-20210911233612031.png?",
                    price: 11,
                    prefix: "￥",
                },
            ],

            //是否显示在线支付的订单信息，如果需要开启，请自己写支付接口，自己修改源码，默认关闭
            onlineList: true,

            //用户赞赏列表数组
            donateList: [
                {
                    //用户名
                    name: "qsyyke",

                    //赞赏信息
                    msg: "文章写的非常好",

                    //用户头像
                    img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",

                    //打赏金额
                    price: 11,

                    //金额前缀
                    prefix: "￥",
                },
                {
                    name: "初尘",
                    msg: "主题太棒了",
                    img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",
                    price: 7,
                    prefix: "￥",
                },
            ],
        },
    }
}
```





## 组件代码

https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/Aurora-theme/lib/client/components/global/Donate.vue



## 组件使用

此组件是一个全局组件，可以直接使用

```vue 
<Donate/>
```



## 顶部图片配置

[顶部图片配置](./image.md)