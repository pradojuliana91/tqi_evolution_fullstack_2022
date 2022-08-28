package br.com.tqi.bootcamp.bookstore.client.delete;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class SuccessDeleteClientScenarios extends BaseTest {

    @Test
    public void shouldDeleteClient() {

        String requestJson = "{\"name\":\"client 1\", \"cpf\":\"11122233300\", \"address\":\"rua das alamedas 437\"," +
                " \"birthDate\":\"1923-01-29\"  }";

        Response response = RestAssured.given()
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
                .body("birthDate", Matchers.is("1923-01-29"))
                .extract()
                .response();

        String clientCode = response.path("code");

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .when()
                .delete(host + "/clients/" + clientCode)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .when()
                .get(host + "/clients/" + clientCode)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", Matchers.is(NOT_FOUND_CLIENT));
    }

}
