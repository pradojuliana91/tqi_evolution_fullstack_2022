package br.com.tqi.bootcamp.bookstore.client.create;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class BadRequestCreateClientScenarios extends BaseTest {

    @Test
    public void shouldNotCreateClientPayloadNull() {
        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .when()
                .post(host + "/clients")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", Matchers.notNullValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaa", "asdfasdfasdfasdfasdfadfasdfadfasdfasdfasdfadfasdfaa"})
    public void shouldNotCreateClientWithNameInvalid(String name) {

        String requestJson = "{\"name\":\"" + name + "\", \"cpf\":\"11122233300\", \"address\":\"rua das alamedas 437\"," +
                " \"birthDate\":\"1923-01-29\"  }";


        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/clients")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_NAME))
                .body("errors[0].message", Matchers.is(INVALID_STRING_LENGTH_5_50));
    }

    @Test
    public void shouldNotCreateClientWithNameNull() {

        String requestJson = "{\"name\":null, \"cpf\":\"11122233300\", \"address\":\"rua das alamedas 437\"," +
                " \"birthDate\":\"1923-01-29\"  }";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/clients")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_NAME))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234567892", "123456789222"})
    public void shouldNotCreateClientWithCPFInvalid(String cpf) {

        String requestJson = "{\"name\":\"client 1\", \"cpf\":\"" + cpf + "\", \"address\":\"rua das alamedas 437\"," +
                " \"birthDate\":\"1923-01-29\"  }";


        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/clients")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_CPF))
                .body("errors[0].message", Matchers.is(INVALID_STRING_LENGTH_11_11));
    }

    @Test
    public void shouldNotCreateClientWithCPFNull() {

        String requestJson = "{\"name\":\"client 1\", \"cpf\":null, \"address\":\"rua das alamedas 437\"," +
                " \"birthDate\":\"1923-01-29\"  }";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/clients")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_CPF))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }

    @Test
    public void shouldNotCreateClientWithCPFWithNotNumber() {

        String requestJson = "{\"name\":\"client 1\", \"cpf\":\"aaabbbcccss\", \"address\":\"rua das alamedas 437\"," +
                " \"birthDate\":\"1923-01-29\"  }";

        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/clients")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_CPF))
                .body("errors[0].message", Matchers.is(MUST_BE_INTEGER));
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaa", "asdfasdfsdfasdfadfafsdfasdfadfadfasdfasdfadfasdfasdfasdfasdafasdfadfasdfasdfasdfasdfasdasfasdfasdfaaa"})
    public void shouldNotCreateClientWithAddressInvalid(String address) {

        String requestJson = "{\"name\":\"client 1\", \"cpf\":\"11122233300\", \"address\":\"" + address + "\"," +
                " \"birthDate\":\"1923-01-29\"  }";


        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/clients")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_ADDRESS))
                .body("errors[0].message", Matchers.is(INVALID_STRING_LENGTH_5_100));
    }

    @Test
    public void shouldNotCreateClientWithAddressNull() {

        String requestJson = "{\"name\":\"client 1\", \"cpf\":\"11122233300\", \"address\":null," +
                " \"birthDate\":\"1923-01-29\"  }";


        RestAssured.given()
                .auth().basic(USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when()
                .post(host + "/clients")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].parameter", Matchers.is(REQUEST_FIELD_ADDRESS))
                .body("errors[0].message", Matchers.is(MUST_HAVE_VALUE));
    }
}
