package com.carecoordination.healthcare.testpages.ManageTeam;

import com.carecoordination.healthcare.context.UserContext;
import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.pages.modules.AppDashBoard.HeaderPage;
import com.carecoordination.healthcare.pages.modules.AppDashBoard.ManageTeam.InviteUserModal;
import com.carecoordination.healthcare.pages.modules.AppDashBoard.ManageTeam.ManageTeamPage;
import com.carecoordination.healthcare.pages.modules.AppDashBoard.ManageTeam.PendingTabComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestDataProvider;

public class InviteUserTest extends BaseTest {

    private ManageTeamPage manageTeamPage;
    private InviteUserModal inviteUserModal;
    private HeaderPage headerPage;
    private PendingTabComponent pendingTabComponent;

    private static final Logger logger = LogManager.getLogger(InviteUserTest.class);

    @BeforeMethod
    public void setUpPages() {
        headerPage = new HeaderPage(actionDriver);
        manageTeamPage = new ManageTeamPage(actionDriver);
        inviteUserModal = new InviteUserModal(actionDriver);
    }


    @Test(description = "Verify the invite button is disable",
            dataProvider = "inviteUserPersonas", dataProviderClass = TestDataProvider.class)
    public void verifyInviteButtonIsDisable(String personaKey){

        UserContext userContext= getCurrentUserContext();

        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        boolean btnStatus = inviteUserModal.isInviteButtonDisable();
        Assert.assertFalse(btnStatus, "Invite button is display for initial state");

    }

    @Test(description = "Verify system admin can invite user with valid data")
    public void verifyInviteUserWithValidDetails(){

        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        inviteUserModal.selectUserRole("Branch Admin");

        inviteUserModal.fillBasicDetails("Smith", "Andrew", "Smith01_admin@yopmail.com","+91", "7849849840");

        inviteUserModal.clickOnInvite();
        pendingTabComponent = new PendingTabComponent(actionDriver);

        Assert.assertTrue(pendingTabComponent.isUserPresent("Smith"), "User not found in the Pending tab");


    }









}
