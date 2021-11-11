# Coze组件使用

> 以下所有的组件都是全局组件

## CozeMood

该组件是展示说说的组件，也就是下面这个页面

![image-20211108194504444](https://ooszy.cco.vin/img/blog-note/image-20211108194504444.png?x-oss-process=style/pictureProcess1)



### 钩子

该组件当所有数据加载完成之后，会返回一个钩子函数`cozeSuccess`，你可以在你自己的主题中，使用该函数

```js
//Mood.vue
<CozeMood @coze-success="cozeSuccess"></CozeMood>

methods: {
    cozeSuccess(cozeMoodData) {
        //数据加载完成之后返回
    }
}
```





### 插槽

该组件也提供了一些插槽

```js
<slot name="coze-mood-content"></slot>
<slot name="coze-img"></slot>
<slot name="coze-mood-bottom-left"></slot>
<slot name="coze-mood-bottom-right"></slot>
```

对应位置如下

![image-20211108202006814](https://ooszy.cco.vin/img/blog-note/image-20211108202006814.png?x-oss-process=style/pictureProcess1)





### 样式变量

如果你需要设置背景颜色等等，提供了css变量，请自行使用



## CozeLogin

该组件是一个注册，登出操作组件，不提供任何的样式，就是一个空壳，提供的目的是，你如果需要让用户登录，发布说说等，可以为用户提供注册等操作，我没有提供样式，是因为什么样式也没有，更便于你们设置其css样式



> 该组件展示效果

![image-20211110234327520](https://ooszy.cco.vin/img/blog-note/image-20211110234327520.png?x-oss-process=style/pictureProcess1)



- 结构

```html
<!--这是一个登录注册的组件-->
<div class="coze-custom-login">
    <slot name="cozeCustomTop"></slot>
    <form v-on:submit.prevent>
        <div class="coze-custom-item-common">
            <input autocomplete type="text" placeholder="请输入用户名" v-model="username" name="username">
        </div>
        <div class="coze-custom-item-common">
            <input autocomplete placeholder="输入6到20位包含数字,字母密码" v-model="password" name="password" type="password">
        </div>

        <div class="coze-custom-item-common">
            <input autocomplete placeholder="请输入邮箱 登录不需要此邮箱" v-model="email" name="email" type="text">
        </div>

        <slot name="cozeCustomCenter"></slot>
        <div class="coze-custom-item-common coze-custom-button coze-custom-register">
            <div @click="verifyIdentify">注册</div>
        </div>
        <div class="coze-custom-item-common coze-custom-button coze-custom-login">
            <div @click="loginIn">登录</div>
        </div>
        <div class="coze-custom-item-common coze-custom-button coze-custom-out">
            <div @click="loginOut">登出</div>
        </div>
    </form>
    <slot name="cozeCustomBottom"></slot>
</div>
```



### 钩子

提供的钩子有



| 名称         | 数据                 | 描述                      |
| ------------ | -------------------- | ------------------------- |
| cozeLoginOut | 返回数据             | 点击登出按钮返回登出状态  |
| cozeLogin    | 返回用户登录相关信息 | 点击登录                  |
| cozeRegister | 返回用户注册数据     | 在leanCloud中，注册新用户 |



- 使用案例

```js
<CozeLogin @coze-login-out="cozeLoginOut" @coze-login="cozeLogin"></CozeLogin>

methods: {
    cozeLoginOut(data) {
      console.log("点击登出按钮")
      console.log(data)
    },
    cozeLogin(data) {
      console.log("点击登录按钮")
      console.log(data)
    },
    cozeRegister(data) {
      console.log("点击注册按钮")
      console.log(data)
    }
  }
```



![image-20211108203333154](https://ooszy.cco.vin/img/blog-note/image-20211108203333154.png?x-oss-process=style/pictureProcess1)



> 你可以根据返回的数据，进行额外的一些操作



> - registerStatus
>
>   > 为0，表示注册失败，反之
>
> - status
>
>   > 为1，表示成功退出登录
>
> - administrator
>
>   > 为0，表示不是管理员，反之





### 插槽

```html
<slot name="cozeCustomTop"></slot>
<slot name="cozeCustomBottom"></slot>
```





## RegisterUser

该组件也是一个全局组件，插件提供的默认注册组件，未提供任何钩子等



![image-20211108203909235](https://ooszy.cco.vin/img/blog-note/image-20211108203909235.png?x-oss-process=style/pictureProcess1)



## 样式

[样式设置](./style.md)





## CozePhoto

该组件是展示相册的一个全局组件，该组件的展示效果如下图

![image-20211111000148676](https://ooszy.cco.vin/img/blog-note/image-20211111000148676.png?x-oss-process=style/pictureProcess1)

该组件会展示所有用户发布在说说上，并且设置为显示的图片

::: tip

后续会加上，不是管理员只显示当前用户对应的图片，管理员显示所有

:::

> 该组件没有提供任何的插槽等



## 样式配置

[样式配置](./style.md)