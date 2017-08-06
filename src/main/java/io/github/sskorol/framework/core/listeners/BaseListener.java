package io.github.sskorol.framework.core.listeners;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.vavr.control.Try;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.util.Optional.ofNullable;

public abstract class BaseListener {

    private static final ThreadLocal<WebDriver> DRIVER_CONTAINER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return DRIVER_CONTAINER.get();
    }

    // Not a real world example, hardcoded local browser, just for the interview
    protected void setupDriver() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        DRIVER_CONTAINER.set(new ChromeDriver());
    }

    protected void cleanUp() {
        ofNullable(getDriver()).ifPresent(d -> Try.run(d::quit));
        DRIVER_CONTAINER.remove();
    }
}
