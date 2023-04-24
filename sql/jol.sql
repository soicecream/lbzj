/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : jol

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 24/04/2023 23:08:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for privilege
-- ----------------------------
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege`  (
  `user_id` char(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `rightstr` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `valuestr` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'true',
  `defunct` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N',
  INDEX `user_id_index`(`user_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of privilege
-- ----------------------------
INSERT INTO `privilege` VALUES ('admin', 'administrator', 'true', 'N');

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`  (
  `problem_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '题目编号，主键',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标题',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '题目描述',
  `input` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '输入说明',
  `output` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '输出说明',
  `sample_input` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '样例输入',
  `sample_output` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '样例输出',
  `examples` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '样例',
  `spj` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '是否为特别题目',
  `hint` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '提示',
  `source` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '来源',
  `in_date` datetime(0) NULL DEFAULT NULL COMMENT '加入时间',
  `time_limit` decimal(10, 3) NOT NULL COMMENT '时间限制',
  `memory_limit` int(0) NOT NULL DEFAULT 0 COMMENT '空间限制',
  `defunct` int(0) NOT NULL DEFAULT 0 COMMENT '状态',
  `accepted` int(0) NOT NULL DEFAULT 0 COMMENT '解决数',
  `submit` int(0) NOT NULL DEFAULT 0 COMMENT '提交数',
  `solved` int(0) NOT NULL DEFAULT 0 COMMENT '解答（未用）',
  `degree` int(0) NOT NULL DEFAULT 1 COMMENT '难度',
  `ordernum` int(0) NULL DEFAULT 1 COMMENT '顺序',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '作者',
  `video_is_upload` int(0) NOT NULL DEFAULT 0 COMMENT '视频是否上传 (0:未上传 1:已上传)',
  `video_defunct` int(0) NOT NULL DEFAULT 0 COMMENT '视频是否可用 (0:禁用 1:可用)',
  PRIMARY KEY (`problem_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1016 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of problem
-- ----------------------------
INSERT INTO `problem` VALUES (1000, '输出Hello Programming!', '打印输出Hello Programming!', '无', '输出Hello Programming!', '无', 'Hello Programming!', '<input></input><output>Hello Programming!</output>', '0', '', NULL, '2021-12-26 17:00:00', 1.000, 128, 1, 3, 1, 0, 1, 1, 'admin', 1, 1);
INSERT INTO `problem` VALUES (1001, '输出两个句子', '按下面格式输出以下句子:\r\nThe big bug flies away,\r\nThe big bat cries.', '无', 'The big bug flies away,\r\nThe big bat cries.', '无', 'The big bug flies away,\r\nThe big bat cries.', '<input></input><output>The big bug flies away,\nThe big bat cries.</output>', '0', '', '基础输出', '2021-12-26 17:00:00', 1.000, 128, 1, 1, 1, 0, 2, 1, 'admin', 1, 0);
INSERT INTO `problem` VALUES (1002, 'a+b=?', '给定$a$和$b$，求出$a+b$的和', '输入两个整数 $a,b$ ( $0<=a,b<=1^{9}$ )', '输出 $a+b$ 的和', '1 2', '3', '<input>1 1</input><output>2</output>', '0', NULL, '基础输入、输出', '2021-12-26 17:00:00', 1.000, 128, 1, 2, 1, 0, 4, 1, 'admin', 0, 0);
INSERT INTO `problem` VALUES (1003, '背诗1', '过年了，喜欢诗词的老爸对彤彤说，背出《山村咏怀》这首诗中的每一个数字，就给她对应数字张数的百元钞票作为红包，假设彤彤目前的红包里的百元钞票有n张，她背对了第一个数字，试编一个程序，输入n的值，输出背对第一个数字时，彤彤红包中的百元钞票张数。\r\n  山村咏怀\r\n       邵雍\r\n一去二三里，\r\n烟村四五家。\r\n亭台六七座，\r\n八九十枝花。', '输入一个整数n，代表目前红包里的百元钞票张数。', '在一行输出一个整数，表示背对第一个数字后获得百元钞票张数。', '34', '35', '<input>1</input><output>1</output>', '0', NULL, '基础变量', '2021-12-26 17:00:00', 1.000, 128, 1, 1, 1, 0, 5, 1, 'admin', 0, 0);
INSERT INTO `problem` VALUES (1015, 'a+b', '输入a和b, 问 $a+b$ 的和为多少', '输入两个整数 a和b $(0 <= a,b <= 1^{9})$', '请输出a+b的和', NULL, NULL, '<input>1 1</input><output>2</output>', '0', 'a+b啊', NULL, NULL, 1000.000, 256, 1, 0, 0, 0, 0, 1, NULL, 0, 0);

-- ----------------------------
-- Table structure for problem_tags
-- ----------------------------
DROP TABLE IF EXISTS `problem_tags`;
CREATE TABLE `problem_tags`  (
  `problem_id` int(0) NOT NULL COMMENT '问题id',
  `tags_id` int(0) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`problem_id`, `tags_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of problem_tags
-- ----------------------------
INSERT INTO `problem_tags` VALUES (1002, 1);
INSERT INTO `problem_tags` VALUES (1002, 2);

-- ----------------------------
-- Table structure for runtimeinfo
-- ----------------------------
DROP TABLE IF EXISTS `runtimeinfo`;
CREATE TABLE `runtimeinfo`  (
  `solution_id` int(0) NOT NULL DEFAULT 0,
  `error` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  PRIMARY KEY (`solution_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of runtimeinfo
-- ----------------------------
INSERT INTO `runtimeinfo` VALUES (1001, 'time_space_table:\n/1000/1.in:AC mem=2184k time=2ms\n\n');
INSERT INTO `runtimeinfo` VALUES (1002, 'time_space_table:\n/1001/1.in:AC mem=2184k time=0ms\n\n');
INSERT INTO `runtimeinfo` VALUES (1003, 'time_space_table:\n/1004/1.in:AC mem=2184k time=2ms\n\n');
INSERT INTO `runtimeinfo` VALUES (1004, 'time_space_table:\n/1002/1.in:AC mem=2184k time=2ms\n\n');
INSERT INTO `runtimeinfo` VALUES (1005, 'time_space_table:\n/1003/1.in:AC mem=2184k time=2ms\n\n');
INSERT INTO `runtimeinfo` VALUES (1006, 'time_space_table:\n/1000/1.in:AC mem=2184k time=2ms\n\n');
INSERT INTO `runtimeinfo` VALUES (1008, '========[1.out]=========\nExpected						      |	Yours\nThe big bug flies away,					      <\nThe big bat cries.					      <\n\n==============================\ntime_space_table:\n/1001/1.in:WA mem=2184k time=2ms\n\n');
INSERT INTO `runtimeinfo` VALUES (1010, '========[1.out]=========\nExpected						      |	Yours\nThe big bug flies away,					      /	Hello Programming!\nThe big bat cries.					      <\n\n==============================\ntime_space_table:\n/1001/1.in:WA mem=2184k time=2ms\n\n');
INSERT INTO `runtimeinfo` VALUES (1012, 'time_space_table:\n/1000/1.in:AC mem=2184k time=2ms\n\n');
INSERT INTO `runtimeinfo` VALUES (1013, 'time_space_table:\n/1002/1.in:AC mem=2184k time=2ms\n\n');
INSERT INTO `runtimeinfo` VALUES (1016, '========[test.out]=========\nExpected						      |	Yours\nHello Programming!					      |	The big bug flies away, The big bat cries.\n==============================\ntime_space_table:\n/1000/test.in:WA mem=2176k time=4ms\n\n');
INSERT INTO `runtimeinfo` VALUES (1019, '========[test.out]=========\nExpected						      |	Yours\nHello Programming!					      |	The big bug flies away, The big bat cries.\n==============================\ntime_space_table:\n/1000/test.in:WA mem=2176k time=3ms\n\n');

-- ----------------------------
-- Table structure for solution
-- ----------------------------
DROP TABLE IF EXISTS `solution`;
CREATE TABLE `solution`  (
  `solution_id` int(0) NOT NULL,
  `problem_id` int(0) NOT NULL DEFAULT 0,
  `user_id` char(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `nick` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `time` int(0) NOT NULL DEFAULT 0,
  `memory` int(0) NOT NULL DEFAULT 0,
  `in_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `result` smallint(0) NOT NULL DEFAULT 0,
  `language` int(0) UNSIGNED NOT NULL DEFAULT 0,
  `ip` char(46) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `contest_id` int(0) NULL DEFAULT 0,
  `valid` tinyint(0) NOT NULL DEFAULT 1,
  `num` tinyint(0) NOT NULL DEFAULT -1,
  `code_length` int(0) NOT NULL DEFAULT 0,
  `judgetime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `pass_rate` decimal(3, 2) UNSIGNED NOT NULL,
  `lint_error` int(0) UNSIGNED NOT NULL DEFAULT 0,
  `judger` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'LOCAL',
  `course_id` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`solution_id`) USING BTREE,
  INDEX `uid`(`user_id`) USING BTREE,
  INDEX `pid`(`problem_id`) USING BTREE,
  INDEX `res`(`result`) USING BTREE,
  INDEX `cid`(`contest_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1007 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of solution
-- ----------------------------
INSERT INTO `solution` VALUES (1001, 1000, 'admin', 'admin', 2, 2184, '2022-09-10 17:37:20', 4, 1, '127.0.0.1', 0, 1, -1, 97, '2022-09-10 17:37:22', 1.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1002, 1001, 'admin', 'admin', 0, 2184, '2022-09-10 17:42:31', 4, 1, '127.0.0.1', 0, 1, -1, 141, '2022-09-10 17:42:32', 1.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1003, 1004, 'admin', 'admin', 2, 2184, '2022-09-10 17:59:11', 4, 1, '127.0.0.1', 0, 1, -1, 149, '2022-09-10 17:59:12', 1.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1004, 1002, 'admin', 'admin', 2, 2184, '2022-09-10 17:59:57', 4, 1, '127.0.0.1', 0, 1, -1, 116, '2022-09-10 17:59:58', 1.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1005, 1003, 'admin', 'admin', 2, 2184, '2022-09-10 18:00:16', 4, 1, '127.0.0.1', 0, 1, -1, 113, '2022-09-10 18:00:17', 1.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1006, 1000, 'admin', 'admin', 2, 2184, '2016-05-13 19:24:00', 4, 1, '127.0.0.1', 0, 1, -1, 0, '2022-09-11 13:28:47', 1.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1008, 1001, 'zwj', 'zwj', 2, 2184, '2016-05-13 19:24:00', 6, 1, '', 0, 1, 1, 62, '2022-09-11 15:12:06', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1009, 1001, 'zwj', 'zwj', 0, 0, '2022-09-11 15:14:43', 11, 1, '', 0, 1, 1, 95, '2022-09-11 15:14:44', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1010, 1001, 'zwj', 'zwj', 2, 2184, '2022-09-11 15:15:39', 6, 1, '', 0, 1, 1, 89, '2022-09-11 15:15:40', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1011, 1000, 'zwj', 'zwj', 0, 0, '2022-09-11 15:19:58', 11, 1, '', 0, 1, 1, 95, '2022-09-11 15:19:59', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1012, 1000, 'zwj', 'zwj', 2, 2184, '2022-09-11 18:15:26', 4, 1, '', 0, 1, 1, 97, '2022-09-11 18:15:28', 1.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1013, 1002, 'zwj', 'zwj', 2, 2184, '2022-09-11 19:44:51', 4, 1, '', 0, 1, 1, 103, '2022-09-11 19:44:53', 1.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1014, 1000, 'zwj', 'zwj', 0, 0, '2022-09-19 10:43:43', 11, 1, '', 0, 1, 1, 0, '2022-12-04 18:10:32', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1015, 1002, 'zwj', 'zwj', 0, 0, '2022-09-21 11:13:19', 6, 1, '', 1, 1, 1, 1, '2022-12-04 18:11:44', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1016, 1000, 'admin', 'admin', 4, 2176, '2022-12-29 14:14:51', 6, 1, '', 1, 1, 1, 128, '2022-12-29 14:15:02', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1017, 1001, 'admin', 'admin', 0, 0, '2022-12-29 14:22:04', 3, 1, '', 1, 1, 1, 128, '2022-12-29 14:22:05', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1018, 1003, 'admin', 'admin', 0, 0, '2022-12-30 09:07:48', 3, 1, '', 1, 1, 1, 128, '2022-12-30 09:07:50', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1019, 1000, 'admin', 'admin', 3, 2176, '2022-12-30 09:08:01', 4, 1, '', 1, 1, 1, 128, '2022-12-30 09:08:03', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1020, 1000, 'admin', 'admin', 0, 0, '2023-02-13 17:58:17', 4, 1, '', 0, 1, 1, 128, '2023-02-13 17:58:17', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1021, 1000, 'admin', 'admin', 0, 0, '2023-03-11 17:30:18', 4, 1, '', 0, 1, 1, 128, '2023-03-11 17:30:18', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1022, 1000, 'admin', 'admin', 0, 0, '2023-03-11 17:30:24', 4, 1, '', 0, 1, 1, 128, '2023-03-11 17:30:24', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1023, 1000, 'wang', 'wang', 0, 0, '2023-04-23 10:10:26', 4, 1, '', 0, 1, 1, 128, '2023-04-23 10:10:26', 0.00, 0, 'admin', 0);
INSERT INTO `solution` VALUES (1024, 1001, 'admin', 'admin', 0, 0, '2023-04-23 10:47:03', 0, 1, '', 0, 1, 1, 128, '2023-04-23 10:47:03', 0.00, 0, 'admin', 0);

-- ----------------------------
-- Table structure for source_code
-- ----------------------------
DROP TABLE IF EXISTS `source_code`;
CREATE TABLE `source_code`  (
  `solution_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '状态ID',
  `source` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`solution_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1025 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of source_code
-- ----------------------------
INSERT INTO `source_code` VALUES (1001, '#include <bits/stdc++.h>\r\nusing namespace std;\r\nint main()\r\n{\r\n    cout<<\"Hello Programming!\";\r\n}');
INSERT INTO `source_code` VALUES (1002, '#include <bits/stdc++.h>\r\nusing namespace std;\r\nint main()\r\n{\r\n    cout<<\"The big bug flies away,\"<<endl;\r\n    cout<<\"The big bat cries.\";\r\n}');
INSERT INTO `source_code` VALUES (1003, '#include <bits/stdc++.h>\r\nusing namespace std;\r\nint main()\r\n{\r\n    int n;\r\n    cin>>n;\r\n    cout<<n+1<<endl;\r\n    cout<<n+3<<endl;\r\n    cout<<n+6;\r\n}');
INSERT INTO `source_code` VALUES (1004, '#include <bits/stdc++.h>\r\nusing namespace std;\r\nint main()\r\n{\r\n    int a,b;\r\n    cin>>a>>b;\r\n    cout<<a+b<<endl;\r\n}');
INSERT INTO `source_code` VALUES (1005, '#include <bits/stdc++.h>\r\nusing namespace std;\r\nint main()\r\n{\r\n    int a,b;\r\n    cin>>a;\r\n    cout<<a+1<<endl;\r\n}');
INSERT INTO `source_code` VALUES (1006, '#include <bits/stdc++.h>\r\nusing namespace std;\r\nint main()\r\n{\r\n    cout<<\"Hello Programming!\";\r\n}');
INSERT INTO `source_code` VALUES (1007, '#include<bits/stdc++.h>\nusing namespace std;\nint main()\n{\n  cout<<\"Hello Programming!<<endl;\n}');
INSERT INTO `source_code` VALUES (1008, '#include<bits/stdc++.h>\nusing namespace std;\nint main()\n{\n  \n}');
INSERT INTO `source_code` VALUES (1009, '#include <bits/stdc++.h>\nusing namespace std;\nint main()\n{\n  cout<<\"Hello Programming!<<endl;\n}');
INSERT INTO `source_code` VALUES (1010, '#include <bits/stdc++.h>\nusing namespace std;\nint main()\n{\n cout<<\"Hello Programming!\";\n}');
INSERT INTO `source_code` VALUES (1011, '#include <bits/stdc++.h>\nusing namespace std;\nint main()\n{\n  cout<<\"Hello Programming!<<endl;\n}');
INSERT INTO `source_code` VALUES (1012, '#include <bits/stdc++.h>\nusing namespace std;\nint main()\n{\n  cout<<\"Hello Programming!\"<<endl;\n}\n');
INSERT INTO `source_code` VALUES (1013, '#include <bits/stdc++.h>\nusing namespace std;\nint main()\n{\n  int a,b;\n  cin>>a>>b;\n  cout<<a+b<<endl;\n}');
INSERT INTO `source_code` VALUES (1014, '');
INSERT INTO `source_code` VALUES (1015, '1');
INSERT INTO `source_code` VALUES (1016, '#include <iostream>\n\nusing namespace std;\n\nint main(){\n    cout << \"The big bug flies away, The big bat cries.\";\n    return 0;\n}');
INSERT INTO `source_code` VALUES (1017, '#include <iostream>\n\nusing namespace std;\n\nint main(){\n    cout << \"The big bug flies away, The big bat cries.\";\n    return 0;\n}');
INSERT INTO `source_code` VALUES (1018, '#include <iostream>\n\nusing namespace std;\n\nint main(){\n    cout << \"The big bug flies away, The big bat cries.\";\n    return 0;\n}');
INSERT INTO `source_code` VALUES (1019, '#include <iostream>\n\nusing namespace std;\n\nint main(){\n    cout << \"The big bug flies away, The big bat cries.\";\n    return 0;\n}');
INSERT INTO `source_code` VALUES (1020, '#include <iostream>\n\nusing namespace std;\n\nint main(){\n    cout << \"The big bug flies away, The big bat cries.\";\n    return 0;\n}');
INSERT INTO `source_code` VALUES (1021, '#include <iostream>\n\nusing namespace std;\n\nint main(){\n    cout << \"The big bug flies away, The big bat cries.\";\n    return 0;\n}');
INSERT INTO `source_code` VALUES (1022, '#include <iostream>\n\nusing namespace std;\n\nint main(){\n    cout << \"The big bug flies away, The big bat cries.\";\n    return 0;\n}');
INSERT INTO `source_code` VALUES (1023, '#include <iostream>\n\nusing namespace std;\n\nint main(){\n    cout << \"The big bug flies away, The big bat cries.\";\n    return 0;\n}');
INSERT INTO `source_code` VALUES (1024, '#include <iostream>\n\nusing namespace std;\n\nint main(){\n    cout << \"The big bug flies away, The big bat cries.\";\n    return 0;\n}');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(0) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(0) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1017 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '竞赛管理', 0, 0, '/admin', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'el-icon-notebook-2', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (2, '系统管理', 0, 0, '/admin', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'el-icon-setting', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (3, '问题管理', 0, 0, '/admin', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'el-icon-document', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (100, '竞赛列表', 1, 0, '/admin/work', 'admin/work/work', NULL, 1, 0, 'C', '0', '0', 'work:list', 'el-icon-notebook-1', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (101, '用户管理', 2, 0, '/admin/user', 'admin/user/user', NULL, 1, 0, 'C', '0', '0', 'user:list', 'el-icon-user', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (102, '角色管理', 2, 0, '/admin/role', 'admin/role/rolelist', NULL, 1, 0, 'C', '0', '0', 'role:list', 'el-icon-chat-round', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (103, '用户权限', 2, 0, '/admin/userRole', 'admin/user/userRole', NULL, 1, 0, 'C', '0', '0', 'ur:list', 'el-icon-set-up', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (104, '添加问题', 3, 0, '/admin/pro/create', 'admin/problem/problem', NULL, 1, 0, 'C', '0', '0', NULL, '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (105, '问题列表', 3, 0, '/admin/prolist', 'admin/problem/list', NULL, 1, 0, 'C', '0', '0', NULL, '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (106, '标签', 3, 0, '/admin/tag', 'admin/tag/list', NULL, 1, 0, 'C', '0', '0', NULL, '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1000, '添加角色', 102, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'role:add', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1001, '删除角色', 102, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'role:del', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '查询角色', 102, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'role:sel', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '修改角色', 102, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'role:up', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '查询用户', 101, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'user:sel', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '添加用户', 101, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'user:add', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '修改用户', 101, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'user:up', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '删除用户', 101, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'user:del', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '修改竞赛', 100, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'work:up', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '添加竞赛', 100, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'work:add', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '删除竞赛', 100, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'work:del', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '添加权限', 103, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'ur:add', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '删除权限', 103, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'ur:del', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '修改权限', 103, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'ur:up', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '添加问题', 104, 0, '', NULL, NULL, 1, 0, '', '0', '0', 'pro:add', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '修改问题', 3, 0, '/admin/pro/edit/:problemId', 'admin/problem/problem', NULL, 1, 0, 'C', '0', '0', NULL, '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '删除问题', 105, 0, '', NULL, NULL, 1, 0, '', '0', '0', NULL, '#', '', NULL, '', NULL, '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `creator_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updator_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `state` int(0) NULL DEFAULT 0 COMMENT '状态(0:禁用, 1:启用)',
  `is_set_top` int(0) NULL DEFAULT 0 COMMENT '置顶(0:禁用, 1:启用)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '常用oj地址', '常用oj地址：\n[牛客](https://ac.nowcoder.com/)\n[洛谷](https://www.luogu.com.cn/)', '1', '2023-02-19 10:22:09', '1', '2023-02-22 22:46:35', 1, 1);
INSERT INTO `sys_notice` VALUES (6, '1', '1', '1', '2023-02-19 10:22:11', '1', '2023-02-19 10:22:09', 1, 0);
INSERT INTO `sys_notice` VALUES (7, '2', '2', '2', '2023-02-19 10:22:08', '1', '2023-02-23 21:01:33', 1, 1);
INSERT INTO `sys_notice` VALUES (8, '3', '3', '1', '2023-02-19 10:22:13', '1', '2023-02-19 10:22:09', 1, 0);

-- ----------------------------
-- Table structure for sys_problem_case
-- ----------------------------
DROP TABLE IF EXISTS `sys_problem_case`;
CREATE TABLE `sys_problem_case`  (
  `problem_id` int(0) NULL DEFAULT NULL COMMENT '问题的id',
  `input` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '输入',
  `output` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '输出'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_problem_case
-- ----------------------------
INSERT INTO `sys_problem_case` VALUES (1001, '', 'The big bug flies away,\nThe big bat cries.');
INSERT INTO `sys_problem_case` VALUES (1002, '2 4', '6');
INSERT INTO `sys_problem_case` VALUES (1003, '1', '1');
INSERT INTO `sys_problem_case` VALUES (1015, '1 1', '2');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(0) NULL DEFAULT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` int(0) NULL DEFAULT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, 1, '0', 'admin', '2021-11-11 12:28:50', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 0, 0, 1, '0', 'admin', '2021-11-11 12:28:50', 'admin', '2022-10-26 19:07:15', '普通角色');
INSERT INTO `sys_role` VALUES (3, '测试', 'cs', 1, '1', 1, 1, 1, '0', 'admin', '2022-10-27 09:59:47', '', NULL, NULL);
INSERT INTO `sys_role` VALUES (4, '2023', 'cs1', 0, '1', 1, 1, 1, '0', 'admin', '2023-01-02 13:26:19', '', NULL, NULL);
INSERT INTO `sys_role` VALUES (8, 'abc', 'aa', NULL, '1', 1, 1, 1, '0', '', NULL, '', NULL, '');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 100);
INSERT INTO `sys_role_menu` VALUES (3, 101);
INSERT INTO `sys_role_menu` VALUES (3, 103);
INSERT INTO `sys_role_menu` VALUES (4, 1);
INSERT INTO `sys_role_menu` VALUES (4, 2);
INSERT INTO `sys_role_menu` VALUES (4, 3);
INSERT INTO `sys_role_menu` VALUES (4, 100);
INSERT INTO `sys_role_menu` VALUES (4, 101);
INSERT INTO `sys_role_menu` VALUES (4, 102);
INSERT INTO `sys_role_menu` VALUES (4, 103);
INSERT INTO `sys_role_menu` VALUES (4, 104);
INSERT INTO `sys_role_menu` VALUES (4, 105);
INSERT INTO `sys_role_menu` VALUES (4, 106);
INSERT INTO `sys_role_menu` VALUES (4, 1000);
INSERT INTO `sys_role_menu` VALUES (4, 1001);
INSERT INTO `sys_role_menu` VALUES (4, 1002);
INSERT INTO `sys_role_menu` VALUES (4, 1003);
INSERT INTO `sys_role_menu` VALUES (4, 1004);
INSERT INTO `sys_role_menu` VALUES (4, 1005);
INSERT INTO `sys_role_menu` VALUES (4, 1006);
INSERT INTO `sys_role_menu` VALUES (4, 1007);
INSERT INTO `sys_role_menu` VALUES (4, 1008);
INSERT INTO `sys_role_menu` VALUES (4, 1009);
INSERT INTO `sys_role_menu` VALUES (4, 1010);
INSERT INTO `sys_role_menu` VALUES (4, 1011);
INSERT INTO `sys_role_menu` VALUES (4, 1012);
INSERT INTO `sys_role_menu` VALUES (4, 1013);
INSERT INTO `sys_role_menu` VALUES (4, 1014);
INSERT INTO `sys_role_menu` VALUES (4, 1015);
INSERT INTO `sys_role_menu` VALUES (4, 1016);
INSERT INTO `sys_role_menu` VALUES (7, 2);
INSERT INTO `sys_role_menu` VALUES (7, 101);
INSERT INTO `sys_role_menu` VALUES (7, 1000);
INSERT INTO `sys_role_menu` VALUES (7, 1001);
INSERT INTO `sys_role_menu` VALUES (7, 1002);
INSERT INTO `sys_role_menu` VALUES (7, 1003);
INSERT INTO `sys_role_menu` VALUES (8, 1);
INSERT INTO `sys_role_menu` VALUES (8, 2);
INSERT INTO `sys_role_menu` VALUES (8, 3);
INSERT INTO `sys_role_menu` VALUES (8, 100);
INSERT INTO `sys_role_menu` VALUES (8, 101);
INSERT INTO `sys_role_menu` VALUES (8, 102);
INSERT INTO `sys_role_menu` VALUES (8, 103);
INSERT INTO `sys_role_menu` VALUES (8, 104);
INSERT INTO `sys_role_menu` VALUES (8, 1000);
INSERT INTO `sys_role_menu` VALUES (8, 1001);
INSERT INTO `sys_role_menu` VALUES (8, 1002);
INSERT INTO `sys_role_menu` VALUES (8, 1003);
INSERT INTO `sys_role_menu` VALUES (8, 1004);
INSERT INTO `sys_role_menu` VALUES (8, 1005);
INSERT INTO `sys_role_menu` VALUES (8, 1006);
INSERT INTO `sys_role_menu` VALUES (8, 1007);
INSERT INTO `sys_role_menu` VALUES (8, 1008);
INSERT INTO `sys_role_menu` VALUES (8, 1009);
INSERT INTO `sys_role_menu` VALUES (8, 1010);
INSERT INTO `sys_role_menu` VALUES (8, 1011);
INSERT INTO `sys_role_menu` VALUES (8, 1012);
INSERT INTO `sys_role_menu` VALUES (8, 1013);
INSERT INTO `sys_role_menu` VALUES (8, 1014);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 4);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (2, 8);

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签名',
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '颜色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tags
-- ----------------------------
INSERT INTO `tags` VALUES (1, '帅', '#FE4C61');
INSERT INTO `tags` VALUES (2, '酷', '#FE4C61');
INSERT INTO `tags` VALUES (5, '赞', NULL);
INSERT INTO `tags` VALUES (11, '123', NULL);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户id（主键）',
  `user_name` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码（加密）',
  `nick` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '昵称',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户E-mail',
  `submit` int(0) NOT NULL DEFAULT 0 COMMENT '用户提交次数',
  `solved` int(0) NOT NULL DEFAULT 0 COMMENT '成功次数',
  `defunct` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否屏蔽（Y/N）',
  `ip` varchar(46) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户注册ip',
  `accesstime` datetime(0) NOT NULL COMMENT '用户注册时间',
  `volume` int(0) NULL DEFAULT 1 COMMENT '页码（表示用户上次看到第几页）',
  `language` int(0) NOT NULL DEFAULT 1 COMMENT '语言',
  `reg_time` datetime(0) NOT NULL COMMENT '用户注册时间',
  `introduce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '个人介绍',
  `school` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '学校',
  `avatar` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `is_rank` int(0) NOT NULL DEFAULT 0 COMMENT '是否参加排名 (0不参加 1参加)',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', '$2a$10$b5h7MKaqXpQH0DuV/2ubNeuiDLleVcx/6rkGxSc.dj9xtdLnqTKWi', '1231231', '', 10, 5, '1', '127.0.0.1', '2022-09-10 17:34:23', 1, 1, '2021-12-26 16:00:00', '俩字：有文化！', '机电', '1.jpg', 1);
INSERT INTO `users` VALUES (2, 'wang', '$2a$10$QlTr8d.h9TKzKTe.tEqYX.lddQGJ26grIuGlO1PTbAL.5N/1.npAa', 'wang', '2', 1, 1, '1', '', '2023-01-25 17:27:38', 0, 1, '2021-12-26 16:00:00', NULL, '机电', 'admin.jpg', 1);
INSERT INTO `users` VALUES (3, 'k', '$2a$10$MHpBKVk/H3dO5c13CyFhfujpRfp0j53dZLv.dtn4daZOxBUsfUnbi', '', '', 0, 0, '0', '', '2023-01-30 18:26:41', 0, 1, '2021-12-26 16:00:00', NULL, '', 'mo.jpeg', 1);

-- ----------------------------
-- Table structure for work
-- ----------------------------
DROP TABLE IF EXISTS `work`;
CREATE TABLE `work`  (
  `workid` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `probelmid` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `starttime` bigint(0) NOT NULL,
  `endtime` bigint(0) NOT NULL,
  `type` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `founder` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `workpassword` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `open` tinyint(0) NOT NULL,
  `notice` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`workid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of work
-- ----------------------------
INSERT INTO `work` VALUES (-1405009919, '第二次', '1001&1002', 1672502400000, 1675094400000, '私有', '1', '123', 1, NULL);
INSERT INTO `work` VALUES (-1291886590, 'a', '1001', 1675958400000, 1677168000000, '公有', '1', NULL, 1, NULL);
INSERT INTO `work` VALUES (1, '第一次作业啊', '1001&1003&1000', 1667435502000, 1672360309000, '公有', 'admin', '', 0, '职业');

-- ----------------------------
-- Procedure structure for DEFAULT_ADMINISTRATOR
-- ----------------------------
DROP PROCEDURE IF EXISTS `DEFAULT_ADMINISTRATOR`;
delimiter ;;
CREATE PROCEDURE `DEFAULT_ADMINISTRATOR`(user_name VARCHAR(48))
BEGIN
    DECLARE privileged_count INT DEFAULT 0;
    SET privileged_count=(SELECT COUNT(1) FROM `privilege`);
    IF privileged_count=0 THEN
        INSERT INTO privilege values(user_name, 'administrator', 'true', 'N');
    end if;
end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
