/*
 Navicat Premium Data Transfer

 Source Server         : face
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 101.35.253.56:3306
 Source Schema         : face-transplant

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 01/10/2024 00:26:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for biz_directory
-- ----------------------------
DROP TABLE IF EXISTS `biz_directory`;
CREATE TABLE `biz_directory`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '目录id',
  `sup_directory_id` bigint(20) NULL DEFAULT 0 COMMENT '上级目录id',
  `directory_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录图片url',
  `directory_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录名称',
  `directory_level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录级别（1-一级目录，2-二级目录，3-三级目录）',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '普通积分',
  `price_rew` decimal(10, 2) NULL DEFAULT NULL COMMENT '奖励积分',
  `directory_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '标签（主要是三级目录）',
  `usage_times` int(20) NULL DEFAULT 0 COMMENT '使用次数',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `number_foun` int(20) NULL DEFAULT 0 COMMENT '基础图片数量',
  `number_rew` int(20) NULL DEFAULT 0 COMMENT '奖励图片数量',
  `introduce` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '目录介绍（主要是三级目录）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '目录列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_integral_log
-- ----------------------------
DROP TABLE IF EXISTS `biz_integral_log`;
CREATE TABLE `biz_integral_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pay_money` bigint(10) NOT NULL DEFAULT 0 COMMENT '支付金额',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `ord_integral` int(20) NULL DEFAULT 0 COMMENT '普通积分',
  `rew_integral` int(20) NULL DEFAULT 0 COMMENT '奖励积分',
  `user_id` bigint(20) NOT NULL COMMENT '所属用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 253 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '积分变动日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_lbt
-- ----------------------------
DROP TABLE IF EXISTS `biz_lbt`;
CREATE TABLE `biz_lbt`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片链接',
  `caption` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '轮播图表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_merge_img
-- ----------------------------
DROP TABLE IF EXISTS `biz_merge_img`;
CREATE TABLE `biz_merge_img`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '融合图片id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `img_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传图片url',
  `model_id` bigint(20) NULL DEFAULT NULL COMMENT '模板图片id',
  `merge_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '融合图片url',
  `merge_time` datetime(0) NULL DEFAULT NULL COMMENT '融合时间',
  `merge_state` tinyint(3) NULL DEFAULT NULL COMMENT '融合状态（0-融合中，1-融合成功，2-融合失败）',
  `merge_list_id` bigint(20) NULL DEFAULT NULL COMMENT '融合组id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3989 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '融合图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_merge_list
-- ----------------------------
DROP TABLE IF EXISTS `biz_merge_list`;
CREATE TABLE `biz_merge_list`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `merge_state` tinyint(3) NULL DEFAULT NULL COMMENT '融合状态（0-融合中，1-融合成功，2-待生成）',
  `merge_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '融合前图片id集合',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '融合成功时间',
  `url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '融合代表图',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '普通积分',
  `price_rew` decimal(10, 2) NULL DEFAULT NULL COMMENT '奖励积分',
  `directory_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '标签（0-单人，1-多人）',
  `directory_id` bigint(20) NULL DEFAULT NULL COMMENT '目录id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '融合组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_order
-- ----------------------------
DROP TABLE IF EXISTS `biz_order`;
CREATE TABLE `biz_order`  (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单号',
  `order_no` varbinary(32) NOT NULL COMMENT '订单流水Id',
  `user_id` bigint(20) NOT NULL COMMENT '支付用户',
  `version` int(10) NULL DEFAULT 0 COMMENT '订单版本号',
  `meal_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '套餐Code',
  `pay_result` tinyint(1) NOT NULL DEFAULT 0 COMMENT '支付结果(0:待支付，1:已支付)',
  `pay_way` tinyint(1) NOT NULL DEFAULT 1 COMMENT '支付方式(1:微信支付)',
  `pay_money` bigint(10) NOT NULL COMMENT '支付金额',
  `transaction_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信支付订单号',
  `create_time` datetime(0) NOT NULL COMMENT '支付时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `entry` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问入口',
  `client` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端',
  `valid_day` datetime(0) NULL DEFAULT NULL COMMENT '有效时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '支付记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_photo_album
-- ----------------------------
DROP TABLE IF EXISTS `biz_photo_album`;
CREATE TABLE `biz_photo_album`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户id',
  `img_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片地址',
  `usage_times` int(20) NULL DEFAULT 0 COMMENT '使用次数',
  `figure_tag` bigint(20) NOT NULL COMMENT '身材标签（0-正常，1-胖，2-瘦）',
  `directory_id` bigint(20) NOT NULL COMMENT '所属目录id',
  `ord_integral` int(20) NULL DEFAULT 0 COMMENT '普通积分',
  `rew_integral` int(20) NULL DEFAULT 0 COMMENT '奖励积分',
  `img_type` tinyint(4) NULL DEFAULT 0 COMMENT '图片类型（0-随机图片，1-基础图片，2-奖励图片）',
  `style_id` bigint(20) NULL DEFAULT NULL COMMENT '同款图片id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2810 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '相册（单个模板）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_qrcode
-- ----------------------------
DROP TABLE IF EXISTS `biz_qrcode`;
CREATE TABLE `biz_qrcode`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分享id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '分享者id',
  `rew_integral` int(20) NULL DEFAULT 0 COMMENT '奖励积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分享记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_setmeal
-- ----------------------------
DROP TABLE IF EXISTS `biz_setmeal`;
CREATE TABLE `biz_setmeal`  (
  `meal_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '套餐Code',
  `meal_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '套餐名称',
  `meal_price` int(10) NOT NULL COMMENT '套餐价格',
  `meal_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐描述',
  `sort` int(10) NULL DEFAULT NULL COMMENT '排序',
  `bonus` int(10) NULL DEFAULT 0 COMMENT '赠送普通积分',
  `valid_num` int(10) NULL DEFAULT 0 COMMENT '普通积分',
  `valid_day` int(10) NULL DEFAULT 0 COMMENT '有效天数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `control` int(10) NULL DEFAULT 0 COMMENT '控制上线（0-未上线，1-上线）',
  PRIMARY KEY (`meal_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '套餐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_sms_code
-- ----------------------------
DROP TABLE IF EXISTS `biz_sms_code`;
CREATE TABLE `biz_sms_code`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '电话号',
  `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '验证码',
  `add_time` datetime(0) NOT NULL COMMENT '添加时间',
  `used` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否使用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `p_index`(`phone`, `add_time`) USING BTREE,
  INDEX `c_index`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_user
-- ----------------------------
DROP TABLE IF EXISTS `biz_user`;
CREATE TABLE `biz_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'null' COMMENT '用户昵称',
  `realname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'null' COMMENT '真实姓名',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birth_date` datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `token` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'token',
  `token_expired_date` datetime(0) NULL DEFAULT NULL COMMENT 'token过期时间',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `extra_info` json NULL COMMENT '扩展信息字段',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `invitation` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小程序码',
  `identity` int(5) NOT NULL DEFAULT 0 COMMENT '用户身份（0-普通用户，1-经销商用户，2-管理员用户）',
  `ord_integral` int(20) NULL DEFAULT 0 COMMENT '普通积分',
  `rew_integral` int(20) NULL DEFAULT 0 COMMENT '奖励积分',
  `identity_id` bigint(20) NULL DEFAULT 0 COMMENT '所属分销商id(没有则为0)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 124 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
