CREATE
    DATABASE amsbuilding CHARACTER SET utf8;

use
    `amsbuilding`;

CREATE TABLE `amsbuilding`.`role`
(
    `id`        BIGINT      NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`position`
(
    `id`   BIGINT      NOT NULL,
    `name` VARCHAR(45) NULL,
    `show` TINYINT     NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`temporarily_absent_type`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `absent_type` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`account`
(
    `id`                   BIGINT       NOT NULL AUTO_INCREMENT,
    `role_id`              BIGINT       NULL,
    `position_id`          BIGINT       NULL,
    `name`                 VARCHAR(50)  NULL,
    `email`                VARCHAR(60)  NULL,
    `phone`                VARCHAR(11)  NULL,
    `gender`               TINYINT(4)   NULL DEFAULT 0,
    `password`             VARCHAR(65)  NULL,
    `identify_card`        VARCHAR(20)  NULL,
    `image_link`           VARCHAR(100) NULL,
    `dob`                  VARCHAR(45)  NULL,
    `enabled`              TINYINT(4)   NULL,
    `home_town`            VARCHAR(200) NULL,
    `current_address`      VARCHAR(200) NULL,
    `created_date`         TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP,
    `enabled_token`        TINYINT      NULL,
    `reset_password_token` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    UNIQUE INDEX `identify_card_UNIQUE` (`identify_card` ASC) VISIBLE,
    INDEX `account_ibfk_1_idx` (`role_id` ASC) VISIBLE,
    INDEX `account_ibfk_2_idx` (`position_id` ASC) VISIBLE,
    CONSTRAINT `account_ibfk_1`
        FOREIGN KEY (`role_id`)
            REFERENCES `amsbuilding`.`role` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `account_ibfk_2`
        FOREIGN KEY (`position_id`)
            REFERENCES `amsbuilding`.`position` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`feedback`
(
    `id`           BIGINT        NOT NULL AUTO_INCREMENT,
    `account_id`   BIGINT        NULL,
    `star`         INT           NULL,
    `description`  VARCHAR(1000) NULL,
    `created_date` TIMESTAMP     NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `feedback_ibfk_1_idx` (`account_id` ASC) VISIBLE,
    CONSTRAINT `feedback_ibfk_1`
        FOREIGN KEY (`account_id`)
            REFERENCES `amsbuilding`.`account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`vehicle`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `vehicle_name` VARCHAR(45) NULL,
    `price_month`  DOUBLE      NULL,
    `price_day`    DOUBLE      NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`status_vehicle_card`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `status_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`status_resident_card`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `status_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`vehicle_card`
(
    `id`                     BIGINT      NOT NULL AUTO_INCREMENT,
    `vehicle_id`             BIGINT      NULL,
    `account_id`             BIGINT      NULL,
    `status_vehicle_card_id` BIGINT      NULL,
    `vehicle_branch`         VARCHAR(45) NULL,
    `license_plate`          VARCHAR(45) NULL,
    `vehicle_color`          VARCHAR(45) NULL,
    `billing_month`          VARCHAR(45) NULL,
    `start_date`             TIMESTAMP   NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `license_plate_UNIQUE` (`license_plate` ASC) VISIBLE,
    INDEX `vehicle_card_ibfk_1_idx` (`vehicle_id` ASC) VISIBLE,
    INDEX `vehicle_card_ibfk_2_idx` (`status_vehicle_card_id` ASC) VISIBLE,
    INDEX `vehicle_card_ibfk_3_idx` (`account_id` ASC) VISIBLE,
    CONSTRAINT `vehicle_card_ibfk_1`
        FOREIGN KEY (`vehicle_id`)
            REFERENCES `amsbuilding`.`vehicle` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `vehicle_card_ibfk_2`
        FOREIGN KEY (`status_vehicle_card_id`)
            REFERENCES `amsbuilding`.`status_vehicle_card` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `vehicle_card_ibfk_3`
        FOREIGN KEY (`account_id`)
            REFERENCES `amsbuilding`.`account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`resident_card`
(
    `id`                      BIGINT      NOT NULL AUTO_INCREMENT,
    `account_id`              BIGINT      NULL,
    `status_resident_card_id` BIGINT      NULL,
    `resident_card_code`      VARCHAR(45) NULL,
    `price`                   DOUBLE      NULL,
    `billing_month`           VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resident_card_code_UNIQUE` (`resident_card_code` ASC) VISIBLE,
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

CREATE TABLE `amsbuilding`.`block`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `block_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`floor`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `floor_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`floor_block`
(
    `id`       BIGINT NOT NULL AUTO_INCREMENT,
    `block_id` BIGINT NULL,
    `floor_id` BIGINT NULL,
    PRIMARY KEY (`id`),
    INDEX `floor_block_ibfk_1_idx` (`block_id` ASC) VISIBLE,
    INDEX `floor_block_ibfk_2_idx` (`floor_id` ASC) VISIBLE,
    CONSTRAINT `floor_block_ibfk_1`
        FOREIGN KEY (`block_id`)
            REFERENCES `amsbuilding`.`block` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `floor_block_ibfk_2`
        FOREIGN KEY (`floor_id`)
            REFERENCES `amsbuilding`.`floor` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`type_apartment`
(
    `id`        BIGINT      NOT NULL AUTO_INCREMENT,
    `type_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`room_number`
(
    `id`                BIGINT      NOT NULL AUTO_INCREMENT,
    `floor_block_id`    BIGINT      NULL,
    `type_apartment_id` BIGINT      NULL,
    `room_name`         VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `room_name_UNIQUE` (`room_name` ASC) VISIBLE,
    INDEX `room_number_ibfk_1_idx` (`floor_block_id` ASC) VISIBLE,
    INDEX `room_number_ibfk_2_idx` (`type_apartment_id` ASC) VISIBLE,
    CONSTRAINT `room_number_ibfk_1`
        FOREIGN KEY (`floor_block_id`)
            REFERENCES `amsbuilding`.`floor_block` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `room_number_ibfk_2`
        FOREIGN KEY (`type_apartment_id`)
            REFERENCES `amsbuilding`.`type_apartment` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`building`
(
    `id`            BIGINT        NOT NULL AUTO_INCREMENT,
    `building_name` VARCHAR(45)   NULL,
    `address`       VARCHAR(200)  NULL,
    `description`   VARCHAR(4000) NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `amsbuilding`.`notification`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT,
    `title`        VARCHAR(45)  NULL,
    `description`  VARCHAR(255) NULL,
    `is_read`      TINYINT      NULL DEFAULT 0,
    `created_date` TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`apartment`
(
    `id`             BIGINT NOT NULL AUTO_INCREMENT,
    `account_id`     BIGINT NULL,
    `building_id`    BIGINT NULL,
    `room_number_id` BIGINT NULL,
    PRIMARY KEY (`id`),
    INDEX `apartment_ibfk_1_idx` (`account_id` ASC) VISIBLE,
    INDEX `apartment_ibfk_2_idx` (`building_id` ASC) VISIBLE,
    INDEX `apartment_ibfk_3_idx` (`room_number_id` ASC) VISIBLE,
    CONSTRAINT `apartment_ibfk_1`
        FOREIGN KEY (`account_id`)
            REFERENCES `amsbuilding`.`account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `apartment_ibfk_2`
        FOREIGN KEY (`building_id`)
            REFERENCES `amsbuilding`.`building` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `apartment_ibfk_3`
        FOREIGN KEY (`room_number_id`)
            REFERENCES `amsbuilding`.`room_number` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`temporarily_absent_detail`
(
    `id`             BIGINT      NOT NULL AUTO_INCREMENT,
    `absent_type_id` BIGINT      NULL,
    `apartment_id`   BIGINT      NULL,
    `name`           VARCHAR(45) NULL,
    `identify_card`  VARCHAR(20) NULL,
    `reason`         VARCHAR(45) NULL,
    `home_town`      VARCHAR(45) NULL,
    `dob`            VARCHAR(45) NULL,
    `start_date`     TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
    `end_date`       TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `identify_card_UNIQUE` (`identify_card` ASC) VISIBLE,
    INDEX `temporarily_absent_detail_ibfk_1_idx` (`absent_type_id` ASC) VISIBLE,
    INDEX `temporarily_absent_detail_ibfk_2_idx` (`apartment_id` ASC) VISIBLE,
    CONSTRAINT `temporarily_absent_detail_ibfk_1`
        FOREIGN KEY (`absent_type_id`)
            REFERENCES `amsbuilding`.`temporarily_absent_type` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `temporarily_absent_detail_ibfk_2`
        FOREIGN KEY (`apartment_id`)
            REFERENCES `amsbuilding`.`apartment` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`status_apartment_billing`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `status_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`apartment_billing`
(
    `id`                          BIGINT      NOT NULL AUTO_INCREMENT,
    `apartment_id`                BIGINT      NULL,
    `status_apartment_billing_id` BIGINT      NULL,
    `total_price`                 DOUBLE      NULL,
    `billing_month`               VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    INDEX `apartment_billing_ibfk_1_idx` (`apartment_id` ASC) VISIBLE,
    INDEX `apartment_billing_ibfk_2_idx` (`status_apartment_billing_id` ASC) VISIBLE,
    CONSTRAINT `apartment_billing_ibfk_1`
        FOREIGN KEY (`apartment_id`)
            REFERENCES `amsbuilding`.`apartment` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `apartment_billing_ibfk_2`
        FOREIGN KEY (`status_apartment_billing_id`)
            REFERENCES `amsbuilding`.`status_apartment_billing` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`service`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`sub_service`
(
    `id`               BIGINT      NOT NULL AUTO_INCREMENT,
    `service_id`       BIGINT      NULL,
    `sub_service_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    INDEX `sub_service_ibfk_1_idx` (`service_id` ASC) VISIBLE,
    CONSTRAINT `sub_service_ibfk_1`
        FOREIGN KEY (`service_id`)
            REFERENCES `amsbuilding`.`service` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`detail_sub_service`
(
    `id`                    BIGINT      NOT NULL AUTO_INCREMENT,
    `sub_service_id`        BIGINT      NULL,
    `detail_sub_servicecol` VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    INDEX `detail_sub_service_ibfk_1_idx` (`sub_service_id` ASC) VISIBLE,
    CONSTRAINT `detail_sub_service_ibfk_1`
        FOREIGN KEY (`sub_service_id`)
            REFERENCES `amsbuilding`.`sub_service` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`reason_detail_sub_service`
(
    `id`                    BIGINT        NOT NULL AUTO_INCREMENT,
    `detail_sub_service_id` BIGINT        NULL,
    `reason_name`           VARCHAR(1000) NULL,
    `price`                 DOUBLE        NULL,
    PRIMARY KEY (`id`),
    INDEX `reason_detail_sub_service_ibfk_1_idx` (`detail_sub_service_id` ASC) VISIBLE,
    CONSTRAINT `reason_detail_sub_service_ibfk_1`
        FOREIGN KEY (`detail_sub_service_id`)
            REFERENCES `amsbuilding`.`detail_sub_service` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`status_service_request`
(
    `id`                          BIGINT      NOT NULL AUTO_INCREMENT,
    `status_service_request_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`service_request`
(
    `id`                           BIGINT      NOT NULL AUTO_INCREMENT,
    `reason_detail_sub_service_id` BIGINT      NULL,
    `status_service_request_id`    BIGINT      NULL,
    `account_id`                   BIGINT      NULL,
    `description`                  VARCHAR(45) NULL,
    `start_date`                   TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
    `end_date`                     TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
    `date_register`                TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `service_request_ibfk_1_idx` (`reason_detail_sub_service_id` ASC) VISIBLE,
    INDEX `service_request_ibfk_2_idx` (`status_service_request_id` ASC) VISIBLE,
    INDEX `service_request_ibfk_3_idx` (`account_id` ASC) VISIBLE,
    CONSTRAINT `service_request_ibfk_1`
        FOREIGN KEY (`reason_detail_sub_service_id`)
            REFERENCES `amsbuilding`.`reason_detail_sub_service` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `service_request_ibfk_2`
        FOREIGN KEY (`status_service_request_id`)
            REFERENCES `amsbuilding`.`status_service_request` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `service_request_ibfk_3`
        FOREIGN KEY (`account_id`)
            REFERENCES `amsbuilding`.`account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`detail_apartment_billing`
(
    `id`                           BIGINT(20)  NOT NULL,
    `apartment_billing_id`         BIGINT(20)  NULL,
    `account_id`                   BIGINT(20)  NULL,
    `vehicle_card_id`              BIGINT(20)  NULL,
    `vehicle_name`                 VARCHAR(45) NULL,
    `vehicle_price`                DOUBLE      NULL,
    `resident_id`                  BIGINT(20)  NULL,
    `resident_code`                VARCHAR(45) NULL,
    `resident_price`               DOUBLE      NULL,
    `reason_detail_sub_service_id` BIGINT(20)  NULL,
    `sub_service_name`             VARCHAR(45) NULL,
    `sub_service_price`            DOUBLE      NULL,
    `billing_month`                VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    INDEX `detail_apartment_billing_ibfk_1_idx` (`apartment_billing_id` ASC) VISIBLE,
    INDEX `detail_apartment_billing_ibfk_2_idx` (`account_id` ASC) VISIBLE,
    INDEX `detail_apartment_billing_ibfk_3_idx` (`vehicle_card_id` ASC) VISIBLE,
    INDEX `detail_apartment_billing_ibfk_4_idx` (`resident_id` ASC) VISIBLE,
    INDEX `detail_apartment_billing_ibfk_5_idx` (`reason_detail_sub_service_id` ASC) VISIBLE,
    CONSTRAINT `detail_apartment_billing_ibfk_1`
        FOREIGN KEY (`apartment_billing_id`)
            REFERENCES `amsbuilding`.`apartment_billing` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `detail_apartment_billing_ibfk_2`
        FOREIGN KEY (`account_id`)
            REFERENCES `amsbuilding`.`account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `detail_apartment_billing_ibfk_3`
        FOREIGN KEY (`vehicle_card_id`)
            REFERENCES `amsbuilding`.`vehicle_card` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `detail_apartment_billing_ibfk_4`
        FOREIGN KEY (`resident_id`)
            REFERENCES `amsbuilding`.`resident_card` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `detail_apartment_billing_ibfk_5`
        FOREIGN KEY (`reason_detail_sub_service_id`)
            REFERENCES `amsbuilding`.`reason_detail_sub_service` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

ALTER TABLE `amsbuilding`.`vehicle_card`
    ADD COLUMN `is_use` INT NULL AFTER `start_date`;
ALTER TABLE `amsbuilding`.`vehicle_card`
    CHANGE COLUMN `is_use` `is_use` INT(11) NULL DEFAULT 1;
ALTER TABLE `amsbuilding`.`resident_card`
    ADD COLUMN `is_use` INT NULL DEFAULT 1 AFTER `billing_month`;


