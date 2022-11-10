package org.electronicsscraper.scraper;

import lombok.Getter;
import lombok.Setter;
import org.electronicsscraper.model.domain.Product;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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

    protected abstract Product extractProductInfo(WebElement element);

}
