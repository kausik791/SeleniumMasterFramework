package org.selenium.pom.base;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pom.utils.ConfigLoader;

import java.time.Duration;
import java.util.List;

public class BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Step("Open page endpoint: {endPoint}")
    public void load(String endPoint) {
        //driver.get("https://askomdch.com" + endPoint);
        driver.get(ConfigLoader.getInstance().getBaseUrl() + endPoint); // using ConfigLoader class  for base URL
    }

    @Step("Wait for overlays to disappear")
    public void waitForOverlaysToDisappear(By overlay) {
        List<WebElement> overlays = driver.findElements(overlay);
        logger.debug("Overlay count: {}", overlays.size());
        if (overlays.size() > 0) {
            wait.until(ExpectedConditions.invisibilityOfAllElements(overlays));
            logger.debug("Overlays invisible");
        } else {
            logger.debug("Overlay not found");
        }

    }

    @Step("Wait for element to be visible")
    public WebElement waitForElementToBeVisible(By element) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(element));

    }

}
