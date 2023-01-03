package org.productalerter;

import lombok.SneakyThrows;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.jupiter.api.Test;
import org.productalerter.enums.ProductAlerterStateEnum;
import org.productalerter.exception.PublisherException;
import org.productalerter.pageobjects.AlertListPageObject;
import org.productalerter.pageobjects.AlertPageObject;
import org.productalerter.service.HttpService;
import org.productalerter.service.MarketAlertUmService;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ProductAlerterModelTest implements FsmModel {

    private final String DRIVER_PATH = "src/main/resources/chromedriver";

    private MarketAlertUmService marketAlertUmService;

    // Linking SUT
    private ProductAlerter sut;

    /************************************
     * STATE VARIABLES
     ************************************/
    private ProductAlerterStateEnum modelProductAlerter = ProductAlerterStateEnum.START;
    private boolean isLoggedIn = false;
    private int numOfAlerts = 0;

    public ProductAlerterModelTest() throws PublisherException, IOException {
        marketAlertUmService = new MarketAlertUmService(new HttpService(), ProductAlerter.USER_ID);
        sut = new ProductAlerter(marketAlertUmService);
    }

    /************************************
     * METHOD IMPLS
     ************************************/

    @Override
    public Object getState() {
        return modelProductAlerter;
    }

    @SneakyThrows
    @Override
    public void reset(boolean b) {
        if (b) {
            sut.deleteDriver();
            sut = new ProductAlerter(marketAlertUmService);
        }
        modelProductAlerter = ProductAlerterStateEnum.START;
        isLoggedIn = false;
        numOfAlerts = 0;
    }

    /************************************
     * TRANSITIONS incl. GUARDS
     ************************************/

    public boolean validLoginGuard() {
        return !isLoggedIn
                && (getState().equals(ProductAlerterStateEnum.START)
                || getState().equals(ProductAlerterStateEnum.HOME_PAGE)
                || getState().equals(ProductAlerterStateEnum.LOGIN_PAGE));
    }
    public @Action void validLogin() {
        // Updating SUT
        sut.login(ProductAlerter.USER_ID);

        // Updating Model
        modelProductAlerter = ProductAlerterStateEnum.ALERTS_PAGE;
        isLoggedIn = true;

        // Checking correspondence between SUT and model by comparing login status
        // and ensuring the web application is showing the alerts list
        assertEquals(isLoggedIn, sut.isLoggedIn(), "The model's login status does not match the SUT's after logging in with valid credentials.");
        assertTrue(sut.isOnAlertsPage(), "The model's page state does not match the SUT's state.");
    }

    public boolean invalidLoginGuard() {
        return !isLoggedIn
                && (getState().equals(ProductAlerterStateEnum.START)
                || getState().equals(ProductAlerterStateEnum.HOME_PAGE)
                || getState().equals(ProductAlerterStateEnum.LOGIN_PAGE));
    }
    public @Action void invalidLogin() {
        // Updating SUT
        sut.login("user123");

        // Updating Model
        modelProductAlerter = ProductAlerterStateEnum.LOGIN_PAGE;
        isLoggedIn = false;

        // Checking correspondence between SUT and model by comparing login status
        // and ensuring the web application is showing the login page
        assertEquals(isLoggedIn, sut.isLoggedIn(), "The model's login status does not match the SUT's after logging in with invalid credentials.");
        assertTrue(sut.isOnLoginPage(), "The model's page state does not match the SUT's state.");
    }

    public boolean logoutGuard() {
        return isLoggedIn
                && (getState().equals(ProductAlerterStateEnum.HOME_PAGE)
                || getState().equals(ProductAlerterStateEnum.ALERTS_PAGE));
    }
    public @Action void logout() {
        // Updating SUT
        sut.logout();

        // Updating Model
        modelProductAlerter = ProductAlerterStateEnum.HOME_PAGE;
        isLoggedIn = false;

        // Checking correspondence between SUT and model by comparing login status
        // and ensuring the web application is showing the home page
        assertEquals(isLoggedIn, sut.isLoggedIn(), "The model's login status does not match the SUT's after logging out.");
        assertTrue(sut.isOnHomePage(), "The model's page state does not match the SUT's state.");
    }

    public boolean viewAlertsGuard() {
        return getState().equals(ProductAlerterStateEnum.START)
                || getState().equals(ProductAlerterStateEnum.ALERTS_PAGE)
                || getState().equals(ProductAlerterStateEnum.HOME_PAGE);
    }
    public @Action void viewAlerts() {
        // Updating SUT
        sut.viewAlerts();

        // Updating Model
        if (isLoggedIn) {
            modelProductAlerter = ProductAlerterStateEnum.ALERTS_PAGE;

            List<AlertPageObject> alertList = new AlertListPageObject(sut.getDriver()).getAlerts();

            // Checking correspondence between SUT and model by ensuring the web application is showing the alerts list
            // if user is logged in and also ensuring the right number of alerts are displayed
            assertTrue(sut.isOnAlertsPage());
            assertEquals(Math.min(numOfAlerts, 5), alertList.size());
        } else {
            modelProductAlerter = ProductAlerterStateEnum.LOGIN_PAGE;

            // Checking correspondence between SUT and model by ensuring the web application is showing the prompting
            // the user to log in first
            assertTrue(sut.isOnLoginPage(), "The model's page state does not match the SUT's state.");
        }
    }

    public boolean viewHomeGuard() {
        return getState().equals(ProductAlerterStateEnum.START)
                || getState().equals(ProductAlerterStateEnum.ALERTS_PAGE)
                || getState().equals(ProductAlerterStateEnum.HOME_PAGE);
    }
    public @Action void viewHome() {
        // Updating SUT
        sut.viewHome();

        // Updating Model
        modelProductAlerter = ProductAlerterStateEnum.HOME_PAGE;

        // Checking correspondence between SUT and model by ensuring the web application is showing the home page
        assertTrue(sut.isOnHomePage(), "The model's page state does not match the SUT's state.");
    }

    public boolean addAlertGuard() {
        // Since the API is "independent" of the actions allowed on the web application, this transition can happen on
        // any state
        return true;
    }
    public @Action void addAlert() {
        // Updating SUT
        sut.addAlert();

        // Updating Model - state remains the same
        numOfAlerts++;

        // Checking correspondence between SUT and model by ensuring the number of alerts on the system were increased
        // by 1
        assertEquals(numOfAlerts, sut.getNumOfAlerts(), "The model's number of alerts is different than the SUT's after adding an alert.");
    }

    public boolean deleteAlertsGuard() {
        return numOfAlerts > 0;
    }
    public @Action void deleteAlerts() {
        // Updating SUT
        sut.deleteAlerts();

        // Updating Model - state remains the same
        numOfAlerts = 0;

        // Checking correspondence between SUT and model by ensuring the alerts were actually deleted on the system
        assertEquals(numOfAlerts, sut.getNumOfAlerts(), "The model's number of alerts is different than the SUT's after deleting all alerts.");
    }

    /************************************
     * TEST RUNNER
     ************************************/

    @Test
    public void ProductAlerterModelRunner() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);

        // Giving this instance of the test class as to not have extra web driver windows open
        final GreedyTester tester = new GreedyTester(this);
        tester.setRandom(new Random());
        tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(300);
        tester.printCoverage();
    }

}