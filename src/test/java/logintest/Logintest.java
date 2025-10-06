package logintest;

import Frameworkbase.SpecBuilder;
import client.AuthClient;
import client.Userclient;
import io.qameta.allure.*;
import models.LoginRequest;
import models.LoginResponse;
import models.UserResponse;
import org.testng.annotations.*;
import utils.MockServerManager;
import utils.TokenManager;
import static org.testng.Assert.assertNotNull;

/**
 * Logintest:
 * Runs API tests using WireMock (mock server) instead of real API.
 */
@Epic("Authentication Tests")
@Feature("Login and User APIs")
public class Logintest {

    @BeforeClass
    @Step("Start WireMock server and setup stubs")
    public void setup() {
        MockServerManager.startServer();   // Start WireMock
        MockServerManager.setupStubs();    // Setup fake endpoints
        SpecBuilder.useMockServer();       // Switch RestAssured to mock base URL
    }

    @Test(description = "Verify login API returns a valid token")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogin() {
        // Create login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("abc@gmail.com");
        loginRequest.setPassword("pass@123");

        // Call login API (mocked)
        LoginResponse response = AuthClient.login(loginRequest);

        // Assert token exists
        assertNotNull(response.getToken(), "Token should not be null");

        // Store token for next test
        TokenManager.setToken(response.getToken());
        System.out.println("Mocked Token: " + TokenManager.getToken());
    }

    @Test(dependsOnMethods = "testLogin", description = "Verify get user API returns user details")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetUser() {
        // Call user API (mocked)
        UserResponse user = Userclient.getUserById(1);
        assertNotNull(user.getData(), "User data should not be null");
        System.out.println("User email: " + user.getData().getEmail());
    }

    @AfterClass
    @Step("Stop WireMock server")
    public void cleanup() {
        MockServerManager.stopServer();
    }
}
