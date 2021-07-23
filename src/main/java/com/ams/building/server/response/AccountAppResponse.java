package com.ams.building.server.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
public class AccountAppResponse {

    private Long id;
    private String name;
    private String roomNumber;
    private String dob;
    private String identifyCard;
    private String email;
    private String phoneNumber;
    private String currentAddress;
    private String imageAvatar;
    private String password;

    @JsonIgnore
    private MultipartFile multipartFile;

}
