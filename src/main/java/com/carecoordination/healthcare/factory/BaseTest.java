package com.carecoordination.healthcare.factory;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.context.OrganizationContext;
import com.carecoordination.healthcare.context.UserContext;
import com.carecoordination.healthcare.helpers.AuthHelper;
import com.carecoordination.healthcare.model.TestUser;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.LoginPage;
import com.carecoordination.healthcare.pages.modules.AppDashBoard.AppDashboardPage;
import com.carecoordination.healthcare.repository.UserRepository;
import com.carecoordination.healthcare.utilities.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {


    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    protected ActionDriver actionDriver;
    protected LandingPage landingPage;
    protected LoginPage loginPage;
    protected AppDashboardPage appDashboardPage;
    private static final String GROUP_SKIP_LOGIN = "skip-login";

    // per-thread flag — true if THIS TEST logged in
    protected static final ThreadLocal<Boolean> isLoggedIn = ThreadLocal.withInitial(() -> false);

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional String browser, Method method, Object[] testData) {

        logger.info("===== Starting Test Setup on thread: {} =====", Thread.currentThread().getName());

        /*
        //1.Read browser from config.properties
        String browser = ConfigReader.getProperty("browser");
        logger.info("Browser selected from config: {}", browser);
         */

        //Reading browser from test NG
        //logger.info("Browser selected from TestNG parameter: {}", browser);

        // 1. Fallback to config if TestNG didn't pass a browser (for single-browser runs)
        if (browser == null || browser.trim().isEmpty()) {
            browser = ConfigReader.getProperty("browser");
            logger.info("No TestNG browser parameter provided. Falling back to config: {}", browser);
        } else {
            logger.info("Browser selected from TestNG parameter: {}", browser);
        }

        //2.Create driver using DriverFactory (ThreadLocal)
        DriverFactory.initDriver(browser);
        logger.debug("WebDriver initialized successfully");

        //3.Create custom ActionDriver wrapper
        actionDriver = new ActionDriver(DriverFactory.getDriver());
        logger.debug("ActionDriver initialized");

        //4.Configure browser settings (max, waits, url)
        configureBrowser();

        //5.Default: do log in, unless the test method is in group "no-login"
        boolean skipLogin = false;

        // Detect if group contains "Skip-login"
        Test testAnnotation = method.getAnnotation(Test.class);
        if (testAnnotation != null) {
            for (String g : testAnnotation.groups()) {
                if (GROUP_SKIP_LOGIN.equalsIgnoreCase(g)) {
                    skipLogin = true;
                    break;
                }
            }
        }

        if (!skipLogin) {

            // 1. Detect persona from data provider
            String personaKey = null;

            if (testData != null && testData.length > 0) {
                if (testData[0] instanceof String) {
                    personaKey = (String) testData[0];
                }
            }

            // 2. Load TestUser
            TestUser testUser =
                    personaKey != null
                            ? UserRepository.getUser(personaKey)
                            : getTestUser(); // default

            if (testUser == null) {
                throw new RuntimeException("Persona not found!");
            }

            logger.info("Persona Login --> role: {} |  Company Type: {} | Organization Structure: {} | Email: {}",
            testUser.getRole(), testUser.getCompanyType(),
                    testUser.getOrgStructure(), testUser.getEmail());

            // 2. Initialize required pages
            landingPage = new LandingPage(actionDriver);
            loginPage = new LoginPage(actionDriver);

            // 3. Perform login
            AuthHelper.login(testUser, landingPage, loginPage);

            isLoggedIn.set(true); // LOGIN HAPPENED
        } else {
            isLoggedIn.set(false);  // Default, Test may login manually
        }
        logger.info("=== @BeforeMethod completed ===");
    }

    // Method to maximize browser , set URL and implicitWait to load the URL
    private void configureBrowser() {
        logger.info("Configuring Browser Settings...");

        //Implicit wait
        int impWait = Integer.parseInt(ConfigReader.getProperty("implicitWait"));
        logger.debug("Setting implicit wait to {} seconds", impWait);

        DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(impWait));

        // Maximize browser
        logger.info("Maximizing browser window");
        DriverFactory.getDriver().manage().window().maximize();


        //Set URL
        String url = ConfigReader.getProperty("url");
        try {
            DriverFactory.getDriver().get(url);
            logger.info("Navigating to URL: {}", url);
        } catch (Exception e) {
            logger.error("Failed to navigate to URL: {}", url, e);
            throw new RuntimeException("Failed to navigate to URL : " + e);
        }
    }

    /**
     * Default user context.(Default log in to system admin)
     * Can be overridden by test classes when needed.
     */

    protected UserContext getUserContext(){
        return new UserContext(UserRole.SYSTEM_ADMIN,
                new OrganizationContext(true));
    }

    /**
     * This method is used for persona based login
     */

    protected String getPersonaKey(){
        return "sysadmin_multi_nonintegrated"; //default
    }

    protected TestUser getTestUser(){
        return UserRepository.getUser(getPersonaKey());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        //Optional: per-test cleanup can go here (e.g., clear cookies, navigate back to dashboard)
        logger.debug("@AfterMethod optional cleanup for thread: {}", Thread.currentThread().getName());

        try {
            if (Boolean.TRUE.equals(isLoggedIn.get())) {
                try {
                    appDashboardPage = new AppDashboardPage(actionDriver);
                    logger.info("Attempting logout after test");
                    appDashboardPage.clickOnLogOut();
                    logger.info("Logout performed");
                } catch (Exception e) {
                    logger.warn("Logout during @AfterMethod failed", e);
                }
            } else {
                logger.debug("Skipping logout because login was skipped for this test");
            }
        } finally {
            // Always quit driver and clear ThreadLocal flags to avoid leaks
            try {
                DriverFactory.quitDriver();
                logger.info("WebDriver quit and ThreadLocal cleared");
            } catch (Exception e) {
                logger.warn("Error while quitting driver", e);
            }
            isLoggedIn.remove();
        }
    }






}
