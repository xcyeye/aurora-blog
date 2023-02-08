# !/bin/bash
cd /opt/aurora-blog-system-web/backend

java -jar article-boot-1.0-SNAPSHOT.jar -Xms200m -Xmx300m
java -jar aurora-admin-1.0-SNAPSHOT.jar -Xms200m -Xmx300m
java -jar auth-server-boot-1.0-SNAPSHOT.jar -Xms200m -Xmx250m
java -jar comment-boot-1.0-SNAPSHOT.jar -Xms200m -Xmx250m
java -jar file-boot-1.0-SNAPSHOT.jar -Xms200m -Xmx300m
java -jar gateway-boot-1.0-SNAPSHOT.jar -Xms200m -Xmx300m
java -jar message-boot-1.0-SNAPSHOT.jar -Xms200m -Xmx300m