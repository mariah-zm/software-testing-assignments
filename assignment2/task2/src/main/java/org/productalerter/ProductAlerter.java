package org.productalerter;

import java.io.IOException;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.productalerter.exception.PublisherException;
import org.productalerter.model.domain.CategoryEnum;
import org.productalerter.model.domain.Product;
import org.productalerter.pageobjects.LoginPageObject;
import org.productalerter.service.HttpService;
import org.productalerter.service.MarketAlertUmPublisher;

public class ProductAlerter {

	public static final String DRIVER_PATH = "src/main/resources/chromedriver";
    public static final String USER_ID = "b9ed2dbc-141a-4395-921d-ee8779610e1f";
    public final String HOME_URL = "https://www.marketalertum.com/";
    public final String ALERTS_URL = "https://www.marketalertum.com/Alerts/List";
    public final String LOGOUT_URL = "https://www.marketalertum.com/Home/Logout";

    private final WebDriver driver;
    private final MarketAlertUmPublisher publisher;

    // Variables required by system
    private boolean isLoggedIn;
    private int numOfAlerts;

    public ProductAlerter(MarketAlertUmPublisher publisher) throws InterruptedException, PublisherException, IOException {
        driver = new ChromeDriver();
        this.publisher = publisher;

        // Deleting all alerts on system start up
        publisher.deleteAllAlerts();
    }

    public void login(String credentials) {
        isLoggedIn = new LoginPageObject(driver).login(credentials);
    }

    public void logout() {
        driver.navigate().to(LOGOUT_URL);
        isLoggedIn = !driver.getCurrentUrl().equals(HOME_URL);
    }

    public void addAlert() {
        try {
            Product dummyProduct = getDummyProduct();
            publisher.publishAlert(dummyProduct);
            System.out.println("Alert added.");
            numOfAlerts++;
        } catch (Exception ex) {
            System.out.println("Error adding alert: " + ex.getMessage());
        }
    }

    public void deleteAlerts() {
        try {
            String message = publisher.deleteAllAlerts();
            int numAlertsDeleted = Integer.parseInt(message.replaceAll("[^0-9]", ""));
            System.out.println("Deleted " + numAlertsDeleted + " alerts.");
            numOfAlerts = numOfAlerts - numAlertsDeleted;
        } catch (Exception ex) {
            System.out.println("Error deleting alerts: " + ex.getMessage());
        }
    }

    public void viewAlerts() {
        driver.navigate().to(ALERTS_URL);
    }

    public void viewHome() {
        driver.navigate().to(HOME_URL);
    }

    public void deleteDriver() {
        driver.quit();
    }

    public boolean isOnAlertsPage() {
        return driver.getCurrentUrl().equals(ALERTS_URL);
    }

    public boolean isOnHomePage() {
        return driver.getCurrentUrl().equals(HOME_URL);
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().equals(LoginPageObject.LOGIN_URL);
    }
    
    public static void main(String[] args) {
    	System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
    	
    	Scanner scanner = new Scanner(System.in);
    	MarketAlertUmPublisher publisher = new MarketAlertUmPublisher(new HttpService(), USER_ID);
    	ProductAlerter runner;
    	
    	try {
			runner = new ProductAlerter(publisher);
		} catch (Exception ex) {
			System.out.println("Failed to start system: " + ex.getMessage());
			return;
		}
    	
    	while (true) {
    		runner.printMenu();
    		
    		// Get menu choice from input
    		int choice = scanner.nextInt();
            // Consuming new line
            scanner.nextLine();
            
            switch (choice) {
	            case 1:
	                runner.login(USER_ID);
	                break;
	            case 2: 
	            	runner.login("user123");
	            	break;
	            case 3:
	                runner.logout();
	                break;
	            case 4:
	                runner.addAlert();
	                break;
	            case 5:
	                runner.deleteAlerts();
	                break;
	            case 6:
	                runner.viewAlerts();
	                break;
	            case 7:
	                runner.viewHome();
	                break;
	            default:
	                System.out.println("Invalid Option!");
            }
    	}
    }

    private Product getDummyProduct() {
        Product product = new Product();
        product.setCategory(CategoryEnum.CAR);
        product.setName("Name");
        product.setDescription("Description");
        product.setPriceInEuros(360000.0);
        product.setImageUrl("https://www.maltapark.com/asset/itemphotos/9521343/9521343_1.jpg/?x=TWF4Vz01NjMmTWF4SD00MjI=&_ts=10");
        product.setUrl("https://www.maltapark.com/item/details/9521343");

        return product;
    }
    
    private void printMenu() {
        System.out.println("****MAIN MENU****:\n" +
        	"Logged in: " + isLoggedIn + "\n" +
        	"Number of alerts: " + numOfAlerts + "\n" +
        	"1. VALID LOGIN\n" +
        	"2. INVALID LOGIN\n" +
        	"3. LOGOUT\n" +
        	"4. ADD ALERT\n" +
        	"5. DELETE ALERTS\n" +
        	"6. VIEW ALERTS\n" + 
        	"7. VIEW HOME");
    }

}
