package org.selenium.pom.pages.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.CartPage;

public class ProductThumbnail extends BasePage {
    public ProductThumbnail(WebDriver driver) {
        super(driver);
    }
    private final By viewCartLink = By.cssSelector("a[title='View cart']");
    private By getAddToCartBtnElement(String productName) {
        //dynamically creating selector
        return By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
    }

    @Step("Add product to cart: {productName}")
    public ProductThumbnail clickAddToCartBtn(String productName) {
        By addToCartBtn = getAddToCartBtnElement(productName);
        //driver.findElement(addToCartBtn).click();
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
        return this;
    }

    @Step("View cart")
    public CartPage clickViewCart() {
        //driver.findElement(viewCartLink).click();
        wait.until(ExpectedConditions.elementToBeClickable(viewCartLink)).click();
        return new CartPage(driver);
    }

}
