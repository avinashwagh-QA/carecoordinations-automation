package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.RegisterPage;
import com.carecoordination.healthcare.utilities.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestDataProvider;


public class RegisterPageTest extends BaseTest {

    private LandingPage landingPage;
    private RegisterPage registerPage;

    private static final Logger logger = LogManager.getLogger(RegisterPageTest.class);

    @BeforeMethod
    public void setUpPages(){
        landingPage = new LandingPage(actionDriver);
        registerPage = new RegisterPage(actionDriver);

        landingPage.clickOnRegisterLink();
        Assert.assertTrue(registerPage.isRegisterPageDisplayed(), "Register page does not displayed");
    }

    @Test(groups = "skip-login",
    description = "Verify basic UI on default register screen")
    public void VerifyDefaultUIonRegisterPage(){

        // Verify Title
        Assert.assertEquals(registerPage.getRegisterPageTitle(), "Register", "Title on register page does not displayed");

        //Verify Invitation code and Email field is displayed
        Assert.assertTrue(registerPage.isInputInvitationCodeAndEmailFieldDisplayed(),
                "Invitation and Email does not displayed on Register page");

        //Verify Default state for Continue button is disabled
        Assert.assertFalse(registerPage.verifyContinueButtonEnabled(), "continue button on register page appear to enabled by default");

    }

    @Test(groups = "skip-login", dataProvider = "invalidRegisterAccountData",
    dataProviderClass = TestDataProvider.class,
    description = "Verify the Register page with valid and invalid email and invitation code")
    public void validateRegisterPageWithInvitationEmailCombination(String invitationCode,
                                                                   String emailKey,
                                                                   String expectedMsgKey){

        // Verify Title
        Assert.assertEquals(registerPage.getRegisterPageTitle(), "Register", "Title on register page does not displayed");

        String invCode = ConfigReader.getProperty(invitationCode);
        String email = ConfigReader.getProperty(emailKey);

        registerPage.completeRegistration(invCode, email);

        String actualMessage= registerPage.getErrorMessage();
        String expectedMessage = ConfigReader.getProperty(expectedMsgKey);

        Assert.assertEquals(actualMessage, expectedMessage, "On register page the message does not match");

    }


    @Test(groups = "skip-login",
    description = "Verify the Login link present on the register page")
    public void validateRegisterPageContainsLoginLink(){

        Assert.assertTrue(registerPage.isLoginLinkDisplayed(), "Login link does not displayed on register page");
    }


    @Test(groups = "skip-login",
    description = "Verify register guide displayed on register page")
    public void validateRegisterPageDisplayedRegisterGuide(){

        Assert.assertTrue(registerPage.isRegisterGuideDisplayed(), "Register guide does nor displayed on register page");
        Assert.assertTrue(registerPage.isRegisterGuideModalOpenWhenClicked(), "Register guide modal does not displayed");
    }

    @Test(groups = "skip-login",
    description = "Verify User navigates Verify OTP page on entering valid email and invitation code")
    public void validateUserNavigatesToOtpPageOnValidCodeAndEmail(){

       String invitationCode = ConfigReader.getProperty("validCode");
       String email = ConfigReader.getProperty("validInvitedEmail");

       registerPage.completeRegistration(invitationCode, email);

    }






}
