set -e

cd docs/.vuepress/dist
echo 'blog.cco.vin' > CNAME

git init
git add -A
git commit -m 'deploy'

git push -f https://github.com/qsyyke/blog.git master:gh-pages

cd -