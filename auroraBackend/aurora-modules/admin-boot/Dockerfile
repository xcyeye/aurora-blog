FROM openjdk:11.0.16-jre-slim

LABEL maintainer="Aurora-Blog"

ARG RUN_DIR=/home/aurora-blog

RUN mkdir -p $RUN_DIR

EXPOSE $AURORA_ADMIN_SERVER_PORT
EXPOSE 50001

ENV TZ=Asia/Shanghai
ENV AURORA_ADMIN_SERVER_PORT=8088
ENV JAVA_OPTS="-Xms128m -Xmx256m -Dfile.encoding=UTF-8 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:50001"

COPY target/admin-boot-1.0-SNAPSHOT.jar ${RUN_DIR}/app.jar

WORKDIR ${RUN_DIR}

CMD echo server_port $AURORA_ADMIN_SERVER_PORT; echo nacos_username: $NACOS_USERNAME; echo nacos_host $NACOS_HOST; echo nacos_port $NACOS_PORT; echo nacos_group $NACOS_GROUP; echo nacos_namespace $NACOS_NAMESPACE; java ${JAVA_OPTS} -jar app.jar --server.port=$AURORA_ADMIN_SERVER_PORT