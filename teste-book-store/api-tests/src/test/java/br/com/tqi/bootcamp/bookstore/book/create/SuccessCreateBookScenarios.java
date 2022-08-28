package br.com.tqi.bootcamp.bookstore.book.create;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.File;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class SuccessCreateBookScenarios extends BaseTest {

    @Test
    public void shouldCreateABook() {

        String requestJson = "{\"name\":\""+ AUTHOR_NAME  +"\", \"description\":\"best of romance\"}";

        Response response = RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/authors")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        String authorCode = response.path("code");

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE)
                .multiPart("authorCode", authorCode)
                .multiPart("publisher", PUBLISHER)
                .multiPart("yearPublication", YEAR_PUBLICATION)
                .multiPart("saleValue", SALE_VALUE)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("code", Matchers.notNullValue())
                .body("title", Matchers.is(TITLE))
                .body("publisher", Matchers.is(PUBLISHER))
                .body("yearPublication", Matchers.is(YEAR_PUBLICATION))
                .body("saleValue", Matchers.is(SALE_VALUE))
                .body("image", Matchers.notNullValue())
                .body("author.code", Matchers.is(authorCode))
                .body("author.name", Matchers.is(AUTHOR_NAME));
    }
}
