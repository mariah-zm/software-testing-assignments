package org.productalerter;

import org.openqa.selenium.chrome.ChromeDriver;
import org.productalerter.model.ChoiceEnum;
import org.productalerter.model.http.CreatorResponse;
import org.productalerter.pageobjects.LoginPageObject;
import org.productalerter.scraper.MaltaParkScraper;
import org.productalerter.service.HttpService;
import org.productalerter.service.MarketAlertUmCreator;
import org.productalerter.service.MarketAlertUmPublisher;

import java.util.Scanner;

public class Runner {

    private static final String DRIVER_PATH = "src/main/resources/chromedriver";
    private static final String USER_ID = "b9ed2dbc-141a-4395-921d-ee8779610e1f";
    private final String MARKET_ALERT_UM_URL = "https://www.marketalertum.com/";
    private final String ALERTS_URL = "https://www.marketalertum.com/Alerts/List";
    private final String LOGOUT_URL = "https://www.marketalertum.com/Home/Logout";

    private final ChromeDriver marketAlertUmDriver;
    private final MarketAlertUmCreator creator;
    private final Scanner scanner = new Scanner(System.in);

    public Runner(MaltaParkScraper scraper, MarketAlertUmPublisher publisher) {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        this.marketAlertUmDriver = new ChromeDriver();
        this.creator = new MarketAlertUmCreator(scraper, publisher);
    }

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        ChromeDriver scraperDriver = new ChromeDriver();
        HttpService httpService = new HttpService();
        MaltaParkScraper scraper = new MaltaParkScraper(scraperDriver);
        MarketAlertUmPublisher publisher = new MarketAlertUmPublisher(httpService, USER_ID);
        Runner runner = new Runner(scraper, publisher);

        ChoiceEnum choice;

        do {
            runner.printMenu();

            int choiceValue = runner.scanner.nextInt();
            // Consuming new line
            runner.scanner.nextLine();

            choice = ChoiceEnum.getEnumValue(choiceValue);

            if (choice == null) {
                System.out.println("Invalid Option!");
                continue;
            }

            switch (choice) {
                case LOG_IN:
                    runner.login();
                    break;
                case LOG_OUT:
                    runner.logout();
                    break;
                case ADD_ALERT:
                    runner.addAlerts();
                    break;
                case DELETE_ALERTS:
                    runner.deleteAlerts();
                    break;
                case VIEW_ALERTS:
                    runner.viewAlerts();
                    break;
                case EXIT:
                    System.out.println("Exiting Program");
            }

        } while (choice != ChoiceEnum.EXIT);

        scraperDriver.quit();
        runner.marketAlertUmDriver.quit();
    }

    private void printMenu() {
        System.out.println("Select Option:\n" +
                "1. LOG IN\n" +
                "2. LOG OUT\n" +
                "3. ADD ALERT\n" +
                "4. DELETE ALERTS\n" +
                "5. VIEW ALERTS\n" +
                "6. EXIT");
    }

    private void login() {
        this.marketAlertUmDriver.navigate().to(MARKET_ALERT_UM_URL);
        new LoginPageObject(this.marketAlertUmDriver).login(USER_ID);
    }

    private void logout() {
        this.marketAlertUmDriver.navigate().to(LOGOUT_URL);
    }

    private void addAlerts() {
        System.out.println("Search products by:\n" +
                "1. Category\n" +
                "2. Name");

        CreatorResponse response;
        int choice = this.scanner.nextInt();
        // Consuming new line
        this.scanner.nextLine();

        switch (choice) {
            case 1:
                String category = getSearchValue("Enter category name");
                response = creator.searchForProductsByCategory(category, 1);
                break;
            case 2:
                String name = getSearchValue("Enter product name");
                response = creator.searchForProductsByName(name , 1);
                break;
            default:
                System.out.println("Invalid option. Returning back to menu");
                return;
        }

        if (response != null) {
            int diff = response.getNumProductsRequested() - response.getAlertsAffected();
            if (diff != 0) {
                System.out.println("Error encountered when adding " + diff + " alerts.");
            }
            System.out.println("Added " + response.getNumProductsRequested() + " alerts.");
        } else {
            System.out.println("Error adding alerts.");
        }
    }

    private void deleteAlerts() {
        System.out.println("Deleting all alerts...");
        CreatorResponse response = creator.deleteAlerts();
        if (response.getErrorMessages() == null) {
            System.out.println("Deleted " + response.getAlertsAffected() + " alerts.");
        } else {
            System.out.println("Error deleting alerts: " + response.getErrorMessages().get(0));
        }
    }

    private void viewAlerts() {
        this.marketAlertUmDriver.navigate().to(ALERTS_URL);
    }

    private String getSearchValue(String message) {
        System.out.println(message + ": ");
        return scanner.nextLine();
    }

}
