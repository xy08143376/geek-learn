/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : pay_sys

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 31/10/2021 21:49:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `order_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '订单id，自增',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `prod_id` bigint(0) NOT NULL COMMENT '商品id',
  `order_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '下单价格，即单价',
  `order_num` int(0) NULL DEFAULT NULL COMMENT '下单数量',
  `order_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单总价',
  `order_status` int(0) NULL DEFAULT NULL COMMENT '订单状态，1已支付2未支付3已完成4已取消',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_prod
-- ----------------------------
DROP TABLE IF EXISTS `t_prod`;
CREATE TABLE `t_prod`  (
  `prod_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '商品id，自增',
  `prod_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
  `prod_category` int(0) NULL DEFAULT NULL COMMENT '商品类别',
  `prod_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品描述',
  `price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '商品价格',
  PRIMARY KEY (`prod_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户密码',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
