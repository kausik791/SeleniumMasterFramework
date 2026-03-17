package org.selenium.pom.tests;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignupApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.FakerUtils;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
@Epic("E-Commerce-API and UI")
@Feature("Checkout")
public class CheckoutTest extends BaseTest {
    @Test
    @Story("Guest checkout with Direct Bank Transfer")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Guest user adds product via API, completes checkout, and verifies order success notice")
    @Owner("kausik")
    @Tag("smoke")
    @Tag("regression")
    @Tag("checkout")
    @TmsLink("TC-201")
    public void GuestCheckoutUsingDirectBankTransfer() throws IOException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product= new Product(1215);

        Allure.parameter("User Type", "Guest");
        Allure.parameter("Payment Method", "Direct Bank Transfer");
        Allure.parameter("Product Id", product.getId());

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        CartApi cartApi = new CartApi();
        cartApi.addToCart(product.getId(), 1);
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.
                load().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");

    }
    @Test
    @Story("Registered user checkout with Direct Bank Transfer")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Create user via API, add product to cart, checkout, and verify order success notice")
    @Owner("kausik")
    @Tag("smoke")
    @Tag("regression")
    @Tag("checkout")
    @TmsLink("TC-202")
    public void LoginAndCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setUsername(username).
                setPassword("demopwd").
                setEmail(username + "@askomdch.com");

        Allure.parameter("User Type", "Registered");
        Allure.parameter("Username", username);
        Allure.parameter("Payment Method", "Direct Bank Transfer");

        SignupApi signUpApi = new SignupApi();
        signUpApi.register(user);
        CartApi cartApi = new CartApi(signUpApi.getCookies());

        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.load();
        checkoutPage.setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();

        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }
}
