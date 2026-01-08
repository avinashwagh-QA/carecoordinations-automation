package com.carecoordination.healthcare.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;


public class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static final DriverManager driverManager = new DriverManager();


    private DriverFactory() {
        //Prevent External instantiation
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void initDriver(String browser) {
        if (tlDriver.get() == null) {
            logger.info("Initiating driver on for browser {} on thread{}", browser, Thread.currentThread().getName());
            WebDriver driver = driverManager.createDriver(browser);
            tlDriver.set(driver);
            logger.debug("WebDriver instance set in ThreadLocal for thread: {}", Thread.currentThread().getName());
        } else {
            logger.warn("WebDriver already initialized for thread: {}", Thread.currentThread().getName());
        }
    }

    public static void quitDriver() {
        if (tlDriver.get() != null) {
            logger.info("Quitting WebDriver for thread: {}", Thread.currentThread().getName());
            tlDriver.get().quit();
            tlDriver.remove();
            logger.debug("WebDriver removed from ThreadLocal for thread: {}", Thread.currentThread().getName());
        } else {
            logger.warn("Attempted to quit WebDriver, but it was null for thread: {}", Thread.currentThread().getName());
        }
    }

}


