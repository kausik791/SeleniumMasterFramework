package org.selenium.pom.base;

import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.constants.DriverType;
import org.selenium.pom.factory.DriverManagerFactory;
import org.selenium.pom.utils.CookieUtils;
import org.selenium.pom.utils.ConfigLoader;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.List;

public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    private ThreadLocal<WebDriver> driver= new ThreadLocal<>();

    private void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }
    public WebDriver getDriver() {
        return driver.get();
    }

    @Parameters("browser")
    @BeforeMethod
    public synchronized void  startDriver(@Optional String browser)//@Optional will mark this parameter from the testng file as optional parameterso that we can hard code it as well
    {
        String localBrowser = "";
        //localBrowser = System.getProperty("browser");// for running from cmd or vm, cmd always take prcedence if both are there
        //localBrowser=browser; // it's for testng.xml but  If VM option is already set then it will take precedence, so we have to delete vm options first to use browser from testng.xml
        if(browser !=null){
            localBrowser = System.getProperty("browser",browser); //using cmd,vm,testng, but won't work with rightclick test class run unless vm is set because in BaseTest @Parameters("browser")
        }
        else
            localBrowser = "CHROME";// it will work after adding @Optional in the method
        //localBrowser= System.getProperty("FIREFOX",browser); //using cmd,vm,testng, but won't work with rightclick test class run unless vm is set because in BaseTest @Parameters("browser")
        //localBrowser = System.getProperty("browser",browser); //using cmd,vm,testng, but won't work with rightclick test class run unless vm is set because in BaseTest @Parameters("browser")
        //setDriver(new DriverManager().initializeDriver(localBrowser)); // when using orginal DriverManager class
        setDriver(DriverManagerFactory.getManager(DriverType.valueOf(localBrowser)).createDriver());
        logger.info("Thread START: {}, DRIVER: {}", Thread.currentThread().getId(), getDriver());
    }

    @BeforeSuite(alwaysRun = true)
    public void writeAllureMetadata() {
        File resultsDir = new File("target", "allure-results");
        try {
            if (!resultsDir.exists() && !resultsDir.mkdirs()) {
                logger.warn("Failed to create Allure results directory: {}", resultsDir.getAbsolutePath());
            }
            writeAllureEnvironment(resultsDir);
            copyAllureCategories(resultsDir);
        } catch (IOException e) {
            logger.warn("Failed to prepare Allure metadata", e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void quitDriver() {
        logger.info("Thread END: {}, DRIVER: {}", Thread.currentThread().getId(), getDriver());
        getDriver().quit();
    }
    @Step("Inject API cookies into browser")
    public void injectCookiesToBrowser(Cookies cookies){
        List<Cookie> seleniumCookies = new CookieUtils().convertRestAssuredCookiesToSeleniumCookies(cookies);
        for(Cookie cookie: seleniumCookies){
            getDriver().manage().addCookie(cookie);
        }

    }

    private void writeAllureEnvironment(File resultsDir) throws IOException {
        Properties props = new Properties();
        props.setProperty("baseUrl", ConfigLoader.getInstance().getBaseUrl());
        props.setProperty("browser", System.getProperty("browser", "CHROME"));
        props.setProperty("os", System.getProperty("os.name"));
        props.setProperty("java.version", System.getProperty("java.version"));
        props.setProperty("env", System.getProperty("env", "local"));
        File envFile = new File(resultsDir, "environment.properties");
        try (OutputStream os = new FileOutputStream(envFile)) {
            props.store(os, null);
        }
    }

    private void copyAllureCategories(File resultsDir) throws IOException {
        File targetFile = new File(resultsDir, "categories.json");
        try (InputStream in = BaseTest.class.getClassLoader().getResourceAsStream("categories.json")) {
            if (in == null) {
                return;
            }
            try (OutputStream out = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }

}
