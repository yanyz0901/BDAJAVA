/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50540
 Source Host           : localhost:3306
 Source Schema         : bda

 Target Server Type    : MySQL
 Target Server Version : 50540
 File Encoding         : 65001

 Date: 14/03/2023 13:22:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '用户类型：0代表普通用户，1代表管理员',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE COMMENT '名字唯一标识',
  UNIQUE INDEX `email`(`email`) USING BTREE COMMENT '邮箱唯一标识',
  UNIQUE INDEX `phone`(`phone`) USING BTREE COMMENT '手机号唯一标识'
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (5, 'yanyz', '$2a$10$9wkQwU1gMF.1BkI/JZuW1egE16BTdyYzrTyB0M6qvCTRrjS.hdjQu', '1', '0', 'yanyuzhengtju@163.com', '18833936118', '2023-01-13 15:57:33', '2023-01-13 15:57:33', '0');
INSERT INTO `user` VALUES (7, 'admin', '$2a$10$Ao/IJz7tEohY1NuBhRGW8eVS4Z8prQQpl28tPW/azMk2xzlBGgYYO', '1', '0', 'caoyh_cyh@163.com', '18888888888', '2023-01-13 19:51:13', '2023-01-13 20:11:42', '0');
INSERT INTO `user` VALUES (17, 'caoyh', '$2a$10$6gVQpL3gqXjc21.WKdHebO.yl939dKxnAVqHYVwXvhW27r6S4Q/Ha', '0', '0', 'caoyahui@tju.edu.cn', '15122288569', '2023-02-27 14:36:21', '2023-02-27 14:36:21', '0');
INSERT INTO `user` VALUES (19, 'lht', '$2a$10$LhHlNiSFPmkGS9rYnSUedu7ttx2Qy5nO57h60q0fKmWGhyIIgvPZi', '0', '0', 'lht', '1111111111111111111', '2023-02-28 14:58:35', '2023-02-28 14:58:35', '0');
INSERT INTO `user` VALUES (20, 'cao123456', '$2a$10$0j0foKlHrNQaK/hyELZJNOzTMWCnI9FhpecDj.GYM/g6QuxcW4/FC', '0', '0', 'cao@tju.edu.cn', '13333333333', '2023-03-11 20:44:58', '2023-03-11 20:44:58', '0');

SET FOREIGN_KEY_CHECKS = 1;
