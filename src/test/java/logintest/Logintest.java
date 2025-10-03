package logintest;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Frameworkbase.SpecBuilder;
import client.AuthClient;
import client.Userclient;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.Filter;
import models.LoginRequest;
import models.LoginResponse;
import models.UserResponse;
import utils.MockServerManager;
import utils.TokenManager;

@Epic("Authentication Tests")
@Feature("Login and User APIs")
public class Logintest {

    private final Filter allureFilter = new AllureRestAssured();

    @BeforeClass
    @Step("Start WireMock server and setup stubs")
    public void setup() {
        MockServerManager.startServer();
        MockServerManager.setupStubs();
        SpecBuilder.useMockServer();
    }

    @Test(description = "Verify login API returns a valid token")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test calls the login endpoint with valid credentials and verifies the token is returned")
    public void testLogin() {
        step("Create login request", () -> {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail("abc@gmail.com");
            loginRequest.setPassword("pass@123");

            step("Call login API", () -> {
                LoginResponse loginResponse = AuthClient.login(loginRequest);
                assertNotNull(loginResponse.getToken(), "Token should not be null");

                TokenManager.setToken(loginResponse.getToken());
                System.out.println("Token: " + TokenManager.getToken());
            });
        });
    }

    @Test(dependsOnMethods = "testLogin", description = "Verify get user API returns user details")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test retrieves user details by ID and verifies the response")
    public void testGetUser() {
        step("Call getUserById API", () -> {
            UserResponse user = Userclient.getUserById(1);
            assertNotNull(user.getData(), "User data should not be null");
            System.out.println("User email: " + user.getData().getEmail());
        });
    }

    @AfterClass
    @Step("Stop WireMock server")
    public void cleanup() {
        MockServerManager.stopServer();
    }

    /**
     * Helper method to wrap steps in Allure
     */
    @Step("{message}")
    private void step(String message, Runnable runnable) {
        runnable.run();
    }
}
