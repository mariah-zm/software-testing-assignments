package org.productalerter.model.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaltaParkProductTest {

    MaltaParkProduct product = new MaltaParkProduct();

    @Test
    void testSetCategoryCar() {
        product.setCategory("Vans & Trucks");
        assertEquals(CategoryEnum.CAR, product.getCategory());
    }

    @Test
    void testSetCategoryBoat() {
        product.setCategory("Marine");
        assertEquals(CategoryEnum.BOAT, product.getCategory());
    }

    @Test
    void testSetCategoryPropertyRent() {
        product.setCategory("Short / Holiday Lets");
        assertEquals(CategoryEnum.PROPERTY_FOR_RENT, product.getCategory());
    }

    @Test
    void testSetCategoryPropertySale() {
        product.setCategory("Property For Sale");
        assertEquals(CategoryEnum.PROPERTY_FOR_SALE, product.getCategory());
    }

    @Test
    void testSetCategoryToys() {
        product.setCategory("Some other category");
        assertEquals(CategoryEnum.TOYS, product.getCategory());
    }

    @Test
    void testSetCategoryElectronics() {
        product.setCategory("Video Games");
        assertEquals(CategoryEnum.ELECTRONICS, product.getCategory());
    }

}