package com.ams.building.server.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Long id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private Boolean enabled;
    private String role;
    private String image;
    private String dob;
    private String identityCard;
    private String homeTown;
    private Long roleId;

    @JsonIgnore
    private MultipartFile multipartFile;
}
