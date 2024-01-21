/*
 Navicat Premium Data Transfer

 Source Server         : bda
 Source Server Type    : MySQL
 Source Server Version : 50540
 Source Host           : localhost:3306
 Source Schema         : bda

 Target Server Type    : MySQL
 Target Server Version : 50540
 File Encoding         : 65001

 Date: 21/01/2024 10:57:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hirxn_task
-- ----------------------------
DROP TABLE IF EXISTS `hirxn_task`;
CREATE TABLE `hirxn_task`  (
  `task_id` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '任务id',
  `rxn_smiles` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '反应表达式',
  `radius` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '半径',
  `task_type` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '预测任务类型',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '任务完成状态,默认为0，任务运行完成时为1，其余为0',
  `rxn_tokens` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '反应token结果',
  `prediction` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '预测值',
  `reaction_img_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '图片ftp路径',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
