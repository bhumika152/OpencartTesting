package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;


public class TC002_loginTest extends BaseClass {

    @Test(groups = {"Sanity" , "Master"})
    public void verify_login() {
        logger.info("**** Starting TC_002_LoginTest *****");
 
        try {
            // Navigate to Home Page and click on My Account
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            logger.info("My Account clicked");

            // Click on Login link
            hp.clickLogin();
            logger.info("Login link clicked");

            // Initialize LoginPage and perform login
            LoginPage lp = new LoginPage(driver);
           
            lp.setEmail(p.getProperty("email"));
            lp.setPassword(p.getProperty("password"));
            
            lp.clickLogin();
            logger.info("Login credentials entered and login button clicked");
             Thread.sleep(5000);            // Initialize MyAccountPage and verify successful login
            MyAccountPage macc = new MyAccountPage(driver);
            boolean targetPage = macc.isMyAccountPageExists();
            
            // Assert if the My Account page is displayed
            Assert.assertTrue(targetPage);
            logger.info("Login successful and My Account page displayed");

        } catch (Exception e) {
            logger.error("Test Failed: " + e.getMessage());
            Assert.fail();
        }
    }
}
