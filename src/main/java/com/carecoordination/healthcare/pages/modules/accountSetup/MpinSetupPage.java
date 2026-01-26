package com.carecoordination.healthcare.pages.modules.accountSetup;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.utilities.EnterMpinUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class MpinSetupPage {

    private final ActionDriver actionDriver;
    private final EnterMpinUtil enterMpinUtil;

    private static final Logger logger = LogManager.getLogger(MpinSetupPage.class);

    public MpinSetupPage(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
        enterMpinUtil = new EnterMpinUtil(actionDriver);
    }

    //locators for MPIN modal
    private final By modalMpinSetup = By.id("setMpinProfileModal");
    private final By modalTitleMpinSetup = By.xpath("//h6[normalize-space()='Setup YOUR account security']");

    private final By mpinInputs = By.xpath("//form[@id='setMpinProfileCommonForm']//div[@id='mpin']//input");
    private final By btnSetMpin = By.id("setMpinProfileSaveBtn");

    public boolean isAccountSetMpinModalIsDisplayed(){
        actionDriver.waitForElementToVisible(modalMpinSetup);
        boolean modal = actionDriver.isDisplayed(modalMpinSetup);
        logger.info("Account setup MPIN page is displyed ..{}", modal);
        return modal;
    }

    public String getTitleForMpinAccountSetup() {
        actionDriver.waitForElementToVisible(modalTitleMpinSetup);
        String title = actionDriver.getText(modalTitleMpinSetup);
        logger.info("Title displayed for the Mpin modal ...{}", title);
        return title;
    }

    public void enterMpin(String mpin){
        actionDriver.waitForElementToVisible(mpinInputs);
        actionDriver.waitForElementToBeClickable(mpinInputs);

        enterMpinUtil.enterMpin(mpinInputs, mpin);
        logger.info("MPIN set on the field {}", mpin);
    }

    public boolean isBtnEnabled(){
        boolean status = actionDriver.isButtonEnabled(btnSetMpin);
        logger.info("Button Set MPIn state is {}", status);
        return status;
    }

    public void clickOnSetInputs(){
        actionDriver.waitForElementToBeClickable(btnSetMpin);
        actionDriver.click(btnSetMpin);
        logger.info("Clicked on the button set MPIn ....");
    }





}
