# 顶部图片

顶部图片是一个全局组件，可以直接使用，位置`docs/.vuepress/theme/lib/client/components/global/TopImage.vue`



## 设置图片地址 

从v1.3.2版本开始，便加入了定制顶部图片的选项，你可以使用随机图片接口，也可以使用自己的图片地址



### 使用随机图片接口

```js
module.exports = {

    themeConfig: {
		//首页文章列表封面图api接口
        homePageImgApi: "https://api.ixiaowai.cn/api/api.php",
    }
}
```



::: tip

首页文章列表图片和文章，标签等顶部图片共同使用一个图片api接口

:::



### 使用自己的图片

> `主题默认使用的是随机图片，如果你想改成自己的图片的话，那么需要开启此功能`

```js
module.exports = {

    themeConfig: {
		//自定义顶部图片
        customTopImg: {
            //是否启用定制顶部图片，控制全局，如果关闭，那么将使用随机图片，随机图片接口可以自己设置
            custom: true,

            //文章顶部图片，数组，每次从数组中随机选择一张
            page: [
                "https://picoss.cco.vin/animate/wall/555260.png",
                'https://picoss.cco.vin/animate/wall/404901.png',
                'https://picoss.cco.vin/animate/wall/734386.png'
            ],
            //友情链接页面
            friend: [
                "https://picoss.cco.vin/animate/wall/669.png",
                'https://picoss.cco.vin/animate/wall/5332.png'
            ],
            //标签页面
            tag: [
                "https://picoss.cco.vin/animate/wall/763311.png"
            ],
            //心情页面
            mood: [
                "https://picoss.cco.vin/animate/wall/5849.png"
            ],
        },
    }
}
```





## 组件

### 代码

```vue
<template>
  <div v-if="isShowTopImg" :style="setBackgroundUrl" id="page-top">
    <div v-if="isShowHeadLine">
      <h1>{{headLine}}</h1>
    </div>
  </div>
</template>
```



### props

```js
props: {
    isShowTopImg: {
      type: Boolean,
      default() {
        return false
      }
    },
    isShowHeadLine: {
      type: Boolean,
      default() {
        return false
      }
    },
    headLine: {
      type: String,
      default() {
        return "";
      }
    }
  }
```

| props        | des           |
| ------------- |:-------------:|
| isShowTopImg      | 是否展示顶部图片 |
| isShowHeadLine      | 是否展示标题，针对于文章页面      |
| headLine | 标题名称      |



## 额外使用组件

如需在自己需要的地方使用，可以通过下面方式，传入对应值就行

```vue
<top-image :is-show-top-img="..."
           :is-show-head-line="..."
           :head-line="."/>
```





## 海报功能配置

[海报功能配置](./poster.md)