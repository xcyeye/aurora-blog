---
categories: [目录结构,主题结构]
---



# 主题目录结构

## 结构

```
├─lib
|  ├─client(组件)
|  |   ├─utils
|  |   ├─styles(主题样式)
|  |   |   ├─默认主题样式
|  |   |   ├─theme.style.css(ccds主题样式)
|  |   ├─public(静态文件)
|  |   |   ├─js
|  |   |   | ├─network.js(axios封装)
|  |   |   | ├─tag.js(tag)
|  |   |   | ├─store(vuex)s
|  |   ├─layouts(layout)
|  |   |    ├─404.vue
|  |   |    └Layout.vue
|  |   ├─components(所有组件)
|  |   |     ├─global(全局组件)
|  |   |     ├─child(子组件)
```



## 路由

主题除了vuepress提供的路由之外，还加入了`/tag,/about,/link,/mood`四个路由

| 路由   |               |
| ------ | :-----------: |
| /tag   | 分类和标签页面 |
| /link  | 友情链接页面 |
| /about | 关于页面  |
| /mood | 心情页面 |

