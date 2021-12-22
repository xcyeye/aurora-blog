---
tag: [目录结构,主题结构]
---



# 主题目录结构

## 结构

```
├─CHANGELOG.md #更新日志
├─deploy-dist.sh
├─deploy.sh
├─package-lock.json
├─package.json
├─postcss.config.js
├─README.md
├─docs
|  |- #Markdown文件
|  ├─.vuepress
|  |     ├─config.js
|  |     ├─themeConfig.js #主题配置
|  |     ├─theme
|  |     |   ├─package.json
|  |     |   ├─lib
|  |     |   |  ├─shared
|  |     |   |  ├─node
|  |     |   |  |  ├─auroraTheme.js #主题入口文件
|  |     |   |  |  ├─auroraTheme.ts
|  |     |   |  |  ├─utils
|  |     |   |  ├─client client配置
|  |     |   |  |   ├─clientAppEnhance.d.ts
|  |     |   |  |   ├─clientAppEnhance.js
|  |     |   |  |   ├─utils
|  |     |   |  |   ├─styles
|  |     |   |  |   |   ├─theme.style.css #主题默认样式配置
|  |     |   |  |   ├─public
|  |     |   |  |   |   ├─js
|  |     |   |  |   |   | ├─network.js #axios封装
|  |     |   |  |   |   | ├─tag.js #获取文章所有tag标签工具
|  |     |   |  |   |   | ├─store #vuex
|  |     |   |  |   |   ├─fonts #主题默认使用的字体
|  |     |   |  |   ├─layouts
|  |     |   |  |   |    ├─404.vue
|  |     |   |  |   |    └Layout.vue
|  |     |   |  |   ├─composables
|  |     |   |  |   ├─components #主题组件
|  |     |   |  |   |     ├─global #被注册为全局的组件
|  |     |   |  |   |     ├─child #子组件
|  |     ├─styles #覆盖主题默认样式文件 
|  |     |   ├─palette.css #定义Css变量
|  |     |   ├─index.css #样式文件用于覆盖默认样式或添加额外样式，因此它一般会在你主题样式的末尾引入
|  |     ├─public #静态文件
|  |     |   ├─avatar.png
|  |     |   ├─navbar.js
|  |     |   └themeConfig.js
```



## 路由

主题除了vuepress提供的路由之外，还加入了`/tag,/about,/link,/mood`四个路由

| 路由   |               |
| ------ | :-----------: |
| /tag   | 分类和标签页面 |
| /link  | 友情链接页面 |
| /about | 关于页面  |
| /mood | 心情页面 |

