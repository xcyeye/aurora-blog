# Front Matter

在开始之前，你可以先读一读官方对于[frontmatter](https://v1.vuepress.vuejs.org/zh/guide/frontmatter.html)的介绍，更有助于你的理解及使用



::: tip

`FrontMatter`是写在md文件中，并且`在每篇md的最顶部，使用---包裹`起来的一种形式，使用的是`yaml`语法，你可以像下面这样



```markdown
---
date: "2021/111/27" #手动设置此篇文章发布时间
tag: [tag1,tag2] #设置此篇文章的标签
categories: [cate1,cate2,cate3] #设置此篇文章的类别
keyword: key1,key2 #设置此篇文章的关键词
description: 这是一个描述 #设置此篇文章的描述
sticky: true #是否置顶该篇文章
coverUrl: "https://aurora.xcye.xyz/avatar.jpg" #设置封面图
title: 这是第一篇文章 # 设置标题
---
```

:::



上面这些是`aurora`主题自己加上的，你也可以加入官方的一些设置，下面这些都是官方的，并且在该主题中，基本上都支持

## 官方预定义变量

### title

- 类型: `string`
- 默认值: `h1_title || siteConfig.title`

当前页面的标题。

### lang

- 类型: `string`
- 默认值: `en-US`

当前页面的语言。

### description

- 类型: `string`
- 默认值: `siteConfig.description`

当前页面的描述。

### layout

- 类型: `string`
- 默认值: `Layout`

设置当前页面的布局组件。

### permalink

- 类型: `string`
- 默认值: `siteConfig.permalink`

参考: [Permalinks](./permalinks.md).

### metaTitle

- 类型: `string`
- 默认值: <code>\`${page.title} | ${siteConfig.title}\`</code>

重写默认的 meta title。

### meta

- 类型: `array`
- 默认值: `undefined`

指定额外的要注入的 meta 标签：

``` yaml
---
meta:
  - name: description
    content: hello
  - name: keywords
    content: super duper SEO
---
```

## 默认主题的预定义变量

### navbar

- 类型: `boolean`
- 默认值: `undefined`

参考: [默认主题配置 > 禁用导航栏](../theme/default-theme-config.md#禁用导航栏)。

### sidebar<Badge type="tip" text="不支持" vertical="top" />

- 类型: `boolean|'auto'`
- 默认值: `undefined`

参考: [默认主题配置 > 侧边栏](../theme/default-theme-config.md#侧边栏)。

### prev<Badge type="tip" text="不支持" vertical="top" />

- 类型: `boolean|string`
- 默认值: `undefined`

参考: [默认主题配置 > 上 / 下一篇链接](../theme/default-theme-config.md#上-下一篇链接)。

### next<Badge type="tip" text="不支持" vertical="top" />

- 类型: `boolean|string`
- 默认值: `undefined`

参考: [默认主题配置 > 上 / 下一篇链接](../theme/default-theme-config.md#上-下一篇链接)。

### search

- 类型: `boolean`
- 默认值: `undefined`

参考: [默认主题配置 > 内置搜索](../theme/default-theme-config.md#内置搜索)。

### tags

- 类型: `array`
- 默认值: `undefined`



