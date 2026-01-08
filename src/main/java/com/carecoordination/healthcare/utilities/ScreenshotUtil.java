package com.carecoordination.healthcare.utilities;

import com.carecoordination.healthcare.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

        private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

        public static String takeScreenshot(String testName) throws IOException {

        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) throw new IOException("Driver is null");

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Save inside reports/screenshots/
        String reportsDir = System.getProperty("user.dir") + File.separator + "reports";
        String screenshotsDir = reportsDir + File.separator + "screenshots";

        Files.createDirectories(new File(screenshotsDir).toPath());

        String fileName = testName + "_" + timeStamp + ".png";
        File dest = new File(screenshotsDir + File.separator + fileName);

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(src.toPath(), dest.toPath());

        // RETURN RELATIVE PATH → THIS IS THE FIX
        return "screenshots/" + fileName;
    }

}
