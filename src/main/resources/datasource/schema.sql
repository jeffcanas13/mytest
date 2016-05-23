USE atms;

--
-- Table structure for table `user`
--

-- DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id`              INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `employee_number` VARCHAR(16)               DEFAULT NULL,
  `first_name`      VARCHAR(32)               NOT NULL,
  `middle_name`     VARCHAR(32)               DEFAULT NULL,
  `last_name`       VARCHAR(32)               DEFAULT NULL,
  `email`           VARCHAR(64)               DEFAULT NULL,
  `password`        CHAR(60)                  NOT NULL,
  `login_attempts`  TINYINT(4)                DEFAULT NULL,
  `status_id`       INT(11) UNSIGNED          DEFAULT NULL,
  `created_by`      INT(11) UNSIGNED          DEFAULT NULL,
  `created_date`    DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_status_idx` (`status_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

-- DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id`    INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `label` VARCHAR(16)               DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vertical`
--

-- DROP TABLE IF EXISTS `vertical`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vertical` (
  `id`           INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code`         VARCHAR(16)               DEFAULT NULL,
  `name`         VARCHAR(32)               DEFAULT NULL,
  `description`  VARCHAR(256)              DEFAULT NULL,
  `status_id`    INT(11) UNSIGNED          DEFAULT NULL,
  `created_by`   INT(11) UNSIGNED          DEFAULT NULL,
  `created_date` DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vertical`
--

LOCK TABLES `vertical` WRITE;
/*!40000 ALTER TABLE `vertical` DISABLE KEYS */;
/*!40000 ALTER TABLE `vertical` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

--
-- Table structure for table `project`
--

-- DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id`                  INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code`                VARCHAR(16)               DEFAULT NULL,
  `name`                VARCHAR(32)               DEFAULT NULL,
  `description`         VARCHAR(256)              DEFAULT NULL,
  `date_start`          DATETIME                  DEFAULT NULL,
  `date_end`            DATETIME                  DEFAULT NULL,
  `target_hours`        DECIMAL(7, 2)             DEFAULT 0.00,
  `source_milestone_id` INT(11) UNSIGNED          DEFAULT NULL,
  `owner_group_id`      INT(11) UNSIGNED          DEFAULT NULL,
  `vertical_id`         INT(11) UNSIGNED          DEFAULT NULL,
  `client`              VARCHAR(64)               DEFAULT NULL,
  `status_id`           INT(11) UNSIGNED          DEFAULT NULL,
  `created_by`          INT(11) UNSIGNED          DEFAULT NULL,
  `created_date`         DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_project_vertical_idx` (`vertical_id`),
  CONSTRAINT `fk_project_vertical` FOREIGN KEY (`vertical_id`) REFERENCES `vertical` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `milestone`
--

-- DROP TABLE IF EXISTS `milestone`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `milestone` (
  `id`           INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title`        VARCHAR(32)               DEFAULT NULL,
  `description`  VARCHAR(128)              DEFAULT NULL,
  `target_hours` DECIMAL(7, 2)             DEFAULT 0.00,
  `project_id`   INT(11) UNSIGNED          DEFAULT NULL,
  `status_id`    INT(11) UNSIGNED          DEFAULT NULL,
  `created_by`   INT(11) UNSIGNED          DEFAULT NULL,
  `created_date` DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_project_idx` (`project_id`),
  CONSTRAINT `fk_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `milestone`
--

LOCK TABLES `milestone` WRITE;
/*!40000 ALTER TABLE `milestone` DISABLE KEYS */;
/*!40000 ALTER TABLE `milestone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assigned_milestone_status`
--

DROP TABLE IF EXISTS `assigned_milestone_status`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assigned_milestone_status` (
  `id`        INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `label`     VARCHAR(32)               DEFAULT NULL,
  `order`     INT(11) UNSIGNED          DEFAULT NULL,
  `status_id` INT(11) UNSIGNED          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assigned_milestone_status`
--

LOCK TABLES `assigned_milestone_status` WRITE;
/*!40000 ALTER TABLE `assigned_milestone_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `assigned_milestone_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assigned_milestone`
--

-- DROP TABLE IF EXISTS `assigned_milestone`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assigned_milestone` (
  `id`                           INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `milestone_id`                 INT(11) UNSIGNED          DEFAULT NULL,
  `assigned_to`                  INT(11) UNSIGNED          DEFAULT NULL,
  `details`                      VARCHAR(128)              DEFAULT NULL,
  `due_date`                     DATETIME                  DEFAULT NULL,
  `duration`                     DECIMAL(7, 2)             DEFAULT 0.00,
  `assigned_milestone_status_id` INT(11) UNSIGNED          DEFAULT NULL,
  `assigned_by`                  INT(11) UNSIGNED          DEFAULT NULL,
  `assigned_date`                DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_milestone_idx` (`milestone_id`),
  KEY `fk_milestone_user_idx` (`assigned_to`),
  KEY `fk_milestone_status_idx` (`assigned_milestone_status_id`),
  CONSTRAINT `fk_milestone_status` FOREIGN KEY (`assigned_milestone_status_id`) REFERENCES `assigned_milestone_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_milestone` FOREIGN KEY (`milestone_id`) REFERENCES `milestone` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_milestone_user` FOREIGN KEY (`assigned_to`) REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assigned_milestone`
--

LOCK TABLES `assigned_milestone` WRITE;
/*!40000 ALTER TABLE `assigned_milestone` DISABLE KEYS */;
/*!40000 ALTER TABLE `assigned_milestone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

-- DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id`           INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code`         VARCHAR(16)               DEFAULT NULL,
  `name`         VARCHAR(32)               DEFAULT NULL,
  `description`  VARCHAR(256)              DEFAULT NULL,
  `status_id`    INT(11) UNSIGNED          DEFAULT NULL,
  `created_by`   INT(11) UNSIGNED          DEFAULT NULL,
  `created_date` DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assigned_milestone_activity`
--

-- DROP TABLE IF EXISTS `assigned_milestone_activity`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assigned_milestone_activity` (
  `id`                    INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `assigned_milestone_id` INT(11) UNSIGNED          DEFAULT NULL,
  `task_id`               INT(11) UNSIGNED          DEFAULT NULL,
  `datetime_start`        DATETIME                  DEFAULT NULL,
  `datetime_end`          DATETIME                  DEFAULT NULL,
  `total_time`            DECIMAL(7, 2)             DEFAULT 0.00,
  `remarks`               VARCHAR(128)              DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_assigned_milestone_idx` (`assigned_milestone_id`),
  KEY `fk_assigned_task_idx` (`task_id`),
  CONSTRAINT `fk_assigned_milestone` FOREIGN KEY (`assigned_milestone_id`) REFERENCES `assigned_milestone` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_assigned_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assigned_milestone_activity`
--

LOCK TABLES `assigned_milestone_activity` WRITE;
/*!40000 ALTER TABLE `assigned_milestone_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `assigned_milestone_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit`
--

-- DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `id`      INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title`   VARCHAR(32)               DEFAULT NULL,
  `type`    VARCHAR(16)               DEFAULT NULL,
  `date`    DATETIME                  DEFAULT NULL,
  `details` VARCHAR(45)               DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

-- DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `id`            INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(32)               DEFAULT NULL,
  `description`   VARCHAR(256)              DEFAULT NULL,
  `status_id`     INT(11) UNSIGNED          DEFAULT NULL,
  `vertical_id`   INT(11) UNSIGNED          DEFAULT NULL,
  `created_by`    INT(11) UNSIGNED          DEFAULT NULL,
  `created_date`  DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_group_vertical_idx` (`vertical_id`),
  CONSTRAINT `fk_group_vertical` FOREIGN KEY (`vertical_id`) REFERENCES `vertical` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_member_role`
--

-- DROP TABLE IF EXISTS `group_member_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_member_role` (
  `id`        INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `label`     VARCHAR(32)               DEFAULT NULL,
  `order`     INT(11) UNSIGNED          DEFAULT NULL,
  `status_id` INT(11) UNSIGNED          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_member_role`
--

LOCK TABLES `group_member_role` WRITE;
/*!40000 ALTER TABLE `group_member_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_member_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_member`
--

-- DROP TABLE IF EXISTS `group_member`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_member` (
  `id`                   INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `group_id`             INT(11) UNSIGNED          DEFAULT NULL,
  `user_id`              INT(11) UNSIGNED          DEFAULT NULL,
  `group_member_role_id` INT(11) UNSIGNED          DEFAULT NULL,
  `created_by`           INT(11) UNSIGNED          DEFAULT NULL,
  `created_date`         DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_group_member_group_idx` (`group_id`),
  KEY `fk_group_member_role_idx` (`group_member_role_id`),
  KEY `fk_group_member_user_idx` (`user_id`),
  CONSTRAINT `fk_group_member_role` FOREIGN KEY (`group_member_role_id`) REFERENCES `group_member_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_member_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_member_group` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_member`
--

LOCK TABLES `group_member` WRITE;
/*!40000 ALTER TABLE `group_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taskset`
--

-- DROP TABLE IF EXISTS `taskset`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taskset` (
  `id`           INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`         VARCHAR(32)               DEFAULT NULL,
  `description`  VARCHAR(256)              DEFAULT NULL,
  `status_id`    INT(11) UNSIGNED          DEFAULT NULL,
  `created_by`   INT(11) UNSIGNED          DEFAULT NULL,
  `created_date` DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taskset`
--

LOCK TABLES `taskset` WRITE;
/*!40000 ALTER TABLE `taskset` DISABLE KEYS */;
/*!40000 ALTER TABLE `taskset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taskset_task`
--

-- DROP TABLE IF EXISTS `taskset_task`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taskset_task` (
  `id`           INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `taskset_id`   INT(11) UNSIGNED          DEFAULT NULL,
  `task_id`      INT(11) UNSIGNED          DEFAULT NULL,
  `status_id`    INT(11) UNSIGNED          DEFAULT NULL,
  `created_by`   INT(11) UNSIGNED          DEFAULT NULL,
  `created_date` DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_taskset_task_idx` (`taskset_id`),
  KEY `fk_taskset_task1_idx` (`task_id`),
  CONSTRAINT `fk_taskset_task` FOREIGN KEY (`taskset_id`) REFERENCES `taskset` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_taskset_task1` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taskset_task`
--

LOCK TABLES `taskset_task` WRITE;
/*!40000 ALTER TABLE `taskset_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `taskset_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_picture`
--

-- DROP TABLE IF EXISTS `user_picture`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_picture` (
  `id`        INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `picture`   MEDIUMBLOB       NOT NULL,
  `file_name` VARCHAR(32)               DEFAULT NULL,
  `file_type` VARCHAR(16)               DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`id`),
  KEY `fk_user_idx` (`id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_picture`
--

LOCK TABLES `user_picture` WRITE;
/*!40000 ALTER TABLE `user_picture` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_taskset`
--

-- DROP TABLE IF EXISTS `user_taskset`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_taskset` (
  `id`           INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`      INT(11) UNSIGNED          DEFAULT NULL,
  `taskset_id`   INT(11) UNSIGNED          DEFAULT NULL,
  `status_id`    INT(11) UNSIGNED          DEFAULT NULL,
  `created_by`   INT(11) UNSIGNED          DEFAULT NULL,
  `created_date` DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_taskset_idx` (`user_id`),
  KEY `fk_taskset_idx` (`taskset_id`),
  CONSTRAINT `fk_taskset` FOREIGN KEY (`taskset_id`) REFERENCES `taskset` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_taskset` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_taskset`
--

LOCK TABLES `user_taskset` WRITE;
/*!40000 ALTER TABLE `user_taskset` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_taskset` ENABLE KEYS */;
UNLOCK TABLES;

CREATE FUNCTION `MINUTES_TO_HOURS`(start_date DATETIME, end_date DATETIME)
RETURNS DOUBLE
RETURN ROUND(TIMESTAMPDIFF(second, start_date, end_date) / 3600, 2)

-- Dump completed on 2016-02-12 12:55:47