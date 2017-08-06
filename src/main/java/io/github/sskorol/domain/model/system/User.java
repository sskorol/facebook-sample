package io.github.sskorol.domain.model.system;

import lombok.Data;

@Data
public class User {

    private final String email;
    private final String password;
    private final String firstName;

    public static User dummy() {
        return new User("test", "test", "test");
    }
}
