# Vuepress-plugin-coze

## introduce

这是Vuepress2的一个插件，该插件提供在线发布，修改说说功能，支持文件上传，登录注册，点赞，评论等功能，使用简单，只需在[leancloud](https://console.leancloud.app/)中获取到正确的`appId`,`appKey`便可以在任何地方使用



## 安装

>  因为该插件是基于Vuepress2的，所以在使用该插件之前，请确保你使用的版本为`Vuepress2`

```sh
npm install vuepress-plugin-coze
```



```js
//docs/.vuepress/config.js
module.exports = {
    plugins: [
        [
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
        ]

    ],
};
```



> 运行你的vuepress应用，待启动完成之后，分别访问`/aurora-register`和`/aurora-coze`进行用户注册和说说展示，上面的[appId配置](#秘钥获取)



## 截图

- 说说展示(`此效果在我主题中`)

![image-20211108190928615](https://ooszy.cco.vin/img/blog-note/image-20211108190928615.png?x-oss-process=style/pictureProcess1)



- 发布说说界面

![image-20211108191227820](https://ooszy.cco.vin/img/blog-note/image-20211108191227820.png?x-oss-process=style/pictureProcess1)



- 提供的注册界面

  > 此界面是该插件自动提供的，并且插件也提供了一个没有任何样式的全局组件，你可以自己进行css编写

![image-20211108191311008](https://ooszy.cco.vin/img/blog-note/image-20211108191311008.png?x-oss-process=style/pictureProcess1)



- 点赞，评论，编写

  > 因为时间问题，目前说说评论暂未实现

![image-20211108191613515](https://ooszy.cco.vin/img/blog-note/image-20211108191613515.png?x-oss-process=style/pictureProcess1)





如果你在使用该插件及[Aurora主题](https://aurora.xcye.xyz)的过程中，有任何的问题，都可以通过以下方式联系我

| QQ                                                           | 主题交流群                                                   | 微信                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233620332.png?x-oss-process=style/pictureProcess1" style="zoom:33%;" /> | <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233827133.png?x-oss-process=style/pictureProcess1" alt="image-20211024233827133" style="zoom:33%;" /> | <img src="https://ooszy.cco.vin/img/blog-note/image-20211024233735110.png?x-oss-process=style/pictureProcess1" alt="image-20211024233735110" style="zoom: 39%;" /> |



## 用户注册

### 秘钥获取

该插件数据存储于`leancloud`,进入[leanCloud国际版](https://console.leancloud.app/)(`一定要使用国际版，国内版域名为console.leancloud.cn我没测试过`)，你如果没有`leanCloud`账户的话，需要先创建一个有效账户

![image-20211108192136530](https://ooszy.cco.vin/img/blog-note/image-20211108192136530.png?x-oss-process=style/pictureProcess1)

![image-20211108192241757](https://ooszy.cco.vin/img/blog-note/image-20211108192241757.png?x-oss-process=style/pictureProcess1)

![image-20211108192330231](https://ooszy.cco.vin/img/blog-note/image-20211108192330231.png?x-oss-process=style/pictureProcess1)

![image-20211108192405575](https://ooszy.cco.vin/img/blog-note/image-20211108192405575.png?x-oss-process=style/pictureProcess1)

![image-20211108192519797](https://ooszy.cco.vin/img/blog-note/image-20211108192519797.png?x-oss-process=style/pictureProcess1)

![image-20211108192639202](https://ooszy.cco.vin/img/blog-note/image-20211108192639202.png?x-oss-process=style/pictureProcess1)





### 注册

| 路由             | 描述               |
| ---------------- | ------------------ |
| /aurora-register | 注册用户，登出操作 |
| /aurora-coze     | 说说默认展示页面   |

> `/aurora-register`是默认注册用户，登出操作页面的路由，如果你在插件配置中设置了`registerPath`值，那么注册，登出的路由将是`registerPath`对应值


现在访问`/aurora-register`路由，注册用户(`第一个注册的用户，默认为管理员`)，你将看到下面页面

![image-20211108193700352](https://ooszy.cco.vin/img/blog-note/image-20211108193700352.png?x-oss-process=style/pictureProcess1)

输入正确信息点击注册

![image-20211108193812105](https://ooszy.cco.vin/img/blog-note/image-20211108193812105.png?x-oss-process=style/pictureProcess1)



现在访问`/aurora-coze`页面

![image-20211108193856216](https://ooszy.cco.vin/img/blog-note/image-20211108193856216.png?x-oss-process=style/pictureProcess1)



恭喜你，设置成功



## 主题中展示说说

如果你设置成功，那么便可以将`CozeMood`组件放在你主题中的任何位置，这是插件注册的全局组件

```html
<div>
	<CozeMood @coze-success="cozeSuccess">
</div>
```



## 插件配置信息

[插件配置信息](https://aurora.xcye.xyz/plugin/coze/config.html)