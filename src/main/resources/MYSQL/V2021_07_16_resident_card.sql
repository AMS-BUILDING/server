CREATE TABLE `amsbuilding`.`status_resident_card`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `status_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`resident_card`
(
    `id`                      BIGINT      NOT NULL AUTO_INCREMENT,
    `account_id`              BIGINT      NULL,
    `status_resident_card_id` BIGINT      NULL,
    `cardCode`                VARCHAR(45) NULL,
    `price`                   DOUBLE      NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `cardCode_UNIQUE` (`cardCode` ASC) VISIBLE,
    INDEX `resident_card_ibfk_1_idx` (`account_id` ASC) VISIBLE,
    INDEX `resident_card_ibfk_2_idx` (`status_resident_card_id` ASC) VISIBLE,
    CONSTRAINT `resident_card_ibfk_1`
        FOREIGN KEY (`account_id`)
            REFERENCES `amsbuilding`.`account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `resident_card_ibfk_2`
        FOREIGN KEY (`status_resident_card_id`)
            REFERENCES `amsbuilding`.`status_resident_card` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
