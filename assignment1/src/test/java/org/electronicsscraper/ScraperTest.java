package org.electronicsscraper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScraperTest {

    private ScanMaltaScraper scraper;

    @BeforeEach
    public void beforeTest() {
        this.scraper = new ScanMaltaScraper();
    }

    @Test
    public void testSearchByCategory() {

    }

    @Test
    public void testSearchByInput() {
        final String INPUT = "Fujifilm";
        this.scraper.searchByInput(INPUT);
    }

    @Test
    public void searchByBestsellers() {

    }

    @Test
    public void searchByNewReleases() {

    }

}