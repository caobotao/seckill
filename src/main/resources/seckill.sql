/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.32-log : Database - seckill
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`seckill` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `seckill`;

/*Table structure for table `item` */

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品名称',
  `price` double NOT NULL DEFAULT '0' COMMENT '商品价格',
  `description` varchar(500) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品描述',
  `sales` int(11) NOT NULL DEFAULT '0' COMMENT '商品销量',
  `img_url` text COLLATE utf8_bin NOT NULL COMMENT '图片链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `item` */

insert  into `item`(`id`,`title`,`price`,`description`,`sales`,`img_url`) values (3,'iphone6',1111,'1',24,'https://img10.360buyimg.com/n1/s450x450_jfs/t1/166699/25/1240/610129/5ff55fc3E97e74c8f/aad8be89146436c0.png'),(4,'iphone7',1111,'1',33,'https://img12.360buyimg.com/n7/jfs/t1/138748/21/18328/252772/5fd5ea33E80c4270a/0731ca014d302492.png');

/*Table structure for table `item_stock` */

DROP TABLE IF EXISTS `item_stock`;

CREATE TABLE `item_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `stock` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
  `item_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `item_stock` */

insert  into `item_stock`(`id`,`stock`,`item_id`) values (3,7,3),(4,15,4);

/*Table structure for table `order_info` */

DROP TABLE IF EXISTS `order_info`;

CREATE TABLE `order_info` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `item_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品id',
  `promo_id` int(11) NOT NULL DEFAULT '0' COMMENT '秒杀活动id',
  `item_price` double NOT NULL DEFAULT '0' COMMENT '商品价格',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '购买数量',
  `order_price` double NOT NULL DEFAULT '0' COMMENT '订单总金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `order_info` */

insert  into `order_info`(`id`,`user_id`,`item_id`,`promo_id`,`item_price`,`amount`,`order_price`) values ('2021011200000300',19,4,0,1111,1,1111),('2021011200000400',19,4,0,1111,1,1111),('2021011200000500',19,4,0,1111,1,1111),('2021011200000600',19,3,0,1111,1,1111),('2021011200000700',19,4,0,1111,1,1111),('2021011200000800',19,3,0,200,1,200),('2021011200000900',19,4,0,1111,1,1111),('2021011200001000',19,3,1,200,1,200),('2021011300001100',19,3,0,1111,1,1111);

/*Table structure for table `promo` */

DROP TABLE IF EXISTS `promo`;

CREATE TABLE `promo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `promo_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '秒杀活动名称',
  `start_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀结束时间',
  `item_id` int(11) NOT NULL DEFAULT '0' COMMENT '秒杀商品id',
  `promo_item_price` double NOT NULL DEFAULT '0' COMMENT '秒杀商品价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `promo` */

insert  into `promo`(`id`,`promo_name`,`start_date`,`end_date`,`item_id`,`promo_item_price`) values (1,'iphone6抢购','2021-01-12 23:05:10','2021-01-12 23:35:04',3,200);

/*Table structure for table `sequence_info` */

DROP TABLE IF EXISTS `sequence_info`;

CREATE TABLE `sequence_info` (
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `current_value` int(11) NOT NULL DEFAULT '0' COMMENT '当前值',
  `step` int(11) NOT NULL DEFAULT '0' COMMENT '步长',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sequence_info` */

insert  into `sequence_info`(`name`,`current_value`,`step`) values ('order_info',12,1);

/*Table structure for table `test1` */

DROP TABLE IF EXISTS `test1`;

CREATE TABLE `test1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `test1` */

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1-男性 2-女性',
  `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄',
  `telphone` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '手机号',
  `register_mode` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'byphone,bywechat,byalipay',
  `third_party_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '第三方账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`name`,`gender`,`age`,`telphone`,`register_mode`,`third_party_id`) values (19,'rocket',1,20,'15216655576','byphone','');

/*Table structure for table `user_password` */

DROP TABLE IF EXISTS `user_password`;

CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `encrpt_password` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '加密后的密码',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_password` */

insert  into `user_password`(`id`,`encrpt_password`,`user_id`) values (9,'c4ca4238a0b923820dcc509a6f75849b',19);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
