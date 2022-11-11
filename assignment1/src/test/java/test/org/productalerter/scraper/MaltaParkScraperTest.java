package test.org.productalerter.scraper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.productalerter.exception.WebScraperException;
import org.productalerter.scraper.MaltaParkScraper;

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
    public void testSearchByInput() throws WebScraperException {
        scraper.searchByInput("Toyota Vitz", 5);
    }

}