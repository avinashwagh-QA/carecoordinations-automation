package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.constants.OtpUserContext;
import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.factory.DriverFactory;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.OtpVerifyPage;
import com.carecoordination.healthcare.pages.landingPages.RegisterPage;
import com.carecoordination.healthcare.utilities.ConfigReader;
import com.carecoordination.healthcare.utilities.OtpAPIUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestDataProvider;


public class RegisterPageTest extends BaseTest {

    private LandingPage landingPage;
    private RegisterPage registerPage;
    private OtpVerifyPage otpVerifyPage;
    private OtpAPIUtil otpAPIUtil;

    private static final Logger logger = LogManager.getLogger(RegisterPageTest.class);

    @BeforeMethod
    public void setUpPages(){
        landingPage = new LandingPage(actionDriver);
        registerPage = new RegisterPage(actionDriver);
        otpVerifyPage = new OtpVerifyPage(actionDriver);
        otpAPIUtil = new OtpAPIUtil();

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

       Assert.assertTrue(otpVerifyPage.isTitleForOtpPageDisplayed(), "Title for OTP verification page not displayed");
    }


    @Test(groups = "skip-login",
    description = "Verify Message on invalid OTP is enter from register page")
    public void verifyMessageOnInvalidOtp(){

        String invitationCode = ConfigReader.getProperty("validCode");
        String email = ConfigReader.getProperty("validInvitedEmail");

        registerPage.completeRegistration(invitationCode, email);

        Assert.assertTrue(otpVerifyPage.isTitleForOtpPageDisplayed(), "Title for OTP verification page not displayed");

        otpVerifyPage.setOTPInputs(ConfigReader.getProperty("invalidOtp"));

        String actualMSg = registerPage.getErrorMessage();
        String expectedMsg = ConfigReader.getProperty("incorrectOtpMsg");

        Assert.assertEquals(actualMSg, expectedMsg, "Message does not match");
    }

    @Test(groups = "skip-login",
            description = "Verify OTP-Resend link is displayed on the OTP verify page when user navigates to register page")
    public void verifyResendOtpLinkIsPresent(){

        String invitationCode = ConfigReader.getProperty("validCode");
        String email = ConfigReader.getProperty("validInvitedEmail");

        registerPage.completeRegistration(invitationCode, email);

        Assert.assertTrue(otpVerifyPage.isTitleForOtpPageDisplayed(), "Title for OTP verification page not displayed");

        Assert.assertTrue(otpVerifyPage.isResendOtpDisplayedOnRegisterPage(), "Resend otp link is not displayed on OTP page ");

    }

    @Test(groups = "skip-login",
    description = "Verify old OTP becomes invalid after resend ")
    public void verifyOldOtpExpiresAfterResend(){

        logger.info("Verify on resend OTP the old Otp expire successfully");

        String invitationCode = ConfigReader.getProperty("validCode");
        String email = ConfigReader.getProperty("validInvitedEmail");

        registerPage.completeRegistration(invitationCode, email);

        Assert.assertTrue(otpVerifyPage.isTitleForOtpPageDisplayed(), "Title for OTP verification page not displayed");

        //Fetching 1st OTP in otp1
        String otp1 = otpAPIUtil.getOtp(email, OtpUserContext.UNREGISTERED_USER);
        logger.info("The OTP on first attempt is {}", otp1);

        //Verify resend OTP link and click
        Assert.assertTrue(otpVerifyPage.isResendOtpDisplayedOnRegisterPage(), "ResendOTP link is not present");
        otpVerifyPage.clickOnResendOtpForRegisterPage();

        // Enter old OTP
        otpVerifyPage.setOTPInputs(otp1);

        String actualMSg = registerPage.getErrorMessage();
        String expectedMsg = ConfigReader.getProperty("incorrectOtpMsg");

        Assert.assertEquals(actualMSg, expectedMsg, "Message does not match");

    }





}


