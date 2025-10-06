package utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * MockServerManager:
 * Starts a WireMock server locally and defines mock API responses.
 */
public class MockServerManager {

    private static WireMockServer wireMockServer;

    // Start WireMock on a random available port
    public static void startServer() {
        if (wireMockServer == null) {
            wireMockServer = new WireMockServer(0); // port = 0 → random
            wireMockServer.start();
            configureFor("localhost", wireMockServer.port());
            System.out.println("✅ WireMock running at: " + getBaseUrl());
        }
    }

    // Stop WireMock after tests
    public static void stopServer() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            wireMockServer = null;
        }
    }

    // Return base URL for mock server
    public static String getBaseUrl() {
        if (wireMockServer != null) {
            return "http://localhost:" + wireMockServer.port();
        }
        return null;
    }

    // Setup fake endpoints (stubs)
    public static void setupStubs() {

        // Mock login endpoint
        stubFor(post(urlEqualTo("/login"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"token\":\"mocked-token-123\"}")));

        // Mock user endpoint
        stubFor(get(urlPathMatching("/users/\\d+"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"data\":{\"id\":1,\"email\":\"alice@example.com\",\"first_name\":\"Alice\",\"last_name\":\"Smith\",\"avatar\":\"avatar.png\"}}")));
    }
}

