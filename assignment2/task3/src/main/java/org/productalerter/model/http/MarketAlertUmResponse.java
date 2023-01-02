package org.productalerter.model.http;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class MarketAlertUmResponse {

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
