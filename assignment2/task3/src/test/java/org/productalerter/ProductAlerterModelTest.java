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
import org.productalerter.pageobjects.AlertListPageObject;
import org.productalerter.pageobjects.AlertPageObject;
import org.productalerter.service.HttpService;
import org.productalerter.service.MarketAlertUmPublisher;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ProductAlerterModelTest implements FsmModel {

    private final String DRIVER_PATH = "src/main/resources/chromedriver";

    private MarketAlertUmPublisher publisher;

    // Linking SUT
    private ProductAlerter sut;

    /************************************
     * STATE VARIABLES
     ************************************/
    private ProductAlerterStateEnum modelProductAlerter = ProductAlerterStateEnum.START;
    private boolean isLoggedIn = false;
    private int numOfAlerts = 0;

    public ProductAlerterModelTest() throws InterruptedException {
        publisher = new MarketAlertUmPublisher(new HttpService(), ProductAlerter.USER_ID);
        sut = new ProductAlerter(publisher);
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
            sut = new ProductAlerter(publisher);
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
        assertEquals(isLoggedIn, sut.isLoggedIn(), "The SUT's login status does not match the model's after logging in with valid credentials.");
        assertTrue(sut.isOnAlertsPage());
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
        assertEquals(isLoggedIn, sut.isLoggedIn(), "The SUT's login status does not match the model's after logging in with invalid credentials.");
        assertTrue(sut.isOnLoginPage());
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
        assertEquals(isLoggedIn, sut.isLoggedIn(), "The SUT's login status does not match the model's after logging out.");
        assertTrue(sut.isOnHomePage());
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
            assertTrue(sut.isOnLoginPage());
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

        // Checking correspondence between SUT and model  by ensuring the web application is showing the login page
        assertTrue(sut.isOnHomePage());
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

        // Checking correspondence between SUT and model  by ensuring the web application is showing the login page
        assertEquals(numOfAlerts, sut.getNumOfAlerts(), "The SUT's number of alerts is different than the model's after adding an alert.");
    }

    public boolean deleteAlertsGuard() {
        // Since the API is "independent" of the actions allowed on the web application, this transition can happen on
        // any state
        return true;
    }
    public @Action void deleteAlerts() {
        // Updating SUT
        sut.deleteAlerts();

        // Updating Model - state remains the same
        numOfAlerts = 0;

        // Checking correspondence between SUT and model  by ensuring the web application is showing the login page
        assertEquals(numOfAlerts, sut.getNumOfAlerts(), "The SUT's number of alerts is different than the model's after deleting all alerts.");
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
        tester.generate(250);
        tester.printCoverage();
    }

}