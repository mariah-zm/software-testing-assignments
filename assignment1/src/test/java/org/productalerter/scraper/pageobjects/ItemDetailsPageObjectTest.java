package org.productalerter.scraper.pageobjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemDetailsPageObjectTest {

    @Mock
    WebDriver driver;

    @InjectMocks
    ItemDetailsPageObject pageObject;

    @Test
    void getCategory() {
        // Arrange
        WebElement element = mock(WebElement.class);
        when(element.getText()).thenReturn("Category: Cars");
        when(driver.findElements(By.xpath("//div[@class='ui list fixed-label item-details']/span[@class='item']")))
                .thenReturn(Collections.singletonList(element));
        when(driver.findElements(By.xpath("//div[@class='ui list fixed-label item-details']/div[@class='item']")))
                .thenReturn(new ArrayList<>());

        // Act
        String category = pageObject.getCategory();

        // Assert
        assertEquals("Cars", category);
    }

    @Test
    void getCategory_Other() {
        // Arrange
        when(driver.findElements(any())).thenReturn(new ArrayList<>());

        // Act
        String category = pageObject.getCategory();

        // Assert
        assertEquals("Other", category);
    }
}