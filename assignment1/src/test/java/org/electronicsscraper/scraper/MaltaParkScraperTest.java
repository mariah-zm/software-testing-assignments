package org.electronicsscraper.scraper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaltaParkScraperTest {

    MaltaParkScraper scraper;

    @BeforeEach
    public void setup() {
        scraper = new MaltaParkScraper();
    }

    @Test
    public void testSearchByInput() throws InterruptedException {
        scraper.searchByInput("Fiat grande punto", 4);
    }

}