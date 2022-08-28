package br.com.tqi.bootcamp.bookstore.author.update;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class NotFoundUpdateAuthorScenarios extends BaseTest {

    @Test
    public void shouldNotUpdateAuthor() {

        String requestJson = "{\"name\":\"atuthor 1\", \"description\":\"best of romance\"}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .put(host + "/authors/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", Matchers.is(NOT_FOUND_AUTHOR));

    }

}
