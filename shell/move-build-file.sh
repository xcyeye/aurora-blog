# !/bin/bash
mkdir -p /opt/aurora-blog-system-web/pageWeb
mkdir -p /opt/aurora-blog-system-web/adminWeb
mkdir -p /opt/aurora-blog-system-web/backend

echo "正在移动pageWeb"
mv -f /opt/aurora-blog-system/auroraPageWeb/dist /opt/aurora-blog-system-web/pageWeb
echo "正在移动adminWeb"
mv -f /opt/aurora-blog-system/auroraAdminWeb/dist /opt/aurora-blog-system-web/adminWeb

echo "正在移动后端服务"
mv -f /opt/aurora-blog-system/auroraBackend/aurora-modules/admin-boot/target/admin-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend
mv -f /opt/aurora-blog-system/auroraBackend/aurora-modules/article-boot/target/article-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend
mv -f /opt/aurora-blog-system/auroraBackend/aurora-modules/auth-server-boot/target/auth-server-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend
mv -f /opt/aurora-blog-system/auroraBackend/aurora-modules/comment-boot/target/comment-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend
mv -f /opt/aurora-blog-system/auroraBackend/aurora-modules/file-boot/target/file-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend
mv -f /opt/aurora-blog-system/auroraBackend/aurora-modules/gateway-boot/target/gateway-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend
mv -f /opt/aurora-blog-system/auroraBackend/aurora-modules/message-boot/target/message-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend