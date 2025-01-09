package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this); // Initialize WebElements
    }

    // Locators using @FindBy
    @FindBy(xpath = "//span[normalize-space()='My Account']")
    WebElement lnkMyAccount;

    @FindBy(xpath = "//a[normalize-space()='Register']")
    WebElement lnkRegister;
    
    @FindBy(linkText = "Login") // Login link added 
    WebElement linkLogin;
    // Action methods
    public void clickMyAccount() {
        lnkMyAccount.click(); // Click on 'My Account'
    }

    public void clickRegister() {
        lnkRegister.click(); // Click on 'Register'
    }
    public void clickLogin() {
        linkLogin.click(); // Click on 'login
    }
    
}
