package marketalertumtester;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import marketalertumtester.pageobjects.AlertListPageObject;
import marketalertumtester.pageobjects.LoginPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    private final String DRIVER_PATH = "src/main/resources/chromedriver";
    public  final String HOME_URL = "https://www.marketalertum.com/";

    String validUserId = "b9ed2dbc-141a-4395-921d-ee8779610e1f";
    String invalidUserId = "invalid-123";

    WebDriver driver;

    @After
    public void teardown() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    @Given("I am a user of MarketAlertUm")
    public void iAmAUserOfMarketAlertUm() {
        // Setting up chrome driver
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        this.driver = new ChromeDriver();
        this.driver.navigate().to(HOME_URL);
    }

    @When("I login using valid credentials")
    public void iLoginUsingValidCredentials() {
        new LoginPageObject(driver).login(validUserId);
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() {
        String pageUrl = driver.getCurrentUrl();
        assertEquals(AlertListPageObject.ALERTS_URL, pageUrl);
    }

    @When("I login using invalid credentials")
    public void iLoginUsingInvalidCredentials() {
        new LoginPageObject(driver).login(invalidUserId);
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() {
        String pageUrl = driver.getCurrentUrl();
        assertEquals(LoginPageObject.LOGIN_URL, pageUrl);
    }
}
