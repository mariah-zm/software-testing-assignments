package org.electronicsscraper;


import org.electronicsscraper.model.ScanCategoryEnum;
import org.electronicsscraper.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ScanMaltaScraper extends Scraper {

    public ScanMaltaScraper() {
        super("https://www.scanmalta.com/shop/");
    }

    public List<Product> searchByInput(String input) {
        this.navigateToHomePage();

        this.driver.findElement(By.id("search")).sendKeys(input);
        this.driver.findElement(By.className("search")).click();

        WebElement productsGrid = this.driver.findElement(By.className("products-grid"));
        List<WebElement> productsElems = productsGrid.findElements(By.className("product-item-info"));
        return extractProductsInfo(productsElems);
    }

    public void searchByCategory(ScanCategoryEnum category) {
        this.navigateToHomePage();

        List<WebElement> categoryElems = this.driver.findElements(By.className("top-category"));

        for (WebElement elem: categoryElems) {
            if (elem.getText().equals(category.toString())){
                elem.click();

                List<WebElement> bookItems = this.driver.findElements(By.className("book-item"));

                break;
            }
        }
    }

    public void searchByBestsellers() {
        this.navigateToEndpoint("bestsellers");
        List<WebElement> bookItems = this.driver.findElements(By.className("book-item"));
    }

    public void searchByLatestProducts() {
        this.navigateToEndpoint("top-new-releases");
        List<WebElement> bookItems = this.driver.findElements(By.className("book-item"));
    }

    @Override
    protected List<Product> extractProductsInfo(List<WebElement> elements) {
        List<Product> products = new ArrayList<>();

        for (WebElement elem: elements) {
            Product product = new Product();

            WebElement nameElem = elem.findElement(By.xpath(".//a[@class='product-item-link']"));
            WebElement photoElem = elem.findElement(By.xpath(".//img[@class='product-image-photo main-img']"));
            WebElement priceElem = elem.findElement(By.xpath(".//span[@class='price']"));

            product.setName(nameElem.getText());
            product.setPriceInCents(Long.parseLong(priceElem.getText().replaceAll("[^0-9]","")));
            product.setUrl(nameElem.getAttribute("href"));
            product.setImageUrl(photoElem.getAttribute("src"));

            // Opening product page in new tab to retrieve description
            String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
            nameElem.sendKeys(selectLinkOpenInNewTab);

            // Getting tab names and switching to newly opened tab
            List<String> handles = this.driver.getWindowHandles().stream().toList();
            this.driver.switchTo().window(handles.get(1));

            WebElement overviewElem = this.driver.findElement(By.xpath(".//div[@class='product attribute overview']"));
            WebElement descriptionElem = overviewElem.findElement(By.xpath(".//div[@class='value']"));
            product.setDescription(descriptionElem.getText());

            products.add(product);

            // Closing opened tab and switching back to search results tab
            this.driver.close();
            this.driver.switchTo().window(handles.get(0));
        }

        return products;
    }

}
