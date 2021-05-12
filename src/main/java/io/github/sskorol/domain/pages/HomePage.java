package io.github.sskorol.domain.pages;

import io.github.sskorol.framework.core.AbstractPage;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

import static io.github.sskorol.framework.core.PageFactory.at;

public class HomePage extends AbstractPage {

    private final By labelWelcomeTitle = By.cssSelector("#mainContainer .uiHeaderTitle");
    private final By linkUserNavigation = By.id("userNavigationLabel");

    public String getWelcomeTitle() {
        return text(labelWelcomeTitle);
    }

    public LoginPage logOut() {
        click(linkUserNavigation);
        clickMenuItem(UserNavigation.LOG_OUT);
        return at(LoginPage.class);
    }

    @Step("Click menu item = \"{item.text}\".")
    private void clickMenuItem(final UserNavigation item) {
        click(By.xpath("//span[text()='" + item.getText() + "']"));
    }

    @Getter
    @RequiredArgsConstructor
    public enum UserNavigation {
        LOG_OUT("Log Out");

        private final String text;
    }
}
