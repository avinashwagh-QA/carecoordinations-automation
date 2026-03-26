package com.carecoordination.healthcare.testpages.ManageTeam;

import com.carecoordination.healthcare.constants.InviteUserField;
import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.context.UserContext;
import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.factory.InviteUserFactory;
import com.carecoordination.healthcare.model.InviteUserData;
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

    //use this method to open invite user modal
    private void openInviteUserModal() {
        headerPage.clickOnManageTeam();
        inviteUserModal.clickOnInviteUser();
    }

    @Test(description = "Verify the invite button is disable when modal is opened for first time",
            dataProvider = "inviteUserPersonas", dataProviderClass = TestDataProvider.class)
    public void verifyInviteButtonIsDisable(String personaKey){

        UserContext userContext= getCurrentUserContext();
        openInviteUserModal();
        boolean btnStatus = inviteUserModal.isInviteButtonEnabled();
        Assert.assertFalse(btnStatus, "Invite button is display for initial state");
    }

    @Test(description = "Verify Invite user modal displayed correct user role")
    public void verifyCorrectUserROleDisplayedInRoleDropdown(){
         openInviteUserModal();
         List<String> actualRoles = inviteUserModal.getUserRoles();

         List<String> expectedRoles = Arrays.stream(UserRole.values())
                 .map(UserRole::getDisplayName)
                 .collect(Collectors.toList());

         Assert.assertEquals(actualRoles, expectedRoles, "User roles does not match");
    }

    @Test(description = "Verify when system admin is selected as role then branch drop down is not displayed")
    public void verifyBranchNotDisplayedWhenSystemAdminSelected(){

        openInviteUserModal();
        inviteUserModal.selectUserRole("System Admin");
        Assert.assertFalse(inviteUserModal.isBranchDisplayed(), "Branch displayed when system admin is selected");
    }

    @Test(description = "Verify branch is displayed when role selected other than system admin")
    public void verifyBranchStatusDisplayedForOtherRole(){

        openInviteUserModal();

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

        openInviteUserModal();

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

        openInviteUserModal();

        InviteUserData user = new InviteUserFactory().createUser(UserRole.BRANCH_ADMIN);
        inviteUserModal.selectUserBranch("Sigma Hospice TX Branch");

        inviteUserModal.inviteUser(user);

        manageTeamPage.clickOnPendingTab();
        manageTeamPage.waitForLoaderToDisappear();

        pendingTabComponent = new PendingTabComponent(actionDriver);

        Assert.assertTrue(inviteUserModal.isAddAndUpdateInviteUserToastDisplayed(), "Toast message does not displayed");
        Assert.assertTrue(pendingTabComponent.isUserPresent(user.getDisplayedName()), "User is not present in the tab");
        Assert.assertEquals(pendingTabComponent.getUserEmailByUserName(user.getDisplayedName()), user.getEmail(), "User email is incorrect");

    }

    @Test(description = "Verify inviting user with already email exist in pending tab")
    public void verifyErrorMessageOnDuplicateEmail(){
        openInviteUserModal();

        InviteUserData user = new InviteUserFactory().createUser(UserRole.BRANCH_ADMIN);
        inviteUserModal.selectUserRole("Branch Admin");

        user.setEmail("kimbra_branchadmin@yopmail.com");

        inviteUserModal.inviteUser(user);
        Assert.assertTrue(inviteUserModal.isDuplicateInviteEmailMessageIsDisplayed(), "Duplicate email message format does not match");
    }

    @Test(description = "Verify error message by inviting user with already register email")
    public void verifyInvitingUserWithAlreadyRegisterEmail(){

        openInviteUserModal();

        InviteUserData user = new InviteUserFactory().createUser(UserRole.BRANCH_ADMIN);
        inviteUserModal.selectUserRole("Branch Admin");

        user.setEmail("Edwin.cstaff@yopmail.com");
        inviteUserModal.inviteUser(user);

        Assert.assertTrue(inviteUserModal.isAlreadyRegisterUserMessageDisplayedOnEmail(), "Already Register email message format does not match");
    }

    @Test(description = "Verify inviting user with invalid phone")
    public void verifyErrorMessageOnPhone(){
        openInviteUserModal();
        InviteUserData user = new InviteUserFactory().createUser(UserRole.BRANCH_ADMIN);
        inviteUserModal.selectUserRole("Branch Admin");

        user.setPhoneNumber("7845");
        inviteUserModal.inviteUser(user);

        Assert.assertEquals(inviteUserModal.getErrorField(InviteUserField.MOBILE_NUMBER), "The mobile number must contain exactly ten digits.");
    }

    @Test(description = "Verify inviting user with already phone number exist in pending tab")
    public void verifyErrorMessageOnDuplicatePhone(){
        openInviteUserModal();
        InviteUserData user = new InviteUserFactory().createUser(UserRole.BRANCH_ADMIN);
        inviteUserModal.selectUserRole("Branch Admin");

        user.setPhoneNumber("(724) 540-2367");
        inviteUserModal.inviteUser(user);

        Assert.assertTrue(inviteUserModal.isDuplicateInvitePhoneMessageIsDisplayed(), "Duplicate Phone number message format does not match");
    }

    @Test(description = "Verify inviting user with already register phone number")
    public void VerifyErrorMessageOnAlreadyRegisterNumber(){
        openInviteUserModal();
        InviteUserData user = new InviteUserFactory().createUser(UserRole.BRANCH_ADMIN);
        inviteUserModal.selectUserRole("Branch Admin");

        user.setPhoneNumber("(309) 090-4995");
        inviteUserModal.inviteUser(user);

        Assert.assertTrue(inviteUserModal.isDuplicateInvitePhoneMessageIsDisplayed(), "Duplicate Phone number message format does not match");
    }


    @Test(description = "Verify After inviting a user On Re-invite correct User data is displayed")
    public void VerifyOnReInviteUserInfoDetailsCorrectlyDisplayed(){

        openInviteUserModal();

        InviteUserData user = new InviteUserFactory().createUser(UserRole.BRANCH_ADMIN);
        inviteUserModal.selectUserBranch("Sigma Hospice TX Branch");

        inviteUserModal.inviteUser(user);

        manageTeamPage.clickOnPendingTab();
        manageTeamPage.waitForLoaderToDisappear();

        pendingTabComponent = new PendingTabComponent(actionDriver);

        Assert.assertTrue(pendingTabComponent.isUserPresent(user.getDisplayedName()));

        InviteUserData actualData = pendingTabComponent.getInviteUserDetailsByUserName(user.getDisplayedName());

        Assert.assertEquals(actualData.getRole(), user.getRole(), "Role does not match");
        Assert.assertEquals(actualData.getBranchName(), "Sigma Hospice TX Branch", "Branch name does not match");
        Assert.assertEquals(actualData.getFirstName(), user.getFirstName(), "First name does not match");
        Assert.assertEquals(actualData.getLastName(), user.getLastName(), "Last name does not match");
        Assert.assertEquals(actualData.getEmail(), user.getEmail(), "Email does not match");
        Assert.assertEquals(actualData.getPhoneNumber(), user.getPhoneNumber(), "Phone number does not match");
    }

    @Test(description = "Verify click on invite user without updating user then user update successfully")
    public void verifyToastMessageDisplayedOnUpdateInviteUser (){
        openInviteUserModal();

        manageTeamPage.waitForLoaderToDisappear();
        pendingTabComponent = new PendingTabComponent(actionDriver);

        Assert.assertTrue(pendingTabComponent.isUserPresent("CM Craig Mitchel"));

        pendingTabComponent.clickOnConfirmAndReInviteUser("CM Craig Mitchel");

        inviteUserModal.clickOnInvite();

        Assert.assertTrue(inviteUserModal.isAddAndUpdateInviteUserToastDisplayed(), "Toast on update invite user is not displayed");
    }


    @Test(description = "Verify updating user email from pending tab then email gets updated ")
    public void verifyUpdatingUserEmailForAlreadyInvitedUser() {

        headerPage.clickOnManageTeam();
        manageTeamPage.clickOnPendingTab();

        manageTeamPage.waitForLoaderToDisappear();
        pendingTabComponent = new PendingTabComponent(actionDriver);

        Assert.assertTrue(pendingTabComponent.isUserPresent("CM Craig Mitchel"));

        pendingTabComponent.clickOnConfirmAndReInviteUser("CM Craig Mitchel");

        String title = pendingTabComponent.clickOnEditInfo();

        Assert.assertEquals(title, "Edit info & Re-invite", "Title does not match");

        inviteUserModal.setInpEmail("Craig10_admin@yopmail.com");
        inviteUserModal.clickOnInvite();

        Assert.assertTrue(inviteUserModal.isAddAndUpdateInviteUserToastDisplayed(), "Toast on update invite user is not displayed");
        manageTeamPage.waitForLoaderToDisappear();
        Assert.assertEquals(pendingTabComponent.getUserEmailByUserName("CM Craig Mitchel"), "Craig10_admin@yopmail.com", "Email does not updated");

    }

    @Test(description = "Verify alert message on edit invitation for email")
    public void verifyAlertMessageOnEmailInEditInvitation(){

        headerPage.clickOnManageTeam();
        manageTeamPage.clickOnPendingTab();

        manageTeamPage.waitForLoaderToDisappear();
        pendingTabComponent = new PendingTabComponent(actionDriver);

        Assert.assertTrue(pendingTabComponent.isUserPresent("CM Craig Mitchel"));

        pendingTabComponent.clickOnConfirmAndReInviteUser("CM Craig Mitchel");

        String title = pendingTabComponent.clickOnEditInfo();

        Assert.assertEquals(title, "Edit info & Re-invite", "Title does not match");

        inviteUserModal.setInpEmail("care");
        inviteUserModal.clickOnInvite();
        Assert.assertTrue(inviteUserModal.isInvalidEmailMessageDisplayed());

    }

    //Invited scenario is remaining
    @Test(description = "Verify on editing inviting user then invited by is correctly displayed")
    public void verifyInvitedByDetailsIsCorrectlyDisplayed() {

        headerPage.clickOnManageTeam();
        manageTeamPage.clickOnPendingTab();

        manageTeamPage.waitForLoaderToDisappear();
        pendingTabComponent = new PendingTabComponent(actionDriver);

        Assert.assertTrue(pendingTabComponent.isUserPresent("CM Craig Mitchel"));

        pendingTabComponent.clickOnConfirmAndReInviteUser("CM Craig Mitchel");

        String title = pendingTabComponent.clickOnEditInfo();

        Assert.assertEquals(title, "Edit info & Re-invite", "Title does not match");

        inviteUserModal.setInpEmail("Craig01_admin@yopmail.com");
        inviteUserModal.clickOnInvite();
        manageTeamPage.waitForLoaderToDisappear();

        String invitedByText = pendingTabComponent.getInvitedUserDetails("CM Craig Mitchel");

        Assert.assertTrue(invitedByText.startsWith("By"), "message does not start with By");

        Assert.assertTrue(invitedByText.contains("Max M Thompson"), "Invited by user is not correctly displayed");

        // Validate date
        Assert.assertTrue(invitedByText.matches(".*\\d{2}/\\d{2}/\\d{4}.*"),
                "Date format incorrect");

        // Validate time
        Assert.assertTrue(invitedByText.matches(".*\\d{2}:\\d{2}\\s?(AM|PM).*"),
                "Time format incorrect");
    }

    @Test(description = "Verify by deleting user from pending tab")
    public void verifyByDeletingUserFromPendingTab(){

        headerPage.clickOnManageTeam();
        manageTeamPage.clickOnPendingTab();

        manageTeamPage.waitForLoaderToDisappear();
        pendingTabComponent = new PendingTabComponent(actionDriver);

        pendingTabComponent.clickOnDeleteInvitation("OM Owen Mitchel");

        Assert.assertTrue(pendingTabComponent.isDeleteInviteUserToastDisplayed(), " Delete user toast does not displayed");
        Assert.assertFalse(pendingTabComponent.isUserPresent("OM Owen Mitchel"), "User is present in the pending tab ");

    }

    @Test(description = "Verify Delete confirmation popup displayed correct user name and role")
    public void VerifyUserNameAndCorrectUserRoleIsDisplayed(){

        headerPage.clickOnManageTeam();
        manageTeamPage.clickOnPendingTab();

        manageTeamPage.waitForLoaderToDisappear();
        pendingTabComponent = new PendingTabComponent(actionDriver);

        String role = pendingTabComponent.getUserRoleByUserName("DD Das Dronr");

        pendingTabComponent.clickOnDeleteInvitation("DD Das Dronr");

        Assert.assertTrue(pendingTabComponent.getDeleteConfirmationText().contains("Das Dronr"), "User name does not match in confirmation modal");
        Assert.assertTrue(pendingTabComponent.getDeleteConfirmationText().contains(role), "User role does not match in the confirmation modal");

        Assert.assertTrue(pendingTabComponent.isDeleteInviteUserToastDisplayed(), " Delete user toast does not displayed");
        Assert.assertFalse(pendingTabComponent.isUserPresent("CM Craig Mitchel"), "User is present in the pending tab ");


    }



}
