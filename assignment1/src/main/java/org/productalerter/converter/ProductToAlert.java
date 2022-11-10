package org.productalerter.converter;

import org.productalerter.model.domain.Product;
import org.productalerter.model.http.AlertRequest;

public class ProductToAlert {

    private ProductToAlert() {}

    public static AlertRequest convert(Product product, String userId) {
        AlertRequest request = new AlertRequest();
        request.setAlertType(product.getCategory().getValue());
        request.setHeading(product.getName());
        request.setDescription(product.getDescription());
        request.setUrl(product.getUrl());
        request.setImageUrl(product.getImageUrl());
        request.setPostedBy(userId);
        request.setPriceInCents(product.getPriceInEuros().longValue() * 100L);
        return request;
    }
}
