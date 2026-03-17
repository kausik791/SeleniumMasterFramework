package org.selenium.pom.utils;

import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

public class CookieUtils {
    @Step("Convert REST-assured cookies to Selenium cookies")
    public List<Cookie> convertRestAssuredCookiesToSeleniumCookies(Cookies cookies){ // as no Cookies is available for selenium, we have use list of Cookie and convert the rest assured cookies to selenium cookies
       List<io.restassured.http.Cookie>  restAssuredCookies = new ArrayList();
        restAssuredCookies = cookies.asList();
        List<Cookie> seleniumCookies = new ArrayList<>();
        for(io.restassured.http.Cookie restCookie : restAssuredCookies){
            seleniumCookies.add(new Cookie(restCookie.getName(), restCookie.getValue(), restCookie.getDomain(),
                    restCookie.getPath(), restCookie.getExpiryDate(), restCookie.isSecured(), restCookie.isHttpOnly(),
                    restCookie.getSameSite()));
        }

        return seleniumCookies;
    }
}
