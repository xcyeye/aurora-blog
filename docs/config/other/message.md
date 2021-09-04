# 公告栏

此公告栏组件是一个全局组件，设计暂时比较简陋，如需美化，请自定修改，组件位置

`docs/.vuepress/theme/lib/client/components/global/Message.vue`

![image-20210831165655118](http://ooszy.cco.vin/img/blog-note/image-20210831165655118.png?x-oss-process=style/pictureProcess1)



## 配置



```yaml
# message可以使用HTML
message: 目前博客还没开发评论，如需添加友情链接，请到我旧博客添加评论评论我都会看，看到会添加到此处<a target="_blank" href="https://www.cco.vin/%e5%8f%8b%e6%83%85%e9%93%be%e6%8e%a5/">点击进入</a>

#是否显示公告 仅在友情链接中有效
isShowMessage: true
```



## 组件代码

```vue
<template>
  <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="c-page" id="c-page" style="text-align: center">
    <p class="message" v-html="message"></p>
  </div>
</template>
```



## 使用

```vue
<message/>
```

