package com.carecoordination.healthcare.pages.landingPages;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class LandingPage {

    private ActionDriver actionDriver;

    private static final Logger logger= LogManager.getLogger(LandingPage.class);

    //Initialize the action drive object by passing the webdriver instance
    public LandingPage(ActionDriver actionDriver){
        this.actionDriver = actionDriver;
    }

    //Defining locators from HomePage
    private final By titleHomePage= By.xpath("//a[@aria-label='Home']");
    private final By loginLink = By.xpath("//a[@class='top-nav-btn'][normalize-space()='Log In']");
    private final By registerLink = By.xpath("//a[@class='top-nav-btn'][normalize-space()='Register']");


    // Home page title displayed
    public boolean isHomePageTitleDisplayed(){
        actionDriver.waitForElementToBeClickable(titleHomePage);
        return actionDriver.isDisplayed(titleHomePage);
    }

    //Click on login page
    public void clickOnLoginLink(){
        actionDriver.waitForElementToBeClickable(loginLink);
        actionDriver.click(loginLink);
        logger.info("Click on log in page from landing page");
    }

    public void clickOnRegisterLink(){
        actionDriver.waitForElementToBeClickable(registerLink);
        actionDriver.click(registerLink);
    }



}
