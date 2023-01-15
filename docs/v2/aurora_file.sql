/*
 Navicat Premium Data Transfer

 Source Server         : 本地Linux
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : 192.168.158.120:3306
 Source Schema         : aurora_file

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 15/01/2023 19:32:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_file
-- ----------------------------
INSERT INTO `au_file` VALUES (1603239846169460736, 1522074993315815424, 'wallhaven-wqve97.png', 2861693, '上传的图片', 'http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-wqve97.png', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\png\\2022\\12\\wallhaven-wqve97.png', 0, '2022-12-15 12:05:40', NULL);
INSERT INTO `au_file` VALUES (1603239846169460737, 1522074993315815424, 'wallhaven-q22jv7.png', 5280952, '上传的图片', 'http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-q22jv7.png', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\png\\2022\\12\\wallhaven-q22jv7.png', 0, '2022-12-15 12:05:40', NULL);
INSERT INTO `au_file` VALUES (1603244459681562624, 1522074993315815424, '大数据学院师范专业教育研习方案.docx', 17280, '上传的图片', 'http://127.0.0.1/aurora-upload/docx/2022/12/大数据学院师范专业教育研习方案.docx', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\docx\\2022\\12\\大数据学院师范专业教育研习方案.docx', 0, '2022-12-15 12:24:00', NULL);
INSERT INTO `au_file` VALUES (1603244459681562625, 1522074993315815424, '附件1 实习生教学设计评议标准.docx', 14603, '上传的图片', 'http://127.0.0.1/aurora-upload/docx/2022/12/附件1 实习生教学设计评议标准.docx', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\docx\\2022\\12\\附件1 实习生教学设计评议标准.docx', 0, '2022-12-15 12:24:00', NULL);
INSERT INTO `au_file` VALUES (1603244459681562626, 1522074993315815424, '附件 2： 教学视频研究.docx', 15920, '上传的图片', 'http://127.0.0.1/aurora-upload/docx/2022/12/附件 2： 教学视频研究.docx', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\docx\\2022\\12\\附件 2： 教学视频研究.docx', 0, '2022-12-15 12:24:00', NULL);
INSERT INTO `au_file` VALUES (1603244459681562627, 1522074993315815424, '附件3教育研习报告.docx', 79216, '上传的图片', 'http://127.0.0.1/aurora-upload/docx/2022/12/附件3教育研习报告.docx', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\docx\\2022\\12\\附件3教育研习报告.docx', 0, '2022-12-15 12:24:00', NULL);
INSERT INTO `au_file` VALUES (1614263989648146432, 1522074993315815424, 'wallhaven-pkvw9p1673705501439.jpg', 8419087, 'aurora 上传的文件', 'http://127.0.0.1/aurora-upload/jpg/2023/1/wallhaven-pkvw9p1673705501439.jpg', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\jpg\\2023\\1\\wallhaven-pkvw9p1673705501439.jpg', 0, '2023-01-14 22:11:41', NULL);
INSERT INTO `au_file` VALUES (1614264910197211136, 1522074993315815424, 'wallhaven-q22jv7.png', 5280952, 'aurora 上传的文件', 'http://127.0.0.1/aurora-upload/png/2023/1/wallhaven-q22jv7.png', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\png\\2023\\1\\wallhaven-q22jv7.png', 0, '2023-01-14 22:15:20', NULL);
INSERT INTO `au_file` VALUES (1614265948266479616, 1522074993315815424, 'wallhaven-wqve971673705968334.png', 2861693, 'aurora 上传的文件', 'http://127.0.0.1/aurora-upload/png/2023/1/wallhaven-wqve971673705968334.png', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\png\\2023\\1\\wallhaven-wqve971673705968334.png', 0, '2023-01-14 22:19:28', NULL);
INSERT INTO `au_file` VALUES (1614267316062568448, 1522074993315815424, '图层01673706294442.png', 1284332, 'aurora 上传的文件', 'http://127.0.0.1/aurora-upload/png/2023/1/图层01673706294442.png', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\png\\2023\\1\\图层01673706294442.png', 0, '2023-01-14 22:24:54', NULL);
INSERT INTO `au_file` VALUES (1614269802026549248, 1522074993315815424, 'wallhaven-4yp1ok1673706887167.jpg', 627618, 'aurora 上传的文件', '/aurora-upload/jpg/2023/1/wallhaven-4yp1ok1673706887167.jpg', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\jpg\\2023\\1\\wallhaven-4yp1ok1673706887167.jpg', 0, '2023-01-14 22:34:47', NULL);
INSERT INTO `au_file` VALUES (1614269929499836416, 1522074993315815424, 'wallhaven-8x3v1k1673706917534.jpg', 222755, 'aurora 添加这是标题 文章上传的封面', '/aurora-upload/jpg/2023/1/wallhaven-8x3v1k1673706917534.jpg', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\jpg\\2023\\1\\wallhaven-8x3v1k1673706917534.jpg', 0, '2023-01-14 22:35:17', NULL);
INSERT INTO `au_file` VALUES (1614294207146409984, 1522074993315815424, 'wallhaven-3zy6r31673712705777.jpg', 4298435, 'aurora 上传的文件', '/aurora-upload/jpg/2023/1/wallhaven-3zy6r31673712705777.jpg', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\jpg\\2023\\1\\wallhaven-3zy6r31673712705777.jpg', 0, '2023-01-15 00:11:45', NULL);
INSERT INTO `au_file` VALUES (1614295360101859328, 1522074993315815424, 'wallhaven-q22jv71673712980662.png', 5280952, 'aurora 上传的文件', '/aurora-upload/png/2023/1/wallhaven-q22jv71673712980662.png', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\png\\2023\\1\\wallhaven-q22jv71673712980662.png', 0, '2023-01-15 00:16:20', NULL);
INSERT INTO `au_file` VALUES (1614295569284382720, 1522074993315815424, 'wallhaven-wqve971673713030535.png', 2861693, 'aurora 上传的文件', '/aurora-upload/png/2023/1/wallhaven-wqve971673713030535.png', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\png\\2023\\1\\wallhaven-wqve971673713030535.png', 0, '2023-01-15 00:17:10', NULL);
INSERT INTO `au_file` VALUES (1614295770963296256, 1522074993315815424, 'wallhaven-q22jv71673713078620.png', 5280952, 'aurora 上传的文件', '/aurora-upload/png/2023/1/wallhaven-q22jv71673713078620.png', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\png\\2023\\1\\wallhaven-q22jv71673713078620.png', 0, '2023-01-15 00:17:58', NULL);
INSERT INTO `au_file` VALUES (1614296064975618048, 1522074993315815424, 'wallhaven-pkvw9p1673713148718.jpg', 8419087, 'aurora 上传的文件', '/aurora-upload/jpg/2023/1/wallhaven-pkvw9p1673713148718.jpg', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\jpg\\2023\\1\\wallhaven-pkvw9p1673713148718.jpg', 0, '2023-01-15 00:19:08', NULL);
INSERT INTO `au_file` VALUES (1614296216486461440, 1522074993315815424, 'wallhaven-3zy6r31673713184840.jpg', 4298435, 'aurora 添加水电费 类别上传的封面', '/aurora-upload/jpg/2023/1/wallhaven-3zy6r31673713184840.jpg', 0, 'F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\jpg\\2023\\1\\wallhaven-3zy6r31673713184840.jpg', 0, '2023-01-15 00:19:44', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1001 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Procedure structure for pre_test1
-- ----------------------------
DROP PROCEDURE IF EXISTS `pre_test1`;
delimiter ;;
CREATE PROCEDURE `pre_test1`()
BEGIN
    DECLARE i INT DEFAULT 0;
    SET autocommit = 0;
    WHILE i < 100 DO
        insert into test(age) values(12);
    END WHILE;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
