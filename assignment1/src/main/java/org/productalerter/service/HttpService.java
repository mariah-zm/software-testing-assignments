package org.productalerter.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class HttpService {

    public HttpResponse doPost(String url, String body) throws IOException {
        // Building request
        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity(body));
        request.setHeader("Content-type", "application/json");

        // Executing request
        HttpClient httpClient = HttpClientBuilder.create().build();
        return httpClient.execute(request);
    }

    public HttpResponse doDelete(String url) throws IOException {
        HttpDelete request = new HttpDelete(url);

        // Executing request
        HttpClient httpClient  = HttpClientBuilder.create().build();
        return httpClient.execute(request);
    }

}
