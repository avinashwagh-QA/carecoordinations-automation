package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.context.OrganizationContext;
import com.carecoordination.healthcare.context.UserContext;
import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.helpers.AuthHelper;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.LoginPage;
import com.carecoordination.healthcare.pages.modules.AppDashBoard.HeaderPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestDataProvider;

public class HeaderAssertion extends BaseTest {

    private HeaderPage headerPage;
    private LandingPage landingPage;
    private LoginPage loginPage;

    private static final Logger logger = LogManager.getLogger(HeaderAssertion.class);

    @BeforeMethod
    public void setUpPages(){
        headerPage = new HeaderPage(actionDriver);
        landingPage = new LandingPage(actionDriver);
        loginPage = new LoginPage(actionDriver);

    }


    @Test(description = "Verify Manager Team displayed for logged in user")
    public void verifyManageTeamMenu(){
        UserContext userContext = getUserContext();

        boolean actualVisibility = headerPage.isManageTeamDisplayed();
        boolean expectedVisibility = userContext.canInviteUser();


        Assert.assertEquals(actualVisibility,expectedVisibility,
                "Header Mange team option not displayed for user role "+ userContext.getRole());


    }

    @Test(description = "Verify Manage team menu in header section displayed for user roles",
    groups = "skip-login", dataProvider=  "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyMangeTeamForAllUserRole(UserRole role, boolean isMultiBranch){

        // 1. Build context for THIS execution
        UserContext userContext = new UserContext( role,
                new OrganizationContext(isMultiBranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true); //for logout - login happened inside test method

        boolean actualVisibility = headerPage.isManageTeamDisplayed();
        boolean expectedVisibility = userContext.canAccessManageTeam();

        Assert.assertEquals(actualVisibility,expectedVisibility,
                "Header Mange team option not displayed for user role "+ userContext.getRole());


    }

    @Test(description ="Verify Channel History menu is displayed for privilege users"
            , groups = "skip-login", dataProvider =  "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyChannelHistoryDisplayedForPrivilegedUsers(UserRole role, boolean isMultiBranch){

        UserContext userContext = new UserContext(role, new OrganizationContext(isMultiBranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true);//for logout - login happened inside test method

        boolean actualVisibility = headerPage.isChannelHistoryDisplayed();
        boolean expectedVisibility = userContext.isPrivileged();

        Assert.assertEquals(actualVisibility,expectedVisibility,
                "Header Channel History option not displayed for user role "+ userContext.getRole());

    }










}
