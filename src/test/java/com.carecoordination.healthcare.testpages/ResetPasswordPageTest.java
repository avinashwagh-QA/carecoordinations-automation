package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.constants.OtpUserContext;
import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.factory.DriverFactory;
import com.carecoordination.healthcare.pages.AppDashBoard.AppDashboardPage;
import com.carecoordination.healthcare.pages.landingPages.*;
import com.carecoordination.healthcare.utilities.ConfigReader;
import com.carecoordination.healthcare.utilities.OtpAPIUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestDataProvider;

public class ResetPasswordPageTest extends BaseTest {


    private ForgotPasswordPage forgotPasswordPage;
    private ResetPasswordPage resetPasswordPage;
    private OtpAPIUtil otpAPIUtil;
    private OtpVerifyPage otpVerifyPage;
    private LandingPage landingPage;
    private LoginPage loginPage;
    private AppDashboardPage appDashboardPage;

    private static final Logger logger = LogManager.getLogger(ResetPasswordPageTest.class);

    @BeforeMethod
    public void setUpPages() {
        landingPage = new LandingPage(actionDriver);
        loginPage = new LoginPage(actionDriver);
        appDashboardPage = new AppDashboardPage(actionDriver);
        forgotPasswordPage = new ForgotPasswordPage(actionDriver);
        resetPasswordPage = new ResetPasswordPage(actionDriver);
        otpVerifyPage = new OtpVerifyPage(actionDriver);
        otpAPIUtil = new OtpAPIUtil();

        landingPage.clickOnLoginLink();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login Page does not displayed");

        boolean otpPage = forgotPasswordPage.navigateToVerificationOtpPageFromForgotPassword(); // navigate otp page
        Assert.assertTrue(otpPage, "OTP verification page does not displayed");
        String email = ConfigReader.getProperty("forgot.password.email");
        String otp = otpAPIUtil.getOtp(email, OtpUserContext.REGISTERED_USER);

        otpVerifyPage.setOTPInputs(otp); // enter otp page
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
        boolean button = resetPasswordPage.verifySubmitButtonEnabled();
        Assert.assertFalse(button, "Submit button displayed as enabled by default");

    }

    @Test(groups = "skip-login",
    dataProvider = "passwordValidationData", dataProviderClass = TestDataProvider.class,
    description = "Verify the Rules on the Rest password page for password validation")
    public void validatePasswordRule(String password, boolean expectedSubmitState){

        resetPasswordPage.setInputNewPassword(password);
        resetPasswordPage.setInputConfirmPassword(password);

        Assert.assertEquals(resetPasswordPage.verifySubmitButtonEnabled(),
                expectedSubmitState,
                "Submit button state mismatch for password: " + password);

    }

    @Test(groups = "skip-login",
    description = "Verify the button remains when password and confirm password are mismatch")
    public void verifyPasswordMismatchOnResetPassword(){

        resetPasswordPage.setInputNewPassword("Password@123");
        resetPasswordPage.setInputConfirmPassword("Password@123");

        Assert.assertTrue(resetPasswordPage.verifySubmitButtonEnabled(),
                "Submit button should remains disable for mismatch password");
    }

    @Test(groups = "skip-login",
    description = "Verify on after entering Old password then error message displayed")
    public void validateResetPasswordOnOldPassword(){

        resetPasswordPage.setInputNewPassword("Password@123");
        resetPasswordPage.setInputConfirmPassword("Password@123");

        Assert.assertTrue(resetPasswordPage.verifySubmitButtonEnabled(), "button not enabled even enter same password");

        resetPasswordPage.clickOnSubmitButton();

        String actualMsg = ConfigReader.getProperty("restOldPasswordMessage");
        String expectedMsg = resetPasswordPage.getErrorMsgOnOldPassword();

        Assert.assertEquals(actualMsg, expectedMsg,"Message does not match on enter old password in reset password");
    }


    @Test(groups = "skip-login",
            description = "Verify on after entering valid password and confirm password user navigates to login page")
    public void validateResetPasswordSuccess(){

        resetPasswordPage.setInputNewPassword("Password@123");
        resetPasswordPage.setInputConfirmPassword("Password@123");

        Assert.assertTrue(resetPasswordPage.verifySubmitButtonEnabled(), "button not enabled even enter same password");

        resetPasswordPage.clickOnSubmitButton();

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("login"),
                "User should be redirected to login page after reset password");
    }

    @Test(groups = "skip-login",
            description = "Verify after reset password user can login with new password")
    public void validateResetPasswordUserCanLogin() {

        String updatedPassword = "Nexios@1419";
        resetPasswordPage.setInputNewPassword(updatedPassword);
        resetPasswordPage.setInputConfirmPassword(updatedPassword);

        Assert.assertTrue(resetPasswordPage.verifySubmitButtonEnabled(), "button not enabled even enter same password");

        resetPasswordPage.clickOnSubmitButton();

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("login"),
                "User should be redirected to login page after reset password");

        String email = ConfigReader.getProperty("resetEmail");

        loginPage.login(email, updatedPassword);

        Assert.assertTrue(appDashboardPage.isUserNamePresent(), "User name does not displayed");

        appDashboardPage.clickOnLogOut();
        Assert.assertTrue(landingPage.isHomePageTitleDisplayed(), "User not logout after reset password");

    }





}
