package org.productalerter.model.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarketAlertUmRequest {

    private int alertType;
    private String heading;
    private String description;
    private String url;
    private String imageUrl;
    private String postedBy;
    private long priceInCents;

}
