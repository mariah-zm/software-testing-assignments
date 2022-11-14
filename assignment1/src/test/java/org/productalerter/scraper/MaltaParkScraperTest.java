package org.productalerter.scraper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.productalerter.exception.PublisherException;
import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.service.HttpService;
import org.productalerter.service.MarketAlertUmPublisher;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaltaParkScraperTest {

    String userId = "b9ed2dbc-141a-4395-921d-ee8779610e1f";

    MaltaParkScraper scraper;
    MarketAlertUmPublisher publisher;

    @BeforeEach
    public void setup() {
        scraper = new MaltaParkScraper();
        publisher = new MarketAlertUmPublisher(new HttpService(), userId);
    }

    @AfterEach
    public void teardown() {
        scraper.delete();
        scraper = null;
        publisher = null;
    }

    @Test
    public void searchByInput() throws org.productalerter.exception.WebScraperException, PublisherException, IOException {
        List<MaltaParkProduct> products = scraper.searchByInput("Fiat Punto", 3);
        for (var prod: products) {
            publisher.publishAlert(prod);
        }
    }

}