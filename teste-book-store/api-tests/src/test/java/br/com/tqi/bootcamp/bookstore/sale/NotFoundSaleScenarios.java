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

public class NotFoundSaleScenarios extends BaseTest {

    @Test
    public void shouldNotSaleWithClientNotExists() {

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

        Response responseBook2 = RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "multipart/form-data")
                .multiPart("title", TITLE_2)
                .multiPart("authorCode", authorCode)
                .multiPart("publisher", PUBLISHER_2)
                .multiPart("yearPublication", YEAR_PUBLICATION_2)
                .multiPart("saleValue", SALE_VALUE_2)
                .multiPart("file", new File(FILE))
                .when()
                .post(host + "/books")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        String codeBook2 = responseBook2.path("code");


        String product1 = "{\"bookCode\":\"" + codeBook1 + "\", \"quantity\":\"2\"}";
        String product2 = "{\"bookCode\":\"" + codeBook2 + "\", \"quantity\":\"3\"}";

        String requestSale = "{\"clientCode\":\"" + UUID.randomUUID() + "\", \"details\":[" + product1 + "," + product2 + "]}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestSale)
                .when()
                .post(host + "/sales")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", Matchers.is(NOT_FOUND_CLIENT));
    }

    @Test
    public void shouldNotSaleWithBookNotExists() {

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

        String product1 = "{\"bookCode\":\"" + UUID.randomUUID() + "\", \"quantity\":\"2\"}";
        String product2 = "{\"bookCode\":\"" + UUID.randomUUID() + "\", \"quantity\":\"3\"}";

        String requestSale = "{\"clientCode\":\"" + codeClient + "\", \"details\":[" + product1 + "," + product2 + "]}";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestSale)
                .when()
                .post(host + "/sales")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", Matchers.is(NOT_FOUND_BOOK));
    }
}
