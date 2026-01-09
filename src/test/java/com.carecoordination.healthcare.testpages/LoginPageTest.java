package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.helpers.AuthHelper;
import com.carecoordination.healthcare.pages.AppDashBoard.AppDashboardPage;
import com.carecoordination.healthcare.pages.landingPages.ForgotPasswordPage;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.LoginPage;
import com.carecoordination.healthcare.utilities.ConfigReader;
import com.carecoordination.healthcare.utilities.RoleUserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestDataProvider;

public class LoginPageTest extends BaseTest {

    private LandingPage landingPage;
    private LoginPage loginPage;
    private AppDashboardPage appDashboardPage;
    private ForgotPasswordPage forgotPasswordPage;

    private static final Logger logger = LogManager.getLogger(LoginPageTest.class);

    @BeforeMethod
    public void setupPages() {
        landingPage = new LandingPage(actionDriver);
        loginPage = new LoginPage(actionDriver);
        appDashboardPage = new AppDashboardPage(actionDriver);
        forgotPasswordPage = new ForgotPasswordPage(actionDriver);

        landingPage.clickOnLoginLink();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login Page does not displayed");
    }

    @Test(groups = {"skip-login"},
    description = "Verify logging into the Application using valid credentials")
    public void verifyLoginWithValidCredential(){

        landingPage.clickOnLoginLink();

        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login Page does not displayed");

        String email = ConfigReader.getProperty("email");
        String password = ConfigReader.getProperty("password");
        loginPage.login(email, password);

        Assert.assertTrue(appDashboardPage.isUserNamePresent(), "User name does not displayed");

        appDashboardPage.clickOnLogOut();
        Assert.assertTrue(landingPage.isHomePageTitleDisplayed(), "Home page title not displayed, user not logout");
    }


    @Test(groups = "skip-login", description = "Verify 'Forgotten Password' link is available in the Login page and is working")
    public void verifyForgotPasswordIsDisplayed() {

        landingPage.clickOnLoginLink();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login Page does not displayed");

        Assert.assertTrue(forgotPasswordPage.isForgotLinkDisplayed(), "Forgot password link does not displayed");

        forgotPasswordPage.clickOnForgotPassword();
        Assert.assertTrue(forgotPasswordPage.isForgotPasswordDisplayed(), "Forgot password page does not displayed");
    }


    @Test(dataProvider = "invalidLoginData", groups = {"skip-login"},
    description = "Verify login error messages for invalid scenarios",
            dataProviderClass = TestDataProvider.class)
    public void VerifyInvalidLoginScenario(String emailKey,
                                           String passwordKey,
                                           String expectedMsgKey){


        String email = ConfigReader.getProperty(emailKey);
        String password = ConfigReader.getProperty(passwordKey);

        loginPage.login(email, password);

        String actualMsg = loginPage.getErrorMessage();
        String expectedMsg = ConfigReader.getProperty(expectedMsgKey);

        Assert.assertEquals(actualMsg, expectedMsg);

    }

    @Test(dataProvider = "roleBasedLogin", groups = {"skip-login"},
            description = "Verify Role-based login using user name identification",
            dataProviderClass = TestDataProvider.class)
    public void verifyRoleBasedLogin(UserRole role) {

        //Log in as per role wise
        AuthHelper.loginAs(role, landingPage, loginPage);

        Assert.assertTrue(appDashboardPage.isDashboardHomeDisplayed(), "Dashboard does not displayed for user role:" + role);

        String actualUserName = appDashboardPage.getUserName();
        String expectedUserName = RoleUserMapper.expectedUserName(role);

        Assert.assertEquals(actualUserName, expectedUserName, "Logged-in user mismatch for role:" + role);

        //log out after login
        appDashboardPage.clickOnLogOut();
        Assert.assertTrue(landingPage.isHomePageTitleDisplayed(), "Home page title not displayed, user not logout");
    }


}
