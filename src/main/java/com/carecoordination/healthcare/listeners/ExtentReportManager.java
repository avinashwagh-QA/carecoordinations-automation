package com.carecoordination.healthcare.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.carecoordination.healthcare.utilities.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener  {

    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);

    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static final ThreadLocal<ExtentTest> extTest = new ThreadLocal<>();
    private String reportFileName;

    @Override
    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reportFileName = "Test-Report-" + timeStamp + ".html";
        String reportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + reportFileName;

        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Care Coordinations Automation Report");
        sparkReporter.config().setReportName("Care Coordinations Functional Tests");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "OrangeHRM");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", System.getProperty("user.name"));

        // optional: add params from testng.xml
        String os = context.getCurrentXmlTest().getParameter("os");
        if (os != null) extent.setSystemInfo("OS", os);

        String browser = context.getCurrentXmlTest().getParameter("browser");
        if (browser != null) extent.setSystemInfo("Browser", browser);

        List<String> groups = context.getCurrentXmlTest().getIncludedGroups();
        if (groups != null && !groups.isEmpty()) extent.setSystemInfo("Groups", groups.toString());

        logger.info("ExtentReports initialized: {}", reportPath);
    }


    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTest test = extent.createTest(result.getTestClass().getName() + " :: " + testName);
        extTest.set(test);
        test.log(Status.INFO, "Test started: " + testName);
        logger.info("TEST STARTED: {}", testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = extTest.get();
        if (test != null) test.log(Status.PASS, result.getMethod().getMethodName() + " passed");
        logger.info("TEST PASSED: {}", result.getMethod().getMethodName());
        extTest.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extTest.get(); // your ThreadLocal<ExtentTest>
        String testName = result.getMethod().getMethodName();

        if (test != null) {
            test.log(Status.FAIL, testName + " failed");
            test.log(Status.INFO, result.getThrowable());
        }

        try {
            // TAKE SCREENSHOT ONCE (ScreenshotUtil returns RELATIVE path: "screenshots/<file>.png")
            String relativePath = ScreenshotUtil.takeScreenshot(testName); // e.g. "screenshots/invalidLogin_20251202_142936.png"

            // Build absolute path to verify file existence (report is in reports/)
            String reportsDir = System.getProperty("user.dir") + File.separator + "reports";
            File imgFile = new File(reportsDir + File.separator + relativePath.replace("/", File.separator));

            if (imgFile.exists() && imgFile.length() > 0) {
                // Attach using the relative path so Extent resolves it relative to the report HTML
                test.addScreenCaptureFromPath(relativePath);
                logger.info("Attached screenshot to report: {}", relativePath);
            } else {
                logger.warn("Screenshot file missing or empty at expected location: {}", imgFile.getAbsolutePath());
                if (test != null) test.info("Screenshot file missing: " + imgFile.getAbsolutePath());
            }
        } catch (Exception e) {
            logger.error("Failed to capture/attach screenshot for test: {}", testName, e);
            if (test != null) test.info("Failed to capture screenshot: " + e.getMessage());
        }

        logger.error("TEST FAILED: {}", testName, result.getThrowable());
        extTest.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extTest.get();
        if (test != null) {
            test.log(Status.SKIP, result.getMethod().getMethodName() + " skipped");
            test.log(Status.INFO, result.getThrowable());
        }
        logger.warn("TEST SKIPPED: {}", result.getMethod().getMethodName());
        extTest.remove();
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
            logger.info("Extent report flushed");
            String path = System.getProperty("user.dir") + File.separator + "reports" + File.separator + reportFileName;
            logger.info("Extent report available at: {}", path);
        }
    }

    // Optional: other ITestListener methods can be left empty or implemented
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

}