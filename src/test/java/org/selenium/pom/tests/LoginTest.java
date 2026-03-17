package org.selenium.pom.tests;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignupApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.ConfigLoader;
import org.selenium.pom.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
@Epic("E-Commerce-API and UI")
@Feature("login")
public class LoginTest extends BaseTest {
    @Test
    @Story("Guest logs in during checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("qa-team")
    @Description("User registers, adds product to cart, logs in during checkout, and sees correct product")
    @Tag("smoke")
    @Tag("regression")
    @Tag("login")
    @TmsLink("TC-301")
    public void loginDuringCheckout() throws IOException, InterruptedException {
     //User userFromproperty = new User().setPassword(ConfigLoader.getInstance().getPassword()).setUsername(ConfigLoader.getInstance().getUsername());
     String username="demouser"+new FakerUtils().generateRandomNumber();
     User user= new User().setUsername(username).
             setPassword("demopwd").
                setEmail(username+"@askomdch.com");
        SignupApi signUpApi = new SignupApi();
        signUpApi.register(user);

        CartApi cartApi = new CartApi();
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        Allure.parameter("Flow", "Login during checkout");
        Allure.parameter("ProductId", product.getId());
        Allure.parameter("Username", username);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        Thread.sleep(5000);
        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.
                load().
                clickHereToLoginLink().
                login(user);
                //login(userFromproperty); // we are using the user from the property file as it is already registered so signupapi is not required so we can commet out those but it's not good practice as it may cause problems in parallell execution as the user from the property file may be used by other test cases as well so it's better to use the user from the property file in the test cases where we are not doing any registration and use the user from the faker utils in the test cases where we are doing registration so that we can avoid any problems in parallell execution
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));

    }

    @Test
    @Story("Already logged-in user opens checkout")
    @Severity(SeverityLevel.NORMAL)
    @Owner("qa-team")
    @Description("Registered and authenticated user opens checkout and sees product in order summary")
    @Tag("smoke")
    @Tag("regression")
    @Tag("login")
    @TmsLink("TC-302")
    public void AlreadyloginDuringCheckout() throws IOException, InterruptedException {
        String username="demouser"+new FakerUtils().generateRandomNumber();
        User user= new User().setUsername(username).
                setPassword("demopwd").
                setEmail(username+"@askomdch.com");
        SignupApi signUpApi = new SignupApi();
        signUpApi.register(user);

        CartApi cartApi = new CartApi(signUpApi.getCookies());
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        Allure.parameter("Flow", "Already logged in");
        Allure.parameter("ProductId", product.getId());
        Allure.parameter("Username", username);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        //Thread.sleep(5000);
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.
                load();
                //clickHereToLoginLink().
                   //login(user);
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));

    }
}
