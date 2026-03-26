package com.carecoordination.healthcare.pages.modules.AppDashBoard.ManageTeam;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.components.TableComponent;
import com.carecoordination.healthcare.model.InviteUserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class PendingTabComponent {

    private final ActionDriver actionDriver;
    private final TableComponent tableComponent;

    private final static Logger logger = LogManager.getLogger(PendingTabComponent.class);

    public PendingTabComponent(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
        tableComponent = new TableComponent(tableHeader, tableRows);
    }

    private final By tableHeader = By.xpath("//table[@id='manageteamListingsTbl']//th");
    private final By tableRows = By.xpath("//table[@id='manageteamListingsTbl']//tbody/tr");

    //Action: Re-Invite user modal
    private final By roleName = By.xpath("//div[@id='viewUserMangeTeam']//div[@class='subText2 view-role']");
    private final By branchName = By.xpath("//div[@id='viewUserMangeTeam']//div[@class='subText2 view-branches']");
    private final By firstName = By.xpath("//div[@id='viewUserMangeTeam']//div[@class='subText2 subTextCapitalize view-first-name']");
    private final By lastName = By.xpath("//div[@id='viewUserMangeTeam']//div[@class='subText2 subTextCapitalize view-last-name']");
    private final By emailUser = By.xpath("//div[@id='viewUserMangeTeam']//div[@class='subText2 view-email']");
    private final By phoneNumber = By.xpath("//div[@id='viewUserMangeTeam']//div[@class='subText2 view-phone']");
    private final By confirmAndReinvite = By.id("editInfoTeam");
    private final By editModalTitle = By.xpath("//div[@id='inviteUserManageTeamModal']//h6[contains(text(),'Edit info & Re-invite')]");

    // Delete Modal Locators
    private final By deleteConfirmationText = By.xpath("//div[contains(@class,'manageTeamConfirmation')]//div[contains(@class,'confirmBoxText')]");
    private final By deleteConfirmationYes = By.xpath("//div[@class='manageTeamConfirmationSection1 text-center']//button[contains(text(),'Yes')]");
    private final By deleteConfirmationNo = By.xpath("//div[@class='manageTeamConfirmationSection1 text-center']//button[contains(text(),'No')]");

    private final By inviteDeleteToastMessage = By.xpath("//div[contains(@class,'iziToast')]//p[contains(@class,'iziToast-message')]");

    public boolean isUserPresent(String username) {
        actionDriver.waitForPageLoad();
        boolean user = tableComponent.isRowPresent("Name & Title", username);
        logger.info("User status in the table :{}", user);
        return user;
    }

    public String getUserEmailByUserName(String userName) {
        String userEmail = tableComponent.getCellText("Name & Title", userName, "Email address");
        logger.info("User name {}, has email {}", userName, userEmail);
        return userEmail;
    }

    public String getUserRoleByUserName(String userName){
        String userRole = tableComponent.getCellText("Name & Title", userName, "Role");
        logger.info("User name {}, has role {}", userName, userRole);
        return userRole;
    }

    public InviteUserData getInviteUserDetailsByUserName(String userName) {
        tableComponent.clickOnAction("Name & Title", userName, "Re-Invite");

        String role = actionDriver.getText(roleName);
        String branch = actionDriver.getText(branchName);
        String firstname = actionDriver.getText(firstName);
        String lastname = actionDriver.getText(lastName);
        String email = actionDriver.getText(emailUser);
        String phoneNum = actionDriver.getText(phoneNumber);

        return new InviteUserData(role, branch, firstname, lastname, email, phoneNum);
    }

    public void clickOnConfirmAndReInviteUser(String userName) {

        tableComponent.clickOnAction("Name & Title", userName, "Re-Invite");

        actionDriver.waitForElementToBeClickable(confirmAndReinvite);
        actionDriver.click(confirmAndReinvite);
    }

    public String clickOnEditInfo(){
        return actionDriver.getText(editModalTitle);
    }

    public String getInvitedUserDetails(String userName){
        String details =
        tableComponent.getCellText("Name & Title", userName, "Invited By");
        logger.info("User invited by details is : {}", details);
        return details;
    }

    public void clickOnDeleteInvitation(String userName){
        tableComponent.clickOnAction("Name & Title", userName,
                "Delete");
        actionDriver.click(deleteConfirmationYes);
        logger.info("User Deleted from pending tab :{}", userName);
    }

    public String getDeleteConfirmationText(){
        actionDriver.waitForElementToVisible(deleteConfirmationText);
        String text =actionDriver.getText(deleteConfirmationText);
        logger.info("Delete confirmation text is : {}", text);
        return text;
    }

    public boolean isDeleteInviteUserToastDisplayed() {

        actionDriver.waitForElementToVisible(inviteDeleteToastMessage);
        boolean status = actionDriver.isDisplayed(inviteDeleteToastMessage);
        logger.info("Update Invite user toast message status is : {}", status);
        logger.info("Message on update invite is : {}", actionDriver.getText(inviteDeleteToastMessage));
        return status;

    }


}