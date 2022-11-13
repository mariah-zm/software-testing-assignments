package marketalertumtester;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import marketalertumtester.helpers.MarketAlertUmTester;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    String validUserId = "b9ed2dbc-141a-4395-921d-ee8779610e1f";
    String invalidUserId = "invalid-123";

    ChromeDriver driver = new ChromeDriver();
    MarketAlertUmTester tester;

    @After
    public void teardown() {
        this.driver.quit();
        tester = null;
    }

    @Given("I am a user of MarketAlertUm")
    public void iAmAUserOfMarketAlertUm() {
        tester = new MarketAlertUmTester(driver);
    }

    @When("I login using valid credentials")
    public void iLoginUsingValidCredentials() {
        tester.login(validUserId);
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() {
        String pageUrl = driver.getCurrentUrl();
        assertEquals(MarketAlertUmTester.ALERTS_URL, pageUrl);
    }

    @When("I login using invalid credentials")
    public void iLoginUsingInvalidCredentials() {
        tester.login(invalidUserId);
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() {
        String pageUrl = driver.getCurrentUrl();
        assertEquals(MarketAlertUmTester.LOGIN_URL, pageUrl);
    }
}
