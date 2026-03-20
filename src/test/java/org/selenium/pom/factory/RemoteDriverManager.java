package org.selenium.pom.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.selenium.pom.constants.DriverType;
import org.selenium.pom.utils.ConfigLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RemoteDriverManager implements DriverManager {
    private final DriverType driverType;

    public RemoteDriverManager(DriverType driverType) {
        this.driverType = driverType;
    }

    @Override
    public WebDriver createDriver() {
        URL gridUrl = buildGridUrl();
        WebDriver driver;
        switch (driverType) {
            case CHROME:
                driver = new RemoteWebDriver(gridUrl, buildChromeOptions());
                break;
            case FIREFOX:
                driver = new RemoteWebDriver(gridUrl, buildFirefoxOptions());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + driverType);
        }
        driver.manage().window().maximize();
        return driver;
    }

    private URL buildGridUrl() {
        try {
            return new URL(ConfigLoader.getInstance().getGridUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid gridUrl: " + ConfigLoader.getInstance().getGridUrl(), e);
        }
    }

    private ChromeOptions buildChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-blink-features=AutomationControlled");
        return options;
    }

    private FirefoxOptions buildFirefoxOptions() {
        return new FirefoxOptions();
    }
}
