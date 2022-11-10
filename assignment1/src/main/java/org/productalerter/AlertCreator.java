package org.productalerter;

import org.productalerter.exception.CategoryNotFoundException;
import org.productalerter.exception.WebScraperException;
import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.model.domain.Product;
import org.productalerter.model.http.AlertResponse;
import org.productalerter.scraper.MaltaParkScraper;
import org.productalerter.service.MarketAlertUmPublisher;

import java.io.IOException;
import java.util.List;

public class AlertCreator {

    private final MaltaParkScraper scraper;
    private final MarketAlertUmPublisher publisher;

    public AlertCreator(MaltaParkScraper scraper, MarketAlertUmPublisher publisher) {
        this.scraper = scraper;
        this.publisher = publisher;
    }

    public String searchForProductsByName(String productName, int numProducts) throws WebScraperException, IOException {
        List<MaltaParkProduct> products = scraper.searchByInput(productName, numProducts);
        for (MaltaParkProduct product: products) {
            AlertResponse response = publisher.publishAlert(product);

        }

        return null;
    }

    public String searchForProductsByCategory(String category, int numProducts) throws WebScraperException, CategoryNotFoundException, IOException {
        List<MaltaParkProduct> products = scraper.searchByCategory(category, numProducts);
        for (MaltaParkProduct product: products) {
            AlertResponse response = publisher.publishAlert(product);

        }

        return null;
    }
}
