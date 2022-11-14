package marketalertumtester.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AlertPageObject {

    private final WebElement alert;

    public AlertPageObject(WebElement alert) {
        this.alert = alert;
    }

    public String findIcon() {
        return this.alert.findElement(By.xpath("//tr/td/h4/img")).getAttribute("src");
    }

    public String findHeading() {
        return this.alert.findElement(By.xpath("//tr/td/h4")).getText();
    }

    public String findDescription() {
        // Description is in the 3rd row
        return this.alert.findElements(By.xpath("//tr/td")).get(2).getText();
    }

    public Double findPrice() {
        // Price is in the 4th row
        String priceContent = this.alert.findElements(By.xpath("//tr/td")).get(3).getText();
        String priceOnly = priceContent.substring(priceContent.indexOf('\u20AC') + 1);
        return Double.parseDouble(priceOnly);
    }

    public String findImageUrl() {
        return this.alert.findElement(By.xpath("//tr/td/img")).getAttribute("src");
    }

    public String findUrl() {
        return this.alert.findElement(By.xpath("//tr/td/a")).getAttribute("href");
    }

}
