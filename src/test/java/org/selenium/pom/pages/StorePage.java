package org.selenium.pom.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.components.ProductThumbnail;

public class StorePage extends BasePage {
    private ProductThumbnail productThumbnail; // composition - has a relationship
    private final By searchFld = By.id("woocommerce-product-search-field-0");
    private final By searchBtn = By.xpath("//button[@value='Search']");
    private final By title = By.className("woocommerce-products-header__title");

    public StorePage(WebDriver driver) {
        super(driver);
        productThumbnail = new ProductThumbnail(driver); // initialize ProductThumbnail
    }

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }
    //below is static selector
    //private final By addToCartBtn = By.cssSelector("a[aria-label='Add “Blue Shoes” to your cart']");
    //private final By viewCartLink = By.cssSelector("a[title='View cart']"); // moved to product thumbnail as it is common for both store page and home page

    @Step("Open store page")
    public StorePage load(){
        load("/store");
        return this;
    }

    @Step("Enter search text: {txt}")
    public StorePage enterTextInSearchFld(String txt) {
        //driver.findElement(searchFld).sendKeys(txt);
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchFld)).sendKeys(txt);
        return this;
    }

    @Step("Click search button")
    public StorePage clickSearchBtn() {
        //driver.findElement(searchBtn).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        return this;
    }

    // for functional convenience
    @Step("Search for product: {txt}")
    public StorePage search(String txt) {

        //driver.findElement(searchFld).sendKeys(txt);
        //driver.findElement(searchBtn).click();
        enterTextInSearchFld(txt).clickSearchBtn();
        return this;

    }

    @Step("Read store page title")
    public String getTitle() {
        return driver.findElement(title).getText();
    }


    // moved to product thumbnail as it is common for both store page and home page
 /*   private By getAddToCartBtnElement(String productName) {
        //dynamically creating selector
        return By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
    }

    public StorePage clickAddToCartBtn(String productName) {
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

    //this is for 91. Explicit Wait - Implement more Strategies
    @Step("Verify store page is loaded")
    public boolean isLoaded() {
        return wait.until(ExpectedConditions.urlContains("/store/"));
    }

}

