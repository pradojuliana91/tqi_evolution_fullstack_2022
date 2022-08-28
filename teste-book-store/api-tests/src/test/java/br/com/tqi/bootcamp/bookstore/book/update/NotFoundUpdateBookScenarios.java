package br.com.tqi.bootcamp.bookstore.book.update;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static br.com.tqi.bootcamp.bookstore.Data.*;
import static io.restassured.RestAssured.given;

public class NotFoundUpdateBookScenarios extends BaseTest {

    @Test
    public void shouldNotUpdateBook() {

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE_2)
                .multiPart("authorCode", UUID.randomUUID().toString())
                .multiPart("publisher", PUBLISHER_2)
                .multiPart("yearPublication", YEAR_PUBLICATION_2)
                .multiPart("saleValue", SALE_VALUE_2)
                .multiPart("file", new File(FILE_2))
                .when()
                .put(host + "/books" + "/" + UUID.randomUUID().toString())
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", Matchers.is(NOT_FOUND_BOOK));

    }

}
