CREATE DATABASE amsbuilding CHARACTER SET utf8;

CREATE TABLE `amsbuilding`.`role`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`account`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `role_id`      BIGINT      NULL,
    `name`         VARCHAR(45) NULL,
    `password`     VARCHAR(45) NULL,
    `phone`        VARCHAR(45) NULL,
    `email`        VARCHAR(45) NULL,
    `enabled`      VARCHAR(45) NULL,
    `image`        VARCHAR(45) NULL,
    `identityCard` VARCHAR(45) NULL,
    `homeTown`     VARCHAR(45) NULL,
    `created_date` TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `account_ibfk_1_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `account_ibfk_1`
        FOREIGN KEY (`role_id`)
            REFERENCES `amsbuilding`.`role` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`floor`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `floor_name` VARCHAR(20) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`block`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `block_name` VARCHAR(45) NULL,
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
    `type_name` VARCHAR(50) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`room_number`
(
    `id`                BIGINT      NOT NULL AUTO_INCREMENT,
    `floor_block_id`    BIGINT      NULL,
    `type_apartment_id` BIGINT      NULL,
    `room_name`         VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
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
    `building_name` VARCHAR(100)  NULL,
    `address`       VARCHAR(200)  NULL,
    `description`   VARCHAR(4000) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`apartment`
(
    `id`             BIGINT NOT NULL AUTO_INCREMENT,
    `account_id`     BIGINT NULL,
    `building_id`    BIGINT NULL,
    `room_number_id` BIGINT NULL,
    PRIMARY KEY (`id`),
    INDEX `apartment_ibfk_2_idx` (`building_id` ASC) VISIBLE,
    INDEX `apartment_ibfk_1_idx` (`account_id` ASC) VISIBLE,
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

CREATE TABLE `amsbuilding`.`temporarily_absent_type`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `absent_type` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`temporarily_absent_detail`
(
    `id`             BIGINT      NOT NULL AUTO_INCREMENT,
    `absent_type_id` BIGINT      NULL,
    `apartment_id`   BIGINT      NULL,
    `name`           VARCHAR(45) NULL,
    `identity_card`  VARCHAR(20) NULL,
    `home_town`      VARCHAR(45) NULL,
    `dob`            VARCHAR(45) NULL,
    `start_date`     TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
    `end_date`       TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `identity_card_UNIQUE` (`identity_card` ASC) VISIBLE,
    INDEX `temporarily_absent_detail_ibfk_1_idx` (`apartment_id` ASC) VISIBLE,
    INDEX `temporarily_absent_detail_ibfk_2_idx` (`absent_type_id` ASC) VISIBLE,
    CONSTRAINT `temporarily_absent_detail_ibfk_1`
        FOREIGN KEY (`apartment_id`)
            REFERENCES `amsbuilding`.`apartment` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `temporarily_absent_detail_ibfk_2`
        FOREIGN KEY (`absent_type_id`)
            REFERENCES `amsbuilding`.`temporarily_absent_type` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

ALTER TABLE `amsbuilding`.`temporarily_absent_detail`
    ADD COLUMN `reason` VARCHAR(45) NULL AFTER `identity_card`;

ALTER TABLE `amsbuilding`.`temporarily_absent_detail`
    ADD UNIQUE INDEX `unique` (`identity_card` ASC, `absent_type_id` ASC) VISIBLE,
    DROP INDEX `identity_card_UNIQUE`;

INSERT INTO `amsbuilding`.`temporarily_absent_type` (`absent_type`)
VALUES ('tạm trú');
INSERT INTO `amsbuilding`.`temporarily_absent_type` (`absent_type`)
VALUES ('tạm vắng');

INSERT INTO `amsbuilding`.`building` (`building_name`, `address`, `description`)
VALUES ('AMSBuilding', 'Cầu giấy Hà nội', 'aaaa');

INSERT INTO `amsbuilding`.`floor` (`floor_name`)
VALUES ('1');
INSERT INTO `amsbuilding`.`floor` (`floor_name`)
VALUES ('2');
INSERT INTO `amsbuilding`.`floor` (`floor_name`)
VALUES ('3');
INSERT INTO `amsbuilding`.`floor` (`floor_name`)
VALUES ('4');
INSERT INTO `amsbuilding`.`floor` (`floor_name`)
VALUES ('5');

INSERT INTO `amsbuilding`.`block` (`block_name`)
VALUES ('A1');
INSERT INTO `amsbuilding`.`block` (`block_name`)
VALUES ('A2');
INSERT INTO `amsbuilding`.`block` (`block_name`)
VALUES ('B1');
INSERT INTO `amsbuilding`.`block` (`block_name`)
VALUES ('B2');

INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('1', '1');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('1', '2');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('1', '3');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('2', '1');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('2', '2');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('2', '3');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('2', '4');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('2', '5');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('3', '1');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('3', '2');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('3', '3');

INSERT INTO `amsbuilding`.`type_apartment` (`type_name`)
VALUES ('100');
INSERT INTO `amsbuilding`.`type_apartment` (`type_name`)
VALUES ('150');
INSERT INTO `amsbuilding`.`type_apartment` (`type_name`)
VALUES ('250');
INSERT INTO `amsbuilding`.`type_apartment` (`type_name`)
VALUES ('300');


