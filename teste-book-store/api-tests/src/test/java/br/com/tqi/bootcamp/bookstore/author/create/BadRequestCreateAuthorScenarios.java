package br.com.tqi.bootcamp.bookstore.author.create;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class BadRequestCreateAuthorScenarios extends BaseTest {

    @Test
    public void shouldNotCreateAuthorPayloadNull() {
        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .when()
                .post(host + "/authors")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", Matchers.notNullValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaa", "asdfasdfasdfasdfasdfadfasdfadfasdfasdfasdfadfasdfaa"})
    public void shouldNotCreateAuthorWithNameInvalid(String name) {

        String requestJson = "{\"name\":\"" + name + "\", \"description\":\"best of romance\"}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/authors")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_NAME))
                .body("errors[0].message", Matchers.is(INVALID_STRING_LENGTH_5_50));
    }

    @Test
    public void shouldNotCreateAuthorWithNameNull() {

        String requestJson = "{\"name\":null, \"description\":\"best of romance\"}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/authors")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_NAME))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaa", "asdfasdfsdfasdfadfafsdfasdfadfadfasdfasdfadfasdfasdfasdfasdafasdfadfasdfasdfasdfasdfasdasfasdfasdfaaa"})
    public void shouldNotCreateAuthorWithDescriptionInvalid(String description) {

        String requestJson = "{\"name\":\"julios silva castro\", \"description\":\"" + description + "\"}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/authors")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_DESCRIPTION))
                .body("errors[0].message", Matchers.is(INVALID_STRING_LENGTH_5_100));
    }

    @Test
    public void shouldNotCreateAuthorWithDescriptionNull() {

        String requestJson = "{\"name\":\"julios silva castro\", \"description\":null}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/authors")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_DESCRIPTION))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

}
