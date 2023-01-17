/*
 Navicat Premium Data Transfer

 Source Server         : 本地Linux
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : 192.168.158.120:3306
 Source Schema         : aurora_article

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 17/01/2023 00:34:42
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_article
-- ----------------------------
INSERT INTO `au_article` VALUES (1614602784120250368, 1, NULL, '今日头条,测试文章', '今日头条,明星', 1, 1522074993315815424, 1, NULL, 'http://127.0.0.1/aurora-upload/jpg/2023/1/wallhaven-3zy6r31673786271807.jpg', '1月13日，音乐圈再传一则悲痛噩耗，知名歌唱家谢莉斯在北京不幸病逝，终年75岁，爱妻离世之后，谢莉斯的丈夫郎文曜第一时间对外发布讣告。\n\n![](https://p3-sign.toutiaoimg.com/tos-cn-i-qvj2lq49k0/8be31a49dc1444cf9d1a820cec9e556f~noop.image?_iz=58558&from=article.pc_detail&x-expires=1674391008&x-signature=6u3Hu%2BfwrmQRJgAZJOb7TyhY7b0%3D)\n\n据悉，今年这对恩爱夫妻刚刚迎来金婚，两人风雨同路50年，感情可谓是相当深厚，从郎文曜的口吻中也能深切感受到其永失所爱的悲痛之情。\n\n![](https://p3-sign.toutiaoimg.com/tos-cn-i-qvj2lq49k0/d7bc95258217427688149233b54f6dc8~noop.image?_iz=58558&from=article.pc_detail&x-expires=1674391008&x-signature=DXtnn5iaMiPRMVqjhHI36QkAWBY%3D)\n\n对于谢莉斯的去世原因，其家人始终没有对外透露，不过有知情人士在网上提及了谢老师去世的诸多细节。\n\n据这位网友表示，自己的外婆与谢莉斯是同病房病友，谢莉斯三周前曾入住了北京中日医院的呼吸科病房，通过其姥姥的告知，这位网友表示谢莉斯在此期间遭受了巨大痛苦，其姥姥作为同病房病友一直有所目睹。\n', '谢莉斯去世细节曝光：因感染新冠入住呼吸科，去世前遭受巨大痛苦', '1月13日，音乐圈再传一则悲痛噩耗，知名歌唱家谢莉斯在北京不幸病逝，终年75岁，爱妻离世之后，谢莉斯的丈夫郎文曜第一时间对外发布讣告。\n\n![](https://p3-sign.toutiaoimg.com/tos-cn-i-qvj2lq4', 0, NULL, 25, 44, NULL, '2023-01-15 21:20:58', '2023-01-15 20:37:56', 0);
INSERT INTO `au_article` VALUES (1614603047082139648, 1, NULL, '今日头条', '今日头条,明星,王思聪', 1, 1522074993315815424, 1, NULL, 'https://p3-sign.toutiaoimg.com/tos-cn-i-qvj2lq49k0/77a7627bbd2d4330aca8aaf60d705462~noop.image?_iz=58558&from=article.pc_detail&x-expires=1674391088&x-signature=CzeYRRx6vABM4dzzba3N3mJ%2FGv0%3D', '# 一级标题\n\n## 二级标题\n\n有句话叫作：“不要把自己不当回事，也不要太把自己当回事。”\n\n可是有的人就太把自己当回事了，兜里有俩钱就不知道自己姓啥名谁了。\n\n这不万达太子王思聪就是因为太把自己当回事出事了！\n\n1月12日，上海静安警方发布通报称，王某某等人殴打路人致轻微伤，有媒体证实，打人者王某某系王思聪。\n\n从警方通报可以看出，“撕葱哥”是误以为别人对他拍照，而且还先动手。\n\n媒体出于善意的提醒，应该奉劝“撕葱哥”一句，别太把自己当根葱！\n\n13日，网传王思聪发朋友圈称已付209万与被殴打者和解，不过也有多位网友指出该截图的头像与王思聪朋友圈头像不符。\n\n![](https://p3-sign.toutiaoimg.com/tos-cn-i-qvj2lq49k0/8485b598757b40b58421234476ce97eb~noop.image?_iz=58558&from=article.pc_detail&x-expires=1674391088&x-signature=y9YfuXxleasZ0Cs5tE%2BzeXuNRFE%3D)\n\n# 02.\n\n如果不是因为有一个有钱的爹，王思聪这样要颜值没有颜值、要才华没有才华的人，估计还真入不了美女的眼，但是就是因为他是富二代，就成了很多女人口中的“国民老公”。\n\n这些年，王思聪凭借他爹的钱，身边美女不断，有人称他“夜夜当新郎”，就在元旦在三亚还有人遇到34岁的他带着一个00后美女一起度假，被称为“王思聪的眼光没变，一直喜欢18岁的女孩。”\n\n见到不顺眼的事，他恕天恕地恕空气，最后连微博都被禁言了。\n\n这次他在上海打人，因为开始警方通报里写的是“王某某”，有人怀疑是王思聪，但是又和他的年龄对不上，因为按理说他应该35岁，而通报里写的是34岁，有人甚至等着王思聪自己出来辟谣。\n', '王思聪打人花209万没和解？别把自己当根葱，有钱也不能使鬼推磨', '有句话叫作：“不要把自己不当回事，也不要太把自己当回事。”\n\n可是有的人就太把自己当回事了，兜里有俩钱就不知道自己姓啥名谁了。\n\n这不万达太子王思聪就是因为太把自己当回事出事了！\n\n1月12日，上海静安警方发布通报称，王某某等人殴打路人致轻', 0, NULL, 4, 48, NULL, '2023-01-15 20:39:16', '2023-01-15 20:38:59', 0);
INSERT INTO `au_article` VALUES (1614808343821950976, 1, NULL, '测试类别,测试', '测试,文章', 1, 1522074993315815424, 1, NULL, 'http://127.0.0.1/aurora-upload/jpg/2023/1/illust_84782162_20220928_080513.jpg', '测试文章1\n\n\n14、幸福在于得到足够的睡眠。就这样，别无所求。\n\n15、我身体里的火车从来不会错轨，所以允许大雪，风暴，泥石流，和荒谬。\n\n16、20多岁的人生，就好像是走进了服装店试遍所有衣服。\n\n17、人，会在不幸的时候切身感到“不幸”，而只有在回望的时候才会感到“幸福”。说“我现在很幸福”的人是值得被祝贺的。\n', '测试文章1', '测试文章1\n\n\n14、幸福在于得到足够的睡眠。就这样，别无所求。\n\n15、我身体里的火车从来不会错轨，所以允许大雪，风暴，泥石流，和荒谬。\n\n16、20多岁的人生，就好像是走进了服装店试遍所有衣服。\n\n17、人，会在不幸的时候切身感到“不幸', 0, NULL, 2, 11, NULL, '2023-01-16 12:21:52', '2023-01-16 10:14:41', 0);
INSERT INTO `au_article` VALUES (1614808490945552384, 1, NULL, '测试类别,测试', '测试,文章', 1, 1522074993315815424, 1, NULL, 'http://127.0.0.1/aurora-upload/jpg/2023/1/illust_89301956_20220928_080720.jpg', '测试文章2\n\n\n14、幸福在于得到足够的睡眠。就这样，别无所求。\n\n15、我身体里的火车从来不会错轨，所以允许大雪，风暴，泥石流，和荒谬。\n\n16、20多岁的人生，就好像是走进了服装店试遍所有衣服。\n\n17、人，会在不幸的时候切身感到“不幸”，而只有在回望的时候才会感到“幸福”。说“我现在很幸福”的人是值得被祝贺的。\n', '测试文章2', '测试文章2\n\n\n14、幸福在于得到足够的睡眠。就这样，别无所求。\n\n15、我身体里的火车从来不会错轨，所以允许大雪，风暴，泥石流，和荒谬。\n\n16、20多岁的人生，就好像是走进了服装店试遍所有衣服。\n\n17、人，会在不幸的时候切身感到“不幸', 0, NULL, 2, 11, NULL, '2023-01-16 10:35:59', '2023-01-16 10:15:16', 0);
INSERT INTO `au_article` VALUES (1614808603055104000, 1, NULL, '测试类别,测试', '测试,文章', 1, 1522074993315815424, 1, NULL, 'http://127.0.0.1/aurora-upload/jpg/2023/1/illust_85552528_20220928_080556.jpg', '14、幸福在于得到足够的睡眠。就这样，别无所求。\n\n15、我身体里的火车从来不会错轨，所以允许大雪，风暴，泥石流，和荒谬。\n\n16、20多岁的人生，就好像是走进了服装店试遍所有衣服。\n\n17、人，会在不幸的时候切身感到“不幸”，而只有在回望的时候才会感到“幸福”。说“我现在很幸福”的人是值得被祝贺的。\n', '测试文章3', '14、幸福在于得到足够的睡眠。就这样，别无所求。\n\n15、我身体里的火车从来不会错轨，所以允许大雪，风暴，泥石流，和荒谬。\n\n16、20多岁的人生，就好像是走进了服装店试遍所有衣服。\n\n17、人，会在不幸的时候切身感到“不幸”，而只有在回望', 0, NULL, 1, 4, NULL, '2023-01-16 12:21:59', '2023-01-16 10:15:43', 0);
INSERT INTO `au_article` VALUES (1614808776233721856, 1, NULL, '12,测试类别,测试', '测试,文章', 1, 1522074993315815424, 1, NULL, 'http://127.0.0.1/aurora-upload/jpg/2023/1/illust_89068921_20220928_081050.jpg', '1、一草一木都比人更有资格做人的老师，真实、自然、活在当下。\n\n2、用自己喜欢的方式，让心里高兴，这是一个普通人，对生活最好的致敬。\n\n3、不要悲伤！因为最大的雨来自最黑暗的云。\n\n4、少年的肩应担起草长莺飞和清风明月，女孩的眼应该藏下星辰大海和万丈光芒。\n\n![](https://p3-sign.toutiaoimg.com/pgc-image/R6uH6pg9flM88v~noop.image?_iz=58558&from=article.pc_detail&x-expires=1674440153&x-signature=IlyvELqzNZ5XGaMRCpahm6xkSlA%3D)\n\n5、关系再亲密也要学会将心比心 “任性”是消耗感情的利器 。\n\n6、别人给你的只是人情和依赖，自己独立才是安全感。\n\n7、如果生活如同梦境一样浑浑噩噩，那么做噩梦的人会比做普通梦的人更容易清醒。\n', '28个惊艳好句：句句都是对生活最好的致敬', '1、一草一木都比人更有资格做人的老师，真实、自然、活在当下。\n\n2、用自己喜欢的方式，让心里高兴，这是一个普通人，对生活最好的致敬。\n\n3、不要悲伤！因为最大的雨来自最黑暗的云。\n\n4、少年的肩应担起草长莺飞和清风明月，女孩的眼应该藏下星辰', 0, NULL, 0, NULL, NULL, NULL, '2023-01-16 10:16:24', 0);
INSERT INTO `au_article` VALUES (1614809130488832000, 1, NULL, '测试类别,测试', '测试,文章', 1, 1522074993315815424, 1, NULL, NULL, '<video class=\"\" playsinline=\"true\" x5-playsinline=\"true\" webkit-playsinline=\"true\" mediatype=\"video\" src=\"https://v9-web.toutiaovod.com/d7e41b8ce3d17efb5c55a7f6d338efd3/63c4c312/video/tos/cn/tos-cn-ve-31/d122f2be0f4e488e90b7566d6af431e7/?a=24&ch=0&cr=0&dr=0&net=5&cd=0%7C0%7C0%7C0&cv=1&br=1155&bt=1155&cs=0&ds=3&ft=Tt496MHbxxou~D.O3PM12lop0BanGbOKziYwF_ny81952Nz7t&mime_type=video_mp4&qs=0&rc=ZGU8ZWc2NDtnZDg6MzlkNkBpMztybmc6ZnZlaTMzNGkzM0A0MTQyYi1gNjMxNl80MzNeYSMvcjRhcjRvYS5gLS1kLi9zcw%3D%3D&l=20230116101653C36FBE14C7B395F69B16&btag=30000\"></video>\n\n\n新年伊始，中国第五大淡水湖——巢湖边，首次发现鸟类卷羽鹈鹕。巢湖生物资源调查组鸟类专家虞磊表示，卷羽鹈鹕是中国国家一级保护动物，数量非常少，此次一次性发现三只实属不易，这与近年来生态环境向好有关。\n', '卫星视角看高质量发展新气象｜穿云透雾见证江淮青绿', '<video class=\"\" playsinline=\"true\" x5-playsinline=\"true\" webkit-playsinline=\"true\" mediatype=\"video\" src=\"https://v9-web', 0, NULL, 0, NULL, NULL, NULL, '2023-01-16 10:17:48', 0);
INSERT INTO `au_article` VALUES (1614809370096836608, 1, NULL, '测试类别,测试,今日头条', '测试,文章', 1, 1522074993315815424, 1, NULL, 'http://127.0.0.1/aurora-upload/jpg/2023/1/illust_95911077_20220928_081122.jpg', '经过三年的努力，新冠疫情对于人们的伤害终于被降到了很低的状态，而目前大多数地区的人都已经感染过新冠并“阳康”了。\n\n在拥有了较强的抵抗能力之后，即将到来的春节就成了最让人感到喜庆的事情，毕竟已经三年了，过去三年都没有好好过年了，现在疫情放开了，终于可以恢复往年的热闹了。\n\n![](https://p3-sign.toutiaoimg.com/tos-cn-i-qvj2lq49k0/babafeae8250437b82bab2235630140b~noop.image?_iz=58558&from=article.pc_detail&x-expires=1674440298&x-signature=5Lr8F0apcKPvMM6IP5MFgCnLBnE%3D)\n\n孩子们自然对于新年的期盼心情也会更加强烈，因为在新年到来的时候，孩子们将会受到长辈们的祝福，收到很多“压岁钱”。\n\n走亲访友给压岁钱，本是一件喜事，但是有时候我们给了压岁钱给的不对，反倒会遭到对方的抱怨，这到底是为何呢？\n\n**过年，给压岁钱给不对很尴尬，花了钱还得罪人**\n\n过年的时候，小风的妈妈带着小风，来到了本家亲戚家串门，而这位本家亲戚以前一直在外地工作，刚回到家乡，看到小风来串门，自然是非常欢迎，并且还给了小风300块钱的压岁钱。\n\n![](https://p3-sign.toutiaoimg.com/tos-cn-i-qvj2lq49k0/8f27c77f2c394215b8b37ec72bf9c8c5~noop.image?_iz=58558&from=article.pc_detail&x-expires=1674440298&x-signature=sYIDJEfbr%2F7%2FzQeOZvsoCuruFVs%3D)\n\n但是，小风的妈妈**看到亲戚给了孩子300块的压岁钱，脸色立马就变得不好了**，没说两句话就告辞了，亲戚也明显地感到了不对，但是却不知道为什么。\n\n原来，小风老家有个习俗，就是**逢喜事的话讲究“双”，逢丧事的话讲究“单”**。\n\n一般过年过节都是双份儿礼、双数开头的份子钱，而走丧的时候才会用单数。\n\n而亲戚给小风的压岁钱，就是“3”开头的，不仅犯了单双忌讳，而且还犯了另一个谐音忌讳，即**“3同散”**，这让小风的妈妈非常的不舒服，不知道亲戚是有意为之，还是真的不知道。\n', '过年，孩子压岁钱给多少合适？按3个“万能数”给，不会得罪人', '经过三年的努力，新冠疫情对于人们的伤害终于被降到了很低的状态，而目前大多数地区的人都已经感染过新冠并“阳康”了。\n\n在拥有了较强的抵抗能力之后，即将到来的春节就成了最让人感到喜庆的事情，毕竟已经三年了，过去三年都没有好好过年了，现在疫情放开', 0, NULL, 1, 9, NULL, '2023-01-16 12:33:39', '2023-01-16 10:18:45', 0);
INSERT INTO `au_article` VALUES (1614809508643086336, 1, NULL, '测试类别,测试', '测试,文章', 1, 1522074993315815424, 1, NULL, 'http://127.0.0.1/aurora-upload/jpg/2023/1/illust_98377233_20220729_132043.jpg', '**大家好，我是大强。疫情开放之后，身边很多的朋友都纷纷中招，基本也都跟网上说的的症状相吻合，发烧、头痛、咳嗽，大家好像都有个共同之处就是咳嗽，不过听专家说咳嗽是转阴之后的正常现象，有人咳嗽两天就痊愈了，但是有的人十天半个月还是咳嗽个不停。**\n\n![](https://p3-sign.toutiaoimg.com/tos-cn-i-qvj2lq49k0/09d83af71fd84f7292bc641b451c7f23~noop.image?_iz=58558&from=article.pc_detail&x-expires=1674440335&x-signature=TNxDyNAxFzxbobSWZJD966Royv0%3D)\n\n**最近大家都被咳嗽折磨了一遍，无论是出门见朋友，还是隔壁邻居，大家都咳嗽不停，聊天说话没两分钟，就要咳嗽几下，大家也不想咳嗽，但是嗓子就是干痒没办法。**\n\n**今天就给大家推荐2道食物，喜欢的朋友可以在家试一下。**\n\n**推荐食谱一：枇杷膏**\n\n**刚买的一斤新鲜枇杷，放水里泡一会儿，抛去枇杷表面的一层绒毛，再搓洗干净，削皮。拿一个大一点的碗，**\n\n![](https://p3-sign.toutiaoimg.com/tos-cn-i-qvj2lq49k0/077caeef4fc84d689faadddad03f3f2f~noop.image?_iz=58558&from=article.pc_detail&x-expires=1674440335&x-signature=ULVYghinU%2FghSAy6PRPgdNZSqaQ%3D)\n\n**放一到两勺的盐，琵琶头部和尾部用小刀削掉，取和果肉放盐水里泡三到四分钟。枇杷剥完后容易氧化变色，放盐水里泡会儿可以预防变色，再捞出来沥干下水分。**\n', '“止咳勇将”终于找到，不是雪梨，隔天喝1次，润肺止咳嗓子不痒', '**大家好，我是大强。疫情开放之后，身边很多的朋友都纷纷中招，基本也都跟网上说的的症状相吻合，发烧、头痛、咳嗽，大家好像都有个共同之处就是咳嗽，不过听专家说咳嗽是转阴之后的正常现象，有人咳嗽两天就痊愈了，但是有的人十天半个月还是咳嗽个不停。', 0, NULL, 1, 16, NULL, '2023-01-16 12:33:30', '2023-01-16 10:19:19', 0);
INSERT INTO `au_article` VALUES (1614809719423639552, 1, NULL, '测试类别,测试', '文章,测试', 1, 1522074993315815424, 1, NULL, 'http://127.0.0.1/aurora-upload/jpg/2023/1/illust_97973029_20220928_080351.jpg', '在1月15日的江西省第十四届人民代表大会第一次会议上，**此前担任江西省国资委主任的卢小青当选江西省副省长。**\n\n![](https://p3-sign.toutiaoimg.com/tos-cn-i-tjoges91tu/TT1RixsHgEPQeW~noop.image?_iz=58558&from=article.pc_detail&x-expires=1674440369&x-signature=1%2FU0QbHm87%2BifYahX7oN39MUeVg%3D)\n\n卢小青，女，汉族，1968年9月生，中共党员，大学本科学历，法学硕士，高级管理人员工商管理硕士。\n\n![](https://p3-sign.toutiaoimg.com/tos-cn-i-tjoges91tu/TT1RiyWEScbBWe~noop.image?_iz=58558&from=article.pc_detail&x-expires=1674440369&x-signature=TjzNTOI6C8epa1D2cJmuticgcUE%3D)\n\n卢小青历任江中制药厂办公室副主任、主任；江中妇科药物研究中心主任；江中制药(集团)有限责任公司综合办公室副主任、人力资源部部长、办公室主任、党委组织部部长、人力资源总监、党委委员、党委副书记、副总经理、副董事长、总经理；华润江中制药集团有限责任公司党委副书记、党委书记、总经理、董事长等，2022年5月在江中制药集团的党委书记、董事长岗位上调任江西省国资委主任。**曾获2021年度全国三八红旗手称号。**\n', '她，升任江西省副省长', '在1月15日的江西省第十四届人民代表大会第一次会议上，**此前担任江西省国资委主任的卢小青当选江西省副省长。**\n\n![](https://p3-sign.toutiaoimg.com/tos-cn-i-tjoges91tu/TT1Rixs', 0, NULL, 3, 41, NULL, '2023-01-16 11:27:27', '2023-01-16 10:20:09', 0);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_bulletin
-- ----------------------------
INSERT INTO `au_bulletin` VALUES (1524397958342647808, NULL, '这是公告的内容', '2022-05-11 22:36:09', NULL, 1522074993315815424, 1, 0, NULL, 0);
INSERT INTO `au_bulletin` VALUES (1524397965154197504, NULL, '这是公告的内容', '2022-05-11 22:36:11', NULL, 1522074993315815424, 1, 0, NULL, 0);
INSERT INTO `au_bulletin` VALUES (1524397967461064704, NULL, '这是公告的内容', '2022-05-11 22:36:12', NULL, 1522074993315815424, 1, 0, NULL, 0);
INSERT INTO `au_bulletin` VALUES (1524397969767931904, NULL, '这是公告的内容', '2022-05-11 22:36:12', NULL, 1522074993315815424, 1, 0, NULL, 0);
INSERT INTO `au_bulletin` VALUES (1524398089817300992, 'asdfasdf', '这是公告的内容', '2022-05-11 22:36:41', NULL, 1522074993315815424, 1, 0, NULL, 0);
INSERT INTO `au_bulletin` VALUES (1524398157244932096, 'asdfasdf', '这是公告的内容', '2022-05-11 22:36:57', NULL, 1522074993315815424, 0, 0, NULL, 0);
INSERT INTO `au_bulletin` VALUES (1524398240799662080, 'asdfasdf', '这是公告的内容', '2022-05-11 22:37:17', NULL, 1522074993315815424, 0, 1, NULL, 0);
INSERT INTO `au_bulletin` VALUES (1524401652748525568, 'asdfasdf', '这是公告的内容', '2022-05-11 22:51:07', NULL, 1522074993315815424, 0, 0, NULL, 0);
INSERT INTO `au_bulletin` VALUES (1524405128467587072, 'asdfasdf', '这是公告的内容', '2022-05-11 23:04:39', NULL, 1522074993315815424, 0, 0, NULL, 0);
INSERT INTO `au_bulletin` VALUES (1524405223913168896, 'q310aiyaiiylba7', '2uwbva890gfxwzx', '2022-05-18 16:23:16', '2022-05-18 16:23:16', 1522074993315815424, 1, 0, NULL, 1);
INSERT INTO `au_bulletin` VALUES (1526840562380447744, 'xzoe5ahdy6k6dn2', '2n71xrcbzb23kvf', '2022-05-18 16:22:12', NULL, 1522074993315815424, 1, 0, NULL, 0);

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
  UNIQUE INDEX `unique_title`(`title` ASC) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE,
  INDEX `union_category`(`uid` ASC, `title` ASC, `user_uid` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_category
-- ----------------------------
INSERT INTO `au_category` VALUES (18, '编程', NULL, '2022-06-05 00:31:41', '2022-12-15 00:40:29', NULL, 1, 1522074993315815424);
INSERT INTO `au_category` VALUES (19, '代码', NULL, '2022-06-05 00:31:41', '2022-12-15 00:44:23', NULL, 1, 1522074993315815424);
INSERT INTO `au_category` VALUES (20, '好友', NULL, '2022-06-05 09:03:20', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` VALUES (21, '大佬', NULL, '2022-06-05 09:03:30', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` VALUES (22, '代码编程', NULL, '2022-06-05 14:02:46', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` VALUES (23, 'sdf', NULL, '2022-12-15 13:12:53', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` VALUES (24, 'asdf', NULL, '2022-12-15 13:12:53', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` VALUES (25, '水电费', '阿斯顿发阿斯顿发', '2022-12-15 13:15:05', NULL, 'http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-q22jv7.png', 0, 1522074993315815424);
INSERT INTO `au_category` VALUES (26, '今日头条', NULL, '2023-01-15 20:37:56', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` VALUES (27, '测试文章', NULL, '2023-01-15 20:37:56', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` VALUES (28, '测试类别', NULL, '2023-01-16 10:14:41', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` VALUES (29, '测试', NULL, '2023-01-16 10:14:41', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_category` VALUES (30, '12', NULL, '2023-01-16 10:16:24', NULL, NULL, 0, 1522074993315815424);

-- ----------------------------
-- Table structure for au_link
-- ----------------------------
DROP TABLE IF EXISTS `au_link`;
CREATE TABLE `au_link`  (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_uid` bigint NULL DEFAULT NULL COMMENT '此条友情链接是哪个用户的',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此条友情链接属于哪个分类',
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
  UNIQUE INDEX `unique_linkUrl_index`(`link_url` ASC) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `categoryName_index`(`category_name` ASC) USING BTREE,
  INDEX `union_link_index`(`uid` ASC, `user_uid` ASC, `link_url` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_link
-- ----------------------------
INSERT INTO `au_link` VALUES (1533376946612215808, 1522074993315815424, '代码', 'https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png', 'https://baidu.com', '青衫烟雨客', NULL, NULL, 0, '2291308094@qq.com', NULL, '2022-06-05 17:15:27', NULL);
INSERT INTO `au_link` VALUES (1533381819781095424, 1522074993315815424, '编程', 'asdflqwer,masdf', 'asdfliwuqerjhbsdf', 'asdfukasdf', NULL, NULL, 0, '2291308094@qq.com', NULL, '2022-06-05 17:34:49', NULL);
INSERT INTO `au_link` VALUES (1533382069581258752, 1522074993315815424, '代码', 'asdf', 'https://jindon.com', 'asdfkjasdf', NULL, NULL, 0, '2291345@qq.com', NULL, '2022-06-05 17:35:48', NULL);
INSERT INTO `au_link` VALUES (1533383863292141568, 1522074993315815424, '代码', 'asdfkjwermsndf', 'asdfkjhawreasdf', 'asdf', NULL, NULL, 0, '22asekjf@qq.com', NULL, '2022-06-05 17:42:57', NULL);
INSERT INTO `au_link` VALUES (1533384660415422464, 1522074993315815424, '大佬', 'https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png', 'https://www.baidu.com', '这是一条测试评论', NULL, NULL, 0, '2604400276@qq.com', NULL, '2022-06-05 17:46:06', NULL);
INSERT INTO `au_link` VALUES (1603257956468006912, 1522074993315815424, '好友', 'http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-q22jv7.png', 'vvvray.xcye.xyz', '测试站点', NULL, NULL, 1, 'xcyeye@gmail.com', 2291408094, '2022-12-15 13:17:38', '2022-12-15 13:32:38');

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
  UNIQUE INDEX `unique_title_index`(`title` ASC) USING BTREE,
  INDEX `userUid_index`(`user_uid` ASC) USING BTREE,
  INDEX `createTime_index`(`create_time` ASC) USING BTREE,
  INDEX `union_tag_index`(`uid` ASC, `title` ASC, `user_uid` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_tag
-- ----------------------------
INSERT INTO `au_tag` VALUES (11, 'java', NULL, '2022-06-04 23:40:10', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_tag` VALUES (12, 'spring', NULL, '2022-06-05 00:31:41', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_tag` VALUES (13, 'sprng boot', NULL, '2022-06-05 00:31:41', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_tag` VALUES (14, 'sprng bootjava', NULL, '2022-06-05 14:02:46', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_tag` VALUES (15, '今日头条', NULL, '2023-01-15 20:37:56', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_tag` VALUES (16, '明星', NULL, '2023-01-15 20:37:56', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_tag` VALUES (17, '王思聪', NULL, '2023-01-15 20:38:58', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_tag` VALUES (18, '测试', NULL, '2023-01-16 10:14:41', NULL, NULL, 0, 1522074993315815424);
INSERT INTO `au_tag` VALUES (19, '文章', NULL, '2023-01-16 10:14:41', NULL, NULL, 0, 1522074993315815424);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of au_talk
-- ----------------------------
INSERT INTO `au_talk` VALUES (1614661800255037440, 1522074993315815424, 1, NULL, '2023-01-17 00:07:10', '2023-01-16 00:32:26', '人生低潮时，就多为自己做点事吧，读书、跑步、旅行，越是艰难的时刻，越要自己撑自己。\n', '说说1', 1, 1, '1614661719772020736,1614661719772020739,1614661719772020738,1614661719772020740,1614661719772020737', 0);
INSERT INTO `au_talk` VALUES (1614662045936394240, 1522074993315815424, 1, NULL, '2023-01-17 00:07:01', '2023-01-16 00:33:25', '我要你静心学习那份等待时机成熟的情绪，也要你一定保有这份等待之外的努力和坚持。\n', '说说2', 1, 1, '1614662025998155776,1614662026027515904,1614662025964601344,1614662026186899456,1614662026128179200', 0);
INSERT INTO `au_talk` VALUES (1614847608245198848, 1522074993315815424, 1, '1615023579966697472', '2023-01-17 00:02:16', '2023-01-16 12:50:42', '测试说说内容1\n', '测试说说', 1, 1, '1614847512297783298,1614847512297783299,1614847512297783296,1614847512297783297', 0);
INSERT INTO `au_talk` VALUES (1614848183963754496, 1522074993315815424, 1, '1615023864856408064,1615023378040320000', '2023-01-16 23:55:46', '2023-01-16 12:52:59', '说书内容\n', '测试说说45', 1, 1, 'http://127.0.0.1/aurora-upload/png/2023/1/illust_88479853_20220906_081459.png,http://127.0.0.1/aurora-upload/png/2023/1/illust_88380383_20220717_200036.png,http://127.0.0.1/aurora-upload/png/2023/1/illust_88911364_20220928_080456.png,http://127.0.0.1/aurora-upload/png/2023/1/illust_88927530_20220928_081118.png,http://127.0.0.1/aurora-upload/jpg/2023/1/illust_88314013_20220928_080426.jpg', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1163 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
