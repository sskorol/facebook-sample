package io.github.sskorol.domain.model.rest;

import lombok.Data;

@Data
public class Photo {

    private final long albumId;
    private final long id;
    private final String title;
    private final String url;
    private final String thumbnailUrl;
}
