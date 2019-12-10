/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : catchtime

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2019-11-27 09:51:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `icon`
-- ----------------------------
DROP TABLE IF EXISTS `icon`;
CREATE TABLE `icon` (
  `icon_id` smallint(5) NOT NULL,
  `icon_address` varchar(20) NOT NULL,
  `color` varchar(10) NOT NULL,
  PRIMARY KEY (`icon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of icon
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` smallint(5) NOT NULL,
  `phone` char(11) NOT NULL,
  `password` varchar(13) NOT NULL,
  `username` varchar(20) NOT NULL,
  `register_date` varchar(30) NOT NULL,
  `moto` varchar(50) DEFAULT NULL,
  `image` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `user_table`
-- ----------------------------
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table` (
  `user_id` smallint(5) NOT NULL,
  `loaction_table_name` varchar(20) DEFAULT NULL,
  `activity_table_name` varchar(20) DEFAULT NULL,
  `connection_tbale_name` varchar(20) DEFAULT NULL,
  `detaildata_table_name` varchar(20) DEFAULT NULL,
  `newplace_table_name` varchar(20) DEFAULT NULL,
  `dayrecord_table_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user_table
-- ----------------------------
