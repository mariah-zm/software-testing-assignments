package org.electronicsscraper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.electronicsscraper.model.AlertRequest;
import org.electronicsscraper.model.Product;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public class MarketAlertUmHttpService {

    private final String URL = "https://api.marketalertum.com/";
    private final String userId;

    public MarketAlertUmHttpService(String userId) {
        this.userId = userId;
    }

    public void publishAlert(Product product, int productType) throws IOException {
        // Preparing request object
        AlertRequest alertReq = new AlertRequest(product, productType, this.userId);
        // Getting json body
        ObjectWriter ow = new ObjectMapper().writer();
        String jsonBody = ow.writeValueAsString(alertReq);
        StringEntity body = new StringEntity(jsonBody);

        // Building request
        String reqUrl = URL + "Alert";
        HttpPost request = new HttpPost(reqUrl);
        request.setEntity(body);
        request.setHeader("Content-type", "application/json");

        // Executing request
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);
    }

    public void deleteAllAlerts() throws IOException {
        String reqUrl = URL + "Alert?userId=" + this.userId;
        HttpDelete request = new HttpDelete(reqUrl);

        // Executing request
        HttpClient httpClient  = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);
    }


}
