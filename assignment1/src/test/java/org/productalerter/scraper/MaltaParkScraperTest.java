package org.productalerter.scraper;

import org.productalerter.exception.CategoryNotFoundException;
import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.model.domain.Product;
import org.productalerter.service.MarketAlertUmPublisher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class MaltaParkScraperTest {

    final String USER_ID = "b9ed2dbc-141a-4395-921d-ee8779610e1f";

    MaltaParkScraper scraper;
    MarketAlertUmPublisher httpService;

    @BeforeEach
    public void setup() {
        this.scraper = new MaltaParkScraper();
        this.httpService = new MarketAlertUmPublisher(USER_ID);
    }

    @AfterEach
    public void teardown() {
        this.scraper.delete();
    }

    @Test
    public void testSearchByInput() throws InterruptedException, IOException, CategoryNotFoundException {
        List<MaltaParkProduct> products = this.scraper.searchByCategory("cars & parts", 5);

        for (Product product: products) {
            this.httpService.publishAlert(product);
        }
    }

}