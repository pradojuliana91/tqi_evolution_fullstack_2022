package br.com.tqi.bootcamp.bookstore.author.find;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static br.com.tqi.bootcamp.bookstore.Data.PASSWORD;
import static br.com.tqi.bootcamp.bookstore.Data.USER_NAME;

public class SuccessFindAuthorScenarios extends BaseTest {

    @Test
    public void shouldFindAuthor() {

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

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .when()
                .get(host + "/authors/" + authorCode)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("code", Matchers.is(authorCode))
                .body("name", Matchers.is("atuthor 1"))
                .body("description", Matchers.is("best of romance"));
    }

}
