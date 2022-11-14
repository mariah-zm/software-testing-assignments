package org.productalerter.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.productalerter.exception.CategoryNotFoundException;
import org.productalerter.exception.PublisherException;
import org.productalerter.exception.WebScraperException;
import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.model.http.CreatorResponse;
import org.productalerter.model.http.MarketAlertUmResponse;
import org.productalerter.scraper.MaltaParkScraper;
import org.productalerter.service.dummies.DummyProductCreator;
import org.productalerter.service.dummies.DummyResponseCreator;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarketAlertUmCreatorTest {

    @Mock
    MaltaParkScraper scraper;

    @Mock
    MarketAlertUmPublisher publisher;

    @InjectMocks
    MarketAlertUmCreator creator;

    @Test
    void testSearchForProductsByName() throws WebScraperException, PublisherException, IOException {
        // Arrange
        int numProducts = 3;
        List<MaltaParkProduct> products = DummyProductCreator.getDummyProducts(numProducts);
        MarketAlertUmResponse publisherResponse = DummyResponseCreator.getDummyResponse();

        when(scraper.searchByInput(anyString(), anyInt())).thenReturn(products);
        when(publisher.publishAlert(any())).thenReturn(publisherResponse);

        // Act
        CreatorResponse response = creator.searchForProductsByName("", numProducts);

        // Assert
        assertEquals(numProducts, response.getNumProductsRequested());
        assertEquals(numProducts, response.getAlertsAffected());
        assertTrue(response.getErrorMessages().isEmpty());
    }

    @Test
    void testSearchForProductsByName_PublisherThrowsExceptionAlways() throws WebScraperException, PublisherException, IOException {
        // Arrange
        int numProducts = 3;
        List<MaltaParkProduct> products = DummyProductCreator.getDummyProducts(numProducts);

        when(scraper.searchByInput(anyString(), anyInt())).thenReturn(products);
        when(publisher.publishAlert(any())).thenThrow(PublisherException.class);

        // Act
        CreatorResponse response = creator.searchForProductsByName("", numProducts);

        // Assert
        assertEquals(numProducts, response.getNumProductsRequested());
        assertEquals(0, response.getAlertsAffected());
        assertEquals(numProducts, response.getErrorMessages().size());
    }

    @Test
    void testSearchForProductsByName_PublisherThrowsExceptionOnce() throws WebScraperException, PublisherException, IOException {
        // Arrange
        int numProducts = 3;
        MarketAlertUmResponse publisherResponse = DummyResponseCreator.getDummyResponse();
        List<MaltaParkProduct> products = DummyProductCreator.getDummyProducts(numProducts);

        when(scraper.searchByInput(anyString(), anyInt())).thenReturn(products);
        when(publisher.publishAlert(products.get(0))).thenReturn(publisherResponse);
        when(publisher.publishAlert(products.get(1))).thenReturn(publisherResponse);
        when(publisher.publishAlert(products.get(2))).thenThrow(PublisherException.class);

        // Act
        CreatorResponse response = creator.searchForProductsByName("", numProducts);

        // Assert
        assertEquals(numProducts, response.getNumProductsRequested());
        assertEquals(numProducts-1, response.getAlertsAffected());
        assertEquals(1, response.getErrorMessages().size());
    }

    @Test
    void testSearchForProductsByName_ScraperThrowsException() throws WebScraperException {
        // Arrange
        int numProducts = 3;

        when(scraper.searchByInput(anyString(), anyInt())).thenThrow(WebScraperException.class);

        // Act
        CreatorResponse response = creator.searchForProductsByName("", numProducts);

        // Assert
        assertEquals(numProducts, response.getNumProductsRequested());
        assertEquals(0, response.getAlertsAffected());
        assertFalse(response.getErrorMessages().isEmpty());
    }

    @Test
    void testSearchForProductsByCategory() throws WebScraperException, CategoryNotFoundException, PublisherException, IOException {
        // Arrange
        int numProducts = 3;
        List<MaltaParkProduct> products = DummyProductCreator.getDummyProducts(numProducts);
        MarketAlertUmResponse publisherResponse = DummyResponseCreator.getDummyResponse();

        when(scraper.searchByCategory(anyString(), anyInt())).thenReturn(products);
        when(publisher.publishAlert(any())).thenReturn(publisherResponse);

        // Act
        CreatorResponse response = creator.searchForProductsByCategory("", numProducts);

        // Assert
        assertEquals(numProducts, response.getNumProductsRequested());
        assertEquals(numProducts, response.getAlertsAffected());
        assertTrue(response.getErrorMessages().isEmpty());
    }

    @Test
    void testSearchForProductsByCategory_PublisherThrowsExceptionAlways() throws WebScraperException, CategoryNotFoundException, PublisherException, IOException {
        // Arrange
        int numProducts = 3;
        List<MaltaParkProduct> products = DummyProductCreator.getDummyProducts(numProducts);

        when(scraper.searchByCategory(anyString(), anyInt())).thenReturn(products);
        when(publisher.publishAlert(any())).thenThrow(PublisherException.class);

        // Act
        CreatorResponse response = creator.searchForProductsByCategory("", numProducts);

        // Assert
        assertEquals(numProducts, response.getNumProductsRequested());
        assertEquals(0, response.getAlertsAffected());
        assertEquals(numProducts, response.getErrorMessages().size());
    }

    @Test
    void testSearchForProductsByCategory_PublisherThrowsExceptionOnce() throws WebScraperException, PublisherException, IOException, CategoryNotFoundException {
        // Arrange
        int numProducts = 3;
        MarketAlertUmResponse publisherResponse = DummyResponseCreator.getDummyResponse();
        List<MaltaParkProduct> products = DummyProductCreator.getDummyProducts(numProducts);

        when(scraper.searchByCategory(anyString(), anyInt())).thenReturn(products);
        when(publisher.publishAlert(products.get(0))).thenReturn(publisherResponse);
        when(publisher.publishAlert(products.get(1))).thenReturn(publisherResponse);
        when(publisher.publishAlert(products.get(2))).thenThrow(PublisherException.class);

        // Act
        CreatorResponse response = creator.searchForProductsByCategory("", numProducts);

        // Assert
        assertEquals(numProducts, response.getNumProductsRequested());
        assertEquals(numProducts-1, response.getAlertsAffected());
        assertEquals(1, response.getErrorMessages().size());
    }

    @Test
    void testSearchForProductsByCategory_ScraperThrowsException() throws WebScraperException, CategoryNotFoundException {
        // Arrange
        int numProducts = 3;

        when(scraper.searchByCategory(anyString(), anyInt())).thenThrow(CategoryNotFoundException.class);

        // Act
        CreatorResponse response = creator.searchForProductsByCategory("", numProducts);

        // Assert
        assertEquals(numProducts, response.getNumProductsRequested());
        assertEquals(0, response.getAlertsAffected());
        assertFalse(response.getErrorMessages().isEmpty());
    }

    @Test
    void testDeleteAlerts() throws PublisherException, IOException {
        // Arrange
        when(publisher.deleteAllAlerts()).thenReturn("Deleted 10 alerts");

        // Act
        CreatorResponse response = creator.deleteAlerts();

        // Assert
        assertEquals(10, response.getNumProductsRequested());
        assertEquals(10, response.getAlertsAffected());
        assertNull(response.getErrorMessages());
    }

    @Test
    void testDeleteAlerts_PublisherThrowsException() throws PublisherException, IOException {
        // Arrange
        when(publisher.deleteAllAlerts()).thenThrow(PublisherException.class);

        // Act
        CreatorResponse response = creator.deleteAlerts();

        // Assert
        assertEquals(0, response.getNumProductsRequested());
        assertEquals(0, response.getAlertsAffected());
        assertFalse(response.getErrorMessages().isEmpty());
    }
}