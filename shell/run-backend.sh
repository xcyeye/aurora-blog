# !/bin/bash
cd /opt/aurora-blog-system-web/backend

nohup java -jar article-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms200m -Xmx300m &
nohup java -jar admin-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms200m -Xmx300m &
nohup java -jar auth-server-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms200m -Xmx250m &
nohup java -jar comment-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms200m -Xmx250m &
nohup java -jar file-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms200m -Xmx300m &
nohup java -jar gateway-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms200m -Xmx300m &
nohup java -jar message-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms200m -Xmx300m &