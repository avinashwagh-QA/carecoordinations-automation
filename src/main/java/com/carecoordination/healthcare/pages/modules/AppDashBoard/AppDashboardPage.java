package com.carecoordination.healthcare.pages.modules.AppDashBoard;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class AppDashboardPage {

    private final ActionDriver actionDriver;

    private static final Logger logger= LogManager.getLogger(AppDashboardPage.class);

    public AppDashboardPage(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
    }

    //Locators for login page
    private final By headerUserName = By.id("headerUserName");
    private final By dashboardHome= By.xpath("//div[normalize-space()='Home']");

    //logout locators
    private final By lnkLogOut = By.xpath("//a[@class='dropdown-item cursor-pointer']");
    private final By confirmYes = By.xpath("//button[@id='actionYes']");
    private final By confirmNo = By.xpath("//button[@id='actionNo']");


    //Username displayed in top header
    public boolean isUserNamePresent(){
        actionDriver.waitForElementToBeClickable(headerUserName);
        return actionDriver.isDisplayed(headerUserName);
    }

    public boolean isDashboardHomeDisplayed(){
        actionDriver.waitForAllElementsToBeVisible(dashboardHome);
       boolean dashboard = actionDriver.isDisplayed(dashboardHome);
        logger.info("Dashboard board displayed after login ...");
        return dashboard;
    }

    public String getUserName(){
        actionDriver.waitForElementToVisible(headerUserName);
        String userName = actionDriver.getText(headerUserName);
        logger.info("User Name displayed in the header {}", userName);
        return userName;
    }

    public void clickOnUserProfile(){
        actionDriver.waitForElementToBeClickable(headerUserName);
        actionDriver.click(headerUserName);
        logger.info("Clicked on User Profile ....");
    }

    //method on click on logout
    public void clickOnLogOut(){
        clickOnUserProfile();
        actionDriver.waitForElementToBeClickable(lnkLogOut);
        actionDriver.click(lnkLogOut);

        logger.info("Clicked on logout from User Profile modal.....");

        actionDriver.waitForElementToBeClickable(confirmYes);
        actionDriver.click(confirmYes);

        logger.info("The user click on confirm to logout.... ");
    }

}
