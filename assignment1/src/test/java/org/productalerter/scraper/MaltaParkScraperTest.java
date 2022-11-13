package org.productalerter.scraper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaltaParkScraperTest {

    MaltaParkScraper scraper;

    @BeforeEach
    public void setup() {
        scraper = new MaltaParkScraper();
    }

    @AfterEach
    public void teardown() {
        scraper.delete();
    }

    @Test
    public void searchByInput() throws org.productalerter.exception.WebScraperException {
        this.scraper.searchByInput("Fiat", 3);
    }

}