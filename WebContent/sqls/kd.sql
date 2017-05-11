/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : kd

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-05-03 17:16:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_class
-- ----------------------------
DROP TABLE IF EXISTS `base_class`;
CREATE TABLE `base_class` (
  `PK_BASE_CLASS` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `CLASS_ID` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '菜单编码',
  `CLASS_NAME` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '菜单名称',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_BASE_CLASS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of base_class
-- ----------------------------
INSERT INTO `base_class` VALUES ('0b059a76-f1b7-4253-a9ea-7e235e889078', 'PAY_TYPE', '支付方式', null, '1', '2017-04-27 20:00:22', '管理员', '2017-04-27 20:00:22', '管理员');
INSERT INTO `base_class` VALUES ('10', 'BILL_STATUS', '单据状态', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_class` VALUES ('14', 'ROOM_STATUS', '房间状态', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_class` VALUES ('20', 'PSN_STATUS', '人员状态', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_class` VALUES ('28', 'PSN_GENDER', '性别', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_class` VALUES ('31', 'PSN_TYPE', '员工性质', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_class` VALUES ('69', 'MEM_LEVAL', '会员等级', null, '1', '2017-04-25 22:22:38', '管理员', '2017-04-25 22:22:38', '管理员');
INSERT INTO `base_class` VALUES ('75', 'BILL_SOURCE', '单据来源', null, '1', '2017-04-25 22:40:48', '管理员', '2017-04-25 22:41:38', '管理员');
INSERT INTO `base_class` VALUES ('84', 'CHARGE_STATUS', '结算状态', null, '1', '2017-04-26 21:53:29', '管理员', '2017-04-26 21:53:29', '管理员');

-- ----------------------------
-- Table structure for base_code
-- ----------------------------
DROP TABLE IF EXISTS `base_code`;
CREATE TABLE `base_code` (
  `PK_BASE_CODE` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `CODE_ID` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编码',
  `CODE_NAME` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `CODE_CLASS` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编码类别',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_BASE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of base_code
-- ----------------------------
INSERT INTO `base_code` VALUES ('11', '01', '初稿', 'BILL_STATUS', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_code` VALUES ('12', '02', '审核', 'BILL_STATUS', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_code` VALUES ('13', '03', '记账', 'BILL_STATUS', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_code` VALUES ('15', '01', '空闲中', 'ROOM_STATUS', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_code` VALUES ('16', '02', '使用中', 'ROOM_STATUS', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_code` VALUES ('17', '03', '打扫中', 'ROOM_STATUS', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_code` VALUES ('18', '11', '修理', 'ROOM_STATUS', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_code` VALUES ('19', '12', '装修', 'ROOM_STATUS', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_code` VALUES ('19ab4fa7-4017-4394-a75b-004facd8b6a6', '03', '支付宝', 'PAY_TYPE', null, '1', '2017-04-27 20:00:56', '管理员', '2017-04-27 20:00:56', '管理员');
INSERT INTO `base_code` VALUES ('21', '01', '空闲', 'PSN_STATUS', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_code` VALUES ('22', '02', '工作', 'PSN_STATUS', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_code` VALUES ('23', '03', '请假', 'PSN_STATUS', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `base_code` VALUES ('29', '1', '男', 'PSN_GENDER', null, '1', '2017-04-25 20:22:08', '管理员', '2017-04-25 20:22:08', '管理员');
INSERT INTO `base_code` VALUES ('30', '0', '女', 'PSN_GENDER', null, '1', '2017-04-25 20:22:08', '管理员', '2017-04-25 20:22:08', '管理员');
INSERT INTO `base_code` VALUES ('32', '01', '服务员', 'PSN_TYPE', null, '1', '2017-04-25 20:25:28', '管理员', '2017-04-25 20:25:28', '管理员');
INSERT INTO `base_code` VALUES ('33', '02', '技师', 'PSN_TYPE', null, '1', '2017-04-25 20:25:28', '管理员', '2017-04-25 20:25:28', '管理员');
INSERT INTO `base_code` VALUES ('34', '03', '收银员', 'PSN_TYPE', null, '1', '2017-04-25 20:25:28', '管理员', '2017-04-25 20:25:28', '管理员');
INSERT INTO `base_code` VALUES ('488c6a9f-e563-4333-bc21-68dc1dc0f9ec', '01', '现金', 'PAY_TYPE', null, '1', '2017-04-27 20:00:56', '管理员', '2017-04-27 20:00:56', '管理员');
INSERT INTO `base_code` VALUES ('6c365646-2b5c-4c2a-a809-b152d1312ee3', '04', '刷卡', 'PAY_TYPE', null, '1', '2017-04-27 20:00:56', '管理员', '2017-04-27 20:00:56', '管理员');
INSERT INTO `base_code` VALUES ('70', '01', '普通会员', 'MEM_LEVAL', null, '1', '2017-04-25 22:23:22', '管理员', '2017-04-25 22:23:22', '管理员');
INSERT INTO `base_code` VALUES ('71', '02', '白金会员', 'MEM_LEVAL', null, '1', '2017-04-25 22:23:23', '管理员', '2017-04-25 22:23:23', '管理员');
INSERT INTO `base_code` VALUES ('72', '03', '黄金会员', 'MEM_LEVAL', null, '1', '2017-04-25 22:23:23', '管理员', '2017-04-25 22:23:23', '管理员');
INSERT INTO `base_code` VALUES ('73', '04', '钻石会员', 'MEM_LEVAL', null, '1', '2017-04-25 22:23:23', '管理员', '2017-04-25 22:23:23', '管理员');
INSERT INTO `base_code` VALUES ('76', '01', '会员消费', 'BILL_SOURCE', null, '1', '2017-04-25 22:41:31', '管理员', '2017-05-02 20:36:45', '管理员');
INSERT INTO `base_code` VALUES ('82', '08', '预约', 'ROOM_STATUS', null, '1', '2017-04-25 23:45:54', '管理员', '2017-04-25 23:45:54', '管理员');
INSERT INTO `base_code` VALUES ('85', '01', '未结账', 'CHARGE_STATUS', null, '1', '2017-04-26 21:53:41', '管理员', '2017-04-27 00:18:23', '管理员');
INSERT INTO `base_code` VALUES ('86', '02', '已结账', 'CHARGE_STATUS', null, '1', '2017-04-26 21:53:41', '管理员', '2017-04-27 00:18:23', '管理员');
INSERT INTO `base_code` VALUES ('bdfe9997-4d90-4480-bf18-1a6a66929447', '02', '微信', 'PAY_TYPE', null, '1', '2017-04-27 20:00:56', '管理员', '2017-04-27 20:00:56', '管理员');
INSERT INTO `base_code` VALUES ('c304bb69-073c-40e2-acf2-588556ff228e', '11', '余额抵扣', 'PAY_TYPE', null, '1', '2017-05-02 20:36:04', '管理员', '2017-05-02 20:36:04', '管理员');
INSERT INTO `base_code` VALUES ('ce38da80-86a5-47f9-9835-1be2d0ce404b', '02', '游客消费', 'BILL_SOURCE', null, '1', '2017-04-28 01:38:58', '管理员', '2017-05-02 20:36:45', '管理员');

-- ----------------------------
-- Table structure for kd_balance
-- ----------------------------
DROP TABLE IF EXISTS `kd_balance`;
CREATE TABLE `kd_balance` (
  `PK_KD_BALANCE` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `PK_KD_MEMBER` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '会员',
  `PK_KD_ROOM` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '房间号',
  `BILL_NO` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '单据编号',
  `BILL_DATE` date DEFAULT NULL COMMENT '单据日期',
  `AMT` double DEFAULT NULL COMMENT '应付金额',
  `REAL_AMT` double DEFAULT NULL COMMENT '实付金额',
  `PAY_TYPE` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付方式',
  `IS_MEMBER` int(1) DEFAULT '0' COMMENT '是否会员',
  `IS_DEDUCTION` int(1) DEFAULT '0' COMMENT '是否使用抵用券',
  `BILL_STATUS` varchar(2) COLLATE utf8_unicode_ci DEFAULT '01' COMMENT '单据状态',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_KD_BALANCE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of kd_balance
-- ----------------------------
INSERT INTO `kd_balance` VALUES ('3dea24d2-efaa-404a-9ac2-2c187cbe1df6', '74', '44', null, '2017-05-02', '80', '0', '11', '1', '1', '01', null, '1', '2017-05-02 21:19:52', '管理员', '2017-05-02 21:19:52', '管理员');
INSERT INTO `kd_balance` VALUES ('452876e4-8ac4-49fc-b926-24df8d8ddf6c', null, '44', null, '2017-05-02', null, '80', '01', '0', '0', '01', null, '1', '2017-05-02 21:11:34', '管理员', '2017-05-02 21:11:34', '管理员');

-- ----------------------------
-- Table structure for kd_balroom
-- ----------------------------
DROP TABLE IF EXISTS `kd_balroom`;
CREATE TABLE `kd_balroom` (
  `PK_KD_BALROOM` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `PK_KD_ROOM` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '会员',
  `PK_KD_CHARGEITEM` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '消费项目',
  `NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '消费项目',
  `PRICE` double DEFAULT NULL COMMENT '单价',
  `MEM_PRICE` double DEFAULT NULL COMMENT '会员价格',
  `QTY` int(11) DEFAULT NULL COMMENT '数量',
  `CHARGE_STATUS` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '结算状态',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `PK_KD_PERSON` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '技师',
  `PK_KD_BALANCE` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '房间账单汇总',
  PRIMARY KEY (`PK_KD_BALROOM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of kd_balroom
-- ----------------------------
INSERT INTO `kd_balroom` VALUES ('21952df2-2751-4746-9bcd-a0307b926598', '44', '55', '足浴', '80', '60', '1', '02', null, '1', '2017-05-02 21:16:41', '管理员', '2017-05-02 21:19:52', '管理员', '35', 'e6e01dec-339e-4db8-ab38-bbf57ce55f87');
INSERT INTO `kd_balroom` VALUES ('6a629303-c298-4a69-a343-0c1e2a571be4', '44', '55', '足浴', '80', '60', '1', '02', null, '1', '2017-05-02 21:11:29', '管理员', '2017-05-02 21:11:33', '管理员', '81', '5d158854-28b8-46f2-a2e8-1bff1bcb7575');

-- ----------------------------
-- Table structure for kd_chargeitem
-- ----------------------------
DROP TABLE IF EXISTS `kd_chargeitem`;
CREATE TABLE `kd_chargeitem` (
  `PK_KD_CHARGEITEM` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编码',
  `NAME` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `DES` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `PRICE` double(5,2) DEFAULT NULL COMMENT '单价',
  `MEM_PRICE` double(5,2) DEFAULT NULL COMMENT '会员价格',
  `NEED_PSN` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '需要技师',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_KD_CHARGEITEM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of kd_chargeitem
-- ----------------------------
INSERT INTO `kd_chargeitem` VALUES ('55', '001', '足浴', null, '80.00', '60.00', '1', null, '1', '2017-04-25 21:54:04', '管理员', '2017-04-26 23:53:12', '管理员');
INSERT INTO `kd_chargeitem` VALUES ('64', '002', '小包厢欢唱', null, '120.00', '120.00', '0', null, '1', '2017-04-25 22:17:23', '管理员', '2017-04-26 23:59:15', '管理员');
INSERT INTO `kd_chargeitem` VALUES ('96', '003', '水果', null, '20.00', '15.00', '0', null, '1', '2017-04-26 23:54:04', '管理员', '2017-04-26 23:54:04', '管理员');

-- ----------------------------
-- Table structure for kd_coupon
-- ----------------------------
DROP TABLE IF EXISTS `kd_coupon`;
CREATE TABLE `kd_coupon` (
  `PK_KD_COUPON` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编码',
  `NAME` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `AMT` double DEFAULT NULL COMMENT '金额',
  `DES` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_KD_COUPON`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of kd_coupon
-- ----------------------------
INSERT INTO `kd_coupon` VALUES ('54', '001', '充值600活动', '600', '充值满600元，赠送1次足浴', null, '1', '2017-04-25 21:48:57', '管理员', '2017-04-27 22:49:36', '管理员');
INSERT INTO `kd_coupon` VALUES ('60', '002', '充值1000活动', '1000', '充值满1000元，赠送2次足浴活动', null, '1', '2017-04-25 22:11:03', '管理员', '2017-04-27 22:49:41', '管理员');
INSERT INTO `kd_coupon` VALUES ('63', '003', '充值2000活动', '2000', '充值满2000元，赠送3次足浴，1张小包厢欢唱券', null, '1', '2017-04-25 22:16:59', '管理员', '2017-04-27 22:49:47', '管理员');

-- ----------------------------
-- Table structure for kd_coupon_item
-- ----------------------------
DROP TABLE IF EXISTS `kd_coupon_item`;
CREATE TABLE `kd_coupon_item` (
  `PK_KD_COUPON_ITEM` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `PK_KD_COUPON` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '优惠券主键',
  `PK_KD_CHARGEITEM` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收费项目',
  `QTY` int(11) DEFAULT '1' COMMENT '次数',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_KD_COUPON_ITEM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of kd_coupon_item
-- ----------------------------
INSERT INTO `kd_coupon_item` VALUES ('59', '54', '55', '1', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `kd_coupon_item` VALUES ('61', '60', '55', '2', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `kd_coupon_item` VALUES ('65', '63', '55', '2', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `kd_coupon_item` VALUES ('66', '63', '64', '1', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');

-- ----------------------------
-- Table structure for kd_member
-- ----------------------------
DROP TABLE IF EXISTS `kd_member`;
CREATE TABLE `kd_member` (
  `PK_KD_MEMBER` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编号',
  `NAME` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '姓名',
  `SIMPLE_NAME` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名字简拼',
  `PHONE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号',
  `IDCARD` varchar(18) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '身份证',
  `GENDER` int(1) DEFAULT '1' COMMENT '性别 0 女 1男',
  `BIRTHDAY` date DEFAULT NULL COMMENT '生日',
  `LEVAL` varchar(2) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '会员等级',
  `CARD_AMT` double(10,2) DEFAULT NULL COMMENT '卡剩余金额',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_KD_MEMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of kd_member
-- ----------------------------
INSERT INTO `kd_member` VALUES ('74', '001', '李四', 'LS', '12345678903', '320220199001011234', '1', '1990-01-01', '01', '6380.00', null, '1', '2017-04-25 22:33:54', '管理员', '2017-04-25 22:33:54', '管理员');
INSERT INTO `kd_member` VALUES ('80', '000', '游客', null, null, null, '1', null, '01', '0.00', null, '1', '2017-04-25 23:04:39', '管理员', '2017-04-25 23:04:39', '管理员');

-- ----------------------------
-- Table structure for kd_member_pou
-- ----------------------------
DROP TABLE IF EXISTS `kd_member_pou`;
CREATE TABLE `kd_member_pou` (
  `PK_KD_MEMBER_POU` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `PK_KD_MEMBER` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '会员',
  `PK_KD_CHARGEITEM` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '消费项目',
  `CAN_USED` int(1) DEFAULT '1' COMMENT '是否可使用',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_KD_MEMBER_POU`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of kd_member_pou
-- ----------------------------
INSERT INTO `kd_member_pou` VALUES ('1', '74', '55', '0', null, '1', null, null, '2017-04-28 01:06:48', '管理员');
INSERT INTO `kd_member_pou` VALUES ('2', '74', '55', '0', null, '1', null, null, '2017-04-28 01:06:48', '管理员');
INSERT INTO `kd_member_pou` VALUES ('2e7098c1-1870-486b-8a63-c2fdc04e47b1', '74', '55', '1', null, '1', null, null, '2017-04-28 01:06:48', '管理员');
INSERT INTO `kd_member_pou` VALUES ('3', '74', '55', '1', null, '1', null, null, '2017-04-28 01:06:48', '管理员');

-- ----------------------------
-- Table structure for kd_person
-- ----------------------------
DROP TABLE IF EXISTS `kd_person`;
CREATE TABLE `kd_person` (
  `PK_KD_PERSON` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编号',
  `NAME` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '姓名',
  `GENDER` int(1) DEFAULT NULL COMMENT '性别 0女 1男',
  `IDCARD` varchar(18) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '身份证',
  `PHONE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号',
  `BIRTH_PLACE` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '籍贯',
  `ADDRESS` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '家庭住址',
  `ENTER_DATE` date DEFAULT NULL COMMENT '入职时间',
  `TYPE` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '员工性质',
  `STATUS` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '员工状态',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_KD_PERSON`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of kd_person
-- ----------------------------
INSERT INTO `kd_person` VALUES ('35', '001', '张三', '0', '320281199001012013', '12345678901', '江苏无锡', '江苏省江阴市XXXXXXXXXXXXXXXXXXXXXXXXXX', '2017-04-25', '02', '01', '111', '1', '2017-04-25 20:34:20', '管理员', '2017-04-25 20:34:27', '管理员');
INSERT INTO `kd_person` VALUES ('37', '002', '李四', '0', null, null, null, null, null, '02', '01', null, '1', '2017-04-25 20:36:17', '管理员', '2017-04-25 20:36:17', '管理员');
INSERT INTO `kd_person` VALUES ('81', '003', '王五', '0', null, null, null, null, null, '02', '01', null, '1', '2017-04-25 23:40:54', '管理员', '2017-04-25 23:40:54', '管理员');
INSERT INTO `kd_person` VALUES ('83', '004', '赵六', '0', null, null, null, null, null, '02', '03', null, '1', '2017-04-25 23:51:04', '管理员', '2017-04-25 23:51:04', '管理员');
INSERT INTO `kd_person` VALUES ('84', '007', '钱九', '1', null, null, null, null, null, '02', '01', null, '1', null, null, null, null);
INSERT INTO `kd_person` VALUES ('85', '006', '周八', '0', null, null, null, null, null, '02', '01', null, '1', null, null, null, null);
INSERT INTO `kd_person` VALUES ('86', '005', '孙七', '0', null, null, null, null, null, '02', '01', null, '1', null, null, null, null);

-- ----------------------------
-- Table structure for kd_psnwork
-- ----------------------------
DROP TABLE IF EXISTS `kd_psnwork`;
CREATE TABLE `kd_psnwork` (
  `PK_KD_PSNWORK` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `BILL_DATE` date DEFAULT NULL COMMENT '日期',
  `PK_KD_PERSON` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '人员',
  `PK_KD_ROOM` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '房间',
  `IS_WORK` int(1) DEFAULT '1' COMMENT '正在工作',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_KD_PSNWORK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of kd_psnwork
-- ----------------------------
INSERT INTO `kd_psnwork` VALUES ('94', null, '35', '44', '1', null, '1', null, null, null, null);
INSERT INTO `kd_psnwork` VALUES ('95', null, '37', '44', '1', null, '1', null, null, null, null);

-- ----------------------------
-- Table structure for kd_room
-- ----------------------------
DROP TABLE IF EXISTS `kd_room`;
CREATE TABLE `kd_room` (
  `PK_KD_ROOM` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编码',
  `NAME` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `STATUS` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '状态',
  `GUSTS` int(4) DEFAULT NULL COMMENT '宾客数量',
  `ORDER_TIME` time(6) DEFAULT NULL COMMENT '预约时间',
  `USE_TIME` time(6) DEFAULT NULL COMMENT '使用时间',
  `BED_COUNT` int(3) DEFAULT NULL COMMENT '床位数量',
  `FLOOR_CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '楼层',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_KD_ROOM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of kd_room
-- ----------------------------
INSERT INTO `kd_room` VALUES ('44', '001', '包厢01', '01', '0', null, null, '3', '3楼', '', '1', '2017-04-25 21:02:09', '管理员', '2017-05-02 21:16:34', '管理员');
INSERT INTO `kd_room` VALUES ('45', '002', '包厢02', '08', '1', '20:11:27.000000', null, '3', '3楼', null, '1', '2017-04-25 21:03:42', '管理员', '2017-05-02 20:11:29', '管理员');
INSERT INTO `kd_room` VALUES ('47', '003', '包厢03', '01', '0', null, null, '3', '3楼', null, '1', '2017-04-25 21:05:23', '管理员', '2017-05-02 20:11:32', '管理员');
INSERT INTO `kd_room` VALUES ('48', '004', '包厢04', '01', null, null, '00:00:00.000000', '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-25 21:13:47', '管理员');
INSERT INTO `kd_room` VALUES ('49', '005', '包厢05', '01', '0', null, null, '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-26 00:39:56', '管理员');
INSERT INTO `kd_room` VALUES ('50', '006', '包厢06', '08', '1', '21:36:45.000000', '00:00:00.000000', '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-26 21:36:49', '管理员');
INSERT INTO `kd_room` VALUES ('51', '007', '包厢07', '01', null, null, '00:00:00.000000', '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-25 21:13:47', '管理员');
INSERT INTO `kd_room` VALUES ('52', '008', '包厢08', '01', null, null, '00:00:00.000000', '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-25 21:13:47', '管理员');
INSERT INTO `kd_room` VALUES ('53', '009', '包厢09', '01', null, null, '00:00:00.000000', '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-25 21:13:47', '管理员');
INSERT INTO `kd_room` VALUES ('54', '010', '包厢10', '01', null, null, '00:00:00.000000', '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-25 21:13:47', '管理员');
INSERT INTO `kd_room` VALUES ('55', '011', '包厢11', '01', null, null, '00:00:00.000000', '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-25 21:13:47', '管理员');
INSERT INTO `kd_room` VALUES ('56', '012', '包厢12', '01', null, null, '00:00:00.000000', '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-25 21:13:47', '管理员');
INSERT INTO `kd_room` VALUES ('57', '013', '包厢13', '01', null, null, '00:00:00.000000', '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-25 21:13:47', '管理员');
INSERT INTO `kd_room` VALUES ('58', '014', '包厢14', '01', '0', null, null, '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-26 00:40:01', '管理员');
INSERT INTO `kd_room` VALUES ('59', '015', '包厢15', '01', null, null, '00:00:00.000000', '3', '3楼', null, '1', '2017-04-25 21:13:47', '管理员', '2017-04-25 23:24:39', '管理员');

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `CURRENT` int(11) NOT NULL DEFAULT '1',
  `STEP` int(11) DEFAULT '1',
  `SEQ_NAME` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('103', '1', 'SYS_DOC_ID');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `PK_SYS_MENU` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `MENU_CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '菜单编码',
  `MENU_NAME` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '菜单名称',
  `MENU_URL` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '资源链接',
  `ICONCLASS` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图标',
  `IS_NAVIGATION` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '是否导航菜单',
  `IS_SHOW` varchar(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT '1',
  `PK_PARENT_MENU` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '父菜单主键',
  `MENU_ORDER` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '序号',
  `MEMO` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`PK_SYS_MENU`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('0b593877-59cc-41d3-9459-90303bbd6a33', null, '账单查询', null, 'fa fa-bookmark', 'Y', '1', null, '3', null, '1', '2017-04-28 23:48:23', '管理员', '2017-04-28 23:48:23', '管理员');
INSERT INTO `sys_menu` VALUES ('2', null, '系统维护', null, 'fa fa-bookmark', 'Y', '1', null, '1', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `sys_menu` VALUES ('25', null, '基础信息', null, 'fa fa-bookmark', 'Y', '1', null, '2', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `sys_menu` VALUES ('26', null, '员工管理', 'aero.kd.view.base.KdPerson.d', null, 'N', 'Y', '25', '21', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `sys_menu` VALUES ('38', null, '包厢管理', 'aero.kd.view.base.KdRoom.d', null, 'N', 'Y', '25', '22', null, '1', '2017-04-25 20:58:38', '管理员', '2017-04-25 20:58:49', '管理员');
INSERT INTO `sys_menu` VALUES ('39', null, '收费管理', 'aero.kd.view.base.KdChargeItem.d', null, 'N', 'Y', '25', '23', null, '1', '2017-04-25 20:58:38', '管理员', '2017-04-25 20:58:49', '管理员');
INSERT INTO `sys_menu` VALUES ('4', null, '菜单维护', 'aero.framework.view.SysMenu.d', null, 'N', 'Y', '2', '11', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `sys_menu` VALUES ('40', null, '活动管理', 'aero.kd.view.base.KdCoupon.d', null, 'N', 'Y', '25', '24', null, '1', '2017-04-25 20:58:38', '管理员', '2017-04-27 00:27:32', '管理员');
INSERT INTO `sys_menu` VALUES ('5', null, '用户维护', 'aero.framework.view.SysUser.d', null, 'N', 'Y', '2', '12', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `sys_menu` VALUES ('6', null, '通用数据编码', 'aero.framework.view.BaseCode.d', null, 'N', 'Y', '2', '13', null, '1', '2017-04-25 00:00:00', '管理员', '2017-04-25 00:00:00', '管理员');
INSERT INTO `sys_menu` VALUES ('67', null, '会员管理', 'aero.kd.view.base.KdMember.d', null, 'N', '1', '25', '25', null, '1', '2017-04-25 22:18:31', '管理员', '2017-04-25 22:18:31', '管理员');
INSERT INTO `sys_menu` VALUES ('6f72113a-2e36-4ae2-86f8-c9797a3f3e41', null, '消费清单查询', 'aero.kd.view.biz.KdBalance.d', null, 'N', '1', '0b593877-59cc-41d3-9459-90303bbd6a33', '31', null, '1', '2017-04-28 23:49:05', '管理员', '2017-04-28 23:49:05', '管理员');
INSERT INTO `sys_menu` VALUES ('cad8493e-0aea-4410-a553-d404af14a9e0', null, '每日消费报表', 'aero.kd.view.biz.KdBalShow.d', null, 'N', '1', '0b593877-59cc-41d3-9459-90303bbd6a33', '32', null, '1', '2017-05-02 21:05:44', '管理员', '2017-05-02 21:05:44', '管理员');

-- ----------------------------
-- Table structure for sys_menu_his
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_his`;
CREATE TABLE `sys_menu_his` (
  `PK_SYS_MENU_HIS` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `PK_SYS_MENU` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '菜单主键',
  `PK_SYS_USER` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '人员PK',
  `COUNT` int(10) DEFAULT NULL COMMENT '点击次数',
  `LAST_CLICK_TIME` datetime DEFAULT NULL COMMENT '最后点击时间',
  PRIMARY KEY (`PK_SYS_MENU_HIS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_menu_his
-- ----------------------------
INSERT INTO `sys_menu_his` VALUES ('0b511a69-e3f8-41ff-8d6b-fd87e3214f36', 'f5afe629-c36f-4ba1-b804-d0743904d4f6', '1', null, '2017-04-29 14:46:32');
INSERT INTO `sys_menu_his` VALUES ('1b94c48d-3c89-4019-a172-6cd7b73697c5', '6f72113a-2e36-4ae2-86f8-c9797a3f3e41', '1', null, '2017-05-03 00:03:22');
INSERT INTO `sys_menu_his` VALUES ('27', '26', '1', null, '2017-04-28 23:19:18');
INSERT INTO `sys_menu_his` VALUES ('41', '38', '1', null, '2017-04-26 00:39:50');
INSERT INTO `sys_menu_his` VALUES ('49', '39', '1', null, '2017-04-26 23:59:32');
INSERT INTO `sys_menu_his` VALUES ('50', '40', '1', null, '2017-04-27 22:58:23');
INSERT INTO `sys_menu_his` VALUES ('68', '67', '1', null, '2017-05-02 20:35:21');
INSERT INTO `sys_menu_his` VALUES ('7', '4', '1', null, '2017-05-02 21:04:55');
INSERT INTO `sys_menu_his` VALUES ('7daa50dc-c378-46bd-a46c-b552983b085b', '3f5599e5-f154-43cf-a984-d7da4897fec0', '1', null, '2017-04-29 14:46:25');
INSERT INTO `sys_menu_his` VALUES ('8', '5', '1', null, '2017-04-25 19:06:54');
INSERT INTO `sys_menu_his` VALUES ('812edaee-7967-4b46-8146-9160cc045e63', 'cad8493e-0aea-4410-a553-d404af14a9e0', '1', null, '2017-05-03 00:03:24');
INSERT INTO `sys_menu_his` VALUES ('9', '6', '1', null, '2017-05-02 20:44:43');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID_` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `USER_` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账号',
  `NAME_` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '姓名',
  `PASSWORD_` varchar(400) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '激活',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '管理员', 'YWRtaW4=', '1', '2017-04-24 00:00:00', null, null, null);
