CREATE TABLE `amsbuilding`.`position`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL,
    `show` TINYINT     NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `amsbuilding`.`account`
    ADD COLUMN `position_id`                    BIGINT       NULL AFTER `role_id`,
    ADD COLUMN `gender`                         TINYINT      NULL DEFAULT 0 AFTER `phone`,
    ADD COLUMN `dob`                            VARCHAR(45)  NULL AFTER `image_link`,
    ADD COLUMN `current_address`                VARCHAR(45)  NULL AFTER `homeTown`,
    ADD COLUMN `start_time`                     TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP AFTER `current_address`,
    ADD COLUMN `end_time`                       TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP AFTER `start_time`,
    CHANGE COLUMN `email` `email`               VARCHAR(45)  NULL DEFAULT NULL AFTER `name`,
    CHANGE COLUMN `phone` `phone`               VARCHAR(45)  NULL DEFAULT NULL AFTER `email`,
    CHANGE COLUMN `identityCard` `identityCard` VARCHAR(20)  NULL DEFAULT NULL AFTER `password`,
    CHANGE COLUMN `image` `image_link`          VARCHAR(100) NULL AFTER `identityCard`,
    CHANGE COLUMN `name` `name`                 VARCHAR(50)  NULL DEFAULT NULL,
    CHANGE COLUMN `password` `password`         VARCHAR(65)  NULL DEFAULT NULL,
    ADD UNIQUE INDEX `identityCard_UNIQUE` (`identityCard` ASC) VISIBLE,
    ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    ADD INDEX `account_ibfk_2_idx` (`position_id` ASC) VISIBLE;
;
ALTER TABLE `amsbuilding`.`account`
    ADD CONSTRAINT `account_ibfk_2`
        FOREIGN KEY (`position_id`)
            REFERENCES `amsbuilding`.`position` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

CREATE TABLE `amsbuilding`.`feedback`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `account_id`   BIGINT      NULL,
    `description`  VARCHAR(45) NULL,
    `created_date` TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `feedback_ibfk_1_idx` (`account_id` ASC) VISIBLE,
    CONSTRAINT `feedback_ibfk_1`
        FOREIGN KEY (`account_id`)
            REFERENCES `amsbuilding`.`account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

