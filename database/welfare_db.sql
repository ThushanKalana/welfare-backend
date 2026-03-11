-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: welfare_db
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `applications`
--

DROP TABLE IF EXISTS `applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applications` (
  `status` enum('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
  `applied_date` date NOT NULL,
  `application_id` bigint NOT NULL AUTO_INCREMENT,
  `citizen_nic` varchar(20) NOT NULL,
  `welfare_type` varchar(50) NOT NULL,
  PRIMARY KEY (`application_id`),
  UNIQUE KEY `application_id` (`application_id`),
  UNIQUE KEY `unique_citizen_nic` (`citizen_nic`),
  CONSTRAINT `fk_applications_citizen` FOREIGN KEY (`citizen_nic`) REFERENCES `citizen` (`citizen_nic`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applications`
--

LOCK TABLES `applications` WRITE;
/*!40000 ALTER TABLE `applications` DISABLE KEYS */;
INSERT INTO `applications` VALUES ('APPROVED','2026-02-15',21,'111267888V','Education Assistance'),('PENDING','2026-02-01',22,'973590288V','Housing Assistance'),('APPROVED','2026-02-15',24,'975690489V','Education Assistance'),('PENDING','2026-02-24',25,'973590286V','Education Assistance');
/*!40000 ALTER TABLE `applications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `citizen`
--

DROP TABLE IF EXISTS `citizen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citizen` (
  `citizen_id` int NOT NULL AUTO_INCREMENT,
  `citizen_nic` varchar(20) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `qr_code` varchar(255) DEFAULT NULL,
  `latitude` decimal(10,8) DEFAULT NULL,
  `longitude` decimal(11,8) DEFAULT NULL,
  PRIMARY KEY (`citizen_nic`),
  UNIQUE KEY `citizen_nic` (`citizen_nic`),
  UNIQUE KEY `unique_citizen_nic` (`citizen_nic`),
  UNIQUE KEY `citizen_id` (`citizen_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citizen`
--

LOCK TABLES `citizen` WRITE;
/*!40000 ALTER TABLE `citizen` DISABLE KEYS */;
INSERT INTO `citizen` VALUES (5,'111267888V','Sahan','Pasindu','No 99, Hill Road, Kandy','kamal.updated@email.com','0719999999','QR456',NULL,NULL),(9,'973590286V','Thushan','kalana','Nilwakka, Kegalle','thushankalana24@gmail.com','0713731530','1771942197018',7.25270000,80.34180000),(6,'973590288V','Dilshan','KD','Kegalle','dilshan@gmail.com','0765678234','1334546',NULL,NULL),(8,'975690489V','Sasitha','niroshan','Rambukkana','thushanceyloncloud@gmail.com','0713731533','1771871553461',7.25270000,80.34180000);
/*!40000 ALTER TABLE `citizen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `citizen_documents`
--

DROP TABLE IF EXISTS `citizen_documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citizen_documents` (
  `document_id` bigint NOT NULL AUTO_INCREMENT,
  `citizen_nic` varchar(20) NOT NULL,
  `document_type` enum('NIC','BIRTH_CERTIFICATE','INCOME_PROOF','ELECTRICITY_BILL','WATER_BILL','BANK_STATEMENT','MEDICAL_REPORT','OTHER') NOT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `file_path` varchar(500) DEFAULT NULL,
  `uploaded_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`document_id`),
  KEY `fk_document_citizen` (`citizen_nic`),
  CONSTRAINT `fk_document_citizen` FOREIGN KEY (`citizen_nic`) REFERENCES `citizen` (`citizen_nic`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citizen_documents`
--

LOCK TABLES `citizen_documents` WRITE;
/*!40000 ALTER TABLE `citizen_documents` DISABLE KEYS */;
INSERT INTO `citizen_documents` VALUES (3,'973590286V','NIC','NIC_1772011821048.pdf','uploads\\documents\\973590286V\\NIC_1772011821048.pdf','2026-02-25 09:30:21'),(4,'975690489V','NIC','NIC_1772015991742.pdf','uploads\\documents\\975690489V\\NIC_1772015991742.pdf','2026-02-25 10:39:52');
/*!40000 ALTER TABLE `citizen_documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `house_building_status`
--

DROP TABLE IF EXISTS `house_building_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `house_building_status` (
  `household_id` int NOT NULL,
  `single_floor_house` tinyint(1) DEFAULT '0',
  `two_floor_house` tinyint(1) DEFAULT '0',
  `multi_floor_house` tinyint(1) DEFAULT '0',
  `super_structure_house` tinyint(1) DEFAULT '0',
  `annex_house` tinyint(1) DEFAULT '0',
  `sub_house` tinyint(1) DEFAULT '0',
  `double_house` tinyint(1) DEFAULT '0',
  `line_house` tinyint(1) DEFAULT '0',
  `slum_house` tinyint(1) DEFAULT '0',
  `hut_house` tinyint(1) DEFAULT '0',
  `group_house` tinyint(1) DEFAULT '0',
  `other_house` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`household_id`),
  CONSTRAINT `fk_house_building_status` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `house_building_status`
--

LOCK TABLES `house_building_status` WRITE;
/*!40000 ALTER TABLE `house_building_status` DISABLE KEYS */;
INSERT INTO `house_building_status` VALUES (1,0,1,0,0,0,0,0,0,0,0,0,0),(4,0,1,0,0,0,0,0,0,0,0,0,0),(5,1,0,0,0,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `house_building_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `house_ownership_status`
--

DROP TABLE IF EXISTS `house_ownership_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `house_ownership_status` (
  `household_id` int NOT NULL,
  `has_own_house` tinyint(1) DEFAULT '0',
  `has_other_house` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`household_id`),
  CONSTRAINT `fk_house_ownership_status` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `house_ownership_status`
--

LOCK TABLES `house_ownership_status` WRITE;
/*!40000 ALTER TABLE `house_ownership_status` DISABLE KEYS */;
INSERT INTO `house_ownership_status` VALUES (1,1,1),(4,1,1),(5,1,0);
/*!40000 ALTER TABLE `house_ownership_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `household`
--

DROP TABLE IF EXISTS `household`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household` (
  `household_id` int NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `district` varchar(100) DEFAULT NULL,
  `gn_division` varchar(100) DEFAULT NULL,
  `application_id` bigint DEFAULT NULL,
  `head_nic` varchar(20) NOT NULL,
  `status` enum('PENDING','PROCESSING','APPROVED','REJECTED') DEFAULT 'PENDING',
  PRIMARY KEY (`household_id`),
  UNIQUE KEY `head_nic` (`head_nic`),
  UNIQUE KEY `unique_household_id` (`household_id`),
  CONSTRAINT `fk_household_citizen` FOREIGN KEY (`head_nic`) REFERENCES `citizen` (`citizen_nic`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `household`
--

LOCK TABLES `household` WRITE;
/*!40000 ALTER TABLE `household` DISABLE KEYS */;
INSERT INTO `household` VALUES (1,'Nilwakka, Kegalle','Kegalle','Puwakdeniya',25,'973590286V','APPROVED'),(3,'No 10, Kany Road, Kegalle','Kegalle','Kegalle',22,'973590288V','PENDING'),(4,'No 120, New Town, Ratnapura','Ratnapura','Ratnapura',21,'111267888V','APPROVED'),(5,'Rambukkana','Kegalle','Rambukkana',24,'975690489V','APPROVED');
/*!40000 ALTER TABLE `household` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `household_building_assets`
--

DROP TABLE IF EXISTS `household_building_assets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household_building_assets` (
  `household_id` int NOT NULL,
  `has_asset_type_1` tinyint(1) DEFAULT NULL,
  `asset_type_1_half_unit` tinyint(1) DEFAULT NULL,
  `asset_type_1_one_unit` tinyint(1) DEFAULT NULL,
  `has_asset_type_2` tinyint(1) DEFAULT NULL,
  `asset_type_2_one_unit` tinyint(1) DEFAULT NULL,
  `asset_type_2_more_than_one` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`household_id`),
  CONSTRAINT `fk_household_building_assets` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `household_building_assets`
--

LOCK TABLES `household_building_assets` WRITE;
/*!40000 ALTER TABLE `household_building_assets` DISABLE KEYS */;
INSERT INTO `household_building_assets` VALUES (1,NULL,NULL,NULL,NULL,NULL,0),(4,1,1,0,1,1,0),(5,NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `household_building_assets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `household_economic_equipment_assets`
--

DROP TABLE IF EXISTS `household_economic_equipment_assets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household_economic_equipment_assets` (
  `household_id` int NOT NULL,
  `motor_boat` tinyint(1) DEFAULT '0' COMMENT 'Motor boat',
  `non_motor_boat` tinyint(1) DEFAULT '0' COMMENT 'Non-motor boat',
  `combined_harvesting_machine` tinyint(1) DEFAULT '0' COMMENT 'Combined harvesting machine',
  `other_agri_or_fishing_equipment` tinyint(1) DEFAULT '0' COMMENT 'Other agricultural or fishing equipment',
  `other_self_employment_tools` tinyint(1) DEFAULT '0' COMMENT 'Other self-employment tools',
  PRIMARY KEY (`household_id`),
  CONSTRAINT `fk_household_economic_equipment_assets` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `household_economic_equipment_assets`
--

LOCK TABLES `household_economic_equipment_assets` WRITE;
/*!40000 ALTER TABLE `household_economic_equipment_assets` DISABLE KEYS */;
INSERT INTO `household_economic_equipment_assets` VALUES (1,0,0,1,0,1),(4,0,0,1,0,1),(5,0,0,0,0,0);
/*!40000 ALTER TABLE `household_economic_equipment_assets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `household_house_equipment_assets`
--

DROP TABLE IF EXISTS `household_house_equipment_assets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household_house_equipment_assets` (
  `household_id` int NOT NULL,
  `gas_cooker` tinyint(1) DEFAULT '0' COMMENT '01',
  `electric_cooker` tinyint(1) DEFAULT '0' COMMENT '02',
  `electric_fan` tinyint(1) DEFAULT '0' COMMENT '03',
  `radio_or_music_player` tinyint(1) DEFAULT '0' COMMENT '04',
  `television` tinyint(1) DEFAULT '0' COMMENT '05',
  `refrigerator` tinyint(1) DEFAULT '0' COMMENT '06',
  `computer_or_laptop` tinyint(1) DEFAULT '0' COMMENT '07',
  PRIMARY KEY (`household_id`),
  CONSTRAINT `fk_household_house_equipment_assets` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `household_house_equipment_assets`
--

LOCK TABLES `household_house_equipment_assets` WRITE;
/*!40000 ALTER TABLE `household_house_equipment_assets` DISABLE KEYS */;
INSERT INTO `household_house_equipment_assets` VALUES (1,1,1,1,0,1,1,1),(4,1,1,1,0,1,1,1),(5,1,0,1,1,1,1,1);
/*!40000 ALTER TABLE `household_house_equipment_assets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `household_livestock_assets`
--

DROP TABLE IF EXISTS `household_livestock_assets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household_livestock_assets` (
  `household_id` int NOT NULL,
  `cattle` tinyint(1) DEFAULT '0' COMMENT 'Cows / Cattle',
  `buffalo` tinyint(1) DEFAULT '0' COMMENT 'Buffalo',
  `goats` tinyint(1) DEFAULT '0' COMMENT 'Goats',
  `sheep` tinyint(1) DEFAULT '0' COMMENT 'Sheep',
  `poultry` tinyint(1) DEFAULT '0' COMMENT 'Poultry (chicken, ducks, etc.)',
  `pigs` tinyint(1) DEFAULT '0' COMMENT 'Pigs',
  `other_livestock` tinyint(1) DEFAULT '0' COMMENT 'Other livestock',
  PRIMARY KEY (`household_id`),
  CONSTRAINT `fk_agricultural_animals_household` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `household_livestock_assets`
--

LOCK TABLES `household_livestock_assets` WRITE;
/*!40000 ALTER TABLE `household_livestock_assets` DISABLE KEYS */;
INSERT INTO `household_livestock_assets` VALUES (1,1,1,0,0,1,0,0),(4,1,1,0,0,1,0,0),(5,0,0,0,0,0,1,1);
/*!40000 ALTER TABLE `household_livestock_assets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `household_members`
--

DROP TABLE IF EXISTS `household_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household_members` (
  `household_id` int NOT NULL,
  `full_name` varchar(150) NOT NULL,
  `relationship_to_head` tinyint DEFAULT NULL COMMENT 'coded values from form',
  `gender` tinyint DEFAULT NULL COMMENT '1=Male, 2=Female',
  `marriage_status` tinyint DEFAULT NULL COMMENT 'coded values',
  `date_of_birth` date DEFAULT NULL,
  `age` int DEFAULT NULL,
  `national_id` varchar(20) DEFAULT NULL,
  `education_level` tinyint DEFAULT NULL COMMENT 'coded values',
  `student_status` tinyint DEFAULT NULL COMMENT '1=Student, 0=Not student',
  `employment_status` tinyint DEFAULT NULL COMMENT 'coded values',
  `monthly_income` decimal(10,2) DEFAULT NULL,
  `social_welfare_benefit` tinyint DEFAULT NULL COMMENT 'coded values',
  `difficulty_status` tinyint DEFAULT NULL COMMENT 'coded values',
  `disability_status` tinyint DEFAULT NULL COMMENT 'coded values',
  `chronic_illness` tinyint DEFAULT NULL COMMENT 'coded values',
  `member_id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`member_id`),
  KEY `fk_household_members` (`household_id`),
  CONSTRAINT `fk_household_members` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `household_members`
--

LOCK TABLES `household_members` WRITE;
/*!40000 ALTER TABLE `household_members` DISABLE KEYS */;
INSERT INTO `household_members` VALUES (4,'Amal',1,1,1,'1990-06-10',40,'90894560V',4,0,1,35000.00,1,0,0,1,143),(1,'Ishani',2,2,2,'2001-06-10',25,'20012345675',4,0,1,35000.00,1,0,0,1,155),(5,'Sasindu',3,1,2,'2001-05-06',24,'20012345678',3,0,1,50000.00,0,0,0,0,156),(5,'Sahin',5,1,2,'1998-03-01',NULL,'9867565V',3,0,1,60000.00,0,0,0,0,157);
/*!40000 ALTER TABLE `household_members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `household_monthly_expenses`
--

DROP TABLE IF EXISTS `household_monthly_expenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household_monthly_expenses` (
  `household_id` int NOT NULL,
  `food_expenses` decimal(12,2) DEFAULT '0.00' COMMENT '01 – ආහාර සහ පාන',
  `housing_expenses` decimal(12,2) DEFAULT '0.00' COMMENT '02 – වාසස්ථාන වියදම්',
  `clothing_expenses` decimal(12,2) DEFAULT '0.00' COMMENT '03 – ඇඳුම් පැළඳුම්',
  `electricity_water_fuel` decimal(12,2) DEFAULT '0.00' COMMENT '04 – විදුලි / ජල / ඉන්ධන',
  `health_expenses` decimal(12,2) DEFAULT '0.00' COMMENT '05 – සෞඛ්‍ය',
  `education_expenses` decimal(12,2) DEFAULT '0.00' COMMENT '06 – අධ්‍යාපන',
  `transport_expenses` decimal(12,2) DEFAULT '0.00' COMMENT '07 – ප්‍රවාහන / ගමන්',
  `communication_expenses` decimal(12,2) DEFAULT '0.00' COMMENT '08 – සන්නිවේදන',
  `loan_installments` decimal(12,2) DEFAULT '0.00' COMMENT '09 – ණය / කට්ටල ගෙවීම්',
  `insurance_savings` decimal(12,2) DEFAULT '0.00' COMMENT '10 – රක්ෂණ / ඉතිරි',
  `social_religious_expenses` decimal(12,2) DEFAULT '0.00' COMMENT '11 – සමාජ / ආගමික',
  `entertainment_expenses` decimal(12,2) DEFAULT '0.00' COMMENT '12 – විනෝදාස්වාද',
  `other_expenses` decimal(12,2) DEFAULT '0.00' COMMENT '13 – වෙනත්',
  `total_expenses` decimal(12,2) GENERATED ALWAYS AS (((((((((((((`food_expenses` + `housing_expenses`) + `clothing_expenses`) + `electricity_water_fuel`) + `health_expenses`) + `education_expenses`) + `transport_expenses`) + `communication_expenses`) + `loan_installments`) + `insurance_savings`) + `social_religious_expenses`) + `entertainment_expenses`) + `other_expenses`)) STORED,
  PRIMARY KEY (`household_id`),
  CONSTRAINT `fk_household_monthly_expenses` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `household_monthly_expenses`
--

LOCK TABLES `household_monthly_expenses` WRITE;
/*!40000 ALTER TABLE `household_monthly_expenses` DISABLE KEYS */;
INSERT INTO `household_monthly_expenses` (`household_id`, `food_expenses`, `housing_expenses`, `clothing_expenses`, `electricity_water_fuel`, `health_expenses`, `education_expenses`, `transport_expenses`, `communication_expenses`, `loan_installments`, `insurance_savings`, `social_religious_expenses`, `entertainment_expenses`, `other_expenses`) VALUES (1,42000.00,3000.00,4000.00,9500.00,7000.00,13000.00,8500.00,2000.00,5000.00,2000.00,1500.00,1000.00,3000.00),(4,42000.00,3000.00,4000.00,9500.00,7000.00,13000.00,8500.00,2000.00,5000.00,2000.00,1500.00,1000.00,3000.00),(5,60000.00,35000.00,10000.00,3000.00,15000.00,20000.00,20000.00,1000.00,0.00,0.00,0.00,5000.00,0.00);
/*!40000 ALTER TABLE `household_monthly_expenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `household_monthly_income`
--

DROP TABLE IF EXISTS `household_monthly_income`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household_monthly_income` (
  `household_id` int NOT NULL,
  `agriculture_income` decimal(12,2) DEFAULT '0.00',
  `business_income` decimal(12,2) DEFAULT '0.00',
  `self_employment_income` decimal(12,2) DEFAULT '0.00',
  `wage_income` decimal(12,2) DEFAULT '0.00',
  `government_assistance_income` decimal(12,2) DEFAULT '0.00',
  `pension_income` decimal(12,2) DEFAULT '0.00',
  `remittance_income` decimal(12,2) DEFAULT '0.00',
  `rental_income` decimal(12,2) DEFAULT '0.00',
  `interest_income` decimal(12,2) DEFAULT '0.00',
  `samurdhi_income` decimal(12,2) DEFAULT '0.00',
  `elder_income` decimal(12,2) DEFAULT '0.00',
  `disabled_income` decimal(12,2) DEFAULT '0.00',
  `local_income` decimal(12,2) DEFAULT '0.00',
  `foreign_income` decimal(12,2) DEFAULT '0.00',
  `donation_income` decimal(12,2) DEFAULT '0.00',
  `other_government_income` decimal(12,2) DEFAULT '0.00',
  `other_income` decimal(12,2) DEFAULT '0.00',
  `total_income` decimal(12,2) GENERATED ALWAYS AS (((((((((((((((((`agriculture_income` + `business_income`) + `self_employment_income`) + `wage_income`) + `government_assistance_income`) + `pension_income`) + `remittance_income`) + `rental_income`) + `interest_income`) + `samurdhi_income`) + `elder_income`) + `disabled_income`) + `local_income`) + `foreign_income`) + `donation_income`) + `other_government_income`) + `other_income`)) STORED,
  PRIMARY KEY (`household_id`),
  CONSTRAINT `fk_household_monthly_income` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `household_monthly_income`
--

LOCK TABLES `household_monthly_income` WRITE;
/*!40000 ALTER TABLE `household_monthly_income` DISABLE KEYS */;
INSERT INTO `household_monthly_income` (`household_id`, `agriculture_income`, `business_income`, `self_employment_income`, `wage_income`, `government_assistance_income`, `pension_income`, `remittance_income`, `rental_income`, `interest_income`, `samurdhi_income`, `elder_income`, `disabled_income`, `local_income`, `foreign_income`, `donation_income`, `other_government_income`, `other_income`) VALUES (1,20000.00,30000.00,8000.00,40000.00,5000.00,5000.00,0.00,10000.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,4000.00),(4,20000.00,30000.00,8000.00,40000.00,5000.00,5000.00,0.00,10000.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,4000.00),(5,10000.00,20000.00,1000.00,100000.00,0.00,50000.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00);
/*!40000 ALTER TABLE `household_monthly_income` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `household_power_status`
--

DROP TABLE IF EXISTS `household_power_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household_power_status` (
  `household_id` int NOT NULL,
  `electricity` tinyint(1) DEFAULT '0',
  `solar_power` tinyint(1) DEFAULT '0',
  `generator` tinyint(1) DEFAULT '0',
  `biogas` tinyint(1) DEFAULT '0',
  `kerosene` tinyint(1) DEFAULT '0',
  `poor` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`household_id`),
  CONSTRAINT `fk_household_power_status` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `household_power_status`
--

LOCK TABLES `household_power_status` WRITE;
/*!40000 ALTER TABLE `household_power_status` DISABLE KEYS */;
INSERT INTO `household_power_status` VALUES (1,1,1,0,0,0,0),(4,1,1,0,0,0,0),(5,1,0,0,0,0,0);
/*!40000 ALTER TABLE `household_power_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `household_vehicle_assets`
--

DROP TABLE IF EXISTS `household_vehicle_assets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household_vehicle_assets` (
  `household_id` int NOT NULL,
  `motor_bicycle_cc_125_or_less` tinyint(1) DEFAULT '0' COMMENT '≤ CC 125',
  `motor_bicycle_above_cc_125` tinyint(1) DEFAULT '0' COMMENT '> CC 125',
  `three_wheeler` tinyint(1) DEFAULT '0',
  `motor_car` tinyint(1) DEFAULT '0',
  `van_or_jeep` tinyint(1) DEFAULT '0',
  `bus` tinyint(1) DEFAULT '0',
  `tractor_or_trailer` tinyint(1) DEFAULT '0',
  `heavy_vehicle_category_2` tinyint(1) DEFAULT '0',
  `heavy_vehicle_category_4` tinyint(1) DEFAULT '0',
  `other_transport_equipment` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`household_id`),
  CONSTRAINT `fk_household_vehicle_assets` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `household_vehicle_assets`
--

LOCK TABLES `household_vehicle_assets` WRITE;
/*!40000 ALTER TABLE `household_vehicle_assets` DISABLE KEYS */;
INSERT INTO `household_vehicle_assets` VALUES (1,0,1,0,1,0,0,0,0,0,0),(4,0,1,0,1,0,0,0,0,0,0),(5,0,1,0,1,0,0,0,0,0,0);
/*!40000 ALTER TABLE `household_vehicle_assets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `household_water_supply`
--

DROP TABLE IF EXISTS `household_water_supply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household_water_supply` (
  `household_id` int NOT NULL,
  `protected_well` tinyint(1) DEFAULT NULL,
  `public_well` tinyint(1) DEFAULT NULL,
  `tube_well` tinyint(1) DEFAULT NULL,
  `pipe_borne_water` tinyint(1) DEFAULT NULL,
  `rainwater` tinyint(1) DEFAULT NULL,
  `bottled_water` tinyint(1) DEFAULT NULL,
  `water_bowser` tinyint(1) DEFAULT NULL,
  `other_water_supply` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`household_id`),
  CONSTRAINT `fk_water_household` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `household_water_supply`
--

LOCK TABLES `household_water_supply` WRITE;
/*!40000 ALTER TABLE `household_water_supply` DISABLE KEYS */;
INSERT INTO `household_water_supply` VALUES (5,1,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `household_water_supply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `message` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `citizen_nic` varchar(20) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `notification_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`notification_id`),
  KEY `fk_notification_citizen` (`citizen_nic`),
  CONSTRAINT `fk_notification_citizen` FOREIGN KEY (`citizen_nic`) REFERENCES `citizen` (`citizen_nic`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES ('Your payment of LKR 50 has been approved and completed. Reference: 3.','2026-02-26 18:02:08','111267888V',' Payment Approved',3,NULL),('Your payment of LKR 50,000 has been approved and completed. Reference: 23333.','2026-02-28 07:44:21','111267888V',' Payment Approved',6,NULL),('Your payment of LKR 12,000 has been approved and completed. Reference: REF 2345.','2026-03-02 06:49:11','975690489V',' Payment Approved',8,'thushanceyloncloud@gmail.com');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `officer_status`
--

DROP TABLE IF EXISTS `officer_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `officer_status` (
  `application_id` bigint NOT NULL,
  `gn_officer_status` enum('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
  `gn_officer_note` varchar(500) DEFAULT NULL,
  `gn_reviewed_at` timestamp NULL DEFAULT NULL,
  `second_officer_status` enum('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
  `second_officer_note` varchar(500) DEFAULT NULL,
  `second_reviewed_at` timestamp NULL DEFAULT NULL,
  `admin_status` enum('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
  `admin_note` varchar(500) DEFAULT NULL,
  `admin_reviewed_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`application_id`),
  CONSTRAINT `fk_officer_status_application` FOREIGN KEY (`application_id`) REFERENCES `applications` (`application_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `officer_status`
--

LOCK TABLES `officer_status` WRITE;
/*!40000 ALTER TABLE `officer_status` DISABLE KEYS */;
INSERT INTO `officer_status` VALUES (21,'APPROVED',' Verified and approved','2026-02-26 06:18:38','APPROVED','Documents verified','2026-02-26 06:10:15','APPROVED','Final approval granted','2026-02-26 06:20:33'),(24,'APPROVED','Verified and approved','2026-03-02 06:33:43','APPROVED','Documents verified','2026-03-02 06:35:38','APPROVED','Final approval granted','2026-03-02 06:37:33');
/*!40000 ALTER TABLE `officer_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `payment_id` bigint NOT NULL AUTO_INCREMENT,
  `citizen_nic` varchar(20) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `payment_method` enum('CASH','BANK_TRANSFER','CHEQUE','ONLINE') NOT NULL,
  `status` enum('PENDING','COMPLETED','FAILED','CANCELLED') DEFAULT 'PENDING',
  `reference_no` varchar(100) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`payment_id`),
  KEY `fk_payment_citizen` (`citizen_nic`),
  CONSTRAINT `fk_payment_citizen` FOREIGN KEY (`citizen_nic`) REFERENCES `citizen` (`citizen_nic`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (4,'111267888V',2000.00,'2026-02-26 16:15:31','BANK_TRANSFER','COMPLETED','REF 2345','Monthly welfare payment'),(8,'111267888V',50000.00,'2026-02-28 07:44:13','BANK_TRANSFER','COMPLETED','23333','yes'),(10,'975690489V',12000.00,'2026-03-02 06:49:04','BANK_TRANSFER','COMPLETED','REF 2345','Payment Approved');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `expiry_date` datetime NOT NULL,
  `revoked` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (3,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzYzOTYzMTk4LCJleHAiOjE3NjQ1Njc5OTh9.6y_vzrzR-rt-3H9PMGqlc7PSmP3H8bciwbXPkUrOFi4','2025-12-01 11:16:38',0),(5,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzYzOTY2MzEwLCJleHAiOjE3NjQ1NzExMTB9.49ff5EjtFSnEO9iUbza_PK70OFeyAwxfE0iWZ1vTYZ8','2025-12-01 12:08:31',0),(6,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzYzOTgwODQ4LCJleHAiOjE3NjQ1ODU2NDh9.J7GEOUKIZ2bBOSB8JpctRih-o1ElTpa6tUdaqq_LVAQ','2025-12-01 16:10:49',0),(7,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzYzOTgwOTkzLCJleHAiOjE3NjQ1ODU3OTN9.79eZ_OuIJXXSpD6xVx8qnt5qC_LOOd0fGX1FCeJ2TlQ','2025-12-01 16:13:13',0),(8,'973590288V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODhWIiwiaWF0IjoxNzYzOTgxMjYwLCJleHAiOjE3NjQ1ODYwNjB9.R46Y1oLDASKCNackPRyLLbdNq0BvkcQ_XJT8zyFU5tA','2025-12-01 16:17:41',0),(9,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzYzOTgxODUwLCJleHAiOjE3NjQ1ODY2NTB9.bsyy8yB2_X_xl-afsg1Yo569Awts3qWzn8Qo26iIr2s','2025-12-01 16:27:31',0),(11,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzYzOTg2ODU2LCJleHAiOjE3NjQ1OTE2NTZ9.RfOctAH9arciSh4dHaCSkhqLUQvEqCPSu6in4Q0rttg','2025-12-01 17:50:57',0),(12,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzYzOTg3MzUyLCJleHAiOjE3NjQ1OTIxNTJ9.95XC61TTROBTpydNRYgO5q7vVbbeXUnUZTojR0IK95Q','2025-12-01 17:59:13',0),(13,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzYzOTk2ODk2LCJleHAiOjE3NjQ2MDE2OTZ9.HCh6200bOVrAMdhxJwA6aXA0zIZLrkHFgLEYcnx2jzo','2025-12-01 20:38:17',0),(14,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzYzOTk3ODExLCJleHAiOjE3NjQ2MDI2MTF9.wjjyukJ_UYzH6oVoRdC2E1fkE-YSwDSTJCiywKdl_qQ','2025-12-01 20:53:32',0),(16,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY0MDQxMDQ1LCJleHAiOjE3NjQ2NDU4NDV9.qUdn1-qoK96wtwSeNePvEsbvQO7eGru1QRRtuLjDTPM','2025-12-02 08:54:05',0),(21,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY0MDQ1MDcyLCJleHAiOjE3NjQ2NDk4NzJ9.owLRFnESsLAXk7eeX2Eo15vgLHVXc932kICcrmAeewE','2025-12-02 10:01:13',0),(23,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY0MDU0OTMwLCJleHAiOjE3NjQ2NTk3MzB9.zHm2C8nwWjeNEM7fWMJo81knH-Rjr0dZc5hdvCJ-3_w','2025-12-02 12:45:30',0),(25,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY0MDYyODI4LCJleHAiOjE3NjQ2Njc2Mjh9.95qVy-VoDRytMDiCbp611WBSz_e3hWxZv9zJWLmFinI','2025-12-02 14:57:08',0),(27,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY0MDY0MDgxLCJleHAiOjE3NjQ2Njg4ODF9.zgqnI0MQcruzJFABpw6c-n56GfYFedMP9vRYbmgg9ek','2025-12-02 15:18:02',0),(28,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY0MDY0MDgxLCJleHAiOjE3NjQ2Njg4ODF9.zgqnI0MQcruzJFABpw6c-n56GfYFedMP9vRYbmgg9ek','2025-12-02 15:18:02',0),(31,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY0MDgwNzgyLCJleHAiOjE3NjQ2ODU1ODJ9.xvQiR5Gt9zLF_8sFJkZUVTHwbQL2WvrplFIw_f0V06g','2025-12-02 19:56:23',0),(32,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY0MDg0MDk0LCJleHAiOjE3NjQ2ODg4OTR9.yWWRaQRAL81loR7P-MRnAz2L251VZ0-XDolHc1hPj5I','2025-12-02 20:51:34',0),(35,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY0NTc4ODc5LCJleHAiOjE3NjUxODM2Nzl9.h5eZgy4nWr8KnLHoGGlS0ZRof7CaWirRLjRLzEobzwQ','2025-12-08 14:18:00',0),(38,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY0NjUyNDEwLCJleHAiOjE3NjUyNTcyMTB9.e2WzUX0KA-vm9MrDWUjFSSEd8-N8mV_-j352Aw5FL80','2025-12-09 10:43:31',0),(39,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4MDI4Mzg4LCJleHAiOjE3Njg2MzMxODh9.Bl8XXwGRmtO__d-BLWsDkFFlSDqdR9T0cah_-kgrXbc','2026-01-17 12:29:49',0),(40,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4MDI4NTIyLCJleHAiOjE3Njg2MzMzMjJ9.2bEV3qYH5_4fGXClZW0dGsOj6Qp2L7lZyw81Iw2qbpk','2026-01-17 12:32:02',0),(41,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4MDI5MTIzLCJleHAiOjE3Njg2MzM5MjN9.blvQssEQ0LHWdTM7ae0wbpeZBVlrkRUrkvrkP6NYgfE','2026-01-17 12:42:03',0),(42,'973590288V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODhWIiwiaWF0IjoxNzY4MDI5MjkxLCJleHAiOjE3Njg2MzQwOTF9.6kcxQnfq1fNPz4jQrTlZhoWhhR208TjvZxX3Uurfom4','2026-01-17 12:44:51',0),(44,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4MDMxODA3LCJleHAiOjE3Njg2MzY2MDd9.jp59eleahAr2-QRxIOwwG0djIMehjGngJzCyxdVh9x8','2026-01-17 13:26:48',0),(45,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4MDMxODYzLCJleHAiOjE3Njg2MzY2NjN9.and-ux61mV7T5qWNkR9tYhKiv_LbDJq2RjaYEJ5zHmo','2026-01-17 13:27:43',0),(46,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4MDM1NzUzLCJleHAiOjE3Njg2NDA1NTN9.KQISIK6HX3RsQXSEkq92_rp2vcU8tpnux7Z1fXNDFvc','2026-01-17 14:32:33',0),(47,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4MTA0NDMyLCJleHAiOjE3Njg3MDkyMzJ9.hf3bqC98ARvmbXDfwiKBIHyHIVAxanm4cQG8g8rJD2g','2026-01-18 09:37:13',0),(48,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4Mjg4NjY4LCJleHAiOjE3Njg4OTM0Njh9.v4u6qa0xl8MvYjLVoeHWdVHm7uM2CvpUelKD55aUIZw','2026-01-20 12:47:48',0),(50,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4MzA2MDQ3LCJleHAiOjE3Njg5MTA4NDd9.4rb_wR9EL5RV9sw2rW5eGfHZkX31z17KVQcWarzOxB4','2026-01-20 17:37:28',0),(51,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4NDEwNTE0LCJleHAiOjE3NjkwMTUzMTR9.EgtrnmtvwbwSIiAzCgyf-htKM_ueWvWyJz3MgsnDSOI','2026-01-21 22:38:35',0),(52,'973590288V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODhWIiwiaWF0IjoxNzY4NDEyMTE4LCJleHAiOjE3NjkwMTY5MTh9.4oc0fvoDDCmY2bTy2EqBw0x2nMDayrVsW-pz1E0RhOg','2026-01-21 23:05:19',0),(54,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4NDk5NzEwLCJleHAiOjE3NjkxMDQ1MTB9.XOwbC6THYYPUR41Lxz-0vXIUO0vQAnUcc0RWt2A5g4o','2026-01-22 23:25:11',0),(55,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4NTAzMTY5LCJleHAiOjE3NjkxMDc5Njl9.st_58Yshr2nfU9ODYEI0LEeO5XoDxcQxHYk1XLzrfgg','2026-01-23 00:22:50',0),(56,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4NTA1MDQ2LCJleHAiOjE3NjkxMDk4NDZ9.wj9ECoBGzGkaE39BcKD8XV1M4S9KSghlL2T47Y-yyig','2026-01-23 00:54:06',0),(57,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4NTA1MTY0LCJleHAiOjE3NjkxMDk5NjR9.1HDawezRV9n4_bYTqwvY-t2hXku5O-TF1i5ci7yRrnI','2026-01-23 00:56:05',0),(58,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzY4NTA1MzA5LCJleHAiOjE3NjkxMTAxMDl9.k0btj0VbDc1nE-qECBF_enf9WU73DrozKjb_j5qvo7Y','2026-01-23 00:58:30',0),(59,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxNDEwMjQ2LCJleHAiOjE3NzIwMTUwNDZ9.q5GgBIbcMCcn_WOZ99sfcwbaPHVMLzbUeNoKSl30W74','2026-02-25 15:54:06',0),(61,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxNDE1Njk3LCJleHAiOjE3NzIwMjA0OTd9.smUiOn56FbHvUI93aLZX_zQqoENTA4YiJ0GLsoEZpOE','2026-02-25 17:24:57',0),(62,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxNDE3NzQ4LCJleHAiOjE3NzIwMjI1NDh9.32vycX4sLAHHCTS3MbP_k_eGLf08sRCtfAYF8P8oHig','2026-02-25 17:59:09',0),(63,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxODI5ODE3LCJleHAiOjE3NzI0MzQ2MTd9.1r_I5uIV0UYSqLq_jjgTpYiW8D1HEpdkiNz1prRlOqw','2026-03-02 12:26:58',0),(64,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxODMwNTAzLCJleHAiOjE3NzI0MzUzMDN9.VE6lwrNl05USNBO1Jxgv7Yzy2DvCb47inGDsB6OJjMc','2026-03-02 12:38:23',0),(66,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxODYwMTk4LCJleHAiOjE3NzI0NjQ5OTh9.JR8PLiowHXj8OtmEq2GnbWzTKmEdSopvuhvwnELAUSs','2026-03-02 20:53:18',0),(67,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxODcxNzMwLCJleHAiOjE3NzI0NzY1MzB9.emGFDRpN1_hYuvGPrtmYg-h3vciriUV8q37d-a_0KI8','2026-03-03 00:05:30',0),(68,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxODcyMTk1LCJleHAiOjE3NzI0NzY5OTV9._X-23KkIvovcuA0HU3IvN7vCHmz7YsJ55ez6lsT2T3c','2026-03-03 00:13:15',0),(69,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxOTA3NDU2LCJleHAiOjE3NzI1MTIyNTZ9.cVy-u3M02MuTfsC5A5uyUOqD7FGNmlZnmPqqWEQzTUM','2026-03-03 10:00:57',0),(70,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxOTExODY1LCJleHAiOjE3NzI1MTY2NjV9.V_fpdsB3VqgUKutKbDXaIZEPEMRcXUIkxPNY2ru5mjE','2026-03-03 11:14:25',0),(71,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxOTEyNzc3LCJleHAiOjE3NzI1MTc1Nzd9.AxW3eOGPDxaxcqi6FF5038FyQEUuqDyH0fPbDmKmyQI','2026-03-03 11:29:38',0),(72,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxOTEzNDkxLCJleHAiOjE3NzI1MTgyOTF9.Onwnz7fxplU_DqAp2vVwDTgeM45uer2OyXPDHGlg5so','2026-03-03 11:41:31',0),(73,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxOTE1NjU3LCJleHAiOjE3NzI1MjA0NTd9._rQW5IpT3-5TnHsVANaTMDOc8UxefnHsoXKMv9STcU8','2026-03-03 12:17:38',0),(74,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxOTQyMzcyLCJleHAiOjE3NzI1NDcxNzJ9.zMfL6Zgd-w3WAAU7wwwC-U2Di076v_t0NFVZqJBDzYE','2026-03-03 19:42:53',0),(75,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxOTQzMTQxLCJleHAiOjE3NzI1NDc5NDF9.3b1GkP_F290-DndnoEYNo2mXjLkIcWDtdqtQdEKy75E','2026-03-03 19:55:41',0),(76,'975690489V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzU2OTA0ODlWIiwiaWF0IjoxNzcxOTQ2MjUyLCJleHAiOjE3NzI1NTEwNTJ9.UH-K6tzexnZzDeLDr9_BGsmCOGuJdw7o5mE9Y0pcpy8','2026-03-03 20:47:32',0),(77,'975690489V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzU2OTA0ODlWIiwiaWF0IjoxNzcxOTk2MDMxLCJleHAiOjE3NzI2MDA4MzF9.d1lcUWnu7_aJ0uCooMdmFzXSLDzHLk1UVjA-GqnDfJc','2026-03-04 10:37:12',0),(78,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcxOTk5NjQxLCJleHAiOjE3NzI2MDQ0NDF9.IrI3XWltvMhnqLaRHBweIMGfN5biHTXXyGleBX8fT-w','2026-03-04 11:37:22',0),(79,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcyMDE3ODkzLCJleHAiOjE3NzI2MjI2OTN9.vxiPGvGXTXkosSrePG9PJKmKy_CydLKP5xiN1cJW_LI','2026-03-04 16:41:33',0),(80,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcyMDc4NTE2LCJleHAiOjE3NzI2ODMzMTZ9.lknVWd4ovselRgGVRrwOq-s7R9AKNJ0fVsVfXpGWC44','2026-03-05 09:31:57',0),(81,'973590288V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODhWIiwiaWF0IjoxNzcyMDg2MTU0LCJleHAiOjE3NzI2OTA5NTR9.bidoLW8wt9rJzybaiD49dE_ueVgSbQiYQekftVSgtRU','2026-03-05 11:39:15',0),(82,'975678345V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzU2NzgzNDVWIiwiaWF0IjoxNzcyMDg2Njc1LCJleHAiOjE3NzI2OTE0NzV9.1fCUayfwv2shETvR_u0ffkk7Wryvi--rNCWqqD0YyNs','2026-03-05 11:47:56',0),(83,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcyMDg2Nzk5LCJleHAiOjE3NzI2OTE1OTl9.68oyoa_JxYfxHjcx-_0dOgJNQIPgtrd2ynf2kKwHCDU','2026-03-05 11:50:00',0),(85,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcyMjE0NjE1LCJleHAiOjE3NzI4MTk0MTV9.4i5kqw9XG8W8dsPkYEmequ-VsbYEBygWMvEc2lBf94s','2026-03-06 23:20:15',0),(86,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcyMjY0NTM5LCJleHAiOjE3NzI4NjkzMzl9.Z7XdBwYfBKBnz2gI3YzLi08xrCbRBm9zUrm2kImtWtk','2026-03-07 13:12:20',0),(88,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcyMjk0ODQxLCJleHAiOjE3NzI4OTk2NDF9.n2a-CVqVHOrDA1WDhLLmKAm4KTPKVwvjMa-FUgxEchY','2026-03-07 21:37:22',0),(89,'975678345V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzU2NzgzNDVWIiwiaWF0IjoxNzcyMjk2NDc3LCJleHAiOjE3NzI5MDEyNzd9.7jIzDXTwB_cVSeqIQVwgfgZa0Oa439xWbvZ5Xo2hEaA','2026-03-07 22:04:38',0),(90,'975678345V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzU2NzgzNDVWIiwiaWF0IjoxNzcyMzM4MTY5LCJleHAiOjE3NzI5NDI5Njl9.ZG0sPmybyYfRf3lsyk-hMS84cclkmn4jzonKUnAfVsc','2026-03-08 09:39:29',0),(91,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcyNDE1NDAyLCJleHAiOjE3NzMwMjAyMDJ9.EwgDNyysiuFC6VE9NcbqL5_NXHMIip7ubc-SkSn5nl8','2026-03-09 07:06:42',0),(92,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcyNDE1NTk4LCJleHAiOjE3NzMwMjAzOTh9._uhOg77AN-vWJkhzeNDecELZr650cxFHODP3bcEMwWA','2026-03-09 07:09:58',0),(93,'975678345V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzU2NzgzNDVWIiwiaWF0IjoxNzcyNDE3ODc4LCJleHAiOjE3NzMwMjI2Nzh9.VHzoKvcRqUSLr51F37UasDy-8DRsWm_T8y6_dw0DdFg','2026-03-09 07:47:58',0),(94,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcyNDE3OTcyLCJleHAiOjE3NzMwMjI3NzJ9.79CIvWZWxhqfNdyp2AQ6a1wQ2aCiftEoJJCv45p-zns','2026-03-09 07:49:32',0),(95,'975678345V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzU2NzgzNDVWIiwiaWF0IjoxNzcyNDMyODI4LCJleHAiOjE3NzMwMzc2Mjh9.HUiHd2_jyhI6xhNpzXW1sfb2CNaraSG0AIXL3ud542U','2026-03-09 11:57:09',0),(96,'973590288V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODhWIiwiaWF0IjoxNzcyNDMzMzE1LCJleHAiOjE3NzMwMzgxMTV9.UY6qy_cu5j9B9fsxR8n_QMRS1XSa_K5rO17uC1vweV0','2026-03-09 12:05:15',0),(97,'973590286V','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM1OTAyODZWIiwiaWF0IjoxNzcyNDMzNDE1LCJleHAiOjE3NzMwMzgyMTV9.Koh7vojovXRS311oFApdfJhmI-nsqZDeYK2aotqalCs','2026-03-09 12:06:56',0);
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` char(36) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nic` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` enum('GN_OFFICER','OFFICER','ADMIN') NOT NULL,
  `active` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `email_2` (`email`),
  UNIQUE KEY `nic` (`nic`),
  UNIQUE KEY `nic_2` (`nic`),
  UNIQUE KEY `nic_3` (`nic`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `password` (`password`),
  UNIQUE KEY `nic_4` (`nic`),
  UNIQUE KEY `phone_2` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('UID001','$2a$10$uR9kMfYmx5UaiTsd7zk5YONY6G754q2gpPpg7rFljXd9uKdIcOTHu','thushandrod24@gmail.com','Thushan','973590286V','0713169722','ADMIN',1),('UID002','$2a$10$FpC9wm8t3/d9OrhYlGpDXOYaZdTv25XU3c4E36BBAhmd9e1Vr2LWC','thushan@gmail.com','thushan2','973590280V','0716789345','OFFICER',0),('UID003','$2a$10$ZDhxkt.40RzwpvsKcO6C7.d3VoKoFC8e300ub6e3Y4lO2juCRv7Rq','thushanceyloncloud@gmail.com','kalana','973590288V','0773169730','OFFICER',1),('UID004','$2a$10$X0.cg8fJO99y3edczf/7XOSLYqgPrAoWYz9oKt44IsuCiaFxLoipm','thushankalana24@gmail.com','Shashitha','975678345V','0714567456','GN_OFFICER',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-02 13:31:47
