package com.carecoordination.healthcare.pages.landingPages;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class RegisterPage {

    private final ActionDriver actionDriver;
    private static final Logger logger = LogManager.getLogger(RegisterPage.class);

    public RegisterPage(ActionDriver actionDriver){
        this.actionDriver = actionDriver;
    }

    //Defining the locators for register page
    private final By inpInvitationCode = By.id("invitation_code");
    private final By inpEmailField = By.id("email");
    private final By btnContinueRegister = By.id("commonInviteCompanySignupBtn");

    private final By titleRegister = By.xpath("//div[@class='cc-heading-txt my-0']");
    private final By errorMessage = By.cssSelector(".custom-block-error-msg");

    private final By lnkLogin = By.xpath("//div[@class='already-account']//a");
    private final By registerGuide = By.xpath("//a[normalize-space()='Click here to read register guide']");
    private final By registerGuideModal = By.id("signupGuideHtmlModel");

    private final By btnGoBack = By.xpath("//button[normalize-space()='Go Back']");

    public boolean isRegisterPageDisplayed() {
        boolean urlCheck = DriverFactory.getDriver().getCurrentUrl().contains("user-register");
        boolean titleCheck = actionDriver.isDisplayed(titleRegister);
        logger.info("Register page displayed | URL: {} | Title: {}", urlCheck, titleCheck);
        return urlCheck && titleCheck;
    }

    public String getRegisterPageTitle() {
        actionDriver.waitForElementToVisible(titleRegister);
        logger.info("Title on register page is {}", titleRegister);
        return actionDriver.getText(titleRegister);
    }

    public boolean isLoginLinkDisplayed(){
        actionDriver.waitForElementToBeClickable(lnkLogin);
        return actionDriver.isDisplayed(lnkLogin);
    }

    public boolean isRegisterGuideDisplayed(){
        actionDriver.waitForElementToBeClickable(registerGuide);
        return  actionDriver.isDisplayed(registerGuide);
    }

    public boolean isRegisterGuideModalOpenWhenClicked(){
        actionDriver.waitForElementToVisible(registerGuide);
        actionDriver.click(registerGuide);
        actionDriver.waitForElementToVisible(registerGuideModal);
        return actionDriver.isDisplayed(registerGuideModal);
    }


    public boolean isInputInvitationCodeAndEmailFieldDisplayed(){
        actionDriver.waitForElementToVisible(inpEmailField);
        actionDriver.waitForElementToVisible(inpInvitationCode);
        return actionDriver.isDisplayed(inpEmailField) && actionDriver.isDisplayed(inpInvitationCode);

    }

    public void enterInpInvitationCode(String invitationCode){
        actionDriver.waitForElementToBeClickable(inpInvitationCode);
        actionDriver.clearText(inpInvitationCode);
        actionDriver.enterText(inpInvitationCode, invitationCode);
    }

    public void enterInpEmailField(String email){
        actionDriver.waitForElementToBeClickable(inpEmailField);
        actionDriver.clearText(inpEmailField);
        actionDriver.enterText(inpEmailField, email);
    }

    public void clickOnContinueFromRegisterPage(){
        actionDriver.waitForElementToBeClickable(btnContinueRegister);
        actionDriver.click(btnContinueRegister);
    }

    public void completeRegistration(String invitationCode, String email){
        enterInpInvitationCode(invitationCode);
        enterInpEmailField(email);
        clickOnContinueFromRegisterPage();
    }

    public boolean verifyContinueButtonEnabled(){
        actionDriver.waitForAllElementsToBeVisible(btnContinueRegister);
        boolean actualState  =  actionDriver.isButtonEnabled(btnContinueRegister);
        logger.info("Continue button state on Register page is {}", actualState);
        return actualState;
    }

    public String getErrorMessage(){
        actionDriver.waitForElementToVisible(errorMessage);
        return actionDriver.getErrorMessage(errorMessage);
    }

    public void clickOnGoBack(){
        actionDriver.waitForElementToBeClickable(btnGoBack);
        actionDriver.click(btnGoBack);
        logger.info("Clicked on Goback from the Otp verify page from register page");
    }





}
