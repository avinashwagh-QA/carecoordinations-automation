package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.pages.landingPages.ForgotPasswordPage;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.LoginPage;
import com.carecoordination.healthcare.pages.landingPages.ResetPasswordPage;
import com.carecoordination.healthcare.utilities.ConfigReader;
import com.carecoordination.healthcare.utilities.OtpAPIUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ResetPasswordPageTest extends BaseTest {

    private ForgotPasswordPage forgotPasswordPage;
    private ResetPasswordPage resetPasswordPage;
    private OtpAPIUtil otpAPIUtil;
    private LandingPage landingPage;
    private LoginPage loginPage;

    private static final Logger logger = LogManager.getLogger(ResetPasswordPageTest.class);

    @BeforeMethod
    public void setUpPages() {
        landingPage = new LandingPage(actionDriver);
        loginPage = new LoginPage(actionDriver);
        forgotPasswordPage = new ForgotPasswordPage(actionDriver);
        resetPasswordPage = new ResetPasswordPage(actionDriver);
        otpAPIUtil = new OtpAPIUtil();

        landingPage.clickOnLoginLink();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login Page does not displayed");

        boolean otpPage = forgotPasswordPage.navigateToVerificationOtpPage(); // navigate otp page
        Assert.assertTrue(otpPage, "OTP verification page does not displayed");

        forgotPasswordPage.setOTPInputs(otpAPIUtil.getOtp()); // enter otp page
    }

    @Test(groups = "skip-login",
            description = "Verify the Default UI for the reset password Page")
    public void validateResetPasswordFormInitialState() {

        // Validation for Reset Password Page Title
        String actualTitle = resetPasswordPage.getResetPasswordPageTitle();
        String expectedTitle = ConfigReader.getProperty("resetPasswordPageTitle");

        Assert.assertEquals(actualTitle.trim().toLowerCase(), expectedTitle.toLowerCase(), "Title does not match");

        //Validation for subtitle
        String actualSubTitle = resetPasswordPage.getSubTitleResetPassword();
        String expectedSubTitle = ConfigReader.getProperty("resetPasswordPageSubTitle");
        Assert.assertEquals(actualSubTitle.trim().toLowerCase(), expectedSubTitle.toLowerCase(), "SubTitle does not match");

        //Validation for Password and confirm password input field displayed
        boolean password =resetPasswordPage.isNewPasswordAndConfirmPasswordDisplayed();
        Assert.assertTrue(password, "Input field for password does not displayed..");

        //Validation on submit button initial state - disabled
        boolean button = resetPasswordPage.verifyButtonEnabled();
        Assert.assertFalse(button, "Submit button displayed as enabled by default");

    }


}
