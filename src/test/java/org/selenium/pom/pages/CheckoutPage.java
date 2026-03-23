package org.selenium.pom.pages;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.User;

public class CheckoutPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutPage.class);

    public CheckoutPage(WebDriver driver) {
        super(driver);

    }

    private final By firstnameFld = By.id("billing_first_name");
    private final By lastNameFld = By.id("billing_last_name");
    private final By addressLineOneFld = By.id("billing_address_1");
    private final By billingCityFld = By.id("billing_city");
    private final By billingPostCodeFld = By.id("billing_postcode");
    private final By billingEmailFld = By.id("billing_email");
    private final By placeOrderBtn = By.id("place_order");
    private final By successNotice = By.cssSelector(".woocommerce-notice");

    private final By clickHereToLoginLink = By.className("showlogin");
    private final By usernameFld = By.id("username");
    private final By passwordFld = By.id("password");
    private final By loginBtn = By.name("login");
    private final By overlay = By.cssSelector(".blockUI.blockOverlay");
    private final By countryDropDown = By.id("billing_country");
    private final By stateDropDown = By.id("billing_state");

    private final By alternateCountryDropDown = By.id("select2-billing_country-container");
    private final By alternateStateDropDown = By.id("select2-billing_state-container");
    private final By directBankTransferRadioBtn = By.id("payment_method_bacs");
    private final By cashOnDeliveryTransferRadioBtn = By.id("payment_method_cod");
    private final By productName = By.cssSelector("td[class='product-name']");

    @Step("Open checkout page")
    public CheckoutPage load() {
        load("/checkout/");
        return this;
    }

    @Step("Enter first name: {firstName}")
    public CheckoutPage enterFirstName(String firstName) {
        //driver.findElement(firstnameFld).sendKeys(firstName);
        WebElement firstNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstnameFld));
        firstNameElement.clear();
        firstNameElement.sendKeys(firstName);
        return this;
    }

    @Step("Enter last name: {lastName}")
    public CheckoutPage enterLastName(String lastName) {
        //driver.findElement(lastNameFld).sendKeys(lastName);
        WebElement lastNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameFld));
        lastNameElement.clear();
        lastNameElement.sendKeys(lastName);
        return this;
    }

    @Step("Select country: {countryName}")
    public CheckoutPage selectCountry(String countryName) {
        //Select select = new Select(driver.findElement(countryDropDown));
/*        Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(countryDropDown)));
        select.selectByVisibleText(countryName);*/
        wait.until(ExpectedConditions.elementToBeClickable(alternateCountryDropDown)).click();
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[text()='" + countryName + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
        e.click();
        return this;
    }

    @Step("Enter address line 1: {addressLineOne}")
    public CheckoutPage enterAddressLineOne(String addressLineOne) {
        //driver.findElement(addressLineOneFld).sendKeys(addressLineOne);
        WebElement addressLineOneElement = wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineOneFld));
        addressLineOneElement.clear();
        addressLineOneElement.sendKeys(addressLineOne);
        return this;
    }

    @Step("Enter city: {city}")
    public CheckoutPage enterCity(String city) {
        //driver.findElement(billingCityFld).sendKeys(city);
        WebElement cityElement = wait.until(ExpectedConditions.visibilityOfElementLocated(billingCityFld));
        cityElement.clear();
        cityElement.sendKeys(city);
        return this;
    }

    @Step("Select state: {stateName}")
    public CheckoutPage selectState(String stateName) {
        //Select select = new Select(driver.findElement(stateDropDown));
       /* Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(stateDropDown)));
        select.selectByVisibleText(stateName);*/
        wait.until(ExpectedConditions.elementToBeClickable(alternateStateDropDown)).click();
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[text()='" + stateName + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
        e.click();
        return this;
    }

    @Step("Enter postal code: {postCode}")
    public CheckoutPage enterPostCode(String postCode) {
        //driver.findElement(billingPostCodeFld).sendKeys(postCode);
        WebElement postCodeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(billingPostCodeFld));
        postCodeElement.clear();
        postCodeElement.sendKeys(postCode);
        return this;
    }

    @Step("Enter email: {email}")
    public CheckoutPage enterEmail(String email) {
        //driver.findElement(billingEmailFld).sendKeys(email);
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(billingEmailFld));
        emailElement.clear();
        emailElement.sendKeys(email);
        return this;
    }

    @Step("Set billing address")
    public CheckoutPage setBillingAddress(BillingAddress billingAddress) {
        return enterFirstName(billingAddress.getFirstName())
                .enterLastName(billingAddress.getLastName())
                .selectCountry(billingAddress.getCountry())
                .enterAddressLineOne(billingAddress.getAddressLineOne())
                .enterCity(billingAddress.getCity())
                .selectState(billingAddress.getState())
                .enterPostCode(billingAddress.getPostalCode())
                .enterEmail(billingAddress.getEmail());

    }

    @Step("Place order")
    public CheckoutPage placeOrder() {
        waitForOverlaysToDisappear(overlay);
        driver.findElement(placeOrderBtn).click();
        return this;
    }


    @Step("Read checkout notice")
    public String getNotice() {
        //return driver.findElement(successNotice).getText();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successNotice)).getText();
    }

    @Step("Click here to login link")
    public CheckoutPage clickHereToLoginLink() {
        //driver.findElement(clickHereToLoginLink).click();
        wait.until(ExpectedConditions.elementToBeClickable(clickHereToLoginLink)).click();
        return this;
    }

    @Step("Enter username")
    public CheckoutPage enterUserName(String username) {
        //driver.findElement(usernameFld).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFld)).sendKeys(username);
        return this;
    }

    @Step("Enter password")
    public CheckoutPage enterPassword(String password) {
        //driver.findElement(passwordFld).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordFld)).sendKeys(password);
        return this;
    }
        @Step("Click login button")
        public CheckoutPage clickLoginBtn () {
            //driver.findElement(loginBtn).click();
            wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
            return this;
        }
        private CheckoutPage waitForLoginBtnToDisappear () {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loginBtn));
            return this;
        }

        //method chaining for functional convenience
        @Step("Login with username and password")
        public CheckoutPage login (String username, String password){
            return
                    enterUserName(username).
                            enterPassword(password).
                            clickLoginBtn().waitForLoginBtnToDisappear();


        }
        @Step("Select Direct Bank Transfer payment method")
        public CheckoutPage selectDirectBankTransfer () {
            WebElement directBankTransferButton = wait.until(ExpectedConditions.elementToBeClickable(directBankTransferRadioBtn));
            if (!directBankTransferButton.isSelected()) {
                directBankTransferButton.click();
            }
            return this;
        }
        @Step("Select Cash on Delivery payment method")
    public CheckoutPage selectCashOnDeliveryTransfer(){
        wait.until(ExpectedConditions.elementToBeClickable(cashOnDeliveryTransferRadioBtn)).click();
        return this;
    }
        @Step("Login with user object")
        public CheckoutPage login (User user){
            return
                    enterUserName(user.getUsername()).
                            enterPassword(user.getPassword()).
                            clickLoginBtn();
            //using composition


        }
        @Step("Read product name from checkout")
        public String getProductName () {
            int attempts = 5;

            while (attempts > 0) {
                try {
                    return wait.until(ExpectedConditions
                                    .visibilityOfElementLocated(productName))
                            .getText();

                } catch (StaleElementReferenceException e) {
                    logger.debug("Retrying due to stale element: {}", e.getMessage());
                }
                attempts--;
            }

            throw new RuntimeException("Product name element not found after retries");
        }

    }

