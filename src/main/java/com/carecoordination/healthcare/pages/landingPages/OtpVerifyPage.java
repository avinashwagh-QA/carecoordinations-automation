package com.carecoordination.healthcare.pages.landingPages;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.utilities.EnterOTPUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class OtpVerifyPage {

    private final ActionDriver actionDriver;
    private final EnterOTPUtil enterOTPUtil;
    private static final Logger logger= LogManager.getLogger(OtpVerifyPage.class);

    public OtpVerifyPage(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
        enterOTPUtil = new EnterOTPUtil(actionDriver);
    }

    // Locators : OTP verify page
    private final By titleVerifyOtpPage = By.xpath("//div[@class='cc-heading-txt']");
    private final By linkResendOtpForgotPassword = By.xpath("//a[normalize-space()='Resend OTP']");
    private final By linkResendOtpRegisterPage = By.xpath("//form[@id='verifyCommonOTPForm']//a");

    private final By OTPInputs = By.cssSelector("div#otp input[type='text']");

    public boolean isTitleForOtpPageDisplayed(){
        actionDriver.waitForElementToVisible(titleVerifyOtpPage);
        boolean title = actionDriver.isDisplayed(titleVerifyOtpPage);
        logger.info("The tile on verify otp page is displayed  {}", title);
        return title;
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

    public String getOtpVerificationTitle(){
        actionDriver.waitForElementToVisible(titleVerifyOtpPage);
        String title = actionDriver.getText(titleVerifyOtpPage);
        logger.info("Title display on OTP verification page is {}", title);
        return title;
    }

    //method to check the resend OTP is present
    public boolean isResendOtpDisplayedOnForgotPassword(){
        return actionDriver.waitForResendOTP(linkResendOtpForgotPassword);
    }

    public boolean isResendOtpDisplayedOnRegisterPage(){
        return actionDriver.waitForResendOTP(linkResendOtpRegisterPage);
    }

    public void clickOnResendOtpForForgotPassword(){
        actionDriver.waitAndClickResendOtp(linkResendOtpForgotPassword);

    }

    public void clickOnResendOtpForRegisterPage(){
        actionDriver.waitAndClickResendOtp(linkResendOtpRegisterPage);
    }




}
