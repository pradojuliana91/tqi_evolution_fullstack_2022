package br.com.tqi.bootcamp.bookstore.author.findpage;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static br.com.tqi.bootcamp.bookstore.Data.PASSWORD;
import static br.com.tqi.bootcamp.bookstore.Data.USER_NAME;

public class SuccessFindPageAuthorScenarios extends BaseTest {

    @Test
    public void shouldFindPageAuthor() {

        String requestJson = "{\"name\":\"atuthor 1\", \"description\":\"best of romance\"}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/authors")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("code", Matchers.notNullValue())
                .body("name", Matchers.is("atuthor 1"))
                .body("description", Matchers.is("best of romance"));

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .get(host + "/authors?page=0&size=100")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("page",  Matchers.equalTo(0))
                .body("count",  Matchers.greaterThanOrEqualTo(1))
                .body("authors.size()",  Matchers.greaterThanOrEqualTo(1));
    }
}
