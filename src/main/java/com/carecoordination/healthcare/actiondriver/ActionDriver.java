package com.carecoordination.healthcare.actiondriver;


import com.carecoordination.healthcare.factory.DriverFactory;
import com.carecoordination.healthcare.utilities.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class ActionDriver {

    private static final Logger logger = LogManager.getLogger(ActionDriver.class);

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final WebDriverWait OtpWait;

    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        int explicitWait = Integer.parseInt(ConfigReader.getProperty("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));

        int OtpWait = Integer.parseInt(ConfigReader.getProperty("OtpWait"));
        this.OtpWait = new WebDriverWait(driver, Duration.ofSeconds(OtpWait));

        logger.debug("ActionDriver initialized with explicitWait={} seconds", explicitWait);
    }

    // Wait for element to be clickable
    public WebElement waitForElementToBeClickable(By locator) {
        try {
            logger.debug("Waiting for element to be clickable: {}", locator);
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("Element not clickable: {}", locator);
            throw new RuntimeException("Element is not Clickable" + locator, e);
        }
    }

    // Wait for WebElement to be clickable-Used for table and list
    public void waitForElementToBeClickable(WebElement element) {
        try {
            logger.debug("Waiting for WebElement to be clickable");
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.error("WebElement not clickable", e);
            throw new RuntimeException("WebElement not clickable", e);
        }
    }

    // Wait for element to be visible
    public WebElement waitForElementToVisible(By locator) {
        try {
            logger.debug("Waiting for visibility of: {}", locator);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not visible: {}", locator);
            throw new RuntimeException("Element is not visible" + locator, e);
        }
    }

    //wait for list of WebElements
    public List<WebElement> waitForAllElementsToBeVisible(By locator) {
        try {
            logger.debug("Waiting for visibility of all the elements: {}", locator);
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            logger.error(" All the Elements are not visible: {}", locator);
            throw new RuntimeException("List of Elements are not visible" + locator, e);
        }
    }

    // Wait for element to be Present in the Dom
    public WebElement waitForElementToBePresent(By locator) {
        try {
            logger.debug("Waiting for Presence of: {}", locator);
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element is not present: {}", locator);
            throw new RuntimeException("Element is not present" + locator, e);
        }
    }

    //wait for page to be load
    public void waitForPageLoad() {
        wait.until(driver ->
                Objects.equals(((JavascriptExecutor) driver)
                        .executeScript("return document.readyState"), "complete"));
    }

    //Navigate Back form browser
    public void navigateToBack(){
        DriverFactory.getDriver().navigate().back();
    }

    //Method to Click an element using locator
    public void click(By locator) {
        try {
            logger.info("Clicking element: {}", locator);
            WebElement element = waitForElementToBeClickable(locator);
            element.click();
        } catch (Exception e) {
            throw new RuntimeException("Unable to click the element: " + locator, e);
        }
    }

    // Click using WebElement
    public void click(WebElement element) {
        try {
            waitForElementToBeClickable(element);
            element.click();
        } catch (Exception e) {
            throw new RuntimeException("Unable to click WebElement", e);
        }
    }

    // remains
    public boolean isButtonEnabled(By buttonLocator) {
        try {
            waitForElementToVisible(buttonLocator);
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLocator));
            return button.isEnabled();   // true=enabled, false=disabled
        } catch (Exception e) {
            logger.warn("Button not found or not interactable: {}", buttonLocator);
            return false;
        }
    }

    //method to get the error message on input fields
    public String getErrorMessage(By locator){
        waitForElementToVisible(locator);
        return getText(locator);
    }


    //Method to enter the value in the input
    public void enterText(By locator, String value) {
        try {
            logger.info("Entering text into {}", locator);
            WebElement element = waitForElementToVisible(locator);
            element.clear();
            element.sendKeys(value);
        } catch (Exception e) {
            logger.error("Unable to enter text in: {}", locator, e);
            throw new RuntimeException("Not able to send the keys " + locator + e.getMessage());
        }
    }

    //Method to get the value for element
    public String getText(By locator) {
        try {
            WebElement element = waitForElementToVisible(locator);
            String text = element.getText();
            logger.debug("Text retrieved from {}: {}", locator, text);
            return text;
        } catch (Exception e) {
            logger.error("Unable to get text from: {}", locator, e);
            throw new RuntimeException("Unable to get text from " + locator + e.getMessage());
        }
    }

    //Method for clear input
    public void clearText(By locator) {
        try {
            WebElement element = waitForElementToVisible(locator);
            element.click();

            // Select ALL and Delete to clear the input box
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            element.sendKeys(Keys.DELETE);
        } catch (Exception e) {
            logger.error("Unable to clear text in element: {}", locator, e);
            throw new RuntimeException("Not able to clear text for: " + locator + " - " + e.getMessage(), e);
        }
    }

    //method for get attribute value
    public String getAttribute(By locator, String attribute){
        waitForElementToVisible(locator);
        return driver.findElement(locator).getAttribute(attribute);
    }

    // method to check if an element is displayed
    public boolean isDisplayed(By locator) {
        try {
            logger.debug("Checking visibility for: {}", locator);
            WebElement element = waitForElementToVisible(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
            logger.debug("Element not displayed (not present/visible): {}", locator);
            return false;
        } catch (Exception e) {
            logger.warn("Unexpected error while checking visibility for: {}", locator, e);
            return false;  // still treat as not displayed for finder methods
        }
    }

    //method to scrollElement
    public void scrollToElement(By locator) {
        try {
            WebElement element = waitForElementToBePresent(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get element" + locator + e.getMessage());
        }

    }

    public boolean waitForResendOTP(By locator) {
        try {
            logger.debug("Waiting for visibility of OTP : {}", locator);
            OtpWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            logger.error("Resend OTP link not appeared after 40s");
            return false;
        }
    }


    // This method is used in autosuggestion wait for searching text until the list of suggestion appears
    public List<WebElement> waitForSuggestionToLoad(By suggestionLocators, String loadingText) {

        //1) Wait until the list of suggestion/list-box to visible
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(suggestionLocators));

        //2) Wait until something other than loading text is appear
        wait.until(driver1 -> {
            List<WebElement> items = driver1.findElements(suggestionLocators);
            if (items.isEmpty()) return false;

            boolean allLoading = true;
            for (WebElement e : items) {

                String t = e.getText().trim().toLowerCase();
                if (!t.isEmpty() && !t.contains(loadingText.toLowerCase())) {
                    allLoading = false;
                    break;
                }
            }
            return !allLoading;  // true when at least one real result OR "No Records Found"
        });

        // return fresh elements loading finish
        return DriverFactory.getDriver().findElements(suggestionLocators);
    }

    //Method for upload doc using javascript
    public void upload(By locator, String filePath) {

        WebElement e = waitForElementToBePresent(locator);

        String absolutePath = Paths.get(filePath).toAbsolutePath().toString();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.display='block'; arguments[0].style.visibility='visible';", e);
        e.sendKeys(absolutePath);
    }

    // common method to get the table rows and cell text from row and column

    public List<WebElement> getTableRows(By tableLocator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableLocator));
    }

    public String getCellText(WebElement rowElement, int colIndex) {

        return rowElement.findElement(By.xpath(".//div[" + colIndex + "]")).getText().trim();
    }

}


