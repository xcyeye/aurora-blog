# !/bin/bash
cd ../
echo "正在打包aurora-theme模块"
mvn clean install

echo "正在打包aurora-base模块"
cd ./aurora-base
clean install

echo "正在打包support模块"
cd ../support
mvn clean install

echo "正在打包aurora-starter模块"
cd ../aurora-starter
mvn clean install

echo "正在打包service模块"
cd ../service
mvn clean install

echo "正在打包common-api模块"
cd ../common-api
mvn clean install

echo "正在打包aurora-modules模块"
cd ../aurora-modules
mvn clean install

echo "开始编译项目"
cd ../aurora-starter
mvn clean compile

cd ../support
mvn clean compile

cd ../service
mvn clean compile

cd ../common-api
mvn clean compile

cd ../aurora-modules
mvn clean compile