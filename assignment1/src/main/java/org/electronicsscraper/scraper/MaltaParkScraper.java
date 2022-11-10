package org.electronicsscraper.scraper;

import org.electronicsscraper.model.Product;
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

    public List<Product> searchByInput(String input, int numProducts) throws InterruptedException {
        this.navigateToHomePage();

        this.driver.findElement(By.id("search")).sendKeys(input);
        this.driver.findElement(By.className("btn-search")).click();

        return getProductsFromPage(numProducts);
    }

    @Override
    protected void navigateToHomePage() throws InterruptedException {
        driver.navigate().to(this.getWebsite());
        driver.manage().window().maximize();

        // Logic to handle dismissal of alert pop up on navigation to home screen
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("warningpopup")));

        // Close button takes 5 seconds to be enabled
        Thread.sleep(5000);
        driver.findElement(By.id("okbutton")).click();
    }

    @Override
    protected Product extractProductInfo(WebElement element) {
        Product product = new Product();

        // Opening product page in new tab to retrieve information
        WebElement linkElem = element.findElement(By.xpath(".//a[@class='header']"));
        String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        linkElem.sendKeys(selectLinkOpenInNewTab);

        // Getting tab names and switching to newly opened tab
        List<String> handles = this.driver.getWindowHandles().stream().toList();
        this.driver.switchTo().window(handles.get(1));

        WebElement nameElem = driver.findElement(By.className("top-title"));
        WebElement priceElem = driver.findElement(By.className("top-price"));
        WebElement photoElem = driver.findElement(By.className("image-wrapper")).findElement(By.tagName("img"));
        WebElement descriptionElem = driver.findElement(By.className("readmore-wrapper"));

        WebElement itemDetailsElem = driver.findElement(By.className("item-details"));
        List<WebElement> itemDetailsItems = itemDetailsElem.findElements(By.className("item"));
        String category = "Other";

        for (WebElement item: itemDetailsItems) {
            WebElement label = item.findElement(By.tagName("label"));
            String labelName = label.getText();

            if (labelName.equals("Category")) {
                category = item.getText();
            }
        }

        product.setName(nameElem.getText());
        product.setDescription(descriptionElem.getText());
        // TODO: check for . to decide if * 100
        product.setPriceInEuros(Long.parseLong(priceElem.getText().replaceAll("[^(.0-9)]", "")));
        product.setCategory(category);
        product.setImageUrl(photoElem.getAttribute("src"));
        product.setUrl(driver.getCurrentUrl());

        // Closing opened tab and switching back to search results tab
        this.driver.close();
        this.driver.switchTo().window(handles.get(0));

        return product;
    }

    private List<Product> getProductsFromPage(int numProducts) {
        WebElement productListings = this.driver.findElement(By.cssSelector(".items.listings.classifieds"));
        List<WebElement> productsElems = productListings.findElements(By.className("item"));
        int toIndex = Math.min(productsElems.size(), numProducts) - 1;
        return extractProductsInfo(productsElems.subList(0, toIndex));
    }

    private List<Product> extractProductsInfo(List<WebElement> elements) {
        List<Product> products = new ArrayList<>();

        for (WebElement elem : elements) {
            Product product = extractProductInfo(elem);
            products.add(product);
        }

        return products;
    }
}
