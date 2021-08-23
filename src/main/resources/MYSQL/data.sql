INSERT INTO `role`
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_MANAGER_SERVICE'),
       (3, 'ROLE_LANDLORD'),
       (4, 'ROLE_EMPLOYEE'),
       (5, 'ROLE_TENANT');

INSERT INTO `position`
VALUES (1, 'nhân viên kĩ thuật', 0),
       (2, 'nhân viên sữa chữa', 0),
       (3, 'nhân viên dọn dẹp', 0),
       (4, 'nhân viên giám sát', 0),
       (5, 'vợ', 1),
       (6, 'chồng', 1),
       (7, 'bố', 1),
       (8, 'mẹ', 1),
       (9, 'anh/chị ruột', 1),
       (10, 'con', 1),
       (11, 'anh/chị em họ', 1),
       (12, 'ông', 1),
       (13, 'bà', 1),
       (14, 'khác', 1);

INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('1', 'Nguyễn Thúy Hằng', 'hangnt16499@gmail.com', '0942578685', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '163446335', 'avatarEvT4I0IGh4', '16/04/1999',
        '1', 'Hà Nội', 'Nam Định');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('2', 'Nguyễn Văn Mạnh', 'manhnvhe130112@gmail.com', '0942578685', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '163446334', 'avatarEvT4I0IGh4', '16/04/1999',
        '1', 'Hà Nội', 'Vĩnh Phúc');

INSERT INTO `status_apartment_billing`
VALUES (1, 'Chưa thanh toán'),
       (2, 'Đã thanh toán');

INSERT INTO `type_apartment`
VALUES (1, '65'),
       (2, '120'),
       (3, '150'),
       (4, '200'),
       (5, '220');

INSERT INTO `vehicle`
VALUES (1, 'Xe điện', 90000, 5000),
       (2, 'Xe máy', 140000, 5000),
       (3, 'Xe ô tô 5 chỗ', 1000000, 40000),
       (4, 'Xe ô tô 7 chỗ', 1400000, 40000);

INSERT INTO `status_resident_card`
VALUES (1, 'Chờ xử lí'),
       (2, 'Đang xử lí'),
       (3, 'Đang hoạt động');

INSERT INTO `status_service_request`
VALUES (1, 'Chờ xử lí'),
       (2, 'Đang xử lí'),
       (3, 'Thành công'),
       (4, 'Huy bỏ');

INSERT INTO `temporarily_absent_type`
VALUES (1, 'tạm trú'),
       (2, 'tạm vắng');

INSERT INTO `service`
VALUES (1, 'dịch vụ đời sống'),
       (2, 'vui chơi giải trí');

INSERT INTO `sub_service`
VALUES (1, 2, 'BBQ'),
       (2, 2, 'Hồ bơi'),
       (3, 2, 'Tennis'),
       (4, 2, 'Sân bóng'),
       (5, 2, 'Đặt tiệc'),
       (6, 2, 'Covid-19'),
       (7, 1, 'Giặt là'),
       (8, 1, 'Dọn dẹp căn hộ'),
       (9, 1, 'Sửa chữa ');

INSERT INTO `detail_sub_service`
VALUES (1, 1, 'BBQ'),
       (2, 2, 'Hồ bơi'),
       (3, 3, 'Tennis'),
       (4, 4, 'Sân bóng'),
       (5, 5, 'Đặt tiệc'),
       (6, 6, 'Covid-19'),
       (7, 7, '1kg'),
       (8, 7, '2kg'),
       (9, 7, '3kg'),
       (10, 7, '4kg'),
       (11, 7, '5kg'),
       (12, 7, '6kg'),
       (13, 7, '7kg'),
       (14, 7, '8kg'),
       (15, 7, '10kg'),
       (16, 8, '65m2'),
       (17, 8, '120m2'),
       (18, 8, '150m2'),
       (19, 8, '200m2'),
       (20, 8, '250m2'),
       (21, 9, 'Bình nóng lạnh'),
       (22, 9, 'Điều hòa'),
       (23, 9, 'Bóng đèn'),
       (24, 9, 'Thiết bị nhà vệ sinh'),
       (25, 9, 'Thiết bị báo cháy');

INSERT INTO `reason_detail_sub_service`
VALUES (1, 1, 'BBQ', 100000),
       (2, 2, 'Hồ bơi', 30000),
       (3, 3, 'Tennis', 150000),
       (4, 4, 'Sân bóng', 200000),
       (5, 5, 'Đặt tiệc', 500000),
       (6, 6, 'Covid-19', 700000),
       (7, 7, '1kg', 15000),
       (8, 8, '2kg', 28000),
       (9, 9, '3kg', 42000),
       (10, 10, '4kg', 56000),
       (11, 11, '5kg', 70000),
       (12, 12, '6kg', 85000),
       (13, 13, '7kg', 95000),
       (14, 14, '8kg', 100000),
       (15, 15, '10kg', 130000),
       (16, 16, '65m2', 1300000),
       (17, 17, '120m2', 2200000),
       (18, 18, '150m2', 25000000),
       (19, 19, '200m2', 3000000),
       (20, 20, '250m2', 3700000),
       (21, 21, 'Hở điện', 0),
       (22, 21, 'Không làm nóng', 0),
       (23, 22, 'Hỏng điều khiển', 0),
       (24, 22, 'Không làm mát', 0),
       (25, 22, 'Vệ sinh điều hòa', 0),
       (26, 22, 'Thay ga', 0),
       (27, 23, 'Thay mới', 0),
       (28, 24, 'Tắc bồn cầu', 0),
       (29, 25, 'Kiểm tra định kì', 0);

