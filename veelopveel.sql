CREATE TABLE IF NOT EXISTS `mydb`.`klant_adres` (
  `klant_klant_id` INT(10) UNSIGNED NOT NULL COMMENT '',
  `adres_adres_id` INT(10) UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`klant_klant_id`, `adres_adres_id`)  COMMENT '',
  INDEX `fk_klant_adres_adres1_idx` (`adres_adres_id` ASC)  COMMENT '',
  CONSTRAINT `fk_klant_adres_klant1`
    FOREIGN KEY (`klant_klant_id`)
    REFERENCES `mydb`.`klant` (`klant_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_klant_adres_adres1`
    FOREIGN KEY (`adres_adres_id`)
    REFERENCES `mydb`.`adres` (`adres_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
