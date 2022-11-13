package marketalertumtester;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import marketalertumtester.helpers.MarketAlertUmTester;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class AlertListSteps {

    String userId = "b9ed2dbc-141a-4395-921d-ee8779610e1f";

    ChromeDriver driver = new ChromeDriver();
    MarketAlertUmTester tester;

    @After
    public void teardown() {
        this.driver.quit();
        tester = null;
    }

    @Given("I am an administrator of the website")
    public void iAmAnAdministratorOfTheWebsite() {
        tester = new MarketAlertUmTester(driver);
        tester.login(userId);
    }

    @Given("I upload {int} alerts")
    public void iUploadAlerts(int arg0) {
    }

    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {
    }

    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
    }

    @And("each alert should contain a heading")
    public void eachAlertShouldContainAHeading() {
    }

    @And("each alert should contain a description")
    public void eachAlertShouldContainADescription() {
    }

    @And("each alert should contain an image")
    public void eachAlertShouldContainAnImage() {
    }

    @And("each alert should contain a price")
    public void eachAlertShouldContainAPrice() {
    }

    @And("each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
    }

    @Given("I upload more than {int} alerts")
    public void iUploadMoreThanAlerts(int arg0) {
    }

    @Then("I should see {int} alerts")
    public void iShouldSeeAlerts(int arg0) {
    }

    @Given("I am an administrator of the website and I upload an alert of type <alert-type>")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfTypeAlertType() {
    }

    @And("the icon displayed should be <icon-file-name>")
    public void theIconDisplayedShouldBeIconFileName() {
    }
}
