package org.productalerter.scraper;

import lombok.Getter;
import lombok.Setter;
import org.productalerter.exception.CategoryNotFoundException;
import org.productalerter.exception.WebScraperException;
import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.model.domain.Product;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

@Getter
@Setter
public abstract class Scraper {

    private final String DRIVER_PATH = "src/main/resources/chromedriver";

    private final String website;
    protected final ChromeDriver driver;

    protected Scraper(String website) {
        // TODO: Validate website format using regex
        this.website = website;

        // Setting up chrome driver
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        ChromeOptions options  = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(options);
    }

    public void delete() {
        driver.close();
    }

    protected void navigateToHomePage() throws InterruptedException {
        driver.navigate().to(website);
        driver.manage().window().maximize();
    }

    public abstract List<MaltaParkProduct> searchByInput(String input, int numProducts) throws WebScraperException;

    public abstract List<MaltaParkProduct> searchByCategory(String category, int numProducts) throws WebScraperException, CategoryNotFoundException;

    protected abstract Product extractProductInfo(WebElement element);

}
