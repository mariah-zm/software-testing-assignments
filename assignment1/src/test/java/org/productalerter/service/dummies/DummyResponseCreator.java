package org.productalerter.service.dummies;

import org.productalerter.model.http.MarketAlertUmResponse;

import java.io.InputStream;
import java.time.LocalDateTime;

public class DummyResponseCreator {

    public static MarketAlertUmResponse getDummyResponse() {
        MarketAlertUmResponse response = new MarketAlertUmResponse();
        response.setId("123");
        response.setHeading("Name");
        response.setDescription("Description");
        response.setPostedBy("user-123");
        response.setImageURL("image-url");
        response.setUrl("url");
        response.setPriceInCents(2550);
        response.setPostDate(LocalDateTime.now());

        return response;
    }


}
