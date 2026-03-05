package com.carecoordination.healthcare.components;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BranchSelectorComponent {

    private final WebDriver driver;
    private final ActionDriver actionDriver;

   public BranchSelectorComponent(){
        this.driver = DriverFactory.getDriver();
        this.actionDriver = new ActionDriver(driver);
    }

    private final By branchDropdown = By.id("selectBranchSectionTeam");
    private final By searchInput = By.cssSelector("input[type='search']");
    private final By branchOptions = By.cssSelector(".dropdown-menu.show .dropdown-item");

    public void selectBranch(String branchName){

        actionDriver.waitForElementToBeClickable(branchDropdown);
        actionDriver.click(branchDropdown);
        actionDriver.enterText(searchInput, branchName);

        List<WebElement> options = driver.findElements(branchOptions);

        for (WebElement option : options){

            if (option.getText().trim().equalsIgnoreCase(branchName)){
                option.click();
                break;
            }
        }
    }

}
