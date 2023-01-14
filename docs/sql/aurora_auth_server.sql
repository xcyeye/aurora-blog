/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : aurora_auth_server

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 05/08/2022 18:03:07
*/
use aurora_auth_server;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for au_login_info
-- ----------------------------
DROP TABLE IF EXISTS `au_login_info`;
CREATE TABLE `au_login_info` (
  `uid` bigint NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录的用户名',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录地',
  `login_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录ip地址',
  `operation_system_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录的操作系统',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` tinyint NOT NULL COMMENT '登录的状态 1：登录成功',
  `message` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录消息，记录登录异常等信息',
  PRIMARY KEY (`uid`),
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `username_index` (`username`) USING BTREE,
  KEY `union_login_index` (`uid`,`username`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_login_info
-- ----------------------------
BEGIN;
INSERT INTO `au_login_info` (`uid`, `username`, `login_location`, `login_ip`, `operation_system_info`, `create_time`, `update_time`, `status`, `message`) VALUES (1528735221686607872, 'aurora', '云南省保山市隆阳区', '106.58.204.31', 'Mac OS X;Chrome 10 version: 101.0.4951.54', '2022-05-23 21:50:54', '2022-05-23 21:50:55', 1, '从缓存加载数据，登录成功');
INSERT INTO `au_login_info` (`uid`, `username`, `login_location`, `login_ip`, `operation_system_info`, `create_time`, `update_time`, `status`, `message`) VALUES (1547594573843406848, 'aurora', '云南省昭通市', '183.225.67.72', 'Mac OS X;Chrome 10 version: 103.0.0.0', '2022-07-14 22:51:16', '2022-07-14 22:51:35', 0, 'aurora不存在');
INSERT INTO `au_login_info` (`uid`, `username`, `login_location`, `login_ip`, `operation_system_info`, `create_time`, `update_time`, `status`, `message`) VALUES (1547594632878235648, 'aurora', '云南省昭通市', '183.225.67.72', 'Mac OS X;Chrome 10 version: 103.0.0.0', '2022-07-14 22:51:28', '2022-07-14 22:51:35', 0, 'aurora不存在');
INSERT INTO `au_login_info` (`uid`, `username`, `login_location`, `login_ip`, `operation_system_info`, `create_time`, `update_time`, `status`, `message`) VALUES (1547594853418934272, 'aurora', '保山', '127.0.0.1', 'Mac OS X;Chrome 10 version: 103.0.0.0', '2022-07-14 22:52:20', NULL, 0, NULL);
INSERT INTO `au_login_info` (`uid`, `username`, `login_location`, `login_ip`, `operation_system_info`, `create_time`, `update_time`, `status`, `message`) VALUES (1550131412991090688, 'aurora', '云南省昭通市昭阳区', '183.225.219.215', 'Mac OS X;Chrome 10 version: 103.0.0.0', '2022-07-21 22:51:43', '2022-07-21 22:51:56', 0, 'aurora不存在');
INSERT INTO `au_login_info` (`uid`, `username`, `login_location`, `login_ip`, `operation_system_info`, `create_time`, `update_time`, `status`, `message`) VALUES (1551918071776681984, 'aurora', '保山', '127.0.0.1', 'Unknown;Unknown version: null', '2022-07-26 21:11:16', NULL, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端id',
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资源的id，多个用逗号分隔',
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '客户端的秘钥',
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '客户端的权限，多个用逗号分隔',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权类型，五种，多个用逗号分隔',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权码模式的跳转uri',
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限，多个用逗号分隔',
  `access_token_validity` int DEFAULT NULL COMMENT 'access_token的过期时间，单位毫秒，覆盖掉硬编码',
  `refresh_token_validity` int DEFAULT NULL COMMENT 'refresh_token的过期时间，单位毫秒，覆盖掉硬编码',
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展字段，JSON',
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '默认false，是否自动授权',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`, `create_time`, `update_time`) VALUES ('myjszl', 'res1', '$2a$10$8FQjxYiaI6phmJ1SrayR4eSQ8uhQtEN92ogtdzxxf7ivvFHfaHz6O', 'all', 'authorization_code,client_credentials,implicit,refresh_token,password', 'http://www.baidu.com', NULL, NULL, NULL, NULL, 'false', '2022-06-25 11:28:10', NULL);
COMMIT;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `authentication` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
