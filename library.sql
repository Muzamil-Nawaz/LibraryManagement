/*
SQLyog Professional v12.09 (64 bit)
MySQL - 10.1.38-MariaDB : Database - library-management
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`library-management` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `library-management`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `admin` */

insert  into `admin`(`id`,`name`,`password`) values (1,'test','test');

/*Table structure for table `books` */

DROP TABLE IF EXISTS `books`;

CREATE TABLE `books` (
  `isbn` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `pages` int(10) NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `books` */

insert  into `books`(`isbn`,`name`,`pages`,`price`) values ('HY78JU','Rich Dad Poor Dad',200,1500);

/*Table structure for table `borrowed_books` */

DROP TABLE IF EXISTS `borrowed_books`;

CREATE TABLE `borrowed_books` (
  `isbn` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `pages` int(100) NOT NULL,
  `price` float NOT NULL,
  `reader_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`isbn`),
  KEY `reader_id` (`reader_id`),
  CONSTRAINT `borrowed_books_ibfk_1` FOREIGN KEY (`reader_id`) REFERENCES `readers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `borrowed_books` */

insert  into `borrowed_books`(`isbn`,`name`,`pages`,`price`,`reader_id`) values ('ADS1ASD2','Think and grow rich',130,1000,1);

/*Table structure for table `readers` */

DROP TABLE IF EXISTS `readers`;

CREATE TABLE `readers` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `readers` */

insert  into `readers`(`id`,`name`,`password`,`email`) values (1,'demo','1234','demo@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
