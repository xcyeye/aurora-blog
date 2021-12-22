---
date: "2021/12/8 16:58"
title: 插件使用教程
tag: [插件,插件使用教程]
---

在vuepress中，插件是在配置文件(`docs/.vuepress/config.js`)中进行配置的，你也可以参照[官方](https://v2.vuepress.vuejs.org/zh/guide/plugin.html#%E7%A4%BE%E5%8C%BA%E6%8F%92%E4%BB%B6)的教程

```js
module.exports = {
    plugins: [
        //.....在这里使用插件
        [
            "插件1",{
                //插件1的配置，具体请自己看每个插件的配置
            }
        ],
        [
            "插件2",{
                //插件2的配置，具体请自己看每个插件的配置
            }
        ],
        [
            "插件3",{
                //插件3的配置，具体请自己看每个插件的配置
            }
        ],
        [
            "插件4",{
                //插件4的配置，具体请自己看每个插件的配置
            }
        ],
        [
            "插件5",{
                //插件5的配置，具体请自己看每个插件的配置
            }
        ],
    ]
  }
```



::: tip

`plugins`这个是一个数组，这是一个数组，这是一个数组，你如果需要在你博客中，加入额外的插件，请在此数组中加入，格式就像上面那样

:::



## 插件使用步骤

比如现在有一个插件，名字和配置项如下，那么其使用步骤为(`此插件并不存在，别加，此插件并不存在，别加，此插件并不存在，别加，，只是演示`)

```json
name: "aurora-plugin",
配置项: {
    github: "https://github.com/vuepress-aurora/vuepress-theme-aurora",
    author: "qsyyke"
}
```



### 1.查看`config.js`文件，是否有`plugins`项

#### config.js文件中，存在`plugins`

> 没加入`aurora-plugin`插件之前的配置文件内容

```js
const { themeConfig } = require("./themeConfig");
module.exports = {
    plugins: [
        [
            '@vuepress/plugin-search',
            {
                locales: {
                    '/': {
                        placeholder: 'Search',
                    },
                    '/zh/': {
                        placeholder: '搜索',
                    },
                },
            }
        ],
    ],
    theme: 'aurora',
    title: '一款简洁.美观.功能强大的vuepress主题',
    themeConfig: {
        //.....
    }
}
```

> 如果你的`config.js`文件中，像上面一样，存在`plugins`项了，那么你就不用再写一个`plugins`
>
> 你的`config.js`文件，不一定和我的一样，不一定和我的一样，不一定和我的一样，不一定和我的一样



那么现在你要加入`aurora-plugin`这个插件，最终的`config.js`文件，就如下

```js
const { themeConfig } = require("./themeConfig");
module.exports = {
    plugins: [
        [
            'aurora-plugin',{
                github: "https://github.com/vuepress-aurora/vuepress-theme-aurora",
                author: "qsyyke"
            }
        ],
        [
            '@vuepress/plugin-search',
            {
                locales: {
                    '/': {
                        placeholder: 'Search',
                    },
                    '/zh/': {
                        placeholder: '搜索',
                    },
                },
            }
        ],
    ],
    theme: 'aurora',
    title: '一款简洁.美观.功能强大的vuepress主题',
    themeConfig: {
        //.....
    }
}
```



#### config.js文件中，不存在`plugins`

如果你的config.js文件中，不存在plugins，那么就表示，你重来没有在博客中，使用过任何的插件，那么现在你就需要自己写`plugins`

> 没加入插件之前的内容

```js
const { themeConfig } = require("./themeConfig");
module.exports = {
    theme: 'aurora',
    title: '一款简洁.美观.功能强大的vuepress主题',
    themeConfig: {
        //.....
    }
}
```



> 加入插件之后的内容

```js
const { themeConfig } = require("./themeConfig");
module.exports = {
    plugins: [
        [
            'aurora-plugin',{
                github: "https://github.com/vuepress-aurora/vuepress-theme-aurora",
                author: "qsyyke"
            }
        ]
    ],
    theme: 'aurora',
    title: '一款简洁.美观.功能强大的vuepress主题',
    themeConfig: {
        //.....
    }
}
```



### 2.下载需要的插件

官方也提供了好几款插件，如果需要的话，请[自行配置](https://v2.vuepress.vuejs.org/zh/reference/plugin/search.html)

![image-20211208170414756](https://ooszy.cco.vin/img/blog-note/image-20211208170414756.png?x-oss-process=style/pictureProcess1)



这里使用我自己的几款插件举例使用

或者你也可以直接到[Npm](https://www.npmjs.com/)中搜索对应的vuepress插件，请注意，需要保证此插件能够在`vuepress2`版本中使用，因为此主题是`vuepress2`版本的，一版本的插件，并不能正常使用

### 例如使用官方search插件

此插件[地址](https://v2.vuepress.vuejs.org/zh/reference/plugin/search.html#%E5%AE%89%E8%A3%85)

1. 定位到依赖安装(`在命令行窗口中，执行npm i xxx这个命令`)

   ![image-20211212154319106](https://ooszy.cco.vin/img/blog-note/image-20211212154319106.png?x-oss-process=style/pictureProcess1)

2. 查看插件使用

   ![image-20211212154444401](https://ooszy.cco.vin/img/blog-note/image-20211212154444401.png?x-oss-process=style/pictureProcess1)

3. 使用

::: details 错误使用

```js
const { themeConfig } = require("./themeConfig");
module.exports = {
    plugins: [
        [
            'archive',
            {
                //需要排除的页面url，在该数组里面的路径，都不会被统计
                excludes: ['/404.html','/about/','/mood/','/link/','/tag/','/photo/'],
                //当某篇文章没有标题时，将使用下面值进行替换
                noTitle: '暂时没有标题配置'
            }
        ],
    ],
    theme: 'aurora',
    title: '一款简洁.美观.功能强大的vuepress主题',
    themeConfig: {
        //.....
    }
}

/*
下面这个不是这样加的
*/
module.exports = {
  plugins: [
    [
      '@vuepress/plugin-search',
      {
        locales: {
          '/': {
            placeholder: 'Search',
          },
          '/zh/': {
            placeholder: '搜索',
          },
        },
      },
    ],
  ],
}

```

> 我说的是加入到对应位置，并不是直接复制，然后粘贴到config.js文件里面，对应对应，对应位置

![image-20211212154854325](https://ooszy.cco.vin/img/blog-note/image-20211212154854325.png?x-oss-process=style/pictureProcess1)

:::



::: details 正确使用

```js
const { themeConfig } = require("./themeConfig");
module.exports = {
    plugins: [
        [
            'archive',
            {
                //需要排除的页面url，在该数组里面的路径，都不会被统计
                excludes: ['/404.html','/about/','/mood/','/link/','/tag/','/photo/'],
                //当某篇文章没有标题时，将使用下面值进行替换
                noTitle: '暂时没有标题配置'
            }
        ],
        [
      '@vuepress/plugin-search',
      {
        locales: {
          '/': {
            placeholder: 'Search',
          },
          '/zh/': {
            placeholder: '搜索',
          },
        },
      },
    ],
    ],
    theme: 'aurora',
    title: '一款简洁.美观.功能强大的vuepress主题',
    themeConfig: {
        //.....
    }
}
```



:::



## 时间轴

[链接](https://aurora.xcye.xyz/plugin/archive)

1. 安装插件

   ```sh
   npm i vuepress-plugin-archive
   ```

2. 使用插件

   ```js
   //docs/.vuepress/config.js
   
   const { path } = require("@vuepress/utils");
   
   module.exports = {
       //在这里配置插件
       plugins: [
           [
               'archive',
               {
                   //需要排除的页面url，在该数组里面的路径，都不会被统计
                   excludes: ['/footer.html','/404.html','/about/','/mood/','/link/','/tag/','/photo/'],
                   //当某篇文章没有标题时，将使用下面值进行替换
                   noTitle: '暂时没有标题配置'
               }
           ],
   }
   ```

   

## 说说

1. 安装

   ```sh
   npm install vuepress-plugin-coze
   ```

2. 使用

   ```sh
   //docs/.vuepress/config.js
   module.exports = {
       plugins: [
            'vuepress-plugin-coze',
           {
               appId: 'leanCloud中得到的appId',
               appKey: 'leanCloud中得到的appKey',
               masterKey: 'leanCloud中得到的masterKey',
               //下面这些是可选的
               avatarPath: 'https://ooszy.cco.vin/img/blog-note/avatar-aurora.png',//说说头像url
               registerPath: '/aurora-register', //自定义插件默认提供的注册页面路由，请在前面加上/
               onlyAdministrator: false //是否运行其他注册的用户发布说说，true表示只有管理员可以发布
   
           }
       ],
   };
   ```

   