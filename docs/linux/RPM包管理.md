---
date: 2021/12/20 15:05
tag: [linux,rpm,yum]
title: Linux的rpm包管理和yum使用
categories: [rpm,linux]
---



::: tip

`rpm`用于互联网下载包的打包及安装工具，它包含在某些 Linux 分发版中。它生成具有.RPM 扩展名的文件。RPM是RedHat Package Manager（RedHat 软件包管理工具）的缩写，类似 windows 的 setup.exe，这一文件格式名称虽然打上了RedHat 的标志，但理念是通用的。 

Linux 的分发版本都有采用（suse,redhat, centos 等等），可以算是公认的行业标准了

:::



## rpm

```sh
rpm –qa [ | grep xxx] # 查询已安装程序列表

[root@qsyyke ~]# rpm -qa | grep firefox
firefox-60.2.2-1.el7.centos.x86_64
```

> 安装包分析
>
> firefox-60.2.2-1.el7.centos.x86_64
>
> `firefox` : 安装程序的名字
>
> `60.2.2-1` : 安装的firefox的版本号
>
> `el7.centos.x86_64` : 使用的操作系统，如果是通用，也就是32位和64位都可以使用的话，那这里就是`noarch`



### 常用命令

- rpm -qa

  查询所有的安装程序

- rpm -q

  查询软件包是否安装

  ```sh
  rpm -q firefox # 如果以及安装，会返回此安装程序信息
  ```

- rpm -qi

  查询软件包信息

  ::: details 点击查看

  ```sh
  [root@qsyyke ~]# rpm -qi firefox
  Name        : firefox
  Version     : 60.2.2
  Release     : 1.el7.centos
  Architecture: x86_64
  Install Date: 2021年12月16日 星期四 15时25分58秒
  Group       : Unspecified
  Size        : 216144933
  License     : MPLv1.1 or GPLv2+ or LGPLv2+
  Signature   : RSA/SHA256, 2018年10月09日 星期二 20时51分59秒, Key ID 24c6a8a7f4a80eb5
  Source RPM  : firefox-60.2.2-1.el7.centos.src.rpm
  Build Date  : 2018年10月09日 星期二 08时33分46秒
  Build Host  : x86-01.bsys.centos.org
  Relocations : (not relocatable)
  Packager    : CentOS BuildSystem <http://bugs.centos.org>
  Vendor      : CentOS
  URL         : https://www.mozilla.org/firefox/
  Summary     : Mozilla Firefox Web browser
  Description :
  Mozilla Firefox is an open-source web browser, designed for standards
  compliance, performance and portability.
  ```
  
  :::

- rpm -ql

  查询软件包中的文件

  ::: details 点击查看
  
  ```sh
  [root@qsyyke ~]# rpm -ql firefox
  /etc/firefox
  /etc/firefox/pref
  /usr/bin/firefox
  /usr/lib64/firefox
  /usr/lib64/firefox/LICENSE
  /usr/lib64/firefox/application.ini
  /usr/lib64/firefox/browser/blocklist.xml
  /usr/lib64/firefox/browser/chrome
  /usr/lib64/firefox/browser/chrome.manifest
  /usr/lib64/firefox/browser/chrome/icons
  /usr/lib64/firefox/browser/chrome/icons/default
  ```
  
  在这里面我们可以看到此`firefox`程序的一些配置文件等路径
  
  :::
  
- rpm -qf

  查询某个文件属于那个安装程序的

  ```sh
  [root@qsyyke ~]# rpm -qf /etc/passwd
  setup-2.8.71-10.el7.noarch
  ```

  



### 卸载rpm程序

```sh
rpm -e RPM 包的名称
```

::: warning

因为可能我们要删除的这个rpm包是另一个rpm包的依赖，所以我们卸载的时候，可能会报错，这个时候，可以增加`--nodeps`选项，就可以强制卸载某个rpm包

:::



### 安装rpm包

```sh
rpm -ivh RPM包全路径名称
```



> 参数说明
>
> `i`: install 安装 
>
> `v`: verbose 提示 
>
> `h`: hash 进度条



## yum

::: tip

`Yum`是一个 Shell 前端软件包管理器。基于 RPM 包管理，能够从指定的服务器自动下载 RPM 包并且安装，可以自动处理依赖性关系，并且一次安装所有依赖的软件包。

:::





```sh
yum list|grep xx 软件列表 #查询 yum 服务器是否有需要安装的软件

yum install xxx # 下载安装


```

