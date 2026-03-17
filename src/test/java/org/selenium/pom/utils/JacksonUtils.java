package org.selenium.pom.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.selenium.pom.objects.BillingAddress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class JacksonUtils {
    /*public static BillingAddress deserializeJson(String fileName, BillingAddress billingAddress) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("C:\\Users\\kausi\\IdeaProjects\\SeleniumFrame\\src\\test\\resources\\myBillingAddress.json"),billingAddress.getClass());
    } */

   /* public static BillingAddress deserializeJson(String fileName, BillingAddress billingAddress) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = JacksonUtils.class.getClassLoader().getResourceAsStream(fileName);
        // output is of BillingAddress, that's why method return type is BillingAddress
        //BillingAddress billingAddress1 = objectMapper.readValue(is, billingAddress.getClass());
        //return billingAddress1;
        return objectMapper.readValue(is, billingAddress.getClass());

    }*/
    //Generic method to deserialize JSON into any object type
    @Step("Deserialize JSON from resource: {fileName}")
    public static <T> T deserializeJson(String fileName, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = JacksonUtils.
                class.
                getClassLoader().
                getResourceAsStream(fileName);
        if (is == null) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        return objectMapper.readValue(is, clazz);
    }
}
