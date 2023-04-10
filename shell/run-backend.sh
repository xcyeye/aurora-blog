# !/bin/bash
cd /opt/aurora-blog-system-web/backend

nohup java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:50001 admin-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms150m -Xmx250m &
nohup java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:50002 article-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms150m -Xmx250m &
nohup java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:50003 auth-server-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms150m -Xmx200m &
nohup java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:50004 comment-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms150m -Xmx200m &
nohup java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:50005 file-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms150m -Xmx250m &
nohup java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:50006 gateway-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms200m -Xmx300m &
nohup java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:50007 message-boot-1.0-SNAPSHOT.jar --spring.profiles.active=prod -Xms150m -Xmx250m &