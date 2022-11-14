package org.productalerter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.productalerter.exception.PublisherException;
import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.model.http.MarketAlertUmResponse;
import org.productalerter.service.dummies.DummyProductCreator;
import org.productalerter.service.dummies.DummyResponseCreator;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarketAlertUmPublisherTest {

    @Mock
    HttpService httpService;

    @InjectMocks
    MarketAlertUmPublisher publisher;

    @Test
    void testPublishAlert() throws IOException, PublisherException {
        // Arrange
        MaltaParkProduct product = DummyProductCreator.getDummyProduct();
        MarketAlertUmResponse alertResponse = DummyResponseCreator.getDummyResponse();

        // Mocking HttpResponse returned from mocked HttpService
        HttpResponse response = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(201);
        when(response.getStatusLine()).thenReturn(statusLine);

        ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).writer();
        String jsonResponse = ow.writeValueAsString(alertResponse);
        StringEntity entity = new StringEntity(jsonResponse);
        when(response.getEntity()).thenReturn(entity);

        when(httpService.doPost(any(), any())).thenReturn(response);

        // Act & Assert
        assertEquals(alertResponse, publisher.publishAlert(product));
    }

    @Test
    void testPublishAlert_Non201ResponseCode() throws IOException {
        // Arrange
        MaltaParkProduct product = DummyProductCreator.getDummyProduct();

        // Mocking HttpResponse returned from mocked HttpService
        HttpResponse response = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(400);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(httpService.doPost(any(), any())).thenReturn(response);

        // Act & Assert
        Exception exception = assertThrows(PublisherException.class, () -> publisher.publishAlert(product));

        // Assert
        assertEquals("Response code is 400", exception.getMessage());
    }

    @Test
    void testPublishAlert_PostThrowsException() throws IOException {
        // Arrange
        MaltaParkProduct product = DummyProductCreator.getDummyProduct();
        when(httpService.doPost(any(), any())).thenThrow(IOException.class);

        // Act & Assert
        Exception exception = assertThrows(PublisherException.class, () -> publisher.publishAlert(product));

        // Assert
        assertTrue(exception.getMessage().contains("Could not do POST request"));
        assertEquals(IOException.class, exception.getCause().getClass());
    }

    @Test
    void testDeleteAllAlert() throws IOException, PublisherException {
        // Arrange
        String responseMsg = "Deleted 10 alerts";

        // Mocking HttpResponse returned from mocked HttpService
        HttpResponse response = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);

        StringEntity entity = new StringEntity(responseMsg);
        when(response.getEntity()).thenReturn(entity);

        when(httpService.doDelete(any())).thenReturn(response);

        // Act & Asset
        assertEquals(responseMsg, publisher.deleteAllAlerts());
    }

    @Test
    void testDeleteAllAlerts_Non200ResponseCode() throws IOException {
        // Arrange
        // Mocking HttpResponse returned from mocked HttpService
        HttpResponse response = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(400);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(httpService.doDelete(any())).thenReturn(response);

        // Act & Assert
        Exception exception = assertThrows(PublisherException.class, () -> publisher.deleteAllAlerts());

        // Assert
        assertEquals("Response code is 400", exception.getMessage());
    }

    @Test
    void testDeleteAllAlerts_DeleteThrowsException() throws IOException {
        // Arrange
        when(httpService.doDelete(any())).thenThrow(IOException.class);

        // Act & Assert
        Exception exception = assertThrows(PublisherException.class, () -> publisher.deleteAllAlerts());

        // Assert
        assertTrue(exception.getMessage().contains("Could not do DELETE request"));
        assertEquals(IOException.class, exception.getCause().getClass());
    }

}