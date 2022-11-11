package org.productalerter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.productalerter.converter.ProductToAlert;
import org.productalerter.exception.PublisherException;
import org.productalerter.model.domain.Product;
import org.productalerter.model.http.MarketAlertUmRequest;
import org.productalerter.model.http.MarketAlertUmResponse;

import java.io.IOException;

public class MarketAlertUmPublisher {

    private final String url = "https://api.marketalertum.com/";
    private final String userId;
    private final HttpService httpService;

    public MarketAlertUmPublisher(HttpService httpService, String userId) {
        this.httpService = httpService;
        this.userId = userId;
    }

    public MarketAlertUmResponse publishAlert(Product product) throws IOException, PublisherException {
        // Preparing request object
        MarketAlertUmRequest alertReq = ProductToAlert.convert(product, userId);
        // Getting json body
        ObjectWriter ow = new ObjectMapper().writer();
        String jsonBody = ow.writeValueAsString(alertReq);
        // Forming request url
        String reqUrl = this.url + "Alert";

        // Using HttpService to do POST request
        HttpResponse response = this.httpService.doPost(reqUrl, jsonBody);

        if (response.getStatusLine().getStatusCode() == 201) {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            return mapper.readValue(response.getEntity().getContent(), MarketAlertUmResponse.class);
        }

        throw new PublisherException("Return code is not 201: " + EntityUtils.toString(response.getEntity()));
    }

    public String deleteAllAlerts() throws IOException, PublisherException {
        // Forming request url
        String reqUrl = this.url + "Alert?userId=" + this.userId;
        // Using HttpService to do DELETE request
        HttpResponse response = this.httpService.doDelete(reqUrl);

        if (response.getStatusLine().getStatusCode() == 200) {
            return EntityUtils.toString(response.getEntity());
        }

        throw new PublisherException("Return code is not 200: " + EntityUtils.toString(response.getEntity()));
    }

}
