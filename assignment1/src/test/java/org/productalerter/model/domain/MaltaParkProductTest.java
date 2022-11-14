package org.productalerter.model.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaltaParkProductTest {

    MaltaParkProduct product = new MaltaParkProduct();

    @Test
    void testSetCategoryCar() {
        // Arrange
        product.setCategory("Vans & Trucks");
        // Act & Assert
        assertEquals(CategoryEnum.CAR, product.getCategory());
    }

    @Test
    void testSetCategoryBoat() {
        // Arrange
        product.setCategory("Marine");
        // Act & Assert
        assertEquals(CategoryEnum.BOAT, product.getCategory());
    }

    @Test
    void testSetCategoryPropertyRent() {
        product.setCategory("Short / Holiday Lets");
        // Act & Assert
        assertEquals(CategoryEnum.PROPERTY_FOR_RENT, product.getCategory());
    }

    @Test
    void testSetCategoryPropertySale() {
        // Arrange
        product.setCategory("Property For Sale");
        // Act & Assert
        assertEquals(CategoryEnum.PROPERTY_FOR_SALE, product.getCategory());
    }

    @Test
    void testSetCategoryToys() {
        // Arrange
        product.setCategory("Some other category");
        // Act & Assert
        assertEquals(CategoryEnum.TOYS, product.getCategory());
    }

    @Test
    void testSetCategoryElectronics() {
        // Arrange
        product.setCategory("Video Games");
        // Act & Assert
        assertEquals(CategoryEnum.ELECTRONICS, product.getCategory());
    }

}