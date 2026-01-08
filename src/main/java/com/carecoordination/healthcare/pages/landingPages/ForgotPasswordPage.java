package com.carecoordination.healthcare.pages.landingPages;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class ForgotPasswordPage {

    private final ActionDriver actionDriver;


    private static final Logger logger= LogManager.getLogger(ForgotPasswordPage.class);

    //Initialize the action drive object by passing the webdriver instance
    public ForgotPasswordPage (ActionDriver actionDriver){
        this.actionDriver = actionDriver;
    }

    //Defining locators for ForgotPassword
    private final By lnkForgotPasswordFromLogin = By.xpath("//span[@id='spanClick' and text()='Forgot your password?']");
    private final By headingMsgForgotPsd = By.xpath("//h6[normalize-space()='Forgot Password?']");
    private final By inputPhoneNumber = By.xpath("//input[@id='phone']");

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
    public boolean isForgotPasswordDisplayed(){
        actionDriver.waitForElementToVisible(headingMsgForgotPsd);
        actionDriver.waitForElementToVisible(inputPhoneNumber);
       return actionDriver.isDisplayed(headingMsgForgotPsd) && actionDriver.isDisplayed(inputPhoneNumber);
    }

}
