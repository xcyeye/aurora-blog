---
stick: true
title: Nodejs详细安装教程
---





# Aurora主题详细使用教程

> 该文章会从零开始教你使用<a href="https://v2.vuepress.vuejs.org/zh/" target="_blank">Vuepress</a>搭建自己的博客网站,如果你会git和node的使用，那就更容易了
>
> 从node，Git安装，到创建vuepress站点
>
> 如果此篇文章对你有帮助，那么可以帮我分享给更多的人么

[[toc]]



## node安装

### 下载node

<a href="https://www.aliyundrive.com/s/2E9D8FW2mWq" target="_blank">点击下载</a>

点击上方链接，下载node，或者你也可以到官方网站进行下载

[Download | Node.js (nodejs.org)](https://nodejs.org/en/download/)

![image-20211013112000619](https://ooszy.cco.vin/img/blog-note/image-20211013112000619.png?x-oss-process=style/pictureProcess1)



当你下载完成之后，你会得到如下文件

![image-20211013112130043](https://ooszy.cco.vin/img/blog-note/image-20211013112130043.png?x-oss-process=style/pictureProcess1)



### 安装过程化

1. 双击点击安装

   ![image-20211013112222351](https://ooszy.cco.vin/img/blog-note/image-20211013112222351.png?x-oss-process=style/pictureProcess1)



2. 选择一个安装目录

   ![image-20211013132648644](https://ooszy.cco.vin/img/blog-note/image-20211013132648644.png?x-oss-process=style/pictureProcess1)

   这里的安装目录可以随便选择，`但是一定要记得此路径，在后面需要使用到`

3. 点击next

   ![image-20211128222436169](https://ooszy.cco.vin/img/blog-note/image-20211128222436169.png?x-oss-process=style/pictureProcess1)

   ::: warning

   这里一定要选择`Add to PATH`，一定要记得选择`Add to PATH`，一定要记得选择`Add to PATH`

   :::

   
   
   这里还是点击next
   
   ![image-20211013132911956](https://ooszy.cco.vin/img/blog-note/image-20211013132911956.png?x-oss-process=style/pictureProcess1)
   
   ![image-20211013132930873](https://ooszy.cco.vin/img/blog-note/image-20211013132930873.png?x-oss-process=style/pictureProcess1)
   
   ![image-20211013133002992](https://ooszy.cco.vin/img/blog-note/image-20211013133002992.png?x-oss-process=style/pictureProcess1)



​		然后等待安装完成之后，就可以了



### 设置环境和变量

::: tip

因为之前选择了`App to PATH`，所以我们值需要简单设置一下就行

:::

#### 验证

上一步安装完成之后，按住`win r`键，输入`cmd`

![image-20211013133147151](https://ooszy.cco.vin/img/blog-note/image-20211013133147151.png?x-oss-process=style/pictureProcess1)



在面板中，输入`node -v`验证是否安装成功

![image-20211013133235630](https://ooszy.cco.vin/img/blog-note/image-20211013133235630.png?x-oss-process=style/pictureProcess1)

当出现`v14.17.3`时，表示node已经安装成功了，此值也有可能不同，取决于你下的node版本



#### 设置

1. 进入上一步安装node的目录中，你将看到如下文件

   ![image-20211013133600368](https://ooszy.cco.vin/img/blog-note/image-20211013133600368.png?x-oss-process=style/pictureProcess1)

2. 在此目录中，新建`node_cache`和`node_global`两个文件夹

   ![image-20211013133711581](https://ooszy.cco.vin/img/blog-note/image-20211013133711581.png?x-oss-process=style/pictureProcess1)



3. 在`cmd`面板中，运行下面两个命令，一个一个的运行

   ```sh
   npm config set prefix "D:\Develop\nodejs\node_global"
   npm config set cache "D:\Develop\nodejs\node_cache"
   ```

   ::: tip

   将后面的路径改成你自己对应文件夹就行

   :::



4. 然后再运行下面这个命令

   ```sh
   npm config set registry=http://registry.npm.taobao.org/ 
   ```

   使用淘宝镜像，不然下载速度非常慢

5. 设置环境变量

   ![image-20211013134927339](https://ooszy.cco.vin/img/blog-note/image-20211013134927339.png?x-oss-process=style/pictureProcess1)

   

   

   ![image-20211013134955551](https://ooszy.cco.vin/img/blog-note/image-20211013134955551.png?x-oss-process=style/pictureProcess1)
   
   ![image-20211013135021613](https://ooszy.cco.vin/img/blog-note/image-20211013135021613.png?x-oss-process=style/pictureProcess1)
   
   

![image-20211128225456194](https://ooszy.cco.vin/img/blog-note/image-20211128225456194.png?x-oss-process=style/pictureProcess1)

![image-20211128225939601](https://ooszy.cco.vin/img/blog-note/image-20211128225939601.png?x-oss-process=style/pictureProcess1)



设置好之后，点击确定就行了

### 验证

现在验证一下，刚才安装的是否正确

打开cmd面板，运行下面命令

```sh
npm root-g
```

![image-20211128230349713](https://ooszy.cco.vin/img/blog-note/image-20211128230349713.png?x-oss-process=style/pictureProcess1)



如果输入命令之后，输出的`node_modules`前面的路径就是你刚才创建的`node_global`的路径，那么就表示你设置成功

### 验证脚手架

::: tip

一定要先验证`aurora`命令是否可用，否则安装主题会有点麻烦

:::



在`cmd`中输入下面命令安装Aurora脚手架

```sh
npm i vuepress-theme-cli -g
```



待下载成功之后，在控制台中输入`aurora blog`，然后按回车

![image-20211128231157929](https://ooszy.cco.vin/img/blog-note/image-20211128231157929.png?x-oss-process=style/pictureProcess1)



如果提示下图

::: warning 

```sh
'aurora' 不是内部或外部命令，也不是可运行的程序
或批处理文件。
```

:::



![image-20211128231224969](https://ooszy.cco.vin/img/blog-note/image-20211128231224969.png?x-oss-process=style/pictureProcess1)



进入到环境变量中，新建一个，输入[创建的node_global路径](#设置)

![image-20211128231434520](https://ooszy.cco.vin/img/blog-note/image-20211128231434520.png?x-oss-process=style/pictureProcess1)





::: tip

正常情况，你输入`aurora blog`之后，便会下载一个模板

![image-20211128231837831](https://ooszy.cco.vin/img/blog-note/image-20211128231837831.png?x-oss-process=style/pictureProcess1)

:::



然后现在你可以了

根据面板中的指示，运行相应命令，就可以启动博客了

