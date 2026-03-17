package org.selenium.pom.api.actions;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.qameta.allure.Step;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.selenium.pom.objects.User;
import org.selenium.pom.utils.ConfigLoader;

import java.util.HashMap;

public class SignupApi {
    private Cookies cookies;


    public Cookies getCookies() {
        return cookies;
    }

    public String fetchRegisterNonceValueUsingGroovy() {
        Response response = getAccount();
        return response.htmlPath().getString("**.findAll { it.@name == 'woocommerce-register-nonce' }.@value");
    }

    public String fetchRegisterNonceValueUsingJsoup() {
        Response response = getAccount();
        //Document doc = Jsoup.parse(response.body().prettyPrint());
        Document doc = Jsoup.parse(response.getBody().asString());
        Element element = doc.selectFirst("#woocommerce-register-nonce");
        assert element != null;
        return element.attr("value");
    }

    @Step("API: Get account page")
    public Response getAccount() {
        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                //baseUri("https://askomdch.com").
                //cookies(getCookies()).
                        //log().all().
                when().
                get("/account").
                then().
                //log().all().
                extract().response();
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to fetch the account, HTTP Status Code: " + response.getStatusCode());
        }

        return response;
    }

    @Step("API: Register user {user.username}")
    public Response register(User user) {
         Cookies cookies = new Cookies();
        Header header = new Header("content-type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("username", user.getUsername());
        formParams.put("email", user.getEmail());
        formParams.put("password", user.getPassword());
        formParams.put("woocommerce-register-nonce", fetchRegisterNonceValueUsingJsoup());
        formParams.put("register", "Register");
        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                headers(headers).
                formParams(formParams).
                cookies(cookies).
                //log().all().
                when().
                post("/account").
        then().
                //log().all().
                extract().
                response();
        if(response.getStatusCode() != 302){
            throw new RuntimeException("Failed to register the account, HTTP Status Code: " + response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        return response;
    }

}
