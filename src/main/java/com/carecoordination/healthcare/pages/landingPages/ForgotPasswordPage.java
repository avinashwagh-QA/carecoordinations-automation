package com.carecoordination.healthcare.pages.landingPages;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.utilities.ConfigReader;
import com.carecoordination.healthcare.utilities.DropdownUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class ForgotPasswordPage {

    private final ActionDriver actionDriver;
    private final DropdownUtil dropdownUtil;
    private final OtpVerifyPage otpVerifyPage;

    private static final Logger logger= LogManager.getLogger(ForgotPasswordPage.class);

    //Initialize the action drive object by passing the webdriver instance
    public ForgotPasswordPage (ActionDriver actionDriver){
        this.actionDriver = actionDriver;
        otpVerifyPage = new OtpVerifyPage(actionDriver);
        dropdownUtil = new DropdownUtil(actionDriver);
    }

    //Defining locators for ForgotPassword
    private final By lnkForgotPasswordFromLogin = By.xpath("//span[@id='spanClick' and text()='Forgot your password?']");
    private final By headingMsgForgotPsd = By.xpath("//h6[normalize-space()='Forgot Password?']");
    private final By inputPhoneNumber = By.xpath("//input[@id='phone']");

   //Locators for country code
    private final By dropDownButton = By.cssSelector("button[data-bs-toggle='dropdown']");
    private final By getDropDownOptions = By.cssSelector(".dropdown-menu.show a.dropdown-item");

    private final By inpPhoneNumber = By.id("phone");
    private final By btnVerify = By.id("forgotPasswordBtn");

    private final By errorMessage = By.cssSelector(".custom-block-error-msg");



    //method to check the forgot link present on login page
    public boolean isForgotLinkDisplayed(){
        actionDriver.waitForAllElementsToBeVisible(lnkForgotPasswordFromLogin);
        logger.info("Forgot password link is displayed on login page...");
        return actionDriver.isDisplayed(lnkForgotPasswordFromLogin);
    }

    //method to click on forgot password from login
    public void clickOnForgotPassword(){
        actionDriver.waitForElementToBeClickable(lnkForgotPasswordFromLogin);
        actionDriver.click(lnkForgotPasswordFromLogin);
        logger.info("Clicked on forgot password lnk from login page ....");
    }

    //method to check forgot password page displayed
    public boolean isForgotPasswordDisplayed() {
        actionDriver.waitForElementToVisible(headingMsgForgotPsd);
        actionDriver.waitForElementToVisible(inputPhoneNumber);
        boolean pageForgotPassword = actionDriver.isDisplayed(headingMsgForgotPsd) && actionDriver.isDisplayed(inputPhoneNumber);
        logger.info("Forgot Password page is displayed ......");
        return pageForgotPassword;
    }

    public void selectCountryCode(String value){
        actionDriver.waitForElementToVisible(dropDownButton);
        dropdownUtil.selectDropDown(dropDownButton,getDropDownOptions, value);
        logger.info("Selecting country code {}", value);
    }

    public void setInputPhoneNumber(String number){
        actionDriver.waitForElementToBeClickable(inpPhoneNumber);
        actionDriver.enterText(inpPhoneNumber, number);
        logger.info("Add Phone number in the input field is {}", number);
    }

    public void clickOnVerifyPhoneNumber(){
        actionDriver.waitForElementToBeClickable(btnVerify);
        actionDriver.click(btnVerify);
        logger.info("Click On Verify Phone number on the Forgot Password");
    }

    public String getErrorMessage(){
        actionDriver.waitForElementToVisible(errorMessage);
        String msg = actionDriver.getErrorMessage(errorMessage);
        logger.info("Error message on the verify phone on Forgot password is : {}", msg);
        return msg;
    }



    //Method to set country code and phone number in forgot password
    public void setPhoneNumberForgotPassword(String countryCode, String phoneNumber){
        selectCountryCode(countryCode);
        setInputPhoneNumber(phoneNumber);
        clickOnVerifyPhoneNumber();
    }

    /**
     *  Common method to navigate the Verify OTP page from forgot password
     */
    public boolean navigateToVerificationOtpPageFromForgotPassword() {

        if (!isForgotLinkDisplayed()) {
            logger.error("Forgot Password link not found");
            return false;
        }
        isForgotLinkDisplayed(); //checking link present on login page

        clickOnForgotPassword(); // clicking on forgot password link

        if (!isForgotPasswordDisplayed()) {
            logger.error("Forgot Password page not loaded");
            return false;
        }

        // reading country code and phone number form properties file
        String countryCode = ConfigReader.getProperty("countryCode");
        String phoneNumber = ConfigReader.getProperty("validPhoneNumber");

        setPhoneNumberForgotPassword(countryCode, phoneNumber); // inserting data into field
        clickOnVerifyPhoneNumber();

        return otpVerifyPage.isOtpPageTitleDisplayed();
    }





}
