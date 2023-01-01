package org.productalerter.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaltaParkProduct {

    private String name;
    private String description;
    private Double priceInEuros;
    private CategoryEnum category;
    private String imageUrl;
    private String url;

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public void setCategory(String category) {
        switch (category) {
            case "Cars":
            case "Motorcycles":
            case "Scooters":
            case "Vans & Trucks":
            case "Vehicle Parts":
            case "Quad Bikes":
                this.category = CategoryEnum.CAR;
                break;
            case "Marine":
                this.category =  CategoryEnum.BOAT;
                break;
            case "Property For Sale":
                this.category = CategoryEnum.PROPERTY_FOR_SALE;
                break;
            case "Property For Rent":
            case "Short / Holiday Lets":
            case "Long Lets":
                this.category = CategoryEnum.PROPERTY_FOR_RENT;
                break;
            case "Cameras & Photo":
            case "Computers & Office":
            case "Consumer Electronics":
            case "Networking & Telecom":
            case "Home Appliances":
            case "TV":
            case "Video Games":
                this.category = CategoryEnum.ELECTRONICS;
                break;
            default: // any other category is treated as toys for convenience
                this.category = CategoryEnum.TOYS;
        };
    }

}
