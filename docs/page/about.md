# 关于页面

## 社交

![image-20211011163020328](https://ooszy.cco.vin/img/blog-note/image-20211011163020328.png?x-oss-process=style/pictureProcess1)

这个需要在`socials`进行设置，设置和首页一样



```js
module.exports = {

    themeConfig: {
		socials: []
    }
}
```



## 关于列表

关于列表是一个一个的

![image-20210831194455529](http://ooszy.cco.vin/img/blog-note/image-20210831194455529.png?x-oss-process=style/pictureProcess1)

![image-20210831194509894](http://ooszy.cco.vin/img/blog-note/image-20210831194509894.png?x-oss-process=style/pictureProcess1)



在`readme.md`中，通过`about`项进行更改，`about`是一个数组对象，此对象格式有两种形式

```js
{
    bar: false,
    title: '',
    describe: [],
    tag: [],
    showTag: true
},
{
    bar: true,
    title: '技  能',
    describe: [
      {
        name: 'java',
        score: 70
      },
    ],
    showTag: false
},
```

| 对象名   | 值            | 描述                                                     |
| -------- | ------------- | -------------------------------------------------------- |
| bar      | true or false | true表示describe数组，以条形的形式展示，否则以li方式显示 |
| title    | String        | 此列表的标题，如我?，技能...                             |
| describe | 数组          | 描述                                                     |
| tag      | 数组          | 此个人说明项的标签                                       |
| showTag  | true or false | true开启tag,false不会显示tag                             |
| name     | String        | 只有bar为true时有效，每个bar的标签                       |
| score    | 数组(0-100)   | 只有bar为true时有效，每个bar的占比                       |





### 全部配置

```js
module.exports = {

    themeConfig: {
        about: [
            {
                bar: false,
                title: "我?",
                describe: [
                    "目前是一名大三学生,CS专业,坐标西南边陲",
                    "喜欢安静,不喜社交",
                    "喜欢听音乐,什么类型都可",
                    "喜欢技术,coding",
                    "目前正在学习java后端",
                    '最喜欢的电影是"忠犬八公的故事",梦想以后独居也能有一只"Hachi"',
                    "主题是我自己开发的，前端太菜了，如果有bug，希望大家多多包涵`Σ(￣□￣||)` ",
                    "有点懒",
                    "目前除了编程没有什么兴趣爱好",
                    "正在追的番有《百妖谱》,《致不灭的你》,《少年歌行》,《鬼灭之刃》,《关于我转生变成史莱姆这档事》...",
                    "喜欢小说，喜欢的作者是--十月流年，最喜欢的小说《至尊修罗》,《一念永恒》,《星辰变》",
                ],
                tag: [
                    "coding",
                    "社恐",
                    "番剧",
                    "电影",
                    "安静",
                    "音乐",
                    "小说",
                    "宅",
                    "懒",
                ],
                showTag: true,
            },
            {
                bar: false,
                title: "大三规划",
                describe: [
                    "做项目",
                    "软考二级",
                    "英语四级",
                    "想找实习",
                    "复习数据结构",
                    "驾照",
                    "做点什么有意义的事",
                ],
                tag: ["大家加油呀`Σ(￣□￣||)` ..."],
                showTag: true,
            },
            {
                bar: false,
                title: "未来规划",
                describe: ["后端工程师", "赚money"],
                tag: ["忘记过去，展望未来"],
                showTag: true,
            },
            {
                bar: true,
                title: "技  能",
                describe: [
                    {
                        name: "java",
                        score: 70,
                    },
                    {
                        name: "HTML5",
                        score: 87,
                    },
                    {
                        name: "javascript",
                        score: 82,
                    },
                    {
                        name: "css",
                        score: 73,
                    },
                    {
                        name: "python",
                        score: 50,
                    },
                    {
                        name: "redis",
                        score: 59,
                    },
                    {
                        name: "mysql",
                        score: 82,
                    },
                    {
                        name: "vue",
                        score: 60,
                    },
                    {
                        name: "spring",
                        score: 71,
                    },
                    {
                        name: "springMVC",
                        score: 77,
                    },
                    {
                        name: "springBoot",
                        score: 71,
                    },
                ],
                showTag: false,
            },

            {
                showTag: false,
                bar: true,
                title: "掌握框架",
                describe: [
                    {
                        name: "spring",
                        score: 79,
                    },
                    {
                        name: "springMVC",
                        score: 81,
                    },
                    {
                        name: "springBoot",
                        score: 82,
                    },
                    {
                        name: "mybatis",
                        score: 82,
                    },
                    {
                        name: "vue",
                        score: 60,
                    },
                    {
                        name: "dubbo",
                        score: 61,
                    },
                ],
            },
            {
                bar: true,
                showTag: false,
                title: "掌握技能",
                describe: [
                    {
                        name: "jetbrains",
                        score: 75,
                    },
                    {
                        name: "linux",
                        score: 68,
                    },
                    {
                        name: "git",
                        score: 78,
                    },
                    {
                        name: "Ctrl C V",
                        score: 100,
                    },
                ],
            },
            {
                title: "关于主题",
                describe: [
                    "theme-ccds主题是我自己独立开发，是一款基于vuepress，对默认主题进行了大量修改，我以前使用的是wordpress的博客主题，但是我对PHP不了解，想改成自己想要的主题，太难了，最近在学vue，正好看到可以使用vuepress来搭建 博客，就将默认主题改成现在这个样，并且喜欢vuepress的最主要原因是，它可以 直接将本地的markdown文档进行编译部署，我原来博客，我记了几个月的笔记，不太想再慢慢从本地复制到WordPress进行发布，以至于博客几个月没有发布文章了，虽然有技术可以解决，但还是喜欢vuepress，简直是懒癌福音，如果你喜欢的话，可以在我的GitHub进行下载，使用文档可以查看https://theme-ccds.cco.vin，该主题已将所有的配置进行抽离，你现在看到的所有信息，都抽离在了一个配置文件中，但是目前还并不能做到开箱即用，如果使用的人多了，我可以进行修改，并且我自己也写了几个组件，包括文章页面看到的顶部图片，友情链接，海报分享功能等等 ，可以在你想使用的地方，直接使用这些组件就可以，该主题我加入了vuex，对前端不太了解，有很多css不对的地方，请大家多多包涵，Thanks♪(･ω･)ﾉ",
                ],
            },
            {
                bar: false,
                title: "更新日志",
                describe: [
                    "开发永不止步......",
                    "2021.9.12 增加相册功能，解决已知bug",
                    "2021.9.9 增加海报分享功能",
                    "2021.9.5 重新修改文章SEO自动配置，使用新规则，解决从tag页面进入文章页面，懒加载失效问题",
                    "2021.9.4 重新初始化仓库",
                    "2.21.8.10 -- 2021.9 主题开发",
                ],
                tag: ['想求个star`(⌒▽⌒)`'],
                showTag: true,
            },
            {
                title: "主题后续计划及闲话",
                bar: false,
                tag: [],
                showTag: false,
                describe: [
                    "为说说页面增加随时发布，随时修改功能",
                    "增加每日计划打卡功能",
                ],
            },
        ],
    }
}
```



## 标签

![image-20210831195631982](http://ooszy.cco.vin/img/blog-note/image-20210831195631982.png?x-oss-process=style/pictureProcess1)

颜色不支持更改，随机，从`randomColor`中随机选择



## bar

![image-20210831195817530](http://ooszy.cco.vin/img/blog-note/image-20210831195817530.png?x-oss-process=style/pictureProcess1)



##  友情链接页面配置

[友情链接](./friendlink.md)