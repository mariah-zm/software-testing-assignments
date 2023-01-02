package org.productalerter.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private String name;
    private String description;
    private Double priceInEuros;
    private CategoryEnum category;
    private String imageUrl;
    private String url;

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

}
