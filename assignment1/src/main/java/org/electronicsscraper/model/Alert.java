package org.electronicsscraper.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Alert {

    protected int alertType;
    protected String heading;
    protected String description;
    protected String url;
    protected String imageUrl;
    protected String postedBy;
    protected long priceInCents;

}
