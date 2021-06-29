ALTER TABLE `amsbuilding`.`temporarily_absent_detail`
DROP
INDEX `unique` ,
ADD UNIQUE INDEX `unique` (`identity_card` ASC, `absent_type_id` ASC, `end_date` ASC) VISIBLE;
;

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
INSERT INTO `fpt_quan_ly_toa_nha`.`position` (`name`, `show`)
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

INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '1', 'aaa');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '2', 'bb');

INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `password`, `gender`, `homeTown`,
                                     `current_address`)
VALUES ('1', 'aaa', 'a@gmail.com', '0942578685', '123456aA@', '0', 'nam dinh', 'nam dinh');

INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('1', '1', '1');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('2', '1', '1');
