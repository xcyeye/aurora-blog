/*
 Navicat Premium Data Transfer

 Source Server         : 本地Linux
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : 192.168.158.120:3306
 Source Schema         : aurora_comment

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 15/01/2023 19:32:20
*/

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
  `comment_ip` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论者的ip地址',
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_comment
-- ----------------------------
INSERT INTO `au_comment` VALUES (1527223166605946880, '哈哈', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '2291308094@qq.com', '2022-05-19 17:42:54', '2022-12-15 13:04:45', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 1, 1527222624664121344, 1, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1', '这是回复的评论', 1522074993315815424, 0, 'ARTICLE');
INSERT INTO `au_comment` VALUES (1527223524266831872, '哈哈', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '2291308094@qq.com', '2022-05-19 17:44:04', '2022-12-15 12:43:11', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 0, 1527222624664121344, 0, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1', '这是回复的评论', 1522074993315815424, 0, 'ARTICLE');
INSERT INTO `au_comment` VALUES (1527224962028105728, '嘿嘿', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '2604400276@qq.com', '2022-05-19 17:49:40', '2022-12-15 13:05:18', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 1, 1527215589574799360, 1, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1', '按照正常的思维，家长都会比较担心自己孩子的身体健康，但是有些家长玩网络时间久了之后，思维方式就会发生改变。 就像这位“老妈”，看到新闻说有人会毒死学习成绩好的同学，而这名同学还以为妈妈在担心自己，却没想到妈妈下面的一句话让自己颠覆认知，不过网友也表示，后妈都不敢这么说，确定是亲生无疑。', 1522074993315815424, 0, 'ARTICLE');
INSERT INTO `au_comment` VALUES (1527225116491739136, 'haha', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '2291308094@qq.com', '2022-05-19 17:50:16', '2022-12-15 13:05:21', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 1, 1527215589574799360, 1, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1', '很多同学在选择大学的时候，都会选择一个比较陌生的城市，一方面是想去一个陌生的城市去看看，开阔一下眼界，另一方面可能是对这个城市真正的喜欢，而这个过程就是对父母的一种考验。 毕竟父母都会将孩子视为掌心里的宝，他们希望的是孩子能够离自己近一些，平时多和父母沟通，但大学毕竟是比较忙的，忙着学业、考试、论文等等', 1522074993315815424, 0, 'ARTICLE');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
