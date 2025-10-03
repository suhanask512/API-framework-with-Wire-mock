package client;

import Frameworkbase.SpecBuilder;
import models.LoginRequest;
import models.LoginResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient {

    public static LoginResponse login(LoginRequest request) {
        Response response = given()
                .spec(SpecBuilder.getRequestSpec())
                .body(request)
                .when()
                .post("/login")
                .then()
                .spec(SpecBuilder.getResponseSpec200())
                .extract().response();

        return response.as(LoginResponse.class);
    }
}


