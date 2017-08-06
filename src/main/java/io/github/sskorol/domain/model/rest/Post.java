package io.github.sskorol.domain.model.rest;

import lombok.Data;

@Data
public class Post {

    private final long userId;
    private final long id;
    private final String title;
    private final String body;
}
