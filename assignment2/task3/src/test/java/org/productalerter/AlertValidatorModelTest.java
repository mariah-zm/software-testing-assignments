package org.productalerter;

import lombok.SneakyThrows;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.jupiter.api.Test;
import org.productalerter.enums.AlertValidatorStateEnum;
import org.productalerter.exception.PublisherException;
import org.productalerter.model.domain.CategoryEnum;
import org.productalerter.model.eventlog.EventLog;
import org.productalerter.model.eventlog.EventLogTypeEnum;
import org.productalerter.pageobjects.AlertListPageObject;
import org.productalerter.pageobjects.AlertPageObject;
import org.productalerter.service.HttpService;
import org.productalerter.service.MarketAlertUmService;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AlertValidatorModelTest implements FsmModel {

    private final String DRIVER_PATH = "src/main/resources/chromedriver";

    private MarketAlertUmService marketAlertUmService;

    // Linking SUT
    private AlertValidator sut;

    /************************************
     * STATE VARIABLES
     ************************************/
    private AlertValidatorStateEnum modelAlertValidator = AlertValidatorStateEnum.START;
    private CategoryEnum alertType;

    private final String[] iconNames = {"icon-car",
                                        "icon-boat",
                                        "icon-property-rent",
                                        "icon-property-sale",
                                        "icon-toys",
                                        "icon-electronics"};

    public AlertValidatorModelTest() throws PublisherException, IOException {
        marketAlertUmService = new MarketAlertUmService(new HttpService(), ProductAlerter.USER_ID);
        sut = new AlertValidator(marketAlertUmService);
    }

    @Override
    public Object getState() {
        return modelAlertValidator;
    }

    @SneakyThrows
    @Override
    public void reset(boolean b) {
        if (b) {
            sut.deleteDriver();
            sut = new AlertValidator(marketAlertUmService);
        }

        modelAlertValidator = AlertValidatorStateEnum.START;
    }

    public boolean addCarAlertGuard() {
        return getState().equals(AlertValidatorStateEnum.START);
    }
    public @Action void addCarAlert() throws PublisherException, IOException {
        // Updating SUT
        sut.addCarAlert();

        // Updating model
        modelAlertValidator = AlertValidatorStateEnum.ALERT_ADDED;
        alertType = CategoryEnum.CAR;

        // Checking correspondence between SUT and model by checking alert was actually added
        doAddAlertValidation();
    }

    public boolean addBoatAlertGuard() {
        return getState().equals(AlertValidatorStateEnum.START);
    }
    public @Action void addBoatAlert() throws PublisherException, IOException {
        // Updating SUT
        sut.addBoatAlert();

        // Updating model
        modelAlertValidator = AlertValidatorStateEnum.ALERT_ADDED;
        alertType = CategoryEnum.BOAT;

        // Checking correspondence between SUT and model by checking alert was actually added
        doAddAlertValidation();
    }

    public boolean addPropertyForRentAlertGuard() {
        return getState().equals(AlertValidatorStateEnum.START);
    }
    public @Action void addPropertyForRentAlert() throws PublisherException, IOException {
        // Updating SUT
        sut.addPropertyForRentAlert();

        // Updating model
        modelAlertValidator = AlertValidatorStateEnum.ALERT_ADDED;
        alertType = CategoryEnum.PROPERTY_FOR_RENT;

        // Checking correspondence between SUT and model by checking alert was actually added
        doAddAlertValidation();
    }

    public boolean addPropertyForSaleAlertGuard() {
        return getState().equals(AlertValidatorStateEnum.START);
    }
    public @Action void addPropertyForSaleAlert() throws PublisherException, IOException {
        // Updating SUT
        sut.addPropertyForSaleAlert();

        // Updating model
        modelAlertValidator = AlertValidatorStateEnum.ALERT_ADDED;
        alertType = CategoryEnum.PROPERTY_FOR_SALE;

        // Checking correspondence between SUT and model by checking alert was actually added
        doAddAlertValidation();
    }

    public boolean addToyAlertGuard() {
        return getState().equals(AlertValidatorStateEnum.START);
    }
    public @Action void addToyAlert() throws PublisherException, IOException {
        // Updating SUT
        sut.addToyAlert();

        // Updating model
        modelAlertValidator = AlertValidatorStateEnum.ALERT_ADDED;
        alertType = CategoryEnum.TOYS;

        // Checking correspondence between SUT and model by checking alert was actually added
        doAddAlertValidation();
    }

    public boolean addElectronicsAlertGuard() {
        return getState().equals(AlertValidatorStateEnum.START);
    }
    public @Action void addElectronicsAlert() throws PublisherException, IOException {
        // Updating SUT
        sut.addElectronicsAlert();

        // Updating model
        modelAlertValidator = AlertValidatorStateEnum.ALERT_ADDED;
        alertType = CategoryEnum.ELECTRONICS;

        // Checking correspondence between SUT and model by checking alert was actually added
        doAddAlertValidation();
    }

    public boolean viewAlertsGuard() {
        return getState().equals(AlertValidatorStateEnum.ALERT_ADDED);
    }
    public @Action void viewAlerts() {
        // Updating SUT
        sut.viewAlerts();

        // Updating Model
        switch (alertType) {
            case CAR:
                modelAlertValidator = AlertValidatorStateEnum.CAR_ALERT;
                break;
            case BOAT:
                modelAlertValidator = AlertValidatorStateEnum.BOAT_ALERT;
                break;
            case PROPERTY_FOR_RENT:
                modelAlertValidator = AlertValidatorStateEnum.PROPERTY_RENT_ALERT;
                break;
            case PROPERTY_FOR_SALE:
                modelAlertValidator = AlertValidatorStateEnum.PROPERTY_SALE_ALERT;
                break;
            case TOYS:
                modelAlertValidator = AlertValidatorStateEnum.TOY_ALERT;
                break;
            case ELECTRONICS:
                modelAlertValidator = AlertValidatorStateEnum.ELECTRONICS_ALERT;
                break;
        }

        // Checking correspondence between SUT and model by checking alert components
        AlertPageObject alert = new AlertListPageObject(sut.getDriver()).getAlerts().get(0);
        assertDoesNotThrow(alert::findHeading);
        assertDoesNotThrow(alert::findDescription);
        assertDoesNotThrow(alert::findPrice);
        assertDoesNotThrow(alert::findUrl);
        assertDoesNotThrow(alert::findImageUrl);
        assertDoesNotThrow(alert::findIcon);

        // Checking correspondence between SUT and model by checking icon name
        int alertIndex = alertType.getValue() - 1;
        String iconUrl = alert.findIcon();
        assertTrue(iconUrl.contains(iconNames[alertIndex]), "The model's alert type for the added alert does not match the SUT's displayed alert icon.");
    }

    private void doAddAlertValidation() throws PublisherException, IOException {
        EventLog log = marketAlertUmService.getEventLog().get(0);
        // Validating log type and add
        assertEquals(EventLogTypeEnum.ALERT_CREATED.getValue(), log.getEventLogType(), "The model's state does not match the SUT's after adding alert.");
        assertNotNull(log.getSystemState().getAlerts());
        assertFalse(log.getSystemState().getAlerts().isEmpty());
        // Validating alert type
        assertEquals(alertType.getValue(), log.getSystemState().getAlerts().get(0).getAlertType(), "The model's alert type for the added alert does not match the SUT's.");
    }

    @Test
    public void AlertValidatorModelRunner() {
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
        tester.generate(30);
        tester.printCoverage();
    }
}
