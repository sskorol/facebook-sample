package io.github.sskorol.domain.model.rest;

import lombok.Data;

@Data
public class Album {

    private final int userId;
    private final int id;
    private final String title;
}
