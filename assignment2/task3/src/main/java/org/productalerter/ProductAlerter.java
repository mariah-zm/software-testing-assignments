package org.productalerter;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.productalerter.exception.PublisherException;
import org.productalerter.model.domain.CategoryEnum;
import org.productalerter.model.domain.Product;
import org.productalerter.pageobjects.AlertListPageObject;
import org.productalerter.pageobjects.LoginPageObject;
import org.productalerter.service.MarketAlertUmService;

import java.io.IOException;

public class ProductAlerter {

    public static final String USER_ID = "b9ed2dbc-141a-4395-921d-ee8779610e1f";
    public final String HOME_URL = "https://www.marketalertum.com/";
    public final String ALERTS_URL = "https://www.marketalertum.com/Alerts/List";
    public final String LOGOUT_URL = "https://www.marketalertum.com/Home/Logout";

    @Getter
    private final WebDriver driver;
    private final MarketAlertUmService publisher;

    // Variables required by system
    @Getter
    private boolean isLoggedIn = false;
    @Getter
    private int numOfAlerts = 0;
    @Getter
    private int alertsOnPage = 0;

    public ProductAlerter(MarketAlertUmService publisher) throws PublisherException, IOException {
        driver = new ChromeDriver();
        this.publisher = publisher;

        // Deleting all alerts on system start up
        publisher.deleteAllAlerts();
    }

    public void login(String credentials) {
        if (!isLoggedIn) {
            driver.navigate().to(HOME_URL);
            isLoggedIn = new LoginPageObject(driver).login(credentials);
        } else {
            throw new IllegalStateException();
        }
    }

    public void logout() {
        if (isLoggedIn) {
            driver.navigate().to(LOGOUT_URL);
            isLoggedIn = !driver.getCurrentUrl().equals(HOME_URL);
        } else {
            throw new IllegalStateException();
        }
    }

    public void addAlert() {
        try {
            Product dummyProduct = getDummyProduct();
            publisher.publishAlert(dummyProduct);
            numOfAlerts++;
        } catch (Exception ex) {
            System.out.println("Error adding alert: " + ex.getMessage());
        }
    }

    public void deleteAlerts() {
        try {
            String message = publisher.deleteAllAlerts();
            int numAlertsDeleted = Integer.parseInt(message.replaceAll("[^0-9]", ""));
            numOfAlerts = numOfAlerts - numAlertsDeleted;
        } catch (Exception ex) {
            System.out.println("Error deleting alerts: " + ex.getMessage());
        }
    }

    public void viewAlerts() {
        driver.navigate().to(ALERTS_URL);
        if (isLoggedIn) {
            alertsOnPage = new AlertListPageObject(driver).getAlerts().size();
        }
    }

    public void viewHome() {
        driver.navigate().to(HOME_URL);
    }

    public void deleteDriver() {
        driver.quit();
    }

    public boolean isOnAlertsPage() {
        return driver.getCurrentUrl().equals(ALERTS_URL);
    }

    public boolean isOnHomePage() {
        return driver.getCurrentUrl().equals(HOME_URL);
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().equals(LoginPageObject.LOGIN_URL);
    }

    private Product getDummyProduct() {
        Product product = new Product();
        product.setCategory(CategoryEnum.CAR);
        product.setName("Name");
        product.setDescription("Description");
        product.setPriceInEuros(360000.0);
        product.setImageUrl("https://www.maltapark.com/asset/itemphotos/9521343/9521343_1.jpg/?x=TWF4Vz01NjMmTWF4SD00MjI=&_ts=10");
        product.setUrl("https://www.maltapark.com/item/details/9521343");

        return product;
    }

}
