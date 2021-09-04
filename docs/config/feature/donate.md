# 赞赏

该组件是一个全局组件，位置`docs/.vuepress/theme/lib/client/components/global/Donate.vue`

![image-20210831164021528](http://ooszy.cco.vin/img/blog-note/image-20210831164021528.png?x-oss-process=style/pictureProcess1)

![image-20210831164036678](http://ooszy.cco.vin/img/blog-note/image-20210831164036678.png?x-oss-process=style/pictureProcess1)



## 配置

### 扫码支付

![](http://ooszy.cco.vin/img/blog-note/image-20210831164324595.png?x-oss-process=style/pictureProcess1)

```yaml
donateImg: [
      https://ooszy.cco.vin/img/blog-public/wxpay.png,
      https://ooszy.cco.vin/img/blog-public/zfbpay.jpg
  ]
```

推荐传入两张二维码图片，支付宝或者位置



### 商品

![image-20210831164526261](http://ooszy.cco.vin/img/blog-note/image-20210831164526261.png?x-oss-process=style/pictureProcess1)

```yaml
donateProduct: [
    {
      #名称
      name: 奶茶,
      #商品图片
      img: https://ooszy.cco.vin/img/blog-public/nc.jpeg,
      #价格
      price: 10,
      #金额前缀
      prefix: $
    }
  ]
```



### 赞赏列表

![image-20210831164718349](http://ooszy.cco.vin/img/blog-note/image-20210831164718349.png?x-oss-process=style/pictureProcess1)

```yaml
donateList: [
    {
      name: sdf,
      msg: 奶阿萨德茶,
      img: https://ooszy.cco.vin/img/blog-public/nc.jpeg,
      price: 10,
      prefix: $
    },
  ]
```



### 在线支付

![image-20210831164942581](http://ooszy.cco.vin/img/blog-note/image-20210831164942581.png?x-oss-process=style/pictureProcess1)

对于在线支付，请自行写支付接口，在下面传入支付接口地址即可，对于赞赏页面，请根据需要自行修改代码

```html
<form action="https://pay.cco.vin/pay/" method="post">
```



### 全部配置

```yaml
donate: {
  donateImg: [
      https://ooszy.cco.vin/img/blog-public/wxpay.png,
      https://ooszy.cco.vin/img/blog-public/zfbpay.jpg
  ],
  #文章也页面是否显示
  articlePage: true,
  # 关于页面是否显示
  aboutPage: true,
  donateProduct: [
    {
      name: 奶茶,
      img: https://ooszy.cco.vin/img/blog-public/nc.jpeg,
      price: 10,
      prefix: $
    }
  ],
  # 是否显示在线支付列表(暂时无效)
  onlineList: true,
  donateList: [
    {
      name: sdf,
      msg: 奶阿萨德茶,
      img: https://ooszy.cco.vin/img/blog-public/nc.jpeg,
      price: 10,
      prefix: $
    }
  ],
}
```





## 组件代码





## 组件使用

此组件是一个全局组件，可以直接使用

```vue 
<Donate v-if="themeProperty.donate.aboutPage"/>
```



