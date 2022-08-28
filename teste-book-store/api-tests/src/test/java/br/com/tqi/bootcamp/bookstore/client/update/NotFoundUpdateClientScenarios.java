package br.com.tqi.bootcamp.bookstore.client.update;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class NotFoundUpdateClientScenarios extends BaseTest {

    @Test
    public void shouldNotUpdateClient() {

        String requestJson = "{\"name\":\"client 1\", \"cpf\":\"11122233300\", \"address\":\"rua das alamedas 437\"," +
                " \"birthDate\":\"1923-01-29\"  }";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .put(host + "/clients/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", Matchers.is(NOT_FOUND_CLIENT));

    }

}
