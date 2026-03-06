package com.carecoordination.healthcare.pages.modules.AppDashBoard.ManageTeam;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.constants.InviteUserField;
import com.carecoordination.healthcare.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.util.Map;

public class InviteUserModal {

    private final ActionDriver actionDriver;
    private static final Logger logger = LogManager.getLogger(InviteUserModal.class);

    public InviteUserModal(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
    }

    private final By btnInviteUser = By.id("createInviteUser");
    private final By drpDownSelectUserRole = By.id("addInvitationUserType");
    private final By selectBranch = By.id("team-select-branch");

    private final By inpFirstName = By.id("first_name");
    private final By inpMiddleName = By.name("middle_name");
    private final By inpLastName = By.id("last_name");
    private final By inpEmail = By.id("email");
    private final By drpCountryCode = By.id("addInvitationCountryId");
    private final By inpPhone = By.id("phone");

    private final By btnInvite = By.id("inviteUserTeamBtn");

    // it will map field with error text
    private Map<InviteUserField, By> errorLocators = Map.of(
            InviteUserField.USER_ROLE, By.xpath("//div[@id='userTypeSelectDiv']//span[@class='cc-error-txt']']"),
            InviteUserField.FIRST_NAME, By.xpath("//input[@id='first_name']/parent::div/following-sibling::span[@class='cc-error-txt']"),
            InviteUserField.LAST_NAME, By.xpath("//input[@id='last_name']/parent::div/following-sibling::span[@class='cc-error-txt']"),
            InviteUserField.USER_EMAIL, By.xpath("//input[@id='email']/parent::div/following-sibling::span[@class='cc-error-txt']"),
            InviteUserField.MOBILE_NUMBER, By.xpath("//input[@id='phone']/parent::div/following-sibling::span[@class='cc-error-txt']")
    );

    // This method will return the message based on field
    public String getErrorField(InviteUserField field){
        return DriverFactory.getDriver().findElement(errorLocators.get(field)).getText();
    }

    public void clickOnInviteUser(){
        actionDriver.waitForElementToBeClickable(btnInviteUser);
        actionDriver.click(btnInviteUser);
        logger.info("Click on Invite User button from Manage team");
    }

    public void selectUserRole(String role){
        actionDriver.selectByValue(drpDownSelectUserRole, role);
        logger.info("The User role{} is selected from {}", role, drpDownSelectUserRole);
    }

    public void selectUserBranch(String branchName){
        actionDriver.selectByValue(selectBranch, branchName);
        logger.info("The User Branch{} is selected from {}", branchName, selectBranch);
    }

    public void setInpFirstName(String firstName){
        actionDriver.waitForElementToBeClickable(inpFirstName);
        actionDriver.enterText(inpFirstName, firstName);
    }

    public void setInpMiddleName(String middleName){
        actionDriver.waitForElementToBeClickable(inpMiddleName  );
        actionDriver.enterText(inpMiddleName, middleName);
    }

    public void setInpLastName(String lastName){
        actionDriver.waitForElementToBeClickable(inpLastName);
        actionDriver.enterText(inpLastName, lastName);
    }

    public void setInpEmail(String email){
        actionDriver.waitForElementToBeClickable(inpEmail);
        actionDriver.enterText(inpEmail, email);
    }

    public void setInpPhone(String countryName, String number){
        actionDriver.selectByValue(drpCountryCode, countryName);
        actionDriver.waitForElementToBeClickable(inpPhone);
        actionDriver.enterText(inpPhone, number);
        logger.info("Phone :- countryCode:{} - Phone:{}",countryName, number);
    }

    public void clickOnInvite(){
        actionDriver.waitForElementToBeClickable(btnInvite);
        actionDriver.click(btnInvite);
        logger.info(" click on invite user button");
    }

    public void fillBasicDetails(String firstname, String lastname, String email, String countryCode, String phone){
        setInpFirstName(firstname);
        setInpLastName(lastname);
        setInpEmail(email);
        setInpPhone(countryCode,phone);
    }



















}
