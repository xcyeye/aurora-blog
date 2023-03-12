/*
 Navicat Premium Data Transfer

 Source Server         : 天翼云
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : 182.44.32.182:3306
 Source Schema         : aurora_article

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 12/03/2023 21:09:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for au_article
-- ----------------------------
DROP TABLE IF EXISTS `au_article`;
CREATE TABLE `au_article`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `is_show_comment` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '文章是否展示评论，0：否，1：是',
  `accessory_uids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章有附件的话，附件的uid集合',
  `category_names` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章类别uid集合',
  `tag_names` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章标签uid集合',
  `is_publish` tinyint NOT NULL DEFAULT 1 COMMENT '文章是否发布，1：发布，0：不发布',
  `user_uid` bigint NOT NULL COMMENT '发布此篇文章的用户uid',
  `is_original_article` tinyint NOT NULL DEFAULT 1 COMMENT '是否原创，1：原创 0：不是原创',
  `original_article_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '如果是原创，则原创链接',
  `cover_picture_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章封面对应的图片uid',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章内容',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章简介',
  `is_timing` tinyint NOT NULL DEFAULT 0 COMMENT '是否定时发布 0： 不定时，1：定时',
  `timing_publish_time` timestamp NULL DEFAULT NULL COMMENT '定时发布时间',
  `like_number` int NULL DEFAULT 0 COMMENT '文章点赞数',
  `read_number` int NULL DEFAULT NULL COMMENT '阅读量',
  `comment_uids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '此篇文章对应的评论uid集合，只需要设置所有父评论的uid',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '此篇文章最后修改的时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章发布时间',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 逻辑删除 1： 已删除',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `union_article_index`(`uid` ASC, `user_uid` ASC, `create_time` ASC) USING BTREE,
  FULLTEXT INDEX `fullText_index`(`content`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_article
-- ----------------------------

-- ----------------------------
-- Table structure for au_bulletin
-- ----------------------------
DROP TABLE IF EXISTS `au_bulletin`;
CREATE TABLE `au_bulletin`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公告的标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '公告创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '公告最后修改时间',
  `user_uid` bigint NULL DEFAULT NULL COMMENT '发布此公告的用户uid',
  `is_show` tinyint NOT NULL DEFAULT 1 COMMENT '1: 显示公告 0： 不显示该公告',
  `is_timing` tinyint NOT NULL DEFAULT 0 COMMENT '1：定时发布 0： 不定时发布公告',
  `timing_publish_time` timestamp NULL DEFAULT NULL COMMENT '定时发布公告的时间',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '1:删除 0：未删除',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE,
  INDEX `union_bulletin_index`(`uid` ASC, `user_uid` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_bulletin
-- ----------------------------

-- ----------------------------
-- Table structure for au_category
-- ----------------------------
DROP TABLE IF EXISTS `au_category`;
CREATE TABLE `au_category`  (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此类别的标题',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此类别的简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此类别的封面图地址',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '1: 删除 ，0：未删除',
  `user_uid` bigint NOT NULL COMMENT '用户的userUid',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE,
  INDEX `union_category`(`uid` ASC, `title` ASC, `user_uid` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_category
-- ----------------------------

-- ----------------------------
-- Table structure for au_link
-- ----------------------------
DROP TABLE IF EXISTS `au_link`;
CREATE TABLE `au_link`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_uid` bigint NULL DEFAULT NULL COMMENT '此条友情链接是哪个用户的',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此条友情链接属于哪个分类',
  `link_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'logo地址',
  `link_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '链接地址',
  `link_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对方的名称',
  `link_description` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述信息',
  `link_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对方站点的封面图',
  `is_publish` tinyint NOT NULL DEFAULT 1 COMMENT '是否展示此条友情链接 1：展示 0：不展示',
  `email` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此友情链接对应的站长邮箱',
  `qq_number` bigint NULL DEFAULT NULL COMMENT '此友情链接对应的站长的qq号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `categoryName_index`(`category_name` ASC) USING BTREE,
  INDEX `union_link_index`(`uid` ASC, `user_uid` ASC, `link_url` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_link
-- ----------------------------

-- ----------------------------
-- Table structure for au_tag
-- ----------------------------
DROP TABLE IF EXISTS `au_tag`;
CREATE TABLE `au_tag`  (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此标签的标题',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此标签的简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `cover_Url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此类别的封面图uid',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '1: 删除，0：未删除',
  `user_uid` bigint NOT NULL COMMENT '用户的userUid',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `union_tag_index`(`uid` ASC, `title` ASC, `user_uid` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_tag
-- ----------------------------

-- ----------------------------
-- Table structure for au_talk
-- ----------------------------
DROP TABLE IF EXISTS `au_talk`;
CREATE TABLE `au_talk`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_uid` bigint NULL DEFAULT NULL COMMENT '发布此说说的用户uid',
  `is_show_comment` tinyint NOT NULL DEFAULT 1 COMMENT '此说说是否显示评论 1： 显示 0： 不显示',
  `comment_uids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此说说的评论uid集合',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '此说说发布时间',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此说说的内容',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此说说标题',
  `is_show` tinyint NOT NULL DEFAULT 1 COMMENT '1： 显示说说，0： 不显示说说',
  `like_number` int NOT NULL DEFAULT 0 COMMENT '此说说的点赞数',
  `picture_src_list` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此说说对应的图片src集合',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '1: 已删除',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `user_uid_index`(`user_uid` ASC) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `union_talk_index`(`uid` ASC, `user_uid` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_talk
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1176 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
