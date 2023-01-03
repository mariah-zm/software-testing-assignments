package org.productalerter;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.productalerter.exception.PublisherException;
import org.productalerter.model.domain.CategoryEnum;
import org.productalerter.model.domain.Product;
import org.productalerter.pageobjects.LoginPageObject;
import org.productalerter.service.MarketAlertUmService;

import java.io.IOException;

public class AlertValidator {

    public static final String USER_ID = "b9ed2dbc-141a-4395-921d-ee8779610e1f";
    public final String ALERTS_URL = "https://www.marketalertum.com/Alerts/List";

    @Getter
    private final WebDriver driver;
    private final MarketAlertUmService marketAlertUmService;

    private CategoryEnum alertType;

    public AlertValidator(MarketAlertUmService marketAlertUmService) throws PublisherException, IOException {
        driver = new ChromeDriver();
        this.marketAlertUmService = marketAlertUmService;

        // Deleting all alerts on system start up
        marketAlertUmService.deleteAllAlerts();
        login();
        // Getting event log to reset it
        marketAlertUmService.getEventLog();
    }

    public void addCarAlert() {
        alertType = CategoryEnum.CAR;
        addAlert();
    }

    public void addBoatAlert() {
        alertType = CategoryEnum.BOAT;
        addAlert();
    }
    public void addPropertyForRentAlert() {
        alertType = CategoryEnum.PROPERTY_FOR_RENT;
        addAlert();
    }
    public void addPropertyForSaleAlert() {
        alertType = CategoryEnum.PROPERTY_FOR_SALE;
        addAlert();
    }

    public void addToyAlert() {
        alertType = CategoryEnum.TOYS;
        addAlert();
    }

    public void addElectronicsAlert() {
        alertType = CategoryEnum.ELECTRONICS;
        addAlert();
    }

    public void viewAlerts() {
        driver.navigate().to(ALERTS_URL);
    }

    public void deleteDriver() {
        driver.quit();
    }

    private void login() {
        new LoginPageObject(driver).login(USER_ID);
    }

    private void addAlert() {
        try {
            Product dummyProduct = getDummyProduct();
            dummyProduct.setCategory(alertType);
            marketAlertUmService.publishAlert(dummyProduct);
        } catch (Exception ex) {
            System.out.println("Error adding alert: " + ex.getMessage());
        }
    }

    private Product getDummyProduct() {
        Product product = new Product();
        product.setName("Name");
        product.setDescription("Description");
        product.setPriceInEuros(360000.0);
        product.setImageUrl("https://www.maltapark.com/asset/itemphotos/9521343/9521343_1.jpg/?x=TWF4Vz01NjMmTWF4SD00MjI=&_ts=10");
        product.setUrl("https://www.maltapark.com/item/details/9521343");

        return product;
    }

}
