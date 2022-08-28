package br.com.tqi.bootcamp.bookstore.client.update;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static br.com.tqi.bootcamp.bookstore.Data.PASSWORD;
import static br.com.tqi.bootcamp.bookstore.Data.USER_NAME;

public class SuccessUpdateClientScenarios extends BaseTest {

    @Test
    public void shouldUpdateClient() {

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

        requestJson = "{\"name\":\"client 2\", \"cpf\":\"99988877722\", \"address\":\"rua das al3 22\"," +
                " \"birthDate\":\"1978-11-15\"  }";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .put(host + "/clients/" + clientCode)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("code", Matchers.notNullValue())
                .body("name", Matchers.is("client 2"))
                .body("cpf", Matchers.is("99988877722"))
                .body("address", Matchers.is("rua das al3 22"))
                .body("birthDate", Matchers.is("1978-11-15"));
    }

}
