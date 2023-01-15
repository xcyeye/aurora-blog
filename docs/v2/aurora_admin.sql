/*
 Navicat Premium Data Transfer

 Source Server         : 本地Linux
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : 192.168.158.120:3306
 Source Schema         : aurora_admin

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 15/01/2023 19:31:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for au_admin_sidebar
-- ----------------------------
DROP TABLE IF EXISTS `au_admin_sidebar`;
CREATE TABLE `au_admin_sidebar`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `level` int NOT NULL DEFAULT 0 COMMENT '前台导航的展示等级 比如0就是一级导航',
  `pre_sidebar_uid` bigint NOT NULL DEFAULT 0 COMMENT '当前导航的父导航uid，也就是该导航显示在哪个导航下面',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的标题',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的对应地址',
  `is_external` tinyint NOT NULL DEFAULT 0 COMMENT '1：链接到外部地址 0：链接就是此站点的，不在新标签也打开',
  `icon_class_name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此导航的类名，用户icon',
  `sort` int NOT NULL DEFAULT 0 COMMENT '此导航的顺序编号',
  `user_uid` bigint NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `nav_uid`(`pre_sidebar_uid` ASC) USING BTREE,
  INDEX `user`(`user_uid` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_admin_sidebar
-- ----------------------------

-- ----------------------------
-- Table structure for au_navigation
-- ----------------------------
DROP TABLE IF EXISTS `au_navigation`;
CREATE TABLE `au_navigation`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `level` int NOT NULL DEFAULT 0 COMMENT '前台导航的展示等级 比如0就是一级导航',
  `parent_nav_uid` bigint NULL DEFAULT NULL COMMENT '当前导航的父导航uid，也就是该导航显示在哪个导航下面',
  `son_nav_uids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '该导航的子导航uid集合',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的标题',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的对应地址',
  `is_external` tinyint NOT NULL DEFAULT 0 COMMENT '1：链接到外部地址 0：链接就是此站点的，不在新标签也打开',
  `icon_class_name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此导航的类名，用户icon',
  `sort` int NOT NULL DEFAULT 0 COMMENT '此导航的顺序编号',
  `user_uid` bigint NOT NULL COMMENT '该导航属于哪个用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '1: 删除 0：不删除',
  `is_show` tinyint NOT NULL DEFAULT 1 COMMENT '1: 展示，0： 不显示',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_navigation
-- ----------------------------
INSERT INTO `au_navigation` VALUES (1526179609817128960, 0, NULL, '1526184455752392704,1526186806055804928,1526186892060008448,1526186914969296896,1526186938008608768', '博客文章', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:04:55', '2022-05-16 21:04:55', 0, 1);
INSERT INTO `au_navigation` VALUES (1526179711717744640, 0, NULL, NULL, '友情链接', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 20:36:12', NULL, 0, 1);
INSERT INTO `au_navigation` VALUES (1526179864444936192, 0, NULL, NULL, '说说', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 20:36:49', NULL, 0, 1);
INSERT INTO `au_navigation` VALUES (1526179978802634752, 0, NULL, NULL, '插件', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 20:37:16', NULL, 0, 1);
INSERT INTO `au_navigation` VALUES (1526184455752392704, 0, 1526179609817128960, NULL, '文章1', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 20:55:04', NULL, 0, 1);
INSERT INTO `au_navigation` VALUES (1526186806055804928, 0, 1526179609817128960, NULL, '文章2', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:04:24', NULL, 0, 1);
INSERT INTO `au_navigation` VALUES (1526186892060008448, 0, 1526179609817128960, NULL, '文章3', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:04:44', NULL, 0, 1);
INSERT INTO `au_navigation` VALUES (1526186914969296896, 0, 1526179609817128960, NULL, '文章4', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:04:50', NULL, 0, 1);
INSERT INTO `au_navigation` VALUES (1526186938008608768, 0, 1526179609817128960, '1526187371460567040,1526187436136734720,1526187459058606080', '文章5', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:07:00', '2022-05-16 21:07:00', 0, 1);
INSERT INTO `au_navigation` VALUES (1526187371460567040, 0, 1526186938008608768, NULL, '文章5-1', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:06:39', NULL, 0, 1);
INSERT INTO `au_navigation` VALUES (1526187436136734720, 0, 1526186938008608768, '1526187552637722624,1526187575698006016', '文章5-2', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:07:27', '2022-05-16 21:07:27', 0, 1);
INSERT INTO `au_navigation` VALUES (1526187459058606080, 0, 1526186938008608768, NULL, '文章5-3', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:07:00', NULL, 0, 1);
INSERT INTO `au_navigation` VALUES (1526187552637722624, 0, 1526187436136734720, NULL, '文章5-2-1', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:07:22', NULL, 0, 1);
INSERT INTO `au_navigation` VALUES (1526187575698006016, 0, 1526187436136734720, NULL, '文章5-2-2', 'kaxnoymqq22q5p1', 0, 'dgd7bv3wivuaeow', 1, 1522074993315815424, '2022-05-16 21:07:27', NULL, 0, 1);

-- ----------------------------
-- Table structure for au_permission
-- ----------------------------
DROP TABLE IF EXISTS `au_permission`;
CREATE TABLE `au_permission`  (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid，自增',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限的名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限的地址，可以是组件的名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `unique_permissionName_index`(`name` ASC) USING BTREE,
  INDEX `create_time_index`(`create_time` ASC) USING BTREE,
  INDEX `path_index`(`path` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_permission
-- ----------------------------
INSERT INTO `au_permission` VALUES (3, '这是首页', 'POST:/', '2022-05-07 16:04:05', '2022-06-25 16:20:53');
INSERT INTO `au_permission` VALUES (4, '用户管理', 'GET:/admin/user', '2022-05-10 14:11:55', NULL);
INSERT INTO `au_permission` VALUES (5, '查询所有路径权限', 'GET:/admin/permission', '2022-05-10 14:12:40', NULL);
INSERT INTO `au_permission` VALUES (6, '插入路径权限', 'POST:/admin/permission', '2022-05-10 14:12:50', NULL);
INSERT INTO `au_permission` VALUES (7, '删除路径权限', 'DELETE:/admin/permission', '2022-05-10 14:13:01', '2022-06-25 16:35:59');
INSERT INTO `au_permission` VALUES (10, '权限', 'GET:/admin/permissionRelation/username/**', '2022-05-10 20:54:30', NULL);
INSERT INTO `au_permission` VALUES (11, '接口名称', 'DELETE:/user/xcye', '2022-12-14 20:42:54', '2022-12-14 20:43:12');

-- ----------------------------
-- Table structure for au_role
-- ----------------------------
DROP TABLE IF EXISTS `au_role`;
CREATE TABLE `au_role`  (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid，自增',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色的名称，不用添加ROLE_',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '用户的状态 1：已禁用 0：未禁用',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `unique_role_name_index`(`name` ASC) USING BTREE,
  INDEX `create_time_index`(`create_time` ASC) USING BTREE COMMENT '创建时间单独索引'
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_role
-- ----------------------------
INSERT INTO `au_role` VALUES (3, 'admin', '2022-05-07 15:09:15', NULL, 0);
INSERT INTO `au_role` VALUES (4, 'user', '2022-05-07 15:10:26', NULL, 0);
INSERT INTO `au_role` VALUES (5, 'root', '2022-05-07 15:10:40', NULL, 0);
INSERT INTO `au_role` VALUES (16, 'chuchen', '2022-05-10 14:17:30', NULL, 0);
INSERT INTO `au_role` VALUES (17, 'test', '2022-05-10 14:17:44', NULL, 0);

-- ----------------------------
-- Table structure for au_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `au_role_permission`;
CREATE TABLE `au_role_permission`  (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `role_uid` bigint NOT NULL,
  `permission_uid` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `roleUid_index`(`role_uid` ASC) USING BTREE,
  INDEX `permissionUid_index`(`permission_uid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_role_permission
-- ----------------------------
INSERT INTO `au_role_permission` VALUES (5, 17, 4, '2023-01-14 22:47:03', NULL);
INSERT INTO `au_role_permission` VALUES (6, 3, 5, '2023-01-14 22:47:03', NULL);
INSERT INTO `au_role_permission` VALUES (7, 4, 6, '2023-01-14 22:47:03', NULL);
INSERT INTO `au_role_permission` VALUES (8, 3, 7, '2023-01-14 22:47:03', NULL);
INSERT INTO `au_role_permission` VALUES (9, 5, 8, '2023-01-14 22:47:03', NULL);
INSERT INTO `au_role_permission` VALUES (10, 3, 9, '2023-01-14 22:47:03', NULL);
INSERT INTO `au_role_permission` VALUES (11, 3, 10, '2023-01-14 22:47:03', NULL);

-- ----------------------------
-- Table structure for au_setting
-- ----------------------------
DROP TABLE IF EXISTS `au_setting`;
CREATE TABLE `au_setting`  (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `param_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `param_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `param_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_setting
-- ----------------------------
INSERT INTO `au_setting` VALUES (1, 'nginx_file_host', 'nginx_file_host', 'http://192.168.31.26', '2023-01-14 22:55:52', '2023-01-15 00:18:48');

-- ----------------------------
-- Table structure for au_site
-- ----------------------------
DROP TABLE IF EXISTS `au_site`;
CREATE TABLE `au_site`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `site_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '站点的icon地址',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '站点的标题 浏览器顶部部分',
  `logo_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '站点的前台logo文字',
  `site_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '站点的logo地址',
  `site_center_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '站点前台中间部分logo',
  `additional_head` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '站点额外的head信息，直接传入<script/>这种',
  `user_uid` bigint NOT NULL COMMENT '此站点信息属于哪个用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '0:不删除 1： 删除',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `user_index`(`user_uid` ASC) USING BTREE,
  INDEX `create_index`(`create_time` ASC) USING BTREE COMMENT '创建时间',
  INDEX `union_site_index`(`uid` ASC, `user_uid` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_site
-- ----------------------------
INSERT INTO `au_site` VALUES (1526144941273128960, NULL, NULL, NULL, NULL, NULL, NULL, 1522074993315815424, '2022-05-16 18:18:03', NULL, 0);
INSERT INTO `au_site` VALUES (1526145056780066816, NULL, NULL, NULL, NULL, NULL, NULL, 1522074993315815424, '2022-05-16 18:18:30', NULL, 0);
INSERT INTO `au_site` VALUES (1526145197306028032, NULL, NULL, NULL, NULL, NULL, NULL, 1522074993315815424, '2022-05-16 18:19:04', NULL, 0);
INSERT INTO `au_site` VALUES (1526145691302764544, 'jr4m4', 'o3ae5', '8wcv9', 'q0cr7', '403ko', 'bb8kg', 1522074993315815424, '2022-05-16 18:21:01', NULL, 0);
INSERT INTO `au_site` VALUES (1526145698235949056, 'jr4m4', 'o3ae5', '8wcv9', 'q0cr7', '403ko', 'bb8kg', 1522074993315815424, '2022-05-16 18:21:03', NULL, 0);
INSERT INTO `au_site` VALUES (1526145701733998592, 'jr4m4', 'o3ae5', '8wcv9', 'q0cr7', '403ko', 'bb8kg', 1522074993315815424, '2022-05-16 18:21:04', NULL, 0);
INSERT INTO `au_site` VALUES (1526145705643089920, '1d8pg', 'sz4uz', 'isluw', 'jvkf7', 'gtc6h', 'fnam7', 1, '2022-05-16 18:32:03', '2022-05-16 18:32:03', 1);
INSERT INTO `au_site` VALUES (1526145776430358528, 'j6762', 'd2lx8', 'xf3h9', 'k1i4j', 'snsfh', '9vxd4', 1522074993315815424, '2022-05-16 18:21:22', NULL, 0);
INSERT INTO `au_site` VALUES (1526146422923599872, '1lzkl', 'ey5q8', 'lhk9m', '8avx1', 'up7s4', '28vej', 1522074993315815424, '2022-05-16 18:36:32', '2022-05-16 18:36:32', 1);

-- ----------------------------
-- Table structure for au_site_setting
-- ----------------------------
DROP TABLE IF EXISTS `au_site_setting`;
CREATE TABLE `au_site_setting`  (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `user_uid` bigint NOT NULL,
  `param_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_site_setting
-- ----------------------------

-- ----------------------------
-- Table structure for au_social
-- ----------------------------
DROP TABLE IF EXISTS `au_social`;
CREATE TABLE `au_social`  (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid,自增',
  `social_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '社交名称',
  `social_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此社交图标的地址',
  `social_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此社交的链接',
  `is_show` tinyint NOT NULL DEFAULT 1 COMMENT '1： 显示此社交 0： 不显示',
  `user_uid` bigint NULL DEFAULT NULL COMMENT '此社交属于哪个用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '1: 删除 0：不删除',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `create_time_index`(`create_time` ASC) USING BTREE COMMENT '创建时间索引',
  INDEX `user_uid_index`(`user_uid` ASC) USING BTREE COMMENT '用户uid索引',
  INDEX `union_social_index`(`uid` ASC, `user_uid` ASC, `create_time` ASC) USING BTREE COMMENT 'uid,user_uid,create_time联合索引'
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_social
-- ----------------------------
INSERT INTO `au_social` VALUES (6, 'ms19c', 'ks00b', 'syr72', 1, 1522074993315815424, '2022-05-16 18:43:50', NULL, 1);
INSERT INTO `au_social` VALUES (7, 'ms19c', 'ks00b', 'syr72', 1, 1522074993315815424, '2022-05-16 18:43:55', NULL, 1);
INSERT INTO `au_social` VALUES (8, 'nzsbg', '4bomm', 'mifm0', 1, 1522074993315815424, '2022-05-16 18:48:34', '2022-05-16 18:48:34', 1);

-- ----------------------------
-- Table structure for au_user
-- ----------------------------
DROP TABLE IF EXISTS `au_user`;
CREATE TABLE `au_user`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户简介',
  `nickname` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '用户性别',
  `login_uid` bigint NULL DEFAULT NULL COMMENT '用户登录记录的uid',
  `site_uid` bigint NULL DEFAULT NULL COMMENT '用户的站点配置uid',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户的头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户的密码',
  `username` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `profession` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户的工作集合',
  `email_uid` bigint NULL DEFAULT NULL COMMENT '此用户对应的邮箱uid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 1：删除 ',
  `is_account_lock` tinyint NOT NULL DEFAULT 0 COMMENT '1: 账户被锁住，0：未被锁住',
  `is_verify_email` tinyint NOT NULL COMMENT '1: 邮箱已验证，0：邮箱未验证',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `unique_username_index`(`username` ASC) USING BTREE COMMENT '用户名索引',
  UNIQUE INDEX `site_uid`(`site_uid` ASC) USING BTREE,
  UNIQUE INDEX `user_email`(`email_uid` ASC) USING BTREE,
  UNIQUE INDEX `user_login`(`login_uid` ASC) USING BTREE,
  UNIQUE INDEX `unique_email_uid_index`(`email_uid` ASC) USING BTREE COMMENT '邮箱Uid索引',
  UNIQUE INDEX `unique_site_uid_index`(`site_uid` ASC) USING BTREE COMMENT 'SiteUid索引',
  UNIQUE INDEX `unique_login_uid_index`(`login_uid` ASC) USING BTREE COMMENT 'LoginUid索引',
  INDEX `create_time_index`(`create_time` ASC) USING BTREE COMMENT '创建时间单独索引',
  INDEX `union_user_index`(`uid` ASC, `username` ASC, `create_time` ASC) USING BTREE COMMENT 'uid，用户名，创建时间联合索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_user
-- ----------------------------
INSERT INTO `au_user` VALUES (1522074993315815424, '享受孤独', '青', 'FEMALE', NULL, NULL, 'http://localhost/aurora-upload/jpg/2022/5/wallhaven-4yp1ok1653828797971.jpg', '$2a$10$xWo3iurzAeZnryZKvUSzUOBhH.nLYaZ7qVc2SwDBIhvLnC0TJSl1G', 'aurora', '计算机科学与技术', 1530936336502022144, '2022-05-19 13:57:34', '2022-06-05 10:58:01', 0, 0, 1);
INSERT INTO `au_user` VALUES (1602991773885931520, NULL, '是xcyeye', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$1lkESE9KEwZWwwZrZMMuvOIdQGsSLm8QUd/kgxLqzWKa7j5knH0K2', 'xcyeye', NULL, NULL, '2022-12-14 19:39:55', '2022-12-14 20:30:35', 1, 1, 0);

-- ----------------------------
-- Table structure for au_user_role
-- ----------------------------
DROP TABLE IF EXISTS `au_user_role`;
CREATE TABLE `au_user_role`  (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `role_uid` bigint NOT NULL COMMENT '角色uid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `user_uid` bigint NOT NULL COMMENT '用户uid',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `roleUid_index`(`role_uid` ASC) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_user_role
-- ----------------------------
INSERT INTO `au_user_role` VALUES (13, 3, '2023-01-14 21:49:47', NULL, 1522074993315815424);
INSERT INTO `au_user_role` VALUES (14, 4, '2023-01-14 21:49:47', NULL, 1522074993315815424);
INSERT INTO `au_user_role` VALUES (15, 5, '2023-01-14 21:49:47', NULL, 1522074993315815424);

-- ----------------------------
-- Table structure for au_white_url
-- ----------------------------
DROP TABLE IF EXISTS `au_white_url`;
CREATE TABLE `au_white_url`  (
  `uid` int NOT NULL AUTO_INCREMENT,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '白名单地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `url_unique_index`(`url` ASC) USING BTREE COMMENT '地址索引',
  INDEX `create_time_index`(`create_time` ASC) USING BTREE COMMENT '创建时间单独索引',
  INDEX `union_whiteUrl_index`(`uid` ASC, `url` ASC) USING BTREE COMMENT 'uid,url,create_time联合索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1080 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_white_url
-- ----------------------------
INSERT INTO `au_white_url` VALUES (14, 'POST:/auth/oauth/token/**', '2022-05-13 18:42:16', NULL);
INSERT INTO `au_white_url` VALUES (15, 'GET:/auth/**', '2022-05-13 18:44:38', NULL);
INSERT INTO `au_white_url` VALUES (16, 'POST:/auth/**', '2022-05-13 19:11:44', NULL);
INSERT INTO `au_white_url` VALUES (18, 'POST:/blog/link/**', '2022-05-13 22:32:21', NULL);
INSERT INTO `au_white_url` VALUES (20, 'POST:/auth/logout', '2022-05-16 00:14:27', NULL);
INSERT INTO `au_white_url` VALUES (21, 'GET:/swaggerui/*', '2022-05-16 22:02:36', NULL);
INSERT INTO `au_white_url` VALUES (22, 'GET:/swagger-ui/*', '2022-05-16 22:03:24', NULL);
INSERT INTO `au_white_url` VALUES (23, 'GET:/admin/swagger-ui/*', '2022-05-16 22:04:11', NULL);
INSERT INTO `au_white_url` VALUES (24, 'GET:/admin/swagger-ui/index.html', '2022-05-16 22:04:41', NULL);
INSERT INTO `au_white_url` VALUES (25, 'GET:/admin/swagger-ui/index.htm', '2022-05-16 22:05:38', NULL);
INSERT INTO `au_white_url` VALUES (30, 'GET:/admin/verifyAccount/**', '2022-05-17 14:12:02', NULL);
INSERT INTO `au_white_url` VALUES (32, 'POST:/message/messageLog/**', '2022-05-17 17:55:05', NULL);
INSERT INTO `au_white_url` VALUES (34, 'GET:/file/**', '2022-05-18 18:39:59', NULL);
INSERT INTO `au_white_url` VALUES (35, 'POST:/file/**', '2022-05-18 18:40:07', NULL);
INSERT INTO `au_white_url` VALUES (36, 'POST:/oauth/**', '2022-05-19 13:26:03', NULL);
INSERT INTO `au_white_url` VALUES (37, 'GET:/swagger-ui.html', '2022-05-21 00:19:46', NULL);
INSERT INTO `au_white_url` VALUES (38, 'GET:/v3/api-docs/**', '2022-05-21 13:28:41', NULL);
INSERT INTO `au_white_url` VALUES (39, 'GET:/webjars/swagger-ui/**', '2022-05-21 13:31:44', NULL);
INSERT INTO `au_white_url` VALUES (40, 'POST:/file/multi', '2022-06-04 19:09:51', NULL);
INSERT INTO `au_white_url` VALUES (43, 'GET:/**/v3/api-docs', '2022-06-25 15:27:11', NULL);
INSERT INTO `au_white_url` VALUES (1070, 'POST:/blog/article/queryListArticleByCondition', '2023-01-14 21:58:32', NULL);
INSERT INTO `au_white_url` VALUES (1071, 'POST:/admin/user/queryListUserByCondition', '2023-01-14 21:58:47', NULL);
INSERT INTO `au_white_url` VALUES (1072, 'POST:/admin/siteSetting/queryListSiteSettingByCondition', '2023-01-14 22:01:41', NULL);
INSERT INTO `au_white_url` VALUES (1073, 'POST:/blog/category/queryListCategoryByCondition', '2023-01-14 22:02:17', NULL);
INSERT INTO `au_white_url` VALUES (1074, 'POST:/blog/talk/queryListTalkByCondition', '2023-01-14 22:02:34', NULL);
INSERT INTO `au_white_url` VALUES (1075, 'POST:/admin/user/queryUserByUid', '2023-01-14 22:03:17', NULL);
INSERT INTO `au_white_url` VALUES (1076, 'POST:/admin/siteSetting/querySiteSettingByUserUid', '2023-01-14 22:03:36', NULL);
INSERT INTO `au_white_url` VALUES (1077, 'POST:/blog/tag/queryListTagByCondition', '2023-01-14 22:05:11', NULL);
INSERT INTO `au_white_url` VALUES (1078, 'POST:/blog/bulletin/queryListBulletinByCondition', '2023-01-14 22:05:40', NULL);
INSERT INTO `au_white_url` VALUES (1079, 'POST:/blog/link/queryListLinkByCondition', '2023-01-14 22:06:04', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1182 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
