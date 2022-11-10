package org.electronicsscraper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.electronicsscraper.model.domain.Product;
import org.electronicsscraper.model.http.AlertRequest;
import org.electronicsscraper.model.http.AlertResponse;

import java.io.IOException;

public class MarketAlertUmHttpService {

    private final String url = "https://api.marketalertum.com/";
    private final String userId;

    public MarketAlertUmHttpService(String userId) {
        this.userId = userId;
    }

    public AlertResponse publishAlert(Product product) throws IOException {
        // Preparing request object
        AlertRequest alertReq = new AlertRequest(product, userId);
        // Getting json body
        ObjectWriter ow = new ObjectMapper().writer();
        String jsonBody = ow.writeValueAsString(alertReq);
        StringEntity body = new StringEntity(jsonBody);

        // Building request
        String reqUrl = url + "Alert";
        HttpPost request = new HttpPost(reqUrl);
        request.setEntity(body);
        request.setHeader("Content-type", "application/json");

        // Executing request
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        return mapper.readValue(response.getEntity().getContent(), AlertResponse.class);
    }

    public String deleteAllAlerts() throws IOException {
        String reqUrl = url + "Alert?userId=" + userId;
        HttpDelete request = new HttpDelete(reqUrl);

        // Executing request
        HttpClient httpClient  = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }


}
