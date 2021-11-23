# 更改图标以及覆盖主题样式

在v.1.3.7之后，你可以自定义图标

::: tip

如果你想要修改的图标，在http://aurora-font.cco.vin/中可以找到，那么你可以直接修改，如果没有在http://aurora-font.cco.vin/中，那么你就需要想你平常使用图标那样，引入字体，样式，然后再使用

:::



## 使用预设图标

假设你想要修改置顶的图标

![image-20211021003525455](https://ooszy.cco.vin/img/blog-note/image-20211021003525455.png?x-oss-process=style/pictureProcess1)



- 你需要`F12`查看此图标得类名，主题所有预设的图标类名都是开头`aurora-`，上图置顶的图标类名为`aurora-stick`

- 在http://aurora-font.cco.vin/aurora.html站点中，找到你喜欢的图标，并记录下下面值

  <img src="https://ooszy.cco.vin/img/blog-note/image-20211021003913582.png?x-oss-process=style/pictureProcess1" alt="image-20211021003913582" style="zoom:33%;" />

- 进入到`docs/.vuepress/styles/index.css`文件中，使用下面的方式修改此图标

  ```css
  .aurora-stick:before {
      content: "\e60a";
  }
  ```

  ![image-20211021004102208](https://ooszy.cco.vin/img/blog-note/image-20211021004102208.png?x-oss-process=style/pictureProcess1)



::: tip

因为在`docs/.vuepress/styles/index.css`文件中的css样式，会添加到`head`元素的底部，所以此文件中的Css样式会覆盖默认主题提供的样式，所以你可以在此文件中，直接根据你的需要，把主题样式更改为你需要的样式，例如修改字体等等

:::



## 使用自己的图标

这里使用阿里巴巴图标库进行演示，只要是字体图标，都是一样的

- 挑选自己喜欢的图标，然后选择添加到库

  ![image-20211021075125699](https://ooszy.cco.vin/img/blog-note/image-20211021075125699.png?x-oss-process=style/pictureProcess1)

- 点击下载代码

  <img src="https://ooszy.cco.vin/img/blog-note/image-20211021075328055.png?x-oss-process=style/pictureProcess1" alt="image-20211021075328055" style="zoom:50%;" />

- 解压之后，你会看到下面这些文件

  ![image-20211021075421251](https://ooszy.cco.vin/img/blog-note/image-20211021075421251.png?x-oss-process=style/pictureProcess1)

- 将`iconfont.css`内的样式复制到`docs/.vuepress/styles/palette.css`文件中(`也可以复制到docs/.vuepress/styles/index.css`)中，`palette.css`中，主要是定义一些变量，你可以在`index.css`中，直接使用这些变量，然后修改字体的路径

- ```css
  :root {
      --test-color: pink;
  }
  
  @font-face {
      font-family: "iconfont"; /* Project id  */
      src: url('iconfont.ttf?t=1634773948652') format('truetype');
  }
  
  .iconfont {
      font-family: "iconfont" !important;
      font-size: 16px;
      font-style: normal;
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
  }
  
  .icon-zhiding:before {
      content: "\e516";
  }
  
  .icon-zhiding1:before {
      content: "\e607";
  }
  ```

- 在`index.css`样式内，便可以像使用预设图标那样使用了

  ```css
  .aurora-stick:before {
      font-family: "iconfont" !important;
      font-size: 16px;
      font-style: normal;
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
      content: "\e607";
  }
  ```

  ::: tip

  这里需要注意，如果你使用自己的图标，那么在`aurora-xxx:before{}`样式内，你需要指定字体的名称，还有`content`值，原理就是`index.css`内的样式，会直接覆盖主题默认的样式

  :::



- <img src="https://ooszy.cco.vin/img/blog-note/image-20211021080627557.png?x-oss-process=style/pictureProcess1" alt="image-20211021080627557" style="zoom:50%;" />





## 覆盖主题默认样式

如果你需要对一些样式进行更改，你可以直接在`index.css`中，直接修改样式，如果你需要使用一些变量，那么你可以在`docs/.vuepress/styles/palette.css`文件内进行定义，然后在`index.css`内，直接使用这些变量就可以了



## 主题特征配置

[主题特征配置](./feature/feature-config.md)