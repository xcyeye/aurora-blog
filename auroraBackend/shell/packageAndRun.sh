# !/bin/bash
cd ../aurora-modules/admin-boot
mvn clean package
cd ../aurora-article
mvn clean package
cd ../aurora-gateway
mvn clean package
cd ../comment-boot
mvn clean package
cd ../file-boot
mvn clean package

cd ../message-boot
mvn clean package

cd ../aurora-auth-server
mvn clean package