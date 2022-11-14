package org.productalerter.converter;

import org.junit.jupiter.api.Test;
import org.productalerter.model.domain.CategoryEnum;
import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.model.http.MarketAlertUmRequest;

import static org.junit.jupiter.api.Assertions.*;

class ProductToAlertTest {

    @Test
    void testConvert() {
        // Arrange
        String userId = "user-123";
        MaltaParkProduct product = this.getProduct();

        // Act
        MarketAlertUmRequest request = ProductToAlert.convert(product, userId);

        // Assert
        assertEquals(CategoryEnum.CAR.getValue(), request.getAlertType());
        assertEquals("Name", request.getHeading());
        assertEquals("Description", request.getDescription());
        assertEquals(2550, request.getPriceInCents());
        assertEquals("image-url", request.getImageUrl());
        assertEquals("url", request.getUrl());
        assertEquals(userId, request.getPostedBy());
    }

    private MaltaParkProduct getProduct() {
        MaltaParkProduct product = new MaltaParkProduct();
        product.setCategory(CategoryEnum.CAR);
        product.setName("Name");
        product.setDescription("Description");
        product.setPriceInEuros(25.50);
        product.setImageUrl("image-url");
        product.setUrl("url");

        return product;
    }
}