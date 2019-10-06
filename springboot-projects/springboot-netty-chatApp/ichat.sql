/*
Navicat MySQL Data Transfer

Source Server         : Mysql
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : ichat

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-10-06 12:28:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for chat_msg
-- ----------------------------
DROP TABLE IF EXISTS `chat_msg`;
CREATE TABLE `chat_msg` (
  `id` varchar(64) NOT NULL,
  `send_user_id` varchar(64) NOT NULL,
  `accept_user_id` varchar(64) NOT NULL,
  `msg` varchar(255) NOT NULL,
  `sign_flag` int(1) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_msg
-- ----------------------------
INSERT INTO `chat_msg` VALUES ('191005K0GST0MZHH', '19052770R39ZRY5P', '190528AC4R3PRPM8', '1', '1', '2019-10-05 23:50:53');
INSERT INTO `chat_msg` VALUES ('191005K11BHKWZR4', '190528AC4R3PRPM8', '19052770R39ZRY5P', 'lll', '1', '2019-10-05 23:52:21');
INSERT INTO `chat_msg` VALUES ('191005K1B94Z7FK4', '19052770R39ZRY5P', '1905288ZAN34KSY8', '!', '1', '2019-10-05 23:53:24');
INSERT INTO `chat_msg` VALUES ('191005K1MBR4SAY8', '1905288ZAN34KSY8', '19052770R39ZRY5P', 'q', '1', '2019-10-05 23:54:10');
INSERT INTO `chat_msg` VALUES ('191005K27G3KNFY8', '19052770R39ZRY5P', '190528AC4R3PRPM8', 'y', '0', '2019-10-05 23:56:00');
INSERT INTO `chat_msg` VALUES ('1910067BSBY3PB2W', '19052770R39ZRY5P', '190528AC4R3PRPM8', 'zzz9898', '0', '2019-10-06 10:23:55');
INSERT INTO `chat_msg` VALUES ('1910067C3ZN9065P', '190528AC4R3PRPM8', '19052770R39ZRY5P', 'haha', '1', '2019-10-06 10:24:57');
INSERT INTO `chat_msg` VALUES ('1910067CK5NNZ9P0', '19052770R39ZRY5P', '190528AC4R3PRPM8', '？', '0', '2019-10-06 10:26:22');
INSERT INTO `chat_msg` VALUES ('1910067KB821DWX4', '19052770R39ZRY5P', '1905288ZAN34KSY8', '你好', '0', '2019-10-06 10:40:45');
INSERT INTO `chat_msg` VALUES ('1910067KM9X4RCSW', '1905288ZAN34KSY8', '1905288Z2T2HK77C', '?', '1', '2019-10-06 10:41:30');
INSERT INTO `chat_msg` VALUES ('1910067KWSGSW940', '1905288Z2T2HK77C', '1905288ZAN34KSY8', '？', '0', '2019-10-06 10:42:12');
INSERT INTO `chat_msg` VALUES ('1910067P79XMW7TC', '1905288Z2T2HK77C', '1905288ZAN34KSY8', 'ccc', '0', '2019-10-06 10:49:20');
INSERT INTO `chat_msg` VALUES ('1910067PP700HHZC', '1905288ZAN34KSY8', '19052770R39ZRY5P', '测试', '1', '2019-10-06 10:50:43');
INSERT INTO `chat_msg` VALUES ('1910067R26PMW6FW', '19052770R39ZRY5P', '190528AC4R3PRPM8', '?', '0', '2019-10-06 10:51:47');

-- ----------------------------
-- Table structure for friends_request
-- ----------------------------
DROP TABLE IF EXISTS `friends_request`;
CREATE TABLE `friends_request` (
  `id` varchar(64) NOT NULL,
  `send_user_id` varchar(64) NOT NULL,
  `accept_user_id` varchar(64) NOT NULL,
  `request_data_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friends_request
-- ----------------------------

-- ----------------------------
-- Table structure for my_friends
-- ----------------------------
DROP TABLE IF EXISTS `my_friends`;
CREATE TABLE `my_friends` (
  `id` varchar(64) NOT NULL,
  `my_user_id` varchar(64) NOT NULL,
  `my_friend_user_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of my_friends
-- ----------------------------
INSERT INTO `my_friends` VALUES ('190528AH14689WX4', '1905288ZAN34KSY8', '19052770R39ZRY5P');
INSERT INTO `my_friends` VALUES ('190528AH147B1N9P', '19052770R39ZRY5P', '1905288ZAN34KSY8');
INSERT INTO `my_friends` VALUES ('190528AH18B0Z06W', '190528AC4R3PRPM8', '19052770R39ZRY5P');
INSERT INTO `my_friends` VALUES ('190528AH18BFK400', '19052770R39ZRY5P', '190528AC4R3PRPM8');
INSERT INTO `my_friends` VALUES ('190528B0HPKM4N7C', '1905288ZAN34KSY8', '1905288Z2T2HK77C');
INSERT INTO `my_friends` VALUES ('190528B0HPMF1BTC', '1905288Z2T2HK77C', '1905288ZAN34KSY8');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(64) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(64) NOT NULL,
  `face_image` varchar(255) DEFAULT NULL,
  `face_image_big` varchar(255) DEFAULT NULL,
  `nickname` varchar(20) NOT NULL,
  `qrcode` varchar(255) DEFAULT NULL,
  `cid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('19052770R39ZRY5P', 'zzz', 'gdyb21LQTcIANtvYMT7QVQ==', '19052770R39ZRY5P_userface64_80x80.png', '19052770R39ZRY5P_userface64.png', 'zzz9898', '', 'b59afbf908102564c4ecfbb7e78927dc');
INSERT INTO `users` VALUES ('1905288Z2T2HK77C', 'xxx', 'gdyb21LQTcIANtvYMT7QVQ==', 'face-default-cat_80x80.jpg', 'face-default-cat.jpg', 'xxx', '1905288Z2T2HK77C_qrcode.png', 'b59afbf908102564c4ecfbb7e78927dc');
INSERT INTO `users` VALUES ('1905288ZAN34KSY8', 'ccc', 'gdyb21LQTcIANtvYMT7QVQ==', 'face-default-cat_80x80.jpg', 'face-default-cat.jpg', 'ccc', '1905288ZAN34KSY8_qrcode.png', 'b59afbf908102564c4ecfbb7e78927dc');
INSERT INTO `users` VALUES ('190528ABTNBBF04H', 'ddd', 'gdyb21LQTcIANtvYMT7QVQ==', 'face-default-cat_80x80.jpg', 'face-default-cat.jpg', 'ddd', '190528ABTNBBF04H_qrcode.png', 'b59afbf908102564c4ecfbb7e78927dc');
INSERT INTO `users` VALUES ('190528AC4R3PRPM8', '哈哈', 'gdyb21LQTcIANtvYMT7QVQ==', 'face-default-cat_80x80.jpg', 'face-default-cat.jpg', '哈哈', '190528AC4R3PRPM8_qrcode.png', 'b59afbf908102564c4ecfbb7e78927dc');
