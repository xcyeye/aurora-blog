# 广告

位置`docs/.vuepress/theme/lib/client/components/AdSense.vue`

![image-20210831162426440](http://ooszy.cco.vin/img/blog-note/image-20210831162426440.png?x-oss-process=style/pictureProcess1)

## 配置

此`readme.md`中的配置，只针对于文章页面，对于其他放置广告组件的页面，不适用

```yaml
adsenseArr: [
  {
  	#位置
    position: right,
    #显示标签
    adsenseMessage: 官网用户专享,
    # 广告代码片段
    script: "",
    # 背景图片
    adsenseBackgroundImg: /img/33.jpg,
  },
  {
    position: center,
    adsenseMessage: 官网用户专享1,
    script: <a href="https://baidu.com">这是中间1</a>,
    adsenseBackgroundImg: /img/33.jpg,
  },
  {
    position: center,
    adsenseMessage: 官网用户专享2,
    script: <a href="https://baidu.com">这是中间2</a>,
    adsenseBackgroundImg: /img/33.jpg,
  },
  {
    position: center,
    adsenseMessage: 官网用户专享3,
    script: <a href="https://baidu.com">这是中间3</a>,
    adsenseBackgroundImg: /img/33.jpg,
  },
]

#文章中，每个多少个p标签插入一个广告
insertAdsenseRule: 17

#每一页文章中，最大广告数
adsenseLength: 3
```





## 代码

```vue
<template>
  <div :class="getPosition" class="adsense" id="adsense">
    <div :style="setBackgroundImg" class="adsense-item" id="adsense-item">
      <h3 class="adsense-message">{{adsenseMessage}}</h3>
      <div class="insert-adsense">
        <slot ></slot>
      </div>
    </div>
  </div>
</template>
```



## props

```js
props: {
    adsensePosition: { //广告显示位置，center或者其他值
      type: String,
      default() {
        return 'center'
      }
    },
    adsenseMessage: { //在广告上，显示的文字
      type: String,
      default() {
        return '官网用户专享'
      }
    },
    adsenseBackgroundImg: {//显示的北京图片
      type: String,
      default() {
        return '/img/33.jpg'
      }
    }
  },
```

## 组件使用

AdSense.vue中提供了一个插槽，使用的时候，可以将广告代码放入插槽中，就可以

```vue
<AdSense adsense-position="right"
         :adsense-background-img="adsenseArr[0].adsenseBackgroundImg"
         :adsense-message="adsenseArr[0].adsenseMessage"
         >
    <div>广告代码片段</div>
</AdSense>
```

