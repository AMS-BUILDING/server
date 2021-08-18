package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BillingApartmentContentResponse {

    private Long id;
    private String email;
    private String content;

}
