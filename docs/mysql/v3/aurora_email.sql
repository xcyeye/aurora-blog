/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟Docker
 Source Server Type    : MySQL
 Source Server Version : 80100 (8.1.0)
 Source Host           : 192.168.0.95:34567
 Source Schema         : aurora_email

 Target Server Type    : MySQL
 Target Server Version : 80100 (8.1.0)
 File Encoding         : 65001

 Date: 15/10/2023 18:01:06
*/
DROP
DATABASE IF EXISTS `aurora_email`;
CREATE
DATABASE `aurora_email` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';
use aurora_email;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for au_comment
-- ----------------------------
DROP TABLE IF EXISTS `au_comment`;
CREATE TABLE `au_comment`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `username` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'Aurora_小可爱' COMMENT '此评论的用户名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此评论这的头像uid',
  `site` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'https://aurora.xcye.xyz' COMMENT '此评论者的博客地址',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此评论这的邮箱地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '此评论的创时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '此评论最后修改时间',
  `comment_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论者的ip地址',
  `operation_system_info` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论者的浏览器版本',
  `is_show_comment` tinyint NOT NULL DEFAULT 1 COMMENT '是否显示此评论 1： 显示 0： 不显示',
  `reply_comment_uid` bigint NULL DEFAULT NULL COMMENT '此评论是回复哪个评论的',
  `is_email_notice` tinyint NOT NULL DEFAULT 0 COMMENT '如果此评论是回复某条评论，则1：已通知回复的那条评论的邮箱，0：未发送邮箱通知',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '在哪个地址发布评论',
  `next_comment_uid_array` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '此评论的所有下一条集合',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `user_uid` bigint NOT NULL COMMENT '此评论是属于哪个用户的',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '1：删除 0：未删除',
  `page_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此评论是在哪种页面发布的',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `unique_username`(`username` ASC) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `replyCommentUid`(`reply_comment_uid` ASC) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_comment
-- ----------------------------

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
INSERT INTO `au_message_log` VALUES (1713133694765637632, 'xcyeya', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-14 10:04:41', NULL, 'topic', 0, NULL);
INSERT INTO `au_message_log` VALUES (1713206292425875456, 'testuser', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-14 14:53:11', NULL, 'topic', 0, NULL);
INSERT INTO `au_message_log` VALUES (1713206373921202176, 'testuser', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-14 14:53:24', NULL, 'topic', 0, NULL);
INSERT INTO `au_message_log` VALUES (1713206488798994432, 'testuser', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-14 14:53:51', NULL, 'topic', 0, NULL);
INSERT INTO `au_message_log` VALUES (1713206871692812288, 'testuser', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-14 14:55:34', NULL, 'topic', 0, NULL);
INSERT INTO `au_message_log` VALUES (1713207081068273664, 'testuser', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-14 14:56:26', NULL, 'topic', 0, NULL);
INSERT INTO `au_message_log` VALUES (1713207351386972160, 'testuser', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-14 14:57:17', NULL, 'topic', 0, NULL);
INSERT INTO `au_message_log` VALUES (1713217454517395456, 'testuser', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-14 15:37:33', NULL, 'topic', 0, NULL);
INSERT INTO `au_message_log` VALUES (1713365107934896128, 'aaaaa', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-15 01:24:10', NULL, 'topic', 0, NULL);
INSERT INTO `au_message_log` VALUES (1713365336658681856, 'aaaaa', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-15 01:25:03', NULL, 'topic', 0, NULL);
INSERT INTO `au_message_log` VALUES (1713369022113914880, 'aaaaa', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-15 01:39:45', NULL, 'topic', 0, NULL);
INSERT INTO `au_message_log` VALUES (1713370152730501120, 'aaaaa', 'aurora.send.operate.user.exchange', NULL, 'aurora.operate.user.lock.account.routing.key', 0, 0, '2023-10-15 01:44:13', NULL, 'topic', 0, NULL);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
