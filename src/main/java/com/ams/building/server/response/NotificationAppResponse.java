package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NotificationAppResponse {

    private Long id;
    private String title;
    private String description;
    private String date;
    private String time;
    private Boolean isRead;

}
