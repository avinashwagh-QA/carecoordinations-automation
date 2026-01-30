package com.carecoordination.healthcare.pages.modules.AppDashBoard;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class HeaderPage {

    private final ActionDriver actionDriver;

    private static final Logger logger = LogManager.getLogger(HeaderPage.class);

    public HeaderPage(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
    }

    //Header Menus - locators
    private final By manageTeam = By.xpath("//a[contains(@href,'manage-team')]");
    private final By channelHistory = By.xpath("//a[contains(@href,'channel-history')]");
    private final By channelUsage = By.xpath("//a[contains(@href,'channel-usages')]");
    private final By alerts = By.id("dropdownMenuAlertNotifications");
    private final By availability = By.xpath("//a[contains(@href,'work-availability')]");


    //Method for is header menus(Name of the menu) is displayed
    public boolean isManageTeamDisplayed() {
        actionDriver.isElementPresentAndDisplayed(manageTeam);
        boolean team = actionDriver.isDisplayed(manageTeam);
        logger.info("Manage team status for logged in user {}", team);
        return team;
    }

    public boolean isChannelHistoryDisplayed() {
        actionDriver.isElementPresentAndDisplayed(channelHistory);
        boolean history = actionDriver.isDisplayed(channelHistory);
        logger.info("Channel History team status for logged in user {}", history);
        return history;
    }

    public boolean isChannelUsageDisplayed(){
        actionDriver.isElementPresentAndDisplayed(channelUsage);
        boolean usage = actionDriver.isDisplayed(channelUsage);
        logger.info("Channel Usage team status for logged in user {}", usage);
        return usage;
    }

    public boolean availabilityDisplayed(){
        actionDriver.isElementPresentAndDisplayed(availability);
        boolean availabilityMenu = actionDriver.isDisplayed(availability);
        logger.info("Work availability Menu option status for logged in user {}", availabilityMenu);
        return availabilityMenu;
    }

    public boolean isAlertDisplayed(){
        actionDriver.isElementPresentAndDisplayed(alerts);
        boolean alert = actionDriver.isDisplayed(alerts);
        logger.info("Alert Menu option status for logged in user {}", alert);
        return alert;
    }













}
