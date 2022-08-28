package br.com.tqi.bootcamp.bookstore.authorization;

import br.com.tqi.bootcamp.bookstore.BaseTest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;

import static br.com.tqi.bootcamp.bookstore.Data.*;

public class AuthorizationScenarios extends BaseTest {

    @Test
    public void shouldReturnUnauthorizedWhenUsernameIsInvalid() {
        RestAssured.given()
                .auth().basic(INVALID_USER_NAME, PASSWORD)
                .header("Content-Type", "application/json")
                .when()
                .get(host + "/clients/all")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

}
