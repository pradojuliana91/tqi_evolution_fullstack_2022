package br.com.tqi.bootcamp.bookstore.book.create;

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

public class BadRequestCreateBookScenarios extends BaseTest {

    @ParameterizedTest
    @ValueSource(strings = {"aaaa", "asdfasdfsdfasdfadfafsdfasdfadfadfasdfasdfadfasdfasdfasdfasdafasdfadfasdfasdfasdfasdfasdasfasdfasdfaaa"})
    public void shouldNotCreateBookTitleInvalid(String title) {

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", title)
                .multiPart("authorCode", UUID.randomUUID())
                .multiPart("publisher", PUBLISHER)
                .multiPart("yearPublication", YEAR_PUBLICATION)
                .multiPart("saleValue", SALE_VALUE)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_TITLE))
                .body("errors[0].message", Matchers.is(INVALID_STRING_LENGTH_5_100));
    }

    @Test
    public void shouldNotCreateBookTitleNull() {

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("authorCode", UUID.randomUUID())
                .multiPart("publisher", PUBLISHER)
                .multiPart("yearPublication", YEAR_PUBLICATION)
                .multiPart("saleValue", SALE_VALUE)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_TITLE))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @Test
    public void shouldNotCreateBookAuthorCodeNull() {

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE)
                .multiPart("publisher", PUBLISHER)
                .multiPart("yearPublication", YEAR_PUBLICATION)
                .multiPart("saleValue", SALE_VALUE)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_AUTHOR_CODE))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaa", "asdfasdfsdfasdfadfafsdfasdfadfadfasdfasdfadfasdfasdfasdfasdafasdfadfasdfasdfasdfasdfasdasfasdfasdfaaa"})
    public void shouldNotCreateBookPublisherInvalid(String publisher) {

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE)
                .multiPart("authorCode", UUID.randomUUID())
                .multiPart("publisher", publisher)
                .multiPart("yearPublication", YEAR_PUBLICATION)
                .multiPart("saleValue", SALE_VALUE)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_PUBLISHER))
                .body("errors[0].message", Matchers.is(INVALID_STRING_LENGTH_5_100));
    }

    @Test
    public void shouldNotCreateBookPublisherNull() {

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE)
                .multiPart("authorCode", UUID.randomUUID())
                .multiPart("yearPublication", YEAR_PUBLICATION)
                .multiPart("saleValue", SALE_VALUE)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_PUBLISHER))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "12345"})
    public void shouldNotCreateBookYearPublicationInvalid(String yearPublication) {

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE)
                .multiPart("authorCode", UUID.randomUUID())
                .multiPart("publisher", PUBLISHER)
                .multiPart("yearPublication", yearPublication)
                .multiPart("saleValue", SALE_VALUE)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_YEAR_PUBLICATION))
                .body("errors[0].message", Matchers.is(INVALID_STRING_LENGTH_4_4));
    }

    @Test
    public void shouldNotCreateBookYearPublicationNull() {

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE)
                .multiPart("authorCode", UUID.randomUUID())
                .multiPart("publisher", PUBLISHER)
                .multiPart("saleValue", SALE_VALUE)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_YEAR_PUBLICATION))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @Test
    public void shouldNotCreateBookYearPublicationIsNoNumber() {

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE)
                .multiPart("authorCode", UUID.randomUUID())
                .multiPart("publisher", PUBLISHER)
                .multiPart("yearPublication", "YYYY")
                .multiPart("saleValue", SALE_VALUE)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_YEAR_PUBLICATION))
                .body("errors[0].message", Matchers.is(MUST_BE_INTEGER));
    }

    @ParameterizedTest
    @ValueSource(strings = {"99", "10000001"})
    public void shouldNotCreateBookSaleValueInvalid(String saleValue) {

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE)
                .multiPart("authorCode", UUID.randomUUID())
                .multiPart("publisher", PUBLISHER)
                .multiPart("yearPublication", YEAR_PUBLICATION)
                .multiPart("saleValue", saleValue)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_SALE_VALUE));
    }

    @Test
    public void shouldNotCreateBookSaleValueNull() {

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE)
                .multiPart("authorCode", UUID.randomUUID())
                .multiPart("publisher", PUBLISHER)
                .multiPart("yearPublication", YEAR_PUBLICATION)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_SALE_VALUE))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }
}
