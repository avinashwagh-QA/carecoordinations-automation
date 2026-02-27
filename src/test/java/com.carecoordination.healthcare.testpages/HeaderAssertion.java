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


    private static final Logger logger = LogManager.getLogger(HeaderAssertion.class);

    @BeforeMethod
    public void setUpPages() {
        headerPage = new HeaderPage(actionDriver);
    }

    @Test(description = "Verify Manage Team displayed for logged in user")
    public void verifyManageTeamMenu() {
        UserContext userContext = getCurrentUserContext();

        boolean actualVisibility = headerPage.isManageTeamDisplayed();
        boolean expectedVisibility = userContext.canInviteUser();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Header Mange team option not displayed for user role " + getCurrentUser().getRole());

    }

    @Test(description = "Verify Manage team is displayed as per user roles wise permission",
            dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyMangeTeamForAllUserRole(String personaKey) {

        TestUser testUser = getCurrentUser();

        //Call default base test method to build user context
        UserContext userContext = getCurrentUserContext();

        logger.info("Validating Manage Team visibility for Role: {} | Company: {} | Org: {}",
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        boolean actualVisibility = headerPage.isManageTeamDisplayed();
        boolean expectedVisibility = userContext.canAccessManageTeam();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Header Mange team option not displayed for user role " + testUser.getRole());

    }

    @Test(description = "Verify Channel History menu is displayed for privilege users"
            , dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyChannelHistoryDisplayedForPrivilegedUsers(String personaKey) {

        TestUser testUser = getCurrentUser();

        //calling base method for user contex
        UserContext userContext = getCurrentUserContext();

        logger.info("Validating channel History visibility for Role: {} | Company: {} | Org: {}",
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        boolean actualVisibility = headerPage.isChannelHistoryDisplayed();
        boolean expectedVisibility = userContext.isPrivileged();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Header Channel History option not displayed for user role " + testUser.getRole());

    }

    @Test(description = "verify channel usage displayed for system admin and branch admin only",
            dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyChannelUsageMenuDisplayPermission(String personaKey) {

        TestUser testUser = getCurrentUser();

        UserContext userContext = getCurrentUserContext(); // method from base test

        logger.info("Validating Channel usage visibility for Role: {} | Company: {} | Org: {}",
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        boolean actualVisibility = headerPage.isChannelUsageDisplayed();
        boolean expectedVisibility = userContext.canAccessChannelUsage();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Header Channel usage option not displayed for user role " + testUser.getRole());

    }

    @Test(description = "verify Alerts option displayed for all users",
            dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyAlertsDisplayedForAllUserRole(String personaKey) {

        TestUser testUser = getCurrentUser();

        UserContext userContext = getCurrentUserContext();

        logger.info("Validating Alert option visibility for Role: {} | Company: {} | Org: {}",
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        boolean actualVisibility = headerPage.isAlertDisplayed();
        boolean expectedVisibility = userContext.canAccessAlerts();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Alert option not displayed for user role " + testUser.getRole());

    }

    @Test(description = "verify availability option displayed for all users",
            dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void verifyAvailabilityDisplayedForAllUserRole(String personaKey) {

        TestUser testUser = getCurrentUser();
        UserContext userContext = getCurrentUserContext();  //default context

        logger.info("Validating Availability visibility for Role: {} | Company: {} | Org: {}",
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        boolean actualVisibility = headerPage.availabilityDisplayed();
        boolean expectedVisibility = userContext.canAccessAvailability();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Availability option not displayed for user role " + testUser.getRole());
    }

    @Test(description = "Verify Company info is displayed for system admin",
            dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyCompanyInfoDisplayedAsPerPermission(String personaKey) {

        TestUser testUser = getCurrentUser();
        UserContext userContext = getCurrentUserContext(); // calling method from base test

        logger.info("Validating Company info visibility for Role: {} | Company: {} | Org: {}",
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        boolean actualVisibility = headerPage.isCompanyInfoDisplayed();
        boolean expectedVisibility = userContext.canAccessCompanyInfo();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Company info not displayed for user roles" + testUser.getRole());
    }

    @Test(description = "Verify branch info is displayed as permission wise",
            dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyBranchInfoDisplayedAsPerPermission(String personaKey) {

        TestUser testUser = getCurrentUser();
        UserContext userContext = getCurrentUserContext(); // calling method from base test

        logger.info("Validating Branch info visibility for Role: {} | Company: {} | Org: {}",
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        boolean actualVisibility = headerPage.isBranchInfoDisplayed();
        boolean expectedVisibility = userContext.canAccessBranchInfo();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Branch info not displayed for user roles" + testUser.getRole());
    }

    @Test(description = "Verify Inactive channel is displayed as permission wise",
            dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyInactiveChannelDisplayedAsPerPermission(String personaKey) {

        TestUser testUser = getCurrentUser();
        UserContext userContext = getCurrentUserContext(); // calling method from base test

        logger.info("Validating Inactive Channels visibility for Role: {} | Company: {} | Org: {}",
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        boolean actualVisibility = headerPage.isInactiveChannelDisplayed();
        boolean expectedVisibility = userContext.canAccessInactiveChannel();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Inactive Channel page not displayed for user roles" + testUser.getRole());
    }

    @Test(description = "Verify Manage milestone is displayed as permission wise",
            dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyManageMilestoneDisplayedAsPerPermission(String personaKey) {

        TestUser testUser = getCurrentUser();
        UserContext userContext = getCurrentUserContext(); // calling method from base test

        logger.info("Validating Manage milestone visibility for Role: {} | Company: {} | Org: {}",
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        boolean actualVisibility = headerPage.isManageMilestoneDisplayed();
        boolean expectedVisibility = userContext.canAccessManageMilestone();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Manage milestone page not displayed for user roles" + testUser.getRole());
    }

    @Test(description = "Verify Manage physician is displayed as permission wise",
            dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyManagePhysicianDisplayedAsPerPermission(String personaKey) {

        TestUser testUser = getCurrentUser();
        UserContext userContext = getCurrentUserContext(); // calling method from base test

        logger.info("Validating Manage physician  visibility for Role: {} | Company: {} | Org: {}",
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        boolean actualVisibility = headerPage.isManagePhysicianDisplayed();
        boolean expectedVisibility = userContext.canAccessManagePhysician();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Manage Physician not displayed for user roles" + testUser.getRole());
    }

    @Test(description = "Verify Manage Pharmacies is displayed as permission wise",
            dataProvider = "personaMatrix", dataProviderClass = TestDataProvider.class)
    public void VerifyManagePharmaciesDisplayedAsPerPermission(String personaKey) {

        TestUser testUser = getCurrentUser();
        UserContext userContext = getCurrentUserContext(); // calling method from base test

        logger.info("Validating Manage pharmacies visibility for Role: {} | Company: {} | Org: {}",
                testUser.getRole(),
                testUser.getCompanyType(),
                testUser.getOrgStructure());

        boolean actualVisibility = headerPage.isManagePharmaciesDisplayed();
        boolean expectedVisibility = userContext.canAccessManagePharmacies();

        Assert.assertEquals(actualVisibility, expectedVisibility,
                "Manage Pharmacies not displayed for user roles" + testUser.getRole());
    }


    /*  Manage Task and tags Header menu remaining
     *  permission and Page objects are define in repsective class
     *  Permission may require to update in future as per need
     *  Assertion in all method just check menu visibility , we have not
     *  verified by opening page
     * */

}
