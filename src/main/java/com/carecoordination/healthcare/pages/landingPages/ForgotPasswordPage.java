package com.carecoordination.healthcare.pages.landingPages;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.utilities.ConfigReader;
import com.carecoordination.healthcare.utilities.DropdownUtil;
import com.carecoordination.healthcare.utilities.EnterOTPUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class ForgotPasswordPage {

    private final ActionDriver actionDriver;
    private final DropdownUtil dropdownUtil;
    private final EnterOTPUtil enterOTPUtil;

    private static final Logger logger= LogManager.getLogger(ForgotPasswordPage.class);

    //Initialize the action drive object by passing the webdriver instance
    public ForgotPasswordPage (ActionDriver actionDriver){
        this.actionDriver = actionDriver;
        dropdownUtil = new DropdownUtil(actionDriver);
        enterOTPUtil = new EnterOTPUtil(actionDriver);
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

    private final By titleVerifyOtpPage = By.xpath("//div[@class='cc-heading-txt']");
    private final By linkResendOtp = By.xpath("//a[normalize-space()='Resend OTP']");
    private final By OTPInputs = By.cssSelector("div#otp input[type='text']");


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

    public void setOTPInputs(String otp){
        actionDriver.waitForElementToVisible(OTPInputs);
        enterOTPUtil.enterOTP(OTPInputs, otp);
        logger.info("OTP-Set on the field {}", otp);
    }

    public void clearOTPInput(){
        actionDriver.waitForElementToVisible(OTPInputs);
        enterOTPUtil.clearOtp(OTPInputs);
        logger.info("Otp field cleared.....");
    }

    //Method to set country code and phone number in forgot password
    public void setPhoneNumberForgotPassword(String countryCode, String phoneNumber){
        selectCountryCode(countryCode);
        setInputPhoneNumber(phoneNumber);
        clickOnVerifyPhoneNumber();
    }

    public String getOtpVerificationTitle(){
        actionDriver.waitForElementToVisible(titleVerifyOtpPage);
        String title = actionDriver.getText(titleVerifyOtpPage);
        logger.info("Title display on OTP verification page is {}", title);
        return title;
    }

    //method to check the resend OTP is present
    public boolean isResendOtpDisplayed(){
        return actionDriver.waitForResendOTP(linkResendOtp);
    }

    public void clickOnResendOtp(){
         actionDriver.waitForResendOTP(linkResendOtp);
         actionDriver.click(linkResendOtp);
    }

    /**
     *  Common method to navigate the Verify OTP page
     */
    public boolean navigateToVerificationOtpPage() {

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

        return actionDriver.waitForElementToVisible(titleVerifyOtpPage).isDisplayed();
    }





}
