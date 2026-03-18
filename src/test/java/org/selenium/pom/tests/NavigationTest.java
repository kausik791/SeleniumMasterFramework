package org.selenium.pom.tests;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;
@Epic("E-Commerce-UI")
@Feature("Navigation")
public class NavigationTest  extends BaseTest {

    @Test
    @Story("Navigate from Home to Store")
    @Description ("Should be able to navigate from home to store" )//allure annotation for description
    @Severity(SeverityLevel.CRITICAL)
    @Owner("kausiK")
    @Tag("smoke")
    @Tag("regression")
    @Tag("navigation")
    @TmsLink("TC-103")
    //@Test(description ="Should be able to navigate from home to store using main menu" )//testng annotation for description but it will show in both.
    public void NavigateFromHomeToStoreUsingMainMenu(){
        StorePage storePage = new HomePage(getDriver()).
                load().
                getMyHeader().
                navigateToStoreUsingMenu();
        Assert.assertEquals(storePage.getTitle(), "Store35");
    }
}
