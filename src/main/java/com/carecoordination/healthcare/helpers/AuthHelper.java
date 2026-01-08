package com.carecoordination.healthcare.helpers;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.LoginPage;
import com.carecoordination.healthcare.utilities.RoleCredentials;

public class AuthHelper {

    public static void loginAs(UserRole role,
                               LandingPage landingPage,
                               LoginPage loginPage){

        landingPage.clickOnLoginLink();

        String email= RoleCredentials.getEmail(role);
        String password= RoleCredentials.getPassword(role);

        loginPage.login(email, password);
    }


}
