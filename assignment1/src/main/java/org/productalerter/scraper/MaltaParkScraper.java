package org.productalerter.scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.productalerter.exception.CategoryNotFoundException;
import org.productalerter.exception.WebScraperException;
import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.scraper.pageobjects.ProductListingsPageObject;

import java.time.Duration;
import java.util.List;

public class MaltaParkScraper {

    private final String WEBSITE = "https://www.maltapark.com/";

    private final WebDriver driver;

    public MaltaParkScraper(WebDriver driver) {
        this.driver = driver;
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
