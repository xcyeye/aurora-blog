set -e

cd docs/.vuepress/dist
echo 'theme-test.cco.vin' > CNAME

git init
git add -A
git commit -m 'deploy'

git push -f https://github.com/qsyyke/theme-test.git master:gh-pages

cd -