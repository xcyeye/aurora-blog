# 生成静态文件
npm run build

# 进入生成的文件夹
cd docs/.vuepress/dist

# echo 'theme-test.cco.vin' > CNAME

git init
git add -A
git commit -m 'deploy'

# 如果发布到 https://<USERNAME>.github.io
git push -f https://gitee.com/qsyyk/gitee-theme-test.git master:gh-pages
#git push -f https://gitee.com/qsyyk/gitee-theme-test.git master:gh-pages

cd -