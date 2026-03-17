package org.selenium.pom.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.components.MyHeader;
import org.selenium.pom.pages.components.ProductThumbnail;

public class HomePage extends BasePage {
    private MyHeader myHeader; // composition - has a relationship
    private ProductThumbnail productThumbnail;// composition - has a relationship

    public MyHeader getMyHeader() {
        return myHeader;
    }

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }

    public HomePage(WebDriver driver) {
        super(driver); // initialize driver of BasePage
        myHeader = new MyHeader(driver); // initialize MyHeader
        productThumbnail = new ProductThumbnail(driver); // initialize ProductThumbnail
    }

    /*public HomePage(WebDriver d) {
        super(d); // initialize driver of BasePage
    }*/
    //private final By storeMenuLink=By.xpath("//li[@id='menu-item-1227']"); //moved to MyHeader as it is common
    //private final By viewCartLink = By.cssSelector("a[title='View cart']"); // moved to product thumbnail as it is common for both store page and home page
   //moved to MyHeader as it is common
    /* public StorePage navigateToStoreUsingMenu() {
        //driver.findElement(storeMenuLink).click(); // driver is inherited from BasePage
        wait.until(ExpectedConditions.elementToBeClickable(storeMenuLink)).click();
        return new StorePage(driver);
    }*/
    @Step("Open home page")
    public HomePage load(){
        load("/");
        wait.until(ExpectedConditions.titleContains("automation expert!"));//this is for 91. Explicit Wait - Implement more Strategies
        return this;
    }






    // moved to product thumbnail as it is common for both store page and home page
    /*private By getAddToCartBtnElement(String productName) {
        //dynamically creating selector
        return By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
    }

    public HomePage clickAddToCartBtn(String productName) {
        By addToCartBtn = getAddToCartBtnElement(productName);
        //driver.findElement(addToCartBtn).click();
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
        return this;
    }

    public CartPage clickViewCart() {
        //driver.findElement(viewCartLink).click();
        wait.until(ExpectedConditions.elementToBeClickable(viewCartLink)).click();
        return new CartPage(driver);
    }*/
}
