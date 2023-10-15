/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟Docker
 Source Server Type    : MySQL
 Source Server Version : 80100 (8.1.0)
 Source Host           : 192.168.0.95:34567
 Source Schema         : aurora_admin

 Target Server Type    : MySQL
 Target Server Version : 80100 (8.1.0)
 File Encoding         : 65001

 Date: 15/10/2023 18:00:37
*/
DROP
DATABASE IF EXISTS `aurora_admin`;
CREATE
DATABASE `aurora_admin` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';
use aurora_admin;
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_admin_sidebar
-- ----------------------------

-- ----------------------------
-- Table structure for au_file
-- ----------------------------
DROP TABLE IF EXISTS `au_file`;
CREATE TABLE `au_file`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_uid` bigint NULL DEFAULT NULL COMMENT '用户uid',
  `file_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此文件的名称',
  `size` bigint NOT NULL COMMENT '此文件的大小 字节为单位',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此文件的简介',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此文件的存放路径，如果是对象存储，则表示objectName',
  `storage_mode` int NOT NULL COMMENT '文件存储的模式',
  `storage_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '存储的路径',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '1.: 已经删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传此文件的时间',
  `delete_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后删除时间',
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE,
  INDEX `fileName_index`(`file_name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_file
-- ----------------------------

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
INSERT INTO `au_login_info` VALUES (1634902515327836160, 'demoAurora', '中国云南省保山市', '106.58.204.208', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-03-12 21:01:49', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1634902640246792192, 'xcyeye', '中国云南省保山市', '106.58.204.208', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-03-12 21:02:19', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1634902767254511616, 'demoAurora', '中国云南省保山市', '106.58.204.208', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-03-12 21:02:49', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1634903071278637056, 'demoAurora', '中国云南省保山市', '106.58.204.208', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-03-12 21:04:01', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1634903363193806848, 'demoAurora', '中国云南省保山市', '106.58.204.208', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-03-12 21:05:11', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1713131795404431360, 'xcyeye', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 09:57:04', '2023-10-14 17:57:14', 0, 'xcyeye不存在');
INSERT INTO `au_login_info` VALUES (1713132755254124544, 'xcyeye', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 10:00:52', '2023-10-14 18:00:52', 0, 'xcyeye不存在');
INSERT INTO `au_login_info` VALUES (1713133228400975872, 'xcyeya', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 10:02:44', '2023-10-14 18:02:45', 0, 'xcyeya不存在');
INSERT INTO `au_login_info` VALUES (1713133376036282368, 'xcyeya', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 10:03:20', '2023-10-14 18:03:20', 0, 'xcyeya不存在');
INSERT INTO `au_login_info` VALUES (1713133556223582208, 'xcyeya', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 10:04:03', '2023-10-14 18:04:03', 0, 'xcyeya不存在');
INSERT INTO `au_login_info` VALUES (1713133605250801664, 'xcyeya', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 10:04:14', '2023-10-14 18:04:15', 0, 'xcyeya不存在');
INSERT INTO `au_login_info` VALUES (1713205413022932992, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 14:49:35', '2023-10-14 22:49:37', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713205689637281792, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 14:50:41', '2023-10-14 22:51:04', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713205856482500608, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 14:51:20', '2023-10-14 22:51:50', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713206165095194624, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 14:52:34', '2023-10-14 22:52:39', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713209538896863232, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 15:05:59', '2023-10-14 23:06:06', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713209841155186688, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 15:07:10', '2023-10-14 23:07:11', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713215073411014656, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 15:27:59', '2023-10-14 23:28:06', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713217020880887808, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 15:35:42', '2023-10-14 23:35:43', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713221471398141952, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 15:53:27', '2023-10-14 23:58:30', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713222819904626688, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 15:58:45', '2023-10-14 23:58:45', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713222893053288448, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 15:59:02', '2023-10-14 23:59:02', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713224053554290688, 'testuser', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 16:03:39', '2023-10-15 00:03:45', 0, 'testuser不存在');
INSERT INTO `au_login_info` VALUES (1713225150482227200, 'xcyeya', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-14 16:08:00', '2023-10-15 00:08:06', 0, 'xcyeya不存在');
INSERT INTO `au_login_info` VALUES (1713351583846244352, 'xcyeya', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-15 00:30:26', '2023-10-15 08:30:37', 0, 'xcyeya不存在');
INSERT INTO `au_login_info` VALUES (1713352216066269184, 'xcyeya', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-15 00:32:55', '2023-10-15 08:33:01', 0, 'xcyeya不存在');
INSERT INTO `au_login_info` VALUES (1713352341706645504, 'xcyeya', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 11 version: 119.0.0.0', '2023-10-15 00:33:25', '2023-10-15 08:33:31', 0, 'xcyeya不存在');
INSERT INTO `au_login_info` VALUES (1713363486656045056, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-10-15 01:17:43', '2023-10-15 09:18:15', 0, 'aaaaa不存在');
INSERT INTO `au_login_info` VALUES (1713363722304626688, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Unknown;Unknown version: null', '2023-10-15 01:18:39', '2023-10-15 09:18:39', 0, 'Unsupported grant type:  password');
INSERT INTO `au_login_info` VALUES (1713364052140498944, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-10-15 01:19:57', '2023-10-15 09:19:58', 0, 'aaaaa不存在');
INSERT INTO `au_login_info` VALUES (1713364116858609664, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Unknown;Unknown version: null', '2023-10-15 01:20:12', '2023-10-15 09:20:13', 0, 'aaaaa不存在');
INSERT INTO `au_login_info` VALUES (1713366544882475008, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Unknown;Unknown version: null', '2023-10-15 01:29:52', '2023-10-15 09:29:58', 0, 'aaaaa不存在');
INSERT INTO `au_login_info` VALUES (1713366884935671808, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Unknown;Unknown version: null', '2023-10-15 01:31:12', '2023-10-15 09:33:42', 0, 'aaaaa不存在');
INSERT INTO `au_login_info` VALUES (1713367571564208128, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Unknown;Unknown version: null', '2023-10-15 01:33:56', '2023-10-15 09:35:23', 0, 'aaaaa不存在');
INSERT INTO `au_login_info` VALUES (1713367985265188864, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Unknown;Unknown version: null', '2023-10-15 01:35:35', '2023-10-15 09:39:31', 0, 'aaaaa不存在');
INSERT INTO `au_login_info` VALUES (1713370205373210624, 'aaabbb', '未知', '0:0:0:0:0:0:0:1', 'Unknown;Unknown version: null', '2023-10-15 01:44:24', '2023-10-15 09:44:26', 0, 'aaabbb不存在');
INSERT INTO `au_login_info` VALUES (1713370362831577088, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Unknown;Unknown version: null', '2023-10-15 01:45:02', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1713371556060733440, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Unknown;Unknown version: null', '2023-10-15 01:49:48', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1713371845820030976, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Unknown;Unknown version: null', '2023-10-15 01:50:55', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1713372688510230528, 'xcyeya', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-10-15 01:54:16', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1713373113464528896, 'xcyeye', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-10-15 01:55:57', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1713396496345866240, 'aaaaa', '未知', '0:0:0:0:0:0:0:1', 'Unknown;Unknown version: null', '2023-10-15 03:28:53', NULL, 1, NULL);
INSERT INTO `au_login_info` VALUES (1713396619519991808, 'xcyeye', '未知', '0:0:0:0:0:0:0:1', 'Windows 10;Chrome 10 version: 108.0.0.0', '2023-10-15 03:29:22', NULL, 1, NULL);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_navigation
-- ----------------------------

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
  INDEX `create_time_index`(`create_time` ASC) USING BTREE,
  INDEX `path_index`(`path` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 174 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_permission
-- ----------------------------
INSERT INTO `au_permission` VALUES (12, '修改导航信息', 'POST:/admin/navigation/updateNavigation', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (13, '根据uid查询', 'POST:/admin/navigation/queryNavigationByUid', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (14, '根据条件查询', 'POST:/admin/navigation/queryListNavigationByCondition', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (15, '查询该用户所有的前台导航信息', 'POST:/admin/navigation/queryAllNavigationByUserUid', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (16, '物理删除', 'POST:/admin/navigation/physicsDeleteNavigation', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (17, '根据uid逻辑删除导航', 'POST:/admin/navigation/loginDeleteNavigation', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (18, '插入新的导航信息', 'POST:/admin/navigation/insertNavigation', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (19, '修改某个用户的角色', 'POST:/admin/permissionRelation/updateUserRole', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (20, '更新某个角色的权限', 'POST:/admin/permissionRelation/updateRolePermission', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (21, '根据permissionPath，查询哪些角色和角色可以可以访问', 'POST:/admin/permissionRelation/queryRoleByPermissionPath', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (22, '加载角色权限关系根据角色名，不返回用户信息', 'POST:/admin/permissionRelation/loadRolePermissionRelByRoleUid', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (23, '根据用户名，加载该用户所拥有的角色权限关系，此接口和loadPermissionByUserUid返回的数据一样', 'POST:/admin/permissionRelation/loadPermissionByUsername', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (24, '根据用户uid，加载该用户所拥有的角色权限关系', 'POST:/admin/permissionRelation/loadPermissionByUserUid', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (25, '根据角色名称，加载对应的角色-权限信息', 'POST:/admin/permissionRelation/loadPermissionByRoleName', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (26, '加载所有的角色权限关系，只返回该角色存在权限部分，如果某个角色没有赋予权限，则不返回', 'POST:/admin/permissionRelation/loadAllRolePermission', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (27, '根据用户名，获取该用户所拥有的所有角色', 'POST:/admin/permissionRelation/loadAllRoleByUsername', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (28, '批量为多个用户增加角色', 'POST:/admin/permissionRelation/batchInsertUserRole', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (29, '批量为多个角色增加权限', 'POST:/admin/permissionRelation/batchInsertRolePermission', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (30, '为某个用户删除多个角色', 'POST:/admin/permissionRelation/batchDeleteUserRole', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (31, '删除某个角色的多个权限', 'POST:/admin/permissionRelation/batchDeleteRolePermission', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (32, '修改路由权限信息', 'POST:/admin/routerPermission/updateRouterPermission', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (33, '根据权限uid查询此权限拥有的路由', 'POST:/admin/routerPermission/queryAllAdminRouterInfoByPermissionUid', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (34, '删除路由权限信息', 'POST:/admin/routerPermission/physicalDeleteRouterPermission', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (35, '新建一条路由权限关系', 'POST:/admin/routerPermission/insertRouterPermission', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (36, '修改社交信息', 'POST:/admin/social/updateSocial', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (37, '根据uid查询社交信息', 'POST:/admin/social/querySocialByUid', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (38, '根据条件查询社交信息', 'POST:/admin/social/queryListSocialByCondition', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (39, '物理删除此社交信息', 'POST:/admin/social/physicalDeleteSocial', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (40, '逻辑删除此社交信息', 'POST:/admin/social/loginDeleteSocial', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (41, '插入新的社交信息', 'POST:/admin/social/insertSocial', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (42, '根据uid修改站点配置', 'POST:/admin/siteSetting/updateSiteSetting', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (43, '根据uid查询站点配置', 'POST:/admin/siteSetting/querySiteSettingByUserUid', '2023-01-15 22:56:11', NULL);
INSERT INTO `au_permission` VALUES (45, '根据uid查询站点配置', 'POST:/admin/siteSetting/querySiteSettingByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (46, '根据条件查询站点配置', 'POST:/admin/siteSetting/queryListSiteSettingByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (47, '物理删除站点配置', 'POST:/admin/siteSetting/physicalDeleteSiteSetting', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (48, '插入站点配置', 'POST:/admin/siteSetting/insertSiteSetting', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (49, '修改路径权限信息', 'POST:/admin/permission/updatePermission', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (50, '根据uid查询权限', 'POST:/admin/permission/queryPermissionByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (51, '查询满足要求的所有权限信息', 'POST:/admin/permission/queryListPermissionByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (52, '删除权限', 'POST:/admin/permission/physicalDeletePermission', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (53, '插入路径权限', 'POST:/admin/permission/insertPermission', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (54, '删除权限', 'POST:/admin/permission/batchPhysicalDeletePermission', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (55, '批量插入路径权限', 'POST:/admin/permission/batchInsertPermission', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (56, '根据uid修改站点信息', 'POST:/admin/site/updateSite', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (57, '根据uid查询站点信息', 'POST:/admin/site/querySiteByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (58, '根据条件查询站点信息', 'POST:/admin/site/queryListSiteByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (59, '物理删除站点信息', 'POST:/admin/site/physicalDeleteSite', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (60, '逻辑删除此uid对应的站点信息', 'POST:/admin/site/logicDeleteSite', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (61, '插入站点信息', 'POST:/admin/site/insertSite', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (62, '修改用户信息', 'POST:/admin/user/updateUser', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (63, '更新密码', 'POST:/admin/user/updatePassword', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (64, '通过username查询用户信息', 'POST:/admin/user/queryUserByUsername', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (65, '通过username查询用户信息', 'POST:/admin/user/queryUserByUsernameContainPassword', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (66, '通过uid查询用户信息', 'POST:/admin/user/queryUserByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (67, '通过username查询用户信息', 'POST:/admin/user/queryOneData', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (68, '查询所有满足要求的用户信息', 'POST:/admin/user/queryListUserByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (69, '真正的从数据库中删除用户信息', 'POST:/admin/user/physicalDeleteUser', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (70, '逻辑删除用户信息', 'POST:/admin/user/logicDeleteUser', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (71, '添加新用户', 'POST:/admin/user/insertUser', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (72, '忘记密码', 'POST:/admin/user/forgotPassword', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (73, '绑定邮箱', 'POST:/admin/user/bindingEmail', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (74, '修改路由信息', 'POST:/admin/adminRouter/updateAdminRouter', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (75, '查询满足要求的所有路由信息', 'POST:/admin/adminRouter/queryListAdminRouterByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (76, '根据uid查询路由', 'POST:/admin/adminRouter/queryAdminRouterByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (77, '删除路由', 'POST:/admin/adminRouter/physicalDeleteAdminRouter', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (78, '插入路由', 'POST:/admin/adminRouter/insertAdminRouter', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (79, '修改白名单数据', 'POST:/admin/whiteUrl/updateWhiteUrl', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (80, '根据查询条件获取所有的白名单数据', 'POST:/admin/whiteUrl/queryListWhiteUrlByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (81, '根据uid删除白名单', 'POST:/admin/whiteUrl/physicalDeleteWhiteUrl', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (82, '插入白名单记录', 'POST:/admin/whiteUrl/insertWhiteUrl', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (83, '批量删除白名单', 'POST:/admin/whiteUrl/batchDeleteWhiteUrl', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (84, '修改角色信息', 'POST:/admin/role/updateRole', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (85, '根据uid查询角色', 'POST:/admin/role/queryRoleByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (86, '查询满足要求的所有角色信息', 'POST:/admin/role/queryListRoleByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (87, '删除角色', 'POST:/admin/role/physicalDeleteRole', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (88, '插入角色', 'POST:/admin/role/insertRole', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (89, '修改系统设置信息', 'POST:/admin/sysSetting/updateSysSetting', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (90, '根据uid查询系统设置', 'POST:/admin/sysSetting/querySysSettingByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (91, '查询满足要求的所有系统设置', 'POST:/admin/sysSetting/queryListSysSettingByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (92, '删除系统设置', 'POST:/admin/sysSetting/physicalDeleteSysSetting', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (93, '插入系统设置', 'POST:/admin/sysSetting/insertSysSetting', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (94, '修改说说内容', 'POST:/blog/talk/updateTalk', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (95, '根据uid查询说说', 'POST:/blog/talk/queryTalkByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (96, '根据条件查询说说', 'POST:/blog/talk/queryListTalkByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (97, '物理删除说说', 'POST:/blog/talk/physicalDeleteTalk', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (98, '逻辑删除说说', 'POST:/blog/talk/logicDeleteTalk', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (99, '插入新的说说', 'POST:/blog/talk/insertTalk', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (100, '修改文章数据', 'POST:/blog/article/updateArticle', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (101, '修改文章阅读数', 'POST:/blog/article/updateArticleReadNum', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (102, '修改文章点赞数', 'POST:/blog/article/updateArticleLikeNum', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (103, '通过条件查询文章数据', 'POST:/blog/article/queryListArticleByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (104, '根据uid查询文章数据', 'POST:/blog/article/queryArticleByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (105, '物理删除文章', 'POST:/blog/article/physicalDeleteArticle', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (106, '逻辑删除文章', 'POST:/blog/article/logicDeleteArticle', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (107, '插入新文章', 'POST:/blog/article/insertArticle', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (108, '修改公告内容', 'POST:/blog/bulletin/updateBulletin', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (109, '根据条件，查询满足要求的公告', 'POST:/blog/bulletin/queryListBulletinByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (110, '根据uid查询公告', 'POST:/blog/bulletin/queryBulletinByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (111, '物理删除公告', 'POST:/blog/bulletin/physicalDeleteBulletin', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (112, '逻辑删除公告', 'POST:/blog/bulletin/logicDeleteBulletin', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (113, '插入公告', 'POST:/blog/bulletin/insertBulletin', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (114, '修改类别信息', 'POST:/blog/category/updateCategory', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (115, '根据条件查询类别信息', 'POST:/blog/category/queryListCategoryByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (116, '根据uid查询类别信息', 'POST:/blog/category/queryCategoryByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (117, '物理删除类别信息', 'POST:/blog/category/physicalDeleteCategory', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (118, '逻辑删除类别', 'POST:/blog/category/logicDeleteCategory', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (119, '插入类别信息', 'POST:/blog/category/insertCategory', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (120, '修改友情链接信息', 'POST:/blog/link/updateLink', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (121, '修改友情链接的发布状态', 'POST:/blog/link/updateLinkPublishStatus', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (122, '根据条件查询', 'POST:/blog/link/queryListLinkByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (123, '根据uid查询', 'POST:/blog/link/queryLinkByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (124, '根据uid删除对应的友情链接', 'POST:/blog/link/physicalDeleteLink', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (125, '插入新友情链接', 'POST:/blog/link/insertLink', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (126, '修改标签信息', 'POST:/blog/tag/updateTag', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (127, '根据uid查询标签', 'POST:/blog/tag/queryTagByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (128, '根据条件查询标签', 'POST:/blog/tag/queryListTagByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (129, '物理删除标签数据', 'POST:/blog/tag/physicalDeleteTag', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (130, '逻辑删除标签信息', 'POST:/blog/tag/logicDeleteTag', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (131, '插入新标签', 'POST:/blog/tag/insertTag', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (132, '发送普通文本', 'POST:/message/sendMail/simpleText', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (133, '重新发送自定义邮件', 'POST:/message/sendMail/resendCustomMail', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (134, '发送自定义html', 'POST:/message/sendMail/customMail', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (135, '更新消费消息', 'POST:/message/messageLog/updateMessageLog', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (136, '重新投递此messageLogUid对应的mq消息', 'POST:/message/messageLog/resendRabbitMqMessage', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (137, '根据uid查询消费消息', 'POST:/message/messageLog/queryMessageLogByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (138, '查询所有消费消息', 'POST:/message/messageLog/queryListMessageLogByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (139, '删除消费消息', 'POST:/message/messageLog/physicalDeleteMessageLog', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (140, '插入新消费消息', 'POST:/message/messageLog/insertMessageLog', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (141, '根据emailDO实体，更新邮箱记录', 'POST:/message/email/updateEmail', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (142, '根据EmailDO实体中的字段以及分页参数查询所有数据，返回一个集合', 'POST:/message/email/queryListEmailByCondition', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (143, '根据userUid进行查询', 'POST:/message/email/queryEmailByUserUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (144, '根据uid查询', 'POST:/message/email/queryEmailByUid', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (145, '根据邮箱号进行查询', 'POST:/message/email/queryByEmailNumber', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (146, '根据唯一uid删除某条邮箱', 'POST:/message/email/physicalDeleteEmail', '2023-01-15 22:57:19', NULL);
INSERT INTO `au_permission` VALUES (147, '向数据库中插入新的邮箱记录，比如主机，授权码等', 'POST:/message/email/insertEmail', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (148, '根据uid更新邮件发送日志', 'POST:/message/emailLog/updateEmailLog', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (149, '查询所有邮件发送日志', 'POST:/message/emailLog/queryListEmailLogByCondition', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (150, '删除uid对应邮件发送日志', 'POST:/message/emailLog/physicalDeleteEmailLog', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (151, '插入邮件发送日志', 'POST:/message/emailLog/insertEmailLog', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (152, '更新评论', 'POST:/comment/updateComment', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (153, '重新发送评论的邮件通知', 'POST:/comment/resendEmail', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (154, '查询所有满足要求的所有评论', 'POST:/comment/queryListCommentByUidArr', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (155, '根据自定义条件查询所有评论', 'POST:/comment/queryListCommentByCondition', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (156, '根据uid查询评论', 'POST:/comment/queryCommentByUid', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (157, '删除单条评论', 'POST:/comment/physicalDeleteComment', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (158, '插入新评论', 'POST:/comment/insertComment', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (159, '根据用户名查询', 'POST:/login/loginInfo/queryLoginInfoByUsername', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (160, '根据条件查询', 'POST:/login/loginInfo/queryListLoginInfoByCondition', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (161, '根据uid删除登录日志', 'POST:/login/loginInfo/physicalDeleteLoginInfo', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (162, '根据uid，批量删除', 'POST:/login/loginInfo/batchDeleteLoginInfoByUid', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (163, '修改文件属性', 'POST:/file/updateFile', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (164, '在typora中自动上传图片', 'POST:/file/typoraUploadFile', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (165, '上传单个文件', 'POST:/file/singleUploadFile', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (166, '查询指定后缀的所有文件', 'POST:/file/querySpecifyFormatFiles', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (167, '查询该userUid所对应的所有文件的后缀信息', 'POST:/file/queryListFileFormat', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (168, '查询文件数据', 'POST:/file/queryListFileByCondition', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (169, '查询文件数据', 'POST:/file/queryFileByUid', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (170, '根据uid删除某个文件的信息，从数据库中删除', 'POST:/file/physicalDeleteFileInfo', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (171, '上传多个文件，返回集合', 'POST:/file/multiUploadFile', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (172, '根据uid删除某个文件', 'POST:/file/deleteFile', '2023-01-15 22:57:20', NULL);
INSERT INTO `au_permission` VALUES (173, '导入文章', 'POST:/blog/article/importArticle', '2023-02-09 21:35:14', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_role
-- ----------------------------
INSERT INTO `au_role` VALUES (3, 'admin', '2022-05-07 15:09:15', NULL, 0);
INSERT INTO `au_role` VALUES (4, 'user', '2022-05-07 15:10:26', NULL, 0);
INSERT INTO `au_role` VALUES (5, 'root', '2022-05-07 15:10:40', NULL, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `au_role_permission` VALUES (12, 4, 12, '2023-01-15 23:01:18', NULL);
INSERT INTO `au_role_permission` VALUES (13, 4, 13, '2023-01-15 23:01:18', NULL);
INSERT INTO `au_role_permission` VALUES (14, 4, 14, '2023-01-15 23:01:18', NULL);
INSERT INTO `au_role_permission` VALUES (15, 4, 15, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (16, 4, 17, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (17, 4, 18, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (18, 4, 36, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (19, 4, 37, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (20, 4, 38, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (21, 4, 39, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (22, 4, 40, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (23, 4, 41, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (24, 4, 42, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (25, 4, 43, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (26, 4, 45, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (27, 4, 46, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (28, 4, 47, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (29, 4, 48, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (30, 4, 56, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (31, 4, 57, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (32, 4, 59, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (33, 4, 60, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (34, 4, 61, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (35, 4, 62, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (36, 4, 63, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (37, 4, 64, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (38, 4, 66, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (39, 4, 67, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (40, 4, 68, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (41, 4, 70, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (42, 4, 73, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (43, 4, 72, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (44, 4, 91, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (45, 4, 159, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (46, 4, 160, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (47, 4, 152, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (48, 4, 153, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (49, 4, 154, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (50, 4, 155, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (51, 4, 156, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (52, 4, 157, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (53, 4, 158, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (54, 4, 94, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (55, 4, 95, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (56, 4, 96, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (57, 4, 97, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (58, 4, 98, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (59, 4, 99, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (60, 4, 100, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (61, 4, 101, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (62, 4, 102, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (63, 4, 103, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (64, 4, 104, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (65, 4, 105, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (66, 4, 106, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (67, 4, 107, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (68, 4, 108, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (69, 4, 109, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (70, 4, 110, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (71, 4, 111, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (72, 4, 112, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (73, 4, 113, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (74, 4, 114, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (75, 4, 115, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (76, 4, 116, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (77, 4, 117, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (78, 4, 118, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (79, 4, 119, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (80, 4, 120, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (81, 4, 121, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (82, 4, 122, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (83, 4, 123, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (84, 4, 124, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (85, 4, 125, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (86, 4, 126, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (87, 4, 127, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (88, 4, 128, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (89, 4, 129, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (90, 4, 130, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (91, 4, 131, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (92, 4, 132, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (93, 4, 133, '2023-01-15 23:01:19', NULL);
INSERT INTO `au_role_permission` VALUES (94, 4, 134, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (95, 4, 141, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (96, 4, 143, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (97, 4, 144, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (98, 4, 145, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (99, 4, 149, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (100, 4, 163, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (101, 4, 164, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (102, 4, 165, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (103, 4, 166, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (104, 4, 167, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (105, 4, 168, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (106, 4, 169, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (107, 4, 170, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (108, 4, 171, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (109, 4, 172, '2023-01-15 23:01:20', NULL);
INSERT INTO `au_role_permission` VALUES (110, 4, 173, '2023-02-09 21:36:43', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_setting
-- ----------------------------
INSERT INTO `au_setting` VALUES (1, 'nginx_file_host', 'nginx_file_host', 'https://cdn.xcye.xyz', '2023-01-14 22:55:52', '2023-01-15 00:18:48');
INSERT INTO `au_setting` VALUES (2, 'admin-web-url', 'admin-web-url', 'https://admin.xcye.xyz/', '2023-01-27 19:10:06', '2023-03-12 19:33:51');
INSERT INTO `au_setting` VALUES (3, 'page-web-url', 'page-web-url', 'https://blog.xcye.xyz/', '2023-01-27 19:10:31', '2023-03-12 19:33:40');
INSERT INTO `au_setting` VALUES (4, 'lazy-loading-img', 'lazy-loading-img', 'https://img1.imgtp.com/2023/03/12/zKprTaVP.png', '2023-01-27 20:13:32', '2023-01-27 20:22:33');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_site
-- ----------------------------

-- ----------------------------
-- Table structure for au_site_setting
-- ----------------------------
DROP TABLE IF EXISTS `au_site_setting`;
CREATE TABLE `au_site_setting`  (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `user_uid` bigint NOT NULL,
  `param_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `param_value` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_site_setting
-- ----------------------------
INSERT INTO `au_site_setting` VALUES (1, 1522074993315815424, '1522074993315815424AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-15 20:54:48', '2023-01-15 20:55:44');
INSERT INTO `au_site_setting` VALUES (2, 1522074993315815424, '1522074993315815424NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]', '2023-01-15 21:00:06', '2023-01-16 16:02:59');
INSERT INTO `au_site_setting` VALUES (3, 1522074993315815424, '1522074993315815424SiteInfo', '{\"readme\":\"#HiauUsernameua\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}],\"pcBackgroundImageList\":[\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/pc/10.jpg\"],\"mobileBackgroundImageList\":[\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/mobile/1.png\",\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/mobile/2.png\",\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/mobile/4.jpg\"]}', '2023-01-25 23:55:30', '2023-01-27 13:46:41');
INSERT INTO `au_site_setting` VALUES (68, 1634877081002713088, '1634877081002713088SiteInfo', '{\"readme\":\"# Hi xcyeye\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://img1.imgtp.com/2023/03/12/zKprTaVP.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://img1.imgtp.com/2023/03/12/FmYKnTsB.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1634877081002713088\",\"logo\":\"https://img1.imgtp.com/2023/03/12/KnUhin4l.png\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"其生若浮，其死若休\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1634877081002713088\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}],\"pcBackgroundImageList\":[\"https://img1.imgtp.com/2023/03/12/0rEXWQ7A.jpg\"],\"mobileBackgroundImageList\":[\"https://img1.imgtp.com/2023/03/12/ZMNyUr3q.png\",\"https://img1.imgtp.com/2023/03/12/FNxsWADm.png\",\"https://img1.imgtp.com/2023/03/12/qOHgaSsC.png\"]}', '2023-03-12 19:20:45', '2023-03-12 19:36:34');
INSERT INTO `au_site_setting` VALUES (69, 1634877081002713088, '1634877081002713088NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1634877081002713088\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634877081002713088\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634877081002713088\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634877081002713088\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634877081002713088\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634877081002713088\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634877081002713088\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634877081002713088/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1634877081002713088\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634877081002713088\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]', '2023-03-12 19:20:45', NULL);
INSERT INTO `au_site_setting` VALUES (70, 1634877081002713088, '1634877081002713088AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1634877081002713088\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634877081002713088\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634877081002713088\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1634877081002713088\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634877081002713088\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634877081002713088\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634877081002713088\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634877081002713088/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634877081002713088\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634877081002713088\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-03-12 19:20:45', NULL);
INSERT INTO `au_site_setting` VALUES (71, 1634877483471347712, '1634877483471347712SiteInfo', '{\"readme\":\"# Hi demoAurora\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-03-12 19:22:21', NULL);
INSERT INTO `au_site_setting` VALUES (72, 1634877483471347712, '1634877483471347712NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1634877483471347712\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634877483471347712\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634877483471347712\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634877483471347712\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634877483471347712\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634877483471347712\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634877483471347712\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634877483471347712/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1634877483471347712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634877483471347712\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]', '2023-03-12 19:22:21', NULL);
INSERT INTO `au_site_setting` VALUES (73, 1634877483471347712, '1634877483471347712AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1634877483471347712\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634877483471347712\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634877483471347712\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1634877483471347712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634877483471347712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634877483471347712\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634877483471347712\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634877483471347712/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634877483471347712\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634877483471347712\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-03-12 19:22:21', NULL);
INSERT INTO `au_site_setting` VALUES (74, 1634902506689011712, '1634902506689011712SiteInfo', '{\"readme\":\"# Hi demoAurora\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-03-12 21:01:47', NULL);
INSERT INTO `au_site_setting` VALUES (75, 1634902506689011712, '1634902506689011712NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1634902506689011712\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634902506689011712\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634902506689011712\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634902506689011712\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634902506689011712\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634902506689011712\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634902506689011712\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634902506689011712/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1634902506689011712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634902506689011712\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]', '2023-03-12 21:01:47', NULL);
INSERT INTO `au_site_setting` VALUES (76, 1634902506689011712, '1634902506689011712AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1634902506689011712\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634902506689011712\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634902506689011712\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1634902506689011712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634902506689011712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634902506689011712\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634902506689011712\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634902506689011712/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634902506689011712\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634902506689011712\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-03-12 21:01:47', NULL);
INSERT INTO `au_site_setting` VALUES (77, 1713133196622176256, '1713133196622176256SiteInfo', '{\"readme\":\"# Hi xcyeya\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-10-14 10:02:37', NULL);
INSERT INTO `au_site_setting` VALUES (78, 1713133196622176256, '1713133196622176256NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1713133196622176256\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1713133196622176256\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1713133196622176256\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1713133196622176256\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1713133196622176256\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1713133196622176256\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1713133196622176256\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1713133196622176256/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1713133196622176256\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1713133196622176256\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]', '2023-10-14 10:02:37', NULL);
INSERT INTO `au_site_setting` VALUES (79, 1713133196622176256, '1713133196622176256AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1713133196622176256\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1713133196622176256\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1713133196622176256\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1713133196622176256\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1713133196622176256\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1713133196622176256\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1713133196622176256\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1713133196622176256/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1713133196622176256\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1713133196622176256\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-10-14 10:02:37', NULL);
INSERT INTO `au_site_setting` VALUES (80, 1713205327737397248, '1713205327737397248SiteInfo', '{\"readme\":\"# Hi testuser\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-10-14 14:49:17', NULL);
INSERT INTO `au_site_setting` VALUES (81, 1713205327737397248, '1713205327737397248NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1713205327737397248\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1713205327737397248\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1713205327737397248\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1713205327737397248\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1713205327737397248\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1713205327737397248\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1713205327737397248\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1713205327737397248/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1713205327737397248\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1713205327737397248\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]', '2023-10-14 14:49:17', NULL);
INSERT INTO `au_site_setting` VALUES (82, 1713205327737397248, '1713205327737397248AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1713205327737397248\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1713205327737397248\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1713205327737397248\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1713205327737397248\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1713205327737397248\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1713205327737397248\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1713205327737397248\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1713205327737397248/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1713205327737397248\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1713205327737397248\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-10-14 14:49:17', NULL);
INSERT INTO `au_site_setting` VALUES (83, 1713363393351000064, '1713363393351000064SiteInfo', '{\"readme\":\"# Hi aaaaa\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-10-15 01:17:21', NULL);
INSERT INTO `au_site_setting` VALUES (84, 1713363393351000064, '1713363393351000064NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1713363393351000064\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1713363393351000064\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1713363393351000064\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1713363393351000064\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1713363393351000064\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1713363393351000064\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1713363393351000064\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1713363393351000064/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1713363393351000064\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1713363393351000064\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]', '2023-10-15 01:17:21', NULL);
INSERT INTO `au_site_setting` VALUES (85, 1713363393351000064, '1713363393351000064AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1713363393351000064\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1713363393351000064\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1713363393351000064\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1713363393351000064\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1713363393351000064\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1713363393351000064\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1713363393351000064\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1713363393351000064/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1713363393351000064\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1713363393351000064\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-10-15 01:17:21', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_social
-- ----------------------------

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_user
-- ----------------------------
INSERT INTO `au_user` VALUES (1634877081002713088, '其生若浮、其死若休', 'aurora-new', 'SECRET', NULL, NULL, 'https://img1.imgtp.com/2023/03/12/KnUhin4l.png', '$2a$10$QP.bFHZRtlwfB7dGIh431Opeb9S2GTUVkmk8D9BJJCuIpZYs.5fGe', 'xcyeye', '计算机科学与技术', 1634878801918877696, '2023-03-12 19:20:45', '2023-10-15 01:55:30', 0, 0, 1);
INSERT INTO `au_user` VALUES (1634902506689011712, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$hD3LK5OVkhhAu3ZpAGNmhesga8RRMa/fg3J6IZUQHS2jbFf2.ioIa', 'demoAurora', NULL, NULL, '2023-03-12 21:01:47', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1713133196622176256, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$QP.bFHZRtlwfB7dGIh431Opeb9S2GTUVkmk8D9BJJCuIpZYs.5fGe', 'xcyeya', NULL, NULL, '2023-10-14 10:02:37', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1713363393351000064, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$1z/Lj/urZno.wC17/727Wemu6fzFvgB3x0VELAVCLzCVJjc2wV..K', 'aaaaa', NULL, NULL, '2023-10-15 01:17:20', '2023-10-15 01:56:17', 1, 0, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of au_user_role
-- ----------------------------
INSERT INTO `au_user_role` VALUES (13, 3, '2023-01-14 21:49:47', '2023-03-12 19:23:32', 1634877081002713088);
INSERT INTO `au_user_role` VALUES (15, 5, '2023-01-14 21:49:47', '2023-03-12 19:23:37', 1634877081002713088);
INSERT INTO `au_user_role` VALUES (84, 4, '2023-03-12 19:20:45', NULL, 1634877081002713088);
INSERT INTO `au_user_role` VALUES (86, 4, '2023-03-12 21:01:47', NULL, 1634902506689011712);
INSERT INTO `au_user_role` VALUES (87, 5, '2023-03-12 21:02:35', NULL, 1634902506689011712);
INSERT INTO `au_user_role` VALUES (88, 4, '2023-10-14 10:02:37', NULL, 1713133196622176256);
INSERT INTO `au_user_role` VALUES (89, 4, '2023-10-14 14:49:16', NULL, 1713205327737397248);
INSERT INTO `au_user_role` VALUES (90, 4, '2023-10-15 01:17:20', NULL, 1713363393351000064);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `au_white_url` VALUES (1080, 'POST:/blog/article/queryArticleByUid', '2023-01-15 19:43:54', NULL);
INSERT INTO `au_white_url` VALUES (1081, 'POST:/comment/queryListCommentByUidArr', '2023-01-15 19:44:08', NULL);
INSERT INTO `au_white_url` VALUES (1082, 'POST:/admin/user/queryUserByUsername', '2023-01-15 19:44:25', NULL);
INSERT INTO `au_white_url` VALUES (1083, 'POST:/blog/article/updateArticleReadNum', '2023-01-15 19:44:47', NULL);
INSERT INTO `au_white_url` VALUES (1084, 'POST:/comment/queryListCommentByCondition', '2023-01-15 19:45:11', NULL);
INSERT INTO `au_white_url` VALUES (1085, 'POST:/blog/article/updateArticleLikeNum', '2023-01-15 19:45:38', NULL);
INSERT INTO `au_white_url` VALUES (1087, 'POST:/file/singleUploadFile', '2023-01-15 19:46:33', NULL);
INSERT INTO `au_white_url` VALUES (1088, 'POST:/admin/user/insertUser', '2023-01-15 22:06:40', NULL);
INSERT INTO `au_white_url` VALUES (1089, 'POST:/message/email/queryByEmailNumber', '2023-01-15 22:11:13', NULL);
INSERT INTO `au_white_url` VALUES (1090, 'POST:/message/email/insertEmail', '2023-01-15 22:14:05', NULL);
INSERT INTO `au_white_url` VALUES (1091, 'POST:/auth/oauthClient/insertOauthClient', '2023-01-15 22:14:22', NULL);
INSERT INTO `au_white_url` VALUES (1092, 'GET:/', '2023-01-15 23:20:34', NULL);
INSERT INTO `au_white_url` VALUES (1093, 'POST:/blog/talk/updateTalkLikeNum', '2023-01-16 23:55:40', NULL);
INSERT INTO `au_white_url` VALUES (1094, 'POST:/admin/sysSetting/queryListSysSettingByCondition', '2023-01-17 23:24:44', NULL);
INSERT INTO `au_white_url` VALUES (1095, 'POST:/blog/article/queryListArticleByTagOrCategory', '2023-01-19 14:06:01', NULL);
INSERT INTO `au_white_url` VALUES (1099, 'POST:/**/queryTotal*Count', '2023-01-26 23:25:22', NULL);

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(48) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '客户端id',
  `resource_ids` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '资源的id，多个用逗号分隔',
  `client_secret` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '客户端的秘钥',
  `scope` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '客户端的权限，多个用逗号分隔',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '授权类型，五种，多个用逗号分隔',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '授权码模式的跳转uri',
  `authorities` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限，多个用逗号分隔',
  `access_token_validity` int NULL DEFAULT NULL COMMENT 'access_token的过期时间，单位毫秒，覆盖掉硬编码',
  `refresh_token_validity` int NULL DEFAULT NULL COMMENT 'refresh_token的过期时间，单位毫秒，覆盖掉硬编码',
  `additional_information` varchar(4096) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '扩展字段，JSON',
  `autoapprove` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '默认false，是否自动授权',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('aaaaa', NULL, '$2a$10$zyTGmM7aNsS5xUS9Alm04.NVOhkmXrAFysiYhA3gd0Fpottk3OiOO', 'all', 'authorization_code,client_credentials,refresh_token,password', NULL, NULL, NULL, NULL, NULL, NULL, '2023-10-15 01:17:41', NULL);
INSERT INTO `oauth_client_details` VALUES ('demoAurora', NULL, '$2a$10$p8IfLT13u1sN8xt3/UKikO89mAg904PZ5XHEs2m42oyy/TNrHytpG', 'all', 'authorization_code,client_credentials,refresh_token,password', NULL, NULL, NULL, NULL, NULL, NULL, '2023-03-12 21:01:48', NULL);
INSERT INTO `oauth_client_details` VALUES ('testuser', NULL, '$2a$10$2LguCgAhsimAseJ.V5n6nOEDPlBK3FcEguMljDF9R0jrDQgQYNUMq', 'all', 'authorization_code,client_credentials,refresh_token,password', NULL, NULL, NULL, NULL, NULL, NULL, '2023-10-14 14:49:34', NULL);
INSERT INTO `oauth_client_details` VALUES ('xcyeya', NULL, '$2a$10$bODMc7qrDgE1CJZ/ULOY.eh1Rm.KPbmQGsUIvRTJwACXGh1E8T0ye', 'all', 'authorization_code,client_credentials,refresh_token,password', NULL, NULL, NULL, NULL, NULL, NULL, '2023-10-14 10:02:44', NULL);
INSERT INTO `oauth_client_details` VALUES ('xcyeye', NULL, '$2a$10$QP.bFHZRtlwfB7dGIh431Opeb9S2GTUVkmk8D9BJJCuIpZYs.5fGe', 'all', 'authorization_code,client_credentials,refresh_token,password', NULL, NULL, NULL, NULL, NULL, NULL, '2023-03-12 19:20:52', '2023-10-15 01:55:48');

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

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1198 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
