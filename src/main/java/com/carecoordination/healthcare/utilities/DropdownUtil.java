package com.carecoordination.healthcare.utilities;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DropdownUtil {

    private final ActionDriver actionDriver;


    private static final Logger logger = LogManager.getLogger(DropdownUtil.class);

    public DropdownUtil(ActionDriver actionDriver){
        this.actionDriver = actionDriver;
    }

    /**
     * Generic method to select dropdown option by visible text
     */

    public void selectDropDown(By dropdownButton, By optionSelector, String value) {

        logger.info("Selecting dropdown value: {}", value);

        actionDriver.waitForElementToBeClickable(dropdownButton);
        actionDriver.click(dropdownButton);

        List<WebElement> options = DriverFactory.getDriver().findElements(optionSelector);

        for (WebElement option : options) {

            if (option.getText().trim().equalsIgnoreCase(value)) {
                option.click();
                logger.info("Dropdown value selected: {}", value);
                return;
            }

        }

        throw new RuntimeException("Option not found in dropdown :" + value);

    }


}
