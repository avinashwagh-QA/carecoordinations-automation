package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.context.OrganizationContext;
import com.carecoordination.healthcare.context.UserContext;
import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.helpers.AuthHelper;
import com.carecoordination.healthcare.model.TestUser;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.LoginPage;
import com.carecoordination.healthcare.pages.modules.AppDashBoard.HeaderPage;
import com.carecoordination.healthcare.repository.UserRepository;
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
    public void setUpPages() {
        headerPage = new HeaderPage(actionDriver);
        landingPage = new LandingPage(actionDriver);
        loginPage = new LoginPage(actionDriver);

    }

    private String currentPersona;


    @Test(description = "Verify Manage Team displayed for logged in user")
    public void verifyManageTeamMenu() {
        UserContext userContext = getUserContext();

        boolean actualVisibility = headerPage.isManageTeamDisplayed();
        boolean expectedVisibility = userContext.canInviteUser();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Header Mange team option not displayed for user role " + userContext.getRole());

    }

    @Test(description = "Verify Manage team is displayed as per user roles wise permission",
            dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyMangeTeamForAllUserRole(String personaKey) {

        TestUser testUser = UserRepository.getUser(personaKey); // Loaded in BaseTest

        // Build user context from persona
        UserContext userContext = new UserContext(testUser.getRole(), new OrganizationContext(
                testUser.getOrgStructure().equals("MULTI_BRANCH")
        ));

        boolean actualVisibility = headerPage.isManageTeamDisplayed();
        boolean expectedVisibility = userContext.canAccessManageTeam();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Header Mange team option not displayed for user role " + testUser.getRole());

    }

    @Test(description = "Verify Channel History menu is displayed for privilege users"
            , groups = "skip-login", dataProvider = "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyChannelHistoryDisplayedForPrivilegedUsers(UserRole role, boolean isMultiBranch) {

        UserContext userContext = new UserContext(role, new OrganizationContext(isMultiBranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true);//for logout - login happened inside test method

        boolean actualVisibility = headerPage.isChannelHistoryDisplayed();
        boolean expectedVisibility = userContext.isPrivileged();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Header Channel History option not displayed for user role " + userContext.getRole());

    }

    @Test(description = "verify channel usage displayed for system admin and branch admin only",
            groups = "skip-login", dataProvider = "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyChannelUsageMenuDisplayPermission(UserRole role, boolean isMultibranch) {

        UserContext userContext = new UserContext(role, new OrganizationContext(isMultibranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true);//for logout - login happened inside test method

        boolean actualVisibility = headerPage.isChannelUsageDisplayed();
        boolean expectedVisibility = userContext.canAccessChannelUsage();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Header Channel usage option not displayed for user role " + userContext.getRole());

    }

    @Test(description = "verify Alerts option displayed for all users",
            groups = "skip-login", dataProvider = "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyAlertsDisplayedForAllUserRole(UserRole role, boolean isMultibranch) {

        UserContext userContext = new UserContext(role, new OrganizationContext(isMultibranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true);//for logout - login happened inside test method

        boolean actualVisibility = headerPage.isAlertDisplayed();
        boolean expectedVisibility = userContext.canAccessAlerts();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Alert option not displayed for user role " + userContext.getRole());

    }

    @Test(description = "verify availability option displayed for all users",
            groups = "skip-login", dataProvider = "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyAvailabilityDisplayedForAllUserRole(UserRole role, boolean isMultibranch) {

        UserContext userContext = new UserContext(role, new OrganizationContext(isMultibranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true);//for logout - login happened inside test method

        boolean actualVisibility = headerPage.availabilityDisplayed();
        boolean expectedVisibility = userContext.canAccessAvailability();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Availability option not displayed for user role " + userContext.getRole());

    }

    @Test(description = "Verify Company info is displayed for system admin",
            groups = "skip-login", dataProvider = "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyCompanyInfoDisplayedAsPerPermission(UserRole role, boolean isMultiBranch) {

        UserContext userContext = new UserContext(role, new OrganizationContext(isMultiBranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true); // for logout - login happened inside test method

        boolean actualVisibility = headerPage.isCompanyInfoDisplayed();
        boolean expectedVisibility = userContext.canAccessCompanyInfo();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Company info not displayed for user roles" + userContext);
    }

    @Test(description = "Verify branch info is displayed as permission wise",
            groups = "skip-login", dataProvider = "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyBranchInfoDisplayedAsPerPermission(UserRole role, boolean isMultiBranch) {

        UserContext userContext = new UserContext(role, new OrganizationContext(isMultiBranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true); // for logout - login happened inside test method

        boolean actualVisibility = headerPage.isBranchInfoDisplayed();
        boolean expectedVisibility = userContext.canAccessBranchInfo();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Branch info not displayed for user roles" + userContext);
    }

    @Test(description = "Verify Inactive channel is displayed as permission wise",
            groups = "skip-login", dataProvider = "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyInactiveChannelDisplayedAsPerPermission(UserRole role, boolean isMultiBranch) {

        UserContext userContext = new UserContext(role, new OrganizationContext(isMultiBranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true); // for logout - login happened inside test method

        boolean actualVisibility = headerPage.isInactiveChannelDisplayed();
        boolean expectedVisibility = userContext.canAccessInactiveChannel();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Inactive Channel page not displayed for user roles" + userContext);
    }


    @Test(description = "Verify Manage milestone is displayed as permission wise",
            groups = "skip-login", dataProvider = "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyManageMilestoneDisplayedAsPerPermission(UserRole role, boolean isMultiBranch) {

        UserContext userContext = new UserContext(role, new OrganizationContext(isMultiBranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true); // for logout - login happened inside test method

        boolean actualVisibility = headerPage.isManageMilestoneDisplayed();
        boolean expectedVisibility = userContext.canAccessManageMilestone();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Manage milestone page not displayed for user roles" + userContext);
    }


    @Test(description = "Verify Manage physician is displayed as permission wise",
            groups = "skip-login", dataProvider = "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyManagePhysicianDisplayedAsPerPermission(UserRole role, boolean isMultiBranch) {

        UserContext userContext = new UserContext(role, new OrganizationContext(isMultiBranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true); // for logout - login happened inside test method

        boolean actualVisibility = headerPage.isManagePhysicianDisplayed();
        boolean expectedVisibility = userContext.canAccessManagePhysician();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Manage Physician not displayed for user roles" + userContext);
    }


    @Test(description = "Verify Manage Pharmacies is displayed as permission wise",
            groups = "skip-login", dataProvider = "roleMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyManagePharmaciesDisplayedAsPerPermission(UserRole role, boolean isMultiBranch) {

        UserContext userContext = new UserContext(role, new OrganizationContext(isMultiBranch));

        AuthHelper.loginAs(role, landingPage, loginPage);
        isLoggedIn.set(true); // for logout - login happened inside test method

        boolean actualVisibility = headerPage.isManagePharmaciesDisplayed();
        boolean expectedVisibility = userContext.canAccessManagePharmacies();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Manage Pharmacies not displayed for user roles" + userContext);
    }



    /*  Manage Task and tags Header menu remaining
     *  permission and Page objects are define in repsective class
     *  Permission may require to update in future as per need
     *  Assertion in all method just check menu visibility , we have not
     *  verified by opening page
     * */

}
