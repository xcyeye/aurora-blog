FROM openjdk:11.0.16-jre-slim

LABEL maintainer="Aurora-Blog"

ARG RUN_DIR=/home/aurora-blog

RUN mkdir -p $RUN_DIR

EXPOSE 8088
EXPOSE 50001

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms128m -Xmx256m -Dfile.encoding=UTF-8 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:50001"

COPY target/comment-boot-1.0-SNAPSHOT.jar ${RUN_DIR}/app.jar

WORKDIR ${RUN_DIR}

CMD sleep 60;java ${JAVA_OPTS} -jar app.jar --server.port=8088