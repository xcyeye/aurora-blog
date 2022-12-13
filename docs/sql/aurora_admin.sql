/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : aurora_admin

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 05/08/2022 18:02:53
*/

use aurora_admin;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for au_admin_sidebar
-- ----------------------------
DROP TABLE IF EXISTS `au_admin_sidebar`;
CREATE TABLE `au_admin_sidebar` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `level` int NOT NULL DEFAULT '0' COMMENT '前台导航的展示等级 比如0就是一级导航',
  `pre_sidebar_uid` bigint NOT NULL DEFAULT '0' COMMENT '当前导航的父导航uid，也就是该导航显示在哪个导航下面',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的标题',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的对应地址',
  `is_external` tinyint NOT NULL DEFAULT '0' COMMENT '1：链接到外部地址 0：链接就是此站点的，不在新标签也打开',
  `icon_class_name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此导航的类名，用户icon',
  `sort` int NOT NULL DEFAULT '0' COMMENT '此导航的顺序编号',
  `user_uid` bigint DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `nav_uid` (`pre_sidebar_uid`) USING BTREE,
  KEY `user` (`user_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_admin_sidebar
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for au_navigation
-- ----------------------------
DROP TABLE IF EXISTS `au_navigation`;
CREATE TABLE `au_navigation` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `level` int NOT NULL DEFAULT '0' COMMENT '前台导航的展示等级 比如0就是一级导航',
  `parent_nav_uid` bigint DEFAULT NULL COMMENT '当前导航的父导航uid，也就是该导航显示在哪个导航下面',
  `son_nav_uids` text COLLATE utf8mb4_general_ci COMMENT '该导航的子导航uid集合',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的标题',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的对应地址',
  `is_external` tinyint NOT NULL DEFAULT '0' COMMENT '1：链接到外部地址 0：链接就是此站点的，不在新标签也打开',
  `icon_class_name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此导航的类名，用户icon',
  `sort` int NOT NULL DEFAULT '0' COMMENT '此导航的顺序编号',
  `user_uid` bigint NOT NULL COMMENT '该导航属于哪个用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1: 删除 0：不删除',
  `is_show` tinyint NOT NULL DEFAULT '1' COMMENT '1: 展示，0： 不显示',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_navigation
-- ----------------------------
BEGIN;
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526179609817128960, 0, NULL, '1526184455752392704,1526186806055804928,1526186892060008448,1526186914969296896,1526186938008608768', '博客文章', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:04:55', '2022-05-16 21:04:55', 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526179711717744640, 0, NULL, NULL, '友情链接', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 20:36:12', NULL, 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526179864444936192, 0, NULL, NULL, '说说', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 20:36:49', NULL, 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526179978802634752, 0, NULL, NULL, '插件', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 20:37:16', NULL, 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526184455752392704, 0, 1526179609817128960, NULL, '文章1', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 20:55:04', NULL, 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526186806055804928, 0, 1526179609817128960, NULL, '文章2', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:04:24', NULL, 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526186892060008448, 0, 1526179609817128960, NULL, '文章3', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:04:44', NULL, 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526186914969296896, 0, 1526179609817128960, NULL, '文章4', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:04:50', NULL, 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526186938008608768, 0, 1526179609817128960, '1526187371460567040,1526187436136734720,1526187459058606080', '文章5', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:07:00', '2022-05-16 21:07:00', 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526187371460567040, 0, 1526186938008608768, NULL, '文章5-1', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:06:39', NULL, 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526187436136734720, 0, 1526186938008608768, '1526187552637722624,1526187575698006016', '文章5-2', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:07:27', '2022-05-16 21:07:27', 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526187459058606080, 0, 1526186938008608768, NULL, '文章5-3', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:07:00', NULL, 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526187552637722624, 0, 1526187436136734720, NULL, '文章5-2-1', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:07:22', NULL, 0, 1);
INSERT INTO `au_navigation` (`uid`, `level`, `parent_nav_uid`, `son_nav_uids`, `title`, `path`, `is_external`, `icon_class_name`, `sort`, `user_uid`, `create_time`, `update_time`, `is_delete`, `is_show`) VALUES (1526187575698006016, 0, 1526187436136734720, NULL, '文章5-2-2', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:07:27', NULL, 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for au_permission
-- ----------------------------
DROP TABLE IF EXISTS `au_permission`;
CREATE TABLE `au_permission` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid，自增',
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限的名称',
  `path` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限的地址，可以是组件的名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique_permissionName_index` (`name`) USING BTREE,
  KEY `create_time_index` (`create_time`) USING BTREE,
  KEY `path_index` (`path`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_permission
-- ----------------------------
BEGIN;
INSERT INTO `au_permission` (`uid`, `name`, `path`, `create_time`, `update_time`) VALUES (3, '这是首页', 'POST:/', '2022-05-07 16:04:05', '2022-06-25 16:20:53');
INSERT INTO `au_permission` (`uid`, `name`, `path`, `create_time`, `update_time`) VALUES (4, '用户管理', 'GET:/admin/user', '2022-05-10 14:11:55', NULL);
INSERT INTO `au_permission` (`uid`, `name`, `path`, `create_time`, `update_time`) VALUES (5, '查询所有路径权限', 'GET:/admin/permission', '2022-05-10 14:12:40', NULL);
INSERT INTO `au_permission` (`uid`, `name`, `path`, `create_time`, `update_time`) VALUES (6, '插入路径权限', 'POST:/admin/permission', '2022-05-10 14:12:50', NULL);
INSERT INTO `au_permission` (`uid`, `name`, `path`, `create_time`, `update_time`) VALUES (7, '删除路径权限', 'DELETE:/admin/permission', '2022-05-10 14:13:01', '2022-06-25 16:35:59');
INSERT INTO `au_permission` (`uid`, `name`, `path`, `create_time`, `update_time`) VALUES (10, '权限', 'GET:/admin/permissionRelation/username/**', '2022-05-10 20:54:30', NULL);
COMMIT;

-- ----------------------------
-- Table structure for au_role
-- ----------------------------
DROP TABLE IF EXISTS `au_role`;
CREATE TABLE `au_role` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid，自增',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色的名称，不用添加ROLE_',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '用户的状态 1：已禁用 0：未禁用',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique_role_name_index` (`name`) USING BTREE,
  KEY `create_time_index` (`create_time`) USING BTREE COMMENT '创建时间单独索引'
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_role
-- ----------------------------
BEGIN;
INSERT INTO `au_role` (`uid`, `name`, `create_time`, `update_time`, `status`) VALUES (3, 'admin', '2022-05-07 15:09:15', NULL, 0);
INSERT INTO `au_role` (`uid`, `name`, `create_time`, `update_time`, `status`) VALUES (4, 'user', '2022-05-07 15:10:26', NULL, 0);
INSERT INTO `au_role` (`uid`, `name`, `create_time`, `update_time`, `status`) VALUES (5, 'root', '2022-05-07 15:10:40', NULL, 0);
INSERT INTO `au_role` (`uid`, `name`, `create_time`, `update_time`, `status`) VALUES (16, 'chuchen', '2022-05-10 14:17:30', NULL, 0);
INSERT INTO `au_role` (`uid`, `name`, `create_time`, `update_time`, `status`) VALUES (17, 'test', '2022-05-10 14:17:44', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for au_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `au_role_permission`;
CREATE TABLE `au_role_permission` (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `role_uid` bigint NOT NULL,
  `permission_uid` bigint NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `roleUid_index` (`role_uid`) USING BTREE,
  KEY `permissionUid_index` (`permission_uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `au_role_permission` (`uid`, `role_uid`, `permission_uid`) VALUES (5, 17, 4);
INSERT INTO `au_role_permission` (`uid`, `role_uid`, `permission_uid`) VALUES (6, 3, 5);
INSERT INTO `au_role_permission` (`uid`, `role_uid`, `permission_uid`) VALUES (7, 4, 6);
INSERT INTO `au_role_permission` (`uid`, `role_uid`, `permission_uid`) VALUES (8, 3, 7);
INSERT INTO `au_role_permission` (`uid`, `role_uid`, `permission_uid`) VALUES (9, 5, 8);
INSERT INTO `au_role_permission` (`uid`, `role_uid`, `permission_uid`) VALUES (10, 3, 9);
INSERT INTO `au_role_permission` (`uid`, `role_uid`, `permission_uid`) VALUES (11, 3, 10);
COMMIT;

-- ----------------------------
-- Table structure for au_site
-- ----------------------------
DROP TABLE IF EXISTS `au_site`;
CREATE TABLE `au_site` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `site_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '站点的icon地址',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '站点的标题 浏览器顶部部分',
  `logo_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '站点的前台logo文字',
  `site_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '站点的logo地址',
  `site_center_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '站点前台中间部分logo',
  `additional_head` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '站点额外的head信息，直接传入<script/>这种',
  `user_uid` bigint NOT NULL COMMENT '此站点信息属于哪个用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '0:不删除 1： 删除',
  PRIMARY KEY (`uid`),
  KEY `user_index` (`user_uid`),
  KEY `create_index` (`create_time`) USING BTREE COMMENT '创建时间',
  KEY `union_site_index` (`uid`,`user_uid`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_site
-- ----------------------------
BEGIN;
INSERT INTO `au_site` (`uid`, `site_icon`, `title`, `logo_title`, `site_logo`, `site_center_logo`, `additional_head`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (1526144941273128960, NULL, NULL, NULL, NULL, NULL, NULL, 1522074993315815424, '2022-05-16 18:18:03', NULL, 0);
INSERT INTO `au_site` (`uid`, `site_icon`, `title`, `logo_title`, `site_logo`, `site_center_logo`, `additional_head`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (1526145056780066816, NULL, NULL, NULL, NULL, NULL, NULL, 1522074993315815424, '2022-05-16 18:18:30', NULL, 0);
INSERT INTO `au_site` (`uid`, `site_icon`, `title`, `logo_title`, `site_logo`, `site_center_logo`, `additional_head`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (1526145197306028032, NULL, NULL, NULL, NULL, NULL, NULL, 1522074993315815424, '2022-05-16 18:19:04', NULL, 0);
INSERT INTO `au_site` (`uid`, `site_icon`, `title`, `logo_title`, `site_logo`, `site_center_logo`, `additional_head`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (1526145691302764544, 'jr4m4', 'o3ae5', '8wcv9', 'q0cr7', '403ko', 'bb8kg', 1522074993315815424, '2022-05-16 18:21:01', NULL, 0);
INSERT INTO `au_site` (`uid`, `site_icon`, `title`, `logo_title`, `site_logo`, `site_center_logo`, `additional_head`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (1526145698235949056, 'jr4m4', 'o3ae5', '8wcv9', 'q0cr7', '403ko', 'bb8kg', 1522074993315815424, '2022-05-16 18:21:03', NULL, 0);
INSERT INTO `au_site` (`uid`, `site_icon`, `title`, `logo_title`, `site_logo`, `site_center_logo`, `additional_head`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (1526145701733998592, 'jr4m4', 'o3ae5', '8wcv9', 'q0cr7', '403ko', 'bb8kg', 1522074993315815424, '2022-05-16 18:21:04', NULL, 0);
INSERT INTO `au_site` (`uid`, `site_icon`, `title`, `logo_title`, `site_logo`, `site_center_logo`, `additional_head`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (1526145705643089920, '1d8pg', 'sz4uz', 'isluw', 'jvkf7', 'gtc6h', 'fnam7', 1, '2022-05-16 18:32:03', '2022-05-16 18:32:03', 1);
INSERT INTO `au_site` (`uid`, `site_icon`, `title`, `logo_title`, `site_logo`, `site_center_logo`, `additional_head`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (1526145776430358528, 'j6762', 'd2lx8', 'xf3h9', 'k1i4j', 'snsfh', '9vxd4', 1522074993315815424, '2022-05-16 18:21:22', NULL, 0);
INSERT INTO `au_site` (`uid`, `site_icon`, `title`, `logo_title`, `site_logo`, `site_center_logo`, `additional_head`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (1526146422923599872, '1lzkl', 'ey5q8', 'lhk9m', '8avx1', 'up7s4', '28vej', 1522074993315815424, '2022-05-16 18:36:32', '2022-05-16 18:36:32', 1);
COMMIT;

-- ----------------------------
-- Table structure for au_social
-- ----------------------------
DROP TABLE IF EXISTS `au_social`;
CREATE TABLE `au_social` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid,自增',
  `social_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '社交名称',
  `social_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此社交图标的地址',
  `social_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此社交的链接',
  `is_show` tinyint NOT NULL DEFAULT '1' COMMENT '1： 显示此社交 0： 不显示',
  `user_uid` bigint DEFAULT NULL COMMENT '此社交属于哪个用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1: 删除 0：不删除',
  PRIMARY KEY (`uid`),
  KEY `create_time_index` (`create_time`) USING BTREE COMMENT '创建时间索引',
  KEY `user_uid_index` (`user_uid`) USING BTREE COMMENT '用户uid索引',
  KEY `union_social_index` (`uid`,`user_uid`,`create_time`) USING BTREE COMMENT 'uid,user_uid,create_time联合索引'
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_social
-- ----------------------------
BEGIN;
INSERT INTO `au_social` (`uid`, `social_name`, `social_icon`, `social_url`, `is_show`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (6, 'ms19c', 'ks00b', 'syr72', 1, 1522074993315815424, '2022-05-16 18:43:50', NULL, 1);
INSERT INTO `au_social` (`uid`, `social_name`, `social_icon`, `social_url`, `is_show`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (7, 'ms19c', 'ks00b', 'syr72', 1, 1522074993315815424, '2022-05-16 18:43:55', NULL, 1);
INSERT INTO `au_social` (`uid`, `social_name`, `social_icon`, `social_url`, `is_show`, `user_uid`, `create_time`, `update_time`, `is_delete`) VALUES (8, 'nzsbg', '4bomm', 'mifm0', 1, 1522074993315815424, '2022-05-16 18:48:34', '2022-05-16 18:48:34', 1);
COMMIT;

-- ----------------------------
-- Table structure for au_user
-- ----------------------------
DROP TABLE IF EXISTS `au_user`;
CREATE TABLE `au_user` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户简介',
  `nickname` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `gender` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '用户性别',
  `login_uid` bigint DEFAULT NULL COMMENT '用户登录记录的uid',
  `site_uid` bigint DEFAULT NULL COMMENT '用户的站点配置uid',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户的头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户的密码',
  `username` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `profession` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户的工作集合',
  `email_uid` bigint DEFAULT NULL COMMENT '此用户对应的邮箱uid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 1：删除 ',
  `is_account_lock` tinyint NOT NULL DEFAULT '0' COMMENT '1: 账户被锁住，0：未被锁住',
  `is_verify_email` tinyint NOT NULL COMMENT '1: 邮箱已验证，0：邮箱未验证',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique_username_index` (`username`) USING BTREE COMMENT '用户名索引',
  UNIQUE KEY `site_uid` (`site_uid`) USING BTREE,
  UNIQUE KEY `user_email` (`email_uid`) USING BTREE,
  UNIQUE KEY `user_login` (`login_uid`) USING BTREE,
  UNIQUE KEY `unique_email_uid_index` (`email_uid`) USING BTREE COMMENT '邮箱Uid索引',
  UNIQUE KEY `unique_site_uid_index` (`site_uid`) USING BTREE COMMENT 'SiteUid索引',
  UNIQUE KEY `unique_login_uid_index` (`login_uid`) USING BTREE COMMENT 'LoginUid索引',
  KEY `create_time_index` (`create_time`) USING BTREE COMMENT '创建时间单独索引',
  KEY `union_user_index` (`uid`,`username`,`create_time`) USING BTREE COMMENT 'uid，用户名，创建时间联合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_user
-- ----------------------------
BEGIN;
INSERT INTO `au_user` (`uid`, `user_summary`, `nickname`, `gender`, `login_uid`, `site_uid`, `avatar`, `password`, `username`, `profession`, `email_uid`, `create_time`, `update_time`, `is_delete`, `is_account_lock`, `is_verify_email`) VALUES (1522074993315815424, '这是我的描述asdfasdf', '青衫烟雨客', 'SECRET', NULL, NULL, 'http://localhost/aurora-upload/jpg/2022/5/wallhaven-4yp1ok1653828797971.jpg', '$2a$10$kQoSZrM7GDQjBhLfGAQT3egaP9VU6YPzfcO2U1YKlbsMw5vyRCGgS', 'aurora', 'asdfjkaf', 1530936336502022144, '2022-05-19 13:57:34', '2022-06-05 10:58:01', 0, 0, 1);
INSERT INTO `au_user` (`uid`, `user_summary`, `nickname`, `gender`, `login_uid`, `site_uid`, `avatar`, `password`, `username`, `profession`, `email_uid`, `create_time`, `update_time`, `is_delete`, `is_account_lock`, `is_verify_email`) VALUES (1530891163745656832, NULL, 'adsffdh', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$T6IMjHbANsr7dCBNdywnuOrvSddrUwx2mYq42llnRHc92/PjuTF2m', 'asdfasdfasdf', NULL, NULL, '2022-05-29 20:37:50', '2022-05-30 22:20:04', 0, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for au_user_role
-- ----------------------------
DROP TABLE IF EXISTS `au_user_role`;
CREATE TABLE `au_user_role` (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `role_uid` bigint NOT NULL COMMENT '角色uid',
  `user_uid` bigint NOT NULL COMMENT '用户uid',
  PRIMARY KEY (`uid`),
  KEY `roleUid_index` (`role_uid`) USING BTREE,
  KEY `userUid_index` (`user_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_user_role
-- ----------------------------
BEGIN;
INSERT INTO `au_user_role` (`uid`, `role_uid`, `user_uid`) VALUES (13, 3, 1522074993315815424);
INSERT INTO `au_user_role` (`uid`, `role_uid`, `user_uid`) VALUES (14, 4, 1522074993315815424);
INSERT INTO `au_user_role` (`uid`, `role_uid`, `user_uid`) VALUES (15, 5, 1522074993315815424);
COMMIT;

-- ----------------------------
-- Table structure for au_white_url
-- ----------------------------
DROP TABLE IF EXISTS `au_white_url`;
CREATE TABLE `au_white_url` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '白名单地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `url_unique_index` (`url`) USING BTREE COMMENT '地址索引',
  KEY `create_time_index` (`create_time`) USING BTREE COMMENT '创建时间单独索引',
  KEY `union_whiteUrl_index` (`uid`,`url`) USING BTREE COMMENT 'uid,url,create_time联合索引'
) ENGINE=InnoDB AUTO_INCREMENT=1070 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_white_url
-- ----------------------------
BEGIN;
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (10, 'GET:/admin/user', '2022-05-11 08:41:44', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (11, 'GET:/admin/sdf', '2022-05-11 08:53:17', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (12, 'GET:/admin', '2022-05-11 09:09:50', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (14, 'POST:/auth/oauth/token/**', '2022-05-13 18:42:16', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (15, 'GET:/auth/**', '2022-05-13 18:44:38', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (16, 'POST:/auth/**', '2022-05-13 19:11:44', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (17, 'GET:/blog/link/**', '2022-05-13 22:32:11', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (18, 'POST:/blog/link/**', '2022-05-13 22:32:21', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (19, 'DELETE:/blog/link/**', '2022-05-13 22:32:34', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (20, 'POST:/auth/logout', '2022-05-16 00:14:27', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (21, 'GET:/swaggerui/*', '2022-05-16 22:02:36', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (22, 'GET:/swagger-ui/*', '2022-05-16 22:03:24', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (23, 'GET:/admin/swagger-ui/*', '2022-05-16 22:04:11', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (24, 'GET:/admin/swagger-ui/index.html', '2022-05-16 22:04:41', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (25, 'GET:/admin/swagger-ui/index.htm', '2022-05-16 22:05:38', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (26, 'GET:/admin/swagger-ui/index.htmdfasdf', '2022-05-16 22:10:58', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (27, 'GET:/admin/swagger-ui/index.htmdfasd', '2022-05-16 22:13:40', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (28, 'GET:/admin/swagger-ui/index.htmdfa', '2022-05-16 22:15:22', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (30, 'GET:/admin/verifyAccount/**', '2022-05-17 14:12:02', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (31, 'GET:/message/messageLog/**', '2022-05-17 17:54:56', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (32, 'POST:/message/messageLog/**', '2022-05-17 17:55:05', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (33, 'PUT:/message/messageLog/**', '2022-05-17 17:55:14', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (34, 'GET:/file/**', '2022-05-18 18:39:59', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (35, 'POST:/file/**', '2022-05-18 18:40:07', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (36, 'POST:/oauth/**', '2022-05-19 13:26:03', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (37, 'GET:/swagger-ui.html', '2022-05-21 00:19:46', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (38, 'GET:/v3/api-docs/**', '2022-05-21 13:28:41', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (39, 'GET:/webjars/swagger-ui/**', '2022-05-21 13:31:44', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (40, 'POST:/file/multi', '2022-06-04 19:09:51', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (41, 'GET:/', '2022-06-25 15:21:16', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (42, 'GET:/bb/sdf**', '2022-06-25 15:21:36', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (43, 'GET:/**/v3/api-docs', '2022-06-25 15:27:11', NULL);
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (70, 'https://image.maruyamahik9.org/CDsVinyl', '1971-08-01 09:27:55', '2017-01-19 03:11:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (71, 'http://video.huimekoon917.biz/VideoGames', '1978-08-15 15:48:54', '2020-10-10 20:36:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (72, 'http://drive.wlm93.jp/AppsGames', '1985-04-12 06:14:11', '2007-06-01 21:08:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (73, 'https://video.zca324.biz/ClothingShoesandJewelry', '2001-06-08 12:15:07', '2006-07-13 01:46:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (74, 'https://auth.zitaozo.net/HouseholdKitchenAppliances', '1976-07-13 23:55:14', '2014-11-03 20:56:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (75, 'https://www.szekwan9.biz/ToolsHomeDecoration', '2021-03-22 17:01:49', '2006-03-12 03:13:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (76, 'https://auth.stewartka.info/BeautyPersonalCare', '2006-08-22 17:52:45', '2017-05-17 16:50:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (77, 'http://image.rymur.xyz/ClothingShoesandJewelry', '1983-10-19 21:24:30', '2022-04-03 06:33:18');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (78, 'http://video.hicks2.jp/Handcrafts', '1984-09-17 08:48:28', '2022-06-06 09:32:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (79, 'http://drive.annakim.us/AutomotivePartsAccessories', '2000-07-16 13:28:10', '2007-02-05 00:48:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (80, 'https://auth.gz10.xyz/CenturionGardenOutdoor', '1993-07-01 11:07:33', '2006-02-15 13:43:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (81, 'https://image.yuton217.cn/ToysGames', '1989-01-04 21:02:13', '2005-09-28 22:08:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (82, 'https://video.sukyee72.cn/Books', '1995-04-19 10:35:06', '2005-12-03 02:41:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (83, 'http://video.brownkim42.cn/CenturionGardenOutdoor', '1974-10-03 01:19:08', '2003-06-13 23:41:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (84, 'https://auth.ytak42.jp/AutomotivePartsAccessories', '2005-03-15 23:35:04', '2014-06-23 10:49:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (85, 'http://drive.chianghy.net/ClothingShoesandJewelry', '1988-06-18 12:17:59', '2002-08-16 19:33:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (86, 'http://www.brianross.org/VideoGames', '2008-10-14 05:58:44', '2000-11-18 02:18:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (87, 'https://image.riwasaki1229.net/Appliances', '2011-07-24 05:56:31', '2005-10-19 10:01:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (88, 'http://video.hanonkay65.co.jp/CollectiblesFineArt', '2008-06-12 15:14:39', '2019-05-30 15:31:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (89, 'http://www.hirakent.info/Others', '1972-01-12 12:13:18', '2021-11-08 19:52:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (90, 'http://drive.itoikk.xyz/Baby', '2007-08-16 21:07:49', '2022-06-21 19:32:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (91, 'https://auth.nishimurar55.jp/ToysGames', '2018-02-12 04:03:46', '2011-05-17 19:35:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (92, 'https://image.takeuchi7.info/CDsVinyl', '2012-03-22 22:57:14', '2020-06-16 23:32:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (93, 'http://drive.airitaka.us/HouseholdKitchenAppliances', '1985-10-13 01:28:38', '2001-02-25 20:49:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (94, 'https://image.zhongyunxi.xyz/Books', '2006-05-18 17:34:05', '2003-12-20 21:00:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (95, 'http://auth.sykoo.info/VideoGames', '1983-11-18 12:55:22', '2010-05-07 07:13:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (96, 'http://www.dkimura6.co.jp/CollectiblesFineArt', '2009-03-10 12:39:49', '2000-04-20 05:54:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (97, 'https://drive.zhennan44.jp/BeautyPersonalCare', '1987-06-09 18:33:37', '2002-12-24 19:20:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (98, 'https://video.wfma1229.info/ToysGames', '1990-07-30 11:08:36', '2020-02-23 04:38:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (99, 'http://www.arimurana6.org/CollectiblesFineArt', '1992-11-10 03:01:29', '2014-01-27 04:10:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (100, 'http://image.yoshida3.info/Books', '1973-06-12 18:26:45', '2016-05-06 22:12:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (101, 'http://auth.kobayashi7.xyz/ComputersElectronics', '1995-06-10 06:52:10', '2010-09-30 20:37:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (102, 'http://auth.daicy.xyz/HouseholdKitchenAppliances', '2006-11-05 14:06:15', '2016-02-24 13:57:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (103, 'https://video.wongkwokwing76.org/PetSupplies', '2021-04-08 20:36:43', '2008-04-03 22:47:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (104, 'http://video.christinapayne329.cn/PetSupplies', '2011-05-28 06:09:52', '2013-02-19 07:03:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (105, 'https://auth.koo410.info/FilmSupplies', '1999-02-03 11:26:51', '2005-12-18 00:31:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (106, 'http://www.yungtszhin60.jp/CDsVinyl', '1974-06-24 19:09:37', '2016-08-10 03:15:12');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (107, 'http://auth.mary1963.jp/ClothingShoesandJewelry', '1991-11-15 07:55:23', '2010-05-19 05:51:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (108, 'https://www.ym55.com/PetSupplies', '2007-03-06 03:14:49', '2005-07-29 18:54:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (109, 'http://www.xiuymeng.info/MusicalInstrument', '1990-06-27 21:33:00', '2005-06-15 03:58:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (110, 'http://www.ikmio.info/MusicalInstrument', '1985-03-01 07:03:13', '2007-12-11 01:26:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (111, 'https://video.cher1.us/Books', '2016-07-07 21:20:36', '2015-11-29 06:40:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (112, 'http://www.kaming424.co.jp/ToolsHomeDecoration', '2003-01-28 12:47:54', '2007-12-25 13:48:12');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (113, 'http://drive.wur1992.info/HealthBabyCare', '1992-02-08 01:34:49', '2003-10-03 10:15:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (114, 'https://www.shitang.jp/CellPhonesAccessories', '1999-03-06 06:48:17', '2001-03-07 02:36:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (115, 'http://image.yuning06.biz/CellPhonesAccessories', '2000-08-22 06:04:52', '2018-10-03 20:28:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (116, 'http://auth.samiu2018.us/AppsGames', '2004-01-27 03:41:10', '2022-06-12 10:14:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (117, 'https://image.misasaki102.info/BeautyPersonalCare', '1970-01-30 00:28:15', '2006-11-18 18:54:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (118, 'https://image.skfo117.us/CenturionGardenOutdoor', '1979-07-30 05:49:29', '2014-12-02 21:52:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (119, 'https://drive.clleung1018.co.jp/ClothingShoesandJewelry', '2006-02-08 17:54:57', '2014-03-26 06:12:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (120, 'https://video.kazuma203.net/AutomotivePartsAccessories', '2011-07-01 01:27:56', '2001-07-19 22:04:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (121, 'https://video.kmyung.net/CollectiblesFineArt', '2019-01-03 05:09:22', '2003-07-10 05:07:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (122, 'https://image.wlu811.info/CDsVinyl', '1996-10-10 03:59:34', '2021-03-17 14:58:18');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (123, 'https://video.sandersn.jp/AppsGames', '2007-07-06 06:18:50', '2011-02-11 03:59:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (124, 'https://auth.fatto214.cn/FilmSupplies', '1974-09-08 18:45:45', '2015-07-02 10:40:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (125, 'https://www.sakuraiei.biz/VideoGames', '1972-05-10 09:27:01', '2001-08-27 17:28:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (126, 'http://drive.takuyaarim1027.jp/Books', '1986-10-29 03:12:52', '2002-10-26 14:15:56');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (127, 'https://www.zhiyuan614.com/HealthBabyCare', '2022-06-22 01:56:21', '2021-03-22 01:57:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (128, 'http://www.ikki5.us/Handcrafts', '1994-08-11 21:12:42', '2014-04-12 02:39:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (129, 'http://video.tyuito.cn/Handcrafts', '1989-10-04 18:29:39', '2001-12-23 01:04:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (130, 'https://www.rgibson.xyz/MusicalInstrument', '1979-09-05 23:44:26', '2008-11-12 21:16:18');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (131, 'http://drive.pha.org/Handcrafts', '2015-10-22 05:50:44', '2001-11-17 11:58:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (132, 'https://drive.warichard.net/ArtsHandicraftsSewing', '1975-05-20 10:59:19', '2017-02-02 05:48:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (133, 'http://www.gumar.info/AutomotivePartsAccessories', '1986-04-04 06:55:36', '2005-09-04 08:59:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (134, 'http://auth.mismori.info/ToolsHomeDecoration', '1987-12-26 20:15:59', '2022-07-12 16:42:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (135, 'http://drive.hnakagawa.biz/FilmSupplies', '1998-04-27 11:50:47', '2014-12-30 19:05:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (136, 'http://auth.josephine3.jp/ArtsHandicraftsSewing', '1991-10-31 16:12:28', '2013-02-27 22:21:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (137, 'http://drive.chifon.net/Food', '2017-08-13 00:27:10', '2019-07-29 04:34:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (138, 'https://image.wyunxi.net/Beauty', '1990-04-14 02:30:43', '2011-09-09 14:02:18');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (139, 'http://drive.edwinramirez.org/Beauty', '1973-07-12 07:12:04', '2017-12-25 11:07:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (140, 'https://image.fanzhiyu110.us/PetSupplies', '2012-07-23 13:32:58', '2015-01-16 21:10:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (141, 'http://drive.karyan7.co.jp/ComputersElectronics', '2020-11-14 19:26:00', '2007-10-15 08:00:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (142, 'http://image.lanzheng.net/ArtsHandicraftsSewing', '2006-12-25 04:28:34', '2009-05-03 23:08:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (143, 'https://auth.carolpo.info/ClothingShoesandJewelry', '2010-03-18 01:24:29', '2004-11-18 00:02:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (144, 'https://video.liangz.info/CDsVinyl', '2015-07-10 08:19:54', '2022-06-23 12:41:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (145, 'http://image.gaoanqi.net/Others', '1978-11-05 04:15:53', '2021-10-02 06:10:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (146, 'http://image.yoshidaryota92.us/HouseholdKitchenAppliances', '1993-11-05 04:45:40', '2015-08-16 23:02:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (147, 'https://drive.payneaa.info/HealthBabyCare', '1978-09-07 02:38:49', '2010-08-13 13:32:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (148, 'http://drive.ayanoh1129.org/Food', '2009-03-03 07:39:07', '2013-03-10 00:28:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (149, 'https://image.taniguchikazum51.cn/BaggageTravelEquipment', '2000-03-15 23:31:07', '2016-04-06 04:26:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (150, 'https://auth.yuenwm.jp/HealthBabyCare', '2012-12-19 20:25:05', '2016-10-26 02:59:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (151, 'http://video.heunglingling.jp/Appliances', '2014-05-22 13:27:39', '2000-05-20 08:17:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (152, 'https://drive.nand.biz/MusicalInstrument', '1983-03-13 05:50:33', '2002-10-04 03:01:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (153, 'http://video.collins427.info/Food', '1974-10-20 02:31:05', '2018-10-08 19:39:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (154, 'https://image.rinshimada.info/IndustrialScientificSupplies', '2000-06-05 11:34:19', '2017-01-22 09:16:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (155, 'http://video.rekudo73.org/Baby', '1970-09-04 03:39:19', '2017-03-23 03:55:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (156, 'http://image.mfisher9.biz/MusicalInstrument', '1973-03-10 12:14:52', '2016-12-10 08:47:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (157, 'http://drive.mmarie.cn/AppsGames', '1974-03-12 03:04:55', '2019-12-16 09:36:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (158, 'https://drive.nnakayama.net/PetSupplies', '1988-04-01 03:24:01', '2015-10-24 01:54:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (159, 'https://video.miomiyamoto10.biz/AutomotivePartsAccessories', '2014-06-25 02:27:16', '2003-09-08 23:05:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (160, 'http://video.fraca.biz/ToysGames', '1983-06-08 08:21:24', '2016-10-24 18:04:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (161, 'https://drive.lamkl.xyz/ComputersElectronics', '1997-01-08 22:55:19', '2017-02-03 07:13:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (162, 'https://image.lok70.org/CellPhonesAccessories', '1987-04-07 19:12:20', '2012-04-02 12:52:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (163, 'http://drive.millsange202.info/Baby', '1973-01-29 12:18:43', '2015-06-10 10:00:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (164, 'https://video.onkay730.biz/Appliances', '1993-01-04 21:25:30', '2002-04-01 10:27:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (165, 'http://auth.troysilva.com/CollectiblesFineArt', '1980-08-13 20:43:10', '2001-12-08 18:36:54');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (166, 'https://drive.hokyau4.com/SportsOutdoor', '1979-06-28 18:30:38', '2016-04-23 22:44:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (167, 'http://auth.hinaotsu89.co.jp/ToysGames', '1979-01-23 22:58:47', '2003-10-13 07:50:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (168, 'http://image.kazuma08.co.jp/HouseholdKitchenAppliances', '2005-04-24 03:44:24', '2014-03-01 04:01:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (169, 'http://www.kwl.net/MusicalInstrument', '1986-11-20 02:43:40', '2018-12-02 20:30:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (170, 'http://www.maoz94.com/Handcrafts', '2010-03-15 16:59:36', '2004-11-30 02:14:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (171, 'https://image.dianat1225.jp/Handcrafts', '1995-02-15 22:46:24', '2011-06-27 05:10:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (172, 'http://video.ishazu.co.jp/ToysGames', '1983-10-14 09:58:48', '2008-11-17 17:29:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (173, 'http://www.focha.info/BaggageTravelEquipment', '2003-10-04 01:05:27', '2018-11-06 20:24:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (174, 'https://www.silva6.us/BaggageTravelEquipment', '1973-04-02 18:02:42', '2000-07-23 18:28:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (175, 'http://image.vargas6.xyz/Books', '2022-01-12 05:05:18', '2011-11-01 23:52:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (176, 'https://drive.kykoo9.net/Books', '1992-04-13 07:18:07', '2001-01-15 15:35:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (177, 'http://www.wendychavez95.cn/ArtsHandicraftsSewing', '1976-10-07 06:47:31', '2003-06-10 08:05:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (178, 'http://www.miukojima327.net/IndustrialScientificSupplies', '2002-06-12 13:10:20', '2019-07-17 09:42:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (179, 'https://www.wmkao.cn/CDsVinyl', '1979-09-24 18:43:30', '2014-12-21 13:22:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (180, 'http://www.wjesus81.net/HealthBabyCare', '1988-07-14 06:16:24', '2018-07-07 23:03:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (181, 'https://drive.kamyl.biz/Others', '1985-03-16 13:59:17', '2012-09-01 07:06:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (182, 'http://image.zhkong511.com/ToysGames', '1977-12-05 20:55:37', '2016-10-18 16:45:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (183, 'http://www.zitang.us/MusicalInstrument', '1980-02-19 03:12:31', '2000-02-18 01:15:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (184, 'http://drive.ryador4.net/VideoGames', '1978-06-14 15:17:35', '2001-01-18 00:02:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (185, 'http://www.zhgong10.net/HealthBabyCare', '1981-08-29 22:40:05', '2010-01-09 17:42:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (186, 'https://auth.gregoryroberts9.jp/CenturionGardenOutdoor', '1978-12-16 03:01:54', '2012-01-11 20:33:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (187, 'http://image.chiang817.net/Baby', '2000-11-16 06:02:30', '2015-06-25 19:08:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (188, 'http://drive.zhaojie.us/ComputersElectronics', '1973-05-23 13:49:26', '2015-08-10 02:46:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (189, 'http://www.brooksjoe1227.cn/MusicalInstrument', '1986-12-20 23:24:07', '2012-11-27 00:54:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (190, 'http://drive.zitaodong.co.jp/IndustrialScientificSupplies', '2017-06-04 20:32:47', '2002-04-24 13:02:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (191, 'http://video.uryota6.co.jp/SportsOutdoor', '2008-10-28 17:36:23', '2002-05-22 14:11:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (192, 'http://video.miufat.co.jp/CDsVinyl', '1994-11-01 10:02:39', '2016-07-21 08:43:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (193, 'https://image.hazuki00.net/ComputersElectronics', '1973-04-30 07:31:28', '2001-04-19 03:52:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (194, 'http://image.yotsuka.org/SportsOutdoor', '1995-06-22 13:58:45', '2002-08-09 03:19:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (195, 'https://auth.mtw.net/BeautyPersonalCare', '2003-06-30 20:16:26', '2019-09-06 17:12:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (196, 'http://image.fonfa712.co.jp/CDsVinyl', '1985-09-15 11:30:17', '2021-05-15 00:31:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (197, 'https://www.lacl.co.jp/CDsVinyl', '2022-02-07 20:37:43', '2004-06-03 07:32:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (198, 'https://video.fanderson.com/Food', '2012-06-07 01:45:19', '2010-12-13 05:10:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (199, 'http://image.akina812.org/FilmSupplies', '1976-03-02 08:48:10', '2010-05-30 02:24:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (200, 'https://www.ding8.xyz/BeautyPersonalCare', '1992-05-04 23:36:46', '2019-06-30 07:26:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (201, 'http://video.lu99.biz/Appliances', '2008-10-03 18:38:53', '2011-08-10 01:30:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (202, 'http://drive.choi74.net/HouseholdKitchenAppliances', '1988-08-01 03:48:00', '2012-10-24 23:37:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (203, 'https://video.nakanokasum.jp/ComputersElectronics', '1991-11-30 12:33:51', '2008-12-22 20:17:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (204, 'https://www.yuningl.cn/FilmSupplies', '2010-05-09 18:29:52', '2012-04-05 10:26:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (205, 'https://image.itsukikino10.org/Appliances', '1986-09-06 19:20:28', '2003-03-23 06:37:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (206, 'https://video.ziyiwang.co.jp/SportsOutdoor', '1987-12-27 03:48:29', '2017-10-23 03:31:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (207, 'https://video.cms.us/CDsVinyl', '1984-08-28 01:10:32', '2007-04-22 20:10:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (208, 'https://drive.char.com/AutomotivePartsAccessories', '2019-12-03 03:35:44', '2002-03-20 04:30:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (209, 'http://www.yuning51.cn/VideoGames', '1982-02-23 10:34:27', '2010-08-14 18:00:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (210, 'https://image.kazus.cn/ToysGames', '1978-06-23 03:36:32', '2001-06-08 21:41:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (211, 'http://video.jiehongzhong4.us/SportsOutdoor', '1973-12-29 05:19:41', '2018-06-15 15:18:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (212, 'http://video.cypang8.cn/HouseholdKitchenAppliances', '1975-01-12 12:48:24', '2011-06-24 22:22:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (213, 'http://www.mox78.com/AppsGames', '1977-02-24 11:43:54', '2019-11-30 07:03:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (214, 'https://drive.yaolu.us/Baby', '2014-11-07 17:12:47', '2016-07-03 09:14:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (215, 'http://www.zitao73.org/ToysGames', '2013-01-31 20:35:08', '2008-01-20 05:15:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (216, 'https://image.hinfujii.xyz/CenturionGardenOutdoor', '1976-12-29 18:06:51', '2002-09-19 12:09:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (217, 'https://drive.yuna19.jp/Books', '1975-03-12 04:44:55', '2012-10-13 10:56:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (218, 'https://www.lixiao1972.us/Others', '2015-09-30 12:34:13', '2017-01-12 00:55:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (219, 'http://www.itohan.us/Others', '1982-12-20 15:33:00', '2010-03-04 14:57:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (220, 'http://www.okasum78.biz/IndustrialScientificSupplies', '1992-01-12 23:31:05', '2012-08-26 08:13:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (221, 'https://drive.jiex.co.jp/Baby', '1976-05-13 10:08:53', '2015-01-28 03:17:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (222, 'http://image.xiuyingmeng.info/CellPhonesAccessories', '2000-11-26 03:04:15', '2019-04-22 03:51:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (223, 'https://www.fosmark.co.jp/VideoGames', '2017-10-01 09:37:59', '2001-03-13 06:49:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (224, 'http://auth.kato9.biz/FilmSupplies', '2021-05-08 20:33:03', '2004-01-02 18:06:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (225, 'http://www.spencer10.cn/Beauty', '2010-05-10 06:17:05', '2016-08-29 05:45:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (226, 'http://auth.pricech.info/Appliances', '1986-10-24 03:44:25', '2002-07-31 04:47:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (227, 'https://www.graycla.co.jp/Books', '1982-01-18 04:50:46', '2004-06-01 12:12:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (228, 'https://drive.travisjone8.info/ComputersElectronics', '2006-08-18 14:17:56', '2022-04-30 21:55:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (229, 'http://drive.victorperry.com/Books', '2004-02-18 19:00:32', '2014-08-23 02:20:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (230, 'https://www.ryotakobayashi.cn/BeautyPersonalCare', '1984-06-29 02:11:53', '2011-08-07 14:10:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (231, 'http://video.gomezjason1015.info/AppsGames', '2019-04-28 16:15:42', '2000-06-09 13:46:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (232, 'http://video.tkaling5.com/Baby', '1999-07-11 14:20:20', '2007-04-19 05:39:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (233, 'http://video.spencer202.com/HouseholdKitchenAppliances', '1984-11-15 20:17:33', '2005-12-26 14:02:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (234, 'http://image.simf.biz/Appliances', '2000-05-20 01:11:58', '2006-10-10 13:27:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (235, 'https://www.mitsuki.net/PetSupplies', '1998-08-24 01:30:35', '2000-02-04 00:46:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (236, 'http://image.halmich.info/Books', '1986-04-11 12:35:47', '2011-02-25 01:45:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (237, 'http://www.eitaimai8.biz/Others', '1986-08-15 14:44:06', '2012-08-14 17:54:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (238, 'http://drive.halldor.jp/Books', '2014-06-13 03:16:20', '2006-03-31 17:30:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (239, 'https://drive.kwokming15.jp/AutomotivePartsAccessories', '1997-11-10 03:21:56', '2010-09-21 18:07:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (240, 'http://www.yokoren.us/VideoGames', '1998-11-21 16:08:22', '2014-07-01 03:49:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (241, 'https://video.kaming120.info/SportsOutdoor', '1986-11-15 17:06:26', '2006-12-01 16:55:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (242, 'https://image.nakayama827.biz/BeautyPersonalCare', '2020-01-19 08:44:01', '2011-06-22 12:13:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (243, 'http://image.hiutung719.xyz/Food', '2020-03-21 07:01:02', '2018-12-10 07:22:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (244, 'http://www.xidin85.biz/ClothingShoesandJewelry', '2005-07-14 23:57:26', '2018-10-12 12:02:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (245, 'https://image.woodsconnie.com/HouseholdKitchenAppliances', '1994-11-07 15:09:52', '2019-09-17 22:59:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (246, 'https://www.yuzhiyuan.co.jp/Baby', '2007-10-26 12:01:30', '2011-12-16 23:05:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (247, 'https://drive.ming725.co.jp/Beauty', '2003-09-03 13:43:28', '2013-05-24 06:21:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (248, 'https://www.jeff7.com/Baby', '1970-03-17 04:44:40', '2022-06-30 09:44:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (249, 'https://image.caixi2.co.jp/Beauty', '2020-06-24 05:26:17', '2003-02-13 12:31:54');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (250, 'http://drive.hasegawakazuma.com/PetSupplies', '2010-07-10 23:09:10', '2010-10-30 23:03:18');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (251, 'http://www.todd5.xyz/HealthBabyCare', '1982-01-05 12:57:03', '2012-07-28 10:01:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (252, 'http://video.yms.info/Beauty', '1975-09-16 05:06:12', '2000-01-05 10:40:12');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (253, 'https://image.tlt.info/CollectiblesFineArt', '1992-05-16 18:25:03', '2005-06-08 10:21:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (254, 'http://www.zhozh.net/CellPhonesAccessories', '2009-01-14 04:59:56', '2003-03-12 05:24:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (255, 'http://auth.syti.xyz/HealthBabyCare', '1981-04-08 09:30:00', '2005-08-04 22:43:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (256, 'http://auth.jitang.com/Baby', '2014-02-17 17:51:57', '2020-11-13 08:14:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (257, 'http://video.mtakwah.com/PetSupplies', '1976-07-14 07:47:55', '2020-10-02 11:16:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (258, 'http://www.ay1963.org/FilmSupplies', '1990-09-02 12:37:55', '2001-03-14 15:21:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (259, 'https://image.tsubasa1204.us/HouseholdKitchenAppliances', '2002-08-14 09:09:49', '2016-08-04 17:35:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (260, 'https://video.adam45.jp/Appliances', '2011-07-26 16:49:58', '2012-06-24 22:56:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (261, 'http://drive.hernandezjonathan.com/ToolsHomeDecoration', '1972-07-27 04:00:47', '2017-01-29 01:41:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (262, 'https://image.bryanttroy3.jp/HealthBabyCare', '2018-10-15 08:16:47', '2019-06-11 07:19:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (263, 'https://www.wongchunyu4.biz/Beauty', '2005-09-29 19:29:09', '2015-10-27 18:40:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (264, 'http://auth.freemanamy6.info/VideoGames', '1998-06-25 14:18:38', '2020-12-09 18:55:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (265, 'http://video.lacw.cn/ToysGames', '1977-10-26 22:20:47', '2001-12-04 19:43:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (266, 'http://video.pardebbie224.com/AutomotivePartsAccessories', '1991-01-21 15:49:24', '2009-12-20 22:01:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (267, 'https://drive.jiehongt1.cn/Food', '2012-08-20 03:40:34', '2018-12-19 20:31:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (268, 'http://www.klh.info/ToysGames', '1975-09-16 09:52:39', '2021-11-01 23:45:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (269, 'https://drive.mingsng.cn/CellPhonesAccessories', '1981-08-10 18:42:38', '2009-12-16 15:48:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (270, 'https://video.ikkita.com/CollectiblesFineArt', '1973-03-02 17:50:56', '2004-07-31 03:18:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (271, 'https://video.bedougl1998.org/CollectiblesFineArt', '1973-09-23 17:55:58', '2017-06-26 22:19:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (272, 'http://auth.leiz11.biz/CenturionGardenOutdoor', '2006-09-28 12:11:28', '2008-11-11 07:21:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (273, 'https://auth.wurui925.xyz/Food', '1989-08-01 11:31:03', '2020-05-24 03:09:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (274, 'http://auth.yiptl.org/AppsGames', '2005-06-17 02:21:20', '2000-12-25 17:28:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (275, 'https://image.renaono.xyz/Appliances', '2015-05-01 14:52:54', '2009-01-27 08:23:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (276, 'https://drive.mahazu.net/CDsVinyl', '1972-02-09 00:00:27', '2005-05-28 18:26:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (277, 'https://image.gordon603.com/PetSupplies', '1971-09-08 12:15:36', '2003-05-02 08:45:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (278, 'https://auth.okyin10.jp/Handcrafts', '1981-03-26 20:26:34', '2015-02-18 02:41:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (279, 'https://image.matsumoto7.cn/ToolsHomeDecoration', '1986-09-19 20:04:27', '2004-06-08 14:21:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (280, 'http://auth.xiuying9.com/AppsGames', '1985-08-29 17:55:24', '2005-12-12 18:46:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (281, 'http://www.ruiye74.jp/Handcrafts', '1990-05-25 00:09:36', '2021-08-06 17:44:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (282, 'https://www.reynoldsca1012.xyz/MusicalInstrument', '1975-04-26 11:13:12', '2009-02-11 17:44:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (283, 'https://www.zhongzhe.co.jp/AutomotivePartsAccessories', '2020-12-25 04:17:25', '2017-02-20 16:45:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (284, 'https://video.otamio.co.jp/ComputersElectronics', '1972-05-01 05:04:00', '2003-03-05 01:22:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (285, 'http://image.gardnerellen.xyz/IndustrialScientificSupplies', '2014-10-10 19:35:20', '2021-01-02 20:09:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (286, 'https://auth.zx3.cn/CDsVinyl', '2004-02-02 17:08:04', '2021-12-19 10:46:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (287, 'https://drive.lush8.biz/Baby', '2018-09-08 23:17:39', '2006-04-12 11:07:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (288, 'https://auth.fslau87.xyz/MusicalInstrument', '1975-06-17 04:06:59', '2004-08-17 12:53:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (289, 'http://video.carmes2.net/CenturionGardenOutdoor', '1999-02-13 00:24:51', '2004-12-30 14:09:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (290, 'https://video.letszhin.org/AutomotivePartsAccessories', '2013-02-05 08:41:26', '2003-07-04 07:05:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (291, 'https://drive.reed9.us/MusicalInstrument', '1972-11-11 06:05:23', '2013-01-22 01:25:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (292, 'https://video.dinzhiyuan508.net/BaggageTravelEquipment', '1982-12-05 11:47:09', '2006-08-05 06:22:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (293, 'https://drive.llm.net/PetSupplies', '2008-05-11 20:58:49', '2004-04-14 20:21:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (294, 'http://drive.anitaharr1961.biz/IndustrialScientificSupplies', '1971-11-11 17:25:23', '2021-07-23 22:27:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (295, 'http://video.hsuantc.xyz/ClothingShoesandJewelry', '1997-08-31 20:16:13', '2004-08-15 01:58:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (296, 'http://video.wlk.org/SportsOutdoor', '1999-11-03 06:49:42', '2000-03-15 03:08:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (297, 'https://drive.johnnypow.co.jp/ClothingShoesandJewelry', '1973-07-24 22:51:39', '2006-08-03 22:08:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (298, 'https://drive.stonekathleen.biz/ToysGames', '1972-12-29 10:58:53', '2020-10-13 07:18:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (299, 'https://video.ishii82.xyz/CenturionGardenOutdoor', '2005-01-25 11:39:20', '2002-06-12 04:14:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (300, 'https://image.kamantam.us/FilmSupplies', '1976-11-19 05:23:07', '2008-04-26 01:43:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (301, 'https://video.lanwu4.jp/PetSupplies', '1976-06-26 08:33:18', '2016-08-20 22:27:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (302, 'http://image.twmeng.jp/SportsOutdoor', '2016-07-30 12:11:46', '2000-05-04 19:09:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (303, 'http://image.xiarui.xyz/IndustrialScientificSupplies', '2011-10-03 07:51:53', '2013-04-19 12:38:57');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (304, 'http://auth.lu208.org/ArtsHandicraftsSewing', '1975-08-31 15:32:49', '2012-08-29 12:12:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (305, 'https://drive.hoyinpang5.co.jp/Food', '2003-06-25 07:36:22', '2014-08-13 02:43:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (306, 'https://www.wangz.jp/HouseholdKitchenAppliances', '1990-11-20 06:56:34', '2021-07-24 21:26:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (307, 'https://www.fong74.net/CellPhonesAccessories', '2017-11-19 09:48:01', '2018-03-23 06:35:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (308, 'http://drive.dj613.cn/BaggageTravelEquipment', '1991-02-04 11:30:26', '2004-09-08 10:29:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (309, 'https://video.maola.cn/Handcrafts', '1991-09-23 11:54:44', '2007-04-20 00:10:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (310, 'http://www.shi1944.co.jp/ToolsHomeDecoration', '2009-02-05 07:52:10', '2020-01-22 20:11:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (311, 'https://video.manurome.xyz/HealthBabyCare', '1996-09-14 11:07:27', '2008-02-07 19:56:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (312, 'https://video.jialun77.xyz/CollectiblesFineArt', '1975-07-14 17:45:38', '2008-09-04 13:44:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (313, 'http://www.houyunxi426.info/CenturionGardenOutdoor', '2002-12-25 15:04:12', '2008-03-30 21:23:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (314, 'http://drive.yamatosugiyama.us/MusicalInstrument', '2020-06-16 10:47:32', '2003-12-29 10:47:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (315, 'https://image.shihanwu.xyz/MusicalInstrument', '1981-01-15 07:28:36', '2021-08-01 09:10:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (316, 'https://video.konor.biz/AutomotivePartsAccessories', '1971-12-24 21:03:20', '2001-12-20 00:28:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (317, 'https://www.zhiyuanluo4.info/Beauty', '1989-02-14 14:10:12', '2021-03-20 08:16:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (318, 'https://image.pachoyee44.com/Baby', '2018-03-11 11:48:57', '2017-02-13 06:27:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (319, 'http://www.dixonjua.org/ComputersElectronics', '2017-12-01 09:40:26', '2020-08-29 16:43:54');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (320, 'https://image.wrightcarrie602.org/Others', '1987-08-11 02:32:55', '2001-11-21 23:08:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (321, 'http://image.tzhiyuan.jp/Baby', '2010-03-22 05:23:57', '2000-12-09 18:17:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (322, 'http://video.kaitnaka404.info/SportsOutdoor', '1994-07-02 12:18:41', '2022-01-14 22:38:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (323, 'https://drive.tinlokwan.jp/CDsVinyl', '1995-04-16 05:24:35', '2006-09-09 04:14:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (324, 'http://drive.mskam.org/AutomotivePartsAccessories', '2016-12-21 18:52:34', '2013-05-02 05:32:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (325, 'http://video.min.co.jp/AutomotivePartsAccessories', '1996-09-07 08:40:49', '2016-03-08 03:34:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (326, 'http://drive.rurodriguez.org/Food', '1978-01-11 21:56:53', '2010-10-07 06:07:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (327, 'http://video.maojiehong10.cn/Books', '2002-05-25 03:18:28', '2004-06-23 00:26:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (328, 'http://www.ziyisu.org/ClothingShoesandJewelry', '1978-09-13 10:19:30', '2018-02-01 03:04:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (329, 'https://auth.indaich.us/SportsOutdoor', '2001-06-21 11:28:13', '2012-08-24 03:41:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (330, 'https://video.millpauline.jp/CDsVinyl', '2006-02-22 01:33:47', '2007-05-01 23:58:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (331, 'https://drive.ryotanoguchi2007.net/CenturionGardenOutdoor', '1972-04-13 01:06:56', '2015-04-25 21:56:54');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (332, 'http://image.lijiehong.com/PetSupplies', '2016-10-06 00:39:47', '2003-04-06 17:52:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (333, 'http://drive.mazitao.org/CollectiblesFineArt', '1985-06-22 15:51:46', '2018-03-23 08:36:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (334, 'http://drive.chowchoyee.info/VideoGames', '1991-07-01 17:02:37', '2016-07-05 20:58:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (335, 'http://drive.shihan66.org/ComputersElectronics', '1972-05-09 20:10:24', '2019-02-15 14:48:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (336, 'http://image.km1.net/AppsGames', '2016-03-19 01:18:34', '2009-12-18 12:50:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (337, 'https://image.jackpatterson.org/Beauty', '2020-03-23 10:53:09', '2019-01-03 22:07:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (338, 'https://video.mionaka201.cn/ClothingShoesandJewelry', '2017-06-24 18:50:20', '2000-03-07 22:58:12');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (339, 'https://image.denises.co.jp/Books', '2002-04-03 11:26:20', '2002-10-09 22:04:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (340, 'https://video.konominato.co.jp/VideoGames', '1977-03-07 18:26:54', '2003-06-12 23:07:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (341, 'https://image.astevens.info/HouseholdKitchenAppliances', '2018-08-18 20:54:01', '2008-09-28 23:10:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (342, 'http://video.tamwingfat.org/BaggageTravelEquipment', '1988-08-27 04:40:01', '2014-03-04 05:41:56');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (343, 'https://video.cox918.jp/HealthBabyCare', '1977-10-12 15:38:14', '2006-08-06 10:01:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (344, 'https://drive.ellsteven.com/ArtsHandicraftsSewing', '1995-03-19 10:31:27', '2018-07-01 03:44:56');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (345, 'https://video.yunxix93.co.jp/IndustrialScientificSupplies', '1991-07-22 17:42:40', '2020-02-26 11:54:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (346, 'http://video.cheuntl.net/MusicalInstrument', '1987-09-16 01:38:39', '2015-12-21 13:17:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (347, 'https://drive.meng8.net/Food', '1996-06-05 21:10:48', '2000-04-17 17:41:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (348, 'https://image.xiaoming1.xyz/AppsGames', '1975-07-25 10:53:27', '2001-08-27 08:10:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (349, 'https://www.ancrawford321.org/Others', '2016-10-23 19:50:43', '2000-05-01 14:30:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (350, 'https://www.crobe514.org/ComputersElectronics', '1989-03-30 03:22:33', '2006-05-06 03:21:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (351, 'http://auth.janetboyd9.com/Others', '1988-04-02 19:59:42', '2010-03-16 23:43:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (352, 'https://auth.myuto7.net/CollectiblesFineArt', '2007-12-16 03:34:55', '2004-02-01 21:35:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (353, 'http://auth.tftse.cn/PetSupplies', '2006-07-01 01:31:46', '2011-09-12 14:53:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (354, 'https://auth.martinezthomas5.org/ToysGames', '2012-12-05 15:27:09', '2018-03-27 09:31:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (355, 'http://video.mengtw309.biz/PetSupplies', '2020-10-27 03:00:05', '2012-05-25 05:11:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (356, 'https://www.nakajd430.biz/Books', '1974-11-13 22:39:03', '2013-05-01 20:35:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (357, 'https://image.yuning2.info/SportsOutdoor', '1988-09-25 18:36:05', '2008-03-17 20:32:56');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (358, 'http://drive.petersonk.net/ClothingShoesandJewelry', '1996-09-28 22:31:57', '2002-07-05 15:44:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (359, 'http://www.yuyokoyama.net/CellPhonesAccessories', '1994-09-27 02:42:31', '2020-06-13 08:13:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (360, 'https://auth.kksi1.info/ComputersElectronics', '1987-03-13 18:03:09', '2010-12-08 01:01:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (361, 'https://auth.rodrim.cn/Beauty', '2010-05-17 04:03:58', '2011-01-08 09:51:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (362, 'http://www.ma1.xyz/CollectiblesFineArt', '1974-01-22 07:29:01', '2000-10-11 20:08:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (363, 'https://www.greennathan10.jp/HealthBabyCare', '1999-01-18 23:24:32', '2002-08-01 22:49:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (364, 'http://image.choisumwing.biz/FilmSupplies', '1977-11-16 15:35:14', '2001-12-08 03:54:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (365, 'http://video.chimingh.us/Others', '1997-12-11 17:54:45', '2016-01-03 07:07:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (366, 'https://auth.hokyaumak506.us/MusicalInstrument', '1990-03-08 05:15:01', '2010-05-04 18:15:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (367, 'http://auth.xiaoming8.xyz/PetSupplies', '1980-04-25 10:20:46', '2000-07-25 11:21:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (368, 'http://video.songzitao.xyz/HouseholdKitchenAppliances', '1983-10-27 05:08:09', '2006-11-12 20:54:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (369, 'https://drive.nky.biz/Baby', '1998-04-22 10:16:42', '2006-05-21 02:00:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (370, 'http://video.kathleen9.cn/Food', '1982-11-04 16:51:46', '2004-11-03 02:17:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (371, 'https://image.onnatang1.xyz/IndustrialScientificSupplies', '2004-04-01 10:05:42', '2022-05-07 15:17:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (372, 'https://www.janice2.org/Others', '2013-11-13 16:44:43', '2020-05-15 09:12:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (373, 'http://drive.okamoto2017.cn/CenturionGardenOutdoor', '1988-07-19 18:56:46', '2004-08-11 17:41:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (374, 'https://auth.momoe806.net/BeautyPersonalCare', '1975-10-20 10:23:05', '2002-08-03 21:44:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (375, 'http://video.maitakeuchi.xyz/PetSupplies', '2011-08-23 23:36:57', '2021-09-13 10:49:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (376, 'https://auth.seiko1.biz/VideoGames', '1982-01-25 23:14:26', '2003-09-27 03:04:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (377, 'http://video.saiwingtong.cn/CellPhonesAccessories', '1995-03-07 01:36:47', '2009-05-31 10:32:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (378, 'https://auth.mawingfat1007.xyz/ClothingShoesandJewelry', '2004-10-26 01:03:35', '2016-02-12 03:46:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (379, 'http://video.waiman57.cn/MusicalInstrument', '1996-03-09 04:16:42', '2005-02-08 16:53:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (380, 'https://image.myunxi15.cn/Others', '1986-08-22 13:22:16', '2022-01-25 16:34:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (381, 'http://image.masuaya.us/Beauty', '2022-02-16 08:07:27', '2008-07-09 14:38:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (382, 'http://www.zpeng323.co.jp/Appliances', '1997-11-19 10:02:26', '2013-07-03 16:13:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (383, 'https://image.kaaoshi8.us/IndustrialScientificSupplies', '1975-08-01 11:11:26', '2014-01-28 03:13:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (384, 'http://auth.otsukami.co.jp/FilmSupplies', '2002-01-31 18:33:59', '2012-11-12 04:38:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (385, 'https://image.yunm10.net/Others', '2018-08-19 17:21:13', '2004-08-16 00:21:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (386, 'http://www.yunxixi.us/VideoGames', '2019-04-26 10:04:50', '2022-06-18 05:48:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (387, 'http://image.tinlong.xyz/CDsVinyl', '1983-02-21 21:34:00', '2006-03-09 08:15:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (388, 'http://www.misakiyoshida629.biz/IndustrialScientificSupplies', '1976-06-30 11:12:27', '2021-01-14 01:38:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (389, 'http://video.ykent.info/ClothingShoesandJewelry', '1978-01-19 18:26:25', '2008-04-27 08:33:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (390, 'http://drive.kazuma521.xyz/PetSupplies', '2008-10-29 23:39:17', '2009-12-02 04:03:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (391, 'http://auth.brias.xyz/ArtsHandicraftsSewing', '1982-04-02 02:48:45', '2013-12-22 16:04:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (392, 'https://drive.wingfat1951.xyz/CenturionGardenOutdoor', '1970-05-19 07:41:42', '2001-04-18 18:15:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (393, 'http://www.shilu.info/ToysGames', '1991-05-28 16:12:37', '2001-08-05 07:01:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (394, 'https://www.ss02.co.jp/FilmSupplies', '1992-03-07 23:10:26', '2017-04-18 21:01:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (395, 'https://video.wonok.jp/FilmSupplies', '1986-01-27 13:51:04', '2014-06-10 20:31:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (396, 'https://www.pengyunxi8.org/VideoGames', '1995-12-06 02:31:40', '2018-10-03 20:31:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (397, 'https://auth.layf1006.com/PetSupplies', '2008-10-01 15:54:26', '2018-04-08 20:52:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (398, 'http://www.martin130.biz/HouseholdKitchenAppliances', '2015-03-01 05:09:45', '2003-08-23 04:49:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (399, 'http://drive.chokm.com/HouseholdKitchenAppliances', '1989-02-24 07:49:41', '2016-07-11 08:55:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (400, 'http://image.yuningyan.com/ComputersElectronics', '2008-08-25 13:02:57', '2009-05-04 22:10:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (401, 'https://video.michaelgibson.cn/BaggageTravelEquipment', '2016-02-09 23:03:55', '2000-06-14 08:23:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (402, 'http://image.shibatahikaru.us/ToysGames', '2004-04-15 21:20:12', '2004-03-04 00:49:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (403, 'http://drive.tanakak105.jp/ClothingShoesandJewelry', '2015-07-13 06:34:22', '2012-06-15 02:08:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (404, 'http://image.yuncy1985.net/FilmSupplies', '1993-12-20 11:06:27', '2004-04-13 11:52:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (405, 'http://auth.vincem1.jp/Beauty', '2019-04-30 10:27:45', '2017-08-27 09:09:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (406, 'https://auth.chuchiyuen.co.jp/BeautyPersonalCare', '2006-07-11 08:28:59', '2011-06-06 07:15:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (407, 'https://drive.saumanchow5.org/ToysGames', '2021-11-28 01:36:20', '2003-12-05 05:31:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (408, 'http://video.nakdaichi.org/ComputersElectronics', '1970-09-08 05:15:32', '2018-06-29 13:38:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (409, 'https://www.anqicu.co.jp/Books', '2019-04-25 18:33:34', '2020-01-08 23:01:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (410, 'https://auth.noyuito05.xyz/CellPhonesAccessories', '2021-09-06 05:43:58', '2017-02-19 23:44:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (411, 'https://www.kg509.co.jp/IndustrialScientificSupplies', '2002-02-21 17:05:37', '2015-05-09 00:43:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (412, 'https://www.zhennyin.us/MusicalInstrument', '1980-08-09 10:37:54', '2003-09-07 18:03:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (413, 'http://image.rup.com/Appliances', '2011-07-12 15:30:38', '2021-02-27 23:15:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (414, 'https://image.shihansh.com/Appliances', '1996-12-30 05:01:26', '2001-12-10 12:49:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (415, 'https://auth.xiuying2.com/AutomotivePartsAccessories', '1997-03-16 08:14:02', '2007-10-26 16:57:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (416, 'http://drive.xiuyixu.biz/ArtsHandicraftsSewing', '2015-01-20 17:08:54', '2022-04-25 03:07:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (417, 'https://auth.wilallen.org/VideoGames', '2003-04-24 07:32:37', '2013-02-12 16:16:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (418, 'http://www.lohm.xyz/SportsOutdoor', '1996-09-04 03:54:13', '2012-04-04 01:07:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (419, 'http://image.karyanyue16.biz/AppsGames', '1973-08-26 08:46:31', '2001-03-27 22:46:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (420, 'http://www.uedasakura.info/Beauty', '1995-02-10 01:39:24', '2008-11-18 22:45:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (421, 'http://image.tfng9.cn/SportsOutdoor', '2012-12-25 23:18:34', '2013-01-12 14:35:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (422, 'http://drive.xiuyingyan7.cn/Food', '2004-11-24 01:22:16', '2010-11-27 05:34:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (423, 'http://video.lujiang.com/BaggageTravelEquipment', '1985-09-22 06:21:42', '2017-07-19 06:41:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (424, 'http://video.gifrancis.jp/Handcrafts', '1999-05-10 19:24:45', '2002-08-10 13:44:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (425, 'https://video.kudo1.us/MusicalInstrument', '1988-05-27 16:49:59', '2013-03-03 07:44:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (426, 'https://www.kamtakwah9.us/ToysGames', '2018-11-18 13:13:54', '2005-01-15 05:05:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (427, 'http://www.watsonther.xyz/HealthBabyCare', '2004-08-11 11:28:14', '2021-07-24 12:22:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (428, 'https://video.suzukikasumi.net/CDsVinyl', '2017-09-01 00:54:47', '2013-07-02 14:08:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (429, 'http://auth.harriseleanor.xyz/VideoGames', '1970-10-06 02:52:36', '2013-06-02 20:03:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (430, 'https://www.pricelillian.org/ToysGames', '1998-11-18 20:40:40', '2016-09-30 21:35:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (431, 'https://drive.onnal.org/BaggageTravelEquipment', '1973-07-23 03:25:09', '2000-12-29 21:13:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (432, 'http://video.yulu9.co.jp/Food', '2010-10-27 20:27:15', '2004-03-02 00:55:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (433, 'http://auth.kktsang.com/ComputersElectronics', '1988-06-23 09:13:30', '2006-12-03 23:09:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (434, 'https://video.jiaya.info/Baby', '2013-07-26 09:03:42', '2008-12-07 08:36:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (435, 'https://www.goto1984.com/Food', '2009-07-24 05:47:01', '2011-10-20 05:36:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (436, 'https://video.yu2006.jp/ToolsHomeDecoration', '1975-03-10 01:29:49', '2004-06-05 09:34:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (437, 'http://image.rena5.net/FilmSupplies', '1983-03-07 20:12:43', '2006-01-15 06:05:56');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (438, 'http://auth.hawkiw.xyz/Others', '1994-04-28 03:56:37', '2014-03-26 00:59:57');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (439, 'https://auth.wailamchang.org/BaggageTravelEquipment', '2002-03-07 11:53:10', '2018-01-23 13:36:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (440, 'http://www.edward1.us/HouseholdKitchenAppliances', '1991-07-30 05:57:07', '2014-04-05 22:05:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (441, 'http://video.konghy2.org/IndustrialScientificSupplies', '1970-10-16 12:15:18', '2014-03-10 10:43:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (442, 'https://video.fakina4.co.jp/Handcrafts', '2019-04-08 18:16:33', '2007-08-25 09:42:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (443, 'https://image.jiangy7.net/CellPhonesAccessories', '1971-05-06 11:15:34', '2005-05-13 00:36:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (444, 'http://video.kwkaming.net/Handcrafts', '1979-04-24 07:26:44', '2021-09-10 01:44:56');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (445, 'http://drive.daterry.info/CollectiblesFineArt', '1980-12-05 17:24:28', '2007-04-23 12:48:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (446, 'https://video.yungkwokming8.xyz/Beauty', '1985-08-06 15:13:50', '2001-12-23 09:42:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (447, 'http://auth.hsa914.org/VideoGames', '2005-05-10 15:34:30', '2021-05-09 18:00:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (448, 'http://image.tay.net/FilmSupplies', '2003-10-27 00:23:09', '2019-03-30 18:55:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (449, 'http://video.fwaiman1110.biz/Others', '1980-09-06 11:12:15', '2010-11-10 08:06:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (450, 'http://www.laikwok.org/AppsGames', '2001-09-06 02:05:19', '2018-05-16 02:42:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (451, 'https://drive.koowh.info/Handcrafts', '2010-10-26 22:37:49', '2003-06-19 22:08:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (452, 'https://auth.choi2.cn/HealthBabyCare', '2013-12-04 23:41:01', '2009-11-10 18:21:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (453, 'http://image.kasuminakamori.co.jp/CenturionGardenOutdoor', '1979-07-29 00:14:57', '2014-02-11 12:50:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (454, 'http://video.medina812.us/FilmSupplies', '1971-03-05 03:15:55', '2003-04-30 16:58:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (455, 'http://auth.panx212.co.jp/Beauty', '1980-07-18 15:50:00', '2014-10-25 09:54:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (456, 'https://auth.tinwingma.biz/CollectiblesFineArt', '1997-04-23 08:05:19', '2021-09-04 16:10:54');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (457, 'https://video.zhl4.net/BeautyPersonalCare', '2013-11-24 08:09:54', '2021-04-10 09:35:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (458, 'https://video.hsuanwaih3.info/FilmSupplies', '1984-03-07 02:26:54', '2019-03-27 06:34:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (459, 'http://auth.bjenkins.jp/Books', '1998-07-15 08:16:09', '2008-02-13 09:37:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (460, 'https://video.kasumi909.jp/IndustrialScientificSupplies', '1986-11-04 09:53:49', '2010-05-21 01:49:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (461, 'http://www.kinan.jp/HealthBabyCare', '2011-08-23 15:07:57', '2022-07-09 13:25:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (462, 'http://image.melvimitc322.net/FilmSupplies', '1978-11-05 22:46:24', '2008-10-28 17:44:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (463, 'http://drive.gz74.us/ToolsHomeDecoration', '2020-08-24 19:10:57', '2007-02-12 07:54:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (464, 'https://image.wch.cn/VideoGames', '1977-02-10 17:21:21', '2007-09-19 03:36:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (465, 'http://www.hayes1102.info/IndustrialScientificSupplies', '2005-07-27 01:14:58', '2021-04-08 02:20:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (466, 'http://auth.larry4.biz/ClothingShoesandJewelry', '2007-11-15 20:07:13', '2012-08-20 02:52:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (467, 'https://image.lu8.org/VideoGames', '2001-12-13 06:33:02', '2006-08-08 15:32:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (468, 'http://www.yuitoka.us/ToysGames', '2005-10-12 02:21:38', '2019-12-20 05:41:12');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (469, 'http://video.rj930.xyz/ArtsHandicraftsSewing', '1994-10-17 12:17:24', '2015-10-16 20:53:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (470, 'https://www.jacksonbet.us/Handcrafts', '2002-11-24 01:30:58', '2012-03-25 01:33:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (471, 'https://video.daichnakamori.jp/MusicalInstrument', '2012-05-10 04:01:54', '2017-04-19 10:51:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (472, 'https://drive.scotg5.us/Handcrafts', '2005-08-12 22:54:36', '2015-03-17 23:04:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (473, 'http://drive.ayato424.org/ComputersElectronics', '1997-09-09 23:30:57', '2000-01-24 22:36:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (474, 'http://image.denell.com/SportsOutdoor', '2019-02-18 13:14:44', '2010-07-25 07:53:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (475, 'http://www.alfjenkins.xyz/Others', '2021-03-06 18:18:05', '2009-12-14 02:30:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (476, 'https://www.lauhokyau.cn/SportsOutdoor', '1978-04-07 21:29:32', '2013-04-23 09:52:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (477, 'http://drive.taylorda204.info/ClothingShoesandJewelry', '2020-10-21 15:10:54', '2006-07-17 04:32:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (478, 'http://www.shinomaed.cn/Handcrafts', '2001-11-18 18:54:33', '2018-02-02 18:23:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (479, 'https://drive.yunilin10.cn/AppsGames', '1975-04-18 03:14:54', '2013-10-23 10:47:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (480, 'http://video.zilu7.org/SportsOutdoor', '2016-04-23 21:33:41', '2011-05-29 21:25:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (481, 'https://video.alvagrace.xyz/IndustrialScientificSupplies', '1975-01-23 19:44:06', '2013-06-05 06:46:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (482, 'https://video.yin8.xyz/CollectiblesFineArt', '1998-11-05 07:36:59', '2016-08-16 02:46:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (483, 'https://auth.aokikas.org/MusicalInstrument', '1993-01-04 07:55:07', '2016-09-15 17:21:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (484, 'https://video.hea.us/Handcrafts', '1977-03-08 18:52:29', '2022-01-11 03:58:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (485, 'https://drive.pg9.us/ArtsHandicraftsSewing', '2000-12-27 21:10:12', '2003-03-26 12:12:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (486, 'https://image.rachelcollins.co.jp/Baby', '2016-08-16 07:35:43', '2008-01-27 19:11:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (487, 'http://image.pachiming51.info/FilmSupplies', '2017-04-29 17:10:14', '2022-03-05 11:33:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (488, 'https://drive.gongzhiyu7.cn/ComputersElectronics', '2009-11-13 19:16:13', '2003-08-14 19:23:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (489, 'https://auth.tinloklui1209.us/FilmSupplies', '2010-08-23 22:53:58', '2005-12-21 13:14:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (490, 'http://auth.ha624.co.jp/CenturionGardenOutdoor', '2000-11-22 08:24:48', '2012-11-27 15:43:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (491, 'http://image.yfa91.net/BeautyPersonalCare', '2019-09-27 05:51:20', '2011-03-19 05:31:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (492, 'http://auth.hendersonta.cn/CDsVinyl', '1990-06-14 18:02:36', '2008-10-23 18:09:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (493, 'https://auth.tang121.us/MusicalInstrument', '2005-04-07 17:56:00', '2017-12-30 13:09:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (494, 'https://image.alu.net/CollectiblesFineArt', '1981-12-24 05:14:35', '2007-12-20 20:43:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (495, 'http://image.zhouz.xyz/PetSupplies', '1980-11-20 02:30:49', '2018-01-19 20:55:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (496, 'http://video.xiaren317.org/Food', '1970-01-19 01:54:08', '2003-04-13 04:10:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (497, 'http://drive.huchunyu.biz/BeautyPersonalCare', '1980-03-26 02:37:58', '2007-06-04 02:37:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (498, 'http://image.han1.info/PetSupplies', '2014-03-07 13:06:59', '2020-10-17 05:12:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (499, 'http://video.nicoles.biz/CellPhonesAccessories', '2018-06-27 22:16:46', '2011-11-06 04:36:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (500, 'http://drive.hoyinsi.org/AppsGames', '1982-07-03 19:39:25', '2018-02-23 03:57:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (501, 'http://auth.chekl.us/ToysGames', '1985-04-29 14:02:42', '2017-06-26 20:19:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (502, 'http://video.yamatos2.net/Baby', '1997-09-20 05:50:27', '2017-05-17 06:47:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (503, 'https://auth.konikki.com/SportsOutdoor', '2019-05-01 03:54:27', '2001-02-01 00:58:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (504, 'https://www.tianl.co.jp/CellPhonesAccessories', '1989-12-06 03:15:52', '2018-05-20 03:46:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (505, 'http://www.ziyi55.info/AutomotivePartsAccessories', '2012-05-20 03:32:41', '2016-10-23 07:07:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (506, 'https://auth.shehht9.com/BeautyPersonalCare', '1971-12-29 15:16:15', '2014-06-03 10:58:57');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (507, 'https://image.yuningt.co.jp/PetSupplies', '1978-09-04 14:18:18', '2011-02-20 16:14:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (508, 'https://auth.theodore1011.biz/Baby', '1995-01-21 17:49:25', '2020-03-13 02:03:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (509, 'https://video.jialun2.xyz/ArtsHandicraftsSewing', '1970-10-10 20:37:35', '2010-01-19 20:26:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (510, 'http://image.wmkao.cn/Books', '1984-07-04 19:35:27', '2011-12-05 06:56:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (511, 'http://www.syuto.co.jp/ArtsHandicraftsSewing', '1995-04-17 13:33:58', '2010-05-19 23:41:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (512, 'http://video.mnana67.jp/BaggageTravelEquipment', '1973-02-02 12:17:50', '2013-11-22 00:02:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (513, 'https://drive.patel420.net/IndustrialScientificSupplies', '2008-01-05 07:09:55', '2014-10-06 11:03:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (514, 'http://www.yamashitad.net/VideoGames', '2003-03-12 06:17:20', '2020-02-27 09:22:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (515, 'http://video.kwokwing621.cn/IndustrialScientificSupplies', '1987-06-25 15:03:31', '2019-09-21 02:21:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (516, 'https://video.rivera1013.net/ToysGames', '1999-07-25 02:11:35', '2011-08-14 12:22:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (517, 'https://image.yamashitarik.net/Beauty', '1985-11-19 11:10:03', '2005-04-24 20:05:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (518, 'https://www.lianqi.cn/ComputersElectronics', '1973-09-24 02:31:44', '2018-12-26 01:43:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (519, 'http://drive.simmons1.info/IndustrialScientificSupplies', '2000-02-09 00:37:29', '2005-09-16 02:06:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (520, 'http://www.yunxgu54.net/Books', '1970-04-18 11:04:13', '2009-06-24 13:04:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (521, 'http://auth.jasonva.com/IndustrialScientificSupplies', '2020-02-23 21:08:17', '2010-09-21 15:36:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (522, 'http://video.chows.com/IndustrialScientificSupplies', '1983-06-03 11:14:03', '2017-04-13 18:10:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (523, 'https://video.kafaiau7.cn/Appliances', '1998-08-07 17:00:45', '2014-04-16 06:17:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (524, 'https://video.adamssandra.cn/ClothingShoesandJewelry', '2013-04-30 06:01:02', '2003-06-03 07:22:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (525, 'https://www.kwan8.cn/CenturionGardenOutdoor', '1993-07-22 11:15:47', '2019-06-28 15:22:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (526, 'http://drive.sakurin525.co.jp/Appliances', '1991-07-22 23:54:54', '2019-06-28 20:29:12');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (527, 'http://auth.arseiko.org/BaggageTravelEquipment', '2012-07-17 08:16:39', '2003-12-09 02:15:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (528, 'https://video.inryota1976.net/Handcrafts', '2010-09-18 22:46:54', '2010-04-28 05:20:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (529, 'http://image.auwm811.biz/AutomotivePartsAccessories', '2001-09-23 12:49:20', '2014-12-20 00:27:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (530, 'http://video.misakihash10.net/IndustrialScientificSupplies', '2014-10-08 13:32:58', '2021-09-20 19:05:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (531, 'https://www.zitaole15.biz/ClothingShoesandJewelry', '1988-09-26 12:43:39', '2012-11-06 00:45:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (532, 'http://image.yuenhokyau.co.jp/SportsOutdoor', '2000-06-20 19:36:05', '2006-07-16 05:11:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (533, 'https://video.earmar7.us/CenturionGardenOutdoor', '1971-03-11 15:52:24', '2005-08-20 13:22:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (534, 'https://video.elizabethgra10.co.jp/Baby', '1983-04-25 08:53:23', '2007-06-01 09:06:57');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (535, 'https://image.hikarikoyam.com/HealthBabyCare', '2001-07-27 12:28:28', '2003-12-15 01:25:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (536, 'https://drive.yuito627.biz/ArtsHandicraftsSewing', '2020-04-07 09:57:39', '2004-12-19 03:16:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (537, 'http://auth.carmenro1980.biz/BaggageTravelEquipment', '2006-02-09 15:29:30', '2015-07-15 23:05:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (538, 'http://www.waiyee1230.xyz/CenturionGardenOutdoor', '1971-05-09 08:21:25', '2003-09-28 08:27:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (539, 'http://auth.fod8.us/CenturionGardenOutdoor', '2015-12-08 11:59:02', '2014-02-27 00:06:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (540, 'http://drive.lidun.org/BaggageTravelEquipment', '1996-08-20 00:24:16', '2016-02-24 08:06:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (541, 'https://auth.liangrui.xyz/FilmSupplies', '1988-06-10 01:59:38', '2000-05-29 11:56:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (542, 'http://drive.xiaomhe1105.us/CellPhonesAccessories', '2011-10-13 13:51:05', '2017-10-25 21:40:12');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (543, 'https://auth.zhennyan.cn/ComputersElectronics', '1981-06-15 07:44:54', '2005-09-30 17:36:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (544, 'http://auth.ayanotakad.cn/Appliances', '1972-07-13 19:08:12', '2001-07-12 20:49:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (545, 'https://drive.chungyinto405.com/IndustrialScientificSupplies', '1980-04-10 13:50:56', '2015-05-04 17:26:57');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (546, 'https://www.riyamazaki98.xyz/ToysGames', '1970-11-28 01:31:55', '2000-04-30 00:35:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (547, 'https://image.chiming14.org/CenturionGardenOutdoor', '1982-12-05 19:47:53', '2009-09-01 09:07:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (548, 'https://www.etake.us/HealthBabyCare', '1999-01-19 16:16:58', '2001-05-09 02:48:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (549, 'https://drive.mt8.biz/PetSupplies', '1994-07-15 02:59:42', '2018-03-26 04:44:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (550, 'http://video.tinwinglee.org/CDsVinyl', '2020-06-26 15:06:57', '2020-09-21 05:00:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (551, 'http://video.philshawn4.info/ClothingShoesandJewelry', '2006-11-12 07:41:17', '2012-05-09 13:27:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (552, 'https://video.jsalazar2010.net/FilmSupplies', '1998-05-03 15:13:46', '2014-11-10 21:18:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (553, 'https://auth.hikaruishikawa8.xyz/HealthBabyCare', '2006-01-02 01:00:18', '2011-11-03 12:56:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (554, 'https://image.anderd08.info/HouseholdKitchenAppliances', '2000-10-22 02:17:09', '2006-10-10 03:38:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (555, 'http://www.hikakoy.com/CenturionGardenOutdoor', '1997-02-12 23:46:18', '2006-10-14 09:15:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (556, 'http://video.anqi72.co.jp/Others', '1992-09-06 18:54:46', '2011-02-19 11:11:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (557, 'http://drive.chunlingling82.info/Appliances', '1981-02-02 11:48:57', '2018-10-10 18:13:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (558, 'https://image.xu516.com/MusicalInstrument', '1990-05-17 03:38:22', '2016-05-04 19:51:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (559, 'http://auth.chimingtong.org/CDsVinyl', '1999-02-18 22:03:33', '2010-10-13 11:00:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (560, 'http://image.simpsph62.biz/ArtsHandicraftsSewing', '2001-04-24 15:36:06', '2022-04-09 19:50:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (561, 'https://www.rhowar302.info/ToysGames', '2013-02-08 10:50:45', '2022-02-26 05:17:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (562, 'https://image.meng8.co.jp/PetSupplies', '2005-04-15 11:49:25', '2003-04-16 21:02:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (563, 'http://www.robedebor.xyz/BeautyPersonalCare', '2013-05-29 16:55:28', '2022-03-26 18:22:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (564, 'http://image.taoj.biz/AppsGames', '2019-05-29 05:28:32', '2011-10-03 00:58:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (565, 'https://image.yutoarim.co.jp/Books', '2020-07-05 04:51:47', '2019-01-29 22:50:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (566, 'https://video.zeng814.info/ArtsHandicraftsSewing', '1973-04-12 16:58:44', '2017-03-02 23:04:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (567, 'https://video.nchiuwai.cn/BaggageTravelEquipment', '1978-09-06 19:40:46', '2006-03-13 00:43:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (568, 'http://www.ming04.com/HealthBabyCare', '1986-12-27 02:11:26', '2017-07-27 18:15:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (569, 'http://drive.yamotsuka.info/PetSupplies', '2000-10-13 10:07:04', '2003-06-01 22:45:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (570, 'http://image.yamassa.us/MusicalInstrument', '1983-05-27 07:56:28', '2017-06-22 01:58:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (571, 'https://auth.yudu.co.jp/HouseholdKitchenAppliances', '1986-04-04 10:11:02', '2003-02-25 10:15:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (572, 'http://video.chimingfon407.biz/Beauty', '1982-12-09 02:05:16', '2004-09-15 00:52:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (573, 'https://drive.holmescarlos.com/CollectiblesFineArt', '2018-02-05 01:58:17', '2007-04-07 07:55:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (574, 'https://www.fukudayamato719.xyz/Books', '2005-07-30 10:14:58', '2008-11-19 13:13:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (575, 'http://video.zengl.net/AutomotivePartsAccessories', '2016-12-13 12:26:16', '2007-01-10 16:14:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (576, 'https://video.xiaomingy.info/Baby', '1986-08-10 03:51:38', '2005-11-05 06:14:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (577, 'http://auth.minatofujiwara.net/VideoGames', '2016-06-09 14:39:55', '2007-05-23 09:23:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (578, 'https://www.tnanam.org/Appliances', '2019-06-14 23:27:35', '2014-04-29 15:18:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (579, 'http://drive.shihyu.org/Others', '1978-04-20 09:49:50', '2009-07-23 08:23:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (580, 'https://image.youngj605.cn/CDsVinyl', '2017-11-26 18:13:04', '2012-02-11 05:50:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (581, 'http://image.endomit1976.xyz/CellPhonesAccessories', '2004-02-24 21:29:01', '2013-10-01 21:13:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (582, 'http://drive.sunyunin.org/MusicalInstrument', '1991-07-19 03:46:49', '2002-05-12 01:45:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (583, 'https://drive.hykoo.co.jp/Appliances', '2010-10-15 04:52:33', '2019-08-16 00:03:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (584, 'http://image.wingfatyau10.net/IndustrialScientificSupplies', '1972-12-13 03:00:59', '2000-01-04 08:47:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (585, 'http://video.kikuchi924.org/MusicalInstrument', '2015-10-25 19:14:36', '2019-01-14 16:39:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (586, 'https://drive.guojialun.net/VideoGames', '1992-08-24 04:49:49', '2004-06-22 06:48:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (587, 'https://image.brendcoleman.info/AutomotivePartsAccessories', '1988-04-02 03:00:05', '2000-11-11 23:01:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (588, 'http://image.fwaiman.cn/ClothingShoesandJewelry', '1988-10-01 00:25:18', '2010-08-07 00:46:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (589, 'https://image.wzhiyuan10.com/Food', '1999-02-26 23:25:37', '2004-11-18 09:54:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (590, 'https://auth.zhaozhiyuan10.com/CenturionGardenOutdoor', '2022-05-28 07:08:13', '2013-07-14 04:13:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (591, 'http://video.ryotatakahashi.com/SportsOutdoor', '2022-01-19 21:13:55', '2011-06-01 17:20:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (592, 'https://www.tanmomoe.com/ClothingShoesandJewelry', '2015-01-17 02:37:47', '2013-09-29 17:00:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (593, 'http://video.guoshihan1013.co.jp/Baby', '1986-10-29 02:20:34', '2010-12-14 00:17:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (594, 'http://image.chiuwk16.co.jp/PetSupplies', '1974-10-18 12:20:41', '2008-11-29 01:58:57');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (595, 'https://image.castroglenn.us/AutomotivePartsAccessories', '1990-10-15 12:56:26', '2002-04-28 11:07:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (596, 'https://www.yuningl1125.org/Handcrafts', '2004-08-23 21:30:44', '2021-08-26 07:55:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (597, 'http://video.lokwokwing920.jp/ComputersElectronics', '2021-03-04 22:25:07', '2015-08-24 12:15:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (598, 'http://auth.zeyunin.cn/AutomotivePartsAccessories', '2009-10-11 07:59:57', '2014-06-22 16:23:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (599, 'https://drive.shino59.us/Beauty', '2021-05-20 10:29:42', '2015-03-29 00:12:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (600, 'http://video.hazukinis1976.com/Food', '1994-02-27 03:54:55', '2007-05-17 15:54:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (601, 'http://image.ksano.co.jp/HealthBabyCare', '1975-03-18 12:53:07', '2011-05-26 16:32:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (602, 'https://www.cpowe.com/CollectiblesFineArt', '1977-12-19 09:55:15', '2016-05-19 13:19:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (603, 'http://image.belkat703.com/MusicalInstrument', '1996-09-14 01:30:19', '2022-06-05 17:03:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (604, 'http://www.cl717.xyz/ToysGames', '2001-02-03 09:02:23', '2004-05-01 22:02:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (605, 'http://video.brooksjacob.net/ClothingShoesandJewelry', '2015-02-07 22:40:33', '2010-10-08 18:47:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (606, 'http://drive.wujia.info/FilmSupplies', '1991-11-15 14:49:17', '2006-05-05 16:06:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (607, 'https://drive.shyuning.xyz/ArtsHandicraftsSewing', '1978-07-06 14:16:56', '2001-07-21 13:50:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (608, 'https://www.liaoyu3.net/ClothingShoesandJewelry', '1995-01-12 23:04:10', '2000-03-02 08:05:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (609, 'https://www.mitsuki2003.jp/MusicalInstrument', '2009-11-18 01:04:40', '2013-08-11 13:26:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (610, 'http://image.maruyay9.co.jp/BeautyPersonalCare', '1983-04-22 19:10:07', '2007-06-14 08:14:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (611, 'https://image.robehic1992.co.jp/CellPhonesAccessories', '1973-11-22 09:00:16', '2003-04-22 07:08:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (612, 'http://video.lahan.org/Baby', '1972-06-04 10:04:47', '2006-08-19 05:34:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (613, 'https://video.andersonmarj.jp/CenturionGardenOutdoor', '2016-09-25 09:10:58', '2018-07-01 02:58:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (614, 'http://drive.boydhenry.xyz/Food', '2004-10-11 18:47:23', '2010-10-01 10:54:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (615, 'https://drive.howardfoster.com/ArtsHandicraftsSewing', '1983-08-05 23:19:32', '2016-11-23 12:49:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (616, 'https://video.miofujit.cn/Appliances', '1998-08-04 10:16:44', '2006-04-04 02:44:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (617, 'http://image.kkazuma.org/CDsVinyl', '1986-11-19 02:34:10', '2001-03-24 20:12:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (618, 'https://drive.hinaku4.us/IndustrialScientificSupplies', '1997-12-08 20:29:35', '2000-06-07 01:03:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (619, 'http://image.shinhirano.org/AutomotivePartsAccessories', '2020-04-01 16:52:17', '2014-03-23 01:08:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (620, 'https://auth.earlhawkins.co.jp/ToysGames', '1984-03-10 10:39:45', '2011-06-15 01:36:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (621, 'http://www.kjo.org/CellPhonesAccessories', '2014-07-28 06:13:13', '2021-09-16 14:22:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (622, 'http://auth.sugawarar607.org/VideoGames', '2009-02-07 13:46:27', '2004-06-03 14:36:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (623, 'https://auth.lwang.biz/Baby', '1995-08-17 04:15:34', '2001-10-25 20:17:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (624, 'https://video.yananqi1.info/Books', '1997-04-13 03:20:19', '2015-11-10 20:34:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (625, 'http://drive.tinwing901.biz/ArtsHandicraftsSewing', '1988-12-14 18:59:26', '2001-04-17 14:02:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (626, 'https://image.duanshihan817.biz/Food', '1980-01-11 10:02:30', '2009-02-12 20:38:57');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (627, 'http://drive.nakamorim9.info/PetSupplies', '2008-02-26 07:49:37', '2013-12-31 18:06:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (628, 'http://www.renafujiwara3.us/AppsGames', '1978-09-07 05:44:20', '2009-05-20 14:51:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (629, 'https://www.wuz.com/PetSupplies', '1972-10-21 04:02:30', '2003-06-07 19:40:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (630, 'http://image.aarob.us/Handcrafts', '1982-05-20 01:50:53', '2018-01-27 11:08:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (631, 'https://video.shinkaneko429.xyz/Appliances', '1997-12-26 18:01:37', '2008-12-04 18:37:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (632, 'http://www.akina1.xyz/FilmSupplies', '1980-02-03 21:38:43', '2020-09-18 16:18:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (633, 'https://auth.ylwu.us/CollectiblesFineArt', '1981-07-15 16:14:41', '2006-04-16 14:58:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (634, 'http://video.castsamuel1962.jp/Books', '1973-05-25 02:45:03', '2017-03-27 07:22:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (635, 'http://www.airi306.com/ToysGames', '1988-11-28 12:51:31', '2009-03-15 10:07:18');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (636, 'http://image.kimura1.info/ToolsHomeDecoration', '1973-01-20 00:30:40', '2016-02-24 03:21:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (637, 'https://drive.kafai4.xyz/CollectiblesFineArt', '1990-01-17 11:35:36', '2018-06-25 11:27:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (638, 'http://drive.cwtsui.co.jp/Beauty', '1975-02-21 16:39:36', '2010-08-31 21:03:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (639, 'https://www.wany.co.jp/SportsOutdoor', '2020-10-30 21:22:48', '2019-08-29 07:07:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (640, 'https://www.jessewest.cn/HouseholdKitchenAppliances', '1982-04-19 02:19:34', '2011-09-08 01:42:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (641, 'https://www.vintho.xyz/SportsOutdoor', '1981-12-10 01:00:06', '2004-11-23 17:20:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (642, 'https://video.fergusontrav44.cn/Books', '1999-09-25 06:05:28', '2010-03-26 07:20:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (643, 'http://auth.jimjoseph.cn/Beauty', '1986-04-23 05:30:22', '2018-04-19 17:40:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (644, 'http://image.rinakayama1012.info/Appliances', '2005-06-02 15:33:55', '2011-09-10 13:14:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (645, 'http://image.anqi7.xyz/Baby', '1993-05-12 09:25:07', '2009-08-26 13:16:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (646, 'http://auth.leukwokkuen.cn/Appliances', '1990-12-22 23:07:16', '2010-04-16 20:19:18');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (647, 'http://image.evasher1973.us/MusicalInstrument', '2006-06-04 03:27:46', '2008-09-26 10:39:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (648, 'http://www.shihancao1.org/BaggageTravelEquipment', '1984-03-07 22:30:40', '2015-03-12 10:20:54');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (649, 'https://image.qlu.co.jp/AutomotivePartsAccessories', '2016-07-18 21:20:48', '2014-09-19 02:16:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (650, 'https://drive.walkerpatrick.xyz/PetSupplies', '1978-11-28 15:25:33', '2012-10-13 18:59:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (651, 'https://drive.arimurayo54.cn/AutomotivePartsAccessories', '1972-08-14 06:17:02', '2012-02-01 12:27:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (652, 'http://image.qin1023.com/IndustrialScientificSupplies', '2014-09-16 06:28:54', '2000-07-05 18:45:12');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (653, 'http://auth.xiuyingdi.co.jp/MusicalInstrument', '1991-12-27 00:40:30', '2008-06-25 13:39:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (654, 'https://video.waiyee51.us/AutomotivePartsAccessories', '1983-10-19 13:14:25', '2020-09-09 23:25:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (655, 'http://video.lizhennan.cn/CDsVinyl', '1975-09-18 06:27:38', '2021-11-09 21:10:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (656, 'http://image.rtakahashi1113.biz/SportsOutdoor', '1993-05-14 15:38:20', '2002-09-05 20:48:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (657, 'http://drive.lu5.info/Others', '1993-11-05 02:31:02', '2015-09-10 20:38:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (658, 'http://www.kaito318.xyz/ComputersElectronics', '1981-10-19 03:06:21', '2006-05-30 20:23:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (659, 'https://video.kusauman.net/CollectiblesFineArt', '1980-08-16 09:15:04', '2002-08-08 22:10:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (660, 'http://auth.tfukuda.co.jp/Handcrafts', '1992-01-27 17:46:33', '2008-11-19 05:02:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (661, 'https://image.shiyin.jp/ToysGames', '2009-04-18 18:11:08', '2015-04-28 12:49:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (662, 'http://www.tran8.us/PetSupplies', '2018-08-14 17:47:55', '2011-11-02 02:05:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (663, 'http://image.zitsh.net/ComputersElectronics', '1990-07-29 22:33:42', '2014-07-29 07:47:12');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (664, 'http://auth.ksiuwai94.org/Others', '1980-01-25 02:46:31', '2003-06-29 07:06:18');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (665, 'http://video.lujial.cn/CollectiblesFineArt', '1983-08-17 17:27:48', '2013-09-14 04:12:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (666, 'https://video.zhiyuanma221.jp/CenturionGardenOutdoor', '2002-10-09 16:34:53', '2020-08-20 19:35:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (667, 'https://drive.liaojiehong.com/CollectiblesFineArt', '2001-02-02 08:06:20', '2020-09-26 03:16:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (668, 'http://image.shikar9.org/ToolsHomeDecoration', '2004-12-15 17:26:13', '2013-10-19 19:09:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (669, 'http://image.ryotah.xyz/Others', '1998-07-16 22:33:40', '2018-04-05 23:31:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (670, 'https://image.longlu8.net/CDsVinyl', '1983-05-23 01:00:58', '2005-06-15 07:37:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (671, 'http://www.zitao4.net/CellPhonesAccessories', '1994-12-04 11:25:23', '2017-07-14 22:19:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (672, 'https://drive.yanxiaoming.com/MusicalInstrument', '2022-03-26 18:26:46', '2001-06-01 04:01:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (673, 'https://image.dda2.xyz/VideoGames', '2018-04-08 07:20:31', '2022-06-08 01:50:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (674, 'https://video.shing320.com/CDsVinyl', '1991-01-14 12:40:47', '2018-12-07 10:33:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (675, 'https://auth.susankelle.info/AutomotivePartsAccessories', '2013-04-24 02:51:30', '2007-01-02 06:52:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (676, 'https://drive.zoushihan.us/VideoGames', '1974-01-17 21:11:24', '2015-06-30 13:51:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (677, 'http://drive.hujialun.us/BeautyPersonalCare', '1975-08-27 14:25:33', '2001-03-19 00:56:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (678, 'https://auth.yuanan629.cn/BaggageTravelEquipment', '2015-01-29 19:18:07', '2021-06-01 20:08:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (679, 'https://video.zhennan524.cn/PetSupplies', '1988-08-06 20:13:08', '2003-05-02 22:47:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (680, 'https://auth.richard619.co.jp/Handcrafts', '1976-10-14 04:30:08', '2017-07-23 05:14:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (681, 'https://auth.hung4.co.jp/Baby', '1992-07-14 22:30:20', '2018-02-20 16:49:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (682, 'http://auth.caolu9.info/Baby', '1985-04-10 20:21:51', '2009-06-01 16:41:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (683, 'http://video.tananqi901.co.jp/ComputersElectronics', '2014-06-06 05:00:53', '2001-12-12 18:09:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (684, 'http://drive.waisanwong.cn/Appliances', '1974-11-17 13:10:37', '2014-08-13 12:32:54');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (685, 'https://drive.anqiy10.co.jp/ClothingShoesandJewelry', '1978-02-28 23:06:35', '2019-03-05 08:37:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (686, 'https://drive.han4.jp/CDsVinyl', '2020-11-22 20:23:15', '2012-04-22 01:28:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (687, 'https://image.anqsu10.info/ToolsHomeDecoration', '1990-02-23 02:00:49', '2011-04-09 11:06:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (688, 'http://video.mn5.info/AppsGames', '1997-02-02 22:43:00', '2022-07-02 02:56:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (689, 'https://drive.daisuke9.com/CenturionGardenOutdoor', '2013-08-26 22:36:12', '2007-02-08 13:56:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (690, 'https://video.bradleykim1998.co.jp/IndustrialScientificSupplies', '1988-03-08 23:47:02', '2014-01-06 04:29:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (691, 'http://www.kryota.jp/ArtsHandicraftsSewing', '1973-01-30 03:21:27', '2011-12-17 20:39:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (692, 'http://video.kftsui.biz/ToolsHomeDecoration', '2018-06-19 23:05:03', '2020-03-27 07:13:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (693, 'https://image.lan2.net/Baby', '1987-01-28 23:44:52', '2007-10-02 04:40:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (694, 'https://www.moritasa.com/Others', '2016-11-25 18:17:25', '2005-08-17 22:15:56');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (695, 'https://drive.fan.com/HealthBabyCare', '1996-05-12 16:09:18', '2013-04-19 19:48:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (696, 'https://auth.mews1222.biz/ArtsHandicraftsSewing', '1995-12-21 23:01:42', '2021-08-31 06:51:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (697, 'http://www.zqin.com/ArtsHandicraftsSewing', '2016-12-20 02:12:51', '2001-10-20 02:22:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (698, 'https://drive.boydcharlotte.info/IndustrialScientificSupplies', '2000-01-07 03:49:51', '2009-01-05 00:39:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (699, 'http://auth.waihan2.net/CenturionGardenOutdoor', '2019-10-05 18:25:02', '2015-05-26 16:57:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (700, 'http://www.anitash.biz/Handcrafts', '2011-09-14 15:33:42', '2011-07-13 06:45:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (701, 'https://www.lxi.net/MusicalInstrument', '2003-07-21 21:26:29', '2001-02-08 08:22:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (702, 'http://image.dong6.biz/ClothingShoesandJewelry', '1976-12-29 03:01:48', '2002-12-30 20:23:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (703, 'https://auth.yuling2002.jp/MusicalInstrument', '1991-02-04 22:15:15', '2003-11-02 03:03:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (704, 'http://drive.zitao54.us/FilmSupplies', '2002-01-01 17:12:14', '2011-06-10 23:46:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (705, 'https://video.wu10.jp/Handcrafts', '1975-09-05 22:44:16', '2012-07-01 21:12:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (706, 'http://drive.kondo804.net/Others', '2015-02-16 23:51:29', '2008-06-18 06:22:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (707, 'http://auth.ziyizhe1212.com/MusicalInstrument', '2007-08-17 15:55:07', '2014-08-01 11:02:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (708, 'https://drive.daviste.org/CellPhonesAccessories', '2014-06-28 14:09:08', '2005-11-09 02:04:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (709, 'http://www.jialuntan.biz/ArtsHandicraftsSewing', '2021-05-23 19:58:59', '2001-03-06 18:00:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (710, 'https://www.shihantian.xyz/Handcrafts', '1970-06-10 11:49:57', '2020-02-18 03:43:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (711, 'http://video.yung1.biz/SportsOutdoor', '1980-10-20 20:20:52', '2003-10-17 08:17:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (712, 'http://image.brnelso.info/Handcrafts', '2021-10-22 22:45:30', '2021-03-07 22:09:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (713, 'https://auth.hiwayne9.cn/HealthBabyCare', '1986-01-04 07:53:33', '2009-09-09 13:18:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (714, 'http://auth.suy.info/SportsOutdoor', '2021-10-21 18:49:14', '2014-12-26 12:10:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (715, 'http://video.fushingka.co.jp/SportsOutdoor', '1977-11-18 13:21:19', '2010-08-23 05:29:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (716, 'https://video.parkerr59.biz/ToolsHomeDecoration', '2007-09-15 23:04:47', '2009-09-19 05:55:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (717, 'http://auth.anle.biz/Baby', '2012-02-15 07:45:48', '2008-09-25 12:50:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (718, 'https://drive.webb1022.net/VideoGames', '2020-06-05 06:33:34', '2002-01-09 00:03:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (719, 'http://image.rinwatanabe72.org/CDsVinyl', '1977-12-06 22:48:43', '2019-06-17 17:16:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (720, 'http://drive.cllee.net/Appliances', '1970-03-03 07:34:28', '2004-01-17 23:31:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (721, 'https://auth.ckm921.com/VideoGames', '1974-08-13 08:24:40', '2022-03-08 05:01:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (722, 'https://image.shehws.co.jp/CollectiblesFineArt', '1996-09-29 17:51:04', '2019-02-24 20:09:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (723, 'https://auth.saumanl.us/FilmSupplies', '1989-04-20 17:45:15', '2009-01-11 00:13:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (724, 'http://drive.heung3.net/ArtsHandicraftsSewing', '2013-12-08 08:58:25', '2019-05-03 01:30:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (725, 'https://image.zhozit.co.jp/SportsOutdoor', '2015-05-11 06:56:33', '2019-08-06 11:39:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (726, 'http://video.choiwy.us/MusicalInstrument', '1984-12-16 18:36:42', '2019-10-10 08:09:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (727, 'http://video.anqicao1001.info/Books', '1982-10-15 08:23:01', '2017-12-22 04:08:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (728, 'https://video.ono10.org/HealthBabyCare', '2018-03-22 21:32:05', '2007-04-30 23:48:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (729, 'http://www.okahikari5.xyz/ToysGames', '2002-05-20 18:47:21', '2003-07-19 07:47:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (730, 'http://drive.ykaman.net/HealthBabyCare', '2007-02-27 12:48:55', '2010-07-18 09:23:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (731, 'http://auth.hsuan8.jp/IndustrialScientificSupplies', '2014-11-30 01:43:05', '2018-11-02 11:29:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (732, 'http://video.lamyunf.org/AppsGames', '1977-06-19 06:09:11', '2004-10-01 07:21:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (733, 'https://video.smm8.com/VideoGames', '2015-09-15 13:13:53', '2022-04-10 23:07:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (734, 'http://www.wong2.us/AutomotivePartsAccessories', '1988-09-28 23:03:46', '2017-03-20 02:39:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (735, 'http://www.mo711.xyz/PetSupplies', '1973-07-04 14:51:46', '2006-03-27 08:02:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (736, 'http://auth.duyuning.jp/ToolsHomeDecoration', '2007-05-28 08:50:16', '2011-04-18 00:28:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (737, 'http://www.dingjia.com/Beauty', '2020-10-28 00:13:07', '2015-02-11 06:40:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (738, 'https://auth.stingfung.com/AppsGames', '1988-12-27 17:24:49', '2007-09-05 03:07:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (739, 'http://video.hernandezstanley.us/BeautyPersonalCare', '2017-10-10 05:09:30', '2007-09-07 16:11:18');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (740, 'https://image.lu63.jp/CenturionGardenOutdoor', '1984-11-24 15:51:17', '2016-01-01 06:30:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (741, 'https://image.shinta.xyz/ComputersElectronics', '1996-04-05 05:48:27', '2017-10-27 00:54:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (742, 'https://www.mengyunin7.cn/AutomotivePartsAccessories', '1979-07-24 02:50:23', '2019-11-12 21:29:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (743, 'http://video.zhili7.info/CollectiblesFineArt', '2017-08-18 03:43:07', '2016-01-30 18:38:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (744, 'https://www.aoshiw00.org/Food', '1977-08-17 22:23:24', '2012-01-15 22:29:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (745, 'http://drive.sa1997.xyz/BeautyPersonalCare', '1980-12-26 13:46:49', '2005-11-11 23:17:54');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (746, 'https://www.zhiyuya2009.jp/Others', '1992-07-21 22:54:12', '2003-03-22 16:31:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (747, 'http://video.yunkw18.jp/ArtsHandicraftsSewing', '1995-01-27 13:25:23', '2003-07-10 04:08:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (748, 'https://image.zy7.net/ToolsHomeDecoration', '2013-08-29 09:13:55', '2002-11-05 06:23:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (749, 'https://www.jones3.biz/ArtsHandicraftsSewing', '2003-01-13 09:08:44', '2017-02-12 13:29:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (750, 'http://video.caz.info/Books', '1984-02-28 23:08:23', '2020-08-20 04:00:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (751, 'https://image.mok4.net/ClothingShoesandJewelry', '2009-08-09 22:21:00', '2014-10-28 15:04:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (752, 'http://auth.lan1130.cn/Others', '1978-11-22 08:08:37', '2019-01-23 18:37:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (753, 'https://drive.sfuj.cn/Handcrafts', '1992-02-11 19:49:48', '2004-05-20 20:21:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (754, 'https://image.hiutungc2.com/CenturionGardenOutdoor', '2006-06-06 11:03:36', '2010-07-26 23:49:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (755, 'https://video.qianzhi.xyz/BaggageTravelEquipment', '2017-08-09 01:27:12', '2007-08-02 00:59:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (756, 'http://image.white1940.com/ArtsHandicraftsSewing', '1973-08-24 13:28:30', '2018-07-24 06:10:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (757, 'https://auth.fernandez4.co.jp/BeautyPersonalCare', '1998-03-08 15:51:03', '2017-07-31 14:41:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (758, 'http://video.bdunn.us/HouseholdKitchenAppliances', '2012-01-08 22:20:23', '2001-02-01 00:12:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (759, 'http://video.takedyuit49.co.jp/CDsVinyl', '2010-06-21 18:54:16', '2000-02-18 02:32:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (760, 'http://video.syche60.us/Others', '1978-06-29 08:33:16', '2009-08-01 14:42:54');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (761, 'http://auth.wfyuen.info/VideoGames', '1987-07-22 08:38:39', '2011-09-13 20:46:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (762, 'https://video.aoshiito.xyz/Others', '1973-05-05 00:33:42', '2010-10-17 19:01:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (763, 'https://drive.salazar2.org/Beauty', '1994-03-07 00:26:39', '2018-05-25 22:52:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (764, 'https://image.kwokkuenng.xyz/ToolsHomeDecoration', '2002-05-19 04:03:05', '2002-10-28 19:13:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (765, 'http://drive.barrymartin9.net/CenturionGardenOutdoor', '2001-07-14 12:06:29', '2005-10-01 18:48:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (766, 'http://www.yunfatm1961.cn/AppsGames', '1984-02-23 20:19:20', '2019-08-17 14:02:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (767, 'https://image.shihansu.co.jp/CDsVinyl', '1980-07-08 22:30:59', '2019-09-10 23:29:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (768, 'https://drive.kaneko914.net/FilmSupplies', '1994-07-14 20:26:29', '2017-03-22 05:45:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (769, 'http://image.onfu.com/Baby', '1994-08-02 03:34:11', '2004-10-15 14:16:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (770, 'https://drive.alexba.net/Handcrafts', '1979-08-16 22:41:46', '2018-03-14 00:50:18');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (771, 'https://www.zli102.org/CollectiblesFineArt', '2008-08-05 03:36:06', '2020-10-10 09:39:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (772, 'http://www.lanf2.net/SportsOutdoor', '1987-07-13 21:33:50', '2004-11-15 19:25:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (773, 'http://video.ikkim.biz/VideoGames', '1986-04-16 10:25:57', '2002-08-05 14:42:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (774, 'https://drive.ikeda44.org/ClothingShoesandJewelry', '1985-02-01 15:49:57', '2000-09-11 03:09:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (775, 'https://auth.mhay.xyz/ComputersElectronics', '1995-04-17 16:41:03', '2001-08-24 11:01:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (776, 'http://www.anne806.jp/IndustrialScientificSupplies', '2014-01-01 14:00:10', '2012-07-25 19:09:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (777, 'http://www.ryotaueno.org/ArtsHandicraftsSewing', '1970-08-22 22:14:14', '2017-08-14 15:31:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (778, 'http://www.xiazou.info/FilmSupplies', '1975-08-05 00:36:39', '2005-04-01 17:00:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (779, 'https://www.yotasano.net/Beauty', '1982-03-14 02:57:27', '2002-10-12 02:29:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (780, 'https://drive.rchavez.com/BaggageTravelEquipment', '1989-03-20 23:53:36', '2000-04-24 11:15:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (781, 'http://drive.luyuning.biz/PetSupplies', '1988-12-09 09:38:52', '2021-05-07 10:52:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (782, 'http://image.zity.us/BeautyPersonalCare', '1992-09-11 18:48:42', '2016-11-03 09:08:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (783, 'https://auth.ikedamisaki.org/ArtsHandicraftsSewing', '2020-03-11 21:09:04', '2009-10-15 04:18:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (784, 'https://www.tistep.jp/CollectiblesFineArt', '1997-04-01 14:07:06', '2012-04-08 21:02:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (785, 'https://drive.ojason.org/SportsOutdoor', '2019-04-24 15:16:18', '2001-09-14 23:31:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (786, 'http://drive.diaz615.us/Food', '2017-08-23 23:23:01', '2004-08-13 00:53:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (787, 'https://www.hayashi10.us/VideoGames', '1980-01-15 16:44:36', '2003-02-06 16:09:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (788, 'https://auth.lsch.jp/Appliances', '2005-01-03 18:56:45', '2020-07-05 20:53:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (789, 'https://drive.hazuki1.biz/Beauty', '2005-03-12 22:57:32', '2008-02-08 03:02:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (790, 'https://www.fslok.us/BeautyPersonalCare', '1979-02-03 18:53:07', '2016-01-06 18:19:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (791, 'https://image.ma73.jp/AutomotivePartsAccessories', '1989-04-24 16:53:17', '2011-04-21 21:14:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (792, 'https://drive.sino2008.us/Others', '1972-12-30 07:40:15', '2012-12-02 16:23:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (793, 'https://image.carlos5.xyz/CollectiblesFineArt', '2018-08-12 18:54:58', '2000-02-14 16:35:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (794, 'http://image.mnomur.org/HouseholdKitchenAppliances', '1978-12-02 17:29:15', '2008-10-11 03:12:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (795, 'http://auth.ellmanue.biz/ClothingShoesandJewelry', '2011-11-24 23:10:57', '2006-01-14 04:06:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (796, 'https://video.miu10.com/AppsGames', '1993-02-19 01:23:14', '2002-07-06 06:11:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (797, 'https://image.martinez69.net/Appliances', '1978-12-28 22:56:31', '2007-05-26 22:22:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (798, 'https://image.ziyso1227.net/AppsGames', '2012-01-10 23:17:43', '2019-02-08 21:01:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (799, 'https://drive.maigoto6.info/Books', '2008-08-12 09:11:01', '2020-07-05 16:13:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (800, 'http://video.zitao6.co.jp/Handcrafts', '2015-07-19 20:03:45', '2010-06-30 19:18:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (801, 'https://image.ftw48.cn/Handcrafts', '1988-04-15 19:27:47', '2018-11-17 13:11:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (802, 'http://drive.ferdawn.com/AppsGames', '1972-10-06 10:00:31', '2005-05-05 03:45:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (803, 'http://auth.wingszehan.com/Baby', '2001-04-05 13:37:01', '2021-07-15 19:19:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (804, 'http://video.fongyuling.net/ToolsHomeDecoration', '2012-06-25 17:08:13', '2013-03-26 13:09:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (805, 'https://www.chakina306.com/SportsOutdoor', '1990-07-04 23:16:17', '2009-01-02 03:03:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (806, 'http://image.judith302.cn/CenturionGardenOutdoor', '1987-12-13 19:46:54', '2016-07-06 23:17:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (807, 'https://drive.tongkm.cn/ToysGames', '2000-09-16 17:32:09', '2006-11-20 10:40:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (808, 'http://www.wuxiuyi929.net/Beauty', '2005-09-18 23:49:39', '2006-05-30 05:05:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (809, 'http://www.mnakajima1.biz/IndustrialScientificSupplies', '1995-07-05 17:01:32', '2019-02-12 06:35:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (810, 'http://www.kwokwing10.info/SportsOutdoor', '1985-11-19 14:47:09', '2017-01-11 07:36:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (811, 'https://drive.tincl.net/BaggageTravelEquipment', '2020-12-30 14:11:39', '2018-07-24 23:52:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (812, 'https://drive.tianzitao.xyz/ClothingShoesandJewelry', '1974-01-26 11:58:28', '2011-05-15 22:02:04');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (813, 'http://video.jfang.info/PetSupplies', '1983-06-29 08:44:32', '2002-08-27 04:54:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (814, 'https://image.kendo9.com/ToysGames', '2011-02-18 03:39:28', '2003-04-19 21:22:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (815, 'http://image.chang917.biz/CDsVinyl', '1989-09-23 12:32:40', '2020-05-21 06:30:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (816, 'https://www.mendezteresa.xyz/CDsVinyl', '2022-05-04 03:56:07', '2000-12-08 03:43:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (817, 'http://image.rikuando2004.net/HealthBabyCare', '1975-06-19 15:07:49', '2013-01-03 12:37:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (818, 'http://drive.ljialun.biz/CenturionGardenOutdoor', '2011-04-01 16:25:17', '2019-09-01 01:01:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (819, 'http://drive.tlsheh.org/ToolsHomeDecoration', '1983-07-05 08:52:01', '2004-02-29 00:50:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (820, 'https://www.aoiyamashita.xyz/Others', '2021-02-04 04:53:19', '2003-06-27 23:54:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (821, 'https://video.george214.net/VideoGames', '2012-04-14 08:59:56', '2009-06-12 08:40:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (822, 'https://image.kwokyinkwon7.cn/MusicalInstrument', '2003-03-11 01:00:19', '2011-07-08 05:19:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (823, 'https://www.chenjialun.info/Others', '2002-05-06 03:29:06', '2002-07-31 23:44:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (824, 'https://auth.martt.org/PetSupplies', '2001-06-25 22:56:11', '2011-01-23 14:15:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (825, 'http://auth.ruxue.cn/AppsGames', '2016-04-16 01:48:15', '2016-02-04 08:38:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (826, 'http://drive.qinzhiyu4.xyz/ToolsHomeDecoration', '2003-12-08 07:41:46', '2021-12-01 18:21:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (827, 'http://auth.yuning2.biz/SportsOutdoor', '2013-06-07 18:33:38', '2022-05-30 01:10:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (828, 'http://image.ctl10.com/MusicalInstrument', '1993-01-09 13:10:24', '2009-03-23 08:16:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (829, 'http://auth.matsumotokasum724.xyz/CollectiblesFineArt', '2020-10-23 04:15:36', '2016-07-12 13:12:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (830, 'http://video.yamazakihikar112.net/ToysGames', '1985-03-25 06:40:14', '2004-09-03 20:21:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (831, 'https://www.takwahma65.xyz/CellPhonesAccessories', '2014-11-28 13:53:33', '2000-11-15 01:56:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (832, 'http://video.lszekwan.cn/ComputersElectronics', '1995-08-04 16:18:13', '2012-05-30 12:26:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (833, 'https://video.tinar.us/VideoGames', '2009-01-10 08:50:53', '2017-10-14 05:32:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (834, 'https://image.tingfung52.biz/AutomotivePartsAccessories', '1983-12-11 18:42:25', '2005-09-03 09:30:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (835, 'https://drive.lanyin.us/Others', '2015-04-25 08:57:04', '2019-05-20 13:55:56');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (836, 'https://image.lewisca.org/MusicalInstrument', '1990-10-26 19:45:04', '2018-06-16 16:49:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (837, 'http://auth.hickkathryn7.info/ComputersElectronics', '2010-06-11 13:50:24', '2020-07-26 04:18:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (838, 'https://image.airik1018.us/ToolsHomeDecoration', '2009-02-19 02:10:00', '2019-01-14 10:11:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (839, 'https://image.nogumitsuki.net/HealthBabyCare', '2011-01-11 11:06:12', '2020-10-23 11:27:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (840, 'http://www.tingfungm.jp/VideoGames', '1984-04-08 13:17:24', '2020-01-07 03:50:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (841, 'https://video.tucker7.net/Beauty', '2005-07-08 06:23:21', '2014-07-11 00:27:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (842, 'https://video.kinoshitami.org/Handcrafts', '1973-01-31 00:04:51', '2015-12-22 11:16:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (843, 'https://auth.nghiutung912.jp/BaggageTravelEquipment', '2019-11-08 19:55:09', '2007-02-02 06:59:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (844, 'https://auth.hanhiutung.co.jp/SportsOutdoor', '1970-08-01 05:44:18', '2002-03-16 09:33:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (845, 'https://www.gu1207.biz/CellPhonesAccessories', '1999-10-12 16:04:58', '2010-07-27 00:58:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (846, 'https://video.ming4.cn/Beauty', '2004-03-10 23:23:33', '2012-06-14 15:47:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (847, 'https://video.robinr.info/CellPhonesAccessories', '1973-11-08 18:21:48', '2017-03-26 21:17:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (848, 'http://video.lul.biz/VideoGames', '1978-02-21 18:58:38', '2002-09-06 21:44:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (849, 'https://drive.zhij.biz/ComputersElectronics', '2012-06-04 00:47:06', '2004-08-21 13:59:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (850, 'http://auth.lorib45.jp/AppsGames', '1992-07-14 19:24:15', '2019-01-06 07:34:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (851, 'https://drive.sarat.jp/BaggageTravelEquipment', '1979-06-15 08:04:41', '2003-05-09 09:12:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (852, 'https://auth.ernest2.jp/ToysGames', '1991-09-05 21:18:20', '2001-11-18 17:06:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (853, 'https://video.yunaima.xyz/BeautyPersonalCare', '2020-02-08 19:13:30', '2003-07-06 19:32:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (854, 'http://www.sherrsn.co.jp/Handcrafts', '1971-08-22 23:43:54', '2009-01-21 08:18:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (855, 'https://video.fattam.net/FilmSupplies', '1980-08-29 14:51:36', '2014-05-04 14:42:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (856, 'https://drive.lesligarza.xyz/Food', '2015-01-07 09:20:24', '2013-09-20 17:20:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (857, 'https://drive.yaya.com/Others', '2001-12-27 19:44:06', '2011-05-20 02:21:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (858, 'https://drive.nishimurayuito.us/HealthBabyCare', '1978-05-20 13:28:50', '2015-03-10 19:23:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (859, 'http://auth.sakura120.us/FilmSupplies', '2006-09-13 14:45:34', '2000-03-31 22:52:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (860, 'http://auth.zjialun1996.biz/Others', '2001-10-30 17:00:28', '2008-05-14 21:09:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (861, 'http://image.chonkay43.xyz/CDsVinyl', '2014-04-28 05:05:03', '2000-03-31 21:41:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (862, 'http://www.sittc5.org/CenturionGardenOutdoor', '1990-08-26 01:12:05', '2019-04-25 20:08:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (863, 'https://drive.brooks3.net/CellPhonesAccessories', '1988-06-27 08:25:59', '2014-07-17 11:05:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (864, 'https://image.sanokai.cn/CollectiblesFineArt', '1989-03-11 05:19:35', '2002-12-22 09:03:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (865, 'https://image.tinwar.co.jp/SportsOutdoor', '1990-02-19 12:21:45', '2000-04-20 06:19:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (866, 'http://video.kasumi9.org/IndustrialScientificSupplies', '1987-05-25 17:46:02', '2008-08-02 14:11:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (867, 'http://video.mt408.xyz/BaggageTravelEquipment', '1985-11-02 14:18:23', '2008-05-29 23:31:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (868, 'http://image.ccy75.us/BeautyPersonalCare', '1974-06-18 18:02:29', '2009-01-08 14:59:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (869, 'http://video.jmyers6.cn/IndustrialScientificSupplies', '1993-04-19 14:55:17', '2017-03-26 03:10:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (870, 'http://drive.inok.us/VideoGames', '1972-09-03 06:32:26', '2004-11-03 20:20:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (871, 'https://auth.choyeetang7.com/ToolsHomeDecoration', '2003-11-14 16:16:31', '2003-07-23 14:30:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (872, 'http://drive.syuito40.com/VideoGames', '1991-05-28 08:54:39', '2016-03-05 02:19:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (873, 'http://drive.xuzi327.org/VideoGames', '1995-06-28 23:40:50', '2017-12-27 06:39:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (874, 'https://drive.yotainoue.cn/Beauty', '1974-07-03 17:35:13', '2013-07-22 16:10:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (875, 'http://video.anqsha.cn/CenturionGardenOutdoor', '2012-08-30 07:15:02', '2014-03-26 19:45:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (876, 'http://drive.jiaxiaoming.xyz/IndustrialScientificSupplies', '2020-05-02 23:26:46', '2020-05-23 07:17:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (877, 'https://www.yueliksun.biz/CellPhonesAccessories', '2018-03-02 19:03:55', '2000-03-21 03:21:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (878, 'http://drive.kobayashise.jp/HealthBabyCare', '1971-08-16 18:52:53', '2011-11-01 20:37:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (879, 'https://drive.war.xyz/HealthBabyCare', '1998-08-18 19:09:55', '2013-05-24 08:49:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (880, 'http://www.kwokmingkoo517.com/CenturionGardenOutdoor', '1977-04-06 08:44:49', '2002-01-08 13:52:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (881, 'http://www.hasshino603.co.jp/Baby', '1988-06-21 22:12:32', '2007-10-27 10:35:55');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (882, 'https://image.kna.cn/HouseholdKitchenAppliances', '2021-03-28 00:21:40', '2007-04-23 12:38:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (883, 'http://auth.kkai64.info/ToysGames', '1975-02-10 20:32:12', '2007-08-11 21:46:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (884, 'https://video.drui.net/ComputersElectronics', '2006-03-08 13:14:59', '2014-03-11 06:40:56');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (885, 'https://auth.rose1986.us/Appliances', '1981-02-02 00:51:24', '2004-04-05 02:34:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (886, 'http://drive.zhu4.cn/MusicalInstrument', '1979-01-22 02:03:53', '2010-02-17 04:32:57');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (887, 'http://image.holan.cn/SportsOutdoor', '1991-02-25 20:56:02', '2005-11-10 21:26:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (888, 'http://drive.harry6.cn/Food', '1982-11-05 20:25:14', '2022-06-26 19:47:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (889, 'https://drive.momoka4.org/BaggageTravelEquipment', '2010-03-16 05:00:51', '2014-09-12 02:35:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (890, 'http://drive.fongsy1.xyz/ArtsHandicraftsSewing', '1977-12-25 21:45:42', '2021-04-17 07:39:12');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (891, 'https://www.shenr.xyz/VideoGames', '2003-07-13 04:04:56', '2010-10-08 09:47:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (892, 'http://video.uchazu1031.org/CenturionGardenOutdoor', '1993-09-04 04:53:31', '2007-02-28 06:13:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (893, 'https://video.luzhennan.biz/CDsVinyl', '1997-12-21 13:30:56', '2015-03-17 11:32:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (894, 'https://auth.hasegawayuto95.us/AppsGames', '1972-05-13 10:38:20', '2015-11-13 09:24:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (895, 'http://www.maedaits705.info/FilmSupplies', '2007-02-14 06:31:26', '2009-01-17 22:31:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (896, 'http://image.zhir08.org/Beauty', '2020-08-08 04:50:24', '2001-03-28 19:14:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (897, 'http://image.br56.cn/AppsGames', '1998-01-14 23:54:14', '2021-06-26 19:07:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (898, 'http://auth.otsuka707.jp/AutomotivePartsAccessories', '1975-02-12 01:45:26', '2002-04-23 06:19:56');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (899, 'https://video.carlmarshall420.jp/CollectiblesFineArt', '2004-07-01 23:10:48', '2014-05-15 13:15:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (900, 'http://www.hcurt610.org/Baby', '2000-03-15 19:33:08', '2019-06-29 20:46:46');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (901, 'http://drive.yunxi5.jp/ArtsHandicraftsSewing', '1988-01-07 05:18:02', '2002-04-11 23:29:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (902, 'https://drive.mitsukim9.net/ClothingShoesandJewelry', '2004-01-27 10:04:41', '2006-02-15 20:23:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (903, 'http://www.xiuyre.jp/PetSupplies', '1999-01-10 06:05:51', '2007-05-08 01:09:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (904, 'http://auth.hasegawaakin.cn/CollectiblesFineArt', '2011-06-10 09:23:32', '2020-06-28 15:08:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (905, 'http://video.ziyizheng62.co.jp/Beauty', '1981-10-05 17:49:40', '2015-04-29 04:58:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (906, 'https://video.yamtana9.us/HouseholdKitchenAppliances', '1996-07-01 08:16:51', '2012-04-11 03:45:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (907, 'https://video.tkm.org/BaggageTravelEquipment', '2005-12-02 05:16:25', '2004-10-31 22:20:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (908, 'https://drive.aoshikojima.xyz/Food', '2012-09-05 21:58:23', '2007-08-07 10:29:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (909, 'http://www.janetg.biz/HealthBabyCare', '2009-07-20 13:38:58', '2014-01-03 08:39:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (910, 'http://image.ruiti.net/VideoGames', '1972-12-30 01:51:26', '2010-10-25 09:15:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (911, 'https://www.chocy.net/SportsOutdoor', '2019-08-02 09:29:56', '2007-11-23 22:51:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (912, 'http://video.liao07.cn/MusicalInstrument', '1988-04-02 14:42:07', '2007-06-05 00:31:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (913, 'https://auth.yuetw71.jp/FilmSupplies', '1982-05-02 20:07:53', '2001-10-25 23:20:16');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (914, 'http://drive.marcup.us/CenturionGardenOutdoor', '1989-08-17 03:18:51', '2011-02-18 07:51:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (915, 'http://www.zhiyuancao04.jp/IndustrialScientificSupplies', '1976-06-27 23:44:06', '2021-06-28 09:33:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (916, 'https://drive.luyu310.com/ArtsHandicraftsSewing', '1987-09-08 03:58:59', '2021-07-29 14:22:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (917, 'http://www.kathy3.jp/Food', '2005-06-01 10:58:16', '2011-04-18 13:11:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (918, 'http://drive.zhelu.org/Books', '1975-07-01 08:31:22', '2008-12-21 12:23:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (919, 'https://www.toddharris8.info/BaggageTravelEquipment', '2017-04-28 09:10:35', '2001-03-18 22:26:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (920, 'http://auth.tamsm.xyz/CDsVinyl', '1974-08-16 05:10:22', '2019-06-07 12:30:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (921, 'https://www.granjames.org/CenturionGardenOutdoor', '2010-07-17 03:53:44', '2020-01-20 11:51:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (922, 'http://auth.imai329.com/HealthBabyCare', '2020-08-18 14:14:28', '2009-11-17 11:59:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (923, 'https://drive.daioka.info/BaggageTravelEquipment', '1978-01-29 09:26:11', '2014-12-05 15:02:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (924, 'http://image.elor921.us/CellPhonesAccessories', '1988-08-02 18:58:12', '2016-03-08 04:16:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (925, 'https://video.olt.us/ToolsHomeDecoration', '2013-04-04 03:35:16', '2004-06-18 18:10:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (926, 'http://drive.yuitowata4.biz/IndustrialScientificSupplies', '2019-01-02 06:10:54', '2016-04-22 14:09:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (927, 'http://www.ota1990.xyz/IndustrialScientificSupplies', '1986-11-07 12:32:26', '2007-01-04 17:33:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (928, 'https://www.hhaz.xyz/PetSupplies', '1980-07-18 11:45:42', '2006-12-27 20:42:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (929, 'https://video.alicethompson56.com/ArtsHandicraftsSewing', '1996-07-25 03:45:54', '2006-11-19 14:07:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (930, 'http://image.ngkaming.info/HealthBabyCare', '2016-03-17 17:16:50', '2022-06-21 22:03:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (931, 'http://image.ziyxi.info/CDsVinyl', '1994-12-12 13:09:15', '2020-06-01 05:00:49');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (932, 'https://auth.kytse.jp/Food', '1972-04-21 01:36:19', '2003-01-23 16:11:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (933, 'https://drive.tinming72.xyz/HouseholdKitchenAppliances', '2022-02-11 15:08:01', '2013-09-20 06:55:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (934, 'https://drive.woogregory98.xyz/Baby', '1985-11-25 03:36:00', '2000-02-29 22:06:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (935, 'http://video.nadaisuke.xyz/CDsVinyl', '1993-08-14 13:11:48', '2002-09-25 18:35:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (936, 'https://drive.anqiqian1980.cn/ToysGames', '2005-05-30 22:44:13', '2011-07-31 06:59:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (937, 'http://auth.fongyf.jp/CDsVinyl', '1986-11-14 14:50:26', '2008-03-10 13:38:58');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (938, 'https://video.mogawa1949.org/AppsGames', '1996-09-21 05:18:59', '2007-09-15 09:09:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (939, 'http://drive.rye.net/CellPhonesAccessories', '1982-07-13 07:01:53', '2012-09-08 16:27:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (940, 'https://drive.waimanl.xyz/Others', '1985-04-26 00:23:15', '2020-03-04 22:50:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (941, 'http://www.greewilliam8.org/ToysGames', '2012-03-24 22:41:49', '2009-07-03 02:34:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (942, 'http://auth.takahashiryota.info/IndustrialScientificSupplies', '1978-12-27 06:25:52', '2004-12-27 04:22:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (943, 'https://drive.ogawayamato.org/BeautyPersonalCare', '2017-04-27 03:04:15', '2007-08-22 06:51:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (944, 'http://video.lakaming46.biz/CellPhonesAccessories', '1996-01-21 13:20:09', '2010-01-08 22:01:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (945, 'https://www.kr59.biz/CollectiblesFineArt', '1988-05-03 22:30:08', '2019-10-20 02:39:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (946, 'https://auth.mozhennan.biz/MusicalInstrument', '2003-07-04 19:36:41', '2011-07-22 08:06:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (947, 'http://www.boyd716.net/PetSupplies', '1986-07-31 10:48:45', '2008-06-29 17:52:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (948, 'http://video.sakura76.com/AutomotivePartsAccessories', '2013-04-29 16:32:37', '2018-09-29 06:51:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (949, 'https://drive.hsuan7.info/HealthBabyCare', '1997-11-16 01:19:39', '2005-02-03 16:21:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (950, 'https://video.kukw.org/ArtsHandicraftsSewing', '1985-08-17 18:36:02', '2000-09-29 08:58:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (951, 'https://image.seleanor.biz/ClothingShoesandJewelry', '2010-09-22 02:24:04', '2017-10-05 12:43:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (952, 'https://video.ziytao.jp/CellPhonesAccessories', '2019-03-14 01:25:19', '2010-05-07 23:29:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (953, 'http://video.kmatsumoto.xyz/ComputersElectronics', '2002-10-07 09:06:47', '2020-10-19 13:01:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (954, 'http://www.koyamaik.biz/ToolsHomeDecoration', '1990-01-01 10:05:57', '2021-07-16 08:42:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (955, 'https://image.kudohazuk5.us/CDsVinyl', '1976-03-03 17:15:25', '2003-03-26 17:00:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (956, 'https://auth.takeuchit.org/ComputersElectronics', '2020-12-01 18:05:41', '2001-10-24 19:42:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (957, 'http://video.wuanqi1017.jp/Appliances', '1992-08-10 08:38:17', '2013-10-22 15:57:18');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (958, 'http://drive.ikakina86.cn/ToolsHomeDecoration', '2015-08-21 13:07:57', '2011-11-05 23:58:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (959, 'https://auth.daichisat.com/Food', '2000-12-01 06:06:12', '2008-02-04 08:40:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (960, 'http://image.wingszechang1.co.jp/Beauty', '1999-03-31 17:43:23', '2013-06-02 03:42:57');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (961, 'http://video.patterson54.co.jp/HouseholdKitchenAppliances', '1980-09-22 17:25:37', '2021-06-19 04:29:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (962, 'http://image.fkaming.biz/MusicalInstrument', '2004-01-02 22:23:25', '2000-10-26 14:09:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (963, 'https://image.liaoziyi.biz/CollectiblesFineArt', '2015-09-07 03:12:47', '2012-07-07 10:59:12');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (964, 'http://drive.zwu.biz/ComputersElectronics', '1983-09-16 02:59:14', '2008-10-22 12:09:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (965, 'http://video.ishidaya.info/CDsVinyl', '1991-06-19 05:21:55', '2008-09-16 03:00:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (966, 'https://drive.mikwhi.xyz/CDsVinyl', '2004-05-26 10:54:34', '2015-03-26 23:59:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (967, 'https://drive.megloria.co.jp/IndustrialScientificSupplies', '1997-10-28 19:29:44', '2011-05-08 00:55:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (968, 'http://drive.debraf.jp/HealthBabyCare', '2016-10-12 10:03:08', '2003-07-09 22:53:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (969, 'https://auth.soan51.org/CDsVinyl', '2001-05-12 04:41:28', '2021-11-03 23:27:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (970, 'http://auth.kikuchikaito.com/AutomotivePartsAccessories', '2021-10-09 05:50:58', '2021-09-21 19:59:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (971, 'https://auth.shihanzheng.co.jp/AutomotivePartsAccessories', '2022-02-15 09:19:22', '2018-10-04 18:31:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (972, 'http://drive.yutochiba.info/AppsGames', '2021-11-16 18:56:36', '2014-05-28 09:14:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (973, 'https://video.fongkwokkuen.net/Food', '2016-04-28 03:50:30', '2016-07-11 15:02:24');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (974, 'http://image.kimmars1.jp/AutomotivePartsAccessories', '2008-03-06 11:39:16', '2008-09-23 17:23:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (975, 'https://image.wgloria2.xyz/BeautyPersonalCare', '1999-12-02 23:01:25', '2007-04-12 21:48:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (976, 'https://image.yunaishi1.net/ToolsHomeDecoration', '2020-12-13 01:14:36', '2004-04-13 16:25:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (977, 'https://auth.ikedamits607.us/Others', '2019-05-12 05:06:55', '2006-10-20 20:31:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (978, 'http://www.portesa.net/CenturionGardenOutdoor', '1980-10-06 09:34:18', '2019-08-11 12:14:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (979, 'http://www.kaito9.jp/Food', '1997-05-14 18:49:28', '2001-09-29 08:57:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (980, 'http://drive.geomartinez1954.co.jp/AutomotivePartsAccessories', '1971-07-14 20:25:35', '2022-01-25 20:53:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (981, 'http://www.sokaman.org/ArtsHandicraftsSewing', '1978-07-22 19:00:25', '2021-08-28 13:27:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (982, 'http://www.stevens1.jp/Beauty', '2014-04-24 01:06:42', '2003-02-03 05:01:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (983, 'http://image.xiuyinghan.org/BaggageTravelEquipment', '1995-09-26 14:21:11', '2002-02-03 16:12:41');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (984, 'http://www.shi6.com/CenturionGardenOutdoor', '2013-07-16 13:51:13', '2018-11-17 04:05:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (985, 'https://auth.cruzste.info/HealthBabyCare', '1979-09-17 17:18:41', '2007-06-11 13:50:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (986, 'http://drive.aoki818.info/Food', '1971-02-22 20:37:02', '2012-10-13 21:25:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (987, 'https://drive.sihuimei.cn/ClothingShoesandJewelry', '1986-02-24 10:58:37', '2014-02-10 19:13:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (988, 'https://image.ramossh.biz/Beauty', '2009-01-13 11:12:03', '2016-01-12 20:44:27');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (989, 'https://auth.stephensj709.xyz/ClothingShoesandJewelry', '1993-11-28 23:19:35', '2009-02-21 12:37:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (990, 'http://drive.jdoris9.info/Handcrafts', '2001-02-24 14:24:32', '2003-04-08 16:51:54');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (991, 'http://drive.zijiang.jp/MusicalInstrument', '2004-03-25 01:12:23', '2019-02-16 20:44:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (992, 'https://image.chenemma1.info/Appliances', '2015-04-06 23:06:11', '2020-11-08 01:07:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (993, 'http://drive.thm62.jp/Beauty', '1980-12-21 14:27:37', '2007-09-18 15:28:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (994, 'http://www.eu7.info/BeautyPersonalCare', '1992-09-03 03:46:24', '2022-02-19 21:59:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (995, 'https://image.rma.jp/IndustrialScientificSupplies', '2016-10-11 10:51:44', '2001-03-29 17:59:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (996, 'http://video.jiehzha.org/BaggageTravelEquipment', '2002-09-29 11:26:16', '2019-09-08 07:08:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (997, 'https://drive.shex.net/CollectiblesFineArt', '1999-01-11 02:50:04', '2022-02-21 15:50:54');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (998, 'https://auth.yuen2.jp/BeautyPersonalCare', '2019-06-07 03:56:36', '2006-04-05 05:06:53');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (999, 'http://image.leunghuimei.xyz/HouseholdKitchenAppliances', '1977-05-10 05:37:09', '2014-04-01 09:11:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1000, 'http://video.wanchi2.us/HealthBabyCare', '2002-02-05 00:22:51', '2017-02-16 16:25:42');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1001, 'http://video.miwada1.info/CellPhonesAccessories', '2001-12-04 14:00:02', '2008-03-14 08:50:50');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1002, 'https://www.ayasait.com/ComputersElectronics', '1983-09-08 15:47:37', '2013-07-21 12:55:51');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1003, 'https://auth.zru1.com/PetSupplies', '2019-01-07 16:37:13', '2008-07-20 16:57:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1004, 'http://auth.twy50.cn/FilmSupplies', '2012-10-28 07:55:08', '2008-01-01 06:13:45');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1005, 'http://image.matsumotoma.cn/SportsOutdoor', '1979-11-16 14:04:51', '2006-07-15 05:00:11');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1006, 'http://www.phillip424.info/ToysGames', '2007-08-16 04:20:12', '2007-10-29 06:19:30');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1007, 'http://image.kennsa.us/CollectiblesFineArt', '1993-06-06 10:26:52', '2005-11-25 21:57:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1008, 'https://video.kenueno6.xyz/ToysGames', '1982-04-26 17:30:34', '2020-08-02 19:14:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1009, 'http://video.hmyi.us/ArtsHandicraftsSewing', '2003-10-06 18:39:36', '2006-12-29 20:03:39');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1010, 'http://www.morihi2.us/Food', '2007-04-01 04:11:31', '2020-06-18 05:47:20');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1011, 'https://auth.bomartin96.xyz/CollectiblesFineArt', '1992-03-07 16:48:38', '2003-05-16 04:23:26');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1012, 'http://image.de55.cn/IndustrialScientificSupplies', '1979-10-29 03:50:36', '2011-12-16 02:52:40');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1013, 'http://www.zya4.org/ComputersElectronics', '2017-06-09 08:11:28', '2018-09-06 18:27:03');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1014, 'https://www.shtl.xyz/CellPhonesAccessories', '2005-08-23 21:00:48', '2017-04-25 06:13:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1015, 'http://www.daichi6.com/Handcrafts', '2004-05-01 13:09:31', '2003-07-23 05:49:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1016, 'https://drive.twl1020.co.jp/Others', '1998-01-13 01:41:09', '2002-07-02 21:41:35');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1017, 'http://www.jilu.info/ClothingShoesandJewelry', '1991-07-18 09:38:33', '2022-07-14 15:36:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1018, 'http://drive.fukudkazuma9.org/Food', '1994-04-09 04:31:26', '2015-03-08 02:06:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1019, 'https://video.koyamrin.cn/SportsOutdoor', '2002-01-18 16:11:33', '2016-04-22 04:38:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1020, 'https://auth.millsaaron.biz/SportsOutdoor', '1998-07-19 03:34:40', '2011-12-22 02:39:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1021, 'https://www.msyin.xyz/MusicalInstrument', '1984-08-19 04:42:13', '2007-05-01 22:57:06');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1022, 'https://auth.zhongyu615.us/HealthBabyCare', '1976-05-15 04:06:09', '2013-06-09 18:40:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1023, 'http://www.okamotokaito.jp/FilmSupplies', '1998-07-12 11:46:52', '2008-09-10 22:39:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1024, 'http://drive.whitecheryl508.xyz/Baby', '2019-07-12 14:50:15', '2019-03-27 16:03:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1025, 'https://video.okada4.net/FilmSupplies', '1979-07-09 10:02:46', '2015-09-14 19:48:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1026, 'https://image.janguy.com/IndustrialScientificSupplies', '2015-05-28 14:30:38', '2002-12-06 10:21:48');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1027, 'http://www.guzhen.info/ArtsHandicraftsSewing', '2001-03-17 04:11:10', '2002-01-10 05:49:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1028, 'http://drive.hs1988.net/Others', '1987-04-29 05:36:14', '2001-12-04 03:25:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1029, 'https://drive.ziyitao3.biz/ArtsHandicraftsSewing', '1995-01-02 05:51:29', '2012-07-16 04:04:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1030, 'https://auth.chengz.us/BeautyPersonalCare', '1982-01-15 07:40:29', '2008-05-08 06:11:31');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1031, 'http://auth.marshcharles129.info/AppsGames', '2001-04-13 21:34:21', '2013-03-17 19:21:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1032, 'https://www.ykk86.com/AutomotivePartsAccessories', '1976-07-24 10:05:36', '2014-03-27 09:31:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1033, 'http://auth.waiyeec.net/FilmSupplies', '2010-09-03 06:54:21', '2008-06-13 13:21:15');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1034, 'https://image.sur.jp/CenturionGardenOutdoor', '1994-07-25 01:35:01', '2016-06-25 18:06:32');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1035, 'https://image.rz1.info/CDsVinyl', '1970-10-02 10:40:22', '2017-10-08 10:48:08');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1036, 'https://drive.liksunwu.xyz/Appliances', '1985-11-01 17:42:19', '2010-07-15 07:10:52');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1037, 'http://video.wingsze2.biz/VideoGames', '2012-06-07 04:05:44', '2022-05-16 21:32:07');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1038, 'http://www.makm.jp/Appliances', '1976-07-13 14:25:29', '2004-01-01 09:43:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1039, 'https://image.hughru.xyz/MusicalInstrument', '1971-12-24 02:03:49', '2009-07-21 17:31:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1040, 'https://auth.lowingsuen.us/CenturionGardenOutdoor', '2011-01-05 19:06:34', '2001-08-06 18:11:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1041, 'http://image.rin2.biz/BaggageTravelEquipment', '2022-05-25 17:06:42', '2020-05-17 18:31:57');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1042, 'https://www.weiyuning.co.jp/PetSupplies', '1983-02-22 04:03:21', '2020-04-29 00:30:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1043, 'http://video.kimlouis.info/HouseholdKitchenAppliances', '1984-12-25 05:02:33', '2020-10-02 14:27:17');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1044, 'http://image.yeunhoyin.net/PetSupplies', '2016-06-23 23:32:18', '2007-04-18 01:04:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1045, 'http://www.rfish1117.net/ArtsHandicraftsSewing', '1975-04-25 07:10:05', '2009-10-15 23:24:02');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1046, 'http://drive.uchida85.net/CDsVinyl', '2008-07-05 18:09:18', '2007-11-03 22:33:14');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1047, 'https://www.jose1.org/FilmSupplies', '2004-08-25 08:25:21', '2022-03-09 08:27:00');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1048, 'https://auth.mits.jp/MusicalInstrument', '2009-01-15 17:45:11', '2002-06-19 06:44:38');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1049, 'https://www.timai1.cn/PetSupplies', '1986-05-23 19:56:27', '2021-08-04 17:35:29');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1050, 'https://www.shihanduan.jp/Books', '2014-10-02 20:49:02', '2007-03-17 21:19:21');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1051, 'https://www.linglingpan87.cn/CenturionGardenOutdoor', '2004-07-28 04:06:56', '2009-12-26 20:45:01');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1052, 'http://www.moksauman1955.com/Handcrafts', '2015-10-10 23:17:59', '2017-09-13 05:34:33');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1053, 'http://www.ayatk.co.jp/Books', '1975-07-31 03:31:47', '2017-12-30 07:03:25');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1054, 'http://video.shimada2.cn/Beauty', '2009-05-12 17:32:52', '2018-10-08 01:24:47');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1055, 'http://www.qianzhiyuan.xyz/CDsVinyl', '1970-04-03 11:11:35', '2009-08-17 05:40:23');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1056, 'https://drive.yota4.com/ArtsHandicraftsSewing', '2019-01-04 11:23:16', '2000-04-19 05:41:44');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1057, 'http://image.wsch.co.jp/Food', '1973-08-26 04:42:26', '2006-09-19 22:01:22');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1058, 'https://www.dennismith.info/ComputersElectronics', '2000-03-05 01:46:18', '2016-02-15 12:01:43');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1059, 'http://auth.fuwingkuen.us/ArtsHandicraftsSewing', '1977-04-28 00:33:34', '2014-03-11 01:06:34');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1060, 'http://drive.qin911.xyz/Appliances', '1995-11-08 07:37:06', '2012-09-09 20:45:37');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1061, 'http://www.cm1102.net/HealthBabyCare', '1988-07-15 08:49:33', '2007-01-10 23:05:09');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1062, 'http://www.jiajieho.cn/PetSupplies', '2005-08-31 19:39:45', '2006-06-25 22:20:28');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1063, 'http://drive.nicmitc315.com/Others', '2011-07-16 00:02:24', '2012-08-10 16:50:10');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1064, 'http://drive.okadah1227.info/Appliances', '2016-08-01 01:45:41', '2019-04-12 05:37:13');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1065, 'http://drive.mh807.info/VideoGames', '1987-03-28 18:42:18', '2015-03-11 17:25:59');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1066, 'https://drive.shimadakasumi.co.jp/Baby', '1982-01-24 23:22:58', '2010-03-09 22:46:36');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1067, 'http://www.tszchingto.net/Food', '2004-02-02 07:03:27', '2002-07-23 14:15:05');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1068, 'https://auth.makk.cn/HouseholdKitchenAppliances', '1993-06-24 03:53:56', '2000-03-04 18:52:19');
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (1069, 'https://drive.ma3.us/Appliances', '2006-01-12 08:24:31', '2010-02-26 18:48:25');
COMMIT;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1182 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
