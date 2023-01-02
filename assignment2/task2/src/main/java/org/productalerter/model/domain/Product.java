package org.productalerter.model.domain;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public Double getPriceInEuros() {
        return priceInEuros;
    }

    public void setPriceInEuros(Double priceInEuros) {
        this.priceInEuros = priceInEuros;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
