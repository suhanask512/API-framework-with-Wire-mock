package client;

import io.restassured.response.Response;
import Frameworkbase.SpecBuilder;
import models.UserResponse;
import utils.TokenManager;

import static io.restassured.RestAssured.given;

public class Userclient {

    public static UserResponse getUserById(int userId) {
        Response response = given()
                .spec(SpecBuilder.getRequestSpec())
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .when()
                .get("/users/" + userId)
                .then()
                .spec(SpecBuilder.getResponseSpec200())
                .extract().response();

        return response.as(UserResponse.class);
    }
}

