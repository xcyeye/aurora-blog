---
tag: [music,player,播放器,plugin]
categories: [plugin]
---



# vuepress-plugin-player

::: tip

这个vuepress2的一个播放器插件，能够在你网站中播放音乐，目前除了支持本地音乐之外，还支持网易云平台的音乐，你可以播放网易云平台中的歌单，或者是某一首或者几首单曲



> 如果你需要播放网易云平台的歌曲，因为有些歌曲是需要会员才能够播放的，所以你如果需要播放某一个歌单(`假设此歌单含有100首音乐`)或者某几首歌，那么最终可能只能播放20,30,40...首?，因为所有歌曲的链接都是通过请求官方api获取的，所以对于那些需要会员或者付费的歌曲，并不能获取到此歌曲的mp3地址，也就播放不了(`但是你可以将这首歌下载到本地，然后使用本地歌曲就可以了`)



> 如果你需要播放网易云歌单中的歌曲，需要登录才能够播放

:::



## 使用

```sh
npm i vuepress-plugin-player
```



```js
//docs/.vuepress/config.js

const { path } = require("@vuepress/utils");

module.exports = {
    //在这里配置插件
    plugins: [
        [
            'player',
            {
                //网易云单个歌单id
                songIds: ['29723011','1887893189','1421069053'],
                //网易云歌单
                playlist: '2410215112',
                showPlaylist: true,
                //是否禁用网易云音乐，如果你选择禁用，那么就将使用本地的歌曲，请传入链接
                disabledNetEaseMusic: true,

                //请求接口的baseURL
                serverUrl: 'http://localhost:3000',

                //本地歌曲
                localSongs: {
                    coverUrl: 'https://ooszy.cco.vin/img/blog-public/avatar.jpg',
                    songs: [
                        {
                            path: '/song/1.mp3',
                            songName: '12',
                            cover: 'https://p1.music.126.net/Rg1x9LeUacIDqtvUzL35Cw==/109951163688517312.jpg'
                        },
                        {
                            path: 'http://m7.music.126.net/20211120155029/f99e2fe5f557455fd37b7bfd0c0d6c3e/ymusic/545a/005e/025f/c03ab3077e74b9d50e07557d82ca472b.flac',
                            songName: '23',
                            cover: 'https://p2.music.126.net/8mnn6YiQldsRIHe_nER8wg==/109951162894925733.jpg'
                        },
                        {
                            path: '/song/3.mp3',
                            songName: '34'
                        },
                    ]
                }
            }
        ]
    ]
}
```



## 参数

### songIds

- Array

> 网易云歌曲的id，如
>
> ````
> songIds: ['29723011','1887893189','1421069053']
> ````



### playlist

- String

> 网易云歌单id，只有开启播放歌单才有效`showPlaylist: true`
>
> 如`playlist: '2410215112'`



### showPlaylist

- Boolean

> 是否开启播放的歌曲来源于网易云歌单，使用歌单中的歌曲和`songIds`配置的单曲，只能二选一，在[disabledNetEaseMusic](#disabledNetEaseMusic)开启情况下
>
> 如`showPlaylist: true`





### disabledNetEaseMusic

- Boolean

> 是否禁用网易云
>
> 如果你禁用，那么就会播放配置的本地歌曲，而不会使用网易云平台的歌曲，推荐使用这个选项，默认是关闭的，也就是使用网易云
>
> 如`disabledNetEaseMusic: true`



### serverUrl

- String

> 向网易云请求数据时的接口，默认使用我的，你也可以自己传入你的，详细使用，请看[serverUrl配置](./serverUrl.md)
>
> 如`serverUrl: 'https://netease-cloud-music-api-teal-psi.vercel.app/'`



### localSongs

- Object

> 本地歌曲配置



::: details 点击查看配置

```json
localSongs: {
    coverUrl: 'https://ooszy.cco.vin/img/blog-public/avatar.jpg',
    songs: [
        {
            path: '/song/1.mp3',
            songName: '12',
            cover: 'https://p1.music.126.net/Rg1x9LeUacIDqtvUzL35Cw==/109951163688517312.jpg'
        },
        {
            path: 'http://m7.music.126.net/20211120155029/f99e2fe5f557455fd37b7bfd0c0d6c3e/ymusic/545a/005e/025f/c03ab3077e74b9d50e07557d82ca472b.flac',
            songName: '23',
            cover: 'https://p2.music.126.net/8mnn6YiQldsRIHe_nER8wg==/109951162894925733.jpg'
        },
        {
            path: '/song/3.mp3',
            songName: '34'
        },
    ]
}
```

:::



### coverUrl

- String

> 所有的歌曲封面，如果你使用本地歌曲，对于一些歌曲或者你需要配置的本地歌曲很多，你不想单独的为每一首歌曲指定封面图，那么你可以配置此项，当那首歌没有配置封面的时候，就会使用该项配置的图片url作为封面图

::: details 点击查看总封面配置

```json
localSongs: {
    coverUrl: 'https://ooszy.cco.vin/img/blog-public/avatar.jpg',
}
```



:::



### songs

- Array

> 本地歌曲集合

::: details 点击查看本地歌曲配置

```js
songs: [
    {
        //放在docs/.vuepress/public/song目录里下的1.mp3文件
        path: '/song/1.mp3',
        songName: '12',
        cover: 'https://p1.music.126.net/Rg1x9LeUacIDqtvUzL35Cw==/109951163688517312.jpg'
    },
    {
        //使用网络上的mp3url
        path: 'http://m7.music.126.net/20211120155029/f99e2fe5f557455fd37b7bfd0c0d6c3e/ymusic/545a/005e/025f/c03ab3077e74b9d50e07557d82ca472b.flac',
        songName: '23',
        cover: 'https://p2.music.126.net/8mnn6YiQldsRIHe_nER8wg==/109951162894925733.jpg'
    },
	.............
]
```

:::



### path

- String

> 本地歌曲的路径url，`你也可以传入网络中某个mp3的url地址`，如果此歌曲你已经下载下来了，那么请放在`docs/.vuepress/public`静态目录中



### songName

- String

> 歌曲名称



### cover

- String

> 单独为这首歌配置封面图





[点击查看serverUrl配置](./serverUrl.md)