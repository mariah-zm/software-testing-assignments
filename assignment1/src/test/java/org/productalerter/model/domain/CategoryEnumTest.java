package org.productalerter.model.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryEnumTest {

    @Test
    void testGetByValue() {
        // Act & Assert
        assertEquals(CategoryEnum.CAR, CategoryEnum.getByValue(CategoryEnum.CAR.getValue()));
    }

    @Test
    void testGetByValueReturnNull() {
        // Act & Assert
        assertNull(CategoryEnum.getByValue(-1));
    }

    @Test
    void testGetValue() {
        // Act & Assert
        assertEquals(1, CategoryEnum.CAR.getValue());
    }
}