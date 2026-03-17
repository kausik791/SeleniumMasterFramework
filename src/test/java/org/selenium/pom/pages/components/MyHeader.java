package org.selenium.pom.pages.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.StorePage;

public class MyHeader extends BasePage {
    public MyHeader(WebDriver driver) {
        super(driver);
    }
    private final By storeMenuLink=By.xpath("//li[@id='menu-item-1227']");
    @Step("Navigate to store using header menu")
    public StorePage navigateToStoreUsingMenu() {
        //driver.findElement(storeMenuLink).click(); // driver is inherited from BasePage
        wait.until(ExpectedConditions.elementToBeClickable(storeMenuLink)).click();
        return new StorePage(driver);
    }
}
