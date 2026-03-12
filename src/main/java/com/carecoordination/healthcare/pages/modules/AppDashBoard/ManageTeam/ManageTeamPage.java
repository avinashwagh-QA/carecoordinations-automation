package com.carecoordination.healthcare.pages.modules.AppDashBoard.ManageTeam;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.components.BranchSelectorComponent;
import com.carecoordination.healthcare.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ManageTeamPage {

    private final ActionDriver actionDriver;
    private final BranchSelectorComponent branchSelectorComponent;

    private static final Logger logger = LogManager.getLogger(ManageTeamPage.class);

    public ManageTeamPage(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
        this.branchSelectorComponent = new BranchSelectorComponent();
    }

    //Locators for ManageTeam
    private final By tabManageTeam = By.xpath("//a[contains(@href,'manage-team-list') and normalize-space()='Manage Team']");
    private final By tabManageMiniTeams = By.xpath("//a[contains(@href,'manage-mini-team') and normalize-space()='Manage Mini-Teams']");
    private final By inpSearchUser = By.id("searchUsersInput");

    private final By activeTab = By.id("activeTab");
    private final By pendingTab = By.id("PendingRegTab");
    private final By teamHistory = By.id("TeamHistoryTab");

    private final By pagination = By.xpath("//div[@id='manageteamListingsTbl_paginate']//a[contains(@class,'paginate_button')]");

    //loader
    private final By loaderLocator = By.xpath("//div[@id='subAdminMainNormalBodySection']//div[contains(@class,'pageAjaxLoader')]");

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
        actionDriver.safeClick(pendingTab);
        logger.info("Click on Pending tab - Mange team in Team menu....");
    }

    public void clickOnTeamHistoryTab(){
        actionDriver.waitForElementToBeClickable(teamHistory);
        actionDriver.click(teamHistory);
        logger.info("Click on Team History tab - Mange team in Team menu....");
    }

    public void waitForLoaderToDisappear(){
        actionDriver.waitForLoaderToDisappear(loaderLocator);
    }

    public void selectBranch(String branchName){
        branchSelectorComponent.selectBranch(branchName);
    }

    public void searchManageTeamByStaffName(String staffName){
        actionDriver.waitForElementToBeClickable(inpSearchUser);
        actionDriver.enterText(inpSearchUser, staffName);
        logger.info("Search on Manage team by Staff name : {}", staffName);
    }

    public void SearchManageTeamByEmail(String email){
        actionDriver.waitForElementToBeClickable(inpSearchUser);
        actionDriver.enterText(inpSearchUser, email);
    }

    //Pagination method to  get all pages
    public int getAllPages(){
        actionDriver.waitForAllElementsToBeVisible(pagination);
        List<WebElement> pages = DriverFactory.getDriver().findElements(pagination);
        return  pages.size();
    }

    //Click on Page number
    public void clickOnPageNumber(int pageNumber) {
        By page = By.xpath("//div[@id='manageteamListingsTbl_paginate']//a[normalize-space()='" + pageNumber + "']");
        actionDriver.click(page);
        logger.info("Click on page number :{}", pageNumber);
    }




}
