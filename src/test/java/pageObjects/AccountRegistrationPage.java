package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage extends BasePage {

    // Constructor
    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this); // Initialize WebElements
    }

    // Locators using @FindBy
    @FindBy(xpath = "//input[@id='input-firstname']")
    private WebElement txtFirstname;

    @FindBy(xpath = "//input[@id='input-lastname']")
    private WebElement txtLastname;

    @FindBy(xpath = "//input[@id='input-email']")
    private WebElement txtEmail;

    @FindBy(xpath = "//input[@id='input-telephone']")
    private WebElement txtTelephone;

    @FindBy(xpath = "//input[@id='input-password']")
    private WebElement txtPassword;

    @FindBy(xpath = "//input[@id='input-confirm']")
    private WebElement txtConfirmPassword;

    @FindBy(xpath = "//input[@name='agree']")
    private WebElement chkAgree;

    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement btnContinue;

    private By confirmationMessageLocator = By.xpath("//h1[normalize-space()='Your Account Has Been Created!']");

    // Action methods
    public void setFirstName(String fname) {
        txtFirstname.sendKeys(fname);
    }

    public void setLastName(String lname) {
        txtLastname.sendKeys(lname);
    }

    public void setEmail(String email) {
        txtEmail.sendKeys(email);
    }

    public void setTelephone(String telephone) {
        txtTelephone.sendKeys(telephone);
    }

    public void setPassword(String password) {
        txtPassword.sendKeys(password);
    }

    public void setConfirmPassword(String confirmPassword) {
        txtConfirmPassword.sendKeys(confirmPassword);
    }

    public void clickAgreeCheckbox() {
        chkAgree.click();
    }

    public void clickContinueButton() {
        btnContinue.click();
    }

    // Method to retrieve confirmation message
    public String getConfirmationMsg() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessageLocator));
            return element.getText();
        } catch (Exception e) {
            return "Error retrieving confirmation message: " + e.getMessage();
        }
    }
}
