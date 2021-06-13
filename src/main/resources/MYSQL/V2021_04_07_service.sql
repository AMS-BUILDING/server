CREATE TABLE `amsbuilding`.`service`
(
    `id`   BIGINT       NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `amsbuilding`.`sub_service`
(
    `id`               BIGINT      NOT NULL AUTO_INCREMENT,
    `service_id`       BIGINT      NULL,
    `sub_service_name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    INDEX `sub_service_ibfk_!_idx` (`service_id` ASC) VISIBLE,
    CONSTRAINT `sub_service_ibfk_!`
        FOREIGN KEY (`service_id`)
            REFERENCES `amsbuilding`.`service` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `amsbuilding`.`detail_sub_service`
(
    `id`                      BIGINT      NOT NULL AUTO_INCREMENT,
    `sub_service_id`          BIGINT      NULL,
    `detail_sub_service_name` VARCHAR(45) NULL,
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

INSERT INTO `amsbuilding`.`service` (`name`)
VALUES ('dịch vụ đời sống');
INSERT INTO `amsbuilding`.`service` (`name`)
VALUES ('vui chơi giải trí');

INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'BBQ');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'Hồ bơi');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'Tennis');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'Sân bóng');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'Đặt tiệc');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'Covid-19');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('1', 'Giặt là');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('1', 'Dọn dẹp căn hộ');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('1', 'Sửa chữa ');

UPDATE `amsbuilding`.`type_apartment`
SET `type_name` = '65'
WHERE (`id` = '1');
UPDATE `amsbuilding`.`type_apartment`
SET `type_name` = '100'
WHERE (`id` = '2');
UPDATE `amsbuilding`.`type_apartment`
SET `type_name` = '125'
WHERE (`id` = '3');
UPDATE `amsbuilding`.`type_apartment`
SET `type_name` = '150'
WHERE (`id` = '4');
INSERT INTO `amsbuilding`.`type_apartment` (`type_name`)
VALUES ('200');

INSERT INTO `amsbuilding`.`service` (`name`)
VALUES ('dịch vụ đời sống');
INSERT INTO `amsbuilding`.`service` (`name`)
VALUES ('vui chơi giải trí');

INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'BBQ');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'Hồ bơi');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'Tennis');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'Sân bóng');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'Đặt tiệc');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('2', 'Covid-19');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('1', 'Giặt là');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('1', 'Dọn dẹp căn hộ');
INSERT INTO `amsbuilding`.`sub_service` (`service_id`, `sub_service_name`)
VALUES ('1', 'Sửa chữa ');

UPDATE `amsbuilding`.`type_apartment`
SET `type_name` = '65'
WHERE (`id` = '1');
UPDATE `amsbuilding`.`type_apartment`
SET `type_name` = '100'
WHERE (`id` = '2');
UPDATE `amsbuilding`.`type_apartment`
SET `type_name` = '125'
WHERE (`id` = '3');
UPDATE `amsbuilding`.`type_apartment`
SET `type_name` = '150'
WHERE (`id` = '4');
INSERT INTO `amsbuilding`.`type_apartment` (`type_name`)
VALUES ('200');

INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('1', 'BBQ');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('2', 'Hồ bơi');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('3', 'Tennis');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('4', 'Sân bóng');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('5', 'Đặt tiệc');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('6', 'Covid-19');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('7', '1kg');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('7', '2kg');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('7', '3kg');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('7', '4kg');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('7', '5kg');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('7', '6kg');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('7', '7kg');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('7', '8kg');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('7', '10kg');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('8', '65m2');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('8', '100m2');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('8', '125m2');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('8', '150m2');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('8', '200m2');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('9', 'Bình nóng lạnh');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('9', 'Điều hòa');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('9', 'Bóng đèn');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('9', 'Thiết bị nhà vệ sinh');
INSERT INTO `amsbuilding`.`detail_sub_service` (`sub_service_id`, `detail_sub_service_name`)
VALUES ('9', 'Thiết bị báo cháy');

INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('1', 'BBQ', '100000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('2', 'Hồ bơi', '70000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('3', 'Tennis', '150000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('4', 'Sân bóng', '200000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('5', 'Đặt tiệc', '500000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('6', 'Covid-19', '700000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('7', '1kg', '15000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('8', '2kg', '28000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('9', '3kg', '42000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('10', '4kg', '56000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('11', '5kg', '70000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('12', '6kg', '85000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('13', '7kg', '95000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('14', '8kg', '100000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('15', '10kg', '130000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('16', '65m2', '1300000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('17', '100m2', '2200000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('18', '125m2', '25000000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('19', '150m2', '3000000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`, `price`)
VALUES ('20', '200m2', '3700000');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`)
VALUES ('21', 'Hở điện');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`)
VALUES ('21', 'Không làm nóng');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`)
VALUES ('22', 'Hỏng điều khiển');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`)
VALUES ('22', 'Không làm mát');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`)
VALUES ('22', 'Vệ sinh điều hòa');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`)
VALUES ('22', 'Thay ga');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`)
VALUES ('23', 'Thay mới');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`)
VALUES ('24', 'Tắc bồn cầu');
INSERT INTO `amsbuilding`.`reason_detail_sub_service` (`detail_sub_service_id`, `reason_name`)
VALUES ('25', 'Kiểm tra định kì');

UPDATE `amsbuilding`.`detail_sub_service`
SET `detail_sub_service_name` = NULL
WHERE (`id` = '1');
UPDATE `amsbuilding`.`detail_sub_service`
SET `detail_sub_service_name` = NULL
WHERE (`id` = '2');
UPDATE `amsbuilding`.`detail_sub_service`
SET `detail_sub_service_name` = NULL
WHERE (`id` = '3');
UPDATE `amsbuilding`.`detail_sub_service`
SET `detail_sub_service_name` = NULL
WHERE (`id` = '4');
UPDATE `amsbuilding`.`detail_sub_service`
SET `detail_sub_service_name` = NULL
WHERE (`id` = '5');
UPDATE `amsbuilding`.`detail_sub_service`
SET `detail_sub_service_name` = NULL
WHERE (`id` = '6');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '1');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '2');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '3');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '4');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '5');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '6');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '7');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '8');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '9');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '10');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '11');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '12');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '13');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '14');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '15');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '16');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '17');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '18');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '19');
UPDATE `amsbuilding`.`reason_detail_sub_service`
SET `reason_name` = NULL
WHERE (`id` = '20');

