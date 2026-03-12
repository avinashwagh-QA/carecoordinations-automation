package com.carecoordination.healthcare.testpages.ManageTeam;

import com.carecoordination.healthcare.constants.InviteUserField;
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

        inviteUserModal.fillBasicDetails("Carter", "Mitchel", "Carter01_admin@yopmail.com","+91", "7849849899");

        inviteUserModal.clickOnInvite();
        manageTeamPage.clickOnPendingTab();

        manageTeamPage.waitForLoaderToDisappear();
        pendingTabComponent = new PendingTabComponent(actionDriver);

        Assert.assertTrue(pendingTabComponent.isUserPresent("CM Carter Mitchel"));
        Assert.assertEquals(pendingTabComponent.getUserEmailByUserName("CM Carter Mitchel"), "Carter01_admin@yopmail.com", "User email is incorrect");

    }


    @Test(description = "Verify inviting user with already email exist")
    public void verifyErrorMessageOnDuplicateEmail(){
        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        inviteUserModal.selectUserRole("Branch Admin");

        inviteUserModal.fillBasicDetails("Carter", "Mitchel", "Carter01_admin@yopmail.com","+91", "7321984989");

        inviteUserModal.clickOnInvite();

        Assert.assertTrue(inviteUserModal.isDuplicateInviteEmailMessageIsDisplayed(), "Duplicate email message format does not match");
    }

    @Test(description = "Verify inviting user with invalid phone")
    public void verifyErrorMessageOnPhone(){
        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        inviteUserModal.selectUserRole("Branch Admin");

        inviteUserModal.fillBasicDetails("Carter", "Mitchel", "Carter02_admin@yopmail.com","+91", "784");

        inviteUserModal.clickOnInvite();

        Assert.assertEquals(inviteUserModal.getErrorField(InviteUserField.MOBILE_NUMBER), "The mobile number must contain exactly ten digits.");
    }

    @Test(description = "Verify inviting user with already phone number exist")
    public void verifyErrorMessageOnDuplicatePhone(){
        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        inviteUserModal.selectUserRole("Branch Admin");

        inviteUserModal.fillBasicDetails("Carter", "Mitchel", "Carter02_admin@yopmail.com","+91", "7849849899");

        inviteUserModal.clickOnInvite();

        Assert.assertTrue(inviteUserModal.isDuplicateInvitePhoneMessageIsDisplayed(), "Duplicate Phone number message format does not match");
    }


}
