package com.carecoordination.healthcare.pages.modules.AppDashBoard.ManageTeam;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.constants.InviteUserField;
import com.carecoordination.healthcare.factory.DriverFactory;
import com.carecoordination.healthcare.model.InviteUserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InviteUserModal {

    private final ActionDriver actionDriver;
    private static final Logger logger = LogManager.getLogger(InviteUserModal.class);
    private final WebDriver driver = DriverFactory.getDriver();

    public InviteUserModal(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
    }

    private final By btnInviteUser = By.id("createInviteUser");
    private final By dropdownUserRole = By.xpath("//div[@id='userTypeSelectDiv']//button");
    private final By dropDownRoleOption = By.xpath("//div[@id='userTypeSelectDiv']//div[contains(@class,'dropdown-menu')]//span[@class='text']");
    private final By drpDownSelectUserRole = By.id("addInvitationUserType");
    private final By selectBranch = By.xpath("//div[@id='branchedSelectDiv']//button");
    private final By selectBranchOption = By.xpath("//div[@id='branchedSelectDiv']//div[contains(@class,'dropdown-menu')]//span[@class='text']");

    private final By inpFirstName = By.xpath("//div[@id='commonManageTeamModalBody']//input[@id='first_name']");
    private final By inpMiddleName = By.name("middle_name");
    private final By inpLastName = By.xpath("//div[@id='commonManageTeamModalBody']//input[@id='last_name']");
    private final By inpEmail = By.xpath("//div[@id='commonManageTeamModalBody']//input[@id='email']");
    private final By drpCountryCode = By.xpath("//button[@data-id='addInvitationCountryId']");
    private final By getDrpCountryCodeOption = By.xpath("//button[@data-id='addInvitationCountryId']/following::div[contains(@class,'dropdown-menu')]//span");
    private final By inpPhone = By.xpath("//div[@id='commonManageTeamModalBody']//input[@id='phone']");

    private final By btnInvite = By.id("inviteUserTeamBtn");

    private final By inviteUpdateToastMessage = By.xpath("//div[contains(@class,'iziToast')]//p[contains(@class,'iziToast-message')]");

    // it will map field with error text
    private Map<InviteUserField, List<By> > errorLocators = Map.of(
            InviteUserField.USER_ROLE, List.of(By.xpath("//div[@id='userTypeSelectDiv']//span[@class='cc-error-txt']")),
            InviteUserField.FIRST_NAME,List.of(By.xpath("//input[@id='first_name']/parent::div/following-sibling::span[@class='cc-error-txt']")),
            InviteUserField.LAST_NAME, List.of(By.xpath("//input[@id='last_name']/parent::div/following-sibling::span[@class='cc-error-txt']")),
            InviteUserField.USER_EMAIL, List.of(By.xpath("//input[@id='email']/parent::div/following-sibling::span[@class='cc-error-txt']")),
            InviteUserField.MOBILE_NUMBER, List.of(By.xpath("//input[@id='phone']/parent::div/following-sibling::span[@class='cc-error-txt']"),
                    By.xpath("//form[@id='inviteUserManageTeamForm']//span[contains(@class,'cc-error-phone-txt')]"))
    );

    // This method will return the message based on field
    public String getErrorField(InviteUserField field) {
        for (By locator : errorLocators.get(field)) {
            try {
            WebElement element = actionDriver.waitForElementToVisible(locator);

                String text = element.getText().trim();
                logger.info("Message found is : {}", text);
                return text;

            } catch (Exception e) {
                logger.debug("Locator not visible yet: {}", locator);
            }
        }
        throw new RuntimeException("No error message found for field: " + field);
    }

    public void clickOnInviteUser(){
        actionDriver.safeClick(btnInviteUser);
        logger.info("Click on Invite User button from Manage team");
    }

    public void selectUserRole(String role){
        actionDriver.selectCustomDropdown(dropdownUserRole,dropDownRoleOption, role);
        logger.info("The User role {} is selected from {}", role, drpDownSelectUserRole);
    }

    public List<String> getUserRoles(){
        actionDriver.waitForElementToBeClickable(dropdownUserRole);

        actionDriver.safeClick(dropdownUserRole);

        List<String> roles = new ArrayList<>();
        actionDriver.waitForAllElementsToBeVisible(dropDownRoleOption);
        List<WebElement> Options  = driver.findElements(dropDownRoleOption);

        for (WebElement option : Options){
            roles.add(option.getText().trim());
        }

        logger.info("Roles displayed in dropdown are : {}", roles);
        return roles;
    }

    public void selectUserBranch(String branchName){
        actionDriver.selectCustomDropdown(selectBranch,selectBranchOption , branchName);
        logger.info("The User selected Branch : {}", branchName);
    }

    public boolean isBranchDisplayed(){
        boolean status = actionDriver.isDisplayed(selectBranch);
        logger.info("The branch drop down status : {}", status);
        return status;
    }

    public void setInpFirstName(String firstName){
        actionDriver.enterText(inpFirstName, firstName);
        logger.info("Inserted user first name as : {}", firstName);
    }

    public void setInpMiddleName(String middleName){
        actionDriver.enterText(inpMiddleName, middleName);
    }

    public void setInpLastName(String lastName){
        actionDriver.enterText(inpLastName, lastName);
        logger.info("Inserted user last name as : {}", lastName);
    }

    public void setInpEmail(String email){
        actionDriver.enterText(inpEmail, email);
        logger.info("Inserted email is : {}", email);
    }

    public void setInpPhone(String countryName, String number){
        actionDriver.selectCustomDropdown(drpCountryCode, getDrpCountryCodeOption, countryName);
        actionDriver.enterMaskedText(inpPhone, number);
        logger.info("Inserted Phone :- countryCode:{} - Phone:{}",countryName, number);
    }

    // method to set the user data using invite user factory and data util
    public void inviteUser(InviteUserData user){

        selectUserRole(user.getRole());

        if(user.getBranchName() !=null && !user.getBranchName().isEmpty()){
            selectUserBranch(user.getBranchName());
        }

        fillBasicDetails(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                "+91",
                user.getPhoneNumber()
        );

        clickOnInvite();
    }


    public void clickOnInvite(){
        actionDriver.click(btnInvite);
        logger.info(" clicked on invite user button from modal");
    }

    public boolean isInviteButtonEnabled(){
        boolean button = actionDriver.isButtonEnabled(btnInvite);
        logger.info("The invite button status : {}", button);
        return button;
    }

    public void fillBasicDetails(String firstname, String lastname, String email, String countryCode, String phone){
        setInpFirstName(firstname);
        setInpLastName(lastname);
        setInpEmail(email);
        setInpPhone(countryCode,phone);
    }

    //regex based message pattern check for email
    public boolean isDuplicateInviteEmailMessageIsDisplayed(){

        String errorMessage = getErrorField(InviteUserField.USER_EMAIL);
        logger.info("Error message displayed on modal for email is : {}", errorMessage);

        String pattern = "You are inviting .* as .*, but our records show that .* already sent an invite on .* at .*";
        return errorMessage.matches(pattern);
    }

    public boolean isAlreadyRegisterUserMessageDisplayedOnEmail(){

        String errorMessage = getErrorField(InviteUserField.USER_EMAIL);
        logger.info("Error message displayed on modal for register email is : {}", errorMessage);

        String pattern = "This user is already assigned as a .* in another branch. To invite them to a new branch with a different role, please update their existing role or contact support for assistance.";
        return errorMessage.matches(pattern);
    }

    public boolean isInvalidEmailMessageDisplayed(){

        String errorMessage = getErrorField(InviteUserField.USER_EMAIL);
        logger.info("Error message displayed on edit modal for register email is : {}", errorMessage);

        String pattern = "Please enter a valid email address.";
        return errorMessage.matches(pattern);
    }


    //regex based message pattern check for Phone
    public boolean isDuplicateInvitePhoneMessageIsDisplayed(){

        String errorMessage = getErrorField(InviteUserField.MOBILE_NUMBER);
        logger.info("Error message displayed on modal for Phone is : {}", errorMessage);

        String pattern = "You are inviting .* as .*, but our records show that .* already sent an invite on .* at .*";
        return errorMessage.matches(pattern);
    }

    public boolean isAddAndUpdateInviteUserToastDisplayed() {

        actionDriver.waitForElementToVisible(inviteUpdateToastMessage);
        boolean status = actionDriver.isDisplayed(inviteUpdateToastMessage);
        logger.info("Add/Update Invite user toast message status is : {}", status);
        logger.info("Message on Add/update invite is : {}", actionDriver.getText(inviteUpdateToastMessage));
        return status;

    }






    }


















