package org.selenium.pom.tests;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("E-Commerce-UI")
@Feature("Product Search")
public class
SearchTest extends BaseTest {

    @Test
    @Story("Search for a product with partial match")
    @Description("Should be able to search for a product using a partial match")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("kausiK")
    @Tag("smoke")
    @Tag("regression")
    @Tag("search")
    @TmsLink("TC-104")
    public void searchWithPartialMatch(){
         String searchFor = "Blue";
            Allure.parameter("Search Keyword", searchFor);
            StorePage storePage = new StorePage(getDriver()).load().search(searchFor);
            Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");
    }

}
