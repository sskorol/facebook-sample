package io.github.sskorol.domain.pages;

import com.google.api.services.gmail.model.Message;
import io.github.sskorol.framework.core.AbstractPage;
import io.qameta.allure.Step;
import io.vavr.control.Try;
import org.openqa.selenium.By;

import static io.github.sskorol.framework.utils.EmailUtils.getMessages;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ResetPasswordPage extends AbstractPage {

    private By radioButtonSendCodeViaEmail = By.id("send_email");
    private By buttonContinue = By.cssSelector("button[type='submit']");
    private By inputRecoveryCode = By.id("recovery_code_entry");
    private By inputNewPassword = By.id("password_new");

    @Step("Select \"Email\" radio button.")
    public ResetPasswordPage resetViaEmail() {
        click(radioButtonSendCodeViaEmail);
        return clickContinue();
    }

    public ResetPasswordPage sendVerificationCode() {
        final String code = Try.of(() -> getMessages("is:unread"))
                               .map(messages -> messages.findFirst().map(Message::getSnippet).orElse(""))
                               .map(snippet -> substringBetween(snippet, "code: ", " "))
                               .getOrElseGet(ex -> "");
        return sendVerificationCode(code);
    }

    @Step("Type verification code = {code}.")
    public ResetPasswordPage sendVerificationCode(final String code) {
        type(inputRecoveryCode, code);
        return clickContinue();
    }

    @Step("Type new password = {password}.")
    public ResetPasswordPage typeNewPassword(final String password) {
        type(inputNewPassword, password);
        return clickContinue();
    }

    @Step("Click \"Continue\" button.")
    public ResetPasswordPage clickContinue() {
        click(buttonContinue);
        return this;
    }
}
