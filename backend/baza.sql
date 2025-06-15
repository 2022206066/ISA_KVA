-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 192.168.0.10    Database: cinema_tickets
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.24.04.1

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

-- Drop database if exists and create new one
DROP DATABASE IF EXISTS `cinema_tickets`;
CREATE DATABASE `cinema_tickets`;
USE `cinema_tickets`;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `genre_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1,'Horror'),(2,'Comedy'),(3,'Romance'),(4,'Action'),(5,'Thriller'),(6,'Documentary'),(7,'Musical');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `director`
--

DROP TABLE IF EXISTS `director`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `director` (
  `director_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `bio` text,
  PRIMARY KEY (`director_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `director`
--

LOCK TABLES `director` WRITE;
/*!40000 ALTER TABLE `director` DISABLE KEYS */;
INSERT INTO `director` VALUES (1,'Gene Stupnitsky','American screenwriter and producer'),(2,'Michael Dowse','Canadian film director and screenwriter'),(3,'David F. Sandberg','Swedish filmmaker'),(4,'John Francis Daley','American actor, filmmaker, and comedian');
/*!40000 ALTER TABLE `director` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actor` (
  `actor_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `bio` text,
  PRIMARY KEY (`actor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
INSERT INTO `actor` VALUES (1,'Jacob Tremblay','Canadian actor'),(2,'Keith L. Williams','American child actor'),(3,'Brady Noon','American actor'),(4,'Kumail Nanjiani','Pakistani-American comedian'),(5,'Dave Bautista','American actor and former professional wrestler'),(6,'Zachary Levi','American actor'),(7,'Asher Angel','American actor'),(8,'Jack Dylan Grazer','American actor'),(9,'Jason Bateman','American actor, director and producer'),(10,'Rachel McAdams','Canadian actress');
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `movie_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `genre_genre_id` int NOT NULL,
  `director_id` int NOT NULL,
  `duration` int NOT NULL COMMENT 'Duration in minutes',
  `release_date` date NOT NULL,
  PRIMARY KEY (`movie_id`),
  KEY `fk_movie_genre1_idx` (`genre_genre_id`),
  KEY `fk_movie_director1_idx` (`director_id`),
  CONSTRAINT `fk_movie_genre1` FOREIGN KEY (`genre_genre_id`) REFERENCES `genre` (`genre_id`),
  CONSTRAINT `fk_movie_director1` FOREIGN KEY (`director_id`) REFERENCES `director` (`director_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (1,'Good Boys','Three 6th-grade boys ditch school and embark on an epic journey while carrying accidentally stolen drugs, being hunted by teenage girls, and trying to make their way home in time for a long-awaited party.',2,1,90,'2019-08-16'),(2,'Stuber','A detective recruits his Uber driver into an unexpected night of adventure.',2,2,93,'2019-07-12'),(3,'Shazam!','A newly fostered young boy in search of his mother instead finds unexpected super powers and soon gains a powerful enemy.',2,3,132,'2019-04-05'),(4,'Game Night','A group of friends who meet regularly for game nights find themselves entangled in a real-life mystery when the shady brother of one of them is seemingly kidnapped by dangerous gangsters.',2,4,100,'2018-02-23'),(5,'The Conjuring','Paranormal investigators Ed and Lorraine Warren work to help a family terrorized by a dark presence in their farmhouse.',1,1,112,'2013-07-19'),(6,'John Wick','An ex-hitman comes out of retirement to track down the gangsters who killed his dog and took his car.',4,2,101,'2014-10-24'),(7,'The Notebook','A poor yet passionate young man falls in love with a rich young woman, giving her a sense of freedom, but they are soon separated because of their social differences.',3,3,123,'2004-06-25'),(8,'Interstellar','A team of explorers travel through a wormhole in space in an attempt to ensure humanity\'s survival.',5,4,169,'2014-11-07'),(9,'The Silence of the Lambs','A young FBI cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer.',5,1,118,'1991-02-14'),(10,'Cinema Paradiso','A filmmaker recalls his childhood when falling in love with the pictures at the cinema of his home village and forms a deep friendship with the cinema\'s projectionist.',6,3,155,'1988-11-17');
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_actor`
--

DROP TABLE IF EXISTS `movie_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_actor` (
  `movie_actor_id` int NOT NULL AUTO_INCREMENT,
  `movie_id` int NOT NULL,
  `actor_id` int NOT NULL,
  PRIMARY KEY (`movie_actor_id`),
  KEY `fk_movie_actor_movie1_idx` (`movie_id`),
  KEY `fk_movie_actor_actor1_idx` (`actor_id`),
  CONSTRAINT `fk_movie_actor_movie1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  CONSTRAINT `fk_movie_actor_actor1` FOREIGN KEY (`actor_id`) REFERENCES `actor` (`actor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_actor`
--

LOCK TABLES `movie_actor` WRITE;
/*!40000 ALTER TABLE `movie_actor` DISABLE KEYS */;
INSERT INTO `movie_actor` VALUES (1,1,1),(2,1,2),(3,1,3),(4,2,4),(5,2,5),(6,3,6),(7,3,7),(8,3,8),(9,4,9),(10,4,10),(11,5,1),(12,5,3),(13,6,5),(14,6,9),(15,7,2),(16,7,10),(17,8,4),(18,8,6),(19,9,7),(20,9,8),(21,10,5),(22,10,7);
/*!40000 ALTER TABLE `movie_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `screening`
--

DROP TABLE IF EXISTS `screening`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `screening` (
  `screening_id` int NOT NULL AUTO_INCREMENT,
  `movie_id` int NOT NULL,
  `screening_date` date NOT NULL,
  `screening_time` time NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `available_seats` int NOT NULL,
  PRIMARY KEY (`screening_id`),
  KEY `fk_screening_movie1_idx` (`movie_id`),
  CONSTRAINT `fk_screening_movie1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `screening`
--

LOCK TABLES `screening` WRITE;
/*!40000 ALTER TABLE `screening` DISABLE KEYS */;
INSERT INTO `screening` VALUES (1,1,'2025-06-10','14:00:00',8.50,50),(2,1,'2025-06-10','18:00:00',10.00,50),(3,1,'2025-06-11','16:00:00',9.00,50),(4,2,'2025-06-10','15:30:00',8.50,40),(5,2,'2025-06-11','19:30:00',10.00,40),(6,3,'2025-06-12','17:00:00',12.00,60),(7,3,'2025-06-13','20:00:00',12.50,60),(8,4,'2025-06-11','14:00:00',8.00,45),(9,4,'2025-06-12','18:30:00',10.00,45),(10,4,'2025-06-13','16:00:00',9.00,45),(11,5,'2025-06-14','19:00:00',11.50,55),(12,5,'2025-06-15','21:00:00',12.00,55),(13,6,'2025-06-14','17:30:00',10.50,50),(14,6,'2025-06-15','20:30:00',11.00,50),(15,7,'2025-06-16','16:45:00',9.50,40),(16,7,'2025-06-17','19:15:00',10.50,40),(17,8,'2025-06-16','18:00:00',13.00,65),(18,8,'2025-06-17','21:00:00',13.50,65),(19,9,'2025-06-18','17:00:00',9.00,45),(20,10,'2025-06-18','19:30:00',11.00,50);
/*!40000 ALTER TABLE `screening` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('USER','ADMIN') NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `favorite_genres` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Alexa01','alex01@gmail.com','$2a$12$UZPM3xmfNZ9FhCcymuHgx.OrioaBQn31CLN6ozEnfP/JebPZrCjNe','ADMIN','Alex','Smith','+3816551234','123 Main St','Comedy, Action'),(8,'Troll','troll52@gmail.com','$2a$12$RaMtxEsBK/fLnCqh7.BtH.E6ySWcJu8H1m4iwuh.K7twP/txp1DF2','USER','Troll','User','+3816559876','456 Oak Ave','Horror, Thriller');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `reservation_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `screening_id` int NOT NULL,
  `status` enum('RESERVED','WATCHED','CANCELED') NOT NULL DEFAULT 'RESERVED',
  `rating` tinyint DEFAULT NULL,
  `reservation_date` datetime NOT NULL,
  `seat_number` varchar(10) NOT NULL,
  PRIMARY KEY (`reservation_id`),
  KEY `fk_reservation_user1_idx` (`user_id`),
  KEY `fk_reservation_screening1_idx` (`screening_id`),
  CONSTRAINT `fk_reservation_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_reservation_screening1` FOREIGN KEY (`screening_id`) REFERENCES `screening` (`screening_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,1,1,'WATCHED',5,'2025-06-05 10:30:00','A12'),(2,1,4,'RESERVED',NULL,'2025-06-06 14:45:00','B8'),(3,8,2,'WATCHED',4,'2025-06-04 16:20:00','C5'),(4,8,6,'CANCELED',NULL,'2025-06-03 09:15:00','D10');
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `review_content` text NOT NULL,
  `is_positive` tinyint NOT NULL,
  `movie_movie_id` int NOT NULL,
  `user_user_id` int NOT NULL,
  `review_date` datetime NOT NULL,
  `rating` int NOT NULL,
  PRIMARY KEY (`review_id`),
  KEY `fk_review_movie1_idx` (`movie_movie_id`),
  KEY `fk_review_user1_idx` (`user_user_id`),
  CONSTRAINT `fk_review_movie1` FOREIGN KEY (`movie_movie_id`) REFERENCES `movie` (`movie_id`),
  CONSTRAINT `fk_review_user1` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (9,'10/10 great movie, everyone should definitely see it!',1,1,1,'2025-06-05 18:30:00',5),(10,'Funny but a bit predictable. Good for a weekend watch.',1,2,8,'2025-06-04 22:15:00',4),(11,'Loved the special effects and the story was engaging!',1,3,1,'2025-06-01 19:45:00',5),(12,'Not as funny as I expected. Some good moments though.',0,4,8,'2025-05-28 20:30:00',3),(13,'Absolutely terrifying! Could not sleep for days after watching.',1,5,1,'2025-06-07 21:15:00',5),(14,'The action sequences were brilliant, loved the cinematography.',1,6,8,'2025-06-08 19:45:00',5),(15,'Such a beautiful love story, made me cry!',1,7,1,'2025-06-06 20:30:00',4),(16,'Mind-blowing visuals and an incredible soundtrack. A masterpiece.',1,8,8,'2025-06-09 22:00:00',5),(17,'Classic thriller, brilliant performance by the entire cast.',1,9,1,'2025-06-10 18:15:00',5),(18,'A nostalgic journey through childhood and cinema. Beautiful.',1,10,8,'2025-06-11 20:45:00',4);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_watched`
--

DROP TABLE IF EXISTS `movie_watched`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_watched` (
  `movie_watched_id` int NOT NULL AUTO_INCREMENT,
  `user_user_id` int NOT NULL,
  `movie_movie_id` int NOT NULL,
  `watched_date` datetime NOT NULL,
  PRIMARY KEY (`movie_watched_id`),
  KEY `fk_movie_watched_user_idx` (`user_user_id`),
  KEY `fk_movie_watched_movie1_idx` (`movie_movie_id`),
  CONSTRAINT `fk_movie_watched_movie1` FOREIGN KEY (`movie_movie_id`) REFERENCES `movie` (`movie_id`),
  CONSTRAINT `fk_movie_watched_user` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_watched`
--

LOCK TABLES `movie_watched` WRITE;
/*!40000 ALTER TABLE `movie_watched` DISABLE KEYS */;
INSERT INTO `movie_watched` VALUES (1,1,1,'2025-06-05 14:30:00'),(2,1,2,'2025-06-01 18:45:00'),(3,8,2,'2025-06-04 19:30:00'),(4,1,3,'2025-05-29 20:15:00'),(5,1,5,'2025-06-07 21:15:00'),(6,8,6,'2025-06-08 19:45:00'),(7,1,7,'2025-06-06 20:30:00'),(8,8,8,'2025-06-09 22:00:00'),(9,1,9,'2025-06-10 18:15:00'),(10,8,10,'2025-06-11 20:45:00');
/*!40000 ALTER TABLE `movie_watched` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-07 17:53:49