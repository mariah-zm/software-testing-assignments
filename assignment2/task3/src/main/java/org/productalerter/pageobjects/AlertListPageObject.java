package org.productalerter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AlertListPageObject {

    public static final String ALERTS_URL = "https://www.marketalertum.com/Alerts/List";

    public final WebDriver driver;

    public AlertListPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public List<AlertPageObject> getAlerts() {
        // Navigating to alerts page if not already on page
        if (!driver.getCurrentUrl().equals(ALERTS_URL)) {
            this.driver.navigate().to(ALERTS_URL);
            this.driver.manage().window().maximize();
        }

        List<WebElement> alertsElems = this.driver.findElements(By.tagName("table"));
        List<AlertPageObject> alerts = new ArrayList<>();
        for (WebElement elem: alertsElems) {
            alerts.add(new AlertPageObject(elem));
        }

        return alerts;
    }

}
