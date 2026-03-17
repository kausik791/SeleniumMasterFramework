package org.selenium.pom.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class CartPage extends BasePage {

    /*private final By productName = By.cssSelector("td[class='product-name'] a");
    private final By checkoutBtn = By.cssSelector(".checkout-button");
    private final By cartHeading=By.cssSelector(".has-text-align-center"); // this for 91. Explicit Wait - Implement more Strategies*/
    @FindBy(how= How.CSS, using = "td[class='product-name'] a") private WebElement productName;
    @FindBy(css = ".checkout-button") @CacheLookup private WebElement checkoutBtn;
    @FindBy(css = ".has-text-align-center") private WebElement cartHeading;
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this); // this can be moved to BasePage if we are doing pagefactory for entire frame work

    }
    @Step("Read product name from cart")
    public String getProductName(){
       // return driver.findElement(productName).getText();
       /* WebElement element = waitForElementToBeVisible(productName);
        return element.getText();*/
        //return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText(); // this visibilityOfElementLocated  method is only available for By

        //using PageFactory
        //return driver.findElement((By) productName).getText(); // not good approach
        //return productName.getText();
        return wait.until(ExpectedConditions.visibilityOf(productName)).getText();

    }
    //this is for 91. Explicit Wait - Implement more Strategies
    @Step("Verify cart page is loaded")
    public Boolean isLoaded(){
       //return wait.until(ExpectedConditions.textToBe(cartHeading,"Cart")); // this textToBe method is only available for By
        //using PageFactory
        //return cartHeading.getText().equals("Cart");
         return wait.until(ExpectedConditions.textToBePresentInElement(cartHeading,"Cart"));
    }
    @Step("Proceed from cart to checkout")
    public CheckoutPage checkout(){
        //driver.findElement(checkoutBtn).click();
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click(); // this elementToBeClickable method is overloaded can take both By and WebElement
        return new CheckoutPage(driver);
    }
}
