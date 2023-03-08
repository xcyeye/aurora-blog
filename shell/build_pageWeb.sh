# !/bin/bash

mkdir -p /opt/aurora-blog-system-web/pageWeb
mkdir -p /opt/aurora-blog-system-web/adminWeb
mkdir -p /opt/aurora-blog-system-web/backend
echo "正在打包pageWeb模块"
cd ../auroraPageWeb
npm run build-prod
echo "正在移动pageWeb"
rsync -a /opt/aurora-blog/auroraPageWeb/dist /opt/aurora-blog-system-web/pageWeb