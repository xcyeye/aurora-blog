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

 Date: 28/01/2023 00:57:16
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
  INDEX `create_time_index`(`create_time` ASC) USING BTREE,
  INDEX `path_index`(`path` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 173 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_setting
-- ----------------------------
INSERT INTO `au_setting` VALUES (1, 'nginx_file_host', 'nginx_file_host', 'http://192.168.31.26', '2023-01-14 22:55:52', '2023-01-15 00:18:48');
INSERT INTO `au_setting` VALUES (2, 'admin-web-url', 'admin-web-url', 'http://localhost:3400', '2023-01-27 19:10:06', NULL);
INSERT INTO `au_setting` VALUES (3, 'page-web-url', 'page-web-url', 'http://localhost:7899', '2023-01-27 19:10:31', NULL);
INSERT INTO `au_setting` VALUES (4, 'lazy-loading-img', 'lazy-loading-img', 'https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/other/lazyLoadImg.png', '2023-01-27 20:13:32', '2023-01-27 20:22:33');

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
  `param_value` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_site_setting
-- ----------------------------
INSERT INTO `au_site_setting` VALUES (1, 1522074993315815424, '1522074993315815424AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-15 20:54:48', '2023-01-15 20:55:44');
INSERT INTO `au_site_setting` VALUES (2, 1522074993315815424, '1522074993315815424NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]', '2023-01-15 21:00:06', '2023-01-16 16:02:59');
INSERT INTO `au_site_setting` VALUES (3, 1522074993315815424, '1522074993315815424SiteInfo', '{\"readme\":\"#HiauUsernameua\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}],\"pcBackgroundImageList\":[\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/pc/10.jpg\"],\"mobileBackgroundImageList\":[\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/mobile/1.png\",\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/mobile/2.png\",\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/mobile/4.jpg\"]}', '2023-01-25 23:55:30', '2023-01-27 13:46:41');
INSERT INTO `au_site_setting` VALUES (8, 1618823263647440896, '1618823263647440896NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:08:37', NULL);
INSERT INTO `au_site_setting` VALUES (9, 1618823263647440896, '1618823263647440896AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:08:37', NULL);
INSERT INTO `au_site_setting` VALUES (10, 1618823985617182720, '1618823985617182720NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:11:29', NULL);
INSERT INTO `au_site_setting` VALUES (11, 1618823985617182720, '1618823985617182720AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:11:29', NULL);
INSERT INTO `au_site_setting` VALUES (12, 1618824237480943616, '1618824237480943616NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:12:29', NULL);
INSERT INTO `au_site_setting` VALUES (13, 1618824237480943616, '1618824237480943616AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:12:29', NULL);
INSERT INTO `au_site_setting` VALUES (14, 1618824892270518272, '1618824892270518272NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:15:05', NULL);
INSERT INTO `au_site_setting` VALUES (15, 1618824892270518272, '1618824892270518272AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:15:05', NULL);
INSERT INTO `au_site_setting` VALUES (16, 1618826587436228608, '1618826587436228608NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618826587436228608\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618826587436228608\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618826587436228608\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618826587436228608\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618826587436228608\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618826587436228608\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618826587436228608\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618826587436228608/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618826587436228608\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618826587436228608\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:21:49', NULL);
INSERT INTO `au_site_setting` VALUES (17, 1618826587436228608, '1618826587436228608AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618826587436228608\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618826587436228608\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618826587436228608\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618826587436228608\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618826587436228608\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618826587436228608\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618826587436228608\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618826587436228608/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618826587436228608\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618826587436228608\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:21:49', NULL);
INSERT INTO `au_site_setting` VALUES (18, 1618827111703257088, '1618827111703257088NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618827111703257088\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618827111703257088\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618827111703257088\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618827111703257088\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618827111703257088\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618827111703257088\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618827111703257088\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618827111703257088/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618827111703257088\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618827111703257088\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:23:54', NULL);
INSERT INTO `au_site_setting` VALUES (19, 1618827111703257088, '1618827111703257088AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618827111703257088\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618827111703257088\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618827111703257088\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618827111703257088\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618827111703257088\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618827111703257088\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618827111703257088\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618827111703257088/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618827111703257088\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618827111703257088\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:23:54', NULL);
INSERT INTO `au_site_setting` VALUES (20, 1618827668509696000, '1618827668509696000NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618827668509696000\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618827668509696000\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618827668509696000\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618827668509696000\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618827668509696000\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618827668509696000\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618827668509696000\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618827668509696000/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618827668509696000\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618827668509696000\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:26:07', NULL);
INSERT INTO `au_site_setting` VALUES (21, 1618827668509696000, '1618827668509696000AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618827668509696000\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618827668509696000\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618827668509696000\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618827668509696000\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618827668509696000\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618827668509696000\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618827668509696000\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618827668509696000/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618827668509696000\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618827668509696000\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:26:07', NULL);
INSERT INTO `au_site_setting` VALUES (22, 1618829155868614656, '1618829155868614656NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618829155868614656\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618829155868614656\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618829155868614656\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618829155868614656\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618829155868614656\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618829155868614656\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618829155868614656\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618829155868614656/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618829155868614656\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618829155868614656\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:32:20', NULL);
INSERT INTO `au_site_setting` VALUES (23, 1618829155868614656, '1618829155868614656AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618829155868614656\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618829155868614656\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618829155868614656\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618829155868614656\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618829155868614656\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618829155868614656\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618829155868614656\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618829155868614656/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618829155868614656\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618829155868614656\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:32:20', NULL);
INSERT INTO `au_site_setting` VALUES (24, 1618829915662589952, '1618829915662589952NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618829915662589952\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618829915662589952\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618829915662589952\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618829915662589952\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618829915662589952\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618829915662589952\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618829915662589952\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618829915662589952/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618829915662589952\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618829915662589952\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:35:07', NULL);
INSERT INTO `au_site_setting` VALUES (25, 1618829915662589952, '1618829915662589952AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618829915662589952\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618829915662589952\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618829915662589952\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618829915662589952\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618829915662589952\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618829915662589952\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618829915662589952\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618829915662589952/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618829915662589952\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618829915662589952\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 12:35:07', NULL);
INSERT INTO `au_site_setting` VALUES (26, 1618852627151855616, '1618852627151855616SiteInfo', '\\{\"readme\":\"# Hi aurora34785\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-01-27 14:05:18', NULL);
INSERT INTO `au_site_setting` VALUES (27, 1618852627151855616, '1618852627151855616NavbarInfo', '[{\\\"name\\\":\\\"首页\\\",\\\"url\\\":\\\"/user/1618852627151855616\\\",\\\"icon\\\":\\\"fa:home\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"友情链接\\\",\\\"url\\\":\\\"/friendLink/1618852627151855616\\\",\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"关于\\\",\\\"url\\\":\\\"/about/1618852627151855616\\\",\\\"icon\\\":\\\"fa:pagelines\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"评论\\\",\\\"url\\\":\\\"/comment/1618852627151855616\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"相册\\\",\\\"url\\\":\\\"/photo/1618852627151855616\\\",\\\"icon\\\":\\\"fa:image\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"时间轴\\\",\\\"url\\\":\\\"/archive/1618852627151855616\\\",\\\"icon\\\":\\\"fa:hourglass-3\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"分类\\\",\\\"url\\\":\\\"/category/1618852627151855616\\\",\\\"icon\\\":\\\"mdi:alarm-bell\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"标签\\\",\\\"url\\\":\\\"/tag/1618852627151855616/\\\",\\\"icon\\\":\\\"mdi:abugida-thai\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说\\\",\\\"url\\\":\\\"\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[{\\\"name\\\":\\\"说说1\\\",\\\"url\\\":\\\"/shareSpace/1618852627151855616\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说2\\\",\\\"url\\\":\\\"/shareSpace-page/1618852627151855616\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]}]}],', '2023-01-27 14:05:18', NULL);
INSERT INTO `au_site_setting` VALUES (28, 1618852627151855616, '1618852627151855616AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618852627151855616\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618852627151855616\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618852627151855616\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618852627151855616\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618852627151855616\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618852627151855616\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618852627151855616\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618852627151855616/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618852627151855616\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618852627151855616\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 14:05:18', NULL);
INSERT INTO `au_site_setting` VALUES (29, 1618853167646646272, '1618853167646646272SiteInfo', '\\{\"readme\":\"# Hi aurora9o54\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-01-27 14:07:26', NULL);
INSERT INTO `au_site_setting` VALUES (30, 1618853167646646272, '1618853167646646272NavbarInfo', '[{\\\"name\\\":\\\"首页\\\",\\\"url\\\":\\\"/user/1618853167646646272\\\",\\\"icon\\\":\\\"fa:home\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"友情链接\\\",\\\"url\\\":\\\"/friendLink/1618853167646646272\\\",\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"关于\\\",\\\"url\\\":\\\"/about/1618853167646646272\\\",\\\"icon\\\":\\\"fa:pagelines\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"评论\\\",\\\"url\\\":\\\"/comment/1618853167646646272\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"相册\\\",\\\"url\\\":\\\"/photo/1618853167646646272\\\",\\\"icon\\\":\\\"fa:image\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"时间轴\\\",\\\"url\\\":\\\"/archive/1618853167646646272\\\",\\\"icon\\\":\\\"fa:hourglass-3\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"分类\\\",\\\"url\\\":\\\"/category/1618853167646646272\\\",\\\"icon\\\":\\\"mdi:alarm-bell\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"标签\\\",\\\"url\\\":\\\"/tag/1618853167646646272/\\\",\\\"icon\\\":\\\"mdi:abugida-thai\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说\\\",\\\"url\\\":\\\"\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[{\\\"name\\\":\\\"说说1\\\",\\\"url\\\":\\\"/shareSpace/1618853167646646272\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说2\\\",\\\"url\\\":\\\"/shareSpace-page/1618853167646646272\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]}]}],', '2023-01-27 14:07:26', NULL);
INSERT INTO `au_site_setting` VALUES (31, 1618853167646646272, '1618853167646646272AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618853167646646272\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618853167646646272\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618853167646646272\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618853167646646272\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618853167646646272\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618853167646646272\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618853167646646272\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618853167646646272/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618853167646646272\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618853167646646272\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 14:07:26', NULL);
INSERT INTO `au_site_setting` VALUES (32, 1618855879712972800, '1618855879712972800SiteInfo', '{\"readme\":', '2023-01-27 14:18:13', NULL);
INSERT INTO `au_site_setting` VALUES (33, 1618855879712972800, '1618855879712972800NavbarInfo', '[{\\\"name\\\":\\\"首页\\\",\\\"url\\\":\\\"/user/1618855879712972800\\\",\\\"icon\\\":\\\"fa:home\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"友情链接\\\",\\\"url\\\":\\\"/friendLink/1618855879712972800\\\",\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"关于\\\",\\\"url\\\":\\\"/about/1618855879712972800\\\",\\\"icon\\\":\\\"fa:pagelines\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"评论\\\",\\\"url\\\":\\\"/comment/1618855879712972800\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"相册\\\",\\\"url\\\":\\\"/photo/1618855879712972800\\\",\\\"icon\\\":\\\"fa:image\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"时间轴\\\",\\\"url\\\":\\\"/archive/1618855879712972800\\\",\\\"icon\\\":\\\"fa:hourglass-3\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"分类\\\",\\\"url\\\":\\\"/category/1618855879712972800\\\",\\\"icon\\\":\\\"mdi:alarm-bell\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"标签\\\",\\\"url\\\":\\\"/tag/1618855879712972800/\\\",\\\"icon\\\":\\\"mdi:abugida-thai\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说\\\",\\\"url\\\":\\\"\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[{\\\"name\\\":\\\"说说1\\\",\\\"url\\\":\\\"/shareSpace/1618855879712972800\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说2\\\",\\\"url\\\":\\\"/shareSpace-page/1618855879712972800\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]}]}],', '2023-01-27 14:18:13', NULL);
INSERT INTO `au_site_setting` VALUES (34, 1618855879712972800, '1618855879712972800AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618855879712972800\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618855879712972800\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618855879712972800\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618855879712972800\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618855879712972800\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618855879712972800\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618855879712972800\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618855879712972800/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618855879712972800\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618855879712972800\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 14:18:13', NULL);
INSERT INTO `au_site_setting` VALUES (35, 1618858290309505024, '1618858290309505024SiteInfo', '{\"readme\":\"#Hiaurora23iu\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-01-27 14:27:48', NULL);
INSERT INTO `au_site_setting` VALUES (36, 1618858290309505024, '1618858290309505024NavbarInfo', '[{\\\"name\\\":\\\"首页\\\",\\\"url\\\":\\\"/user/1618858290309505024\\\",\\\"icon\\\":\\\"fa:home\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"友情链接\\\",\\\"url\\\":\\\"/friendLink/1618858290309505024\\\",\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"关于\\\",\\\"url\\\":\\\"/about/1618858290309505024\\\",\\\"icon\\\":\\\"fa:pagelines\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"评论\\\",\\\"url\\\":\\\"/comment/1618858290309505024\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"相册\\\",\\\"url\\\":\\\"/photo/1618858290309505024\\\",\\\"icon\\\":\\\"fa:image\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"时间轴\\\",\\\"url\\\":\\\"/archive/1618858290309505024\\\",\\\"icon\\\":\\\"fa:hourglass-3\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"分类\\\",\\\"url\\\":\\\"/category/1618858290309505024\\\",\\\"icon\\\":\\\"mdi:alarm-bell\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"标签\\\",\\\"url\\\":\\\"/tag/1618858290309505024/\\\",\\\"icon\\\":\\\"mdi:abugida-thai\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说\\\",\\\"url\\\":\\\"\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[{\\\"name\\\":\\\"说说1\\\",\\\"url\\\":\\\"/shareSpace/1618858290309505024\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说2\\\",\\\"url\\\":\\\"/shareSpace-page/1618858290309505024\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]}]}],', '2023-01-27 14:27:48', NULL);
INSERT INTO `au_site_setting` VALUES (37, 1618858290309505024, '1618858290309505024AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618858290309505024\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618858290309505024\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618858290309505024\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618858290309505024\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618858290309505024\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618858290309505024\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618858290309505024\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618858290309505024/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618858290309505024\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618858290309505024\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 14:27:48', NULL);
INSERT INTO `au_site_setting` VALUES (38, 1618858615108018176, '1618858615108018176SiteInfo', '{\"readme\":\"# Hi aurora45234\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-01-27 14:29:15', NULL);
INSERT INTO `au_site_setting` VALUES (39, 1618858615108018176, '1618858615108018176NavbarInfo', '[{\\\"name\\\":\\\"首页\\\",\\\"url\\\":\\\"/user/1618858615108018176\\\",\\\"icon\\\":\\\"fa:home\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"友情链接\\\",\\\"url\\\":\\\"/friendLink/1618858615108018176\\\",\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"关于\\\",\\\"url\\\":\\\"/about/1618858615108018176\\\",\\\"icon\\\":\\\"fa:pagelines\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"评论\\\",\\\"url\\\":\\\"/comment/1618858615108018176\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"相册\\\",\\\"url\\\":\\\"/photo/1618858615108018176\\\",\\\"icon\\\":\\\"fa:image\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"时间轴\\\",\\\"url\\\":\\\"/archive/1618858615108018176\\\",\\\"icon\\\":\\\"fa:hourglass-3\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"分类\\\",\\\"url\\\":\\\"/category/1618858615108018176\\\",\\\"icon\\\":\\\"mdi:alarm-bell\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"标签\\\",\\\"url\\\":\\\"/tag/1618858615108018176/\\\",\\\"icon\\\":\\\"mdi:abugida-thai\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说\\\",\\\"url\\\":\\\"\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[{\\\"name\\\":\\\"说说1\\\",\\\"url\\\":\\\"/shareSpace/1618858615108018176\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说2\\\",\\\"url\\\":\\\"/shareSpace-page/1618858615108018176\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]}]}],', '2023-01-27 14:29:15', NULL);
INSERT INTO `au_site_setting` VALUES (40, 1618858615108018176, '1618858615108018176AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618858615108018176\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618858615108018176\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618858615108018176\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618858615108018176\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618858615108018176\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618858615108018176\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618858615108018176\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618858615108018176/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618858615108018176\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618858615108018176\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 14:29:15', NULL);
INSERT INTO `au_site_setting` VALUES (41, 1618859823881265152, '1618859823881265152SiteInfo', '{\"readme\":\"# Hi auroraasfd\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-01-27 14:33:53', NULL);
INSERT INTO `au_site_setting` VALUES (42, 1618859823881265152, '1618859823881265152NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]', '2023-01-27 14:33:53', NULL);
INSERT INTO `au_site_setting` VALUES (43, 1618859823881265152, '1618859823881265152AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618859823881265152\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618859823881265152\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618859823881265152\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618859823881265152\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618859823881265152\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618859823881265152\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618859823881265152\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618859823881265152/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618859823881265152\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618859823881265152\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 14:33:53', NULL);
INSERT INTO `au_site_setting` VALUES (44, 1618868360900517888, '1618868360900517888SiteInfo', '{\"readme\":\"# Hi auroraNewUser\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-01-27 15:07:49', NULL);
INSERT INTO `au_site_setting` VALUES (45, 1618868360900517888, '1618868360900517888NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618868360900517888\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618868360900517888\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618868360900517888\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618868360900517888\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618868360900517888\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618868360900517888\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618868360900517888\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618868360900517888/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1618868360900517888\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618868360900517888\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]', '2023-01-27 15:07:49', NULL);
INSERT INTO `au_site_setting` VALUES (46, 1618868360900517888, '1618868360900517888AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618868360900517888\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618868360900517888\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618868360900517888\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618868360900517888\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618868360900517888\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618868360900517888\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618868360900517888\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618868360900517888/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618868360900517888\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618868360900517888\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 15:07:49', NULL);
INSERT INTO `au_site_setting` VALUES (47, 1618923200477405184, '1618923200477405184SiteInfo', '{\"readme\":\"# Hi newComment\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"Aurora博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"Aurora博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"Aurora博客系统和Aurora主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}', '2023-01-27 18:45:44', NULL);
INSERT INTO `au_site_setting` VALUES (48, 1618923200477405184, '1618923200477405184NavbarInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618923200477405184\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618923200477405184\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618923200477405184\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618923200477405184\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618923200477405184\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618923200477405184\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618923200477405184\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618923200477405184/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1618923200477405184\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618923200477405184\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]', '2023-01-27 18:45:44', NULL);
INSERT INTO `au_site_setting` VALUES (49, 1618923200477405184, '1618923200477405184AllPageInfo', '[{\"name\":\"首页\",\"url\":\"/user/1618923200477405184\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1618923200477405184\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1618923200477405184\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1618923200477405184\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1618923200477405184\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1618923200477405184\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1618923200477405184\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1618923200477405184/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1618923200477405184\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1618923200477405184\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]', '2023-01-27 18:45:44', NULL);

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
INSERT INTO `au_user` VALUES (1522074993315815424, '享受孤独', '青', 'FEMALE', NULL, NULL, 'http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg', '$2a$10$xWo3iurzAeZnryZKvUSzUOBhH.nLYaZ7qVc2SwDBIhvLnC0TJSl1G', 'aurora', '计算机科学与技术', 1530936336502022144, '2022-05-19 13:57:34', '2023-01-15 20:35:45', 0, 0, 1);
INSERT INTO `au_user` VALUES (1602991773885931520, NULL, '是xcyeye', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$1lkESE9KEwZWwwZrZMMuvOIdQGsSLm8QUd/kgxLqzWKa7j5knH0K2', 'xcyeye', NULL, NULL, '2022-12-14 19:39:55', '2022-12-14 20:30:35', 1, 1, 0);
INSERT INTO `au_user` VALUES (1614626868384243712, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$9xxYCN9yaP1VpeDHfTKlauvcKQWYNGQ2xRzp1l..r5ZupA6cbZ2/e', 'testUser1', NULL, NULL, '2023-01-15 22:13:38', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1614627188367695872, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$rshcKB8RFOe8DRVEbjainuLApxAgZfJPwqStWiZFA9Nea0oWStHl6', 'userTest2', NULL, NULL, '2023-01-15 22:14:54', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1614628221676429312, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$Yn/vOAvBWbzNDthXeRCLnO1FTTlatG5x/qs6.NAui2rz6MQnIR2.m', 'uset2', NULL, NULL, '2023-01-15 22:19:01', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1614629284898938880, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$pzunEFEagrR7vZ8i4f5G5eZafipan/ZLMwMlIlc2vnswMcLMHrdAS', 'userTest3', NULL, NULL, '2023-01-15 22:23:14', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1614629991534305280, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$HIjz7dgj4sME2U8hRM1DPOCnp8pHhIOS9Sn/4LiOLyUeRuzcds4DK', 'userTest45', NULL, NULL, '2023-01-15 22:26:03', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1614632347701354496, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$BcWrCEmo1V8m3qpu7R7eq.cdk7uOWO533ikU2sQwV7HDDFkVIuFge', 'userTest67', NULL, NULL, '2023-01-15 22:35:24', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1614632543302721536, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$Izh34x0ilN5WN5SIQUwdt.GcE26zBhdxmZJWBu.iGVQ9GlR0p3KNG', 'userTest445', NULL, NULL, '2023-01-15 22:36:11', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1614633248822403072, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$dNhbKnLiwzpwWbdT19oGdufM4FyBhDOgYt8bwiW6VX5P1DQT7myeu', 'usertest666', NULL, NULL, '2023-01-15 22:38:59', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1614633782836994048, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$hXqhUtcIScVGSFscbcQTA.7pjMvppXIp1yxeucXL3qvDkV5ADb/oi', 'usetTest65', NULL, NULL, '2023-01-15 22:41:07', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1614636755034054656, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$hyTJQwmgwtbxWSHzB4SNUukHVTO7nGdLctPzEfiMAl82gRnyu3sPO', 'userTest111', NULL, 1614639093817335808, '2023-01-15 22:52:55', '2023-01-15 23:02:14', 0, 0, 0);
INSERT INTO `au_user` VALUES (1614642398067433472, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$8e7lG9dli8wYCtqSDbne5e7uWo3/rSDoqbV1GY1vbY9pc3Mdg5EpK', 'usertest222', NULL, 1614642402586320896, '2023-01-15 23:15:21', '2023-01-15 23:16:33', 0, 0, 0);
INSERT INTO `au_user` VALUES (1614645374353940480, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$Cmvy1PsCskE7j1vgtTGAveMueKNH4nG/mpTIucob8r/jjQ0RHFMRa', 'userTest4444', NULL, 1614645378075910144, '2023-01-15 23:27:10', '2023-01-15 23:27:37', 0, 0, 0);
INSERT INTO `au_user` VALUES (1614645811509469184, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$.EClHrNtnIR1HDeRP.sWau3.ofOoxCj7c6x9VhfgPOQCqn7GI5zYu', 'userTest66656', NULL, NULL, '2023-01-15 23:28:54', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1614652944812613632, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$t2zrXVbiM2UQl6xlbXGOcubJDCpMFIlaQPUJqRI13jqCgoucc7HNy', 'userTest12', NULL, NULL, '2023-01-15 23:57:15', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1614654711742865408, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$dXiuIdW0PZmXQBLByk6/duumQlbm4abZhuwysCTPGvivNf2bRDBuO', 'userTest89', NULL, NULL, '2023-01-16 00:04:16', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1615023244989440000, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$JMbM4P32l3D29QLnfspD0e6QaNXA84hCA.LOU.i1gN1OI003FFRJG', 'testUser56', NULL, NULL, '2023-01-17 00:28:42', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1615389094829367296, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$pneKMzrt7jiG7DyVArhrOecEQQAD0A7tI/ZmwwZWghdTBfVUlFSmC', 'mobile', NULL, NULL, '2023-01-18 00:42:27', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1615972174438866944, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$KZQVMkLexBDVqBHDqpGRyuJ7vmo25pDO/sZRMWbGqed2ramtNS2Xe', 'qsyyke', NULL, NULL, '2023-01-19 15:19:24', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1617948463987171328, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$kbp8oixivF3uxqDcIT5cj.UUslKVSxQOANjoY1oNHZ60oWS5M9jZW', 'asldfjhasdf', NULL, NULL, '2023-01-25 02:12:28', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1617949976432222208, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$jduPXeqIRDL9p.Hy85WxU.6xaP0xgYY8Sd.UGD69YW/ijEDhx0OSq', 'asdfkjbawe', NULL, NULL, '2023-01-25 02:18:29', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1617950715128848384, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$BDkZonkhojG1CI5GgpsvNePRgEmEctqC8y29/hjP585ULneOSQXqy', 'asdfkjbwe', NULL, NULL, '2023-01-25 02:21:25', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1617951410473148416, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$LqQzPRlCP5M2BOtxjtfTYO5xPvcwsuQu.ehXToP4BuHWGkG0rcgoy', 'asdfljasn', NULL, NULL, '2023-01-25 02:24:11', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1617951989400346624, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$Qaj88HiTAIRSPUaJj1Wu7uMyUgKs649xG4ck/gHEGvbK6/Vb2aO/O', 'asdfkj', NULL, NULL, '2023-01-25 02:26:29', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1617952438283149312, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$pjdkSlMQvVSzmRGCdgKU4.gyzXHhVY7OuYb3FYSwH.a45lGUrzohG', 'asfasdf', NULL, NULL, '2023-01-25 02:28:16', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1617953145501523968, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$KhoLzqgKLDIv5p22wNvL/.KjiA8Zr0LWjOfJKhEAvSSFAGYDzFufi', 'asdfjasf', NULL, NULL, '2023-01-25 02:31:04', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1617953571122716672, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$1h9es.QEpBWcoT5AdUuRtOV0Kc03L.3F1qOF7LTW0oxKFie1/Js4K', 'asdfkjb', NULL, NULL, '2023-01-25 02:32:46', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1617953908189569024, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$4wUwgpBlir/ZNcZCeQlS2eVhbyIAvDhO3lL8gBgcw7nDcFnZlHR.y', 'asdfkbhasjf', NULL, NULL, '2023-01-25 02:34:06', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1617954127790743552, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$5CGwX2KACfU3dKSN4p9bq.TPjbWEa2a6ZR11wzO4qXXn5/MWps/qy', 'asdfty', NULL, NULL, '2023-01-25 02:34:59', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1617954871709278208, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$ueLWgC3hjMHpw2EZUB0Fy.OQmLmzwIyhCkrXOIvTxcX4V8IV.UZSC', 'asfhktrr', NULL, NULL, '2023-01-25 02:37:56', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618159033348792320, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$q4P9B6wogXuYKHzvMJjOX.VXWubQKTZHi9jhRfu4BdoxnqY7dJGsu', 'asdkfuhewr', NULL, 1618159038291304448, '2023-01-25 16:09:12', '2023-01-25 16:10:47', 0, 0, 0);
INSERT INTO `au_user` VALUES (1618160303128190976, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$651Ws42zk9AApPJzARIm3.wmVtP6LPSppr4smNSgt.qOh3bhswdHq', 'askdjhr', NULL, NULL, '2023-01-25 16:14:14', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618161485259218944, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$BMjhCWj4se2ci5b.O142v.FLMmAEuy/kCNHAqfQrbeIq6e5YgV5pG', '4w5oerijfd', NULL, NULL, '2023-01-25 16:18:56', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618161682810937344, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$vAII1h0L9aSBqGfzRVef8Ox.yYyXR8m9ojRunKzWM8wD8xHzPMb4C', '4w5oasd', NULL, NULL, '2023-01-25 16:19:43', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618161991318773760, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$Z.1hbEGMb5PvookrvwSYKO69mcj/eXczQmrcaczhx3f8itp787JFG', 'adiuh4esd', NULL, NULL, '2023-01-25 16:20:57', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618162652135563264, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$fWsQZGNSp0/H3AQP4LoqauvKIvODpHE/0BHuy8HM9Cn/4Z2MNYo/S', 'roijfsdf', NULL, NULL, '2023-01-25 16:23:34', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618163963090444288, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$usTeAZi8aXYCVJYpAYhtf.vU54nOHlDLQ9jSw/sr2Vb./9WZXSynS', 'werkjndf', NULL, NULL, '2023-01-25 16:28:47', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618166957576036352, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$gdje6s21zRKUmXipKpCKgu.BjY2FMsm8V0ks0AaWNRgsmOr/3jT7q', 'asdlfkjwer', NULL, NULL, '2023-01-25 16:40:41', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618167256160149504, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$gs0aXA.w0OfGcwUMtaIFCOvU6uOTRz9HfG2hTolujLxHs5cNtAw86', 'adlfkjwr', NULL, 1618167259919867904, '2023-01-25 16:41:52', '2023-01-25 16:41:54', 0, 0, 0);
INSERT INTO `au_user` VALUES (1618168442493870080, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$hJg5.86yVmtOnXFhii.2vOGRXZz8KJt1RW3u1OeO5yhH3.Puq.iza', 'alsdfj', NULL, 1618168447562211328, '2023-01-25 16:46:35', '2023-01-25 16:46:37', 0, 0, 0);
INSERT INTO `au_user` VALUES (1618171255831666688, '新用户在1522074993315815424用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'http://192.168.31.26/aurora-upload/jpg/2023/1/FC58EE85D9B11F7F459BF2D1B90B3D971674637569046.jpg', '$2a$10$zepMuhaDH4Y3sBPcKYtzze0AI6cRzxRy.tmCTZRiUIjREhrHvlRXS', 'akdsjfhads', NULL, 1618171259142594560, '2023-01-25 16:57:46', '2023-01-25 17:06:09', 0, 0, 1);
INSERT INTO `au_user` VALUES (1618674440585355264, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$kzRBB2sxx9ScyiGqo9Glw.HLkv2di7Dt8D39pvUzcTuVyFt2AdQeK', 'userTerfx', NULL, NULL, '2023-01-27 02:17:14', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618819423267201024, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$ibNEi8q9/FGLUHzeC4SXVumCdhAKqkAh8Utj6fzBLZceG.0Xl1lMq', 'kasdjft7', NULL, NULL, '2023-01-27 11:53:21', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618823263647440896, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$iiXXi/jRkysHOkhpFWC8wuoB7zZdLB2zb0Pv7xmqXiGkODMLKPiDi', 'auro65sdf34', NULL, NULL, '2023-01-27 12:08:37', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618823985617182720, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$N1IiM.mpOA0zWcrgmnYvRuIqhT6feO0rZ.J8jdiMfGrSsEnHGYzkO', 'auroraad734', NULL, NULL, '2023-01-27 12:11:29', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618824237480943616, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$K5QL8tpCFglNNfO0p0apI.rc4Y4KHFjWlhIxMUg6q6r.FUB3iNVCu', 'aur8437ora', NULL, NULL, '2023-01-27 12:12:29', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618824892270518272, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$wVI740qboGJ65Q4G9K320e3AX4PMrv1Klr1n3CX8Pwyk1ZWjQw5Qy', 'a345urora', NULL, NULL, '2023-01-27 12:15:05', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618826587436228608, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$bJPjUcUA0ePuUEFbgWJS2.LwcRD9wvyMTSMnhLznTaNruWyfbZoPm', 'newUser1', NULL, NULL, '2023-01-27 12:21:49', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618827111703257088, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$ZQxO.m2L0NwEdju.xIUuH.h53x7Cdr4iZWWNDP26yp384Wv0zqo5e', 'aur35ora', NULL, NULL, '2023-01-27 12:23:54', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618827668509696000, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$zHIctTefFUQNgnCalbZWwO5bnoo2JLI6nN7MLxQNrXnWxN2S6TXWa', 'auro87ra', NULL, NULL, '2023-01-27 12:26:07', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618829155868614656, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$hfka1vF3g10AZL7Ixh9LmOHXhna3gbFheBOaFiyi37orjjQnek4Bm', 'aurora5069w4', NULL, NULL, '2023-01-27 12:32:20', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618829915662589952, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$pQOEaQOkdxrZt8cua966kuAdf.47ZqFS8Bt.oKuaJBn6Qx/fDKsSG', 'aurora40589', NULL, NULL, '2023-01-27 12:35:07', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618852627151855616, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$YsqktxkngxtNyiyFC322VOUjBp1FiR9/EgjGmYr.PeRTJW/.SDJq6', 'aurora34785', NULL, NULL, '2023-01-27 14:05:17', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618853167646646272, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$oM7Dt2LiHQPjblcUbBefG.9AV1tm8uxZ6h7lqXX2QdD4n2lonPpUO', 'aurora9o54', NULL, NULL, '2023-01-27 14:07:26', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618855879712972800, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$TqdesKMYysEYkKHbVywt1.aiR/3PX2/zqBQ9e4ovg.odOsfHfWTYW', 'newUser45', NULL, NULL, '2023-01-27 14:18:13', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618858290309505024, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$mgaz19pQbeQj4cVj.QkYou7qeDcyqdrEn/x86vvZSiXoyLii3/xCK', 'aurora23iu', NULL, NULL, '2023-01-27 14:27:48', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618858615108018176, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$bBuoApvZFqCK2dkYO0KD1O8.AHOeYNkCkjpv0C0JU1UIvkg0AQg6u', 'aurora45234', NULL, NULL, '2023-01-27 14:29:05', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618859823881265152, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'http://192.168.31.26/aurora-upload/jpg/2023/1/illust_73549163_20220928_0811011674801500585.jpg', '$2a$10$4jKyWbikd1HqiOb/sQWJUenBolcC4kr/sfTzuaFmCKZZ4VB7xtBZq', 'auroraasfd', NULL, NULL, '2023-01-27 14:33:53', '2023-01-27 14:38:21', 0, 0, 0);
INSERT INTO `au_user` VALUES (1618868360900517888, NULL, 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$wghjQPnoy59oNvNRoHeeq.DBcIXJAAnzQaYytLCsfjK/ymltq45Hq', 'auroraNewUser', NULL, NULL, '2023-01-27 15:07:49', NULL, 0, 0, 0);
INSERT INTO `au_user` VALUES (1618923200477405184, '新用户在1618868360900517888用户处评论系统创建的用户', 'aurora-new', 'SECRET', NULL, NULL, 'https://picture.xcye.xyz/avatar.jpg', '$2a$10$naYlHlCZ.YVZKMpGNllbNOEJ4i1VX.KHY5oz57bu2GdV/FooCLC7K', 'newComment', NULL, 1618923211430354944, '2023-01-27 18:45:43', '2023-01-27 18:45:48', 0, 0, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_user_role
-- ----------------------------
INSERT INTO `au_user_role` VALUES (13, 3, '2023-01-14 21:49:47', NULL, 1522074993315815424);
INSERT INTO `au_user_role` VALUES (14, 4, '2023-01-14 21:49:47', NULL, 1522074993315815424);
INSERT INTO `au_user_role` VALUES (15, 5, '2023-01-14 21:49:47', NULL, 1522074993315815424);
INSERT INTO `au_user_role` VALUES (16, 4, '2023-01-15 22:13:50', NULL, 1614626868384243712);
INSERT INTO `au_user_role` VALUES (17, 4, '2023-01-15 22:14:54', NULL, 1614627188367695872);
INSERT INTO `au_user_role` VALUES (18, 4, '2023-01-15 22:19:01', NULL, 1614628221676429312);
INSERT INTO `au_user_role` VALUES (19, 4, '2023-01-15 22:23:14', NULL, 1614629284898938880);
INSERT INTO `au_user_role` VALUES (20, 4, '2023-01-15 22:26:03', NULL, 1614629991534305280);
INSERT INTO `au_user_role` VALUES (21, 4, '2023-01-15 22:35:24', NULL, 1614632347701354496);
INSERT INTO `au_user_role` VALUES (22, 4, '2023-01-15 22:36:11', NULL, 1614632543302721536);
INSERT INTO `au_user_role` VALUES (23, 4, '2023-01-15 22:38:59', NULL, 1614633248822403072);
INSERT INTO `au_user_role` VALUES (24, 4, '2023-01-15 22:41:07', NULL, 1614633782836994048);
INSERT INTO `au_user_role` VALUES (25, 4, '2023-01-15 22:52:55', NULL, 1614636755034054656);
INSERT INTO `au_user_role` VALUES (26, 4, '2023-01-15 23:15:21', NULL, 1614642398067433472);
INSERT INTO `au_user_role` VALUES (27, 4, '2023-01-15 23:27:10', NULL, 1614645374353940480);
INSERT INTO `au_user_role` VALUES (28, 4, '2023-01-15 23:28:54', NULL, 1614645811509469184);
INSERT INTO `au_user_role` VALUES (29, 4, '2023-01-15 23:57:15', NULL, 1614652944812613632);
INSERT INTO `au_user_role` VALUES (30, 4, '2023-01-16 00:04:16', NULL, 1614654711742865408);
INSERT INTO `au_user_role` VALUES (31, 4, '2023-01-17 00:28:42', NULL, 1615023244989440000);
INSERT INTO `au_user_role` VALUES (32, 4, '2023-01-18 00:42:27', NULL, 1615389094829367296);
INSERT INTO `au_user_role` VALUES (33, 4, '2023-01-19 15:19:24', NULL, 1615972174438866944);
INSERT INTO `au_user_role` VALUES (34, 4, '2023-01-25 02:12:28', NULL, 1617948463987171328);
INSERT INTO `au_user_role` VALUES (35, 4, '2023-01-25 02:18:29', NULL, 1617949976432222208);
INSERT INTO `au_user_role` VALUES (36, 4, '2023-01-25 02:21:25', NULL, 1617950715128848384);
INSERT INTO `au_user_role` VALUES (37, 4, '2023-01-25 02:24:11', NULL, 1617951410473148416);
INSERT INTO `au_user_role` VALUES (38, 4, '2023-01-25 02:26:29', NULL, 1617951989400346624);
INSERT INTO `au_user_role` VALUES (39, 4, '2023-01-25 02:28:16', NULL, 1617952438283149312);
INSERT INTO `au_user_role` VALUES (40, 4, '2023-01-25 02:31:04', NULL, 1617953145501523968);
INSERT INTO `au_user_role` VALUES (41, 4, '2023-01-25 02:32:46', NULL, 1617953571122716672);
INSERT INTO `au_user_role` VALUES (42, 4, '2023-01-25 02:34:06', NULL, 1617953908189569024);
INSERT INTO `au_user_role` VALUES (43, 4, '2023-01-25 02:34:59', NULL, 1617954127790743552);
INSERT INTO `au_user_role` VALUES (44, 4, '2023-01-25 02:37:56', NULL, 1617954871709278208);
INSERT INTO `au_user_role` VALUES (45, 4, '2023-01-25 16:09:12', NULL, 1618159033348792320);
INSERT INTO `au_user_role` VALUES (46, 4, '2023-01-25 16:14:14', NULL, 1618160303128190976);
INSERT INTO `au_user_role` VALUES (47, 4, '2023-01-25 16:18:56', NULL, 1618161485259218944);
INSERT INTO `au_user_role` VALUES (48, 4, '2023-01-25 16:19:43', NULL, 1618161682810937344);
INSERT INTO `au_user_role` VALUES (49, 4, '2023-01-25 16:20:57', NULL, 1618161991318773760);
INSERT INTO `au_user_role` VALUES (50, 4, '2023-01-25 16:23:34', NULL, 1618162652135563264);
INSERT INTO `au_user_role` VALUES (51, 4, '2023-01-25 16:28:47', NULL, 1618163963090444288);
INSERT INTO `au_user_role` VALUES (52, 4, '2023-01-25 16:40:41', NULL, 1618166957576036352);
INSERT INTO `au_user_role` VALUES (53, 4, '2023-01-25 16:41:52', NULL, 1618167256160149504);
INSERT INTO `au_user_role` VALUES (54, 4, '2023-01-25 16:46:35', NULL, 1618168442493870080);
INSERT INTO `au_user_role` VALUES (55, 4, '2023-01-25 16:57:46', NULL, 1618171255831666688);
INSERT INTO `au_user_role` VALUES (56, 4, '2023-01-27 02:17:14', NULL, 1618674440585355264);
INSERT INTO `au_user_role` VALUES (57, 4, '2023-01-27 11:53:21', NULL, 1618819423267201024);
INSERT INTO `au_user_role` VALUES (61, 4, '2023-01-27 12:08:37', NULL, 1618823263647440896);
INSERT INTO `au_user_role` VALUES (62, 4, '2023-01-27 12:11:29', NULL, 1618823985617182720);
INSERT INTO `au_user_role` VALUES (63, 4, '2023-01-27 12:12:29', NULL, 1618824237480943616);
INSERT INTO `au_user_role` VALUES (64, 4, '2023-01-27 12:15:05', NULL, 1618824892270518272);
INSERT INTO `au_user_role` VALUES (65, 4, '2023-01-27 12:21:49', NULL, 1618826587436228608);
INSERT INTO `au_user_role` VALUES (66, 4, '2023-01-27 12:23:54', NULL, 1618827111703257088);
INSERT INTO `au_user_role` VALUES (67, 4, '2023-01-27 12:26:07', NULL, 1618827668509696000);
INSERT INTO `au_user_role` VALUES (68, 4, '2023-01-27 12:32:20', NULL, 1618829155868614656);
INSERT INTO `au_user_role` VALUES (69, 4, '2023-01-27 12:35:07', NULL, 1618829915662589952);
INSERT INTO `au_user_role` VALUES (70, 4, '2023-01-27 14:05:17', NULL, 1618852627151855616);
INSERT INTO `au_user_role` VALUES (71, 4, '2023-01-27 14:07:26', NULL, 1618853167646646272);
INSERT INTO `au_user_role` VALUES (72, 4, '2023-01-27 14:18:13', NULL, 1618855879712972800);
INSERT INTO `au_user_role` VALUES (73, 4, '2023-01-27 14:27:48', NULL, 1618858290309505024);
INSERT INTO `au_user_role` VALUES (74, 4, '2023-01-27 14:29:05', NULL, 1618858615108018176);
INSERT INTO `au_user_role` VALUES (75, 4, '2023-01-27 14:33:53', NULL, 1618859823881265152);
INSERT INTO `au_user_role` VALUES (76, 4, '2023-01-27 15:07:49', NULL, 1618868360900517888);
INSERT INTO `au_user_role` VALUES (77, 4, '2023-01-27 18:45:44', NULL, 1618923200477405184);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1192 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
