package com.carecoordination.healthcare.pages.modules.accountSetup;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class AccountSetupPage {

    private final ActionDriver actionDriver;

    private static final Logger logger = LogManager.getLogger(AccountSetupPage.class);

    public AccountSetupPage(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
    }

    private final By companyLogo = By.xpath("//form[@id='commonCompanySignupForm']//img");

    //User name user role company name
    private final By lblUserName = By.xpath("//div[contains(@class,'cc-heading-txt') and contains(text(),'Hello!')]");
    private final By lblRoleAndOrg = By.xpath("//div[contains(@class,'cc-heading-txt') and contains(text(),'(')]");
    private final By lblWelcomeCC = By.xpath("//div[contains(text(),'Welcome to Care Coordinations')]");

    //Title and Password inputs
    private final By inpProfessionTitle = By.id("professional");
    private final By inpPassword = By.id("password");

    private final By checkBoxTermCondition = By.cssSelector(".form-group.customCheckboxLabel");
    private final By lnkTermsAndCondition = By.xpath("//span[normalize-space()='Terms & Conditions']");

    private final By btnCopyPassword = By.xpath("//span[normalize-space()='Copy Password']");
    private final By msgSuccessCopyPassword = By.xpath("//span[contains(@class,'custom-copy-success-msg') and normalize-space()='Copied!']");
    private final By btnRegister = By.id("commonCompanySignupBtn");

    private final By infoIcon = By.id("infoDropDownBtn");
    private final By toolTip = By.xpath("//div[@class='dropdown-menu dropdown-menu-end user-permission-filter-dropdown show']//a");

    public String getWelcomeTitleOnAccountSetup (){
        actionDriver.waitForElementToVisible(lblWelcomeCC);
        String title = actionDriver.getText(lblWelcomeCC);
        logger.info("Title displayed on Account set up page {}", title );
        return title;
    }
    public boolean isCompanyLogoDisplayed() {
        actionDriver.waitForElementToVisible(companyLogo);
        boolean logo = actionDriver.isDisplayed(companyLogo);
        logger.info("Logo displayed on account setup page ....");
        return logo;
    }

    public String getDisplayedUserName() {
        actionDriver.waitForPageLoad();
        actionDriver.waitForElementToVisible(lblUserName);
        String user = actionDriver.getText(lblUserName);
        logger.info("The user name displayed in account setup page is {}", user);
        return user;
    }

    public String getDisplayedRoleAndOrg(){
        actionDriver.waitForElementToVisible(lblRoleAndOrg);
    String roleAndOrg =   actionDriver.getText(lblRoleAndOrg);
    logger.info("Role and Org displayed in account setup page is {}", roleAndOrg);
    return roleAndOrg;
    }

    public boolean isTitleAndPasswordDisplayed(){
        actionDriver.waitForElementToVisible(inpProfessionTitle);
        actionDriver.waitForElementToVisible(inpPassword);
        boolean title = actionDriver.isDisplayed(inpProfessionTitle);
        boolean password = actionDriver.isDisplayed(inpPassword);
        return title && password;
    }

    public void enterProfession(String profession) {
        actionDriver.waitForElementToBeClickable(inpProfessionTitle);
        actionDriver.clearText(inpProfessionTitle);
        actionDriver.enterText(inpProfessionTitle, profession);
        logger.info("Profession set on account set up page is {}", profession);
    }

    public void enterPassword(String password){
        actionDriver.waitForElementToBeClickable(inpPassword);
        actionDriver.clearText(inpPassword);
        actionDriver.enterText(inpPassword, password);
    }

    public void clickOnCheckBoxTermsAndCondition(){
        actionDriver.waitForElementToBeClickable(checkBoxTermCondition);
        actionDriver.click(checkBoxTermCondition);
        logger.info("Click on check box Terms and condition form account setup...");
    }

    public void clickOnTermsAndCondition(){
        actionDriver.waitForElementToBeClickable(lnkTermsAndCondition);
        actionDriver.click(lnkTermsAndCondition);
        logger.info("Click on terms and condition page form account setup...");
    }

    public boolean isTermsAndConditionDisplayed(){
        actionDriver.waitForElementToVisible(lnkTermsAndCondition);
        boolean termsCondition = actionDriver.isDisplayed(lnkTermsAndCondition);
        logger.info("Terms and condition displayed status on Account setup page {}", termsCondition);
        return termsCondition;
    }

    public void clickOnRegisterButton(){
        actionDriver.waitForElementToBeClickable(btnRegister);
        actionDriver.click(btnRegister);
    }

    public boolean isRegisterButtonEnabled() {
        boolean btnStatus = actionDriver.isButtonEnabled(btnRegister);
        logger.info("The Register button status on the account setup page is displayed : {}", btnStatus);
        return btnStatus;
    }

    public void clickOnCopyPassword(){
        actionDriver.waitForElementToBeClickable(btnCopyPassword);
        actionDriver.click(btnCopyPassword);
    }

    public boolean isSuccessToastDisplayedOnCopyPassword() {
        actionDriver.waitForElementToVisible(msgSuccessCopyPassword);
        boolean msg = actionDriver.isDisplayed(msgSuccessCopyPassword);
        logger.info("On Copy password message is displayed {}", msg);
        return msg;
    }

    public void clickOnToolTip(){
        actionDriver.waitForElementToBeClickable(infoIcon);
        actionDriver.click(infoIcon);
        logger.info("Clicked on info icon for title from Account setup page");
    }


    public String getToolTipDisplayedOnAccountSetup(){
        actionDriver.waitForElementToVisible(toolTip);
        String tooltip = actionDriver.getText(toolTip);
        logger.info("Title displayed on account setup is {}", toolTip);
        return tooltip;
    }




}
