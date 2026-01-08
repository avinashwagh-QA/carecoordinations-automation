package com.carecoordination.healthcare.pages.landingPages;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class LoginPage {

    private final ActionDriver actionDriver;

    private static final Logger logger= LogManager.getLogger(LoginPage.class);

    //Initialize the action drive object by passing the webdriver instance
    public LoginPage(ActionDriver actionDriver){
        this.actionDriver = actionDriver;
    }

    //Locators for login page
    private final By inputEmail = By.id("email");
    private final By inputPassword = By.id("password");
    private final By btnLogin = By.xpath("//button[@id='FinalCommonLoginBtn']");
    private final By loginPage = By.xpath("//span[@class='login100-form-title p-b-10']");

    private final By lnkForgotPassword = By.xpath("//a[@class='forgot-password-txt spanAnchor']");
    By lnkRegister = By.cssSelector("a.signup-link[href*='user-register']");

    private final By errorMessage = By.xpath("//div[@class='custom-block-error-msg mt-3']");



    //method for login page displayed
    public boolean isLoginPageDisplayed(){
        actionDriver.waitForElementToVisible(loginPage);
        boolean displayed = actionDriver.isDisplayed(loginPage);
        logger.info("Login page displayed status: {}", displayed);
        return displayed;
    }

    //method for click on registration from login page
    public void clickOnRegisterFromLogin(){
        actionDriver.waitForElementToBeClickable(lnkRegister);
        actionDriver.click(lnkRegister);
        logger.info("Clicked on Registration link from login page");
    }

    //Method to login
    public void login(String email, String password) {
        logger.info("Adding Username and Password {} {}", email, password);

        if (email != null) {
            actionDriver.enterText(inputEmail, email);
        }
        if (password != null) {
            actionDriver.enterText(inputPassword, password);
        }
        actionDriver.click(btnLogin);
        logger.info("Clicking  on login button ......");

    }



    //method for get message on invalid attempts
    public String getErrorMessage(){
        actionDriver.waitForElementToVisible(errorMessage);
        return actionDriver.getText(errorMessage);
    }


}
