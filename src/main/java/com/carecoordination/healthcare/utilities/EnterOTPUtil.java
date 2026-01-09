package com.carecoordination.healthcare.utilities;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class EnterOTPUtil {

    private final ActionDriver actionDriver;

    private static final Logger logger = LogManager.getLogger(EnterOTPUtil.class);

    public EnterOTPUtil(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
    }

    public void enterOTP(By otpInputsLocator, String otp) {

        if (otp == null || otp.isEmpty()) {
            throw new IllegalArgumentException("OTP can not be null or empty");
        }

        actionDriver.waitForAllElementsToBeVisible(otpInputsLocator);
        List<WebElement> OtpInputs = DriverFactory.getDriver().findElements(otpInputsLocator);

        if (OtpInputs.size() < otp.length()) {
            throw new RuntimeException("OTP length is greater then the number of input boxes");
        }

        char[] otpDigits = otp.toCharArray();

        for (int i = 0; i < otpDigits.length; i++) {

            WebElement input = OtpInputs.get(i);
            actionDriver.waitForElementToBeClickable(input);

            input.clear();
            input.sendKeys(String.valueOf(otpDigits[i]));

            logger.info("Entered OTP digit at position {}", i + 1);

        }

    }

    /**
     * Clear OTP fields (for negative testing / resend)
     */
    public void clearOtp(By otpInputsLocator) {
        List<WebElement> otpInputs = DriverFactory.getDriver().findElements(otpInputsLocator);

        for (WebElement input : otpInputs) {
            actionDriver.waitForElementToBeClickable(input);
            input.clear();
            logger.info("Cleared all OTP input fields");
        }
    }

}
