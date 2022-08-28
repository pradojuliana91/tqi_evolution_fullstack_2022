package br.com.tqi.bootcamp.bookstore.book.entry;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.UUID;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class BadRequestEntryBookScenarios extends BaseTest {


    @ParameterizedTest
    @ValueSource(strings = {"0", "10000001"})
    public void shouldNotBookEntryQuantityInvalid(String quantity) {

        String requestJsonEntry = "{\"quantity\":\""+ quantity +"\", \"costValue\":\"23999\"}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJsonEntry)
                .when()
                .post(host + "/books/" + UUID.randomUUID() + "/entry")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_QUANTITY));
    }

    @Test
    public void shouldNotBookEntryQuantityNull() {

        String requestJsonEntry = "{\"quantity\":null, \"costValue\":\"23999\"}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJsonEntry)
                .when()
                .post(host + "/books/" + UUID.randomUUID() + "/entry")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_QUANTITY))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"99", "10000001"})
    public void shouldNotBookEntryCostValueInvalid(String costValue) {

        String requestJsonEntry = "{\"quantity\":\"20\", \"costValue\":\"" + costValue + "\"}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJsonEntry)
                .when()
                .post(host + "/books/" + UUID.randomUUID() + "/entry")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_COST_VALUE));
    }

    @Test
    public void shouldNotBookEntryCostValueNull() {

        String requestJsonEntry = "{\"quantity\":\"20\", \"costValue\":null}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJsonEntry)
                .when()
                .post(host + "/books/" + UUID.randomUUID() + "/entry")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_COST_VALUE))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

}
