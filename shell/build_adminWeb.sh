# !/bin/bash

mkdir -p /opt/aurora-blog-system-web/pageWeb
mkdir -p /opt/aurora-blog-system-web/adminWeb
mkdir -p /opt/aurora-blog-system-web/backend
echo "正在打包adminWeb模块"
cd ../auroraAdminWeb
pnpm build-prod
echo "正在移动adminWeb"
rsync -a /opt/aurora-blog-system/auroraAdminWeb/dist /opt/aurora-blog-system-web/adminWeb