/*
 Navicat Premium Data Transfer

 Source Server         : 天翼云
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : 182.44.32.182:3306
 Source Schema         : aurora_auth_server

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 12/03/2023 21:09:31
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
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录ip地址',
  `operation_system_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录的操作系统',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` tinyint NOT NULL COMMENT '登录的状态 1：登录成功',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录消息，记录登录异常等信息',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `username_index`(`username` ASC) USING BTREE,
  INDEX `union_login_index`(`uid` ASC, `username` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_login_info
-- ----------------------------
INSERT INTO `au_login_info` VALUES (1634902515327836160, 'auroraDemo', '中国云南省保山市', '106.58.204.208', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-03-12 21:01:49', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1634902640246792192, 'xcyeye', '中国云南省保山市', '106.58.204.208', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-03-12 21:02:19', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1634902767254511616, 'auroraDemo', '中国云南省保山市', '106.58.204.208', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-03-12 21:02:49', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1634903071278637056, 'auroraDemo', '中国云南省保山市', '106.58.204.208', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-03-12 21:04:01', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1634903363193806848, 'auroraDemo', '中国云南省保山市', '106.58.204.208', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-03-12 21:05:11', NULL, 1, NULL);

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
INSERT INTO `oauth_client_details` VALUES ('auroraDemo', NULL, '$2a$10$p8IfLT13u1sN8xt3/UKikO89mAg904PZ5XHEs2m42oyy/TNrHytpG', 'all', 'authorization_code,client_credentials,refresh_token,password', NULL, NULL, NULL, NULL, NULL, NULL, '2023-03-12 21:01:48', NULL);
INSERT INTO `oauth_client_details` VALUES ('xcyeye', NULL, '$2a$10$/PQyuzJHQaTRQnc8pG0XU.d7oEtwdg/cHDn36WfRUIp6m/rOfeECC', 'all', 'authorization_code,client_credentials,refresh_token,password', NULL, NULL, NULL, NULL, NULL, NULL, '2023-03-12 19:20:52', NULL);

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authentication` bigint NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
