package org.marketalertumtester;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MarketAlertUmTester {

    public static final String HOME_URL = "https://www.marketalertum.com/";
    public static final String LOGIN_URL = "https://www.marketalertum.com/Alerts/Login";
    public static final String ALERTS_URL = "https://www.marketalertum.com/Alerts/List";

    private final String DRIVER_PATH = "src/main/resources/chromedriver";
    private final ChromeDriver driver;

    public MarketAlertUmTester(ChromeDriver driver) {
        // Setting up chrome driver
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        this.driver = driver;
    }

    public void login(String userId) {
        // Navigating to login page
        this.navigateToUrl(LOGIN_URL);
        WebElement inputBox = this.driver.findElement(By.id("UserId"));
        inputBox.sendKeys(userId);
        inputBox.submit();
    }

    private void navigateToUrl(String url) {
        this.driver.navigate().to(url);
        this.driver.manage().window().maximize();
    }

}