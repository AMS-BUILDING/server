CREATE TABLE `amsbuilding`.`status_service_request`
(
    `id`                          BIGINT      NOT NULL AUTO_INCREMENT,
    `status_service_request_name` VARCHAR(50) NULL,
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

INSERT INTO `amsbuilding`.`status_service_request` (`status_service_request_name`)
VALUES ('Chờ xử lí');
INSERT INTO `amsbuilding`.`status_service_request` (`status_service_request_name`)
VALUES ('Đang xử lí');
INSERT INTO `amsbuilding`.`status_service_request` (`status_service_request_name`)
VALUES ('Thành công');
INSERT INTO `amsbuilding`.`status_service_request` (`status_service_request_name`)
VALUES ('Hủy');
