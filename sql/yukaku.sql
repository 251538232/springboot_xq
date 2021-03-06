/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : yukaku

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-05-23 18:58:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_access_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_access_token`;
CREATE TABLE `sys_access_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ＩＤ',
  `user_id` bigint(20) NOT NULL,
  `access_token` varchar(100) NOT NULL COMMENT 'access_token',
  `expire_time` datetime DEFAULT NULL COMMENT 'expire time',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '作成時間',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
  PRIMARY KEY (`id`),
  UNIQUE KEY `access_token` (`access_token`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='システムユーザーToken';

-- ----------------------------
-- Records of sys_access_token
-- ----------------------------
INSERT INTO `sys_access_token` VALUES ('10', '1', '5e26aa54-e2f4-490a-855c-c60f47826e20', '2020-05-23 21:07:37', '2020-05-16 20:16:31', '2020-05-16 21:07:37');

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` int(9) NOT NULL DEFAULT '0',
  `ken_id` int(2) DEFAULT NULL,
  `city_id` int(5) DEFAULT NULL,
  `town_id` int(9) DEFAULT NULL,
  `zip` varchar(8) DEFAULT NULL,
  `office_flg` int(1) DEFAULT NULL,
  `delete_flg` int(1) DEFAULT NULL,
  `ken_name` varchar(8) DEFAULT NULL,
  `ken_furi` varchar(8) DEFAULT NULL,
  `city_name` varchar(24) DEFAULT NULL,
  `city_furi` varchar(24) DEFAULT NULL,
  `town_name` varchar(32) DEFAULT NULL,
  `town_furi` varchar(32) DEFAULT NULL,
  `town_memo` varchar(16) DEFAULT NULL,
  `kyoto_street` varchar(32) DEFAULT NULL,
  `block_name` varchar(64) DEFAULT NULL,
  `block_furi` varchar(64) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `office_name` varchar(255) DEFAULT NULL,
  `office_furi` varchar(255) DEFAULT NULL,
  `office_address` varchar(255) DEFAULT NULL,
  `new_id` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_area
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(50) DEFAULT NULL COMMENT '種類',
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(200) DEFAULT NULL COMMENT 'value',
  `disable` int(4) DEFAULT '0' COMMENT '1:disable 0:enable',
  `description` varchar(200) DEFAULT NULL COMMENT 'description',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成時間',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='システムコンフィグ';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'disable', '1', '無効', '0', 'データ無効', '2018-06-26 10:17:14', '2018-06-26 11:02:27');
INSERT INTO `sys_config` VALUES ('2', 'disable', '0', '有効', '0', 'データ有効', '2018-06-26 10:17:15', '2018-06-26 11:02:31');
INSERT INTO `sys_config` VALUES ('3', 'postion_level', '1', 'Ｐ１', '0', 'Ｐ１職（一般社員）', '2018-06-26 11:15:26', '2018-06-26 11:15:26');
INSERT INTO `sys_config` VALUES ('4', 'postion_level', '2', 'Ｐ２', '0', 'Ｐ２職（係長）', '2018-06-26 11:15:46', '2018-06-26 11:15:46');
INSERT INTO `sys_config` VALUES ('5', 'postion_level', '3', 'Ｐ３', '0', 'Ｐ３職（課長）', '2018-06-26 11:15:47', '2018-06-26 11:15:47');
INSERT INTO `sys_config` VALUES ('6', 'postion_level', '4', 'Ｐ４', '0', 'Ｐ４職（部長）', '2018-06-26 11:15:48', '2018-06-26 11:15:48');
INSERT INTO `sys_config` VALUES ('7', 'postion_level', '5', 'Ｐ５', '0', 'Ｐ５職（本部長）', '2018-06-26 11:21:55', '2018-06-26 11:21:55');
INSERT INTO `sys_config` VALUES ('8', 'postion_level', '6', 'Ｐ６', '0', 'Ｐ６職', '2018-06-26 11:21:55', '2018-06-26 11:21:55');
INSERT INTO `sys_config` VALUES ('9', 'postion_level', '7', 'Ｐ７', '0', 'Ｐ７職', '2018-06-26 11:21:55', '2018-06-26 11:21:55');
INSERT INTO `sys_config` VALUES ('10', 'postion_level', '8', 'Ｐ８', '0', 'Ｐ８職', '2018-06-26 11:21:55', '2018-06-26 11:21:55');
INSERT INTO `sys_config` VALUES ('11', 'postion_level', '9', 'Ｐ９', '0', 'Ｐ９職', '2018-06-26 11:21:55', '2018-06-26 11:21:55');
INSERT INTO `sys_config` VALUES ('12', 'postion_level', '10', 'Ｐ１０', '0', 'Ｐ１０職', '2018-06-26 11:21:55', '2018-06-26 11:21:55');
INSERT INTO `sys_config` VALUES ('13', 'postion_level', '11', 'Ｐ１１', '0', 'Ｐ１１職', '2018-06-26 11:21:55', '2018-06-26 11:21:55');
INSERT INTO `sys_config` VALUES ('14', 'postion_level', '12', 'Ｐ１２', '0', 'Ｐ１２職', '2018-06-26 11:21:55', '2018-06-26 11:21:55');
INSERT INTO `sys_config` VALUES ('21', 'apply_status', '0', '承認待ち', '0', '承認待ち', '2018-06-26 13:51:19', '2018-06-26 13:51:19');
INSERT INTO `sys_config` VALUES ('22', 'apply_status', '1', '承認済み', '0', '承認済み', '2018-06-26 13:51:20', '2018-06-26 13:51:20');
INSERT INTO `sys_config` VALUES ('23', 'apply_status', '2', '却下', '0', '却下', '2018-06-26 13:51:21', '2018-06-26 13:51:21');
INSERT INTO `sys_config` VALUES ('24', 'apply_status', '3', '取り消し', '0', '取り消し', '2018-06-26 13:51:22', '2018-06-26 13:51:22');
INSERT INTO `sys_config` VALUES ('25', 'apply_rule_level', 'd1', 'Ｄ１', '0', '本人上位組織', '2018-06-26 14:17:24', '2018-06-26 14:17:24');
INSERT INTO `sys_config` VALUES ('26', 'apply_rule_level', 'd2', 'Ｄ２', '0', '本人上2位組織', '2018-06-26 14:17:28', '2018-06-26 14:17:28');
INSERT INTO `sys_config` VALUES ('29', 'apply_rule_level', 'd3', 'Ｄ３', '0', '本人上3位組織', '2018-06-26 15:55:30', '2018-06-26 15:55:30');
INSERT INTO `sys_config` VALUES ('30', 'apply_rule_level', 'd4', 'Ｄ４', '0', '本人上4位組織', '2018-06-26 15:55:31', '2018-06-26 15:55:31');
INSERT INTO `sys_config` VALUES ('31', 'apply_rule_level', 'd4', 'Ｄ５', '0', '本人上5位組織', '2018-06-26 15:55:31', '2018-06-26 15:55:31');
INSERT INTO `sys_config` VALUES ('32', 'apply_rule_level', 'd5', 'Ｄ６', '0', '本人上6位組織', '2018-06-26 15:55:32', '2018-06-26 15:55:32');
INSERT INTO `sys_config` VALUES ('33', 'apply_rule_level', 'd6', 'Ｄ７', '0', '本人上7位組織', '2018-06-26 15:55:34', '2018-06-26 15:55:34');
INSERT INTO `sys_config` VALUES ('34', 'day_type', 'rest', '休日', '0', null, '2018-06-27 14:43:44', '2018-06-27 14:43:44');
INSERT INTO `sys_config` VALUES ('35', 'day_type', 'normal', '平日', '0', null, '2018-06-27 14:43:52', '2018-06-27 14:43:52');
INSERT INTO `sys_config` VALUES ('36', 'project_type', 'work', '作業', '0', null, '2018-08-13 13:21:39', '2018-08-13 13:21:39');
INSERT INTO `sys_config` VALUES ('37', 'project_type', 'service', '問い合わせ', '0', null, '2018-08-13 13:21:40', '2018-08-13 13:21:40');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) DEFAULT NULL COMMENT 'ユーザー名',
  `operation` varchar(50) DEFAULT NULL COMMENT 'ユーザー操作',
  `request_method` varchar(200) DEFAULT NULL COMMENT '請求したメソッド',
  `request_params` varchar(1000) DEFAULT NULL COMMENT 'リクエストパラメータ',
  `execute_time` bigint(20) NOT NULL COMMENT '実行時間(ms)',
  `ip_address` varchar(64) DEFAULT NULL COMMENT 'IPアドレス',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成時間',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='システムログ';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', null, 'GET', 'jp.co.xq.controller.LoginController.login()', '\"admin\"', '1567', '127.0.0.1', '2020-05-16 20:16:31', '2020-05-16 20:16:31');
INSERT INTO `sys_log` VALUES ('2', 'admin', 'GET', 'jp.co.xq.controller.SysMenuController.getMenuTree()', null, '239', '127.0.0.1', '2020-05-16 20:16:33', '2020-05-16 20:16:32');
INSERT INTO `sys_log` VALUES ('3', 'admin', 'GET', 'jp.co.xq.controller.SystemController.getSystemTime()', null, '16', '127.0.0.1', '2020-05-16 20:16:34', '2020-05-16 20:16:34');
INSERT INTO `sys_log` VALUES ('4', 'admin', 'GET', 'jp.co.xq.controller.SysRoleController.list()', '{\"access_token\":\"5e26aa54-e2f4-490a-855c-c60f47826e20\",\"_search\":\"false\",\"nd\":\"1589627813215\",\"limit\":\"10\",\"page\":\"1\",\"sidx\":\"id\",\"order\":\"asc\",\"_\":\"1589627812780\"}', '125', '127.0.0.1', '2020-05-16 20:16:53', '2020-05-16 20:16:53');
INSERT INTO `sys_log` VALUES ('5', 'admin', 'GET', 'jp.co.xq.controller.SysLogController.list()', '{\"access_token\":\"5e26aa54-e2f4-490a-855c-c60f47826e20\",\"_search\":\"false\",\"nd\":\"1589627825198\",\"limit\":\"10\",\"page\":\"1\",\"sidx\":\"id\",\"order\":\"asc\",\"_\":\"1589627824980\"}', '62', '127.0.0.1', '2020-05-16 20:17:05', '2020-05-16 20:17:05');
INSERT INTO `sys_log` VALUES ('6', 'admin', 'GET', 'jp.co.xq.controller.SysMenuController.getMenuTree()', null, '22', '127.0.0.1', '2020-05-16 20:17:28', '2020-05-16 20:17:28');
INSERT INTO `sys_log` VALUES ('7', 'admin', 'GET', 'jp.co.xq.controller.SystemController.getSystemTime()', null, '0', '127.0.0.1', '2020-05-16 20:17:29', '2020-05-16 20:17:29');
INSERT INTO `sys_log` VALUES ('8', 'admin', 'GET', 'jp.co.xq.controller.SysRoleController.list()', '{\"access_token\":\"5e26aa54-e2f4-490a-855c-c60f47826e20\",\"_search\":\"false\",\"nd\":\"1589628180726\",\"limit\":\"10\",\"page\":\"1\",\"sidx\":\"id\",\"order\":\"asc\",\"_\":\"1589628180189\"}', '20', '127.0.0.1', '2020-05-16 20:23:01', '2020-05-16 20:23:00');
INSERT INTO `sys_log` VALUES ('9', 'admin', 'GET', 'jp.co.xq.controller.SysMenuController.list()', '{\"_\":\"1589628180190\"}', '42', '127.0.0.1', '2020-05-16 20:23:08', '2020-05-16 20:23:07');
INSERT INTO `sys_log` VALUES ('10', 'admin', 'GET', 'jp.co.xq.controller.SysRoleController.list()', '{\"access_token\":\"5e26aa54-e2f4-490a-855c-c60f47826e20\",\"_search\":\"false\",\"nd\":\"1589628191348\",\"limit\":\"10\",\"page\":\"1\",\"sidx\":\"id\",\"order\":\"asc\",\"_\":\"1589628180191\"}', '5', '127.0.0.1', '2020-05-16 20:23:11', '2020-05-16 20:23:11');
INSERT INTO `sys_log` VALUES ('11', 'admin', 'GET', 'jp.co.xq.controller.SysMenuController.list()', '{\"_\":\"1589628180192\"}', '8', '127.0.0.1', '2020-05-16 20:23:16', '2020-05-16 20:23:16');
INSERT INTO `sys_log` VALUES ('12', 'admin', 'GET', 'jp.co.xq.controller.SysRoleController.list()', '{\"access_token\":\"5e26aa54-e2f4-490a-855c-c60f47826e20\",\"_search\":\"false\",\"nd\":\"1589628198310\",\"limit\":\"10\",\"page\":\"1\",\"sidx\":\"id\",\"order\":\"asc\",\"_\":\"1589628180193\"}', '9', '127.0.0.1', '2020-05-16 20:23:18', '2020-05-16 20:23:18');
INSERT INTO `sys_log` VALUES ('13', 'admin', 'GET', 'jp.co.xq.controller.SysLogController.list()', '{\"access_token\":\"5e26aa54-e2f4-490a-855c-c60f47826e20\",\"_search\":\"false\",\"nd\":\"1589628202916\",\"limit\":\"10\",\"page\":\"1\",\"sidx\":\"id\",\"order\":\"asc\",\"_\":\"1589628202715\"}', '9', '127.0.0.1', '2020-05-16 20:23:23', '2020-05-16 20:23:22');
INSERT INTO `sys_log` VALUES ('14', 'admin', 'GET', 'jp.co.xq.controller.SysConfigController.list()', '{\"access_token\":\"5e26aa54-e2f4-490a-855c-c60f47826e20\",\"_search\":\"false\",\"nd\":\"1589628207082\",\"limit\":\"10\",\"page\":\"1\",\"sidx\":\"id\",\"order\":\"asc\",\"_\":\"1589628206821\"}', '56', '127.0.0.1', '2020-05-16 20:23:27', '2020-05-16 20:23:27');
INSERT INTO `sys_log` VALUES ('15', 'admin', 'GET', 'jp.co.xq.controller.SysRoleController.list()', '{\"_\":\"1589628225077\"}', '4', '127.0.0.1', '2020-05-16 20:23:47', '2020-05-16 20:23:47');
INSERT INTO `sys_log` VALUES ('16', 'admin', 'GET', 'jp.co.xq.controller.SysLogController.list()', '{\"access_token\":\"5e26aa54-e2f4-490a-855c-c60f47826e20\",\"_search\":\"false\",\"nd\":\"1589628261989\",\"limit\":\"10\",\"page\":\"1\",\"sidx\":\"id\",\"order\":\"asc\",\"_\":\"1589628261809\"}', '10', '127.0.0.1', '2020-05-16 20:24:22', '2020-05-16 20:24:22');
INSERT INTO `sys_log` VALUES ('17', null, 'GET', 'jp.co.xq.controller.LoginController.login()', '\"admin\"', '2221', '127.0.0.1', '2020-05-16 20:30:00', '2020-05-16 20:29:59');
INSERT INTO `sys_log` VALUES ('18', null, 'GET', 'jp.co.xq.controller.LoginController.login()', '\"admin\"', '24', '127.0.0.1', '2020-05-16 20:41:42', '2020-05-16 20:41:42');
INSERT INTO `sys_log` VALUES ('19', 'admin', 'GET', 'jp.co.xq.controller.SysMenuController.getMenuTree()', null, '202', '127.0.0.1', '2020-05-16 20:41:44', '2020-05-16 20:41:43');
INSERT INTO `sys_log` VALUES ('20', 'admin', 'GET', 'jp.co.xq.controller.SystemController.getSystemTime()', null, '23', '127.0.0.1', '2020-05-16 20:41:45', '2020-05-16 20:41:44');
INSERT INTO `sys_log` VALUES ('21', 'admin', 'GET', 'jp.co.xq.controller.SysRoleController.list()', '{\"access_token\":\"5e26aa54-e2f4-490a-855c-c60f47826e20\",\"_search\":\"false\",\"nd\":\"1589630220984\",\"limit\":\"10\",\"page\":\"1\",\"sidx\":\"id\",\"order\":\"asc\",\"_\":\"1589630220764\"}', '141', '127.0.0.1', '2020-05-16 20:57:01', '2020-05-16 20:57:01');
INSERT INTO `sys_log` VALUES ('22', 'admin', 'GET', 'jp.co.xq.controller.SysMenuController.getMenuTree()', null, '19', '127.0.0.1', '2020-05-16 20:57:09', '2020-05-16 20:57:09');
INSERT INTO `sys_log` VALUES ('23', 'admin', 'GET', 'jp.co.xq.controller.SystemController.getSystemTime()', null, '0', '127.0.0.1', '2020-05-16 20:57:10', '2020-05-16 20:57:09');
INSERT INTO `sys_log` VALUES ('24', 'admin', 'GET', 'jp.co.xq.controller.SysMenuController.getMenuTree()', null, '17', '127.0.0.1', '2020-05-16 20:58:35', '2020-05-16 20:58:35');
INSERT INTO `sys_log` VALUES ('25', 'admin', 'GET', 'jp.co.xq.controller.SystemController.getSystemTime()', null, '0', '127.0.0.1', '2020-05-16 20:58:36', '2020-05-16 20:58:35');
INSERT INTO `sys_log` VALUES ('26', 'admin', 'GET', 'jp.co.xq.controller.SysMenuController.getMenuTree()', null, '11', '127.0.0.1', '2020-05-16 21:01:13', '2020-05-16 21:01:12');
INSERT INTO `sys_log` VALUES ('27', 'admin', 'GET', 'jp.co.xq.controller.SystemController.getSystemTime()', null, '0', '127.0.0.1', '2020-05-16 21:01:13', '2020-05-16 21:01:13');
INSERT INTO `sys_log` VALUES ('28', 'admin', 'GET', 'jp.co.xq.controller.SysMenuController.getMenuTree()', null, '29', '127.0.0.1', '2020-05-16 21:01:17', '2020-05-16 21:01:16');
INSERT INTO `sys_log` VALUES ('29', 'admin', 'GET', 'jp.co.xq.controller.SystemController.getSystemTime()', null, '0', '127.0.0.1', '2020-05-16 21:01:17', '2020-05-16 21:01:17');
INSERT INTO `sys_log` VALUES ('30', null, 'GET', 'jp.co.xq.controller.LoginController.login()', '\"adm\"', '1082', '0:0:0:0:0:0:0:1', '2020-05-16 21:04:10', '2020-05-16 21:04:09');
INSERT INTO `sys_log` VALUES ('31', null, 'GET', 'jp.co.xq.controller.base.BaseViewController.url()', '\"zh_CN\"', '40', '0:0:0:0:0:0:0:1', '2020-05-16 21:04:49', '2020-05-16 21:04:49');
INSERT INTO `sys_log` VALUES ('32', null, 'GET', 'jp.co.xq.controller.LoginController.login()', '\"adm\"', '10', '0:0:0:0:0:0:0:1', '2020-05-16 21:04:55', '2020-05-16 21:04:54');
INSERT INTO `sys_log` VALUES ('33', null, 'GET', 'jp.co.xq.controller.base.BaseViewController.url()', '\"en_US\"', '0', '0:0:0:0:0:0:0:1', '2020-05-16 21:05:26', '2020-05-16 21:05:25');
INSERT INTO `sys_log` VALUES ('34', null, 'GET', 'jp.co.xq.controller.LoginController.login()', '\"adm\"', '8', '0:0:0:0:0:0:0:1', '2020-05-16 21:05:32', '2020-05-16 21:05:31');
INSERT INTO `sys_log` VALUES ('35', null, 'GET', 'jp.co.xq.controller.LoginController.login()', '\"admin\"', '132', '127.0.0.1', '2020-05-16 21:07:37', '2020-05-16 21:07:37');
INSERT INTO `sys_log` VALUES ('36', 'admin', 'GET', 'jp.co.xq.controller.SysMenuController.getMenuTree()', null, '135', '127.0.0.1', '2020-05-16 21:07:38', '2020-05-16 21:07:38');
INSERT INTO `sys_log` VALUES ('37', 'admin', 'GET', 'jp.co.xq.controller.SystemController.getSystemTime()', null, '90', '127.0.0.1', '2020-05-16 21:07:39', '2020-05-16 21:07:38');
INSERT INTO `sys_log` VALUES ('38', 'admin', 'GET', 'jp.co.xq.controller.SysLogController.list()', '{\"access_token\":\"5e26aa54-e2f4-490a-855c-c60f47826e20\",\"_search\":\"false\",\"nd\":\"1589631339186\",\"limit\":\"10\",\"page\":\"1\",\"sidx\":\"id\",\"order\":\"asc\",\"_\":\"1589631338958\"}', '99', '127.0.0.1', '2020-05-16 21:15:39', '2020-05-16 21:15:39');
INSERT INTO `sys_log` VALUES ('39', 'admin', 'GET', 'jp.co.xq.controller.SysLogController.list()', '{\"access_token\":\"5e26aa54-e2f4-490a-855c-c60f47826e20\",\"_search\":\"false\",\"nd\":\"1589632005737\",\"limit\":\"10\",\"page\":\"2\",\"sidx\":\"id\",\"order\":\"asc\",\"_\":\"1589631338959\"}', '11', '127.0.0.1', '2020-05-16 21:26:46', '2020-05-16 21:26:45');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL COMMENT '親メニューID，一番トップは0',
  `name` varchar(50) NOT NULL COMMENT 'メニュー名',
  `url` varchar(200) DEFAULT NULL COMMENT 'メニューURL',
  `permission` varchar(500) DEFAULT NULL COMMENT '複数の場合カンマ区切り',
  `type` int(4) DEFAULT NULL COMMENT 'Type 0：目次 1：メニュー 2：ボタン',
  `icon` varchar(100) DEFAULT NULL COMMENT 'メニューicon',
  `order_num` int(4) DEFAULT '0' COMMENT 'sequence',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='メニュー管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', 'システム管理', null, null, '0', 'fa fa-cog', '1', '2018-06-20 11:04:05', '2018-06-20 11:04:05');
INSERT INTO `sys_menu` VALUES ('2', '0', '社員管理', '', '', '0', 'fa fa-address-card', '2', '2018-06-20 11:04:05', '2018-06-20 11:04:05');
INSERT INTO `sys_menu` VALUES ('3', '0', 'プロジェクト管理', '', '', '0', 'fa fa-bars', '3', '2018-06-20 11:04:05', '2018-06-20 11:04:05');
INSERT INTO `sys_menu` VALUES ('5', '0', 'データ分析', null, null, '0', 'fa fa-paper-plane', '4', '2018-08-03 15:31:11', '2018-08-03 15:31:11');
INSERT INTO `sys_menu` VALUES ('6', '1', 'ユーザー役柄', 'module/sys/sysrole.html', null, '1', 'fa fa-file', '5', '2018-08-02 15:11:50', '2018-08-02 15:11:50');
INSERT INTO `sys_menu` VALUES ('7', '1', 'メニュー管理', 'module/sys/sysmenu.html', null, '1', 'fa fa-paper-plane', '6', '2018-06-20 11:07:54', '2018-06-20 11:07:54');
INSERT INTO `sys_menu` VALUES ('8', '1', 'コンフィグ設定', 'module/sys/sysconfig.html', null, '1', 'fa fa-cog', '7', '2018-06-20 11:07:54', '2018-06-20 11:07:54');
INSERT INTO `sys_menu` VALUES ('9', '1', 'システムログ', 'module/sys/syslog.html', null, '1', 'fa fa-file', '99', '2018-06-20 11:07:54', '2018-06-20 11:07:54');
INSERT INTO `sys_menu` VALUES ('10', '1', 'ユーザー管理', 'module/sys/sysuser.html', null, '1', 'fa fa-file', '8', '2018-07-23 15:16:03', '2018-07-23 15:16:03');
INSERT INTO `sys_menu` VALUES ('19', '7', '検索', null, 'sys:menu:list,sys:menu:info', '2', null, '9', '2018-06-20 11:07:54', '2018-06-20 11:07:54');
INSERT INTO `sys_menu` VALUES ('20', '7', '新規', null, 'sys:menu:save,sys:menu:select', '2', null, '10', '2018-06-20 11:07:54', '2018-06-20 11:07:54');
INSERT INTO `sys_menu` VALUES ('21', '7', '更新', null, 'sys:menu:update,sys:menu:select', '2', null, '11', '2018-06-20 11:07:54', '2018-06-20 11:07:54');
INSERT INTO `sys_menu` VALUES ('22', '7', '削除', null, 'sys:menu:delete', '2', null, '12', '2018-06-20 11:07:54', '2018-06-20 11:07:54');
INSERT INTO `sys_menu` VALUES ('23', '7', 'パスワード更新', '', 'sysuser:changepassword', '2', '', '16', '2018-07-23 15:16:03', '2018-07-23 15:16:03');
INSERT INTO `sys_menu` VALUES ('26', '10', '検索', null, 'sysuser:list,sysuser:detail', '2', null, '13', '2018-07-23 15:16:03', '2018-07-23 15:16:03');
INSERT INTO `sys_menu` VALUES ('27', '10', '新規', null, 'sysuser:create', '2', null, '14', '2018-07-23 15:16:03', '2018-07-23 15:16:03');
INSERT INTO `sys_menu` VALUES ('28', '10', '更新', null, 'sysuser:update', '2', null, '15', '2018-07-23 15:16:03', '2018-07-23 15:16:03');
INSERT INTO `sys_menu` VALUES ('29', '10', '削除', null, 'sysuser:delete', '2', null, '16', '2018-07-23 15:16:03', '2018-07-23 15:16:03');
INSERT INTO `sys_menu` VALUES ('46', '5', 'charts1', 'module/charts/echarts1.html', '', '1', 'fa fa-file', '6', '2018-08-07 14:50:34', '2018-08-07 14:50:34');
INSERT INTO `sys_menu` VALUES ('47', '5', 'charts2', 'module/charts/echarts2.html', '', '1', 'fa fa-file', '6', '2018-08-07 14:50:34', '2018-08-07 14:50:34');
INSERT INTO `sys_menu` VALUES ('48', '5', 'charts3', 'module/charts/echarts3.html', '', '1', 'fa fa-file', '6', '2018-08-07 14:50:34', '2018-08-07 14:50:34');
INSERT INTO `sys_menu` VALUES ('58', '1', 'ユーザー問合せ', 'module/sys/sysuserquestion.html', null, '1', 'fa fa-file', '6', '2018-09-04 11:41:14', '2018-09-04 11:41:14');
INSERT INTO `sys_menu` VALUES ('59', '58', '検索', null, 'sysuserquestion:list,sysuserquestion:detail', '2', null, '6', '2018-09-04 11:41:14', '2018-09-04 11:41:14');
INSERT INTO `sys_menu` VALUES ('60', '58', '新規', null, 'sysuserquestion:create', '2', null, '6', '2018-09-04 11:41:14', '2018-09-04 11:41:14');
INSERT INTO `sys_menu` VALUES ('61', '58', '更新', null, 'sysuserquestion:update', '2', null, '6', '2018-09-04 11:41:14', '2018-09-04 11:41:14');
INSERT INTO `sys_menu` VALUES ('62', '58', '削除', null, 'sysuserquestion:delete', '2', null, '6', '2018-09-04 11:41:14', '2018-09-04 11:41:14');
INSERT INTO `sys_menu` VALUES ('63', '58', '回答', null, 'sysuserquestion:answer', null, null, '0', '2018-09-04 11:42:04', '2018-09-04 11:42:04');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_name` varchar(100) DEFAULT NULL COMMENT '役柄名称',
  `description` varchar(100) DEFAULT NULL COMMENT '説明',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '作成者ＩＤ',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成時間',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Role';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理員', 'スーパーユーザー', '1', '2018-08-07 17:25:40', '2018-08-17 11:26:29');
INSERT INTO `sys_role` VALUES ('2', '一般社員', '一般社員', '1', '2018-08-07 11:15:16', '2018-08-16 17:21:12');
INSERT INTO `sys_role` VALUES ('3', '限定管理', '限定管理', null, '2018-08-01 14:19:33', '2018-08-01 14:19:33');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ＩＤ',
  `role_id` bigint(20) NOT NULL COMMENT '役柄ID',
  `menu_id` bigint(20) NOT NULL COMMENT 'メニューID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=465 DEFAULT CHARSET=utf8 COMMENT='Role与メニュー関係';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('32', '5', '1');
INSERT INTO `sys_role_menu` VALUES ('33', '5', '6');
INSERT INTO `sys_role_menu` VALUES ('34', '5', '7');
INSERT INTO `sys_role_menu` VALUES ('35', '5', '19');
INSERT INTO `sys_role_menu` VALUES ('36', '5', '20');
INSERT INTO `sys_role_menu` VALUES ('37', '5', '21');
INSERT INTO `sys_role_menu` VALUES ('38', '5', '22');
INSERT INTO `sys_role_menu` VALUES ('39', '5', '8');
INSERT INTO `sys_role_menu` VALUES ('40', '5', '9');
INSERT INTO `sys_role_menu` VALUES ('41', '5', '10');
INSERT INTO `sys_role_menu` VALUES ('42', '5', '26');
INSERT INTO `sys_role_menu` VALUES ('43', '5', '27');
INSERT INTO `sys_role_menu` VALUES ('44', '5', '28');
INSERT INTO `sys_role_menu` VALUES ('45', '5', '29');
INSERT INTO `sys_role_menu` VALUES ('46', '5', '2');
INSERT INTO `sys_role_menu` VALUES ('47', '5', '30');
INSERT INTO `sys_role_menu` VALUES ('48', '5', '3');
INSERT INTO `sys_role_menu` VALUES ('49', '5', '31');
INSERT INTO `sys_role_menu` VALUES ('50', '6', '1');
INSERT INTO `sys_role_menu` VALUES ('51', '6', '6');
INSERT INTO `sys_role_menu` VALUES ('52', '6', '7');
INSERT INTO `sys_role_menu` VALUES ('53', '6', '19');
INSERT INTO `sys_role_menu` VALUES ('54', '6', '20');
INSERT INTO `sys_role_menu` VALUES ('55', '6', '21');
INSERT INTO `sys_role_menu` VALUES ('56', '6', '22');
INSERT INTO `sys_role_menu` VALUES ('57', '6', '8');
INSERT INTO `sys_role_menu` VALUES ('58', '6', '9');
INSERT INTO `sys_role_menu` VALUES ('59', '6', '10');
INSERT INTO `sys_role_menu` VALUES ('60', '6', '26');
INSERT INTO `sys_role_menu` VALUES ('61', '6', '27');
INSERT INTO `sys_role_menu` VALUES ('62', '6', '28');
INSERT INTO `sys_role_menu` VALUES ('63', '6', '29');
INSERT INTO `sys_role_menu` VALUES ('64', '7', '1');
INSERT INTO `sys_role_menu` VALUES ('65', '7', '6');
INSERT INTO `sys_role_menu` VALUES ('66', '7', '7');
INSERT INTO `sys_role_menu` VALUES ('67', '7', '19');
INSERT INTO `sys_role_menu` VALUES ('68', '7', '20');
INSERT INTO `sys_role_menu` VALUES ('69', '7', '21');
INSERT INTO `sys_role_menu` VALUES ('70', '7', '22');
INSERT INTO `sys_role_menu` VALUES ('71', '7', '8');
INSERT INTO `sys_role_menu` VALUES ('72', '7', '9');
INSERT INTO `sys_role_menu` VALUES ('73', '7', '10');
INSERT INTO `sys_role_menu` VALUES ('74', '7', '26');
INSERT INTO `sys_role_menu` VALUES ('75', '7', '27');
INSERT INTO `sys_role_menu` VALUES ('76', '7', '28');
INSERT INTO `sys_role_menu` VALUES ('77', '7', '29');
INSERT INTO `sys_role_menu` VALUES ('78', '7', '2');
INSERT INTO `sys_role_menu` VALUES ('79', '7', '30');
INSERT INTO `sys_role_menu` VALUES ('99', '8', '1');
INSERT INTO `sys_role_menu` VALUES ('100', '8', '6');
INSERT INTO `sys_role_menu` VALUES ('101', '8', '7');
INSERT INTO `sys_role_menu` VALUES ('102', '8', '19');
INSERT INTO `sys_role_menu` VALUES ('103', '8', '20');
INSERT INTO `sys_role_menu` VALUES ('104', '8', '21');
INSERT INTO `sys_role_menu` VALUES ('105', '8', '22');
INSERT INTO `sys_role_menu` VALUES ('106', '8', '8');
INSERT INTO `sys_role_menu` VALUES ('107', '8', '9');
INSERT INTO `sys_role_menu` VALUES ('108', '8', '10');
INSERT INTO `sys_role_menu` VALUES ('109', '8', '26');
INSERT INTO `sys_role_menu` VALUES ('110', '8', '27');
INSERT INTO `sys_role_menu` VALUES ('111', '8', '28');
INSERT INTO `sys_role_menu` VALUES ('112', '8', '29');
INSERT INTO `sys_role_menu` VALUES ('113', '8', '2');
INSERT INTO `sys_role_menu` VALUES ('114', '8', '30');
INSERT INTO `sys_role_menu` VALUES ('115', '8', '3');
INSERT INTO `sys_role_menu` VALUES ('116', '8', '31');
INSERT INTO `sys_role_menu` VALUES ('117', '8', '5');
INSERT INTO `sys_role_menu` VALUES ('415', '2', '2');
INSERT INTO `sys_role_menu` VALUES ('416', '2', '34');
INSERT INTO `sys_role_menu` VALUES ('417', '2', '44');
INSERT INTO `sys_role_menu` VALUES ('418', '2', '45');
INSERT INTO `sys_role_menu` VALUES ('419', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('420', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('421', '1', '7');
INSERT INTO `sys_role_menu` VALUES ('422', '1', '19');
INSERT INTO `sys_role_menu` VALUES ('423', '1', '20');
INSERT INTO `sys_role_menu` VALUES ('424', '1', '21');
INSERT INTO `sys_role_menu` VALUES ('425', '1', '22');
INSERT INTO `sys_role_menu` VALUES ('426', '1', '8');
INSERT INTO `sys_role_menu` VALUES ('427', '1', '9');
INSERT INTO `sys_role_menu` VALUES ('428', '1', '10');
INSERT INTO `sys_role_menu` VALUES ('429', '1', '26');
INSERT INTO `sys_role_menu` VALUES ('430', '1', '27');
INSERT INTO `sys_role_menu` VALUES ('431', '1', '28');
INSERT INTO `sys_role_menu` VALUES ('432', '1', '29');
INSERT INTO `sys_role_menu` VALUES ('433', '1', '35');
INSERT INTO `sys_role_menu` VALUES ('434', '1', '36');
INSERT INTO `sys_role_menu` VALUES ('435', '1', '37');
INSERT INTO `sys_role_menu` VALUES ('436', '1', '38');
INSERT INTO `sys_role_menu` VALUES ('437', '1', '40');
INSERT INTO `sys_role_menu` VALUES ('438', '1', '41');
INSERT INTO `sys_role_menu` VALUES ('439', '1', '42');
INSERT INTO `sys_role_menu` VALUES ('440', '1', '43');
INSERT INTO `sys_role_menu` VALUES ('441', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('442', '1', '30');
INSERT INTO `sys_role_menu` VALUES ('443', '1', '34');
INSERT INTO `sys_role_menu` VALUES ('444', '1', '39');
INSERT INTO `sys_role_menu` VALUES ('445', '1', '44');
INSERT INTO `sys_role_menu` VALUES ('446', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('447', '1', '31');
INSERT INTO `sys_role_menu` VALUES ('448', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('449', '1', '46');
INSERT INTO `sys_role_menu` VALUES ('450', '1', '47');
INSERT INTO `sys_role_menu` VALUES ('451', '1', '48');
INSERT INTO `sys_role_menu` VALUES ('452', '1', '49');
INSERT INTO `sys_role_menu` VALUES ('453', '1', '50');
INSERT INTO `sys_role_menu` VALUES ('454', '1', '51');
INSERT INTO `sys_role_menu` VALUES ('455', '1', '52');
INSERT INTO `sys_role_menu` VALUES ('456', '1', '53');
INSERT INTO `sys_role_menu` VALUES ('457', '1', '54');
INSERT INTO `sys_role_menu` VALUES ('458', '1', '55');
INSERT INTO `sys_role_menu` VALUES ('459', '1', '58');
INSERT INTO `sys_role_menu` VALUES ('460', '1', '59');
INSERT INTO `sys_role_menu` VALUES ('461', '1', '60');
INSERT INTO `sys_role_menu` VALUES ('462', '1', '61');
INSERT INTO `sys_role_menu` VALUES ('463', '1', '62');
INSERT INTO `sys_role_menu` VALUES ('464', '1', '63');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) NOT NULL COMMENT 'ユーザー名',
  `password` varchar(100) DEFAULT NULL COMMENT 'パスワード',
  `salt` varchar(20) DEFAULT NULL COMMENT 'salt',
  `email` varchar(100) DEFAULT NULL COMMENT 'メール',
  `mobile` varchar(100) DEFAULT NULL COMMENT '携帯番号',
  `status` int(4) DEFAULT NULL COMMENT '0：禁止 1：正常　2:限定',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '作成者ＩＤ',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成時間',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5370 DEFAULT CHARSET=utf8 COMMENT='システムユーザー';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '929769f9179bcac28bef08b9c8586bd6ddd7687d', '6fZP1GtLUUzETCOINrYk', 'admin1@yukaku.com', '08033908667', '1', '1', '2018-06-20 11:03:12', '2018-06-20 11:03:12');

-- ----------------------------
-- Table structure for sys_user_question
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_question`;
CREATE TABLE `sys_user_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'システムユーザーＩＤ',
  `type` varchar(50) DEFAULT NULL COMMENT '問合せタイプ',
  `title` varchar(100) DEFAULT NULL COMMENT 'タイトル',
  `question_content` varchar(1000) DEFAULT NULL COMMENT '問合せ内容',
  `answer_content` varchar(255) DEFAULT NULL COMMENT '回答内容',
  `answer_user_id` bigint(20) DEFAULT NULL COMMENT '回答者ＩＤ',
  `status` int(4) DEFAULT '0' COMMENT '状態 0:未回答 1:回答済み 2:削除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成時間',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='ユーザー問合せ';

-- ----------------------------
-- Records of sys_user_question
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT 'ユーザーID',
  `role_id` bigint(20) NOT NULL COMMENT '役柄ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=844 DEFAULT CHARSET=utf8 COMMENT='ユーザーとRole関係';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('5', '2', '1');
INSERT INTO `sys_user_role` VALUES ('6', '3', '1');
INSERT INTO `sys_user_role` VALUES ('7', '4', '1');
INSERT INTO `sys_user_role` VALUES ('8', '5', '1');
INSERT INTO `sys_user_role` VALUES ('9', '6', '1');
INSERT INTO `sys_user_role` VALUES ('10', '7', '1');
INSERT INTO `sys_user_role` VALUES ('12', '8', '2');
INSERT INTO `sys_user_role` VALUES ('13', '11', '2');
