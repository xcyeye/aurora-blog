/*
 Navicat Premium Data Transfer

 Source Server         : 天翼云
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : 182.44.32.182:3306
 Source Schema         : aurora_email

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 12/03/2023 21:09:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for au_email
-- ----------------------------
DROP TABLE IF EXISTS `au_email`;
CREATE TABLE `au_email`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_uid` bigint NOT NULL COMMENT '此条记录和用户表中的某个用户对应',
  `email_host` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送者邮件的主机',
  `email_password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送者邮件的密码，或者授权码',
  `protocol` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送者的协议',
  `email` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱号',
  `port` int NULL DEFAULT NULL COMMENT '此邮件发送的端口',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `union_email_index`(`uid` ASC, `user_uid` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_email
-- ----------------------------

-- ----------------------------
-- Table structure for au_email_log
-- ----------------------------
DROP TABLE IF EXISTS `au_email_log`;
CREATE TABLE `au_email_log`  (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid',
  `user_uid` bigint NOT NULL,
  `sender` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送者的邮箱号',
  `subject` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `receiver` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接受者的邮箱号',
  `is_send` tinyint NOT NULL COMMENT '1:发送成功0：没有发送成功',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `sendEmail_index`(`sender` ASC) USING BTREE,
  INDEX `union_emailLog_index`(`uid` ASC, `sender` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1569 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_email_log
-- ----------------------------

-- ----------------------------
-- Table structure for au_mail_template
-- ----------------------------
DROP TABLE IF EXISTS `au_mail_template`;
CREATE TABLE `au_mail_template`  (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid',
  `template` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮件发送模板的html',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮件默认发送标题，如果没有传入的话',
  `user_uid` bigint NOT NULL COMMENT '此模板是哪个用户创建的',
  `type_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此模板是回复评论，还是收到评论等',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1016 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_mail_template
-- ----------------------------

-- ----------------------------
-- Table structure for au_message_log
-- ----------------------------
DROP TABLE IF EXISTS `au_message_log`;
CREATE TABLE `au_message_log`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '投递的消息',
  `exchange` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交换机名称',
  `queue` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '队列名称',
  `routing_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绑定路由key',
  `try_count` int NOT NULL COMMENT '重试次数',
  `consume_status` tinyint NOT NULL COMMENT '1: 表示消费成功 0：表示消费失败',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `exchange_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交换机类型',
  `ack_status` tinyint NOT NULL COMMENT '确认状态 1：应答了',
  `error_message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '如果发生错误，则错误消息是什么',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `createTIme_index`(`create_time` ASC) USING BTREE,
  INDEX `routingKey_index`(`routing_key` ASC) USING BTREE,
  INDEX `union_message_index`(`uid` ASC, `routing_key` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_message_log
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
