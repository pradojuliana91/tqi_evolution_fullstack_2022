package br.com.tqi.bootcamp.bookstore.book.entry;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class NotFoundEntryBookScenarios extends BaseTest {

    @Test
    public void shouldNotFindBook() {

        String requestJsonEntry = "{\"quantity\":\"20\", \"costValue\":\"23999\"}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJsonEntry)
                .when()
                .post(host + "/books/" + UUID.randomUUID() + "/entry")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", Matchers.is(NOT_FOUND_BOOK));
    }

}
