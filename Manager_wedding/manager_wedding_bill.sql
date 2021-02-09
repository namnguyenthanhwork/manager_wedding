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
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `bill_code` varchar(45) NOT NULL,
  `bill_name` varchar(100) NOT NULL,
  `lobby_code` varchar(45) NOT NULL,
  `lobby_location` varchar(45) NOT NULL,
  `lobby_capacity` int NOT NULL,
  `lobby_cost` double NOT NULL,
  `bill_time` varchar(45) NOT NULL,
  `bill_date` date NOT NULL,
  `menu_code` varchar(300) NOT NULL,
  `menu_cost` double NOT NULL,
  `service_code` varchar(300) NOT NULL,
  `service_cost` double NOT NULL,
  `sum_price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,'BILL001','Tiệc cưới Thành Nam','s003','Tầng 1',5,2000000,'sáng','2001-02-16','1-menu001,2-menu001,3-menu001,4-menu001,5-menu001',275000,'karaoke003,singer002,decoration003',9800000,12075000),(2,'BILL002','Tiệc cưới Hải Yến','s005','Tầng 2',10,2000000,'tối','2021-02-16','1-menu001,2-menu001,3-menu001,4-menu001,5-menu001,6-menu001,7-menu001,8-menu001,9-menu001,10-menu001',550000,'singer004,decoration003,karaoke002',5200000,7750000),(3,'BILL003','Tiệc cưới Thảo Nhi','s009','Tầng 3',5,2000000,'chiều','2021-03-12','1-menu001,2-menu001,3-menu001,4-menu001,5-menu001',275000,'karaoke004,singer003',5400000,7675000),(4,'BILL004','Tiệc cưới Thảo Nhi','s004','Tầng 1',5,2000000,'chiều','2021-02-16','1-menu001,2-menu001,3-menu001,4-menu001,5-menu001',275000,'karaoke001',100000,2375000),(5,'BILL005','Tiệc Cưới Nhi','s006','Tầng 2',10,2000000,'sáng','2022-03-16','1-menu001,2-menu001,3-menu001,4-menu001,5-menu001,6-menu001,7-menu001,8-menu001,9-menu001,10-menu001',550000,'singer001',10000000,12550000),(6,'BILL006','nam','s008','Tầng 3',10,2000000,'sáng','2022-03-22','1-menu001,2-menu001,3-menu001,4-menu001,5-menu001,6-menu001,7-menu001,8-menu001,9-menu001,10-menu001',550000,'singer001',10000000,12550000),(7,'BILL007','nhi','s001','Tầng 1',15,3500000,'tối','2022-01-16','1-menu001,2-menu001,3-menu001,4-menu001,5-menu001,6-menu001,7-menu001,8-menu001,9-menu001,10-menu001,11-menu001,12-menu001,13-menu001,14-menu001,15-menu001',825000,'singer001',10000000,14325000),(8,'BILL008','Tiệc cưới Trương Thảo Nhi','s010','Tầng 3',10,2000000,'sáng','2021-12-22','1-menu001,2-menu001,3-menu001,4-menu001,5-menu001,6-menu001,7-menu001,8-menu001,9-menu001,10-menu001',550000,'singer001,karaoke002,decoration002',11700000,14250000),(9,'BILL009','nammm','s003','Tầng 1',5,2000000,'sáng','2022-12-12','1-menu001,2-menu001,3-menu001,4-menu001,5-menu001',275000,'karaoke001,singer001',10100000,12375000),(10,'BILL010','thành nam','s009','Tầng 3',5,2000000,'sáng','2022-08-16','1-menu001,2-menu001,3-menu001,4-menu001,5-menu001',275000,'singer004',3500000,5775000);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-09 21:18:25
