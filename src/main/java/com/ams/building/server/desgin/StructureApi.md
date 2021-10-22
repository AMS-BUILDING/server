# API DOCUMENT

## 1. API Login

#### POST:  localhost:8080/api/login

| Request Body      | Description | Require| Data Type|
| ----------- | ----------- |-----|-----|
| username      | email or user       |true| String|
| password   | password of user        |true|String|

*Example*

```java
{
        "username":"hangnt16499@gmail.com",
        "password":"123456aA@"
        }
```

*Output*

```java
{
        "accessToken":<token>,
        "expirationTime":3600000
        }
```

## 2. Forgot Password

#### POST: localhost:8080/api/forward-password

| Request Param      | Description | Require|Data Type|
| ----------- | ----------- |-----|----|
| email      | email or user want to change      |true|String|

*Output*

```java
    Mã code đã được gửi đến mail.Vui lòng check
```

## 3. Update Profile

#### POST: localhost:8080/api/tenant/update/profile

| ModelAttribute     | Description | Require|Data Type|
| ----------- | ----------- |-----|-----|
| name      | name of user      |false|String|
| phone   | phone of user       |false|String|
| email   | email of user        |false( in app will can't update email)|String|
| multipartFile   | File image when choose       |false|MultipartFile|
| identifyCard   | Identify card of user        |false|String|

*Example*: form data

```java
    name:Nguyễn Thúy Hằng
        phone:0942578685
        email:hangnt16499@gmail.com
    multipartFile:186532560_775564276658983_5796496429334396925_ n.jpg
            dentifyCard:163446335
```

*Output*

```java
Update profile success
```

## 4. Reset Password

#### POST: localhost:8080/api/reset-password

| Request Body      | Description | Require|Data Type|
| ----------- | ----------- |-----|-----|
| token      | code when send mail      |true|String|
| password      | new password of user      |true|String|

*Exanmple*

```java
{
        "token":"999999",
        "password":"1234567aA@"
        }
```

## 5. Show Image

#### GET : localhost:8080/api/download

| Request Param      | Description | Require|Data Type |
| ----------- | ----------- |-----|----|
| image      | new image     |true|String|

*Output*

```java
Download image success
```

## 6. Change Password

#### POST : localhost:8080/api/tenant/change-password

| Request Body      | Description | Require|Data type|
| ----------- | ----------- |-----|----|
| oldPassword      | old pass of user    |true|String |
| newPassword      | new pass of user    |true|String |

*Example*

```java
{
        "oldPassword": "123456aA@",
        "newPassword": "123456aA@"
        }
```

*Output*

```java
Change password success
```

## 7. Absent

### 7.1 Search Absent

#### GET : localhost:8080/api/admin/absent/search

| Request Param      | Description | Require| Default Value|Data type|
| ----------- | ----------- |-----|-----|----|
| name      | Name     |true|" "|String|
| identifyCard      | Identify card    |true|" "|String|
| absentType      | Type : Tạm Trú or Tạm Vắng   |true|-1|Long|
| pageNo      | Page No   |true|0|Integer|

*Output*

```java
"totalElement": 9,
        "data": [
        {
        "absentDetailId": 1,
        "name": "hang",
        "identifyCard": "163448995",
        "homeTown": "aaaaa",
        "block": "A1",
        "roomNumber": "A102",
        "startDate": "06/06/2020",
        "endDate": "06/06/2020",
        "absentType": "tạm trú",
        "reason": "aaaa"
        },
        {
        "absentDetailId": 2,
        "name": "hang",
        "identifyCard": "163448995",
        "homeTown": "aaaaa",
        "block": "A1",
        "roomNumber": "A102",
        "startDate": "06/06/2020",
        "endDate": "06/06/2020",
        "absentType": "tạm vắng",
        "reason": "aaaa"
        },
        {
        "absentDetailId": 3,
        "name": "Nguyễn Thúy Hằng",
        "identifyCard": "163446335",
        "homeTown": "Nam Định",
        "block": "A1",
        "roomNumber": "A102",
        "startDate": "11/07/2021",
        "endDate": "11/01/2022",
        "absentType": "tạm trú",
        "reason": "ABCD"
        },
        {
        "absentDetailId": 4,
        "name": "Nguyễn Thúy Hằng",
        "identifyCard": "163446345",
        "homeTown": "Nam Định",
        "block": "A1",
        "roomNumber": "A102",
        "startDate": "11/07/2021",
        "endDate": "11/01/2022",
        "absentType": "tạm trú",
        "reason": "ABCD"
        },
        {
        "absentDetailId": 5,
        "name": "Nguyễn Thúy Hằng",
        "identifyCard": "163446343",
        "homeTown": "Nam Định",
        "block": "A1",
        "roomNumber": "A103",
        "startDate": "11/07/2021",
        "endDate": "11/01/2022",
        "absentType": "tạm trú",
        "reason": "ABCD"
        }
        ]
        }
```

### 7.2 Export  Absent

#### GET : localhost:8080/api/admin/absent/export

| Request Param      | Description | Require| Default Value|Data type|
| ----------- | ----------- |-----|-----|-----|
| name      | Name     |true|"  "|String |
| identifyCard      | Identify card    |true|"   "|String |
| absentType      | Type : Tạm Trú or Tạm Vắng   |true|-1|Long|

*Output*

```java
Tên ,Chứng minh thư,Loại đăng kí,Lí do,Quê quán, Số block, Số phòng, Ngày bắt đầu,Ngày kết thúc
        hang,163448995,tạm trú,aaaa,aaaaa,A1,A102,06/06/2020,06/06/2020
        hang,163448995,tạm vắng,aaaa,aaaaa,A1,A102,06/06/2020,06/06/2020
        Nguyễn Thúy Hằng,163446335,tạm trú,ABCD,Nam Định,A1,A102,11/07/2021,11/01/2022
        Nguyễn Thúy Hằng,163446345,tạm trú,ABCD,Nam Định,A1,A102,11/07/2021,11/01/2022
        Nguyễn Thúy Hằng,163446343,tạm trú,ABCD,Nam Định,A1,A103,11/07/2021,11/01/2022
        Nguyễn Thúy Hằng,164881223,tạm trú,ABCD,Nam Định,A1,A204,11/07/2021,11/01/2022
        Nguyễn Thúy An,164881223,tạm vắng,ABCD,Nam Định,A1,A204,11/07/2021,11/01/2022
        Nguyễn Thúy An,163778999,tạm vắng,ABCD,Nam Định,A1,A204,11/07/2021,11/01/2022
        Nguyễn Thúy An,163889000,tạm vắng,ABCD,Nam Định,A1,A103,11/07/2021,11/01/2022
```

### 7.3 Add  Absent

#### POST : localhost:8080/api/landlord/absent/add

*Requets Body: Example*

```java
{
        "name": "Nguyễn Thúy An",
        "dob": "1999-04-16",
        "identifyCard": "163889000",
        "homeTown": "Nam Định",
        "reason": "ABCD",
        "startDate": "2021-07-11",
        "endDate": "2022-01-11",
        "absentType": 2,
        "accountId": 33
        }
```

*Output*

```java
Add success
```

## 8. Employee

### 8.1 Search Employee

#### GET: localhost:8080/api/manager-service/employee/search

| Request Param      | Description | Require| Default Value|Data type| 
| ----------- | ----------- |-----|-----|----|
| name      | Name     |true|"  "|String |
| identifyCard      | Identify card    |true|"   "|String |
| phoneNumber      | Type : Tạm Trú or Tạm Vắng   |true|-1|Long|

*Output*

```java
{
        "totalElement": 5,
        "data": [
        {
        "id": 32,
        "name": "Nguyễn 32",
        "phone": "0987456321",
        "email": "a@gmail.com",
        "dob": "16/04/1999",
        "identifyCard": "163446325",
        "homeTown": "Nam Định",
        "currentAddress": "Hà nội",
        "gender": true,
        "positionName": "nhân viên kĩ thuật"
        },
        {
        "id": 40,
        "name": "Nguyễn Văn B",
        "phone": "0987456321",
        "email": "asgfdfg@gmail.com",
        "dob": "16/04/1999",
        "identifyCard": "163554663",
        "homeTown": "Nam Định",
        "currentAddress": "Hà nội",
        "gender": true,
        "positionName": "nhân viên kĩ thuật"
        },
        {
        "id": 52,
        "name": "Nguyễn Văn B",
        "phone": "0987456322",
        "email": "1@gmail.com",
        "dob": "16/04/1999",
        "identifyCard": "12345678909",
        "homeTown": "Hà nội",
        "currentAddress": "Hà nội",
        "gender": true,
        "positionName": "nhân viên kĩ thuật"
        },
        {
        "id": 53,
        "name": "Nguyễn Văn B",
        "phone": "0987456321",
        "email": "1dsdd@gmail.com",
        "dob": "16/04/1999",
        "identifyCard": "1234567890",
        "homeTown": "Hà nội",
        "currentAddress": "Hà nội",
        "gender": true,
        "positionName": "nhân viên kĩ thuật"
        },
        {
        "id": 54,
        "name": "Nguyễn Văn B",
        "phone": "0987456320",
        "email": "hang123@gmail.com",
        "dob": "16/04/1999",
        "identifyCard": "098750999",
        "homeTown": "Hà nội",
        "currentAddress": "Hà nội",
        "gender": true,
        "positionName": "nhân viên kĩ thuật"
        }
        ]
        }
```

### 8.2 Detail Employee

#### GET: localhost:8080/api/manager-service/employee/get-one/{id}

| Path Variable     | Description | Require| Default Value|Data type| 
| ----------- | ----------- |-----|-----|----|
| id      | id of employee     |true| |Long |

*Output*

```java
{
        "id": 32,
        "name": "Nguyễn 32",
        "phone": "0987456321",
        "email": "a@gmail.com",
        "dob": "16/04/1999",
        "identifyCard": "163446325",
        "homeTown": "Nam Định",
        "currentAddress": "Hà nội",
        "gender": true,
        "positionName": "nhân viên kĩ thuật"
        }
```

### 8.3 Add Employee

#### POST: localhost:8080/api/admin/employee/add

*Example Request Body*

```java
{
        "name":"Nguyễn Văn B",
        "gender":true,
        "dob":"16/04/2021",
        "phoneNumber":"0968567766",
        "email":"hang1243@gmail.com",
        "identifyCard":"156778994",
        "currentAddress":"Hà nội",
        "homeTown":"Hà nội",
        "position":1
        }
```

### 8.4 Update Employee

#### POST: localhost:8080/api/admin/employee/update/{id}

*Example Request Body*

```java
{
        "name":"Nguyễn 32",
        "gender":true,
        "dob":"16/04/1999",
        "phoneNumber":"0987456321",
        "identifyCard":"163446325",
        "currentAddress":"Hà nội",
        "homeTown":"Hà nội",
        "position":1
        }
```

*Output*

```java
Update Employee success
```

### 8.5 Remove Employee

#### POST: localhost:8080/api/admin/employee/remove/{id}

*Output*

```java
Remove Employee success
```

### 8.6 Export Employee

#### GET: localhost:8080/api/admin/employee/export

| Request Param      | Description | Require| Default Value|Data type| 
| ----------- | ----------- |-----|-----|----|
| name      | Name     |true|"  "|String |
| identifyCard      | Identify card    |true|"   "|String |
| phoneNumber      | Type : Tạm Trú or Tạm Vắng   |true|-1|Long|

*Output*

```java
Tên,Giới tính,Số điện thoại,Email,Ngày tháng năm sinh,Chứng minh thư, Địa chỉ hiện tại, Quê quán, Vị trí
        Nguyễn 32,Male,0987456321,a@gmail.com,16/04/1999,163446325,Hà nội,Nam Định,nhân viên kĩ thuật
        Nguyễn Văn B,Male,0987456321,asgfdfg@gmail.com,16/04/1999,163554663,Hà nội,Nam Định,nhân viên kĩ thuật
        Nguyễn Văn B,Male,0987456322,1@gmail.com,16/04/1999,12345678909,Hà nội,Hà nội,nhân viên kĩ thuật
        Nguyễn Văn B,Male,0987456321,1dsdd@gmail.com,16/04/1999,1234567890,Hà nội,Hà nội,nhân viên kĩ thuật
        Nguyễn Văn B,Male,0987456320,hang123@gmail.com,16/04/1999,098750999,Hà nội,Hà nội,nhân viên kĩ thuật

```

## 9. Feedback

### 9.1 Search Feedback

#### GET: localhost:8080/api/admin/feedback/search

| Request Param      | Description | Require| Default Value|Data type| 
| ----------- | ----------- |-----|-----|----|
| title      | title     |true|"  "|String |

*Compulsory*:

```java
    AUTHORIZATION:Bearer Token
        Token:<token>
```

*Output*

```java
{
        "totalElement": 3,
        "data": [
        {
        "feedbackId": 1,
        "description": "aaa",
        "star": 3,
        "name": "abcxyz",
        "createdDate": "25/07/2021"
        },
        {
        "feedbackId": 2,
        "description": "aaa",
        "star": 3,
        "name": "abcxyz",
        "createdDate": "28/07/2021"
        },
        {
        "feedbackId": 3,
        "description": "aaa",
        "star": 3,
        "name": "abcxyz",
        "createdDate": "18/08/2021"
        }
        ]
        }
```

### 9.2 Add Feedback

#### POST: localhost:8080/api/landlord/feedback/add

*Example*

```java
{
        "accountId":2,
        "description":"aaa",
        "star":3
        }
```

*Output*

```java
Adđ feed back success
```

### 10. Position

#### GET : localhost:8080/api/manager-service/position/search

| Param    | Description | Require| Default Value|Data type| 
| ----------- | ----------- |-----|-----|----|
| show      | check type position     |true|true |Boolean |

```

*Output*
```java
[
    {
        "id": 5,
        "name": "vợ"
    },
    {
        "id": 6,
        "name": "chồng"
    },
    {
        "id": 7,
        "name": "bố"
    },
    {
        "id": 8,
        "name": "mẹ"
    },
    {
        "id": 9,
        "name": "anh/chị ruột"
    },
    {
        "id": 10,
        "name": "bạn"
    },
    {
        "id": 11,
        "name": "anh/chị em họ"
    },
    {
        "id": 12,
        "name": "ông"
    },
    {
        "id": 13,
        "name": "bà"
    },
    {
        "id": 14,
        "name": "khác"
    }
]
```

## 11. Service

### Web

#### 11.1 List Service

##### GET : localhost:8080/api/manager-service/service/list

*Output*

```java
[
        {
        "id": 1,
        "serviceName": "dịch vụ đời sống"
        },
        {
        "id": 2,
        "serviceName": "vui chơi giải trí"
        }
        ]
```

#### 11.2 List Sub Service

##### GET : localhost:8080/api/manager-service/service/search

*Output*

```java
{
        "totalElement": 9,
        "data": [
        {
        "subSerivceId": 1,
        "serviceName": "vui chơi giải trí",
        "subServiceName": "BBQ"
        },
        {
        "subSerivceId": 2,
        "serviceName": "vui chơi giải trí",
        "subServiceName": "Hồ bơi"
        },
        {
        "subSerivceId": 3,
        "serviceName": "vui chơi giải trí",
        "subServiceName": "Tennis"
        },
        {
        "subSerivceId": 4,
        "serviceName": "vui chơi giải trí",
        "subServiceName": "Sân bóng"
        },
        {
        "subSerivceId": 5,
        "serviceName": "vui chơi giải trí",
        "subServiceName": "Đặt tiệc"
        }
        ]
        }
```

### 12.Service Request

#### 12.1: Search Service Request

##### GET : localhost:8080/api/manager-service/request-service/search

| Param    | Description | Require| Default Value|Data type| 
| ----------- | ----------- |-----|-----|----|
| name      | name of user     |true| " "  |String |
| serviceName      | name of service      |true| " "  |String |
| statusId      | status of service requets     |true| -1 |String |

*Output*:

```java
{
        "totalElement": 9,
        "data": [
        {
        "subSerivceId": 1,
        "serviceName": "vui chơi giải trí",
        "subServiceName": "BBQ"
        },
        {
        "subSerivceId": 2,
        "serviceName": "vui chơi giải trí",
        "subServiceName": "Hồ bơi"
        },
        {
        "subSerivceId": 3,
        "serviceName": "vui chơi giải trí",
        "subServiceName": "Tennis"
        },
        {
        "subSerivceId": 4,
        "serviceName": "vui chơi giải trí",
        "subServiceName": "Sân bóng"
        },
        {
        "subSerivceId": 5,
        "serviceName": "vui chơi giải trí",
        "subServiceName": "Đặt tiệc"
        }
        ]
        }
```

#### 12.2: Detail Service Request

##### GET : localhost:8080/api/manager-service/request-service/get-one/{id}

*Output*:

```java
{
    "id": 1,
    "name": "f",
    "block": "A1",
    "roomName": "A102",
    "serviceName": "1kg",
    "status": "Chờ xử lí",
    "dayWant": "18/07/2021"
}
```

#### 12.3: Update Status  Service Request

##### POST : localhost:8080/api/landlord/request-service/update/{id}?statusId=3

*Output*:

```java
"Update success
```

### 13. Apartment

#### 13.1 Search Resident By Name RoomNumber And Phone

##### GET: http://localhost:8080/api/admin/apartment/search/resident

| Param    | Description | Require| Default Value|Data type| 
| ----------- | ----------- |-----|-----|----|
| roomNumber      | Room Number of Resident     |true| "  "  |String |

*Output*:

```java
{
    "totalElement": 3,
    "data": [
        {
            "accountId": 41,
            "apartmentId": 1,
            "name": "f",
            "phone": "0123456789",
            "blockName": "A1",
            "roomNumber": "A102",
            "gender": true,
            "dob": "16/04/1999",
            "email": "manhnvhe130112@fpt.edu.vn",
            "homeTown": "Nam Định",
            "currentAddress": "abcde",
            "identifyCard": "163445997",
            "relationShip": "Chủ hộ"
        },
        {
            "accountId": 55,
            "apartmentId": 24,
            "name": "resi",
            "phone": "",
            "blockName": "A1",
            "roomNumber": "A102",
            "gender": true,
            "dob": "16/04/1999",
            "email": null,
            "homeTown": "Nam Định",
            "currentAddress": "Hà Nội",
            "identifyCard": null,
            "relationShip": "anh/chị em họ"
        },
        {
            "accountId": 56,
            "apartmentId": 25,
            "name": "resgi1",
            "phone": "",
            "blockName": "A1",
            "roomNumber": "A102",
            "gender": true,
            "dob": "16/04/1999",
            "email": null,
            "homeTown": "Nam Định",
            "currentAddress": "Hà Nội",
            "identifyCard": null,
            "relationShip": "anh/chị em họ"
        }
    ]
}
```

#### 13.2 Search Apartment By Owner

##### GET:localhost:8080/api/admin/apartment/search

*Output*

```java
{
    "totalElement": 2,
    "data": [
        {
            "apartmentId": 1,
            "accountId": 41,
            "roomNumberId": 1,
            "blockName": "A1",
            "roomName": "A102",
            "ownerName": "f"
        },
        {
            "apartmentId": 3,
            "accountId": 33,
            "roomNumberId": 2,
            "blockName": "A1",
            "roomName": "A103",
            "ownerName": "Nguyễn Thúy Hằng"
        }
    ]
}
```

### 13.3 Export Apartment

#### GET: localhost:8080/api/admin/apartment/export

*Output*

```java
Tòa , Số Phòng , Tên chủ hộ
A1 , A102 , f
A1 , A103 , Nguyễn Thúy Hằng
```

### 13.4:Remove Resident

#### POST: /localhost:8080/api/admin/apartment/disable/owner

| Param    | Description | Require| Default Value|Data type| 
| ----------- | ----------- |-----|-----|----|
| ownerId      | id of resident    |true| " "  |Long |

*Output*

```java
Remove success
```

### 13.5 Update Resident

#### POST: localhost:8080/api/admin/resident/update

*Example:*

```java
{
    "accountId": 34,
    "name": "abcxyz",
    "gender": true,
    "dob": "16/04/1999",
    "phone": "0913831175",
    "email": "abcxtz@gmail.com",
    "identifyCard": "165345 0898 fdgdf",
    "currentAddress": "Nam dinh",
    "homeTown": "Nam Dinh",
    "positionId":"6"
}
```

*Output*

```java
Update success
```

### 13.5 Add Resident Role = Resident

#### POST: localhost:8080/api/admin/apartment/add/resident

*Example:*

```java
{
    "apartmentId": 1,
    "residentRequestList": [
        {
            "name": "resi",
            "identifyCard": "",
            "phone": "",
            "email": "",
            "gender": true,
            "dob": "16/04/1999",
            "positionId": 11,
            "currentAddress": "Nam Dinh",
            "homeTown": "Ha Noi"
        },
        {
            "name": "resgi1",
            "identifyCard": "",
            "phone": "",
            "email": "",
            "gender": true,
            "dob": "16/04/1999",
            "positionId": 11,
            "currentAddress": "Nam Dinh",
            "homeTown": "Ha Noi"
        }
    ]
}
```

*Output*

```java
Add success
```

### 13.6 Add Resident Role = Owner

#### POST: localhost:8080/api/admin/apartment/add/owner

*Example:*

```java
{
    "apartmentId": 1,
    "name": "abcd",
    "gender": true,
    "dob": "16/04/1999",
    "email": "thuyhangnknd@gmail.com",
    "phone": "0123456789",
    "currentAddress": "abcde",
    "identifyCard": "163445997",
    "homeTown": "namDinh"
}
```

*Output*

```java
Add success
```
