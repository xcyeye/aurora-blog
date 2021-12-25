---
date: 2021/12/24 08:08
title: vuepress切换打包工具
tag: [vite,webpack]
categories: [build-tool]
---

vuepress在`beta.28`版本以前，默认使用的打包工具是`webpack`，但是从`beta.28`版本及之后的版本，便使用了`vite`作为默认的打包工具，但是你可以手动进行切换



## 如何查看当前的打包工具

### **vite**

![image-20211224081408044](https://ooszy.cco.vin/img/blog-note/image-20211224081408044.png)

如果你运行`npm run dev`之后，会出现多个打开网址，那么这个就是使用`vite`作为打包工具，同时，你也可以运行`npm run build`命令，在控制台会有提示

![image-20211224081606424](https://ooszy.cco.vin/img/blog-note/image-20211224081606424.png)



### **webpack**

![image-20211224081735990](https://ooszy.cco.vin/img/blog-note/image-20211224081735990.png)

如果你运行是上面这种，只出现一个网址，那么你使用的就是`webpack`作为打包工具，并且你可以通过打印的信息便可以看出



::: warning

我推荐大家使用`webpack`，因为目前使用`vite`的话，我主题中，需要引入`leanCloud`的依赖包，但是此依赖包里面，好像并不能使用`import`进行导入，所以`vuepress-plugin-coze`插件，我还是使用`require()`进行引入，但是这样的话，使用vite启动，浏览器控制台就会报一个错误`require is not defined`，解决办法就是使用`webpack`

:::





## 切换打包工具

如果你需要在这两个工具之间进行切换，那么你需要先安装`vuepress-webpack@next`,请运行下面命令

```sh
npm install -D vuepress-webpack@next
```



然后进入到`docs/.vuepress/config.js`文件中，加上下面内容

```js
module.exports = {
    bundler: '@vuepress/bundler-webpack',
}
```



::: details 还不会加?

![image-20211224082501080](https://ooszy.cco.vin/img/blog-note/image-20211224082501080.png)

:::

如果你在`config.js`文件里面加上`bundler: '@vuepress/bundler-webpack'`，那么你现在重新运行`npm run dev`或者`npm run build`将使用`webpack`作为打包工具

同理，如果你把上面的`bundler: '@vuepress/bundler-webpack'`注释了，那么就使用默认的`vite`作为打包工具



::: warning

推荐使用`webpakck`作为打包工具

:::





