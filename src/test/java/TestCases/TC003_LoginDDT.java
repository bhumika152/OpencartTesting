package TestCases;

import org.testng.annotations.Test;
import Utilities.DataProviderDemo;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC003_LoginDDT extends BaseClass {
    // Logger instance
//    private static final Logger logger = LogManager.getLogger(TC003_LoginDDT.class);

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviderDemo.class, groups = "DataDriven")
    public void verify_loginDDT(String email, String password) {
        logger.info("Starting verify_loginDDT test with email: " + email);

        try {
            // Create an object for the HomePage
            HomePage hp = new HomePage(driver);
            logger.info("Navigating to the Login page.");
            
            // Navigate to Login Page
            hp.clickMyAccount();
            logger.info("Clicked on 'My Account' link.");
            hp.clickLogin();
            logger.info("Clicked on 'Login' link.");

            // Perform Login
            LoginPage lp = new LoginPage(driver);
            lp.setEmail(email); // Set email from the data provider
            logger.info("Entered email: " + email);
            lp.setPassword(password); // Set password from the data provider
            logger.info("Entered password.");
            lp.clickLogin();
            logger.info("Clicked on 'Login' button.");

            // Verify if MyAccount page is displayed
            MyAccountPage macc = new MyAccountPage(driver);
            boolean targetPage = macc.isMyAccountPageExists();

            // Assert the result
            if (targetPage) {
                logger.info("Login successful for email: " + email);
                macc.clickLogout(); // Logout if login is successful
                logger.info("Logged out successfully.");
            } else {
                logger.error("Login failed for email: " + email);
            }
        } catch (Exception e) {
            logger.error("An exception occurred during the login process for email: " + email, e);
        }

        logger.info("Finished verify_loginDDT test for email: " + email);
    }
}
