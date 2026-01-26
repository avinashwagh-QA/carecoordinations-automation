package com.carecoordination.healthcare.utilities;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EnterMpinUtil {

    private final ActionDriver actionDriver;

    private static  final Logger logger = LogManager.getLogger(EnterMpinUtil.class);

    public EnterMpinUtil(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
    }

    public void enterMpin(By mpinInputsLocator, String mpin){

        if (mpin == null || mpin.isEmpty()) {
            throw new IllegalArgumentException("mpin can not be null or empty");
        }

        actionDriver.waitForAllElementsToBeVisible(mpinInputsLocator);
         List<WebElement> mpinInputs = DriverFactory.getDriver().findElements(mpinInputsLocator);

         if (mpinInputs.size() < mpin.length()){
             throw new RuntimeException("Mpin length is greater then the number of input boxes");
         }

         char [] mpinInput = mpin.toCharArray();

         for (int i=0;i< mpinInput.length ;i++){

             WebElement input = mpinInputs.get(i);
             actionDriver.waitForElementToBeClickable(input);

             input.clear();
             input.sendKeys(String.valueOf(mpinInput[i]));

             logger.info("Entered MPIN digit at position {}", i+1);
         }
    }

    /**
     * Clear MPIN fields (for negative testing)
     */
    public void clearMpin(By mpinInputsLocator) {
        List<WebElement> mpinInputs = DriverFactory.getDriver().findElements(mpinInputsLocator);

        for (WebElement input : mpinInputs) {
            actionDriver.waitForElementToVisible(input);
            input.clear();
            logger.info("Cleared all MPIN input fields");
        }
    }



}
