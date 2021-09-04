# 顶部图片

顶部图片是一个全局组件，可以直接使用，位置`docs/.vuepress/theme/lib/client/components/global/TopImage.vue`



## 设置图片地址

图片地址并没有通过readme.md文件进行设置，如需更改图片地址，请修改`docs/.vuepress/theme/lib/client/public/js/store/state.js`中对应值，默认地址为https://api.iro.tw/webp_pc.php

```js
setBackgroundUrl() {
    return "background-image: url(" + this.$store.state.animeImg + ");"
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

