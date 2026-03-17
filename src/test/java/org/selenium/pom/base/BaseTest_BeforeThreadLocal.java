package org.selenium.pom.base;

import org.openqa.selenium.WebDriver;
import org.selenium.pom.factory.DriverManagerOriginal;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest_BeforeThreadLocal {
    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void stratDriver(String browser) {
        driver = new DriverManagerOriginal().initializeDriver(browser);
    }

    @AfterMethod
    public void quitDriver() {
        driver.quit();
    }


}
