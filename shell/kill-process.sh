# !/bin/bash
boot_names=(article-boot admin-boot auth-server-boot comment-boot file-boot gateway-boot message-boot)
version_name=1.0-SNAPSHOT.jar
for element in ${boot_names[@]}
do
  jar_name=${element}-${version_name}
  echo "正在杀死 ${jar_name} 进程"
  process_pid=`ps -elf |grep ${jar_name}|grep -v grep |head -n 1 |awk '{printf $4}'`
  echo ${process_pid}

  if [[ -n $process_pid ]];
  then
    echo "${jar_name} 的进程pid为 ${process_pid}"
    kill -9 ${process_pid}
  fi
done