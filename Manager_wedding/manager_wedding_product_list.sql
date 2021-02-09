-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: manager_wedding
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `product_list`
--

DROP TABLE IF EXISTS `product_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_list` (
  `product_name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `product_code` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `product_cost` double DEFAULT NULL,
  `product_type` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `manufacturer` varchar(45) COLLATE utf8_unicode_ci DEFAULT ' '
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_list`
--

LOCK TABLES `product_list` WRITE;
/*!40000 ALTER TABLE `product_list` DISABLE KEYS */;
INSERT INTO `product_list` VALUES ('súp cua bắc mỹ','FOOD001',250000,'food',' '),('cơm chiên hải sản','FOOD002',35000,'food',' '),('chả giò hải sản','FOOD003',55000,'food',' '),('mực sữa chiên nước mắm','FOOD004',65000,'food',' '),('cá phile tẩm mè','FOOD005',100000,'food',' '),('mực chiên giòn','FOOD006',85000,'food',' '),('heo quay','FOOD007',120000,'food',' '),('súp bào ngư','FOOD008',78000,'food',' '),('xôi chiên','FOOD009',45000,'food',' '),('bò xào','FOOD010',78000,'food',' '),('coca-cola','DRINK001',25000,'drink','coca-cola'),('soda','DRINK002',20000,'drink','soda'),('dasani','DRINK003',25000,'drink','dasani'),('bia','DRINK004',55000,'drink','333'),('rượu vang','DRINK005',550000,'drink','');
/*!40000 ALTER TABLE `product_list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-09 21:18:27
