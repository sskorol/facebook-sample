package io.github.sskorol.domain.model.system;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    INCORRECT_PASSWORD("The password you’ve entered is incorrect. Forgot Password?");

    private final String text;
}
