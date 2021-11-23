# 文章推荐

此组件并不是全局组件，组件位置`docs/.vuepress/theme/lib/client/components/RecommendPage.vue`

![image-20210831161503804](http://ooszy.cco.vin/img/blog-note/image-20210831161503804.png?x-oss-process=style/pictureProcess1)



## 配置

```js
module.exports = {

    themeConfig: {
        recommendPageLength: 30 //最多推荐多少篇文章
        randomColor: ['#ffcad4','#d8e2dc','#8d99ae','#b8f2e6','#84c7d0'] //每个推荐背景颜色，随机
    recommendNoTitle: '`╮(￣▽￣)╭`' //文章标题为空时，就会使用这个进行替换
	}
}
```



推荐的文章是随机的，并不能指定



## 组件代码

```vue
<template>
  <BCenter>
    <template #page-center1>
      <h2 id="recommend-top-title">推荐阅读</h2>
    </template>
    <template #page-center3>
      <div id="recommend" class="recommend">
        <recommend-item
            :item="item"
            :key="index"
            :theme-property="themeProperty"
            :index="index"
            v-for="(item,index) in allPages"/>
      </div>
    </template>
  </BCenter>
</template>
```

其中`<BCenter>`组件是一个全局组件，其就是一个居中效果的，展示效果和下图差不多

![image-20210831161059607](http://ooszy.cco.vin/img/blog-note/image-20210831161059607.png?x-oss-process=style/pictureProcess1)

提供了很多插槽



### props

```js
props: {
    themeProperty: null
}
```



## 搜索功能配置

[搜索功能配置](./search.md)

