package org.productalerter.converter;

import org.productalerter.model.domain.Product;
import org.productalerter.model.http.MarketAlertUmRequest;

public class ProductToAlert {

    private ProductToAlert() {}

    public static MarketAlertUmRequest convert(Product product, String userId) {
        MarketAlertUmRequest request = new MarketAlertUmRequest();
        request.setAlertType(product.getCategory().getValue());
        request.setHeading(product.getName());
        request.setDescription(product.getDescription());
        request.setUrl(product.getUrl());
        request.setImageUrl(product.getImageUrl());
        request.setPostedBy(userId);
        request.setPriceInCents((long) (product.getPriceInEuros() * 100));
        return request;
    }
}
