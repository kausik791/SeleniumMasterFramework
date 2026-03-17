package org.selenium.pom.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class ChromeDriverManager implements DriverManager
{

    @Override
    public WebDriver createDriver() {
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
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }
}
