package org.productalerter.scraper.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.productalerter.model.domain.MaltaParkProduct;

import java.util.ArrayList;
import java.util.List;

public class ProductListingsPageObject {

    private final WebDriver driver;

    public ProductListingsPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public List<MaltaParkProduct> getProductsFromPage(int numProducts) {
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

    private MaltaParkProduct extractProductInfo(WebElement element) {
        MaltaParkProduct product;

        try {
            // Opening product page in new tab to retrieve information
            WebElement linkElem = element.findElement(By.xpath(".//a[@class='header']"));
            String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
            linkElem.sendKeys(selectLinkOpenInNewTab);

            // Getting tab names and switching to newly opened tab
            List<String> handles = this.driver.getWindowHandles().stream().toList();
            this.driver.switchTo().window(handles.get(1));

            product = new ProductInfoPageObject(this.driver).getProductInfo();
        } catch (Exception ex) {
            product = null;
        }

        // Closing new tab (if opened) and switching back to search results tab
        List<String> handles = this.driver.getWindowHandles().stream().toList();
        if (handles.size() > 1) {
            this.driver.close();
            this.driver.switchTo().window(handles.get(0));
        }

        return product;
    }
}
