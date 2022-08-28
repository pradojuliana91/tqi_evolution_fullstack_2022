package br.com.tqi.bootcamp.bookstore.book.update;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.File;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class SuccessUpdateBookScenarios extends BaseTest {

    @Test
    public void shouldUpdateABook() {

        String requestJson = "{\"name\":\"" + AUTHOR_NAME + "\", \"description\":\"best of romance\"}";

        Response responseAuthor = RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/authors")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        String authorCode = responseAuthor.path("code");

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

        String bookCode = responseBook.path("code");

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE_2)
                .multiPart("authorCode", authorCode)
                .multiPart("publisher", PUBLISHER_2)
                .multiPart("yearPublication", YEAR_PUBLICATION_2)
                .multiPart("saleValue", SALE_VALUE_2)
                .multiPart("file", new File(FILE_2))
                .when()
                .put(host + "/books" + "/" + bookCode)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("code", Matchers.is(bookCode))
                .body("title", Matchers.is(TITLE_2))
                .body("publisher", Matchers.is(PUBLISHER_2))
                .body("image", Matchers.notNullValue())
                .body("yearPublication", Matchers.is(YEAR_PUBLICATION_2))
                .body("saleValue", Matchers.is(SALE_VALUE_2));
    }
}








