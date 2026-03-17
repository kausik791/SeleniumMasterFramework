package org.selenium.pom.utils;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class FakerUtils {

    @Step("Generate random number")
    public Long generateRandomNumber(){
        Faker faker = new Faker();
        return faker.number().randomNumber(10, true);
    }
}
