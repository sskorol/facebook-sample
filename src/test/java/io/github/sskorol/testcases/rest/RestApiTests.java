package io.github.sskorol.testcases.rest;

import io.github.sskorol.domain.model.rest.Album;
import io.github.sskorol.domain.model.rest.Photo;
import io.github.sskorol.domain.model.rest.Post;
import io.github.sskorol.domain.model.rest.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import one.util.streamex.StreamEx;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.github.sskorol.framework.core.BaseConfig.BASE_CONFIG;
import static io.restassured.RestAssured.*;
import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.RandomUtils.nextInt;

import static org.assertj.core.api.Assertions.assertThat;

public class RestApiTests {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_CONFIG.restUrl();
    }

    @Test
    public void shouldRetrieveAllPosts() {
        final Response getPosts = get("/posts");
        getPosts.then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON);
        assertThat(getPosts.as(Post[].class)).hasSize(100);
    }

    @Test
    public void shouldNotFindMissingUser() {
        final int invalidUserId = StreamEx.of(get("/users").as(User[].class))
                                          .max(comparing(User::getId))
                                          .map(u -> nextInt(u.getId() + 1, u.getId() + 100))
                                          .orElse(-1);
        when().get("/users/" + invalidUserId).then().statusCode(404);
    }

    @Test
    public void albumsShouldHaveValidPhotoUrls() {
        final int randomAlbumId = nextInt(1, 101);
        StreamEx.of(get("/albums").as(Album[].class))
                .map(Album::getId)
                .filter(id -> id == randomAlbumId)
                .flatMap(id -> StreamEx.of(get("/photos").as(Photo[].class))
                                       .filter(photo -> photo.getAlbumId() == id))
                .map(Photo::getUrl)
                .forEach(url -> get(url).then().statusCode(200));
    }
}
