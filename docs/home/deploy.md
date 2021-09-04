# 部署

对于如何部署，可以到<a href="https://vuepress.vuejs.org/zh/guide/deploy.html">vuepress官网</a>查看更详细说明，以下是使用GitHub Pages进行部署



## GitHub Pages

1. 在 `docs/.vuepress/config.js` 中设置正确的 `base`。

    如果你打算发布到 `https://<USERNAME>.github.io/`，则可以省略这一步，因为 `base` 默认即是 `"/"`。

    如果你打算发布到 `https://<USERNAME>.github.io/<REPO>/`（也就是说你的仓库在 `https://github.com/<USERNAME>/<REPO>`），则将 `base` 设置为 `"/<REPO>/"`。

2. 在你的项目中，创建一个如下的 `deploy.sh` 文件（请自行判断去掉高亮行的注释）

    ```sh
    #!/usr/bin/env sh
    
    # 确保脚本抛出遇到的错误
    set -e
    
    # 生成静态文件
    npm run docs:build
    
    # 进入生成的文件夹
    cd docs/.vuepress/dist
    
    # 如果是发布到自定义域名
    # echo 'www.example.com' > CNAME
    
    git init
    git add -A
    git commit -m 'deploy'
    
    # 如果发布到 https://<USERNAME>.github.io
    # git push -f git@github.com:<USERNAME>/<USERNAME>.github.io.git master
    
    # 如果发布到 https://<USERNAME>.github.io/<REPO>
    # git push -f git@github.com:<USERNAME>/<REPO>.git master:gh-pages
    
    cd -
    ```

    下面这个是使用自定义域名进行部署的`deploy.sh`文件

    ```sh
    set -e
    
    cd docs/.vuepress/dist
    echo 'blog.cco.vin' > CNAME
    
    git init
    git add -A
    git commit -m 'deploy'
    
    git push -f https://github.com/qsyyke/blog.git master:gh-pages
    
    cd -
    ```



### 步骤

1. 进入github，新建一个仓库，用于托管`npm run build`打包之后的HTML文件

    ![image-20210831133913906](http://ooszy.cco.vin/img/blog-note/image-20210831133913906.png?x-oss-process=style/pictureProcess1)

    将`docs/.vuepress/dist`下的所有文件使用git上传到该仓库中

2. 进入settings-->pages

    进行第二步之前，表示你已经将dist目录中的文件，已上传到此仓库中

    ![image-20210831134813016](http://ooszy.cco.vin/img/blog-note/image-20210831134813016.png?x-oss-process=style/pictureProcess1)

    ![image-20210831134956617](http://ooszy.cco.vin/img/blog-note/image-20210831134956617.png?x-oss-process=style/pictureProcess1)

    然后你就可以点击上面的那个连接，进行访问了，现在就已经将打包后的静态文件部署到github page



## 部署到自定义域名

部署到自定义域名也是一样的方法，只是需要在github的settings-->pages中，添加自定义域名就可以



### 步骤

1. 到阿里云，腾讯云，或其他服务商处，申请一个域名，服务器，如果使用国内服务器，域名需备案

2. ![image-20210831135358064](http://ooszy.cco.vin/img/blog-note/image-20210831135358064.png?x-oss-process=style/pictureProcess1)

3. 如果出现

    ![image-20210831135435268](http://ooszy.cco.vin/img/blog-note/image-20210831135435268.png?x-oss-process=style/pictureProcess1)

    就表示，该`newblog.cco.vin`域名并没有添加一个CNAME解析，进入到域名解析处

    ![image-20210831135743656](http://ooszy.cco.vin/img/blog-note/image-20210831135743656.png?x-oss-process=style/pictureProcess1)

    对newblog添加`CNAME`记录，指向`<user>.github.io.`就可以了