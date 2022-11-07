package org.electronicsscraper.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private String name;
    private String description;
    private long priceInCents;
    private String imageUrl;
    private String url;

}
