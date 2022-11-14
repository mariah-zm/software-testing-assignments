package marketalertumtester.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPageObject {

    public static final String LOGIN_URL = "https://www.marketalertum.com/Alerts/Login";

    public final WebDriver driver;

    public LoginPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String userId) {
        // Navigating to login page if not already on page
        if (!driver.getCurrentUrl().equals(LOGIN_URL)) {
            this.driver.navigate().to(LOGIN_URL);
            this.driver.manage().window().maximize();
        }

        WebElement inputBox = this.driver.findElement(By.id("UserId"));
        inputBox.sendKeys(userId);
        inputBox.submit();
    }

}
