package org.selenium.pom.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.utils.ConfigLoader;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class MyFirstTestCase extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(MyFirstTestCase.class);
    //@Test
    public void guestCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
  /*      BillingAddress billingAddress = new BillingAddress();
        // using setter iniatialization(Builder Pattern)
        billingAddress.
                setFirstName("demo").
                setLastName("user").
                setCountry("India").
                setAddressLineOne("123 Main St").
                setCity("Anytown").
                setState("Maharashtra").
                setPostalCode("400069").
                setEmail("demo@example.com");
        //driver.get("https://askomdch.com/");*/

        //BillingAddress billingAddress = new BillingAddress();
        String searchFor = "Blue";
        BillingAddress billingAddressDesrialize = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);
        //HomePage homePage = new HomePage(driver);
        HomePage homePage = new HomePage(getDriver());// after adding thread local
        homePage.load();
        StorePage storePage = homePage.getMyHeader().navigateToStoreUsingMenu();
        storePage.isLoaded();
        //this is strructural way of writing
        /*storePage.
                enterTextInSearchFld("Blue").
                clickSearchBtn();
         */
        //this is functional way of writing
        //storePage.search("Blue");
        storePage.search(searchFor);
        //Assert.assertEquals(storePage.getTitle(), "Search results: “Blue”");
        Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");
        //storePage.clickAddToCartBtn("Blue Shoes");
        storePage.getProductThumbnail().clickAddToCartBtn(product.getName());
        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
        cartPage.isLoaded();
        //Assert.assertEquals(cartPage.getProductName(), "Blue Shoes");
        Assert.assertEquals(cartPage.getProductName(), product.getName());
        CheckoutPage checkout = cartPage.checkout();
/*        checkout.
                enterFirstName("demo").
                enterLastName("user").
                selectCountry("India").
                enterAddressLineOne("San francisco").
                enterCity("San francisco").
                selectState("Maharashtra").
                enterPostCode("400069").
                enterEmail("demo@example.com");*/
        //using BillingAddress pojo instead of hardcoding
        //checkout.setBillingAddress(billingAddress);
        checkout.setBillingAddress(billingAddressDesrialize);
        checkout.
                selectDirectBankTransfer().
                placeOrder();
        Assert.assertEquals(checkout.getNotice(), "Thank you. Your order has been received.");
    }

   //@Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
        //driver.get("https://askomdch.com/");
        //using constructor initialization
        BillingAddress billingAddress = new BillingAddress("demo", "user", "San francisco", "San francisco", "400069", "demo@example.com", "India", "Maharashtra","123 Main St","3363636");
        String searchFor = "Blue";
        BillingAddress billingAddressDesrialize = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);
        //User user = new User("test1234", "babu@mymail.com");
        //User user = new User(ConfigLoader.getInstance().getPassword(), ConfigLoader.getInstance().getUsername());
        User user = new User().setPassword(ConfigLoader.getInstance().getPassword()).setUsername(ConfigLoader.getInstance().getUsername()); // using setter method for User object initialization in builder pattern style

        StorePage storePage = new HomePage(getDriver()).
                load().getMyHeader().
                navigateToStoreUsingMenu();
        storePage.isLoaded();
        //search("Blue");
        storePage.search(searchFor);
        //Assert.assertEquals(storePage.getTitle(), "Search results: “Blue”");
        Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");
        //storePage.clickAddToCartBtn("Blue Shoes");
        storePage.getProductThumbnail().clickAddToCartBtn(product.getName());
        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
        cartPage.isLoaded();
        //Assert.assertEquals(cartPage.getProductName(), "Blue Shoes");
        Assert.assertEquals(cartPage.getProductName(), product.getName());
        CheckoutPage checkout = cartPage.checkout();
        checkout.clickHereToLoginLink();
        logger.info("Login button clicked");
        /*checkout.
                login("babu@mymail.com", "test1234");*/
        checkout.login(user);
        Thread.sleep(5000);

       /* checkout.
                enterFirstName("demo").
                enterLastName("user").
                selectCountry("India").
                enterAddressLineOne("San francisco").
                enterCity("San francisco").
                selectState("Maharashtra").
                enterPostCode("400069").
                enterEmail("demo@example.com");*/
        //using BillingAddress pojo instead of hardcoding
        //checkout.setBillingAddress(billingAddress);
        checkout.setBillingAddress(billingAddressDesrialize);

        checkout.placeOrder();

        Assert.assertEquals(checkout.getNotice(), "Thank you. Your order has been received.");


    }
}
