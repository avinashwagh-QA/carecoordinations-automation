package com.carecoordination.healthcare.helpers;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.context.UserContext;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.LoginPage;
import com.carecoordination.healthcare.utilities.RoleCredentials;

public class AuthHelper {

    /**
     * Login using UserContext (preferred entry point).
     * BaseTest should always call this method.
     */
    public static void login(UserContext userContext,
                             LandingPage landingPage,
                             LoginPage loginPage) {

        loginAs(userContext.getRole(), landingPage, loginPage);
    }


/**
 * Login using role.
 * Kept for backward compatibility and future flexibility.
 */

    public static void loginAs(UserRole role,
                               LandingPage landingPage,
                               LoginPage loginPage){

        landingPage.clickOnLoginLink();

        String email= RoleCredentials.getEmail(role);
        String password= RoleCredentials.getPassword(role);

        loginPage.login(email, password);
    }



}
