# Copyright 1999-2019 Seata.io Group.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# the Dockerfile support x86 & arrch64
# build：
# 1. mvn -Prelease-seata -Dmaven.test.skip=true clean install -U
# 2. cd distribution/target/seata-server-xxx/seata/
# 3. docker build --no-cache --build-arg SEATA_VERSION=1.6.0-SNAPSHOT -t seata-server:1.6.0-dev .
#
# run:
# 1. docker run --name=seata-server -d seata-server:1.6.0-dev
#
# https://hub.docker.com/orgs/seataio
FROM openjdk:8u342

# set label
LABEL maintainer="Seata <seata.io>"

# set environment
ENV SEATA_DOWNLOAD_URL="https://qiniu-cdn.xcye.xyz/seata-server-1.7.0.tar.gz"

RUN mkdir -p /seata-server \
    && curl -SL $SEATA_DOWNLOAD_URL -o /seata-server/seata.tar.gz \
    && tar -xzf /seata-server/seata.tar.gz -C /seata-server --strip-components=1 \
    && rm -rf /seata-server/conf/* /seata-server/seata.tar.gz

WORKDIR /$BASE_DIR

# set extra environment
ENV LOADER_PATH="/seata-server/lib"
CMD ["bash","-c","/seata-server/bin/seata-server.sh && tail -f /dev/null"]
