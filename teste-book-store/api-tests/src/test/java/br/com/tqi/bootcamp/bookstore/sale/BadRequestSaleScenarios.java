package br.com.tqi.bootcamp.bookstore.sale;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class BadRequestSaleScenarios extends BaseTest {

    @Test
    public void shouldNotSaleWithWithoutClient() {

        String product1 = "{\"bookCode\":\"" + UUID.randomUUID() + "\", \"quantity\":\"2\"}";
        String product2 = "{\"bookCode\":\"" + UUID.randomUUID() + "\", \"quantity\":\"3\"}";

        String requestSale = "{\"clientCode\":null, \"details\":[" + product1 + "," + product2 + "]}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestSale)
                .when()
                .post(host + "/sales")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_CLIENT_CODE))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @Test
    public void shouldNotSaleWithWithoutDetails() {

        String requestSale = "{\"clientCode\":\"" + UUID.randomUUID() + "\", \"details\":null}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestSale)
                .when()
                .post(host + "/sales")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_DETAILS))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @Test
    public void shouldNotSaleWithWithoutDetailBookCode() {

        String product1 = "{\"bookCode\":null, \"quantity\":\"2\"}";

        String requestSale = "{\"clientCode\":\"" + UUID.randomUUID() + "\", \"details\":[" + product1 + "]}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestSale)
                .when()
                .post(host + "/sales")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_DETAILS + "[0]." + REQUEST_FIELD_BOOK_CODE))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @Test
    public void shouldNotSaleWithWithoutDetailQuantity() {

        String product1 = "{\"bookCode\":\"" + UUID.randomUUID() + "\", \"quantity\":null}";

        String requestSale = "{\"clientCode\":\"" + UUID.randomUUID() + "\", \"details\":[" + product1 + "]}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestSale)
                .when()
                .post(host + "/sales")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_DETAILS + "[0]." + REQUEST_FIELD_QUANTITY))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @Test
    public void shouldNotSaleWithDuplicateProduct() {

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

        Response responseBook1 = RestAssured.given()
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
                .extract()
                .response();

        String codeBook1 = responseBook1.path("code");

        String requestJsonClient = "{\"name\":\"client 1\", \"cpf\":\"11122233300\", \"address\":\"rua das alamedas 437\"," +
                " \"birthDate\":\"1923-01-29\"  }";

        Response responseCli = RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJsonClient)
                .when()
                .post(host + "/clients")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        String codeClient = responseCli.path("code");

        String product1 = "{\"bookCode\":\"" + codeBook1 + "\", \"quantity\":\"2\"}";
        String product2 = "{\"bookCode\":\"" + codeBook1 + "\", \"quantity\":\"3\"}";

        String requestSale = "{\"clientCode\":\"" + codeClient + "\", \"details\":[" + product1 + "," + product2 + "]}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestSale)
                .when()
                .post(host + "/sales")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", Matchers.is(DUPLICATE_SALE_BOOK));
    }

    @Test
    public void shouldDontSaleWithDosentHasStock() {

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

        Response responseBook1 = RestAssured.given()
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
                .extract()
                .response();

        String codeBook1 = responseBook1.path("code");

        String requestJsonClient = "{\"name\":\"client 1\", \"cpf\":\"11122233300\", \"address\":\"rua das alamedas 437\"," +
                " \"birthDate\":\"1923-01-29\"  }";

        Response responseCli = RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJsonClient)
                .when()
                .post(host + "/clients")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        String codeClient = responseCli.path("code");

        String product1 = "{\"bookCode\":\"" + codeBook1 + "\", \"quantity\":\"2\"}";

        String requestSale = "{\"clientCode\":\"" + codeClient + "\", \"details\":[" + product1 + "]}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestSale)
                .when()
                .post(host + "/sales")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
                //.body("message", Matchers.is("Livro " + TITLE + " só contém 0 em estoque!")); encode
    }
}
