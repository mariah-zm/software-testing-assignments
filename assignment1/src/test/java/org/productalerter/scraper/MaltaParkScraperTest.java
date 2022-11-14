package org.productalerter.scraper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.productalerter.exception.WebScraperException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MaltaParkScraperTest {

    @Mock
    WebDriver driver;

    @InjectMocks
    MaltaParkScraper scraper;

    @Test
    void testSearchByInput_WebScraperException() {
        // Arrange
        when(driver.navigate()).thenThrow(TimeoutException.class);

        // Act & Assert
        Exception exception = assertThrows(WebScraperException.class, () -> scraper.searchByInput("", 3));

        // Assert
        assertEquals("Could not retrieve products", exception.getMessage());
        assertEquals(TimeoutException.class, exception.getCause().getClass());
    }


    @Test
    void testSearchByCategory_WebScraperException() {
        // Arrange
        when(driver.navigate()).thenThrow(TimeoutException.class);

        // Act & Assert
        Exception exception = assertThrows(WebScraperException.class, () -> scraper.searchByCategory("", 3));

        // Assert
        assertEquals("Could not retrieve products", exception.getMessage());
        assertEquals(TimeoutException.class, exception.getCause().getClass());
    }

}