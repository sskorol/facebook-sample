package io.github.sskorol.domain.model.rest;

import lombok.Data;

@Data
public class Address {

    private final Geo geo;
    private final String zipcode;
    private final String street;
    private final String suite;
    private final String city;
}