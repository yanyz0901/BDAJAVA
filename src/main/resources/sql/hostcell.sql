/*
 Navicat Premium Data Transfer

 Source Server         : bda
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : bda

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 03/12/2023 21:28:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hostcell
-- ----------------------------
DROP TABLE IF EXISTS `hostcell`;
CREATE TABLE `hostcell` (
  `hostcell_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NOT NULL,
  `ftp_path` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `hostcell_name` varchar(255) NOT NULL,
  PRIMARY KEY (`hostcell_id`),
  KEY `hostcellname` (`hostcell_name`),
  KEY `taskid` (`task_id`),
  KEY `userid` (`user_id`),
  CONSTRAINT `taskid` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`),
  CONSTRAINT `userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of hostcell
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
