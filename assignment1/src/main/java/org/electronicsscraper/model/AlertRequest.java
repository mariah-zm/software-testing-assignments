package org.electronicsscraper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertRequest extends Alert {

    public AlertRequest(Product product, int alertType, String userId) {
        this.alertType = alertType;
        this.heading = product.getName();
        this.description = product.getDescription();
        this.url = product.getUrl();
        this.imageUrl = product.getImageUrl();
        this.postedBy = userId;
        this.priceInCents = product.getPriceInEuros() * 100;
    }

}
