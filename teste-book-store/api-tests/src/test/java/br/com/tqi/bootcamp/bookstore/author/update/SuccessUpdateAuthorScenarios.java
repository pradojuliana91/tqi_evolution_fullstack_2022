package br.com.tqi.bootcamp.bookstore.author.update;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class SuccessUpdateAuthorScenarios extends BaseTest {

    @Test
    public void shouldUpdateAuthor() {

        String requestJson = "{\"name\":\"atuthor 1\", \"description\":\"best of romance\"}";

        Response response = RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/authors")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("code", Matchers.notNullValue())
                .body("name", Matchers.is("atuthor 1"))
                .body("description", Matchers.is("best of romance"))
                .extract()
                .response();

        String authorCode = response.path("code");

        requestJson = "{\"name\":\"atuthor 2\", \"description\":\"best of terror\"}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .put(host + "/authors/" + authorCode)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("code", Matchers.notNullValue())
                .body("name", Matchers.is("atuthor 2"))
                .body("description", Matchers.is("best of terror"));
    }

}
