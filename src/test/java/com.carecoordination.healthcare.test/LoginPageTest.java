package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.context.UserContext;
import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.model.TestUser;
import com.carecoordination.healthcare.pages.modules.AppDashBoard.AppDashboardPage;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.LoginPage;
import com.carecoordination.healthcare.utilities.ConfigReader;
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

    private static final Logger logger = LogManager.getLogger(LoginPageTest.class);

    @BeforeMethod
    public void setupPages() {
        landingPage = new LandingPage(actionDriver);
        loginPage = new LoginPage(actionDriver);
        appDashboardPage = new AppDashboardPage(actionDriver);

       // landingPage.clickOnLoginLink();
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

    @Test(description = "Verify Role-based login using user name identification",
            dataProvider = "personaMatrix",  dataProviderClass = TestDataProvider.class)
    public void verifyRoleBasedLogin(String personaKey) {

        TestUser testUser = getCurrentUser();

        //Call default base test method to build user context
        UserContext userContext = getCurrentUserContext();

        logger.info("Validating Role Based Login for User:{} |  Role: {} | Company: {} | Org: {}",
                testUser.getUserName(),
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        Assert.assertTrue(appDashboardPage.isDashboardHomeDisplayed(), "Dashboard does not displayed for user role:" + testUser.getRole());

        String actualUserName = appDashboardPage.getUserName();
        String expectedUserName = testUser.getUserName();

        Assert.assertEquals(actualUserName, expectedUserName, "Logged-in user mismatch for role:" + testUser.getRole());

    }


    @Test(description = "Verify the Default login works in the Base test")
    public void verifyLoginOnBaseTest(){


        Assert.assertTrue(appDashboardPage.isDashboardHomeDisplayed());
    }


}
