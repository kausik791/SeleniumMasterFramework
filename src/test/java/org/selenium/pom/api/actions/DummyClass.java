package org.selenium.pom.api.actions;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.selenium.pom.objects.User;
import org.selenium.pom.utils.FakerUtils;

public class DummyClass {
    private static final Logger logger = LoggerFactory.getLogger(DummyClass.class);
    public static void main(String[] args) {
    //new SignupApi().getAccount();
        //System.out.println(new SignupApi().fetchRegisterNonceValueUsingGroovy());
       // System.out.println(new SignupApi().fetchRegisterNonceValueUsingJsoup());

        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setUsername(username).
                setPassword("demopwd").
                setEmail(username + "@askomdch.com");
        SignupApi signUpApi = new SignupApi();
        Response response = signUpApi.register(user);
        //System.out.println("session ID is "+response.sessionId()); //it is null as the session ID is not being set in the response
        //System.out.println("REGISTER COOKIES: " + response.getDetailedCookies());
        logger.info("REGISTER COOKIES: {}", signUpApi.getCookies()); // we are getting the cookies from the SignupApi class where we have defined a variable to store the cookies and then we are getting the cookies from the response and storing it in the variable and then we are returning the cookies from the getCookies method and then we are printing the cookies in the main method
        //CartApi cartApi = new CartApi();// without login
        //CartApi cartApi = new CartApi(signUpApi.getCookies()); // Register cookies  is having login cookies as well
           // cartApi.addToCart(1215, 1);
           // System.out.println("CART COOKIES: " + cartApi.getCookies());
    }
}
