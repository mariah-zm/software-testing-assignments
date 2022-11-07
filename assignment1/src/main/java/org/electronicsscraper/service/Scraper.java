package org.electronicsscraper.service;

import lombok.Getter;
import lombok.Setter;
import org.electronicsscraper.model.Product;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

@Getter
@Setter
public abstract class Scraper {

    private final String DRIVER_PATH = "src/main/resources/chromedriver";

    private final String website;
    protected final ChromeDriver driver;

    public Scraper(String website) {
        // TODO: Validate website format using regex
        this.website = website;

        // Setting up chrome driver
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        this.driver = new ChromeDriver();
    }

    public void delete() {
        this.driver.close();
    }

    protected void navigateToHomePage() {
        this.driver.navigate().to(this.website);
        this.driver.manage().window().maximize();
    }

    protected void navigateToEndpoint(String endpoint) {
        this.driver.navigate().to(this.website +  endpoint);
        this.driver.manage().window().maximize();
    }

    protected abstract List<Product> extractProductsInfo(List<WebElement> elements);

}
