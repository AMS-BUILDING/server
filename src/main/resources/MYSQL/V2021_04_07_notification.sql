CREATE TABLE `amsbuilding`.`notification_building`
(
    `id`                 BIGINT NOT NULL AUTO_INCREMENT,
    `building_id`        BIGINT NULL,
    `title_notification` VARCHAR(45) NULL,
    `description`        VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    INDEX                `notification_building_ibfk_1_idx` (`building_id` ASC) VISIBLE,
    CONSTRAINT `notification_building_ibfk_1`
        FOREIGN KEY (`building_id`)
            REFERENCES `amsbuilding`.`building` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
