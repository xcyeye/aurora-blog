/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟Docker
 Source Server Type    : MySQL
 Source Server Version : 80100 (8.1.0)
 Source Host           : 192.168.199.128:3306
 Source Schema         : nacos_config

 Target Server Type    : MySQL
 Target Server Version : 80100 (8.1.0)
 File Encoding         : 65001

 Date: 24/09/2023 15:35:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
                                `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
                                `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
                                `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
                                `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
                                `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
                                `c_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `c_use` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `effect` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `c_schema` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
                                `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 937 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (84, 'transport.type', 'SEATA_GROUP', 'TCP', 'b136ef5f6a01d816991fe3cf7a6ac763', '2022-04-13 06:40:03', '2022-04-13 06:50:15', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (85, 'transport.server', 'SEATA_GROUP', 'NIO', 'b6d9dfc0fb54277321cebc0fff55df2f', '2022-04-13 06:40:03', '2022-04-13 06:50:15', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (86, 'transport.heartbeat', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 06:40:03', '2022-04-13 06:50:15', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (87, 'transport.enableTmClientBatchSendRequest', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 06:40:04', '2022-04-13 06:50:16', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (88, 'transport.enableRmClientBatchSendRequest', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 06:40:04', '2022-04-13 06:50:16', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (89, 'transport.enableTcServerBatchSendResponse', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 06:40:04', '2022-04-13 06:50:16', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (90, 'transport.rpcRmRequestTimeout', 'SEATA_GROUP', '30000', '5ecc613150de01b7e6824594426f24f4', '2022-04-13 06:40:04', '2022-04-13 06:50:16', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (91, 'transport.rpcTmRequestTimeout', 'SEATA_GROUP', '30000', '5ecc613150de01b7e6824594426f24f4', '2022-04-13 06:40:05', '2022-04-13 06:50:16', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (92, 'transport.rpcTcRequestTimeout', 'SEATA_GROUP', '30000', '5ecc613150de01b7e6824594426f24f4', '2022-04-13 06:40:05', '2022-04-13 06:50:16', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (93, 'transport.threadFactory.bossThreadPrefix', 'SEATA_GROUP', 'NettyBoss', '0f8db59a3b7f2823f38a70c308361836', '2022-04-13 06:40:05', '2022-04-13 06:50:17', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (94, 'transport.threadFactory.workerThreadPrefix', 'SEATA_GROUP', 'NettyServerNIOWorker', 'a78ec7ef5d1631754c4e72ae8a3e9205', '2022-04-13 06:40:06', '2022-04-13 06:50:17', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (95, 'transport.threadFactory.serverExecutorThreadPrefix', 'SEATA_GROUP', 'NettyServerBizHandler', '11a36309f3d9df84fa8b59cf071fa2da', '2022-04-13 06:40:06', '2022-04-13 06:50:17', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (96, 'transport.threadFactory.shareBossWorker', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 06:40:06', '2022-04-13 06:50:17', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (97, 'transport.threadFactory.clientSelectorThreadPrefix', 'SEATA_GROUP', 'NettyClientSelector', 'cd7ec5a06541e75f5a7913752322c3af', '2022-04-13 06:40:07', '2022-04-13 06:50:18', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (98, 'transport.threadFactory.clientSelectorThreadSize', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2022-04-13 06:40:07', '2022-04-13 06:50:18', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (99, 'transport.threadFactory.clientWorkerThreadPrefix', 'SEATA_GROUP', 'NettyClientWorkerThread', '61cf4e69a56354cf72f46dc86414a57e', '2022-04-13 06:40:08', '2022-04-13 06:50:18', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (100, 'transport.threadFactory.bossThreadSize', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2022-04-13 06:40:08', '2022-04-13 06:50:18', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (101, 'transport.threadFactory.workerThreadSize', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2022-04-13 06:40:08', '2022-04-13 06:50:19', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (102, 'transport.shutdown.wait', 'SEATA_GROUP', '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '2022-04-13 06:40:09', '2022-04-13 06:50:19', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (103, 'transport.serialization', 'SEATA_GROUP', 'seata', 'b943081c423b9a5416a706524ee05d40', '2022-04-13 06:40:09', '2022-04-13 06:50:19', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (104, 'transport.compressor', 'SEATA_GROUP', 'none', '334c4a4c42fdb79d7ebc3e73b517e6f8', '2022-04-13 06:40:09', '2022-04-13 06:50:19', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (105, 'service.vgroupMapping.default_tx_group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2022-04-13 06:40:09', '2022-04-13 06:50:19', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (106, 'service.default.grouplist', 'SEATA_GROUP', '127.0.0.1:8091', 'c32ce0d3e264525dcdada751f98143a3', '2022-04-13 06:40:10', '2022-04-13 06:50:20', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (107, 'service.enableDegrade', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 06:40:10', '2022-04-13 06:50:20', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (108, 'service.disableGlobalTransaction', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 06:40:10', '2022-04-13 06:50:20', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (109, 'client.rm.asyncCommitBufferLimit', 'SEATA_GROUP', '10000', 'b7a782741f667201b54880c925faec4b', '2022-04-13 06:40:10', '2022-04-13 06:50:20', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (110, 'client.rm.lock.retryInterval', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2022-04-13 06:40:10', '2022-04-13 06:50:20', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (111, 'client.rm.lock.retryTimes', 'SEATA_GROUP', '30', '34173cb38f07f89ddbebc2ac9128303f', '2022-04-13 06:40:11', '2022-04-13 06:50:20', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (112, 'client.rm.lock.retryPolicyBranchRollbackOnConflict', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 06:40:11', '2022-04-13 06:50:21', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (113, 'client.rm.reportRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2022-04-13 06:40:11', '2022-04-13 06:50:21', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (114, 'client.rm.tableMetaCheckEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 06:40:11', '2022-04-13 06:50:21', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (115, 'client.rm.tableMetaCheckerInterval', 'SEATA_GROUP', '60000', '2b4226dd7ed6eb2d419b881f3ae9c97c', '2022-04-13 06:40:12', '2022-04-13 06:50:21', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (116, 'client.rm.sqlParserType', 'SEATA_GROUP', 'druid', '3d650fb8a5df01600281d48c47c9fa60', '2022-04-13 06:40:12', '2022-04-13 06:50:22', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (117, 'client.rm.reportSuccessEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 06:40:12', '2022-04-13 06:50:22', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (118, 'client.rm.sagaBranchRegisterEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 06:40:12', '2022-04-13 06:50:22', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (119, 'client.rm.sagaJsonParser', 'SEATA_GROUP', 'fastjson', 'd00d3dbc0834f08411c7b6c4c39e9f00', '2022-04-13 06:40:13', '2022-04-13 06:50:22', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (120, 'client.rm.tccActionInterceptorOrder', 'SEATA_GROUP', '-2147482648', 'f056d9efa5dae3872f9da035c83bcde8', '2022-04-13 06:40:13', '2022-04-13 06:50:22', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (121, 'client.tm.commitRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2022-04-13 06:40:13', '2022-04-13 06:50:23', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (122, 'client.tm.rollbackRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2022-04-13 06:40:13', '2022-04-13 06:50:23', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (123, 'client.tm.defaultGlobalTransactionTimeout', 'SEATA_GROUP', '60000', '2b4226dd7ed6eb2d419b881f3ae9c97c', '2022-04-13 06:40:14', '2022-04-13 06:50:23', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (124, 'client.tm.degradeCheck', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 06:40:14', '2022-04-13 06:50:23', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (125, 'client.tm.degradeCheckAllowTimes', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2022-04-13 06:40:14', '2022-04-13 06:50:23', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (126, 'client.tm.degradeCheckPeriod', 'SEATA_GROUP', '2000', '08f90c1a417155361a5c4b8d297e0d78', '2022-04-13 06:40:15', '2022-04-13 06:50:23', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (127, 'client.tm.interceptorOrder', 'SEATA_GROUP', '-2147482648', 'f056d9efa5dae3872f9da035c83bcde8', '2022-04-13 06:40:15', '2022-04-13 06:50:24', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (128, 'client.undo.dataValidation', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 06:40:15', '2022-04-13 06:50:24', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (129, 'client.undo.logSerialization', 'SEATA_GROUP', 'jackson', 'b41779690b83f182acc67d6388c7bac9', '2022-04-13 06:40:15', '2022-04-13 06:50:24', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (130, 'client.undo.onlyCareUpdateColumns', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 06:40:16', '2022-04-13 06:50:24', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (131, 'server.undo.logSaveDays', 'SEATA_GROUP', '7', '8f14e45fceea167a5a36dedd4bea2543', '2022-04-13 06:40:16', '2022-04-13 06:50:24', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (132, 'server.undo.logDeletePeriod', 'SEATA_GROUP', '86400000', 'f4c122804fe9076cb2710f55c3c6e346', '2022-04-13 06:40:16', '2022-04-13 06:50:25', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (133, 'client.undo.logTable', 'SEATA_GROUP', 'undo_log', '2842d229c24afe9e61437135e8306614', '2022-04-13 06:40:16', '2022-04-13 06:50:25', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (134, 'client.undo.compress.enable', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 06:40:16', '2022-04-13 06:50:25', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (135, 'client.undo.compress.type', 'SEATA_GROUP', 'zip', 'adcdbd79a8d84175c229b192aadc02f2', '2022-04-13 06:40:17', '2022-04-13 06:50:25', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (136, 'client.undo.compress.threshold', 'SEATA_GROUP', '64k', 'bd44a6458bdbff0b5cac721ba361f035', '2022-04-13 06:40:17', '2022-04-13 06:50:25', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (137, 'tcc.fence.logTableName', 'SEATA_GROUP', 'tcc_fence_log', 'db229b665c7cfd5abc03971d4ed284c6', '2022-04-13 06:40:17', '2022-04-13 06:50:26', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (138, 'tcc.fence.cleanPeriod', 'SEATA_GROUP', '1h', '7c68645d71b803bf0ba2f22519f73e08', '2022-04-13 06:40:17', '2022-04-13 06:50:26', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (139, 'log.exceptionRate', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2022-04-13 06:40:17', '2022-04-13 06:50:26', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (140, 'store.mode', 'SEATA_GROUP', 'db', 'd77d5e503ad1439f585ac494268b351b', '2022-04-13 06:40:17', '2022-04-13 06:50:26', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (141, 'store.lock.mode', 'SEATA_GROUP', 'file', '8c7dd922ad47494fc02c388e12c00eac', '2022-04-13 06:40:18', '2022-04-13 06:50:26', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (142, 'store.session.mode', 'SEATA_GROUP', 'file', '8c7dd922ad47494fc02c388e12c00eac', '2022-04-13 06:40:18', '2022-04-13 06:50:26', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (143, 'store.file.dir', 'SEATA_GROUP', 'file_store/data', '6a8dec07c44c33a8a9247cba5710bbb2', '2022-04-13 06:40:18', '2022-04-13 06:50:27', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (144, 'store.file.maxBranchSessionSize', 'SEATA_GROUP', '16384', 'c76fe1d8e08462434d800487585be217', '2022-04-13 06:40:18', '2022-04-13 06:50:27', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (145, 'store.file.maxGlobalSessionSize', 'SEATA_GROUP', '512', '10a7cdd970fe135cf4f7bb55c0e3b59f', '2022-04-13 06:40:18', '2022-04-13 06:50:27', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (146, 'store.file.fileWriteBufferCacheSize', 'SEATA_GROUP', '16384', 'c76fe1d8e08462434d800487585be217', '2022-04-13 06:40:19', '2022-04-13 06:50:28', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (147, 'store.file.flushDiskMode', 'SEATA_GROUP', 'async', '0df93e34273b367bb63bad28c94c78d5', '2022-04-13 06:40:19', '2022-04-13 06:50:28', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (148, 'store.file.sessionReloadReadSize', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2022-04-13 06:40:19', '2022-04-13 06:50:28', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (149, 'store.db.datasource', 'SEATA_GROUP', 'druid', '3d650fb8a5df01600281d48c47c9fa60', '2022-04-13 06:40:19', '2022-04-13 06:50:28', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (150, 'store.db.dbType', 'SEATA_GROUP', 'mysql', '81c3b080dad537de7e10e0987a4bf52e', '2022-04-13 06:40:20', '2022-04-13 06:50:28', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (151, 'store.db.driverClassName', 'SEATA_GROUP', 'com.mysql.cj.jdbc.Driver', '33763409bb7f4838bde4fae9540433e4', '2022-04-13 06:40:20', '2022-04-13 06:50:29', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (152, 'store.db.url', 'SEATA_GROUP', 'jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&rewriteBatchedStatements=true', '030ea5ff5c2ef043adf9826c70570b0b', '2022-04-13 06:40:20', '2022-04-13 06:50:29', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (153, 'store.db.user', 'SEATA_GROUP', 'root', '63a9f0ea7bb98050796b649e85481845', '2022-04-13 06:40:21', '2022-04-13 06:50:29', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (154, 'store.db.password', 'SEATA_GROUP', 'yourpassword', '8fce5c1254e0615fab810a3d55c3a9f1', '2022-04-13 06:40:21', '2022-04-13 06:50:30', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (155, 'store.db.minConn', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2022-04-13 06:40:21', '2022-04-13 06:50:30', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (156, 'store.db.maxConn', 'SEATA_GROUP', '30', '34173cb38f07f89ddbebc2ac9128303f', '2022-04-13 06:40:21', '2022-04-13 06:50:30', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (157, 'store.db.globalTable', 'SEATA_GROUP', 'global_table', '8b28fb6bb4c4f984df2709381f8eba2b', '2022-04-13 06:40:21', '2022-04-13 06:50:30', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (158, 'store.db.branchTable', 'SEATA_GROUP', 'branch_table', '54bcdac38cf62e103fe115bcf46a660c', '2022-04-13 06:40:22', '2022-04-13 06:50:30', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (159, 'store.db.distributedLockTable', 'SEATA_GROUP', 'distributed_lock', '26146b7a3a4907101617cb0f19bf613f', '2022-04-13 06:40:22', '2022-04-13 06:50:31', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (160, 'store.db.queryLimit', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2022-04-13 06:40:22', '2022-04-13 06:50:31', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (161, 'store.db.lockTable', 'SEATA_GROUP', 'lock_table', '55e0cae3b6dc6696b768db90098b8f2f', '2022-04-13 06:40:22', '2022-04-13 06:50:31', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (162, 'store.db.maxWait', 'SEATA_GROUP', '5000', 'a35fe7f7fe8217b4369a0af4244d1fca', '2022-04-13 06:40:22', '2022-04-13 06:50:31', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (163, 'store.redis.mode', 'SEATA_GROUP', 'single', 'dd5c07036f2975ff4bce568b6511d3bc', '2022-04-13 06:40:23', '2022-04-13 06:50:31', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (164, 'store.redis.single.host', 'SEATA_GROUP', '127.0.0.1', 'f528764d624db129b32c21fbca0cb8d6', '2022-04-13 06:40:23', '2022-04-13 06:50:32', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (165, 'store.redis.single.port', 'SEATA_GROUP', '6379', '92c3b916311a5517d9290576e3ea37ad', '2022-04-13 06:40:23', '2022-04-13 06:50:32', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (166, 'store.redis.maxConn', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2022-04-13 06:40:24', '2022-04-13 06:50:32', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (167, 'store.redis.minConn', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2022-04-13 06:40:24', '2022-04-13 06:50:33', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (168, 'store.redis.maxTotal', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2022-04-13 06:40:24', '2022-04-13 06:50:33', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (169, 'store.redis.database', 'SEATA_GROUP', '0', 'cfcd208495d565ef66e7dff9f98764da', '2022-04-13 06:40:24', '2022-04-13 06:50:33', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (170, 'store.redis.queryLimit', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2022-04-13 06:40:24', '2022-04-13 06:50:33', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (171, 'server.recovery.committingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2022-04-13 06:40:25', '2022-04-13 06:50:34', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (172, 'server.recovery.asynCommittingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2022-04-13 06:40:25', '2022-04-13 06:50:34', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (173, 'server.recovery.rollbackingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2022-04-13 06:40:25', '2022-04-13 06:50:34', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (174, 'server.recovery.timeoutRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2022-04-13 06:40:26', '2022-04-13 06:50:34', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (175, 'server.maxCommitRetryTimeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2022-04-13 06:40:26', '2022-04-13 06:50:35', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (176, 'server.maxRollbackRetryTimeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2022-04-13 06:40:26', '2022-04-13 06:50:35', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (177, 'server.rollbackRetryTimeoutUnlockEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 06:40:26', '2022-04-13 06:50:35', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (178, 'server.distributedLockExpireTime', 'SEATA_GROUP', '10000', 'b7a782741f667201b54880c925faec4b', '2022-04-13 06:40:27', '2022-04-13 06:50:35', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (179, 'server.xaerNotaRetryTimeout', 'SEATA_GROUP', '60000', '2b4226dd7ed6eb2d419b881f3ae9c97c', '2022-04-13 06:40:27', '2022-04-13 06:50:36', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (180, 'server.session.branchAsyncQueueSize', 'SEATA_GROUP', '5000', 'a35fe7f7fe8217b4369a0af4244d1fca', '2022-04-13 06:40:27', '2022-04-13 06:50:36', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (181, 'server.session.enableBranchAsyncRemove', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 06:40:27', '2022-04-13 06:50:36', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (182, 'metrics.enabled', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 06:40:28', '2022-04-13 06:50:36', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (183, 'metrics.registryType', 'SEATA_GROUP', 'compact', '7cf74ca49c304df8150205fc915cd465', '2022-04-13 06:40:28', '2022-04-13 06:50:37', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (184, 'metrics.exporterList', 'SEATA_GROUP', 'prometheus', 'e4f00638b8a10e6994e67af2f832d51c', '2022-04-13 06:40:28', '2022-04-13 06:50:37', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (185, 'metrics.exporterPrometheusPort', 'SEATA_GROUP', '9898', '7b9dc501afe4ee11c56a4831e20cee71', '2022-04-13 06:40:28', '2022-04-13 06:50:37', 'nacos', '0:0:0:0:0:0:0:1', '', '5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (716, 'transport.type', 'SEATA_GROUP', 'TCP', 'b136ef5f6a01d816991fe3cf7a6ac763', '2022-04-13 13:29:12', '2022-04-13 13:29:12', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (717, 'transport.server', 'SEATA_GROUP', 'NIO', 'b6d9dfc0fb54277321cebc0fff55df2f', '2022-04-13 13:29:12', '2022-04-13 13:29:12', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (718, 'transport.heartbeat', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 13:29:13', '2022-04-13 13:29:13', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (719, 'transport.enableTmClientBatchSendRequest', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 13:29:13', '2022-04-13 13:29:13', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (720, 'transport.enableRmClientBatchSendRequest', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 13:29:13', '2022-04-13 13:29:13', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (721, 'transport.enableTcServerBatchSendResponse', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 13:29:13', '2022-04-13 13:29:13', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (722, 'transport.rpcRmRequestTimeout', 'SEATA_GROUP', '30000', '5ecc613150de01b7e6824594426f24f4', '2022-04-13 13:29:13', '2022-04-13 13:29:13', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (723, 'transport.rpcTmRequestTimeout', 'SEATA_GROUP', '30000', '5ecc613150de01b7e6824594426f24f4', '2022-04-13 13:29:13', '2022-04-13 13:29:13', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (724, 'transport.rpcTcRequestTimeout', 'SEATA_GROUP', '30000', '5ecc613150de01b7e6824594426f24f4', '2022-04-13 13:29:14', '2022-04-13 13:29:14', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (725, 'transport.threadFactory.bossThreadPrefix', 'SEATA_GROUP', 'NettyBoss', '0f8db59a3b7f2823f38a70c308361836', '2022-04-13 13:29:14', '2022-04-13 13:29:14', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (726, 'transport.threadFactory.workerThreadPrefix', 'SEATA_GROUP', 'NettyServerNIOWorker', 'a78ec7ef5d1631754c4e72ae8a3e9205', '2022-04-13 13:29:14', '2022-04-13 13:29:14', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (727, 'transport.threadFactory.serverExecutorThreadPrefix', 'SEATA_GROUP', 'NettyServerBizHandler', '11a36309f3d9df84fa8b59cf071fa2da', '2022-04-13 13:29:14', '2022-04-13 13:29:14', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (728, 'transport.threadFactory.shareBossWorker', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 13:29:14', '2022-04-13 13:29:14', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (729, 'transport.threadFactory.clientSelectorThreadPrefix', 'SEATA_GROUP', 'NettyClientSelector', 'cd7ec5a06541e75f5a7913752322c3af', '2022-04-13 13:29:15', '2022-04-13 13:29:15', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (730, 'transport.threadFactory.clientSelectorThreadSize', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2022-04-13 13:29:15', '2022-04-13 13:29:15', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (731, 'transport.threadFactory.clientWorkerThreadPrefix', 'SEATA_GROUP', 'NettyClientWorkerThread', '61cf4e69a56354cf72f46dc86414a57e', '2022-04-13 13:29:15', '2022-04-13 13:29:15', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (732, 'transport.threadFactory.bossThreadSize', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2022-04-13 13:29:15', '2022-04-13 13:29:15', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (733, 'transport.threadFactory.workerThreadSize', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2022-04-13 13:29:16', '2022-04-13 13:29:16', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (734, 'transport.shutdown.wait', 'SEATA_GROUP', '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '2022-04-13 13:29:16', '2022-04-13 13:29:16', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (735, 'transport.serialization', 'SEATA_GROUP', 'seata', 'b943081c423b9a5416a706524ee05d40', '2022-04-13 13:29:16', '2022-04-13 13:29:16', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (736, 'transport.compressor', 'SEATA_GROUP', 'none', '334c4a4c42fdb79d7ebc3e73b517e6f8', '2022-04-13 13:29:16', '2022-04-13 13:29:16', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (738, 'service.default.grouplist', 'SEATA_GROUP', '127.0.0.1:8091', 'c32ce0d3e264525dcdada751f98143a3', '2022-04-13 13:29:17', '2022-04-13 13:29:17', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (739, 'service.enableDegrade', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 13:29:17', '2022-04-13 13:29:17', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (740, 'service.disableGlobalTransaction', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 13:29:17', '2022-04-13 13:29:17', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (741, 'client.rm.asyncCommitBufferLimit', 'SEATA_GROUP', '10000', 'b7a782741f667201b54880c925faec4b', '2022-04-13 13:29:17', '2022-04-13 13:29:17', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (742, 'client.rm.lock.retryInterval', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2022-04-13 13:29:17', '2022-04-13 13:29:17', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (743, 'client.rm.lock.retryTimes', 'SEATA_GROUP', '30', '34173cb38f07f89ddbebc2ac9128303f', '2022-04-13 13:29:17', '2022-04-13 13:29:17', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (744, 'client.rm.lock.retryPolicyBranchRollbackOnConflict', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 13:29:18', '2022-04-13 13:29:18', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (745, 'client.rm.reportRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2022-04-13 13:29:18', '2022-04-13 13:29:18', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (746, 'client.rm.tableMetaCheckEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 13:29:18', '2022-04-13 13:29:18', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (747, 'client.rm.tableMetaCheckerInterval', 'SEATA_GROUP', '60000', '2b4226dd7ed6eb2d419b881f3ae9c97c', '2022-04-13 13:29:18', '2022-04-13 13:29:18', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (748, 'client.rm.sqlParserType', 'SEATA_GROUP', 'druid', '3d650fb8a5df01600281d48c47c9fa60', '2022-04-13 13:29:18', '2022-04-13 13:29:18', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (749, 'client.rm.reportSuccessEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 13:29:18', '2022-04-13 13:29:18', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (750, 'client.rm.sagaBranchRegisterEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 13:29:18', '2022-04-13 13:29:18', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (751, 'client.rm.sagaJsonParser', 'SEATA_GROUP', 'fastjson', 'd00d3dbc0834f08411c7b6c4c39e9f00', '2022-04-13 13:29:19', '2022-04-13 13:29:19', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (752, 'client.rm.tccActionInterceptorOrder', 'SEATA_GROUP', '-2147482648', 'f056d9efa5dae3872f9da035c83bcde8', '2022-04-13 13:29:19', '2022-04-13 13:29:19', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (753, 'client.tm.commitRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2022-04-13 13:29:19', '2022-04-13 13:29:19', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (754, 'client.tm.rollbackRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2022-04-13 13:29:19', '2022-04-13 13:29:19', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (755, 'client.tm.defaultGlobalTransactionTimeout', 'SEATA_GROUP', '60000', '2b4226dd7ed6eb2d419b881f3ae9c97c', '2022-04-13 13:29:19', '2022-04-13 13:29:19', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (756, 'client.tm.degradeCheck', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 13:29:20', '2022-04-13 13:29:20', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (757, 'client.tm.degradeCheckAllowTimes', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2022-04-13 13:29:20', '2022-04-13 13:29:20', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (758, 'client.tm.degradeCheckPeriod', 'SEATA_GROUP', '2000', '08f90c1a417155361a5c4b8d297e0d78', '2022-04-13 13:29:20', '2022-04-13 13:29:20', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (759, 'client.tm.interceptorOrder', 'SEATA_GROUP', '-2147482648', 'f056d9efa5dae3872f9da035c83bcde8', '2022-04-13 13:29:20', '2022-04-13 13:29:20', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (760, 'client.undo.dataValidation', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 13:29:20', '2022-04-13 13:29:20', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (761, 'client.undo.logSerialization', 'SEATA_GROUP', 'kryo', 'd78f017576c8b3ad5beec73e6c39a59e', '2022-04-13 13:29:20', '2022-04-23 03:22:32', 'nacos', '0:0:0:0:0:0:0:1', '', '', '', '', '', 'text', '', '');
INSERT INTO `config_info` VALUES (762, 'client.undo.onlyCareUpdateColumns', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 13:29:20', '2022-04-13 13:29:20', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (763, 'server.undo.logSaveDays', 'SEATA_GROUP', '7', '8f14e45fceea167a5a36dedd4bea2543', '2022-04-13 13:29:21', '2022-04-13 13:29:21', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (764, 'server.undo.logDeletePeriod', 'SEATA_GROUP', '86400000', 'f4c122804fe9076cb2710f55c3c6e346', '2022-04-13 13:29:21', '2022-04-13 13:29:21', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (765, 'client.undo.logTable', 'SEATA_GROUP', 'undo_log', '2842d229c24afe9e61437135e8306614', '2022-04-13 13:29:21', '2022-04-13 13:29:21', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (766, 'client.undo.compress.enable', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 13:29:21', '2022-04-13 13:29:21', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (767, 'client.undo.compress.type', 'SEATA_GROUP', 'zip', 'adcdbd79a8d84175c229b192aadc02f2', '2022-04-13 13:29:21', '2022-04-13 13:29:21', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (768, 'client.undo.compress.threshold', 'SEATA_GROUP', '64k', 'bd44a6458bdbff0b5cac721ba361f035', '2022-04-13 13:29:21', '2022-04-13 13:29:21', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (769, 'tcc.fence.logTableName', 'SEATA_GROUP', 'tcc_fence_log', 'db229b665c7cfd5abc03971d4ed284c6', '2022-04-13 13:29:22', '2022-04-13 13:29:22', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (770, 'tcc.fence.cleanPeriod', 'SEATA_GROUP', '1h', '7c68645d71b803bf0ba2f22519f73e08', '2022-04-13 13:29:22', '2022-04-13 13:29:22', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (771, 'log.exceptionRate', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2022-04-13 13:29:22', '2022-04-13 13:29:22', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (772, 'store.mode', 'SEATA_GROUP', 'file', '8c7dd922ad47494fc02c388e12c00eac', '2022-04-13 13:29:22', '2022-04-13 13:29:22', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (773, 'store.lock.mode', 'SEATA_GROUP', 'file', '8c7dd922ad47494fc02c388e12c00eac', '2022-04-13 13:29:22', '2022-04-13 13:29:22', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (774, 'store.session.mode', 'SEATA_GROUP', 'file', '8c7dd922ad47494fc02c388e12c00eac', '2022-04-13 13:29:22', '2022-04-13 13:29:22', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (775, 'store.db.datasource', 'SEATA_GROUP', 'druid', '3d650fb8a5df01600281d48c47c9fa60', '2022-04-13 13:29:22', '2022-04-13 13:29:22', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (776, 'store.db.dbType', 'SEATA_GROUP', 'mysql', '81c3b080dad537de7e10e0987a4bf52e', '2022-04-13 13:29:22', '2022-04-13 13:29:22', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (777, 'store.db.driverClassName', 'SEATA_GROUP', 'com.mysql.cj.jdbc.Driver', '33763409bb7f4838bde4fae9540433e4', '2022-04-13 13:29:23', '2022-04-13 13:29:23', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (778, 'store.db.url', 'SEATA_GROUP', 'jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&rewriteBatchedStatements=true', '030ea5ff5c2ef043adf9826c70570b0b', '2022-04-13 13:29:23', '2022-04-13 13:29:23', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (779, 'store.db.user', 'SEATA_GROUP', 'root', '63a9f0ea7bb98050796b649e85481845', '2022-04-13 13:29:23', '2022-04-13 13:29:23', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (780, 'store.db.password', 'SEATA_GROUP', 'yourpassword', '8fce5c1254e0615fab810a3d55c3a9f1', '2022-04-13 13:29:24', '2022-04-13 13:29:24', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (781, 'store.db.minConn', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2022-04-13 13:29:24', '2022-04-13 13:29:24', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (782, 'store.db.maxConn', 'SEATA_GROUP', '30', '34173cb38f07f89ddbebc2ac9128303f', '2022-04-13 13:29:24', '2022-04-13 13:29:24', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (783, 'store.db.globalTable', 'SEATA_GROUP', 'global_table', '8b28fb6bb4c4f984df2709381f8eba2b', '2022-04-13 13:29:24', '2022-04-13 13:29:24', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (784, 'store.db.branchTable', 'SEATA_GROUP', 'branch_table', '54bcdac38cf62e103fe115bcf46a660c', '2022-04-13 13:29:24', '2022-04-13 13:29:24', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (785, 'store.db.distributedLockTable', 'SEATA_GROUP', 'distributed_lock', '26146b7a3a4907101617cb0f19bf613f', '2022-04-13 13:29:24', '2022-04-13 13:29:24', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (786, 'store.db.queryLimit', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2022-04-13 13:29:25', '2022-04-13 13:29:25', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (787, 'store.db.lockTable', 'SEATA_GROUP', 'lock_table', '55e0cae3b6dc6696b768db90098b8f2f', '2022-04-13 13:29:25', '2022-04-13 13:29:25', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (788, 'store.db.maxWait', 'SEATA_GROUP', '5000', 'a35fe7f7fe8217b4369a0af4244d1fca', '2022-04-13 13:29:25', '2022-04-13 13:29:25', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (789, 'store.redis.mode', 'SEATA_GROUP', 'single', 'dd5c07036f2975ff4bce568b6511d3bc', '2022-04-13 13:29:25', '2022-04-13 13:29:25', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (790, 'store.redis.single.host', 'SEATA_GROUP', '127.0.0.1', 'f528764d624db129b32c21fbca0cb8d6', '2022-04-13 13:29:25', '2022-04-13 13:29:25', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (791, 'store.redis.single.port', 'SEATA_GROUP', '6379', '92c3b916311a5517d9290576e3ea37ad', '2022-04-13 13:29:25', '2022-04-13 13:29:25', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (792, 'store.redis.sentinel.masterName', 'SEATA_GROUP', 'null', '37a6259cc0c1dae299a7866489dff0bd', '2022-04-13 13:29:26', '2022-04-13 13:29:26', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (793, 'store.redis.sentinel.sentinelHosts', 'SEATA_GROUP', 'null', '37a6259cc0c1dae299a7866489dff0bd', '2022-04-13 13:29:26', '2022-04-13 13:29:26', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (794, 'store.redis.maxConn', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2022-04-13 13:29:26', '2022-04-13 13:29:26', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (795, 'store.redis.minConn', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2022-04-13 13:29:26', '2022-04-13 13:29:26', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (796, 'store.redis.maxTotal', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2022-04-13 13:29:26', '2022-04-13 13:29:26', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (797, 'store.redis.database', 'SEATA_GROUP', '0', 'cfcd208495d565ef66e7dff9f98764da', '2022-04-13 13:29:27', '2022-04-13 13:29:27', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (798, 'store.redis.password', 'SEATA_GROUP', 'null', '37a6259cc0c1dae299a7866489dff0bd', '2022-04-13 13:29:27', '2022-04-13 13:29:27', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (799, 'store.redis.queryLimit', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2022-04-13 13:29:27', '2022-04-13 13:29:27', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (800, 'server.recovery.committingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2022-04-13 13:29:27', '2022-04-13 13:29:27', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (801, 'server.recovery.asynCommittingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2022-04-13 13:29:27', '2022-04-13 13:29:27', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (802, 'server.recovery.rollbackingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2022-04-13 13:29:28', '2022-04-13 13:29:28', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (803, 'server.recovery.timeoutRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2022-04-13 13:29:28', '2022-04-13 13:29:28', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (804, 'server.maxCommitRetryTimeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2022-04-13 13:29:28', '2022-04-13 13:29:28', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (805, 'server.maxRollbackRetryTimeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2022-04-13 13:29:28', '2022-04-13 13:29:28', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (806, 'server.rollbackRetryTimeoutUnlockEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 13:29:29', '2022-04-13 13:29:29', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (807, 'server.distributedLockExpireTime', 'SEATA_GROUP', '10000', 'b7a782741f667201b54880c925faec4b', '2022-04-13 13:29:29', '2022-04-13 13:29:29', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (808, 'server.xaerNotaRetryTimeout', 'SEATA_GROUP', '60000', '2b4226dd7ed6eb2d419b881f3ae9c97c', '2022-04-13 13:29:29', '2022-04-13 13:29:29', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (809, 'server.session.branchAsyncQueueSize', 'SEATA_GROUP', '5000', 'a35fe7f7fe8217b4369a0af4244d1fca', '2022-04-13 13:29:29', '2022-04-13 13:29:29', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (810, 'server.session.enableBranchAsyncRemove', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2022-04-13 13:29:29', '2022-04-13 13:29:29', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (811, 'metrics.enabled', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2022-04-13 13:29:30', '2022-04-13 13:29:30', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (812, 'metrics.registryType', 'SEATA_GROUP', 'compact', '7cf74ca49c304df8150205fc915cd465', '2022-04-13 13:29:30', '2022-04-13 13:29:30', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (813, 'metrics.exporterList', 'SEATA_GROUP', 'prometheus', 'e4f00638b8a10e6994e67af2f832d51c', '2022-04-13 13:29:30', '2022-04-13 13:29:30', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (814, 'metrics.exporterPrometheusPort', 'SEATA_GROUP', '9898', '7b9dc501afe4ee11c56a4831e20cee71', '2022-04-13 13:29:30', '2022-04-13 13:29:30', '', '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (816, 'service.vgroupMapping.aurora-blog-tx-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2022-05-03 08:25:43', '2022-05-03 08:25:43', NULL, '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (820, 'nacos-config-dev.properties', 'DEFAULT_GROUP', 'user.name=nacos-config-properties', '7ff1687d091968f281131f6a9244d6b4', '2022-05-19 10:39:08', '2022-05-19 10:39:08', NULL, '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'properties', NULL, '');
INSERT INTO `config_info` VALUES (847, 'share-redis-dev.yaml', 'THEME-GROUP', 'spring:\n  redis:\n    database: 1\n    host: 192.168.158.120\n    password: \'\'\n    port: 6379\n    jedis:\n      pool:\n        max-active: 15\n        max-idle: 10\n        min-idle: 0\n        max-wait: -1ms', '0fb72755d43b487cac8239f72eb05f60', '2022-05-19 23:49:25', '2022-11-04 02:29:20', 'nacos', '192.168.158.1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (848, 'share-sentinel-dev.yaml', 'THEME-GROUP', 'spring:\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.158.120:8080', '9e6ebe4557e443dadeaecb31147282fa', '2022-05-19 23:50:08', '2022-11-04 02:29:33', 'nacos', '192.168.158.1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (849, 'share-rabbitmq-dev.yaml', 'THEME-GROUP', 'spring:\n  rabbitmq:\n    host: 192.168.158.120\n    port: 5672\n    username: admin\n    password: 123456\n    publisher-returns: true\n    publisher-confirm-type: correlated\n    listener:\n      direct:\n        acknowledge-mode: manual\n      simple:\n        acknowledge-mode: manual', 'd7bfe1aad8a095e0888046bde433d70a', '2022-05-19 23:50:50', '2022-11-04 02:29:44', 'nacos', '192.168.158.1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (850, 'share-aurora-oauth-dev.yaml', 'THEME-GROUP', '# token验证和生成用到的秘钥\naurora:\n  oauth:\n    secret-key: auroraaadkfhasdfuywasdfkjahdsfuewkrnbsdjtywq8745ghsdfsgdfhsdfgerbjhsdtfquewrw9erygtwertfysatf', 'e41685372342053eec5d5d26e53f732f', '2022-05-19 23:52:03', '2022-05-19 23:52:03', NULL, '0:0:0:0:0:0:0:1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (851, 'share-actuator-dev.yaml', 'THEME-GROUP', '\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"', '76f4e71d8ec547c2d940ab52c7a14aaf', '2022-05-19 23:53:01', '2022-05-19 23:53:01', NULL, '0:0:0:0:0:0:0:1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (852, 'share-pagehelper-dev.yaml', 'THEME-GROUP', '# 分页插件\npagehelper:\n  helperDialect: mysql\n  reasonable: true\n  supportMethodsArguments: true\n  params: count=countSql', '51cdaf050077cc14350cc9cd2d8d207a', '2022-05-19 23:53:42', '2022-05-19 23:53:42', NULL, '0:0:0:0:0:0:0:1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (853, 'share-mybatis-dev.yaml', 'THEME-GROUP', 'mybatis:\n  configuration:\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl', 'ac0e247b309081c92f966a8de49234e0', '2022-05-19 23:54:16', '2022-05-19 23:54:16', NULL, '0:0:0:0:0:0:0:1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (854, 'share-feign-dev.yaml', 'THEME-GROUP', 'feign:\n  sentinel:\n    enabled: true\n  client:\n    config:\n      default:\n        connectTimeout: 5000\n        readTimeout: 5000', '03e10c99cce614ba75c6d3306ab8e5f2', '2022-05-19 23:56:55', '2022-05-19 23:56:55', NULL, '0:0:0:0:0:0:0:1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (855, 'aurora-auth-server-dev.yaml', 'THEME-GROUP', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/aurora_auth_server?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: yourpassword\n\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG\naurora:\n  oauth:\n    access-token-validity-seconds: 259200\n    refresh-token-validity-seconds: 259200\n    redis-delete-retry: 4\n    max-login-failure: 4\n    re-login-minute: 5\n    tx-map-api: https://apis.map.qq.com/ws/location/v1/ip?key=PU6BZ-VVO6V-66QP2-UNRJQ-K2JCO-24BCX\n  snow-flake-datacenter-id: 1\n  snow-flake-worker-id: 2\n  swagger:\n    author:\n      name: aurora\n      url: https://aurora.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 认证中心API\n      description: 这是Aurora主题的博客的认证中心api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/LICENSE', '5742775acc2ad37f75d500251491f145', '2022-05-19 23:57:06', '2022-11-04 04:37:34', 'nacos', '192.168.158.1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (856, 'share-seata-dev.yaml', 'THEME-GROUP', 'seata:\n  enabled: true\n  application-id: applicationName\n  tx-service-group: aurora-blog-tx-group\n  enable-auto-data-source-proxy: true\n  config:\n    type: nacos\n    nacos:\n      serverAddr: 192.168.158.120:8848\n      group: SEATA_GROUP\n      username: \"nacos\"\n      password: \"nacos\"\n  registry:\n    type: nacos\n    nacos:\n      application: seata-server\n      server-addr: 192.168.158.120:8848\n      group: SEATA_GROUP\n#      namespace:\n      username: \"nacos\"\n      password: \"nacos\"\n  service:\n    vgroup-mapping:\n      aurora-blog-tx-group: default', '14c90183a40d980a453c16b1e7ca8fb2', '2022-05-20 00:08:00', '2022-11-04 02:31:49', 'nacos', '192.168.158.1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (857, 'aurora-message-dev.yaml', 'THEME-GROUP', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/aurora_email?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: yourpassword\n  mail:\n    host: smtp.qq.com\n    username: 2291308094@qq.com\n    password: uutqhjyodxtrdiea\n    protocol: smtps\n    properties.mail.smtp.port: 465\n    properties.mail.smtp.starttls.enable: true\n    properties.mail.smtp.starttlls.required: true\n    properties.mail.smtp.ssl.enable: true\n    default-encoding: utf-8\n\n# 自定义的配置\naurora:\n  snow-flake-worker-id: 14\n  snow-flake-datacenter-id: 24\n  amqp:\n    amqp-max-retry-consume: 3\n  swagger:\n    author:\n      name: aurora\n      url: https://aurora.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客消息API\n      description: 这是Aurora主题的博客消息api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/LICENSE\nlogging:\n  level:\n    root: info\n', '874c496481f103a7a931dcefaf7f0b32', '2022-05-20 00:11:22', '2022-11-04 04:37:59', 'nacos', '192.168.158.1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (858, 'aurora-file-dev.yaml', 'THEME-GROUP', 'spring:\n  servlet:\n    multipart:\n      max-file-size: 150MB\n      max-request-size: 500MB\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/aurora_file?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: yourpassword\n\n\n# 自定义的配置\naurora:\n  file:\n    nginx-root-path: F:\\opt\\aurora-theme\\nginx-upload\n    nginx-server-name: http://127.0.0.1\n    save-file-folder: aurora-upload\n  snow-flake-worker-id: 12\n  snow-flake-datacenter-id: 23\n  swagger:\n    author:\n      name: aurora\n      url: https://aurora.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客文件API\n      description: 这是Aurora主题的博客文件api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/LICENSE', 'cf77fad1e6f4ddbba924b114b65a9d80', '2022-05-20 00:17:39', '2022-12-05 13:04:41', 'nacos', '192.168.158.1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (859, 'aurora-comment-dev.yaml', 'THEME-GROUP', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/aurora_comment?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: yourpassword\n\naurora:\n  snow-flake-datacenter-id: 5\n  snow-flake-worker-id: 23\n  swagger:\n    author:\n      name: aurora\n      url: https://aurora.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客评论API\n      description: 这是Aurora主题的博客评论api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/LICENSE', 'f15f485b3c65ef5bb380d7b3a89cb4ce', '2022-05-20 00:19:53', '2022-11-04 04:38:24', 'nacos', '192.168.158.1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (860, 'aurora-gateway-dev.yaml', 'THEME-GROUP', 'spring:\n  cloud:\n    sentinel:\n      filter:\n        enabled: false\n      scg:\n        fallback:\n          mode: response\n          response-status: 200\n          response-body: \'{\"code\":404,\"data\":{},\"message\":\"请求太快,请稍后在试┭┮﹏┭┮\",\"success\":false}\'\n      transport:\n        port: 8719\n        dashboard: localhost:8080\n      datasource:\n        ds1:\n          nacos:\n            server-addr: localhost:8848\n            dataId: sentinel-aurora-gateway\n            groupId: DEFAULT_GROUP\n            data-type: json\n            rule-type: flow\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        - id: openapi\n          uri: http://localhost:${server.port}\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*),/$\\{path}/v3/api-docs\n        - id: aurora-admin\n          uri: lb://aurora-admin\n          predicates:\n            - Path=/admin/**\n        - id: aurora-comment\n          uri: lb://aurora-comment\n          predicates:\n            - Path=/comment/**\n        - id: aurora-article\n          uri: lb://aurora-article\n          predicates:\n            - Path=/blog/**\n        - id: aurora-file\n          uri: lb://aurora-file\n          predicates:\n            - Path=/file/**\n        - id: aurora-message\n          uri: lb://aurora-message\n          predicates:\n            - Path=/message/**\n        - id: aurora-auth-server\n          uri: lb://aurora-auth-server\n          predicates:\n            - Path=/auth/**,/login/**,/oauth/**\n\n\naurora:\n  swagger:\n    author:\n      name: aurora\n      url: https://aurora.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: gateway聚合平台\n      description: 这是Aurora主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/LICENSE', '868351fc7508bb200e40078c720a25dc', '2022-05-20 00:26:18', '2023-02-09 08:12:50', 'nacos', '192.168.158.1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (861, 'aurora-article-dev.yaml', 'THEME-GROUP', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/aurora_article?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: yourpassword\n\n# 自定义的配置\naurora:\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: http://localhost:8088/admin/verifyAccount/\n  swagger:\n    author:\n      name: aurora\n      url: https://aurora.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客文章API\n      description: 这是Aurora主题的博客文章部分的api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/LICENSE\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG\n', '660263c60bc0e7854b26ad936876f5d9', '2022-05-20 00:32:39', '2022-11-07 14:31:36', 'nacos', '192.168.158.1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (863, 'aurora-admin-dev.yaml', 'THEME-GROUP', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/aurora_admin?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: yourpassword\n# 自定义的配置\naurora:\n  defaultRoleList: \n    - user\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: http://localhost:7777/admin/verifyAccount/bindEmail\n    enable-account-prefix-path: http://localhost:7777/admin/verifyAccount/enable\n  default:\n    user:\n      site-info: \"{\\\"readme\\\":\\\"# Hi auUsernameua\\\\n\\\",\\\"showWave\\\":true,\\\"showTopImgBubble\\\":true,\\\"mobilePageSidebar\\\":true,\\\"latestPageSize\\\":6,\\\"githubUrl\\\":\\\"https://github.com/xcyeye\\\",\\\"homePageLazyLoadingImg\\\":\\\"https://picture.xcye.xyz/image-20220328221012634.png\\\",\\\"randomPictureInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"defaultCoverRequestInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"footerInfo\\\":{\\\"enable\\\":true,\\\"isShowThemeCopyright\\\":true,\\\"isShowRunTime\\\":true,\\\"prefixRuntime\\\":\\\"Aurora博客系统\\\",\\\"backgroundImage\\\":\\\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\\\",\\\"footInfo\\\":[\\\"Copyright©byxcyeAllRightsReserved.\\\",\\\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\\\"]},\\\"friendLinkSiteInformation\\\":{\\\"title\\\":\\\"Aurora博客系统\\\",\\\"url\\\":\\\"http://xcye.xyz/user/1522074993315815424\\\",\\\"logo\\\":\\\"http://127.0.0.1/aurora-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\\\",\\\"cover\\\":\\\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\\\",\\\"describe\\\":\\\"Aurora博客系统和Aurora主题作者\\\",\\\"contact\\\":\\\"2291308094\\\"},\\\"socialsArr\\\":[{\\\"aHref\\\":\\\"tencent://message/?uin=2291308094\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:qq\\\",\\\"color\\\":\\\"#90f1ef\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://github.com/xcyeye/\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:github\\\",\\\"color\\\":\\\"#bbe6e4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://space.bilibili.com/483962286\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa-brands:envira\\\",\\\"color\\\":\\\"efd1cd\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://music.163.com/#/user/home?id=1411050784\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:music\\\",\\\"color\\\":\\\"#6fffe9\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"mailto:2291308094@qq.com\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:envelope\\\",\\\"color\\\":\\\"#f2b5d4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"/friendLink/1522074993315815424\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":false,\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"color\\\":\\\"#b8f2e6\\\",\\\"showImgSrc\\\":\\\"\\\"}]}\"\n      welcome-article: \'# HI 你好世界\'\n      navbar-info: \"[{\\\"name\\\":\\\"首页\\\",\\\"url\\\":\\\"/user/auUserUidua\\\",\\\"icon\\\":\\\"fa:home\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"友情链接\\\",\\\"url\\\":\\\"/friendLink/auUserUidua\\\",\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"关于\\\",\\\"url\\\":\\\"/about/auUserUidua\\\",\\\"icon\\\":\\\"fa:pagelines\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"评论\\\",\\\"url\\\":\\\"/comment/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"相册\\\",\\\"url\\\":\\\"/photo/auUserUidua\\\",\\\"icon\\\":\\\"fa:image\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"时间轴\\\",\\\"url\\\":\\\"/archive/auUserUidua\\\",\\\"icon\\\":\\\"fa:hourglass-3\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"分类\\\",\\\"url\\\":\\\"/category/auUserUidua\\\",\\\"icon\\\":\\\"mdi:alarm-bell\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"标签\\\",\\\"url\\\":\\\"/tag/auUserUidua/\\\",\\\"icon\\\":\\\"mdi:abugida-thai\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说\\\",\\\"url\\\":\\\"\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[{\\\"name\\\":\\\"说说1\\\",\\\"url\\\":\\\"/shareSpace/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说2\\\",\\\"url\\\":\\\"/shareSpace-page/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]}]}]\"\n      page-info: \'[{\"name\":\"首页\",\"url\":\"/user/auUserUidua\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/auUserUidua\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/auUserUidua\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/auUserUidua\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/auUserUidua\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/auUserUidua/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/auUserUidua\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/auUserUidua\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]\'\n    avatar: https://picture.xcye.xyz/avatar.jpg\n    role: ROLE_user\n    permission: user\n    nickname: aurora-new\n  swagger:\n    author:\n      name: aurora\n      url: https://aurora.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客后台API\n      description: 这是Aurora主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-aurora/vuepress-theme-aurora/blob/master/LICENSE\n  amqp:\n    exchanges:\n      # 发送邮件的交换机\n      - exchange: aurora.send.email.common.exchange\n        exchange-type: topic\n        dead-letter-exchange: aurora.send.email.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          # 发送html邮件\n          - queue: send.html.mail.queue\n            dead-letter-queue: send.html.mail.dead.letter.queue\n            routing-key: send.html.amil.routing\n            dead-letter-routing-key: send.html.mail.dead.letter.routing\n\n          - queue: send.simple.text.mail.queue\n            dead-letter-queue: send.simple.text.mail.dead.letter.queue\n            routing-key: send.simple.text.mail.routing\n            dead-letter-routing-key: send.simple.text.mail.dead.letter.routing\n            # 发送错误消息的交换机\n      - exchange: aurora.send.mistake.exchange\n        exchange-type: topic\n        dead-letter-exchange: aurora.send.mistake.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: aurora.mistake.queue\n            dead-letter-queue: aurora.mistake.dead.letter.queue\n            routing-key: aurora.mistake.routing.key\n            dead-letter-routing-key: aurora.mistake.dead.letter.routing.key\n              # 发送操作用户数据的交换机\n      - exchange: aurora.send.operate.user.exchange\n        exchange-type: topic\n        dead-letter-exchange: aurora.send.operate.user.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: aurora.operate.user.binding.email.queue\n            dead-letter-queue: aurora.operate.user.binding.email.dead.letter.queue\n            routing-key: aurora.operate.user.binding.email.routing\n            dead-letter-routing-key: aurora.operate.user.binding.email.dead.letter.routing\n\n          - queue: aurora.operate.user.lock.account.queue\n            dead-letter-queue: aurora.operate.user.lock.account.dead.letter.queue\n            routing-key: aurora.operate.user.lock.account.routing.key\n            dead-letter-routing-key: aurora.operate.user.lock.account.dead.letter.routing.key\n\n          - queue: aurora.update.role.permission.queue\n            dead-letter-queue: aurora.update.role.permission.dead.letter.queue\n            routing-key: aurora.update.role.permission.routing.key\n            dead-letter-routing-key: aurora.update.role.permission.dead.letter.routing\n\n          - queue: aurora.update.white.url.queue\n            dead-letter-queue: aurora.update.white.url.dead.letter.queue\n            routing-key: aurora.update.white.url.routing.key\n            dead-letter-routing-key: aurora.update.white.url.dead.letter.routing\n              # 发送评论的交换机\n      - exchange: aurora.send.comment.exchange\n        exchange-type: topic\n        dead-letter-exchange: aurora.send.comment.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: aurora.PAGE.comment.queue\n            dead-letter-queue: aurora.PAGE.comment.dead.letter.queue\n            routing-key: aurora.PAGE.routingKey\n            dead-letter-routing-key: aurora.PAGE.comment.dead.letter.routingKey\n    default-max-length: 25\n    default-ttl: 6000\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG', 'a9c99642ff5899a302ec4edd8dfcec8a', '2022-05-20 00:40:39', '2023-01-27 07:07:32', 'nacos', '192.168.158.1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (870, 'share-swagger-dev.yaml', 'THEME-GROUP', 'aurora:\n    gateway:\n        server-base-url: http://localhost:7777', '928543c3d321fc76b7d373319f1fa116', '2022-05-21 04:16:37', '2022-05-21 04:16:37', NULL, '0:0:0:0:0:0:0:1', '', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', NULL, NULL, NULL, 'yaml', NULL, '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `datum_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '增加租户字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_beta' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_tag' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id` ASC, `tag_name` ASC, `tag_type` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_tag_relation' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint UNSIGNED NOT NULL,
  `nid` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create` ASC) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified` ASC) USING BTREE,
  INDEX `idx_did`(`data_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1095 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '多租户改造' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `resource` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role` ASC, `resource` ASC, `action` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username` ASC, `role` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '租户容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp` ASC, `tenant_id` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'tenant_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (7, '1', '480d1031-510d-466c-8be8-77853d9dc7c2', 'dev', '这是aurora主题开发的dev环境', 'nacos', 1652971994101, 1652971994101);
INSERT INTO `tenant_info` VALUES (8, '1', '84313f7a-0de5-4214-9f62-455859382e1a', 'prod', '这是aurora主题的生产环境', 'nacos', 1652972010999, 1652972010999);
INSERT INTO `tenant_info` VALUES (9, '1', 'a6b1905f-d8c6-4a43-8f21-fe662f62bdae', 'aurora', '这是主题的配置', 'nacos', 1653004098513, 1653004098513);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$zu96b2.cV0MeusTIJ4/xkOCCQMGRAx8NAztxmhANJghxtLX/iYGOy', 1);

SET FOREIGN_KEY_CHECKS = 1;
