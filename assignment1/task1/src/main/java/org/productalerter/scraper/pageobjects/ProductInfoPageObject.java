package org.productalerter.scraper.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.scraper.pageobjects.ItemDetailsPageObject;

public class ProductInfoPageObject {

    private final WebDriver driver;

    public ProductInfoPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public MaltaParkProduct getProductInfo() {
        MaltaParkProduct product = new MaltaParkProduct();
        product.setName(this.getName());
        product.setDescription(this.getDescription());
        product.setPriceInEuros(this.getPrice());
        product.setCategory(this.getCategory());
        product.setImageUrl(this.getImageUrl());
        product.setUrl(driver.getCurrentUrl());
        return product;
    }

    public String getName() {
        return driver.findElement(By.className("top-title")).getText();
    }

    public Double getPrice() {
        WebElement priceElem = driver.findElement(By.className("top-price"));
        return Double.parseDouble(priceElem.getText().replaceAll("[^(.0-9)]", ""));
    }

    public String getDescription() {
        return driver.findElement(By.className("readmore-wrapper")).getText();
    }

    public String getCategory() {
        return new ItemDetailsPageObject(this.driver).getCategory();
    }

    public String getImageUrl() {
        return driver.findElement(By.xpath("//div[@class='image-wrapper slick-slide']/a/img")).getAttribute("src");
    }
}
