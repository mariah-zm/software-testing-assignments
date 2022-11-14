package marketalertumtester;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import marketalertumtester.dummies.DummyProductCreator;
import marketalertumtester.pageobjects.AlertListPageObject;
import marketalertumtester.pageobjects.AlertPageObject;
import marketalertumtester.pageobjects.LoginPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.productalerter.exception.CategoryNotFoundException;
import org.productalerter.exception.PublisherException;
import org.productalerter.exception.WebScraperException;
import org.productalerter.model.domain.CategoryEnum;
import org.productalerter.model.domain.MaltaParkProduct;
import org.productalerter.service.HttpService;
import org.productalerter.service.MarketAlertUmPublisher;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlertListSteps {

    private final String DRIVER_PATH = "src/main/resources/chromedriver";

    String userId = "b9ed2dbc-141a-4395-921d-ee8779610e1f";

    // Helpers
    MarketAlertUmPublisher publisher;
    List<AlertPageObject> alerts;

    WebDriver driver;

    @Before
    public void setup() {
        publisher = new MarketAlertUmPublisher(new HttpService(), userId);
    }

    @After
    public void teardown() throws PublisherException, IOException {
        this.publisher.deleteAllAlerts();
        this.alerts = null;

        if (this.driver != null) {
            this.driver.quit();
        }
    }

    @Given("I am an administrator of the website and I upload {int} alerts")
    public void iUploadAlerts(int arg0) throws PublisherException, IOException {
        List<MaltaParkProduct> products = DummyProductCreator.getDummyProducts(arg0);
        for (MaltaParkProduct product: products) {
            this.publisher.publishAlert(product);
        }
    }

    @Given("I am a logged in user of MarketAlertUm")
    public void iAmAUserOfMarketAlertUm() {
        // Setting up chrome driver
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        this.driver = new ChromeDriver();
        new LoginPageObject(driver).login(userId);
    }

    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {
        this.driver.navigate().to(AlertListPageObject.ALERTS_URL);
        this.alerts = new AlertListPageObject(driver).getAlerts();
    }

    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
        for (AlertPageObject alert: alerts) {
            assertDoesNotThrow(alert::findIcon);
        }
    }

    @And("each alert should contain a heading")
    public void eachAlertShouldContainAHeading() {
        for (AlertPageObject alert: alerts) {
            assertDoesNotThrow(alert::findHeading);
        }
    }

    @And("each alert should contain a description")
    public void eachAlertShouldContainADescription() {
        for (AlertPageObject alert: alerts) {
            assertDoesNotThrow(alert::findDescription);
        }
    }

    @And("each alert should contain an image")
    public void eachAlertShouldContainAnImage() {
        for (AlertPageObject alert: alerts) {
            assertDoesNotThrow(alert::findImageUrl);
        }
    }

    @And("each alert should contain a price")
    public void eachAlertShouldContainAPrice() {
        for (AlertPageObject alert: alerts) {
            assertDoesNotThrow(alert::findPrice);
        }
    }

    @And("each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
        for (AlertPageObject alert: alerts) {
            assertDoesNotThrow(alert::findUrl);
        }
    }

    @Given("I am an administrator of the website and I upload more than {int} alerts")
    public void iUploadMoreThanAlerts(int arg0) throws PublisherException, IOException {
        List<MaltaParkProduct> products = DummyProductCreator.getDummyProducts(arg0 + 1);
        for (MaltaParkProduct product: products) {
            this.publisher.publishAlert(product);
        }
    }

    @Then("I should see {int} alerts")
    public void iShouldSeeAlerts(int arg0) {
        assertEquals(arg0, alerts.size());
    }

    @Given("I am an administrator of the website and I upload an alert of type {int}")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType(int alertType) throws WebScraperException, CategoryNotFoundException, PublisherException, IOException {
        MaltaParkProduct product = DummyProductCreator.getDummyProductWithCategory(alertType);
        // Resetting category to match alertType
        product.setCategory(CategoryEnum.getByValue(alertType));
        this.publisher.publishAlert(product);
    }

    @And("the icon displayed should be {word}")
    public void theIconDisplayedShouldBe(String iconFileName) {
        AlertPageObject alert = alerts.get(0);
        String iconSrc = alert.findIcon();
        assertTrue(iconSrc.contains(iconFileName));
    }

}
