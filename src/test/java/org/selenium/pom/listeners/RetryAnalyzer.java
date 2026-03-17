package org.selenium.pom.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(RetryAnalyzer.class);
    private int retryCount = 0;
    private final int maxRetryCount = 1;

    @Override
    public boolean retry(ITestResult result) {
        Throwable throwable = result.getThrowable();
        if (!isRetryable(throwable)) {
            logger.info("No retry for {}. Reason: {}", result.getMethod().getQualifiedName(),
                    throwable == null ? "no exception" : throwable.getClass().getSimpleName());
            return false;
        }
        if (retryCount < maxRetryCount) {
            logger.info("Retrying {} (attempt {})", result.getMethod().getQualifiedName(), retryCount + 1);
            retryCount++;
            return true;
        }
        logger.info("No more retries for {}", result.getMethod().getQualifiedName());
        return false;
    }

    private boolean isRetryable(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        if (throwable instanceof AssertionError) {
            return false;
        }
        return (throwable instanceof TimeoutException)
                || (throwable instanceof StaleElementReferenceException)
                || (throwable instanceof ElementClickInterceptedException)
                || (throwable instanceof ElementNotInteractableException);
    }
}
