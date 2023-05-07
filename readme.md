

### 站点

- [前台](https://blog.xcye.xyz/)

- [后台](https://admin.xcye.xyz/login?redirect=/dashboard/analysis)

  演示账号：用户名: demoAurora 密码:Aurora2023@*

## 介绍

![](https://img1.imgtp.com/2023/03/27/mMvUAT0A.jpg)

### 后端使用到的技术栈有：

- Java11
- Spring Cloud Alibaba
- RabbitMq
- Seata
- Oauth2

### 前端使用到的技术栈有：

- Vue3
- TypeScript
- Pinia
- NaiveUi


## 截图

### 首页
![](https://img1.imgtp.com/2023/03/27/8izucK3o.png)
![](https://img1.imgtp.com/2023/03/27/lxlE8Jrx.png)
![](https://img1.imgtp.com/2023/03/27/elzrLUvf.png)
### 友情链接
![](https://img1.imgtp.com/2023/03/27/pH6oiFnr.png)
![](https://img1.imgtp.com/2023/03/27/4dIz84Jo.png)

### 相册
![](https://img1.imgtp.com/2023/03/27/mNqTm95W.png)
### 说说
![](https://img1.imgtp.com/2023/03/27/jHTkL682.png)
![](https://img1.imgtp.com/2023/03/27/UfYjjJ7P.png)

### 时间轴
![](https://img1.imgtp.com/2023/03/27/kjWGqIxq.png)
### 分类和类别
![](https://img1.imgtp.com/2023/03/27/TpKqd21N.png)



### 文章页 
![](https://img1.imgtp.com/2023/03/27/JXwHUyRZ.png)
![](https://img1.imgtp.com/2023/03/27/88BBr1ip.png)
![](https://img1.imgtp.com/2023/03/27/Tzt19hNH.png)

### 邮件通知
![](https://img1.imgtp.com/2023/03/27/i337Uzzj.jpg)

### 后台

![](https://img1.imgtp.com/2023/03/27/qArF1pzY.png)
![](https://img1.imgtp.com/2023/03/27/n3vbKGZq.png)
![](https://img1.imgtp.com/2023/03/27/47uJRURJ.png)

## 目录结构





## 搭建

此系统的前端和后端所需的环境如下，我使用的操作系统为**Centos8**，以下安装教程都是基于此发行版来安装的，如果你使用的是其他的发行版，或者Centos7，下面的教程如果不能装的话，那么你需要自己在搜索引擎里面去搜索对应的安装教程，这种安装的变化，主要是针对于MySQL，RabbitMQ，对于Nacos，Seata，Redis，JDK，Node是完全一样的，因为后面这几个都是基于压缩包进行安装

> 后端：
>
> - Nacos   **1.4.4**
> - RabbitMQ   **3.11.10**
> - JDK  **>=11**
> - Seata   **1.4.2**
> - MySQL   **>=8**
> - Redis **运行使用的是7.0.9，对版本要求不大，你可以先使用你自己的试试**

> - Node **>=18**
> - Npm **>=9**
> - Pnpm **>=7**

### MySQl安装

> 如果你已经安装过MySQL，并且版本`>=8`，可以跳过此步骤

1. 运行

   ```sh
   sudo dnf install @mysql
   ```

2. 启动MySQL

   ```sh
   sudo systemctl start mysqld.service
   # 可以通过systemctl status mysqld.service查看启动状态
   ```

3. 运行初始化脚本

   ```sh
   sudo mysql_secure_installation
   ```

   ![image-20230428084714587](https://cdn.xcye.xyz/blog-upload/png/2023/4/image-20230428084714587.png)
   ![image-20230428084847330](https://cdn.xcye.xyz/blog-upload/png/2023/4/image-20230428084847330.png)

   > 我在设置密码的时候，设置的密码为`Aurora2023@*`

4. 登录MySQL

   ```sh
   mysql -u root -p 
   use mysql;
   ```

   ```mysql
   mysql> select Host,User from user where User='root';
   +-----------+------+
   | Host      | User |
   +-----------+------+
   | localhost | root |
   +-----------+------+
   ```

   > 如果你是root用户，并且Host这里不是`%`的话，那么就不能使用远程登录MySQL，需要将root用户的Host修改为`%`
   >
   > ```mysql
   > update user set Host='%' where User='root';
   > ```
   > 

5. 重新启动MySQL

   ```sh
   sudo systemctl restart mysqld # 重新启动
   sudo systemctl enable --now mysqld # 设置开机启动
   ```

#### 导入MySQL数据

我这里使用的MySQL远程链接工具为Naivcat

1. 在Naivcat中链接你服务器上的MySQL服务

2. 进入项目的`docs/mysql/v2`目录

   ![image-20230428091428904](https://cdn.xcye.xyz/blog-upload/png/2023/4/image-20230428091428904.png)

   1. 先运行`create_database.sql`创建该项目所需要的数据库
   2. 然后再在每个数据库下面，运行对应的`docs/mysql/v2/table-data`表sql
   3. 运行`docs/mysql/v2/sql-update.sql`


### Redis安装

1. 进入linux，使用wget下载压缩包

   ```sh
   wget https://file.xcye.xyz/7.0.11.tar.gz
   tar -zxvf 7.0.11.tar.gz
   # 解压之后进入redis目录
   make install # 如果你没有安装make，需要先安装
   ```

2. 启动redis，因为我已经配置过`redis.conf`，所以配置文件不需要怎么修改，直接启动就行了

   ```sh
   # cd redis-7.0.11
   redis-server ./redis.conf 
   # 此配置文件默认使用的端口为35680，如果你的操作系统开启了防火墙，需要对这个端口放行
   ```

### JDK安装





### RabbitMQ安装

```
https://www.cnblogs.com/andreamwu/p/14723420.html
```

### Nacos安装



### Node安装

```sh
wget https://nodejs.org/dist/v18.16.0/node-v18.16.0-linux-x64.tar.xz

tar -xf node-v18.16.0-linux-x64.tar.xz

npm install vite -g

npm i --legacy-peer-deps
pnpm i --ignore-scripts puppeteer
```



### Maven安装

```sh
wget https://dlcdn.apache.org/maven/maven-3/3.9.1/binaries/apache-maven-3.9.1-bin.tar.gz
```



### Git安装

```sh
git config core.fileMode false
```



### Nginx安装

```sh
```