INSERT INTO `amsbuilding`.`building` (building_name, address, description)
VALUES ('AMSBuilding', '423 Minh Khai, Vĩnh Tuy, Hai Bà Trưng, Hà Nội',
        'Tọa lạc số 423 Minh Khai, Hai Bà Trưng, Hà Nội, dự án chung cư AMS BUILDING nằm ngay trên những tuyến đường giao thông huyết mạch của thành phố và thừa hưởng toàn bộ tiện ích khu vực. Vị trí chung cư là một vị trí đắc địa, tọa lạc trung tâm quận Hai Bà Trưng- 1 trong 4 quận nội thành Hà Nội. Đây là địa bàn tập trung nhiều cơ quan hành chính – kinh tế – văn hóa đầu não của cả nước cũng như nhiều địa điểm văn hóa, du lịch nổi tiếng như Hồ Gươm, Nhà hát lớn…');

INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '1', 'Nguyễn Anh Tú', 'tuna@gmail.com', '0911363921', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446332', 'avatarEvT4I0IGh4', '10/04/1997',
        '1', 'Hà Nội', 'Thái Bình');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '1', 'Trần Bảo Sơn', 'sontb@gmail.com', '0911363911', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446333', 'avatarEvT4I0IGh4', '30/08/1995',
        '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '1', 'Nguyễn Công Hậu', 'haunc@gmail.com', '0977631842', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446337', 'avatarEvT4I0IGh4', '03/09/1994',
        '1', 'Hà Nội', 'Tuyên Quang');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '1', 'Lê Ðăng Khánh', 'khanhld@gmail.com', '0978631563', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446338', 'avatarEvT4I0IGh4', '01/02/1998',
        '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '1', 'Nguyễn Hải Nam', 'namnh@gmail.com', '0977381897', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446339', 'avatarEvT4I0IGh4', '07/07/1991',
        '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '2', 'Nguyễn Văn Thành', 'thanhnv@gmail.com', '0978381906', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446340', 'avatarEvT4I0IGh4', '03/06/1995',
        '1', 'Hà Nội', 'Nam Định');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '2', 'Nguyễn Huy Hoàng', 'hoangnh@gmail.com', '0911381907', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446341', 'avatarEvT4I0IGh4', '05/07/1986',
        '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '2', 'Đặng Văn Long', 'longdv@gmail.com', '0911391908', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446342', 'avatarEvT4I0IGh4', '06/08/1992',
        '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '2', 'Lê Văn Giang ', 'gianglv@gmail.com', '0911381910', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446302', 'avatarEvT4I0IGh4', '10/05/1993',
        '1', 'Hà Nội', 'Nam Định');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '2', 'Nguyễn Văn Trọng', 'trongnv@gmail.com', '0911381911', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446343', 'avatarEvT4I0IGh4', '10/10/1988',
        '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '3', 'Lê Thị Hiền', 'hienlt@gmail.com', '0977381912', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446344', 'avatarEvT4I0IGh4', '24/08/1982',
        '1', 'Hà Nội', 'Thái Bình');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '3', 'Nguyễn Anh Thơ', 'thona@gmail.com', '0977381913', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446345', 'avatarEvT4I0IGh4', '01/01/1973',
        '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '3', 'Nguyễn Hoài An', 'annh@gmail.com', '0977381914', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446346', 'avatarEvT4I0IGh4', '08/03/1986',
        '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '3', 'Nguyễn Thị Hải Anh', 'anhnth@gmail.com', '0977381915', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446347', 'avatarEvT4I0IGh4', '22/11/1991',
        '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '3', 'Đặng Ngọc Linh', 'lingnd@gmail.com', '0977381916', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446348', 'avatarEvT4I0IGh4', '15/08/1976',
        '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '4', 'Phạm Văn Cảnh ', 'canhpv@gmail.com', '0977381917', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446349', 'avatarEvT4I0IGh4', '10/03/1971',
        '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '4', 'Trần Việt Hoàng', 'hoangtv@gmail.com', '0977381918', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446350', 'avatarEvT4I0IGh4', '23/11/1969',
        '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '4', 'Đặng Văn Huy', 'huydv@gmail.com', '0977381919', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446351', 'avatarEvT4I0IGh4', '01/10/1975',
        '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '4', 'Đỗ Văn Dũng', 'dungdv@gmail.com', '0977381920', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446352', 'avatarEvT4I0IGh4', '02/02/1991',
        '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('4', '4', 'Nguyễn Minh Đức', 'ducnm@gmail.com', '0977381921', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '101446354', 'avatarEvT4I0IGh4', '10/01/1992',
        '1', 'Hà Nội', 'Hưng Yên');

INSERT INTO `amsbuilding`.`block` (`block_name`)
VALUES ('A');
INSERT INTO `amsbuilding`.`block` (`block_name`)
VALUES ('B');
INSERT INTO `amsbuilding`.`block` (`block_name`)
VALUES ('C');
INSERT INTO `amsbuilding`.`block` (`block_name`)
VALUES ('D');

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

INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('1', '1');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('1', '2');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('1', '3');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('1', '4');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('1', '5');
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
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('3', '4');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('3', '5');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('4', '1');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('4', '2');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('4', '3');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('4', '4');
INSERT INTO `amsbuilding`.`floor_block` (`block_id`, `floor_id`)
VALUES ('4', '5');

INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '1', 'A101');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '1', 'A102');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '2', 'A103');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '2', 'A104');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '3', 'A105');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '3', 'A106');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '4', 'A107');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '4', 'A108');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '5', 'A109');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('1', '5', 'A110');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('2', '1', 'A201');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('2', '1', 'A202');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('2', '2', 'A203');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('2', '2', 'A204');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('2', '3', 'A205');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('2', '3', 'A206');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('2', '4', 'A207');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('2', '4', 'A208');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('2', '5', 'A209');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('2', '5', 'A210');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('3', '1', 'A301');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('3', '1', 'A302');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('3', '2', 'A303');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('3', '2', 'A304');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('3', '3', 'A305');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('3', '3', 'A306');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('3', '4', 'A307');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('3', '4', 'A308');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('3', '5', 'A309');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('3', '5', 'A310');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('4', '1', 'A401');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('4', '1', 'A402');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('4', '2', 'A403');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('4', '2', 'A404');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('4', '3', 'A405');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('4', '3', 'A406');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('4', '4', 'A407');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('4', '4', 'A408');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('4', '5', 'A409');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('4', '5', 'A410');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('5', '1', 'A501');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('5', '1', 'A502');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('5', '2', 'A503');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('5', '2', 'A504');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('5', '3', 'A505');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('5', '3', 'A506');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('5', '4', 'A507');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('5', '4', 'A508');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('5', '5', 'A509');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('5', '5', 'A510');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('6', '1', 'B101');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('6', '1', 'B102');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('6', '2', 'B103');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('6', '2', 'B104');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('6', '3', 'B105');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('6', '3', 'B106');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('6', '4', 'B107');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('6', '4', 'B108');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('6', '5', 'B109');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('6', '5', 'B110');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('7', '1', 'B201');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('7', '1', 'B202');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('7', '2', 'B203');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('7', '2', 'B204');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('7', '3', 'B205');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('7', '3', 'B206');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('7', '4', 'B207');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('7', '4', 'B208');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('7', '5', 'B209');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('7', '5', 'B210');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('8', '1', 'B301');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('8', '1', 'B302');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('8', '2', 'B303');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('8', '2', 'B304');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('8', '3', 'B305');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('8', '3', 'B306');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('8', '4', 'B307');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('8', '4', 'B308');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('8', '5', 'B309');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('8', '5', 'B310');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('9', '1', 'B401');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('9', '1', 'B402');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('9', '2', 'B403');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('9', '2', 'B404');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('9', '3', 'B405');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('9', '3', 'B406');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('9', '4', 'B407');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('9', '4', 'B408');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('9', '5', 'B409');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('9', '5', 'B410');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('10', '1', 'B501');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('10', '1', 'B502');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('10', '2', 'B503');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('10', '2', 'B504');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('10', '3', 'B505');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('10', '3', 'B506');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('10', '4', 'B507');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('10', '4', 'B508');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('10', '5', 'B509');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('10', '5', 'B510');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('11', '1', 'C101');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('11', '1', 'C102');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('11', '2', 'C103');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('11', '2', 'C104');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('11', '3', 'C105');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('11', '3', 'C106');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('11', '4', 'C107');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('11', '4', 'C108');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('11', '5', 'C109');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('11', '5', 'C110');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('12', '1', 'C201');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('12', '1', 'C202');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('12', '2', 'C203');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('12', '2', 'C204');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('12', '3', 'C205');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('12', '3', 'C206');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('12', '4', 'C207');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('12', '4', 'C208');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('12', '5', 'C209');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('12', '5', 'C210');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('13', '1', 'C301');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('13', '1', 'C302');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('13', '2', 'C303');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('13', '2', 'C304');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('13', '3', 'C305');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('13', '3', 'C306');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('13', '4', 'C307');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('13', '4', 'C308');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('13', '5', 'C309');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('13', '5', 'C310');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('14', '1', 'C401');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('14', '1', 'C402');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('14', '2', 'C403');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('14', '2', 'C404');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('14', '3', 'C405');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('14', '3', 'C406');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('14', '4', 'C407');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('14', '4', 'C408');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('14', '5', 'C409');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('14', '5', 'C410');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('15', '1', 'C501');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('15', '1', 'C502');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('15', '2', 'C503');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('15', '2', 'C504');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('15', '3', 'C505');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('15', '3', 'C506');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('15', '4', 'C507');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('15', '4', 'C508');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('15', '5', 'C509');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('15', '5', 'C510');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('16', '1', 'D101');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('16', '1', 'D102');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('16', '2', 'D103');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('16', '2', 'D104');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('16', '3', 'D105');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('16', '3', 'D106');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('16', '4', 'D107');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('16', '4', 'D108');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('16', '5', 'D109');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('16', '5', 'D110');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('17', '1', 'D201');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('17', '1', 'D202');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('17', '2', 'D203');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('17', '2', 'D204');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('17', '3', 'D205');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('17', '3', 'D206');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('17', '4', 'D207');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('17', '4', 'D208');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('17', '5', 'D209');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('17', '5', 'D210');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('18', '1', 'D301');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('18', '1', 'D302');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('18', '2', 'D303');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('18', '2', 'D304');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('18', '3', 'D305');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('18', '3', 'D306');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('18', '4', 'D307');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('18', '4', 'D308');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('18', '5', 'D309');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('18', '5', 'D310');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('19', '1', 'D401');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('19', '1', 'D402');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('19', '2', 'D403');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('19', '2', 'D404');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('19', '3', 'D405');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('19', '3', 'D406');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('19', '4', 'D407');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('19', '4', 'D408');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('19', '5', 'D409');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('19', '5', 'D410');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('20', '1', 'D501');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('20', '1', 'D502');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('20', '2', 'D503');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('20', '2', 'D504');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('20', '3', 'D505');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('20', '3', 'D506');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('20', '4', 'D507');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('20', '4', 'D508');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('20', '5', 'D509');
INSERT INTO `amsbuilding`.`room_number` (`floor_block_id`, `type_apartment_id`, `room_name`)
VALUES ('20', '5', 'D510');

INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '1');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '2');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '3');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '4');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '5');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '6');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '7');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '8');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '9');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '10');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '11');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '12');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '13');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '14');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '15');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '16');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '17');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '18');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '19');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '20');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '21');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '22');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '23');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '24');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '25');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '26');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '27');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '28');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '29');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '30');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '31');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '32');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '33');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '34');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '35');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '36');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '37');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '38');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '39');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '40');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '41');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '42');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '43');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '44');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '45');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '46');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '47');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '48');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '49');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '50');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '51');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '52');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '53');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '54');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '55');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '56');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '57');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '58');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '59');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '60');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '61');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '62');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '63');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '64');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '65');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '66');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '67');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '68');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '69');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '70');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '71');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '72');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '73');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '74');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '75');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '76');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '77');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '78');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '79');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '80');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '81');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '82');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '83');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '84');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '85');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '86');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '87');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '88');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '89');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '90');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '91');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '92');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '93');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '94');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '95');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '96');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '97');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '98');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '99');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '100');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '101');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '102');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '103');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '104');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '105');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '106');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '107');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '108');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '109');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '110');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '111');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '112');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '113');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '114');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '115');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '116');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '117');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '118');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '119');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '120');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '121');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '122');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '123');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '124');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '125');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '126');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '127');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '128');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '129');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '130');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '131');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '132');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '133');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '134');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '135');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '136');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '137');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '138');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '139');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '140');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '141');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '142');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '143');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '144');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '145');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '146');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '147');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '148');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '149');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '150');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '151');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '152');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '153');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '154');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '155');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '156');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '157');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '158');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '159');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '160');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '161');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '162');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '163');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '164');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '165');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '166');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '167');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '168');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '169');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '170');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '171');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '172');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '173');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '174');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '175');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '176');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '177');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '178');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '179');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '180');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '181');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '182');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '183');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '184');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '185');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '186');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '187');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '188');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '189');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '190');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '191');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '192');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '193');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '194');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '195');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '196');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '197');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '198');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '199');
INSERT INTO `amsbuilding`.`apartment` (`building_id`, `room_number_id`)
VALUES ('1', '200');

INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Trần Huy Việt', 'thuyhangnknd@gmail.com', '0977358801', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134001', 'avatarEvT4I0IGh4',
        '08/03/1986', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Văn Hải', 'hangnguyen1604999@gmail.com', '0977358802', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134002', 'avatarEvT4I0IGh4',
        '23/11/1969', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Thị Thủy', 'hangnthe130461@fpt.edu.vn', '0977358803', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134003', 'avatarEvT4I0IGh4',
        '05/07/1986', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Lê Văn Đức ', 'hangnt5199@gmail.com', '0977358804', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134004', 'avatarEvT4I0IGh4',
        '15/08/1976', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Trần Quốc Đại', 'loidvhe130407@fpt.edu.vn', '0977358805', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134005', 'avatarEvT4I0IGh4',
        '02/02/1991', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Minh Anh', 'loidv01071999@gmail.com', '0977358806', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134006', 'avatarEvT4I0IGh4',
        '05/07/1986', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễnn Lan Anh', 'lois2huyen@gmail.com', '0977358807', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134007', 'avatarEvT4I0IGh4',
        '03/09/1994', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Đào Thị Thu Hà', 'tuannahe130840@fpt.edu.vn', '0977358808', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134008', 'avatarEvT4I0IGh4',
        '15/08/1976', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Trường Giang', 'tuanna1301@gmail.com', '0977358809', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134009', 'avatarEvT4I0IGh4',
        '02/02/1991', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Lê Minh Hiếu', 'tuangooners14@gmail.com', '0977358810', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134010', 'avatarEvT4I0IGh4',
        '03/09/1994', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Phạm Tố Nga', 'caubehaman1@gmail.com', '0977358811', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134011', 'avatarEvT4I0IGh4',
        '05/07/1986', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Việt Hương', 'caubehaman2@gmail.com', '0977358812', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134012', 'avatarEvT4I0IGh4',
        '02/02/1991', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Trần Văn Lâm', 'caubehaman3@gmail.com', '0977358813', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134013', 'avatarEvT4I0IGh4',
        '03/09/1994', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Thị Thúy Kiều', 'caubehaman4@gmail.com', '0977358814', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134014', 'avatarEvT4I0IGh4',
        '15/08/1976', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Trần Huy Phong', 'caubehaman5@gmail.com', '0977358815', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134015', 'avatarEvT4I0IGh4',
        '03/09/1994', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Khắc Ninh', 'caubehaman6@gmail.com', '0977358816', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134016', 'avatarEvT4I0IGh4',
        '06/08/1992', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Lê Kim Long', 'caubehaman7@gmail.com', '0977358817', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134017', 'avatarEvT4I0IGh4',
        '05/07/1986', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn  Mạnh Thắng', 'caubehaman8@gmail.com', '0977358818', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134018', 'avatarEvT4I0IGh4',
        '06/08/1992', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Trần Mạnh Tuấn ', 'caubehaman9@gmail.com', '0977358819', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134019', 'avatarEvT4I0IGh4',
        '03/09/1994', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Phạm Minh Khôi ', 'caubehaman10@gmail.com', '0977358820', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134020', 'avatarEvT4I0IGh4',
        '06/08/1992', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Phạm Minh Nhật', 'caubehaman11@gmail.com', '0977358821', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134021', 'avatarEvT4I0IGh4',
        '15/08/1976', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Ngọc Thuận ', 'caubehaman12@gmail.com', '0977358822', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134022', 'avatarEvT4I0IGh4',
        '06/08/1992', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Đặng Nhật Nam', 'caubehaman13@gmail.com', '0977358823', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134023', 'avatarEvT4I0IGh4',
        '02/02/1991', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Phú Hùng', 'caubehaman14@gmail.com', '0977358824', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134024', 'avatarEvT4I0IGh4',
        '06/08/1992', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Đinh Quang Anh', 'caubehaman15@gmail.com', '0977358825', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134025', 'avatarEvT4I0IGh4',
        '15/08/1976', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Đào Phương Nam ', 'caubehaman16@gmail.com', '0977358826', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134026', 'avatarEvT4I0IGh4',
        '05/07/1986', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Lê Quốc Thắng', 'caubehaman17@gmail.com', '0977358827', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134027', 'avatarEvT4I0IGh4',
        '03/09/1994', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Thái Dương', 'caubehaman18@gmail.com', '0977358828', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134028', 'avatarEvT4I0IGh4',
        '02/02/1991', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Hoàng Thái Sơn ', 'caubehaman19@gmail.com', '0977358829', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134029', 'avatarEvT4I0IGh4',
        '03/09/1994', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Trần Thành Đạt', 'caubehaman20@gmail.com', '0977358830', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134030', 'avatarEvT4I0IGh4',
        '02/02/1991', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Đức Tuyên', 'caubehaman21@gmail.com', '0977358831', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134031', 'avatarEvT4I0IGh4',
        '06/08/1992', '1', 'Hà Nội', 'Thái Bình');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Hoàng Minh Đức', 'caubehaman22@gmail.com', '0977358832', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134032', 'avatarEvT4I0IGh4',
        '05/07/1986', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Trung Quân', 'caubehaman23@gmail.com', '0977358833', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134033', 'avatarEvT4I0IGh4',
        '06/08/1992', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Vương Tuấn Khải', 'caubehaman24@gmail.com', '0977358834', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134034', 'avatarEvT4I0IGh4',
        '03/09/1994', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Viết Cương ', 'caubehaman25@gmail.com', '0977358835', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134035', 'avatarEvT4I0IGh4',
        '02/02/1991', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Trần Đức Viễn', 'caubehaman26@gmail.com', '0977358836', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134036', 'avatarEvT4I0IGh4',
        '06/08/1992', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Quang Huy', 'caubehaman27@gmail.com', '0977358837', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134037', 'avatarEvT4I0IGh4',
        '05/07/1986', '1', 'Hà Nội', 'Thái Bình');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Quang Khải ', 'caubehaman28@gmail.com', '0977358838', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134038', 'avatarEvT4I0IGh4',
        '06/08/1992', '1', 'Hà Nội', 'Thái Bình');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Thái Vân Linh', 'caubehaman29@gmail.com', '0977358839', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134039', 'avatarEvT4I0IGh4',
        '15/08/1976', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Vân Trang', 'caubehaman30@gmail.com', '0977358840', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134040', 'avatarEvT4I0IGh4',
        '03/09/1994', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Đông  Nhi ', 'caubehaman31@gmail.com', '0977358841', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134041', 'avatarEvT4I0IGh4',
        '05/07/1986', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Lê Thùy Linh', 'caubehaman32@gmail.com', '0977358842', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134042', 'avatarEvT4I0IGh4',
        '02/02/1991', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Lê Phương Thảo', 'caubehaman33@gmail.com', '0977358843', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134043', 'avatarEvT4I0IGh4',
        '03/09/1994', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Thảo Trang', 'caubehaman34@gmail.com', '0977358844', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134044', 'avatarEvT4I0IGh4',
        '05/07/1986', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Hoàng Thảo Vy', 'caubehaman35@gmail.com', '0977358845', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134045', 'avatarEvT4I0IGh4',
        '03/09/1994', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Trịnh Thủy Tiên', 'caubehaman36@gmail.com', '0977358846', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134046', 'avatarEvT4I0IGh4',
        '02/02/1991', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Thái Hà ', 'caubehaman37@gmail.com', '0977358847', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134047', 'avatarEvT4I0IGh4',
        '15/08/1976', '1', 'Hà Nội', 'Thái Bình');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Thùy Dung', 'caubehaman38@gmail.com', '0977358848', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134048', 'avatarEvT4I0IGh4',
        '02/02/1991', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Thanh Hà ', 'caubehaman39@gmail.com', '0977358849', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134049', 'avatarEvT4I0IGh4',
        '05/07/1986', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `name`, `email`, `phone`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('3', 'Nguyễn Hà Trang', 'caubehaman40@gmail.com', '0977358850', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200134050', 'avatarEvT4I0IGh4',
        '15/08/1976', '1', 'Hà Nội', 'Hải Dương');

INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '6', 'Nguyễn Văn Lợi', 'nguoidumuc001@gmail.com', '0978135701', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323401', 'avatarEvT4I0IGh4',
        '05/07/1986', '1', 'Hà Nội', 'Nam Định');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `dob`,
                                     `enabled`, `home_town`, `current_address`)
VALUES ('5', '5', 'Ngô Hải An', '0978135702', '0', '001200323499', '02/12/1990', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `dob`,
                                     `enabled`, `home_town`, `current_address`)
VALUES ('5', '10', 'Nguyễn Du', '0978135703', '1', '012345678912', '01/01/1992', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '11', 'Mai Nam Hải', '1', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '5', 'Vũ Thanh Thanh', 'nguoidumuc002@gmail.com', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323402', 'avatarEvT4I0IGh4',
        '10/03/1991', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '14', 'Vũ Thanh Tùng', '1', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Nguyễn Hải Sơn', '0978135704', '1', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '8', 'Trịnh Thị Mùi', '0', '001200323403', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '7', 'Trịnh Văn Quyết', '1', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Nguyễn Thúy Hằng ', '0978135705', '0', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '11', 'Nguyễn Tiến Nam', '1', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '14', 'Ngô Bá Khá', '1', '001200323404', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '6', 'Phạm Văn Nhân', 'nguoidumuc003@gmail.com', '0978135707', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323405', 'avatarEvT4I0IGh4',
        '10/03/1990', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '7', 'Nguyễn Đình Chiến', '0978135706', '1', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Nguyễn Văn Cường ', '1', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '11', 'Nguyễn Thị Thu Thủy', '0', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '7', 'Trần Bình Minh', '1', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '5', 'Nguyễn Phương Chi', 'nguoidumuc004@gmail.com', '0978135708', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323406', 'avatarEvT4I0IGh4',
        '10/10/1992', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '14', 'Nguyễn Đức Tuyến', '1', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Nguyễn Hoàng Dũng', '1', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `dob`,
                                     `enabled`, `home_town`, `current_address`)
