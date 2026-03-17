package org.selenium.pom.utils;

import io.qameta.allure.Step;
import java.io.*;
import java.util.Properties;

public class PropertyUtils {

    @Step("Load properties file: {filePath}")
    public static Properties propertyLoader(String filePath) {
        Properties properties = new Properties();
        //By using BufferedReader
        /*BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("failed to load properties file "+ filePath);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("properties file not found at " + filePath);
        }*/


        // By using getResourceAsStream
  /*      try (InputStream is = PropertyUtils.class
                .getClassLoader()
                .getResourceAsStream(filePath)) {*/

        // By using getResourceAsStream different ways
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(filePath)) {
            if (is == null) {
                throw new RuntimeException("Properties file not found in classpath: " + filePath);
            }
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + filePath, e);
        }
        return properties;
    }
}
