package client;

import Frameworkbase.SpecBuilder;
import io.restassured.response.Response;
import models.UserResponse;
import utils.TokenManager;
import static io.restassured.RestAssured.given;

/**
 * UserClient:
 * Handles User-related API calls (secured endpoints).
 */
public class Userclient {

    public static UserResponse getUserById(int userId) {
        Response response = given()
                .spec(SpecBuilder.getRequestSpec())
                .header("Authorization", "Bearer " + TokenManager.getToken()) // Add token to header
                .when()
                .get("/users/" + userId) // GET user by ID
                .then()
                .spec(SpecBuilder.getResponseSpec200())
                .extract().response();

        return response.as(UserResponse.class);
    }
}
