CREATE TABLE `amsbuilding`.`status_vehicle_card`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `status_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `amsbuilding`.`vehicle`
(
    `id`            BIGINT      NOT NULL AUTO_INCREMENT,
    `vechicle_name` VARCHAR(50) NULL,
    `price`         DOUBLE      NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `amsbuilding`.`vehicle_card`
(
    `id`                     BIGINT      NOT NULL AUTO_INCREMENT,
    `vehicle_id`             BIGINT      NULL,
    `account_id`             BIGINT      NULL,
    `status_vehicle_card_id` BIGINT      NULL,
    `vehicle_type_name`      VARCHAR(45) NULL,
    `vehicle_branch`         VARCHAR(45) NULL,
    `license_plate`          VARCHAR(45) NOT NULL,
    `vehicle_color`          VARCHAR(45) NULL,
    `start_date`             TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
    `end_date`               TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `vehicle_card_ibfk_1_idx` (`account_id` ASC) VISIBLE,
    INDEX `vehicle_card_ibfk_2_idx` (`vehicle_id` ASC) VISIBLE,
    INDEX `vehicle_card_ibfk_3_idx` (`status_vehicle_card_id` ASC) VISIBLE,
    CONSTRAINT `vehicle_card_ibfk_1`
        FOREIGN KEY (`account_id`)
            REFERENCES `amsbuilding`.`account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `vehicle_card_ibfk_2`
        FOREIGN KEY (`vehicle_id`)
            REFERENCES `amsbuilding`.`vehicle` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `vehicle_card_ibfk_3`
        FOREIGN KEY (`status_vehicle_card_id`)
            REFERENCES `amsbuilding`.`status_vehicle_card` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

ALTER TABLE `amsbuilding`.`vehicle`
    CHANGE COLUMN `vechicle_name` `vehicle_name` VARCHAR(50) NULL DEFAULT NULL;

INSERT INTO `amsbuilding`.`status_vehicle_card` (`status_name`)
VALUES ('Chờ xử lí');
INSERT INTO `amsbuilding`.`status_vehicle_card` (`status_name`)
VALUES ('Đang xử lí');
INSERT INTO `amsbuilding`.`status_vehicle_card` (`status_name`)
VALUES ('Thành công');
INSERT INTO `amsbuilding`.`vehicle` (`vehicle_name`, `price`)
VALUES ('Xe điện', '90000');
INSERT INTO `amsbuilding`.`vehicle` (`vehicle_name`, `price`)
VALUES ('Xe máy', '140000');
INSERT INTO `amsbuilding`.`vehicle` (`vehicle_name`, `price`)
VALUES ('Xe ô tô 5 chỗ', '1000000');
INSERT INTO `amsbuilding`.`vehicle` (`vehicle_name`, `price`)
VALUES ('Xe ô tô 7 chỗ', '1400000');
