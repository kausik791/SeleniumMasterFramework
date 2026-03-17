package org.selenium.pom.tests;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.dataproviders.MyDataProvider;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
@Epic("E-Commerce-UI")
@Feature("Cart")
public class AddToCartTest extends BaseTest {
    @Test
    @Story("Add product to cart from store page")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Search product on store page and verify selected product is added to cart")
    @Owner("kausiK")
    @Tag("smoke")
    @Tag("regression")
    @Tag("cart")
    @TmsLink("TC-101")
    public void addToCartFromStorePage() throws IOException {
        String searchFor = "Blue";
        Product product = new Product(1215);

        Allure.parameter("Search Keyword", searchFor);
        Allure.parameter("Product ID", 1215);
        Allure.parameter("Product Name", product.getName());

        CartPage cartPage = new StorePage(getDriver()).
                load().
                search(searchFor).
                getProductThumbnail().
                clickAddToCartBtn(product.getName()).
                clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());

    }
    //@Test(dataProvider = "getFeaturedProducts")
    @Test(dataProvider = "getFeaturedProducts",dataProviderClass = MyDataProvider.class)// we have to provide data provider class because we have moved the data provider method to MyDataProvider class
    @Story("Add featured product to cart using data provider")
    @Severity(SeverityLevel.NORMAL)
    @Description("Add featured product and verify product name in cart")
    @Owner("kausiK")
    @Tag("smoke")
    @Tag("regression")
    @Tag("cart")
    @TmsLink("TC-102")
    public void addToCartFeaturedProducts(Product product) throws IOException {//we have to provide product as parameter because we are getting product object from data provider

        Allure.parameter("Product ID", product.getId());
        Allure.parameter("Product Name", product.getName());

        CartPage cartPage = new HomePage(getDriver()).
                load().
                getProductThumbnail().
                clickAddToCartBtn(product.getName()).
                clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());

    }

    //moving the code to MyDataProvider class because we can use this data provider in multiple test classes and we have to maintain the code in one place
   /* @DataProvider(name = "getFeaturedProducts",parallel = true)
    public Object[] getFeaturedProducts() throws IOException {
        return JacksonUtils.deserializeJson("products.json", Product[].class);
    }*/
}
