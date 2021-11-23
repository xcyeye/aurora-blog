# 注册组件

::: tip

在v1.4.2版本中，你可以自己增加新的页面，通过url进行访问，使用方法如下

:::



## 前言

在v1.4.2版本中，主题新增了一个全局组件，在这个组件中，默认只保留了导航栏，手机端侧边栏，样式菜单，背景图片，并且你可以通过传入`props`属性，不展示他们

默认的内容如下

<img src="https://ooszy.cco.vin/img/blog-note/image-20211021224708201.png?x-oss-process=style/pictureProcess1" alt="image-20211021224708201" style="zoom:50%;" />



或者你什么都不要，完全按照你想要的进行布局

<img src="https://ooszy.cco.vin/img/blog-note/image-20211021224804290.png?x-oss-process=style/pictureProcess1" alt="image-20211021224804290" style="zoom:50%;" />



## 使用

使用步骤

- 在`docs/.vuepress`文件夹内，任意位置，创建一个`.vue`文件，推荐创建一个单独文件夹，用来保存这些组件

- 在`docs/.vuepress`目录下，新建`clientAppEnhance.js`文件，文件的内容如下

  ```js
  import { defineClientAppEnhance } from '@vuepress/client'
  
  export default defineClientAppEnhance(({ app, router, siteData }) => {
    // ...
  })
  ```

  ::: tip

  - `app` 是由 [createApp](https://v3.cn.vuejs.org/api/application-api.html) 创建的 Vue 应用实例。
  - `router` 是由 [createRouter](https://next.router.vuejs.org/zh/api/index.html#createrouter) 创建的路由实例。
  - `siteData` 是一个根据用户配置生成的对象，包含 [base](https://v2.vuepress.vuejs.org/zh/reference/config.html#base), [lang](https://v2.vuepress.vuejs.org/zh/reference/config.html#lang), [title](https://v2.vuepress.vuejs.org/zh/reference/config.html#title), [description](https://v2.vuepress.vuejs.org/zh/reference/config.html#description), [head](https://v2.vuepress.vuejs.org/zh/reference/config.html#head) 和 [locales](https://v2.vuepress.vuejs.org/zh/reference/config.html#locales)。

  Client App Enhance 会在客户端应用创建后被调用，它可以为 Vue 应用添加任意功能。

  详细介绍，可以查看官方说明https://v2.vuepress.vuejs.org/zh/advanced/cookbook/usage-of-client-app-enhance.html

​		:::

- 你现在就可以在此`clientAppEnhance.js`文件中，注册组件，或者是进行其他的操作，在此文件中，如果你在此文件中，注册了一个组件，那么这个组件是一个全局组件，你可以在`.md`文件中，直接使用该组件(`vuepress框架，会先将.md文件解析成.vue文件，然后再是.html`)

- 在`docs/`目录下，新建一个`.md`，在此文件中，写入下面frontmatter

  ```md
  ---
  layout: Aurora #你刚才注册的组件 Aurora.vue
  ---
  ```

  > 如果你在`.md`文件中，写入上面frontmatter，那么这个md文件，将使用`Aurora`作为布局，尽管此md文件中，还存在其他的内容，都会被清空

- 然后现在你就可以在`Aurora.vue`中，进行任意的布局了





## 案例

下面是一个简单的案例

> `创建组件`

```vue
<template>
<!--docs/.vuepress/components/Aurora.vue-->
  <aurora-global :show-bg="true" :show-style-menu="true" :show-navbar="true">
    <template #top>
      <h1 style="margin: 0 auto">注册组件测试</h1>
    </template>

    <template #center>
      <div style="width: 30rem;height: 30rem;background: plum;margin: 0 auto"></div>
    </template>

    <template #bottom>
      <aurora-footer/>
    </template>
  </aurora-global>
</template>

<script>
export default {
  name: "Mycom"
}
</script>

<style scoped>

</style>
```



> 注册全局组件

```js
//clientAppEnhance.js

import { h } from 'vue';
import { defineClientAppEnhance } from '@vuepress/client';
import Aurora from './components/Aurora'
export default defineClientAppEnhance(({ app, router }) => {
    app.component("Aurora",Aurora)
});
```



> 新建`docs/register.md`文件，并指定`layout`

```md
---
layout: Aurora
---
```



因为`docs/`目录下的md文件，会自动解析成`.html`，你可以直接在浏览器中进行访问，`localhost:8080/register.html`

结果

![image-20211021230635363](https://ooszy.cco.vin/img/blog-note/image-20211021230635363.png?x-oss-process=style/pictureProcess1)



::: tip

接下来，其他的事情，就交给你进行创造了

::: 



## AuroraGlobal.vue

此组件是一个全局组件，组件的内容如下

```vue
<template>
  <div class="common"
       :style="$store.state.borderRadiusStyle +
       $store.state.opacityStyle + $store.state.fontColorStyle +
       $store.state.fontFamilyStyle + $store.state.filterBlurStyle">
    <Navbar class="sidebar-single-enter-animate" :style="$store.state.opacityStyle" v-if="showNavbar">
      <template #before>
        <slot name="navbar-before" />
      </template>
      <template #after>
        <slot name="navbar-after" />
      </template>
    </Navbar>
    <mobile-sidebar v-if="showMobileSidebar"/>
    <social-spin v-if="showStyleMenu"/>
    <style-menu
        v-if="showStyleMenu"
        :theme-property="themeProperty"
        @setIsFitter="setIsFitter"
        @setBodyStyle="getBodyStyle"
        @setBodyWallpaper="setBodyWallpaper"
        :is-show-ico="true"
        custom-class="custom-about"/>
    <slot name="top"></slot>
    <slot name="center"></slot>
    <slot name="bottom"></slot>
    <div id="set-bg"
         v-if="showBg"
         :class="{'set-bg-fitter': $store.state.isFitter}"
         :style="'--opacity: ' + $store.state.varOpacity +
         '; --fitter-blue: ' + $store.state.varFilterBlur +
         'px; --borderRadius: ' + $store.state.varBorderRadius +
         'px; --backgroundImageUrl: url(' + $store.state.homeWps + ')'"
    ></div>
  </div>

</template>
<script lang="ts">

//组件导入
import StyleMenu from '../child/home/StyleMenu.vue'
import Navbar from '../../components/Navbar.vue'
import Home from '../Home'
import MobileSidebar from "../child/side/MobileSidebar.vue";
import SocialSpin from '../SocialSpin'

//配置导入
import {computed, defineComponent, Transition,} from 'vue'
import {usePageData, usePageFrontmatter} from '@vuepress/client'
import type {DefaultThemePageFrontmatter} from '../../../shared'
import {useThemeData, useThemeLocaleData} from '../../composables'
import EasyTyper from "easy-typer-js";
import $ from 'jquery'

export default defineComponent({
  name: 'Common',
  components: {
    SocialSpin,
    Navbar,
    Transition,
    StyleMenu,
    Home,
    MobileSidebar,
  },
  props: {
    showBg: {
      type: Boolean,
      default() {
        return true
      }
    },
    showStyleMenu: {
      type: Boolean,
      default() {
        return true
      }
    },
    showNavbar: {
      type: Boolean,
      default() {
        return true
      }
    },
    showMobileSidebar: {
      type: Boolean,
      default() {
        return true
      }
    }
  },
  data() {
    return {
      sidebarRowVar: 5,
      colorStyle: '',
      fontStyle: '',
      isShowFooter: '',
      colorFontStyle: '',
      isFitter: false,
      themeProperty: '',
      //首页壁纸数组
      homeWps: [],
      mobilePageSidebar: true
    }
  },
  computed: {
    setBodyStyle() {
      if (this.fontStyle === "") {
        return ""
      }
      return this.$store.state.fontColorStyle + ";"+ this.$store.state.fontFamilyStyle
    }
  },
  methods: {
    getBodyStyle() {
      let fontColorStyle = this.$store.state.fontColorStyle
      let fontFamilyStyle = this.$store.state.fontFamilyStyle

      if (fontColorStyle === undefined) {
        this.colorStyle = '--fontColor: ""'
      }else {
        this.colorStyle = fontColorStyle
      }
      if (fontFamilyStyle === undefined) {
        this.fontStyle = '--fontFamily: ""'
      }else {
        this.fontStyle = fontFamilyStyle
      }

      this.colorFontStyle = this.colorStyle + " "+ this.fontStyle
    },
    setBodyWallpaper() {
      //切换首页壁纸
      if (this.homeWps.length === 1) {
        this.$store.commit("setHomeWps",{
          homeWps: this.homeWps[0]
        })
        return
      }

      for (let i = 0; i < this.homeWps.length; i++) {
        if (this.$store.state.homeWps.search(this.homeWps[i]) !== -1) {
          if (i === this.homeWps.length -1) {
            this.$store.commit("setHomeWps",{
              homeWps: this.homeWps[0]
            })
            return;
          }else {
            this.$store.commit("setHomeWps",{
              homeWps: this.homeWps[i + 1]
            })
            return;
          }
        }
      }
    },
    setIsFitter(isFitter) {
      this.isFitter = isFitter
    },
  },
  setup() {
    const page = usePageData()
    const frontmatter = usePageFrontmatter<DefaultThemePageFrontmatter>()
    const themeLocale = useThemeLocaleData()

    // navbar
    const shouldShowNavbar = computed(
        () =>
            frontmatter.value.navbar !== false && themeLocale.value.navbar !== false
    )

    return {
      themeLocale,
      frontmatter,
      page,
      shouldShowNavbar,
    }
  },
  created() {
    if (this.$store.state.printRightIndex === 0) {
      console.log("%c vuepress-theme-Aurora %c by qsyyke","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(255,202,212,.8);padding: 10px;border-bottom-left-radius: 13px;border-top-left-radius: 13px;","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(178,247,239,.85);padding: 10px;border-bottom-right-radius: 13px;border-top-right-radius: 13px;")
      console.log("%c Version %c "+ this.$store.state.latestVersion + "","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(255,202,212,.8);padding: 10px;border-bottom-left-radius: 13px;border-top-left-radius: 13px;","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(178,247,239,.85);padding: 10px;border-bottom-right-radius: 13px;border-top-right-radius: 13px;")
    }

    this.$store.state.printRightIndex = 1
    this.themeProperty = useThemeData().value

    //从配置文件中，获取首页壁纸
    let homeWps = []
    if (this.themeProperty.homeWps === undefined || this.themeProperty.homeWps == null) {
      homeWps.push("https://picoss.cco.vin/animate/wall/404901.png")
    }else {
      homeWps = this.themeProperty.homeWps
    }

    if (homeWps.length === 0) {
      homeWps.push("https://picoss.cco.vin/animate/wall/404901.png")
    }

    this.homeWps = homeWps

    if (this.aboutOption !== undefined || this.aboutOption != null) {
      this.aboutOption = this.themeProperty.about
    }

    this.$store.commit("setIsFitter",{
      isFitter: this.themeProperty.isFitter
    })
    this.isShowFooter = this.themeProperty.isShowFooter

    let fontColorStyle = this.$store.state.fontColorStyle
    let fontFamilyStyle = this.$store.state.fontFamilyStyle
    if (fontColorStyle === undefined) {
      this.colorStyle = '--fontColor: ""'
    }else {
      this.colorStyle = fontColorStyle
    }
    if (fontFamilyStyle === undefined) {
      this.fontStyle = '-fontFamily: ""'
    }else {
      this.fontStyle = fontFamilyStyle
    }

    this.colorFontStyle = this.colorStyle + " "+ this.fontStyle
  },
  mounted() {
    if (document.documentElement.clientWidth < 719) {
      this.sidebarRowVar = 6
    }

    //手机端壁纸
    let screen = document.body.clientWidth
    if (screen < 500) {
      if (this.themeProperty.homeWpsMobile !== undefined &&
          this.themeProperty.homeWpsMobile != null) {
        try {
          if (this.themeProperty.homeWpsMobile.length !== 0) {
            this.homeWps = this.themeProperty.homeWpsMobile
          }
        }catch (e) {
        }
      }
    }

    let backgroundUrl = ''
    if (this.$store.state.homeWps === "") {
      //将首页壁纸设置为配置文件数组中的第一张图片
      backgroundUrl = this.homeWps[0]
    }else {
      //将首页壁纸设置为配置文件数组中的第一张图片
      backgroundUrl = this.$store.state.homeWps
    }
    this.$store.commit("setHomeWps",{
      homeWps: backgroundUrl
    })


    const frontmatter = usePageFrontmatter<DefaultThemePageFrontmatter>()
    let keywordValue = this.themeProperty.keyword
    let descriptionValue = this.themeProperty.description

    if (keywordValue === undefined) {
      keywordValue = "vuepress,vuepress-theme-aurora,blog-theme"
    }

    if (descriptionValue === undefined) {
      descriptionValue = "个人博客"
    }

    if (frontmatter.value.home) {
      //如果是首页的话，就将key和description设置为配置中的
      let descriptionDom = document.querySelectorAll('meta[name="description"]')
      let keywordDom= document.querySelector('meta[name="keyword"]')

      //设置关键词
      if (keywordDom === null) {
        //head中没有keyword 添加一个
        let keywordMeta = $('<meta name="keyword" content="'+ keywordValue +'">').get(0)
        document.querySelector("head").appendChild(keywordMeta)
      }else {
        //已经存在keyword属性的dom 设置其content
        keywordDom.setAttribute("content",keywordValue)
      }

      if (descriptionDom.length !== 0) {
        new Promise((resolve,reject) => {
          for (let i = 0; i < descriptionDom.length; i++) {
            document.querySelector("head").removeChild(descriptionDom[i])
          }
          resolve()
        }).then(() => {
          let descriptionMeta = $('<meta name="description" content="'+ descriptionValue +'">').get(0)
          document.querySelector("head").appendChild(descriptionMeta)
        })
      }else {
        let descriptionMeta = $('<meta name="description" content="'+ descriptionValue +'">').get(0)
        document.querySelector("head").appendChild(descriptionMeta)
      }
    }

    if (document.body.clientWidth < 550 && this.themeProperty.mobilePageSidebar !== undefined) {
      this.mobilePageSidebar = this.themeProperty.mobilePageSidebar
    }
  }
})
</script>

<style lang="css">
/* 从调色板中引入变量 */
@import '@vuepress/plugin-palette/palette';
</style>

<style lang="css" src="@vuepress/plugin-palette/style"></style>
```





### props

该组件的`props`有

```ts
 props: {
    showBg: {
      type: Boolean,
      default() {
        return true
      }
    },
    showStyleMenu: {
      type: Boolean,
      default() {
        return true
      }
    },
    showNavbar: {
      type: Boolean,
      default() {
        return true
      }
    },
    showMobileSidebar: {
      type: Boolean,
      default() {
        return true
      }
    }
  },
```



- showBg

  > 是否显示背景图片

- showStyleMenu

  > 是否显示样式菜单

- showNavbar

  > 是否显示导航

- showMobileSidebar

  > 是否显示手机端侧边栏