/*
 Navicat Premium Data Transfer

 Source Server         : 天翼云
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : 182.44.32.182:3306
 Source Schema         : aurora_comment

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 12/03/2023 21:09:43
*/
DROP
DATABASE IF EXISTS `aurora_comment`;
CREATE
DATABASE `aurora_comment` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';
USE aurora_comment;
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
