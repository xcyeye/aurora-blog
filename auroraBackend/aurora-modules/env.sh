#!/bin/bash

echo "Log file save path:  $PROJECT_LOG_STORAGE_PATH"
echo "Active ENV: $ACTIVE_ENV"
echo "Executing Jar Version $ACTIVE_VERSION"
echo "The following are all runtime environments"
# 遍历所有环境变量并筛选
for var in $(env | cut -d= -f1); do
  # 如果变量以指定的前缀开头，并且不包含"PASSWORD"
  if [[ $var == AURORA* || $var == NACOS* || $var == MYSQL* || $var == REDIS* || $var == RABBITMQ* ]] && [[ $var != *PASSWORD* ]]; then
    echo "$var=${!var}"
  fi
done