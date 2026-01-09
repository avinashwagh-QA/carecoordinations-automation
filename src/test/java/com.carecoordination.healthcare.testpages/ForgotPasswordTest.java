package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.pages.AppDashBoard.AppDashboardPage;
import com.carecoordination.healthcare.pages.landingPages.ForgotPasswordPage;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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









}
