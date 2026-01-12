package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.pages.AppDashBoard.AppDashboardPage;
import com.carecoordination.healthcare.pages.landingPages.ForgotPasswordPage;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.LoginPage;
import com.carecoordination.healthcare.utilities.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestDataProvider;

public class ForgotPasswordTest extends BaseTest {

    private LandingPage landingPage;
    private LoginPage loginPage;
    private AppDashboardPage appDashboardPage;
    private ForgotPasswordPage forgotPasswordPage;

    private static final Logger logger = LogManager.getLogger(ForgotPasswordTest.class);

    @BeforeMethod
    public void setupPages() {
        landingPage = new LandingPage(actionDriver);
        loginPage = new LoginPage(actionDriver);
        appDashboardPage = new AppDashboardPage(actionDriver);
        forgotPasswordPage = new ForgotPasswordPage(actionDriver);

        landingPage.clickOnLoginLink();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login Page does not displayed");
    }

    @Test(groups = "skip-login", description = "Verify 'Forgotten Password' link is available in the Login page and is working")
    public void verifyForgotPasswordIsDisplayed() {

        landingPage.clickOnLoginLink();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login Page does not displayed");

        Assert.assertTrue(forgotPasswordPage.isForgotLinkDisplayed(), "Forgot password link does not displayed");

        forgotPasswordPage.clickOnForgotPassword();
        Assert.assertTrue(forgotPasswordPage.isForgotPasswordDisplayed(), "Forgot password page does not displayed");
    }

    @Test(groups = "skip-login", dataProvider = "invalidForgotPasswordMobileData",
            description = "Verify invalid mobile number scenarios on forgot password",
            dataProviderClass= TestDataProvider.class)
    public void verifyInvalidMobileNumberOnForgotPassword(String countryCodeKey,
                                         String phoneNumberKey,
                                         String expectedMsgKey){


        Assert.assertTrue(forgotPasswordPage.isForgotLinkDisplayed(), "Forgot password link does not displayed");

        forgotPasswordPage.clickOnForgotPassword();

        Assert.assertTrue(forgotPasswordPage.isForgotPasswordDisplayed(), "Forgot password page does not displayed");

        String countryCode  = ConfigReader.getProperty(countryCodeKey);
        String phoneNumber  = ConfigReader.getProperty(phoneNumberKey);

        forgotPasswordPage.setPhoneNumberForgotPassword(countryCode, phoneNumber );

        String actualMsg = forgotPasswordPage.getErrorMessage();
        String expectedMsg = ConfigReader.getProperty(expectedMsgKey);

        Assert.assertEquals(actualMsg, expectedMsg, "Message does not match on Forgot password");

    }

    /**
     *  Common method to navigate the Verify OTP page
     */

    public void navigateToVerificationOtpPage() {

        Assert.assertTrue(forgotPasswordPage.isForgotLinkDisplayed(), "Forgot password link does not displayed");

        forgotPasswordPage.clickOnForgotPassword();

        Assert.assertTrue(forgotPasswordPage.isForgotPasswordDisplayed(), "Forgot password page does not displayed");

        String countryCode = ConfigReader.getProperty("countryCode");
        String phoneNumber = ConfigReader.getProperty("validPhoneNumber");

        forgotPasswordPage.setPhoneNumberForgotPassword(countryCode, phoneNumber);

        forgotPasswordPage.clickOnVerifyPhoneNumber();

    }

    @Test(groups = "skip-login",
            description = "Verify OTP Verification page title is displayed as OTP Verification")
    public void verifyOtpVerificationPageIsDisplayed() {

        navigateToVerificationOtpPage();

        Assert.assertEquals(forgotPasswordPage.getOtpVerificationTitle(),
                "OTP Verification", "Title does not match");
    }

    @Test(groups = "skip-login",
            description = "Verify the resent OTP-Link is displayed on the OTP verification page")
    public void verifyResendOtpLinkIsPresent() {

        navigateToVerificationOtpPage();

        Assert.assertTrue(forgotPasswordPage.isResendOtpDisplayed(), "ResendOTP link is not present");
    }

    @Test(groups = "skip-login",
            description = "Verify the message on entering an invalid-OTP in forgot password")
    public void verifyMessageOnInvalidOtp() {

        navigateToVerificationOtpPage();

        forgotPasswordPage.setOTPInputs(ConfigReader.getProperty("invalidOtp"));

        String actualMSg = forgotPasswordPage.getErrorMessage();
        String expectedMsg = ConfigReader.getProperty("incorrectOtpMsg");

        Assert.assertEquals(actualMSg, expectedMsg, "Message does not match");
    }














}