VALUES ('5', '6', 'Trịnh Nam Sơn', '0978135709', '1', '013122301001', '02/02/1989', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Nguyễn Công Sơn', '1', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '9', 'Phạm Văn Lâm', '0978135710', '1', '001200323407', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Vũ Trọng Bình', '0978135711', '1', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Nguyễn Thúy Quỳnh', '0', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Nguyễn Văn Nam ', '1', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '6', 'Trần Nam Dũng ', 'nguoidumuc005@gmail.com', '0978135712', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323408', 'avatarEvT4I0IGh4', '1',
        'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '11', 'Trần Hải Long ', '1', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '12', 'Trần Văn Lợi', '1', '10/03/1950', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '13', 'Phạm Thành Long', '1', '10/03/1951', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '11', 'Nguyễn Thành Tiến', '1', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '13', 'Nguyễn Trịnh Trọng', '1', '10/07/1950', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '5', 'Nguyễn Thị Vân ', 'nguoidumuc006@gmail.com', '0978135713', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323409', 'avatarEvT4I0IGh4',
        '04/03/1986', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Nguyễn Thị Ngọc Thảo', '0', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '11', 'Vũ Thị Ngọc', '0', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '12', 'Đào Duy Quân', '1', '13/03/1952', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `identify_card`, `dob`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '13', 'Văn Hà Anh', '0', '001200323410', '19/09/1956', '1', 'Hà Nội', 'Hải Dương');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '6', 'Vũ Minh Hiếu', '0978135714', '1', '013122301002', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '6', 'Đào Văn Sơn', '0978135715', '1', '013122301003', '1', 'Hà Nội', 'Hà Nam');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '7', 'Nguyễn Sơn Tùng', '1', '013122301004', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Nguyễn Quang Huy', '1', '1', 'Hà Nội', 'Ninh Bình');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '11', 'Nguyễn Đại Đức', '1', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '5', 'Nguyễn Thị Minh Tâm', 'nguoidumuc008@gmail.com', '0978135716', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323411', 'avatarEvT4I0IGh4',
        '10/10/1985', '1', 'Hà Nội', 'Thái Bình');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '11', 'Nguyễn Ngọc Ánh', '0', '1', 'Hà Nội', 'Nam Định');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '12', 'Nguyễn Việt Hưng', '1', '20/03/1951', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '8', 'Nguyễn Ngọc Trâm', '0', '1', 'Hà Nội', 'Bắc Ninh');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Nguyễn Mai Anh', '0', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '5', 'Lê Thị Hiên', '0978135717', '0', '013122301005', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '6', 'Phạm Tuấn Anh', 'nguoidumuc009@gmail.com', '0978135718', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323412', 'avatarEvT4I0IGh4',
        '20/10/19985', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Lê Văn Lâm', '1', '1', 'Hà Nội', 'Tuyên Quang');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '14', 'Đỗ Duy Nam ', '1', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '11', 'Vũ Văn Hiếu', '1', '1', 'Hà Nội', 'Hà Nam');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '9', 'Nguyễn Xuân Ngọc', '0978135719', '1', '013122301006', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Trần Thi Bình', '1', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Mai Nam Hải ', '1', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '6', 'Phùng Thanh Tùng', 'nguoidumuc010@gmail.com', '0978135720', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323413', 'avatarEvT4I0IGh4',
        '10/05/1988', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '8', 'Phùng Thị Xuân', '1', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '12', 'Lê Trọng Lịch', '1', '10/03/1948', '1', 'Hà Nội', 'Hà Nôik');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '7', 'Vũ Minh Quân', '0978135722', '1', '013122301007', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Trần Quốc Anh', '1', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '12', 'Nguyễn Quang Hiếu', '1', '10/10/1949', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Nguyễn Ngọc Sơn', '1', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Nguyễn Ngọc Hiếu', '1', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '5', 'Nguyễn Hải Sơn', 'nguoidumuc011@gmail.com', '0978135723', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323414', 'avatarEvT4I0IGh4',
        '10/05/1985', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Nguyễn Quang Huy', '1', '10/03/1994', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '5', 'Trịnh Mai Tiên', '0978135724', '0', '013122301008', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '8', 'Nguyễn Mai Sương', '0', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '9', 'Nguyễn HẢi Tiến', '1', '001200323415', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Trần Nam Anh', '1', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Trần Thúy Diễm', '0', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '11', 'Nguyễn Quang Hiếu', '1', '1', 'Hà Nội', 'Hưng Yên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `gender`, `password`, `identify_card`,
                                     `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '6', 'Phạm Văn Lợi', 'nguoidumuc012@gmail.com', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323416', 'avatarEvT4I0IGh4',
        '11/12/1990', '1', 'Hà Nội', 'Nam Định');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Nguyễn Quang Huy', '1', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '14', 'Nguyễn Bá Mạnh', '1', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '6', 'Nguyễn Ngọc Kiên', 'nguoidumuc013@gmail.com', '0978135725', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323417', 'avatarEvT4I0IGh4',
        '10/03/1987', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Lương Minh Trang', '0', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '12', 'Lê Trung Kiên', '1', '01/03/1954', '1', 'Hà Nội', 'Bắc Ninh');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '14', 'Hoàng Xuân Vinh', '1', '1', 'Hà Nội', 'HÀ Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `identify_card`, `enabled`,
                                     `home_town`, `current_address`)
VALUES ('5', '9', 'Nguyễn Quang Huy', '1', '013122301009', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '14', 'Vũ Tùng Sơn', '0', '10/10/2006', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '11', 'Võ Hoàng Yến', '1', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '6', 'Võ Trọng Minh', 'nguoidumuc014@gmail.com', '0978135726', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323418', 'avatarEvT4I0IGh4',
        '10/03/1979', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `dob`,
                                     `enabled`, `home_town`, `current_address`)
VALUES ('5', '5', 'Lê Quỳnh Hương', '0978135727', '0', '013122301010', '10/06/1976', '1', 'Hà Nội', 'Hà Nội');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Mai Hải Anh', '0', '1', 'Hà Nội', 'Bắc Ninh');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `dob`,
                                     `enabled`, `home_town`, `current_address`)
VALUES ('5', '9', 'Bạch Hồng Nghĩa', '0978135721', '1', '013122301011', '21/05/1999', '1', 'Hà Nội', 'Bắc Giang');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Nguyễn Quang Hiếu', '1', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '6', 'Vũ Nam Tiến', 'nguoidumuc015@gmail.com', '0978135728', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323419', 'avatarEvT4I0IGh4', '1',
        'Hà Nội', 'Bắc Giang');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Trịnh Văn Bình', '1', '1', 'Hà Nội', 'Bắc Giang');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '8', 'Nguyễn Hoàng Giang', '0', '10/03/1970', '1', 'Hà Nội', 'Nam ĐỊnh');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `phone`, `gender`, `identify_card`, `dob`,
                                     `enabled`, `home_town`, `current_address`)
VALUES ('5', '5', 'Trịnh Thị Tuyết', '0978135729', '0', '013122301012', '10/10/1992', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Nguyễn Quang Huy', '1', '1', 'Hà Nội', 'Thái Bình');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `gender`, `password`, `identify_card`,
                                     `image_link`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '9', 'Vũ Hải An', 'nguoidumuc016@gmail.com', '0',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323420', 'avatarEvT4I0IGh4', '1',
        'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '13', 'Nguyễn Bá Huy', '1', '1', 'Hà Nội', 'Thái Bình');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '13', 'Vũ Quang Long', '1', '1', 'Hà Nội', 'Bắc Ninh');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Mai Xuân Hải', '1', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Nguyễn Đại Hiệp', '1', '1', 'Hà Nội', 'Hà Nam');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '6', 'Nguyễn Quang Hiếu', 'nguoidumuc017@gmail.com', '0978135730', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323421', 'avatarEvT4I0IGh4',
        '04/11/1984', '1', 'Hà Nội', 'Bắc Giang');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '9', 'Nguyễn Minh Giang Anh', '0', '1', 'Hà Nội', 'Thái Nguyên');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '10', 'Trần Minh Quân', '1', '1', 'Hà Nội', 'Hải Phòng');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `gender`, `dob`, `enabled`, `home_town`,
                                     `current_address`)
VALUES ('5', '5', 'Thiều Quỳnh Hương', '0', '10/10/1995', '1', 'Hà Nội', 'Bắc Ninh');
INSERT INTO `amsbuilding`.`account` (`role_id`, `position_id`, `name`, `email`, `phone`, `gender`, `password`,
                                     `identify_card`, `image_link`, `dob`, `enabled`, `home_town`, `current_address`)
VALUES ('5', '9', 'Phan Hải Nam', 'nguoidumuc018@gmail.com', '0978135733', '1',
        '$2a$12$USmWF4mjeFbIs500llf9rOYQC.HurTd360lNWZb0QKfbQ5DPj/rGS', '001200323422', 'avatarEvT4I0IGh4',
        '10/11/1999', '1', 'Hà Nội', 'Thái Nguyên');

UPDATE `amsbuilding`.`apartment`
SET `account_id` = '55'
WHERE (`id` = '1');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('105', '1', '1');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '56'
WHERE (`id` = '2');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('106', '1', '2');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '57'
WHERE (`id` = '3');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '58'
WHERE (`id` = '4');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('107', '1', '3');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('108', '1', '3');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('109', '1', '4');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('110', '1', '4');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('111', '1', '4');

UPDATE `amsbuilding`.`apartment`
SET `account_id` = '59'
WHERE (`id` = '7');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('112', '1', '7');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('113', '1', '7');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('114', '1', '7');


UPDATE `amsbuilding`.`apartment`
SET `account_id` = '60'
WHERE (`id` = '9');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('115', '1', '9');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('116', '1', '9');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '61'
WHERE (`id` = '11');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('117', '1', '11');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '62'
WHERE (`id` = '12');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('118', '1', '12');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('119', '1', '13');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '63'
WHERE (`id` = '13');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('120', '1', '13');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('121', '1', '14');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '64'
WHERE (`id` = '14');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('122', '1', '14');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('123', '1', '14');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '65'
WHERE (`id` = '17');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('124', '1', '17');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('125', '1', '17');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('126', '1', '17');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '66'
WHERE (`id` = '19');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('127', '1', '19');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('128', '1', '19');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '67'
WHERE (`id` = '21');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('129', '1', '21');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('130', '1', '22');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '68'
WHERE (`id` = '22');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '69'
WHERE (`id` = '23');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('131', '1', '23');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('132', '1', '23');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('133', '1', '24');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('134', '1', '24');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('135', '1', '24');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('136', '1', '27');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('137', '1', '27');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('138', '1', '27');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('139', '1', '29');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('140', '1', '29');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '70'
WHERE (`id` = '27');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '71'
WHERE (`id` = '29');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '72'
WHERE (`id` = '31');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '73'
WHERE (`id` = '32');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '74'
WHERE (`id` = '33');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '75'
WHERE (`id` = '34');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('141', '1', '31');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('142', '1', '32');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('143', '1', '33');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('144', '1', '33');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('145', '1', '34');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('146', '1', '34');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('147', '1', '34');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('148', '1', '37');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('149', '1', '37');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('150', '1', '37');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('151', '1', '39');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('152', '1', '39');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '76'
WHERE (`id` = '37');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '77'
WHERE (`id` = '39');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '78'
WHERE (`id` = '41');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '79'
WHERE (`id` = '42');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '80'
WHERE (`id` = '43');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '81'
WHERE (`id` = '44');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '82'
WHERE (`id` = '47');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '83'
WHERE (`id` = '49');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('153', '1', '41');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('154', '1', '42');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('155', '1', '43');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('156', '1', '43');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('157', '1', '44');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('158', '1', '44');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('159', '1', '44');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('160', '1', '47');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('161', '1', '47');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('162', '1', '47');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('163', '1', '49');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('164', '1', '49');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '84'
WHERE (`id` = '156');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '85'
WHERE (`id` = '157');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '86'
WHERE (`id` = '158');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '87'
WHERE (`id` = '159');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('165', '1', '156');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('166', '1', '156');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('167', '1', '157');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('168', '1', '157');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('169', '1', '158');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('170', '1', '159');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('171', '1', '159');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('172', '1', '159');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '88'
WHERE (`id` = '166');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '89'
WHERE (`id` = '167');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '90'
WHERE (`id` = '168');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '91'
WHERE (`id` = '169');
INSERT INTO `amsbuilding`.`apartment` (`account_id`, `building_id`, `room_number_id`)
VALUES ('173', '1', '166');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '92'
WHERE (`id` = '176');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '93'
WHERE (`id` = '177');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '94'
WHERE (`id` = '178');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '95'
WHERE (`id` = '179');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '96'
WHERE (`id` = '186');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '97'
WHERE (`id` = '187');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '98'
WHERE (`id` = '188');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '99'
WHERE (`id` = '189');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '100'
WHERE (`id` = '196');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '101'
WHERE (`id` = '197');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '102'
WHERE (`id` = '198');
UPDATE `amsbuilding`.`apartment`
SET `account_id` = '103'
WHERE (`id` = '199');
