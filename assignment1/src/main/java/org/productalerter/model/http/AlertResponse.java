package org.productalerter.model.http;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlertResponse {

    private String id;
    private int alertType;
    private String heading;
    private String description;
    private String url;
    private String imageURL;
    private String postedBy;
    private long priceInCents;
    private LocalDateTime postDate;

}
