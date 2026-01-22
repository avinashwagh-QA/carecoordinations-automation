package com.carecoordination.healthcare.pages.landingPages;

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

    private final By inpProfessionTitle = By.id("professional");
    private final By inpPassword = By.id("password");

    private final By checkBoxTermCondition = By.cssSelector(".form-group.customCheckboxLabel");
    private final By lnkTermsAndCondition = By.xpath("//span[normalize-space()='Terms & Conditions']");

    private final By btnCopyPassword = By.xpath("//span[normalize-space()='Copy Password']");
    private final By btnRegister = By.id("commonCompanySignupBtn");

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

    public void clickOnRegisterButton(){
        actionDriver.waitForElementToBeClickable(btnRegister);
        actionDriver.click(btnRegister);
    }

    public void clickOnCopyPassword(){
        actionDriver.waitForElementToBeClickable(btnCopyPassword);
        actionDriver.click(btnCopyPassword);
    }


}
