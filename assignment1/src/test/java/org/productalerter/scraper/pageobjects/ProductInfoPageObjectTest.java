package org.productalerter.scraper.pageobjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.productalerter.model.domain.CategoryEnum;
import org.productalerter.model.domain.MaltaParkProduct;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductInfoPageObjectTest {

    @Mock
    WebDriver driver;

    @InjectMocks
    ProductInfoPageObject pageObject;

    @Test
    void getProductInfo() {
        // Arrange
        String dummy = "123";

        WebElement element = mock(WebElement.class);
        when(element.getText()).thenReturn(dummy);
        when(element.getAttribute(any())).thenReturn(dummy);

        when(driver.findElement(any())).thenReturn(element);
        when(driver.getCurrentUrl()).thenReturn(dummy);

        // Required for category
        when(driver.findElements(By.xpath("//div[@class='ui list fixed-label item-details']/span[@class='item']")))
                .thenReturn(Collections.singletonList(element));
        when(driver.findElements(By.xpath("//div[@class='ui list fixed-label item-details']/div[@class='item']")))
                .thenReturn(new ArrayList<>());

        // Act
        MaltaParkProduct product = pageObject.getProductInfo();

        // Assert
        assertEquals(dummy, product.getName());
        assertEquals(dummy, product.getDescription());
        assertEquals(CategoryEnum.TOYS, product.getCategory()); // others are treated as toys
        assertEquals(Double.parseDouble(dummy), product.getPriceInEuros());
        assertEquals(dummy, product.getImageUrl());
        assertEquals(dummy, product.getUrl());
    }
}