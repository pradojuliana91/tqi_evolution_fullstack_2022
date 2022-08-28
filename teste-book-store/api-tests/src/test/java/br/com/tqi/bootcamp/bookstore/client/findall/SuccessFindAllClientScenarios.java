package br.com.tqi.bootcamp.bookstore.client.findall;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static br.com.tqi.bootcamp.bookstore.Data.PASSWORD;
import static br.com.tqi.bootcamp.bookstore.Data.USER_NAME;

public class SuccessFindAllClientScenarios extends BaseTest {

    @Test
    public void shouldFindAllClient() {

        String requestJson = "{\"name\":\"client 1\", \"cpf\":\"11122233300\", \"address\":\"rua das alamedas 437\"," +
                " \"birthDate\":\"1923-01-29\"  }";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/clients")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("code", Matchers.notNullValue())
                .body("name", Matchers.is("client 1"))
                .body("cpf", Matchers.is("11122233300"))
                .body("address", Matchers.is("rua das alamedas 437"))
                .body("birthDate", Matchers.is("1923-01-29"));

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .get(host + "/clients/all")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()",  Matchers.greaterThanOrEqualTo(1));
    }
}
