package org.productalerter.model.domain;

public enum CategoryEnum {

    CAR(1),
    BOAT(2),
    PROPERTY_FOR_RENT(3),
    PROPERTY_FOR_SALE(4),
    TOYS(5),
    ELECTRONICS(6);

    private final int value;

    CategoryEnum(int value) {
        this.value = value;
    }

    public static CategoryEnum getByValue(int value) {
        for(CategoryEnum e: CategoryEnum.values()) {
            if(e.value == value) {
                return e;
            }
        }
        return null; // not found
    }

    public int getValue() {
        return value;
    }

}
