package org.selenium.pom.api.actions;

import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.selenium.pom.utils.ConfigLoader;

import java.util.HashMap;

import static io.restassured.RestAssured.given;


public class CartApi {
    private Cookies cookies;

    public CartApi() {//use this constructor when do addtoCart without login as the cookies will be null and we will create a new instance of cookies in the addToCart method
    }

    public CartApi(Cookies cookies) {//use this constructor when do addtoCart after login as we will pass the cookies from the SignupApi class to the CartApi class and then we will use the cookies in the addToCart method to add the product to the cart
        this.cookies = cookies;
    }

    public Cookies getCookies() {
        return cookies;
    }

    @Step("API: Add product {productID} to cart with quantity {quantity}")
    public Response addToCart(int productID, int quantity) {
        Header header = new Header("content-type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);
        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("product_sku","");
        formParams.put("product_id",productID );
        formParams.put("quantity",quantity);
        if(cookies == null){
            cookies = new Cookies();
        }
        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                headers(headers).
                formParams(formParams).
                cookies(cookies).
                log().all().
                when().
                post("/?wc-ajax=add_to_cart").
                then().
                log().all().
                extract().
                response();
        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to add product" + productID + " to the cart" +
                    ", HTTP Status Code: " + response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        return response;

    }
}
