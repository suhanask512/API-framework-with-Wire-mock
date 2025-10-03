package utils;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;

public class MockServerManager {

    private static WireMockServer wireMockServer;

    // Start WireMock on a random port
    public static void startServer() {
        if (wireMockServer == null) {
            wireMockServer = new WireMockServer(0);
            wireMockServer.start();
            configureFor("localhost", wireMockServer.port());
            System.out.println("WireMock running at: " + getBaseUrl());
        }
    }

    public static void stopServer() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            wireMockServer = null;
        }
    }

    public static String getBaseUrl() {
        if (wireMockServer != null) {
            return "http://localhost:" + wireMockServer.port();
        }
        return null;
    }

    // Define your stubbed endpoints here
    public static void setupStubs() {

        // Mock login
        stubFor(post(urlEqualTo("/login"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"token\":\"mocked-token-123\"}")));

        // Mock user
        stubFor(get(urlPathMatching("/users/\\d+"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"data\":{\"id\":1,\"email\":\"alice@example.com\",\"first_name\":\"Alice\",\"last_name\":\"Smith\",\"avatar\":\"avatar.png\"}}")));
    }
}
