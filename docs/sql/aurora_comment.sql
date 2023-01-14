/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : aurora_comment

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 05/08/2022 18:03:14
*/
use aurora_comment;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for au_comment
-- ----------------------------
DROP TABLE IF EXISTS `au_comment`;
CREATE TABLE `au_comment` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `username` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'Aurora_小可爱' COMMENT '此评论的用户名',
  `avatar` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此评论这的头像uid',
  `site` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'https://aurora.xcye.xyz' COMMENT '此评论者的博客地址',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此评论这的邮箱地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '此评论的创时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '此评论最后修改时间',
  `comment_ip` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论者的ip地址',
  `operation_system_info` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论者的浏览器版本',
  `is_show_comment` tinyint NOT NULL DEFAULT '1' COMMENT '是否显示此评论 1： 显示 0： 不显示',
  `reply_comment_uid` bigint DEFAULT NULL COMMENT '此评论是回复哪个评论的',
  `is_email_notice` tinyint NOT NULL DEFAULT '0' COMMENT '如果此评论是回复某条评论，则1：已通知回复的那条评论的邮箱，0：未发送邮箱通知',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '在哪个地址发布评论',
  `next_comment_uid_array` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '此评论的所有下一条集合',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `user_uid` bigint NOT NULL COMMENT '此评论是属于哪个用户的',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1：删除 0：未删除',
  `page_type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此评论是在哪种页面发布的',
  PRIMARY KEY (`uid`),
  KEY `unique_username` (`username`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `replyCommentUid` (`reply_comment_uid`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_comment
-- ----------------------------
BEGIN;
INSERT INTO `au_comment` (`uid`, `username`, `avatar`, `site`, `email`, `create_time`, `update_time`, `comment_ip`, `operation_system_info`, `is_show_comment`, `reply_comment_uid`, `is_email_notice`, `path`, `next_comment_uid_array`, `content`, `user_uid`, `is_delete`, `page_type`) VALUES (1527217620658122752, '哈哈哈', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '260400276@qq.com', '2022-05-19 17:20:29', '2022-06-05 10:58:19', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 0, 1527215589574799360, 1, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1', '如果说“黄泉一笑”的技能效果具有唯一性的话，那小苏觉得还可以勉强接受，但实际情况是高级技能“取之有道”完全可以达到类似的效果，并且获取难度还要比前者简单的多。也就是说，只要召唤兽身上带有“取之有道”，你完全可以把它当成范式的专属觉醒来使用，既然这样，小苏特别想问开发组一个问题：何为专属？专属的意义又', 1522074993315815424, 0, 'ARTICLE');
INSERT INTO `au_comment` (`uid`, `username`, `avatar`, `site`, `email`, `create_time`, `update_time`, `comment_ip`, `operation_system_info`, `is_show_comment`, `reply_comment_uid`, `is_email_notice`, `path`, `next_comment_uid_array`, `content`, `user_uid`, `is_delete`, `page_type`) VALUES (1527222624664121344, '这是用户A', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '260400276@qq.com', '2022-05-19 17:42:55', '2022-06-05 10:58:21', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 0, 1527218392640745472, 1, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1,1527222995046330368,1527223166605946880', '如果说“黄泉一笑”的技能效果具有唯一性的话，那小苏觉得还可以勉强接受，但实际情况是高级技能“取之有道”完全可以达到类似的效果，并且获取难度还要比前者简单的多。也就是说，只要召唤兽身上带有“取之有道”，你完全可以把它当成范式的专属觉醒来使用，既然这样，小苏特别想问开发组一个问题：何为专属？专属的意义又', 1522074993315815424, 0, 'ARTICLE');
INSERT INTO `au_comment` (`uid`, `username`, `avatar`, `site`, `email`, `create_time`, `update_time`, `comment_ip`, `operation_system_info`, `is_show_comment`, `reply_comment_uid`, `is_email_notice`, `path`, `next_comment_uid_array`, `content`, `user_uid`, `is_delete`, `page_type`) VALUES (1527222995046330368, '哈哈', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '2291308094@qq.com', '2022-05-19 17:41:51', '2022-06-05 23:02:10', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 0, 1527222624664121344, 1, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1', '这是回复的评论', 1522074993315815424, 0, 'ARTICLE');
INSERT INTO `au_comment` (`uid`, `username`, `avatar`, `site`, `email`, `create_time`, `update_time`, `comment_ip`, `operation_system_info`, `is_show_comment`, `reply_comment_uid`, `is_email_notice`, `path`, `next_comment_uid_array`, `content`, `user_uid`, `is_delete`, `page_type`) VALUES (1527223166605946880, '哈哈', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '2291308094@qq.com', '2022-05-19 17:42:54', '2022-06-02 13:45:26', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 1, 1527222624664121344, 1, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1', '这是回复的评论', 1522074993315815424, 0, 'ARTICLE');
INSERT INTO `au_comment` (`uid`, `username`, `avatar`, `site`, `email`, `create_time`, `update_time`, `comment_ip`, `operation_system_info`, `is_show_comment`, `reply_comment_uid`, `is_email_notice`, `path`, `next_comment_uid_array`, `content`, `user_uid`, `is_delete`, `page_type`) VALUES (1527223524266831872, '哈哈', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '2291308094@qq.com', '2022-05-19 17:44:04', '2022-06-02 17:00:20', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 0, 1527222624664121344, 1, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1', '这是回复的评论', 1522074993315815424, 0, 'ARTICLE');
INSERT INTO `au_comment` (`uid`, `username`, `avatar`, `site`, `email`, `create_time`, `update_time`, `comment_ip`, `operation_system_info`, `is_show_comment`, `reply_comment_uid`, `is_email_notice`, `path`, `next_comment_uid_array`, `content`, `user_uid`, `is_delete`, `page_type`) VALUES (1527224560616435712, '哈哈', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '2291308094@qq.com', '2022-05-19 17:48:11', '2022-06-02 16:59:58', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 1, 1527215589574799360, 1, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1', '这是回复的评论', 1522074993315815424, 0, 'ARTICLE');
INSERT INTO `au_comment` (`uid`, `username`, `avatar`, `site`, `email`, `create_time`, `update_time`, `comment_ip`, `operation_system_info`, `is_show_comment`, `reply_comment_uid`, `is_email_notice`, `path`, `next_comment_uid_array`, `content`, `user_uid`, `is_delete`, `page_type`) VALUES (1527224962028105728, '嘿嘿', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '2604400276@qq.com', '2022-05-19 17:49:40', '2022-06-02 13:45:28', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 1, 1527215589574799360, 1, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1', '按照正常的思维，家长都会比较担心自己孩子的身体健康，但是有些家长玩网络时间久了之后，思维方式就会发生改变。 就像这位“老妈”，看到新闻说有人会毒死学习成绩好的同学，而这名同学还以为妈妈在担心自己，却没想到妈妈下面的一句话让自己颠覆认知，不过网友也表示，后妈都不敢这么说，确定是亲生无疑。', 1522074993315815424, 0, 'ARTICLE');
INSERT INTO `au_comment` (`uid`, `username`, `avatar`, `site`, `email`, `create_time`, `update_time`, `comment_ip`, `operation_system_info`, `is_show_comment`, `reply_comment_uid`, `is_email_notice`, `path`, `next_comment_uid_array`, `content`, `user_uid`, `is_delete`, `page_type`) VALUES (1527225116491739136, 'haha', 'wrh4isu5g6sm3uj', '7znarc9posf7kje', '2291308094@qq.com', '2022-05-19 17:50:16', '2022-06-02 13:45:28', '127.0.0.1', 'Windows 10;Chrome version: 75.0.3770.142', 1, 1527215589574799360, 1, '5f3nzq9l66v7hfs', 'auyg5fb9817gml1', '很多同学在选择大学的时候，都会选择一个比较陌生的城市，一方面是想去一个陌生的城市去看看，开阔一下眼界，另一方面可能是对这个城市真正的喜欢，而这个过程就是对父母的一种考验。 毕竟父母都会将孩子视为掌心里的宝，他们希望的是孩子能够离自己近一些，平时多和父母沟通，但大学毕竟是比较忙的，忙着学业、考试、论文等等', 1522074993315815424, 0, 'ARTICLE');
COMMIT;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='AT transaction mode undo table';

-- ----------------------------
-- Records of undo_log
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
