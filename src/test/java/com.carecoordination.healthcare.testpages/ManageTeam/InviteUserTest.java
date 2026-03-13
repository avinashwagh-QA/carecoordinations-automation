package com.carecoordination.healthcare.testpages.ManageTeam;

import com.carecoordination.healthcare.constants.InviteUserField;
import com.carecoordination.healthcare.constants.UserRole;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test(description = "Verify the invite button is disable when modal is opened for first time",
            dataProvider = "inviteUserPersonas", dataProviderClass = TestDataProvider.class)
    public void verifyInviteButtonIsDisable(String personaKey){

        UserContext userContext= getCurrentUserContext();

        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        boolean btnStatus = inviteUserModal.isInviteButtonEnabled();
        Assert.assertFalse(btnStatus, "Invite button is display for initial state");

    }

    @Test(description = "Verify Invite user modal displayed correct user role")
    public void verifyCorrectUserROleDisplayedInRoleDropdown(){

        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

         List<String> actualRoles = inviteUserModal.getUserRoles();

         List<String> expectedRoles = Arrays.stream(UserRole.values())
                 .map(UserRole::getDisplayName)
                 .collect(Collectors.toList());

         Assert.assertEquals(actualRoles, expectedRoles, "User roles does not match");

    }

    @Test(description = "Verify when system admin is selected as role then branch drop down is not displayed")
    public void verifyBranchNotDisplayedWhenSystemAdminSelected(){

        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        inviteUserModal.selectUserRole("System Admin");

        Assert.assertFalse(inviteUserModal.isBranchDisplayed(), "Branch displayed when system admin is selected");
    }

    @Test(description = "Verify branch is displayed when role selected other than system admin")
    public void verifyBranchStatusDisplayedForOtherRole(){

        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        Assert.assertTrue(inviteUserModal.isBranchDisplayed(), "Branch not displayed for Branch Admin");
        List<UserRole> userRoles = List.of(UserRole.BRANCH_ADMIN,
                UserRole.MANAGER_SUPERVISOR, UserRole.CLERICAL_STAFF,
                UserRole.FIELD_CLINICIAN, UserRole.TRIAGE_STAFF, UserRole.EXTERNAL_VENDOR);

        for (UserRole role : userRoles){
            inviteUserModal.selectUserRole(role.getDisplayName());

            Assert.assertTrue(inviteUserModal.isBranchDisplayed(), "Branch not displayed for role: " + role);
        }
    }

    @Test(description = "Verify invite button remains disabled until mandatory fields are filled")
    public void verifyInviteButtonDisabledUntilMandatoryFieldsFilled(){

        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        inviteUserModal.selectUserRole("Branch Admin");
        Assert.assertFalse(inviteUserModal.isInviteButtonEnabled(), "Button enabled");

        inviteUserModal.selectUserBranch("Sigma Hospice New Jersey");
        Assert.assertFalse(inviteUserModal.isInviteButtonEnabled(), "Button enabled");

        inviteUserModal.setInpFirstName("Kevin");
        Assert.assertFalse(inviteUserModal.isInviteButtonEnabled(), "Button enabled");

        inviteUserModal.setInpLastName("Lewis");
        Assert.assertFalse(inviteUserModal.isInviteButtonEnabled(), "Button enabled");

        inviteUserModal.setInpEmail("kevin.BA@yopmail.com");
        Assert.assertFalse(inviteUserModal.isInviteButtonEnabled(), "Button enabled");

        inviteUserModal.setInpPhone("+91", "8741874100");
        Assert.assertTrue(inviteUserModal.isInviteButtonEnabled(), "Button enabled");

    }

    @Test(description = "Verify system admin can invite user with valid data")
    public void verifyInviteUserWithValidDetails(){

        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        inviteUserModal.selectUserRole("Branch Admin");

        inviteUserModal.selectUserBranch("Sigma Hospice NYC Branch");

        inviteUserModal.fillBasicDetails("Carter", "Mitchel", "Carter01_admin@yopmail.com","+91", "7849849899");

        inviteUserModal.clickOnInvite();
        manageTeamPage.clickOnPendingTab();

        manageTeamPage.waitForLoaderToDisappear();
        pendingTabComponent = new PendingTabComponent(actionDriver);

        Assert.assertTrue(pendingTabComponent.isUserPresent("CM Carter Mitchel"));
        Assert.assertEquals(pendingTabComponent.getUserEmailByUserName("CM Carter Mitchel"), "Carter01_admin@yopmail.com", "User email is incorrect");

    }

    @Test(description = "Verify inviting user with already email exist in pending tab")
    public void verifyErrorMessageOnDuplicateEmail(){
        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        inviteUserModal.selectUserRole("Branch Admin");

        inviteUserModal.fillBasicDetails("Carter", "Mitchel", "Carter01_admin@yopmail.com","+91", "7321984989");

        inviteUserModal.clickOnInvite();

        Assert.assertTrue(inviteUserModal.isDuplicateInviteEmailMessageIsDisplayed(), "Duplicate email message format does not match");
    }

    @Test(description = "Verify error message by inviting user with already register email")
    public void verifyInvitingUserWithAlreadyRegisterEmail(){

        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();

        inviteUserModal.selectUserRole("Branch Admin");

        inviteUserModal.fillBasicDetails("Carter", "Mitchel", "Max.admin@yopmail.com","+91", "7321984901");

        inviteUserModal.clickOnInvite();

        Assert.assertTrue(inviteUserModal.isAlreadyRegisterUserMessageDisplayedOnEmail(), "Already Register email message format does not match");

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
