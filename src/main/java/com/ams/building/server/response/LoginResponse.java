package com.ams.building.server.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
public class LoginResponse {

    private Long id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private Boolean enabled;
    private String role;
    private String image;
    private String dob;
    private String identifyCard;
    private String homeTown;
    private Long roleId;
    private String currentAddress;

    @JsonIgnore
    private MultipartFile multipartFile;

}
