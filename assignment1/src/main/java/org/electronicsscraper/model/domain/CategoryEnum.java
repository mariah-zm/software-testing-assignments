package org.electronicsscraper.model.domain;

public enum CategoryEnum {

    CAR(1),
    BOAT(2),
    PROPERTY_FOR_SALE(3),
    PROPERTY_FOR_RENT(4),
    TOYS(5),
    ELECTRONICS(6);

    private final int value;

    CategoryEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}