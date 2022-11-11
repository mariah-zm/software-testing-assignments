package org.productalerter.model.domain;

public class MaltaParkProduct extends Product {

    @Override
    public void setCategory(String category) {
        this.category = switch (category) {
            case "Cars", "Motorcycles", "Scooters", "Vans & Trucks", "Vehicle Parts", "Quad Bikes" ->
                    CategoryEnum.CAR;
            case "Marine" ->
                    CategoryEnum.BOAT;
            case "Property For Sale" ->
                    CategoryEnum.PROPERTY_FOR_SALE;
            case "Property For Rent", "Short / Holiday Lets", "Long Lets" ->
                    CategoryEnum.PROPERTY_FOR_RENT;
            case "Cameras & Photo", "Computers & Office", "Consumer Electronics", "Networking & Telecom",
                    "Home Appliances", "TV", "Video Games" ->
                    CategoryEnum.ELECTRONICS;
            default ->  // any other category is treated as toys for convenience
                    CategoryEnum.TOYS;
        };
    }

}