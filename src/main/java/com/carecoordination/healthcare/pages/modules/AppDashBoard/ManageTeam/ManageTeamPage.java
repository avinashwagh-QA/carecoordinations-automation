package com.carecoordination.healthcare.pages.modules.AppDashBoard.ManageTeam;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class ManageTeamPage {

    private final ActionDriver actionDriver;

    private static final Logger logger = LogManager.getLogger(ManageTeamPage.class);

    public ManageTeamPage(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
    }

    //Locators for ManageTeam
    private final By tabManageTeam = By.xpath("//a[contains(@href,'manage-team-list') and normalize-space()='Manage Team']");
    private final By tabManageMiniTeams = By.xpath("//a[contains(@href,'manage-mini-team') and normalize-space()='Manage Mini-Teams']");

    private final By drpBranchSelection = By.id("selectBranchSectionTeam");

    private final By btnInviteUser = By.id("createInviteUser");
    private final By inpSearchUser = By.id("searchUsersInput");


    private final By activeTab = By.id("activeTab");
    private final By pendingTab = By.id("PendingRegTab");
    private final By teamHistory = By.id("TeamHistoryTab");


    public void clickOnManageTeamTab(){
        actionDriver.waitForElementToBeClickable(tabManageTeam);
        actionDriver.click(tabManageTeam);
        logger.info("Click on Mange Team tab in Team menu....");
    }

    public void clickOnManageMiniTeamsTab(){
        actionDriver.waitForElementToBeClickable(tabManageMiniTeams);
        actionDriver.click(tabManageMiniTeams);
        logger.info("Click on Mange mini Teams tab in Team menu....");
    }

    public void clickOnActiveTab(){
        actionDriver.waitForElementToBeClickable(activeTab);
        actionDriver.click(activeTab);
        logger.info("Click on Active tab - Mange team in Team menu....");
    }

    public void clickOnPendingTab(){
        actionDriver.waitForElementToBeClickable(pendingTab);
        actionDriver.click(pendingTab);
        logger.info("Click on Pending tab - Mange team in Team menu....");
    }

    public void clickOnTeamHistoryTab(){
        actionDriver.waitForElementToBeClickable(teamHistory);
        actionDriver.click(teamHistory);
        logger.info("Click on Team History tab - Mange team in Team menu....");
    }




}
