package org.selenium.pom.listeners;

import io.qameta.allure.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.selenium.pom.base.BaseTest;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        if (!(instance instanceof BaseTest)) {
            return;
        }
        WebDriver driver = ((BaseTest) instance).getDriver();
        if (driver == null) {
            return;
        }
        attachScreenshot(driver, result.getMethod().getMethodName());
        attachPageSource(driver);
        attachBrowserConsoleLogs(driver);
        attachFrameworkLogs();
        saveFailureScreenshot(driver, result);
    }

    @Attachment(value = "Screenshot - {testName}", type = "image/png")
    private byte[] attachScreenshot(WebDriver driver, String testName) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page Source", type = "text/html")
    private String attachPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    @Attachment(value = "Browser Console Logs", type = "text/plain")
    private String attachBrowserConsoleLogs(WebDriver driver) {
        try {
            LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
            StringBuilder sb = new StringBuilder();
            for (LogEntry entry : logs) {
                sb.append(entry.getLevel()).append(" ")
                        .append(entry.getMessage()).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "Browser logs not available: " + e.getMessage();
        }
    }

    @Attachment(value = "Framework Logs", type = "text/plain")
    private byte[] attachFrameworkLogs() {
        File logFile = new File("target", "logs" + File.separator + "automation.log");
        try {
            if (!logFile.exists()) {
                return ("Log file not found: " + logFile.getAbsolutePath()).getBytes();
            }
            try (FileInputStream in = new FileInputStream(logFile);
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                return out.toByteArray();
            }
        } catch (IOException e) {
            logger.warn("Failed to read log file: {}", logFile.getAbsolutePath(), e);
            return ("Failed to read log file: " + e.getMessage()).getBytes();
        }
    }

    private void saveFailureScreenshot(WebDriver driver, ITestResult result) {
        File destFile = new File("target" + File.separator + "screenshots" + File.separator +
                result.getTestClass().getRealClass().getSimpleName() + "_" +
                result.getMethod().getMethodName() + ".png");
        File parent = destFile.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                logger.warn("Failed to create screenshot directory: {}", parent.getAbsolutePath());
            }
        }
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(), "PNG", destFile);
        } catch (IOException e) {
            logger.warn("Failed to write screenshot: {}", destFile.getAbsolutePath(), e);
        }
    }
}
