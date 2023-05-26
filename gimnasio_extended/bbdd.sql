-- MySQL Script generated by MySQL Workbench
-- Wed May 24 17:03:39 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gimnasio
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gimnasio
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gimnasio` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `gimnasio` ;

-- -----------------------------------------------------
-- Table `gimnasio`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gimnasio`.`cliente` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NULL DEFAULT NULL,
  `apellidos` VARCHAR(255) NULL DEFAULT NULL,
  `edad` INT(4) NULL DEFAULT NULL,
  `altura` DOUBLE NULL DEFAULT NULL,
  `peso` INT(4) NULL DEFAULT NULL,
  `picPath` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 403
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `gimnasio`.`ejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gimnasio`.`ejercicio` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NULL DEFAULT NULL,
  `numeroDeSeries` INT(2) NULL DEFAULT NULL,
  `repeticiones` INT(2) NULL DEFAULT NULL,
  `cargaEnKg` INT(3) NULL DEFAULT NULL,
  `picPath` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 254
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `gimnasio`.`cliente_has_ejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gimnasio`.`cliente_has_ejercicio` (
  `cliente_id` INT(11) NOT NULL,
  `ejercicio_id` INT(11) NOT NULL,
  PRIMARY KEY (`cliente_id`, `ejercicio_id`),
  INDEX `fk_cliente_has_ejercicio_2_idx` (`ejercicio_id` ASC) VISIBLE,
  CONSTRAINT `fk_cliente_has_ejercicio_1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `gimnasio`.`cliente` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_cliente_has_ejercicio_2`
    FOREIGN KEY (`ejercicio_id`)
    REFERENCES `gimnasio`.`ejercicio` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

USE `gimnasio` ;

-- -----------------------------------------------------
-- function calcularTotalPedido
-- -----------------------------------------------------

DELIMITER $$
USE `gimnasio`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `calcularTotalPedido`(id_pedido INT) RETURNS decimal(10,2)
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE total_pedido DECIMAL(10, 2);
    SET total_pedido = 0;
    
    SELECT SUM(cantidad ^precio) INTO total_pedido
		FROM detalle_venta
        WHERE pedido_id = id_pedido;
    
RETURN total_pedido;
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `gimnasio`.`cliente` (`nombre`, `apellidos`, `edad`, `altura`, `peso`, `picPath`) VALUES ('Loas', 'Albinas', '23', '1.2', '55', '/com/hibernate/gui/Imagen_2.jpg');
INSERT INTO `gimnasio`.`cliente` (`nombre`, `apellidos`, `edad`, `altura`, `peso`, `picPath`) VALUES ('Tridente', 'Marino', '55', '1.8', '42', '/com/hibernate/gui/Imagen_3.jpg');
INSERT INTO `gimnasio`.`cliente` (`nombre`, `apellidos`, `edad`, `altura`, `peso`, `picPath`) VALUES ('Tobias', 'Gatinyo', '32', '1.8', '33', '/com/hibernate/gui/Imagen_4.jpg');
INSERT INTO `gimnasio`.`cliente` (`nombre`, `apellidos`, `edad`, `altura`, `peso`, `picPath`) VALUES ('Regis', 'Lego', '55', '1.6', '77', '/com/hibernate/gui/Imagen_5.jpg');
INSERT INTO `gimnasio`.`cliente` (`nombre`, `apellidos`, `edad`, `altura`, `peso`, `picPath`) VALUES ('Calvo', 'Pepinillo', '78', '1.3', '89', '/com/hibernate/gui/Imagen_6.jpg');

INSERT INTO `gimnasio`.`ejercicio` (`nombre`, `numeroDeSeries`, `repeticiones`, `cargaEnKg`, `picPath`, `zona`) VALUES ('Biceps', '23', '12', '40', '/com/hibernate/gui/Imagen_1.jpg', 'MUSCULACION');
INSERT INTO `gimnasio`.`ejercicio` (`nombre`, `numeroDeSeries`, `repeticiones`, `cargaEnKg`, `picPath`, `zona`) VALUES ('Triceps', '32', '23', '20', '/com/hibernate/gui/Imagen_2.jpg', 'AEROBICO');
INSERT INTO `gimnasio`.`ejercicio` (`nombre`, `numeroDeSeries`, `repeticiones`, `cargaEnKg`, `picPath`, `zona`) VALUES ('Bicicleta', '20', '23', '23', '/com/hibernate/gui/Imagen_3.jpg', 'ZUMBA');
