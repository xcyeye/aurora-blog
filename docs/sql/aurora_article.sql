/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : aurora_article

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 05/08/2022 18:03:00
*/
use aurora_article;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for au_article
-- ----------------------------
DROP TABLE IF EXISTS `au_article`;
CREATE TABLE `au_article` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `is_show_comment` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '文章是否展示评论，0：否，1：是',
  `accessory_uids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章有附件的话，附件的uid集合',
  `category_names` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章类别uid集合',
  `tag_names` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章标签uid集合',
  `is_publish` tinyint NOT NULL DEFAULT '1' COMMENT '文章是否发布，1：发布，0：不发布',
  `user_uid` bigint NOT NULL COMMENT '发布此篇文章的用户uid',
  `is_original_article` tinyint NOT NULL DEFAULT '1' COMMENT '是否原创，1：原创 0：不是原创',
  `original_article_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '如果是原创，则原创链接',
  `cover_picture_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章封面对应的图片uid',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章内容',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章简介',
  `is_timing` tinyint NOT NULL DEFAULT '0' COMMENT '是否定时发布 0： 不定时，1：定时',
  `timing_publish_time` timestamp NULL DEFAULT NULL COMMENT '定时发布时间',
  `like_number` int DEFAULT '0' COMMENT '文章点赞数',
  `read_number` int DEFAULT NULL COMMENT '阅读量',
  `comment_uids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '此篇文章对应的评论uid集合，只需要设置所有父评论的uid',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '此篇文章最后修改的时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章发布时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 逻辑删除 1： 已删除',
  PRIMARY KEY (`uid`),
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `union_article_index` (`uid`,`user_uid`,`create_time`) USING BTREE,
  FULLTEXT KEY `fullText_index` (`content`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_article
-- ----------------------------
BEGIN;
INSERT INTO `au_article` (`uid`, `is_show_comment`, `accessory_uids`, `category_names`, `tag_names`, `is_publish`, `user_uid`, `is_original_article`, `original_article_url`, `cover_picture_url`, `content`, `title`, `summary`, `is_timing`, `timing_publish_time`, `like_number`, `read_number`, `comment_uids`, `update_time`, `create_time`, `is_delete`) VALUES (1533124341986238464, 1, NULL, '编程,代码编程,代码', 'java,spring,sprng bootjava,sprng boot', 1, 1522074993315815424, 1, NULL, 'http://localhost/aurora-upload/jpg/2022/5/wallhaven-72o6ze1653997838365.jpg', '这是内容\n\n![image1654408906265.png](http://localhost/aurora-upload/png/2022/6/image1654408906265.png)\n\n\naskjdh\n\n\n## 作品简介\n\n项目背景：\n\n在疫情形势下，线上学习已成为必不可少的学习形式，学习者对线上学习平台的选择也越来越挑剔。本项目采用Neo4j图数据库为基础，通过Java的Springboot框架、Vue前端框架实现前后端管理与数据获取对接的网络工程师考试学习系统，形成完整的网络工程师知识框架，具有直观化、可视化的优点。本项目构建一个完整、清晰的知识体系，学习者可通过一部分知识节点引入其他知识节点的学习，也可在学习完知识后，进行一个简单的练习，能有效的加强学习者的逻辑思维能力，形成一定程度的知识框架，同时通过学习后的练习与测试，可以达到一定的巩固和举一反三效果。\n\n创新点：\n\n1）研究知识图谱现有先进技术并进行学习，通过网络工程师这一主题呈现图谱的构造；\n\n2）网络工程师这一主题学习软件、练习平台的上市率较少、知名度较低，题材具有一定的新颖性，对消费者帮助较大\n\n3）知识图谱能够很好的解决知识信息查阅时的不准确、检索困难等问题。该课题针对行业领域专家以和系统维护人员设计了节点、关系的添加与删除的功能，更新保证了知识点的准确性和完整性。\n\n4）Neo4j是一个原生的图数据库引擎，它存储了原生的图数据，因此，可以使用图结构的自然伸展特性来设计免索引邻近节点遍历的查询算法，设计具有灵活性，开发具有敏捷性，与当前一些主流的数据库相比，不管是传统的关系型数据库，还是NoSQL数据库，或者同类的图数据库，Neo4j都是出类拔萃的。\n\n软件特点：\n\n（1）闪电般的读/写速度，无与伦比的高性能表现。\n\n（2）非结构化数据存储方式，在数据库设计上具有很大的灵活性。\n\n（3）能很好地适应需求变化，并适合使用敏捷开发方法。\n\n（4）很容易使用，可以用嵌入式、服务器模式、分布式模式等方式来使用数据库。\n\n（5）使用简单框图就可以设计数据模型，方便建模。\n\n（6）图数据的结构特点可以提供更多更优秀的算法设计。\n\n（7）完全支持ACID完整的事务管理特性。\n\n（8）提供分布式高可用模式，可以支持大规模的数据增长。\n\n（9）数据库安全可靠，可以实时备份数据，很方便恢复数据。\n\n（10）图的数据结构直观而形象地表现了现实世界的应用场景。\n\n## 开源组件说明\n\n### 后端\n\n该作品是以java作为主要开发语言，使用前后端分离的方式进行协作开发，后端使用到了目前流行的Spring Boot技术，以及目前世界领先的图数据库-Neo4j。其中后端部分使用到了以下框架\n\n1. Spring Boot：相关框架，大家web开发环境，[Spring Boo官网](https://spring.io/projects/spring-boot)\n2. Spring Security：权限控制，[Spring Security官网](https://spring.io/projects/spring-security)\n3. Spring Neo4j：操作Neo4j图数据库的依赖，[Neo4j官网](https://neo4j.com)\n4. Lombok：简化Java开发，消除代码臃肿，[Lombok官网](https://projectlombok.org/)\n5. Easyexcel：操作excel工具，[Github](https://github.com/alibaba/easyexcel)\n6. Springfox： 是一套围绕 Open API 规范构建的开源工具，可以帮助设计，构建，记录和使用 REST API，方便前端接口的对接以及api接口的阅读。[Springfox官网](http://springfox.github.io/springfox/)\n\n### 前端\n\n后台管理使用Vue进行开发，这是一套用于构建用户界面的渐进式框架，以及Vue相关生态，如下：\n\n1. Vuex：Vue状态管理模式+库\n2. Vue-Router：Vue应用的路由管理框架\n3. Element Ui：开发Vue后台管理桌面端的组件库\n\n除此以外，还使用到了下面框架进行开发\n\n1. Apache ECharts：JavaScript的数据可视化图表库\n2. Axios：基于Promise的网络请求库，用于和后端接口的对接\n\n## 安装说明\n\n在本地部署此作品，需要以下环境：\n\n安装Jdk1.8及以上版本的java开发环境\n\n安装Node.js，14版本及以上\n\n注：为了在不同操作系统上，快速部署，在项目的后端服务上，我们使用的Neo4j服务为我们已部署到云服务器上的服务，可以不用在本机上，单独安装Neo4j服务。\n\n### 快速部署\n\n#### 后端\n\n该项目已将后端服务进行打包，进入网盘中的2022023013-02素材与源码 ---> 快速部署文件夹中，将此文件夹中的neo4j-front文件夹和.jar文件下载到本地\n\n![image-20220506185722304](https://picture.xcye.xyz/image-20220506185722304.png)\n\n然后运行命令java -jar neo4j-back-0.0.1-SNAPSHOT.jar，启动后端服务，此后端服务会占用8030端口，请运行之前，确保该端口未被其他程序占用\n\n![image-20220506190037838](https://picture.xcye.xyz/image-20220506190037838.png)\n\n#### 前端页面\n\n进入neo4j-front目录中，从此目录中进入命令行窗口中，运行npm install命令，安装前端所需的服务\n\n![image-20220506190221066](https://picture.xcye.xyz/image-20220506190221066.png)\n\n待依赖安装成功之后，运行npm run serve启动前端\n\n![image-20220506190443761](https://picture.xcye.xyz/image-20220506190443761.png)\n\n![image-20220506190509208](https://picture.xcye.xyz/image-20220506190509208.png)\n\n出现上图这样，就表示前端页面启动成功，现在只需要在浏览器中输入http://localhost:8088，便可以访问该知识图谱系统\n\n![image-20220506190642053](https://picture.xcye.xyz/image-20220506190642053.png)\n\n## 作品效果图\n\n![image-20220506153145196](https://picture.xcye.xyz/image-20220506153145196.png)\n\n![image-20220506112059591](https://picture.xcye.xyz/image-20220506112059591.png)\n\n> 在此界面中，学习者点击某个知识点后，系统会自动查询出该知识点的介绍，一次该知识点的重要等级等信息，如果是系统的管理者，或者在此该领域内(比如计算机网络)的专家，老师，如果发现此知识点存在错误，可以通过修改面板，对该知识点的信息进行修改，从而让更多的学习者，获取到最权威，准确的学习资料。\n\n![image-20220506111409316](https://picture.xcye.xyz/image-20220506111409316.png)\n\n> 知识点测试题：我们可以为每一个知识点增加大量的题型(选择题，判断题，填空题，简答题)，学习者在学习该查看该节点的相关知识后，如果对此知识点不是很清晰，他可以通过做题的形式，加深对该知识点的理解\n\n![image-20220506112426429](https://picture.xcye.xyz/image-20220506112426429.png)\n\n> 作为一个学习兼管理的后台系统，我们也对具有相关权限的用户提供了用户管理，权限管理，试题管理，以及学习者个人信息的更新，修改操作。\n\n![image-20220506112846731](https://picture.xcye.xyz/image-20220506112846731.png)\n\n![image-20220506112917696](https://picture.xcye.xyz/image-20220506112917696.png)\n\n> 在权限管理中，我们设计了基于RBAC的系统权限控制，保证了该知识图谱系统的安全。\n\n![image-20220506113307775](https://picture.xcye.xyz/image-20220506113307775.png)\n\n![image-20220506113331962](https://picture.xcye.xyz/image-20220506113331962.png)\n\n![image-20220506113403191](https://picture.xcye.xyz/image-20220506113403191.png)\n\n## 设计思路\n\n当我们经过多次讨论决定做和知识图谱相关的主题之后，通过网络和老师的咨询，对当前使用较多的图数据库Neo4j、JanusGraph、HugeGraph进行了分析和比较，最终选择Neo4j作为该项目的数据库，Neo4j拥有着存取速度快，单节点的服务器可承载上亿级的节点和关系。社区版最多支持 320 亿个节点、320 亿个关系和 640 亿个属性等特点。\n\n选定图数据库之后，我们对程序的设计，充分围绕Neo4j的特性进行设计，无论是在不同知识点的链接上，还是权限的相关控制上，Neo4j都远超关系型数据库，以下是我们对程序各个部分的设计思路：\n\n### 权限控制\n\n权限控制部分我们使用了RBAC这种基于角色的权限访问控制模型，每一个角色拥有着访问多个路径的权限，可以和容易的对角色的权限进行赋予和取消，然后再根据用户的责任和资格来指派响应的角色，就能很容易的对系统的权限进行控制，从而保证了系统的安全。\n\n![image-20220506195447528](https://picture.xcye.xyz/image-20220506195447528.png)\n\n上图中的蓝圈表示角色，绿圈表示资源。从上图很容易看出每个角色多拥有的权限，使用Neo4j能够将每个角色和资源的关系链接起来，他们箭头的指向直接显示每个角色拥有的权限。\n\n我们也在此基础上进行了扩展，资源不仅仅是访问的Uri，也可以是菜单栏的某个组件，用户，试题等等，最终neo4j可以将这种多种不同属性的资源，通过角色节点链接起来，如下图：\n\n![image-20220506200234260](https://picture.xcye.xyz/image-20220506200234260.png)\n\n在程序的技术选项上，我们使用了Spring家族中的Spring Security框架，能够使用较少的代码，为系统增加权限控制。当每一个用户执行查询，修改，增加，删除等操作时，在Spring Security中，对每一个请求进行鉴权操作，便可以保障系统的安全。\n\n### 知识点\n\n![image-20220507081635235](https://picture.xcye.xyz/image-20220507081635235.png)\n\n如果我们想要学习一个新的知识点，那我们必然会先从基础开始学起，然后逐步学习更深的内容，而且知识点具有迁移性，我们可以从一个知识点引出其他知识点，在Neo4j中，可以很好的建立起这样的知识点关系来，我们只需要为知识点设置一些属性，当将一个知识点和另一个知识点建立关系时候，因为建立关系需要确定方向，以及关系的节点属性，通过neo4j的关系建立，我们便可以很容易的建立起复杂，高性能的知识图谱结构，上图展示的是计算机网络的知识图谱，但是并不代表该系统只能做计算机网络的知识图谱学习，我们还可以对知识点进行横向扩展，可以引出《操作系统》，《计算机组成原理》，或者其他专业的知识点。\n\n![image-20220507082731302](https://picture.xcye.xyz/image-20220507082731302.png)\n\n### 试题管理\n\n对于一个学习系统来说，试题是必不可少的一部分，当我们学习了某个知识点之后，如果没有对应的题库来加固对知识点的掌握，那么长时间之后，我们学过的知识点会变得模糊，我们在设计之初，就充分考虑到了这点，为该图谱系统增加了题库。\n\n![image-20220508154338385](https://picture.xcye.xyz/image-20220508154338385.png)\n\n而且在Neo4j中，节点之间存在关系属性，在数据库设计的时候，我们将每一题都看成是一个节点，将单个或者多个节点和某个知识点建立关系，那么我们就可以为已经存在的节点，创建对应的题库。\n\n![image-20220508155117116](https://picture.xcye.xyz/image-20220508155117116.png)\n\n在题型上，我们实现了考试中常见的题型，包括选择题，填空题，简答题，判断题，当学生在查看某个知识点之后，如果在此知识点上，存在题库，那么学习者便可以通过做题，加深对该知识点的理解。\n\n![image-20220508155757800](https://picture.xcye.xyz/image-20220508155757800.png)\n\n在学习者做完题之后，能够查看所做的题时候正确，并且系统会给出正确的结果，以及该题的分析\n\n![image-20220508155909170](https://picture.xcye.xyz/image-20220508155909170.png)\n\n## 重点难点\n\n在做该知识图谱系统的过程中，我们也遇到了很多的问题，下面我们将从几个方面简述遇到的难题\n\n### 关系展示\n\n因为Neo4j是一个图数据库，节点之间通过关心链接起来，从而形成一个关系网，对于知识图谱也一样，我们在设计的时候，想要尽可能的将节点之间的关系展示出来，最终我们使用了Apache ECharts的关系图模型来展示节点点的关系\n', '这是标题', '概述', 1, '2022-06-25 14:38:21', 3487, NULL, NULL, '2022-06-05 14:45:38', '2022-06-05 00:31:41', 0);
COMMIT;

-- ----------------------------
-- Table structure for au_bulletin
-- ----------------------------
DROP TABLE IF EXISTS `au_bulletin`;
CREATE TABLE `au_bulletin` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '公告的标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '公告创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '公告最后修改时间',
  `user_uid` bigint DEFAULT NULL COMMENT '发布此公告的用户uid',
  `is_show` tinyint NOT NULL DEFAULT '1' COMMENT '1: 显示公告 0： 不显示该公告',
  `is_timing` tinyint NOT NULL DEFAULT '0' COMMENT '1：定时发布 0： 不定时发布公告',
  `timing_publish_time` timestamp NULL DEFAULT NULL COMMENT '定时发布公告的时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1:删除 0：未删除',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `union_bulletin_index` (`uid`,`user_uid`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_bulletin
-- ----------------------------
BEGIN;
INSERT INTO `au_bulletin` (`uid`, `title`, `content`, `create_time`, `update_time`, `user_uid`, `is_show`, `is_timing`, `timing_publish_time`, `is_delete`) VALUES (1524397958342647808, NULL, '这是公告的内容', '2022-05-11 22:36:09', NULL, 1522074993315815424, 1, 0, NULL, 0);
INSERT INTO `au_bulletin` (`uid`, `title`, `content`, `create_time`, `update_time`, `user_uid`, `is_show`, `is_timing`, `timing_publish_time`, `is_delete`) VALUES (1524397965154197504, NULL, '这是公告的内容', '2022-05-11 22:36:11', NULL, 1522074993315815424, 1, 0, NULL, 0);
INSERT INTO `au_bulletin` (`uid`, `title`, `content`, `create_time`, `update_time`, `user_uid`, `is_show`, `is_timing`, `timing_publish_time`, `is_delete`) VALUES (1524397967461064704, NULL, '这是公告的内容', '2022-05-11 22:36:12', NULL, 1522074993315815424, 1, 0, NULL, 0);
INSERT INTO `au_bulletin` (`uid`, `title`, `content`, `create_time`, `update_time`, `user_uid`, `is_show`, `is_timing`, `timing_publish_time`, `is_delete`) VALUES (1524397969767931904, NULL, '这是公告的内容', '2022-05-11 22:36:12', NULL, 1522074993315815424, 1, 0, NULL, 0);
INSERT INTO `au_bulletin` (`uid`, `title`, `content`, `create_time`, `update_time`, `user_uid`, `is_show`, `is_timing`, `timing_publish_time`, `is_delete`) VALUES (1524398089817300992, 'asdfasdf', '这是公告的内容', '2022-05-11 22:36:41', NULL, 1522074993315815424, 1, 0, NULL, 0);
INSERT INTO `au_bulletin` (`uid`, `title`, `content`, `create_time`, `update_time`, `user_uid`, `is_show`, `is_timing`, `timing_publish_time`, `is_delete`) VALUES (1524398157244932096, 'asdfasdf', '这是公告的内容', '2022-05-11 22:36:57', NULL, 1522074993315815424, 0, 0, NULL, 0);
INSERT INTO `au_bulletin` (`uid`, `title`, `content`, `create_time`, `update_time`, `user_uid`, `is_show`, `is_timing`, `timing_publish_time`, `is_delete`) VALUES (1524398240799662080, 'asdfasdf', '这是公告的内容', '2022-05-11 22:37:17', NULL, 1522074993315815424, 0, 1, NULL, 0);
INSERT INTO `au_bulletin` (`uid`, `title`, `content`, `create_time`, `update_time`, `user_uid`, `is_show`, `is_timing`, `timing_publish_time`, `is_delete`) VALUES (1524401652748525568, 'asdfasdf', '这是公告的内容', '2022-05-11 22:51:07', NULL, 1522074993315815424, 0, 0, NULL, 0);
INSERT INTO `au_bulletin` (`uid`, `title`, `content`, `create_time`, `update_time`, `user_uid`, `is_show`, `is_timing`, `timing_publish_time`, `is_delete`) VALUES (1524405128467587072, 'asdfasdf', '这是公告的内容', '2022-05-11 23:04:39', NULL, 1522074993315815424, 0, 0, NULL, 0);
INSERT INTO `au_bulletin` (`uid`, `title`, `content`, `create_time`, `update_time`, `user_uid`, `is_show`, `is_timing`, `timing_publish_time`, `is_delete`) VALUES (1524405223913168896, 'q310aiyaiiylba7', '2uwbva890gfxwzx', '2022-05-18 16:23:16', '2022-05-18 16:23:16', 1522074993315815424, 1, 0, NULL, 1);
INSERT INTO `au_bulletin` (`uid`, `title`, `content`, `create_time`, `update_time`, `user_uid`, `is_show`, `is_timing`, `timing_publish_time`, `is_delete`) VALUES (1526840562380447744, 'xzoe5ahdy6k6dn2', '2n71xrcbzb23kvf', '2022-05-18 16:22:12', NULL, 1522074993315815424, 1, 0, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for au_category
-- ----------------------------
DROP TABLE IF EXISTS `au_category`;
CREATE TABLE `au_category` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此类别的标题',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此类别的简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此类别的封面图地址',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1: 删除 ，0：未删除',
  `user_uid` bigint NOT NULL COMMENT '用户的userUid',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique_title` (`title`),
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `union_category` (`uid`,`title`,`user_uid`,`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_category
-- ----------------------------
BEGIN;
INSERT INTO `au_category` (`uid`, `title`, `summary`, `create_time`, `update_time`, `cover_url`, `is_delete`, `user_uid`) VALUES (18, '编程', NULL, '2022-06-05 00:31:41', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` (`uid`, `title`, `summary`, `create_time`, `update_time`, `cover_url`, `is_delete`, `user_uid`) VALUES (19, '代码', NULL, '2022-06-05 00:31:41', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` (`uid`, `title`, `summary`, `create_time`, `update_time`, `cover_url`, `is_delete`, `user_uid`) VALUES (20, '好友', NULL, '2022-06-05 09:03:20', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` (`uid`, `title`, `summary`, `create_time`, `update_time`, `cover_url`, `is_delete`, `user_uid`) VALUES (21, '大佬', NULL, '2022-06-05 09:03:30', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` (`uid`, `title`, `summary`, `create_time`, `update_time`, `cover_url`, `is_delete`, `user_uid`) VALUES (22, '代码编程', NULL, '2022-06-05 14:02:46', NULL, NULL, 0, 1522074993315815424);
COMMIT;

-- ----------------------------
-- Table structure for au_link
-- ----------------------------
DROP TABLE IF EXISTS `au_link`;
CREATE TABLE `au_link` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_uid` bigint DEFAULT NULL COMMENT '此条友情链接是哪个用户的',
  `category_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '此条友情链接属于哪个分类',
  `link_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'logo地址',
  `link_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '链接地址',
  `link_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对方的名称',
  `link_description` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述信息',
  `link_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '对方站点的封面图',
  `is_publish` tinyint NOT NULL DEFAULT '1' COMMENT '是否展示此条友情链接 1：展示 0：不展示',
  `email` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此友情链接对应的站长邮箱',
  `qq_number` bigint DEFAULT NULL COMMENT '此友情链接对应的站长的qq号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `unique_linkUrl_index` (`link_url`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `categoryName_index` (`category_name`) USING BTREE,
  KEY `union_link_index` (`uid`,`user_uid`,`link_url`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_link
-- ----------------------------
BEGIN;
INSERT INTO `au_link` (`uid`, `user_uid`, `category_name`, `link_logo`, `link_url`, `link_title`, `link_description`, `link_cover`, `is_publish`, `email`, `qq_number`, `create_time`, `update_time`) VALUES (1527448550208184320, 1522074993315815424, '大佬', 'xojf8lfu6g73dq5', 'ou31q35tvboyndv', 'fmdfo82yjf620po', 'x43p1ja2inxrrjj', 'dvjhry5nxuc0ed2', 1, '2291308094@qq.com', 1, '2022-05-20 08:38:07', '2022-06-05 09:02:54');
INSERT INTO `au_link` (`uid`, `user_uid`, `category_name`, `link_logo`, `link_url`, `link_title`, `link_description`, `link_cover`, `is_publish`, `email`, `qq_number`, `create_time`, `update_time`) VALUES (1533376946612215808, 1522074993315815424, '代码', 'https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png', 'https://baidu.com', '青衫烟雨客', NULL, NULL, 0, '2291308094@qq.com', NULL, '2022-06-05 17:15:27', NULL);
INSERT INTO `au_link` (`uid`, `user_uid`, `category_name`, `link_logo`, `link_url`, `link_title`, `link_description`, `link_cover`, `is_publish`, `email`, `qq_number`, `create_time`, `update_time`) VALUES (1533381391257444352, 1522074993315815424, '代码', 'https://aurora.xcye.xyz/avatar.png', 'https://xcye.xyz', 'asdfkjhasd', NULL, NULL, 0, '2604400276@qq.com', NULL, '2022-06-05 17:33:12', NULL);
INSERT INTO `au_link` (`uid`, `user_uid`, `category_name`, `link_logo`, `link_url`, `link_title`, `link_description`, `link_cover`, `is_publish`, `email`, `qq_number`, `create_time`, `update_time`) VALUES (1533381819781095424, 1522074993315815424, '编程', 'asdflqwer,masdf', 'asdfliwuqerjhbsdf', 'asdfukasdf', NULL, NULL, 0, '2291308094@qq.com', NULL, '2022-06-05 17:34:49', NULL);
INSERT INTO `au_link` (`uid`, `user_uid`, `category_name`, `link_logo`, `link_url`, `link_title`, `link_description`, `link_cover`, `is_publish`, `email`, `qq_number`, `create_time`, `update_time`) VALUES (1533382069581258752, 1522074993315815424, '代码', 'asdf', 'https://jindon.com', 'asdfkjasdf', NULL, NULL, 0, '2291345@qq.com', NULL, '2022-06-05 17:35:48', NULL);
INSERT INTO `au_link` (`uid`, `user_uid`, `category_name`, `link_logo`, `link_url`, `link_title`, `link_description`, `link_cover`, `is_publish`, `email`, `qq_number`, `create_time`, `update_time`) VALUES (1533383863292141568, 1522074993315815424, '代码', 'asdfkjwermsndf', 'asdfkjhawreasdf', 'asdf', NULL, NULL, 0, '22asekjf@qq.com', NULL, '2022-06-05 17:42:57', NULL);
INSERT INTO `au_link` (`uid`, `user_uid`, `category_name`, `link_logo`, `link_url`, `link_title`, `link_description`, `link_cover`, `is_publish`, `email`, `qq_number`, `create_time`, `update_time`) VALUES (1533384660415422464, 1522074993315815424, '大佬', 'https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png', 'https://www.baidu.com', '这是一条测试评论', NULL, NULL, 0, '2604400276@qq.com', NULL, '2022-06-05 17:46:06', NULL);
COMMIT;

-- ----------------------------
-- Table structure for au_tag
-- ----------------------------
DROP TABLE IF EXISTS `au_tag`;
CREATE TABLE `au_tag` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此标签的标题',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此标签的简介',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `cover_Url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此类别的封面图uid',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1: 删除，0：未删除',
  `user_uid` bigint NOT NULL COMMENT '用户的userUid',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique_title_index` (`title`),
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `createTime_index` (`created_time`) USING BTREE,
  KEY `union_tag_index` (`uid`,`title`,`user_uid`,`created_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_tag
-- ----------------------------
BEGIN;
INSERT INTO `au_tag` (`uid`, `title`, `summary`, `created_time`, `updated_time`, `cover_Url`, `is_delete`, `user_uid`) VALUES (11, 'java', NULL, '2022-06-04 23:40:10', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_tag` (`uid`, `title`, `summary`, `created_time`, `updated_time`, `cover_Url`, `is_delete`, `user_uid`) VALUES (12, 'spring', NULL, '2022-06-05 00:31:41', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_tag` (`uid`, `title`, `summary`, `created_time`, `updated_time`, `cover_Url`, `is_delete`, `user_uid`) VALUES (13, 'sprng boot', NULL, '2022-06-05 00:31:41', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_tag` (`uid`, `title`, `summary`, `created_time`, `updated_time`, `cover_Url`, `is_delete`, `user_uid`) VALUES (14, 'sprng bootjava', NULL, '2022-06-05 14:02:46', NULL, NULL, 0, 1522074993315815424);
COMMIT;

-- ----------------------------
-- Table structure for au_talk
-- ----------------------------
DROP TABLE IF EXISTS `au_talk`;
CREATE TABLE `au_talk` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_uid` bigint DEFAULT NULL COMMENT '发布此说说的用户uid',
  `is_show_comment` tinyint NOT NULL DEFAULT '1' COMMENT '此说说是否显示评论 1： 显示 0： 不显示',
  `comment_uids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此说说的评论uid集合',
  `updated_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '此说说发布时间',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此说说的内容',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此说说标题',
  `is_show` tinyint NOT NULL DEFAULT '1' COMMENT '1： 显示说说，0： 不显示说说',
  `like_number` int NOT NULL DEFAULT '0' COMMENT '此说说的点赞数',
  `picture_uids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此说说对应的图片uid集合',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1: 已删除',
  PRIMARY KEY (`uid`),
  KEY `user_uid_index` (`user_uid`) USING BTREE,
  KEY `createTime_index` (`created_time`) USING BTREE,
  KEY `union_talk_index` (`uid`,`user_uid`,`created_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of au_talk
-- ----------------------------
BEGIN;
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
) ENGINE=InnoDB AUTO_INCREMENT=1159 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
