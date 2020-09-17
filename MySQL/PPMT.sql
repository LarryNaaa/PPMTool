-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ppmt
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ppmt` ;

-- -----------------------------------------------------
-- Schema ppmt
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ppmt` DEFAULT CHARACTER SET utf8 ;
USE `ppmt` ;

-- -----------------------------------------------------
-- Table `ppmt`.`Project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ppmt`.`Project` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `projectName` VARCHAR(45) NOT NULL,
  `projectIdentifier` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  `start_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  `created_At` DATETIME NULL,
  `updated_At` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `projectIdentifier_UNIQUE` (`projectIdentifier` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ppmt`.`Backlog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ppmt`.`Backlog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `PTSequence` INT NULL,
  `projectIdentifier` VARCHAR(45) NULL,
  `project_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `Backlog_Project_idx` (`project_id` ASC) VISIBLE,
  CONSTRAINT `Backlog_Project`
    FOREIGN KEY (`project_id`)
    REFERENCES `ppmt`.`Project` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ppmt`.`ProjectTask`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ppmt`.`ProjectTask` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `projectSequence` VARCHAR(45) NULL,
  `summary` VARCHAR(45) NOT NULL,
  `acceptanceCriteria` VARCHAR(45) NULL,
  `status` VARCHAR(45) NULL,
  `priority` INT NULL,
  `dueDate` DATETIME NULL,
  `backlog_id` INT NOT NULL,
  `projectIdentifier` VARCHAR(45) NULL,
  `create_At` DATETIME NULL,
  `update_At` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `ProjectTask_Backlog1_idx` (`backlog_id` ASC) VISIBLE,
  CONSTRAINT `ProjectTask_Backlog1`
    FOREIGN KEY (`backlog_id`)
    REFERENCES `ppmt`.`Backlog` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
