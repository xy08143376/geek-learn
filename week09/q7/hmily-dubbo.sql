/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : hmily-dubbo

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 22/11/2021 16:34:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_account_cny
-- ----------------------------
DROP TABLE IF EXISTS `t_account_cny`;
CREATE TABLE `t_account_cny`  (
  `account_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `amount` double(255, 0) NOT NULL DEFAULT 0 COMMENT 'decimal更好,为方便就用double',
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_account_dollar
-- ----------------------------
DROP TABLE IF EXISTS `t_account_dollar`;
CREATE TABLE `t_account_dollar`  (
  `account_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `amount` double(255, 0) NOT NULL DEFAULT 0 COMMENT 'decimal更好，为了方便用double',
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_cancel_log
-- ----------------------------
DROP TABLE IF EXISTS `t_cancel_log`;
CREATE TABLE `t_cancel_log`  (
  `tx_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '事务id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tx_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_confirm_log
-- ----------------------------
DROP TABLE IF EXISTS `t_confirm_log`;
CREATE TABLE `t_confirm_log`  (
  `tx_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '事务id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tx_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_lock_money
-- ----------------------------
DROP TABLE IF EXISTS `t_lock_money`;
CREATE TABLE `t_lock_money`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `from_account_id` int(0) NOT NULL COMMENT '资金来源账户',
  `amount` double(255, 0) NOT NULL DEFAULT 0 COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_try_log
-- ----------------------------
DROP TABLE IF EXISTS `t_try_log`;
CREATE TABLE `t_try_log`  (
  `tx_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '事务id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tx_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
