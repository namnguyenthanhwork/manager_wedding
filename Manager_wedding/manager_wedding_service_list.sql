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
-- Table structure for table `service_list`
--

DROP TABLE IF EXISTS `service_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_list` (
  `service_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `service_code` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `service_cost` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `service_type` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `service_rent_time` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_list`
--

LOCK TABLES `service_list` WRITE;
/*!40000 ALTER TABLE `service_list` DISABLE KEYS */;
INSERT INTO `service_list` VALUES ('Karaoke 1h','KARAOKE001','100000','karaoke',1),('Karaoke 2h','KARAOKE002','200000','karaoke',2),('Karaoke 3h','KARAOKE003','300000','karaoke',3),('Karaoke 4h','KARAOKE004','400000','karaoke',4),('Thảm đỏ','DECORATION001','200000','decoration',NULL),('Trang trí cổng','DECORATION002','1500000','decoration',NULL),('Sơn Tùng','SINGER001','10000000','singer',NULL),('Jack','SINGER002','8000000','singer',NULL),('Hồ Quang Hiếu','SINGER003','5000000','singer',NULL),('Hoàng Dũng','SINGER004','3500000','singer',NULL);
/*!40000 ALTER TABLE `service_list` ENABLE KEYS */;
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
