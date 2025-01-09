package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

    // Constructor to initialize WebDriver and WebElements
    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    // Locator for My Account page heading
    @FindBy(xpath = "//h2[text()='My Account']")
    private WebElement msgHeading;

    @FindBy(xpath = "//div[@class='list-group']//a[text()='Logout']") 
    WebElement InkLogout;
    // Method to check if My Account page is displayed
    public boolean isMyAccountPageExists() {
        try {
            return msgHeading.isDisplayed(); // Returns true if the heading is displayed
        } catch (Exception e) {
            return false; // Returns false if any exception occurs (e.g., element not found)
        }
    }
    public void clickLogout() {
    	InkLogout.click();
    }
}
