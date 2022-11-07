package org.electronicsscraper.service;

import org.electronicsscraper.model.Product;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MarketAlertUmHttpServiceTest {

    final String USER_ID = "b9ed2dbc-141a-4395-921d-ee8779610e1f";
    MarketAlertUmHttpService httpService = new MarketAlertUmHttpService(USER_ID);

    @Test
    public void testPublishAlert() throws IOException {
        ScanMaltaScraper scraper = new ScanMaltaScraper();
        Product product = scraper.searchByInput("Fuji Instax Square SQ1", 3).get(0);
        this.httpService.publishAlert(product, 6);
    }

    @Test
    public void testDeleteAllAlerts() throws IOException {
        this.httpService.deleteAllAlerts();
    }

}