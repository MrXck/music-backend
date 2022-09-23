/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50644
Source Host           : localhost:3306
Source Database       : music

Target Server Type    : MYSQL
Target Server Version : 50644
File Encoding         : 65001

Date: 2022-08-20 12:45:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for singer
-- ----------------------------
DROP TABLE IF EXISTS `singer`;
CREATE TABLE `singer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `img_src` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for singer_song
-- ----------------------------
DROP TABLE IF EXISTS `singer_song`;
CREATE TABLE `singer_song` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `singer_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `singer_song_singer_id` (`singer_id`),
  KEY `singer_song_song_id` (`song_id`),
  CONSTRAINT `singer_song_singer_id` FOREIGN KEY (`singer_id`) REFERENCES `singer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `singer_song_song_id` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for song
-- ----------------------------
DROP TABLE IF EXISTS `song`;
CREATE TABLE `song` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `img_src` varchar(256) NOT NULL,
  `create_time` datetime NOT NULL,
  `src` varchar(256) NOT NULL,
  `time` int(11) NOT NULL,
  `lyric` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for song_collect
-- ----------------------------
DROP TABLE IF EXISTS `song_collect`;
CREATE TABLE `song_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `song_collect_user_id` (`user_id`),
  KEY `song_collect_song_id` (`song_id`),
  CONSTRAINT `song_collect_song_id` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `song_collect_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for song_comment
-- ----------------------------
DROP TABLE IF EXISTS `song_comment`;
CREATE TABLE `song_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(140) NOT NULL,
  `user_id` int(11) NOT NULL,
  `star` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `song_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `song_comment_user_id` (`user_id`),
  KEY `song_comment_song_id` (`song_id`),
  CONSTRAINT `song_comment_song_id` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `song_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for song_list
-- ----------------------------
DROP TABLE IF EXISTS `song_list`;
CREATE TABLE `song_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `img_src` varchar(128) NOT NULL,
  `creator` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `song_list_user_id` (`creator`),
  CONSTRAINT `song_list_user_id` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for song_list_collect
-- ----------------------------
DROP TABLE IF EXISTS `song_list_collect`;
CREATE TABLE `song_list_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `song_list_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `song_list_collect_user_id` (`user_id`),
  KEY `song_list_collect_song_list_id` (`song_list_id`),
  CONSTRAINT `song_list_collect_song_list_id` FOREIGN KEY (`song_list_id`) REFERENCES `song_list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `song_list_collect_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for song_list_comment
-- ----------------------------
DROP TABLE IF EXISTS `song_list_comment`;
CREATE TABLE `song_list_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(140) NOT NULL,
  `user_id` int(11) NOT NULL,
  `song_list_id` int(11) NOT NULL,
  `star` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `song_list_comment_user_id` (`user_id`),
  KEY `song_list_comment_song_list_id` (`song_list_id`),
  CONSTRAINT `song_list_comment_song_list_id` FOREIGN KEY (`song_list_id`) REFERENCES `song_list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `song_list_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for song_song_list
-- ----------------------------
DROP TABLE IF EXISTS `song_song_list`;
CREATE TABLE `song_song_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `song_list_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `song_song_list_song_list_id` (`song_list_id`),
  KEY `song_song_list_song_id` (`song_id`),
  CONSTRAINT `song_song_list_song_id` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `song_song_list_song_list_id` FOREIGN KEY (`song_list_id`) REFERENCES `song_list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `avatar` varchar(256) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
