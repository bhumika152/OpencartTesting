package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    // Constructor to initialize WebDriver and WebElements
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Locators using @FindBy
    @FindBy(xpath = "//input[@id='input-email']")
    private WebElement txtEmailAddress;

    @FindBy(xpath = "//input[@id='input-password']")
    private WebElement txtPassword;

    @FindBy(xpath = "//input[@value='Login']")
    private WebElement btnLogin;

    // Action method to set email
    public void setEmail(String email) {
        txtEmailAddress.sendKeys(email);
    }

    // Action method to set password
    public void setPassword(String pwd) {
        txtPassword.sendKeys(pwd);
    }

    // Action method to click the login button
    public void clickLogin() {
        btnLogin.click();
    }
}
