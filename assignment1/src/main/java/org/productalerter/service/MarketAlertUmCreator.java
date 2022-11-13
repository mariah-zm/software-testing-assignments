package org.productalerter.service;

import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.model.http.CreatorResponse;
import org.productalerter.scraper.MaltaParkScraper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarketAlertUmCreator {

    private final MaltaParkScraper scraper;
    private final MarketAlertUmPublisher publisher;

    public MarketAlertUmCreator(MaltaParkScraper scraper, MarketAlertUmPublisher publisher) {
        this.scraper = scraper;
        this.publisher = publisher;
    }

    public CreatorResponse searchForProductsByName(String productName, int numProducts) {
        int alertsCreated = 0;
        List<String> errors = new ArrayList<>();

        try {
            List<MaltaParkProduct> products = scraper.searchByInput(productName, numProducts);

            for (MaltaParkProduct product: products) {
                try {
                    publisher.publishAlert(product);
                    ++alertsCreated;
                } catch (Exception ex) {
                    errors.add(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            errors.add(ex.getMessage());
            return new CreatorResponse(numProducts, alertsCreated, errors);
        }

        return new CreatorResponse(numProducts, alertsCreated, errors);
    }

    public CreatorResponse searchForProductsByCategory(String category, int numProducts) {
        int alertsCreated = 0;
        List<String> errors = new ArrayList<>();

        try {
            List<MaltaParkProduct> products = scraper.searchByCategory(category, numProducts);

            for (MaltaParkProduct product: products) {
                try {
                    publisher.publishAlert(product);
                    ++alertsCreated;
                } catch (Exception ex) {
                    errors.add(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            errors.add(ex.getMessage());
            return new CreatorResponse(numProducts, alertsCreated, errors);
        }

        return new CreatorResponse(numProducts, alertsCreated, errors);
    }

    public CreatorResponse deleteAlerts() {
        try {
            String message = publisher.deleteAllAlerts();
            int numAlertsDeleted = Integer.parseInt(message.replaceAll("[^0-9]", ""));
            return new CreatorResponse(numAlertsDeleted, numAlertsDeleted, null);
        } catch (Exception ex) {
            return new CreatorResponse(0, 0, Collections.singletonList(ex.getMessage()));
        }
    }
}
