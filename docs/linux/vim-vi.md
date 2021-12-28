---
date: 2021/12/16 19:16
stick: true
title: linux中vim和vi的区别和使用及快捷键
---



下面笔记并没有记录所有的vim快捷键以及使用教程，只是记录，我自己容易忘记的

![image-20211216191730434](https://ooszy.cco.vin/img/blog-note/image-20211216191730434.png?x-oss-process=style/pictureProcess1)



::: tip

Vim 具有程序编辑的能力，可以看做是 Vi 的增强版本，可以主动的以字体颜色辨别语法的正确性，方便程序设计。 

代码补完、编译及错误跳转等方便编程的功能特别丰富，在程序员中被广泛使用

:::



## 三种模式

![image-20211216191925559](https://ooszy.cco.vin/img/blog-note/image-20211216191925559.png?x-oss-process=style/pictureProcess1)



在`vim`中，一共有三种模式，对于一些快捷键需要在特定的模式下才可以进行

### 一般模式

![image-20211216192138962](https://ooszy.cco.vin/img/blog-note/image-20211216192138962.png?x-oss-process=style/pictureProcess1)

> 这种就是一般模式，在一般模式下，我们可以使用上下左右方向键，使用`yy,dd,p,u`等命令



### 编辑模式

![image-20211216192242224](https://ooszy.cco.vin/img/blog-note/image-20211216192242224.png?x-oss-process=style/pictureProcess1)





### 命令行模式

![image-20211216192313047](https://ooszy.cco.vin/img/blog-note/image-20211216192313047.png?x-oss-process=style/pictureProcess1)



## 常用快捷键

`对于下面的这些快捷键和命令，都不能在编辑模式下使用，在编辑模式下，直接是插入`

1. yy

   > 拷贝当前行yy , 拷贝当前行向下的 5 行 5yy，并粘贴（输入 p）。

2. dd

   > 删除当前行 dd , 删除当前行向下的 5 行 5dd

3. 在文件中查找某个单词 [命令行下 /关键字 ， 回车 查找 , 输入 n 就是查找下一个 ]

   ![image-20211216192801235](https://ooszy.cco.vin/img/blog-note/image-20211216192801235.png?x-oss-process=style/pictureProcess1)

4. 设置文件的行号，取消文件的行号.[命令行下 : set nu 和 :set nonu] 

   ![image-20211216192836832](https://ooszy.cco.vin/img/blog-note/image-20211216192836832.png?x-oss-process=style/pictureProcess1)

5. 在一般模式下, 使用快捷键到该文档的最末行[G]和最首行[gg] 
6. 在一般模式下, 然后又撤销这个动作 u ，就只需要输入这个u字符就行
7. ，在一般模式下, 并将光标移动到 , 输入 20,再输入 shift+g `shift g`都是键盘上的按键



![image-20211216193012380](https://ooszy.cco.vin/img/blog-note/image-20211216193012380.png?x-oss-process=style/pictureProcess1)