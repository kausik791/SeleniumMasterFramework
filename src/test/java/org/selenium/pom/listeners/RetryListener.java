package org.selenium.pom.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {
    private static final Logger logger = LoggerFactory.getLogger(RetryListener.class);
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (testMethod != null) {
            logger.debug("Applying RetryAnalyzer to {}.{}", testMethod.getDeclaringClass().getName(), testMethod.getName());
        }
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
