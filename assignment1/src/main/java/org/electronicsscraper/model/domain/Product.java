package org.electronicsscraper.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Product {

    protected String name;
    protected String description;
    protected Double priceInEuros;
    protected CategoryEnum category;
    protected String imageUrl;
    protected String url;

    public abstract void setCategory(String category);

}
