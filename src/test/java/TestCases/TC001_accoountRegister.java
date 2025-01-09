package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_accoountRegister extends BaseClass {

    @Test(groups = {"Regression","Master"})
    public void verify_account_registration() {
        logger.info("Starting TC001 - Account Registration Test...");
        
        try {
            // Navigate to Registration Page
            HomePage hp = new HomePage(driver);
            logger.info("Navigating to My Account...");
            hp.clickMyAccount();
            logger.info("Clicking Register...");
            hp.clickRegister();

            // Fill Registration Form
            AccountRegistrationPage regPage = new AccountRegistrationPage(driver);
            logger.info("Filling in customer details...");
            regPage.setFirstName(randomString().toUpperCase());
            regPage.setLastName(randomString().toUpperCase());
            regPage.setEmail(randomString() + "@gmail.com");
            regPage.setTelephone(randomNumber(10));
            
            String password = randomAlphaNumeric();
            regPage.setPassword(password);
            regPage.setConfirmPassword(password);
            regPage.clickAgreeCheckbox();
            regPage.clickContinueButton();
            
            // Validate Confirmation Message
            logger.info("Validating expected confirmation message...");
            String confMsg = regPage.getConfirmationMsg();
            Assert.assertEquals(confMsg, "Your Account Has Been Created!", 
                "Confirmation message does not match!");
            
            logger.info("Account registration test passed.");
        } catch (Exception e) {
            logger.error("Test failed due to an exception: " + e.getMessage());
            Assert.fail("Exception occurred during test execution: " + e.getMessage());
        }
    }
}
