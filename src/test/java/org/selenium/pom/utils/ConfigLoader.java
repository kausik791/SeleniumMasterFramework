package org.selenium.pom.utils;

import org.selenium.pom.constants.EnvType;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;
    private ConfigLoader() {
        //String env = System.getProperty("env", "STAGE");//overloaded System.getProperty method if env is not provided through cmd it will take STAGE as default
        String env = System.getProperty("env", String.valueOf(EnvType.STAGE));//more refined version, we can give values which are only available in EnvType enum, so we can avoid spelling mistakes, likes stage instead of STAGE
        switch (EnvType.valueOf(env)) {
            case STAGE:
                //properties = PropertyUtils.propertyLoader("src/test/resources/stg_config.properties"); // when using BufferedReader
                properties = PropertyUtils.propertyLoader("stg_config.properties"); //when using getResourceAsStream
                break;
            case PRODUCTION:
                //properties = PropertyUtils.propertyLoader("src/test/resources/prod_config.properties"); // when using BufferedReader
                properties = PropertyUtils.propertyLoader("prod_config.properties"); //when using getResourceAsStream
                break;
            default:
                throw new IllegalStateException("Invalid env type: " + env);
        }
    }
    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }
    public String getBaseUrl(){
        String prop = properties.getProperty("baseUrl");
        if(prop != null) return prop;
        else throw new RuntimeException("property baseUrl is not specified in the stg_config.properties file");
    }

    public String getUsername(){
        String prop = properties.getProperty("username");
        if(prop != null) return prop;
        else throw new RuntimeException("property username is not specified in the stg_config.properties file");
    }

    public String getPassword(){
        String prop = properties.getProperty("password");
        if(prop != null) return prop;
        else throw new RuntimeException("property password is not specified in the stg_config.properties file");
    }
    public String getEmail(){
        String prop = properties.getProperty("email");
        if(prop != null) return prop;
        else throw new RuntimeException("property email is not specified in the stg_config.properties file");
    }
}
