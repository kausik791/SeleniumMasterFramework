package org.selenium.pom.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class AccountPage extends BasePage {
    private final By usernameFld = By.cssSelector("#username");
    private final By passwordFld = By.cssSelector("#password");
    private final By loginBtn = By.xpath("//button[contains(@name,'login')]");
    private final By errorMessage = By.xpath("//ul[@class='woocommerce-error']/child::li");


    public AccountPage(WebDriver driver) {
        super(driver);
    }
    public AccountPage load(){
        load("/account/");
        return this;
    }

    @Step("Enter username")
    public AccountPage enterUserName(String username){
        //driver.findElement(usernameFld).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFld)).sendKeys(username);
        return this;
    }
    @Step("Enter password")
    public AccountPage enterPassword(String password){
        //driver.findElement(passwordFld).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordFld)).sendKeys(password);
        return this;
    }
    @Step("Click login button")
    public AccountPage clickLoginBtn(){
        //driver.findElement(loginBtn).click();
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
        return this;
    }
    private AccountPage waitForLoginBtnToDisappear(){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginBtn));
        return this;
    }

    //method chaining for functional convenience
    @Step("Login with username and password")
    public AccountPage login(String username, String password){
        return
                enterUserName(username).
                        enterPassword(password).
                        clickLoginBtn();


    }

    public String getErrorTxt(){
        return driver.findElement(errorMessage).getText();
    }
}
