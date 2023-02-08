# !/bin/bash
cd ../auroraBackend/aurora-modules/admin-boot
mvn clean package

cd ../article-boot
mvn clean package

cd ../gateway-boot
mvn clean package

cd ../comment-boot
mvn clean package

cd ../file-boot
mvn clean package

cd ../message-boot
mvn clean package

cd ../auth-server-boot
mvn clean package