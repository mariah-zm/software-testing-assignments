package org.electronicsscraper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScraperTest {

    private ScanMaltaScraper scraper;

    @BeforeEach
    public void beforeTest() {
        this.scraper = new ScanMaltaScraper();
    }

    @AfterEach
    public void afterTest() {
        this.scraper.delete();
    }

    @Test
    public void testSearchByInput() {
        final String INPUT = "Fujifilm";
        this.scraper.searchByInput(INPUT, 2);
    }

    @Test
    public void searchByBestSellers() {
        this.scraper.searchByBestSellers(2);
    }

    @Test
    public void searchByLatestProducts() {
        this.scraper.searchByLatestProducts(2);
    }

}