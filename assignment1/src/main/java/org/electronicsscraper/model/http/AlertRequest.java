package org.electronicsscraper.model.http;

import lombok.Getter;
import lombok.Setter;
import org.electronicsscraper.model.domain.Product;

@Getter
@Setter
public class AlertRequest {

    private int alertType;
    private String heading;
    private String description;
    private String url;
    private String imageUrl;
    private String postedBy;
    private long priceInCents;

    public AlertRequest(Product product, String userId) {
        this.alertType = product.getCategory().getValue();
        this.heading = product.getName();
        this.description = product.getDescription();
        this.url = product.getUrl();
        this.imageUrl = product.getImageUrl();
        this.postedBy = userId;
        this.priceInCents = product.getPriceInEuros().longValue() * 100L;
    }

}
