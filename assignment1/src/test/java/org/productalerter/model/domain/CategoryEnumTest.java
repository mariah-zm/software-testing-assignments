package org.productalerter.model.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryEnumTest {

    @Test
    void testGetByValue() {
        assertEquals(CategoryEnum.CAR, CategoryEnum.getByValue(CategoryEnum.CAR.getValue()));
    }

    @Test
    void testGetByValueReturnNull() {
        assertNull(CategoryEnum.getByValue(-1));
    }

    @Test
    void testGetValue() {
        assertEquals(1, CategoryEnum.CAR.getValue());
    }
}