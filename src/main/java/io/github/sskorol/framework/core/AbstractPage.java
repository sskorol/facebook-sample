package io.github.sskorol.framework.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

import static io.github.sskorol.framework.core.BaseConfig.BASE_CONFIG;
import static io.github.sskorol.framework.core.listeners.BaseListener.getDriver;
import static java.util.Optional.ofNullable;

public abstract class AbstractPage implements Page {

    private final WebDriverWait wait;

    public AbstractPage() {
        this.wait = ofNullable(getDriver())
                .map(driver -> new WebDriverWait(driver, BASE_CONFIG.waitTimeout()))
                .orElseThrow(() -> new IllegalStateException("Unable to access WebDriver instance!"));
    }

    protected void click(final By locator) {
        waitFor(locator, ExpectedConditions::elementToBeClickable).click();
    }

    protected void type(final By locator, final CharSequence text) {
        waitFor(locator, ExpectedConditions::visibilityOfElementLocated).sendKeys(text);
    }

    protected String text(final By locator) {
        return waitFor(locator, ExpectedConditions::visibilityOfElementLocated).getText();
    }

    private WebElement waitFor(final By locator, final Function<By, ExpectedCondition<WebElement>> condition) {
        return wait.until(condition.apply(locator));
    }
}
