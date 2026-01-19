package com.carecoordination.healthcare.pages.landingPages;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class ResetPasswordPage {

    private final ActionDriver actionDriver;

    private static final Logger logger = LogManager.getLogger(ResetPasswordPage.class);

    //Initialize the action drive object by passing the webdriver instance
    public ResetPasswordPage(ActionDriver actionDriver){
        this.actionDriver = actionDriver;
    }

    //Defining locators for ForgotPassword
    private final By titleResetPassword = By.xpath("//h6[normalize-space()='Reset password']");

    private final By subTitleResetPassword = By.xpath("//p[normalize-space()='Please enter a new password']");

    private final By inputNewPassword = By.id("password_reset");
    private final By inputConfirmPassword = By.id("confirm_password_reset");

    private final By btnSubmit= By.id("resetPasswordBtn");

    //Title for reset password
    public String getResetPasswordPageTitle(){
        actionDriver.waitForElementToVisible(titleResetPassword);
        String title = actionDriver.getText(titleResetPassword);
        logger.info("Title displayed on reset password is {}", title);
        return title;
    }

    //Subtitle is displayed for reset password
    public String getSubTitleResetPassword(){
        actionDriver.waitForElementToVisible(subTitleResetPassword);
        return actionDriver.getText(subTitleResetPassword);
    }

    public boolean isNewPasswordAndConfirmPasswordDisplayed(){
        actionDriver.waitForElementToVisible(inputNewPassword);
        actionDriver.waitForElementToVisible(inputConfirmPassword);
        boolean password = actionDriver.isDisplayed(inputNewPassword) && actionDriver.isDisplayed(inputConfirmPassword);
        logger.info("On reset password page new password and confirm password field is displayed...");
        return password;
    }

    public void setInputNewPassword(String newPassword){
        actionDriver.waitForElementToBeClickable(inputNewPassword);
        actionDriver.enterText(inputNewPassword, newPassword);
    }

    public void setInputConfirmPassword(String confirmPassword){
        actionDriver.waitForElementToBeClickable(inputConfirmPassword);
        actionDriver.enterText(inputNewPassword, confirmPassword);
    }

    public boolean verifyButtonEnabled(){
        actionDriver.waitForAllElementsToBeVisible(btnSubmit);
      boolean actualState  =  actionDriver.isButtonEnabled(btnSubmit);
      logger.info("Submit button state on Reset-password is {}", actualState);
      return  actualState;
    }





}
