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

    public boolean isManageTeamDisplayed() {
        actionDriver.isElementPresentAndDisplayed(manageTeam);
        boolean team = actionDriver.isDisplayed(manageTeam);
        logger.info("Manage team status for logged in user {}", team);
        return team;
    }











}
