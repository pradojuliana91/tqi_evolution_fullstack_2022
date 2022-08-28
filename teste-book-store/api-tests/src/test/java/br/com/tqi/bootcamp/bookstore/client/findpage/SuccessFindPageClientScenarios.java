package br.com.tqi.bootcamp.bookstore.client.findpage;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static br.com.tqi.bootcamp.bookstore.Data.PASSWORD;
import static br.com.tqi.bootcamp.bookstore.Data.USER_NAME;

public class SuccessFindPageClientScenarios extends BaseTest {

    @Test
    public void shouldFindPageClient() {

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
                .get(host + "/clients?page=0&size=100")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("page",  Matchers.equalTo(0))
                .body("count",  Matchers.greaterThanOrEqualTo(1))
                .body("clients.size()",  Matchers.greaterThanOrEqualTo(1));
    }
}
