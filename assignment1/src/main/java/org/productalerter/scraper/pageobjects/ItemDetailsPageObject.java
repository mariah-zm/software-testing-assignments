package org.productalerter.scraper.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ItemDetailsPageObject {

    private final WebDriver driver;

    public ItemDetailsPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public String getCategory() {
        List<WebElement> itemDetailsElems = driver.findElements(By.xpath("//div[@class='ui list fixed-label item-details']/span[@class='item']"));
        itemDetailsElems.addAll(driver.findElements(By.xpath("//div[@class='ui list fixed-label item-details']/div[@class='item']")));

        for (WebElement elem : itemDetailsElems) {
            String text = elem.getText();
            if (text.contains("Category")) {
                return text.substring(text.indexOf(":") + 1);
            }
        }

        return "Other";
    }

}
