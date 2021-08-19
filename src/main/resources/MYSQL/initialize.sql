CREATE
DATABASE amsbuilding CHARACTER SET utf8;

CREATE TABLE `amsbuilding`.`role`
(
    `id`   BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `amsbuilding`.`position`
(
    `id`   BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL,
    `show` TINYINT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`account`
(
    `id`              BIGINT NOT NULL AUTO_INCREMENT,
    `role_id`         BIGINT NULL,
    `position_id`     BIGINT NULL,
    `name`            VARCHAR(50) NULL,
    `email`           VARCHAR(60) NULL,
    `phone`           VARCHAR(11) NULL,
    `gender`          TINYINT NULL DEFAULT 0,
    `password`        VARCHAR(65) NULL,
    `identify_card`   VARCHAR(20) NULL,
    `image_link`      VARCHAR(100) NULL,
    `dob`             VARCHAR(45) NULL,
    `enabled`         TINYINT NULL,
    `home_town`       VARCHAR(200) NULL,
    `current_address` VARCHAR(200) NULL,
    `created_date`    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    UNIQUE INDEX `identify_card_UNIQUE` (`identify_card` ASC) VISIBLE,
    INDEX             `account_ibfk_1_idx` (`role_id` ASC) VISIBLE,
    INDEX             `account_ibfk_2_idx` (`position_id` ASC) VISIBLE,
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

CREATE TABLE `amsbuilding`.`floor`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `floor_name` VARCHAR(20) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`block`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `block_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`floor_block`
(
    `id`       BIGINT NOT NULL AUTO_INCREMENT,
    `block_id` BIGINT NULL,
    `floor_id` BIGINT NULL,
    PRIMARY KEY (`id`),
    INDEX      `floor_block_ibfk_1_idx` (`block_id` ASC) VISIBLE,
    INDEX      `floor_block_ibfk_2_idx` (`floor_id` ASC) VISIBLE,
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
    `id`        BIGINT NOT NULL AUTO_INCREMENT,
    `type_name` VARCHAR(50) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`room_number`
(
    `id`                BIGINT NOT NULL AUTO_INCREMENT,
    `floor_block_id`    BIGINT NULL,
    `type_apartment_id` BIGINT NULL,
    `room_name`         VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    INDEX               `room_number_ibfk_1_idx` (`floor_block_id` ASC) VISIBLE,
    INDEX               `room_number_ibfk_2_idx` (`type_apartment_id` ASC) VISIBLE,
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
    `id`            BIGINT NOT NULL AUTO_INCREMENT,
    `building_name` VARCHAR(100) NULL,
    `address`       VARCHAR(200) NULL,
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
    INDEX            `apartment_ibfk_2_idx` (`building_id` ASC) VISIBLE,
    INDEX            `apartment_ibfk_1_idx` (`account_id` ASC) VISIBLE,
    INDEX            `apartment_ibfk_3_idx` (`room_number_id` ASC) VISIBLE,
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
    `id`          BIGINT NOT NULL AUTO_INCREMENT,
    `absent_type` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`temporarily_absent_detail`
(
    `id`             BIGINT NOT NULL AUTO_INCREMENT,
    `absent_type_id` BIGINT NULL,
    `apartment_id`   BIGINT NULL,
    `name`           VARCHAR(45) NULL,
    `identify_card`  VARCHAR(20) NULL,
    `home_town`      VARCHAR(45) NULL,
    `dob`            VARCHAR(45) NULL,
    `start_date`     TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `end_date`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `identity_card_UNIQUE` (`identify_card` ASC) VISIBLE,
    INDEX            `temporarily_absent_detail_ibfk_1_idx` (`apartment_id` ASC) VISIBLE,
    INDEX            `temporarily_absent_detail_ibfk_2_idx` (`absent_type_id` ASC) VISIBLE,
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
CREATE TABLE `amsbuilding`.`feedback`
(
    `id`           BIGINT NOT NULL AUTO_INCREMENT,
    `account_id`   BIGINT NULL,
    `description`  VARCHAR(1000) NULL,
    `created_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX          `feedback_ibfk_1_idx` (`account_id` ASC) VISIBLE,
    CONSTRAINT `feedback_ibfk_1`
        FOREIGN KEY (`account_id`)
            REFERENCES `amsbuilding`.`account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

ALTER TABLE `amsbuilding`.`temporarily_absent_detail`
    ADD COLUMN `reason` VARCHAR(45) NULL AFTER `identify_card`;

ALTER TABLE `amsbuilding`.`temporarily_absent_detail`
    ADD UNIQUE INDEX `unique` (`identify_card` ASC, `absent_type_id` ASC) VISIBLE,
DROP
    INDEX `identity_card_UNIQUE`;

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

INSERT INTO `amsbuilding`.`role` (`name`)
VALUES ('ROLE_ADMIN');
INSERT INTO `amsbuilding`.`role` (`name`)
VALUES ('ROLE_MANAGER_SERVICE');
INSERT INTO `amsbuilding`.`role` (`name`)
VALUES ('ROLE_LANDLORD');
INSERT INTO `amsbuilding`.`role` (`name`)
VALUES ('ROLE_EMPLOYEE');
INSERT INTO `amsbuilding`.`role` (`name`)
VALUES ('ROLE_TENANT');

INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('nhân viên kĩ thuật', '0');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('nhân viên sữa chữa', '0');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('nhân viên dọn dẹp', '0');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('nhân viên giám sát', '0');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('vợ', '1');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('chồng', '1');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('bố', '1');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('mẹ', '1');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('anh/chị ruột', '1');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('bạn', '1');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('anh/chị em họ', '1');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('ông', '1');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('bà', '1');
INSERT INTO `amsbuilding`.`position` (`name`, `show`)
VALUES ('khác', '1');
