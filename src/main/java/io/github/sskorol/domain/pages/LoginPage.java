package io.github.sskorol.domain.pages;

import io.github.sskorol.domain.model.system.User;
import io.github.sskorol.framework.core.AbstractPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static io.github.sskorol.framework.core.PageFactory.at;

public class LoginPage extends AbstractPage {

    private final By inputEmail = By.id("email");
    private final By inputPassword = By.id("pass");
    private final By buttonLogin = By.id("loginbutton");
    private final By labelPasswordError = By.cssSelector(".uiContextualLayer div[style]");
    private final By linkForgotPassword = By.cssSelector(".uiContextualLayer div[style] > a");

    @Step("Type {email} into \"Email or Phone\" field.")
    public LoginPage typeEmail(final String email) {
        type(inputEmail, email);
        return this;
    }

    @Step("Type {password} into \"Password\" field.")
    public LoginPage typePassword(final String password) {
        type(inputPassword, password);
        return this;
    }

    @Step("Click \"Log In\" button.")
    public HomePage login() {
        click(buttonLogin);
        return at(HomePage.class);
    }

    @Step("Login into Facebook account.")
    public HomePage loginWith(final User user) {
        return typeEmail(user.getEmail())
                .typePassword(user.getPassword())
                .login();
    }

    @Step("Click \"Forgot Password?\" link.")
    public ResetPasswordPage forgotPassword() {
        click(linkForgotPassword);
        return at(ResetPasswordPage.class);
    }

    public String getPasswordError() {
        return text(labelPasswordError);
    }
}
