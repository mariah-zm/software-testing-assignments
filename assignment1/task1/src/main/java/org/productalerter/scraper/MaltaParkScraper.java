package org.productalerter.scraper;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.productalerter.exception.CategoryNotFoundException;
import org.productalerter.exception.WebScraperException;
import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.scraper.pageobjects.ProductInfoPageObject;
import org.productalerter.scraper.pageobjects.ProductListingsPageObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MaltaParkScraper {

    private final String DRIVER_PATH = "src/main/resources/chromedriver";
    private final String WEBSITE = "https://www.maltapark.com/";

    private final ChromeDriver driver;

    public MaltaParkScraper() {
        // Setting up chrome driver
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        ChromeOptions options  = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        this.driver = new ChromeDriver(options);
    }

    public List<MaltaParkProduct> searchByInput(String input, int numProducts) throws WebScraperException {
        try {
            this.navigateToHomePage();
        } catch (Exception ex) {
            throw new WebScraperException("Could not retrieve products", ex);
        }

        this.driver.findElement(By.id("search")).sendKeys(input);
        this.driver.findElement(By.className("search-checkbox")).click();
        this.driver.findElement(By.className("btn-search")).click();

        return new ProductListingsPageObject(this.driver).getProductsFromPage(numProducts);
    }

    public List<MaltaParkProduct> searchByCategory(String navBarCategory, int numProducts) throws WebScraperException, CategoryNotFoundException {
        try {
            this.navigateToHomePage();
        } catch (Exception ex) {
            throw new WebScraperException("Could not retrieve products", ex);
        }

        List<WebElement> categoryElems = this.driver.findElements(By.className("category"));

        for (WebElement elem : categoryElems) {
            if (elem.getText().equals(navBarCategory)) {
                elem.click();
                return new ProductListingsPageObject(this.driver).getProductsFromPage(numProducts);
            }
        }
        throw new CategoryNotFoundException("Category " + navBarCategory + " does not exist on " + WEBSITE);
    }

    public void delete() {
        driver.quit();
    }

    private void navigateToHomePage() {
        driver.navigate().to(WEBSITE);
        driver.manage().window().maximize();

        // Logic to handle dismissal of alert pop up on navigation to home screen
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("warningpopup")));
        WebElement closeButton = alert.findElement(By.id("okbutton"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("button.ui.button.disabled")));
        closeButton.click();
    }

}