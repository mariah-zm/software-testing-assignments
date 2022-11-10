package org.productalerter.scraper;

import org.productalerter.exception.CategoryNotFoundException;
import org.productalerter.exception.WebScraperException;
import org.productalerter.model.domain.MaltaParkProduct;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MaltaParkScraper extends Scraper {

    public MaltaParkScraper() {
        super("https://www.maltapark.com/");
    }

    @Override
    public List<MaltaParkProduct> searchByInput(String input, int numProducts) throws WebScraperException {
        try {
            this.navigateToHomePage();
        } catch (InterruptedException ex) {
            throw new WebScraperException("Could not retrieve products", ex);
        }

        this.driver.findElement(By.id("search")).sendKeys(input);
        this.driver.findElement(By.className("search-checkbox")).click();
        this.driver.findElement(By.className("btn-search")).click();

        return getProductsFromPage(numProducts);
    }

    public List<MaltaParkProduct> searchByCategory(String navBarCategory, int numProducts) throws WebScraperException, CategoryNotFoundException {
        try {
            this.navigateToHomePage();
        } catch (InterruptedException ex) {
            throw new WebScraperException("Could not retrieve products", ex);
        }

        List<WebElement> categoryElems = this.driver.findElements(By.className("category"));

        for(WebElement elem: categoryElems) {
            if (elem.getText().equals(navBarCategory)) {
                elem.click();
                return getProductsFromPage(numProducts);
            }
        }
        throw new CategoryNotFoundException("Category " + navBarCategory + " does not exist on " + this.getWebsite());
    }

    @Override
    protected void navigateToHomePage() throws InterruptedException {
        driver.navigate().to(this.getWebsite());
        driver.manage().window().maximize();

        // Logic to handle dismissal of alert pop up on navigation to home screen
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("warningpopup")));

        // Close button takes 5 seconds to be enabled
        Thread.sleep(5000);
        driver.findElement(By.id("okbutton")).click();
    }

    @Override
    protected MaltaParkProduct extractProductInfo(WebElement element) {
        try {
            // Opening product page in new tab to retrieve information
            WebElement linkElem = element.findElement(By.xpath(".//a[@class='header']"));
            String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
            linkElem.sendKeys(selectLinkOpenInNewTab);

            // Getting tab names and switching to newly opened tab
            List<String> handles = this.driver.getWindowHandles().stream().toList();
            this.driver.switchTo().window(handles.get(1));

            // Locating web elements containing product information
            WebElement nameElem = driver.findElement(By.className("top-title"));
            WebElement priceElem = driver.findElement(By.className("top-price"));
            WebElement photoElem = driver.findElement(By.className("image-wrapper")).findElement(By.tagName("img"));
            WebElement descriptionElem = driver.findElement(By.className("readmore-wrapper"));

            List<WebElement> itemDetailsElems = driver.findElements(By.className("item-details"));
            String category = this.getProductCategory(itemDetailsElems);

            // Setting properties from web elements
            MaltaParkProduct product = new MaltaParkProduct();
            product.setName(nameElem.getText());
            product.setDescription(descriptionElem.getText());
            product.setPriceInEuros(Double.parseDouble(priceElem.getText().replaceAll("[^(.0-9)]", "")));
            product.setCategory(category);
            product.setImageUrl(photoElem.getAttribute("src"));
            product.setUrl(driver.getCurrentUrl());

            // Closing opened tab and switching back to search results tab
            this.driver.close();
            this.driver.switchTo().window(handles.get(0));

            return product;
        } catch (Exception ex) {
            return null;
        }
    }

    private String getProductCategory(List<WebElement> itemDetailsElems) {
        for (WebElement elem : itemDetailsElems) {
            List<WebElement> itemDetailsItems = elem.findElements(By.className("item"));

            for (WebElement item: itemDetailsItems) {
                WebElement label = item.findElement(By.tagName("label"));
                String labelName = label.getText();

                if (labelName.contains("Category")) {
                    String text = item.getText();
                    return text.substring(text.indexOf(":") + 1);
                }
            }
        }

        return "Other";
    }

    private List<MaltaParkProduct> getProductsFromPage(int numProducts) {
        WebElement productListings = this.driver.findElement(By.cssSelector(".items.listings.classifieds"));
        List<WebElement> productsElems = productListings.findElements(By.className("item"));
        return extractProductsInfo(productsElems, numProducts);
    }

    private List<MaltaParkProduct> extractProductsInfo(List<WebElement> elements, int numProducts) {
        List<MaltaParkProduct> products = new ArrayList<>();

        for (WebElement elem : elements) {
            // Items that are listed as 'WANTED' are ignored
            if (elem.findElements(By.id("wantedlabel")).isEmpty()) {
                MaltaParkProduct product = extractProductInfo(elem);

                if (product != null) {
                    products.add(product);
                }

                // Breaking from loop if the required amount of products are found
                if (products.size() == numProducts) {
                    break;
                }
            }
        }

        return products;
    }
}
