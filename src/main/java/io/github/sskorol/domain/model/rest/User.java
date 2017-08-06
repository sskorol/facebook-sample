package io.github.sskorol.domain.model.rest;

import lombok.Data;

@Data
public class User {

    private final int id;
    private final String phone;
    private final String username;
    private final String website;
    private final Address address;
    private final String email;
    private final Company company;
    private final String name;
}