-- MySQL dump 10.13  Distrib 8.0.16, for Linux (x86_64)
--
-- Host: localhost    Database: camera
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `camera`
--

DROP TABLE IF EXISTS `camera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `camera` (
  `id` varchar(36) NOT NULL,
  `serial` varchar(255) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `last_online` varchar(20) DEFAULT NULL,
  `state` varchar(10) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `manufature` varchar(255) DEFAULT NULL,
  `group` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `discription` text,
  `store` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `camera`
--

LOCK TABLES `camera` WRITE;
/*!40000 ALTER TABLE `camera` DISABLE KEYS */;
INSERT INTO `camera` VALUES ('185339746','185339746','testCamera',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `camera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `camera_user`
--

DROP TABLE IF EXISTS `camera_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `camera_user` (
  `customer_id` int(11) NOT NULL,
  `appKey` varchar(50) DEFAULT NULL,
  `appSecret` varchar(100) DEFAULT NULL,
  `accessToken` varchar(255) DEFAULT NULL,
  `store` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `camera_user`
--

LOCK TABLES `camera_user` WRITE;
/*!40000 ALTER TABLE `camera_user` DISABLE KEYS */;
INSERT INTO `camera_user` VALUES (124,'2202b037f424462888e3918831dd9680','4e45ac4dbaf66fddb8afb4da7e313cef','at.5uyx0yis9406e27x5u5fgnv79bw8o9qj-7eenpxb28g-08h29pq-4xtl710fs',NULL);
/*!40000 ALTER TABLE `camera_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `camera_user_relation`
--

DROP TABLE IF EXISTS `camera_user_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `camera_user_relation` (
  `camera_id` varchar(36) NOT NULL,
  `customer_id` varchar(36) NOT NULL,
  PRIMARY KEY (`camera_id`,`customer_id`),
  UNIQUE KEY `camera_id_UNIQUE` (`camera_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `camera_user_relation`
--

LOCK TABLES `camera_user_relation` WRITE;
/*!40000 ALTER TABLE `camera_user_relation` DISABLE KEYS */;
INSERT INTO `camera_user_relation` VALUES ('185339746','124');
/*!40000 ALTER TABLE `camera_user_relation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-17  5:31:00
