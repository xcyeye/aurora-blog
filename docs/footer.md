# 页脚设置

## 配置

页脚 组件位置`docs/.vuepress/theme/lib/client/components/global/Footer.vue`

![image-20210831151827669](http://ooszy.cco.vin/img/blog-note/image-20210831151827669.png?x-oss-process=style/pictureProcess1)




```yaml
isShowFooter: true #是否展示页脚，如果你自己使用Footer.vue组件，此项没用

#页脚显示，以HTML的形式显示 并且首页只显示此数组中的两项，只显示两项
footer: 
  - Copyright © by qsyyke All Rights Reserved.
  - Theme <a href="https://www.cco.vin">ccds</a> by qsyyke
  - "<a target='_blank' href='http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142' style='display:inline-block;text-decoration:none;height:20px;line-height:20px;'><img src='' style='float:left;'/><p style='float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px;'>滇公网安备 53060202000142号</p></a>"

#是否展示运行时间 默认为true
isShowRunTime: true 

#开始时间
startRunTime: 8/7/2021 12:22:00
#运行时间前缀
prefixRuntime: 小破站已运行
```



```js
module.exports = {

    themeConfig: {
		//页脚信息，支持HTML，这是一个数组
        footer: [
            "Copyright © by qsyyke All Rights Reserved.",
            "<a target='_blank' href='http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142' style='display:inline-block;text-decoration:none;height:20px;line-height:20px;'><img src='' style='float:left;'/><p style='float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px;'>滇公网安备 53060202000142号</p></a>",
            "<a href='https://www.upyun.com/?utm_source=lianmeng&utm_medium=referral'>本网站由<img style='height: 24.1px;width: 55.3px;' src='https://ooszy.cco.vin/img/blog-note/%E5%8F%88%E6%8B%8D%E4%BA%91_logo5.png?x-oss-process=style/pictureProcess1'>提供CDN加速服务</a>",
        ],

        //是否显示页脚，控制全局
        isShowFooter: true,

        //是否显示主题信息在页脚，为false关闭
        isShowThemeCopyright: false,

        //是否展示运行时间
        isShowRunTime: true,

        //网站开始时间，请按照以下格式进行
        startRunTime: "8/7/2021 12:22:00",

        //网站运行时间前缀
        prefixRuntime: "小破站已运行",
    }
}
```



## 组件设置

因为Footer.vue是一个全局组件，可以直接使用

```vue
<Footer :theme-property="themeProperty"
        :is-home="frontmatter.home"
        :is-show-footer="isShowFooter">
</Footer>
```

- theme-property主题配置
- is-home是否为首页，决定页脚展示效果
- is-show-footer 是否展示页脚

如果需要修改运行多少天等等，需要修改代码，在`Footer.vue`中

```js
this.runTime = this.prefixRuntime + A + "天" + B + "小时" + C + "分" + D + "秒";
```





## 组件代码

**Footer.vue**

```vue
<template>
  <div class="c-page-parent" v-if="!isHome">
    <div v-if="isShowFooter" :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="footer" id="c-page">
      <FooterItem v-for="item in footerArr" :item="item"></FooterItem>
      <div class="footer-item" v-if="isShowRunTime" id="footer-item">
        <span >{{runTime}}</span>
      </div>
    </div>
  </div>

  <div v-if="isHome" class="home-footer" id="home-footer">
    <span v-html="footerArr[0]"></span>
    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
    <span v-html="footerArr[1]"></span>
  </div>
</template>
```



**FooterItem.vue**

```vue
<template>
  <div class="footer-item" id="footer-item">
    <span v-html="item"></span>
  </div>
</template>
```



## 覆盖主题样式配置

[覆盖主题样式配置](./style.md)