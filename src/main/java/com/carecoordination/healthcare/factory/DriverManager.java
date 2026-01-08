package com.carecoordination.healthcare.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DriverManager {

    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    //Initialize the Webdriver based on the config.properties
    protected WebDriver createDriver(String browser) {
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                logger.info("Chrome Driver Instance Is Created");
                break;
            case "firefox":
                driver = new FirefoxDriver();
                logger.info("FireFox Driver Instance Is Created");
                break;
            case "edge":
                driver = new EdgeDriver();
                logger.info("Edge Driver Instance Is Created");
                break;
            default:
                logger.error("Invalid browser name provided: {}", browser);
                throw new RuntimeException("browser not supported: " + browser);
        }
        logger.debug("Driver instance created successfully: {}", driver);
        return driver;
    }
}
