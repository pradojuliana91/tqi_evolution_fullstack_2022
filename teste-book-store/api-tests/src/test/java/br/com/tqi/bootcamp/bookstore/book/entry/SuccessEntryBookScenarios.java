package br.com.tqi.bootcamp.bookstore.book.entry;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.File;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class SuccessEntryBookScenarios extends BaseTest {

    @Test
    public void shouldEntryABook() {

        String requestJson = "{\"name\":\"" + AUTHOR_NAME + "\", \"description\":\"best of romance\"}";

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

        Response responseBook = RestAssured.given()
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
                .body("author.name", Matchers.is(AUTHOR_NAME))
                .extract()
                .response();

        String entryBook = responseBook.path("code");

        String requestJsonEntry = "{\"quantity\":\"20\", \"costValue\":\"23999\"}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJsonEntry)
                .when()
                .post(host + "/books/" + entryBook + "/entry")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .when()
                .get(host + "/books/" + entryBook)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("code", Matchers.is(entryBook))
                .body("quantity", Matchers.is(20));
    }
}
