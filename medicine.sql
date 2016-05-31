/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : medicine

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2016-05-31 11:35:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_gen_modules
-- ----------------------------
DROP TABLE IF EXISTS `t_gen_modules`;
CREATE TABLE `t_gen_modules` (
  `id` varchar(50) NOT NULL COMMENT '标识',
  `generate` varchar(5) DEFAULT NULL COMMENT '是否生成',
  `code` varchar(100) DEFAULT NULL COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `project_id` varchar(50) DEFAULT NULL COMMENT '所属项目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模块信息';

-- ----------------------------
-- Records of t_gen_modules
-- ----------------------------
INSERT INTO `t_gen_modules` VALUES ('medicine01', 'true', 'medicine', '医药管理', 'yhy', 'web-medicine,');

-- ----------------------------
-- Table structure for t_gen_projects
-- ----------------------------
DROP TABLE IF EXISTS `t_gen_projects`;
CREATE TABLE `t_gen_projects` (
  `id` varchar(50) NOT NULL COMMENT '标识',
  `generate` varchar(5) DEFAULT NULL COMMENT '是否生成',
  `code` varchar(100) DEFAULT NULL COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `path` varchar(1000) DEFAULT NULL COMMENT '路径',
  `pkg` varchar(255) DEFAULT NULL COMMENT '包名',
  `main_src` varchar(255) DEFAULT NULL COMMENT '主代码',
  `main_res` varchar(255) DEFAULT NULL COMMENT '主资源',
  `main_app` varchar(255) DEFAULT NULL COMMENT '主应用',
  `test_src` varchar(255) DEFAULT NULL COMMENT '测试代码',
  `test_res` varchar(255) DEFAULT NULL COMMENT '测试资源',
  `views` varchar(255) DEFAULT NULL COMMENT '页面路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目信息';

-- ----------------------------
-- Records of t_gen_projects
-- ----------------------------
INSERT INTO `t_gen_projects` VALUES ('web-medicine', 'true', 'medicine', '医药管理子项目', null, null, null, null, null, null, null, null);
INSERT INTO `t_gen_projects` VALUES ('web-sys', 'true', 'sys', '系统模块', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for t_gen_tables
-- ----------------------------
DROP TABLE IF EXISTS `t_gen_tables`;
CREATE TABLE `t_gen_tables` (
  `id` varchar(50) NOT NULL COMMENT '标识',
  `generate` varchar(5) DEFAULT NULL COMMENT '是否生成',
  `menu` varchar(5) DEFAULT NULL COMMENT '是否菜单',
  `code` varchar(100) DEFAULT NULL COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `comments` varchar(1000) DEFAULT NULL COMMENT '备注',
  `extend` varchar(100) DEFAULT NULL COMMENT '继承',
  `ignore_col` varchar(4000) DEFAULT NULL COMMENT '忽略列',
  `module_id` varchar(50) DEFAULT NULL COMMENT '所属模块',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表信息';

-- ----------------------------
-- Records of t_gen_tables
-- ----------------------------
INSERT INTO `t_gen_tables` VALUES ('medicine01004', 'true', 'true', 't_medicine_med_sales', '药品销售记录表', '药品登记', 'sys', null, 'medicine01,');

-- ----------------------------
-- Table structure for t_medicine_chain
-- ----------------------------
DROP TABLE IF EXISTS `t_medicine_chain`;
CREATE TABLE `t_medicine_chain` (
  `id` varchar(50) NOT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(50) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `deletion` int(11) DEFAULT NULL,
  `history` int(11) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_medicine_chain
-- ----------------------------
INSERT INTO `t_medicine_chain` VALUES ('157787ba-bdf3-4b78-951e-1ff673af44d2', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 11:47:51', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 11:47:51', '0', '0', '0', null, '北京二店');
INSERT INTO `t_medicine_chain` VALUES ('665648ed-6fa1-49e0-885c-8a80a50c4202', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 11:47:45', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 11:47:45', '0', '0', '0', null, '北京一店');

-- ----------------------------
-- Table structure for t_medicine_med
-- ----------------------------
DROP TABLE IF EXISTS `t_medicine_med`;
CREATE TABLE `t_medicine_med` (
  `id` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(50) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `deletion` int(11) DEFAULT NULL,
  `history` int(11) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `type_id` varchar(50) DEFAULT NULL,
  `spec` varchar(255) DEFAULT NULL COMMENT '规格',
  `price` decimal(10,2) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL COMMENT '品牌',
  `vendor` varchar(255) DEFAULT NULL COMMENT '生产厂商',
  `mstate` varchar(50) DEFAULT '0' COMMENT '状态',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `barcode` varchar(255) DEFAULT NULL COMMENT '条形码编码',
  `chain_id` varchar(50) DEFAULT NULL COMMENT '链锁店ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_medicine_med
-- ----------------------------
INSERT INTO `t_medicine_med` VALUES ('977db33d-3a0a-4a03-ba02-b27c8e2453f9', '感康', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 11:50:45', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 15:20:17', '0', '0', '0', null, '89157944-7891-4ec3-ae40-5ef915a87025', '10m', '12.50', '哈药', '哈药哈药哈药', '入库', '17', '20040', '157787ba-bdf3-4b78-951e-1ff673af44d2');
INSERT INTO `t_medicine_med` VALUES ('a716bce8-fd5e-4a6e-a63c-844c93b4252a', '药品1', 'anonymousUser', '2016-05-21 11:53:16', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 15:56:41', '0', '0', '0', null, '89157944-7891-4ec3-ae40-5ef915a87025', '10m', '43.00', '哈药1', '哈药哈药哈药', '入库', '40', '20041', '157787ba-bdf3-4b78-951e-1ff673af44d2');
INSERT INTO `t_medicine_med` VALUES ('bd1ca7df-941d-4827-ab6c-3906c38cdb9c', '中西药', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 12:46:47', '72c29994-a075-11e4-a96d-005056a8', '2016-05-31 11:15:20', '0', '0', '0', null, '8106a58c-1c0a-4764-9f52-a13ce70090dc', '10m', '12.50', '哈药', '哈药哈药哈药', '入库', '11', '20042', '665648ed-6fa1-49e0-885c-8a80a50c4202');
INSERT INTO `t_medicine_med` VALUES ('e12b0eaa-206b-4d0c-9552-e6a57a1fbd5f', '中西药', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 12:45:58', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 12:45:58', '0', '0', '0', null, '8106a58c-1c0a-4764-9f52-a13ce70090dc', '10m', '12.50', '哈药', '哈药哈药哈药', '入库', '54', '20043', '665648ed-6fa1-49e0-885c-8a80a50c4202');

-- ----------------------------
-- Table structure for t_medicine_med_sales
-- ----------------------------
DROP TABLE IF EXISTS `t_medicine_med_sales`;
CREATE TABLE `t_medicine_med_sales` (
  `id` varchar(50) NOT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(50) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `deletion` int(11) DEFAULT NULL,
  `history` int(11) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `med_id` varchar(50) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL COMMENT '销售数量',
  `barcode` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_medicine_med_sales
-- ----------------------------
INSERT INTO `t_medicine_med_sales` VALUES ('2007fa97-07ad-46f5-a87e-47b493b7f0f4', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 15:56:41', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 15:56:41', '0', '0', '0', null, 'a716bce8-fd5e-4a6e-a63c-844c93b4252a', '1', '20041');
INSERT INTO `t_medicine_med_sales` VALUES ('31e1bf69-1fc0-4577-9720-f2f79862fa8a', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 15:20:32', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 15:20:32', '0', '0', '0', null, 'a716bce8-fd5e-4a6e-a63c-844c93b4252a', '1', '20041');
INSERT INTO `t_medicine_med_sales` VALUES ('98f26e1f-c267-42b5-aba2-b580146e5c82', '72c29994-a075-11e4-a96d-005056a8', '2016-05-31 11:15:20', '72c29994-a075-11e4-a96d-005056a8', '2016-05-31 11:15:20', '0', '0', '0', null, 'bd1ca7df-941d-4827-ab6c-3906c38cdb9c', '1', '20042');
INSERT INTO `t_medicine_med_sales` VALUES ('ff14871d-6b94-4215-9766-b57650877494', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 15:19:57', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 15:19:57', '0', '0', '0', null, '977db33d-3a0a-4a03-ba02-b27c8e2453f9', '1', '20040');

-- ----------------------------
-- Table structure for t_medicine_med_type
-- ----------------------------
DROP TABLE IF EXISTS `t_medicine_med_type`;
CREATE TABLE `t_medicine_med_type` (
  `id` varchar(50) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(50) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `deletion` int(11) DEFAULT NULL,
  `history` int(11) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_medicine_med_type
-- ----------------------------
INSERT INTO `t_medicine_med_type` VALUES ('8106a58c-1c0a-4764-9f52-a13ce70090dc', '感冒药', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 11:45:03', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 11:45:03', '0', '0', '0', null);
INSERT INTO `t_medicine_med_type` VALUES ('89157944-7891-4ec3-ae40-5ef915a87025', '中西药', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 11:45:15', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 11:45:15', '0', '0', '0', null);

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` varchar(50) NOT NULL COMMENT '标识',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `genre` varchar(50) DEFAULT NULL COMMENT '类型',
  `creater` varchar(100) NOT NULL COMMENT '创建人员',
  `created` datetime NOT NULL COMMENT '创建日期',
  `modifier` varchar(100) NOT NULL COMMENT '修改人员',
  `modified` datetime NOT NULL COMMENT '修改日期',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
  `deletion` int(11) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `history` int(11) NOT NULL DEFAULT '0' COMMENT '历史数据',
  `memo` varchar(1000) DEFAULT NULL COMMENT '备注',
  `code` varchar(100) DEFAULT NULL COMMENT '编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('0886b3e1-1647-43cb-9bae-4cf15630e50e', '链锁店删除', 'medicine:chain:disable', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:25:03', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:25:03', '0', '0', '0', null, '002');
INSERT INTO `t_sys_role` VALUES ('0a5146a1-9209-4a3a-b301-f7cd47e6b75e', '药品类型删除', 'medicine:med:type:disable', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:14:33', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:14:33', '0', '0', '0', null, '003');
INSERT INTO `t_sys_role` VALUES ('1', 'BMS管理员', 'bms:role:admin', 'root', '2016-05-21 22:41:30', 'root', '2016-05-21 22:41:34', '1', '0', '0', '0', '10000');
INSERT INTO `t_sys_role` VALUES ('1df72bb6-d75c-420f-b63d-72a5527c8710', '用户删除', 'sys:user:disable', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:20:18', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:20:18', '0', '0', '0', null, '002');
INSERT INTO `t_sys_role` VALUES ('39dbf5c7-43bd-4f1e-9307-b59382cd7d17', '用户查看', 'sys:user:view', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:57:23', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:57:23', '0', '0', '0', null, '003');
INSERT INTO `t_sys_role` VALUES ('4554f35a-a69b-4286-aabd-b0bc5bfcf2ff', '用户修改', 'sys:user:edit', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:19:23', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:19:23', '0', '0', '0', null, '001');
INSERT INTO `t_sys_role` VALUES ('4b8bcd50-a142-4865-b80a-8e3ac538d901', '药品删除', 'medicine:med:disable', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:23:49', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:23:49', '0', '0', '0', null, '002');
INSERT INTO `t_sys_role` VALUES ('5e74d7c8-23a7-429a-83a1-04d83d8c6d92', '链锁店查看', 'medicine:chain:view', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:51:32', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:51:32', '0', '0', '0', null, '003');
INSERT INTO `t_sys_role` VALUES ('845aff54-bc45-4581-b7ec-228775420cff', '权限编辑', 'sys:role:disable', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 11:58:11', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 11:58:11', '0', '0', '0', null, '001');
INSERT INTO `t_sys_role` VALUES ('94bd8ea4-ecd0-4d80-82d0-132c445e2ef2', '药品类型查看', 'medicine:med:type:view', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:14:09', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:14:09', '0', '0', '0', null, '001');
INSERT INTO `t_sys_role` VALUES ('9cd6c5e8-569e-48e1-88de-fb9b2d95cf73', '权限查看', 'sys:role:view', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:57:43', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:57:43', '0', '0', '0', null, '003');
INSERT INTO `t_sys_role` VALUES ('9e4713a6-c501-4b00-8ea2-ed1853c2e83f', '药品查看', 'medicine:med:view', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:57:00', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:57:00', '0', '0', '0', null, '003');
INSERT INTO `t_sys_role` VALUES ('c522af10-8cb2-485e-93d0-a0cc02f9f793', '药品修改', 'medicine:med:edit', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:23:19', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:23:19', '0', '0', '0', null, '001');
INSERT INTO `t_sys_role` VALUES ('c8ce5228-1fbe-47af-ab4d-a8c9db5306f0', '链锁店修改', 'medicine:chain:edit', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:24:44', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 12:24:44', '0', '0', '0', null, '001');
INSERT INTO `t_sys_role` VALUES ('e7552af3-e856-4e02-be8e-812eba7734c4', '药品类型修改', 'medicine:med:type:edit', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:14:23', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:14:23', '0', '0', '0', null, '002');
INSERT INTO `t_sys_role` VALUES ('f2277f61-67ee-4ac4-9e4e-3c4ca2bb4a5f', '权限删除', 'sys:role:edit', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 11:58:34', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:05:25', '0', '0', '0', null, '002');

-- ----------------------------
-- Table structure for t_sys_users
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_users`;
CREATE TABLE `t_sys_users` (
  `id` varchar(50) NOT NULL,
  `loginname` varchar(50) DEFAULT NULL,
  `passwd` varchar(255) DEFAULT NULL,
  `uname` varchar(50) DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `chainid` varchar(50) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(50) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `deletion` int(11) DEFAULT NULL,
  `history` int(11) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_users
-- ----------------------------
INSERT INTO `t_sys_users` VALUES ('482726ab-7a5a-43d9-8047-b47a602c1ee2', 'yhy', '1c41c8651b900917f5aaff670e3fa7029291cadb2c7470de49456c5bf70bdcfca1254ccb071a0969', '张三', '1', '1', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 10:29:08', '72c29994-a075-11e4-a96d-005056a8', '0', '0', '0', null, '2016-05-28 13:12:15');
INSERT INTO `t_sys_users` VALUES ('626fe671-ad52-4791-9fa7-d1cdcb8a6e03', '123', '5999d5bd2358ccb1c893d8f07093ce83eea5c9e1eb3445fb73663a617c103d5335d8c0f3d4d0b9ea', '智', '0', '1', '72c29994-a075-11e4-a96d-005056a8', '2016-05-22 15:29:15', '72c29994-a075-11e4-a96d-005056a8', '0', '1', '0', null, '2016-05-22 15:31:24');
INSERT INTO `t_sys_users` VALUES ('72c29994-a075-11e4-a96d-005056a8', 'admin', 'd5541cd80576c4e0cf787d1ae395d32e8110ae86a4af356dc8325e8bb12d77b52f45b84b8d72721e', '管理员', '0', 'a716bce8-fd5e-4a6e-a63c-844c93b4', 'root', '2015-02-03 20:23:34', 'root', '0', '0', '0', '0', '2016-05-21 20:05:44');

-- ----------------------------
-- Table structure for t_sys_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_roles`;
CREATE TABLE `t_sys_user_roles` (
  `id` varchar(50) NOT NULL COMMENT '标识',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户标识',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色标识',
  `creater` varchar(50) NOT NULL COMMENT '创建人员',
  `created` datetime NOT NULL COMMENT '创建日期',
  `modifier` varchar(50) NOT NULL COMMENT '修改人员',
  `modified` datetime NOT NULL COMMENT '修改日期',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
  `deletion` int(11) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `history` int(11) NOT NULL DEFAULT '0' COMMENT '历史数据',
  `memo` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `fk_user_roles_userid` (`user_id`),
  KEY `fk_user_roles_roleid` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色信息表';

-- ----------------------------
-- Records of t_sys_user_roles
-- ----------------------------
INSERT INTO `t_sys_user_roles` VALUES ('1057aef1-9434-4d9e-b3c5-d4ea5f55137e', '72c29994-a075-11e4-a96d-005056a8', '0886b3e1-1647-43cb-9bae-4cf15630e50e', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('2fa204d1-61cd-4ca4-98e6-b0a6e7b9a2fe', '482726ab-7a5a-43d9-8047-b47a602c1ee2', '4b8bcd50-a142-4865-b80a-8e3ac538d901', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:11:53', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:11:53', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('35ce1d4f-ba7b-47db-962e-6b22ba709386', '72c29994-a075-11e4-a96d-005056a8', '9cd6c5e8-569e-48e1-88de-fb9b2d95cf73', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('46e1eb61-d9cb-4282-b0b8-00f432291db1', '72c29994-a075-11e4-a96d-005056a8', '39dbf5c7-43bd-4f1e-9307-b59382cd7d17', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('528f2b42-e4b8-4bfa-a1e5-7e76383b3034', '72c29994-a075-11e4-a96d-005056a8', '4554f35a-a69b-4286-aabd-b0bc5bfcf2ff', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('5401a5be-dc63-4740-a714-3d55d0eeebc4', '72c29994-a075-11e4-a96d-005056a8', '1df72bb6-d75c-420f-b63d-72a5527c8710', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('71298331-165b-44e0-ac1b-965222e0e3e8', '72c29994-a075-11e4-a96d-005056a8', '4b8bcd50-a142-4865-b80a-8e3ac538d901', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('7307cc7d-486f-41af-a488-ea7de9a09404', '72c29994-a075-11e4-a96d-005056a8', '1', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('804e2a33-84e6-4083-8b66-639a9155962f', '72c29994-a075-11e4-a96d-005056a8', 'c8ce5228-1fbe-47af-ab4d-a8c9db5306f0', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('8ec10fa0-9aa9-4da8-9a7c-7cb7c996b4f3', '72c29994-a075-11e4-a96d-005056a8', 'c522af10-8cb2-485e-93d0-a0cc02f9f793', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('a7c79c4d-ff74-4421-b1a8-98be5890a88c', '482726ab-7a5a-43d9-8047-b47a602c1ee2', '9e4713a6-c501-4b00-8ea2-ed1853c2e83f', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:11:53', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:11:53', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('b1f22b94-1189-4b4d-98ac-fe07bb84b497', '72c29994-a075-11e4-a96d-005056a8', 'e7552af3-e856-4e02-be8e-812eba7734c4', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('b3afb749-892b-4c68-b29f-577bad1a00a7', '72c29994-a075-11e4-a96d-005056a8', '0a5146a1-9209-4a3a-b301-f7cd47e6b75e', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('cbca5956-858b-42c9-a41e-ab8a23f17075', '72c29994-a075-11e4-a96d-005056a8', '9e4713a6-c501-4b00-8ea2-ed1853c2e83f', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('cf8cd5f7-ff09-48f9-abc7-d7e1b615ad75', '482726ab-7a5a-43d9-8047-b47a602c1ee2', 'c522af10-8cb2-485e-93d0-a0cc02f9f793', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:11:53', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:11:53', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('e075146d-b8f0-470a-9567-26a4dd8c9ff7', '72c29994-a075-11e4-a96d-005056a8', '5e74d7c8-23a7-429a-83a1-04d83d8c6d92', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('e66b74c5-b047-4a3b-bb8e-6568643922db', '72c29994-a075-11e4-a96d-005056a8', '845aff54-bc45-4581-b7ec-228775420cff', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('f220ab46-eb42-4cdb-97d5-384dda8eb810', '72c29994-a075-11e4-a96d-005056a8', '94bd8ea4-ecd0-4d80-82d0-132c445e2ef2', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);
INSERT INTO `t_sys_user_roles` VALUES ('f3b769b0-8764-4b43-9b13-8dc47287edd3', '72c29994-a075-11e4-a96d-005056a8', 'f2277f61-67ee-4ac4-9e4e-3c4ca2bb4a5f', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '72c29994-a075-11e4-a96d-005056a8', '2016-05-28 13:17:25', '0', '0', '0', null);

-- ----------------------------
-- View structure for v_gen_columns
-- ----------------------------
DROP VIEW IF EXISTS `v_gen_columns`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `v_gen_columns` AS select uuid() AS `id`,`c`.`TABLE_NAME` AS `table_name`,lcase(`c`.`COLUMN_NAME`) AS `name`,ifnull(if((`c`.`COLUMN_COMMENT` = ''),lcase(`c`.`COLUMN_NAME`),`c`.`COLUMN_COMMENT`),lcase(`c`.`COLUMN_NAME`)) AS `comment`,lcase(`c`.`DATA_TYPE`) AS `types`,(case `c`.`DATA_TYPE` when 'datetime' then 19 when 'int' then `c`.`NUMERIC_PRECISION` when 'tinyint' then `c`.`NUMERIC_PRECISION` when 'smallint' then `c`.`NUMERIC_PRECISION` when 'bigint' then `c`.`NUMERIC_PRECISION` when 'decimal' then `c`.`NUMERIC_PRECISION` else ifnull(`c`.`CHARACTER_MAXIMUM_LENGTH`,0) end) AS `length`,(case `c`.`DATA_TYPE` when 'int' then `c`.`NUMERIC_SCALE` when 'decimal' then `c`.`NUMERIC_SCALE` else 0 end) AS `scale`,`c`.`COLUMN_DEFAULT` AS `defaults`,if((`c`.`IS_NULLABLE` = 'no'),'0','1') AS `nullable`,if((`c`.`COLUMN_KEY` = 'pri'),'1','0') AS `pkey`,`c`.`ORDINAL_POSITION` AS `ordinal` from `information_schema`.`columns` `c` where (`c`.`TABLE_SCHEMA` = 'medicine') order by `c`.`ORDINAL_POSITION` ;
