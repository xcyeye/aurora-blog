# !/bin/bash
mkdir -p /opt/aurora-blog-system-web/pageWeb
mkdir -p /opt/aurora-blog-system-web/adminWeb
mkdir -p /opt/aurora-blog-system-web/backend

cd ../auroraBackend/aurora-modules/admin-boot
mvn clean package
rsync -a /opt/aurora-blog-system/auroraBackend/aurora-modules/admin-boot/target/admin-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend

cd ../article-boot
mvn clean package
rsync -a /opt/aurora-blog-system/auroraBackend/aurora-modules/article-boot/target/article-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend

cd ../gateway-boot
mvn clean package
rsync -a /opt/aurora-blog-system/auroraBackend/aurora-modules/gateway-boot/target/gateway-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend

cd ../comment-boot
mvn clean package
rsync -a /opt/aurora-blog-system/auroraBackend/aurora-modules/comment-boot/target/comment-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend

cd ../file-boot
mvn clean package
rsync -a /opt/aurora-blog-system/auroraBackend/aurora-modules/file-boot/target/file-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend

cd ../message-boot
mvn clean package
rsync -a /opt/aurora-blog-system/auroraBackend/aurora-modules/message-boot/target/message-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend

cd ../auth-server-boot
mvn clean package
rsync -a /opt/aurora-blog-system/auroraBackend/aurora-modules/auth-server-boot/target/auth-server-boot-1.0-SNAPSHOT.jar /opt/aurora-blog-system-web/backend