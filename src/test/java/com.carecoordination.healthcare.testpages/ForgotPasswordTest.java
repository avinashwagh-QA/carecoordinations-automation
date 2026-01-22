package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.constants.OtpUserContext;
import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.factory.DriverFactory;
import com.carecoordination.healthcare.pages.landingPages.*;
import com.carecoordination.healthcare.utilities.ConfigReader;
import com.carecoordination.healthcare.utilities.OtpAPIUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestDataProvider;

public class ForgotPasswordTest extends BaseTest {

    private LandingPage landingPage;
    private LoginPage loginPage;
    private ForgotPasswordPage forgotPasswordPage;
    private OtpAPIUtil otpAPIUtil;
    private ResetPasswordPage resetPasswordPage;
    private OtpVerifyPage otpVerifyPage;

    private static final Logger logger = LogManager.getLogger(ForgotPasswordTest.class);

    @BeforeMethod
    public void setupPages() {
        landingPage = new LandingPage(actionDriver);
        loginPage = new LoginPage(actionDriver);
        forgotPasswordPage = new ForgotPasswordPage(actionDriver);
        otpAPIUtil = new OtpAPIUtil();
        resetPasswordPage = new ResetPasswordPage(actionDriver);
        otpVerifyPage = new OtpVerifyPage(actionDriver);

        landingPage.clickOnLoginLink();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login Page does not displayed");
    }

    @Test(groups = "skip-login", description = "Verify 'Forgotten Password' link is available in the Login page and is working")
    public void verifyForgotPasswordIsDisplayed() {

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

    @Test(groups = "skip-login",
            description = "Verify OTP Verification page title is displayed as OTP Verification")
    public void verifyOtpVerificationPageIsDisplayed() {

        forgotPasswordPage.navigateToVerificationOtpPageFromForgotPassword();

        Assert.assertEquals(otpVerifyPage.getOtpPageTitle(),
                "OTP Verification", "Title does not match");
    }

    @Test(groups = "skip-login",
            description = "Verify the resend OTP-Link is displayed on the OTP verification page")
    public void verifyResendOtpLinkIsPresent() {

        boolean otpPage = forgotPasswordPage.navigateToVerificationOtpPageFromForgotPassword(); // navigate otp page
        Assert.assertTrue(otpPage, "OTP verification page does not displayed");

        Assert.assertTrue(otpVerifyPage.isResendOtpDisplayedOnForgotPassword(), "ResendOTP link is not present");
    }

    @Test(groups = "skip-login",
            description = "Verify the message on entering an invalid-OTP in forgot password")
    public void verifyMessageOnInvalidOtp() {

        boolean otpPage = forgotPasswordPage.navigateToVerificationOtpPageFromForgotPassword(); // navigate otp page
        Assert.assertTrue(otpPage, "OTP verification page does not displayed");

        otpVerifyPage.setOTPInputs(ConfigReader.getProperty("invalidOtp"));

        String actualMSg = forgotPasswordPage.getErrorMessage();
        String expectedMsg = ConfigReader.getProperty("incorrectOtpMsg");

        Assert.assertEquals(actualMSg, expectedMsg, "Message does not match");
    }

    @Test(groups = "skip-login",
            description = "Verify the entering valid OTP navigates to Rest password page")
    public void verifyEnteringValidOtpNavigatesToResetPassword() {

        logger.info("Verifying valid OTP navigates to reset password page");

        boolean otpPage = forgotPasswordPage.navigateToVerificationOtpPageFromForgotPassword(); // navigate otp page
        Assert.assertTrue(otpPage, "OTP verification page does not displayed");

        String email = ConfigReader.getProperty("forgot.password.email");
        String otp = otpAPIUtil.getOtp(email,OtpUserContext.REGISTERED_USER);

        otpVerifyPage.setOTPInputs(otp);

        String actualTitle = resetPasswordPage.getResetPasswordPageTitle();
        String expectedTitle = ConfigReader.getProperty("resetPasswordPageTitle");

        Assert.assertEquals(actualTitle.trim().toLowerCase(), expectedTitle.toLowerCase(), "Title does not match");

    }

    @Test(groups = "skip-login",
            description = "Verify old OTP becomes invalid after resend OTP")
    public void verifyOldOtpExpiresAfterResend() {

        logger.info("Verify on resend OTP the old Otp expire successfully");

        boolean otpPage = forgotPasswordPage.navigateToVerificationOtpPageFromForgotPassword(); // navigate otp page
        Assert.assertTrue(otpPage, "OTP verification page does not displayed");

        //Fetching 1st OTP in otp1
        String email = ConfigReader.getProperty("forgot.password.email");

        String otp1 = otpAPIUtil.getOtp(email,OtpUserContext.REGISTERED_USER);
        logger.info("The OTP on first attempt is {}", otp1);

        // Verify resend OTP link and click
        Assert.assertTrue(otpVerifyPage.isResendOtpDisplayedOnForgotPassword(), "ResendOTP link is not present");
        otpVerifyPage.clickOnResendOtpForForgotPassword();

        //Fetching 2nd OTP in otp2
        String otp2 = otpAPIUtil.getOtp(email,OtpUserContext.REGISTERED_USER);
        logger.info("The OTP on first attempt is {}", otp2);

        // Enter old OTP
        otpVerifyPage.setOTPInputs(otp1);

        String actualMSg = otpVerifyPage.getErrorMessage();
        String expectedMsg = ConfigReader.getProperty("incorrectOtpMsg");

        Assert.assertEquals(actualMSg, expectedMsg, "Message does not match");
    }


    @Test(groups = "skip-login",
        description = "Verify browser back from reset password navigates to home page")
    public void verifyBrowserBackFromResetPasswordNavigatesToHomePage(){

        logger.info("Verify browser back from reset password navigates to home page");

        boolean otpPage = forgotPasswordPage.navigateToVerificationOtpPageFromForgotPassword(); // navigate otp page
        Assert.assertTrue(otpPage, "OTP verification page does not displayed");

        String email = ConfigReader.getProperty("forgot.password.email");

        String otp = otpAPIUtil.getOtp(email,OtpUserContext.REGISTERED_USER);

        otpVerifyPage.setOTPInputs(otp);
        String actualTitle = resetPasswordPage.getResetPasswordPageTitle();
        String expectedTitle = ConfigReader.getProperty("resetPasswordPageTitle");

        Assert.assertEquals(actualTitle.trim().toLowerCase(), expectedTitle.toLowerCase(), "Title does not match");

        actionDriver.waitForPageLoad(); // Wait for page to be load - reset password page
        actionDriver.navigateToBack(); // Navigating back
        actionDriver.waitForPageLoad(); // wait for page tove load after back navigation

        String actualURL = DriverFactory.getDriver().getCurrentUrl();
        String expectedURL = ConfigReader.getProperty("url");

        Assert.assertNotNull(actualURL);
        Assert.assertTrue(actualURL.startsWith(expectedURL),"Does not navigate to Home page");
    }














}
