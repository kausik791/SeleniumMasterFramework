package org.selenium.pom.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

public class DriverManager_withoutenum {
    public WebDriver initializeDriver() {
        String browser = System.getProperty("browser");
        WebDriver driver;
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup(); //Stores it in WebDriverManager’s cache (default location).
                //WebDriverManager.chromedriver().cachePath("Drivers").setup(); // it will download chromedriver to Drivers folder
                ChromeOptions options = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
// Disable password save bubble
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
// Disable password breach detection popup
                prefs.put("profile.password_manager_leak_detection", false);
// Disable autofill
                prefs.put("autofill.profile_enabled", false);
                options.setExperimentalOption("prefs", prefs);
// Additional stability args
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-save-password-bubble");
                options.addArguments("--disable-blink-features=AutomationControlled");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        return driver;
    }

}
