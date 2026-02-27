package com.carecoordination.healthcare.helpers;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.model.TestUser;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.LoginPage;
import com.carecoordination.healthcare.utilities.RoleCredentials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AuthHelper {

    private static final Logger logger = LogManager.getLogger(AuthHelper.class);

    /**
     * Login using UserContext (preferred entry point).
     * BaseTest should always call this method.
     */
    public static void login(TestUser testUser,
                             LandingPage landingPage,
                             LoginPage loginPage) {

        loginAs(testUser, landingPage, loginPage);
    }


/**
 * Login using role.
 * Kept for backward compatibility and future flexibility.
 */

    public static void loginAs(UserRole role,
                               LandingPage landingPage,
                               LoginPage loginPage) {

        logger.info("Logging with Role-Based Login Used: {}", role);

        landingPage.clickOnLoginLink();

        String email= RoleCredentials.getEmail(role);
        String password= RoleCredentials.getPassword(role);

        loginPage.login(email, password);
    }


    /**
     *  Login as Per User persona based
     *  Credentials come from JSON
    */

    public static void loginAs(TestUser user, LandingPage landingPage,
                               LoginPage loginPage){

        logger.info("Logging in with Persona Email: {}", user.getEmail());

        landingPage.clickOnLoginLink();
        loginPage.login(user.getEmail(), user.getPassword());

    }


}
