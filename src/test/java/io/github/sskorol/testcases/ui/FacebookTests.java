package io.github.sskorol.testcases.ui;

import io.github.sskorol.core.DataSupplier;
import io.github.sskorol.domain.model.system.ErrorMessage;
import io.github.sskorol.domain.model.system.User;
import io.github.sskorol.domain.pages.HomePage;
import io.github.sskorol.domain.pages.LoginPage;
import org.testng.annotations.Test;

import static io.github.sskorol.framework.core.PageFactory.at;
import static io.github.sskorol.framework.core.PageFactory.open;
import static io.github.sskorol.framework.utils.JsonUtils.toEntity;
import static org.assertj.core.api.Assertions.assertThat;

public class FacebookTests {

    @DataSupplier
    public User getValidUser() {
        return toEntity(User.class).orElse(User.dummy());
    }

    @Test(dataProvider = "getValidUser")
    public void userShouldSuccessfullyLogin(final User user) {
        open(LoginPage.class)
                .loginWith(user);

        assertThat(at(HomePage.class).getWelcomeTitle())
                .as("Username")
                .contains(user.getFirstName());
    }

    @Test(dataProvider = "getValidUser")
    public void userShouldNotLoginWithWrongPassword(final User user) {
        open(LoginPage.class)
                .typeEmail(user.getEmail())
                .typePassword("")
                .login();

        assertThat(at(LoginPage.class).getPasswordError())
                .as("Error message")
                .isEqualTo(ErrorMessage.INCORRECT_PASSWORD.getText());
    }

    @Test(dataProvider = "getValidUser", enabled = false)
    public void userShouldBeAbleToResetPassword(final User user) {
        open(LoginPage.class)
                .typeEmail(user.getEmail())
                .typePassword("")
                .login();

        at(LoginPage.class)
                .forgotPassword()
                .resetViaEmail()
                .sendVerificationCode()
                .typeNewPassword(user.getPassword())
                .clickContinue();

        at(HomePage.class)
                .logOut()
                .loginWith(user);

        assertThat(at(HomePage.class).getWelcomeTitle())
                .as("Username")
                .contains(user.getFirstName());
    }
}
