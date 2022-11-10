package org.productalerter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.productalerter.converter.ProductToAlert;
import org.productalerter.model.domain.Product;
import org.productalerter.model.http.AlertRequest;
import org.productalerter.model.http.AlertResponse;

import java.io.IOException;

public class MarketAlertUmPublisher {

    private final String url = "https://api.marketalertum.com/";
    private final String userId;
    private final HttpService httpService;

    public MarketAlertUmPublisher(HttpService httpService, String userId) {
        this.httpService = httpService;
        this.userId = userId;
    }

    public AlertResponse publishAlert(Product product) throws IOException {
        // Preparing request object
        AlertRequest alertReq = ProductToAlert.convert(product, userId);
        // Getting json body
        ObjectWriter ow = new ObjectMapper().writer();
        String jsonBody = ow.writeValueAsString(alertReq);
        // Forming request url
        String reqUrl = this.url + "Alert";

        // Using HttpService to do POST request
        HttpResponse response = this.httpService.doPost(reqUrl, jsonBody);

        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        return mapper.readValue(response.getEntity().getContent(), AlertResponse.class);
    }

    public String deleteAllAlerts() throws IOException {
        // Forming request url
        String reqUrl = this.url + "Alert?userId=" + this.userId;
        // Using HttpService to do DELETE request
        HttpResponse response = this.httpService.doDelete(reqUrl);
        return EntityUtils.toString(response.getEntity());
    }

}
