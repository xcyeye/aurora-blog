/*
 Navicat Premium Data Transfer

 Source Server         : 本地Linux
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : 192.168.158.120:3306
 Source Schema         : aurora_auth_server

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 15/01/2023 19:32:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for au_login_info
-- ----------------------------
DROP TABLE IF EXISTS `au_login_info`;
CREATE TABLE `au_login_info`  (
  `uid` bigint NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录的用户名',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录地',
  `login_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录ip地址',
  `operation_system_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录的操作系统',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` tinyint NOT NULL COMMENT '登录的状态 1：登录成功',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录消息，记录登录异常等信息',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `username_index`(`username` ASC) USING BTREE,
  INDEX `union_login_index`(`uid` ASC, `username` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_login_info
-- ----------------------------
INSERT INTO `au_login_info` VALUES (1547594573843406848, 'aurora', '云南省昭通市', '183.225.67.72', 'Mac OS X;Chrome 10 version: 103.0.0.0', '2022-07-14 22:51:16', '2022-07-14 22:51:35', 0, 'aurora不存在');
INSERT INTO `au_login_info` VALUES (1547594632878235648, 'aurora', '云南省昭通市', '183.225.67.72', 'Mac OS X;Chrome 10 version: 103.0.0.0', '2022-07-14 22:51:28', '2022-07-14 22:51:35', 0, 'aurora不存在');
INSERT INTO `au_login_info` VALUES (1547594853418934272, 'aurora', '保山', '127.0.0.1', 'Mac OS X;Chrome 10 version: 103.0.0.0', '2022-07-14 22:52:20', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1550131412991090688, 'aurora', '云南省昭通市昭阳区', '183.225.219.215', 'Mac OS X;Chrome 10 version: 103.0.0.0', '2022-07-21 22:51:43', '2022-07-21 22:51:56', 0, 'aurora不存在');
INSERT INTO `au_login_info` VALUES (1551918071776681984, 'aurora', '保山', '127.0.0.1', 'Unknown;Unknown version: null', '2022-07-26 21:11:16', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1589891632415776768, 'aurora', '云南省保山市', '39.144.147.93', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-11-08 16:04:38', '2022-11-08 16:04:45', 0, 'aurora不存在');
INSERT INTO `au_login_info` VALUES (1589891944019009536, 'aurora', '云南省保山市', '39.144.147.93', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-11-08 16:05:52', '2022-11-08 16:05:58', 0, 'aurora不存在');
INSERT INTO `au_login_info` VALUES (1589907580904415232, 'aurora', '保山', '127.0.0.1', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-11-08 17:08:01', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1599735080325095424, 'aurora', '保山', '127.0.0.1', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-12-05 19:58:59', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1599735087568658432, 'aurora', '保山', '127.0.0.1', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-12-05 19:59:01', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1599998169041739776, 'aurora', '保山', '127.0.0.1', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-12-06 13:24:24', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1600103173127806976, 'aurora', '保山', '127.0.0.1', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-12-06 20:21:39', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1602234808057077760, 'aurora', '保山', '127.0.0.1', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-12-12 17:32:01', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1602234809286008832, 'aurora', '保山', '127.0.0.1', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-12-12 17:32:01', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1603062500323106816, 'aurora', '保山', '127.0.0.1', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-12-15 00:20:58', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1604823333914943488, 'aurora', '保山', '127.0.0.1', 'Unknown;Unknown version: null', '2022-12-19 20:57:53', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1604836441156886528, 'aurora', '云南省昭通市昭阳区', '183.225.219.203', 'Unknown;Unknown version: null', '2022-12-19 21:49:58', '2022-12-19 21:49:59', 0, 'Bad credentials');
INSERT INTO `au_login_info` VALUES (1604837021333987328, 'aurora', '云南省昭通市昭阳区', '183.225.219.203', 'Unknown;Unknown version: null', '2022-12-19 21:52:16', '2022-12-19 21:52:18', 0, 'Bad credentials');
INSERT INTO `au_login_info` VALUES (1604837057056874496, 'aurora', '保山', '127.0.0.1', 'Unknown;Unknown version: null', '2022-12-19 21:52:25', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1605187122132819968, 'Admin', '云南省昭通市昭阳区', '183.225.219.203', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-12-20 21:03:28', '2022-12-20 21:03:33', 0, 'Admin不存在');
INSERT INTO `au_login_info` VALUES (1605187132673105920, 'User01', '云南省昭通市昭阳区', '183.225.219.203', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-12-20 21:03:30', '2022-12-20 21:03:33', 0, 'User01不存在');
INSERT INTO `au_login_info` VALUES (1605187165640335360, 'Admin', '云南省昭通市昭阳区', '183.225.219.203', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-12-20 21:03:37', '2022-12-20 21:03:38', 0, 'Admin不存在');
INSERT INTO `au_login_info` VALUES (1605187227393073152, 'User01', '云南省昭通市昭阳区', '183.225.219.203', 'Windows 10;Chrome 10 version: 106.0.0.0', '2022-12-20 21:03:52', '2022-12-20 21:03:52', 0, 'User01不存在');
INSERT INTO `au_login_info` VALUES (1614258023528800256, 'aurora', '云南省昭通市昭阳区', '183.225.67.140', 'Windows 10;Chrome 10 version: 106.0.0.0', '2023-01-14 21:47:59', '2023-01-14 21:48:02', 0, NULL);
INSERT INTO `au_login_info` VALUES (1614258209378410496, 'aurora', '云南省昭通市昭阳区', '183.225.67.140', 'Windows 10;Chrome 10 version: 106.0.0.0', '2023-01-14 21:48:43', '2023-01-14 21:48:43', 0, NULL);
INSERT INTO `au_login_info` VALUES (1614258513046020096, 'aurora', '保山', '127.0.0.1', 'Windows 10;Chrome 10 version: 106.0.0.0', '2023-01-14 21:49:55', NULL, 0, NULL);
INSERT INTO `au_login_info` VALUES (1614280256393453568, 'aurora', '保山', '127.0.0.1', 'Windows 10;Chrome 10 version: 106.0.0.0', '2023-01-14 23:16:20', NULL, 0, NULL);

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端id',
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源的id，多个用逗号分隔',
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端的秘钥',
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端的权限，多个用逗号分隔',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权类型，五种，多个用逗号分隔',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权码模式的跳转uri',
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限，多个用逗号分隔',
  `access_token_validity` int NULL DEFAULT NULL COMMENT 'access_token的过期时间，单位毫秒，覆盖掉硬编码',
  `refresh_token_validity` int NULL DEFAULT NULL COMMENT 'refresh_token的过期时间，单位毫秒，覆盖掉硬编码',
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段，JSON',
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认false，是否自动授权',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('myjszl', 'res1', '$2a$10$8FQjxYiaI6phmJ1SrayR4eSQ8uhQtEN92ogtdzxxf7ivvFHfaHz6O', 'all', 'authorization_code,client_credentials,implicit,refresh_token,password', 'http://www.baidu.com', NULL, NULL, NULL, NULL, 'false', '2022-06-25 11:28:10', '2022-12-20 13:58:17');

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authentication` bigint NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
